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
 * SeatType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "seat_type", schema = "dbo")
public class SeatTypeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer seatTypeId;
	private String name;
	private Set<SeatFilterEntity> seatFilterEntities = new HashSet<SeatFilterEntity>(0);
	private Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities = new HashSet<RideCriteriaDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public SeatTypeEntity() {
	}

	/** full constructor */
	public SeatTypeEntity(String name, Set<SeatFilterEntity> seatFilterEntities,
			Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities) {
		this.name = name;
		this.seatFilterEntities = seatFilterEntities;
		this.rideCriteriaDetailEntities = rideCriteriaDetailEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "seat_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSeatTypeId() {
		return this.seatTypeId;
	}

	public void setSeatTypeId(Integer seatTypeId) {
		this.seatTypeId = seatTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seatType")
	public Set<SeatFilterEntity> getSeatFilters() {
		return this.seatFilterEntities;
	}

	public void setSeatFilters(Set<SeatFilterEntity> seatFilterEntities) {
		this.seatFilterEntities = seatFilterEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seatType")
	public Set<RideCriteriaDetailEntity> getRideCriteriaDetails() {
		return this.rideCriteriaDetailEntities;
	}

	public void setRideCriteriaDetails(
			Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities) {
		this.rideCriteriaDetailEntities = rideCriteriaDetailEntities;
	}

}