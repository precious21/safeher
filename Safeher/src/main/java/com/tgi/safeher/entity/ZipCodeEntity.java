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
 * ZipCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zip_code", schema = "dbo")
public class ZipCodeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer zipCodeId;
	private CityEntity cityEntity;
	private String zipCode;
	private Set<AddressEntity> addresses = new HashSet<AddressEntity>(0);

	// Constructors

	/** default constructor */
	public ZipCodeEntity() {
	}

	/** full constructor */
	public ZipCodeEntity(CityEntity cityEntity, String zipCode, Set<AddressEntity> addresses) {
		this.cityEntity = cityEntity;
		this.zipCode = zipCode;
		this.addresses = addresses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "zip_code_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getZipCodeId() {
		return this.zipCodeId;
	}

	public void setZipCodeId(Integer zipCodeId) {
		this.zipCodeId = zipCodeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return this.cityEntity;
	}

	public void setCity(CityEntity cityEntity) {
		this.cityEntity = cityEntity;
	}

	@Column(name = "zip_code", length = 11)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "zipCode")
	public Set<AddressEntity> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<AddressEntity> addresses) {
		this.addresses = addresses;
	}

}