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
 * StatusFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "status_filter", schema = "dbo")
public class StatusFilterEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer statusFilterId;
	private Status status;
	private StatusCategoryEntity statusCategory;

	// Constructors

	/** default constructor */
	public StatusFilterEntity() {
	}

	/** minimal constructor */
	public StatusFilterEntity(Integer statusFilterId) {
		this.statusFilterId = statusFilterId;
	}

	/** full constructor */
	public StatusFilterEntity(Integer statusFilterId, Status status,
			StatusCategoryEntity statusCategory) {
		this.statusFilterId = statusFilterId;
		this.status = status;
		this.statusCategory = statusCategory;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "status_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getStatusFilterId() {
		return this.statusFilterId;
	}

	public void setStatusFilterId(Integer statusFilterId) {
		this.statusFilterId = statusFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_category_id")
	public StatusCategoryEntity getStatusCategory() {
		return this.statusCategory;
	}

	public void setStatusCategory(StatusCategoryEntity statusCategory) {
		this.statusCategory = statusCategory;
	}

}