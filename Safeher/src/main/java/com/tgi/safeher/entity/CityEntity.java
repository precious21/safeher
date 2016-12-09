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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * City entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "city", schema = "dbo")
public class CityEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer cityId;
	private StateProvinceEntity stateProvinceEntity;
	private CountryEntity countryEntity;
	private String name;
	private String code;
	private Set<AddressEntity> addresses = new HashSet<AddressEntity>(0);
	private Set<ZipCodeEntity> zipCodeEntities = new HashSet<ZipCodeEntity>(0);
	private Set<SuburbEntity> suburbEntities = new HashSet<SuburbEntity>(0);

	// Constructors

	/** default constructor */
	public CityEntity() {
	}

	/** full constructor */
	public CityEntity(StateProvinceEntity stateProvinceEntity, CountryEntity countryEntity, String name,
			String code, Set<AddressEntity> addresses, Set<ZipCodeEntity> zipCodeEntities,
			Set<SuburbEntity> suburbEntities) {
		this.stateProvinceEntity = stateProvinceEntity;
		this.countryEntity = countryEntity;
		this.name = name;
		this.code = code;
		this.addresses = addresses;
		this.zipCodeEntities = zipCodeEntities;
		this.suburbEntities = suburbEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "city_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	public StateProvinceEntity getStateProvince() {
		return this.stateProvinceEntity;
	}

	public void setStateProvince(StateProvinceEntity stateProvinceEntity) {
		this.stateProvinceEntity = stateProvinceEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.countryEntity;
	}

	public void setCountry(CountryEntity countryEntity) {
		this.countryEntity = countryEntity;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
	public Set<AddressEntity> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
	public Set<ZipCodeEntity> getZipCodes() {
		return this.zipCodeEntities;
	}

	public void setZipCodes(Set<ZipCodeEntity> zipCodeEntities) {
		this.zipCodeEntities = zipCodeEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
	public Set<SuburbEntity> getSuburbs() {
		return this.suburbEntities;
	}

	public void setSuburbs(Set<SuburbEntity> suburbEntities) {
		this.suburbEntities = suburbEntities;
	}

}