package com.tgi.safeher.service;

import java.io.IOException;

import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.RideRequestResponseEntity;

public interface IRideRequestResponseService {

	public void saveRideRequestResponse(SafeHerDecorator decorator)
			throws GenericException;

	public void saveRideRequestResponseV2(SafeHerDecorator decorator)
			throws GenericException;
	
	public void driverRequestToPassenger(SafeHerDecorator decorator)
			throws GenericException;
	
	public void driverRequestToPassengerV2(SafeHerDecorator decorator)
			throws GenericException;

	public void cancelRequest(SafeHerDecorator decorator)
			throws GenericException;

	public void cancelRequestV2(SafeHerDecorator decorator)
			throws GenericException;

	public void acceptRequest(SafeHerDecorator decorator)
			throws GenericException;

	public void acceptRequestV2(SafeHerDecorator decorator)
			throws GenericException;

	public void acceptRequestV3(SafeHerDecorator decorator)
			throws GenericException;

	public void getDriverInfo(SafeHerDecorator decorator)
			throws GenericException;

	public void getColorInfo(SafeHerDecorator decorator)
			throws GenericException;

	boolean getColorFunctionality(RideRequestResponseBean bean)
			throws GenericException;

	public void preRideStart(SafeHerDecorator decorator)
			throws GenericException;

	public void matchColorManagment(SafeHerDecorator decorator)
			throws GenericException, IOException;

	public void matchColorManagmentV2(SafeHerDecorator decorator)
			throws GenericException, IOException;

	public void getReasons(SafeHerDecorator decorator) throws GenericException;

	public void cancelOrLateReasonRequest(SafeHerDecorator decorator)
			throws GenericException;

	public void reachedStartDestination(SafeHerDecorator decorator)
			throws GenericException, IOException;

	public void passengerOrDriverNotReached(SafeHerDecorator decorator)
			throws GenericException;

	public void rideAction(SafeHerDecorator decorator)
			throws GenericException;

	public void saveRideRequestResponse(RideRequestResponseEntity requestResponseEntity);

	public void cancelRequestByDriver(SafeHerDecorator decorator)throws GenericException;

	public void checkForRideNotification(SafeHerDecorator decorator)
			throws GenericException;

	public void getRideTracking(SafeHerDecorator decorator)
			throws GenericException;
	
public void saveRideRequestResponseV2Mongo(SafeHerDecorator decorator)	throws GenericException;
	
	public void cancelOrLateReasonRequestV2(SafeHerDecorator decorator)
			throws GenericException;
	
	public void cancelRequestByDriverV2(SafeHerDecorator decorator)
			throws GenericException ;
	
	public void getDriverInfoV2(SafeHerDecorator decorator)
			throws GenericException;
}
