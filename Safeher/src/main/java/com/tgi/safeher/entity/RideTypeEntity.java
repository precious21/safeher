package com.tgi.safeher.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_type", schema = "dbo")
public class RideTypeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private RideTypeId id;

	// Constructors

	/** default constructor */
	public RideTypeEntity() {
	}

	/** full constructor */
	public RideTypeEntity(RideTypeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rideTypeId", column = @Column(name = "ride_type_id", nullable = false, precision = 9, scale = 0)),
			@AttributeOverride(name = "name", column = @Column(name = "name", length = 20)) })
	public RideTypeId getId() {
		return this.id;
	}

	public void setId(RideTypeId id) {
		this.id = id;
	}

}