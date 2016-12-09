package com.tgi.safeher.service.manager.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.service.IAddressService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.ILicenceService;
import com.tgi.safeher.service.IPaymentService;
import com.tgi.safeher.service.IProfilService;
import com.tgi.safeher.service.IVehicleService;
import com.tgi.safeher.service.manager.IProfileManager;

@Service
@Transactional
@Scope("prototype")
public class ProfileManager implements IProfileManager {
	
	@Autowired
	private ILicenceService licenceService;
	
	@Autowired
	private IVehicleService vehicleService;
		
	@Autowired
	private IPaymentService paymentService;
	
	@Autowired
	private IDriverService driverService;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private IProfilService profileService;

	@Override
	public void licenceDetail(SafeHerDecorator decorator)
			throws GenericException {

		licenceService.licenceDetail(decorator);
	}

	@Override
	public void getVehicleMake(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.getVehicleMake(decorator);

	}

	@Override
	public void getVehicleModel(SafeHerDecorator decorator)
			throws GenericException {

		vehicleService.getVehicleModel(decorator);
	}

	@Override
	public void addCreditCard(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.saveCreditCardInfo(decorator);
	}

	@Override
	public void addBankAccount(SafeHerDecorator decorator)
			throws GenericException {
		//paymentService.saveBankAccountInfo(decorator);
		paymentService.saveBankAccountInfoV2(decorator);
		
	}

	@Override
	public void getBankList(SafeHerDecorator decorator) throws GenericException {
	
		paymentService.getBankList(decorator);
	}

	@Override
	public void getVehicleColor(SafeHerDecorator decorator)
			throws GenericException {
		
		 vehicleService.getVehicleColor(decorator);
		 	
	}

	@Override
	public void addVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {

		vehicleService.saveVehicleInfo(decorator);
	}

	@Override
	public void logOut(SafeHerDecorator decorator) throws GenericException {

		driverService.logOutUser(decorator);
	}

	@Override
	public void addAddress(SafeHerDecorator decorator) throws GenericException {
		addressService.saveUserAddress(decorator);
		
	}

	@Override
	public void getCountryList(SafeHerDecorator decorator)
			throws GenericException {
		addressService.getCountryList(decorator);
		
	}

	@Override
	public void getStateList(SafeHerDecorator decorator)
			throws GenericException {
		addressService.getStateList(decorator);
		
	}

	@Override
	public void getZipCodeList(SafeHerDecorator decorator)
			throws GenericException {
		addressService.getZipCodeList(decorator);
		
	}

	@Override
	public void getCityList(SafeHerDecorator decorator) throws GenericException {
		addressService.getCityList(decorator);
	}

	@Override
	public void getBankInfo(SafeHerDecorator decorator) throws GenericException {
		paymentService.getBankInfo(decorator);
		
	}

	@Override
	public void getLicenceInfo(SafeHerDecorator decorator)
			throws GenericException {
		licenceService.getDriverLicenceInfo(decorator);
		
	}

	@Override
	public void getVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.getVehicleInfo(decorator);
		
	}

	@Override
	public void updateVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.updateVehicleInfo(decorator);
		
	}

	@Override
	public void updateLicenceInfo(SafeHerDecorator decorator)
			throws GenericException {
		licenceService.updateDriverLicenceInfo(decorator);
		
	}

	@Override
	public void updateBankInfo(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.updateBankInfo(decorator);
		
	}

	@Override
	public void getPaymentMethod(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.getPassengerPaymentDetail(decorator);
		
	}

	@Override
	public void addPayPalInfo(SafeHerDecorator decorator) throws GenericException {
		paymentService.addPayPalInfo(decorator);
	}

	@Override
	public void defaultCreatitCard(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.defaultCreatitCard(decorator);
		
	}

	@Override
	public void getMultipleBankInfoByDriver(SafeHerDecorator decorator) throws GenericException {
		paymentService.getMultipleDrivers(decorator);
	}

	@Override
	public void setDefaultDriver(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.setDefaultBankInfo(decorator);		
	}

	@Override
	public void getVehicelList(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.getvehicleList(decorator);
		
	}

	@Override
	public void defaultVehicle(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.setDefaultVehicle(decorator);
		
	}

	@Override
	public void passengerPersonalEdit(SafeHerDecorator decorator)
			throws GenericException {
	//	profileService.passengerPeronalEdit(decorator);
	}

	@Override
	public void driverGeneralConfig(SafeHerDecorator decorator)
			throws GenericException {
		profileService.driverGeneralConfig(decorator);	
	}

	@Override
	public void deleteVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {
		vehicleService.deleteVehicleInfo(decorator);		
	}

	@Override
	public void setInActiveOrDeleteBankInfo(SafeHerDecorator decorator)
			throws GenericException {
		paymentService.setInActiveOrDeleteBankInfo(decorator);
	}

	@Override
	public void getProfileInformation(SafeHerDecorator decorator)
			throws GenericException {
//		profileService.getUserProfileInformation(decorator);
		profileService.getUserProfileInformationV2(decorator);
		
	}

	@Override
	public void checkUserInformation(SafeHerDecorator decorator)
			throws GenericException {
		profileService.checkUserInformation(decorator);		
	}

}
