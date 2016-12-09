package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area", schema = "dbo")
public class AreaEntity implements java.io.Serializable {

	// Fields

	private Integer areaId;
	private CityEntity city;
	private CountryEntity country;
	private String name;
	private Set<AreaSuburbEntity> areaSuburbs = new HashSet<AreaSuburbEntity>(0);

	// Constructors

	/** default constructor */
	public AreaEntity() {
	}

	/** full constructor */
	public AreaEntity(CityEntity city, CountryEntity country, String name,
			Set<AreaSuburbEntity> areaSuburbs) {
		this.city = city;
		this.country = country;
		this.name = name;
		this.areaSuburbs = areaSuburbs;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "area_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return this.city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "area")
	public Set<AreaSuburbEntity> getAreaSuburbs() {
		return this.areaSuburbs;
	}

	public void setAreaSuburbs(Set<AreaSuburbEntity> areaSuburbs) {
		this.areaSuburbs = areaSuburbs;
	}

}