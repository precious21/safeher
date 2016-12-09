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
 * VehicleMake entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vehicle_make", schema = "dbo")
public class VehicleMakeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer vehicleMakeId;
	private String name;
	private Set<VehicleModelEntity> vehicleModels = new HashSet<VehicleModelEntity>(0);
	private Set<VehicleInfoEntity> vehicleInfos = new HashSet<VehicleInfoEntity>(0);

	// Constructors

	/** default constructor */
	public VehicleMakeEntity() {
	}

	/** minimal constructor */
	public VehicleMakeEntity(Integer vehicleMakeId) {
		this.vehicleMakeId = vehicleMakeId;
	}

	/** full constructor */
	public VehicleMakeEntity(Integer vehicleMakeId, String name,
			Set<VehicleModelEntity> vehicleModels, Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleMakeId = vehicleMakeId;
		this.name = name;
		this.vehicleModels = vehicleModels;
		this.vehicleInfos = vehicleInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "vehicle_make_id", unique = true, nullable = false)
	public Integer getVehicleMakeId() {
		return this.vehicleMakeId;
	}

	public void setVehicleMakeId(Integer vehicleMakeId) {
		this.vehicleMakeId = vehicleMakeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehicleMake")
	public Set<VehicleModelEntity> getVehicleModels() {
		return this.vehicleModels;
	}

	public void setVehicleModels(Set<VehicleModelEntity> vehicleModels) {
		this.vehicleModels = vehicleModels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehicleMake")
	public Set<VehicleInfoEntity> getVehicleInfos() {
		return this.vehicleInfos;
	}

	public void setVehicleInfos(Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleInfos = vehicleInfos;
	}

}