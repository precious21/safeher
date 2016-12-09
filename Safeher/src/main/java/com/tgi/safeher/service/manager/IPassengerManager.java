package com.tgi.safeher.service.manager;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;

public interface IPassengerManager {

	public void rideCriteriaSearch(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException,DataIntegrityViolationException;

	public void rideCriteriaSearchV2(SafeHerDecorator decorator)
			throws GenericRuntimeException, GenericException,DataIntegrityViolationException;
	
	public void rideSesrchResult(SafeHerDecorator decorator)
	
			throws GenericRuntimeException, DataAccessException, Exception;

	public void ridePassengerInfo(SafeHerDecorator decorator)
			throws GenericRuntimeException;

	public void getPassengerGiftedRideInfo(SafeHerDecorator decorator)throws GenericRuntimeException, GenericException;

	public void giftRideCriteriaSearchV2(SafeHerDecorator decorator)throws GenericRuntimeException, GenericException,DataIntegrityViolationException;

	public void isRefresh(SafeHerDecorator decorator) throws GenericException;

	public void midRideRequest(SafeHerDecorator decorator) throws GenericException;

	public void rejectGiftRide(SafeHerDecorator decorator) throws GenericException;

	public void midRideDistinationRequest(SafeHerDecorator decorator) throws GenericException;

}
