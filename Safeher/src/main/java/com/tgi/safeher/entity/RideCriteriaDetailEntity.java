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
 * RideCriteriaDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_criteria_detail", schema = "dbo")
public class RideCriteriaDetailEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideCriteriaDetailId;
	private SeatTypeEntity seatTypeEntity;
	private RideCriteriaEntity rideCriteriaEntity;
	private Short childNo;
	private Short childAge;
	private String sex;

	// Constructors

	/** default constructor */
	public RideCriteriaDetailEntity() {
	}

	/** full constructor */
	public RideCriteriaDetailEntity(SeatTypeEntity seatTypeEntity, RideCriteriaEntity rideCriteriaEntity,
			Short childNo, Short childAge, String sex) {
		this.seatTypeEntity = seatTypeEntity;
		this.rideCriteriaEntity = rideCriteriaEntity;
		this.childNo = childNo;
		this.childAge = childAge;
		this.sex = sex;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_criteria_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideCriteriaDetailId() {
		return this.rideCriteriaDetailId;
	}

	public void setRideCriteriaDetailId(Integer rideCriteriaDetailId) {
		this.rideCriteriaDetailId = rideCriteriaDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "child_seat_type")
	public SeatTypeEntity getSeatType() {
		return this.seatTypeEntity;
	}

	public void setSeatType(SeatTypeEntity seatTypeEntity) {
		this.seatTypeEntity = seatTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteriaEntity;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteriaEntity) {
		this.rideCriteriaEntity = rideCriteriaEntity;
	}

	@Column(name = "child_no")
	public Short getChildNo() {
		return this.childNo;
	}

	public void setChildNo(Short childNo) {
		this.childNo = childNo;
	}

	@Column(name = "child_age")
	public Short getChildAge() {
		return this.childAge;
	}

	public void setChildAge(Short childAge) {
		this.childAge = childAge;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}