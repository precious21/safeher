package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
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
 * AppUserVehicle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_vehicle", schema = "dbo")
public class AppUserVehicleEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserVehicleId;
	private RoleEntity role;
	private AppUserEntity appUser;
	private StatusEntity status;
	private VehicleInfoEntity vehicleInfo;
	private Timestamp fromDate;
	private Timestamp toDate;
	private String isActive;

	// Constructors

	/** default constructor */
	public AppUserVehicleEntity() {
	}

	/** minimal constructor */
	public AppUserVehicleEntity(Integer appUserVehicleId) {
		this.appUserVehicleId = appUserVehicleId;
	}

	/** full constructor */
	public AppUserVehicleEntity(Integer appUserVehicleId, RoleEntity role, AppUserEntity appUser,
			StatusEntity status, VehicleInfoEntity vehicleInfo, Timestamp fromDate,
			Timestamp toDate, String isActive) {
		this.appUserVehicleId = appUserVehicleId;
		this.role = role;
		this.appUser = appUser;
		this.status = status;
		this.vehicleInfo = vehicleInfo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.isActive = isActive;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_vehicle_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserVehicleId() {
		return this.appUserVehicleId;
	}

	public void setAppUserVehicleId(Integer appUserVehicleId) {
		this.appUserVehicleId = appUserVehicleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_app_user")
	public RoleEntity getRole() {
		return this.role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_role")
	public StatusEntity getStatus() {
		return this.status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_info_id")
	public VehicleInfoEntity getVehicleInfo() {
		return this.vehicleInfo;
	}

	public void setVehicleInfo(VehicleInfoEntity vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}

	@Column(name = "from_date", length = 23)
	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date", length = 23)
	public Timestamp getToDate() {
		return this.toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}