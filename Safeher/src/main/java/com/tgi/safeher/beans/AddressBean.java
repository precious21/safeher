package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class AddressBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5310709600522578362L;
	private String zipCode;
	private String stateProvince;
	private String cityId;
	private String stateProvinceId;
	private String city;
	private String country;
	private String fromDate;
	private String toDate;
	private String addressLineOne;
	private String addressLineTwo;
	private String appUser;
	private String addressType;
	private String addressId;
	
	
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String aaddressType) {
		addressType = aaddressType;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAddressLineOne() {
		return addressLineOne;
	}
	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}
	public String getAddressLineTwo() {
		return addressLineTwo;
	}
	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}
	@Override
	public String toString() {
		return "AddressBean [zipCode=" + zipCode + ", stateProvince="
				+ stateProvince + ", city=" + city + ", country=" + country
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", addressLineOne=" + addressLineOne + ", addressLineTwo="
				+ addressLineTwo + ", appUser=" + appUser + ", addressType="
				+ addressType + "]";
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getStateProvinceId() {
		return stateProvinceId;
	}
	public void setStateProvinceId(String stateProvinceId) {
		this.stateProvinceId = stateProvinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	

}
