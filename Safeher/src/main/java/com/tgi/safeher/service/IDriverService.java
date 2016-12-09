package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IDriverService {

	public void saveDriverInfoSignUp(SafeHerDecorator decorator) throws GenericException;

	public void signUpUser(SafeHerDecorator decorator) throws GenericException;

	public void saveLocationAddress(SafeHerDecorator decorator) throws GenericException;

	public void saveSSNNUmber(SafeHerDecorator decorator) throws GenericException;

	public void userSingIn(SafeHerDecorator decorator) throws GenericException;

	public void saveFcmToken(SafeHerDecorator decorator) throws GenericException;

	public void matchPassword(SafeHerDecorator decorator) throws GenericException;

	public void sendEmailToClient(SafeHerDecorator decorator) throws GenericException;

	public void saveNewPassword(SafeHerDecorator decorator) throws GenericException;

	public void saveUserFavorities(SafeHerDecorator decorator) throws GenericException;

	public void getUserFavorities(SafeHerDecorator decorator) throws GenericException;

	public void deleteUserFavorities(SafeHerDecorator decorator) throws GenericException;

	// public AppUserBean getAppUserBean(String id) throws GenericException;
	public void logOutUser(SafeHerDecorator decorator) throws GenericException;

	public void logOutFromAnotherDevice(SafeHerDecorator decorator) throws GenericException;

	public void saveQualifiedVehicalOrDisclaimer(SafeHerDecorator decorator) throws GenericException;

	public void appUserVisibility(SafeHerDecorator decorator) throws GenericException;

	public String appUserActiveDeActive(SafeHerDecorator decorator) throws GenericException;

	public void driverPassengerRating(SafeHerDecorator decorator) throws GenericException;

	public void userSocialSingIn(SafeHerDecorator decorator) throws GenericException;

	public void deleteUnneccesaryActiveDriver() throws GenericException;

	public void inCompleteSignDataEmail() throws GenericException;
	// Mongo
	public void appUserVisibilityV2(SafeHerDecorator decorator) throws GenericException;

	public void deleteUnneccesaryActiveDriverMongo() throws GenericException;
	
	public void findIncompleteUsers()
			throws GenericException;
}
