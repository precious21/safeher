package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.maps.model.LatLng;
import org.springframework.data.geo.Point;
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class DistanceAPIBeanV2 implements Serializable {

	private Integer appUserId;
	private Double latOrigins;
	private Double latDestinations;
	private Double lngOrigins;
	private Double lngDestinations;
	private String origins;
	private String destinations;
	private Double totalTimeSeconds = 0d;
	private Double totalDistanceMeters = 0d;
	private Double totalFare = 0d;
	private Long idleTime = 0l;
	private Long idleRestTime = 0l;
	private Double minTime=0d;
	private String driverStatus;
	private String requestNo;
	private String charityId;
	private String charityName;
	
	
	
	////////////////////////////FOr Ride Request Params
	
	private String noPassengers;
	private String noChild;
	private String noSeat;
	private String picUrl;
	private String firstName;
	private Point loc;
	
	
	
	public String getNoPassengers() {
		return noPassengers;
	}
	public void setNoPassengers(String noPassengers) {
		this.noPassengers = noPassengers;
	}
	public String getNoChild() {
		return noChild;
	}
	public void setNoChild(String noChild) {
		this.noChild = noChild;
	}
	public String getNoSeat() {
		return noSeat;
	}
	public void setNoSeat(String noSeat) {
		this.noSeat = noSeat;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Double getMinTime() {
		return minTime;
	}
	public void setMinTime(Double minTime) {
		this.minTime = minTime;
	}
	public String getOrigins() {
		return origins;
	}
	public void setOrigins(String origins) {
		this.origins = origins;
	}
	public String getDestinations() {
		return destinations;
	}
	public void setDestinations(String destinations) {
		this.destinations = destinations;
	}
	public Double getTotalTimeSeconds() {
		return totalTimeSeconds;
	}
	public void setTotalTimeSeconds(Double totalTimeSeconds) {
		this.totalTimeSeconds = totalTimeSeconds;
	}
	public Double getTotalDistanceMeters() {
		return totalDistanceMeters;
	}
	public void setTotalDistanceMeters(Double totalDistanceMeters) {
		this.totalDistanceMeters = totalDistanceMeters;
	}
	public Double getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}
	public Long getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(Long idleTime) {
		this.idleTime = idleTime;
	}
	public Double getLatOrigins() {
		return latOrigins;
	}
	public void setLatOrigins(Double latOrigins) {
		this.latOrigins = latOrigins;
	}
	public Double getLatDestinations() {
		return latDestinations;
	}
	public void setLatDestinations(Double latDestinations) {
		this.latDestinations = latDestinations;
	}
	public Double getLngOrigins() {
		return lngOrigins;
	}
	public void setLngOrigins(Double lngOrigins) {
		this.lngOrigins = lngOrigins;
	}
	public Double getLngDestinations() {
		return lngDestinations;
	}
	public void setLngDestinations(Double lngDestinations) {
		this.lngDestinations = lngDestinations;
	}
	public Integer getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getDriverStatus() {
		return driverStatus;
	}
	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}
	public Long getIdleRestTime() {
		return idleRestTime;
	}
	public void setIdleRestTime(Long idleRestTime) {
		this.idleRestTime = idleRestTime;
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
	public String getCharityName() {
		return charityName;
	}
	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}
	@Override
	public String toString() {
		return "DistanceAPIBeanV2 [appUserId=" + appUserId + ", latOrigins="
				+ latOrigins + ", latDestinations=" + latDestinations
				+ ", lngOrigins=" + lngOrigins + ", lngDestinations="
				+ lngDestinations + ", origins=" + origins + ", destinations="
				+ destinations + ", totalTimeSeconds=" + totalTimeSeconds
				+ ", totalDistanceMeters=" + totalDistanceMeters
				+ ", totalFare=" + totalFare + ", idleTime=" + idleTime
				+ ", idleRestTime=" + idleRestTime + ", minTime=" + minTime
				+ ", driverStatus=" + driverStatus + ", requestNo=" + requestNo
				+ ", charityId=" + charityId + ", charityName=" + charityName
				+ ", noPassengers=" + noPassengers + ", noChild=" + noChild
				+ ", noSeat=" + noSeat + ", picUrl=" + picUrl + ", firstName="
				+ firstName + "]";
	}
	public Point getLoc() {
		return loc;
	}
	public void setLoc(Point loc) {
		this.loc = loc;
	}
	
	
	
	
}
