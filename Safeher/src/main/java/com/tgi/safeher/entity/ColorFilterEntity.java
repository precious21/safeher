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
 * ColorFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "color_filter", schema = "dbo")
public class ColorFilterEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer colorFilterId;
	private ColorCategoryEntity colorCategory;
	private ColorEntity color;

	// Constructors

	/** default constructor */
	public ColorFilterEntity() {
	}

	/** minimal constructor */
	public ColorFilterEntity(Integer colorFilterId) {
		this.colorFilterId = colorFilterId;
	}

	/** full constructor */
	public ColorFilterEntity(Integer colorFilterId, ColorCategoryEntity colorCategory,
			ColorEntity color) {
		this.colorFilterId = colorFilterId;
		this.colorCategory = colorCategory;
		this.color = color;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "color_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getColorFilterId() {
		return this.colorFilterId;
	}

	public void setColorFilterId(Integer colorFilterId) {
		this.colorFilterId = colorFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_category_id")
	public ColorCategoryEntity getColorCategory() {
		return this.colorCategory;
	}

	public void setColorCategory(ColorCategoryEntity colorCategory) {
		this.colorCategory = colorCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_id")
	public ColorEntity getColor() {
		return this.color;
	}

	public void setColor(ColorEntity color) {
		this.color = color;
	}

}