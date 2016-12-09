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
 * ColorCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "color_category", schema = "dbo")
public class ColorCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer colorCategoryId;
	private String name;
	private Set<ColorFilterEntity> colorFilters = new HashSet<ColorFilterEntity>(0);

	// Constructors

	/** default constructor */
	public ColorCategoryEntity() {
	}

	/** minimal constructor */
	public ColorCategoryEntity(Integer colorCategoryId) {
		this.colorCategoryId = colorCategoryId;
	}

	/** full constructor */
	public ColorCategoryEntity(Integer colorCategoryId, String name,
			Set<ColorFilterEntity> colorFilters) {
		this.colorCategoryId = colorCategoryId;
		this.name = name;
		this.colorFilters = colorFilters;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "color_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getColorCategoryId() {
		return this.colorCategoryId;
	}

	public void setColorCategoryId(Integer colorCategoryId) {
		this.colorCategoryId = colorCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "colorCategory")
	public Set<ColorFilterEntity> getColorFilters() {
		return this.colorFilters;
	}

	public void setColorFilters(Set<ColorFilterEntity> colorFilters) {
		this.colorFilters = colorFilters;
	}

}