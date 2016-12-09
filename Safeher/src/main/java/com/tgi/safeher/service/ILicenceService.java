package com.tgi.safeher.service;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.DriverInfoEntity;
import com.tgi.safeher.entity.StateProvinceEntity;

public interface ILicenceService {

	public boolean updateDriverInfo(SafeHerDecorator decorator , String driverInfoId) throws GenericException;
	
	public boolean addLicenceDetail(SafeHerDecorator decorator) throws GenericException;
	
	public AppUserBean getAppUserById(String accountId);
	
	public StateProvinceEntity getStateProvinceById(int StateId);
	
	public DriverInfoEntity getDriverInfoEntityById(String LicenceNo);
	
	public boolean makeHistory(SafeHerDecorator decorator);
	
	public boolean checkLicenceNoIsUnique(String LicenceNo);
	
	public void licenceDetail(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getDriverLicenceInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void updateDriverLicenceInfo(SafeHerDecorator decorator)
			throws GenericException;
}
