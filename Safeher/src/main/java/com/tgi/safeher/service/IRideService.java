package com.tgi.safeher.service;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.RideEntity;

public interface IRideService {

	public RideCriteriaBean saveRideCriteria(SafeHerDecorator decorator) throws GenericException, DataAccessException, Exception;
	
	public RideCriteriaBean saveRideCriteriaV2(SafeHerDecorator decorator) throws GenericException, DataAccessException, Exception;

	public void addRideSearchResult(SafeHerDecorator decorator) throws DataAccessException, Exception;
	
	public void addRideSearchResultV2(SafeHerDecorator decorator) throws DataAccessException, Exception;
	
	public DistanceAPIBean getPassengerRideInfo(SafeHerDecorator decorator);

	public String getPicUrlByAppUserId(String appUserId);

	public void jusReached(SafeHerDecorator decorator) throws GenericException, IOException;

	public void startRide(SafeHerDecorator decorator) throws GenericException;

	public void startRideV2(SafeHerDecorator decorator) throws GenericException;

	public void endRide(SafeHerDecorator decorator) throws GenericException;

	public void endRideV2(SafeHerDecorator decorator) throws GenericException;

	public void endRideV3(SafeHerDecorator decorator) throws GenericException;
	
	public void giftEndRideV2(SafeHerDecorator decorator) throws GenericException;
	
	public void giftEndRideV3(SafeHerDecorator decorator) throws GenericException;

	public void midEndRide(SafeHerDecorator decorator) throws GenericException;

	public void getInvoiceInfo(SafeHerDecorator decorator) throws GenericException;

	public void confirmEndRide(SafeHerDecorator decorator) throws GenericException;

	public void getkeyToken(SafeHerDecorator decorator) throws GenericException;

	public void rideBillingTransaction(SafeHerDecorator decorator) throws GenericException;
	
	public void ledgerManagment(SafeHerDecorator decorator) throws GenericException;

	public String getUserRatingByAppUser(String appUserId);

	public void addGitedRide(SafeHerDecorator decorator)throws GenericException, DataAccessException, Exception;

	public void getGiftedRides(SafeHerDecorator decorator)throws GenericException;

	public void getGiftedInfo(SafeHerDecorator decorator)throws GenericException;

	public RideCriteriaBean saveGiftRideSearchV2(SafeHerDecorator decorator)throws GenericException, DataAccessException, Exception;

	public RideBean getInoviceInfo(RideBean bean)throws GenericException;

	public void refreshApplication(SafeHerDecorator decorator) throws GenericException;

	public void midRideRequest(SafeHerDecorator decorator)  throws GenericException;

	public void rejectGiftRide(SafeHerDecorator decorator) throws GenericException;
	
	public RideEntity getRideInfo(String rideNo) throws GenericException;

	public void inActiveGiftRides() throws GenericException;
	
	public void endRideV4(SafeHerDecorator decorator) throws GenericException;
	
	public void giftEndRideV3Mongo(SafeHerDecorator decorator) throws GenericException;

	public void midRideDistinationRequest(SafeHerDecorator decorator) throws GenericException;


}
