package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class DriverConfigBean implements Serializable {

	private List<VehicleMakeInfoBean> vehicleMakeList;
	private List<VehicleMakeInfoBean> vehicleModelList;
	private List<ColorBean> colorList;
	private List<StateProvinceBean> stateList;
	private List<BankBean> bankList;
	private String countryId;
	private String appUserId;
	public List<VehicleMakeInfoBean> getVehicleMakeList() {
		return vehicleMakeList;
	}
	public void setVehicleMakeList(List<VehicleMakeInfoBean> vehicleMakeList) {
		this.vehicleMakeList = vehicleMakeList;
	}
	public List<VehicleMakeInfoBean> getVehicleModelList() {
		return vehicleModelList;
	}
	public void setVehicleModelList(List<VehicleMakeInfoBean> vehicleModelList) {
		this.vehicleModelList = vehicleModelList;
	}
	public List<ColorBean> getColorList() {
		return colorList;
	}
	public void setColorList(List<ColorBean> colorList) {
		this.colorList = colorList;
	}
	public List<StateProvinceBean> getStateList() {
		return stateList;
	}
	public void setStateList(List<StateProvinceBean> stateList) {
		this.stateList = stateList;
	}
	public List<BankBean> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankBean> bankList) {
		this.bankList = bankList;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	
}
