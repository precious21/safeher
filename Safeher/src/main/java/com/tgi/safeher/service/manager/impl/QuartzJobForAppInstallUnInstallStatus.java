package com.tgi.safeher.service.manager.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.repo.ActiveDriverCustomRepository;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.IPromReffService;
import com.tgi.safeher.service.IRideService;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.service.impl.RideRequestResponseService;
import com.tgi.safeher.utils.DateUtil;

@Service("quartzJobForAppInstallUnInstallStatus")
public class QuartzJobForAppInstallUnInstallStatus {
	
	@Autowired
	private IDriverService iDriverService;
	
	@Autowired
	private IPromReffService promotionService;
	
	@Autowired
	private IRideService rideService;	
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private ActiveDriverCustomRepository activeDriverCustomRepository;
	
	private static final Logger logger = Logger.getLogger(RideRequestResponseService.class);
	
	@SuppressWarnings("unused")
	private void checkForAppInstallUnInstallStatus() {
		logger.info("Starting job to check app is install or uninstall");
		try {	
			/*iDriverService.deleteUnneccesaryActiveDriver();*/
			iDriverService.deleteUnneccesaryActiveDriverMongo();
		} catch (GenericException e) {
			e.printStackTrace();
			logger.info("******Exiting from job due to this error: "+e.getMessage()+"******");
		}
		logger.info("Ending job to check app is install or uninstall");
	}
	

	@SuppressWarnings("unused")
	private void promotionExpiryInActive(){
		logger.info("Starting job to Promotion make in Active ");
		try {	
			promotionService.inActivePromotions();
		} catch (GenericException e) {
			e.printStackTrace();
			logger.info("******Exiting from job due to this error: "+e.getMessage()+"******");
		}
		logger.info("Ending job to Promotion make in Active");
	}
	
	@SuppressWarnings("unused")
	private void giftRidesInActiveMethod(){
		logger.info("Starting job to GiftRides make in Active ");
		try {	
			rideService.inActiveGiftRides();
		} catch (GenericException e) {
			e.printStackTrace();
			logger.info("******Exiting from job due to this error: "+e.getMessage()+"******");
		}
		logger.info("Ending job to GiftRides make in Active");
	}
	
	@SuppressWarnings("unused")
	private void dumpDataIntoMongoForDriverOnlineTrackTime(){
		logger.info("Starting job to dumpDataIntoMongoForDriverOnlineTrackTime");	
		//async saving saveLocationTrackIntoDrivingDetailFromQuartz
		Date date = new Date();
		List<ActiveDriverLocationMongoEntity> list = activeDriverCustomRepository.findAll();
		if(list != null & list.size() > 0){
			for(ActiveDriverLocationMongoEntity en : list){
				asyncServiceImpl.saveLocationTrackIntoDrivingDetailFromQuartz(
						en.getAppUserId(), DateUtil.getMongoDbDate(date));					
			}
		}
		logger.info("Ending job to dumpDataIntoMongoForDriverOnlineTrackTime");
	}
	
	@SuppressWarnings("unused")
	private void sendIncompleteDataToUsers(){
		logger.info("Starting job to sendIncompleteDataToUsers");	
		try {
			iDriverService.findIncompleteUsers();
		} catch (GenericException e) {
			logger.info("Ending job to sendIncompleteDataToUsers with exception: "+ e);
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void getIncompleteSignInEmailJobs(){
		logger.info("Starting job to SignIn InComplete Data Email Service ");
		try {	
			//iDriverService.inCompleteSignDataEmail();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("******Exiting from job due to this error: "+e.getMessage()+"******");
		}
		logger.info("Ending job to SignIn InComplete Data Email Service");
	}

	@SuppressWarnings("unused")
	private void saveDriverDrivingDetailIntoSql(){
		logger.info("Starting job to Driver Driving Detail ");
		try {	
			asyncServiceImpl.saveDriverDrivingDetailIntoSql();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("******Exiting from job due to this error: "+e.getMessage()+"******");
		}
		logger.info("Ending job to Driver Driving Detail");
	}
}
