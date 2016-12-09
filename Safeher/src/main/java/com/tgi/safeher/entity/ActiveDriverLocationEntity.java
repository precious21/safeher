package com.tgi.safeher.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * ActiveDriverLocatoin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "active_driver_locatoin", schema = "dbo")
public class ActiveDriverLocationEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer activeDriverLocatoinId;
	private AreaSuburbEntity areaSuburb;
	private AppUserEntity appUser;
	private String longValue;
	private String latValue;
	private String placeId;
	private String isRequested;
	private String isBooked;
	private String isPhysical;
	private SuburbEntity suburb;
	private AreaEntity area;
	private String isOnline;
	private String preLatValue;
	private String preLongValue;
	private String direction;
	private Timestamp lastActiveTime;
	private String rideNo;
	// Constructors

	/** default constructor */
	public ActiveDriverLocationEntity() {
	}

	/** full constructor */
	public ActiveDriverLocationEntity(AreaSuburbEntity areaSuburb,
			AppUserEntity appUser, String longValue, String latValue,
			String placeId, String isRequested, String isBooked,
			String isPhysical, String isOnline,
			Set<DriverLocationTrackEntity> driverLocationTracks) {
		this.areaSuburb = areaSuburb;
		this.appUser = appUser;
		this.longValue = longValue;
		this.latValue = latValue;
		this.placeId = placeId;
		this.isRequested = isRequested;
		this.isBooked = isBooked;
		this.isPhysical = isPhysical;
		this.isOnline = isOnline;
		
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "active_driver_locatoin_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getActiveDriverLocatoinId() {
		return this.activeDriverLocatoinId;
	}

	public void setActiveDriverLocatoinId(Integer activeDriverLocatoinId) {
		this.activeDriverLocatoinId = activeDriverLocatoinId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_suburb_id")
	public AreaSuburbEntity getAreaSuburb() {
		return this.areaSuburb;
	}

	public void setAreaSuburb(AreaSuburbEntity areaSuburb) {
		this.areaSuburb = areaSuburb;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_driver")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "long_value", length = 30)
	public String getLongValue() {
		return this.longValue;
	}

	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}

	@Column(name = "lat_value", length = 30)
	public String getLatValue() {
		return this.latValue;
	}

	public void setLatValue(String latValue) {
		this.latValue = latValue;
	}

	@Column(name = "place_id", length = 50)
	public String getPlaceId() {
		return this.placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	@Column(name = "is_requested", length = 1)
	public String getIsRequested() {
		return this.isRequested;
	}

	public void setIsRequested(String isRequested) {
		this.isRequested = isRequested;
	}

	@Column(name = "is_booked", length = 1)
	public String getIsBooked() {
		return this.isBooked;
	}

	public void setIsBooked(String isBooked) {
		this.isBooked = isBooked;
	}
	@Column(name = "is_physical", length = 1)
	public String getIsPhysical() {
		return isPhysical;
	}

	public void setIsPhysical(String isPhysical) {
		this.isPhysical = isPhysical;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_id")
	public SuburbEntity getSuburb() {
		return this.suburb;
	}

	public void setSuburb(SuburbEntity suburb) {
		this.suburb = suburb;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public AreaEntity getArea() {
		return this.area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}
	
	@Column(name = "is_online", length = 1)
	public String getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	
	@Column(name = "pre_lat_value", length = 30)
	 public String getPreLatValue() {
	  return this.preLatValue;
	 }

	 public void setPreLatValue(String preLatValue) {
	  this.preLatValue = preLatValue;
	 }

	 @Column(name = "pre_long_value", length = 30)
	 public String getPreLongValue() {
	  return this.preLongValue;
	 }

	 public void setPreLongValue(String preLongValue) {
	  this.preLongValue = preLongValue;
	 }

	@Column(name = "direction", length = 300)
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "last_active_time", length = 23)
	public Timestamp getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Timestamp lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	@Override
	public String toString() {
		return "ActiveDriverLocationEntity [activeDriverLocatoinId="
				+ activeDriverLocatoinId + ", areaSuburb=" + areaSuburb
				+ ", appUser=" + appUser + ", longValue=" + longValue
				+ ", latValue=" + latValue + ", placeId=" + placeId
				+ ", isRequested=" + isRequested + ", isBooked=" + isBooked
				+ ", isPhysical=" + isPhysical + ", suburb=" + suburb
				+ ", area=" + area + ", isOnline=" + isOnline
				+ ", preLatValue=" + preLatValue + ", preLongValue="
				+ preLongValue + ", direction=" + direction+	"]";
	}
	
	@Column(name = "rideNo", length = 32)
	public String getRideNo() {
		return rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
}