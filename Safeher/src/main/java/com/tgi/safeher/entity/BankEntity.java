package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * Bank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bank", schema = "dbo")
public class BankEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer bankId;
	private String name;
	private Set<BankCountryFilterEntity> bankCountryFilters = new HashSet<BankCountryFilterEntity>(
			0);
	private Set<BankAccountInfoEntity> bankAccountInfos = new HashSet<BankAccountInfoEntity>(
			0);

	// Constructors

	/** default constructor */
	public BankEntity() {
	}

	/** minimal constructor */
	public BankEntity(Integer bankId) {
		this.bankId = bankId;
	}

	/** full constructor */
	public BankEntity(Integer bankId, String name,
			Set<BankCountryFilterEntity> bankCountryFilters,
			Set<BankAccountInfoEntity> bankAccountInfos) {
		this.bankId = bankId;
		this.name = name;
		this.bankCountryFilters = bankCountryFilters;
		this.bankAccountInfos = bankAccountInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "bank_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getBankId() {
		return this.bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bank")
	public Set<BankCountryFilterEntity> getBankCountryFilters() {
		return this.bankCountryFilters;
	}

	public void setBankCountryFilters(Set<BankCountryFilterEntity> bankCountryFilters) {
		this.bankCountryFilters = bankCountryFilters;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bank")
	public Set<BankAccountInfoEntity> getBankAccountInfos() {
		return this.bankAccountInfos;
	}

	public void setBankAccountInfos(Set<BankAccountInfoEntity> bankAccountInfos) {
		this.bankAccountInfos = bankAccountInfos;
	}

}