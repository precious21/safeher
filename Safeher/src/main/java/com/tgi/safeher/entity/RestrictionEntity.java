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
 * Restriction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "restriction", schema = "dbo")
public class RestrictionEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer restrictionId;
	private String name;
	private Set<DriverRestrictionEntity> driverRestrictionEntities = new HashSet<DriverRestrictionEntity>(
			0);

	// Constructors

	/** default constructor */
	public RestrictionEntity() {
	}

	/** full constructor */
	public RestrictionEntity(String name, Set<DriverRestrictionEntity> driverRestrictionEntities) {
		this.name = name;
		this.driverRestrictionEntities = driverRestrictionEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "restriction_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRestrictionId() {
		return this.restrictionId;
	}

	public void setRestrictionId(Integer restrictionId) {
		this.restrictionId = restrictionId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restriction")
	public Set<DriverRestrictionEntity> getDriverRestrictions() {
		return this.driverRestrictionEntities;
	}

	public void setDriverRestrictions(Set<DriverRestrictionEntity> driverRestrictionEntities) {
		this.driverRestrictionEntities = driverRestrictionEntities;
	}

}