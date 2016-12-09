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
 * Color entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "color", schema = "dbo")
public class ColorEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer colorId;
	private String name;
	private String value;
	private Set<ColorFilterEntity> colorFilters = new HashSet<ColorFilterEntity>(
			0);
	private Set<VehicleInfoEntity> vehicleInfos = new HashSet<VehicleInfoEntity>(
			0);

	// Constructors

	/** default constructor */
	public ColorEntity() {
	}

	/** minimal constructor */
	public ColorEntity(Integer colorId) {
		this.colorId = colorId;
	}

	/** full constructor */
	public ColorEntity(Integer colorId, String name, String value,
			Set<ColorFilterEntity> colorFilters,
			Set<VehicleInfoEntity> vehicleInfos) {
		this.colorId = colorId;
		this.name = name;
		this.value = value;
		this.colorFilters = colorFilters;
		this.vehicleInfos = vehicleInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "color_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getColorId() {
		return this.colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "value", length = 15)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "color")
	public Set<ColorFilterEntity> getColorFilters() {
		return this.colorFilters;
	}

	public void setColorFilters(Set<ColorFilterEntity> colorFilters) {
		this.colorFilters = colorFilters;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "color")
	public Set<VehicleInfoEntity> getVehicleInfos() {
		return this.vehicleInfos;
	}

	public void setVehicleInfos(Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleInfos = vehicleInfos;
	}

}