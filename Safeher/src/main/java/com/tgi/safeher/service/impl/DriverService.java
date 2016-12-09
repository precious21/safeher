package com.tgi.safeher.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.AppUserRegFlowBean;
import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.beans.LogOutBean;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.beans.UserFavoritiesBean;
import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.AddressTypeEnum;
import com.tgi.safeher.common.enumeration.BioMetricTypeEnum;
import com.tgi.safeher.common.enumeration.EmailTypeEnum;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.UserRegFlowEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AddressEntity;
import com.tgi.safeher.entity.AddressTypeEntity;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusLogEntity;
import com.tgi.safeher.entity.AppUserRegTrackEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.BiometricTypeEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.CityEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.DriverInfoEntity;
import com.tgi.safeher.entity.FavorityTypeEntity;
import com.tgi.safeher.entity.PersonAddressEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonDetailHistoryEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserFavoritiesEntity;
import com.tgi.safeher.entity.UserInformationEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserLoginTypeEntity;
import com.tgi.safeher.entity.UserRatingDetailEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.UserSelectedCharitiesEntity;
import com.tgi.safeher.entity.ZipCodeEntity;
import com.tgi.safeher.map.dao.MapDao;
import com.tgi.safeher.map.service.IGoogleMapServices;
import com.tgi.safeher.repo.ActiveDriverCustomRepository;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.IAsync;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.converter.CharityConverter;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.CommonUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.Email;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;
import com.tgi.safeher.utils.ValidationUtil;

@Service
@Transactional
@Scope("prototype")
public class DriverService implements IDriverService{
	private static final Logger logger = Logger.getLogger(DriverService.class);
 
	@Autowired
	private SignUpConverter signUpConverter;
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private MapDao mapDao;
	
	@Autowired
	private IGoogleMapServices iGoogleMapServices;
	
	@Autowired
	private CharityConverter charityConverter;
	
	@Autowired
	private Email email;
	
	@Autowired
	private PushIOS pushIOS;
	
	@Autowired
	private PushAndriod pushAndriod;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private IAsyncEmailService asyncEmailServiceImpl;
	
	@Autowired
	private ActiveDriverCustomRepository activeDriverCustomRepository;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverRepository;
	
	@Autowired
	private IAsyncEmailService iAsyncEmailService;
	

	@Autowired
	private IAsync iAsyncService;
	
	@Override
	public void saveDriverInfoSignUp(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveDriverInfoSignUp  with AppUserBean "+ bean + "******");
		signUpConverter.validateDriver(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				if (StringUtil.isEmpty(bean.getAppUserId())) {
					// if (!bean.getIsSocial().equals("1")) {
					if (StringUtil.isNotEmpty(bean.getEmail())
							&& appUserDao.checkIfEmailExistsV2(bean.getEmail(), bean.getIsDriver())) {
						if (!ValidationUtil.isEmailAddress(bean.getEmail())) {
							throw new GenericException("Invalid email address");
						}
					} else {
						throw new GenericException(
								"Provided email is already registered");
					}
					//for new user
					if (StringUtil.isNotEmpty(bean.getPhoneNumber())
							&& !appUserDao.checkIfPhoneNumberExistsV2(bean.getPhoneNumber(), bean.getIsDriver())) {
						throw new GenericException(
								"Provided phone number is already registered");
					}
					// }
				}else{
					//for updating user
					if (StringUtil.isNotEmpty(bean.getPhoneNumber())
							&& !appUserDao.checkIfPhoneNumberExistsForUpdateV2(
									new Integer(bean.getAppUserId()), bean.getPhoneNumber(), bean.getIsDriver())) {
						throw new GenericException(
								"Provided phone number is already registered");
					}
					
				}
				saveAppUser(decorator);
			}catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Sign up failed");
		}

	}
	
	@Override
	public void signUpUser(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveDriverInfoSignUp  with AppUserBean "+ bean + "******");
		signUpConverter.validateSignUpUser(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				if (StringUtil.isEmpty(bean.getAppUserId())) {
					// if (!bean.getIsSocial().equals("1")) {
					if (StringUtil.isNotEmpty(bean.getEmail())
							&& appUserDao.checkIfEmailExistsV2(bean.getEmail(), bean.getIsDriver())) {
						if (!ValidationUtil.isEmailAddress(bean.getEmail())) {
							throw new GenericException("Invalid email address");
						}
					} else {
						throw new GenericException(
								"Provided email is already registered");
					}
					//for new user
					if (StringUtil.isNotEmpty(bean.getPhoneNumber())
							&& !appUserDao.checkIfPhoneNumberExistsV2(bean.getPhoneNumber(), bean.getIsDriver())) {
						throw new GenericException(
								"Provided phone number is already registered");
					}
					// }
				}else{
					//for updating user
					if (StringUtil.isNotEmpty(bean.getPhoneNumber())
							&& !appUserDao.checkIfPhoneNumberExistsForUpdateV2(
									new Integer(bean.getAppUserId()), bean.getPhoneNumber(), bean.getIsDriver())) {
						throw new GenericException(
								"Provided phone number is already registered");
					}
					
				}
				saveSignUpUser(decorator);
			}catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Sign up failed");
		}

	}
	
	@Override
	public void saveLocationAddress(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveLocationAddress  with AppUserBean "+ bean + "******");
		signUpConverter.validateLocationAddress(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				saveAddress(decorator);
			}catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Saving location failed");
		}

	}
	
	@Override
	public void saveQualifiedVehicalOrDisclaimer(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveQualifiedVehicalOrDisclaimer  with AppUserBean "+ bean + "******");
		
		if(StringUtil.isEmpty(bean.getAppUserId())){
			throw new GenericException("Please provide appUserId");
		}
		if(StringUtil.isEmpty(bean.getIsFromWindow())){
			throw new GenericException("Please provide isFromWindow");
		}
		if(StringUtil.isEmpty(bean.getDisclosureOrVehicle())){
			throw new GenericException("Please provide disclosureOrVehicle");
		}
		if(bean.getDisclosureOrVehicle().equals("1")){
			if(StringUtil.isEmpty(bean.getIsQualifiedVehical())){
				throw new GenericException("Please provide isQualifiedVehical");
			}	
		}
		
		try {
			AppUserEntity appUserEntity = appUserDao.findById(
					AppUserEntity.class, new Integer(bean.getAppUserId()));
			if(appUserEntity != null){
				
				//start asynchronous saving appUserRegFlow
				int userRegFlow = 0;
				if(bean.getDisclosureOrVehicle().equals("1")){
					userRegFlow = UserRegFlowEnum.QualifiedVehicle.getValue();
					
					//save qualified vehicle into driver_info table
					DriverInfoEntity driverInfoEntity = new DriverInfoEntity();
					driverInfoEntity.setIsQualifiedVehicle(bean.getIsQualifiedVehical());
					appUserDao.saveOrUpdate(driverInfoEntity);
					appUserEntity.setDriverInfo(driverInfoEntity);
					
				}else if(bean.getDisclosureOrVehicle().equals("0")){
					userRegFlow = UserRegFlowEnum.Disclosure.getValue();
				}
				
				AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
				appUserRegFlowBean.setAppUserId(appUserEntity.getAppUserId()+"");
				appUserRegFlowBean.setIsFromApp(bean.getIsFromWindow());
				appUserRegFlowBean.setStepCode(userRegFlow+"");
				appUserRegFlowBean.setIsCompleted("1");
				asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
				//end asynchronous saving appUserRegFlow

				decorator.setResponseMessage("Successful");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}else{
				throw new GenericException("User dont exists");
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}

	}
	
	@Override
	public void saveSSNNUmber(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveSSNNUmber  with AppUserBean "+ bean + "******");
		
		if(StringUtil.isEmpty(bean.getAppUserId())){
			throw new GenericException("Please provide appUserId");
		}
		if(StringUtil.isEmpty(bean.getUserInformationValue())){
			throw new GenericException("Please provide userInformationValue");
		}
		
		try {
			AppUserEntity appUserEntity = appUserDao.findById(
					AppUserEntity.class, new Integer(bean.getAppUserId()));
			if(appUserEntity != null){
				if(appUserEntity.getPersonDetail() != null){
//					appUserEntity.getPersonDetail().setSsnNumber(
//							EncryptDecryptUtil.encrypt(bean.getSsnNumber()));
//					appUserDao.saveOrUpdate(appUserEntity.getPersonDetail());
					
					UserInformationEntity informationEntity = (UserInformationEntity) appUserDao.
							findByIdParamCommonStr("UserInformationEntity", "appUserId", 
									EncryptDecryptUtil.encrypt(appUserEntity.getAppUserId()+""));
					if(informationEntity == null){
						informationEntity = new UserInformationEntity();
					}
					informationEntity.setAppUserId(
							EncryptDecryptUtil.encrypt(appUserEntity.getAppUserId()+""));
					informationEntity.setInfoDate(DateUtil.getCurrentTimestamp());
					informationEntity.setValue(EncryptDecryptUtil.encrypt256(bean.getUserInformationValue()));
					appUserDao.saveOrUpdate(informationEntity);

					//start asynchronous saving appUserRegFlow
					AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
					appUserRegFlowBean.setAppUserId(appUserEntity.getAppUserId()+"");
					appUserRegFlowBean.setIsFromApp(bean.getIsFromWindow());
					appUserRegFlowBean.setStepCode(
							UserRegFlowEnum.SecurityCheck.getValue()+"");
					appUserRegFlowBean.setIsCompleted("1");
					asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
					//end asynchronous saving appUserRegFlow
					
					decorator.setResponseMessage("Security added successful");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				}
			}else{
				throw new GenericException("User dont exists");
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}

	}
	
	private void saveAddress(SafeHerDecorator decorator){
		
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveAddress with AppUserBean "+bean +"  ******");
		String responseMessage = "saving location successfull";

		UserLoginEntity userLoginEntity = appUserDao.findByIdParam2(
				new Integer(bean.getAppUserId()));
		if(userLoginEntity != null){

			//savingAddress
			CityEntity cityEntity = new CityEntity();
			CityEntity mailingCityEntity = new CityEntity();
			StateProvinceEntity stateProvinceEntity = new StateProvinceEntity();
			StateProvinceEntity mailingStateProvinceEntity = new StateProvinceEntity();
			CountryEntity countryEntity = new CountryEntity();
			ZipCodeEntity zipCodeEntity = new ZipCodeEntity();
			ZipCodeEntity mailingZipCodeEntity = new ZipCodeEntity();
			AddressTypeEntity addressTypeEntity = new AddressTypeEntity();
			AddressEntity addressEntity = new AddressEntity();
			
			//createCountry
//			if(StringUtil.isNotEmpty(bean.getCountryName())){
//				countryEntity = mapDao.findBy(
//						CountryEntity.class, "name", bean.getCountryName().trim());
//				if(countryEntity == null){
//					countryEntity = new CountryEntity();
//					countryEntity.setName(bean.getCountryName().trim());
////						countryEntity.setAbbreviation(bean.getCountryCode());
//					mapDao.saveOrUpdate(countryEntity);
//				}
//			}

			//createState
//			if(StringUtil.isNotEmpty(bean.getStateName())){
//				stateProvinceEntity = mapDao.findBy(
//						StateProvinceEntity.class, "name", bean.getStateName().trim());
//				if(stateProvinceEntity == null){
//					stateProvinceEntity = new StateProvinceEntity();
//					stateProvinceEntity.setName(bean.getStateName().trim());
////						stateProvinceEntity.setCode(bean.getStateCode());
//					stateProvinceEntity.setCountry(countryEntity);
//					mapDao.saveOrUpdate(stateProvinceEntity);
//				}
//				
//			}
			
			//countryId 5 = United State
			bean.setCountryName("United States");
			countryEntity.setCountryId(5);
			stateProvinceEntity.setStateId(
					new Integer(bean.getStateId()));
			if(StringUtil.isNotEmpty(bean.getMailingStateId())){
				mailingStateProvinceEntity.setStateId(
						new Integer(bean.getMailingStateId()));
				
			}

			//createCity
			if(StringUtil.isNotEmpty(bean.getCityName())){
				cityEntity = mapDao.findBy(CityEntity.class, "name", bean.getCityName().trim());
				if(cityEntity == null){
					cityEntity = new CityEntity();
					cityEntity.setName(bean.getCityName().trim());
					cityEntity.setStateProvince(stateProvinceEntity);
					cityEntity.setCountry(countryEntity);
					mapDao.saveOrUpdate(cityEntity);
				}
			}
			if(StringUtil.isNotEmpty(bean.getMailingCityName())){
				mailingCityEntity = mapDao.findBy(CityEntity.class, "name", bean.getMailingCityName().trim());
				if(mailingCityEntity == null){
					mailingCityEntity = new CityEntity();
					mailingCityEntity.setName(bean.getMailingCityName().trim());
					mailingCityEntity.setStateProvince(mailingStateProvinceEntity);
					mailingCityEntity.setCountry(countryEntity);
					mapDao.saveOrUpdate(mailingCityEntity);
				}
			}

			//createCityZipCode
			if(StringUtil.isNotEmpty(bean.getMailingZipCode())){
//				mailingZipCodeEntity = (ZipCodeEntity) appUserDao.findByIdParamCommon(
//						"ZipCodeEntity", "city.cityId", mailingCityEntity.getCityId());
//				if(mailingZipCodeEntity == null){
//					mailingZipCodeEntity = new ZipCodeEntity();
//					mailingZipCodeEntity.setZipCode(bean.getZipCode());
//					mailingZipCodeEntity.setCity(cityEntity);
//					mapDao.saveOrUpdate(mailingZipCodeEntity);
//				}
					mailingZipCodeEntity.setZipCode(bean.getZipCode());
					mailingZipCodeEntity.setCity(cityEntity);
					mapDao.saveOrUpdate(mailingZipCodeEntity);
				}
			if(StringUtil.isNotEmpty(bean.getZipCode())){
//				zipCodeEntity = (ZipCodeEntity) appUserDao.findByIdParamCommon(
//						"ZipCodeEntity", "city.cityId", cityEntity.getCityId());
//				if(zipCodeEntity == null){
//					zipCodeEntity = new ZipCodeEntity();
//					zipCodeEntity.setZipCode(bean.getZipCode());
//					zipCodeEntity.setCity(cityEntity);
//					mapDao.saveOrUpdate(zipCodeEntity);
//				}
					zipCodeEntity.setZipCode(bean.getZipCode());
					zipCodeEntity.setCity(cityEntity);
					mapDao.saveOrUpdate(zipCodeEntity);
				}

			PersonAddressEntity personAddressEntity = appUserDao.findAddressByAddressTypeAndPerson(
					userLoginEntity.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Residential.getValue());

			if(personAddressEntity == null){
				personAddressEntity = new PersonAddressEntity();
			}else{
				responseMessage = "updating location successful";
				addressEntity = personAddressEntity.getAddress();
			}
			addressEntity.setFullAddress(bean.getLocation());
			addressEntity.setCountry(countryEntity);
			addressEntity.setStateProvince(stateProvinceEntity);
			addressEntity.setCity(cityEntity);
			addressEntity.setZipCode(zipCodeEntity);
			//addressEntity.setZipCode(zipCodeEntity);
			appUserDao.saveOrUpdate(addressEntity);
			
			personAddressEntity.setAddress(addressEntity);
			personAddressEntity.setIsActive("1");
			personAddressEntity.setPerson(userLoginEntity.getAppUser().getPerson());
			personAddressEntity.setPersonDetail(
					userLoginEntity.getAppUser().getPersonDetail());
			addressTypeEntity.setAddressTypeId(
					AddressTypeEnum.Residential.getValue());
			personAddressEntity.setAddressType(addressTypeEntity);
			appUserDao.saveOrUpdate(personAddressEntity);
			
			addressEntity = new AddressEntity();
			PersonAddressEntity personAddressEntityMail = appUserDao.findAddressByAddressTypeAndPerson(
					userLoginEntity.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Mailing.getValue());

			if(personAddressEntityMail == null){
				personAddressEntityMail = new PersonAddressEntity();
			}else{
				responseMessage = "updating location successful";
				addressEntity = personAddressEntityMail.getAddress();
			}
			addressEntity.setFullAddress(bean.getMailingAddress());
			addressEntity.setCountry(countryEntity);
			addressEntity.setStateProvince(mailingStateProvinceEntity);
			addressEntity.setCity(mailingCityEntity);
			addressEntity.setZipCode(mailingZipCodeEntity);
			//addressEntity.setZipCode(zipCodeEntity);
			appUserDao.saveOrUpdate(addressEntity);
			
			personAddressEntityMail.setAddress(addressEntity);
			personAddressEntityMail.setIsActive("0");
			personAddressEntityMail.setPerson(userLoginEntity.getAppUser().getPerson());
			personAddressEntityMail.setPersonDetail(
					userLoginEntity.getAppUser().getPersonDetail());
			addressTypeEntity.setAddressTypeId(
					AddressTypeEnum.Mailing.getValue());
			personAddressEntityMail.setAddressType(addressTypeEntity);
			appUserDao.saveOrUpdate(personAddressEntityMail);
			
			//send email for no Massachusetts state
			if(!stateProvinceEntity.getStateId().equals(23) && 
					!mailingStateProvinceEntity.getStateId().equals(23)){
				//Start Sending Mail
				Map<String, String> map = new HashMap<String,String>();
				map.put("Name", userLoginEntity.getAppUser().getPerson().getFirstName());
				map.put("contactUsLink", "www.gosafr.com/contact");
				iAsyncEmailService.sendEmail(EmailTypeEnum.InvalidState.getValue(), 
						userLoginEntity.getAppUser().getAppUserId()+"", 
						userLoginEntity.getAppUser().getPersonDetail().getPrimaryEmail(), map);
				//End Mailing	
			}

			//start asynchronous saving appUserRegFlow
			AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
			appUserRegFlowBean.setAppUserId(userLoginEntity.getAppUser().getAppUserId()+"");
			appUserRegFlowBean.setIsFromApp(bean.getIsFromWindow());
			appUserRegFlowBean.setStepCode(
					UserRegFlowEnum.Address.getValue()+"");
			appUserRegFlowBean.setIsCompleted("1");
			asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
			//end asynchronous saving appUserRegFlow
			
		}else{
			decorator.setResponseMessage("User dont exists");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			return;
		}
				
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage(responseMessage);
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

	}
	
	private void saveSignUpUser(SafeHerDecorator decorator) throws GenericException{
		
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveSignUpUser with AppUserBean "+bean +"  ******");
		String responseMessage = "Sign Up successfull";
		PersonEntity personEntity = new PersonEntity();
		UserLoginEntity userLoginEntity = new UserLoginEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		PersonDetailEntity personDetailEntity = new PersonDetailEntity();
		PersonDetailHistoryEntity personDetailHistoryEntity = new PersonDetailHistoryEntity();
		UserLoginTypeEntity userLoginTypeEntity = new UserLoginTypeEntity();

		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			userLoginEntity = appUserDao.findByIdParam2(
					new Integer(bean.getAppUserId()));
			if(userLoginEntity == null){
				userLoginEntity = new UserLoginEntity();
				appUserEntity = new AppUserEntity();
				personEntity = new PersonEntity();
				personDetailEntity = new PersonDetailEntity();
				userLoginTypeEntity.setName(bean.getIsFromWindow());
				appUserDao.saveOrUpdate(userLoginTypeEntity);
				userLoginEntity.setUserLoginType(userLoginTypeEntity);
			}else{
				appUserEntity = userLoginEntity.getAppUser();
				personEntity = userLoginEntity.getAppUser().getPerson();
				personDetailEntity =  userLoginEntity.getAppUser().getPersonDetail();
				responseMessage = "Your profile has been updated";
			}
		}

		//savingPerson
		signUpConverter.convertBeanToPersonEntity(bean, personEntity);
		appUserDao.saveOrUpdate(personEntity);

		//savingPersonDetail
		signUpConverter.convertBeanToPersonDetailEntity(bean, personDetailEntity);
		personDetailEntity.setPerson(personEntity);
		appUserDao.saveOrUpdate(personDetailEntity);

		//savingAppUser
		signUpConverter.convertBeanToAppUserEntity(bean, appUserEntity);
		appUserEntity.setPerson(personEntity);
		appUserEntity.setPersonDetail(personDetailEntity);
		appUserDao.saveOrUpdate(appUserEntity);
		bean.setIsActivated(appUserEntity.getIsActivated());
		((AppUserBean)decorator.getDataBean()).setAppUserId(
				appUserEntity.getAppUserId()+"");
		
		//savingUserLogin
		signUpConverter.convertBeanToUserLoginEntity(bean, userLoginEntity);
		userLoginEntity.setAppUser(appUserEntity);
		String sessionNO = System.currentTimeMillis() +
				"SESSION"+appUserEntity.getAppUserId();
		userLoginEntity.setCurrentSessionNo(sessionNO);
		appUserDao.saveOrUpdate(userLoginEntity);

		//savingPersonDetailHistory
//		signUpConverter.populatePersonDetailHistoryFromPersonDetail(
//				personDetailEntity, personDetailHistoryEntity);
//		appUserDao.saveOrUpdate(personDetailHistoryEntity);
//		if(StringUtil.isNotEmpty(bean.getAppUserId()) && 
//				bean.getIsDriver().equalsIgnoreCase("1")){
//			List<AppUserVehicleEntity> vehicleInfoList = 
//					appUserDao.findListByOject(AppUserVehicleEntity.class, 
//							"appUser", appUserEntity, 0, 10);
//			if(vehicleInfoList != null){
//				List<VehicleInfoBean> vehicleInfoBeanlist = 
//						signUpConverter.convertEntityListBeanList(vehicleInfoList);
//				bean.setVehicaleList(vehicleInfoBeanlist);
//			}
//		}

		//saveSocialImageForPassenger
		if(StringUtil.isNotEmpty(bean.getIsSocial()) && 
				bean.getIsSocial().equals("1") && 
				StringUtil.isNotEmpty(bean.getUserImageUrl()) && 
				bean.getIsDriver().equals("0")){
			if(appUserEntity != null){
				AppUserBiometricEntity appUserBiometricEntity = appUserDao.findByIdParam(
						appUserEntity.getAppUserId());
				if(appUserBiometricEntity == null){
					appUserBiometricEntity = new AppUserBiometricEntity();
					BiometricTypeEntity biometricTypeEntity = new BiometricTypeEntity();
					biometricTypeEntity.setBiometricTypeId(BioMetricTypeEnum.Face.getValue());
					appUserBiometricEntity.setBiometricType(biometricTypeEntity);
				}
				signUpConverter.populateAppUserBiometricEntity(appUserBiometricEntity);
				appUserBiometricEntity.setPath(bean.getUserImageUrl());
				appUserBiometricEntity.setAppUser(appUserEntity);
				if(appUserEntity.getPerson() != null){
					appUserBiometricEntity.setPerson(appUserEntity.getPerson());
				}
				appUserDao.saveOrUpdate(appUserBiometricEntity);
				((AppUserBean)decorator.getDataBean()
						).setUserImageUrl(bean.getUserImageUrl());
			}
		}
		bean.setPassword(null);
		
		//send email for no mail
		if(appUserEntity.getIsDriver() != null && 
				appUserEntity.getIsDriver().equals("1") && 
				personEntity.getSex() != null && 
				personEntity.getSex().equals("M")){
			//Start Sending Mail
			Map<String, String> map = new HashMap<String,String>();
			map.put("Name", userLoginEntity.getAppUser().getPerson().getFirstName());
			map.put("fbLink", "https://www.facebook.com/GoSafr/?fref=ts");
			map.put("tweetLink", "https://twitter.com/gosafr");
			iAsyncEmailService.sendEmail(EmailTypeEnum.NoMale.getValue(), 
					userLoginEntity.getAppUser().getAppUserId()+"", 
					userLoginEntity.getAppUser().getPersonDetail().getPrimaryEmail(), map);
			//End Mailing	
			
		}
//		if(appUserEntity.getIsDriver() != null && 
//				appUserEntity.getIsDriver().equals("1")){

			//start sending verification email
			Map<String, String> emailMap = new HashMap<String, String>();
			emailMap.put("Name", personEntity.getFirstName());
			emailMap.put("Url", "verifyUser");
			emailMap.put("vcode",
					CommonUtil.generateSecureRando("VC" + appUserEntity.getAppUserId() +
							System.currentTimeMillis()));

			asyncEmailServiceImpl.sendEmail(
					EmailTypeEnum.AccountVerification.getValue(), appUserEntity.getAppUserId()+"", bean.getEmail(), emailMap);

			AppUserPhoneEmailStatusEntity appUserPhoneEmailStatusEntity = new AppUserPhoneEmailStatusEntity();
			appUserPhoneEmailStatusEntity.setPending("1");
			appUserPhoneEmailStatusEntity.setNotVerified("1");

			AppUserPhoneEmailStatusLogEntity appUserPhoneEmailStatusLogEntity = new AppUserPhoneEmailStatusLogEntity();
			appUserPhoneEmailStatusLogEntity.setPrimaryEmail("1");
			appUserPhoneEmailStatusLogEntity.setSecondaryEmail("0");
			appUserPhoneEmailStatusLogEntity.setPrimaryCell("0");
			appUserPhoneEmailStatusLogEntity.setCode(EncryptDecryptUtil.
					encryptVerification(emailMap.get("vcode")));
			appUserPhoneEmailStatusLogEntity.setAppUser(appUserEntity);

			asyncServiceImpl.saveUserEmailCode(appUserPhoneEmailStatusEntity, appUserPhoneEmailStatusLogEntity);
			//end sending verification email
			
//		}

		if(appUserEntity.getIsDriver() != null && 
				appUserEntity.getIsDriver().equals("0")){
			//Start sending completion mail for passenger
			Map<String, String> map = new HashMap<String,String>();
			map.put("Name", appUserEntity.getPerson().getFirstName());
			map.put("fbLink", "https://www.facebook.com/GoSafr/?fref=ts");
			map.put("tweetLink", "https://twitter.com/gosafr");
			asyncEmailServiceImpl.sendEmail(EmailTypeEnum.CompletionPassenger.getValue(), 
					appUserEntity.getAppUserId()+"",appUserEntity.getPersonDetail().getPrimaryEmail(), map);
			//End Mailing
		}
				
		//start asynchronous saving appUserRegFlow
		AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
		appUserRegFlowBean.setAppUserId(appUserEntity.getAppUserId()+"");
		appUserRegFlowBean.setIsFromApp(bean.getIsFromWindow());
		appUserRegFlowBean.setStepCode(
				UserRegFlowEnum.BasicInfo.getValue()+"");
		appUserRegFlowBean.setIsCompleted("1");
		asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
		//end asynchronous saving appUserRegFlow
		//Start asynchronous Generating Referal For Passenger or Driver
		SafeHerDecorator Referaldecorator = new SafeHerDecorator();
		PromAndReffBean promoBean = new PromAndReffBean();
		if (bean.getIsUpdate() != null && bean.getIsUpdate().equals("0")) {
			promoBean = prepareReferalBean(promoBean);
			if (appUserEntity.getIsDriver() != null && appUserEntity.getIsDriver().equals("0")) {
				promoBean.setIsDriver("0");
			} else if (appUserEntity.getIsDriver() != null && appUserEntity.getIsDriver().equals("1")) {
				promoBean.setIsDriver("1");
			}
			promoBean.setAppUser(appUserEntity.getAppUserId()+"");
			Referaldecorator.setDataBean(promoBean);
			iAsyncService.generateAsyncReferalCode(Referaldecorator);
		}
		//End asynchronous Generating Referal For Passenger or Driver
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage(responseMessage);
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

	}
	
	private PromAndReffBean prepareReferalBean(PromAndReffBean promoBean) {
		
		promoBean.setDurationInDays("900");
		promoBean.setIsPromotion("0");
		promoBean.setIsSingle("0");
		promoBean.setMaxUseCount("500");
		promoBean.setPromDescription("Referal Descritpion");
	//	promoBean.setIsDriver();
		promoBean.setIsCount("1");
		promoBean.setCountValue("10");
		promoBean.setIsPartial("1");
		promoBean.setPartialValue("2");
		promoBean.setStartDate(DateUtil.getCurrentDateWithoutTime()+"");
		promoBean.setExpiryDate(DateUtil.getCurrentDateWithoutTime()+"");
		promoBean.setAmount("10");
		return promoBean;
	}

	private void saveAppUser(SafeHerDecorator decorator){
		
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in saveAppUser with AppUserBean "+bean +"  ******");
		String responseMessage = "Sign Up successfull";
		String errorMessage = "Sign up fail";
		PersonEntity personEntity = new PersonEntity();
		UserLoginEntity userLoginEntity = new UserLoginEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		PersonDetailEntity personDetailEntity = new PersonDetailEntity();
		PersonDetailHistoryEntity personDetailHistoryEntity = new PersonDetailHistoryEntity();

		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			Object[] object =  appUserDao.getAppUserEntityPersonObj(new Integer(bean.getAppUserId()));
			if(object == null){
				appUserEntity = new AppUserEntity();
				personEntity = new PersonEntity();
				userLoginEntity = new UserLoginEntity();
				personDetailEntity = new PersonDetailEntity();
			}else{
				appUserEntity = (AppUserEntity)object[0];
				personEntity = (PersonEntity) object[1];
				personDetailEntity =  (PersonDetailEntity)object[2];
				userLoginEntity = appUserDao.findByIdParam2(new Integer(bean.getAppUserId()));
				if(userLoginEntity == null){
					userLoginEntity = new UserLoginEntity();
				}
				responseMessage = "Your profile has been updated";
				errorMessage = "Some error occurred while saving your profile changes";
			}
		}

		signUpConverter.convertBeanToPersonEntity(bean, personEntity);
		appUserDao.saveOrUpdate(personEntity);

		signUpConverter.convertBeanToPersonDetailEntity(bean, personDetailEntity);
		personDetailEntity.setPerson(personEntity);
		appUserDao.saveOrUpdate(personDetailEntity);

		signUpConverter.convertBeanToAppUserEntity(bean, appUserEntity);
		appUserEntity.setPerson(personEntity);
		appUserEntity.setPersonDetail(personDetailEntity);
		appUserDao.saveOrUpdate(appUserEntity);
		bean.setIsActivated(appUserEntity.getIsActivated());
		((AppUserBean)decorator.getDataBean()).setAppUserId(appUserEntity.getAppUserId()+"");

		if(StringUtil.isNotEmpty(bean.getLocation())){
			PersonAddressEntity personAddressEntity = appUserDao.findByOject(
					PersonAddressEntity.class, "person", personEntity);
			AddressTypeEntity addressTypeEntity = new AddressTypeEntity();
			addressTypeEntity.setAddressTypeId(3);
			AddressEntity addressEntity = new AddressEntity();
			if(personAddressEntity == null){
				personAddressEntity = new PersonAddressEntity();
			}else{
				addressEntity = personAddressEntity.getAddress();
			}
				addressEntity.setFullAddress(bean.getLocation());
			
			personAddressEntity.setAddress(addressEntity);
			appUserDao.saveOrUpdate(addressEntity);
			personAddressEntity.setIsActive("1");
			personAddressEntity.setPerson(personEntity);
			personAddressEntity.setPersonDetail(personDetailEntity);
			personAddressEntity.setAddressType(addressTypeEntity);
			appUserDao.saveOrUpdate(personAddressEntity);
		}
		signUpConverter.convertBeanToUserLoginEntity(bean, userLoginEntity);
		userLoginEntity.setAppUser(appUserEntity);
		String sessionNO = System.currentTimeMillis() +
				"SESSION"+appUserEntity.getAppUserId();
		userLoginEntity.setCurrentSessionNo(sessionNO);
		appUserDao.saveOrUpdate(userLoginEntity);
		
//			UserCurrentSessionEntity currentSessionEntity = appUserDao.findByOject(
//					UserCurrentSessionEntity.class, "userLogin", userLoginEntity);
//			if(currentSessionEntity == null){
//				currentSessionEntity = new UserCurrentSessionEntity();
//			}
//			UserCurrentSessionEntity currentSessionEntity = new UserCurrentSessionEntity();
//			currentSessionEntity.setStartDatetime(
//					new Timestamp(new Date().getTime()));
//			currentSessionEntity.setSessionNo(sessionNO);
//			currentSessionEntity.setUserLogin(userLoginEntity);
//			appUserDao.saveOrUpdate(currentSessionEntity);
//			
//			UserSessionHistoryEntity sessionHistoryEntity = new UserSessionHistoryEntity();
//			sessionHistoryEntity.setSessionNo(sessionNO);
//			sessionHistoryEntity.setStartDatetime(
//					new Timestamp(new Date().getTime()));
//			sessionHistoryEntity.setUserCurrentSession(currentSessionEntity);
//			appUserDao.saveOrUpdate(sessionHistoryEntity);
//
//			SingletonSession.getSessionMap().put(
//					appUserEntity.getAppUserId()+"", sessionNO);
		
		//saveImageForPassenger
		
		if(StringUtil.isNotEmpty(bean.getUserImageUrl())){
			if(appUserEntity != null){
				AppUserBiometricEntity appUserBiometricEntity = appUserDao.findByIdParam(
						appUserEntity.getAppUserId());
				if(appUserBiometricEntity == null){
					appUserBiometricEntity = new AppUserBiometricEntity();
				}
				signUpConverter.populateAppUserBiometricEntity(appUserBiometricEntity);
				appUserBiometricEntity.setPath(bean.getUserImageUrl());
				appUserBiometricEntity.setAppUser(appUserEntity);
				if(appUserEntity.getPerson() != null){
					appUserBiometricEntity.setPerson(appUserEntity.getPerson());
				}
				appUserDao.saveOrUpdate(appUserBiometricEntity);
				((AppUserBean)decorator.getDataBean()
						).setUserImageUrl(bean.getUserImageUrl());
			}
		}

		signUpConverter.populatePersonDetailHistoryFromPersonDetail(
				personDetailEntity, personDetailHistoryEntity);
		appUserDao.saveOrUpdate(personDetailHistoryEntity);
		if(StringUtil.isNotEmpty(bean.getAppUserId()) && 
				bean.getIsDriver().equalsIgnoreCase("1")){
			List<AppUserVehicleEntity> vehicleInfoList = 
					appUserDao.findListByOject(AppUserVehicleEntity.class, 
							"appUser", appUserEntity, 0, 10);
			if(vehicleInfoList != null){
				List<VehicleInfoBean> vehicleInfoBeanlist = 
						signUpConverter.convertEntityListBeanList(vehicleInfoList);
				bean.setVehicaleList(vehicleInfoBeanlist);
			}
		}
		
		//start sending verification email
		Map<String, String> emailMap = new HashMap<String, String>();
		emailMap.put("Name", personEntity.getFirstName());
		emailMap.put("Url", "verifyUser");
		emailMap.put("vcode",
				CommonUtil.generateSecureRando("VC" + appUserEntity.getAppUserId() +
						System.currentTimeMillis()));

		asyncEmailServiceImpl.sendEmail(
				EmailTypeEnum.AccountVerification.getValue(), appUserEntity.getAppUserId()+"", bean.getEmail(), emailMap);

		AppUserPhoneEmailStatusEntity appUserPhoneEmailStatusEntity = new AppUserPhoneEmailStatusEntity();
		appUserPhoneEmailStatusEntity.setPending("1");
		appUserPhoneEmailStatusEntity.setNotVerified("1");

		AppUserPhoneEmailStatusLogEntity appUserPhoneEmailStatusLogEntity = new AppUserPhoneEmailStatusLogEntity();
		appUserPhoneEmailStatusLogEntity.setPrimaryEmail("1");
		appUserPhoneEmailStatusLogEntity.setSecondaryEmail("0");
		appUserPhoneEmailStatusLogEntity.setPrimaryCell("0");
		appUserPhoneEmailStatusLogEntity.setCode(EncryptDecryptUtil.
				encryptVerification(emailMap.get("vcode")));
		appUserPhoneEmailStatusLogEntity.setAppUser(appUserEntity);

		asyncServiceImpl.saveUserEmailCode(appUserPhoneEmailStatusEntity, appUserPhoneEmailStatusLogEntity);
		//end sending verification email
		
		//Start sending completion mail
		Map<String, String> map = new HashMap<String,String>();
		map.put("Name", appUserEntity.getPerson().getFirstName());
		map.put("fbLink", "https://www.facebook.com/GoSafr/?fref=ts");
		map.put("tweetLink", "https://twitter.com/gosafr");
		asyncEmailServiceImpl.sendEmail(EmailTypeEnum.CompletionPassenger.getValue(), 
				appUserEntity.getAppUserId()+"",appUserEntity.getPersonDetail().getPrimaryEmail(), map);
		//End Mailing
		
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage(responseMessage);
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

	}

	
	@Override
	public void userSingIn(SafeHerDecorator decorator) throws GenericException{
		
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in userSingIn with SignInBean "+bean +"  ******");
		AppUserBean Apibean = new AppUserBean();
		signUpConverter.validateUserSignIn(decorator);
		if(decorator.getErrors().size() == 0){
			try {
				UserLoginEntity userLoginEntity = null;
				if(bean.getIsSocial().equals("0")){
					bean.setPassword(EncryptDecryptUtil.encrypt(bean.getPassword()));
					userLoginEntity = appUserDao.verifyUser(bean);	
				}else if(bean.getIsSocial().equals("1")){
					userLoginEntity = appUserDao.verifySocialUser(bean);
					if(userLoginEntity == null){
						throw new GenericException("User dont exist");
					}
				}
				if(userLoginEntity == null){
					throw new GenericException("The email and password you entered don't match.");
				}else{
					
					if(userLoginEntity.getAppUser() != null && 
							userLoginEntity.getAppUser().getIsDriver() != null && 
							userLoginEntity.getAppUser().getIsDriver().equals("1")){
						
						if(userLoginEntity.getAppUser().getPerson() != null && 
								userLoginEntity.getAppUser().getPerson().getSex().equals("F")){
							if(userLoginEntity.getIsComplete() != null && 
									!userLoginEntity.getIsComplete().equals("1")){
								Apibean.setStepCode((new Integer(
										userLoginEntity.getUserRegFlowId()) + 1)+"");
								Apibean.setAppUserId(userLoginEntity.getAppUser().getAppUserId()+"");
								decorator.getResponseMap().put("data", Apibean);
								decorator.setResponseMessage("Please complete your registration to sign in");
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
								return;
							}else{
								Apibean.setStepCode("0");
							}
						}
					}
					
					if(userLoginEntity.getAppUser() != null){
					      //sendNotificationToPreviousLoggedInDevice
					      try {
					    	  if(userLoginEntity.getKeyToken() != null){
					    		  if(!bean.getKeyToken().equals(userLoginEntity.getKeyToken())){
					    			  String message = "You have been logged out as you logged in another device";
					    			  if(bean.getOsType().equals("1")){
					    				  if(bean.getIsDriver().equals("0")){
					    					  pushAndriod.pushAndriodPassengerNotificationForSignOutAnotherDevice(userLoginEntity.getKeyToken(),
					    						   "", "", "", "", PushNotificationStatus.SignOutUser.toString(), message);
					    				  }
					    			  }else {
					    				  if(bean.getIsDriver().equals("0")){
					    					  pushIOS.pushIOSPassengerPriorityForSingOutAnotherDevice(userLoginEntity.getKeyToken(),
					    						   userLoginEntity.getIsDev(), "", "", "", "",
					    						   PushNotificationStatus.SignOutUser.toString(), 
					    						   message, userLoginEntity.getFcmToken());
					    				  }else{
					    					  pushIOS.pushIOSDriverPriorityForSignOutAnotherDevice(userLoginEntity.getKeyToken(),
					    						   userLoginEntity.getIsDev(), "", "", "", "",
					    						   PushNotificationStatus.SignOutUser.toString(),
					    						   message, userLoginEntity.getFcmToken());
					    				  }
					    			  }
					    		  }
					    	  }
					      } catch (IOException e) {
					       e.printStackTrace();
					      }
						/*Check User Status
						if(userLoginEntity.getIsOnline() != null){
							if(userLoginEntity.getIsOnline().trim() == "1" || userLoginEntity.getIsOnline().equals("1") ){
								throw new GenericException("You have already signed-in from other device! Please Logout and Sign-in again");
							}
						}
						
						appUserDao.setUserStatus(userLoginEntity.getUserLoginId(),"1");
*/
						String sessionNO = System.currentTimeMillis() +
								"SESSION"+userLoginEntity.getAppUser().getAppUserId();
//						if(userLoginEntity.getAppUser().getIsDriver().equals("1")){
//							ActiveDriverLocationEntity activeDriverLocationEntity = appUserDao.findByOject(
//									ActiveDriverLocationEntity.class, "appUser", userLoginEntity.getAppUser());
//							if(activeDriverLocationEntity != null && StringUtil.isNotEmpty(
//									activeDriverLocationEntity.getIsOnline()) && 
//										activeDriverLocationEntity.getIsOnline().equals("1")){
	//
//								throw new GenericException("User is already logged in to another device");
//							}
//						}
//						String sessionNO = System.currentTimeMillis() +
//								"SESSION"+userLoginEntity.getAppUser().getAppUserId();
//						UserCurrentSessionEntity currentSessionEntity = appUserDao.findByOject(
//								UserCurrentSessionEntity.class, "userLogin", userLoginEntity);
//						if(currentSessionEntity == null){
//							currentSessionEntity = new UserCurrentSessionEntity();
//						}
//						currentSessionEntity.setStartDatetime(
//								new Timestamp(new Date().getTime()));
//						currentSessionEntity.setSessionNo(sessionNO);
//						currentSessionEntity.setUserLogin(userLoginEntity);
//						appUserDao.saveOrUpdate(currentSessionEntity);
//						
//						UserSessionHistoryEntity sessionHistoryEntity = new UserSessionHistoryEntity();
//						sessionHistoryEntity.setSessionNo(sessionNO);
//						sessionHistoryEntity.setStartDatetime(
//								new Timestamp(new Date().getTime()));
//						sessionHistoryEntity.setUserCurrentSession(currentSessionEntity);
//						appUserDao.saveOrUpdate(sessionHistoryEntity);
//						System.out.println(SingletonSession.getSessionMap().size());
//						SingletonSession.getSessionMap().put(
//								userLoginEntity.getAppUser().getAppUserId()+"", sessionNO);
//						String query = "update UserLoginEntity set currentSessionNo ='"
//								+ sessionNO
//								+"'where loginEmail ='"
//								+ bean.getEmail()
//								+ "'";
	//
//						appUserDao.hibernateQuery(query);
						
						signUpConverter.convertPersonEnityToBean(userLoginEntity, decorator);
						Apibean=signUpConverter.convertAppiPersonEnityToBean(userLoginEntity, decorator);
						Apibean.setSessionNo(sessionNO);
						if(userLoginEntity.getAppUser() != null && 
								userLoginEntity.getAppUser().getAppUserId() != null){
							AppUserBiometricEntity biometricEntity = appUserDao.findByOject(
									AppUserBiometricEntity.class, "appUser", userLoginEntity.getAppUser());
							if(biometricEntity != null){
								Apibean.setUserImageUrl(biometricEntity.getPath());
							}
							if(userLoginEntity.getAppUser().getPerson() != null && 
									userLoginEntity.getAppUser().getPerson().getPersonId() != null){
//								PersonAddressEntity personAddressEntity = appUserDao.findByOject(
//										PersonAddressEntity.class, "person", userLoginEntity.getAppUser().getPerson());
								PersonAddressEntity personAddressEntity = appUserDao.findAddressByAddressTypeAndPerson(
										userLoginEntity.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Residential.getValue());
								if(personAddressEntity != null){
									if(personAddressEntity.getAddress() != null){
										Apibean.setLocation(
												personAddressEntity.getAddress().getFullAddress());
									}
								}
								PersonDetailEntity personDetailEntity = appUserDao.findByOject(
										PersonDetailEntity.class, "person", userLoginEntity.getAppUser().getPerson());
								if(personDetailEntity != null){
									Apibean.setAltEmail(personDetailEntity.getSecondaryEmail());
									Apibean.setAltPhoneNumber(personDetailEntity.getSecondaryCell());
								}
							}
						}
						
						//set isActive true
//						userLoginEntity.setIsActive("1");
					//	appUserDao.merge(userLoginEntity);
						Long count=userLoginEntity.getCurrentLoginCount()+1;
						//isActive='1' , remove this is_active because we r not updating this on sign in
						String Qur = "update UserLoginEntity set currentLoginCount ='"
								+ count
								+ "' , keyToken ='"+bean.getKeyToken()+"'  , isActive='0', osType='"+bean.getOsType()+"'where loginEmail ='"
								+ bean.getEmail()
								+ "'";

						appUserDao.hibernateQuery(Qur);
						
						if(StringUtil.isNotEmpty(bean.getIsDev())){
							String Qur2 = "update UserLoginEntity set isDev = '"+bean.getIsDev()+"' where loginEmail ='"
									+ bean.getEmail()
									+ "'";

							appUserDao.hibernateQuery(Qur2);
						}
						
						//saveFcmToken
						if(StringUtil.isNotEmpty(bean.getFcmToken())){
							String Qur2 = "update UserLoginEntity set fcmToken = '"+bean.getFcmToken()+"' where loginEmail ='"
									+ bean.getEmail()
									+ "'";

							appUserDao.hibernateQuery(Qur2);
						}

						//offlineDriver
						if(bean.getIsDriver().equals("1")){
							/*String updateDriverOfflineQur = "update ActiveDriverLocationEntity set isOnline = '0', isRequested = '0', "+
									"isBooked = '0' where appUser.appUserId ="+bean.getAppUserId();
							appUserDao.hibernateQuery(updateDriverOfflineQur);*/
							
							activeDriverCustomRepository.updateLocation(userLoginEntity.getAppUser().getAppUserId());
						}
						
						//fetchCharities
						if(userLoginEntity.getAppUser().getAppUserId() != null){
							List<UserSelectedCharitiesEntity> list = appUserDao.getUserCharities(
									userLoginEntity.getAppUser().getAppUserId());
							if(list != null && list.size() > 0){
								for(int i=0;i<list.size();i++){
									UserSelectedCharitiesEntity selectedCharitiesEntity = list.get(i);
									if(selectedCharitiesEntity != null){
										CharitiesEntity charitiesEntity = selectedCharitiesEntity.getCharities();
										if(charitiesEntity != null){
											CharitiesBean charityBean = new CharitiesBean();
											charityConverter.convertEntityToCharityBean(
													charitiesEntity, charityBean);
											Apibean.getCharitiesList().add(charityBean);
										}
									}
								}
							}
						}
						
						AppUserPaymentInfoEntity paymentInfoEntity = appUserDao.findByOject(
								AppUserPaymentInfoEntity.class, "appUser", userLoginEntity.getAppUser());
						if(paymentInfoEntity != null){
							if(paymentInfoEntity.getDefaultType() != null){
								Apibean.getCreditCardInfoBean().setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
								if(Apibean.getCreditCardInfoBean().getDefaultType() != null 
										&& Apibean.getCreditCardInfoBean().getDefaultType().trim().equalsIgnoreCase("C")){
									CreditCardInfoEntity cardInfoEntity = appUserDao.findCreditCard(
											paymentInfoEntity.getAppUserPaymentInfoId());
									if(cardInfoEntity != null){
										Apibean.setCreditCardInfoBean(
												signUpConverter.convertEntityToCreditCardInofBean(cardInfoEntity));
										Apibean.getCreditCardInfoBean().setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
									}
								}
							}
						}
						//fetchUserRatingAndVehicleList
						if(userLoginEntity.getAppUser() != null && 
								bean.getIsDriver().equalsIgnoreCase("1")){
							UserRatingEntity ratingEntity = appUserDao.findByOject(
									UserRatingEntity.class, "appUser", userLoginEntity.getAppUser());
							if(ratingEntity != null){
								Apibean.setUserRating(
										ratingEntity.getCurrentValue()+"");
							}
							List<AppUserVehicleEntity> vehicleInfoList = 
									appUserDao.findListByOject(AppUserVehicleEntity.class, 
											"appUser", userLoginEntity.getAppUser(), 0, 10);
							if(vehicleInfoList != null){
								List<VehicleInfoBean> vehicleInfoBeanlist = 
										signUpConverter.convertEntityListBeanList(vehicleInfoList);
								Apibean.setVehicaleList(vehicleInfoBeanlist);
							}
						}
						// fetchingUserRating
						UserRatingEntity userRatingEntity = appUserDao
								.findByOject(UserRatingEntity.class, "appUser",
										userLoginEntity.getAppUser());
						if (userRatingEntity != null) {
							Apibean.setUserRating(userRatingEntity
									.getCurrentValue() + "");
						}
						Apibean.setIsActive("0");
						Apibean.setIsActivated(userLoginEntity.
								getAppUser().getIsActivated());
						decorator.getResponseMap().put("data", Apibean);
						decorator.setResponseMessage("Sign in successfull");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					}else{
						throw new GenericException("App user don't exists");
					}
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from userSingIn with Exception "+e.getMessage()+"  ******");
				throw new GenericException("Server is not responding right now");
			}
		}else{
			throw new GenericException("Sign in failed");
		}
		
	}

	@Override
	public void sendEmailToClient(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		logger.info("******Entering in sendEmailToClient with AppUserBean "+bean +"  ******");
		
		if(StringUtil.isNotEmpty(bean.getEmail())){
			if(appUserDao.checkIfEmailExists(bean.getEmail())){
				throw new GenericException("Provided email don't exist");
			}else{
				String message = email.sendEmail(bean.getEmail());
				if(message != null && message.length() > 0){
					throw new GenericException(message);
				}else{
					decorator.setResponseMessage("Please check your email to reset password");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				}
			}
		}else{
			throw new GenericException("Please provide email address");
		}
	}

	@Override
	public void saveNewPassword(SafeHerDecorator decorator)
			throws GenericException {	
		try {
			AppUserBean bean = (AppUserBean) decorator.getDataBean();
			logger.info("******Entering in saveNewPassword with AppUserBean "+bean +"  ******");
			String email = EncryptDecryptUtil.decrypt(bean.getEmail());
			UserLoginEntity entity = appUserDao.findBy(
					UserLoginEntity.class, "loginEmail", email);
			if(entity != null){
				entity.setPswd(EncryptDecryptUtil.encrypt(bean.getPassword()));
				appUserDao.saveOrUpdate(entity);
				decorator.setResponseMessage("New Password saved successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			}else{
				throw new GenericException("User don't exists");
			}
		} catch (Exception e) {
			logger.info("******Exiting from saveNewPassword with Exception "+e.getMessage()+" ******");
			e.printStackTrace();
			throw new GenericException("Please try again latter");
		}
		
	}

/*	@Override
	public void logOutUser(SafeHerDecorator decorator) throws GenericException {
	// {
			
			LogOutBean bean = (LogOutBean) decorator.getDataBean();
			logger.info("******Entering in logOutUser with LogOutBean "+bean +"  ******");
			signUpConverter.validateUserSignOut(decorator);
			UserLoginEntity entity = appUserDao.findByOject(UserLoginEntity.class,"appUser.appUserId",
				Integer.valueOf(bean.getAppUserId()));
		if (entity != null) {
			if(entity.getAppUser()!=null && entity.getAppUser().getIsDriver() != null
					&& entity.getAppUser().getIsDriver().equals("1")){
				appUserDao.deleteDriverActiveLocation(entity.getAppUser().getAppUserId());
			}
//TODO: To be implemented user status check
		//	appUserDao.setUserStatus(entity.getUserLoginId(),"0");
			// we dont need this check because we are not updating is_active on login
//			if (entity.getIsActive().equals("1")) {
				// entity.setIsActive("0");
				// entity.setCurrentLoginCount(entity.getCurrentLoginCount() +
				// 1);
				// appUserDao.merge(entity);
//				String Qur = "update UserLoginEntity set isActive='0' where loginEmail='"
//						+ entity.getLoginEmail() + "'";
//				appUserDao.hibernateQuery(Qur);
				//Session maintain here currently hault
//			UserCurrentSessionEntity currentSessionEntity = appUserDao.findByOject(
//					UserCurrentSessionEntity.class, "userLogin", entity);
//			if(currentSessionEntity != null){
//				currentSessionEntity.setEndDatetime(
//						new Timestamp(new Date().getTime()));
//				currentSessionEntity.setIsUserLogout("1");
//				appUserDao.saveOrUpdate(currentSessionEntity);
//			}
			
				decorator.setResponseMessage("Logout Successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
//			} else {
//					decorator.setReturnCode(ReturnStatusEnum.FAILURE
//							.getValue());
//					decorator.setResponseMessage("Logout Error");
//					throw new GenericException("Logout Failed! You Must have a Login for that");
//				}

			} else {
				throw new GenericException("Please Check Your User ID");
			}
	//	} catch (Exception e) {
			//e.printStackTrace();
		//	throw new GenericException("You Must have a Login for that");
	//	}
	}*/
	
	@Override
	public void logOutUser(SafeHerDecorator decorator) throws GenericException {
	// {
			
			LogOutBean bean = (LogOutBean) decorator.getDataBean();
			logger.info("******Entering in logOutUser with LogOutBean "+bean +"  ******");
			signUpConverter.validateUserSignOut(decorator);
			UserLoginEntity entity = appUserDao.findByOject(UserLoginEntity.class,"appUser.appUserId",
				Integer.valueOf(bean.getAppUserId()));
		if (entity != null) {
			if(entity.getAppUser()!=null && entity.getAppUser().getIsDriver() != null
					&& entity.getAppUser().getIsDriver().equals("1")){
				//TODO: check itttt
				//appUserDao.deleteDriverActiveLocation(entity.getAppUser().getAppUserId());
				activeDriverRepository.deleteByAppUserId(entity.getAppUser().getAppUserId());
			}
//TODO: To be implemented user status check
		//	appUserDao.setUserStatus(entity.getUserLoginId(),"0");
			// we dont need this check because we are not updating is_active on login
//			if (entity.getIsActive().equals("1")) {
				// entity.setIsActive("0");
				// entity.setCurrentLoginCount(entity.getCurrentLoginCount() +
				// 1);
				// appUserDao.merge(entity);
//				String Qur = "update UserLoginEntity set isActive='0' where loginEmail='"
//						+ entity.getLoginEmail() + "'";
//				appUserDao.hibernateQuery(Qur);
				//Session maintain here currently hault
//			UserCurrentSessionEntity currentSessionEntity = appUserDao.findByOject(
//					UserCurrentSessionEntity.class, "userLogin", entity);
//			if(currentSessionEntity != null){
//				currentSessionEntity.setEndDatetime(
//						new Timestamp(new Date().getTime()));
//				currentSessionEntity.setIsUserLogout("1");
//				appUserDao.saveOrUpdate(currentSessionEntity);
//			}
			List<AppUserRegTrackEntity> appUserRegFlowEntity = appUserDao.findTrackEntity(entity.getAppUser().getAppUserId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("Name", entity.getAppUser().getPerson().getFirstName());
			String emailAddress =entity.getAppUser().getPersonDetail().getPrimaryEmail();
			for (AppUserRegTrackEntity apk : appUserRegFlowEntity) {
				if (apk.getUserRegFlow().getUserRegFlowId() == UserRegFlowEnum.BankInfo.getValue()) {
					asyncEmailServiceImpl.sendEmail(EmailTypeEnum.AccountCreation.getValue(), entity.getAppUser().getAppUserId()+"", emailAddress, map);
				}else{
					asyncEmailServiceImpl.sendEmail(EmailTypeEnum.InCompleteDate.getValue(), entity.getAppUser().getAppUserId()+"", emailAddress, map);
				}
			}
				decorator.setResponseMessage("Logout Successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
//			} else {
//					decorator.setReturnCode(ReturnStatusEnum.FAILURE
//							.getValue());
//					decorator.setResponseMessage("Logout Error");
//					throw new GenericException("Logout Failed! You Must have a Login for that");
//				}
				
				//async saving saveLocationTrackIntoDrivingDetailFromQuartz
				if(entity.getAppUser().getIsDriver().equals("1")){
					asyncServiceImpl.saveLocationTrackIntoDrivingDetailFromQuartz(
							entity.getAppUser().getAppUserId(), DateUtil.getMongoDbDate(new Date()));	
				}

			} else {
				throw new GenericException("Please Check Your User ID");
			}
	//	} catch (Exception e) {
			//e.printStackTrace();
		//	throw new GenericException("You Must have a Login for that");
	//	}
	}
	
	@Override
	public void saveUserFavorities(SafeHerDecorator decorator)
				throws GenericException {
		
		UserFavoritiesBean bean = (UserFavoritiesBean) decorator.getDataBean();
		logger.info("******Entering in saveUserFavorities with UserFavoritiesBean "+bean +"  ******");
		signUpConverter.validateUserFavorities(decorator);
		if(decorator.getErrors().size() == 0){
			try {
				saveFavorities(decorator);
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from saveUserFavorities with Exception "+e.getMessage()+" ********");
				throw new GenericException("Server is not responding right now");
				
			}
		}else{
			throw new GenericException("Please provide all mendatory information");
		}
	}
	
	@Override
	public void getUserFavorities(SafeHerDecorator decorator)
				throws GenericException {
		UserFavoritiesBean bean = (UserFavoritiesBean) decorator.getDataBean();
		logger.info("******Entering in getUserFavorities with UserFavoritiesBean "+bean +"  ******");
		
		try {
			List<UserFavoritiesEntity> list = appUserDao.getUserFavorities(
					new Integer(bean.getAppUserId()), new Integer(bean.getFavorityType()));
			if(list != null && list.size() > 0){
				for(UserFavoritiesEntity entity : list){
					UserFavoritiesBean favoritiesBean = new UserFavoritiesBean();
					signUpConverter.populateBeanFromUserFavoritiesEntity(entity, favoritiesBean);
					bean.getFavList().add(favoritiesBean);
				}
				decorator.getResponseMap().put("data", bean.getFavList());
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			}else{
				decorator.setResponseMessage("No Record for this user");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from getUserFavorities with DataException "+e.getMessage()+" ********");
			throw new GenericException("Server is not responding right now");
		}
	}

	public String saveFavorities(SafeHerDecorator decorator) {

		String message = "";
		UserFavoritiesBean bean = (UserFavoritiesBean) decorator.getDataBean();
		logger.info("******Entering in saveFavorities with UserFavoritiesBean "+bean +"  ******");
		

//		try {
			AppUserEntity appUserEntity = new AppUserEntity();
			UserFavoritiesEntity entity = new UserFavoritiesEntity();
			FavorityTypeEntity favorityTypeEntity = new FavorityTypeEntity();
//			bean.setLocation(iGoogleMapServices.getFormatedAddress(
//					bean.getFavLat(), bean.getFavLong()));

			signUpConverter.populateUserFavoritiesEntityFromBean(entity, bean);
			appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
			favorityTypeEntity.setFavorityTypeId(new Integer(bean
					.getFavorityType()));
			entity.setAppUser(appUserEntity);
			entity.setFavorityType(favorityTypeEntity);
			appUserDao.saveOrUpdate(entity);
			decorator
					.setResponseMessage("User’s selected favorite, saved successfully");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
//		} catch (GenericException e) {
//			decorator.setResponseMessage("Favorities location saving faild due to some error");
//			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
//			e.printStackTrace();
//		}
		return "";
	}

	@Override
	public void appUserVisibility(SafeHerDecorator decorator)
			throws GenericException {
		
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in appUserVisibility with SignInBean "+bean +"  ******");
		signUpConverter.validateDriverVisibility(decorator);
		if (decorator.getErrors().size() == 0) {
//			ActiveDriverLocationEntity entity = new ActiveDriverLocationEntity();
//			AppUserEntity appEntity = appUserDao.get(AppUserEntity.class,
//					Integer.valueOf(bean.getAppUserId()));
//			entity = appUserDao.findByOject(ActiveDriverLocationEntity.class,
//					"appUser", appEntity);
//			if (entity != null) {
//				entity.setIsOnline("0");
//				appUserDao.saveOrUpdate(entity);
//			}
			if(bean.getIsDriver().equalsIgnoreCase("1")){
				appUserDao.updateLocation(
						Integer.valueOf(bean.getAppUserId()), "ActiveDriverLocationEntity");
			}else{
				appUserDao.updateLocation(
						Integer.valueOf(bean.getAppUserId()), "PassengerSourceLocationEntity");
			}
			decorator.setResponseMessage("Visisbility is Off");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		}
	}

	//Mongo
	@Override
	public void appUserVisibilityV2(SafeHerDecorator decorator)
			throws GenericException {
		
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in appUserVisibility with SignInBean "+bean +"  ******");
		signUpConverter.validateDriverVisibility(decorator);
		if (decorator.getErrors().size() == 0) {
//			ActiveDriverLocationEntity entity = new ActiveDriverLocationEntity();
//			AppUserEntity appEntity = appUserDao.get(AppUserEntity.class,
//					Integer.valueOf(bean.getAppUserId()));
//			entity = appUserDao.findByOject(ActiveDriverLocationEntity.class,
//					"appUser", appEntity);
//			if (entity != null) {
//				entity.setIsOnline("0");
//				appUserDao.saveOrUpdate(entity);
//			}
			if(bean.getIsDriver().equalsIgnoreCase("1")){
				/*appUserDao.updateLocation(
						Integer.valueOf(bean.getAppUserId()), "ActiveDriverLocationEntity");*/
				activeDriverCustomRepository.updateLocation(Integer.valueOf(bean.getAppUserId()));

				//async saving saveLocationTrackIntoDrivingDetailFromQuartz
				asyncServiceImpl.saveLocationTrackIntoDrivingDetailFromQuartz(
						Integer.valueOf(bean.getAppUserId()), DateUtil.getMongoDbDate(new Date()));
			}else{
				appUserDao.updateLocation(
						Integer.valueOf(bean.getAppUserId()), "PassengerSourceLocationEntity");
			}
			decorator.setResponseMessage("Visisbility is Off");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		}
	}
	
	
	
	@Override
	public String appUserActiveDeActive(SafeHerDecorator decorator)
			throws GenericException {
		String result = "success";
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in appUserActiveDeActive with SignInBean "+bean +"  ******");
		AppUserEntity appEntity = appUserDao.get(AppUserEntity.class,
				Integer.valueOf(bean.getAppUserId()));
		if(appEntity == null){
			result = "User not found";
			return result;
		}
		UserLoginEntity entity = appUserDao.findByOject(UserLoginEntity.class,
				"appUser", appEntity);
		if (entity != null) {
			if (bean.getIsAvalible().equals("1")) {
				entity.setIsActive("1");
			} else {
				entity.setIsActive("0");
			}
			appUserDao.saveOrUpdate(entity);
		}else{
			result = "User not found";
		}
		logger.info("******Exiting from appUserActiveDeActive with result " +result+" *********");
		return result;
	}

	@Override
	public void logOutFromAnotherDevice(SafeHerDecorator decorator)
			throws GenericException {
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in logOutFromAnotherDevice with SignInBean "+bean +"  ******");
		UserLoginEntity userLoginEntity = appUserDao.verifyUser(bean);
		if(userLoginEntity == null){
			throw new GenericException("The email and password you entered don't match.");
		}else{
			if(userLoginEntity.getAppUser() != null){
				ActiveDriverLocationEntity activeDriverLocationEntity = appUserDao.findByOject(
						ActiveDriverLocationEntity.class, "appUser", userLoginEntity.getAppUser());
				if(activeDriverLocationEntity == null){
					throw new GenericException("User don't exists");
				}
				appUserDao.delete(activeDriverLocationEntity);
				userSingIn(decorator);
			}else{
				throw new GenericException("User don't exists");
			}
		}
	}

	@Override
	public void deleteUserFavorities(SafeHerDecorator decorator)
			throws GenericException {
		UserFavoritiesBean bean = (UserFavoritiesBean) decorator.getDataBean();
		logger.info("******Entering in deleteUserFavorities with UserFavoritiesBean "+bean +"  ******");
		if (StringUtil.isNotEmpty(bean.getUserFavoritiesId())) {
			try {
				UserFavoritiesEntity entity = appUserDao.findById(
						UserFavoritiesEntity.class, new Integer(bean.getUserFavoritiesId()));
				System.out.println(bean.getUserFavoritiesId());
				if(entity != null){
					appUserDao.delete(entity);
					decorator.setResponseMessage(
							"Successfully deleted ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				} else {
					throw new GenericException(
							"Record don't exists");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from deleteUserFavorities with Exception "+bean +"  ******");
				throw new GenericException("Server is not responding right now");
				
			}
		} else {
			throw new GenericException(
					"Please provide favority id");
		}
	}

	@Override
	public void driverPassengerRating(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("******Entering in driverPassengerRating  ******");
		signUpConverter.validateRating(decorator);
		if (decorator.getErrors().size() == 0) {
			String message = saveUserRating(decorator);
			if(message != null && message.length() > 0){
				throw new GenericException(message);
			}else{
				decorator.setResponseMessage("Rated successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			}
		} else {
			throw new GenericException(
					"Please provide all these information");
		}
	}
	
	private String saveUserRating(SafeHerDecorator decorator){
		
		String message = "";
		UserRatingBean bean = (UserRatingBean) decorator.getDataBean();
		logger.info("******Entering in saveUserRating with UserRatingBean "+bean +"  ******");
		UserRatingDetailEntity ratingDetailEntity = new UserRatingDetailEntity();
		UserCommentEntity commentEntity = new UserCommentEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		AppUserEntity ratingBy = new AppUserEntity();

//		appUserEntity.setAppUserId(
//				new Integer(bean.getUserToAppUserId()));
//		ratingBy.setAppUserId(
//				new Integer(bean.getUserByAppUserId()));
		
		appUserEntity = appUserDao.findById(AppUserEntity.class,
				new Integer(bean.getUserToAppUserId()));
		if(appUserEntity == null){
			message = "Rating failed due to some invalid information, please try again latter";
			return message;
		}
		ratingBy = appUserDao.findById(AppUserEntity.class,
				new Integer(bean.getUserByAppUserId()));
		if(ratingBy == null){
			message = "Rating failed due to some invalid information, please try again latter";
			return message;
		}
		
		UserRatingEntity ratingEntity = appUserDao.findByOject(
				UserRatingEntity.class, "appUser", appUserEntity);
		if(ratingEntity == null){
			ratingEntity = new UserRatingEntity();
		}
		ratingEntity.setAppUser(appUserEntity);
		ratingEntity.setLastRatingDate(DateUtil.getCurrentTimestamp());
		ratingEntity.setCurrentValue(
				appUserDao.getRatingAvg(ratingEntity.getUserRatingId()));
		if(ratingEntity.getCurrentValue() == null || 
				ratingEntity.getCurrentValue() <= 0){
			ratingEntity.setCurrentValue(
					Double.parseDouble(bean.getUserRating()));
		}
		
		appUserDao.saveOrUpdate(ratingEntity);
		
		ratingDetailEntity = appUserDao.getRatingDetail(
				ratingEntity.getUserRatingId(), new Integer(bean.getUserByAppUserId()));
		if(ratingDetailEntity == null){
			ratingDetailEntity = new UserRatingDetailEntity();
		}
		ratingDetailEntity.setAppUser(ratingBy);
//		ratingDetailEntity.setValue(new Short(bean.getUserRating()));
		ratingDetailEntity.setValue((short)Double.parseDouble(bean.getUserRating()));
		ratingDetailEntity.setRatingDate(DateUtil.getCurrentTimestamp());
		ratingDetailEntity.setUserRating(ratingEntity);
		
		appUserDao.saveOrUpdate(ratingDetailEntity);
		
		commentEntity.setUserRatingDetail(ratingDetailEntity);
		commentEntity.setComments(bean.getComment());
		/*commentEntity.setCommentDate(DateUtil.convertDateToString(new Date(), "MM/dd/yyyy"));*/
		commentEntity.setCommentDate(DateUtil.now());
//		commentEntity.setRateValue(new Short(bean.getUserRating()));
		commentEntity.setRateValue((short)Double.parseDouble(bean.getUserRating()));
		commentEntity.setAppUserByUserBy(ratingBy);
		commentEntity.setAppUserByUserFor(appUserEntity);
		commentEntity.setRideNo(bean.getRideNo());
		
		appUserDao.saveOrUpdate(commentEntity);
		
		return message;
	}

	@Override
	public void saveFcmToken(SafeHerDecorator decorator)
			throws GenericException {
		
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in saveFcmToken with SignInBean "+bean +"  ******");
		if(StringUtil.isEmpty(bean.getAppUserId())){
			throw new GenericException("Please provide App User");
		}
		if(StringUtil.isEmpty(bean.getFcmToken())){
			throw new GenericException("Please provide FCM token");
		}
		//saveFcmToken
		if(StringUtil.isNotEmpty(bean.getFcmToken())){
			String Qur2 = "update UserLoginEntity set fcmToken = '"+
					bean.getFcmToken()+"' where appUser.appUserId ="+bean.getAppUserId();
			int result = appUserDao.hibernateQuery(Qur2);
			if(result == 0){
				throw new GenericException("Please provide correct data");
			}
		}
		decorator.setResponseMessage("FCM token saved successfully");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public void matchPassword(SafeHerDecorator decorator)
			throws GenericException {
		
		SignInBean bean = (SignInBean) decorator.getDataBean();
		logger.info("******Entering in matchPassword with SignInBean "+bean +"  ******");
		if(StringUtil.isEmpty(bean.getAppUserId())){
			throw new GenericException("Please provide App User");
		}
		if(StringUtil.isEmpty(bean.getPassword())){
			throw new GenericException("Please provide password");
		}
		//matchPassword
		try {
			boolean result = appUserDao.matchPassword(
					new Integer(bean.getAppUserId()), EncryptDecryptUtil.encrypt(bean.getPassword()));
			if(!result){
				throw new GenericException("Wrong password");
			}
			decorator.setResponseMessage("password matched successfully");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from DataAccessException with Exception "+e.getMessage() +"  ******");
			throw new GenericException(
					"Server is not responding right now");
		}
	}

	@Override
	public void userSocialSingIn(SafeHerDecorator decorator)
			throws GenericException {
		SignInBean bean = (SignInBean) decorator.getDataBean();
		AppUserBean Apibean = new AppUserBean();
		signUpConverter.validateUserSocialSignIn(decorator);
		if(decorator.getErrors().size() == 0){
			UserLoginEntity userLoginEntity = appUserDao.verifySocialUser(bean);
			if(userLoginEntity == null){
				throw new GenericException("Your Social Information Doesn't Match our Record");
			}else{
				if(userLoginEntity.getAppUser() != null){
					String sessionNO = System.currentTimeMillis() +
							"SESSION"+userLoginEntity.getAppUser().getAppUserId();
					
					signUpConverter.convertPersonEnityToBean(userLoginEntity, decorator);
					Apibean=signUpConverter.convertAppiPersonEnityToBean(userLoginEntity, decorator);
					Apibean.setSessionNo(sessionNO);
					if(userLoginEntity.getAppUser() != null && 
							userLoginEntity.getAppUser().getAppUserId() != null){
						AppUserBiometricEntity biometricEntity = appUserDao.findByOject(
								AppUserBiometricEntity.class, "appUser", userLoginEntity.getAppUser());
						if(biometricEntity != null){
							Apibean.setUserImageUrl(biometricEntity.getPath());
						}
						if(userLoginEntity.getAppUser().getPerson() != null && 
								userLoginEntity.getAppUser().getPerson().getPersonId() != null){
//							PersonAddressEntity personAddressEntity = appUserDao.findByOject(
//									PersonAddressEntity.class, "person", userLoginEntity.getAppUser().getPerson());
							PersonAddressEntity personAddressEntity = appUserDao.findAddressByAddressTypeAndPerson(
									userLoginEntity.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Residential.getValue());
							if(personAddressEntity != null){
								if(personAddressEntity.getAddress() != null){
									Apibean.setLocation(
											personAddressEntity.getAddress().getFullAddress());
								}
							}
							PersonDetailEntity personDetailEntity = appUserDao.findByOject(
									PersonDetailEntity.class, "person", userLoginEntity.getAppUser().getPerson());
							if(personDetailEntity != null){
								Apibean.setAltEmail(personDetailEntity.getSecondaryEmail());
								Apibean.setAltPhoneNumber(personDetailEntity.getSecondaryCell());
							}
						}
					}
					
					Long count=userLoginEntity.getCurrentLoginCount()+1;
					String Qur = "update UserLoginEntity set currentLoginCount ='"
							+ count
							+ "' , keyToken ='"+bean.getKeyToken()+"'  , isActive='0', osType='"+bean.getOsType()+"'where loginEmail ='"
							+ bean.getEmail()
							+ "'";

					appUserDao.hibernateQuery(Qur);
					
					if(StringUtil.isNotEmpty(bean.getIsDev())){
						String Qur2 = "update UserLoginEntity set isDev = '"+bean.getIsDev()+"' where loginEmail ='"
								+ bean.getEmail()
								+ "'";

						appUserDao.hibernateQuery(Qur2);
					}
					
					//saveFcmToken
					if(StringUtil.isNotEmpty(bean.getFcmToken())){
						String Qur2 = "update UserLoginEntity set fcmToken = '"+bean.getFcmToken()+"' where loginEmail ='"
								+ bean.getEmail()
								+ "'";

						appUserDao.hibernateQuery(Qur2);
					}
					
					//fetchCharities
					if(userLoginEntity.getAppUser().getAppUserId() != null){
						List<UserSelectedCharitiesEntity> list = appUserDao.getUserCharities(
								userLoginEntity.getAppUser().getAppUserId());
						if(list != null && list.size() > 0){
							for(int i=0;i<list.size();i++){
								UserSelectedCharitiesEntity selectedCharitiesEntity = list.get(i);
								if(selectedCharitiesEntity != null){
									CharitiesEntity charitiesEntity = selectedCharitiesEntity.getCharities();
									if(charitiesEntity != null){
										CharitiesBean charityBean = new CharitiesBean();
										charityConverter.convertEntityToCharityBean(
												charitiesEntity, charityBean);
										Apibean.getCharitiesList().add(charityBean);
									}
								}
							}
						}
					}
					
					AppUserPaymentInfoEntity paymentInfoEntity = appUserDao.findByOject(
							AppUserPaymentInfoEntity.class, "appUser", userLoginEntity.getAppUser());
					if(paymentInfoEntity != null){
						if(paymentInfoEntity.getDefaultType() != null){
							Apibean.getCreditCardInfoBean().setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
							if(Apibean.getCreditCardInfoBean().getDefaultType() != null 
									&& Apibean.getCreditCardInfoBean().getDefaultType().trim().equalsIgnoreCase("C")){
								CreditCardInfoEntity cardInfoEntity = appUserDao.findCreditCard(
										paymentInfoEntity.getAppUserPaymentInfoId());
								if(cardInfoEntity != null){
									Apibean.setCreditCardInfoBean(
											signUpConverter.convertEntityToCreditCardInofBean(cardInfoEntity));
									Apibean.getCreditCardInfoBean().setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
								}
							}
						}
					}
					Apibean.setIsActive("0");
					decorator.getResponseMap().put("data", Apibean);
					decorator.setResponseMessage("Social Sign in successfull");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				}else{
					throw new GenericException("App user don't exists");
				}
			}
		}else{
			throw new GenericException("Social Sign in failed");
		}
		
	}

	@Override
	public void deleteUnneccesaryActiveDriver()
			throws GenericException {
		try {
			Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(DateUtil.getCurrentTimestamp().getTime());
	        cal.add(Calendar.HOUR, -12);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			appUserDao.deleteUnneccesaryActiveDriver(
					DateUtil.getCurrentTimestamp(), later);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException(e.getMessage());
		}
	}
	//Mongo
	@Override
	public void deleteUnneccesaryActiveDriverMongo()
			throws GenericException {
		try {
			Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(DateUtil.getCurrentTimestamp().getTime());
	        cal.add(Calendar.HOUR, -12);
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			appUserDao.deleteUnneccesaryActiveDriverMongo(
					new Date(DateUtil.getCurrentTimestamp().getTime()), new Date(later.getTime()));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException(e.getMessage());
		}
	}

	@Override
	public void inCompleteSignDataEmail() throws GenericException {
		try {
			
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException(e.getMessage());
		}
	}
	@Override
	public void findIncompleteUsers() throws GenericException {
		try {
			List<UserLoginEntity> list = appUserDao.findIncompleteUsers();
			if(list != null && list.size() > 0){
				for(UserLoginEntity userLoginEntity : list){
					if(userLoginEntity.getAppUser() != null && 
							userLoginEntity.getAppUser().getPersonDetail() != null && 
							userLoginEntity.getAppUser().getPersonDetail().getPrimaryEmail() != null){
						//Start Sending Mail
						Map<String, String> map = new HashMap<String,String>();
						map.put("Name", userLoginEntity.getAppUser().getPerson().getFirstName());
						map.put("hereLink", "www.gosafr.com");
						iAsyncEmailService.sendEmail(EmailTypeEnum.DraftAccountCreatedConfirmationEmail.getValue(), 
								userLoginEntity.getAppUser().getAppUserId()+"", 
								userLoginEntity.getAppUser().getPersonDetail().getPrimaryEmail(), map);
						//End Mailing	
					}
				}
			}else{
				throw new GenericException("No user found to send incomplete data email");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
//			throw new GenericException(e.getMessage());
			throw new GenericException("Server is not responding right now");
		}
	}
	
}
