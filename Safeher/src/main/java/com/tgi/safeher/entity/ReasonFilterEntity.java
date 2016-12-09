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
 * ReasonFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reason_filter", schema = "dbo")
public class ReasonFilterEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer reasonFilterId;
	private ReasonCategoryEntity reasonCategoryEntity;
	private ReasonEntity reasonEntity;
	private String isDriver;

	// Constructors

	/** default constructor */
	public ReasonFilterEntity() {
	}

	/** full constructor */
	public ReasonFilterEntity(ReasonCategoryEntity reasonCategoryEntity, ReasonEntity reasonEntity, String isDriver) {
		this.reasonCategoryEntity = reasonCategoryEntity;
		this.reasonEntity = reasonEntity;
		this.isDriver = isDriver;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "reason_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getReasonFilterId() {
		return this.reasonFilterId;
	}

	public void setReasonFilterId(Integer reasonFilterId) {
		this.reasonFilterId = reasonFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_category_id")
	public ReasonCategoryEntity getReasonCategory() {
		return this.reasonCategoryEntity;
	}

	public void setReasonCategory(ReasonCategoryEntity reasonCategoryEntity) {
		this.reasonCategoryEntity = reasonCategoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_id")
	public ReasonEntity getReason() {
		return this.reasonEntity;
	}

	public void setReason(ReasonEntity reasonEntity) {
		this.reasonEntity = reasonEntity;
	}

	@Column(name = "is_driver", length = 1)
	public String getIsDriver() {
		return this.isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
}