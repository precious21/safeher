package com.tgi.safeher.beans;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.StateProvinceEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class DriverInfoBean  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6310323497634814283L;
	
	private String driverInfoId;
	private String licenceNo;
	private String expiryDate;
	private String stateId;
	private String driverNo;
	private String appUserId;
//	private StateProvinceEntity stateObj;
	
	
	public String getDriverInfoId() {
		return driverInfoId;
	}
	public void setDriverInfoId(String driverInfoId) {
		this.driverInfoId = driverInfoId;
	}
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
//	public StateProvinceEntity getStateObj() {
//		return stateObj;
//	}
//	public void setStateObj(StateProvinceEntity stateObj) {
//		this.stateObj = stateObj;
//	}
	@Override
	public String toString() {
		return "DriverInfoBean [driverInfoId=" + driverInfoId + ", licenceNo="
				+ licenceNo + ", expiryDate=" + expiryDate + ", stateId="
				+ stateId + ", driverNo=" + driverNo + ", appUserId="
				+ appUserId + "]";
	}
	
	

}
