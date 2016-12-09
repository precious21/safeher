package com.tgi.safeher.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class VehicleMakeInfoBean  implements java.io.Serializable{

	private String vehicleMakeId;
	private String name;
	private List<VehicleMakeInfoBean> lstVehicle;
	private String vehicleModelName;
	private String vehicleModelId;
	
	
	
	public String getVehicleModelName() {
		return vehicleModelName;
	}
	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}
	public String getVehicleModelId() {
		return vehicleModelId;
	}
	public void setVehicleModelId(String vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}
	public String getVehicleMakeId() {
		return vehicleMakeId;
	}
	public void setVehicleMakeId(String vehicleMakeId) {
		this.vehicleMakeId = vehicleMakeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<VehicleMakeInfoBean> getLstVehicle() {
		return lstVehicle;
	}
	public void setLstVehicle(List<VehicleMakeInfoBean> lstVehicle) {
		this.lstVehicle = lstVehicle;
	}
	
}
