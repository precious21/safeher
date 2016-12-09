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
 * SeatFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "seat_filter", schema = "dbo")
public class SeatFilterEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer seatFilterId;
	private SeatTypeEntity seatTypeEntity;
	private SeatCategoryEntity seatCategoryEntity;

	// Constructors

	/** default constructor */
	public SeatFilterEntity() {
	}

	/** full constructor */
	public SeatFilterEntity(SeatTypeEntity seatTypeEntity, SeatCategoryEntity seatCategoryEntity) {
		this.seatTypeEntity = seatTypeEntity;
		this.seatCategoryEntity = seatCategoryEntity;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "seat_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSeatFilterId() {
		return this.seatFilterId;
	}

	public void setSeatFilterId(Integer seatFilterId) {
		this.seatFilterId = seatFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_type_id")
	public SeatTypeEntity getSeatType() {
		return this.seatTypeEntity;
	}

	public void setSeatType(SeatTypeEntity seatTypeEntity) {
		this.seatTypeEntity = seatTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_category")
	public SeatCategoryEntity getSeatCategory() {
		return this.seatCategoryEntity;
	}

	public void setSeatCategory(SeatCategoryEntity seatCategoryEntity) {
		this.seatCategoryEntity = seatCategoryEntity;
	}

}