package com.tgi.safeher.service.converter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.beans.AddressBean;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.BankAccountInfoBean;
import com.tgi.safeher.beans.BankBean;
import com.tgi.safeher.beans.CityBean;
import com.tgi.safeher.beans.ColorBean;
import com.tgi.safeher.beans.CountryBean;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.DriverConfigBean;
import com.tgi.safeher.beans.DriverInfoBean;
import com.tgi.safeher.beans.LogOutBean;
import com.tgi.safeher.beans.PayPalBean;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ProfileInformationBean;
import com.tgi.safeher.beans.ReasonBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideQuickInfoBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.beans.StateProvinceBean;
import com.tgi.safeher.beans.UserFavoritiesBean;
import com.tgi.safeher.beans.UserImageBean;
import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.beans.VehicleMakeInfoBean;
import com.tgi.safeher.beans.ZipCodeBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.BankAccountInfoEntity;
import com.tgi.safeher.entity.BankEntity;
import com.tgi.safeher.entity.CityEntity;
import com.tgi.safeher.entity.ColorEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.DriverEndorcementEntity;
import com.tgi.safeher.entity.DriverInfoEntity;
import com.tgi.safeher.entity.DriverRestrictionEntity;
import com.tgi.safeher.entity.DriverVehClassEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonDetailHistoryEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.ReasonEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideFinalizeEntity;
import com.tgi.safeher.entity.RideQuickInfoEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserElectronicResourceEntity;
import com.tgi.safeher.entity.UserFavoritiesEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.VehicleInfoEntity;
import com.tgi.safeher.entity.VehicleMakeEntity;
import com.tgi.safeher.entity.VehicleModelEntity;
import com.tgi.safeher.entity.ZipCodeEntity;
import com.tgi.safeher.utils.CreditCardUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
public class SignUpConverter implements Serializable {

	public void validateDriver(SafeHerDecorator decorator) {

		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			if (StringUtil.isNotEmpty(bean.getIsSocial())
					&& bean.getIsSocial().equals("1")) {
				if (StringUtil.isEmpty(bean.getSocialId())) {
					decorator.getErrors().add("Please provide Social Id");
				}
				return;
			}
			if (StringUtil.isEmpty(bean.getFirstName())) {
				decorator.getErrors().add("Please provide fist name");
			}
			if (StringUtil.isEmpty(bean.getLastName())) {
				decorator.getErrors().add("Please provide last name");
			}
			if (StringUtil.isEmpty(bean.getEmail())) {
				decorator.getErrors().add("Please provide email");
			}
			if (StringUtil.isEmpty(bean.getPassword())) {
				decorator.getErrors().add("Please provide password");
			}
			// if(StringUtil.isEmpty(bean.getGender())){
			// decorator.getErrors().add("Please provide gender");
			// }
			// if(StringUtil.isEmpty(bean.getLocaion())){
			// decorator.getErrors().add("Please provide location");
			// }
			if (StringUtil.isEmpty(bean.getPhoneNumber())) {
				decorator.getErrors().add("Please provide phone number");
			}
			if (StringUtil.isEmpty(bean.getOsType())) {
				decorator.getErrors().add("Please provide OS Information");
			}
			if(StringUtil.isEmpty(bean.getKeyToken())){
				 decorator.getErrors().add("Please provide Key Token");
			}
		}

	}

	public void validateLocationAddress(SafeHerDecorator decorator) {

		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			if (StringUtil.isEmpty(bean.getLocation())) {
				decorator.getErrors().add("Please provide physical address");
			}
			if (StringUtil.isEmpty(bean.getCityName())) {
				decorator.getErrors().add("Please provide city");
			}
//			if (StringUtil.isEmpty(bean.getStateName())) {
//				decorator.getErrors().add("Please provide state");
//			}
			if (StringUtil.isEmpty(bean.getStateId())) {
				decorator.getErrors().add("Please provide state id");
			}
			if (StringUtil.isEmpty(bean.getZipCode())) {
				decorator.getErrors().add("Please provide zip code");
			}

			if (StringUtil.isEmpty(bean.getMailingAddress())) {
				decorator.getErrors().add("Please provide mailing physical address");
			}
			if (StringUtil.isEmpty(bean.getMailingCityName())) {
				decorator.getErrors().add("Please provide mailing city");
			}
			if (StringUtil.isEmpty(bean.getMailingStateId())) {
				decorator.getErrors().add("Please provide mailing state id");
			}
			if (StringUtil.isEmpty(bean.getMailingZipCode())) {
				decorator.getErrors().add("Please provide mailing zip code");
			}
		}else{
			decorator.getErrors().add("Please provide appUserId");
		}

	}

	public void validateSignUpUser(SafeHerDecorator decorator) {

		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			if (StringUtil.isNotEmpty(bean.getIsSocial())
					&& bean.getIsSocial().equals("1")) {
				if (StringUtil.isEmpty(bean.getSocialId())) {
					decorator.getErrors().add("Please provide Social Id");
				}
				return;
			}
			if (StringUtil.isEmpty(bean.getFirstName())) {
				decorator.getErrors().add("Please provide fist name");
			}
			if (StringUtil.isEmpty(bean.getLastName())) {
				decorator.getErrors().add("Please provide last name");
			}
			if (StringUtil.isEmpty(bean.getEmail())) {
				decorator.getErrors().add("Please provide email");
			}
			if (StringUtil.isEmpty(bean.getPassword())) {
				decorator.getErrors().add("Please provide password");
			}
			if (StringUtil.isEmpty(bean.getPhoneNumber())) {
				decorator.getErrors().add("Please provide phone number");
			}
			if (StringUtil.isEmpty(bean.getIsFromWindow())) {
				decorator.getErrors().add("Please provide isFromWindow Information");
			}else{
				if (bean.getIsFromWindow().equals("0")) {
					if (StringUtil.isEmpty(bean.getOsType())) {
						decorator.getErrors().add("Please provide OS Information");
					}
					if(StringUtil.isEmpty(bean.getKeyToken())){
						 decorator.getErrors().add("Please provide Key Token");
					}	
//					if(StringUtil.isEmpty(bean.getFcmToken())){
//						 decorator.getErrors().add("Please provide FCM Token");
//					}	
					if(StringUtil.isEmpty(bean.getIsDev())){
						 decorator.getErrors().add("Please provide isDev");
					}		
				}
			}
		}

	}

	public void convertBeanToPersonEntity(AppUserBean bean, PersonEntity entity) {

		if (StringUtil.isNotEmpty(bean.getFirstName())) {
			entity.setFirstName(bean.getFirstName());
		}
		if (StringUtil.isNotEmpty(bean.getLastName())) {
			entity.setLastName(bean.getLastName());
		}
		if (StringUtil.isNotEmpty(bean.getGender())) {
			entity.setSex(bean.getGender());
		}
		entity.setIsBlocked("0");

	}

	public void convertBeanToPersonDetailEntity(AppUserBean bean,
			PersonDetailEntity entity) {

		if (StringUtil.isNotEmpty(bean.getEmail())) {
			entity.setPrimaryEmail(bean.getEmail());
		}
		if (StringUtil.isNotEmpty(bean.getAltEmail())) {
			entity.setSecondaryEmail(bean.getAltEmail());
		}
		if (StringUtil.isNotEmpty(bean.getPhoneNumber())) {
			entity.setPrimaryCell(bean.getPhoneNumber());
		}
		if (StringUtil.isNotEmpty(bean.getAltPhoneNumber())) {
			entity.setSecondaryCell(bean.getAltPhoneNumber());
		}
		if (StringUtil.isNotEmpty(bean.getResPhone())) {
			entity.setResPhone(bean.getResPhone());
		}
	}

	public void convertBeanToUserLoginEntity(AppUserBean bean,
			UserLoginEntity entity) {

		if (StringUtil.isNotEmpty(bean.getEmail())) {
			entity.setLoginEmail(bean.getEmail());
		}
		if (StringUtil.isNotEmpty(bean.getPassword())) {
			entity.setPswd(EncryptDecryptUtil.
					encrypt(bean.getPassword()));
		}
		if (StringUtil.isNotEmpty(bean.getIsSocial())) {
			entity.setIsSocial(bean.getIsSocial());
		}
		if (StringUtil.isNotEmpty(bean.getSocialId())) {
			entity.setSocialId(bean.getSocialId());
		}
		if (StringUtil.isNotEmpty(bean.getKeyToken())) {
			entity.setKeyToken(bean.getKeyToken());
		}
		if (StringUtil.isNotEmpty(bean.getFcmToken())) {
			entity.setFcmToken(bean.getFcmToken());
		}
		if (StringUtil.isNotEmpty(bean.getOsType())) {
			entity.setOsType(bean.getOsType());
		}
		if (StringUtil.isNotEmpty(bean.getIsDev())) {
			entity.setIsDev(bean.getIsDev());
		}
		if (StringUtil.isNotEmpty(bean.getGender())) {
			entity.setSex(bean.getGender());
		}

		entity.setIsActive("0");
		entity.setCurrentLoginCount(0L);

	}

	public void populatePersonDetailHistoryFromPersonDetail(
			PersonDetailEntity detailEntity, PersonDetailHistoryEntity historyEntity) {
		if (StringUtil.isNotEmpty(detailEntity.getPersonDetailId() + "")) {
			if (StringUtil.isNotEmpty(detailEntity.getPrimaryEmail())) {
				historyEntity.setPrimaryEmail(detailEntity.getPrimaryEmail());
			}
			if (StringUtil.isNotEmpty(detailEntity.getSecondaryEmail())) {
				historyEntity.setSecondaryEmail(detailEntity
						.getSecondaryEmail());
			}
			if (StringUtil.isNotEmpty(detailEntity.getPrimaryCell())) {
				historyEntity.setPrimaryCell(detailEntity.getPrimaryCell());
			}
			if (StringUtil.isNotEmpty(detailEntity.getSecondaryCell())) {
				historyEntity.setSecondaryCell(detailEntity.getSecondaryCell());
			}
			if (StringUtil.isNotEmpty(detailEntity.getResPhone())) {
				historyEntity.setResPhone(detailEntity.getResPhone());
			}
			historyEntity.setPersonDetail(detailEntity);
			historyEntity.setChangeDate(DateUtil.getCurrentTimestamp());
		}
	}

	public void convertBeanToAppUserEntity(AppUserBean bean,
			AppUserEntity entity) {

		StatusEntity statusEntity = new StatusEntity();
		statusEntity.setStatusId(StatusEnum.New.getValue());
		if (StringUtil.isNotEmpty(bean.getIsDriver())) {
			entity.setIsDriver(bean.getIsDriver());
		}
		entity.setUserDate(DateUtil.getCurrentTimestamp());
		entity.setIsActivated("1");
		entity.setUserStatus(statusEntity);
		// entity.setRegistrationDate(DateUtil.getCurrentTimestamp()+"");

	}

	public void validateUserSignIn(SafeHerDecorator decorator) {

		SignInBean bean = (SignInBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getIsSocial())) {
			decorator.getErrors().add("Please provide your isSocial");
			return;
		}else{
			if (bean.getIsSocial().equals("1")) {
				if (StringUtil.isEmpty(bean.getSocialId())) {
					decorator.getErrors().add("Please provide your socialId");
					return;	
				}
			}
		}
		if (bean.getIsSocial().equals("0")) {
			if (StringUtil.isEmpty(bean.getEmail())) {
				decorator.getErrors().add("Please provide your Email");
				return;
			}
			if (StringUtil.isEmpty(bean.getPassword())) {
				decorator.getErrors().add("Please provide your Password");
				return;
			}
		}
		if (StringUtil.isEmpty(bean.getKeyToken())) {
			decorator.getErrors().add("Please provide your Key Token");
			return;
		}
		if (StringUtil.isEmpty(bean.getOsType())) {
			decorator.getErrors().add("Please provide your Os Information");
			return;
		}
				
	}

	public void convertPersonEnityToBean(UserLoginEntity entity,
			SafeHerDecorator decorator) {

		AppUserBean bean = new AppUserBean();
		if (entity.getAppUser() != null) {
			bean.setAppUserId(entity.getAppUser().getAppUserId() + "");
			if (entity.getAppUser().getPerson() != null) {
				PersonEntity personEntity = entity.getAppUser().getPerson();
				if (StringUtil.isNotEmpty(personEntity.getFirstName())) {
					bean.setFirstName(personEntity.getFirstName());
				}
				if (StringUtil.isNotEmpty(personEntity.getLastName())) {
					bean.setLastName(personEntity.getLastName());
				}
				if (StringUtil.isNotEmpty(personEntity.getSex())) {
					bean.setGender(personEntity.getSex());
				}
			}
		}
		if (StringUtil.isNotEmpty(entity.getLoginEmail())) {
			bean.setEmail(entity.getLoginEmail());
		}
		if (StringUtil.isNotEmpty(entity.getIsSocial())) {
			bean.setIsSocial(entity.getIsSocial());
		}
		decorator.setDataBean(bean);

	}

	public AppUserBean convertEntitytoAppUserBeanForLicence(AppUserEntity entity) {
		AppUserBean bean = new AppUserBean();
		if (entity.getAppUserId() != null) {
			bean.setAppUserId(entity.getAppUserId().toString());
		}
		if (entity.getDriverInfo() != null) {
			bean.setDriverInfoId(entity.getDriverInfo().getDriverInfoId()
					.toString());
		}
		// if(StringUtil.isNotEmpty(entity.getIsDriver())){
		bean.setIsDriver(bean.getIsDriver());
		// }
		return bean;

	}

	public AppUserBean convertAppiPersonEnityToBean(UserLoginEntity entity,
			SafeHerDecorator decorator) {

		AppUserBean bean = new AppUserBean();
		if (entity.getAppUser() != null) {
			bean.setAppUserId(entity.getAppUser().getAppUserId() + "");
			bean.setIsDriver(entity.getAppUser().getIsDriver());
			if (entity.getAppUser().getPerson() != null) {
				PersonEntity personEntity = entity.getAppUser().getPerson();
				if (StringUtil.isNotEmpty(personEntity.getFirstName())) {
					bean.setFirstName(personEntity.getFirstName());
				}
				if (StringUtil.isNotEmpty(personEntity.getLastName())) {
					bean.setLastName(personEntity.getLastName());
				}
				if (StringUtil.isNotEmpty(personEntity.getSex())) {
					bean.setGender(personEntity.getSex());
				}
			}
			if (entity.getAppUser().getPersonDetail() != null) {
				PersonDetailEntity personDetailEntity = entity.getAppUser()
						.getPersonDetail();
				if (StringUtil.isNotEmpty(personDetailEntity.getPrimaryCell())) {
					bean.setPhoneNumber(personDetailEntity.getPrimaryCell());
				}
			}
		}
		if (StringUtil.isNotEmpty(entity.getLoginEmail())) {
			bean.setEmail(entity.getLoginEmail());
		}
		if (StringUtil.isNotEmpty(entity.getIsSocial())) {
			bean.setIsSocial(entity.getIsSocial());
		}
		bean.setIsActive(entity.getIsActive());
		return bean;

	}

	public DriverInfoEntity validateBeanToDriverInfoEntity(DriverInfoBean bean) {
		DriverInfoEntity infoIntity = new DriverInfoEntity();
		if (StringUtil.isNotEmpty(bean.getLicenceNo())) {
			infoIntity.setCurrentLicenceNo(bean.getLicenceNo());
		}
		if (StringUtil.isNotEmpty(bean.getExpiryDate())) {

			infoIntity.setCurrentLicenceExpiry(DateUtil
					.parseTimestampFromFormats(bean.getExpiryDate()));
		}
		// if(bean.getStateObj()!=null)
		// {
		// infoIntity.setStateProvince(bean.getStateObj());
		// }
		infoIntity.setDriverNo("s100" + bean.getAppUserId());
		return infoIntity;

	}

	public AppUserEntity validateAppUser(DriverInfoBean driverBean) {
		AppUserEntity info = new AppUserEntity();
		if (StringUtil.isNotEmpty(driverBean.getDriverInfoId())) {

		}
		return info;

	}

	public void validateDriverLicence(SafeHerDecorator decorator) {

		DriverInfoBean bean = (DriverInfoBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getExpiryDate())) {
			decorator.getErrors().add("Please provide Licence Expire Date");
		}
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}
		if (StringUtil.isEmpty(bean.getLicenceNo())) {
			decorator.getErrors().add("Please provide Licence No ");
		}
		if (StringUtil.isEmpty(bean.getStateId())) {
			decorator.getErrors().add("Please provide State Info ");
		}

	}

	public void populateAppUserBiometricEntity(
			AppUserBiometricEntity appUserBiometricEntity) {
		appUserBiometricEntity.setIsActive("1");
		appUserBiometricEntity.setProvidedDate(new Timestamp(new Date()
				.getTime()));
		appUserBiometricEntity.setEndDate(new Timestamp(new Date().getTime()));
		// appUserBiometricEntity.setBiometricType(BioMetricTypeEnum.Face);
	}

	public List<VehicleMakeInfoBean> convertEntiyToVehicleBean(
			List<VehicleMakeEntity> veh) {
		List<VehicleMakeInfoBean> newLst = new ArrayList<VehicleMakeInfoBean>();
		for (VehicleMakeEntity v1 : veh) {
			VehicleMakeInfoBean v2 = new VehicleMakeInfoBean();
			v2.setName(v1.getName());
			v2.setVehicleMakeId(v1.getVehicleMakeId().toString());
			newLst.add(v2);
		}
		return newLst;

	}

	public List<VehicleMakeInfoBean> convertVehicleModelEntiyToVehicleBean(
			List<VehicleModelEntity> veh) {
		List<VehicleMakeInfoBean> newLst = new ArrayList<VehicleMakeInfoBean>();
		for (VehicleModelEntity v1 : veh) {
			VehicleMakeInfoBean v2 = new VehicleMakeInfoBean();
			v2.setVehicleModelName(v1.getName());
			v2.setVehicleMakeId(v1.getVehicleMake().getVehicleMakeId()
					.toString());
			v2.setVehicleModelId(v1.getVehicleModelId().toString());
			newLst.add(v2);
		}
		return newLst;

	}

	public void validateCreditCardInfo(SafeHerDecorator decorator) {
		CreditCardInfoBean bean = (CreditCardInfoBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getFirstName())) {
			decorator.getErrors().add("Please provide fist name");
		}
		if (StringUtil.isEmpty(bean.getLastName())) {
			decorator.getErrors().add("Please provide last name");
		}
		if (StringUtil.isEmpty(bean.getCreditCardNo())) {
			decorator.getErrors().add("Please provide card no");
		}
		if (StringUtil.isEmpty(bean.getExpiryDate())) {
			decorator.getErrors().add("Please provide Expiry Date");
		}
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getCvv())) {
			decorator.getErrors().add("Please provide Cvv No");
		}
		if (CreditCardUtil.getCreditCardTypeByNumber(bean.getCreditCardNo())
				.equals("invalid")) {
			decorator.getErrors()
					.add("Please provide Valid Credit Card Number");
		}
		if (StringUtil.isEmpty(bean.getIsDefault())) {
			decorator.getErrors().add("Please Provide IsDefault Information");
		}
		if (StringUtil.isEmpty(bean.getNounce())) {
			decorator.getErrors().add("Please Provide Nounce");
		}
	}

	public CreditCardInfoEntity convertBeanToCreditCardInfoEntity(
			CreditCardInfoBean bean) {
		CreditCardInfoEntity entity = new CreditCardInfoEntity();
		entity.setCardNumber(bean.getCreditCardNo());
		entity.setFirstName(bean.getFirstName());
		entity.setLastName(bean.getLastName());
		entity.setIsActive("1");
		/*entity.setExpiryDate(DateUtil.parseTimestampFromFormats(bean
				.getExpiryDate()));*/
//		entity.setExpiryDate(DateUtil.parseTimestampFromFormats(bean
//			    .getExpiryDate()+" 00:00:00"));
		entity.setExpiryDate(Timestamp.valueOf("2023-10-12 18:48:05.123"));
		entity.setCvv(bean.getCvv());

		return entity;

	}

	public CreditCardInfoBean convertEntityToCreditCardInofBean(CreditCardInfoEntity entity) {

		CreditCardInfoBean bean = new CreditCardInfoBean();
		bean.setCreditCardInfoId(entity.getCreditCardInfoId()+"");
		bean.setCreditCardNo(entity.getCardNumber());
		bean.setFirstName(entity.getFirstName());
		bean.setLastName(entity.getLastName());
		bean.setIsActive(entity.getIsActive());
		bean.setCvv(entity.getCvv());
		bean.setExpiryDate(entity.getExpiryDate()+"");
		if(entity.getAppUserPaymentInfo() != null){
			bean.setAppPaymentUserId(
					entity.getAppUserPaymentInfo().getAppUserPaymentInfoId()+"");
		}
		bean.setBtCustomer(entity.getBtCustomerNo());

		return bean;

	}

	public AppUserPaymentInfoEntity convertBeanAppUserPaymentInfoEntity() {
		AppUserPaymentInfoEntity appUser = new AppUserPaymentInfoEntity();
		appUser.setIsCard("1");
		appUser.setDefaultType("C");
		return appUser;
	}

	public void validateBankAccountInfo(SafeHerDecorator decorator) {

		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(bean.getAccountNo())) {
			decorator.getErrors().add("Please provide Account No");
		}
		if (StringUtil.isEmpty(bean.getAccountTitle())) {
			decorator.getErrors().add("Please provide Account Title");
		}
		if (StringUtil.isEmpty(bean.getLocation())) {
			decorator.getErrors().add("Please provide Bank Address");
		}
		if (StringUtil.isEmpty(bean.getIbanNo())) {
			decorator.getErrors().add("Please provide IBAN No");
		}
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getRoutingNo())) {
			decorator.getErrors().add("Please provide Routing no");
		}
		if (StringUtil.isEmpty(bean.getSwiftCode())) {
			decorator.getErrors().add("Please provide Swift Code");
		}
		if (StringUtil.isEmpty(bean.getBankId())) {
			decorator.getErrors().add("Please provide Bank Information");
		}
		if (StringUtil.isEmpty(bean.getZipCode())) {
			decorator.getErrors().add("Please provide Zip Code");
		}
		if (StringUtil.isEmpty(bean.getIsDefault())) {
			decorator.getErrors().add("Please provide Default ");
		}
	}

	public void validateBankAccountInfoV2(SafeHerDecorator decorator) {

		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getAccountNo())) {
			decorator.getErrors().add("Please provide Account No");
		}
		if (StringUtil.isEmpty(bean.getAccountTitle())) {
			decorator.getErrors().add("Please provide Account Title");
		}
		if (StringUtil.isEmpty(bean.getRoutingNo())) {
			decorator.getErrors().add("Please provide Routing no");
		}
		if (StringUtil.isEmpty(bean.getIsDefault())) {
			decorator.getErrors().add("Please provide Default ");
		}
	}

	public BankAccountInfoEntity convertBeanToBankAccountInfoEntity(
			BankAccountInfoBean bean) {
		BankAccountInfoEntity entity = new BankAccountInfoEntity();
		entity.setAccountNo(bean.getAccountNo());
		entity.setAccountTitle(bean.getAccountTitle());
		entity.setIbanNo(bean.getIbanNo());
		entity.setIsActive(bean.getIsActive());
		entity.setLocation(bean.getLocation());
		entity.setSwiftCode(bean.getSwiftCode());
		entity.setRoutingNo(bean.getRoutingNo());
		entity.setZipcode(bean.getZipCode());
		entity.setIsDefault(bean.getIsDefault());
		entity.setIsActive("1");
		return entity;

	}

	public List<BankBean> convertEntiyToBankBean(List<BankEntity> bnk) {
		List<BankBean> newLst = new ArrayList<BankBean>();
		for (BankEntity v1 : bnk) {
			BankBean v2 = new BankBean();
			v2.setBankId(v1.getBankId().toString());
			v2.setName(v1.getName());
			newLst.add(v2);
		}
		return newLst;

	}

	public List<ColorBean> convertEntiyToVehicleColorBean(List<ColorEntity> veh) {
		List<ColorBean> newLst = new ArrayList<ColorBean>();
		for (ColorEntity v1 : veh) {
			ColorBean v2 = new ColorBean();
			v2.setColorId(v1.getColorId().toString());
			v2.setName(v1.getName());
			newLst.add(v2);
		}
		return newLst;

	}

	public VehicleInfoEntity convertBeanToVehicleInfoEntity(VehicleInfoBean bean) {
		VehicleInfoEntity entity = new VehicleInfoEntity();
		entity.setManufacturingYear(bean.getManufacturingYear());
		entity.setPlateNumber(bean.getPlateNumber());
		entity.setIsActive(bean.getIsActive());
		entity.setTitle(bean.getTitle());
		// entity.setSeatCapacity(Short.valueOf(bean.getSeatCapacity()));
		return entity;
	}

	public void validateVehicleInfo(SafeHerDecorator decorator) {

		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getColor())) {
			decorator.getErrors().add("Please provide Vehicle Color");
		}
		if (StringUtil.isEmpty(bean.getVehicleMake())) {
			decorator.getErrors().add("Please provide Vehicle Make");
		}
		if (StringUtil.isEmpty(bean.getVehicleModel())) {
			decorator.getErrors().add("Please provide Vehicle Model");
		}
		if (StringUtil.isEmpty(bean.getManufacturingYear())) {
			decorator.getErrors().add("Please provide Manufacturing Year");
		}
		// if(StringUtil.isEmpty(bean.getTitle())){
		// decorator.getErrors().add("Please provide gender");
		// }
		if (StringUtil.isEmpty(bean.getPlateNumber())) {
			decorator.getErrors().add("Please provide Registration Number");
		}
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Application User ID ");
		}
		// if(StringUtil.isEmpty(bean.getSeatCapacity())){
		// decorator.getErrors().add("Please provide Vehicle Seat Capacity");
		// }

	}

	public void validateUserFavorities(SafeHerDecorator decorator) {

		UserFavoritiesBean bean = (UserFavoritiesBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())
				&& bean.getAppUserId().equalsIgnoreCase("0")) {
			decorator.getErrors().add("App user don't exist");
		}
		if (StringUtil.isEmpty(bean.getFavorityType())) {
			decorator.getErrors().add("Please provide favorite type");
		} else {
			if (new Integer(bean.getFavorityType()) <= 0
					|| new Integer(bean.getFavorityType()) > 3) {
				decorator.getErrors().add(
						"You can only 1 or 2 or 3 for favortie type ");
			}
		}
		if (StringUtil.isEmpty(bean.getFavLat())) {
			decorator.getErrors().add("Please provide favorite lattitude");
		}
		if (StringUtil.isEmpty(bean.getFavLong())) {
			decorator.getErrors().add("Please provide favorite longtitude");
		}

	}

	public void populateUserFavoritiesEntityFromBean(
			UserFavoritiesEntity entity, UserFavoritiesBean bean) {
		entity.setFovLat(bean.getFavLat());
		entity.setFovLong(bean.getFavLong());
		entity.setLable(bean.getLable());
		entity.setFavoritiesLocation(bean.getLocation());
		entity.setPlaceId(bean.getPlaceId());
	}

	public void populateBeanFromUserFavoritiesEntity(
			UserFavoritiesEntity entity, UserFavoritiesBean bean) {
		bean.setUserFavoritiesId(entity.getUserFavoritiesId() + "");
		if (entity.getAppUser() != null) {
			bean.setAppUserId(entity.getAppUser().getAppUserId() + "");
		}
		if (entity.getFavorityType() != null) {
			bean.setFavorityType(entity.getFavorityType().getFavorityTypeId()
					+ "");
		}
		bean.setFavLat(entity.getFovLat());
		bean.setFavLong(entity.getFovLong());
		bean.setLable(entity.getLable());
		bean.setLocation(entity.getFavoritiesLocation());
		if(entity.getPlaceId()!=null){
			bean.setPlaceId(entity.getPlaceId());
		}
	
	}

	public void validateUserSignOut(SafeHerDecorator decorator) {

		LogOutBean bean = (LogOutBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide your App User Id");
		}
		if (StringUtil.isEmpty(bean.getUserLoginId())) {
			// we dont need to check userLoginId
			// decorator.getErrors().add("Please provide your User Login Id");
		}

	}

	public void validateAddressBean(SafeHerDecorator decorator) {

		AddressBean bean = (AddressBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getStateProvince())) {
			decorator.getErrors().add("Please provide State Province ");
		}
		if (StringUtil.isEmpty(bean.getCity())) {
			decorator.getErrors().add("Please provide City Information");
		}
		if (StringUtil.isEmpty(bean.getCountry())) {
			decorator.getErrors().add("Please provide Country Information");
		}
		// if(StringUtil.isEmpty(bean.getFromDate())){
		// decorator.getErrors().add("Please provide From Date Information");
		// }
		if (StringUtil.isEmpty(bean.getAppUser())) {
			decorator.getErrors().add("Please provide App User Information");
		}
		if (StringUtil.isEmpty(bean.getZipCode())) {
			decorator.getErrors().add("Please provide Zip Code Information");
		}
		if (StringUtil.isEmpty(bean.getAddressLineOne())) {
			decorator.getErrors().add("Please provide Address 1 Information ");
		}
		if (StringUtil.isEmpty(bean.getAddressLineTwo())) {
			decorator.getErrors().add("Please provide Address 2 Information");
		}
		if (StringUtil.isEmpty(bean.getAddressType())) {
			decorator.getErrors()
					.add("Please provide Address Type Information");
		}
	}

	public List<CountryBean> convertEntiyToCountryBean(List<CountryEntity> veh) {
		List<CountryBean> newLst = new ArrayList<CountryBean>();
		for (CountryEntity v1 : veh) {
			CountryBean v2 = new CountryBean();
			v2.setName(v1.getName());
			v2.setCountryId(v1.getCountryId().toString());
			v2.setCode(v1.getCode());
			v2.setAbbriviation(v1.getAbbreviation());
			newLst.add(v2);
		}
		return newLst;

	}

	public List<StateProvinceBean> convertEntiyToStateBean(
			List<StateProvinceEntity> veh) {
		List<StateProvinceBean> newLst = new ArrayList<StateProvinceBean>();
		for (StateProvinceEntity v1 : veh) {
			StateProvinceBean v2 = new StateProvinceBean();
			v2.setName(v1.getName());
			v2.setStateId(v1.getStateId().toString());
			v2.setCode(v1.getCode());
			v2.setCountryId(v1.getCountry().getCountryId().toString());
			newLst.add(v2);
		}
		return newLst;

	}

	public List<CityBean> convertEntiyToCityBean(List<CityEntity> veh) {
		List<CityBean> newLst = new ArrayList<CityBean>();
		for (CityEntity v1 : veh) {
			CityBean v2 = new CityBean();
			v2.setName(v1.getName());
			v2.setStateId(v1.getStateProvince().getStateId().toString());
			v2.setCode(v1.getCode());
			v2.setCountryId(v1.getCountry().getCountryId().toString());
			newLst.add(v2);
		}
		return newLst;

	}

	public List<ZipCodeBean> convertEntiyToZipCode(List<ZipCodeEntity> veh) {
		List<ZipCodeBean> newLst = new ArrayList<ZipCodeBean>();
		for (ZipCodeEntity v1 : veh) {
			ZipCodeBean v2 = new ZipCodeBean();
			v2.setCityId(v1.getCity().getCityId().toString());
			v2.setZipCode(v1.getZipCode());
			v2.setZipCodeId(v1.getZipCodeId().toString());
			newLst.add(v2);
		}
		return newLst;

	}

	public void validateState(SafeHerDecorator decorator) {

		StateProvinceBean bean = (StateProvinceBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getCountryId())) {
			decorator.getErrors().add("Please Select Country First");
		}

	}

	public void validateCity(SafeHerDecorator decorator) {

		CityBean bean = (CityBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getCountryId())) {
			decorator.getErrors().add("Please Select Country First");
		}
		if (StringUtil.isEmpty(bean.getStateId())) {
			decorator.getErrors().add("Please Select State First");
		}

	}

	public void validateZipCode(SafeHerDecorator decorator) {

		ZipCodeBean bean = (ZipCodeBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getCityId())) {
			decorator.getErrors().add("Please Select City First");
		}

	}

	public void populateUserElecRes(SafeHerDecorator decorator,
			List<UserElectronicResourceEntity> list) {
		UserImageBean bean = (UserImageBean) decorator.getDataBean();
		UserImageBean imageBean = new UserImageBean();
		for (UserElectronicResourceEntity entity : list) {
			if (entity.getAppUser() != null) {
				// String path = entity.getPath().replace("\\\\", "\\");
				imageBean = new UserImageBean();
				imageBean.setAppUserId(entity.getAppUser().getAppUserId() + "");
				imageBean.setMediaUrl(entity.getPath());
				// imageBean.setMediaUrl(path);
				imageBean.setUserElecResourceId(entity
						.getUserElectronicResourceId() + "");
				bean.getImageList().add(imageBean);
			}
		}
	}

	public BankAccountInfoBean convertEntityToBankBean(
			BankAccountInfoEntity bankInfoEntity) {
		BankAccountInfoBean bean = new BankAccountInfoBean();
		bean.setAccountNo(bankInfoEntity.getAccountNo());
		bean.setBankAccountInfoId(bankInfoEntity.getBankAccountInfoId()
				.toString());
		bean.setAccountTitle(bankInfoEntity.getAccountTitle());
		bean.setBankId(bankInfoEntity.getBank().getBankId().toString());
		// bean.setAppUserId(bankInfoEntity.get)
		bean.setAppUserPaymentInfoId(bankInfoEntity.getAppUserPaymentInfo()
				.getAppUserPaymentInfoId().toString());
		bean.setRoutingNo(bankInfoEntity.getRoutingNo());
		bean.setSwiftCode(bankInfoEntity.getSwiftCode());
		bean.setIbanNo(bankInfoEntity.getIbanNo());
		bean.setIsActive(bankInfoEntity.getIsActive());
		bean.setLocation(bankInfoEntity.getLocation());
		bean.setZipCode(bankInfoEntity.getZipcode());
		return bean;

	}

	public void validateGetDriverIfo(SafeHerDecorator decorator) {

		DriverInfoBean bean = (DriverInfoBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}

	}

	public DriverInfoBean convertEntityToDriverInfoBean(DriverInfoEntity entity) {
		DriverInfoBean bean = new DriverInfoBean();
		bean.setStateId(entity.getStateProvince().getStateId().toString());
		bean.setDriverInfoId(entity.getDriverInfoId().toString());
		bean.setDriverNo(entity.getDriverNo());
		String expiryDated = entity.getCurrentLicenceExpiry().toString();
		expiryDated = expiryDated.substring(0, 10);
		bean.setExpiryDate(expiryDated);
		bean.setLicenceNo(entity.getCurrentLicenceNo());
		return bean;

	}

	public void validateGetVehicleIfo(SafeHerDecorator decorator) {

		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}

	}

	public VehicleInfoBean convertEntityToVehicleInfoBean(
			VehicleInfoEntity entity) {
		VehicleInfoBean bean = new VehicleInfoBean();
		bean.setIsActive(entity.getIsActive());
		bean.setColor(entity.getColor().getColorId().toString());
		bean.setPlateNumber(entity.getPlateNumber());
		// bean.setRoleAppUser(entity.ge);
		if (entity.getSeatCapacity() != null) {
			bean.setSeatCapacity(entity.getSeatCapacity().toString());
		}
		bean.setStatus(entity.getStatus().getStatusId().toString());
		bean.setTitle(entity.getTitle());
		bean.setVehicleInfoId(entity.getVehicleInfoId().toString());
		bean.setVehicleMake(entity.getVehicleMake().getVehicleMakeId()
				.toString());
		bean.setVehicleModel(entity.getVehicleModel().getVehicleModelId()
				.toString());
		bean.setSeatCapacity(entity.getSeatCapacity() + "");
		bean.setManufacturingYear(entity.getManufacturingYear());
		return bean;

	}

	public BankAccountInfoEntity convertBeanToBankAccountInfoEntityForUpdate(
			BankAccountInfoBean bean, BankAccountInfoEntity entity) {
		entity.setAccountNo(bean.getAccountNo());
		entity.setAccountTitle(bean.getAccountTitle());
		// entity.setIbanNo(bean.getIbanNo());
	//	entity.setIsActive(bean.getIsActive());
		entity.setLocation(bean.getLocation());
		entity.setSwiftCode(bean.getSwiftCode());
		entity.setRoutingNo(bean.getRoutingNo());
	//	entity.setIsActive("1");
		return entity;

	}

	public VehicleInfoEntity convertBeanToUpdateVehicleInfo(
			VehicleInfoBean bean, VehicleInfoEntity vehicleInfoEntity) {
		vehicleInfoEntity.setManufacturingYear(bean.getManufacturingYear());
		vehicleInfoEntity.setPlateNumber(bean.getPlateNumber());
		vehicleInfoEntity
				.setSeatCapacity(Short.valueOf(bean.getSeatCapacity()));
		// vehicleInfoEntity.setChildSeatCapacity(bean.get);
		vehicleInfoEntity.setIsActive(bean.getIsActive());
		return vehicleInfoEntity;

	}

	public void validGetBankInfo(SafeHerDecorator decorator) {

		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please Enter App User ID");
		}
	}

	public void validateDriverVisibility(SafeHerDecorator decorator) {
		SignInBean bean = (SignInBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}
	}

	public void convertBeanToRideReqResEntity(RideRequestResponseEntity entity,
			RideRequestResponseBean bean) {
		AppUserEntity appUserEntity = new AppUserEntity();
		RideRequestResponseEntity rideRequestResponseEntity = new RideRequestResponseEntity();
		RideSearchResultDetailEntity searchResultDetailEntity = new RideSearchResultDetailEntity();
		StatusEntity statusEntity = new StatusEntity();
		StatusEntity statusResponse = new StatusEntity();
		if (StringUtil.isEmpty(bean.getStatusRespons())) {
			bean.setStatusRespons("6");
		}
		statusResponse.setStatusId(new Integer(bean.getStatusRespons()));
		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		statusEntity.setStatusId(new Integer(bean.getStatusRespons()));
		searchResultDetailEntity.setRideSearchResultDetailId(new Integer(bean
				.getRideSearchResultDetailId()));
		rideRequestResponseEntity.setAppUser(appUserEntity);
		rideRequestResponseEntity
				.setRideSearchResultDetail(searchResultDetailEntity);
		rideRequestResponseEntity.setStatusByStatusFinal(statusEntity);
		entity.setRequestTime(DateUtil.getCurrentTimestamp());

	}

	public void convertBeanToFinalizeEntity(RideFinalizeEntity entity,
			RideRequestResponseBean bean) {
		RideRequestResponseEntity rideRequestResponseEntity = new RideRequestResponseEntity();
		RideCriteriaEntity rideCriteriaEntity = new RideCriteriaEntity();

//		rideRequestResponseEntity.setRideReqResId(new Integer(bean
//				.getRideReqResId()));
		// rideCriteriaEntity.setRideCriteriaId(new
		// Integer(bean.getRideCriteriaId()));
		// entity.setRideCriteria(rideCriteriaEntity);
//		entity.setRideRequestResponse(rideRequestResponseEntity);
		entity.setFinalizeTime(DateUtil.getCurrentTimestamp());

	}

	public void convertBeanToEndorcement(DriverEndorcementEntity entity,
			VehicleInfoBean bean) {
		AppUserEntity appUserEntity = new AppUserEntity();

		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		entity.setAppUser(appUserEntity);
		entity.setExpiryDate(DateUtil.getCurrentTimestamp());
		entity.setIsActive("1");

	}

	public void convertBeanToRestriction(DriverRestrictionEntity entity,
			VehicleInfoBean bean) {
		AppUserEntity appUserEntity = new AppUserEntity();

		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		entity.setAppUser(appUserEntity);
		entity.setExpiryDate(DateUtil.getCurrentTimestamp());
		entity.setIsActive("1");

	}

	public void convertBeanToVehicalClass(DriverVehClassEntity entity,
			VehicleInfoBean bean) {
		AppUserEntity appUserEntity = new AppUserEntity();

		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		entity.setAppUser(appUserEntity);
		entity.setExpiryDate(DateUtil.getCurrentTimestamp());
		entity.setIsActive("1");

	}

	public void validGetCreaditCard(SafeHerDecorator decorator) {

		CreditCardInfoBean bean = (CreditCardInfoBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please Enter App User ID");
		}
	}

	public List<CreditCardInfoBean> convertEntityToCreditCardBean(
			List<CreditCardInfoEntity> creditCardInfoEntity) {
		List<CreditCardInfoBean> lst = new ArrayList<CreditCardInfoBean>();
		CreditCardInfoBean bean = null;
		for (CreditCardInfoEntity crd : creditCardInfoEntity) {
			bean = new CreditCardInfoBean();
			bean.setCreditCardNo(crd.getCardNumber());
			bean.setFirstName(crd.getFirstName());
			bean.setAppPaymentUserId(crd.getAppUserPaymentInfo()
					.getAppUserPaymentInfoId().toString());
			bean.setCreditCardTypeId(crd.getCreditCardType()
					.getCreditCardTypeId().toString());
			bean.setCvv(crd.getCvv());
			bean.setExpiryDate(crd.getExpiryDate().toString());
			bean.setIsActive(crd.getIsActive());
			bean.setLastName(crd.getLastName());
			bean.setAppUserId(crd.getAppUserPaymentInfo().getAppUser()
					.getAppUserId().toString());
			bean.setCreditCardInfoId(crd.getCreditCardInfoId().toString());

			lst.add(bean);

		}
		return lst;
	}

	public void validateColorSelection(SafeHerDecorator decorator) {

		PreRideRequestBean bean = (PreRideRequestBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getPreRideId())) {
			decorator.getErrors().add("Please provide Pre Ride ID");
		}
		// if (StringUtil.isEmpty(bean.getDriverappUserId())) {
		// decorator.getErrors().add("Please provide Driver App User ID");
		// }
		// if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
		// decorator.getErrors().add("Please provide Passenger App User ID");
		// }
		// if (StringUtil.isEmpty(bean.getIsDriver())) {
		// decorator.getErrors().add("Please provide isDriver Flag");
		// }

	}

	public void validatePreRideStart(SafeHerDecorator decorator) {
		PreRideRequestBean bean = (PreRideRequestBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getPreRideId())) {
			decorator.getErrors().add("Please provide Pre Ride ID");
		}
		if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
			decorator.getErrors().add("Please provide Passenger App User ID");
		}
		if (StringUtil.isEmpty(bean.getDriverappUserId())) {
			decorator.getErrors().add("Please provide Driver App User ID");
		}
//		if (StringUtil.isEmpty(bean.getEstimatedTime())) {
//			decorator.getErrors().add("Please provide Estimated Time");
//		}
		if (StringUtil.isEmpty(bean.getRequestNo())) {
			decorator.getErrors().add("Please provide Request No");
		}
	}

	public void convertEntityToRideRequestResponseBean(ReasonEntity entity,
			ReasonBean bean) {

		if (StringUtil.isNotEmpty(entity.getReasonId() + "")) {
			bean.setReasonid(entity.getReasonId() + "");
		}
		if (StringUtil.isNotEmpty(entity.getName())) {
			bean.setReason(entity.getName());
		}
	}

	public void validateMatchColor(SafeHerDecorator decorator) {
		PreRideRequestBean bean = (PreRideRequestBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add(
					"Please provide Driver Flag True Other Wise False");
		} else if (bean.getIsDriver().equals("1")) {
			if (StringUtil.isEmpty(bean.getPreRideId())) {
				decorator.getErrors().add("Please provide Pre Ride ID");
			}
			if (StringUtil.isEmpty(bean.getIsPassengerColorVerified())) {
				decorator.getErrors().add(
						"Please provide Passenger Verification Info");
			}
			if (StringUtil.isEmpty(bean.getDriverVerificationAttemps())) {
				decorator.getErrors().add(
						"Please provide Driver Attemps against Verification");
			}
			if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
				decorator.getErrors().add(
						"Please provide Passenger App User ID");
			}
			if (StringUtil.isEmpty(bean.getDriverappUserId())) {
				decorator.getErrors().add("Please provide Driver App User ID");
			}
		} else if (bean.getIsDriver().equals("0")) {
			if (StringUtil.isEmpty(bean.getPreRideId())) {
				decorator.getErrors().add("Please provide Pre Ride ID");
			}
			if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
				decorator.getErrors().add(
						"Please provide Passenger App User ID");
			}
			if (StringUtil.isEmpty(bean.getDriverappUserId())) {
				decorator.getErrors().add("Please provide Driver App User ID");
			}
			if (StringUtil.isEmpty(bean.getIsDriverColorVerified())) {
				decorator.getErrors().add(
						"Please provide Driver Verification Info");
			}
			if (StringUtil.isEmpty(bean.getPassengerVerificationAttemps())) {
				decorator
						.getErrors()
						.add("Please provide Passenger Attemps against Verification");
			}
		}
	}

	public void validateReachedRideStart(SafeHerDecorator decorator) {
		PreRideRequestBean bean = (PreRideRequestBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getPreRideId())) {
			decorator.getErrors().add("Please provide Pre Ride ID");
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add(
					"Please provide Driver Flag True Other Wise False");
		}
		if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
			decorator.getErrors().add(
					"Please provide Passenger App User ID");
		}
		if (StringUtil.isEmpty(bean.getDriverappUserId())) {
			decorator.getErrors().add("Please provide Driver App User ID");
		}
	}

	public void validateRating(SafeHerDecorator decorator) {
		UserRatingBean bean = (UserRatingBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getUserByAppUserId())) {
			decorator.getErrors().add("Please provide user by id");
		}
		if (StringUtil.isEmpty(bean.getUserToAppUserId())) {
			decorator.getErrors().add(
					"Please provide user to id");
		}
		if (StringUtil.isEmpty(bean.getUserRating())) {
			decorator.getErrors().add(
					"Please provide user rating");
		}
		if (StringUtil.isEmpty(bean.getRideNo())) {
			decorator.getErrors().add(
					"Please provide Ride No");
		}
		
	}

	public void validPayPalInfo(SafeHerDecorator decorator) {
		PayPalBean paypalBean= (PayPalBean)decorator.getDataBean();
		if (StringUtil.isEmpty(paypalBean.getAppUserId())) {
			decorator.getErrors().add(
					"Please provide App User Id");
		}
		if (StringUtil.isEmpty(paypalBean.getIsActive())) {
			decorator.getErrors().add(
					"Please provide Active Status");
		}
	}

	public List<BankAccountInfoBean> convertEntityToBeanList(
			List<BankAccountInfoEntity> bankAccountInfoList) {
		List<BankAccountInfoBean> bankInfoBaenlist=new ArrayList<BankAccountInfoBean>();
		for (BankAccountInfoEntity bankInfoEntity : bankAccountInfoList) {

			BankAccountInfoBean bean = new BankAccountInfoBean();
			bean.setAccountNo(bankInfoEntity.getAccountNo());
			bean.setBankAccountInfoId(bankInfoEntity.getBankAccountInfoId()
					.toString());
			bean.setAccountTitle(bankInfoEntity.getAccountTitle());
			bean.setBankId(bankInfoEntity.getBank().getBankId().toString());
			// bean.setAppUserId(bankInfoEntity.get)
			bean.setAppUserPaymentInfoId(bankInfoEntity.getAppUserPaymentInfo()
					.getAppUserPaymentInfoId().toString());
			bean.setRoutingNo(bankInfoEntity.getRoutingNo());
			bean.setSwiftCode(bankInfoEntity.getSwiftCode());
			bean.setIbanNo(bankInfoEntity.getIbanNo());
			bean.setIsActive(bankInfoEntity.getIsActive());
			bean.setLocation(bankInfoEntity.getLocation());
			bean.setZipCode(bankInfoEntity.getZipcode());
			bean.setIsDefault(bankInfoEntity.getIsDefault());
			bankInfoBaenlist.add(bean);
			
		}
		return bankInfoBaenlist;
		
	}

	public void validDefaultBankInfo(SafeHerDecorator decorator) {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please Enter App User ID");
		}
		if (StringUtil.isEmpty(bean.getBankAccountInfoId())) {
			decorator.getErrors().add("Please Enter Bank Account ID");
		}
		if (StringUtil.isEmpty(bean.getIsDefault())) {
			decorator.getErrors().add("Please Enter Is Default Or Not");
		}
	}

	public void validateCancelRequest(SafeHerDecorator decorator) {
		RideRequestResponseBean bean = (RideRequestResponseBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}
		if (StringUtil.isEmpty(bean.getPassengerId())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}
		if (StringUtil.isEmpty(bean.getRequestNo())) {
			decorator.getErrors().add("Please provide Appi User Id");
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please provide Driver Check");
		}
		
	}

	public void validateGetVehicleInfo(SafeHerDecorator decorator) {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}

	}

	public List<VehicleInfoBean> convertEntityListBeanList(
			List<AppUserVehicleEntity> vehicleInfoList) {
		List<VehicleInfoBean> vehicleInfoBeanList =new ArrayList<VehicleInfoBean>();
		VehicleInfoBean vehicleInfoBean=null;
		for(AppUserVehicleEntity appVehicle:vehicleInfoList){
			vehicleInfoBean =new VehicleInfoBean();
			vehicleInfoBean.setAppUserVehicleId(appVehicle.getAppUserVehicleId().toString());
			vehicleInfoBean.setVehicleInfoId(appVehicle.getVehicleInfo().getVehicleInfoId().toString());
			vehicleInfoBean.setIsActive(appVehicle.getIsActive());
			vehicleInfoBean.setVehicleMake(appVehicle.getVehicleInfo().getVehicleMake().getName());
			vehicleInfoBean.setVehicleModel(appVehicle.getVehicleInfo().getVehicleModel().getName());
			vehicleInfoBean.setColor(appVehicle.getVehicleInfo().getColor().getName());
			vehicleInfoBean.setPlateNumber(appVehicle.getVehicleInfo().getPlateNumber());
			vehicleInfoBean.setSeatCapacity(appVehicle.getVehicleInfo().getSeatCapacity()+"");
			vehicleInfoBean.setManufacturingYear(appVehicle.getVehicleInfo().getManufacturingYear());
			vehicleInfoBeanList.add(vehicleInfoBean);
		}
		return vehicleInfoBeanList;
	}

	public void validatedefaultVehicle(SafeHerDecorator decorator) {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
		if (StringUtil.isEmpty(bean.getAppUserVehicleId())) {
			decorator.getErrors().add("Please provide App User Vehicle Info Id");
		}
		if (StringUtil.isEmpty(bean.getIsActive())) {
			decorator.getErrors().add("Please provide Is Active");
		}
	}

	public void vaildatePersonalEditByPassenger(SafeHerDecorator decorator) {
		AppUserBean bean = (AppUserBean) decorator.getDataBean();
		
		if (StringUtil.isNotEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
	
	}

	public void convertEntityToRideQuickBean(RideQuickInfoEntity entity, 
			RideQuickInfoBean bean) {
		if(entity != null){
			bean.setCharityId(entity.getCharityId());
			bean.setCharityName(entity.getCharityName());
			bean.setPassengerAppId(entity.getPassengerAppId());
			bean.setRequestNo(entity.getRequestNo());
			bean.setNotificationType(entity.getNotificationType());
			bean.setNotificationMessage(entity.getNotificationMessage());
			bean.setSendingTime(entity.getSendingTime()+"");
		}
	
	}

	public void validateUserSocialSignIn(SafeHerDecorator decorator) {
		
		SignInBean bean = (SignInBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getSocialId())){
			decorator.getErrors().add("Please provide your Social Id");
			return;
		}
		if (StringUtil.isEmpty(bean.getKeyToken())) {
			decorator.getErrors().add("Please provide your Key Token");
			return;
		}
		if (StringUtil.isEmpty(bean.getOsType())) {
			decorator.getErrors().add("Please provide your Os Information");
			return;
		}
		
	}

	public void validateDeleteVehicle(SafeHerDecorator decorator) {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
		if (StringUtil.isEmpty(bean.getAppUserVehicleId())) {
			decorator.getErrors()
					.add("Please provide App User Vehicle Info Id");
		}
		if (StringUtil.isEmpty(bean.getVehicleInfoId())) {
			decorator.getErrors().add("Please provide VehicleInfo Id");
		}
	}

	public void validateDeleteBankInfo(SafeHerDecorator decorator) {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
		if (StringUtil.isEmpty(bean.getAppUserPaymentInfoId())) {
			decorator.getErrors()
					.add("Please provide App User Payment Info Id");
		}
		if (StringUtil.isEmpty(bean.getBankAccountInfoId())) {
			decorator.getErrors().add("Please provide Bank Info Id");
		}
		
	}

	public void validateAppUserId(SafeHerDecorator decorator) {
		ProfileInformationBean bean = (ProfileInformationBean) decorator.getDataBean();
		
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
		
//		if (StringUtil.isEmpty(bean.getIsDriver())) {
//			decorator.getErrors().add("Please provide isDriver 1 or 0");
//		}
	}

	public ProfileInformationBean convertPersonInforToBean(UserLoginEntity entity,SafeHerDecorator decorator){
		ProfileInformationBean bean = (ProfileInformationBean) decorator.getDataBean();
		if (entity.getAppUser() != null) {
			bean.setAppUserId(entity.getAppUser().getAppUserId() + "");
			bean.setIsDriver(entity.getAppUser().getIsDriver());
			if (entity.getAppUser().getPerson() != null) {
				PersonEntity personEntity = entity.getAppUser().getPerson();
				if (StringUtil.isNotEmpty(personEntity.getFirstName())) {
					bean.setFirstName(personEntity.getFirstName());
				}
				if (StringUtil.isNotEmpty(personEntity.getLastName())) {
					bean.setLastName(personEntity.getLastName());
				}
				if (StringUtil.isNotEmpty(personEntity.getSex())) {
					bean.setSex(personEntity.getSex());
				}
			}
			if (entity.getAppUser().getPersonDetail() != null) {
				PersonDetailEntity personDetailEntity = entity.getAppUser()
						.getPersonDetail();
				if (StringUtil.isNotEmpty(personDetailEntity.getPrimaryCell())) {
					bean.setPhoneNumber(personDetailEntity.getPrimaryCell());
				}
				if (StringUtil.isNotEmpty(personDetailEntity.getPrimaryEmail())) {
					bean.setEmail(personDetailEntity.getPrimaryEmail());
				}
				if (StringUtil.isNotEmpty(personDetailEntity.getSecondaryCell())) {
					bean.setAltPhoneNumber(personDetailEntity.getSecondaryCell());
				}
				if (StringUtil.isNotEmpty(personDetailEntity.getSecondaryEmail())) {
					bean.setAltEmail(personDetailEntity.getSecondaryEmail());
				}
			}
			
		}
		
		if (StringUtil.isNotEmpty(entity.getIsSocial())) {
			bean.setIsSocial(entity.getIsSocial());
		}
		if(StringUtil.isNotEmpty(entity.getIsSocial()) && entity.getIsSocial().trim().equals("1") ){
			bean.setSocialId(entity.getSocialId());
		}
		if (StringUtil.isNotEmpty(entity.getIsActive())) {
			bean.setIsActive(entity.getIsActive());
		}
		if (StringUtil.isNotEmpty(entity.getCurrentSessionNo())) {
			bean.setSessionNo(entity.getCurrentSessionNo());
		}
		bean.setFcmToken(entity.getFcmToken());
		bean.setKeyToken(entity.getKeyToken());
		return bean;
	}

	public void validateUserInformation(SafeHerDecorator decorator) {
		ProfileInformationBean bean = (ProfileInformationBean) decorator
				.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
	
	
	}

	public void validateUserDetailInformation(SafeHerDecorator decorator) {
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();

		if (StringUtil.isEmpty(rideCriteria.getIsDriver())) {
			decorator.getErrors().add("Please provide Is Driver Flag");
			return;
		}
		if (StringUtil.isNotEmpty(rideCriteria.getIsDriver())) {
			if (rideCriteria.getIsDriver().equals("1")) {
				if (StringUtil.isEmpty(rideCriteria.getAppUserId())) {
					decorator.getErrors().add("Please provide App User");
					return;
				}
				if (StringUtil.isEmpty(rideCriteria.getRequestNo())) {
					decorator.getErrors().add("Please provide Ride Request no");
					return;
				}
			} else if (rideCriteria.getIsDriver().equals("0")) {
				if (StringUtil.isEmpty(rideCriteria.getAppUserId())) {
					decorator.getErrors().add("Please provide App User");
					return;
				}
			}
		}
		
	}
}