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
 * SeatCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "seat_category", schema = "dbo")
public class SeatCategoryEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer seatCategory;
	private String name;
	private Set<SeatFilterEntity> seatFilterEntities = new HashSet<SeatFilterEntity>(0);

	// Constructors

	/** default constructor */
	public SeatCategoryEntity() {
	}

	/** full constructor */
	public SeatCategoryEntity(String name, Set<SeatFilterEntity> seatFilterEntities) {
		this.name = name;
		this.seatFilterEntities = seatFilterEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "seat_category", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSeatCategory() {
		return this.seatCategory;
	}

	public void setSeatCategory(Integer seatCategory) {
		this.seatCategory = seatCategory;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seatCategory")
	public Set<SeatFilterEntity> getSeatFilters() {
		return this.seatFilterEntities;
	}

	public void setSeatFilters(Set<SeatFilterEntity> seatFilterEntities) {
		this.seatFilterEntities = seatFilterEntities;
	}

}