package com.tgi.safeher.service.manager.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.service.IRideHistoryService;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.IRideService;
import com.tgi.safeher.service.IUserRatingService;
import com.tgi.safeher.service.manager.IRideManager;
@Service
@Transactional
@Scope("prototype")
public class RideManager implements IRideManager {

	@Autowired
	private IRideRequestResponseService iRideRequestResponseService;
	
	@Autowired
	private IRideService rideService;
	
	@Autowired
	private IUserRatingService userRatingService;
	
	@Autowired
	private IRideHistoryService rideHistoryService;
	
	@Override
	public void getColorManagment(SafeHerDecorator decorator) throws GenericException {
		iRideRequestResponseService.getColorInfo(decorator);
	}

	@Override
	public void startPerRide(SafeHerDecorator decorator) throws GenericException {
		iRideRequestResponseService.preRideStart(decorator);		
	}

	@Override
	public void getReasons(SafeHerDecorator decorator) throws GenericException {
		iRideRequestResponseService.getReasons(decorator);
		
	}

	@Override
	public void getColorMatch(SafeHerDecorator decorator)
			throws GenericException, IOException {
		iRideRequestResponseService.matchColorManagmentV2(decorator);

	}

	@Override
	public void getColorMatchV2(SafeHerDecorator decorator)
			throws GenericException, IOException {
		iRideRequestResponseService.matchColorManagmentV2(decorator);
	}

	@Override
	public void reachedStartDestination(SafeHerDecorator decorator) throws GenericException, IOException {
		iRideRequestResponseService.reachedStartDestination(decorator);		
	}

	@Override
	public void justReached(SafeHerDecorator decorator) throws GenericException, IOException {
		rideService.jusReached(decorator);
	}

	@Override
	public void startRide(SafeHerDecorator decorator) throws GenericException {
//		rideService.startRide(decorator);
		rideService.startRideV2(decorator);
	}

	@Override
	public void endRide(SafeHerDecorator decorator) throws GenericException {
		rideService.endRide(decorator);	
	}

	@Override
	public void endRideV2(SafeHerDecorator decorator) throws GenericException {
		// rideService.endRideV2(decorator);

		RideBean rideBean = (RideBean) decorator.getDataBean();	
		RideEntity rideEntity = rideService.getRideInfo(rideBean.getRideNo());
		if (rideEntity.getIsGifted()!=null && rideEntity.getIsGifted().equals("0")) {
//			rideService.endRideV3(decorator);
			rideService.endRideV4(decorator);
		} else if (rideEntity.getIsGifted()!=null && rideEntity.getIsGifted().equals("1")) {
			rideService.giftEndRideV2(decorator);
		}

	}

	@Override
	public void getInvoiceInfo(SafeHerDecorator decorator) throws GenericException {
		rideService.getInvoiceInfo(decorator);
	}

	@Override
	public void confirmEndRide(SafeHerDecorator decorator) throws GenericException {
		rideService.confirmEndRide(decorator);		
	}

	@Override
	public void getClientToken(SafeHerDecorator decorator) throws GenericException {
		rideService.getkeyToken(decorator);		
	}

	@Override
	public void rideBillingTransaction(SafeHerDecorator decorator)
			throws GenericException {
		rideService.rideBillingTransaction(decorator);		
	}

	@Override
	public void midEndRide(SafeHerDecorator decorator) throws GenericException {
		rideService.midEndRide(decorator);	
	}

	@Override
	public void getLatestShareSummary(SafeHerDecorator decorator) throws GenericException {
		rideHistoryService.getLatestShareSummary(decorator);	
	}

	@Override
	public void rideShortEarningHistory(SafeHerDecorator decorator)
			throws GenericException {
		rideHistoryService.shortEarningHistory(decorator);		
	}

	@Override
	public void userRatingHistory(SafeHerDecorator decorator)
			throws GenericException {
		userRatingService.detailUserRating(decorator);
	}

	@Override
	public void getRecentRides(SafeHerDecorator decorator)
			throws GenericException {
		rideHistoryService.getRecentRides(decorator);	
		
	}

	@Override
	public void getRideInfo(SafeHerDecorator decorator) throws GenericException {
		rideHistoryService.getRideInfo(decorator);		
	}

	@Override
	public void getPaymentByFilter(SafeHerDecorator decorator)
			throws GenericException {
		rideHistoryService.paymentFilter(decorator);		
	}

	@Override
	public void getInvoiceByNo(SafeHerDecorator decorator)
			throws GenericException {
		rideHistoryService.getInvoiceByNo(decorator);			
	}

	@Override
	public void addGiftedRide(SafeHerDecorator decorator)
			throws GenericException {
		try {
			rideService.addGitedRide(decorator);
		} catch (DataAccessException e) {
			throw new GenericException(e);
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	@Override
	public void getGiftedRide(SafeHerDecorator decorator)
			throws GenericException {
		rideService.getGiftedRides(decorator);	
	}

	@Override
	public void cancelRequestByDriver(SafeHerDecorator decorator)
			throws GenericException {
		//TODO:Check
			iRideRequestResponseService.cancelRequestByDriver(decorator);
			/*iRideRequestResponseService.cancelRequestByDriverV2(decorator);*/
	
	}

	@Override
	public void reportUserRating(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		userRatingService.reportUserRating(decorator);

	}

	@Override
	public void rideGeneralHistory(SafeHerDecorator decorator)
			throws GenericException {
		rideHistoryService.rideGeneralHistory(decorator);
	}
}
