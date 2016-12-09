package com.tgi.safeher.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * DriverLocationTrack entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_location_track", schema = "dbo")
public class DriverLocationTrackEntity implements java.io.Serializable {

	// Fields

	private Integer driverLocationTrackId;
	private String longVal;
	private String latVal;
	private String placeId;
	private String rideNo;
	private String activeDriverLocationId;
	private Timestamp trackTime;

	// Constructors

	/** default constructor */
	public DriverLocationTrackEntity() {
	}

	/** full constructor */
	public DriverLocationTrackEntity(ActiveDriverLocationEntity activeDriverLocatoin,
			AreaSuburbEntity areaSuburb, String longVal, String latVal, String placeId) {
		this.longVal = longVal;
		this.latVal = latVal;
		this.placeId = placeId;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_location_track_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverLocationTrackId() {
		return this.driverLocationTrackId;
	}

	public void setDriverLocationTrackId(Integer driverLocationTrackId) {
		this.driverLocationTrackId = driverLocationTrackId;
	}
	
	@Column(name = "long_val", length = 30)
	public String getLongVal() {
		return this.longVal;
	}

	public void setLongVal(String longVal) {
		this.longVal = longVal;
	}

	@Column(name = "lat_val", length = 30)
	public String getLatVal() {
		return this.latVal;
	}

	public void setLatVal(String latVal) {
		this.latVal = latVal;
	}

	@Column(name = "place_id", length = 40)
	public String getPlaceId() {
		return this.placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	@Column(name = "active_driver_location_id", length = 20)
	public String getActiveDriverLocationId() {
		return activeDriverLocationId;
	}

	public void setActiveDriverLocationId(String activeDriverLocationId) {
		this.activeDriverLocationId = activeDriverLocationId;
	}
	@Column(name = "track_time", length = 23)
	public Timestamp getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(Timestamp trackTime) {
		this.trackTime = trackTime;
	}
	@Column(name = "rideNo", length = 32)
	public String getRideNo() {
		return rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

}