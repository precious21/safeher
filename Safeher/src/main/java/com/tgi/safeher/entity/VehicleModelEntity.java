package com.tgi.safeher.entity;

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
 * VehicleModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vehicle_model", schema = "dbo")
public class VehicleModelEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer vehicleModelId;
	private VehicleMakeEntity vehicleMake;
	private String name;
	private Set<VehicleInfoEntity> vehicleInfos = new HashSet<VehicleInfoEntity>(0);

	// Constructors

	/** default constructor */
	public VehicleModelEntity() {
	}

	/** minimal constructor */
	public VehicleModelEntity(Integer vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	/** full constructor */
	public VehicleModelEntity(Integer vehicleModelId, VehicleMakeEntity vehicleMake,
			String name, Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleModelId = vehicleModelId;
		this.vehicleMake = vehicleMake;
		this.name = name;
		this.vehicleInfos = vehicleInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "vehicle_model_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getVehicleModelId() {
		return this.vehicleModelId;
	}

	public void setVehicleModelId(Integer vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_make_id")
	public VehicleMakeEntity getVehicleMake() {
		return this.vehicleMake;
	}

	public void setVehicleMake(VehicleMakeEntity vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehicleModel")
	public Set<VehicleInfoEntity> getVehicleInfos() {
		return this.vehicleInfos;
	}

	public void setVehicleInfos(Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleInfos = vehicleInfos;
	}

}