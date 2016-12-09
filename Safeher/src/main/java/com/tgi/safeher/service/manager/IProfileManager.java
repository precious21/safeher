package com.tgi.safeher.service.manager;

import java.util.List;

import com.tgi.safeher.beans.VehicleMakeInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IProfileManager {

	public void licenceDetail(SafeHerDecorator decorator)
			throws GenericException;

	public void getVehicleMake(SafeHerDecorator decorator)
			throws GenericException;

	public void getVehicleModel(SafeHerDecorator decorator)
			throws GenericException;

	public void addCreditCard(SafeHerDecorator decorator)
			throws GenericException;

	public void defaultCreatitCard(SafeHerDecorator decorator)
			throws GenericException;

	public void addBankAccount(SafeHerDecorator decorator)
			throws GenericException;

	public void getBankList(SafeHerDecorator decorator) throws GenericException;
	
	public void getBankInfo(SafeHerDecorator decorator) throws GenericException;

	public void getVehicleColor(SafeHerDecorator decorator)
			throws GenericException;
	
	public void addVehicleInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void logOut(SafeHerDecorator decorator)
			throws GenericException;

	public void addAddress(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getCountryList(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getStateList(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getZipCodeList(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getCityList(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getLicenceInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void getVehicleInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void updateVehicleInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void updateLicenceInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void updateBankInfo(SafeHerDecorator decorator)
			throws GenericException;

	public void getPaymentMethod(SafeHerDecorator decorator)
			throws GenericException;

	public void addPayPalInfo(SafeHerDecorator decorator) throws GenericException;

	public void getMultipleBankInfoByDriver(SafeHerDecorator decorator) throws GenericException;

	public void setDefaultDriver(SafeHerDecorator decorator) throws GenericException;

	public void getVehicelList(SafeHerDecorator decorator) throws GenericException;

	public void defaultVehicle(SafeHerDecorator decorator)throws GenericException;

	public void passengerPersonalEdit(SafeHerDecorator decorator) throws GenericException;

	public void driverGeneralConfig(SafeHerDecorator decorator)throws GenericException;

	public void deleteVehicleInfo(SafeHerDecorator decorator) throws GenericException;

	public void setInActiveOrDeleteBankInfo(SafeHerDecorator decorator) throws GenericException;

	public void getProfileInformation(SafeHerDecorator decorator) throws GenericException;

	public void checkUserInformation(SafeHerDecorator decorator) throws GenericException;

	/*public void userInformation(SafeHerDecorator decorator) throws GenericException;
*/
}
