package com.tgi.safeher.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.AddressBean;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DriverConfigBean;
import com.tgi.safeher.beans.ProfileInformationBean;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.AddressTypeEnum;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.PaymentDao;
import com.tgi.safeher.dao.VehicleDao;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.BankEntity;
import com.tgi.safeher.entity.ColorEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.PersonAddressEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.VehicleMakeEntity;
import com.tgi.safeher.entity.VehicleModelEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.IProfilService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.EncryptDecryptUtil;

@Service
@Transactional
@Scope("prototype")
public class ProfilService implements IProfilService {

	private static final Logger logger = Logger.getLogger(ProfilService.class);
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private SignUpConverter signUpConverter;

	@Autowired
	private ActiveDriverLocationRepository ActiveLocationTrackRepository;
	
	@Override
	public void passengerPeronalEdit(SafeHerDecorator decorator)
			throws GenericException {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		PersonEntity personEntity=null;
		PersonAddressEntity pesonAddress=null;
		PersonDetailEntity personDetailEntity=null;
		signUpConverter.vaildatePersonalEditByPassenger(decorator);
		if (decorator.getErrors().size() == 0) {
			
		}else{
			throw new GenericException("Please FullFill the Requriment of Passenger Personal Request");
		}

	}


	@Override
	public void driverGeneralConfig(SafeHerDecorator decorator)
			throws GenericException {
		DriverConfigBean bean = (DriverConfigBean) decorator.getDataBean();
		if (decorator.getErrors().size() == 0) {
		//Color List
		List<ColorEntity> vehicleColorLst = vehicleDao.getVehicleColor();
		bean.setColorList(signUpConverter.convertEntiyToVehicleColorBean(vehicleColorLst));
		
		
		//vehicle List
		List<VehicleMakeEntity> lsVehicleMake = vehicleDao.getVehicleMake();
		bean.setVehicleMakeList(signUpConverter.convertEntiyToVehicleBean(lsVehicleMake));
		
		//vehicle Model
		List<VehicleModelEntity> lsVehicleModel = appUserDao.getAll(VehicleModelEntity.class);
		bean.setVehicleModelList(signUpConverter.convertVehicleModelEntiyToVehicleBean(lsVehicleModel));
		
		//bank List
		List<BankEntity> BankLst =paymentDao.getBankList();
		bean.setBankList(signUpConverter.convertEntiyToBankBean(BankLst));
		
		
		CountryEntity countryInstance = appUserDao.findById(CountryEntity.class,5);
		//State Province
			if (countryInstance == null) {
				throw new GenericException("Please check you country id ");
			}
		bean.setStateList(signUpConverter.convertEntiyToStateBean(appUserDao.getStateByCountry(countryInstance)));
		decorator.getResponseMap().put("data", bean);
		decorator.setResponseMessage("Driver Configuration List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

		} else {
			throw new GenericException("Please Fullfill the Requriment of Driver Configuration");
		}
	}


	@Override
	public void getUserProfileInformation(SafeHerDecorator decorator)
			throws GenericException {
		
		ProfileInformationBean bean = (ProfileInformationBean) decorator.getDataBean();
		VehicleInfoBean vehicleInfo = new VehicleInfoBean();
		signUpConverter.validateAppUserId(decorator);
		AppUserVehicleEntity appUserVehicle;
		AppUserPaymentInfoEntity appUserPaymentInfoEntity;
		CreditCardInfoEntity creditCardInfo;
		UserRatingEntity ratingEntity=null;
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
				Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("Please provide Valid AppUser Id");
			}
			
			UserLoginEntity userLogin = appUserDao.findByOject(UserLoginEntity.class, "appUser", appUser);
			if (userLogin == null) {
				throw new GenericException("Please Provide Valid UserLogin Information");
			}
			
			//Personal Information Setting
			bean = signUpConverter.convertPersonInforToBean(userLogin, decorator);
			if(userLogin.getAppUser().getPerson() != null && 
					userLogin.getAppUser().getPerson().getPersonId() != null){
//				PersonAddressEntity personAddressEntity = appUserDao.findByOject(
//						PersonAddressEntity.class, "person", userLogin.getAppUser().getPerson());
				PersonAddressEntity personAddressEntity = appUserDao.findAddressByAddressTypeAndPerson(
						userLogin.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Residential.getValue());
				if(personAddressEntity != null){
					if(personAddressEntity.getAddress() != null){
						bean.setLocation(
								personAddressEntity.getAddress().getFullAddress());
					}
				}
			}
			//Vehicle Information Setting
			if(bean.getIsDriver()!=null  && bean.getIsDriver().equals("1")){
				appUserVehicle = appUserDao.findByOjectForVehicle(
						AppUserVehicleEntity.class, "appUser", appUser);
				if (appUserVehicle != null) {
					vehicleInfo.setVehicleMake(appUserVehicle.getVehicleInfo()
							.getVehicleMake().getName());
					vehicleInfo.setVehicleInfoId(appUserVehicle.getVehicleInfo().getVehicleInfoId()+"");
					vehicleInfo.setVehicleModel(appUserVehicle.getVehicleInfo()
							.getVehicleModel().getName());
					vehicleInfo.setPlateNumber(appUserVehicle.getVehicleInfo()
							.getPlateNumber());
					vehicleInfo.setIsActive(appUserVehicle.getVehicleInfo().getIsActive());
					vehicleInfo.setList(null);
					bean.setVehicleInfo(vehicleInfo);
				}
				bean.setCreditCardBean(null);
			}
			
			if (bean.getIsDriver() != null && bean.getIsDriver().equals("0")) {
				// CreditCardInformation
				appUserPaymentInfoEntity = paymentDao.findByOject(
						AppUserPaymentInfoEntity.class, "appUser", appUser);
				if (appUserPaymentInfoEntity != null) {
					creditCardInfo = appUserDao.findDefaultCreditCard(
							CreditCardInfoEntity.class, "appUserPaymentInfo",
							appUserPaymentInfoEntity);
					if(appUserPaymentInfoEntity.getDefaultType()!=null && appUserPaymentInfoEntity.getDefaultType().trim().equals("C")){
						if(creditCardInfo!=null){
							bean.getCreditCardBean().setIsCard("1");
							bean.getCreditCardBean().setDefaultType("C");
							bean.getCreditCardBean().setAppPaymentUserId(appUserPaymentInfoEntity.getAppUserPaymentInfoId()+"");
							bean.getCreditCardBean().setCreditCardNo(creditCardInfo.getCardNumber());
							bean.getCreditCardBean().setCreditCardTypeId(creditCardInfo.getCreditCardType().getName());
							bean.getCreditCardBean().setCvv(creditCardInfo.getCvv());
							bean.getCreditCardBean().setExpiryDate(creditCardInfo.getExpiryDate().toString());
							bean.getCreditCardBean().setIsActive(creditCardInfo.getIsActive());
						}
					}else{
						if(appUserPaymentInfoEntity.getDefaultType()!=null && appUserPaymentInfoEntity.getDefaultType().trim().equals("P")){
							bean.getCreditCardBean().setDefaultType("P");
							bean.getCreditCardBean().setIsPaypal("1");
						}
					}
					
				}
				
				
			}
			 ratingEntity = appUserDao.findByOject(
						UserRatingEntity.class, "appUser", appUser);
				if (ratingEntity != null) {
					bean.getUserRatingBean().setUserRating(ratingEntity.getCurrentValue() + "");
				} else {
					bean.getUserRatingBean().setUserRating("0.0");
				}
			bean.setUserImageUrl(getPicUrlByAppUser(appUser));
			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("Profile Information");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of Profil Information");
		}
	}


	@Override
	public void getUserProfileInformationV2(SafeHerDecorator decorator)
			throws GenericException {
		
		ProfileInformationBean bean = (ProfileInformationBean) decorator.getDataBean();
		VehicleInfoBean vehicleInfo = new VehicleInfoBean();
		signUpConverter.validateAppUserId(decorator);
		AppUserVehicleEntity appUserVehicle;
		AppUserPaymentInfoEntity appUserPaymentInfoEntity;
		CreditCardInfoEntity creditCardInfo;
		UserRatingEntity ratingEntity=null;
		if (decorator.getErrors().size() == 0) {
			UserLoginEntity userLogin = appUserDao.findByOject(UserLoginEntity.class, 
					"appUser.appUserId", Integer.valueOf(bean.getAppUserId()));
			if (userLogin == null) {
				throw new GenericException("Please Provide Valid UserLogin Information");
			}
			AppUserEntity appUser = userLogin.getAppUser();
			if (userLogin.getAppUser() == null) {
				throw new GenericException("Please provide Valid AppUser Id");
			}
			
			if(userLogin.getAppUser().getPerson() != null && 
					userLogin.getAppUser().getPerson().getSex().equals("F")){
				if (userLogin.getIsComplete() != null && !userLogin.getIsComplete().equals("1")) {
					bean.setStepCode((new Integer(userLogin.getUserRegFlowId()) + 1) + "");
					bean.setAppUserId(userLogin.getAppUser().getAppUserId() + "");
				} else {
					bean.setStepCode("0");
				}
			}
			
			//Personal Information Setting
			bean = signUpConverter.convertPersonInforToBean(userLogin, decorator);
			if(userLogin.getAppUser().getPerson() != null && 
					userLogin.getAppUser().getPerson().getPersonId() != null){
				PersonAddressEntity personAddressEntity = appUserDao.findAddressByAddressTypeAndPerson(
						userLogin.getAppUser().getPerson().getPersonId(), AddressTypeEnum.Residential.getValue());
				if(personAddressEntity != null){
					if(personAddressEntity.getAddress() != null){
						bean.setLocation(
								personAddressEntity.getAddress().getFullAddress());
					}
				}
			}
			//Mailing 
			if(bean.getIsDriver()!=null  && bean.getIsDriver().equals("1")){
//				 appUserVehicle = appUserDao
//					      .findByOjectForVehicleV2(AppUserVehicleEntity.class,
//					        "appUser.appUserId", appUser.getAppUserId());
//				if (appUserVehicle != null) {
//					vehicleInfo.setVehicleMake(appUserVehicle.getVehicleInfo()
//							.getVehicleMake().getName());
//					vehicleInfo.setVehicleInfoId(appUserVehicle.getVehicleInfo().getVehicleInfoId()+"");
//					vehicleInfo.setVehicleModel(appUserVehicle.getVehicleInfo()
//							.getVehicleModel().getName());
//					vehicleInfo.setPlateNumber(appUserVehicle.getVehicleInfo()
//							.getPlateNumber());
//					vehicleInfo.setIsActive(appUserVehicle.getVehicleInfo().getIsActive());
//					vehicleInfo.setList(null);
//					bean.setVehicleInfo(vehicleInfo);
//				}
//				bean.setCreditCardBean(null);
				

				List<PersonAddressEntity> personDetailLst = appUserDao.findPersonAddrress(appUser.getPerson());
				List<AddressBean> addrLst = new ArrayList<AddressBean>();
				AddressBean addbean;
				for (PersonAddressEntity personAdd : personDetailLst) {
					addbean = new AddressBean();
					addbean.setAddressType(personAdd.getAddressType().getName());
					addbean.setAddressLineOne(personAdd.getAddress().getAddressLineOne());
					addbean.setCity(personAdd.getAddress().getCity().getName());
					addbean.setStateProvince(personAdd.getAddress().getStateProvince().getName());
					addbean.setCityId(personAdd.getAddress().getCity().getCityId() + "");
					addbean.setStateProvinceId(personAdd.getAddress().getStateProvince().getStateId() + "");
					addbean.setZipCode(personAdd.getAddress().getZipCode().getZipCode());
					addrLst.add(addbean);
				}

				bean.setAddressLst(addrLst);
			}
			if (bean.getIsDriver() != null && bean.getIsDriver().equals("1")) {
				ActiveDriverLocationMongoEntity activeLocation = ActiveLocationTrackRepository
						.findByAppUserId(appUser.getAppUserId());
				if (activeLocation.getIsOnline() != null) {
					bean.setIsActive(activeLocation.getIsOnline());
				}else{
					bean.setIsActive("0");
				}

			}
			if (bean.getIsDriver() != null && bean.getIsDriver().equals("0")) {
				// CreditCardInformation
				appUserPaymentInfoEntity = (AppUserPaymentInfoEntity) paymentDao.findByIdParamCommon(
						"AppUserPaymentInfoEntity", "appUser.appUserId", appUser.getAppUserId());
				if (appUserPaymentInfoEntity != null) {
					creditCardInfo = appUserDao.findDefaultCreditCard(
							CreditCardInfoEntity.class, "appUserPaymentInfo",
							appUserPaymentInfoEntity);
					if(appUserPaymentInfoEntity.getDefaultType() != null && 
							appUserPaymentInfoEntity.getDefaultType().trim().equals("C")){
						if(creditCardInfo!=null){
							bean.getCreditCardBean().setIsCard("1");
							bean.getCreditCardBean().setDefaultType("C");
							bean.getCreditCardBean().setAppPaymentUserId(appUserPaymentInfoEntity.getAppUserPaymentInfoId()+"");
							bean.getCreditCardBean().setCreditCardNo(creditCardInfo.getCardNumber());
							bean.getCreditCardBean().setCreditCardTypeId(creditCardInfo.getCreditCardType().getName());
							bean.getCreditCardBean().setCvv(creditCardInfo.getCvv());
							bean.getCreditCardBean().setExpiryDate(creditCardInfo.getExpiryDate().toString());
							bean.getCreditCardBean().setIsActive(creditCardInfo.getIsActive());
						}
					}else{
						if(appUserPaymentInfoEntity.getDefaultType()!=null && appUserPaymentInfoEntity.getDefaultType().trim().equals("P")){
							bean.getCreditCardBean().setDefaultType("P");
							bean.getCreditCardBean().setIsPaypal("1");
						}
					}
					
				}
			}
			ratingEntity = (UserRatingEntity) appUserDao.findByIdParamCommon(
						"UserRatingEntity", "appUser.appUserId", appUser.getAppUserId());
			if (ratingEntity != null) {
				bean.getUserRatingBean().setUserRating(ratingEntity.getCurrentValue() + "");
			} else {
				bean.getUserRatingBean().setUserRating("0.0");
			}
			bean.setUserImageUrl(getPicUrlByAppUser(appUser));
			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("Profile Information");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of Profil Information");
		}
	}

	private String getPicUrlByAppUser(AppUserEntity appUser) {
		AppUserBiometricEntity appUserEntity = appUserDao.findByOject(
				AppUserBiometricEntity.class, "appUser", appUser);
		if (appUserEntity == null) {
			return "";
		}
		return appUserEntity.getPath();
	}


	@Override
	public void checkUserInformation(SafeHerDecorator decorator)
			throws GenericException {
		ProfileInformationBean bean = (ProfileInformationBean) decorator.getDataBean();
		signUpConverter.validateUserInformation(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("Please provide Valid AppUser Id");
			}

			UserLoginEntity userLogin = appUserDao.findByOject(
					UserLoginEntity.class, "appUser", appUser);
			if (userLogin == null) {
				throw new GenericException(
						"Please Provide Valid UserLogin Information");
			}
			
			
			if (userLogin.getFcmToken() == null) {
				bean.setFcmToken("0");
			} else {
				if (userLogin.getFcmToken().equals(bean.getFcmToken())) {
					bean.setFcmToken("0");
				} else {
					bean.setFcmToken("1");
				}
			}

			if (userLogin.getKeyToken() == null) {
				bean.setKeyToken("0");
			} else {
				if (userLogin.getKeyToken().equals(bean.getKeyToken())) {
					bean.setKeyToken("0");
				} else {
					bean.setKeyToken("1");
				}
			}
			bean.setUserRatingBean(null);
			bean.setCreditCardBean(null);
			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("User Information ");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		}else{
			throw new GenericException(
					"Please Fullfill the Requriment of User Information");
		}
	}
	
	
	public String verifyUser(String code, String appUserId, String type){
		
		String encryptedCode = EncryptDecryptUtil.encryptVerification(code);
		boolean flag = appUserDao.verifyUser(encryptedCode, appUserId, type);
		if(flag)
			return "verified";
		else
			return "unverified";
		
	}

}
