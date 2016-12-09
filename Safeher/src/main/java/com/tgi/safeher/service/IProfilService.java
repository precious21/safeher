package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IProfilService {
	public void passengerPeronalEdit(SafeHerDecorator decorator)
			throws GenericException;

	public void driverGeneralConfig(SafeHerDecorator decorator)
			throws GenericException;

	public void getUserProfileInformation(SafeHerDecorator decorator) throws GenericException;

	public void checkUserInformation(SafeHerDecorator decorator) throws GenericException;

	public void getUserProfileInformationV2(SafeHerDecorator decorator) throws GenericException;
	
	public String verifyUser(String code, String appUserId, String type);
}
