package com.tgi.safeher.map.service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IMapService {

	public void getAllPassangerDriver(SafeHerDecorator decorator) throws GenericException;
	public void savePassangerDriver(SafeHerDecorator decorator) throws GenericException;
	public void savePassangerDriverMongo(SafeHerDecorator decorator) throws GenericException;
}
