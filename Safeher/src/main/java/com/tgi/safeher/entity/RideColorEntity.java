package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideColor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_color", schema = "dbo")
public class RideColorEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideColorId;
	private String name;
	private String value;
	private Set<PreRideEntity> preRidesForColorDriver = new HashSet<PreRideEntity>(0);
	private Set<PreRideEntity> preRidesForColorPassenger = new HashSet<PreRideEntity>(0);
	private Set<RideFinalizeEntity> rideFinalizeEntities = new HashSet<RideFinalizeEntity>(0);

	// Constructors

	/** default constructor */
	public RideColorEntity() {
	}

	/** full constructor */
	public RideColorEntity(String name, Set<PreRideEntity> preRidesForColorDriver,
			Set<PreRideEntity> preRidesForColorPassenger,
			Set<RideFinalizeEntity> rideFinalizeEntities) {
		this.name = name;
		this.preRidesForColorDriver = preRidesForColorDriver;
		this.preRidesForColorPassenger = preRidesForColorPassenger;
		this.rideFinalizeEntities = rideFinalizeEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_color_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideColorId() {
		return this.rideColorId;
	}

	public void setRideColorId(Integer rideColorId) {
		this.rideColorId = rideColorId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "value", length = 15)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideColorByColorDriver")
	public Set<PreRideEntity> getPreRidesForColorDriver() {
		return this.preRidesForColorDriver;
	}

	public void setPreRidesForColorDriver(Set<PreRideEntity> preRidesForColorDriver) {
		this.preRidesForColorDriver = preRidesForColorDriver;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideColorByColorPassenger")
	public Set<PreRideEntity> getPreRidesForColorPassenger() {
		return this.preRidesForColorPassenger;
	}

	public void setPreRidesForColorPassenger(
			Set<PreRideEntity> preRidesForColorPassenger) {
		this.preRidesForColorPassenger = preRidesForColorPassenger;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideColor")
	public Set<RideFinalizeEntity> getRideFinalizes() {
		return this.rideFinalizeEntities;
	}

	public void setRideFinalizes(Set<RideFinalizeEntity> rideFinalizeEntities) {
		this.rideFinalizeEntities = rideFinalizeEntities;
	}

}