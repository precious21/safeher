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
 * VehicleInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vehicle_info", schema = "dbo")
public class VehicleInfoEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer vehicleInfoId;
	private VehicleMakeEntity vehicleMake;
	private VehicleModelEntity vehicleModel;
	private ColorEntity color;
	private StatusEntity status;
	private String plateNumber;
	private String title;
	private String manufacturingYear;
	private String isActive;
	private Short seatCapacity;
	private Short childSeatCapacity;
	private Set<AppUserVehicleEntity> appUserVehicles = new HashSet<AppUserVehicleEntity>(0);

	// Constructors

	/** default constructor */
	public VehicleInfoEntity() {
	}

	/** minimal constructor */
	public VehicleInfoEntity(Integer vehicleInfoId) {
		this.vehicleInfoId = vehicleInfoId;
	}

	/** full constructor */
	public VehicleInfoEntity(Integer vehicleInfoId, VehicleMakeEntity vehicleMake,
			VehicleModelEntity vehicleModel, ColorEntity color, StatusEntity status,
			String plateNumber, Short seatCapacity, Short childSeatCapacity,
			String title, String manufacturingYear,
			String isActive, Set<AppUserVehicleEntity> appUserVehicles) {
		this.vehicleInfoId = vehicleInfoId;
		this.vehicleMake = vehicleMake;
		this.vehicleModel = vehicleModel;
		this.color = color;
		this.status = status;
		this.plateNumber = plateNumber;
		this.title = title;
		this.manufacturingYear = manufacturingYear;
		this.isActive = isActive;
		this.seatCapacity = seatCapacity;
		this.childSeatCapacity = childSeatCapacity;
		this.appUserVehicles = appUserVehicles;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "vehicle_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getVehicleInfoId() {
		return this.vehicleInfoId;
	}

	public void setVehicleInfoId(Integer vehicleInfoId) {
		this.vehicleInfoId = vehicleInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_make_id")
	public VehicleMakeEntity getVehicleMake() {
		return this.vehicleMake;
	}

	public void setVehicleMake(VehicleMakeEntity vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_model_id")
	public VehicleModelEntity getVehicleModel() {
		return this.vehicleModel;
	}

	public void setVehicleModel(VehicleModelEntity vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_id")
	public ColorEntity getColor() {
		return this.color;
	}

	public void setColor(ColorEntity color) {
		this.color = color;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_vehicle")
	public StatusEntity getStatus() {
		return this.status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}

	@Column(name = "plate_number", length = 20)
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Column(name = "title", length = 32)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "manufacturing_year", length = 4)
	public String getManufacturingYear() {
		return this.manufacturingYear;
	}

	public void setManufacturingYear(String manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehicleInfo")
	public Set<AppUserVehicleEntity> getAppUserVehicles() {
		return this.appUserVehicles;
	}

	public void setAppUserVehicles(Set<AppUserVehicleEntity> appUserVehicles) {
		this.appUserVehicles = appUserVehicles;
	}

	@Column(name = "seat_capacity", precision = 2, scale = 0)
	public Short getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(Short seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	
	@Column(name = "child_seat_capacity", precision = 2, scale = 0)
	public Short getChildSeatCapacity() {
		return childSeatCapacity;
	}

	public void setChildSeatCapacity(Short childSeatCapacity) {
		this.childSeatCapacity = childSeatCapacity;
	}

}