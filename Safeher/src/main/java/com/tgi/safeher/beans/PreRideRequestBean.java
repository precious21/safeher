package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class PreRideRequestBean implements Serializable {
	private String preRideId;
	private String statusId;
	private String rideColorByDriver;
	private String rideColorByPassenger;
	private String rideColorByDriverCode;
	private String rideColorByPassengerCode;
	private String rideFinalizeId;
	private String isDriverColorVerified;
	private String isPassengerColorVerified;
	private String driverVerificationAttemps;
	private String passengerVerificationAttemps;
	private String driverappUserId;
	private String passengerappUserId;
	private String isDriver;
	private String estimatedTime;
	private String requestNo;
	private String receiverId;
	private String notificationType;
	private String notificationMessage;
	
	
	public String getRideColorByDriver() {
		return rideColorByDriver;
	}
	public void setRideColorByDriver(String rideColorByDriver) {
		this.rideColorByDriver = rideColorByDriver;
	}
	public String getRideColorByPassenger() {
		return rideColorByPassenger;
	}
	public void setRideColorByPassenger(String rideColorByPassenger) {
		this.rideColorByPassenger = rideColorByPassenger;
	}
	public String getRideColorByDriverCode() {
		return rideColorByDriverCode;
	}
	public void setRideColorByDriverCode(String rideColorByDriverCode) {
		this.rideColorByDriverCode = rideColorByDriverCode;
	}
	public String getRideColorByPassengerCode() {
		return rideColorByPassengerCode;
	}
	public void setRideColorByPassengerCode(String rideColorByPassengerCode) {
		this.rideColorByPassengerCode = rideColorByPassengerCode;
	}
	public String getPreRideId() {
		return preRideId;
	}
	public void setPreRideId(String preRideId) {
		this.preRideId = preRideId;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getRideFinalizeId() {
		return rideFinalizeId;
	}
	public void setRideFinalizeId(String rideFinalizeId) {
		this.rideFinalizeId = rideFinalizeId;
	}
	public String getIsDriverColorVerified() {
		return isDriverColorVerified;
	}
	public void setIsDriverColorVerified(String isDriverColorVerified) {
		this.isDriverColorVerified = isDriverColorVerified;
	}
	public String getIsPassengerColorVerified() {
		return isPassengerColorVerified;
	}
	public void setIsPassengerColorVerified(String isPassengerColorVerified) {
		this.isPassengerColorVerified = isPassengerColorVerified;
	}
	public String getDriverVerificationAttemps() {
		return driverVerificationAttemps;
	}
	public void setDriverVerificationAttemps(String driverVerificationAttemps) {
		this.driverVerificationAttemps = driverVerificationAttemps;
	}
	public String getPassengerVerificationAttemps() {
		return passengerVerificationAttemps;
	}
	public void setPassengerVerificationAttemps(String passengerVerificationAttemps) {
		this.passengerVerificationAttemps = passengerVerificationAttemps;
	}
	/**
	 * @return the passengerappUserId
	 */
	public String getPassengerappUserId() {
		return passengerappUserId;
	}
	/**
	 * @param passengerappUserId the passengerappUserId to set
	 */
	public void setPassengerappUserId(String passengerappUserId) {
		this.passengerappUserId = passengerappUserId;
	}
	/**
	 * @return the driverappUserId
	 */
	public String getDriverappUserId() {
		return driverappUserId;
	}
	/**
	 * @param driverappUserId the driverappUserId to set
	 */
	public void setDriverappUserId(String driverappUserId) {
		this.driverappUserId = driverappUserId;
	}
	/**
	 * @return the isDriver
	 */
	public String getIsDriver() {
		return isDriver;
	}
	/**
	 * @param isDriver the isDriver to set
	 */
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	/**
	 * @return the estimatedTime
	 */
	public String getEstimatedTime() {
		return estimatedTime;
	}
	/**
	 * @param estimatedTime the estimatedTime to set
	 */
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	/**
	 * @return the requestNo
	 */
	public String getRequestNo() {
		return requestNo;
	}
	/**
	 * @param requestNo the requestNo to set
	 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
}
