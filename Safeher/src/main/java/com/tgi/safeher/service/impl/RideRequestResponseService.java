package com.tgi.safeher.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.model.LatLng;
import com.notnoop.exceptions.NetworkIOException;
import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.Andriod.PushNotificationsAndriod;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.API.thirdParty.IOS.PushNotificationsIOS;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.DistanceAPIBeanV2;
import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ReasonBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideQuickInfoBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.beans.RideTrackingBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ColorEnum;
import com.tgi.safeher.common.enumeration.ProcessStateAndActionEnum;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.common.thread.RideRequestResponseThread;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.PreRideEntity;
import com.tgi.safeher.entity.ReasonEntity;
import com.tgi.safeher.entity.RideColorEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideFinalizeEntity;
import com.tgi.safeher.entity.RideProcessTrackingDetailEntity;
import com.tgi.safeher.entity.RideQuickInfoEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.RideSearchResultEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.VehicleInfoEntity;
import com.tgi.safeher.entity.VehicleModelEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.jms.Producer;
import com.tgi.safeher.jms.model.Notification;
import com.tgi.safeher.map.service.IGoogleMapServices;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.repository.ActiveDriverStatusRepository;
import com.tgi.safeher.repository.IRideColorManagementRepository;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RequestStatusRepository;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.repository.RideRequestResponseRepository;
import com.tgi.safeher.repository.RideSearchResultDetailRepository;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.service.validator.SafeHerCommonValidator;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class RideRequestResponseService implements IRideRequestResponseService {

	private static final Logger logger = Logger.getLogger(RideRequestResponseService.class);
	@Autowired
	private SignUpConverter signUpConverter;

	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private RideDao rideDao;

	@Autowired
	private Producer producer;

	@Autowired
	private RideRequestResponseThread rideRequestResponseThread;

	@Autowired
	private IGoogleMapServices iGoogleMapServices;

	@Autowired
	private IGoogleMapsAPIService iGoogleMapsAPIService;
	
	@Autowired
	private RideRequestResponseRepository rideRequestResponseRepository;
	
	@Autowired
	private ActiveDriverStatusRepository activeDriverStatusRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private RideRequestRepository rideRequestRepository;
	
	@Autowired
	private RideSearchResultDetailRepository rideSearchResultDetailRepository;
	
	@Autowired
	private RequestStatusRepository requestStatusRepository;
	
	@Autowired
	private IRideColorManagementRepository iRideColorManagementRepository;

	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private SafeHerCommonValidator safeHerCommonValidator;
	
	@Autowired
	private PushIOS iosPush;
	
	@Autowired
	private PushAndriod andriodPush;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;


	@Override
	public void saveRideRequestResponse(SafeHerDecorator decorator)	throws GenericException {
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator.getDataBean();
		bean.setStatusFinal("1");
		String appUserPassengerId;
		if (bean.getList() != null && bean.getList().size() > 0) {
			for (RideRequestResponseBean requestResponseBean : bean.getList()) {
				appUserPassengerId = saveRideRequestAndResponse(requestResponseBean);
			}
			decorator.setResponseMessage("Request send to all nearest driver");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		} else {
			decorator.setResponseMessage("User don't exist");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}

	}

	@Override
	public void saveRideRequestResponse(RideRequestResponseEntity requestResponseEntity){
		Map<String,RideRequestResponseEntity> passengerNotificationsMap = 
				notificationRepository.findNotification(requestResponseEntity.getRequestNo());
		  Map<String,RideRequestResponseEntity> mapRedis= null;
		  if(passengerNotificationsMap!=null){
		   
		   if(passengerNotificationsMap.get(requestResponseEntity.getDriverId())!=null){
		    passengerNotificationsMap.remove(requestResponseEntity.getDriverId());
		    passengerNotificationsMap.put(requestResponseEntity.getDriverId(), requestResponseEntity);
		    notificationRepository.updateNotification(passengerNotificationsMap, requestResponseEntity.getRequestNo());
		   }else{
			   passengerNotificationsMap.put(requestResponseEntity.getDriverId(), requestResponseEntity);
			    notificationRepository.saveNotification(passengerNotificationsMap, requestResponseEntity.getRequestNo());
		   }
		   
		   
		  }else{
		   mapRedis = new HashMap<String,RideRequestResponseEntity>();
		   mapRedis.put(requestResponseEntity.getDriverId(),requestResponseEntity);
		   notificationRepository.saveNotification(mapRedis, requestResponseEntity.getRequestNo());
		   
		  }
	}
	
	@Override
	public void saveRideRequestResponseV2(SafeHerDecorator decorator)	throws GenericException {
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		List<DistanceAPIBean> activeDriversList = new ArrayList<DistanceAPIBean>();
		UserLoginEntity userLoginEntity = new UserLoginEntity();
//		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ 
//			rideCriteriaBean.getAppUserId() +" with source Latitude "+rideCriteriaBean.getSourceLat()
//			+" and Longitude "+rideCriteriaBean.getSourceLong());
		
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && 
				StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			userLoginEntity = rideDao.findByIdParam2(
					Integer.parseInt(rideCriteriaBean.getAppUserId()));
			if(userLoginEntity != null){
				/*activeDriversList = getActiveDriverList(rideCriteriaBean, userLoginEntity);*/
				activeDriversList = getActiveDriverListMongo(rideCriteriaBean, userLoginEntity);
				
				if(activeDriversList != null && activeDriversList.size() > 0){
					//saveRideSearchResult
					RideSearchResultEntity rideSearchRes = new RideSearchResultEntity();
					RideCriteriaEntity rideCriteriaEntity = new RideCriteriaEntity();
					rideCriteriaEntity.setRideCriteriaId(new Integer(rideCriteriaBean.getRideCriteriaId()));
					rideSearchRes.setTotalOption(activeDriversList.size());
					rideSearchRes.setResultTime(DateUtil.now());
					rideSearchRes.setRideCriteria(rideCriteriaBean.getRideCriteriaWrapper());
					rideSearchRes.setRideSearch(rideCriteriaBean.getRideSearchWrapper());
					if(rideDao.saveOrUpdate(rideSearchRes)){
						CharitiesEntity charitiesEntity = appUserDao.findById(
								CharitiesEntity.class, new Integer(rideCriteriaBean.getCharityId()));
						Notification notification = new Notification();
						System.out.println(activeDriversList.size()+" drivers found");
						for (DistanceAPIBean distanceBean : activeDriversList) {
							DistanceAPIBeanV2 distanceAPIBeanV2 = convertDistanceAPIBeantoV2(distanceBean);
							distanceAPIBeanV2.setRequestNo(rideCriteriaBean.getRequestNo());
							if(charitiesEntity != null){
								distanceAPIBeanV2.setCharityId(charitiesEntity.getCharitiesId()+"");
								distanceAPIBeanV2.setCharityName(charitiesEntity.getName());
							}
							notification.setDataBean(distanceAPIBeanV2);
							notification.setEntity(rideSearchRes);
							notification.setEntityAppUser(userLoginEntity);
							notification.setRequestNo(distanceAPIBeanV2.getRequestNo());
							notification.setPassengerId(userLoginEntity.getAppUser().getAppUserId()+"");
							producer.convertAndSendMessage(notification);
						}
						requestStatusRepository.saveRequestStatus(
								"0", rideCriteriaBean.getRequestNo());
						
						//saving rideTracking
						try {
							RideTrackingBean rideTrackingBean = new RideTrackingBean();
							rideTrackingBean.setRequestNo(rideCriteriaBean.getRequestNo());
							rideTrackingBean.setIsDriver("1");
							rideTrackingBean.setStatus(StatusEnum.Intial.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.SEARCH_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Search_Ride_By_Passenger.getValue()+"");
							rideTrackingBean.setPassengerId(userLoginEntity.getAppUser().getAppUserId()+"");
							rideTrackingBean.setIsComplete("0");
							asyncServiceImpl.saveRideTracking(rideTrackingBean);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
//					logger.info("Push Notification to Active Drivers End with drivers not found Exception ");
					throw new GenericRuntimeException("No Driver found");
				}
			}
		}else{
//			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
	}

	
	/*@Override
	public void saveRideRequestResponseV2Mongo(SafeHerDecorator decorator)	throws GenericException {
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		List<DistanceAPIBean> activeDriversList = new ArrayList<DistanceAPIBean>();
		UserLoginEntity userLoginEntity = new UserLoginEntity();
//		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ 
//			rideCriteriaBean.getAppUserId() +" with source Latitude "+rideCriteriaBean.getSourceLat()
//			+" and Longitude "+rideCriteriaBean.getSourceLong());
		
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && 
				StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			userLoginEntity = rideDao.findByIdParam2(
					Integer.parseInt(rideCriteriaBean.getAppUserId()));
			if(userLoginEntity != null){
				activeDriversList = getActiveDriverListMongo(rideCriteriaBean, userLoginEntity);
				if(activeDriversList != null && activeDriversList.size() > 0){
					//saveRideSearchResult
					RideSearchResultEntity rideSearchRes = new RideSearchResultEntity();
					RideCriteriaEntity rideCriteriaEntity = new RideCriteriaEntity();
					rideCriteriaEntity.setRideCriteriaId(new Integer(rideCriteriaBean.getRideCriteriaId()));
					rideSearchRes.setTotalOption(activeDriversList.size());
					rideSearchRes.setResultTime(DateUtil.now());
					rideSearchRes.setRideCriteria(rideCriteriaBean.getRideCriteriaWrapper());
					rideSearchRes.setRideSearch(rideCriteriaBean.getRideSearchWrapper());
					if(rideDao.saveOrUpdate(rideSearchRes)){
						CharitiesEntity charitiesEntity = appUserDao.findById(
								CharitiesEntity.class, new Integer(rideCriteriaBean.getCharityId()));
						Notification notification = new Notification();
						System.out.println(activeDriversList.size()+" drivers found");
						for (DistanceAPIBean distanceBean : activeDriversList) {
							DistanceAPIBeanV2 distanceAPIBeanV2 = convertDistanceAPIBeantoV2(distanceBean);
							distanceAPIBeanV2.setRequestNo(rideCriteriaBean.getRequestNo());
							if(charitiesEntity != null){
								distanceAPIBeanV2.setCharityId(charitiesEntity.getCharitiesId()+"");
								distanceAPIBeanV2.setCharityName(charitiesEntity.getName());
							}
							notification.setDataBean(distanceAPIBeanV2);
							notification.setEntity(rideSearchRes);
							notification.setEntityAppUser(userLoginEntity);
							notification.setRequestNo(distanceAPIBeanV2.getRequestNo());
							notification.setPassengerId(userLoginEntity.getAppUser().getAppUserId()+"");
							producer.convertAndSendMessage(notification);
						}
						requestStatusRepository.saveRequestStatus(
								"0", rideCriteriaBean.getRequestNo());
						
						//saving rideTracking
						try {
							RideTrackingBean rideTrackingBean = new RideTrackingBean();
							rideTrackingBean.setRequestNo(rideCriteriaBean.getRequestNo());
							rideTrackingBean.setStatus(StatusEnum.Intial.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.SEARCH_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Search_Ride_By_Passenger.getValue()+"");
							rideTrackingBean.setIsComplete("0");
							asyncServiceImpl.saveRideTracking(rideTrackingBean);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
//					logger.info("Push Notification to Active Drivers End with drivers not found Exception ");
					throw new GenericRuntimeException("No Driver found");
				}
			}
		}else{
//			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
	}
	*/
	@Override
	public void saveRideRequestResponseV2Mongo(SafeHerDecorator decorator)	throws GenericException {
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		List<DistanceAPIBean> activeDriversList = new ArrayList<DistanceAPIBean>();
		UserLoginEntity userLoginEntity = new UserLoginEntity();
//		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ 
//			rideCriteriaBean.getAppUserId() +" with source Latitude "+rideCriteriaBean.getSourceLat()
//			+" and Longitude "+rideCriteriaBean.getSourceLong());
		
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && 
				StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			userLoginEntity = rideDao.findByIdParam2(
					Integer.parseInt(rideCriteriaBean.getAppUserId()));
			if(userLoginEntity != null){
				activeDriversList = getActiveDriverListMongo(rideCriteriaBean, userLoginEntity);
				
				if(activeDriversList != null && activeDriversList.size() > 0){
					//saveRideSearchResult
					RideSearchResultEntity rideSearchRes = new RideSearchResultEntity();
					RideCriteriaEntity rideCriteriaEntity = new RideCriteriaEntity();
					rideCriteriaEntity.setRideCriteriaId(new Integer(rideCriteriaBean.getRideCriteriaId()));
					rideSearchRes.setTotalOption(activeDriversList.size());
					rideSearchRes.setResultTime(DateUtil.now());
					rideSearchRes.setRideCriteria(rideCriteriaBean.getRideCriteriaWrapper());
					rideSearchRes.setRideSearch(rideCriteriaBean.getRideSearchWrapper());
					if(rideDao.saveOrUpdate(rideSearchRes)){
						CharitiesEntity charitiesEntity = appUserDao.findById(
								CharitiesEntity.class, new Integer(rideCriteriaBean.getCharityId()));
						Notification notification = new Notification();
						System.out.println(activeDriversList.size()+" drivers found");
						for (DistanceAPIBean distanceBean : activeDriversList) {
							DistanceAPIBeanV2 distanceAPIBeanV2 = convertDistanceAPIBeantoV2(distanceBean);
							distanceAPIBeanV2.setRequestNo(rideCriteriaBean.getRequestNo());
							if(charitiesEntity != null){
								distanceAPIBeanV2.setCharityId(charitiesEntity.getCharitiesId()+"");
								distanceAPIBeanV2.setCharityName(charitiesEntity.getName());
							}
							notification.setDataBean(distanceAPIBeanV2);
							notification.setEntity(rideSearchRes);
							notification.setEntityAppUser(userLoginEntity);
							notification.setRequestNo(distanceAPIBeanV2.getRequestNo());
							notification.setPassengerId(userLoginEntity.getAppUser().getAppUserId()+"");
							producer.convertAndSendMessage(notification);
						}
						requestStatusRepository.saveRequestStatus(
								"0", rideCriteriaBean.getRequestNo());
						
						//saving rideTracking
						try {
							RideTrackingBean rideTrackingBean = new RideTrackingBean();
							rideTrackingBean.setRequestNo(rideCriteriaBean.getRequestNo());
							rideTrackingBean.setStatus(StatusEnum.Intial.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.SEARCH_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Search_Ride_By_Passenger.getValue()+"");
							rideTrackingBean.setPassengerId(userLoginEntity.getAppUser().getAppUserId()+"");
							rideTrackingBean.setIsComplete("0");
							asyncServiceImpl.saveRideTracking(rideTrackingBean);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
//					logger.info("Push Notification to Active Drivers End with drivers not found Exception ");
					throw new GenericRuntimeException("No Driver found");
				}
			}
		}else{
//			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
	}
	public DistanceAPIBeanV2 convertDistanceAPIBeantoV2(DistanceAPIBean distanceAPIBean){
		DistanceAPIBeanV2 distanceAPIBeanV2 = new DistanceAPIBeanV2();
		
		distanceAPIBeanV2.setLatDestinations(distanceAPIBean.getLatDestinations());
		distanceAPIBeanV2.setLngDestinations(distanceAPIBean.getLngDestinations());
		distanceAPIBeanV2.setAppUserId(distanceAPIBean.getAppUserId());
		distanceAPIBeanV2.setDriverStatus(distanceAPIBean.getDriverStatus());
		//TODO: check
		distanceAPIBeanV2.setLoc(distanceAPIBean.getLoc());
		return distanceAPIBeanV2;
	}

	public List<DistanceAPIBean> getActiveDriverList(RideCriteriaBean rideCriteriaBean,
			UserLoginEntity userLoginEntity) throws GenericException{

		List<DistanceAPIBean> activeDriversList = new ArrayList<DistanceAPIBean>();
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		activeDriversList = iGoogleMapsAPIService.getActiveDriverMinDistanceAPI(
				rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
		if(activeDriversList != null && activeDriversList.size() == 0){
			if (CollectionUtil.isEmpty(activeDriversList)
					&& activeDriversList.size() == 0 ) {
				if(userLoginEntity != null){
					if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
							&& userLoginEntity.getOsType().compareTo("0") != 0) {
						try {
//							logger.info("No Active Drivers, Looking Deeper Details");
							pushAndriod.pushAndriodPassengerNotification(userLoginEntity.getKeyToken(), 
									rideCriteriaBean.getAppUserId(), "", "", "",
									PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
//							logger.info("Active Drivers, Ended with Exception "+e.getMessage());
							throw new GenericRuntimeException(
									"Error in GCM Google APi");
						}
					} else {
						try {
//							logger.info("No Active Drivers, Looking Deeper Details");
							pushIOS.pushIOSPassenger(userLoginEntity.getKeyToken(),
									userLoginEntity.getIsDev(), rideCriteriaBean.getAppUserId(), "", "",
									"", PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait", userLoginEntity.getFcmToken());
						} catch (Exception e) {
//							logger.info("Active Drivers, Ended with Exception "+e.getMessage());
							throw new GenericRuntimeException("Error in APNS APi");
						}
					}
					try{
						activeDriversList = iGoogleMapsAPIService.getActiveDriverMaxDistanceAPI(
								rideCriteriaBean.getSourceLat(), rideCriteriaBean.getSourceLong());
					} catch (Exception ex) {
						throw new GenericException(
								"Sql query Error For getting Active Drivers");
					}
				}
			}
		}
		
		return activeDriversList;
	}
	
	
	public List<DistanceAPIBean> getActiveDriverListMongo(RideCriteriaBean rideCriteriaBean,
			UserLoginEntity userLoginEntity) throws GenericException{

		List<DistanceAPIBean> activeDriversList = new ArrayList<DistanceAPIBean>();
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		activeDriversList = iGoogleMapsAPIService.getActiveDriverMinDistanceAPIMongo(
				rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
		if(activeDriversList != null && activeDriversList.size() == 0){
			if (CollectionUtil.isEmpty(activeDriversList)
					&& activeDriversList.size() == 0 ) {
				if(userLoginEntity != null){
					if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
							&& userLoginEntity.getOsType().compareTo("0") != 0) {
						try {
//							logger.info("No Active Drivers, Looking Deeper Details");
							pushAndriod.pushAndriodPassengerNotification(userLoginEntity.getKeyToken(), 
									rideCriteriaBean.getAppUserId(), "", "", "",
									PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
//							logger.info("Active Drivers, Ended with Exception "+e.getMessage());
							throw new GenericRuntimeException(
									"Error in GCM Google APi");
						}
					} else {
						try {
//							logger.info("No Active Drivers, Looking Deeper Details");
							pushIOS.pushIOSPassenger(userLoginEntity.getKeyToken(),
									userLoginEntity.getIsDev(), rideCriteriaBean.getAppUserId(), "", "",
									"", PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait", userLoginEntity.getFcmToken());
						} catch (Exception e) {
//							logger.info("Active Drivers, Ended with Exception "+e.getMessage());
							throw new GenericRuntimeException("Error in APNS APi");
						}
					}
					try{
						activeDriversList = iGoogleMapsAPIService.getActiveDriverMaxDistanceAPIMongo(
								rideCriteriaBean.getSourceLat(), rideCriteriaBean.getSourceLong());
					} catch (Exception ex) {
						throw new GenericException(
								"Sql query Error For getting Active Drivers");
					}
				}
			}
		}
		
		return activeDriversList;
	}
	
	public String saveRideRequestAndResponse(RideRequestResponseBean bean) {
		//RideRequestResponseEntity entity = new RideRequestResponseEntity();
		// signUpConverter.convertBeanToRideReqResEntity(entity, bean);

		RideRequestResponseEntity rideRequestResponseEntity = new RideRequestResponseEntity();
		
		AppUserEntity appUserEntity = new AppUserEntity();
		RideSearchResultDetailEntity searchResultDetailEntity = new RideSearchResultDetailEntity();
		StatusEntity statusEntity = new StatusEntity();
		StatusEntity statusResponse = new StatusEntity();
		
		if (StringUtil.isEmpty(bean.getStatusRespons())) {
			bean.setStatusRespons("6");
		}
		//statusResponse.setStatusId(new Integer(bean.getStatusRespons()));
		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		statusEntity.setStatusId(new Integer(bean.getStatusRespons()));
		searchResultDetailEntity.setRideSearchResultDetailId(new Integer(bean.getRideSearchResultDetailId()));
		rideRequestResponseEntity.setAppUser(appUserEntity);
		rideRequestResponseEntity.setRideSearchResultDetail(searchResultDetailEntity);
		rideRequestResponseEntity.setStatusByStatusFinal(statusEntity);
		// rideRequestResponseEntity.setRequestTime(
		// DateUtil.parseTimestampFromFormats(bean.getRequestSent()));
		System.out.println("BeForeeeeeeeeeeeeeeeeeeeeeeeeeee      :"+rideRequestResponseEntity.getRideReqResId());
		appUserDao.save(rideRequestResponseEntity);
		System.out.println("Aftereeerrrrrrrrrrrrrrrrrrrrrrrrrrr     :"+rideRequestResponseEntity.getRideReqResId());
		// if (rideRequestResponseEntity.getAppUser() != null) {
		// return entity.getAppUser().getAppUserId() + "";
		return "";
	}

	@Override
	public void driverRequestToPassenger(SafeHerDecorator decorator)
			throws GenericException {
		PushNotificationsAndriod pushNotificationsAndriod=new PushNotificationsAndriod();
		PushNotificationsIOS pushNotificationsIOS=new PushNotificationsIOS();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		bean.setStatusFinal("2");
		bean.setStatusRespons("2");
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {

			// now we are getting rideRequestResponse from driverid and
			// requestNo so that,s why we change method calling
			// List<Object[]> object = appUserDao.findRideRequest(new
			// Integer(bean
			// .getAppUserId()));
			List<Object[]> object = appUserDao.findRideRequestResponse(
					new Integer(bean.getAppUserId()), bean.getRequestNo());
			if (object != null && object.size() > 0) {
				RideRequestResponseEntity requestResponseEntity = (RideRequestResponseEntity) object
						.get(0)[0];
				AppUserEntity driver = (AppUserEntity) object.get(0)[2];
				AppUserEntity userPassenger = requestResponseEntity
						.getAppUser();
				/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
				AppUserEntity entity = appUserDao
						.findDrivers(requestResponseEntity.getRideReqResId());
				if (entity != null) {
					/*activeDriverLocationEntity = appUserDao
							.findByOject(ActiveDriverLocationEntity.class,
									"appUser", entity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsRequested("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}*/
					
					activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
					if (activeDriverLocationMongoEntity != null) {
						activeDriverLocationMongoEntity.setIsRequested("1");
						activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
						/*appUserDao.saveOrUpdate(activeDriverLocationMongoEntity);*/
					}
				}
				String result = getKeyToken(userPassenger);
				if (result.equalsIgnoreCase("failed")) {
					throw new GenericException(
							"Ride Request failded due to some error1");
				} else {
					String IOSMode = getIOSMode(userPassenger);
					if (IOSMode.equalsIgnoreCase("null")) {
						throw new GenericException(
								"Ride Request failded due to some error2");
					} else {
						try {
							UserLoginEntity loginEntity = appUserDao
									.findByOject(UserLoginEntity.class,
											"appUser", userPassenger);
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushNotificationsAndriod
										.pushAndriodForPassengerRide(
												result,
												userPassenger.getAppUserId()
														+ "",
												driver.getAppUserId() + "",
												requestResponseEntity
														.getRideReqResId() + "",
												"Accept_Ride_From_Driver",
												bean.getRequestNo());
								decorator
										.setResponseMessage("Request has been sent");
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushNotificationsIOS.pushIOSForPassengerRide(
										result, IOSMode,
										userPassenger.getAppUserId() + "",
										driver.getAppUserId() + "",
										requestResponseEntity.getRideReqResId()
												+ "",
										"Accept_Ride_From_Driver",
										bean.getRequestNo(), loginEntity.getFcmToken());
								decorator
										.setResponseMessage("Request has been sent");
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
							}
						} catch (IOException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						// PushNotificationsIOS.pushIOS(result, "Passenger",
						// "prod");
					}
				}
			} else {
				throw new GenericException(
						"Ride Request failed due to some error3");
			}

			// if (StringUtil.isNotEmpty(appUserPassengerId)) {
			// // rideRequestResponseThread.start(decorator);
			// rideRequestResponseThread.getPassengerRequest().add(bean);
			// rideRequestResponseThread.getPassengerRequestMap().put(bean.getAppUserId(),
			// rideRequestResponseThread.getPassengerRequest());
			//
			// for(RideRequestResponseBean requestResponseBean :
			// rideRequestResponseThread.getPassengerRequest()){
			// if(rideRequestResponseThread.getPassengerRequest().size() == 1){
			// PushNotificationsIOS.pushIOS(
			// "ee73fc39622a16d3e1003fde7f55aac1e72734357384ac7f6f02bd42789512b4",
			// "Driver", "dev");
			// requestResponseBean.setRequestSent("1");
			// }else{
			// if(!requestResponseBean.getRequestSent().equals("1")){
			// new java.util.Timer().schedule(
			// new java.util.TimerTask() {
			// @Override
			// public void run() {
			// PushNotificationsIOS.pushIOS(
			// "ee73fc39622a16d3e1003fde7f55aac1e72734357384ac7f6f02bd42789512b4",
			// "Driver", "dev");
			// }
			// },
			// 5000
			// );
			// requestResponseBean.setRequestSent("1");
			// }
			// }
			// }
			//
			// decorator.setResponseMessage("Request sent to passenger");
			// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			//
			// } else {
			// throw new GenericException(
			// "Ride Request failded due to some error");
			// }

		} else {
			decorator.setResponseMessage("User don't exist");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}

	}

	@Override
	public void driverRequestToPassengerV2(SafeHerDecorator decorator)
			throws GenericException {
		
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
//		Map<Object, Object> list = rideRequestRepository
//				.findAllRideRequestResponseResultDetail();
//		System.out.println("sizeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+list.size());
//		if(list.size() > 0 || list.size() < 0){
//			throw new GenericException("abc");
//		}
		String[] requestNO = bean.getRequestNo().split("_");
		String requestStatus = "";
		if(requestNO != null && requestNO.length > 0){
			requestStatus = requestStatusRepository.
					findRequestStatus(requestNO[0]);
		}else{
			throw new GenericException("Please provide ride requestNo");
		}
//		requestStatusRepository.updateRequestStatus("0", bean.getRequestNo());
		//RE1470991727655624
		bean.setStatusFinal(StatusEnum.Accepted.getValue()+"");
		bean.setStatusRespons(StatusEnum.Accepted.getValue()+"");
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
//			requestStatus = requestStatusRepository.
//					findRequestStatus(bean.getRequestNo());
			if(requestStatus != null && requestStatus.equals("1")){
				try {
//					Thread.sleep(5000);
					Thread.sleep(3000);
					requestStatus = requestStatusRepository.
							findRequestStatus(requestNO[0]);
					if(requestStatus != null && requestStatus.equals("2")){
						throw new GenericException("This request has been accepted by another driver");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new GenericException("Ride request failed due to some error");
				}
			}else if(requestStatus != null && requestStatus.equals("2")){
				throw new GenericException("This request has been accepted by another driver");
			}
			
			requestStatusRepository.updateRequestStatus("1", requestNO[0]);
			
//			RideRequestResponseEntity requestResponseEntity = null;
//			RideRequestResponseEntity requestResponseEntityV2 = null;
//			Map<String,RideRequestResponseEntity> passengerNotificationsMap = 
//					notificationRepository.findNotification(bean.getRequestNo());
//			if(passengerNotificationsMap != null 
//					&& passengerNotificationsMap.size() > 0){
//				System.out.println(passengerNotificationsMap.size()+"this is redis map size");
//				requestResponseEntity = passengerNotificationsMap.
//						get(bean.getAppUserId());
////				requestResponseEntityV2 = appUserDao.findRideRequestResponseV2(
////						new Integer(bean.getAppUserId()), bean.getRequestNo());
//			}else{
//				throw new GenericException("This request has been accepted by another driver");
//			}
			
			RideRequestResponseEntity requestResponseEntity = rideRequestRepository.
					findNotification(bean.getRequestNo());
			if(requestResponseEntity == null){
				try {
//					Thread.sleep(3000);
					Thread.sleep(2000);
					requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getAppUserId()), bean.getRequestNo());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//RE1470986576993594
			if (requestResponseEntity != null) {
				AppUserEntity driver = (AppUserEntity) requestResponseEntity.
						getRideSearchResultDetail().getAppUser();
				AppUserEntity userPassenger = requestResponseEntity
						.getAppUser();
				
				//TODO:Check
				/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
				if (driver != null) {
					/*activeDriverLocationEntity = appUserDao
							.findByOject(ActiveDriverLocationEntity.class,
									"appUser", driver);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsRequested("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}*/
					
					activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
					if (activeDriverLocationMongoEntity != null) {
						activeDriverLocationMongoEntity.setIsRequested("1");
						activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
						/*appUserDao.saveOrUpdate(activeDriverLocationMongoEntity);*/
					}
				}
//				UserLoginEntity passengerLogInEntity = appUserDao.findByOject(
//						UserLoginEntity.class, "appUser", userPassenger);
				UserLoginEntity loginEntity = appUserDao.findByIdParam2(
						userPassenger.getAppUserId());
				if(loginEntity != null){
					if (StringUtil.isEmpty(loginEntity.getKeyToken())) {
						throw new GenericException(
								"Ride Request failded due to some error");
					} else {
						try {
//							UserLoginEntity loginEntity = appUserDao
//									.findByOject(UserLoginEntity.class,
//											"appUser", userPassenger);
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod
										.pushAndriodPassengerNotification(
												loginEntity.getKeyToken(),
												userPassenger.getAppUserId() + "",
												driver.getAppUserId() + "",
												requestResponseEntity.getRideReqResId() +"" ,bean.getRequestNo(),
												PushNotificationStatus.AcceptRideFromDriver.toString(), 
												"Driver has accepted your request");

								decorator
										.setResponseMessage("Request has been sent");
								requestStatusRepository.updateRequestStatus("2", requestNO[0]);
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								if (loginEntity.getIsDev() == null) {
									throw new GenericException(
											"Ride Request failded due to some error");
								}else{
									pushIOS.pushIOSPassenger(
											loginEntity.getKeyToken(), loginEntity.getIsDev()+"",
											userPassenger.getAppUserId() + "",
											driver.getAppUserId() + "",
											requestResponseEntity.getRideReqResId()+ "", bean.getRequestNo(),//check null
											PushNotificationStatus.AcceptRideFromDriver.toString(), 
											"Driver has accepted your request", loginEntity.getFcmToken());
									decorator
											.setResponseMessage("Request has been sent");
									decorator
											.setReturnCode(ReturnStatusEnum.SUCCESFUL
													.toString());
									requestStatusRepository.updateRequestStatus("2", requestNO[0]);
								}
							}

							//saving rideTracking
							try {		
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setIsDriver("2");
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setStatus(StatusEnum.Accepted.getValue()+"");
									if(driver != null){
										rideTrackingBean.setDriverId(driver.getAppUserId()+"");
									}
									rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.Accept_And_Navigate_By_Driver.getValue()+"");
									rideTrackingBean.setIsComplete("0");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							//async saving driverDrivingDetailIntoMongo
							DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
							detailBean.setAppUserId(driver.getAppUserId()+"");
							detailBean.setTotalAcceptedRequest(1.0);
							asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
							
							//async saving driverTrackIntoDrivingDetailIntoMongo
							asyncServiceImpl.saveLocationTrackIntoDrivingDetail(driver.getAppUserId(), "1");
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}else{
					throw new GenericException(
							"User not found");
				}
			} else {
				throw new GenericException(
						"Ride Request failed due to some error1");
			}

		} else {
			decorator.setResponseMessage("User don't exist");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}

	}

	public String saveRideFinalizeAndPreRide(SafeHerDecorator decorator,
			RideCriteriaEntity criteriaEntity) {
		
		RideFinalizeEntity finalizeEntity = new RideFinalizeEntity();
		PreRideEntity preRideEntity = new PreRideEntity();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		StatusEntity statusEntity = new StatusEntity();
		statusEntity.setStatusId(StatusEnum.MoveToPickUp.getValue());
		finalizeEntity.setRideCriteria(criteriaEntity);
		signUpConverter.convertBeanToFinalizeEntity(finalizeEntity, bean);
//		RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
//				new Integer(bean.getAppUserId()), bean.getRequestNo());
//		if(requestResponseEntity != null){
//			finalizeEntity.setRideRequestResponse(requestResponseEntity);
//		}else{
//		}
		AppUserEntity driver = new AppUserEntity();
		driver.setAppUserId(new Integer(bean.getAppUserId()));
		finalizeEntity.setRequestNo(bean.getRequestNo());
		finalizeEntity.setAppUser(driver);
		System.out.println(finalizeEntity.getRideFinalizeId());
		appUserDao.saveOrUpdate(finalizeEntity);
		System.out.println(finalizeEntity.getRideFinalizeId());

		preRideEntity.setRideFinalize(finalizeEntity);
		preRideEntity.setRequestNo(bean.getRequestNo());
		
		preRideEntity.setStatus(statusEntity);
		preRideEntity.setDriverStartTime(DateUtil.now());
		preRideEntity.setEstimatedTime(bean.getEstimatedTime());
		
		appUserDao.saveOrUpdate(preRideEntity);
		return preRideEntity.getPreRideId() + "";
	}

	public String getKeyToken(AppUserEntity appUserEntity) {

		UserLoginEntity entity = appUserDao.findByOject(UserLoginEntity.class,
				"appUser", appUserEntity);
		if (entity != null) {
			return entity.getKeyToken() + "";
		}
		return "failed";
	}

	public String getIOSMode(AppUserEntity appUserEntity) {

		UserLoginEntity entity = appUserDao.findByOject(UserLoginEntity.class,
				"appUser", appUserEntity);
		if (entity != null) {
			return entity.getIsDev() + "";
		}
		return "null";
	}

	@Override
	public void cancelRequest(SafeHerDecorator decorator)
			throws GenericException {
		PushNotificationsAndriod pushNotificationsAndriod=new PushNotificationsAndriod();
		PushNotificationsIOS pushNotificationsIOS=new PushNotificationsIOS();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			// RideRequestResponseEntity requestResponseEntity = appUserDao
			// .findById(RideRequestResponseEntity.class,
			// new Integer(bean.getRideReqResId()));

			// now we are getting rideRequestResponse from driverid and
			// requestNo so that,s why we change method calling
			// List<Object[]> object = appUserDao.findRideRequest(new
			// Integer(bean
			// .getAppUserId()));
			List<Object[]> object = appUserDao.findRideRequestResponse(
					new Integer(bean.getAppUserId()), bean.getRequestNo());
			if (object != null && object.size() > 0) {
				RideRequestResponseEntity requestResponseEntity = (RideRequestResponseEntity) object
						.get(0)[0];
				if (requestResponseEntity != null) {
					ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
					StatusEntity statusResponse = new StatusEntity();
					statusResponse.setStatusId(4);
					requestResponseEntity
							.setStatusByStatusResponse(statusResponse);
					appUserDao.saveOrUpdate(requestResponseEntity);
					if (StringUtil.isNotEmpty(bean.getIsDriver())
							&& bean.getIsDriver().equals("0")) {
						AppUserEntity userEntity = appUserDao.findById(
								AppUserEntity.class,
								new Integer(bean.getAppUserId()));
						if (userEntity != null) {
							if (userEntity != null
									&& userEntity.getIsDriver()
											.equalsIgnoreCase("1")) {
								activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", userEntity);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity
											.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);
								}
							} else {
								// AppUserEntity entity =
								// appUserDao.findDrivers(requestResponseEntity.getRideReqResId());
								// if(entity != null){
								// activeDriverLocationEntity =
								// appUserDao.findByOject(
								// ActiveDriverLocationEntity.class, "appUser",
								// entity);
								// if(activeDriverLocationEntity != null){
								// activeDriverLocationEntity.setIsBooked("0");
								// activeDriverLocationEntity.setIsRequested("0");
								// appUserDao.saveOrUpdate(activeDriverLocationEntity);
								// }
								// }
							}
							String result = getKeyToken(userEntity);
							if (result.equalsIgnoreCase("failed")) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								String IOSMode = getIOSMode(userEntity);
								if (IOSMode.equalsIgnoreCase("null")) {
									throw new GenericException(
											"Ride Request failded due to some error");
								} else {
									try {
										UserLoginEntity loginEntity = appUserDao
												.findByOject(
														UserLoginEntity.class,
														"appUser", userEntity);
										if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("1")) {
											pushNotificationsAndriod
													.pushAndriodForPassengerRide(
															result,
															"",
															"",
															"",
															"Cancel_Ride_From_Passenger",
															bean.getRequestNo());
											decorator
													.setResponseMessage("Request has been sent");
										} else if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("0")) {
											pushNotificationsIOS
													.pushIOSForRide(result,
															IOSMode, "", "",
															"Cancel_Ride_From_Passenger", loginEntity.getFcmToken());
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										}
									} catch (IOException e) {
										// TODO: handle exception
										e.printStackTrace();
									}
									// PushNotificationsIOS.pushIOS(result,
									// "Passenger", "prod");
								}
								//delete from radis
								deleteRideFromRedis(bean.getRequestNo());
								
								//saving rideTracking
								try {		
									String[] requestNO = bean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										RideTrackingBean rideTrackingBean = new RideTrackingBean();
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Request_By_Passenger.getValue()+"");
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
					// decorator.setResponseMessage("Request has been cancelled");
					// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

					// String result =
					// getKeyToken(requestResponseEntity.getAppUser());
					// if (result.equalsIgnoreCase("failed")) {
					// throw new GenericException(
					// "Ride Request failded due to some error");
					// } else {
					// PushNotificationsIOS.pushIOS(result, "Driver", "dev");
					// }
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}
	}

	@Override
	public void cancelRequestV2(SafeHerDecorator decorator)
			throws GenericException {
		PushNotificationsAndriod pushNotificationsAndriod=new PushNotificationsAndriod();
		PushNotificationsIOS pushNotificationsIOS=new PushNotificationsIOS();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {

			//passenger has to send requestNo and driverId as sending in cancelRequest and 
			//now we will fetch record using requestNo and driverId from redis
//			RideRequestResponseEntity requestResponseEntity = null;
//			Map<String,RideRequestResponseEntity> passengerNotificationsMap = 
//					notificationRepository.findNotification(bean.getRequestNo());
//			if(passengerNotificationsMap != null 
//					&& passengerNotificationsMap.size() > 0){
//				requestResponseEntity = passengerNotificationsMap.
//						get(bean.getAppUserId());
//			}
//			if(requestResponseEntity == null){
//				requestResponseEntity = appUserDao.findRideRequestResponseV2(
//						new Integer(bean.getAppUserId()), bean.getRequestNo());
//			}
			RideRequestResponseEntity requestResponseEntity = rideRequestRepository.
					findNotification(bean.getRequestNo());
			if(requestResponseEntity == null){
				try {
					Thread.sleep(3000);
					requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getAppUserId()), bean.getRequestNo());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*if (requestResponseEntity != null) {
				ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(StatusEnum.Rejected.getValue());
				requestResponseEntity
						.setStatusByStatusResponse(statusResponse);
				appUserDao.updateRideReqResStatusUsingRequestNoAndDriverId(
						bean.getRequestNo(), bean.getAppUserId(), statusResponse);
				if (StringUtil.isNotEmpty(bean.getIsDriver())
						&& bean.getIsDriver().equals("0")) {
					AppUserEntity userEntity = appUserDao.findById(
							AppUserEntity.class,
							new Integer(bean.getAppUserId()));
					if (userEntity != null) {
						if (userEntity != null
								&& userEntity.getIsDriver()
										.equalsIgnoreCase("1")) {
							activeDriverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", userEntity);
							if (activeDriverLocationEntity != null) {
								activeDriverLocationEntity
										.setIsRequested("0");
								activeDriverLocationEntity.setIsBooked("0");
								appUserDao
										.saveOrUpdate(activeDriverLocationEntity);
							}
						} else {
							// AppUserEntity entity =
							// appUserDao.findDrivers(requestResponseEntity.getRideReqResId());
							// if(entity != null){
							// activeDriverLocationEntity =
							// appUserDao.findByOject(
							// ActiveDriverLocationEntity.class, "appUser",
							// entity);
							// if(activeDriverLocationEntity != null){
							// activeDriverLocationEntity.setIsBooked("0");
							// activeDriverLocationEntity.setIsRequested("0");
							// appUserDao.saveOrUpdate(activeDriverLocationEntity);
							// }
							// }
						}
						String result = getKeyToken(userEntity);
						if (result.equalsIgnoreCase("failed")) {
							throw new GenericException(
									"Ride Request failded due to some error");
						} else {
							String IOSMode = getIOSMode(userEntity);
							if (IOSMode.equalsIgnoreCase("null")) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								try {
									UserLoginEntity loginEntity = appUserDao
											.findByOject(
													UserLoginEntity.class,
													"appUser", userEntity);
									if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("1")) {
										pushNotificationsAndriod
												.pushAndriodForPassengerRide(
														result,
														"",
														"",
														"",
														"Cancel_Ride_From_Passenger",
														bean.getRequestNo());
										decorator
												.setResponseMessage("Request has been sent");
									} else if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("0")) {
										pushNotificationsIOS
												.pushIOSForRide(result,
														IOSMode, "", "",
														"Cancel_Ride_From_Passenger", loginEntity.getFcmToken());
										decorator
												.setResponseMessage("Request has been sent");
										decorator
												.setReturnCode(ReturnStatusEnum.SUCCESFUL
														.toString());
									}
								} catch (IOException e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								// PushNotificationsIOS.pushIOS(result,
								// "Passenger", "prod");
							}
							//delete from radis
//							deleteRideFromRedis(bean.getRequestNo());
							try {
								asyncServiceImpl.deletRideRequestResponseFromRedis(
										bean.getRequestNo(), requestResponseEntity.getAppUser().getAppUserId()+"");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//saving rideTracking
							try {		
								String[] requestNO = bean.getRequestNo().split("_");
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setIsDriver("1");
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
									rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Request_By_Passenger.getValue()+"");
									rideTrackingBean.setIsComplete("0");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							//saveRideStatusCancelForReleaseDriverFree
							activeDriverStatusRepository.saveRequestStatus(
									"C", bean.getRequestNo());
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}
	}*/
			if (requestResponseEntity != null) {
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(StatusEnum.Rejected.getValue());
				requestResponseEntity
						.setStatusByStatusResponse(statusResponse);
				appUserDao.updateRideReqResStatusUsingRequestNoAndDriverId(
						bean.getRequestNo(), bean.getAppUserId(), statusResponse);
				if (StringUtil.isNotEmpty(bean.getIsDriver())
						&& bean.getIsDriver().equals("0")) {
					AppUserEntity userEntity = appUserDao.findById(
							AppUserEntity.class,
							new Integer(bean.getAppUserId()));
					if (userEntity != null) {
						if (userEntity != null
								&& userEntity.getIsDriver()
										.equalsIgnoreCase("1")) {
							/*activeDriverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", userEntity);*/
							activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(userEntity.getAppUserId());
							/*if (activeDriverLocationEntity != null) {
								activeDriverLocationEntity
										.setIsRequested("0");
								activeDriverLocationEntity.setIsBooked("0");
								appUserDao
										.saveOrUpdate(activeDriverLocationEntity);
							}*/
							if (activeDriverLocationMongoEntity != null) {
								activeDriverLocationMongoEntity
										.setIsRequested("0");
								activeDriverLocationMongoEntity.setIsBooked("0");
							/*	appUserDao
										.saveOrUpdate(activeDriverLocationEntity);*/
								activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
							}
						} else {
							// AppUserEntity entity =
							// appUserDao.findDrivers(requestResponseEntity.getRideReqResId());
							// if(entity != null){
							// activeDriverLocationEntity =
							// appUserDao.findByOject(
							// ActiveDriverLocationEntity.class, "appUser",
							// entity);
							// if(activeDriverLocationEntity != null){
							// activeDriverLocationEntity.setIsBooked("0");
							// activeDriverLocationEntity.setIsRequested("0");
							// appUserDao.saveOrUpdate(activeDriverLocationEntity);
							// }
							// }
						}
						String result = getKeyToken(userEntity);
						if (result.equalsIgnoreCase("failed")) {
							throw new GenericException(
									"Ride Request failded due to some error");
						} else {
							String IOSMode = getIOSMode(userEntity);
							if (IOSMode.equalsIgnoreCase("null")) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								try {
									UserLoginEntity loginEntity = appUserDao
											.findByOject(
													UserLoginEntity.class,
													"appUser", userEntity);
									if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("1")) {
										pushNotificationsAndriod
												.pushAndriodForPassengerRide(
														result,
														"",
														"",
														"",
														"Cancel_Ride_From_Passenger",
														bean.getRequestNo());
										decorator
												.setResponseMessage("Request has been sent");
									} else if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("0")) {
										pushNotificationsIOS
												.pushIOSForRide(result,
														IOSMode, "", "",
														"Cancel_Ride_From_Passenger", loginEntity.getFcmToken());
										decorator
												.setResponseMessage("Request has been sent");
										decorator
												.setReturnCode(ReturnStatusEnum.SUCCESFUL
														.toString());
									}
								} catch (IOException e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								// PushNotificationsIOS.pushIOS(result,
								// "Passenger", "prod");
							}
							//delete from radis
//							deleteRideFromRedis(bean.getRequestNo());
							try {
								asyncServiceImpl.deletRideRequestResponseFromRedis(
										bean.getRequestNo(), requestResponseEntity.getAppUser().getAppUserId()+"");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							//saving rideTracking
							try {		
								String[] requestNO = bean.getRequestNo().split("_");
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
									rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Request_By_Passenger.getValue()+"");
									rideTrackingBean.setIsComplete("0");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							//async saving driverDrivingDetailIntoMongo
							DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
							detailBean.setAppUserId(userEntity.getAppUserId()+"");
							detailBean.setTotalCancelPreRides(1.0);
							asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}
	}
	
	public void deleteRideFromRedis(String requestNo){
		notificationRepository.deleteNotification(requestNo);
		requestStatusRepository.deleteRequestStatus(requestNo);
		String requestStatus1 = requestStatusRepository.
				findRequestStatus(requestNo);
		Map<String,RideRequestResponseEntity> map = 
				notificationRepository.findNotification(requestNo);
		System.out.println(map);
		System.out.println(requestStatus1);
	}

	@Override
	public void acceptRequestV2(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			
			//passenger has to send requestNo and driverId as sending in cancelRequest and 
			//now we will fetch record using requestNo and driverId from redis
//			RideRequestResponseEntity requestResponseEntity = null;
//			Map<String,RideRequestResponseEntity> passengerNotificationsMap = 
//					notificationRepository.findNotification(bean.getRequestNo());
//			if(passengerNotificationsMap != null 
//					&& passengerNotificationsMap.size() > 0){
//				requestResponseEntity = passengerNotificationsMap.
//						get(bean.getAppUserId());
//			}
//			if(requestResponseEntity == null){
//				requestResponseEntity = appUserDao.findRideRequestResponseV2(
//						new Integer(bean.getAppUserId()), bean.getRequestNo());
//			}
			RideRequestResponseEntity requestResponseEntity = rideRequestRepository.
					findNotification(bean.getRequestNo());
			if(requestResponseEntity == null){
				try {
					Thread.sleep(3000);
					requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getAppUserId()), bean.getRequestNo());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (requestResponseEntity != null) {
				ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(StatusEnum.Ready.getValue());
				requestResponseEntity
						.setStatusByStatusResponse(statusResponse);
				appUserDao.updateRideReqResStatusUsingRequestNoAndDriverId(
						bean.getRequestNo(), bean.getAppUserId(), statusResponse);
				RideCriteriaEntity criteriaEntity = requestResponseEntity
						.getRideSearchResultDetail().getRideSearchResult()
						.getRideCriteria();
				String preRideId = saveRideFinalizeAndPreRide(decorator,
						criteriaEntity);
				bean.setPreRideId(preRideId);
				AppUserEntity appUserEntity = appUserDao.findById(
						AppUserEntity.class, new Integer(bean.getAppUserId()));
				if (appUserEntity != null
						&& appUserEntity.getIsDriver().equalsIgnoreCase("1")) {
					/*activeDriverLocationEntity = appUserDao.findByOject(
							ActiveDriverLocationEntity.class, "appUser",
							appUserEntity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsBooked("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}*/
				} else {
					AppUserEntity entity = appUserDao
							.findDrivers(requestResponseEntity
									.getRideReqResId());
					if (entity != null) {
						activeDriverLocationEntity = appUserDao.findByOject(
								ActiveDriverLocationEntity.class, "appUser",
								entity);
						if (activeDriverLocationEntity != null) {
							activeDriverLocationEntity.setIsRequested("1");
							appUserDao.saveOrUpdate(activeDriverLocationEntity);
						}
					}
				}
				String result = getKeyToken(appUserEntity);
				if (result.equalsIgnoreCase("failed")) {
					throw new GenericException(
							"Ride Request failded due to some error");
				} else {
					String IOSMode = getIOSMode(appUserEntity);
					if (IOSMode.equalsIgnoreCase("null")) {
						throw new GenericException(
								"Ride Request failded due to some error");
					} else {
						try {
							UserLoginEntity loginEntity = appUserDao
									.findByOject(UserLoginEntity.class,
											"appUser", appUserEntity);
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod
										.pushAndriodDriverNotification(
												result,
												appUserEntity.getAppUserId()
														+ "",
												requestResponseEntity
														.getAppUser()
														.getAppUserId()
														+ "",
												preRideId,
												requestResponseEntity
														.getRideReqResId() + "",
												PushNotificationStatus.AcceptRideFromPassenger
														.toString(),
												"Passenger Has Accept the Ride Request");
								// decorator.setResponseMessage("Request has been sent");
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSDriver(
										result,
										IOSMode,
										appUserEntity.getAppUserId() + "",
										requestResponseEntity.getAppUser()
												.getAppUserId() + "",
										preRideId,
										requestResponseEntity.getRideReqResId()
												+ "",
										PushNotificationStatus.AcceptRideFromPassenger
												.toString(),
										"Passenger Has Accept the Ride Request", loginEntity.getFcmToken());
								// decorator.setResponseMessage("Request has been sent");
								// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
							}
							//this is for socket message to receiver start
							bean.setPassengerId(appUserEntity.getAppUserId()+"");
							bean.setDriverId(requestResponseEntity.getAppUser()
												.getAppUserId() + "");
							bean.setPreRideId(preRideId);
							bean.setRideReqResId(requestResponseEntity.getRideReqResId()+"");
							bean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
									.getValue());
							bean.setNotificationMessage("Passenger Has Accept the Ride Request");
							decorator.setDataBean(bean);
							//this is for socket message to receiver end
							
							ActiveDriverLocationEntity driverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", appUserEntity);
							if (driverLocationEntity != null) {

								bean.setLng(driverLocationEntity.getLongValue());
								bean.setLat(driverLocationEntity.getLatValue());
								getColorFunctionality(bean);
								decorator
										.setResponseMessage("Request has been sent Please Fetch Color Information");
								decorator.getResponseMap().put("data", bean);
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
							}
						} catch (IOException e) {
							decorator
									.setResponseMessage("Request has been failed");
							decorator.setReturnCode(ReturnStatusEnum.FAILURE
									.toString());
							// TODO: handle exception
							e.printStackTrace();
						}

						//delete from radis
//						deleteRideFromRedis(bean.getRequestNo());
						try {
							asyncServiceImpl.deletRideRequestResponseFromRedis(
									bean.getRequestNo(), requestResponseEntity.getAppUser().getAppUserId()+"");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//saving rideTracking
						try {		
							String[] requestNO = bean.getRequestNo().split("_");
							if(requestNO != null && requestNO.length > 0){
								RideTrackingBean rideTrackingBean = new RideTrackingBean();
								rideTrackingBean.setIsDriver("1");
								rideTrackingBean.setRequestNo(requestNO[0]);
								rideTrackingBean.setStatus(StatusEnum.Accepted.getValue()+"");
								rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
								rideTrackingBean.setAction(ProcessStateAndActionEnum.Accept_Request_By_Passenger.getValue()+"");
								rideTrackingBean.setIsComplete("0");
								asyncServiceImpl.saveRideTracking(rideTrackingBean);	
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}

	}
	/*@Override
	public void acceptRequestV3(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			
			//passenger has to send requestNo and driverId as sending in cancelRequest and 
			//now we will fetch record using requestNo and driverId from redis
//			RideRequestResponseEntity requestResponseEntity = null;
//			Map<String,RideRequestResponseEntity> passengerNotificationsMap = 
//					notificationRepository.findNotification(bean.getRequestNo());
//			if(passengerNotificationsMap != null 
//					&& passengerNotificationsMap.size() > 0){
//				requestResponseEntity = passengerNotificationsMap.
//						get(bean.getAppUserId());
//			}
//			if(requestResponseEntity == null){
//				requestResponseEntity = appUserDao.findRideRequestResponseV2(
//						new Integer(bean.getAppUserId()), bean.getRequestNo());
//			}
			RideRequestResponseEntity requestResponseEntity = rideRequestRepository.
					findNotification(bean.getRequestNo());
			if(requestResponseEntity == null){
				try {
					Thread.sleep(3000);
					requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getAppUserId()), bean.getRequestNo());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (requestResponseEntity != null) {
				//ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(StatusEnum.Ready.getValue());
				requestResponseEntity
						.setStatusByStatusResponse(statusResponse);
				appUserDao.updateRideReqResStatusUsingRequestNoAndDriverId(
						bean.getRequestNo(), bean.getAppUserId(), statusResponse);
				RideCriteriaEntity criteriaEntity = requestResponseEntity
						.getRideSearchResultDetail().getRideSearchResult()
						.getRideCriteria();
				String preRideId = saveRideFinalizeAndPreRide(decorator,
						criteriaEntity);
				bean.setPreRideId(preRideId);
				AppUserEntity appUserEntity = appUserDao.findById(
						AppUserEntity.class, new Integer(bean.getAppUserId()));
				if (appUserEntity != null
						&& appUserEntity.getIsDriver().equalsIgnoreCase("1")) {
					activeDriverLocationEntity = appUserDao.findByOject(
							ActiveDriverLocationEntity.class, "appUser",
							appUserEntity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsBooked("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}
					activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(appUserEntity.getAppUserId());
					if (activeDriverLocationMongoEntity != null) {
						activeDriverLocationMongoEntity.setIsBooked("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
						activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
						
					}
				} else {
					AppUserEntity entity = appUserDao
							.findDrivers(requestResponseEntity
									.getRideReqResId());
					if (entity != null) {
						activeDriverLocationEntity = appUserDao.findByOject(
								ActiveDriverLocationEntity.class, "appUser",
								entity);
						if (activeDriverLocationEntity != null) {
							activeDriverLocationEntity.setIsRequested("1");
							appUserDao.saveOrUpdate(activeDriverLocationEntity);
						}
						
						activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(entity.getAppUserId());
						if(activeDriverLocationMongoEntity !=null){
							activeDriverLocationMongoEntity.setIsRequested("1");
						}
					}
				}
				String result = getKeyToken(appUserEntity);
				if (result.equalsIgnoreCase("failed")) {
					throw new GenericException(
							"Ride Request failded due to some error");
				} else {
					String IOSMode = getIOSMode(appUserEntity);
					if (IOSMode.equalsIgnoreCase("null")) {
						throw new GenericException(
								"Ride Request failded due to some error");
					} else {
						try {
							UserLoginEntity loginEntity = appUserDao
									.findByOject(UserLoginEntity.class,
											"appUser", appUserEntity);
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod
										.pushAndriodDriverNotification(
												result,
												appUserEntity.getAppUserId()
														+ "",
												requestResponseEntity
														.getAppUser()
														.getAppUserId()
														+ "",
												preRideId,
												requestResponseEntity
														.getRideReqResId() + "",
												PushNotificationStatus.AcceptRideFromPassenger
														.toString(),
												"Passenger Has Accept the Ride Request");
								// decorator.setResponseMessage("Request has been sent");
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSDriver(
										result,
										IOSMode,
										appUserEntity.getAppUserId() + "",
										requestResponseEntity.getAppUser()
												.getAppUserId() + "",
										preRideId,
										requestResponseEntity.getRideReqResId()
												+ "",
										PushNotificationStatus.AcceptRideFromPassenger
												.toString(),
										"Passenger Has Accept the Ride Request", loginEntity.getFcmToken());
								// decorator.setResponseMessage("Request has been sent");
								// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
							}
							//this is for socket message to receiver start
							bean.setPassengerId(appUserEntity.getAppUserId()+"");
							bean.setDriverId(requestResponseEntity.getAppUser()
												.getAppUserId() + "");
							bean.setPreRideId(preRideId);
							bean.setRideReqResId(requestResponseEntity.getRideReqResId()+"");
							bean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
									.getValue());
							bean.setNotificationMessage("Passenger Has Accept the Ride Request");
							decorator.setDataBean(bean);
							//this is for socket message to receiver end
							
							ActiveDriverLocationEntity driverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", appUserEntity);
							ActiveDriverLocationEntity driverLocationEntity = appUserDao
							.findByOject(
									ActiveDriverLocationEntity.class,
									"appUser", appUserEntity);
					ActiveDriverLocationMongoEntity driverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(appUserEntity.getAppUserId());
					
							if (driverLocationMongoEntity != null) {

								bean.setLng(driverLocationMongoEntity.getLongValue());
								bean.setLat(driverLocationMongoEntity.getLatValue());
								getColorFunctionality(bean);
								decorator
										.setResponseMessage("Request has been sent Please Fetch Color Information");
								decorator.getResponseMap().put("data", bean);
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
							}
						} catch (IOException e) {
							decorator
									.setResponseMessage("Request has been failed");
							decorator.setReturnCode(ReturnStatusEnum.FAILURE
									.toString());
							// TODO: handle exception
							e.printStackTrace();
						}

						//delete from radis
//						deleteRideFromRedis(bean.getRequestNo());
						try {
							asyncServiceImpl.deletRideRequestResponseFromRedis(
									bean.getRequestNo(), requestResponseEntity.getAppUser().getAppUserId()+"");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//saving rideTracking
						try {		
							String[] requestNO = bean.getRequestNo().split("_");
							if(requestNO != null && requestNO.length > 0){
								RideTrackingBean rideTrackingBean = new RideTrackingBean();
								rideTrackingBean.setIsDriver("1");
								rideTrackingBean.setRequestNo(requestNO[0]);
								rideTrackingBean.setStatus(StatusEnum.Accepted.getValue()+"");
								rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
								rideTrackingBean.setAction(ProcessStateAndActionEnum.Accept_Request_By_Passenger.getValue()+"");
								rideTrackingBean.setIsComplete("0");
								asyncServiceImpl.saveRideTracking(rideTrackingBean);	
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}

	}*/

	
	@Override
	public void acceptRequestV3(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			
			RideRequestResponseEntity requestResponseEntity = rideRequestRepository.
					findNotification(bean.getRequestNo());
			
			if(requestResponseEntity.getStatusByStatusResponse()!=null && requestResponseEntity.getStatusByStatusResponse().getStatusId()==4){
				throw new GenericException("Ride Already Cancel");
			}
			if(requestResponseEntity == null){
				try {
//					Thread.sleep(3000);
					Thread.sleep(2000);
					requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getAppUserId()), bean.getRequestNo());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (requestResponseEntity != null) {
				/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(StatusEnum.Ready.getValue());
				requestResponseEntity
						.setStatusByStatusResponse(statusResponse);
				appUserDao.updateRideReqResStatusUsingRequestNoAndDriverId(
						bean.getRequestNo(), bean.getAppUserId(), statusResponse);
				RideCriteriaEntity criteriaEntity = requestResponseEntity
						.getRideSearchResultDetail().getRideSearchResult()
						.getRideCriteria();
				String preRideId = saveRideFinalizeAndPreRide(decorator,
						criteriaEntity);
				bean.setPreRideId(preRideId);
				bean.setRideReqResId(requestResponseEntity.getRideReqResId()+"");

				AppUserEntity appUserEntity = new AppUserEntity();
				appUserEntity.setAppUserId(
						new Integer(bean.getAppUserId()));
//				UserLoginEntity userLoginEntity = appUserDao.findByOject(
//						UserLoginEntity.class, "appUser", appUserEntity);
				UserLoginEntity userLoginEntity = appUserDao.findByIdParam2(
						appUserEntity.getAppUserId());
				if (userLoginEntity != null && 
						userLoginEntity.getAppUser() != null) {
					
					if(userLoginEntity.getKeyToken() == null){
						throw new GenericException(
								"Key token don't exits");	
					}
					
					//appUserEntity = userLoginEntity.getAppUser();
					/*activeDriverLocationEntity = appUserDao.findByOject(
							ActiveDriverLocationEntity.class, "appUser", appUserEntity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsBooked("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}*/
					
					activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(appUserEntity.getAppUserId());
					if(activeDriverLocationMongoEntity !=null){
						activeDriverLocationMongoEntity.setIsBooked("1");
						/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
						activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
					}
					
					try {
						if (userLoginEntity.getOsType() != null && 
								userLoginEntity.getOsType().equals("1")) {
							pushAndriod
									.pushAndriodPassengerNotification(
											userLoginEntity.getKeyToken(),
											bean.getPassengerappUserId(),
											bean.getDriverappUserId(),
											bean.getPreRideId(), requestResponseEntity.getRideReqResId()+"",
											PushNotificationStatus.PreRideStart
													.toString(),
											"Passenger has accepted your request");
						} else if (userLoginEntity.getOsType() != null &&
								userLoginEntity.getOsType().equals("0")) {
							pushIOS.pushIOSDriver(userLoginEntity
									.getKeyToken(), userLoginEntity.getIsDev(), requestResponseEntity
									.getRideReqResId()+"", bean
									.getPreRideId(), "", "",
									PushNotificationStatus.PreRideStart
											.toString(), "Passenger has accepted your request", userLoginEntity.getFcmToken());
						}
						
						if(activeDriverLocationMongoEntity != null){
							bean.setLng(activeDriverLocationMongoEntity.getLongValue());
							bean.setLat(activeDriverLocationMongoEntity.getLatValue());
							getColorFunctionality(bean);	
						}
						/*if(activeDriverLocationMongoEntity !=null){
							bean.setLng(activeDriverLocationMongoEntity.getLongValue());
							bean.setLat(activeDriverLocationMongoEntity.getLatValue());
							getColorFunctionality(bean);
						}
						*/
						//saving rideTracking
						try {		
							String[] requestNO = bean.getRequestNo().split("_");
							if(requestNO != null && requestNO.length > 0){
								RideTrackingBean rideTrackingBean = new RideTrackingBean();
								rideTrackingBean.setIsDriver("1");
								rideTrackingBean.setRequestNo(requestNO[0]);
								rideTrackingBean.setStatus(StatusEnum.Accepted.getValue()+"");
								rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
								rideTrackingBean.setAction(ProcessStateAndActionEnum.Accept_Request_By_Passenger.getValue()+"");
								rideTrackingBean.setIsComplete("0");
								asyncServiceImpl.saveRideTracking(rideTrackingBean);	
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						decorator.getResponseMap().put("data", bean);
						decorator.setResponseMessage("Notification is Sucessfully sent To driver");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

					} catch (NetworkIOException e) {
						decorator.setResponseMessage("Request has been failed");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());

					} catch (IOException e) {
						decorator.setResponseMessage("Request has been failed");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
					}

				} else {
					throw new GenericException("User dont exists");
				}

				//delete from radis
//				deleteRideFromRedis(bean.getRequestNo());
				try {
					asyncServiceImpl.deletRideRequestResponseFromRedis(
							bean.getRequestNo(), requestResponseEntity.getAppUser().getAppUserId()+"");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}

	}

	@Override
	public void acceptRequest(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			RideRequestResponseEntity requestResponseEntity = appUserDao
					.findById(RideRequestResponseEntity.class,
							new Integer(bean.getRideReqResId()));
			if (requestResponseEntity != null) {
				ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(5);
				requestResponseEntity.setStatusByStatusResponse(statusResponse);
				appUserDao.saveOrUpdate(requestResponseEntity);
				RideCriteriaEntity criteriaEntity = requestResponseEntity
						.getRideSearchResultDetail().getRideSearchResult()
						.getRideCriteria();
				String preRideId = saveRideFinalizeAndPreRide(decorator,
						criteriaEntity);
				bean.setPreRideId(preRideId);
				AppUserEntity appUserEntity = appUserDao.findById(
						AppUserEntity.class, new Integer(bean.getAppUserId()));
				if (appUserEntity != null
						&& appUserEntity.getIsDriver().equalsIgnoreCase("1")) {
					activeDriverLocationEntity = appUserDao.findByOject(
							ActiveDriverLocationEntity.class, "appUser",
							appUserEntity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsBooked("1");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}
				} else {
					AppUserEntity entity = appUserDao
							.findDrivers(requestResponseEntity
									.getRideReqResId());
					if (entity != null) {
						activeDriverLocationEntity = appUserDao.findByOject(
								ActiveDriverLocationEntity.class, "appUser",
								entity);
						if (activeDriverLocationEntity != null) {
							activeDriverLocationEntity.setIsRequested("1");
							appUserDao.saveOrUpdate(activeDriverLocationEntity);
						}
					}
				}
				String result = getKeyToken(appUserEntity);
				if (result.equalsIgnoreCase("failed")) {
					throw new GenericException(
							"Ride Request failded due to some error");
				} else {
					String IOSMode = getIOSMode(appUserEntity);
					if (IOSMode.equalsIgnoreCase("null")) {
						throw new GenericException(
								"Ride Request failded due to some error");
					} else {
						try {
							UserLoginEntity loginEntity = appUserDao
									.findByOject(UserLoginEntity.class,
											"appUser", appUserEntity);
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod
										.pushAndriodDriverNotification(
												result,
												appUserEntity.getAppUserId()
														+ "",
												requestResponseEntity
														.getAppUser()
														.getAppUserId()
														+ "",
												preRideId,
												requestResponseEntity
														.getRideReqResId() + "",
												PushNotificationStatus.AcceptRideFromPassenger
														.toString(),
												"Passenger Has Accept the Ride Request");
								// decorator.setResponseMessage("Request has been sent");
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSDriver(
										result,
										IOSMode,
										appUserEntity.getAppUserId() + "",
										requestResponseEntity.getAppUser()
												.getAppUserId() + "",
										preRideId,
										requestResponseEntity.getRideReqResId()
												+ "",
										PushNotificationStatus.AcceptRideFromPassenger
												.toString(),
										"Passenger Has Accept the Ride Request", loginEntity.getFcmToken());
								// decorator.setResponseMessage("Request has been sent");
								// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
							}

							ActiveDriverLocationEntity driverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", appUserEntity);
							if (driverLocationEntity != null) {

								bean.setLng(driverLocationEntity.getLongValue());
								bean.setLat(driverLocationEntity.getLatValue());
								getColorFunctionality(bean);
								decorator
										.setResponseMessage("Request has been sent Please Fetch Color Information");
								decorator.getResponseMap().put("data", bean);
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
							}
						} catch (IOException e) {
							decorator
									.setResponseMessage("Request has been failed");
							decorator.setReturnCode(ReturnStatusEnum.FAILURE
									.toString());
							// TODO: handle exception
							e.printStackTrace();
						}

						//delete from radis
						deleteRideFromRedis(bean.getRequestNo());
					}
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		}

	}

	@Override
	public void getDriverInfo(SafeHerDecorator decorator)
			throws GenericException {

		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		// bean.setLatitude("31.45542307711616");
		// bean.setLongitude("74.30010795593262");
		AppUserEntity appUserEntity = appUserDao.findPesonDetail(new Integer(
				bean.getAppUserId()));
		if (appUserEntity != null) {
			PersonEntity personEntity = appUserEntity.getPerson();
			PersonDetailEntity personDetailEntity = appUserDao
					.getPersonDetail(personEntity.getPersonId());
			bean.setFirstName(personEntity.getFirstName());
			bean.setPhoneNumber(personDetailEntity.getPrimaryCell());
			AppUserVehicleEntity appUserVehicleEntity = appUserDao
					.findDriverVehicalDetail(new Integer(bean.getAppUserId()));

			AppUserBiometricEntity biometricEntity = appUserDao.findByOject(
					AppUserBiometricEntity.class, "appUser", appUserEntity);
			if(biometricEntity != null){
				bean.setUserImageUrl(biometricEntity.getPath());
			}
			UserRatingEntity ratingEntity = appUserDao.findByOject(
					UserRatingEntity.class, "appUser", appUserEntity);
			if(ratingEntity != null){
				bean.setDriverRating(ratingEntity.getCurrentValue()+"");
			}else{
				bean.setDriverRating("0.0");
			}
			if (appUserVehicleEntity != null) {
				VehicleInfoEntity infoEntity = appUserVehicleEntity
						.getVehicleInfo();
				VehicleModelEntity modelEntity = appUserVehicleEntity
						.getVehicleInfo().getVehicleModel();
				bean.setPlateNo(infoEntity.getPlateNumber());
				bean.setVehicalModal(modelEntity.getName());
			}
			ActiveDriverLocationEntity driverLocationEntity = appUserDao
					.findByOject(ActiveDriverLocationEntity.class, "appUser",
							appUserEntity);
			if (driverLocationEntity != null) {

				DistanceAPIBean distanceAPIBean = new DistanceAPIBean();
				distanceAPIBean.setLatLngOrigins(new LatLng(Double.parseDouble(bean.getLatitude()), Double.parseDouble(bean.getLongitude())));
				distanceAPIBean.setLatLngDestinations(new LatLng(Double.parseDouble(driverLocationEntity.getLatValue()), Double.parseDouble(driverLocationEntity.getLongValue())));
				//distanceAPIBean.setOrigins(iGoogleMapServices
					//	.getFormatedAddress(bean.getLatitude(),
							//	bean.getLongitude()));
				//distanceAPIBean.setDestinations(iGoogleMapServices
					//	.getFormatedAddress(driverLocationEntity.getLatValue(),
							//	driverLocationEntity.getLongValue()));
				DistanceAPIBean apiBean = iGoogleMapsAPIService
						.googleDistanceAPIV2(distanceAPIBean);
				bean.setDistance(apiBean.getTotalDistanceMeters() / 1609.344
						+ " Miles");
				bean.setArrivaltime(apiBean.getTotalTimeSeconds() / 60
						+ " Minutes");

				decorator
						.setResponseMessage("Successfull feteched driver inof");
				decorator.getResponseMap().put("data", bean);
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}
		} else {
			throw new GenericException("User don;t exist");
		}
	}

	@Override
	public void getDriverInfoV2(SafeHerDecorator decorator)
			throws GenericException {

		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		// bean.setLatitude("31.45542307711616");
		// bean.setLongitude("74.30010795593262");
		AppUserEntity appUserEntity = appUserDao.findPesonDetail(new Integer(
				bean.getAppUserId()));
		if (appUserEntity != null) {
			PersonEntity personEntity = appUserEntity.getPerson();
			PersonDetailEntity personDetailEntity = appUserDao
					.getPersonDetail(personEntity.getPersonId());
			bean.setFirstName(personEntity.getFirstName());
			bean.setPhoneNumber(personDetailEntity.getPrimaryCell());
			AppUserVehicleEntity appUserVehicleEntity = appUserDao
					.findDriverVehicalDetail(new Integer(bean.getAppUserId()));

			AppUserBiometricEntity biometricEntity = appUserDao.findByOject(
					AppUserBiometricEntity.class, "appUser", appUserEntity);
			if(biometricEntity != null){
				bean.setUserImageUrl(biometricEntity.getPath());
			}
			UserRatingEntity ratingEntity = appUserDao.findByOject(
					UserRatingEntity.class, "appUser", appUserEntity);
			if(ratingEntity != null){
				bean.setDriverRating(ratingEntity.getCurrentValue()+"");
			}else{
				bean.setDriverRating("0.0");
			}
			if (appUserVehicleEntity != null) {
				VehicleInfoEntity infoEntity = appUserVehicleEntity
						.getVehicleInfo();
				VehicleModelEntity modelEntity = appUserVehicleEntity
						.getVehicleInfo().getVehicleModel();
				bean.setPlateNo(infoEntity.getPlateNumber());
				bean.setVehicalModal(modelEntity.getName());
				bean.setVehicalMake(modelEntity.getVehicleMake().getName());
			}
			/*ActiveDriverLocationEntity driverLocationEntity = appUserDao
					.findByOject(ActiveDriverLocationEntity.class, "appUser",
							appUserEntity);*/
			ActiveDriverLocationMongoEntity driverLocationEntity = activeDriverLocationRepository.findByAppUserId(appUserEntity.getAppUserId());
			if (driverLocationEntity != null) {

				DistanceAPIBean distanceAPIBean = new DistanceAPIBean();
				distanceAPIBean.setLatLngOrigins(new LatLng(Double.parseDouble(bean.getLatitude()), Double.parseDouble(bean.getLongitude())));
				distanceAPIBean.setLatLngDestinations(new LatLng(Double.parseDouble(driverLocationEntity.getLatValue()), Double.parseDouble(driverLocationEntity.getLongValue())));
				//distanceAPIBean.setOrigins(iGoogleMapServices
					//	.getFormatedAddress(bean.getLatitude(),
							//	bean.getLongitude()));
				//distanceAPIBean.setDestinations(iGoogleMapServices
					//	.getFormatedAddress(driverLocationEntity.getLatValue(),
							//	driverLocationEntity.getLongValue()));
				DistanceAPIBean apiBean = iGoogleMapsAPIService
						.googleDistanceAPIV2(distanceAPIBean);
				Double miles = apiBean.getTotalDistanceMeters() / 1609.344;
				if(miles != null && miles < 0.1){
					miles = 0.1;
				}
				bean.setDistance(new DecimalFormat(
	                     "##.##").format(miles)+" "
						+"Miles Away");
//				bean.setArrivaltime(apiBean.getTotalTimeSeconds() / 60
//						+ " Minutes");
				if(apiBean.getTotalTimeSeconds() < 120.0){
					bean.setArrivaltime("02:00");	
				}else{
					bean.setArrivaltime(DateUtil.getMinutesAndSecs(
							apiBean.getTotalTimeSeconds()) +"");
				}

				decorator
						.setResponseMessage("Successfull feteched driver info");
				decorator.getResponseMap().put("data", bean);
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}
		} else {
			throw new GenericException("User don't exist");
		}
	}

	
	@Override
	public boolean getColorFunctionality(RideRequestResponseBean bean)
			throws GenericException {

		if (bean.getPreRideId() != null) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(bean.getPreRideId()));
			logger.info(preRideEntity.getPreRideId());
			int passengerColor = ColorEnum.randomColor().getValue();
			preRideEntity.setRideColorByColorDriver(appUserDao.get(
					RideColorEntity.class, passengerColor));
			int driverColor;
			while (true) {
				driverColor = ColorEnum.randomColor().getValue();
				if (passengerColor != driverColor) {
					break;
				}
			}
			logger.info(preRideEntity.getPreRideId());
			preRideEntity.setRideColorByColorPassenger(appUserDao.get(
					RideColorEntity.class, driverColor));
			if (!appUserDao.update(preRideEntity)) {
				return false;
			}
			return true;
		}

		return false;
	}

	@Override
	public void getColorInfo(SafeHerDecorator decorator)
			throws GenericException {
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		signUpConverter.validateColorSelection(decorator);
		if (decorator.getErrors().size() == 0) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				preBean = convertPreEntityToBean(preRideEntity);
				decorator.setResponseMessage("Successfull feteched Color Info");
				decorator.getResponseMap().put("data", preBean);
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException(
						"No Ride Information Against Your Request");
			}
		}

	}

	private PreRideRequestBean convertPreEntityToBean(
			PreRideEntity preRideEntity) {
		PreRideRequestBean preBean = new PreRideRequestBean();
		preBean.setPreRideId(preRideEntity.getPreRideId().toString());
		preBean.setRideColorByDriver(preRideEntity.getRideColorByColorDriver()
				.getName());
		preBean.setRideColorByDriverCode(preRideEntity
				.getRideColorByColorDriver().getValue());
		preBean.setRideColorByPassenger(preRideEntity
				.getRideColorByColorPassenger().getName());
		preBean.setRideColorByPassengerCode(preRideEntity
				.getRideColorByColorPassenger().getValue());
		return preBean;
	}

	@Override
	public void preRideStart(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		signUpConverter.validatePreRideStart(decorator);
		if (decorator.getErrors().size() == 0) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				preRideEntity.setStatus(appUserDao.get(StatusEntity.class, 8));
				preRideEntity.setDriverStartTime(DateUtil.now());
				preRideEntity.setEstimatedTime(preBean.getEstimatedTime());
				// set Driver Start Time
				if (appUserDao.update(preRideEntity)) {
					AppUserEntity appUserEntity = appUserDao.findById(
							AppUserEntity.class,
							new Integer(preBean.getPassengerappUserId()));
					String result = getKeyToken(appUserEntity);
					if (result.equalsIgnoreCase("failed")) {
						throw new GenericException(
								"Pre Ride Start Request failed due to some error");
					} else {
						String IOSMode = getIOSMode(appUserEntity);
						if (IOSMode.equalsIgnoreCase("null")) {
							throw new GenericException(
									"Pre Ride Start Request failded due to some error");
						} else {
							try {
								UserLoginEntity loginEntity = appUserDao
										.findByOject(UserLoginEntity.class,
												"appUser", appUserEntity);
								if (loginEntity.getOsType() != null
										&& loginEntity.getOsType().equals("1")) {
									pushAndriod
											.pushAndriodPassengerNotification(
													result,
													preBean.getPassengerappUserId(),
													preBean.getDriverappUserId(),
													preBean.getPreRideId(),
													preBean.getEstimatedTime(),
													PushNotificationStatus.PreRideStart
															.toString(),
													"Ride Request");
								} else if (loginEntity.getOsType() != null
										&& loginEntity.getOsType().equals("0")) {
									pushIOS.pushIOSPassenger(loginEntity
											.getKeyToken(), IOSMode, preBean
											.getPassengerappUserId(), preBean
											.getDriverappUserId(), preBean
											.getPreRideId(), preBean
											.getEstimatedTime(),
											PushNotificationStatus.PreRideStart
													.toString(), "Ride Request", loginEntity.getFcmToken());

								}
								//this is for socket message to receiver start
								preBean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
										.getValue());
								preBean.setNotificationMessage("Passenger Has Accept the Ride Request");
								decorator.setDataBean(preBean);
								//this is for socket message to receiver end

								//saving rideTracking
								try {	
									String[] requestNO = preBean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										RideTrackingBean rideTrackingBean = new RideTrackingBean();
										rideTrackingBean.setIsDriver("2");
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setStatus(StatusEnum.DriverArrival.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.Start_Pre_Ride_By_Driver.getValue()+"");
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								decorator
										.setResponseMessage("Notification is Sucessfully sent To Passenger");
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());

							} catch (NetworkIOException e) {
								decorator
										.setResponseMessage("Request has been failed");
								decorator
										.setReturnCode(ReturnStatusEnum.FAILURE
												.toString());

							} catch (IOException e) {
								decorator
										.setResponseMessage("Request has been failed");
								decorator
										.setReturnCode(ReturnStatusEnum.FAILURE
												.toString());
							}

						}
					}
				}
			} else {
				throw new GenericException(
						"No Ride Information Against Your Request Please Check it");
			}
		} else {
			throw new GenericException(
					"Please Send Complete Information Against This Request");
		}

	}
	
	@Override
	public void getReasons(SafeHerDecorator decorator) throws GenericException {
		ReasonBean bean = (ReasonBean) decorator.getDataBean();
		if (StringUtil.isNotEmpty(bean.getReasonCategoryId())) {
			List<ReasonEntity> list = appUserDao.getReasons(new Integer(bean
					.getReasonCategoryId()));
			if (list != null && list.size() > 0) {
				populateReasons(decorator, list);
			}
		} else {
			throw new GenericException("Please provide reason category");
		}
	}

	private void populateReasons(SafeHerDecorator decorator,
			List<ReasonEntity> list) {
		ReasonBean reasonBean = new ReasonBean();
		for (int i = 0; i < list.size(); i++) {
			ReasonEntity entity = list.get(i);
			ReasonBean bean = new ReasonBean();
			signUpConverter
					.convertEntityToRideRequestResponseBean(entity, bean);
			reasonBean.getReasonsList().add(bean);
		}
		decorator.getResponseMap().put("reasons", reasonBean.getReasonsList());
		decorator.setResponseMessage("Fetch All Reasons");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public void cancelOrLateReasonRequest(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideTrackingBean rideTrackingBean = new RideTrackingBean();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {

			// multiple ride finalize records are saving against same
			// rideReqResIds for the time being we are fetching list and getting
			// 0 index of list
			// RideFinalizeEntity finalizeEntity = appUserDao
			// .findRideFinalize(new Integer(bean.getRideReqResId()));

			// ////////////////////////this portio/////////////////////////////
			RideFinalizeEntity finalizeEntity = new RideFinalizeEntity();
			List<RideFinalizeEntity> list = appUserDao
					.findRideFinalizeListV2(bean.getRequestNo(), Integer.parseInt(bean.getDriverId()));

			if (list != null && list.size() > 0) {
				finalizeEntity = list.get(0);
			}
			System.out.println(list.size() +"                         **********************************************");
			// ////////////////////////this portion/////////////////////////////

			if (finalizeEntity != null) {
//				RideRequestResponseEntity requestResponseEntity = finalizeEntity
//						.getRideRequestResponse();
				RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getDriverId()), bean.getRequestNo());
				if (requestResponseEntity != null) {
					AppUserEntity userEntity = new AppUserEntity();
					// userEntity.setAppUserId(new
					// Integer(bean.getAppUserId()));
					userEntity = appUserDao.findById(AppUserEntity.class,
							new Integer(bean.getAppUserId()));
					if (bean.getIsCancel() != null
							&& bean.getIsCancel().equals("1")) {
						
						//saveRideStatusCancelForReleaseDriverFree
						activeDriverStatusRepository.saveRequestStatus(
								"C", bean.getRequestNo());
						StatusEntity statusResponse = new StatusEntity();
						statusResponse.setStatusId(4);
						requestResponseEntity
								.setStatusByStatusResponse(statusResponse);
						appUserDao.saveOrUpdate(requestResponseEntity);

						finalizeEntity.setTimeCancle(DateUtil
								.getCurrentTimestamp());
						finalizeEntity.setIsCancled("1");
						finalizeEntity.setIsCancleByDriver(bean.getIsDriver());

						/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
						ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
						if (userEntity != null
								&& userEntity.getIsDriver() != null
								&& userEntity.getIsDriver().equalsIgnoreCase(
										"1")) {
							/*activeDriverLocationEntity = appUserDao
							.findByOject(
									ActiveDriverLocationEntity.class,
									"appUser", userEntity);*/
					activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(Integer.parseInt(bean.getDriverId()));
					
					/*if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsRequested("0");
						activeDriverLocationEntity.setIsBooked("0");
						appUserDao
								.saveOrUpdate(activeDriverLocationEntity);
					}*/
					if (activeDriverLocationMongoEntity != null) {
						activeDriverLocationMongoEntity.setIsRequested("0");
						activeDriverLocationMongoEntity.setIsBooked("0");
						/*appUserDao
								.saveOrUpdate(activeDriverLocationEntity);*/
						activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
							}
						} else {
							AppUserEntity entity = appUserDao
									.findDrivers(requestResponseEntity
											.getRideReqResId());
							if (entity != null) {
								/*activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", entity);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsBooked("0");
									activeDriverLocationEntity
											.setIsRequested("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsBooked("0");
									activeDriverLocationMongoEntity
											.setIsRequested("0");
									/*appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								
								}
							}
						}
					}
					ReasonEntity reasonEntity = new ReasonEntity();
					reasonEntity.setReasonId(new Integer(bean.getReasonid()));
					finalizeEntity.setCommentsCancle(bean.getCommentReason());
					finalizeEntity.setReason(reasonEntity);
					appUserDao.saveOrUpdate(finalizeEntity);

					UserLoginEntity loginEntity = appUserDao.findByOject(
							UserLoginEntity.class, "appUser", userEntity);
					if (loginEntity != null) {
						// userEntity = loginEntity.getAppUser();
						if (userEntity != null) {
							if (loginEntity.getKeyToken() == null) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								try {

									if (bean.getIsCancel() != null
											&& bean.getIsCancel().equals("1")) {
										if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("1")) {
											if (userEntity.getIsDriver() != null
													&& userEntity.getIsDriver()
															.equals("0")) {
												pushAndriod
														.pushAndriodPassengerNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideCancelRequest
																		.toString(),
																bean.getReason());

											} else {
												pushAndriod
														.pushAndriodDriverNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideCancelRequest
																		.toString(),
																bean.getReason());
											}
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										} else if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("0")) {
											if (loginEntity.getIsDev() != null) {
												if (userEntity.getIsDriver() != null
														&& userEntity
																.getIsDriver()
																.equals("0")) {
													pushIOS.pushIOSPassenger(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());

												} else {
													pushIOS.pushIOSDriver(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());
												}
												decorator
														.setResponseMessage("Request has been sent");
												decorator
														.setReturnCode(ReturnStatusEnum.SUCCESFUL
																.toString());
											} else {
												throw new GenericException(
														"Ride Request failded due to some error");
											}
										} else {
											throw new GenericException(
													"Ride Request failded due to some error");
										}
										if (userEntity.getIsDriver().equalsIgnoreCase("1")) {
											rideTrackingBean.setIsDriver("2");
											rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
											rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
											rideTrackingBean.setAction(ProcessStateAndActionEnum.Canel_Late_Reason_By_Passenger.getValue()+"");
										}else{
											rideTrackingBean.setIsDriver("1");
											rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
											rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
											rideTrackingBean.setAction(ProcessStateAndActionEnum.Canel_Late_Reason_By_Driver.getValue()+"");
										}

										//this is for socket message to receiver start
										bean.setNotificationType(PushNotificationStatus.PreRideCancelRequest
												.getValue());
										//this is for socket message to receiver end
									} else {
										if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("1")) {
											if (userEntity.getIsDriver() != null
													&& userEntity.getIsDriver()
															.equals("0")) {
												pushAndriod
														.pushAndriodPassengerNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideLateOrCancelRequest
																		.toString(),
																bean.getReason());

											} else {
												pushAndriod
														.pushAndriodDriverNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideLateOrCancelRequest
																		.toString(),
																bean.getReason());
											}
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										} else if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("0")) {
											if (loginEntity.getIsDev() != null) {
												if (userEntity.getIsDriver() != null
														&& userEntity
																.getIsDriver()
																.equals("0")) {
													pushIOS.pushIOSPassenger(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideLateOrCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());

												} else {
													pushIOS.pushIOSDriver(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideLateOrCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());
												}
												decorator
														.setResponseMessage("Request has been sent");
												decorator
														.setReturnCode(ReturnStatusEnum.SUCCESFUL
																.toString());
											} else {
												throw new GenericException(
														"Ride Request failded due to some error");
											}
										} else {
											throw new GenericException(
													"Ride Request failded due to some error");
										}

										//this is for socket message to receiver start
										bean.setNotificationType(PushNotificationStatus.PreRideLateOrCancelRequest
												.getValue());
										//this is for socket message to receiver end
									}
								} catch (IOException e) {
									e.printStackTrace();
								}

								//this is for socket message to receiver start
								bean.setNotificationMessage(bean.getReason());
								decorator.setDataBean(bean);
								//this is for socket message to receiver end
								
								//saving rideTracking
								try {
									String[] requestNO = bean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}

			} else {
				throw new GenericException(
						"Ride cancel request failded due to some error");
			}

		} else {
			throw new GenericException("User don't exists");
		}
	}

	@Override
	public void cancelOrLateReasonRequestV2(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideTrackingBean rideTrackingBean = new RideTrackingBean();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {

			// multiple ride finalize records are saving against same
			// rideReqResIds for the time being we are fetching list and getting
			// 0 index of list
			// RideFinalizeEntity finalizeEntity = appUserDao
			// .findRideFinalize(new Integer(bean.getRideReqResId()));

			// ////////////////////////this portio/////////////////////////////
			RideFinalizeEntity finalizeEntity = new RideFinalizeEntity();
			List<RideFinalizeEntity> list = appUserDao
					.findRideFinalizeListV2(bean.getRequestNo(), Integer.parseInt(bean.getDriverId()));

			if (list != null && list.size() > 0) {
				finalizeEntity = list.get(0);
			}
			System.out.println(list.size() +"                         **********************************************");
			// ////////////////////////this portion/////////////////////////////

			if (finalizeEntity != null) {
//				RideRequestResponseEntity requestResponseEntity = finalizeEntity
//						.getRideRequestResponse();
				RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(bean.getDriverId()), bean.getRequestNo());
				if (requestResponseEntity != null) {
					AppUserEntity userEntity = new AppUserEntity();
					// userEntity.setAppUserId(new
					// Integer(bean.getAppUserId()));
					userEntity = appUserDao.findById(AppUserEntity.class,
							new Integer(bean.getAppUserId()));
					if (bean.getIsCancel() != null
							&& bean.getIsCancel().equals("1")) {
						StatusEntity statusResponse = new StatusEntity();
						statusResponse.setStatusId(4);
						requestResponseEntity
								.setStatusByStatusResponse(statusResponse);
						appUserDao.saveOrUpdate(requestResponseEntity);

						finalizeEntity.setTimeCancle(DateUtil
								.getCurrentTimestamp());
						finalizeEntity.setIsCancled("1");
						finalizeEntity.setIsCancleByDriver(bean.getIsDriver());

						/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
						ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
						
						if (userEntity != null
								&& userEntity.getIsDriver() != null
								&& userEntity.getIsDriver().equalsIgnoreCase(
										"1")) {
							/*activeDriverLocationEntity = appUserDao
									.findByOject(
											ActiveDriverLocationEntity.class,
											"appUser", userEntity);*/
							activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(Integer.parseInt(bean.getDriverId()));
							
							/*if (activeDriverLocationEntity != null) {
								activeDriverLocationEntity.setIsRequested("0");
								activeDriverLocationEntity.setIsBooked("0");
								appUserDao
										.saveOrUpdate(activeDriverLocationEntity);
							}*/
							if (activeDriverLocationMongoEntity != null) {
								activeDriverLocationMongoEntity.setIsRequested("0");
								activeDriverLocationMongoEntity.setIsBooked("0");
								/*appUserDao
										.saveOrUpdate(activeDriverLocationEntity);*/
								activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								
							}

							//async saving driverDrivingDetailIntoMongo
							DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
							detailBean.setAppUserId(userEntity.getAppUserId()+"");
							detailBean.setTotalCancelPreRides(1.0);
							asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
							
							//async saving driverTrackIntoDrivingDetailIntoMongo
							asyncServiceImpl.saveLocationTrackIntoDrivingDetail(userEntity.getAppUserId(), "1");
							
						} else {
//							AppUserEntity entity = appUserDao
//									.findDrivers(requestResponseEntity
//											.getRideReqResId());
							AppUserEntity entity = appUserDao.findById(
									AppUserEntity.class, new Integer(bean.getDriverId()));
							if (entity != null) {
								/*activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", entity);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsBooked("0");
									activeDriverLocationEntity
											.setIsRequested("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);
								}*/activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(entity.getAppUserId());
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsBooked("0");
									activeDriverLocationMongoEntity
											.setIsRequested("0");
									/*appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								}
							}
						}
						
						//async delate from redis arrival and crieteria
						try {
							asyncServiceImpl.deletRideCriteriaAndArrivalTimeFromRedis(
									bean.getPassengerId());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ReasonEntity reasonEntity = new ReasonEntity();
					reasonEntity.setReasonId(new Integer(bean.getReasonid()));
					finalizeEntity.setCommentsCancle(bean.getCommentReason());
					finalizeEntity.setReason(reasonEntity);
					appUserDao.saveOrUpdate(finalizeEntity);

					UserLoginEntity loginEntity = appUserDao.findByOject(
							UserLoginEntity.class, "appUser", userEntity);
					if (loginEntity != null) {
						// userEntity = loginEntity.getAppUser();
						if (userEntity != null) {
							if (loginEntity.getKeyToken() == null) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								try {

									if (bean.getIsCancel() != null
											&& bean.getIsCancel().equals("1")) {
										if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("1")) {
											if (userEntity.getIsDriver() != null
													&& userEntity.getIsDriver()
															.equals("0")) {
												pushAndriod
														.pushAndriodPassengerNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideCancelRequest
																		.toString(),
																bean.getReason());

											} else {
												pushAndriod
														.pushAndriodDriverNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideCancelRequest
																		.toString(),
																bean.getReason());
											}
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										} else if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("0")) {
											if (loginEntity.getIsDev() != null) {
												if (userEntity.getIsDriver() != null
														&& userEntity
																.getIsDriver()
																.equals("0")) {
													pushIOS.pushIOSPassenger(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());

												} else {
													pushIOS.pushIOSDriver(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());
												}
												decorator
														.setResponseMessage("Request has been sent");
												decorator
														.setReturnCode(ReturnStatusEnum.SUCCESFUL
																.toString());
											} else {
												throw new GenericException(
														"Ride Request failded due to some error");
											}
										} else {
											throw new GenericException(
													"Ride Request failded due to some error");
										}
										if (userEntity.getIsDriver().equalsIgnoreCase("1")) {
											rideTrackingBean.setIsDriver("2");
											rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
											rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
											rideTrackingBean.setAction(ProcessStateAndActionEnum.Canel_Late_Reason_By_Passenger.getValue()+"");
										}else{
											rideTrackingBean.setIsDriver("1");
											rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
											rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
											rideTrackingBean.setAction(ProcessStateAndActionEnum.Canel_Late_Reason_By_Driver.getValue()+"");
										}

										//this is for socket message to receiver start
										bean.setNotificationType(PushNotificationStatus.PreRideCancelRequest
												.getValue());
										//this is for socket message to receiver end
									} else {
										if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("1")) {
											if (userEntity.getIsDriver() != null
													&& userEntity.getIsDriver()
															.equals("0")) {
												pushAndriod
														.pushAndriodPassengerNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideLateOrCancelRequest
																		.toString(),
																bean.getReason());

											} else {
												pushAndriod
														.pushAndriodDriverNotification(
																loginEntity
																		.getKeyToken(),
																bean.getReason(),
																"",
																"",
																"",
																PushNotificationStatus.PreRideLateOrCancelRequest
																		.toString(),
																bean.getReason());
											}
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										} else if (loginEntity.getOsType() != null
												&& loginEntity.getOsType()
														.equals("0")) {
											if (loginEntity.getIsDev() != null) {
												if (userEntity.getIsDriver() != null
														&& userEntity
																.getIsDriver()
																.equals("0")) {
													pushIOS.pushIOSPassenger(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideLateOrCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());

												} else {
													pushIOS.pushIOSDriver(
															loginEntity
																	.getKeyToken(),
															loginEntity
																	.getIsDev(),
															bean.getReason(),
															"",
															"",
															"",
															PushNotificationStatus.PreRideLateOrCancelRequest
																	.toString(),
															bean.getReason(), loginEntity.getFcmToken());
												}
												decorator
														.setResponseMessage("Request has been sent");
												decorator
														.setReturnCode(ReturnStatusEnum.SUCCESFUL
																.toString());
											} else {
												throw new GenericException(
														"Ride Request failded due to some error");
											}
										} else {
											throw new GenericException(
													"Ride Request failded due to some error");
										}

										//this is for socket message to receiver start
										bean.setNotificationType(PushNotificationStatus.PreRideLateOrCancelRequest
												.getValue());
										//this is for socket message to receiver end
									}
								} catch (IOException e) {
									e.printStackTrace();
								}

								//this is for socket message to receiver start
								bean.setNotificationMessage(bean.getReason());
								decorator.setDataBean(bean);
								//this is for socket message to receiver end
								
								//saving rideTracking
								try {
									String[] requestNO = bean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}

			} else {
				throw new GenericException(
						"Ride cancel request failded due to some error");
			}

		} else {
			throw new GenericException("User don't exists");
		}
	}
	@Override
	public void matchColorManagment(SafeHerDecorator decorator)
			throws GenericException, IOException {
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		signUpConverter.validateMatchColor(decorator);
		if (decorator.getErrors().size() == 0) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				RideTrackingBean rideTrackingBean = new RideTrackingBean();
				if (preBean.getIsDriver().equals("0")) {
					preRideEntity.setIsDriverColorVerified(preBean
							.getIsDriverColorVerified());
					preRideEntity.setPassengerVerificationAttemps(preBean
							.getPassengerVerificationAttemps());
					preRideEntity.setPassengerVerificationTime(DateUtil.now());
				} else if (preBean.getIsDriver().equals("1")) {
					preRideEntity.setIsPassengerColorVerified(preBean
							.getIsPassengerColorVerified());
					preRideEntity.setDriverVerificationAttemps(preBean
							.getDriverVerificationAttemps());
					preRideEntity.setDriverVerificationTime(DateUtil.now());
				}

				if (appUserDao.update(preRideEntity)) {

					if ((preRideEntity.getIsPassengerColorVerified() != null && preRideEntity
							.getIsDriverColorVerified() != null)
							&& preRideEntity.getIsDriverColorVerified().equals(
									"1")
							&& preRideEntity.getIsPassengerColorVerified()
									.equals("1")) {
						 RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
							       new Integer(preBean.getDriverappUserId()), preRideEntity.getRequestNo());
							    if (requestResponseEntity != null){
							    	if(requestResponseEntity.getStatusByStatusFinal().getStatusId()==StatusEnum.Rejected.getValue()){
							    		sendNotification(preBean.getPassengerappUserId(), "0",
												PushNotificationStatus.RideCancelByEmergency
														.toString(),
												"Ride has been Canceled");
										sendNotification(preBean.getDriverappUserId(), "1",
												PushNotificationStatus.RideCancelByEmergency
														.toString(), "Ride has been Canceled");
										decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
										decorator.getResponseMap().put("VerificationFlag", "false");
										if(preBean.getIsDriver().equals("0")){
											rideTrackingBean.setAction(ProcessStateAndActionEnum.
													Cancel_Ride_On_Color_Match_By_Driver.getValue()+"");
										}else{
											rideTrackingBean.setAction(ProcessStateAndActionEnum.
													Cancel_Ride_On_Color_Match_By_Passenger.getValue()+"");
										}
										rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
							    	}else{
							    		sendNotification(preBean.getPassengerappUserId(), "0",
												PushNotificationStatus.SuccessfullVerification
														.toString(),
												"Congratulation Have a Safe Ride!");
										sendNotification(preBean.getDriverappUserId(), "1",
												PushNotificationStatus.SuccessfullVerification
														.toString(), "Please Start Ride !");
										decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
										decorator.getResponseMap().put("VerificationFlag", "true");
										rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.
												Color_Matched.getValue()+"");
							    	}
							    }
										
					} else if ((preRideEntity.getIsPassengerColorVerified() != null && preRideEntity
							.getIsDriverColorVerified() != null)
							&& preRideEntity.getIsDriverColorVerified().equals(
									"0")
							&& preRideEntity.getIsPassengerColorVerified()
									.equals("0")) {
						sendNotification(preBean.getPassengerappUserId(), "0",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						sendNotification(preBean.getDriverappUserId(), "1",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						cancelRideRequestV2(preRideEntity, preBean);
						decorator
								.setResponseMessage("Please Cancel Ride && Change Your Screens");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());
					} else if (preRideEntity.getIsPassengerColorVerified() != null
							&& preRideEntity.getIsPassengerColorVerified()
									.equals("0")) {
						sendNotification(preBean.getPassengerappUserId(), "0",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						cancelRideRequestV2(preRideEntity, preBean);
						//setRideTrackingStatus
						rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
						rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
						rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Driver.getValue()+"");
						decorator
								.setResponseMessage("you have not Verified the Color");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());
					} else if (preRideEntity.getIsPassengerColorVerified() != null
							&& preRideEntity.getIsPassengerColorVerified()
									.equals("0")
							&& preRideEntity.getIsDriverColorVerified() != null
							&& preRideEntity.getIsDriverColorVerified().equals(
									"1")) {
						sendNotification(preBean.getPassengerappUserId(), "0",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						cancelRideRequestV2(preRideEntity, preBean);
						//setRideTrackingStatus
						rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
						rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
						rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Driver.getValue()+"");
						decorator
								.setResponseMessage("you have not Verified the Color");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());
					} else if (preRideEntity.getIsDriverColorVerified() != null
							&& preRideEntity.getIsDriverColorVerified().equals(
									"0")) {
						sendNotification(preBean.getDriverappUserId(), "1",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						cancelRideRequestV2(preRideEntity, preBean);
						//setRideTrackingStatus
						rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
						rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
						rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Passenger.getValue()+"");
						decorator
								.setResponseMessage("you have not Verified the Color Please Change Screen");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());

					} else if (preRideEntity.getIsDriverColorVerified() != null
							&& preRideEntity.getIsDriverColorVerified().equals(
									"0")
							&& (preRideEntity.getIsPassengerColorVerified() != null && preRideEntity
									.getIsPassengerColorVerified().equals("1"))) {
						sendNotification(preBean.getDriverappUserId(), "1",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification");
						cancelRideRequestV2(preRideEntity, preBean);
						//setRideTrackingStatus
						rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
						rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
						rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Passenger.getValue()+"");
						decorator
								.setResponseMessage("you have not Verified the Color Please Change Screen");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());

					} else {

						if (preBean.getIsDriver().equals("0")) {
							sendNotification(preBean.getDriverappUserId(),
									"1",
									PushNotificationStatus.ColorVerification
											.toString(),
									"Passenger has verified your color please confirm on your side");
							decorator
									.setResponseMessage("You Have Verifiy Driver Color");
							//setRideTrackingStatus
							rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Color_Matched_By_Passenger.getValue()+"");
							decorator.getResponseMap().put("VerificationFlag", "False");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									.toString());
						} else if (preBean.getIsDriver().equals("1")) {
							sendNotification(preBean.getPassengerappUserId(),
									"0",
									PushNotificationStatus.ColorVerification
											.toString(),
									"Driver has verified your color please confirm on your side");
							//setRideTrackingStatus
							rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Color_Matched_By_Driver.getValue()+"");
							decorator
									.setResponseMessage("You Have Verifiy Passenger Color");
							decorator.getResponseMap().put("VerificationFlag", "False");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									.toString());
						}
					}

					//saving rideTracking
					try {	
						String[] requestNO = preBean.getRequestNo().split("_");
						if(requestNO != null && requestNO.length > 0){
							rideTrackingBean.setRequestNo(requestNO[0]);
							rideTrackingBean.setIsComplete("0");
							asyncServiceImpl.saveRideTracking(rideTrackingBean);	
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} else {
					throw new GenericException(
							"Your Info Make Some Problem Against Saving");
				}
			} else {
				throw new GenericException(
						"No Ride Information Against Your Request Please Check it");
			}
		} else {
			throw new GenericException(
					"Please Send Complete Information Against This Request");
		}
	}

	@Override
	public void matchColorManagmentV2(SafeHerDecorator decorator)
			throws GenericException, IOException {
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		String requestStatus = "";
		signUpConverter.validateMatchColor(decorator);
		
		if (decorator.getErrors().size() == 0) {
			RideRequestResponseEntity requestResponseEntity = appUserDao
					.findRideRequestResponseV2(new Integer(preBean.getDriverappUserId()), preBean.getRequestNo());
			if (requestResponseEntity != null) {
				if (requestResponseEntity.getStatusByStatusResponse().getStatusId() == 4) {
					throw new GenericException("Ride Already Canel");
				}
			}
			requestStatus = requestStatusRepository.
					findRequestStatus(preBean.getRequestNo());
			if(requestStatus != null && requestStatus.equals("1")){
				try {
					Thread.sleep(2000);
					RideRequestResponseEntity requestResponseEntity2 = appUserDao
							.findRideRequestResponseV2(new Integer(preBean.getDriverappUserId()), preBean.getRequestNo());
					if (requestResponseEntity2 != null) {
						if (requestResponseEntity2.getStatusByStatusResponse().getStatusId() == 4) {
							throw new GenericException("Ride Already Canel");
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new GenericException("Ride request failed due to some error");
				}
			}
			requestStatusRepository.updateRequestStatus("1", preBean.getRequestNo());
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				RideTrackingBean rideTrackingBean = new RideTrackingBean();
				if (preBean.getIsDriver().equals("0")) {
					preRideEntity.setIsDriverColorVerified(preBean
							.getIsDriverColorVerified());
					preRideEntity.setPassengerVerificationAttemps(preBean
							.getPassengerVerificationAttemps());
					preRideEntity.setPassengerVerificationTime(DateUtil.now());
				} else if (preBean.getIsDriver().equals("1")) {
					preRideEntity.setIsPassengerColorVerified(preBean
							.getIsPassengerColorVerified());
					preRideEntity.setDriverVerificationAttemps(preBean
							.getDriverVerificationAttemps());
					preRideEntity.setDriverVerificationTime(DateUtil.now());
				}

				if (appUserDao.update(preRideEntity)) {
					//saveIntoRedisForSocket
					iRideColorManagementRepository.saveColorStatus(
							preBean, preBean.getRequestNo());
					requestStatusRepository.updateRequestStatus("0", preBean.getRequestNo());
					if ((preRideEntity.getIsPassengerColorVerified() != null && preRideEntity
							.getIsDriverColorVerified() != null)
							&& preRideEntity.getIsDriverColorVerified().equals(
									"1")
							&& preRideEntity.getIsPassengerColorVerified()
									.equals("1")) {
//						 RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
//							       new Integer(preBean.getDriverappUserId()), preRideEntity.getRequestNo());
//							    if (requestResponseEntity != null){
//							    	if(requestResponseEntity.getStatusByStatusFinal().getStatusId()==StatusEnum.Rejected.getValue()){
//							    		sendNotificationColorVerification(preBean.getPassengerappUserId(), "0",
//												PushNotificationStatus.RideCancelByEmergency
//														.toString(),
//												"Ride has been Canceled", decorator);
//							    		sendNotificationColorVerification(preBean.getDriverappUserId(), "1",
//												PushNotificationStatus.RideCancelByEmergency
//														.toString(), "Ride has been Canceled", decorator);
//										decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
//												.toString());
//										decorator.getResponseMap().put("VerificationFlag", "false");
//										if(preBean.getIsDriver().equals("0")){
//											rideTrackingBean.setIsDriver("1");
//											rideTrackingBean.setAction(ProcessStateAndActionEnum.
//													Cancel_Ride_On_Color_Match_By_Driver.getValue()+"");
//										}else{
//											rideTrackingBean.setIsDriver("2");
//											rideTrackingBean.setAction(ProcessStateAndActionEnum.
//													Cancel_Ride_On_Color_Match_By_Passenger.getValue()+"");
//										}
//										rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
//										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
//										
//										//saveRideStatusCancelForReleaseDriverFree
//										activeDriverStatusRepository.saveRequestStatus(
//												"C", preBean.getRequestNo());
//							    	}else{
							    		sendNotificationColorVerification(preBean.getPassengerappUserId(), "0",
												PushNotificationStatus.SuccessfullVerification
														.toString(),
												"Congratulation Have a Safe Ride!", decorator);
							    		sendNotificationColorVerification(preBean.getDriverappUserId(), "1",
												PushNotificationStatus.SuccessfullVerification
														.toString(), "Please Start Ride !", decorator);
										decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
										decorator.getResponseMap().put("VerificationFlag", "true");
										if(preBean.getIsDriver().equals("0")){
											rideTrackingBean.setIsDriver("1");
										}else{
											rideTrackingBean.setIsDriver("2");
										}
										rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.
												Color_Matched.getValue()+"");
//							    	}
//							    }
										
					} else if ((preRideEntity.getIsPassengerColorVerified() != null && 
								preRideEntity.getIsPassengerColorVerified().equals("0")) ||   
									(preRideEntity .getIsDriverColorVerified() != null && 
									preRideEntity.getIsDriverColorVerified().equals( "0"))) {
						
						//saveRideStatusCancelForReleaseDriverFree
						activeDriverStatusRepository.saveRequestStatus(
								"C", preBean.getRequestNo());
						
						sendNotificationColorVerification(preBean.getPassengerappUserId(), "0",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification", decorator);
						sendNotificationColorVerification(preBean.getDriverappUserId(), "1",
								PushNotificationStatus.ColorVerificationFailed
										.toString(),
								"Ride Cancel Because Of No Color Verification", decorator);
						cancelRideRequestV2(preRideEntity, preBean);
						decorator
								.setResponseMessage("Please Cancel Ride && Change Your Screens");
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.toString());
						
						if(preBean.getIsDriver().equals("0")){
							rideTrackingBean.setIsDriver("1");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.
									Cancel_Ride_On_Color_Match_By_Driver.getValue()+"");
						}else{
							rideTrackingBean.setIsDriver("2");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.
									Cancel_Ride_On_Color_Match_By_Passenger.getValue()+"");
						}
						appUserDao.InActiveDriverLocationByHql(
								Integer.parseInt(preBean.getDriverappUserId()), "0", "0");
						
						//async delate from redis arrival and crieteria
						try {
							asyncServiceImpl.deletRideCriteriaAndArrivalTimeFromRedis(
									preBean.getPassengerappUserId());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {

						if (preBean.getIsDriver().equals("0")) {
							sendNotificationColorVerification(preBean.getDriverappUserId(),
									"1",
									PushNotificationStatus.ColorVerification
											.toString(),
									"Passenger has verified your color please confirm on your side", decorator);
							decorator
									.setResponseMessage("You Have Verifiy Driver Color");
							//setRideTrackingStatus
							rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
							rideTrackingBean.setIsDriver("1");
							rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Color_Matched_By_Passenger.getValue()+"");
							decorator.getResponseMap().put("VerificationFlag", "False");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									.toString());
						} else if (preBean.getIsDriver().equals("1")) {
							sendNotificationColorVerification(preBean.getPassengerappUserId(),
									"0",
									PushNotificationStatus.ColorVerification
											.toString(),
									"Driver has verified your color please confirm on your side", decorator);
							//setRideTrackingStatus
							rideTrackingBean.setIsDriver("2");
							rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
							rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
							rideTrackingBean.setAction(ProcessStateAndActionEnum.Color_Matched_By_Driver.getValue()+"");
							decorator
									.setResponseMessage("You Have Verifiy Passenger Color");
							decorator.getResponseMap().put("VerificationFlag", "False");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									.toString());
						}
					}

					//saving rideTracking
					try {	
						String[] requestNO = preBean.getRequestNo().split("_");
						if(requestNO != null && requestNO.length > 0){
							rideTrackingBean.setRequestNo(requestNO[0]);
							rideTrackingBean.setIsComplete("0");
							asyncServiceImpl.saveRideTracking(rideTrackingBean);	
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} else {
					throw new GenericException(
							"Your Info Make Some Problem Against Saving");
				}
			} else {
				throw new GenericException(
						"No Ride Information Against Your Request Please Check it");
			}
		} else {
			throw new GenericException(
					"Please Send Complete Information Against This Request");
		}
	}

	private void cancelRideRequest(PreRideEntity preRideEntity,
			PreRideRequestBean preBean) {
		// ////////////////////////this portio/////////////////////////////
		RideFinalizeEntity finalizeEntity = preRideEntity.getRideFinalize();
		// ////////////////////////this portion/////////////////////////////
		if (finalizeEntity != null && preRideEntity.getRequestNo() != null) {
//			RideRequestResponseEntity requestResponseEntity = finalizeEntity
//					.getRideRequestResponse();
			RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
					new Integer(preBean.getDriverappUserId()), preRideEntity.getRequestNo());
			if (requestResponseEntity != null) {
				AppUserEntity userEntity = new AppUserEntity();
				// userEntity.setAppUserId(new Integer(bean.getAppUserId()));
				userEntity = appUserDao.findById(AppUserEntity.class,
						new Integer(preBean.getDriverappUserId()));
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(4);
				requestResponseEntity.setStatusByStatusResponse(statusResponse);
				appUserDao.saveOrUpdate(requestResponseEntity);

				finalizeEntity.setTimeCancle(DateUtil.getCurrentTimestamp());
				finalizeEntity.setIsCancled("1");
				finalizeEntity.setIsCancleByDriver(preBean.getIsDriver());

				ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
				/*if (userEntity != null && userEntity.getIsDriver() != null
						&& userEntity.getIsDriver().equalsIgnoreCase("1")) {
					activeDriverLocationEntity = appUserDao.findByOject(
							ActiveDriverLocationEntity.class, "appUser",
							userEntity);
					if (activeDriverLocationEntity != null) {
						activeDriverLocationEntity.setIsRequested("0");
						activeDriverLocationEntity.setIsBooked("0");
						appUserDao.saveOrUpdate(activeDriverLocationEntity);
					}
				}*/
				if(requestResponseEntity.getRideSearchResultDetail() != null){
					AppUserEntity entity = requestResponseEntity.getRideSearchResultDetail().getAppUser();
					if (entity != null) {
						activeDriverLocationEntity = appUserDao
								.findByOject(
										ActiveDriverLocationEntity.class,
										"appUser", entity);
						if (activeDriverLocationEntity != null) {
							activeDriverLocationEntity.setIsBooked("0");
							activeDriverLocationEntity
									.setIsRequested("0");
							appUserDao
									.saveOrUpdate(activeDriverLocationEntity);
						}
					}
				}

				ReasonEntity reasonEntity = new ReasonEntity();
				reasonEntity.setReasonId(new Integer(17));
				finalizeEntity
						.setCommentsCancle("Ride Cancel Against Color Verification");
				finalizeEntity.setReason(reasonEntity);
				appUserDao.saveOrUpdate(finalizeEntity);

			}
		}
	}
	
	
	private void cancelRideRequestV2(PreRideEntity preRideEntity,
			PreRideRequestBean preBean) {
		// ////////////////////////this portio/////////////////////////////
		RideFinalizeEntity finalizeEntity = preRideEntity.getRideFinalize();
		// ////////////////////////this portion/////////////////////////////
		if (finalizeEntity != null && preRideEntity.getRequestNo() != null) {
//			RideRequestResponseEntity requestResponseEntity = finalizeEntity
//					.getRideRequestResponse();
			RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
					new Integer(preBean.getDriverappUserId()), preRideEntity.getRequestNo());
			if (requestResponseEntity != null) {
				AppUserEntity userEntity = new AppUserEntity();
				// userEntity.setAppUserId(new Integer(bean.getAppUserId()));
				userEntity = appUserDao.findById(AppUserEntity.class,
						new Integer(preBean.getDriverappUserId()));
				StatusEntity statusResponse = new StatusEntity();
				statusResponse.setStatusId(4);
				requestResponseEntity.setStatusByStatusResponse(statusResponse);
				appUserDao.saveOrUpdate(requestResponseEntity);

				finalizeEntity.setTimeCancle(DateUtil.getCurrentTimestamp());
				finalizeEntity.setIsCancled("1");
				finalizeEntity.setIsCancleByDriver(preBean.getIsDriver());

				/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
				ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
					
				if(requestResponseEntity.getRideSearchResultDetail() != null){
					AppUserEntity entity = requestResponseEntity.getRideSearchResultDetail().getAppUser();
					if (entity != null) {
						/*activeDriverLocationEntity = appUserDao
								.findByOject(
										ActiveDriverLocationEntity.class,
										"appUser", entity);
						if (activeDriverLocationEntity != null) {
							activeDriverLocationEntity.setIsBooked("0");
							activeDriverLocationEntity
									.setIsRequested("0");
							appUserDao
									.saveOrUpdate(activeDriverLocationEntity);
						}*/
						activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(entity.getAppUserId());
						if (activeDriverLocationMongoEntity != null) {
							activeDriverLocationMongoEntity.setIsRequested("0");
							activeDriverLocationMongoEntity.setIsBooked("0");
							/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
							activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
						}
					}
				}

				ReasonEntity reasonEntity = new ReasonEntity();
				reasonEntity.setReasonId(new Integer(17));
				finalizeEntity
						.setCommentsCancle("Ride Cancel Against Color Verification");
				finalizeEntity.setReason(reasonEntity);
				appUserDao.saveOrUpdate(finalizeEntity);

			}
		}
	}

	@Override
	public void passengerOrDriverNotReached(SafeHerDecorator decorator)
			throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			String message = "";
			AppUserEntity userEntity = new AppUserEntity();
			userEntity.setAppUserId(new Integer(bean.getAppUserId()));
			UserLoginEntity loginEntity = appUserDao.findByOject(
					UserLoginEntity.class, "appUser", userEntity);
			if (loginEntity != null) {
				userEntity = loginEntity.getAppUser();
				if (userEntity != null) {
					if (userEntity.getIsDriver() != null
							&& userEntity.getIsDriver().equals("0")) {
						message = "Driver has arrived";
					} else if (userEntity.getIsDriver() != null
							&& userEntity.getIsDriver().equals("1")) {
						message = "Passenger has arrived";
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}

					if (loginEntity.getKeyToken() == null) {
						throw new GenericException(
								"Ride Request failded due to some error");
					} else {
						try {
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								if (userEntity.getIsDriver() != null
										&& userEntity.getIsDriver().equals("0")) {
									pushAndriod
											.pushAndriodPassengerNotification(
													loginEntity.getKeyToken(),
													"",
													"",
													"",
													"",
													PushNotificationStatus.PreRideLateOrCancelRequestDriver
															.toString(),
													message);

								} else {
									pushAndriod
											.pushAndriodDriverNotification(
													loginEntity.getKeyToken(),
													"",
													"",
													"",
													"",
													PushNotificationStatus.PreRideRequest
															.toString(),
													message);
								}
								decorator
										.setResponseMessage("Request has been sent");
								decorator
										.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.toString());
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								if (loginEntity.getIsDev() != null) {
									if (userEntity.getIsDriver() != null
											&& userEntity.getIsDriver().equals(
													"0")) {
										pushIOS.pushIOSPassenger(
												loginEntity.getKeyToken(),
												loginEntity.getIsDev(),
												"",
												"",
												"",
												"",
												PushNotificationStatus.PreRideLateOrCancelRequestDriver
														.toString(), message, loginEntity.getFcmToken());

									} else {
										pushIOS.pushIOSDriver(
												loginEntity.getKeyToken(),
												loginEntity.getIsDev(),
												"",
												"",
												"",
												"",
												PushNotificationStatus.PreRideRequest
														.toString(), message, loginEntity.getFcmToken());
									}
									decorator
											.setResponseMessage("Request has been sent");
									decorator
											.setReturnCode(ReturnStatusEnum.SUCCESFUL
													.toString());
								} else {
									throw new GenericException(
											"Ride Request failded due to some error");
								}
							} else {
								throw new GenericException(
										"Ride Request failded due to some error");
							}
							
							//this is for socket message to receiver start
							bean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
									.getValue());
							bean.setNotificationMessage(message);
							decorator.setDataBean(bean);
							//this is for socket message to receiver end

							//saveColorStatus
							requestStatusRepository.saveRequestStatus(
									"0", bean.getRequestNo());
							//saving rideTracking
							try {	
								String[] requestNO = bean.getRequestNo().split("_");
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setIsDriver("2");
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setStatus(StatusEnum.MatchColor.getValue()+"");
									rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.Color_Match.getValue()+"");
									rideTrackingBean.setIsComplete("0");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}
			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		} else {
			throw new GenericException("Ride Request failded due to some error");
		}
	}

	@Override
	public void reachedStartDestination(SafeHerDecorator decorator)
			throws GenericException, IOException {
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		signUpConverter.validateReachedRideStart(decorator);
		if (decorator.getErrors().size() == 0) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				if (preBean.getIsDriver().equals("0")) {
					preRideEntity.setPassengerArrivalTime(DateUtil.now());
					preRideEntity.setStatus(appUserDao.get(StatusEntity.class,
							12));
				} else if (preBean.getIsDriver().equals("1")) {
					preRideEntity.setDriverArrivalTime(DateUtil.now());
					preRideEntity.setStatus(appUserDao.get(StatusEntity.class,
							11));
				}
				if (appUserDao.update(preRideEntity)) {
					if (preBean.getIsDriver().equals("0")) {
						sendNotification(preBean.getPassengerappUserId(),
								preBean.getIsDriver(),
								PushNotificationStatus.StartDestinationReached
										.toString(),
								"Passenger Has Reached to Destination");
						decorator
								.setResponseMessage("Passenger Has Reached to Destination");
					} else if (preBean.getIsDriver().equals("1")) {
						sendNotification(preBean.getDriverappUserId(),
								preBean.getIsDriver(),
								PushNotificationStatus.StartDestinationReached
										.toString(),
								"Driver Has Reached to Destination");
						decorator
								.setResponseMessage("Driver Has Reached to Destination");
					}
				} else {
					throw new GenericException(
							"Your Info Make Some Problem Against Saving");
				}
			}
		}
	}

	private void sendNotification(String appUserId, String isDriver,
			String Notifcation, String message) throws IOException,
			GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = null;
		AppUserEntity appUserEntity = null;
		if (isDriver.equals("0")) {
			appUserEntity = appUserDao.findById(AppUserEntity.class,
					new Integer(appUserId));
			loginEntity = appUserDao.findByOject(UserLoginEntity.class,
					"appUser", appUserEntity);
			if (loginEntity.getOsType() != null
					&& loginEntity.getOsType().equals("1")) {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), "", "", "", "", Notifcation,
						message);
			} else {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), "", "", "", "", Notifcation,
						message, loginEntity.getFcmToken());
			}

		} else if (isDriver.equals("1")) {
			appUserEntity = appUserDao.findById(AppUserEntity.class,
					new Integer(appUserId));
			loginEntity = appUserDao.findByOject(UserLoginEntity.class,
					"appUser", appUserEntity);
			if (loginEntity.getOsType() != null
					&& loginEntity.getOsType().equals("1")) {
				pushAndriod.pushAndriodDriverNotification(
						loginEntity.getKeyToken(), "", "", "", "", Notifcation,
						message);
			} else {
				pushIOS.pushIOSDriver(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), "", "", "", "", Notifcation,
						message, loginEntity.getFcmToken());
			}
		}
	}

	private void sendNotificationColorVerification(String appUserId, String isDriver,
			String Notifcation, String message, SafeHerDecorator decorator) throws IOException,
			GenericException {
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		UserLoginEntity loginEntity = null;
		AppUserEntity appUserEntity = null;
		if (isDriver.equals("0")) {
			appUserEntity = appUserDao.findById(AppUserEntity.class,
					new Integer(appUserId));
			loginEntity = appUserDao.findByOject(UserLoginEntity.class,
					"appUser", appUserEntity);
			if (loginEntity.getOsType() != null
					&& loginEntity.getOsType().equals("1")) {
				andriodPush.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), "", "", "", "", Notifcation,
						message);
			} else {
				iosPush.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), "", "", "", "", Notifcation,
						message, loginEntity.getFcmToken());
			}

		} else if (isDriver.equals("1")) {
			appUserEntity = appUserDao.findById(AppUserEntity.class,
					new Integer(appUserId));
			loginEntity = appUserDao.findByOject(UserLoginEntity.class,
					"appUser", appUserEntity);
			if (loginEntity.getOsType() != null
					&& loginEntity.getOsType().equals("1")) {
				andriodPush.pushAndriodDriverNotification(
						loginEntity.getKeyToken(), "", "", "", "", Notifcation,
						message);
			} else {
				iosPush.pushIOSDriver(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), "", "", "", "", Notifcation,
						message, loginEntity.getFcmToken());
			}
		}
		preBean.setNotificationType(PushNotificationStatus.valueOf(Notifcation).getValue());
		preBean.setNotificationMessage(message);
		decorator.setDataBean(preBean);
	}

	@Override
	public void rideAction(SafeHerDecorator decorator) throws GenericException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (bean.getIsDriver() != null && bean.getIsDriver().equals("1")) {
			bean.setAppUserId(bean.getPassengerId());
		} else if (bean.getIsDriver() != null && bean.getIsDriver().equals("0")) {
			bean.setAppUserId(bean.getDriverId());
		}
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {

			// multiple ride finalize records are saving against same
			// rideReqResIds for the time being we are fetching list and getting
			// 0 index of list
			// RideFinalizeEntity finalizeEntity = appUserDao
			// .findRideFinalize(new Integer(bean.getRideReqResId()));

			// ////////////////////////this portio/////////////////////////////
			RideFinalizeEntity finalizeEntity = new RideFinalizeEntity();
			List<RideFinalizeEntity> list = appUserDao
					.findRideFinalizeList(new Integer(bean.getRideReqResId()));

			if (list != null && list.size() > 0) {
				finalizeEntity = list.get(0);
			}
			// ////////////////////////this portion/////////////////////////////

			if (finalizeEntity != null) {
				String pushNotificationStatus = "";
				RideRequestResponseEntity requestResponseEntity = finalizeEntity
						.getRideRequestResponse();
				if (requestResponseEntity != null) {
					AppUserEntity userEntity = new AppUserEntity();
					// userEntity.setAppUserId(new
					// Integer(bean.getAppUserId()));
					userEntity = appUserDao.findById(AppUserEntity.class,
							new Integer(bean.getAppUserId()));
					if (bean.getRideAction() != null
							&& bean.getRideAction().equals("BL")) {
						// work for billing in future
						pushNotificationStatus = PushNotificationStatus.PreRideBillingRequest
								.toString();
					}
//					ReasonEntity reasonEntity = new ReasonEntity();
//					reasonEntity.setReasonId(new Integer(bean.getReasonid()));
//					finalizeEntity.setCommentsCancle(bean.getCommentReason());
//					finalizeEntity.setReason(reasonEntity);
//					appUserDao.saveOrUpdate(finalizeEntity);

					UserLoginEntity loginEntity = appUserDao.findByOject(
							UserLoginEntity.class, "appUser", userEntity);
					if (loginEntity != null) {
						// userEntity = loginEntity.getAppUser();
						if (userEntity != null) {
							if (loginEntity.getKeyToken() == null) {
								throw new GenericException(
										"Ride Request failded due to some error");
							} else {
								try {
									if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("1")) {
										if (userEntity.getIsDriver() != null
												&& userEntity.getIsDriver()
														.equals("0")) {
											pushAndriod
													.pushAndriodPassengerNotification(
															loginEntity
																	.getKeyToken(),
															"Passenger is late please start billing",
															"",
															"",
															"",
															pushNotificationStatus, "Passenger is late please start billing");

										} else {
											pushAndriod
													.pushAndriodDriverNotification(
															loginEntity
																	.getKeyToken(),
															"Passenger is late please start billing",
															"",
															"",
															"",
															pushNotificationStatus,"Passenger is late please start billing");
										}
										decorator
												.setResponseMessage("Request has been sent");
										decorator
												.setReturnCode(ReturnStatusEnum.SUCCESFUL
														.toString());
									} else if (loginEntity.getOsType() != null
											&& loginEntity.getOsType()
													.equals("0")) {
										if (loginEntity.getIsDev() != null) {
											if (userEntity.getIsDriver() != null
													&& userEntity
															.getIsDriver()
															.equals("0")) {
												pushIOS.pushIOSPassenger(
														loginEntity
																.getKeyToken(),
														loginEntity
																.getIsDev(),
														"Passenger is late please start billing",
														"",
														"",
														"",
														pushNotificationStatus,"Passenger is late please start billing",
														loginEntity.getFcmToken());

											} else {
												pushIOS.pushIOSDriver(
														loginEntity
																.getKeyToken(),
														loginEntity
																.getIsDev(),
														"Passenger is late please start billing",
														"",
														"",
														"",
														pushNotificationStatus, "Passenger is late please start billing",
														loginEntity.getFcmToken());
											}
											decorator
													.setResponseMessage("Request has been sent");
											decorator
													.setReturnCode(ReturnStatusEnum.SUCCESFUL
															.toString());
										} else {
											throw new GenericException(
													"Ride Request failded due to some error");
										}
									} else {
										throw new GenericException(
												"Ride Request failded due to some error");
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
						}
					} else {
						throw new GenericException(
								"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"Ride Request failded due to some error");
				}

			} else {
				throw new GenericException(
						"Ride cancel request failded due to some error");
			}

		} else {
			throw new GenericException("User don't exists");
		}
	}

	@Override
	public void cancelRequestByDriver(SafeHerDecorator decorator)
			throws GenericException {
	//	PushNotificationsAndriod pushNotificationsAndriod = new PushNotificationsAndriod();
		PushAndriod pushNotificationsAndriod=new PushAndriod();
	//	PushNotificationsIOS pushNotificationsIOS = new PushNotificationsIOS();
		PushIOS pushNotificationsIOS=new PushIOS();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		signUpConverter.validateCancelRequest(decorator);
		if (decorator.getErrors().size() == 0) {
			// RideRequestResponseEntity requestResponseEntity = appUserDao
			// .findById(RideRequestResponseEntity.class,
			// new Integer(bean.getRideReqResId()));

			// now we are getting rideRequestResponse from driverid and
			// requestNo so that,s why we change method calling
			// List<Object[]> object = appUserDao.findRideRequest(new
			// Integer(bean
			// .getAppUserId()));
			List<Object[]> object = appUserDao.findRideRequestResponse(
					new Integer(bean.getAppUserId()), bean.getRequestNo());
			if (object != null && object.size() > 0) {
				RideRequestResponseEntity requestResponseEntity = (RideRequestResponseEntity) object
						.get(0)[0];
				if (requestResponseEntity != null) {
				/*	ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
					ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
					StatusEntity statusResponse = new StatusEntity();
					statusResponse.setStatusId(StatusEnum.Rejected.getValue());
					requestResponseEntity
							.setStatusByStatusResponse(statusResponse);
					appUserDao.saveOrUpdate(requestResponseEntity);
					if (StringUtil.isNotEmpty(bean.getIsDriver())
							&& bean.getIsDriver().equals("1")) {
						AppUserEntity userEntity = appUserDao.findById(
								AppUserEntity.class,
								new Integer(bean.getAppUserId()));
						if (userEntity != null) {
							if (userEntity != null
									&& userEntity.getIsDriver()
											.equalsIgnoreCase("1")) {
								/*activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", userEntity);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity
											.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(userEntity.getAppUserId());
								
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity
											.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
									decorator.setResponseMessage("Successfully Cancel the Request Against "+bean.getRequestNo()  +" Go For Your Next Ride");
									decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
						
								}
							} else {
								throw new GenericException(
										"This Ride Request For Drivers!");
							}
//							String result = getKeyToken(userEntity);
//							if (result.equalsIgnoreCase("failed")) {
//								throw new GenericException(
//										"Key Token is Missing Against Passenger");
//							} else {
		//						String IOSMode = getIOSMode(userEntity);
//								if (IOSMode.equalsIgnoreCase("null")) {
//									throw new GenericException(
//											"IOS Mode is Missing Against Passenger");
//								} else {
//								try {
////										UserLoginEntity loginEntity = appUserDao
////												.findByOject(
////														UserLoginEntity.class,
////														"appUser", userEntity);
////										if (loginEntity.getOsType() != null
////												&& loginEntity.getOsType()
////														.equals("1")) {
////											pushNotificationsAndriod
////													.pushAndriodPassengerNotification(
////															result,
////															"",
////															"",
////															"",
////															bean.getRequestNo(),
////															PushNotificationStatus.CancelRideFromDriver.toString(), 
////															"Driver has cancel the Request");
////											decorator
////													.setResponseMessage("Request has been sent");
////											decorator
////													.setReturnCode(ReturnStatusEnum.SUCCESFUL
////															.toString());
////										} else if (loginEntity.getOsType() != null
////												&& loginEntity.getOsType()
////														.equals("0")) {
////											pushNotificationsIOS
////													.pushIOSPassenger(
////															result,
////															IOSMode,
////															"",
////															"",
////															"",
////															bean.getRequestNo(),
////															PushNotificationStatus.CancelRideFromDriver
////																	.toString(),
////															"Driver has cancel the Request");
////
////											decorator
////													.setResponseMessage("Request has been sent");
////											decorator
////													.setReturnCode(ReturnStatusEnum.SUCCESFUL
////															.toString());
////										}
//									}
//								catch (IOException e) {
//										e.printStackTrace();
//									}
//									// PushNotificationsIOS.pushIOS(result,
//									// "Passenger", "prod");
//								}
								// delete from radis
								deleteRideFromRedis(bean.getRequestNo());
								
								//saving rideTracking
								try {		
									String[] requestNO = bean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										RideTrackingBean rideTrackingBean = new RideTrackingBean();
										rideTrackingBean.setIsDriver("2");
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Request_By_Driver.getValue()+"");
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								//async saving driverDrivingDetailIntoMongo
								DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
								detailBean.setAppUserId(userEntity.getAppUserId()+"");
								detailBean.setTotalCancelPreRides(1.0);
								asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
								
								//async saving driverTrackIntoDrivingDetailIntoMongo
								asyncServiceImpl.saveLocationTrackIntoDrivingDetail(userEntity.getAppUserId(), "2");
								
								decorator.setResponseMessage("Successfully Canel the Request Against "+bean.getRequestNo());
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"This Cancel Request Only For Driver");
				}

			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		} else {
			throw new GenericException(
					"Please Fulfill the Requriment of Cancel Request!");
		}

	}
	
	
	@Override
	public void cancelRequestByDriverV2(SafeHerDecorator decorator)
			throws GenericException {
	//	PushNotificationsAndriod pushNotificationsAndriod = new PushNotificationsAndriod();
		PushAndriod pushNotificationsAndriod=new PushAndriod();
	//	PushNotificationsIOS pushNotificationsIOS = new PushNotificationsIOS();
		PushIOS pushNotificationsIOS=new PushIOS();
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		signUpConverter.validateCancelRequest(decorator);
		if (decorator.getErrors().size() == 0) {
			// RideRequestResponseEntity requestResponseEntity = appUserDao
			// .findById(RideRequestResponseEntity.class,
			// new Integer(bean.getRideReqResId()));

			// now we are getting rideRequestResponse from driverid and
			// requestNo so that,s why we change method calling
			// List<Object[]> object = appUserDao.findRideRequest(new
			// Integer(bean
			// .getAppUserId()));
			List<Object[]> object = appUserDao.findRideRequestResponse(
					new Integer(bean.getAppUserId()), bean.getRequestNo());
			if (object != null && object.size() > 0) {
				RideRequestResponseEntity requestResponseEntity = (RideRequestResponseEntity) object
						.get(0)[0];
				if (requestResponseEntity != null) {
					/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
					*/
					ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
					StatusEntity statusResponse = new StatusEntity();
					statusResponse.setStatusId(StatusEnum.Rejected.getValue());
					requestResponseEntity
							.setStatusByStatusResponse(statusResponse);
					appUserDao.saveOrUpdate(requestResponseEntity);
					if (StringUtil.isNotEmpty(bean.getIsDriver())
							&& bean.getIsDriver().equals("1")) {
						AppUserEntity userEntity = appUserDao.findById(
								AppUserEntity.class,
								new Integer(bean.getAppUserId()));
						if (userEntity != null) {
							if (userEntity != null
									&& userEntity.getIsDriver()
											.equalsIgnoreCase("1")) {
								/*activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", userEntity);*/
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(userEntity.getAppUserId());
								
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity
											.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao
											.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
									decorator.setResponseMessage("Successfully Canel the Request Against "+bean.getRequestNo()  +" Go For Your Next Ride");
									decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
						
								}
							} else {
								throw new GenericException(
										"This Ride Request For Drivers!");
							}
//							String result = getKeyToken(userEntity);
//							if (result.equalsIgnoreCase("failed")) {
//								throw new GenericException(
//										"Key Token is Missing Against Passenger");
//							} else {
		//						String IOSMode = getIOSMode(userEntity);
//								if (IOSMode.equalsIgnoreCase("null")) {
//									throw new GenericException(
//											"IOS Mode is Missing Against Passenger");
//								} else {
//								try {
////										UserLoginEntity loginEntity = appUserDao
////												.findByOject(
////														UserLoginEntity.class,
////														"appUser", userEntity);
////										if (loginEntity.getOsType() != null
////												&& loginEntity.getOsType()
////														.equals("1")) {
////											pushNotificationsAndriod
////													.pushAndriodPassengerNotification(
////															result,
////															"",
////															"",
////															"",
////															bean.getRequestNo(),
////															PushNotificationStatus.CancelRideFromDriver.toString(), 
////															"Driver has cancel the Request");
////											decorator
////													.setResponseMessage("Request has been sent");
////											decorator
////													.setReturnCode(ReturnStatusEnum.SUCCESFUL
////															.toString());
////										} else if (loginEntity.getOsType() != null
////												&& loginEntity.getOsType()
////														.equals("0")) {
////											pushNotificationsIOS
////													.pushIOSPassenger(
////															result,
////															IOSMode,
////															"",
////															"",
////															"",
////															bean.getRequestNo(),
////															PushNotificationStatus.CancelRideFromDriver
////																	.toString(),
////															"Driver has cancel the Request");
////
////											decorator
////													.setResponseMessage("Request has been sent");
////											decorator
////													.setReturnCode(ReturnStatusEnum.SUCCESFUL
////															.toString());
////										}
//									}
//								catch (IOException e) {
//										e.printStackTrace();
//									}
//									// PushNotificationsIOS.pushIOS(result,
//									// "Passenger", "prod");
//								}
								// delete from radis
								deleteRideFromRedis(bean.getRequestNo());
								
								//saving rideTracking
								try {		
									String[] requestNO = bean.getRequestNo().split("_");
									if(requestNO != null && requestNO.length > 0){
										RideTrackingBean rideTrackingBean = new RideTrackingBean();
										rideTrackingBean.setRequestNo(requestNO[0]);
										rideTrackingBean.setStatus(StatusEnum.Rejected.getValue()+"");
										rideTrackingBean.setState(ProcessStateAndActionEnum.PRE_RIDE.getValue()+"");
										rideTrackingBean.setAction(ProcessStateAndActionEnum.Cancel_Request_By_Driver.getValue()+"");
										rideTrackingBean.setIsComplete("0");
										asyncServiceImpl.saveRideTracking(rideTrackingBean);	
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								//async saving driverDrivingDetailIntoMongo
								DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
								detailBean.setAppUserId(userEntity.getAppUserId()+"");
								detailBean.setTotalCancelPreRides(1.0);
								asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
								
								//async saving driverTrackIntoDrivingDetailIntoMongo
								asyncServiceImpl.saveLocationTrackIntoDrivingDetail(userEntity.getAppUserId(), "2");
								
								decorator.setResponseMessage("Successfully Canel the Request Against "+bean.getRequestNo());
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
							}
						} else {
							throw new GenericException(
									"Ride Request failded due to some error");
					}
				} else {
					throw new GenericException(
							"This Cancel Request Only For Driver");
				}

			} else {
				throw new GenericException(
						"Ride Request failded due to some error");
			}
		} else {
			throw new GenericException(
					"Please Fulfill the Requriment of Cancel Request!");
		}

	}

	@Override
	public void checkForRideNotification(SafeHerDecorator decorator)
			throws GenericException {
		RideQuickInfoBean bean = (RideQuickInfoBean)
				decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getDriverId())){
			
			Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(DateUtil.getCurrentTimestamp().getTime());
	        cal.add(Calendar.SECOND, -13);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			
			RideQuickInfoEntity rideQuickInfoEntity = appUserDao.getRideQuickInfo(
					bean.getDriverId(), DateUtil.getCurrentTimestamp(), later);
			if(rideQuickInfoEntity != null){
				signUpConverter.convertEntityToRideQuickBean(
						rideQuickInfoEntity, bean);
				bean.setNotificationExist("1");
				decorator.setResponseMessage("Successfully found notification");
			}else{
				bean.setNotificationExist("0");
				decorator.setResponseMessage("No any notification found");
			}
			decorator.getResponseMap().put("data", bean);
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}else{
			throw new GenericException("Please provide driver id");
		}
		
	}

	@Override
	public void getRideTracking(SafeHerDecorator decorator)
			throws GenericException {
		RideBean bean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in getRideTracking  with AppUserBean "
				+ bean + "******");
		try {
			safeHerCommonValidator.validateRideTrackingJson(decorator);
			if (decorator.getErrors().size() == 0) {
				String passengerOrDriver = "";
				String request = null;
				if(bean.getIsDriver().equals("1")){
					passengerOrDriver = "driver";
				}else{
					passengerOrDriver = "passenger";
				}
				if(StringUtil.isNotEmpty(bean.getRequestNo())){
					String[] requestNO = bean.getRequestNo().split("_");
					request = requestNO[0];
				}
				RideProcessTrackingDetailEntity detailEntity = appUserDao.
						getrideTracking(new Integer(bean.getAppUserId()), passengerOrDriver, request);
				if(detailEntity != null){
					RideQuickInfoBean rideQuickInfoBean = new RideQuickInfoBean();
					String result = getRideInfo(rideQuickInfoBean, detailEntity);
					if(result.length() > 0){
//						throw new GenericException(result);
						decorator.setResponseMessage(result);
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
					}
					decorator.getResponseMap().put("data", rideQuickInfoBean);
					decorator.setResponseMessage("Ride found successfully");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
				}else{
//					throw new GenericException("No any ride found");
					decorator.setResponseMessage("No any ride found");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
				}
			} else {
				throw new GenericException("Please provide following information");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}
	
	public String getRideInfo(RideQuickInfoBean bean, 
			RideProcessTrackingDetailEntity detailEntity){
		String result = "";
		if(detailEntity.getProcessAction() != null && 
				detailEntity.getRideProcessTracking() != null){
			
			RideQuickInfoEntity rideQuickInfoEntity = appUserDao.getRideQuickInfoFromRequestNo(
					detailEntity.getRideProcessTracking().getRideProcessTrackingId());
			
			if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Search_Ride_By_Passenger.getValue())){
				if(rideQuickInfoEntity != null){
					signUpConverter.convertEntityToRideQuickBean(
							rideQuickInfoEntity, bean);
					bean.setIsCancel("0");
				}		
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Accept_And_Navigate_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						AcceptRideFromDriver.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Accept_Request_By_Passenger.getValue())){
				
				if(detailEntity.getRideProcessTracking().getDriver() != null){
					bean.setDriverAppId(detailEntity.getRideProcessTracking()
							.getDriver().getAppUserId()+"");
					bean.setIsCancel("0");	
				}
				if(detailEntity.getRideProcessTracking().getPassenger() != null){
					bean.setPassengerAppId(detailEntity.getRideProcessTracking()
							.getPassenger().getAppUserId()+"");	
				}
				bean.setNotificationType(PushNotificationStatus.
						AcceptRideFromPassenger.getValue());
				bean.setPreRideId(appUserDao.getPreRide(
						detailEntity.getRideProcessTracking().getRequestNo()));
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Cancel_Request_By_Passenger.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						CancelRideFromPassenger.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("1");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Cancel_Request_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						CancelRideFromDriver.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("1");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Start_Pre_Ride_By_Driver.getValue())){
				
				bean.setNotificationType(PushNotificationStatus.
						PreRideStart.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Color_Match.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						ColorVerification.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Color_Matched_By_Driver.getValue())){
				
				bean.setNotificationType(PushNotificationStatus.
						ColorMatchedByDriver.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				bean.setVerificationFlag("false");
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Color_Matched_By_Passenger.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						ColorMatchedByPassenger.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				bean.setVerificationFlag("false");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						CancelRideOnColorMatchByDriver.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
				}
				bean.setIsCancel("1");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Cancel_Ride_On_Color_Match_By_Passenger.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						CancelRideOnColorMatchByPassenger.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
				}
				bean.setIsCancel("1");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Color_Matched.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						SuccessfullVerification.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				bean.setVerificationFlag("true");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Canel_Late_Reason_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						PreRideCancelRequest.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
				}
				bean.setIsCancel("1");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Canel_Late_Reason_By_Passenger.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						PreRideCancelRequest.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
				}
				bean.setIsCancel("1");
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.Start_Ride_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						startRide.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.End_Ride_By_Driver.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						InvoiceRequest.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else if(detailEntity.getProcessAction().getProcessActionId().equals(
					ProcessStateAndActionEnum.End_Ride_By_Passenger.getValue())){

				bean.setNotificationType(PushNotificationStatus.
						PassengerMidEndRide.getValue());
				if(rideQuickInfoEntity != null){
					bean.setRequestNo(rideQuickInfoEntity.getRequestNo());
					bean.setIsCancel("0");
				}
				
			}else{
				result = "No any ride found";
			}
		}else{
			result = "No any ride found";
		}
		
		return result;
	}
}