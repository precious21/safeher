package com.tgi.safeher.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface ICharityService {

	public void saveUserSelectedCharities(SafeHerDecorator decorator) throws GenericException;
	public void getCharities(SafeHerDecorator decorator) throws GenericException;
	public void getCharitiesV2(SafeHerDecorator decorator) throws GenericException;
	public void getDrivers(SafeHerDecorator decorator) throws GenericException;
}
