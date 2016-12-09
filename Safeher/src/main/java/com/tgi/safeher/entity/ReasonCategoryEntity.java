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
 * ReasonCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reason_category", schema = "dbo")
public class ReasonCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer reasonCategoryId;
	private String name;
	private Set<ReasonFilterEntity> reasonFilterEntities = new HashSet<ReasonFilterEntity>(0);

	// Constructors

	/** default constructor */
	public ReasonCategoryEntity() {
	}

	/** full constructor */
	public ReasonCategoryEntity(String name, Set<ReasonFilterEntity> reasonFilterEntities) {
		this.name = name;
		this.reasonFilterEntities = reasonFilterEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "reason_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getReasonCategoryId() {
		return this.reasonCategoryId;
	}

	public void setReasonCategoryId(Integer reasonCategoryId) {
		this.reasonCategoryId = reasonCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reasonCategory")
	public Set<ReasonFilterEntity> getReasonFilters() {
		return this.reasonFilterEntities;
	}

	public void setReasonFilters(Set<ReasonFilterEntity> reasonFilterEntities) {
		this.reasonFilterEntities = reasonFilterEntities;
	}

}