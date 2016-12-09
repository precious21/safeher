package com.tgi.safeher.service;

import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideDetailEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserPromotionUseEntity;

public interface IAsync {

	public void endRideAsyncCall(RideBillEntity rideBillEntity, RideBean rideBean, Double rideAmount,
			Double totalDiscount, CreditCardInfoBean creditCardInfoBean, UserPromotionEntity userPromotionEntity,
			UserPromotionUseEntity userPromotionUseEntity, RideDetailEntity rideDetail, RideEntity rideEntity);

	public void generateAsyncReferalCode(SafeHerDecorator decorator) throws GenericException;
}
