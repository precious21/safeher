package com.tgi.safeher.service.manager.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RequiredDataBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideSearchResultBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.map.service.IGoogleMapServices;
import com.tgi.safeher.repository.CriteriaRepository;
import com.tgi.safeher.repository.RequiredDataRepository;
import com.tgi.safeher.service.ICalculatorService;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.IRideService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.service.manager.IPassengerManager;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;

//@Component
//@Scope("prototype")
@Service
@Scope("prototype")
@Transactional
public class PassengerManager implements IPassengerManager {
	private static final Logger logger = Logger
			.getLogger(PassengerManager.class);
	@Autowired
	private IRideService rideService;

	@Autowired
	private CriteriaRepository criteriaRepository;
	
	@Autowired
	private IGoogleMapsAPIService googleMapApiService;

	@Autowired
	private RideConverter rideConverter;

	@Autowired
	private IRideRequestResponseService rideRequestReponceService;

	@Autowired
	private IGoogleMapServices googleMapAPIService;

	@Autowired
	private IGoogleMapsAPIService googleMapService;

	@Autowired
	private ICalculatorService calculatorService;
	
	@Autowired
	private RequiredDataRepository requiredDataRepository;

	@Override
	public void rideCriteriaSearch(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException {
		
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("**************Entering in rideCriteriaSearch with RideCriteriaBean " +rideCriteria+"*******");
		RideSearchResultBean rideSearchBean = new RideSearchResultBean();
		String PassengerId = rideCriteria.getAppUserId();
		rideConverter.validateRideCriteria(decorator);
		String requestNo = getUniqueNo(PassengerId);
		logger.info("Ride Criteria Search Method called with Request no " +requestNo);
		rideCriteria.setRequestNo(requestNo);
		if (decorator.getErrors().size() == 0) {
			List<RideSearchResultBean> driverPushNotification = null;
			decorator.setDataBean(rideCriteria);
			// send Source Lat Log to Notify Driver's about passenger
			
			// Find Suburbs and Save RideCriteria
			if (decorator.getErrors().size() == 0) {
				try {
					rideCriteria = rideService.saveRideCriteria(decorator); // TODO
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Method rideCriteriaSearchended with Exception " +e.getMessage());
					throw new GenericRuntimeException(e.getMessage());
				}	
				// Temparay

				// convert one bean to another
				rideSearchBean = rideConverter
						.convertRideCriteriaToRideSearchResult(rideCriteria);
				//////////////////Sending notifications
				/*driverPushNotification = googleMapApiService
						.pushNotificationsToActiveDrivers(decorator);*/
				driverPushNotification = googleMapApiService
						.pushNotificationsToActiveDriversMongo(decorator);
				
				if (CollectionUtil.isEmpty(driverPushNotification)) {
				     throw new GenericRuntimeException(
				       "No Active Driver Found on this Search");
				    }
				rideSearchBean
						.setDriverPushNotification(driverPushNotification);
				rideSearchBean.setAppUserId(PassengerId);
				decorator.setDataBean(rideSearchBean);
				// fill Ride Search Result and its Detail
				try {
					rideService.addRideSearchResult(decorator);
				} catch (Exception e) {
					logger.info("Method rideCriteriaSearchended with Exception " +e.getMessage());
					e.printStackTrace();
					throw new GenericRuntimeException(e.getMessage());
				}

				// Request For REquest Responce
				rideRequestReponceService.saveRideRequestResponse(decorator);
				// rideConverter.convertRideSearchToRequestResponceBean(decorator);
				decorator.getResponseMap().put("rideCriteriaId",rideCriteria.getRideCriteriaId());
				decorator.setResponseMessage("Notification Send to driver");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				logger.info("********* Method rideCriteriaSearchended end **************");
			} else {
				throw new GenericRuntimeException(
						"Driver Not Seen in this area");
			}
		} else {
			throw new GenericRuntimeException("Ride Criteria is Not Valid");
		}

	}

	@Override
	public void rideCriteriaSearchV2(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException {
		
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		String PassengerId = rideCriteria.getAppUserId();
		rideConverter.validateRideCriteria(decorator);
		String requestNo = getUniqueNo(PassengerId);
		logger.info("**************Entering in rideCriteriaSearchV2 with RideCriteriaBean " +rideCriteria+"*******");
		logger.info("Ride Criteria Search Method called with Request no " +requestNo);
		rideCriteria.setRequestNo(requestNo);
		if (decorator.getErrors().size() == 0) {
			decorator.setDataBean(rideCriteria);
			if (decorator.getErrors().size() == 0) {
				try {
					rideCriteria = rideService.saveRideCriteriaV2(decorator);
				} catch (DataAccessException e) {
					e.printStackTrace();
					logger.info("Method rideCriteriaSearchV2 ended with Exception " +e.getMessage());
					throw new GenericException("Server is not responding right now");
				} catch (Exception e) {
					e.printStackTrace();
					throw new GenericException("Server is Down");
				}	
				
				// Request For REquest Responce
				try{
					/*rideRequestReponceService.saveRideRequestResponseV2(decorator);*/
					rideRequestReponceService.saveRideRequestResponseV2(decorator);
				}catch (DataAccessException e) {
					e.printStackTrace();
					logger.info("Method Request Responce ended with Exception " +e.getMessage());
					throw new GenericException("Server is not responding right now");
				} catch (Exception e) {
					e.printStackTrace();
					throw new GenericException("Server is Down");
				}	
			
				// rideConverter.convertRideSearchToRequestResponceBean(decorator);
				decorator.getResponseMap().put("rideCriteriaId",rideCriteria.getRideCriteriaId());
				decorator.setResponseMessage("Notification Send to driver");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				logger.info("Ride Criteria Search Method End");
			} else {
				throw new GenericRuntimeException(
						"Driver Not Seen in this area");
			}
		} else {
			throw new GenericRuntimeException("Ride Criteria is Not Valid");
		}

	}

	private String getUniqueNo(String passengerId) {
		String requestNo = "RE" + System.currentTimeMillis() + passengerId;
		return requestNo;
	}

	@Override
	public void rideSesrchResult(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		rideService.addRideSearchResult(decorator);
	}

	@Override
	public void ridePassengerInfo(SafeHerDecorator decorator)
			throws GenericRuntimeException {
		RequiredDataBean requiredDataBean = null;
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("**************Entering in ridePassengerInfo with RideCriteriaBean " + rideCriteria +" *********");
		try {
			rideConverter.validatePassengerInfo(decorator);
			if (decorator.getErrors().size() == 0) {
				
				requiredDataBean = requiredDataRepository.findRequiredData(rideCriteria
						.getAppUserId());
				// rideService.getPassengerRideInfo(decorator);
				String urlPath = null;
				
				try{
					
				urlPath = rideService.getPicUrlByAppUserId(rideCriteria
						.getAppUserId());
				rideCriteria.setUserImagePath(urlPath);
				}catch(Exception ex){
					ex.printStackTrace();
					logger.info("**************Method ridePassengerInfo ended with exception "+ex.getMessage()+"********* ");
				}
				decorator.setDataBean(rideCriteria);
				//
				DistanceAPIBean distanceAPIBean = rideService
						.getPassengerRideInfo(decorator);
				distanceAPIBean.setLatLngOrigins(new LatLng(distanceAPIBean.getLatOrigins(), distanceAPIBean.getLngOrigins()));
				distanceAPIBean.setLatLngDestinations(new LatLng(distanceAPIBean.getLatDestinations(), distanceAPIBean.getLngDestinations()));
							
				////
				DistanceAPIBean distanceBean = new DistanceAPIBean();
				distanceBean.setLatLngDestinations(distanceAPIBean.getLatLngOrigins());
				distanceBean.setLatLngOrigins(new LatLng(Double.valueOf(rideCriteria.getSourceLat()),
						Double.valueOf(rideCriteria.getSourceLong())));

				distanceBean = googleMapService.googleDistanceAPIV2(distanceBean);

				distanceAPIBean.setDistance(distanceBean.getTotalDistanceMeters() / 1609.344 + "");
				distanceAPIBean.setArrivalTime(DateUtil.getMinutesAndSecs(distanceBean.getTotalTimeSeconds()) + "");
				////
				
				
				/*distanceAPIBean.setOrigins(googleMapAPIService
						.getFormatedAddress(distanceAPIBean.getLatOrigins()
								.toString(), distanceAPIBean.getLngOrigins()
								.toString()));
				distanceAPIBean
						.setDestinations(googleMapAPIService
								.getFormatedAddress(distanceAPIBean
										.getLatDestinations().toString(),
										distanceAPIBean.getLngDestinations()
												.toString()));*/
				
				if(requiredDataBean != null){
					distanceAPIBean.setTotalFare(requiredDataBean.getEstimatedFare());
				}else{
					requiredDataBean = new RequiredDataBean();
				distanceAPIBean = googleMapService
						.googleDistanceAPIV2(distanceAPIBean);
				
				
				calculatorService.calculateFare(distanceAPIBean);
				requiredDataBean.setEstimatedFare(distanceAPIBean.getTotalFare());
				requiredDataRepository.saveRequiredData(rideCriteria.getAppUserId(), requiredDataBean);
				}
				distanceAPIBean.setPicUrl(urlPath);
				distanceAPIBean.setCurrentRating(rideService.getUserRatingByAppUser(rideCriteria
						.getAppUserId()));
				decorator.getResponseMap().put("data", distanceAPIBean);
				
				decorator.setResponseMessage("Passenger Information");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				logger.info("**************Ending ridePassengerInfo ended with distanceAPIBean "+distanceAPIBean +" **********");
			} else {
				throw new GenericException("Ride Information Error");
			}

			// googleMapAPIService.googleDistanceAPI(distanceAPIBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("**************Method ridePassengerInfo ended with exception "+e.getMessage()+"********* ");
			throw new GenericRuntimeException(e.getMessage());
			
		}

	}

	@Override
	public void getPassengerGiftedRideInfo(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException {
		rideService.getGiftedInfo(decorator);
	}

	@Override
	public void giftRideCriteriaSearchV2(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException,
			DataIntegrityViolationException {
		
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("**************Entering in giftRideCriteriaSearchV2 with RideCriteriaBean " + rideCriteria +" *********");
		String PassengerId = rideCriteria.getReciverAppUserId();
		rideConverter.validateGiftRideCriteria(decorator);
		String requestNo = getUniqueNo(PassengerId);
		logger.info("Gift Ride Search Method called with Request no " +requestNo);
		rideCriteria.setRequestNo(requestNo);
		if (decorator.getErrors().size() == 0) {
			decorator.setDataBean(rideCriteria);
			if (decorator.getErrors().size() == 0) {
				try {
					rideCriteria = rideService.saveGiftRideSearchV2(decorator);
				} catch (DataAccessException e) {
					e.printStackTrace();
					logger.info("Gift Ride Criteria Search Method giftRideCriteriaSearchV2 ended with Exception "
							+ e.getMessage());
					throw new GenericException("Server is not responding right now");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Gift Ride Criteria Search Method giftRideCriteriaSearchV2 ended with Exception "
							+ e.getMessage());
					throw new GenericException("Server is Down");
				}

				// Request For REquest Responce
				rideRequestReponceService.saveRideRequestResponseV2(decorator);
				// rideConverter.convertRideSearchToRequestResponceBean(decorator);
				decorator.getResponseMap().put("rideCriteriaId",rideCriteria.getRideCriteriaId());
				decorator.setResponseMessage("Notification Send to driver");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				logger.info("Gift Ride Criteria Search Method End");
			} else {
				throw new GenericException(
						"Gift Driver Not Seen in this area");
			}
		} else {
			throw new GenericRuntimeException("Gift Ride Criteria is Not Valid");
		}
		
	}

	@Override
	public void isRefresh(SafeHerDecorator decorator) throws GenericException {
		rideService.refreshApplication(decorator);
	}

	@Override
	public void midRideRequest(SafeHerDecorator decorator)
			throws GenericException {
		rideService.midRideRequest(decorator);
	}

	@Override
	 public void rejectGiftRide(SafeHerDecorator decorator)
	   throws GenericException {
	  rideService.rejectGiftRide(decorator);
	 }

	@Override
	public void midRideDistinationRequest(SafeHerDecorator decorator)
			throws GenericException {
		  rideService.midRideDistinationRequest(decorator);
		
	}
}
