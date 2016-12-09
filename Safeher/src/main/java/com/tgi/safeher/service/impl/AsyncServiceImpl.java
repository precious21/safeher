package com.tgi.safeher.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.geo.Point;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.API.thirdParty.BrainTree.BrainTree;
import com.tgi.safeher.API.thirdParty.Twilio.SmsTwilioRest;
import com.tgi.safeher.beans.AppUserRegFlowBean;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideTrackingBean;
import com.tgi.safeher.common.enumeration.ActiveDriverStatusEnum;
import com.tgi.safeher.common.enumeration.PaymentModeEnum;
import com.tgi.safeher.common.enumeration.ProcessStateAndActionEnum;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.enumeration.UserRegFlowEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusLogEntity;
import com.tgi.safeher.entity.AppUserRegTrackEntity;
import com.tgi.safeher.entity.AppUserTypeEntity;
import com.tgi.safeher.entity.DriverDrivingDetailEntity;
import com.tgi.safeher.entity.DriverDrivingSummaryEntity;
import com.tgi.safeher.entity.DriverLocationTrackEntity;
import com.tgi.safeher.entity.PaymentModeEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.ProcessActionEntity;
import com.tgi.safeher.entity.ProcessStateEntity;
import com.tgi.safeher.entity.RideBillDistribution;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RidePaymnetDistributionEntity;
import com.tgi.safeher.entity.RideProcessTrackingDetailEntity;
import com.tgi.safeher.entity.RideProcessTrackingEntity;
import com.tgi.safeher.entity.RideQuickInfoEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserPromotionUseEntity;
import com.tgi.safeher.entity.UserRegFlowEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverDrivingDetailMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrakListMongoEntity;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.service.converter.MapConverter;
import com.tgi.safeher.repo.ActiveDriverCustomRepository;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.repo.DriverDrivingDetailRepository;
import com.tgi.safeher.repo.DriverLocationTrackRepository;
import com.tgi.safeher.repository.ArrivalTimeRepository;
import com.tgi.safeher.repository.CriteriaRepository;
import com.tgi.safeher.repository.DriverNavigationRepository;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;
import com.twilio.http.TwilioRestClient;

@Service
@EnableAsync
@Transactional
@Scope("prototype")
public class AsyncServiceImpl {
	private static final Logger logger = Logger.getLogger(AsyncServiceImpl.class);
	
	@Autowired
	private RideDao rideDao;
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private RideRequestRepository rideRequestRepository;
	
	@Autowired
	private DriverNavigationRepository driverNavigationRepository;
	
	@Autowired
	private MapConverter mapConverter;

	@Autowired
	private CriteriaRepository criteriaRepository;

	@Autowired
	private ArrivalTimeRepository arrivalTimeRepository;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	@Autowired
	private DriverLocationTrackRepository driverLocationTrackRepository;
	
	@Autowired
	private DriverDrivingDetailRepository driverDrivingDetailRepository;
	
	@Autowired
	private ActiveDriverCustomRepository activeDriverCustomRepository;
	
	@Autowired
	private IAsyncEmailService iAsyncEmailService;

	
	
	
	@Async
    public void saveRideSearchResultDetailEntityAndRideReqResEntity(RideSearchResultDetailEntity
    		rideSearchResultDetailEntity, RideRequestResponseEntity rideRequestResponseEntity) throws InterruptedException {
		logger.info("******Entering in saveRideSearchResultDetailEntityAndRideReqResEntity  *******");
		rideDao.saveOrUpdate(rideSearchResultDetailEntity);
		rideRequestResponseEntity.setRideSearchResultDetail(rideSearchResultDetailEntity);
		rideDao.saveOrUpdate(rideRequestResponseEntity);
    }

	@Async
    public void saveRideRequestResponseEntity(RideRequestResponseEntity
    		rideRequestResponseEntity) throws InterruptedException {
		logger.info("******Entering in saveRideRequestResponseEntity  *******");
		rideDao.saveOrUpdate(rideRequestResponseEntity);
    }

	@Async
    public void saveRideQuickInfo(RideQuickInfoEntity
    		rideQuickInfoEntity) throws InterruptedException {
		logger.info("******Entering in saveRideQuickInfo  *******");
		rideDao.saveOrUpdate(rideQuickInfoEntity);
    }

	@Async
    public void saveRideTracking(RideTrackingBean bean) throws InterruptedException {
		logger.info("******Entering in saveRideTracking  *******");
		RideProcessTrackingEntity rideProcessTrackingEntity = new RideProcessTrackingEntity();
		RideProcessTrackingDetailEntity rideProcessTrackingDetailEntity = new RideProcessTrackingDetailEntity();
		ProcessStateEntity processStateEntity = new ProcessStateEntity();
		ProcessActionEntity processActionEntity = new ProcessActionEntity();
		AppUserEntity passenger = new AppUserEntity();
		AppUserEntity driver = new AppUserEntity();
		AppUserTypeEntity appUserTypeEntity = new AppUserTypeEntity();
		
		appUserTypeEntity.setAppUserTypeId(new Integer(bean.getIsDriver()));
		
		rideProcessTrackingEntity = rideDao.findBy(
				RideProcessTrackingEntity.class, "requestNo", bean.getRequestNo());
		if(rideProcessTrackingEntity == null){
			rideProcessTrackingEntity = new RideProcessTrackingEntity();
			if(StringUtil.isNotEmpty(bean.getPassengerId())){
				passenger.setAppUserId(new Integer(bean.getPassengerId()));
				rideProcessTrackingEntity.setPassenger(passenger);	
			}
		}
		
		processStateEntity.setProcessStateId(new Integer(bean.getState()));
		processActionEntity.setProcessActionId(new Integer(bean.getAction()));
		
		//saving rideProcessTracking
		rideProcessTrackingEntity.setRequestNo(bean.getRequestNo());
		rideProcessTrackingEntity.setIsComplete(bean.getIsComplete());
		rideProcessTrackingEntity.setRideNo(bean.getRideNo());
		rideProcessTrackingEntity.setInvoiceNo(bean.getInvoiceNo());
		if(StringUtil.isNotEmpty(bean.getDriverId())){
			driver.setAppUserId(new Integer(bean.getDriverId()));
			rideProcessTrackingEntity.setDriver(driver);	
		}
		rideDao.saveOrUpdate(rideProcessTrackingEntity);
		
		//saving rideProcessTrackingDetail
		rideProcessTrackingDetailEntity.setAppUserType(appUserTypeEntity);
		rideProcessTrackingDetailEntity.setRideProcessTracking(rideProcessTrackingEntity);
		rideProcessTrackingDetailEntity.setProcessState(processStateEntity);
		rideProcessTrackingDetailEntity.setProcessAction(processActionEntity);
		rideProcessTrackingDetailEntity.setActionTime(DateUtil.getCurrentTimestamp());
		rideProcessTrackingDetailEntity.setStatusFlag(bean.getStatus());
		if(rideDao.checkForRideTrackingReContinued(rideProcessTrackingEntity.
				getRideProcessTrackingId(), new Integer(bean.getAction()))){
			rideProcessTrackingDetailEntity.setIsRecontinued("1");
			rideProcessTrackingDetailEntity.setRecontinuedStatus(bean.getContinuedStatus());
			rideProcessTrackingDetailEntity.setRecontinuedTime(DateUtil.getCurrentTimestamp());
		}else{
			rideProcessTrackingDetailEntity.setIsRecontinued("0");
		}
		rideDao.saveOrUpdate(rideProcessTrackingDetailEntity);

		//updating rideQuickInfo
		if(StringUtil.isNotEmpty(bean.getAction()) && bean.getAction().equals(
				ProcessStateAndActionEnum.Accept_And_Navigate_By_Driver.getValue()+"")){
			rideDao.updateRideQuickInfo(bean.getRequestNo()+"_"+bean.getDriverId(), 
					rideProcessTrackingEntity.getRideProcessTrackingId());
//			rideQuickInfoEntity.setRideProcessTracking(rideProcessTrackingEntity);
//			rideDao.saveOrUpdate(rideQuickInfoEntity);	
		}
		
//		if(StringUtil.isNotEmpty(bean.getStatus()) && 
//		bean.getStatus().equals(StatusEnum.Intial.getValue()+"")){
//	rideDao.updateRideQuickInfo(bean.getRequestNo(), 
//			rideProcessTrackingEntity.getRideProcessTrackingId());
//}
		logger.info("******Exitingt in saveRideTracking  *******");
    }
	
	@Async
    public void deletRideRequestResponseFromRedis(String requestNo, String passegnerId) 
    		throws InterruptedException {
//		Map<Object, Object> list3 = rideRequestRepository
//				.findAllRideRequestResponseResultDetail();
//		System.out.println("sizeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+list3.size());
		logger.info("******Entering in deletRideRequestResponseFromRedis with request# and passengerId# "+requestNo+" "+passegnerId+"  *******");
		String[] requestNO = requestNo.split("_");
		if(requestNO != null && requestNO.length > 0){
			List<RideSearchResultDetailEntity> list = rideDao.
					getSpecificRideDrivers(requestNO[0]);
			if(list != null && list.size() > 0){
				for(RideSearchResultDetailEntity rideSearchResultDetailEntity : list){
					rideRequestRepository.delateRideRequestResponseResultDetail(
							rideSearchResultDetailEntity.getRequestNo());
				}	
			}
		}
//		if(StringUtil.isNotEmpty(passegnerId)){
//			criteriaRepository.deleteCriteria(passegnerId);
//		}
    }
	
	@Async
    public void deletRideCriteriaAndArrivalTimeFromRedis(String passegnerId) 
    		throws InterruptedException {
		if(StringUtil.isNotEmpty(passegnerId)){
			criteriaRepository.deleteCriteria(passegnerId);
			arrivalTimeRepository.deleteArrivalTimeFlag(passegnerId);
		}
    }
	

	@Async
    public void deletDriverNavigationFromRedis(String driverId) 
    		throws InterruptedException {
		logger.info("******Entering in deletDriverNavigationFromRedis with request# and driverId# "+driverId+"  *******");
		if(StringUtil.isNotEmpty(driverId)){
			driverNavigationRepository.deleteDriverNavigation(driverId);
		}
    }

	@Async
	public void replaceTokensAndroid(String prevToken, String currToken){
		logger.info("******Entering in replaceTokensAndroid with prevToken and currToken "+prevToken+" "+currToken+"  *******");
		rideDao.replaceTokensAndroid(prevToken, currToken);
	}

	@Async
	public void replaceFCMToken(String prevToken, String currToken){
		logger.info("******Entering in replaceFCMToken with prevToken and currToken "+prevToken+" "+currToken+"  *******");
		rideDao.replaceFCMToken(prevToken, currToken);
	}

	/*@Async
	public void saveActiveDriverIntoDb(MapBean bean){
		logger.info("******Entering in saveActiveDriverFromLiveNavigation with bean "+bean+"  *******");
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){
			ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
			DriverLocationTrackEntity driverLocationTrackEntity = new DriverLocationTrackEntity();
			activeDriverLocationEntity = rideDao.findByOject(
					ActiveDriverLocationEntity.class, "appUser", driver);
			if(activeDriverLocationEntity == null){
				activeDriverLocationEntity = new ActiveDriverLocationEntity();
			}	
			mapConverter.convertBeanToDriverLoc(
					activeDriverLocationEntity, bean, driverLocationTrackEntity);
			populateEntities(activeDriverLocationEntity, driverLocationTrackEntity);
			
			activeDriverLocationEntity.setRideNo(bean.getRideNo());
			rideDao.saveOrUpdate(activeDriverLocationEntity);

			driverLocationTrackEntity.setRideNo(bean.getRideNo());
			driverLocationTrackEntity.setActiveDriverLocationId(
					activeDriverLocationEntity.getActiveDriverLocatoinId()+"");
			rideDao.saveOrUpdate(driverLocationTrackEntity);

		}
		logger.info("******Exiting in saveActiveDriverFromLiveNavigation with bean "+bean+"  *******");
	}*/
	@Async
	public void saveActiveDriverIntoDb(MapBean bean){
		logger.info("******Entering in saveActiveDriverFromLiveNavigation with bean "+bean+"  *******");
		ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
		
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){
		//	ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
			DriverLocationTrackEntity driverLocationTrackEntity = new DriverLocationTrackEntity();
			//activeDriverLocationEntity = rideDao.findByOject(
			//		ActiveDriverLocationEntity.class, "appUser", driver);
			//activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(new Integer(bean.getAppUserId()));
			activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
			//if(activeDriverLocationEntity == null){
			//	activeDriverLocationEntity = new ActiveDriverLocationEntity();
			//}	
			if(activeDriverLocationMongoEntity == null){
				activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
			}
			//mapConverter.convertBeanToDriverLoc(
			//		activeDriverLocationEntity, bean, driverLocationTrackEntity);
			mapConverter.convertBeanToDriverLocMongo(
					activeDriverLocationMongoEntity, bean, driverLocationTrackEntity);
			//populateEntities(activeDriverLocationEntity, driverLocationTrackEntity);
			populateEntitiesMongo(activeDriverLocationMongoEntity, driverLocationTrackEntity);
			//activeDriverLocationEntity.setRideNo(bean.getRideNo());
			activeDriverLocationMongoEntity.setRideNo(bean.getRideNo());
			activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
			//rideDao.saveOrUpdate(activeDriverLocationEntity);

			driverLocationTrackEntity.setRideNo(bean.getRideNo());
			//driverLocationTrackEntity.setActiveDriverLocationId(
			//		activeDriverLocationEntity.getActiveDriverLocatoinId()+"");
			driverLocationTrackEntity.setActiveDriverLocationId(
				activeDriverLocationMongoEntity.getActiveDriverLocatoinId()+"");
			rideDao.saveOrUpdate(driverLocationTrackEntity);

		}
		logger.info("******Exiting in saveActiveDriverFromLiveNavigation with bean "+bean+"  *******");
	}

	@Async
	public void saveDriverLocationTrackIntoMongo(MapBean bean){
		logger.info("******Entering in saveDriverLocationTrackIntoMongo with bean "+bean+"  *******");
		
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){

			ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();	
			DriverLocationTrackMongoEntity driverLocationTrackMongoEntity = new DriverLocationTrackMongoEntity();
			DriverLocationTrakListMongoEntity driverLocationTrakListMongoEntity = new DriverLocationTrakListMongoEntity();
			List<DriverLocationTrakListMongoEntity> list = new ArrayList<DriverLocationTrakListMongoEntity>();
			
			activeDriverLocationMongoEntity = activeDriverLocationRepository.
					findByAppUserId(driver.getAppUserId());
			if(activeDriverLocationMongoEntity != null){
//				activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();			
				mapConverter.convertBeanToDriverLocMongoV2(
						activeDriverLocationMongoEntity, bean, driverLocationTrakListMongoEntity);
				populateEntitiesMongoV2(activeDriverLocationMongoEntity, driverLocationTrakListMongoEntity);
				activeDriverLocationMongoEntity.setRideNo(bean.getRideNo());
				activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
				if(StringUtil.isEmpty(bean.getRideNo())){
					driverLocationTrakListMongoEntity.setActiveDriverStatus(
							ActiveDriverStatusEnum.Free.getValue()+"");
					bean.setRideNo("");
				}else{
					if(StringUtil.isNotEmpty(bean.getRideNo()) && 
							bean.getRideNo().contains("RE")){
						driverLocationTrakListMongoEntity.setActiveDriverStatus(
								ActiveDriverStatusEnum.Pre_Ride.getValue()+"");
					}else if(StringUtil.isNotEmpty(bean.getRideNo()) && 
							bean.getRideNo().contains("RD")){
						driverLocationTrakListMongoEntity.setActiveDriverStatus(
								ActiveDriverStatusEnum.On_Ride.getValue()+"");
					}	
				}

				driverLocationTrakListMongoEntity.setRideNo(bean.getRideNo());
				driverLocationTrakListMongoEntity.setIsSavedIntoSql("0");
				
				driverLocationTrackMongoEntity = driverLocationTrackRepository.
						findByAppUserId(driver.getAppUserId());
				if(driverLocationTrackMongoEntity == null){
					driverLocationTrackMongoEntity=new DriverLocationTrackMongoEntity();
					driverLocationTrackMongoEntity.setAppUserId(driver.getAppUserId());
				}else{
					list = driverLocationTrackMongoEntity.getList();
					if(list == null){
						list = new ArrayList<DriverLocationTrakListMongoEntity>();
					}
				}
				driverLocationTrakListMongoEntity.setIdNo(
						list.size()+1);
				list.add(driverLocationTrakListMongoEntity);
				driverLocationTrackMongoEntity.setActiveDriverLocationId(
					activeDriverLocationMongoEntity.getActiveDriverLocatoinId()+"");
//				driverLocationTrackMongoEntity.getList().add(driverLocationTrakListMongoEntity);
				driverLocationTrackMongoEntity.setList(list);
				driverLocationTrackRepository.save(driverLocationTrackMongoEntity);
			}


		}
		logger.info("******Exiting in saveDriverLocationTrackIntoMongo with bean "+bean+"  *******");
	}

	@Async
	public void saveDriverLocationTrackListIntoMongo(MapBean bean) throws GenericException{
		logger.info("******Entering in saveDriverLocationTrackIntoMongo with bean "+bean+"  *******");
		
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){

			ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();	
			DriverLocationTrackMongoEntity driverLocationTrackMongoEntity = new DriverLocationTrackMongoEntity();
			List<DriverLocationTrakListMongoEntity> list = new ArrayList<DriverLocationTrakListMongoEntity>();
			
			activeDriverLocationMongoEntity = activeDriverLocationRepository.
					findByAppUserId(driver.getAppUserId());
			if(activeDriverLocationMongoEntity == null){
				activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
				activeDriverLocationMongoEntity.setAppUserId(driver.getAppUserId());
			}
			mapConverter.convertBeanToActivDriverMongo(
					activeDriverLocationMongoEntity, bean);
			activeDriverLocationMongoEntity.setRideNo(bean.getRideNo());
			activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
			
			driverLocationTrackMongoEntity = driverLocationTrackRepository.
					findByAppUserId(driver.getAppUserId());
			if(driverLocationTrackMongoEntity == null){
				driverLocationTrackMongoEntity=new DriverLocationTrackMongoEntity();
				driverLocationTrackMongoEntity.setAppUserId(driver.getAppUserId());
			}else{
				list = driverLocationTrackMongoEntity.getList();
				if(list == null){
					list = new ArrayList<DriverLocationTrakListMongoEntity>();
				}
			}
			if(bean.getLatlngList() != null && bean.getLatlngList().size() > 0){
				for(Point p : bean.getLatlngList()){
					DriverLocationTrakListMongoEntity driverLocationTrakListMongoEntity = 
							new DriverLocationTrakListMongoEntity();
					populateDriverLocationList(p, driverLocationTrakListMongoEntity);
					driverLocationTrakListMongoEntity.setIdNo(
							list.size()+1);
					list.add(driverLocationTrakListMongoEntity);
				}
			}else{
				//for the time being
				//when driver will send all lat lng of 3 min then remove this
				DriverLocationTrakListMongoEntity driverLocationTrakListMongoEntity = 
						new DriverLocationTrakListMongoEntity();
				driverLocationTrakListMongoEntity.setActiveDriverStatus(
						ActiveDriverStatusEnum.Free.getValue()+"");
				driverLocationTrakListMongoEntity.setRideNo("");
				driverLocationTrakListMongoEntity.setIsSavedIntoSql("0");
				driverLocationTrakListMongoEntity.setTrackTime(
						DateUtil.getCurrentTimestamp());
				driverLocationTrakListMongoEntity.setLatVal(bean.getLat()+"");
				driverLocationTrakListMongoEntity.setLongVal(bean.getLng()+"");
				driverLocationTrakListMongoEntity.setIdNo(
						list.size()+1);
				list.add(driverLocationTrakListMongoEntity);
			}

			driverLocationTrackMongoEntity.setActiveDriverLocationId(
				activeDriverLocationMongoEntity.getActiveDriverLocatoinId()+"");
			driverLocationTrackMongoEntity.setList(list);
			driverLocationTrackRepository.save(driverLocationTrackMongoEntity);


		}
		logger.info("******Exiting in saveDriverLocationTrackIntoMongo with bean "+bean+"  *******");
	}
	
	@Async
	public void saveDriverDrivingDetailIntoMongo(DriverDrivingDetailBean bean){
		logger.info("******Entering in saveDriverDrivingDetailIntoMongo with bean "+bean+"  *******");
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){
			bean.setDate(DateUtil.getMongoDbDate(new Date()));
			DriverDrivingDetailMongoEntity driverDrivingDetailMongoEntity = driverDrivingDetailRepository.
					findByAppUserIdAndDate(driver.getAppUserId(), bean.getDate());
			if(driverDrivingDetailMongoEntity == null){
				driverDrivingDetailMongoEntity = new DriverDrivingDetailMongoEntity();
			}
			mapConverter.convertBeanToDriverDrivDetailMongo(
					driverDrivingDetailMongoEntity, bean);
			//note: saving time in seconds, distance in miles, earnings in $
			driverDrivingDetailRepository.save(driverDrivingDetailMongoEntity);

		}
		logger.info("******Exiting in saveDriverDrivingDetailIntoMongo with bean "+bean+"  *******");
	}
	
	public void populateEntities(ActiveDriverLocationEntity activeDriverLocationEntity, 
			DriverLocationTrackEntity driverLocationTrackEntity){
		activeDriverLocationEntity.setIsOnline("1");
		activeDriverLocationEntity.setIsBooked("1");
		activeDriverLocationEntity.setIsRequested("1");
		activeDriverLocationEntity.setIsPhysical("1");

		driverLocationTrackEntity.setTrackTime(
				DateUtil.getCurrentTimestamp());
		
	}
	
	public void populateEntitiesMongo(ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity, 
			DriverLocationTrackEntity driverLocationTrackEntity){ activeDriverLocationMongoEntity.setIsOnline("1");
		activeDriverLocationMongoEntity.setIsBooked("1");
		activeDriverLocationMongoEntity.setIsRequested("1");
		activeDriverLocationMongoEntity.setIsPhysical("1");

		driverLocationTrackEntity.setTrackTime(
				DateUtil.getCurrentTimestamp());
		
	}
	
	public void populateDriverLocationList(Point p,  
			DriverLocationTrakListMongoEntity driverLocationTrakListMongoEntity){
		driverLocationTrakListMongoEntity.setActiveDriverStatus(
				ActiveDriverStatusEnum.Free.getValue()+"");
		driverLocationTrakListMongoEntity.setRideNo("");
		driverLocationTrakListMongoEntity.setIsSavedIntoSql("0");
		driverLocationTrakListMongoEntity.setTrackTime(
				DateUtil.getCurrentTimestamp());
		driverLocationTrakListMongoEntity.setLatVal(p.getX()+"");
		driverLocationTrakListMongoEntity.setLongVal(p.getY()+"");
	}
	
	public void populateEntitiesMongoV2(ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity, 
			DriverLocationTrakListMongoEntity driverLocationTrackEntity){ 
//		activeDriverLocationMongoEntity.setIsOnline("1");
//		activeDriverLocationMongoEntity.setIsBooked("1");
//		activeDriverLocationMongoEntity.setIsRequested("1");
//		activeDriverLocationMongoEntity.setIsPhysical("1");

		driverLocationTrackEntity.setTrackTime(
				DateUtil.getCurrentTimestamp());
		
	}

	@Async
	public void giftEndRideAsyncCall(RideBillEntity rideBillEntity, RideBean rideBean, 
			Double rideAmount, CreditCardInfoBean creditCardInfoBean){
		logger.info("******Entering in giftEndRideAsyncCall async *******");
		
		
		logger.info("******Exiting in giftEndRideAsyncCall async *******");
	}

	@Async
	public void endRideAsyncCall(RideBillEntity rideBillEntity, RideBean rideBean, 
			Double rideAmount, Double totalDiscount, CreditCardInfoBean creditCardInfoBean, 
			UserPromotionEntity userPromotionEntity, UserPromotionUseEntity userPromotionUseEntity){
		logger.info("******Entering in endRideAsyncCall async *******");
		
		String message = BrainTree.transactionUsingCustomer(
				rideAmount+"", EncryptDecryptUtil.decrypt(creditCardInfoBean.getBtCustomer()));	
		if (!message.contains("Error")) {

			System.out.println("Transcation ID    :"+message);
			rideBillEntity.setBtTransactionNo(EncryptDecryptUtil.encrypt(
					message));
			rideBillEntity.setPaymentTime(DateUtil.now());
			rideBillEntity.setTotalAmount(Double.valueOf(rideBean
					.getTotalAmount()));
			PaymentModeEntity paymentMode =new PaymentModeEntity();
			paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
			rideBillEntity.setPaymentMode(paymentMode);
			StatusEntity status_payment =new StatusEntity();
			status_payment.setStatusId(StatusEnum.Deduct.getValue());
			rideBillEntity.setStatus(status_payment);
			//rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);
			
			if (appUserDao.update(rideBillEntity)) {
				RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
				ridePaymentDistributionEntity.setIsDue("1");
				ridePaymentDistributionEntity.setRideBill(rideBillEntity);
				ridePaymentDistributionEntity.setStatus(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
				//Will Change On Customer Demand Distribution here
				Double charityPayment =  Double.valueOf(rideBean.getTotalAmount())*2/100;
				Double driverPayment = (Double.valueOf(rideBean.getTotalAmount()))*20/100;
				System.out.println((Double.valueOf(rideBean.getTotalAmount())));
				Double companyPayment = (Double.valueOf(rideBean.getTotalAmount()))* 78/100;
				
				rideBean.setCharityAmount(charityPayment+"");
				rideBean.setDriverAmount(driverPayment+"");
				rideBean.setCompanyAmount(companyPayment+"");
				
				ridePaymentDistributionEntity.setCharityAmount(charityPayment);
				ridePaymentDistributionEntity.setCompanyAmount(companyPayment);
				ridePaymentDistributionEntity.setDriverAmount(driverPayment);
				appUserDao.saveOrUpdate(ridePaymentDistributionEntity);
				
				//saving setRideBill
				if(userPromotionEntity != null && 
						userPromotionEntity.getPromotionInfo() != null && 
							totalDiscount > 0){
					userPromotionUseEntity.setRideBill(rideBillEntity);
				}
				if(userPromotionEntity != null && 
						userPromotionEntity.getPromotionInfo() != null && 
							totalDiscount > 0){
					userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
					userPromotionUseEntity.setRideBill(rideBillEntity);
					userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
					userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
					appUserDao.saveOrUpdate(userPromotionUseEntity);
				}
				
				//send async invoice email
				try {
					iAsyncEmailService.sendInvoiceEmail(rideBean);
				} catch (GenericException e1) {
					e1.printStackTrace();
				}

				//saving rideTracking
				try {
					String[] requestNO = rideBean.getRequestNo().split("_");
					if(requestNO != null && requestNO.length > 0){
						RideTrackingBean rideTrackingBean = new RideTrackingBean();
						rideTrackingBean.setRequestNo(requestNO[0]);
						rideTrackingBean.setInvoiceNo(rideBean.getInvoiceNo());
						rideTrackingBean.setStatus(StatusEnum.Complete.getValue()+"");
						rideTrackingBean.setState(ProcessStateAndActionEnum.END_RIDE.getValue()+"");
						rideTrackingBean.setAction(ProcessStateAndActionEnum.End_Ride_By_Driver.getValue()+"");
						rideTrackingBean.setIsComplete("1");
						saveRideTracking(rideTrackingBean);	
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//saving userPromotionUse
				if(userPromotionEntity != null && 
						userPromotionEntity.getPromotionInfo() != null && 
							totalDiscount > 0){
					userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
					userPromotionUseEntity.setRideBill(rideBillEntity);
					userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
					userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
					appUserDao.saveOrUpdate(userPromotionUseEntity);
				}
				
				//set isBooked and isRequest 0
				AppUserEntity driver = appUserDao.findById(AppUserEntity.class,
						new Integer(rideBean.getAppUserByAppUserDriver()));
				if(driver != null){
					ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
					if (driver.getIsDriver().equalsIgnoreCase("1")) {
						activeDriverLocationMongoEntity = activeDriverLocationRepository.
								findByAppUserId(driver.getAppUserId());
						if (activeDriverLocationMongoEntity != null) {
							activeDriverLocationMongoEntity.setIsRequested("0");
							activeDriverLocationMongoEntity.setIsBooked("0");
							activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
						}
					}
				}

				//async saving driverDrivingDetailIntoMongo
				DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
				detailBean.setAppUserId(driver.getAppUserId()+"");
				detailBean.setTotalRides(1.0);
				detailBean.setTotalEarning(new Double(
						rideBean.getTotalAmount()));
				detailBean.setDriverEarning((
						Double.valueOf(rideBean.getTotalAmount()))*20/100);
				detailBean.setDisputeAmount(0.0);
				saveDriverDrivingDetailIntoMongo(detailBean);

				//async saving driverTrackIntoDrivingDetailIntoMongo
				saveLocationTrackIntoDrivingDetail(driver.getAppUserId(), "3");
				
			}	
		}else{
			
		}
		
		logger.info("******Exiting in endRideAsyncCall async *******");
	}

	@Async
	public void saveSignUpFlow(AppUserRegFlowBean bean){
		logger.info("******Entering in saveSignUpFlow async with data: "+bean+"  *******");
		
		UserRegFlowEntity userRegFlowEntity = (UserRegFlowEntity) appUserDao.findByIdParamCommon(
				"UserRegFlowEntity", "userRegFlowId", new Integer(bean.getStepCode()));
		if(userRegFlowEntity != null){
			AppUserRegTrackEntity appUserRegTrackEntity = new AppUserRegTrackEntity();
			UserLoginEntity userLoginEntity = appUserDao.findByIdParam2(
					new Integer(bean.getAppUserId()));
			if (userLoginEntity != null && 
					userLoginEntity.getAppUser() != null) {
				userLoginEntity.setUserRegFlowId(
						userRegFlowEntity.getStepCode()+"");
				if(userRegFlowEntity.getUserRegFlowId() == 
						UserRegFlowEnum.BankInfo.getValue()){
					userLoginEntity.setIsComplete("1");	
				}else{
					userLoginEntity.setIsComplete("0");
				}
				appUserDao.saveOrUpdate(userLoginEntity);
				appUserRegTrackEntity.setAppUser(userLoginEntity.getAppUser());
				appUserRegTrackEntity.setUserRegFlow(userRegFlowEntity);
				appUserRegTrackEntity.setActionTme(DateUtil.getCurrentTimestamp());
				appUserRegTrackEntity.setIsCompleted(bean.getIsCompleted());
				if(bean.getIsFromApp().equals("1")){
					appUserRegTrackEntity.setIsFromApp("0");
				}else{
					appUserRegTrackEntity.setIsFromApp("1");	
				}
				rideDao.saveOrUpdate(appUserRegTrackEntity);

				//if verificationRequired = 1
				//send verification email
				//send completion email
				//send success email
				//send fail email
				
//				if(userRegFlowEntity.getIsVerificationRequired() != null && 
//						userRegFlowEntity.getIsVerificationRequired().equals("1")){
//					
//					appUserRegTrackEntity.setIsVerified(bean.getIsVerified());
//					appUserRegTrackEntity.setIsVerificationEmailSent(
//							bean.getIsVerificationEmailSent());
//					appUserRegTrackEntity.setVerificationEmailTime(DateUtil.getCurrentTimestamp());
//				}
//				if(StringUtil.isNotEmpty(bean.getIsCompletionEmailSent())){
//					appUserRegTrackEntity.setIsCompletionEmailSent(bean.getIsCompletionEmailSent());
//					appUserRegTrackEntity.setCompletionEmailTime(
//							DateUtil.getCurrentTimestamp()+"");
//				}
			}
		}
		
	}
	
	@Async
	public void saveLocationTrackIntoDrivingDetail(Integer appUserId, String rideType){

		DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
		DriverLocationTrackMongoEntity driverLocationTrackMongoEntity = 
				activeDriverCustomRepository.findDriverTrack(appUserId);
		if(driverLocationTrackMongoEntity != null){
			if(driverLocationTrackMongoEntity.getList() != null && 
					driverLocationTrackMongoEntity.getList().size() > 0){
				int count = 1;
				Double distance = 0.0;
				double preLat = 0.0;
				double preLng = 0.0;
				Date startDate = null;
				Date endDate = null;
				for(DriverLocationTrakListMongoEntity obj : driverLocationTrackMongoEntity.getList()){
					if(count == 1){
						preLat = new Double(obj.getLatVal());
						preLng = new Double(obj.getLongVal());
						startDate = obj.getTrackTime();
					}else if(count == driverLocationTrackMongoEntity.getList().size()){
						endDate = obj.getTrackTime();
					}
					distance += Common.getDistance(preLat, preLng, new Double(
							obj.getLatVal()), new Double(obj.getLongVal()));
//					obj.setIsSavedIntoSql("1");
					activeDriverCustomRepository.updateIsSavedIntoSqlToOne(
							appUserId, obj.getIdNo());
					count++;
				}
				//note: onlne = 1, pre rie = 2 and ride = 3
				if(rideType.equals("1")){
					detailBean.setTotalOnlineTime(
							Common.getSecFromTwoDates(startDate, endDate));
					detailBean.setTotalOnlineDistance(new Double(
							new DecimalFormat("##.##").format(distance / 1609.34)));
					
				}else if(rideType.equals("2")){
					detailBean.setTotalPreRideTime(
							Common.getSecFromTwoDates(startDate, endDate));
					detailBean.setTotalPreRideDistance(new Double(
							new DecimalFormat("##.##").format(distance / 1609.34)));
					
				}else if(rideType.equals("3")){
					detailBean.setTotalRideTime(
							Common.getSecFromTwoDates(startDate, endDate));
					detailBean.setTotalRideDistance(new Double(
							new DecimalFormat("##.##").format(distance / 1609.34)));
					
				}
				detailBean.setAppUserId(appUserId+"");
				detailBean.setDate(DateUtil.getMongoDbDate(new Date()));
				saveDriverDrivingDetail(detailBean);
			}
		}
	}
	
	@Async
	public void saveLocationTrackIntoDrivingDetailFromQuartz(Integer appUserId, Date date){
		//call this method from quartz for online time track of driver
		DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
		DriverLocationTrackMongoEntity driverLocationTrackMongoEntity = 
				activeDriverCustomRepository.findDriverTrackForOnline(appUserId);
		if(driverLocationTrackMongoEntity != null){
			if(driverLocationTrackMongoEntity.getList() != null && 
					driverLocationTrackMongoEntity.getList().size() > 0){
				int count = 1;
				Double distance = 0.0;
				double preLat = 0.0;
				double preLng = 0.0;
				Date startDate = null;
				Date endDate = null;
				for(DriverLocationTrakListMongoEntity obj : driverLocationTrackMongoEntity.getList()){
					if(count == 1){
						preLat = new Double(obj.getLatVal());
						preLng = new Double(obj.getLongVal());
						startDate = obj.getTrackTime();
					}else if(count == driverLocationTrackMongoEntity.getList().size()){
						endDate = obj.getTrackTime();
					}
					distance += Common.getDistance(preLat, preLng, new Double(
							obj.getLatVal()), new Double(obj.getLongVal()));
//					obj.setIsSavedIntoSql("1");
					activeDriverCustomRepository.updateIsSavedIntoSqlToOne(
							appUserId, obj.getIdNo());
					count++;
				}
				detailBean.setTotalOnlineTime(
						Common.getSecFromTwoDates(startDate, endDate));
				detailBean.setTotalOnlineDistance(new Double(
						new DecimalFormat("##.##").format(distance / 1609.34)));
				detailBean.setAppUserId(appUserId+"");
				detailBean.setDate(date);
				
				saveDriverDrivingDetail(detailBean);
			}
		}
	}

	public void saveDriverDrivingDetail(DriverDrivingDetailBean bean){
		logger.info("******Entering in saveDriverDrivingDetail with bean "+bean+"  *******");
		AppUserEntity driver = rideDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(driver != null){
			
			DriverDrivingDetailMongoEntity driverDrivingDetailMongoEntity = driverDrivingDetailRepository.
					findByAppUserIdAndDate(driver.getAppUserId(), bean.getDate());
			if(driverDrivingDetailMongoEntity == null){
				driverDrivingDetailMongoEntity = new DriverDrivingDetailMongoEntity();
			}
			mapConverter.convertBeanToDriverDrivDetailMongo(
					driverDrivingDetailMongoEntity, bean);
			//note: saving time in seconds, distance in miles, earnings in $
			driverDrivingDetailRepository.save(driverDrivingDetailMongoEntity);

		}
		logger.info("******Exiting in saveDriverDrivingDetail with bean "+bean+"  *******");
	}

	public void saveDriverDrivingDetailIntoSql(){
		logger.info("******Entering in saveDriverDrivingDetailIntoSql*******");
		Calendar calendar = Calendar.getInstance();
		DriverDrivingDetailEntity driverDetail =new DriverDrivingDetailEntity();
		Date date=new Date();
		calendar.setTime(date);
		AppUserEntity appUser;
		
	Timestamp st1=new Timestamp(DateUtil.getStartOfDay(calendar.getTime()).getTime());
	Timestamp st2=new Timestamp(DateUtil.getEndOfDay(calendar.getTime()).getTime());
		calendar.set(Calendar.DAY_OF_MONTH, DateUtil.getDay(date) - 1);
		System.out.println(DateUtil.getStartOfDay(calendar.getTime()));
		System.out.println(DateUtil.getEndOfDay(calendar.getTime()));
		List<DriverDrivingDetailMongoEntity> driverDrivingDetailMongoEntity = driverDrivingDetailRepository
				.findByDateBetween(new Date(st1.getTime()),new Date(st2.getTime()));
		
		if (CollectionUtil.isNotEmpty(driverDrivingDetailMongoEntity)) {
			for (DriverDrivingDetailMongoEntity driverMongo : driverDrivingDetailMongoEntity) {
				appUser =new AppUserEntity();
				appUser.setAppUserId(driverMongo.getAppUserId());
				DriverDrivingSummaryEntity driverSummary = appUserDao.findDriverDrivingSummary(appUser);
				if (driverSummary == null) {
					prepareDriverSummary(driverSummary, driverMongo, appUser);
				} else {
					prepareDriverSummary(driverSummary, driverMongo, appUser);
				}
				prepareDriverDrivingDetail(driverDetail, driverSummary, driverMongo);
			}
		}
		
		if (CollectionUtil.isNotEmpty(driverDrivingDetailMongoEntity)) {
			System.out.println(driverDrivingDetailRepository.deleteByDateBetween(new Date(st1.getTime()), new Date(st2.getTime())));
		}
		
		logger.info("******Exiting in saveDriverDrivingDetailIntoSql*******");
	}
	
	private void prepareDriverDrivingDetail(DriverDrivingDetailEntity driverDetail,
			DriverDrivingSummaryEntity driverSummary, DriverDrivingDetailMongoEntity driverMongo) {
		driverDetail.setStartDate(DateUtil.getCurrentTimestamp());
		driverDetail.setDriverDrivingSummaryEntity(driverSummary);
		driverDetail.setDisputeAmount(driverMongo.getDisputeAmount()!=null ? driverMongo.getDisputeAmount()+"" : "0" );
		driverDetail.setDriverEarning(driverMongo.getDriverEarning()!=null ? driverMongo.getDriverEarning()+"" : "0");
		driverDetail.setTotalAcceptedRequest(driverMongo.getTotalAcceptedRequest() !=null ? driverMongo.getTotalAcceptedRequest()+"" : "0");
		driverDetail.setTotalCancelPreRides(driverMongo.getTotalCancelPreRides()!=null ? driverMongo.getTotalCancelPreRides()+"" : "0");
		driverDetail.setTotalEarning(driverMongo.getTotalEarning() !=null ? driverMongo.getTotalEarning()+"" : "0" );
		driverDetail.setTotalOnlineDistance(driverMongo.getTotalOnlineDistance()!=null ? driverMongo.getTotalOnlineDistance()+"" : "0");
		driverDetail.setTotalOnlineTime(driverMongo.getTotalOnlineTime()!=null ? driverMongo.getTotalOnlineTime()+"" : "0");
		driverDetail.setTotalRideTime(driverMongo.getTotalRideTime()!=null ? driverMongo.getTotalRideTime()+"" : "0");
		driverDetail.setTotalRides(driverMongo.getTotalRides()!=null ? driverMongo.getTotalRides()+"" : "0");
		driverDetail.setTotalRideDistance(driverMongo.getTotalRideDistance()!=null ? driverMongo.getTotalRideDistance()+"" : "0");
		driverDetail.setTotalPreRideTime(driverMongo.getTotalPreRideTime()!=null ? driverMongo.getTotalPreRideTime()+"" : "0");
		driverDetail.setTotalPreRideDistance(driverMongo.getTotalPreRideDistance()!=null ? driverMongo.getTotalPreRideDistance()+"" : "0");
		driverDetail.setTotalRequests(driverMongo.getTotalRequests()!=null ? driverMongo.getTotalRequests()+"" : "0");
		appUserDao.save(driverDetail);	
		
	}

	private void prepareDriverSummary(DriverDrivingSummaryEntity driverSummary,
			DriverDrivingDetailMongoEntity driverMongo,AppUserEntity appUser) {
		if(driverSummary==null){
			driverSummary = new DriverDrivingSummaryEntity();
			driverSummary.setAppUser(appUser);
			driverSummary.setDisputeAmount(driverMongo.getDisputeAmount()!=null ? driverMongo.getDisputeAmount()+"" : "0" );
			driverSummary.setDistanceUnit("Milies");
			driverSummary.setDriverEarning(driverMongo.getDriverEarning()!=null ? driverMongo.getDriverEarning()+"" : "0");
			driverSummary.setTotalAcceptedRequest(driverMongo.getTotalAcceptedRequest() !=null ? driverMongo.getTotalAcceptedRequest()+"" : "0");
			driverSummary.setModifiedTime(DateUtil.getCurrentTimestamp());
			driverSummary.setTotalCancelPreRides(driverMongo.getTotalCancelPreRides()!=null ? driverMongo.getTotalCancelPreRides()+"" : "0");
			driverSummary.setTotalEarning(driverMongo.getTotalEarning() !=null ? driverMongo.getTotalEarning()+"" : "0" );
			driverSummary.setTotalOnlineDistance(driverMongo.getTotalOnlineDistance()!=null ? driverMongo.getTotalOnlineDistance()+"" : "0");
			driverSummary.setTotalOnlineTime(driverMongo.getTotalOnlineTime()!=null ? driverMongo.getTotalOnlineTime()+"" : "0");
			driverSummary.setTotalRideTime(driverMongo.getTotalRideTime()!=null ? driverMongo.getTotalRideTime()+"" : "0");
			driverSummary.setTotalRides(driverMongo.getTotalRides()!=null ? driverMongo.getTotalRides()+"" : "0");
			driverSummary.setTotalRideDistance(driverMongo.getTotalRideDistance()!=null ? driverMongo.getTotalRideDistance()+"" : "0");
			driverSummary.setTotalPreRideTime(driverMongo.getTotalPreRideTime()!=null ? driverMongo.getTotalPreRideTime()+"" : "0");
			driverSummary.setTotalPreRideDistance(driverMongo.getTotalPreRideDistance()!=null ? driverMongo.getTotalPreRideDistance()+"" : "0");
			driverSummary.setTotalRequests(driverMongo.getTotalRequests()!=null ? driverMongo.getTotalRequests()+"" : "0");
			appUserDao.save(driverSummary);
		} else {
			Double dispute = (driverMongo.getDisputeAmount() != null ? driverMongo.getDisputeAmount() : 0)
					+ Double.valueOf(driverSummary.getDisputeAmount());
			driverSummary.setDisputeAmount(dispute + "");

			Double drEar = (driverMongo.getDriverEarning() != null ? driverMongo.getDriverEarning() : 0)
					+ Double.valueOf(driverSummary.getDriverEarning());
			driverSummary.setDriverEarning(drEar + "");

			Double drTotal = (driverMongo.getTotalAcceptedRequest() != null ? driverMongo.getTotalAcceptedRequest() : 0)
					+ Double.valueOf(driverSummary.getTotalAcceptedRequest());
			driverSummary.setTotalAcceptedRequest(drTotal + "");

			driverSummary.setModifiedTime(DateUtil.getCurrentTimestamp());
			Double drTotalCancel = (driverMongo.getTotalCancelPreRides() != null ? driverMongo.getTotalCancelPreRides()
					: 0) + Double.valueOf(driverSummary.getTotalCancelPreRides());
			driverSummary.setTotalCancelPreRides(drTotalCancel + "");

			Double drTotalEr = (driverMongo.getTotalEarning() != null ? driverMongo.getTotalEarning() : 0)
					+ Double.valueOf(driverSummary.getTotalEarning());
			driverSummary.setTotalEarning(drTotalEr + "");

			Double drTotalOnlinDis = (driverMongo.getTotalOnlineDistance() != null
					? driverMongo.getTotalOnlineDistance() : 0)
					+ Double.valueOf(driverSummary.getTotalOnlineDistance());
			driverSummary.setTotalOnlineDistance(drTotalOnlinDis + "");

			Double drTotalOnlinTim = (driverMongo.getTotalOnlineTime() != null ? driverMongo.getTotalOnlineTime() : 0)
					+ Double.valueOf(driverSummary.getTotalOnlineTime());
			driverSummary.setTotalOnlineTime(drTotalOnlinTim + "");

			Double drTotalRideTim = (driverMongo.getTotalRideTime() != null ? driverMongo.getTotalRideTime() : 0)
					+ Double.valueOf(driverSummary.getTotalRideTime());
			driverSummary.setTotalRideTime(drTotalRideTim + "");

			Double drTotalRides = (driverMongo.getTotalRides() != null ? driverMongo.getTotalRides() : 0)
					+ Double.valueOf(driverSummary.getTotalRides());
			driverSummary.setTotalRides(drTotalRides + "");

			Double drTotalRidesDistance = (driverMongo.getTotalRideDistance() != null
					? driverMongo.getTotalRideDistance() : 0) + Double.valueOf(driverSummary.getTotalRideDistance());
			driverSummary.setTotalRideDistance(drTotalRidesDistance + "");

			Double drTotalPreRidesTime = (driverMongo.getTotalPreRideTime() != null ? driverMongo.getTotalPreRideTime()
					: 0) + Double.valueOf(driverSummary.getTotalPreRideTime());
			driverSummary.setTotalPreRideTime(drTotalPreRidesTime + "");

			Double drTotalPreRidesDistance = (driverMongo.getTotalPreRideDistance() != null
					? driverMongo.getTotalPreRideDistance() : 0)
					+ Double.valueOf(driverSummary.getTotalPreRideDistance());
			driverSummary.setTotalPreRideDistance(drTotalPreRidesDistance + "");

			Double drTotalRequest = (driverMongo.getTotalRequests() != null ? driverMongo.getTotalRequests() : 0)
					+ Double.valueOf(driverSummary.getTotalRequests());
			driverSummary.setTotalRequests(drTotalRequest + "");

			appUserDao.update(driverSummary);
		}
	}

	/*
	 * Save User Secret Code
	 */
	@Async
	public void saveUserEmailCode(AppUserPhoneEmailStatusEntity appUserPhoneEmailStatusEntity, AppUserPhoneEmailStatusLogEntity appUserPhoneEmailStatusLogEntity ){
	
		boolean found = false;
		try{
			found = appUserDao.save(appUserPhoneEmailStatusEntity);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(found){
			try{
					appUserPhoneEmailStatusLogEntity.setAppUserPhoneEmailStatus(appUserPhoneEmailStatusEntity);
					appUserDao.saveOrUpdate(appUserPhoneEmailStatusLogEntity);
				}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
/*	
	 * Save User Secret Code
	 
	@Async
	public String sendSMS(String email, String  body, String appUserId){
		String status = " ";
		PersonDetailEntity personDetailEntity = null;
		try{
			SmsTwilioRest smsTwilioRest = new SmsTwilioRest();
			UserLoginEntity userLoginEntity = appUserDao.findByIdParam2(Integer.parseInt(appUserId));
			if(userLoginEntity != null){
				if(userLoginEntity.getAppUser()!=null){
					personDetailEntity = userLoginEntity.getAppUser().getPersonDetail();
				}
			
			}
			if(personDetailEntity != null){
				status = smsTwilioRest.sendSMS("+"+personDetailEntity.getPrimaryCell(), "+18574018199", body, "");
			}
		}catch (Exception e) {
			// TODO: handle exception
			status = "error";
		}
		
		return status;
		
		
	}*/
	
}
