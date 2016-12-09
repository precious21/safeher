package com.tgi.safeher.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * PassengerSourceLocatoin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "passenger_source_locatoin", schema = "dbo")
public class PassengerSourceLocationEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	

	private Integer passengerSourceLocationId;
	private String longValue;
	private String latValue;
	private String placeId;
	private String isPhysical;
	private AppUserEntity appUserPassenger;
	private AreaEntity area;
	private AreaSuburbEntity areaSuburb;
	private String isOnline;
	private SuburbEntity suburb;

	// Constructors

	/** default constructor */
	public PassengerSourceLocationEntity() {
	}
	
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "passenger_source_locatoin_id", nullable = false, precision = 9, scale = 0)
	public Integer getPassengerSourceLocationId() {
		return passengerSourceLocationId;
	}

	public void setPassengerSourceLocationId(Integer passengerSourceLocationId) {
		this.passengerSourceLocationId = passengerSourceLocationId;
	}

	@Column(name = "long_value", length = 30)
	public String getLongValue() {
		return longValue;
	}

	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}

	@Column(name = "lat_value", length = 30)
	public String getLatValue() {
		return latValue;
	}

	public void setLatValue(String latValue) {
		this.latValue = latValue;
	}

	@Column(name = "place_id", length = 50)
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	@Column(name = "is_physical", length = 1)
	public String getIsPhysical() {
		return isPhysical;
	}

	public void setIsPhysical(String isPhysical) {
		this.isPhysical = isPhysical;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_passenger")
	public AppUserEntity getAppUserPassenger() {
		return appUserPassenger;
	}

	public void setAppUserPassenger(AppUserEntity appUserPassenger) {
		this.appUserPassenger = appUserPassenger;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id", insertable = false, updatable = false)
	public AreaEntity getArea() {
		return this.area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_suburb_id", insertable = false, updatable = false)
	public AreaSuburbEntity getAreaSuburb() {
		return this.areaSuburb;
	}

	public void setAreaSuburb(AreaSuburbEntity areaSuburb) {
		this.areaSuburb = areaSuburb;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_id")
	public SuburbEntity getSuburb() {
		return this.suburb;
	}

	public void setSuburb(SuburbEntity suburb) {
		this.suburb = suburb;
	}
	
	@Column(name = "is_online", length = 1)
	public String getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

}