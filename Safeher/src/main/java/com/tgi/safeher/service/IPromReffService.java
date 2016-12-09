package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IPromReffService {

	public void generatePromReffCode(SafeHerDecorator decorator)
			throws GenericException;
	
	public void  userPromotion(SafeHerDecorator decorator)
			throws GenericException;

	public void getPromotions(SafeHerDecorator decorator)throws GenericException;

	public void activeUserPromotion(SafeHerDecorator decorator) throws GenericException;
	public void safeHerApiPromReffCodeGeneration(SafeHerDecorator decorator)
			throws GenericException;
	public void generateReferralCode(SafeHerDecorator decorator)
			throws GenericException;
	public void sendReferralToFrnds(SafeHerDecorator decorator)
			throws GenericException;

	public void inActivePromotions() throws GenericException;
}
