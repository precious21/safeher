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
 * DriverRestriction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_restriction", schema = "dbo")
public class DriverRestrictionEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer driverRestrictionId;
	private DriverInfoEntity driverInfoEntity;
	private RestrictionEntity restrictionEntity;
	private Timestamp expiryDate;
	private String isActive;
	private AppUserEntity appUser;

	// Constructors

	/** default constructor */
	public DriverRestrictionEntity() {
	}

	/** full constructor */
	public DriverRestrictionEntity(DriverInfoEntity driverInfoEntity, RestrictionEntity restrictionEntity,
			Timestamp expiryDate, String isActive, AppUserEntity appUser) {
		this.driverInfoEntity = driverInfoEntity;
		this.restrictionEntity = restrictionEntity;
		this.expiryDate = expiryDate;
		this.isActive = isActive;
		this.appUser= appUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_restriction_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverRestrictionId() {
		return this.driverRestrictionId;
	}

	public void setDriverRestrictionId(Integer driverRestrictionId) {
		this.driverRestrictionId = driverRestrictionId;
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
	@JoinColumn(name = "restriction_id")
	public RestrictionEntity getRestriction() {
		return this.restrictionEntity;
	}

	public void setRestriction(RestrictionEntity restrictionEntity) {
		this.restrictionEntity = restrictionEntity;
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