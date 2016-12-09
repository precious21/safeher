package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.geo.Point;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideSearchResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8674533452083406259L;
	
	private String rideSearchResultDetailId;
	private String appUserId;
	private String rideSearchResultEntityId;
	private String driverLong;
	private String driverLat;
	private String isPreSelect;
	private String rideSearchEntityId;
	private String rideCriteriaEntityId;
	private String osType;
	private String requestNo;
	private List<RideSearchResultBean> driverPushNotification;
	private Point loc;
	
	public String getRideCriteriaEntityId() {
		return rideCriteriaEntityId;
	}
	public void setRideCriteriaEntityId(String rideCriteriaEntityId) {
		this.rideCriteriaEntityId = rideCriteriaEntityId;
	}
	public String getRideSearchResultDetailId() {
		return rideSearchResultDetailId;
	}
	public void setRideSearchResultDetailId(String rideSearchResultDetailId) {
		this.rideSearchResultDetailId = rideSearchResultDetailId;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getRideSearchResultEntityId() {
		return rideSearchResultEntityId;
	}
	public void setRideSearchResultEntityId(String rideSearchResultEntityId) {
		this.rideSearchResultEntityId = rideSearchResultEntityId;
	}
	public String getDriverLong() {
		return driverLong;
	}
	public void setDriverLong(String driverLong) {
		this.driverLong = driverLong;
	}
	public String getDriverLat() {
		return driverLat;
	}
	public void setDriverLat(String driverLat) {
		this.driverLat = driverLat;
	}
	public String getIsPreSelect() {
		return isPreSelect;
	}
	public void setIsPreSelect(String isPreSelect) {
		this.isPreSelect = isPreSelect;
	}
	public String getRideSearchEntityId() {
		return rideSearchEntityId;
	}
	public void setRideSearchEntityId(String rideSearchEntityId) {
		this.rideSearchEntityId = rideSearchEntityId;
	}
	public List<RideSearchResultBean> getDriverPushNotification() {
		return driverPushNotification;
	}
	public void setDriverPushNotification(List<RideSearchResultBean> driverPushNotification) {
		this.driverPushNotification = driverPushNotification;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
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
	@Override
	public String toString() {
		return "RideSearchResultBean [rideSearchResultDetailId="
				+ rideSearchResultDetailId + ", appUserId=" + appUserId
				+ ", rideSearchResultEntityId=" + rideSearchResultEntityId
				+ ", driverLong=" + driverLong + ", driverLat=" + driverLat
				+ ", isPreSelect=" + isPreSelect + ", rideSearchEntityId="
				+ rideSearchEntityId + ", rideCriteriaEntityId="
				+ rideCriteriaEntityId + ", osType=" + osType + ", requestNo="
				+ requestNo + ", driverPushNotification="
				+ driverPushNotification + "]";
	}
	public Point getLoc() {
		return loc;
	}
	public void setLoc(Point loc) {
		this.loc = loc;
	}
	
	
	
}
