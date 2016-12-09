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
 * DeiverVehClass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deiver_veh_class", schema = "dbo")
public class DriverVehClassEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer deiverVehClass;
	private DriverInfoEntity driverInfoEntity;
	private VehClassEntity vehClassEntity;
	private Timestamp expiryDate;
	private String isActive;
	private AppUserEntity appUser;

	// Constructors

	/** default constructor */
	public DriverVehClassEntity() {
	}

	/** full constructor */
	public DriverVehClassEntity(DriverInfoEntity driverInfoEntity, VehClassEntity vehClassEntity,
 Timestamp expiryDate, String isActive, AppUserEntity appUser) {
		this.driverInfoEntity = driverInfoEntity;
		this.vehClassEntity = vehClassEntity;
		this.expiryDate = expiryDate;
		this.isActive = isActive;
		this.appUser=appUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "deiver_veh_class", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDeiverVehClass() {
		return this.deiverVehClass;
	}

	public void setDeiverVehClass(Integer deiverVehClass) {
		this.deiverVehClass = deiverVehClass;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_info_id")
	public DriverInfoEntity getDriverInfo() {
		return this.driverInfoEntity;
	}

	public void setDriverInfo(DriverInfoEntity driverInfoEntity) {
		this.driverInfoEntity = driverInfoEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "veh_class_id")
	public VehClassEntity getVehClass() {
		return this.vehClassEntity;
	}

	public void setVehClass(VehClassEntity vehClassEntity) {
		this.vehClassEntity = vehClassEntity;
	}

	@Column(name = "expiry_date", length = 23)
	public Timestamp getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

}