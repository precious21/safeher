package com.tgi.safeher.entity;

import java.sql.Timestamp;

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
 * PersonAddressHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person_address_history", schema = "dbo")
public class PersonAddressHistoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer personAddressHistoryId;
	private PersonAddressEntity personAddress;
	private ZipCodeEntity zipCode;
	private AddressTypeEntity addressType;
	private StateProvinceEntity stateProvince;
	private CityEntity city;
	private PersonEntity person;
	private CountryEntity country;
	private Timestamp fromDate;
	private Timestamp toDate;
	private String addressLineOne;
	private String addressLineTwo;
	private String isCurrent;

	// Constructors

	/** default constructor */
	public PersonAddressHistoryEntity() {
	}

	/** full constructor */
	public PersonAddressHistoryEntity(PersonAddressEntity personAddress, ZipCodeEntity zipCode,
			AddressTypeEntity addressType, StateProvinceEntity stateProvince, CityEntity city,
			PersonEntity person, CountryEntity country, Timestamp fromDate,
			Timestamp toDate, String addressLineOne, String addressLineTwo,
			String isCurrent) {
		this.personAddress = personAddress;
		this.zipCode = zipCode;
		this.addressType = addressType;
		this.stateProvince = stateProvince;
		this.city = city;
		this.person = person;
		this.country = country;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.isCurrent = isCurrent;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "person_address_history_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPersonAddressHistoryId() {
		return this.personAddressHistoryId;
	}

	public void setPersonAddressHistoryId(Integer personAddressHistoryId) {
		this.personAddressHistoryId = personAddressHistoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_address_id")
	public PersonAddressEntity getPersonAddress() {
		return this.personAddress;
	}

	public void setPersonAddress(PersonAddressEntity personAddress) {
		this.personAddress = personAddress;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zip_code_id")
	public ZipCodeEntity getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(ZipCodeEntity zipCode) {
		this.zipCode = zipCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_type_id")
	public AddressTypeEntity getAddressType() {
		return this.addressType;
	}

	public void setAddressType(AddressTypeEntity addressType) {
		this.addressType = addressType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	public StateProvinceEntity getStateProvince() {
		return this.stateProvince;
	}

	public void setStateProvince(StateProvinceEntity stateProvince) {
		this.stateProvince = stateProvince;
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
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
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

	@Column(name = "is_current", length = 1)
	public String getIsCurrent() {
		return this.isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

}