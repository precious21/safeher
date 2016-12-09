package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class AppUserBean implements Serializable {
	
	//firstName, lastName, email, password, phoneNumber, altPhoneNumber, paymentOption, creditCardInfo, payPallInfo, picture, gender
	
	//firstName, lastName, email, altEmail, password, phoneNumber, altPhoneNumber, profileImage, gender
	
	private String appUserId;
	private String firstName;
	private String lastName;
	private String email;
	private String altEmail;
	private String password;
	private String phoneNumber;
	private String altPhoneNumber;
	private String resPhone;
	private String profileImage;
	private String gender;
	private String location;
	private String longitude;
	private String latitude;
	private String isDriver;
	private String userImageUrl;
	private String driverInfoId;
	private String isSocial;
	private String socialId;
	private String keyToken;
	private String fcmToken;
	private String isActive;
	private String isActivated;
	private String plateNo;
	private String vehicalModal;
	private String vehicalMake;
	private String distance;
	private String Arrivaltime;
	private String osType;
	private String isDev = "1";
	private String isFromWindow = "";
	private String driverRating;
	private String sessionNo;
	private String userRating;
	private String countryName;
	private String stateName;
	private String cityName;
	private String stepCode;
	private String zipCode;
	private String userInformationValue;
	private String stateId;
	private String countryId;
	private String mailingAddress;    
	private String mailingStateId;    
	private String mailingCityName;
	private String mailingZipCode;
	private String isQualifiedVehical;
	private String disclosureOrVehicle;
	private String isUpdate;
	private List<CharitiesBean> charitiesList = new ArrayList<CharitiesBean>();
	private List<VehicleInfoBean> vehicaleList = new ArrayList<VehicleInfoBean>();
	private CreditCardInfoBean creditCardInfoBean = new CreditCardInfoBean();
	
	
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getArrivaltime() {
		return Arrivaltime;
	}
	public void setArrivaltime(String arrivaltime) {
		Arrivaltime = arrivaltime;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getVehicalModal() {
		return vehicalModal;
	}
	public void setVehicalModal(String vehicalModal) {
		this.vehicalModal = vehicalModal;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsSocial() {
		return isSocial;
	}
	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}
	public String getUserImageUrl() {
		return userImageUrl;
	}
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAltPhoneNumber() {
		return altPhoneNumber;
	}
	public void setAltPhoneNumber(String altPhoneNumber) {
		this.altPhoneNumber = altPhoneNumber;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getResPhone() {
		return resPhone;
	}
	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getDriverInfoId() {
		return driverInfoId;
	}
	public void setDriverInfoId(String driverInfoId) {
		this.driverInfoId = driverInfoId;
	}
	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	public String getKeyToken() {
		return keyToken;
	}
	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getIsDev() {
		return isDev;
	}
	public void setIsDev(String isDev) {
		this.isDev = isDev;
	}
	public String getSessionNo() {
		return sessionNo;
	}
	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	}
	public List<CharitiesBean> getCharitiesList() {
		return charitiesList;
	}
	public void setCharitiesList(List<CharitiesBean> charitiesList) {
		this.charitiesList = charitiesList;
	}
	public CreditCardInfoBean getCreditCardInfoBean() {
		return creditCardInfoBean;
	}
	public void setCreditCardInfoBean(CreditCardInfoBean creditCardInfoBean) {
		this.creditCardInfoBean = creditCardInfoBean;
	}
	public String getDriverRating() {
		return driverRating;
	}
	public void setDriverRating(String driverRating) {
		this.driverRating = driverRating;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	@Override
	public String toString() {
		return "AppUserBean [appUserId=" + appUserId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", altEmail=" + altEmail + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", altPhoneNumber="
				+ altPhoneNumber + ", resPhone=" + resPhone + ", profileImage="
				+ profileImage + ", gender=" + gender + ", location="
				+ location + ", longitude=" + longitude + ", latitude="
				+ latitude + ", isDriver=" + isDriver + ", userImageUrl="
				+ userImageUrl + ", driverInfoId=" + driverInfoId
				+ ", isSocial=" + isSocial + ", socialId=" + socialId
				+ ", keyToken=" + keyToken + ", fcmToken=" + fcmToken
				+ ", isActive=" + isActive + ", plateNo=" + plateNo
				+ ", vehicalModal=" + vehicalModal + ", distance=" + distance
				+ ", Arrivaltime=" + Arrivaltime + ", osType=" + osType
				+ ", isDev=" + isDev + ", driverRating=" + driverRating
				+ ", sessionNo=" + sessionNo + ", charitiesList="
				+ charitiesList + ", creditCardInfoBean=" + creditCardInfoBean
				+ "]";
	}
	public String getUserRating() {
		return userRating;
	}
	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}
	public List<VehicleInfoBean> getVehicaleList() {
		return vehicaleList;
	}
	public void setVehicaleList(List<VehicleInfoBean> vehicaleList) {
		this.vehicaleList = vehicaleList;
	}
	public String getIsActivated() {
		return isActivated;
	}
	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}
	public String getIsFromWindow() {
		return isFromWindow;
	}
	public void setIsFromWindow(String isFromWindow) {
		this.isFromWindow = isFromWindow;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getIsQualifiedVehical() {
		return isQualifiedVehical;
	}
	public void setIsQualifiedVehical(String isQualifiedVehical) {
		this.isQualifiedVehical = isQualifiedVehical;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getMailingStateId() {
		return mailingStateId;
	}
	public void setMailingStateId(String mailingStateId) {
		this.mailingStateId = mailingStateId;
	}
	public String getMailingCityName() {
		return mailingCityName;
	}
	public void setMailingCityName(String mailingCityName) {
		this.mailingCityName = mailingCityName;
	}
	public String getMailingZipCode() {
		return mailingZipCode;
	}
	public void setMailingZipCode(String mailingZipCode) {
		this.mailingZipCode = mailingZipCode;
	}
	public String getDisclosureOrVehicle() {
		return disclosureOrVehicle;
	}
	public void setDisclosureOrVehicle(String disclosureOrVehicle) {
		this.disclosureOrVehicle = disclosureOrVehicle;
	}
	public String getUserInformationValue() {
		return userInformationValue;
	}
	public void setUserInformationValue(String userInformationValue) {
		this.userInformationValue = userInformationValue;
	}
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	public String getVehicalMake() {
		return vehicalMake;
	}
	public void setVehicalMake(String vehicalMake) {
		this.vehicalMake = vehicalMake;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	
	

}
