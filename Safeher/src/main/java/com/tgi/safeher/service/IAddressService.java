package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IAddressService {

	public void saveUserAddress(SafeHerDecorator decorator) throws GenericException;
	
	public void getCountryList(SafeHerDecorator decorator) throws GenericException;
	
	public void getStateList(SafeHerDecorator decorator) throws GenericException;
	
	public void getCityList(SafeHerDecorator decorator) throws GenericException;
	
	public void getZipCodeList(SafeHerDecorator decorator) throws GenericException;
	
}
