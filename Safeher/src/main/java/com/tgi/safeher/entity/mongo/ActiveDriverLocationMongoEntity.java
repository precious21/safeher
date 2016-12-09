package com.tgi.safeher.entity.mongo;

import java.sql.Timestamp;
import java.util.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tgi.safeher.utils.DateUtil;

/**
 * ActiveDriverLocatoin entity. @author MyEclipse Persistence Tools
 */
@Document(collection="ActiveDriverLocationMongoEntity")
public class ActiveDriverLocationMongoEntity implements java.io.Serializable {









	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private String _id;
	private String activeDriverLocatoinId;
	//private AreaSuburbEntity areaSuburb;
	//private AppUserEntity appUser;
	private String longValue;
	private String latValue;
	private String placeId;
	private String isRequested;
	private String isBooked;
	private String isPhysical;
	private Integer appUserId;
	private String rideNo;
	//private SuburbEntity suburb;
	//private AreaEntity area;
	private String isOnline;
	private String preLatValue;
	private String preLongValue;
	private String direction;
	private Date lastActiveTime;
	//private GeoJsonPoint location;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private Point loc;
	//private Set<DriverLocationTrackEntity> driverLocationTracks = new HashSet<DriverLocationTrackEntity>(
		//	0);

	// Fields

	public ActiveDriverLocationMongoEntity(/*String _id,*/
			String activeDriverLocatoinId, String longValue, String latValue,
			String placeId, String isRequested, String isBooked,
			String isPhysical, Integer appUserId, String isOnline,
			String preLatValue, String preLongValue, String direction,
			Date lastActiveTime, Point loc, String rideNo) {
		super();
		//this._id = _id;
		this.activeDriverLocatoinId = activeDriverLocatoinId;
		this.longValue = longValue;
		this.latValue = latValue;
		this.placeId = placeId;
		this.isRequested = isRequested;
		this.isBooked = isBooked;
		this.isPhysical = isPhysical;
		this.appUserId = appUserId;
		this.isOnline = isOnline;
		this.preLatValue = preLatValue;
		this.preLongValue = preLongValue;
		this.direction = direction;
		this.lastActiveTime = lastActiveTime;
		this.loc = loc;
		this.rideNo = rideNo;
	}


	
	

	/** default constructor */
	public ActiveDriverLocationMongoEntity() {
	}
	public ActiveDriverLocationMongoEntity(Point p) {
		this.loc = p;
		this.lastActiveTime = new Date(DateUtil.getCurrentTimestamp().getTime());
	}

	
	/*@GenericGenerator(name = "generator", strategy = "increment")*/
	@Id
	public String getActiveDriverLocatoinId() {
		return activeDriverLocatoinId;
	}

	public void setActiveDriverLocatoinId(String activeDriverLocatoinId) {
		this.activeDriverLocatoinId = activeDriverLocatoinId;
	}



	public String getLongValue() {
		return longValue;
	}

	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}

	public String getLatValue() {
		return latValue;
	}

	public void setLatValue(String latValue) {
		this.latValue = latValue;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getIsRequested() {
		return isRequested;
	}

	public void setIsRequested(String isRequested) {
		this.isRequested = isRequested;
	}

	public String getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(String isBooked) {
		this.isBooked = isBooked;
	}

	public String getIsPhysical() {
		return isPhysical;
	}

	public void setIsPhysical(String isPhysical) {
		this.isPhysical = isPhysical;
	}

	

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getPreLatValue() {
		return preLatValue;
	}

	public void setPreLatValue(String preLatValue) {
		this.preLatValue = preLatValue;
	}

	public String getPreLongValue() {
		return preLongValue;
	}

	public void setPreLongValue(String preLongValue) {
		this.preLongValue = preLongValue;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	

	/*public GeoJsonPoint getLocation() {
		return location;
	}

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}*/



	public Point getLoc() {
		return loc;
	}



	public void setLoc(Point loc) {
		this.loc = loc;
	}


	/*public String get_id() {
		return _id;
	}





	public void set_id(String _id) {
		this._id = _id;
	}
*/







	@Override
	public String toString() {
		return "ActiveDriverLocationMongoEntity [_id=" 
				+ ", activeDriverLocatoinId=" + activeDriverLocatoinId
				+ ", longValue=" + longValue + ", latValue=" + latValue
				+ ", placeId=" + placeId + ", isRequested=" + isRequested
				+ ", isBooked=" + isBooked + ", isPhysical=" + isPhysical
				+ ", appUserId=" + appUserId + ", isOnline=" + isOnline
				+ ", preLatValue=" + preLatValue + ", preLongValue="
				+ preLongValue + ", direction=" + direction
				+ ", lastActiveTime=" + lastActiveTime + ", loc=" + loc + "]";
	}





	public Integer getAppUserId() {
		return appUserId;
	}





	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}





	public String getRideNo() {
		return rideNo;
	}





	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
	
	
	
}