package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * StatusCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "status_category", schema = "dbo")
public class StatusCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer statusCategoryId;
	private String name;
	private Set<StatusFilterEntity> statusFilters = new HashSet<StatusFilterEntity>(0);

	// Constructors

	/** default constructor */
	public StatusCategoryEntity() {
	}

	/** minimal constructor */
	public StatusCategoryEntity(Integer statusCategoryId) {
		this.statusCategoryId = statusCategoryId;
	}

	/** full constructor */
	public StatusCategoryEntity(Integer statusCategoryId, String name,
			Set<StatusFilterEntity> statusFilters) {
		this.statusCategoryId = statusCategoryId;
		this.name = name;
		this.statusFilters = statusFilters;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "status_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getStatusCategoryId() {
		return this.statusCategoryId;
	}

	public void setStatusCategoryId(Integer statusCategoryId) {
		this.statusCategoryId = statusCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "statusCategory")
	public Set<StatusFilterEntity> getStatusFilters() {
		return this.statusFilters;
	}

	public void setStatusFilters(Set<StatusFilterEntity> statusFilters) {
		this.statusFilters = statusFilters;
	}

}