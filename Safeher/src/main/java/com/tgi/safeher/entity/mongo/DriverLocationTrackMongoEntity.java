package com.tgi.safeher.entity.mongo;

import java.sql.Timestamp;
import java.util.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.utils.DateUtil;

/**
 * ActiveDriverLocatoin entity. @author MyEclipse Persistence Tools
 */
@Document(collection="DriverLocationTrackMongoEntity")
public class DriverLocationTrackMongoEntity implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String driverLocationTrackId;
	private Integer appUserId;
	private String activeDriverLocationId;
	private List<DriverLocationTrakListMongoEntity> list;
	
	@Id
	public String getDriverLocationTrackId() {
		return driverLocationTrackId;
	}
	public void setDriverLocationTrackId(String driverLocationTrackId) {
		this.driverLocationTrackId = driverLocationTrackId;
	}

	public List<DriverLocationTrakListMongoEntity> getList() {
		return list;
	}

	public void setList(List<DriverLocationTrakListMongoEntity> list) {
		this.list = list;
	}
	public Integer getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}
	public String getActiveDriverLocationId() {
		return activeDriverLocationId;
	}
	public void setActiveDriverLocationId(String activeDriverLocationId) {
		this.activeDriverLocationId = activeDriverLocationId;
	}
	
	
}