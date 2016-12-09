package com.tgi.safeher.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.PostRideEntity;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideDetailEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RidePaymnetDistributionEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.service.IRideHistoryService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;


@Service
@Transactional
@Scope("prototype")
public class RideHistoryService implements IRideHistoryService {

	private static final Logger logger = Logger.getLogger(RideHistoryService.class);
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private RideConverter rideConverter;
	
	@Autowired
	private RideDao rideDao;
	
	@Override
	public void getLatestShareSummary(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		List<RideBillEntity> rideBillList=null; 
		int counter=0;
		Double totalShare=0.0;
		Integer duration=0;
		rideConverter.vaildateLatestShare(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			if (appUser == null) {
				throw new GenericException(
						"Please Enter the valid Driver Information");
			} else {
				if (appUser.getIsDriver() == null) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				} else if (appUser.getIsDriver() != null
						&& appUser.getIsDriver().equals("0")) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				}
			}
			Date fromDate =getStartDate(DateUtil.now());
			Date toDate = getEndDate(DateUtil.now());
			
			System.out.println(fromDate);
			System.out.println(toDate);
			/////////////////////////
			rideBillList = appUserDao.calculateLastTripAmount(appUser,
					new java.sql.Timestamp(fromDate.getTime()), new java.sql.Timestamp(toDate.getTime()));
			if (rideBillList == null && CollectionUtil.isEmpty(rideBillList)) {
				rideBean.setLastDuration("0");
				rideBean.setLastShare("0.0");
			}
		
			for (RideBillEntity rideBillListObject : rideBillList) {
				Timestamp timeStamp = rideBillListObject
								.getRideInfoId().getEndTime();
				Date endDate = new Date(timeStamp.getTime());
				Date startDate = new Date(rideBillListObject
						.getRideInfoId().getStartTime().getTime());
				long diff = (endDate.getTime() - startDate.getTime()) / 1000;
				
				System.out.println(rideBillListObject.getTotalAmount());
				System.out.println(rideBillListObject.getTipAmount());
				System.out.println(diff);
				if (counter == 0) {
					rideBean.setLastDuration(Long.toString(diff));
					if (rideBillListObject.getTipAmount() != null
							&& rideBillListObject.getTipAmount() != 0) {
					Double lastSumShare=(rideBillListObject.getTotalAmount()+ rideBillListObject.getTipAmount());
						rideBean.setLastShare(lastSumShare.toString());
						totalShare += rideBillListObject.getTotalAmount()
								+ rideBillListObject.getTipAmount();
						duration=duration+Integer.valueOf(Long.toString(diff));
						++counter;
					} else {
						rideBean.setLastShare(rideBillListObject.getTotalAmount().toString());
						totalShare += rideBillListObject.getTotalAmount();
						duration=duration+Integer.valueOf(Long.toString(diff));
						++counter;
					}

				} else {
						++counter;
						
						if (rideBillListObject.getTipAmount() != null) {
						totalShare += rideBillListObject.getTotalAmount()
								+ rideBillListObject.getTipAmount();
						duration=duration+Integer.valueOf(Long.toString(diff));
						}else{
							totalShare += rideBillListObject.getTotalAmount();
							duration=duration+Integer.valueOf(Long.toString(diff));
						}
					
				}
			
			}
			rideBean.setTodayDuration(Integer.toString(duration));
			rideBean.setTodayShare(Double.toString(totalShare));
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			decorator.setResponseMessage("Today Driver Share");
			decorator.getResponseMap().put("data", rideBean);
		} else {
			throw new GenericException("Share Summary Requirement Error");
		}

	}

	@Override
	public void shortEarningHistory(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		rideConverter.vaildateShortEarning(decorator);
		List<RideBillEntity> rideBillList = null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		int counter = 0;
		Double totalShare = 0.0;
		Integer duration = 0;
		Double distance = 0.0;
		Double totalTipAmount = 0.0;
		Double driverEarning = 0.0;

		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			if (appUser == null) {
				throw new GenericException("Please Enter the valid Driver Id");
			} else {
				if (appUser.getIsDriver() == null) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				} else if (appUser.getIsDriver() != null
						&& appUser.getIsDriver().equals("0")) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				}
			}

			// ////

			if (StringUtil.isEmpty(rideBean.getStartTime())) {
				startDate = DateUtil.now();
			} else {
				startDate = DateUtil.parseTimestampFromFormats(rideBean
						.getStartTime());
			}

			if (StringUtil.isEmpty(rideBean.getEndTime())) {
				endDate = DateUtil.now();
			} else {
				endDate = DateUtil.parseTimestampFromFormats(rideBean
						.getEndTime());
			}
			Date fromDate =getStartDate(startDate);
			Date toDate = getEndDate(endDate);
						
			System.out.println(fromDate);
			System.out.println(toDate);
			// ///////////////////////
			rideBean.setStartTime(fromDate.toString());
			rideBean.setEndTime(toDate.toString());
			rideBean.setTotalRides(appUserDao.countShortEarningRides(appUser, new java.sql.Timestamp(
					fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime())).toString());
			rideBillList = appUserDao.calculateLastTripAmount(appUser,
					new java.sql.Timestamp(fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime()));
			for (RideBillEntity rideBillListObject : rideBillList) {
				Timestamp timeStamp = rideBillListObject.getRideInfoId()
						.getEndTime();
				Date rideEndDate = new Date(timeStamp.getTime());
				Date rideStartDate = new Date(rideBillListObject
						.getRideInfoId().getStartTime().getTime());
				long diff = (rideEndDate.getTime() - rideStartDate.getTime()) / 1000;
				System.out.println(rideBillListObject.getTotalAmount());
				System.out.println(rideBillListObject.getTipAmount());
				System.out.println(diff);
				if (counter == 0) {
					rideBean.setLastDuration(Long.toString(diff));
					if (rideBillListObject.getTipAmount() != null
							&& rideBillListObject.getTipAmount() != 0) {
						Double lastSumShare = (rideBillListObject
								.getTotalAmount() + rideBillListObject
								.getTipAmount());
						rideBean.setLastShare(lastSumShare.toString());
						totalShare += rideBillListObject.getTotalAmount();
						totalTipAmount += rideBillListObject.getTipAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
						++counter;
					} else {
						rideBean.setLastShare(rideBillListObject
								.getTotalAmount().toString());
						totalShare += rideBillListObject.getTotalAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
						++counter;
					}

				} else {
					++counter;

					if (rideBillListObject.getTipAmount() != null) {
						totalShare += rideBillListObject.getTotalAmount();
						totalTipAmount += rideBillListObject.getTipAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
					} else {
						totalShare += rideBillListObject.getTotalAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
					}

				}
				PostRideEntity postrideEntity = appUserDao.findByOjectForPost(
						PostRideEntity.class, "rideEntityId",
						rideBillListObject.getRideInfoId());
				if (postrideEntity != null) {
					distance += postrideEntity.getActualDistance();
				}
				RidePaymnetDistributionEntity distributionEntity = appUserDao.
						findByOjectByRideDistribution(RidePaymnetDistributionEntity.class, "rideBill", rideBillListObject);
				if (distributionEntity != null) {
					if(StringUtil.isNotEmpty(distributionEntity.getDriverAmount()+"")){
						driverEarning += distributionEntity.getDriverAmount();	
					}
				}

			}
			rideBean.setTotalRides(rideBillList.size()+"");
			rideBean.setDriverEarning(driverEarning+"");
			rideBean.setTotalDuration(duration.toString());
			rideBean.setRideAmount(totalShare.toString());
			rideBean.setTotalDistance(distance.toString());
			rideBean.setTotalTipAmount(totalTipAmount.toString());
			
		} else {
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			decorator.setResponseMessage("Fill Your Requriments");
			throw new GenericException("Short Summary Requirement Error");
		}
		
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		decorator.setResponseMessage("Short Earning Summary For Driver");
		decorator.getResponseMap().put("data", rideBean);


	}

	@Override
	public void getRecentRides(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("User Recent Ride  Fetching...." );
		RideBean rideBean = (RideBean) decorator.getDataBean();
		RideBean rideBeanListObj=null;
		AppUserEntity appUser  =null;
		List<RideBillEntity> rideBillList = null;
		List<RideBean> rideBeanList =new ArrayList<RideBean>();
		rideConverter.vaildateRides(decorator);
		///////////////////////////////////////
		Timestamp startDate = null;
		Timestamp endDate = null;
		/////////////////////////////////////
		if (decorator.getErrors().size() == 0) {
			
			if (StringUtil.isEmpty(rideBean.getStartTime())) {
				startDate = DateUtil.now();
			} else {
				startDate = DateUtil.parseTimestampFromFormats(rideBean
						.getStartTime());
			}

			if (StringUtil.isEmpty(rideBean.getEndTime())) {
				endDate = DateUtil.now();
			} else {
				endDate = DateUtil.parseTimestampFromFormats(rideBean
						.getEndTime());
			}
			Date fromDate = getStartDate(startDate);
			Date toDate = getEndDate(endDate);
			System.out.println(fromDate);
			System.out.println(toDate);
			/////////////////////////////////////
			if(rideBean.getAppUserByAppUserDriver()!=null){
				logger.info("Driver Recent Ride  Fetching...." );
				appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
				decorator.setResponseMessage(" Recent Rides For Driver");
			}else if(rideBean.getAppUserByAppUserPassenger()!=null){
				logger.info("Passenger Recent Ride  Fetching...." );
				 appUser = appUserDao.get(AppUserEntity.class,
							Integer.valueOf(rideBean.getAppUserByAppUserPassenger()));
				 decorator.setResponseMessage(" Recent Rides For Passenger");
			}
			
			if (appUser == null) {
				throw new GenericException("Please Enter the valid AppUser Id");
			}
			if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("0")) {
				rideBillList = appUserDao.GetRecentRidesForPassenger(appUser,
						new java.sql.Timestamp(fromDate.getTime()),
						new java.sql.Timestamp(toDate.getTime()),
						Integer.valueOf(rideBean.getFirstIndex()),
						Integer.valueOf(rideBean.getMaxResult()));
				rideBeanList = prepareBeanFromEntityForPassenger(rideBillList);
				rideBean.setRideList(rideBeanList);
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				decorator.setResponseMessage(" Recent Rides For Passenger");
				decorator.getResponseMap().put("data", rideBean);

			} else if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("1")) {
				rideBillList = appUserDao.GetRecentRidesForDriver(appUser,
						new java.sql.Timestamp(fromDate.getTime()),
						new java.sql.Timestamp(toDate.getTime()),
						Integer.valueOf(rideBean.getFirstIndex()),
						Integer.valueOf(rideBean.getMaxResult()));
				rideBeanList = prepareBeanFromEntityForDriver(rideBillList);
				rideBean.setRideList(rideBeanList);
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				
				decorator.getResponseMap().put("data", rideBean);
			}
			///////////////////////////////////

		}else{
			throw new GenericException("Please Fullfill the Requriment of this api ");
		}

	}

	private List<RideBean> prepareBeanFromEntityForDriver(
			List<RideBillEntity> rideBillList) throws GenericException {
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		if (rideBillList == null) {
			throw new GenericException("Driver has no recent Rides");
		}
		for (RideBillEntity rideBillEntityListObj : rideBillList) {
			RideBean rideBeanListObj = new RideBean();
			rideBeanListObj.setRideNo(rideBillEntityListObj.getRideInfoId()
					.getRideNo());
			// rideBeanListObj.setPaymentModeName(rideBillEntityListObj.getPaymentMode().getName());
			// /
			// rideBeanListObj.setPaymentModeId(rideBillEntityListObj.getPaymentMode().getPaymentModeId().toString());
			rideBeanListObj.setAppUserByAppUserPassenger(rideBillEntityListObj.getAppUserByAppUserPassenger().getAppUserId().toString());
			rideBeanListObj.setAppUserByAppUserDriver(rideBillEntityListObj.getAppUserByAppUserDriver().getAppUserId().toString());
			rideBeanListObj.setFirstName(rideBillEntityListObj.getAppUserByAppUserPassenger().getPerson().getFirstName());
			rideBeanListObj.setLastName(rideBillEntityListObj.getAppUserByAppUserPassenger().getPerson().getLastName());
		//	rideBeanListObj.setDestinationLat(rideBillEntityListObj
			//		.getRideInfoId().getRideCriteria().getDestinationLat());
			if (rideBillEntityListObj.getRideInfoId().getRideCriteria().getDistinationAddress() != null) {
				rideBeanListObj.setDistinationAddress(rideBillEntityListObj.getRideInfoId().getRideCriteria()
						.getDistinationAddress());
			} else {
				rideBeanListObj.setDistinationAddress("''");
			}
			if (rideBillEntityListObj.getRideInfoId().getRideCriteria().getSourceAddress() != null) {
				rideBeanListObj.setSourceAddress(rideBillEntityListObj.getRideInfoId().getRideCriteria()
						.getSourceAddress());
			} else {
				rideBeanListObj.setSourceAddress("''");
			}
			String userPisUrl=getPicUrlByAppUser(rideBillEntityListObj.getAppUserByAppUserPassenger());
			if(userPisUrl!=null){
				rideBeanListObj.setUserPisUrl(userPisUrl);
			}else{
				rideBeanListObj.setUserPisUrl("''");
			}
			rideBeanListObj.setIsCompleted(rideBillEntityListObj
					.getRideInfoId().getIsCompleted());
			rideBeanListObj.setIsCancel(rideBillEntityListObj.getRideInfoId()
					.getIsCancel());
			rideBeanListObj.setTotalAmount(rideBillEntityListObj.getTotalAmount().toString());
			// rideBeanListObj.setRideTypeId(rideBillEntityListObj.getRideInfoId().getRideTypeId().toString());
			rideBeanListObj.setRideEndTime(rideBillEntityListObj
					.getRideInfoId().getEndTime().toString());
			rideBeanListObj.setTotalAmount(rideBillEntityListObj.getTotalAmount().toString());
			rideBeanListObj.setRideStartTime(rideBillEntityListObj
					.getRideInfoId().getStartTime().toString());
			rideBeanList.add(rideBeanListObj);

		}
		return rideBeanList;
	}

	private List<RideBean> prepareBeanFromEntityForPassenger(
			List<RideBillEntity> rideBillList) throws GenericException {
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		if (rideBillList == null) {
			throw new GenericException("Driver has no recent Rides");
		}
		for (RideBillEntity rideBillEntityListObj : rideBillList) {
			RideBean rideBeanListObj = new RideBean();
			rideBeanListObj.setRideNo(rideBillEntityListObj.getRideInfoId()
					.getRideNo());
			// rideBeanListObj.setPaymentModeName(rideBillEntityListObj.getPaymentMode().getName());
			// /
			// rideBeanListObj.setPaymentModeId(rideBillEntityListObj.getPaymentMode().getPaymentModeId().toString());
			rideBeanListObj.setAppUserByAppUserPassenger(rideBillEntityListObj.getAppUserByAppUserPassenger().getAppUserId().toString());
			rideBeanListObj.setAppUserByAppUserDriver(rideBillEntityListObj.getAppUserByAppUserDriver().getAppUserId().toString());
			rideBeanListObj.setDriverFirstName(rideBillEntityListObj.getAppUserByAppUserDriver().getPerson().getFirstName());
			rideBeanListObj.setDriverLastName(rideBillEntityListObj.getAppUserByAppUserDriver().getPerson().getLastName());
		//	rideBeanListObj.setDestinationLat(rideBillEntityListObj
			//		.getRideInfoId().getRideCriteria().getDestinationLat());
			if (rideBillEntityListObj.getRideInfoId().getRideCriteria().getDistinationAddress() != null) {
				rideBeanListObj.setDistinationAddress(rideBillEntityListObj.getRideInfoId().getRideCriteria()
						.getDistinationAddress());
			} else {
				rideBeanListObj.setDistinationAddress("''");
			}
			if (rideBillEntityListObj.getRideInfoId().getRideCriteria().getSourceAddress() != null) {
				rideBeanListObj.setSourceAddress(rideBillEntityListObj.getRideInfoId().getRideCriteria()
						.getSourceAddress());
			} else {
				rideBeanListObj.setSourceAddress("''");
			}
//			rideBeanListObj.setDistinationAddress("This is Distination Address...rt ....");
//			rideBeanListObj.setSourceAddress("This is Source Address.... fxc .. ");
			String userPisUrl=getPicUrlByAppUser(rideBillEntityListObj.getAppUserByAppUserDriver());
			if(userPisUrl!=null){
				rideBeanListObj.setUserPisUrl(userPisUrl);
			}else{
				rideBeanListObj.setUserPisUrl("''");
			}
			
			rideBeanListObj.setIsCompleted(rideBillEntityListObj
					.getRideInfoId().getIsCompleted());
			rideBeanListObj.setIsCancel(rideBillEntityListObj.getRideInfoId()
					.getIsCancel());
			rideBeanListObj.setTotalAmount(rideBillEntityListObj.getTotalAmount().toString());
			// rideBeanListObj.setRideTypeId(rideBillEntityListObj.getRideInfoId().getRideTypeId().toString());
			rideBeanListObj.setRideEndTime(rideBillEntityListObj
					.getRideInfoId().getEndTime().toString());
			rideBeanListObj.setRideStartTime(rideBillEntityListObj
					.getRideInfoId().getStartTime().toString());
			rideBeanList.add(rideBeanListObj);

		}
		return rideBeanList;
	}

	private Date getEndDate(Timestamp endDate) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(endDate);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return currentDate.getTime();
	}

	private Date getStartDate(Timestamp startDate) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(startDate);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return currentDate.getTime();
	}

	@Override
	public void getRideInfo(SafeHerDecorator decorator) throws GenericException {
		logger.info("User Ride Info Fetching...." );
		RideBean rideBean = (RideBean) decorator.getDataBean();
		AppUserEntity appUser  =null;
		RideBean rideBeanObj=null;
		RideEntity rideEntity=null;
		RideBillEntity rideBillEntity=null;
		AppUserVehicleEntity appUserVehicle=null;
		UserRatingEntity ratingEntity=null;
		rideConverter.vaildateRideInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			/////////////////
			if(rideBean.getAppUserByAppUserDriver()!=null){
				logger.info("Driver Recent Ride  Fetching...." );
				appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			}else if(rideBean.getAppUserByAppUserPassenger()!=null){
				logger.info("Passenger Recent Ride  Fetching...." );
				 appUser = appUserDao.get(AppUserEntity.class,
							Integer.valueOf(rideBean.getAppUserByAppUserPassenger()));
			}
			/////////////////
			rideBeanObj=new RideBean();
			rideEntity = appUserDao.findByParameter(RideEntity.class,
					"rideNo", rideBean.getRideNo());
			if(rideEntity==null){
				throw new GenericException("Ride No is Not Valid");
			}
			rideBillEntity = appUserDao.findByOject(RideBillEntity.class,
					"rideInfoId", rideEntity);
			if(rideBillEntity==null){
				throw new GenericException("This Ride is Not Complete its process");
			}
			if (appUser == null) {
				throw new GenericException("Please Enter the valid Driver Id");
			}
			
			if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("0")) {
				rideBean = populateRideBeanFromObjForPassenger(rideEntity,
						rideBillEntity);
				//Vehicle Information 
				
				//Rating 		
				 ratingEntity = appUserDao.findByOject(
						UserRatingEntity.class, "appUser", rideBillEntity.getAppUserByAppUserDriver());
				if (ratingEntity != null) {
					rideBean.setUserRating(ratingEntity.getCurrentValue() + "");
				} else {
					rideBean.setUserRating("0.0");
				}
			
				decorator.setResponseMessage("Ride Information For Passenger");

			} else if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("1")) {
				rideBean = populateRideBeanFromObjForDriver(
						rideEntity, rideBillEntity);
				 ratingEntity = appUserDao.findByOject(
							UserRatingEntity.class, "appUser", rideBillEntity.getAppUserByAppUserPassenger());
					if (ratingEntity != null) {
						rideBean.setUserRating(ratingEntity.getCurrentValue() + "");
					} else {
						rideBean.setUserRating("0.0");
					}
				decorator.setResponseMessage("Ride Information For Driver");

			}
			appUserVehicle = appUserDao.findByOjectForVehicle(
					AppUserVehicleEntity.class, "appUser", rideBillEntity.getAppUserByAppUserDriver());
			if (appUserVehicle != null) {
				rideBean.setVehicleMake(appUserVehicle.getVehicleInfo()
						.getVehicleMake().getName());
				rideBean.setVehicleModel(appUserVehicle.getVehicleInfo()
						.getVehicleModel().getName());
				rideBean.setVehicleNum(appUserVehicle.getVehicleInfo()
						.getPlateNumber());
			}
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			decorator.getResponseMap().put("data", rideBean);
		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of this api ");
		}

	}

	private RideBean populateRideBeanFromObjForDriver(RideEntity rideEntity,
			RideBillEntity rideBillEntity) {
		RideBean rideBeanObj = new RideBean();
		PostRideEntity postRide = null;
		RideDetailEntity rideDetail=null;
		rideBeanObj.setEstimatedArrival(rideEntity.getEstimatedArrival());
		rideBeanObj.setEstimatedDuration(rideEntity.getEstimatedDuration());
		rideBeanObj.setEstimatedDistance(rideEntity.getEstimatedDistance()
				.toString());
		postRide= appUserDao.findByOject(PostRideEntity.class, "rideEntityId", rideEntity);
		if(postRide!=null){
		rideBeanObj.setActualDuration(postRide.getActualDuration());
		rideBeanObj.setActualDistance(postRide.getActualDistance().toString());
		}
		rideDetail=appUserDao.findByOject(RideDetailEntity.class, "rideEntityId", rideEntity);
		if(rideDetail!=null){
		rideBeanObj.setActualChild(rideDetail.getActualChild().toString());
		rideBeanObj.setActualPerson(rideDetail.getActualPerson().toString());
		rideBeanObj.setActualSeats(rideDetail.getActualSeats().toString());
		}
		rideBeanObj.setStartTime(rideEntity.getStartTime().toString());
		rideBeanObj.setEndTime(rideEntity.getEndTime().toString());
		rideBeanObj.setIsCompleted(rideEntity.getIsCompleted());
		rideBeanObj.setRideTypeId(rideEntity.getRideTypeId().toString());
		rideBeanObj.setFirstName(rideBillEntity.getAppUserByAppUserPassenger()
				.getPerson().getFirstName());
		rideBeanObj.setLastName(rideBillEntity.getAppUserByAppUserPassenger()
				.getPerson().getLastName());
		rideBeanObj.setAppUserByAppUserPassenger(rideBillEntity
				.getAppUserByAppUserPassenger().getAppUserId().toString());
		rideBeanObj.setInvoiceNo(rideBillEntity.getInvoiceNo());
		if (rideEntity.getRideCriteria().getDistinationAddress() != null) {
			rideBeanObj.setDistinationAddress(rideEntity.getRideCriteria()
					.getDistinationAddress());
		} else {
			rideBeanObj.setDistinationAddress("''");
		}
		if (rideEntity.getRideCriteria().getSourceAddress() != null) {
			rideBeanObj.setSourceAddress(rideEntity.getRideCriteria()
					.getSourceAddress());
		} else {
			rideBeanObj.setSourceAddress("''");
		}
		if (rideBillEntity.getPaymentMode() != null) {
			rideBeanObj.setPaymentModeName(rideBillEntity.getPaymentMode()
					.getName());
		}
		String passengerPic = getPicUrlByAppUser(rideBillEntity
				.getAppUserByAppUserPassenger());
		if (passengerPic != null) {
			rideBeanObj.setUserPisUrl(passengerPic);
		} else {
			rideBeanObj.setUserPisUrl("''");
		}
		if (rideBillEntity.getPaymentMode() != null) {
			rideBeanObj.setPaymentModeName(rideBillEntity.getPaymentMode()
					.getName());
		}else{
			rideBeanObj.setPaymentModeName("''");
		}
		rideBeanObj.setRideAmount(rideBillEntity.getRideAmount().toString());
		rideBeanObj.setFineAmount(rideBillEntity.getFineAmount().toString());
		if (rideBillEntity.getTipAmount() != null) {
			rideBeanObj.setTipAmount(rideBillEntity.getTipAmount().toString());
		}
		rideBeanObj.setTotalAmount(rideBillEntity.getTotalAmount().toString());
		rideBeanObj.setDestinationLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLat());
		rideBeanObj.setDestinationLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLong());
		rideBeanObj.setSourceLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLat());
		rideBeanObj.setSourceLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLong());
		return rideBeanObj;
	}

	private RideBean populateRideBeanFromObjForPassenger(RideEntity rideEntity,
			RideBillEntity rideBillEntity) {
		RideBean rideBeanObj = new RideBean();
		PostRideEntity postRide = null;
		RideDetailEntity rideDetail=null;
		rideBeanObj.setEstimatedArrival(rideEntity.getEstimatedArrival());
		rideBeanObj.setEstimatedDuration(rideEntity.getEstimatedDuration());
		rideBeanObj.setEstimatedDistance(rideEntity.getEstimatedDistance()
				.toString());
		postRide= appUserDao.findByOject(PostRideEntity.class, "rideEntityId", rideEntity);
		if(postRide!=null){
		rideBeanObj.setActualDuration(postRide.getActualDuration());
		rideBeanObj.setActualDistance(postRide.getActualDistance().toString());
		}
		rideDetail=appUserDao.findByOject(RideDetailEntity.class, "rideEntityId", rideEntity);
		if(rideDetail!=null){
		rideBeanObj.setActualChild(rideDetail.getActualChild().toString());
		rideBeanObj.setActualPerson(rideDetail.getActualPerson().toString());
		rideBeanObj.setActualSeats(rideDetail.getActualSeats().toString());
		}
		rideBeanObj.setStartTime(rideEntity.getStartTime().toString());
		rideBeanObj.setEndTime(rideEntity.getEndTime().toString());
		rideBeanObj.setIsCompleted(rideEntity.getIsCompleted());
		rideBeanObj.setRideTypeId(rideEntity.getRideTypeId().toString());
		rideBeanObj.setFirstName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getFirstName());
		rideBeanObj.setLastName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getLastName());
		rideBeanObj.setAppUserByAppUserDriver(rideBillEntity
				.getAppUserByAppUserDriver().getAppUserId().toString());
		String driverPic = getPicUrlByAppUser(rideBillEntity
				.getAppUserByAppUserDriver());
		if (driverPic != null) {
			rideBeanObj.setUserPisUrl(driverPic);
		} else {
			rideBeanObj.setUserPisUrl("''");
		}
	
		//rideBeanObj.setActualChild(rideEntity.getRideCriteria().getNoChild()
		//		.toString());
	//	rideBeanObj.setActualPerson(rideEntity.getRideCriteria()
			//	.getNoPassenger().toString());
		rideBeanObj.setInvoiceNo(rideBillEntity.getInvoiceNo());
		if (rideEntity.getRideCriteria().getDistinationAddress() != null) {
			rideBeanObj.setDistinationAddress(rideEntity.getRideCriteria()
					.getDistinationAddress());
		} else {
			rideBeanObj.setDistinationAddress("''");
		}
		if (rideEntity.getRideCriteria().getSourceAddress() != null) {
			rideBeanObj.setSourceAddress(rideEntity.getRideCriteria()
					.getSourceAddress());
		} else {
			rideBeanObj.setSourceAddress("''");
		}
		if (rideBillEntity.getPaymentMode() != null) {
			rideBeanObj.setPaymentModeName(rideBillEntity.getPaymentMode()
					.getName());
		}else{
			rideBeanObj.setPaymentModeName("''");
		}
		rideBeanObj.setRideAmount(rideBillEntity.getRideAmount().toString());
		rideBeanObj.setFineAmount(rideBillEntity.getFineAmount().toString());
		if (rideBillEntity.getTipAmount() != null) {
			rideBeanObj.setTipAmount(rideBillEntity.getTipAmount().toString());
		}

		rideBeanObj.setTotalAmount(rideBillEntity.getTotalAmount().toString());
		rideBeanObj.setDestinationLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLat());
		rideBeanObj.setDestinationLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLong());
		rideBeanObj.setSourceLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLat());
		rideBeanObj.setSourceLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLong());
		return rideBeanObj;
		}

	@Override
	public void paymentFilter(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("Payment Filter....");
		RideBean rideBean = (RideBean) decorator.getDataBean();
		AppUserEntity appUser = null;
		List<RideBillEntity> rideBillList = null;
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		Timestamp startDate = null;
		Timestamp endDate = null;
		rideConverter.vaildatePaymentFilter(decorator);
		if (decorator.getErrors().size() == 0) {

			if (rideBean.getAppUserByAppUserDriver() != null) {
				logger.info("Driver Payment History Fetching....");
				appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			} else if (rideBean.getAppUserByAppUserPassenger() != null) {
				logger.info("Passenger Payment History Fetching....");
				appUser = appUserDao.get(AppUserEntity.class, Integer
						.valueOf(rideBean.getAppUserByAppUserPassenger()));
			}
			// /////////////////////////////////////
			
			// ///////////////////////////////////
			if (decorator.getErrors().size() == 0) {

				if (StringUtil.isEmpty(rideBean.getStartTime())) {
					startDate = DateUtil.now();
				} else {
					startDate = DateUtil.parseTimestampFromFormats(rideBean
							.getStartTime());
				}

				if (StringUtil.isEmpty(rideBean.getEndTime())) {
					endDate = DateUtil.now();
				} else {
					endDate = DateUtil.parseTimestampFromFormats(rideBean
							.getEndTime());
				}
				Date fromDate = getStartDate(startDate);
				Date toDate = getEndDate(endDate);
				System.out.println(fromDate);
				System.out.println(toDate);
				// ///////////////////////////////////
				if (rideBean.getIsCancel() != null) {
					if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("0")) {
						rideBillList = appUserDao.getPaymentFilterByIsCancelforPassenger(
								appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()),
								rideBean.getIsCancel());
						rideBeanList = rideConverter
								.preparePaymentBeanFromEntity(rideBillList);
						decorator.setResponseMessage("Payment Information For Passenger");
					} else if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("1")) {
						rideBillList = appUserDao.getPaymentFilterByIsCancelforDriver(
								appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()),
								rideBean.getIsCancel());
						rideBeanList = rideConverter
								.preparePaymentBeanFromEntity(rideBillList);
						decorator.setResponseMessage("Payment Information For Driver");
					}

				}
				if (rideBean.getPaymentModeId() != null) {
					if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("0")) {
						rideBillList = appUserDao.getPaymentFilterByIsPaymentforPassenger(
										appUser,
										new java.sql.Timestamp(fromDate.getTime()),
										new java.sql.Timestamp(toDate.getTime()),
										Integer.valueOf(rideBean.getFirstIndex()), 
										Integer.valueOf(rideBean.getMaxResult()),
										Integer.valueOf(rideBean.getPaymentModeId()));
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
						decorator.setResponseMessage("Payment Information For Passenger");
					} else if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("1")) {
						rideBillList = appUserDao.getPaymentFilterByIsPaymentforDriver(
								appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()), 
								Integer.valueOf(rideBean.getMaxResult()),
								Integer.valueOf(rideBean.getPaymentModeId()));
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
						decorator.setResponseMessage("Payment Information For Driver");
					}

				}
				if (rideBean.getStatus() != null) {
					if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("0")) {
						rideBillList = appUserDao.getPaymentFilterByStatusforPassenger(appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()),
								Integer.valueOf(rideBean.getStatus()));
						decorator.setResponseMessage("Payment Information For Passenger");
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
					} else if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("1")) {
						rideBillList = appUserDao.getPaymentFilterByStatusforDriver(appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()),
								Integer.valueOf(rideBean.getStatus()));
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
						decorator.setResponseMessage("Payment Information For Driver");
					}
					
				} else {
					if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("0")) {
						rideBillList = appUserDao.GetRecentRidesForPassenger(
								appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()));
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
						decorator.setResponseMessage("Payment Information For Passenger");
					} else if (appUser.getIsDriver() != null
							&& appUser.getIsDriver().equals("1")) {
						rideBillList = appUserDao.GetRecentRidesForDriver(
								appUser,
								new java.sql.Timestamp(fromDate.getTime()),
								new java.sql.Timestamp(toDate.getTime()),
								Integer.valueOf(rideBean.getFirstIndex()),
								Integer.valueOf(rideBean.getMaxResult()));
						rideBeanList = rideConverter.preparePaymentBeanFromEntityforPayment(rideBillList);
						decorator.setResponseMessage("Payment Information For Driver");
					}
					
					rideBean.setRideList(rideBeanList);
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					logger.info("Payment Filter....End");
					decorator.getResponseMap().put("data", rideBean);

				}

			} else {
				throw new GenericException(
						"Please Fullfill the Requriment of this Payment Filter Api ");
			}
		}
	}

	@Override
	public void getInvoiceByNo(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("User Invoice Info Fetching....");
		RideBean rideBean = (RideBean) decorator.getDataBean();
		AppUserEntity appUser = null;
		RideEntity rideEntity = null;
		RideBillEntity rideBillEntity = null;
		rideConverter.vaildateInvoiceInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			// ///////////////
			if (rideBean.getAppUserByAppUserDriver() != null) {
				logger.info("Driver Invoice Information  Fetching....");
				appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			} else if (rideBean.getAppUserByAppUserPassenger() != null) {
				logger.info("Passenger Invoice Information  Fetching....");
				appUser = appUserDao.get(AppUserEntity.class, Integer
						.valueOf(rideBean.getAppUserByAppUserPassenger()));
			}
			rideBillEntity = appUserDao.findByParameter(RideBillEntity.class,
					"invoiceNo", rideBean.getInvoiceNo());
			if (rideBillEntity == null) {
				throw new GenericException(
						"This Ride is Not Complete its process");
			}
			rideEntity = rideBillEntity.getRideInfoId();
			if (rideEntity == null) {
				throw new GenericException("Ride No is Not Valid");
			}
			if (appUser == null) {
				throw new GenericException("Please Enter the valid Driver Id");
			}
			rideBean = rideConverter.populateInvoiceFromObj(rideEntity,
					rideBillEntity);
			if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("0")) {

				decorator.setResponseMessage("Ride Information For Passenger");

			} else if (appUser.getIsDriver() != null
					&& appUser.getIsDriver().equals("1")) {
				decorator.setResponseMessage("Ride Information For Driver");

			}

			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			decorator.getResponseMap().put("data", rideBean);
			logger.info("User Invoice Info Fetching. . . . . . . . . . . . . . End");

		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of this Invoice No Api ");
		}

	}
	public String getPicUrlByAppUser(AppUserEntity appUser) {
		AppUserBiometricEntity appUserEntity = rideDao.findByOject(
				AppUserBiometricEntity.class, "appUser", appUser);
		if (appUserEntity == null) {
			return "";
		}
		return appUserEntity.getPath();
	}

	@Override
	public void rideGeneralHistory(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		rideConverter.vaildateShortEarning(decorator);
		List<RideBillEntity> rideBillList = null;
		UserRatingEntity ratingEntity=null;
		List<UserCommentEntity> userComentEntity= null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		int counter = 0;
		Double totalShare = 0.0;
		Integer duration = 0;
		Double distance = 0.0;
		Double totalTipAmount = 0.0;
		Double driverEarning = 0.0;
		Double userRating=0.0;

		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserDriver()));
			if (appUser == null) {
				throw new GenericException("Please Enter the valid Driver Id");
			} else {
				if (appUser.getIsDriver() == null) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				} else if (appUser.getIsDriver() != null
						&& appUser.getIsDriver().equals("0")) {
					throw new GenericException(
							"Please Enter the valid Driver Information");
				}
			}

			// ////

			if (StringUtil.isEmpty(rideBean.getStartTime())) {
				startDate = DateUtil.now();
			} else {
				startDate = DateUtil.parseTimestampFromFormats(rideBean
						.getStartTime());
			}

			if (StringUtil.isEmpty(rideBean.getEndTime())) {
				endDate = DateUtil.now();
			} else {
				endDate = DateUtil.parseTimestampFromFormats(rideBean
						.getEndTime());
			}
			Date fromDate =getStartDate(startDate);
			Date toDate = getEndDate(endDate);
						
			System.out.println(fromDate);
			System.out.println(toDate);
			// ///////////////////////
			rideBean.setStartTime(fromDate.toString());
			rideBean.setEndTime(toDate.toString());
			/*rideBean.setTotalRides(appUserDao.countShortEarningRides(appUser, new java.sql.Timestamp(
					fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime())).toString());*/
			rideBillList = appUserDao.calculateLastTripAmount(appUser,
					new java.sql.Timestamp(fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime()));
			for (RideBillEntity rideBillListObject : rideBillList) {
				Timestamp timeStamp = rideBillListObject.getRideInfoId()
						.getEndTime();
				Date rideEndDate = new Date(timeStamp.getTime());
				Date rideStartDate = new Date(rideBillListObject
						.getRideInfoId().getStartTime().getTime());
				long diff = (rideEndDate.getTime() - rideStartDate.getTime()) / 1000;
				System.out.println(rideBillListObject.getTotalAmount());
				System.out.println(rideBillListObject.getTipAmount());
				System.out.println(diff);
				if (counter == 0) {
					rideBean.setLastDuration(Long.toString(diff));
					if (rideBillListObject.getTipAmount() != null
							&& rideBillListObject.getTipAmount() != 0) {
						Double lastSumShare = (rideBillListObject
								.getTotalAmount() + rideBillListObject
								.getTipAmount());
						rideBean.setLastShare(lastSumShare.toString());
						totalShare += rideBillListObject.getTotalAmount();
						totalTipAmount += rideBillListObject.getTipAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
						++counter;
					} else {
						rideBean.setLastShare(rideBillListObject
								.getTotalAmount().toString());
						totalShare += rideBillListObject.getTotalAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
						++counter;
					}

				} else {
					++counter;

					if (rideBillListObject.getTipAmount() != null) {
						totalShare += rideBillListObject.getTotalAmount();
						totalTipAmount += rideBillListObject.getTipAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
					} else {
						totalShare += rideBillListObject.getTotalAmount();
						duration = duration
								+ Integer.valueOf(Long.toString(diff));
					}

				}
				PostRideEntity postrideEntity = appUserDao.findByOjectForPost(
						PostRideEntity.class, "rideEntityId",
						rideBillListObject.getRideInfoId());
				if (postrideEntity != null) {
					distance += postrideEntity.getActualDistance();
				}
				RidePaymnetDistributionEntity distributionEntity = appUserDao.
						findByOject(RidePaymnetDistributionEntity.class, "rideBill", rideBillListObject);
				if (distributionEntity != null) {
					if(StringUtil.isNotEmpty(distributionEntity.getDriverAmount()+"")){
						driverEarning += distributionEntity.getDriverAmount();	
					}
				}

			}
			rideBean.setTotalRides(rideBillList.size()+"");
//			rideBean.setDriverEarning((driverEarning+"").substring(0, 5));
			
			String text = Double.toString(Math.abs(driverEarning));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;
			if (decimalPlaces == 0) {
				rideBean.setDriverEarning(driverEarning + ".00");
			} else if (decimalPlaces == 1) {
				rideBean.setDriverEarning(driverEarning + "0");
			} else if (decimalPlaces >= 2) {
				rideBean.setDriverEarning(new DecimalFormat(
						"##.##").format(driverEarning) + "");
			}
			

//			rideBean.setRideAmount((totalShare+"").substring(0, 5));
			String text2 = Double.toString(Math.abs(totalShare));
			int integerPlaces2 = text2.indexOf('.');
			int decimalPlaces2 = text2.length() - integerPlaces2 - 1;
			if (decimalPlaces2 == 0) {
				rideBean.setRideAmount(driverEarning + ".00");
			} else if (decimalPlaces2 == 1) {
				rideBean.setRideAmount(driverEarning + "0");
			} else if (decimalPlaces2 >= 2) {
				rideBean.setRideAmount(new DecimalFormat(
						"##.##").format(totalShare) + "");
			}
			
			rideBean.setTotalDuration(duration.toString());
			rideBean.setTotalDistance(distance.toString());
			rideBean.setTotalTipAmount(totalTipAmount.toString());
			
			
			userComentEntity = new ArrayList<UserCommentEntity>();
			
			ratingEntity = appUserDao.findByOject(UserRatingEntity.class,
					"appUser", appUser);
			if (ratingEntity != null) {
				rideBean.setUserRating(ratingEntity.getCurrentValue() + "");
				userRating = appUserDao.getRatingAvg(ratingEntity.getUserRatingId());

			} else {
				rideBean.setUserRating("0.0");
			}
			
			if (ratingEntity != null) {
			userRating = appUserDao.findUserIntervalRating(ratingEntity.getUserRatingId(),new java.sql.Timestamp(fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime()));
			}
			
			if(userRating != null){
				rideBean.setUserIntervalRating(String.valueOf(userRating));
			}
			else{
				rideBean.setUserIntervalRating("0.0");
			}
			
			/*userRating= appUserDao.findUserIntervalRating(appUser,
					new java.sql.Timestamp(fromDate.getTime()),
					new java.sql.Timestamp(toDate.getTime()));
			//rideBean.setUserRating(userRating.toString());
			if(userRating == null){
				rideBean.setUserIntervalRating("0.0");
			}else{
				rideBean.setUserIntervalRating(String.valueOf(userRating));
			}*/
			
			//Should be Handle Later
			rideBean.setDriverOnTime("1:12:07");
			
		} else {
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			decorator.setResponseMessage("Fill Your Requriments");
			throw new GenericException("General Driver Summary Requirement Error");
		}
		
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		decorator.setResponseMessage("General Driver Summary For Driver");
		decorator.getResponseMap().put("data", rideBean);

	}
}
