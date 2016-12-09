package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_category", schema = "dbo")
public class RideCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideCategoryId;
	private String name;
	private Set<RideSearchEntity> rideSearchEntities = new HashSet<RideSearchEntity>(0);

	// Constructors

	/** default constructor */
	public RideCategoryEntity() {
	}

	/** full constructor */
	public RideCategoryEntity(String name, Set<RideSearchEntity> rideSearchEntities) {
		this.name = name;
		this.rideSearchEntities = rideSearchEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideCategoryId() {
		return this.rideCategoryId;
	}

	public void setRideCategoryId(Integer rideCategoryId) {
		this.rideCategoryId = rideCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCategory")
	public Set<RideSearchEntity> getRideSearchs() {
		return this.rideSearchEntities;
	}

	public void setRideSearchs(Set<RideSearchEntity> rideSearchEntities) {
		this.rideSearchEntities = rideSearchEntities;
	}

}