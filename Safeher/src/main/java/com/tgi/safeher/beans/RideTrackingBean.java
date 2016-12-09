package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideTrackingBean implements Serializable {
	
	private static final long serialVersionUID = 7265965227881182391L;
	
	private String requestNo;//mandatory
	private String status;//mandatory
	private String state;//mandatory
	private String action;//mandatory
	private String isRecontinued;
	private String continuedStatus;
	private String invoiceNo;//mandatory when available 
	private String rideNo;//mandatory when available
	private String isComplete;//mandatory
	private String passengerId;
	private String driverId;
	private String isDriver;
	
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIsRecontinued() {
		return isRecontinued;
	}
	public void setIsRecontinued(String isRecontinued) {
		this.isRecontinued = isRecontinued;
	}
	public String getContinuedStatus() {
		return continuedStatus;
	}
	public void setContinuedStatus(String continuedStatus) {
		this.continuedStatus = continuedStatus;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getRideNo() {
		return rideNo;
	}
	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	
}
