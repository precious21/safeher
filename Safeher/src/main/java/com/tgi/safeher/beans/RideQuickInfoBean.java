package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideQuickInfoBean implements Serializable {
	
	private static final long serialVersionUID = 7265965227881182391L;
	
	private String driverId;
	private String passengerId;
	private String isDriver;
	private String currentTime;
	private String PassengerAppId;
	private String DriverAppId;
	private String charityName;
	private String requestNo;
	private String charityId;
	private String sendingTime;
	private String notificationType;
	private String notificationMessage;
	private String notificationExist;
	private String PreRideId;
	private String isCancel;
	private String VerificationFlag;
	private String rideReqResId;
	private String EstimatedTime;
	private String discount;
	private String paymentDone;
	private String InvoiceNo;
	private String RideNo;
	
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getPassengerAppId() {
		return PassengerAppId;
	}
	public void setPassengerAppId(String passengerAppId) {
		PassengerAppId = passengerAppId;
	}
	public String getDriverAppId() {
		return DriverAppId;
	}
	public void setDriverAppId(String driverAppId) {
		DriverAppId = driverAppId;
	}
	public String getCharityName() {
		return charityName;
	}
	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getCharityId() {
		return charityId;
	}
	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}
	public String getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(String sendingTime) {
		this.sendingTime = sendingTime;
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
	public String getNotificationExist() {
		return notificationExist;
	}
	public void setNotificationExist(String notificationExist) {
		this.notificationExist = notificationExist;
	}
	public String getPreRideId() {
		return PreRideId;
	}
	public void setPreRideId(String preRideId) {
		PreRideId = preRideId;
	}
	public String getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}
	public String getVerificationFlag() {
		return VerificationFlag;
	}
	public void setVerificationFlag(String verificationFlag) {
		VerificationFlag = verificationFlag;
	}
	public String getRideReqResId() {
		return rideReqResId;
	}
	public void setRideReqResId(String rideReqResId) {
		this.rideReqResId = rideReqResId;
	}
	public String getEstimatedTime() {
		return EstimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		EstimatedTime = estimatedTime;
	}
	public String getPaymentDone() {
		return paymentDone;
	}
	public void setPaymentDone(String paymentDone) {
		this.paymentDone = paymentDone;
	}
	public String getRideNo() {
		return RideNo;
	}
	public void setRideNo(String rideNo) {
		RideNo = rideNo;
	}
	public String getInvoiceNo() {
		return InvoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

}
