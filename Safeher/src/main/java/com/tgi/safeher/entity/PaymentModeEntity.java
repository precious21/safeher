package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * PaymentMode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "payment_mode", schema = "dbo")
public class PaymentModeEntity implements java.io.Serializable {

	// Fields

	private Integer paymentModeId;
	private String name;
	private Set<RideBillEntity> rideBills = new HashSet<RideBillEntity>(0);
	private Set<AppUserPaymentInfoEntity> appUserPaymentInfos = new HashSet<AppUserPaymentInfoEntity>(
			0);

	// Constructors

	/** default constructor */
	public PaymentModeEntity() {
	}

	/** full constructor */
	public PaymentModeEntity(String name, Set<RideBillEntity> rideBills,
			Set<AppUserPaymentInfoEntity> appUserPaymentInfos) {
		this.name = name;
		this.rideBills = rideBills;
		this.appUserPaymentInfos = appUserPaymentInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "payment_mode_id", unique = true, nullable = false, precision = 7, scale = 0)
	public Integer getPaymentModeId() {
		return this.paymentModeId;
	}

	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	@Column(name = "name", length = 18)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paymentMode")
	public Set<RideBillEntity> getRideBills() {
		return this.rideBills;
	}

	public void setRideBills(Set<RideBillEntity> rideBills) {
		this.rideBills = rideBills;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paymentMode")
	public Set<AppUserPaymentInfoEntity> getAppUserPaymentInfos() {
		return this.appUserPaymentInfos;
	}

	public void setAppUserPaymentInfos(
			Set<AppUserPaymentInfoEntity> appUserPaymentInfos) {
		this.appUserPaymentInfos = appUserPaymentInfos;
	}

}