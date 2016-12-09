package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IRideHistoryService {
	public void getLatestShareSummary(SafeHerDecorator decorator) throws GenericException;

	public void shortEarningHistory(SafeHerDecorator decorator) throws GenericException;

	public void getRecentRides(SafeHerDecorator decorator)throws GenericException;

	public void getRideInfo(SafeHerDecorator decorator)throws GenericException;

	public void paymentFilter(SafeHerDecorator decorator) throws GenericException;

	public void getInvoiceByNo(SafeHerDecorator decorator)throws GenericException;

	public void rideGeneralHistory(SafeHerDecorator decorator) throws GenericException;
}
