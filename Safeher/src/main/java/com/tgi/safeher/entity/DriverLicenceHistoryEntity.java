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
 * DriverLicenceHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_licence_history", schema = "dbo")
public class DriverLicenceHistoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer driverLicenceHistoryId;
	private DriverInfoEntity driverInfo;
	private String licenceNo;
	private Timestamp licenceExpiry;
	private Timestamp provideDate;
	private Timestamp endDate;
	private String isActive;

	// Constructors

	/** default constructor */
	public DriverLicenceHistoryEntity() {
	}

	/** full constructor */
	public DriverLicenceHistoryEntity(DriverInfoEntity driverInfoEntity, String licenceNo,
			Timestamp licenceExpiry, Timestamp provideDate, Timestamp endDate,
			String isActive) {
		this.driverInfo = driverInfoEntity;
		this.licenceNo = licenceNo;
		this.licenceExpiry = licenceExpiry;
		this.provideDate = provideDate;
		this.endDate = endDate;
		this.isActive = isActive;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_licence_history_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverLicenceHistoryId() {
		return this.driverLicenceHistoryId;
	}

	public void setDriverLicenceHistoryId(Integer driverLicenceHistoryId) {
		this.driverLicenceHistoryId = driverLicenceHistoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_info_id")
	public DriverInfoEntity getDriverInfo() {
		return this.driverInfo;
	}

	public void setDriverInfo(DriverInfoEntity driverInfoEntity) {
		this.driverInfo = driverInfoEntity;
	}

	@Column(name = "licence_no", length = 20)
	public String getLicenceNo() {
		return this.licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	@Column(name = "licence_expiry", length = 23)
	public Timestamp getLicenceExpiry() {
		return this.licenceExpiry;
	}

	public void setLicenceExpiry(Timestamp licenceExpiry) {
		this.licenceExpiry = licenceExpiry;
	}

	@Column(name = "provide_date", length = 23)
	public Timestamp getProvideDate() {
		return this.provideDate;
	}

	public void setProvideDate(Timestamp provideDate) {
		this.provideDate = provideDate;
	}

	@Column(name = "end_date", length = 23)
	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}