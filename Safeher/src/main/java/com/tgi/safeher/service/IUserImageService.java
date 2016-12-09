package com.tgi.safeher.service;

import java.io.Serializable;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IUserImageService extends Serializable {

	public void saveUserImage(SafeHerDecorator decorator) throws GenericException;

	public void saveDriverUserImage(SafeHerDecorator decorator) throws GenericException;

	public void appUserImage(SafeHerDecorator decorator) throws GenericException;

	public void saveElectronicsResource(SafeHerDecorator decorator) throws GenericException;

	public void saveElectronicsResourceV2(SafeHerDecorator decorator) throws GenericException;

	public void fetchElectronicsResource(SafeHerDecorator decorator) throws GenericException;

	public void removeElectronicsResource(SafeHerDecorator decorator) throws GenericException;
}
