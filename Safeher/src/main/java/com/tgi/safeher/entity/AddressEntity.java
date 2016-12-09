package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * Address entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "address", schema = "dbo")
public class AddressEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer addressId;
	private ZipCodeEntity zipCodeEntity;
	private StateProvinceEntity stateProvinceEntity;
	private CityEntity cityEntity;
	private CountryEntity countryEntity;
	private Timestamp fromDate;
	private Timestamp toDate;
	private String addressLineOne;
	private String addressLineTwo;
	private String fullAddress;

	// Constructors

	/** default constructor */
	public AddressEntity() {
	}

	/** full constructor */
	public AddressEntity(ZipCodeEntity zipCodeEntity, StateProvinceEntity stateProvinceEntity, CityEntity cityEntity,
			CountryEntity countryEntity, Timestamp fromDate, Timestamp toDate,
			String addressLineOne, String addressLineTwo,
			Set<PersonAddressEntity> personAddresses) {
		this.zipCodeEntity = zipCodeEntity;
		this.stateProvinceEntity = stateProvinceEntity;
		this.cityEntity = cityEntity;
		this.countryEntity = countryEntity;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
	
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "address_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zip_code_id")
	public ZipCodeEntity getZipCode() {
		return this.zipCodeEntity;
	}

	public void setZipCode(ZipCodeEntity zipCodeEntity) {
		this.zipCodeEntity = zipCodeEntity;
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
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return this.cityEntity;
	}

	public void setCity(CityEntity cityEntity) {
		this.cityEntity = cityEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.countryEntity;
	}

	public void setCountry(CountryEntity countryEntity) {
		this.countryEntity = countryEntity;
	}

	@Column(name = "from_date", length = 23)
	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date", length = 23)
	public Timestamp getToDate() {
		return this.toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	@Column(name = "address_line_one")
	public String getAddressLineOne() {
		return this.addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	@Column(name = "address_line_two")
	public String getAddressLineTwo() {
		return this.addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	@Column(name = "full_address")
	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

}