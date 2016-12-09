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
 * DriverEndorcement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_endorcement", schema = "dbo")
public class DriverEndorcementEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer driverEndorcementId;
	private DriverInfoEntity driverInfoEntity;
	private EndorcementEntity endorcementEntity;
	private Timestamp expiryDate;
	private String isActive;
	private AppUserEntity appUser;

	// Constructors

	/** default constructor */
	public DriverEndorcementEntity() {
	}

	/** full constructor */
	public DriverEndorcementEntity(DriverInfoEntity driverInfoEntity,
			EndorcementEntity endorcementEntity, Timestamp expiryDate,
			String isActive, AppUserEntity appUser) {
		this.driverInfoEntity = driverInfoEntity;
		this.endorcementEntity = endorcementEntity;
		this.expiryDate = expiryDate;
		this.isActive = isActive;
		this.appUser = appUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_endorcement_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverEndorcementId() {
		return this.driverEndorcementId;
	}

	public void setDriverEndorcementId(Integer driverEndorcementId) {
		this.driverEndorcementId = driverEndorcementId;
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
	@JoinColumn(name = "endorcement_id")
	public EndorcementEntity getEndorcement() {
		return this.endorcementEntity;
	}

	public void setEndorcement(EndorcementEntity endorcementEntity) {
		this.endorcementEntity = endorcementEntity;
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