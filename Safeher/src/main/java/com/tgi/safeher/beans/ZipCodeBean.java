package com.tgi.safeher.beans;

public class ZipCodeBean {
	String zipCodeId;
	String zipCode;
	String cityId;
	public String getZipCodeId() {
		return zipCodeId;
	}
	public void setZipCodeId(String zipCodeId) {
		this.zipCodeId = zipCodeId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "ZipCodeBean [zipCodeId=" + zipCodeId + ", zipCode=" + zipCode
				+ ", cityId=" + cityId + "]";
	}
	
	
	

}
