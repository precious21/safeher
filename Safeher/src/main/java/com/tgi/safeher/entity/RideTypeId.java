package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideTypeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class RideTypeId extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideTypeId;
	private String name;

	// Constructors

	/** default constructor */
	public RideTypeId() {
	}

	/** minimal constructor */
	public RideTypeId(Integer rideTypeId) {
		this.rideTypeId = rideTypeId;
	}

	/** full constructor */
	public RideTypeId(Integer rideTypeId, String name) {
		this.rideTypeId = rideTypeId;
		this.name = name;
	}

	// Property accessors

	@Column(name = "ride_type_id", nullable = false, precision = 9, scale = 0)
	public Integer getRideTypeId() {
		return this.rideTypeId;
	}

	public void setRideTypeId(Integer rideTypeId) {
		this.rideTypeId = rideTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RideTypeId))
			return false;
		RideTypeId castOther = (RideTypeId) other;

		return ((this.getRideTypeId() == castOther.getRideTypeId()) || (this
				.getRideTypeId() != null && castOther.getRideTypeId() != null && this
				.getRideTypeId().equals(castOther.getRideTypeId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRideTypeId() == null ? 0 : this.getRideTypeId()
						.hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		return result;
	}

}