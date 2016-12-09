package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * BankCountryFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bank_country_filter", schema = "dbo")
public class BankCountryFilterEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer bankCountryFilterId;
	private BankEntity bank;
	private CountryEntity country;

	// Constructors

	/** default constructor */
	public BankCountryFilterEntity() {
	}

	/** minimal constructor */
	public BankCountryFilterEntity(Integer bankCountryFilterId) {
		this.bankCountryFilterId = bankCountryFilterId;
	}

	/** full constructor */
	public BankCountryFilterEntity(Integer bankCountryFilterId, BankEntity bank,
			CountryEntity country) {
		this.bankCountryFilterId = bankCountryFilterId;
		this.bank = bank;
		this.country = country;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "bank_country_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getBankCountryFilterId() {
		return this.bankCountryFilterId;
	}

	public void setBankCountryFilterId(Integer bankCountryFilterId) {
		this.bankCountryFilterId = bankCountryFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	public BankEntity getBank() {
		return this.bank;
	}

	public void setBank(BankEntity bank) {
		this.bank = bank;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

}