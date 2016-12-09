package com.tgi.safeher.entity;

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
 * RideCharity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_charity", schema = "dbo")
public class RideCharityEntity implements java.io.Serializable {

	// Fields

	private Integer rideCharityId;
	private CharitiesEntity charities;
	private RideFinalizeEntity rideFinalize;
	private RideEntity ride;

	// Constructors

	/** default constructor */
	public RideCharityEntity() {
	}

	/** full constructor */
	public RideCharityEntity(CharitiesEntity charities, RideFinalizeEntity rideFinalize, RideEntity ride) {
		this.charities = charities;
		this.rideFinalize = rideFinalize;
		this.ride = ride;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_charity_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideCharityId() {
		return this.rideCharityId;
	}

	public void setRideCharityId(Integer rideCharityId) {
		this.rideCharityId = rideCharityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charities_id")
	public CharitiesEntity getCharities() {
		return this.charities;
	}

	public void setCharities(CharitiesEntity charities) {
		this.charities = charities;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_finalize_id")
	public RideFinalizeEntity getRideFinalize() {
		return this.rideFinalize;
	}

	public void setRideFinalize(RideFinalizeEntity rideFinalize) {
		this.rideFinalize = rideFinalize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRide() {
		return this.ride;
	}

	public void setRide(RideEntity ride) {
		this.ride = ride;
	}

}