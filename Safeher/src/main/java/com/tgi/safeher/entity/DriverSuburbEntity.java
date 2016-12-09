package com.tgi.safeher.entity;

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
 * DriverSuburb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_suburb", schema = "dbo")
public class DriverSuburbEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer driverSuburbId;
	private AppUserEntity appUserEntity;
	private SuburbEntity suburbEntity;
	private Short priorty;

	// Constructors

	/** default constructor */
	public DriverSuburbEntity() {
	}

	/** full constructor */
	public DriverSuburbEntity(AppUserEntity appUserEntity, SuburbEntity suburbEntity, Short priorty) {
		this.appUserEntity = appUserEntity;
		this.suburbEntity = suburbEntity;
		this.priorty = priorty;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_suburb_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverSuburbId() {
		return this.driverSuburbId;
	}

	public void setDriverSuburbId(Integer driverSuburbId) {
		this.driverSuburbId = driverSuburbId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_driver")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_id")
	public SuburbEntity getSuburb() {
		return this.suburbEntity;
	}

	public void setSuburb(SuburbEntity suburbEntity) {
		this.suburbEntity = suburbEntity;
	}

	@Column(name = "priorty")
	public Short getPriorty() {
		return this.priorty;
	}

	public void setPriorty(Short priorty) {
		this.priorty = priorty;
	}

}