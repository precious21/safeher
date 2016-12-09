package com.tgi.safeher.jms;

import java.util.Date;

import javax.jms.JMSException;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.DistanceAPIBeanV2;
import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.common.enumeration.DriverDrivingDetailInsertionTypeEnum;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.dao.GoogleMapDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.RideQuickInfoEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.RideSearchResultEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.jms.model.Notification;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.repository.RideRequestResponseRepository;
import com.tgi.safeher.repository.RideSearchResultDetailRepository;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

/**
 * This method is receiving asynchronous messages on ride.request.queue
 * 
 * @author Awais Haider
 * 
 */
@Component("asyncReceiver")
@Transactional
public class RequestRideMessageReceiver {

	private static final Logger logger = Logger
			.getLogger(RequestRideMessageReceiver.class);
	
	@Autowired
	private GoogleMapDao mapDao;
	
	@Autowired
	private RideDao rideDao;
	
	@Autowired
	private RideRequestResponseRepository rideRequestResponseRepository;
	
	@Autowired
	private RideSearchResultDetailRepository rideSearchResultDetailRepository;
	
	@Autowired
	private RideRequestRepository rideRequestRepository;
	
	@Autowired
	private IRideRequestResponseService iRideRequestResponseService;

	@Autowired
	private NotificationRegistry registry;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;

	/**
	 * Receive Asynchronous message on ride.request.queue
	 * 
	 * @param notification
	 * @throws JMSException
	 * @throws InterruptedException
	 * @throws GenericException
	 */
	@Async
	public void receiveMessage(Notification notification) throws JMSException,
			InterruptedException, GenericException {

		DistanceAPIBeanV2 distanceBean = notification.getDataBean();
		logger.info("**************Entering in async receiveMessage with DistanceAPIBeanV2 "+distanceBean+" *************");
		RideSearchResultEntity rideSearchRes = notification.getEntity();
		RideQuickInfoEntity rideQuickInfoEntity = new RideQuickInfoEntity();
		UserLoginEntity userLoginEntity = notification.getEntityAppUser();
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		
		UserLoginEntity userDriver = rideDao.findByIdParam2(distanceBean
				.getAppUserId());
		if (userDriver != null) {
			// TODO: Manage Device Token
			if (userDriver.getAppUser() != null) {
				if (StringUtil.isNotEmpty(userDriver.getKeyToken())) {

					// saveRideSearchResultDetail into redis
					distanceBean.setRequestNo(distanceBean.getRequestNo()
							+"_"+userDriver.getAppUser().getAppUserId());
					
					RideSearchResultDetailEntity rideSearchResultDetailEntity = new RideSearchResultDetailEntity();
					rideSearchResultDetailEntity.setDriverLat(distanceBean
							.getLatDestinations() + "");
					rideSearchResultDetailEntity.setDriverLong(distanceBean
							.getLngDestinations() + "");
					rideSearchResultDetailEntity.setAppUser(userDriver
							.getAppUser());
					rideSearchResultDetailEntity
							.setRideSearchResult(rideSearchRes);
					rideSearchResultDetailEntity.setRequestNo(distanceBean
							.getRequestNo());

//					rideSearchResultDetailRepository
//							.saveRideSearchResultDetailEntity(rideSearchResultDetailEntity);

					// saveRideReqResponse into redis
					RideRequestResponseEntity rideRequestResponseEntity = new RideRequestResponseEntity();
					StatusEntity statusEntity = new StatusEntity();
					StatusEntity statusResponse = new StatusEntity();
					statusEntity.setStatusId(new Integer(StatusEnum.Intial
							.getValue()));
					rideRequestResponseEntity.setAppUser(userLoginEntity
							.getAppUser());
					rideRequestResponseEntity
							.setRideSearchResultDetail(rideSearchResultDetailEntity);
					rideRequestResponseEntity
							.setStatusByStatusFinal(statusEntity);
					//this is for radis start
					rideRequestResponseEntity.setRequestNo(
							distanceBean.getRequestNo());
					rideRequestResponseEntity.setDriverId(
							userDriver.getAppUser().getAppUserId()+"");
					
					rideQuickInfoEntity.setCharityId(distanceBean.getCharityId());
					rideQuickInfoEntity.setPassengerAppId(
							userLoginEntity.getAppUser().getAppUserId() + "");
					rideQuickInfoEntity.setDriverAppId(
							userDriver.getAppUser().getAppUserId()+"");
					rideQuickInfoEntity.setCharityName(distanceBean.getCharityName());
					rideQuickInfoEntity.setRequestNo(distanceBean.getRequestNo());
					rideQuickInfoEntity.setNotificationType(
							PushNotificationStatus.searchRideCriteria.toString());
					rideQuickInfoEntity.setNotificationMessage(
							"Passenger Has Searched Request In Your Area!");
					rideQuickInfoEntity.setSendingTime(DateUtil.getCurrentTimestamp());
					
					//sendNotification
					if (StringUtil.isNotEmpty(userDriver.getOsType())
							&& userDriver.getOsType().equals("1")) {
						try {
							// logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+
							// " Driver App User Id"+distanceBean.getAppUserId());
							pushAndriod
									.pushAndriodDriverNotification(
											userDriver.getKeyToken(),
											userLoginEntity.getAppUser()
													.getAppUserId() + "",
											distanceBean.getCharityName(),
											distanceBean.getRequestNo(),
											distanceBean.getCharityId(),
											PushNotificationStatus.searchRideCriteria
													.toString(),
											"Passenger Has Searched Request In Your Area!");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logger.info("**************Exiting from async receiveMessage with exception "+e.getMessage()+" *********"); 
							throw new GenericRuntimeException(
									"Error in GCM Google APi");
						}
					} else {
						// logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+
						// " Driver App User Id"+distanceBean.getAppUserId());
						pushIOS.pushIOSDriver(userDriver.getKeyToken(),
								userDriver.getIsDev(), 
								userLoginEntity.getAppUser()
								.getAppUserId() + "",
								distanceBean.getCharityName(),
								distanceBean.getRequestNo(),
								distanceBean.getCharityId(),
								PushNotificationStatus.searchRideCriteria
										.toString(),
								"Passenger Has Searched Request In Your Area!", userDriver.getFcmToken());
					}
					//save into redis
					rideRequestRepository.saveRideRequestResponseResultDetail(
							distanceBean.getRequestNo(), rideRequestResponseEntity);
					//this is for radis end
					
					//async save into db
//					asyncServiceImpl.saveRideRequestResponseEntity(rideRequestResponseEntity);
					asyncServiceImpl.saveRideSearchResultDetailEntityAndRideReqResEntity(
							rideSearchResultDetailEntity, rideRequestResponseEntity);
					asyncServiceImpl.saveRideQuickInfo(rideQuickInfoEntity);

					//async saving driverDrivingDetailIntoMongo
					DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
					detailBean.setAppUserId(userDriver.getAppUser().getAppUserId()+"");
					detailBean.setTotalRequests(1.0);
					detailBean.setDate(DateUtil.getMongoDbDate(new Date()));
					asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);
				}
			}
		}

	}

}
