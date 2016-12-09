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
 * Country entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "country", schema = "dbo")
public class CountryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer countryId;
	private String name;
	private String code;
	private String abbreviation;
	private Set<AddressEntity> addresses = new HashSet<AddressEntity>(0);
	private Set<SuburbEntity> suburbEntities = new HashSet<SuburbEntity>(0);
	private Set<CityEntity> cityEntities = new HashSet<CityEntity>(0);
	private Set<StateProvinceEntity> stateProvinceEntities = new HashSet<StateProvinceEntity>(0);

	// Constructors

	/** default constructor */
	public CountryEntity() {
	}

	/** full constructor */
	public CountryEntity(String name, String code, String abbreviation,
			Set<AddressEntity> addresses, Set<SuburbEntity> suburbEntities, Set<CityEntity> cityEntities,
			Set<StateProvinceEntity> stateProvinceEntities) {
		this.name = name;
		this.code = code;
		this.abbreviation = abbreviation;
		this.addresses = addresses;
		this.suburbEntities = suburbEntities;
		this.cityEntities = cityEntities;
		this.stateProvinceEntities = stateProvinceEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "country_id", unique = true, nullable = false, precision = 11, scale = 0)
	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "abbreviation", length = 3)
	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
	public Set<AddressEntity> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
	public Set<SuburbEntity> getSuburbs() {
		return this.suburbEntities;
	}

	public void setSuburbs(Set<SuburbEntity> suburbEntities) {
		this.suburbEntities = suburbEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
	public Set<CityEntity> getCities() {
		return this.cityEntities;
	}

	public void setCities(Set<CityEntity> cityEntities) {
		this.cityEntities = cityEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
	public Set<StateProvinceEntity> getStateProvinces() {
		return this.stateProvinceEntities;
	}

	public void setStateProvinces(Set<StateProvinceEntity> stateProvinceEntities) {
		this.stateProvinceEntities = stateProvinceEntities;
	}

}