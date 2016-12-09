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
 * CreditCardType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "credit_card_type", schema = "dbo")
public class CreditCardTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer creditCardTypeId;
	private String name;
	private Set<CreditCardInfoEntity> creditCardInfos = new HashSet<CreditCardInfoEntity>(0);

	// Constructors

	/** default constructor */
	public CreditCardTypeEntity() {
	}

	/** minimal constructor */
	public CreditCardTypeEntity(Integer creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
	}

	/** full constructor */
	public CreditCardTypeEntity(Integer creditCardTypeId, String name,
			Set<CreditCardInfoEntity> creditCardInfos) {
		this.creditCardTypeId = creditCardTypeId;
		this.name = name;
		this.creditCardInfos = creditCardInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "credit_card_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getCreditCardTypeId() {
		return this.creditCardTypeId;
	}

	public void setCreditCardTypeId(Integer creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creditCardType")
	public Set<CreditCardInfoEntity> getCreditCardInfos() {
		return this.creditCardInfos;
	}

	public void setCreditCardInfos(Set<CreditCardInfoEntity> creditCardInfos) {
		this.creditCardInfos = creditCardInfos;
	}

}