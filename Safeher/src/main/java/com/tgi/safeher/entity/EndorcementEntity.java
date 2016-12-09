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
 * Endorcement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "endorcement", schema = "dbo")
public class EndorcementEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer endorcementId;
	private String name;
	private Set<DriverEndorcementEntity> driverEndorcementEntities = new HashSet<DriverEndorcementEntity>(
			0);

	// Constructors

	/** default constructor */
	public EndorcementEntity() {
	}

	/** full constructor */
	public EndorcementEntity(String name, Set<DriverEndorcementEntity> driverEndorcementEntities) {
		this.name = name;
		this.driverEndorcementEntities = driverEndorcementEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "endorcement_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEndorcementId() {
		return this.endorcementId;
	}

	public void setEndorcementId(Integer endorcementId) {
		this.endorcementId = endorcementId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "endorcement")
	public Set<DriverEndorcementEntity> getDriverEndorcements() {
		return this.driverEndorcementEntities;
	}

	public void setDriverEndorcements(Set<DriverEndorcementEntity> driverEndorcementEntities) {
		this.driverEndorcementEntities = driverEndorcementEntities;
	}

}