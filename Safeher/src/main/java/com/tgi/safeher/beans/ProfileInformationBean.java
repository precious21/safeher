package com.tgi.safeher.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonEntity;




@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class ProfileInformationBean implements Serializable {

	private String appUserId;
	private String isDriver;
	
	//Person
	private String personId;
	private String firstName;
	private String lastName;
	private String dob;
	private String sex;
	private String isBlocked;
	//personDetail
	private String personDetailId;
	
	
	private String altPhoneNumber;
	private String altEmail;
	private String email;
	private String phoneNumber;
	private String isActive;
	private String resPhone;
	private String userImageUrl;
	//Vehicle Information
	private VehicleInfoBean vehicleInfo;
	
	//Rating
	private UserRatingBean userRatingBean =new UserRatingBean();
	
	//Payment Information
	private  CreditCardInfoBean creditCardBean = new CreditCardInfoBean();

	// Address Information
	private List<AddressBean> addressLst = new ArrayList<AddressBean>();
	
	// USerLogin
	private String isSocial;
	private String keyToken;
	private String fcmToken;
	private String socialId;
	private String location;
	private String sessionNo;
	private String stepCode;
	
	public String getIsSocial() {
		return isSocial;
	}

	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}

	public String getKeyToken() {
		return keyToken;
	}

	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getPersonDetailId() {
		return personDetailId;
	}

	public void setPersonDetailId(String personDetailId) {
		this.personDetailId = personDetailId;
	}

	public String getResPhone() {
		return resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}

	public UserRatingBean getUserRatingBean() {
		return userRatingBean;
	}

	public void setUserRatingBean(UserRatingBean userRatingBean) {
		this.userRatingBean = userRatingBean;
	}

	public CreditCardInfoBean getCreditCardBean() {
		return creditCardBean;
	}

	public void setCreditCardBean(CreditCardInfoBean creaditCardBean) {
		this.creditCardBean = creaditCardBean;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public VehicleInfoBean getVehicleInfo() {
		return vehicleInfo;
	}

	public void setVehicleInfo(VehicleInfoBean vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getAltPhoneNumber() {
		return altPhoneNumber;
	}

	public void setAltPhoneNumber(String altPhoneNumber) {
		this.altPhoneNumber = altPhoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public String getSessionNo() {
		return sessionNo;
	}

	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	public List<AddressBean> getAddressLst() {
		return addressLst;
	}

	public void setAddressLst(List<AddressBean> addressLst) {
		this.addressLst = addressLst;
	}
	
	
	
	
}
