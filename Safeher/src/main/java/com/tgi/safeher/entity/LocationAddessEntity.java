package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * LocationAddess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "location_addess", schema = "dbo")
public class LocationAddessEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Long locationDetailId;
	private String addressOther;

	// Constructors

	/** default constructor */
	public LocationAddessEntity() {
	}

	/** full constructor */
	public LocationAddessEntity(String addressOther) {
		this.addressOther = addressOther;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "location_detail_id", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getLocationDetailId() {
		return this.locationDetailId;
	}

	public void setLocationDetailId(Long locationDetailId) {
		this.locationDetailId = locationDetailId;
	}

	@Column(name = "address_other")
	public String getAddressOther() {
		return this.addressOther;
	}

	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}

}