package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideRequestResponseBean implements Serializable {
	
	private String rideReqResId;
	private String passengerReqId;
	private String rideCriteriaId;
	private String appUserId;
	private String receiverId;
	private String requestTime;
	private String responsTime;
	private String statusRespons;
	private String rideSearchResultDetailId;
	private String statusFinal;
	private String isCall;
	private String isText;
	private String osType;
	private String requestSent = "";
	private List<RideRequestResponseBean> list = new ArrayList<RideRequestResponseBean>();
	private String lng;
	private String lat;
	private String preRideId;	
	private String isDriver;
	private String reasonid;
	private String reason;
	private String commentReason;
	private String isCancel;
	private String rideAction;
	private String driverId;
	private String passengerId;
	private String requestNo;
	private String notificationType;
	private String notificationMessage;
	private String driverappUserId;
	private String passengerappUserId;
	private String estimatedTime;
	
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getRideCriteriaId() {
		return rideCriteriaId;
	}
	public void setRideCriteriaId(String rideCriteriaId) {
		this.rideCriteriaId = rideCriteriaId;
	}
	public String getRequestSent() {
		return requestSent;
	}
	public void setRequestSent(String requestSent) {
		this.requestSent = requestSent;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponsTime() {
		return responsTime;
	}
	public void setResponsTime(String responsTime) {
		this.responsTime = responsTime;
	}
	public String getStatusRespons() {
		return statusRespons;
	}
	public void setStatusRespons(String statusRespons) {
		this.statusRespons = statusRespons;
	}
	public String getRideSearchResultDetailId() {
		return rideSearchResultDetailId;
	}
	public void setRideSearchResultDetailId(String rideSearchResultDetailId) {
		this.rideSearchResultDetailId = rideSearchResultDetailId;
	}
	public String getStatusFinal() {
		return statusFinal;
	}
	public void setStatusFinal(String statusFinal) {
		this.statusFinal = statusFinal;
	}
	public String getIsCall() {
		return isCall;
	}
	public void setIsCall(String isCall) {
		this.isCall = isCall;
	}
	public String getIsText() {
		return isText;
	}
	public void setIsText(String isText) {
		this.isText = isText;
	}
	public String getRideReqResId() {
		return rideReqResId;
	}
	public void setRideReqResId(String rideReqResId) {
		this.rideReqResId = rideReqResId;
	}
	public String getPassengerReqId() {
		return passengerReqId;
	}
	public void setPassengerReqId(String passengerReqId) {
		this.passengerReqId = passengerReqId;
	}
	public List<RideRequestResponseBean> getList() {
		return list;
	}
	public void setList(List<RideRequestResponseBean> list) {
		this.list = list;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
/**
	 * @return the preRideId
	 */
	public String getPreRideId() {
		return preRideId;
	}
	/**
	 * @param preRideId the preRideId to set
	 */
	public void setPreRideId(String preRideId) {
		this.preRideId = preRideId;
	}public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getReasonid() {
		return reasonid;
	}
	public void setReasonid(String reasonid) {
		this.reasonid = reasonid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCommentReason() {
		return commentReason;
	}
	public void setCommentReason(String commentReason) {
		this.commentReason = commentReason;
	}
	public String getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}
	public String getRideAction() {
		return rideAction;
	}
	public void setRideAction(String rideAction) {
		this.rideAction = rideAction;
	}
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
	public String getDriverappUserId() {
		return driverappUserId;
	}
	public void setDriverappUserId(String driverappUserId) {
		this.driverappUserId = driverappUserId;
	}
	public String getPassengerappUserId() {
		return passengerappUserId;
	}
	public void setPassengerappUserId(String passengerappUserId) {
		this.passengerappUserId = passengerappUserId;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
}
