package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PersonAddressHistoryId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class PersonAddressHistoryId  implements java.io.Serializable {

	// Fields

	private Timestamp fromDate;
	private Timestamp toDate;
	private Long countryId;
	private Integer stateId;
	private Integer cityId;
	private Integer zipCodeId;
	private String addressLineOne;
	private String addressLineTwo;
	private Integer personAddressHistoryId;
	private Integer addressTypeId;
	private String isCurrent;
	private Integer personAddressId;
	private Integer personId;

	// Constructors

	/** default constructor */
	public PersonAddressHistoryId() {
	}

	/** minimal constructor */
	public PersonAddressHistoryId(Integer personAddressHistoryId) {
		this.personAddressHistoryId = personAddressHistoryId;
	}

	/** full constructor */
	public PersonAddressHistoryId(Timestamp fromDate, Timestamp toDate,
			Long countryId, Integer stateId, Integer cityId, Integer zipCodeId,
			String addressLineOne, String addressLineTwo,
			Integer personAddressHistoryId, Integer addressTypeId,
			String isCurrent, Integer personAddressId, Integer personId) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.zipCodeId = zipCodeId;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.personAddressHistoryId = personAddressHistoryId;
		this.addressTypeId = addressTypeId;
		this.isCurrent = isCurrent;
		this.personAddressId = personAddressId;
		this.personId = personId;
	}

	// Property accessors

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

	@Column(name = "country_id", precision = 11, scale = 0)
	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Column(name = "state_id", precision = 9, scale = 0)
	public Integer getStateId() {
		return this.stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "city_id", precision = 9, scale = 0)
	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Column(name = "zip_code_id", precision = 9, scale = 0)
	public Integer getZipCodeId() {
		return this.zipCodeId;
	}

	public void setZipCodeId(Integer zipCodeId) {
		this.zipCodeId = zipCodeId;
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

	@Column(name = "person_address_history_id", nullable = false, precision = 9, scale = 0)
	public Integer getPersonAddressHistoryId() {
		return this.personAddressHistoryId;
	}

	public void setPersonAddressHistoryId(Integer personAddressHistoryId) {
		this.personAddressHistoryId = personAddressHistoryId;
	}

	@Column(name = "address_type_id", precision = 9, scale = 0)
	public Integer getAddressTypeId() {
		return this.addressTypeId;
	}

	public void setAddressTypeId(Integer addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	@Column(name = "is_current", length = 1)
	public String getIsCurrent() {
		return this.isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	@Column(name = "person_address_id", precision = 9, scale = 0)
	public Integer getPersonAddressId() {
		return this.personAddressId;
	}

	public void setPersonAddressId(Integer personAddressId) {
		this.personAddressId = personAddressId;
	}

	@Column(name = "person_id", precision = 9, scale = 0)
	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PersonAddressHistoryId))
			return false;
		PersonAddressHistoryId castOther = (PersonAddressHistoryId) other;

		return ((this.getFromDate() == castOther.getFromDate()) || (this
				.getFromDate() != null && castOther.getFromDate() != null && this
				.getFromDate().equals(castOther.getFromDate())))
				&& ((this.getToDate() == castOther.getToDate()) || (this
						.getToDate() != null && castOther.getToDate() != null && this
						.getToDate().equals(castOther.getToDate())))
				&& ((this.getCountryId() == castOther.getCountryId()) || (this
						.getCountryId() != null
						&& castOther.getCountryId() != null && this
						.getCountryId().equals(castOther.getCountryId())))
				&& ((this.getStateId() == castOther.getStateId()) || (this
						.getStateId() != null && castOther.getStateId() != null && this
						.getStateId().equals(castOther.getStateId())))
				&& ((this.getCityId() == castOther.getCityId()) || (this
						.getCityId() != null && castOther.getCityId() != null && this
						.getCityId().equals(castOther.getCityId())))
				&& ((this.getZipCodeId() == castOther.getZipCodeId()) || (this
						.getZipCodeId() != null
						&& castOther.getZipCodeId() != null && this
						.getZipCodeId().equals(castOther.getZipCodeId())))
				&& ((this.getAddressLineOne() == castOther.getAddressLineOne()) || (this
						.getAddressLineOne() != null
						&& castOther.getAddressLineOne() != null && this
						.getAddressLineOne().equals(
								castOther.getAddressLineOne())))
				&& ((this.getAddressLineTwo() == castOther.getAddressLineTwo()) || (this
						.getAddressLineTwo() != null
						&& castOther.getAddressLineTwo() != null && this
						.getAddressLineTwo().equals(
								castOther.getAddressLineTwo())))
				&& ((this.getPersonAddressHistoryId() == castOther
						.getPersonAddressHistoryId()) || (this
						.getPersonAddressHistoryId() != null
						&& castOther.getPersonAddressHistoryId() != null && this
						.getPersonAddressHistoryId().equals(
								castOther.getPersonAddressHistoryId())))
				&& ((this.getAddressTypeId() == castOther.getAddressTypeId()) || (this
						.getAddressTypeId() != null
						&& castOther.getAddressTypeId() != null && this
						.getAddressTypeId()
						.equals(castOther.getAddressTypeId())))
				&& ((this.getIsCurrent() == castOther.getIsCurrent()) || (this
						.getIsCurrent() != null
						&& castOther.getIsCurrent() != null && this
						.getIsCurrent().equals(castOther.getIsCurrent())))
				&& ((this.getPersonAddressId() == castOther
						.getPersonAddressId()) || (this.getPersonAddressId() != null
						&& castOther.getPersonAddressId() != null && this
						.getPersonAddressId().equals(
								castOther.getPersonAddressId())))
				&& ((this.getPersonId() == castOther.getPersonId()) || (this
						.getPersonId() != null
						&& castOther.getPersonId() != null && this
						.getPersonId().equals(castOther.getPersonId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFromDate() == null ? 0 : this.getFromDate().hashCode());
		result = 37 * result
				+ (getToDate() == null ? 0 : this.getToDate().hashCode());
		result = 37 * result
				+ (getCountryId() == null ? 0 : this.getCountryId().hashCode());
		result = 37 * result
				+ (getStateId() == null ? 0 : this.getStateId().hashCode());
		result = 37 * result
				+ (getCityId() == null ? 0 : this.getCityId().hashCode());
		result = 37 * result
				+ (getZipCodeId() == null ? 0 : this.getZipCodeId().hashCode());
		result = 37
				* result
				+ (getAddressLineOne() == null ? 0 : this.getAddressLineOne()
						.hashCode());
		result = 37
				* result
				+ (getAddressLineTwo() == null ? 0 : this.getAddressLineTwo()
						.hashCode());
		result = 37
				* result
				+ (getPersonAddressHistoryId() == null ? 0 : this
						.getPersonAddressHistoryId().hashCode());
		result = 37
				* result
				+ (getAddressTypeId() == null ? 0 : this.getAddressTypeId()
						.hashCode());
		result = 37 * result
				+ (getIsCurrent() == null ? 0 : this.getIsCurrent().hashCode());
		result = 37
				* result
				+ (getPersonAddressId() == null ? 0 : this.getPersonAddressId()
						.hashCode());
		result = 37 * result
				+ (getPersonId() == null ? 0 : this.getPersonId().hashCode());
		return result;
	}

}