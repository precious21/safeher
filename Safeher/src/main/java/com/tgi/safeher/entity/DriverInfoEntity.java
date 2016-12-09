package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * DriverInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_info", schema = "dbo")
public class DriverInfoEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer driverInfoId;
	private StateProvinceEntity stateProvinceEntity;
	private String currentLicenceNo;
	private Timestamp currentLicenceExpiry;
	private String driverNo;
	private String isQualifiedVehicle;
	private Set<AppUserEntity> appUserEntities = new HashSet<AppUserEntity>(0);
	private Set<DriverRestrictionEntity> driverRestrictionEntities = new HashSet<DriverRestrictionEntity>(
			0);
	private Set<DriverVehClassEntity> deiverVehClasses = new HashSet<DriverVehClassEntity>(
			0);
	private Set<DriverLicenceHistoryEntity> driverLicenceHistoryEntities = new HashSet<DriverLicenceHistoryEntity>(
			0);
	private Set<DriverEndorcementEntity> driverEndorcementEntities = new HashSet<DriverEndorcementEntity>(
			0);

	// Constructors

	/** default constructor */
	public DriverInfoEntity() {
	}

	/** full constructor */
	public DriverInfoEntity(StateProvinceEntity stateProvinceEntity, String currentLicenceNo,
			Timestamp currentLicenceExpiry, String driverNo,
			Set<AppUserEntity> appUserEntities, Set<DriverRestrictionEntity> driverRestrictionEntities,
			Set<DriverVehClassEntity> deiverVehClasses,
			Set<DriverLicenceHistoryEntity> driverLicenceHistoryEntities,
			Set<DriverEndorcementEntity> driverEndorcementEntities) {
		this.stateProvinceEntity = stateProvinceEntity;
		this.currentLicenceNo = currentLicenceNo;
		this.currentLicenceExpiry = currentLicenceExpiry;
		this.driverNo = driverNo;
		this.appUserEntities = appUserEntities;
		this.driverRestrictionEntities = driverRestrictionEntities;
		this.deiverVehClasses = deiverVehClasses;
		this.driverLicenceHistoryEntities = driverLicenceHistoryEntities;
		this.driverEndorcementEntities = driverEndorcementEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverInfoId() {
		return this.driverInfoId;
	}

	public void setDriverInfoId(Integer driverInfoId) {
		this.driverInfoId = driverInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_licence")
	public StateProvinceEntity getStateProvince() {
		return this.stateProvinceEntity;
	}

	public void setStateProvince(StateProvinceEntity stateProvinceEntity) {
		this.stateProvinceEntity = stateProvinceEntity;
	}

	@Column(name = "current_licence_no", length = 32)
	public String getCurrentLicenceNo() {
		return this.currentLicenceNo;
	}

	public void setCurrentLicenceNo(String currentLicenceNo) {
		this.currentLicenceNo = currentLicenceNo;
	}

	@Column(name = "current_licence_expiry", length = 23)
	public Timestamp getCurrentLicenceExpiry() {
		return this.currentLicenceExpiry;
	}

	public void setCurrentLicenceExpiry(Timestamp currentLicenceExpiry) {
		this.currentLicenceExpiry = currentLicenceExpiry;
	}

	@Column(name = "driver_no", length = 32)
	public String getDriverNo() {
		return this.driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverInfo")
	public Set<AppUserEntity> getAppUsers() {
		return this.appUserEntities;
	}

	public void setAppUsers(Set<AppUserEntity> appUserEntities) {
		this.appUserEntities = appUserEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverInfo")
	public Set<DriverRestrictionEntity> getDriverRestrictions() {
		return this.driverRestrictionEntities;
	}

	public void setDriverRestrictions(Set<DriverRestrictionEntity> driverRestrictionEntities) {
		this.driverRestrictionEntities = driverRestrictionEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverInfo")
	public Set<DriverVehClassEntity> getDeiverVehClasses() {
		return this.deiverVehClasses;
	}

	public void setDeiverVehClasses(Set<DriverVehClassEntity> deiverVehClasses) {
		this.deiverVehClasses = deiverVehClasses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverInfo")
	public Set<DriverLicenceHistoryEntity> getDriverLicenceHistories() {
		return this.driverLicenceHistoryEntities;
	}

	public void setDriverLicenceHistories(
			Set<DriverLicenceHistoryEntity> driverLicenceHistoryEntities) {
		this.driverLicenceHistoryEntities = driverLicenceHistoryEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverInfo")
	public Set<DriverEndorcementEntity> getDriverEndorcements() {
		return this.driverEndorcementEntities;
	}

	public void setDriverEndorcements(Set<DriverEndorcementEntity> driverEndorcementEntities) {
		this.driverEndorcementEntities = driverEndorcementEntities;
	}

	@Column(name = "is_qualified_vehicle", length = 1)
	public String getIsQualifiedVehicle() {
		return isQualifiedVehicle;
	}

	public void setIsQualifiedVehicle(String isQualifiedVehicle) {
		this.isQualifiedVehicle = isQualifiedVehicle;
	}

}