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
 * StateProvince entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "state_province", schema = "dbo")
public class StateProvinceEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer stateId;
	private CountryEntity countryEntity;
	private String name;
	private String code;
	private Set<AddressEntity> addresses = new HashSet<AddressEntity>(0);
	private Set<DriverInfoEntity> driverInfoEntities = new HashSet<DriverInfoEntity>(0);
	private Set<SuburbEntity> suburbEntities = new HashSet<SuburbEntity>(0);
	private Set<CityEntity> cityEntities = new HashSet<CityEntity>(0);

	// Constructors

	/** default constructor */
	public StateProvinceEntity() {
	}

	/** full constructor */
	public StateProvinceEntity(CountryEntity countryEntity, String name, String code,
			Set<AddressEntity> addresses, Set<DriverInfoEntity> driverInfoEntities,
			Set<SuburbEntity> suburbEntities, Set<CityEntity> cityEntities) {
		this.countryEntity = countryEntity;
		this.name = name;
		this.code = code;
		this.addresses = addresses;
		this.driverInfoEntities = driverInfoEntities;
		this.suburbEntities = suburbEntities;
		this.cityEntities = cityEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "state_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getStateId() {
		return this.stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stateProvince")
	public Set<AddressEntity> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stateProvince")
	public Set<DriverInfoEntity> getDriverInfos() {
		return this.driverInfoEntities;
	}

	public void setDriverInfos(Set<DriverInfoEntity> driverInfoEntities) {
		this.driverInfoEntities = driverInfoEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stateProvince")
	public Set<SuburbEntity> getSuburbs() {
		return this.suburbEntities;
	}

	public void setSuburbs(Set<SuburbEntity> suburbEntities) {
		this.suburbEntities = suburbEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stateProvince")
	public Set<CityEntity> getCities() {
		return this.cityEntities;
	}

	public void setCities(Set<CityEntity> cityEntities) {
		this.cityEntities = cityEntities;
	}

}