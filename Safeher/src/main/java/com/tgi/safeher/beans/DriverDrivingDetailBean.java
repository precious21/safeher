package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class DriverDrivingDetailBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String driverDrivingDetailId;
	private String appUserId;
	private Double totalOnlineTime;
	private Double totalRideTime;
	private Double totalPreRideTime;
	private Double totalOnlineDistance;
	private Double totalRideDistance;
	private Double totalPreRideDistance;
	private Double totalEarning;
	private Double totalRides;
	private Double driverEarning;
	private Double disputeAmount;
	private Double totalAcceptedRequest;
	private Double totalCancelPreRides;
	private Double totalRequests;
	private int insertionType;
	private Date date;
	
	public String getDriverDrivingDetailId() {
		return driverDrivingDetailId;
	}
	public void setDriverDrivingDetailId(String driverDrivingDetailId) {
		this.driverDrivingDetailId = driverDrivingDetailId;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public Double getTotalOnlineTime() {
		return totalOnlineTime;
	}
	public void setTotalOnlineTime(Double totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}
	public Double getTotalRideTime() {
		return totalRideTime;
	}
	public void setTotalRideTime(Double totalRideTime) {
		this.totalRideTime = totalRideTime;
	}
	public Double getTotalPreRideTime() {
		return totalPreRideTime;
	}
	public void setTotalPreRideTime(Double totalPreRideTime) {
		this.totalPreRideTime = totalPreRideTime;
	}
	public Double getTotalOnlineDistance() {
		return totalOnlineDistance;
	}
	public void setTotalOnlineDistance(Double totalOnlineDistance) {
		this.totalOnlineDistance = totalOnlineDistance;
	}
	public Double getTotalRideDistance() {
		return totalRideDistance;
	}
	public void setTotalRideDistance(Double totalRideDistance) {
		this.totalRideDistance = totalRideDistance;
	}
	public Double getTotalPreRideDistance() {
		return totalPreRideDistance;
	}
	public void setTotalPreRideDistance(Double totalPreRideDistance) {
		this.totalPreRideDistance = totalPreRideDistance;
	}
	public Double getTotalEarning() {
		return totalEarning;
	}
	public void setTotalEarning(Double totalEarning) {
		this.totalEarning = totalEarning;
	}
	public Double getTotalRides() {
		return totalRides;
	}
	public void setTotalRides(Double totalRides) {
		this.totalRides = totalRides;
	}
	public Double getDriverEarning() {
		return driverEarning;
	}
	public void setDriverEarning(Double driverEarning) {
		this.driverEarning = driverEarning;
	}
	public Double getDisputeAmount() {
		return disputeAmount;
	}
	public void setDisputeAmount(Double disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	public Double getTotalAcceptedRequest() {
		return totalAcceptedRequest;
	}
	public void setTotalAcceptedRequest(Double totalAcceptedRequest) {
		this.totalAcceptedRequest = totalAcceptedRequest;
	}
	public Double getTotalCancelPreRides() {
		return totalCancelPreRides;
	}
	public void setTotalCancelPreRides(Double totalCancelPreRides) {
		this.totalCancelPreRides = totalCancelPreRides;
	}
	public Double getTotalRequests() {
		return totalRequests;
	}
	public void setTotalRequests(Double totalRequests) {
		this.totalRequests = totalRequests;
	}
	public int getInsertionType() {
		return insertionType;
	}
	public void setInsertionType(int insertionType) {
		this.insertionType = insertionType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
