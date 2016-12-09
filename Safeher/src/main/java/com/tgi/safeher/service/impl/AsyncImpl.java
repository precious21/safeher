package com.tgi.safeher.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.BrainTree.BrainTree;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideTrackingBean;
import com.tgi.safeher.common.constant.EmailConstant;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.PaymentModeEnum;
import com.tgi.safeher.common.enumeration.ProcessStateAndActionEnum;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.PaymentModeEntity;
import com.tgi.safeher.entity.PromotionInfoEntity;
import com.tgi.safeher.entity.PromotionCodesEntity;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideCharityEntity;
import com.tgi.safeher.entity.RideDetailEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RidePaymnetDistributionEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserPromotionUseEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.IAsync;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.IPromReffService;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@EnableAsync
@Transactional
@Scope("prototype")
public class AsyncImpl implements IAsync{

	private static final Logger logger = Logger.getLogger(RideService.class);
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private IAsyncEmailService iAsyncEmailService;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	@Autowired
	private PushIOS iosPush;
	
	@Autowired
	private PushAndriod andriodPush;

	@Autowired
	private IPromReffService iPromoReffService;
	
	@Override
	@Async
	public void endRideAsyncCall(RideBillEntity rideBillEntity, RideBean rideBean, 
			Double rideAmount, Double totalDiscount, CreditCardInfoBean creditCardInfoBean, 
			UserPromotionEntity userPromotionEntity, UserPromotionUseEntity userPromotionUseEntity, 
			RideDetailEntity rideDetail, RideEntity rideEntity){
		logger.info("******Entering in endRideAsyncCall async *******");
		String paymentDone = "";

		String message = BrainTree.transactionUsingCustomer(
				rideAmount+"", EncryptDecryptUtil.decrypt(creditCardInfoBean.getBtCustomer()));	
		if (!message.contains("Error")) {

			paymentDone = "1";	
			
			//send payment notification
			try {
				sendPaymentNotification(paymentDone, 
						rideDetail, rideBean.getAppUserByAppUserDriver(), rideBean.getAppUserByAppUserPassenger());
			} catch (GenericException e) {
				e.printStackTrace();
			}
			
			appUserDao.saveOrUpdate(rideDetail);
			if (appUserDao.update(rideEntity)) {

				rideBean.setTimeStampStartTime(rideEntity.getStartTime());
				rideBean.setTimeStampEndTime(rideEntity.getEndTime());
				
				//savingRideCharity
				if(StringUtil.isNotEmpty(rideBean.getCharityId())){
					CharitiesEntity charitiesEntity = new CharitiesEntity();
					charitiesEntity.setCharitiesId(new Integer(rideBean.getCharityId()));
					RideCharityEntity rideCharityEntity = new RideCharityEntity();
//					rideCharityEntity.setRideCharityId(userCharitiesEntity);
					rideCharityEntity.setRide(rideEntity);
					rideCharityEntity.setCharities(charitiesEntity);
					appUserDao.saveOrUpdate(rideCharityEntity);
				}

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

						userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
						userPromotionUseEntity.setRideBill(rideBillEntity);
						userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
						userPromotionUseEntity.setUseAmount(totalDiscount.intValue());

						userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
						userPromotionUseEntity.setRideBill(rideBillEntity);
						userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
						userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
						appUserDao.saveOrUpdate(userPromotionUseEntity);
					}
//						if(userPromotionEntity != null && 
//								userPromotionEntity.getPromotionInfo() != null && 
//									totalDiscount > 0){
//							userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
//							userPromotionUseEntity.setRideBill(rideBillEntity);
//							userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
//							userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
//							appUserDao.saveOrUpdate(userPromotionUseEntity);
//						}
					
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
							asyncServiceImpl.saveRideTracking(rideTrackingBean);	
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//saving userPromotionUse
//						if(userPromotionEntity != null && 
//								userPromotionEntity.getPromotionInfo() != null && 
//									totalDiscount > 0){
//							userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
//							userPromotionUseEntity.setRideBill(rideBillEntity);
//							userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
//							userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
//							appUserDao.saveOrUpdate(userPromotionUseEntity);
//						}
					
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
					asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);

					//async saving driverTrackIntoDrivingDetailIntoMongo
					asyncServiceImpl.saveLocationTrackIntoDrivingDetail(driver.getAppUserId(), "3");
					
					try {
						//send async invoice email
						Thread.sleep(120000);
						//fetching passenger rating to driver
						UserCommentEntity userCommentEntity = appUserDao.getPassengerRatingToDriver(
								new Integer(rideBean.getAppUserByAppUserPassenger()), rideBean.getRideNo());
						if(userCommentEntity != null){
							rideBean.setIsRatingDone("1");
						}else{
							rideBean.setIsRatingDone("0");
				}
						iAsyncEmailService.sendInvoiceEmail(rideBean);
						
					} catch (GenericException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
					
				}
			}
		}else{
			paymentDone = "0";
			//send payment notification
			try {
				sendPaymentNotification(paymentDone, 
						rideDetail, rideBean.getAppUserByAppUserDriver(), rideBean.getAppUserByAppUserPassenger());
			} catch (GenericException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("******Exiting in endRideAsyncCall async *******");
	}

	public void sendPaymentNotification(String paymentDone
			, RideDetailEntity rideDetailEntity, String driverId, String passengerId) throws GenericException {

		logger.info("******Entering in sendPaymentNotification For sending notification of payment ******");
		String sourceAddress=rideDetailEntity.getSourceAddress()!=null 
				? rideDetailEntity.getSourceAddress() :"";
		if(sourceAddress.length() > 100){
			sourceAddress=sourceAddress.substring(0, 100);
		}
		String destinationAddress=rideDetailEntity.getDestinationAddress()!=null
				? rideDetailEntity.getDestinationAddress() :"";
		if(destinationAddress.length() > 100){
			destinationAddress=destinationAddress.substring(0, 100);
		}
		
		String message = "";
		if(paymentDone.equals("1")){
			message = "Your payment against ride from: "+sourceAddress+" to: "+destinationAddress+" has been done";
		}else if(paymentDone.equals("0")){
			message = "Your payment against ride from: "+sourceAddress+" to: "+destinationAddress+" has not been done";
		}
		
		//sending notification to driver
		UserLoginEntity driver = (UserLoginEntity) appUserDao.findByIdParamCommon(
				"UserLoginEntity", "appUser.appUserId", new Integer(driverId));
		if (driver.getOsType() != null
				&& driver.getOsType().equals("1")) {
			try {
				andriodPush.pushAndriodPassengerNotification(
						driver.getKeyToken(), paymentDone, "", "", "",
						PushNotificationStatus.Payment.toString(),
						message);
			} catch (IOException e) {
				logger.info("******Exiting from sendPaymentNotification For Andriod with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				iosPush.pushIOSDriver(driver.getKeyToken(),
						driver.getIsDev(), paymentDone, "", "", "",
						PushNotificationStatus.Payment.toString(),
						message, driver.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendPaymentNotification For IOS  with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}
		
		//sending notification to passenger
		UserLoginEntity passenger = (UserLoginEntity) appUserDao.findByIdParamCommon(
				"UserLoginEntity", "appUser.appUserId", new Integer(passengerId));
		if (passenger.getOsType() != null
				&& passenger.getOsType().equals("1")) {
			try {
				andriodPush.pushAndriodPassengerNotification(
						passenger.getKeyToken(), paymentDone, "", "", "",
						PushNotificationStatus.Payment.toString(),
						message);
			} catch (IOException e) {
				logger.info("******Exiting from sendPaymentNotification For Andriod with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				iosPush.pushIOSPassenger(passenger.getKeyToken(),
						passenger.getIsDev(), paymentDone, "", "", "",
						PushNotificationStatus.Payment.toString(),
						message, passenger.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendPaymentNotification For IOS  with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}
	}

	@Override
	@Async
	public void generateAsyncReferalCode(SafeHerDecorator decorator) throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		PromotionInfoEntity infoEntity = appUserDao.findReferral(new Integer(2), new Integer(bean.getAppUserTypeId()));
		if (infoEntity == null) {
			iPromoReffService.safeHerApiPromReffCodeGeneration(decorator);
			iPromoReffService.generateReferralCode(decorator);
		} else {
			iPromoReffService.generateReferralCode(decorator);
		}
	}

}
