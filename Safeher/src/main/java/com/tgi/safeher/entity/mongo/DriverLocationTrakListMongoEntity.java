package com.tgi.safeher.entity.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="DriverLocationTrakListMongoEntity")
public class DriverLocationTrakListMongoEntity {
	
	private String driverLocationTrackId;
	private String longVal;
	private String latVal;
	private String placeId;
	private String rideNo;
	private Date trackTime;
	private String isSavedIntoSql;
	private String activeDriverStatus;
	private Integer idNo;
	
	public String getDriverLocationTrackId() {
		return driverLocationTrackId;
	}
	public void setDriverLocationTrackId(String driverLocationTrackId) {
		this.driverLocationTrackId = driverLocationTrackId;
	}
	public String getLongVal() {
		return longVal;
	}
	public void setLongVal(String longVal) {
		this.longVal = longVal;
	}
	public String getLatVal() {
		return latVal;
	}
	public void setLatVal(String latVal) {
		this.latVal = latVal;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getRideNo() {
		return rideNo;
	}
	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
	public Date getTrackTime() {
		return trackTime;
	}
	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}
	public String getActiveDriverStatus() {
		return activeDriverStatus;
	}
	public void setActiveDriverStatus(String activeDriverStatus) {
		this.activeDriverStatus = activeDriverStatus;
	}
	public String getIsSavedIntoSql() {
		return isSavedIntoSql;
	}
	public void setIsSavedIntoSql(String isSavedIntoSql) {
		this.isSavedIntoSql = isSavedIntoSql;
	}
	public Integer getIdNo() {
		return idNo;
	}
	public void setIdNo(Integer idNo) {
		this.idNo = idNo;
	}
	
	
}
