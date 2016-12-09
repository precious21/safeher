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
 * PersonAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person__address", schema = "dbo")
public class PersonAddressEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer personAddressId;
	private AddressTypeEntity addressType;
	private AddressEntity address;
	private PersonDetailEntity personDetail;
	private PersonEntity person;
	private String isActive;
	private Timestamp fromDate;
	private Set<PersonAddressHistoryEntity> personAddressHistoryEntities = new HashSet<PersonAddressHistoryEntity>(
			0);

	// Constructors

	/** default constructor */
	public PersonAddressEntity() {
	}

	/** full constructor */
	public PersonAddressEntity(AddressTypeEntity addressTypeEntity, AddressEntity addressEntity,
			PersonDetailEntity personDetailEntity, PersonEntity personEntity, String isActive,
			Timestamp fromDate, Set<PersonAddressHistoryEntity> personAddressHistoryEntities) {
		this.addressType = addressTypeEntity;
		this.address = addressEntity;
		this.personDetail = personDetailEntity;
		this.person = personEntity;
		this.isActive = isActive;
		this.fromDate = fromDate;
		this.personAddressHistoryEntities = personAddressHistoryEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "person_address_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPersonAddressId() {
		return this.personAddressId;
	}

	public void setPersonAddressId(Integer personAddressId) {
		this.personAddressId = personAddressId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_type_id")
	public AddressTypeEntity getAddressType() {
		return this.addressType;
	}

	public void setAddressType(AddressTypeEntity addressTypeEntity) {
		this.addressType = addressTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	public AddressEntity getAddress() {
		return this.address;
	}

	public void setAddress(AddressEntity addressEntity) {
		this.address = addressEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_detail_id")
	public PersonDetailEntity getPersonDetail() {
		return this.personDetail;
	}

	public void setPersonDetail(PersonDetailEntity personDetailEntity) {
		this.personDetail = personDetailEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.person;
	}

	public void setPerson(PersonEntity personEntity) {
		this.person = personEntity;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "from_date", length = 23)
	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personAddress")
	public Set<PersonAddressHistoryEntity> getPersonAddressHistories() {
		return this.personAddressHistoryEntities;
	}

	public void setPersonAddressHistories(
			Set<PersonAddressHistoryEntity> personAddressHistoryEntities) {
		this.personAddressHistoryEntities = personAddressHistoryEntities;
	}

}