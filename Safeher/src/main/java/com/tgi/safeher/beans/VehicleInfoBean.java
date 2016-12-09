package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class VehicleInfoBean  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5235931661544373250L;

	private String vehicleInfoId;
	private String vehicleMake;
	private String vehicleModel;
	private String color;
	private String status;
	private String plateNumber;
	private String title;
	private String manufacturingYear;
	private String isActive;
	private String appUserId;
	private String seatCapacity;
	private String roleAppUser;
	private String appUserVehicleId;
	private String flag;
	private List<String[]> list = new ArrayList<String[]>();
	private String removeItem;
	private String saveItem;
	private List<VehicleInfoBean> vehicleInfoList; 
	
	
	public String getRemoveItem() {
		return removeItem;
	}
	public void setRemoveItem(String removeItem) {
		this.removeItem = removeItem;
	}
	public String getSaveItem() {
		return saveItem;
	}
	public void setSaveItem(String saveItem) {
		this.saveItem = saveItem;
	}
	public List<String[]> getList() {
		return list;
	}
	public void setList(List<String[]> list) {
		this.list = list;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getRoleAppUser() {
		return roleAppUser;
	}
	public void setRoleAppUser(String roleAppUser) {
		this.roleAppUser = roleAppUser;
	}
	public String getVehicleInfoId() {
		return vehicleInfoId;
	}
	public void setVehicleInfoId(String vehicleInfoId) {
		this.vehicleInfoId = vehicleInfoId;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getManufacturingYear() {
		return manufacturingYear;
	}
	public void setManufacturingYear(String manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getSeatCapacity() {
		return seatCapacity;
	}
	public void setSeatCapacity(String seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	public String getAppUserVehicleId() {
		return appUserVehicleId;
	}
	public void setAppUserVehicleId(String appUserVehicleId) {
		this.appUserVehicleId = appUserVehicleId;
	}
	public List<VehicleInfoBean> getVehicleInfoList() {
		return vehicleInfoList;
	}
	public void setVehicleInfoList(List<VehicleInfoBean> vehicleInfoList) {
		this.vehicleInfoList = vehicleInfoList;
	}
	
}

