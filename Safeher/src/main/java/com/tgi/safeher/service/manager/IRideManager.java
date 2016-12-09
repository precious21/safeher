package com.tgi.safeher.service.manager;

import java.io.IOException;

import org.springframework.dao.DataAccessException;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IRideManager {

	public void getColorManagment(SafeHerDecorator decorator)
			throws GenericException;

	public void startPerRide(SafeHerDecorator decorator)
			throws GenericException;

	void getReasons(SafeHerDecorator decorator) throws GenericException;

	public void getColorMatch(SafeHerDecorator decorator) throws GenericException, IOException;

	public void getColorMatchV2(SafeHerDecorator decorator) throws GenericException, IOException;

	public void reachedStartDestination(SafeHerDecorator decorator) throws GenericException, IOException;

	public void justReached(SafeHerDecorator decorator) throws GenericException, IOException;

	public void startRide(SafeHerDecorator decorator) throws GenericException;

	public void endRide(SafeHerDecorator decorator) throws GenericException;

	public void endRideV2(SafeHerDecorator decorator) throws GenericException;

	public void midEndRide(SafeHerDecorator decorator) throws GenericException;

	public void getInvoiceInfo(SafeHerDecorator decorator) throws GenericException;

	public void confirmEndRide(SafeHerDecorator decorator) throws GenericException;

	public void getClientToken(SafeHerDecorator decorator) throws GenericException;

	public void rideBillingTransaction(SafeHerDecorator decorator) throws GenericException;

	public void getLatestShareSummary(SafeHerDecorator decorator) throws GenericException;

	public void rideShortEarningHistory(SafeHerDecorator decorator) throws GenericException;

	public void userRatingHistory(SafeHerDecorator decorator) throws GenericException;

	public void getRecentRides(SafeHerDecorator decorator)throws GenericException;

	public void getRideInfo(SafeHerDecorator decorator) throws GenericException;

	public void getPaymentByFilter(SafeHerDecorator decorator) throws GenericException;

	public void getInvoiceByNo(SafeHerDecorator decorator) throws GenericException;

	public void addGiftedRide(SafeHerDecorator decorator) throws GenericException;

	public void getGiftedRide(SafeHerDecorator decorator) throws GenericException;

	public void cancelRequestByDriver(SafeHerDecorator decorator)throws GenericException;

	public void reportUserRating(SafeHerDecorator decorator)throws GenericException, DataAccessException, Exception;

	public void rideGeneralHistory(SafeHerDecorator decorator) throws GenericException;
}
