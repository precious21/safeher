package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PassengerSourceLocatoinId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class PassengerSourceLocatoinId implements java.io.Serializable {

	// Fields

	private String longValue;
	private String latValue;
	private String placeId;
	private Integer passengerSourceLocatoinId;
	private Integer appUserPassenger;
	private Integer areaSuburbId;

	// Constructors

	/** default constructor */
	public PassengerSourceLocatoinId() {
	}

	/** minimal constructor */
	public PassengerSourceLocatoinId(Integer passengerSourceLocatoinId) {
		this.passengerSourceLocatoinId = passengerSourceLocatoinId;
	}

	/** full constructor */
	public PassengerSourceLocatoinId(String longValue, String latValue,
			String placeId, Integer passengerSourceLocatoinId,
			Integer appUserPassenger, Integer areaSuburbId) {
		this.longValue = longValue;
		this.latValue = latValue;
		this.placeId = placeId;
		this.passengerSourceLocatoinId = passengerSourceLocatoinId;
		this.appUserPassenger = appUserPassenger;
		this.areaSuburbId = areaSuburbId;
	}

	// Property accessors

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

	@Column(name = "passenger_source_locatoin_id", nullable = false, precision = 9, scale = 0)
	public Integer getPassengerSourceLocatoinId() {
		return this.passengerSourceLocatoinId;
	}

	public void setPassengerSourceLocatoinId(Integer passengerSourceLocatoinId) {
		this.passengerSourceLocatoinId = passengerSourceLocatoinId;
	}

	@Column(name = "app_user_passenger", precision = 9, scale = 0)
	public Integer getAppUserPassenger() {
		return this.appUserPassenger;
	}

	public void setAppUserPassenger(Integer appUserPassenger) {
		this.appUserPassenger = appUserPassenger;
	}

	@Column(name = "area_suburb_id", precision = 9, scale = 0)
	public Integer getAreaSuburbId() {
		return this.areaSuburbId;
	}

	public void setAreaSuburbId(Integer areaSuburbId) {
		this.areaSuburbId = areaSuburbId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PassengerSourceLocatoinId))
			return false;
		PassengerSourceLocatoinId castOther = (PassengerSourceLocatoinId) other;

		return ((this.getLongValue() == castOther.getLongValue()) || (this
				.getLongValue() != null && castOther.getLongValue() != null && this
				.getLongValue().equals(castOther.getLongValue())))
				&& ((this.getLatValue() == castOther.getLatValue()) || (this
						.getLatValue() != null
						&& castOther.getLatValue() != null && this
						.getLatValue().equals(castOther.getLatValue())))
				&& ((this.getPlaceId() == castOther.getPlaceId()) || (this
						.getPlaceId() != null && castOther.getPlaceId() != null && this
						.getPlaceId().equals(castOther.getPlaceId())))
				&& ((this.getPassengerSourceLocatoinId() == castOther
						.getPassengerSourceLocatoinId()) || (this
						.getPassengerSourceLocatoinId() != null
						&& castOther.getPassengerSourceLocatoinId() != null && this
						.getPassengerSourceLocatoinId().equals(
								castOther.getPassengerSourceLocatoinId())))
				&& ((this.getAppUserPassenger() == castOther
						.getAppUserPassenger()) || (this.getAppUserPassenger() != null
						&& castOther.getAppUserPassenger() != null && this
						.getAppUserPassenger().equals(
								castOther.getAppUserPassenger())))
				&& ((this.getAreaSuburbId() == castOther.getAreaSuburbId()) || (this
						.getAreaSuburbId() != null
						&& castOther.getAreaSuburbId() != null && this
						.getAreaSuburbId().equals(castOther.getAreaSuburbId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLongValue() == null ? 0 : this.getLongValue().hashCode());
		result = 37 * result
				+ (getLatValue() == null ? 0 : this.getLatValue().hashCode());
		result = 37 * result
				+ (getPlaceId() == null ? 0 : this.getPlaceId().hashCode());
		result = 37
				* result
				+ (getPassengerSourceLocatoinId() == null ? 0 : this
						.getPassengerSourceLocatoinId().hashCode());
		result = 37
				* result
				+ (getAppUserPassenger() == null ? 0 : this
						.getAppUserPassenger().hashCode());
		result = 37
				* result
				+ (getAreaSuburbId() == null ? 0 : this.getAreaSuburbId()
						.hashCode());
		return result;
	}

}