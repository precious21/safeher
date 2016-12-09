package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SignInBean implements Serializable{
	
	private String email;
	private String password;
	private String appUserId;
	private String isDriver;
	private String lng;
	private String lat;
	private String isPhysical;
	private String keyToken;
	private String fcmToken;
	private String isAvalible;
	private String osType;
	private String isDev;
	private String socialId;
	private String isSocial;
	
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getIsPhysical() {
		return isPhysical;
	}
	public void setIsPhysical(String isPhysical) {
		this.isPhysical = isPhysical;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getKeyToken() {
		return keyToken;
	}
	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}
	public String getIsAvalible() {
		return isAvalible;
	}
	public void setIsAvalible(String isAvalible) {
		this.isAvalible = isAvalible;
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
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	public String getIsSocial() {
		return isSocial;
	}
	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}
	@Override
	public String toString() {
		return "SignInBean [email=" + email + ", password=" + password
				+ ", appUserId=" + appUserId + ", isDriver=" + isDriver
				+ ", lng=" + lng + ", lat=" + lat + ", isPhysical="
				+ isPhysical + ", keyToken=" + keyToken + ", fcmToken="
				+ fcmToken + ", isAvalible=" + isAvalible + ", osType="
				+ osType + ", isDev=" + isDev + "]";
	}
	

}

