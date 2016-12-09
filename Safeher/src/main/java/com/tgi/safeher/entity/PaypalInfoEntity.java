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
 * PaypalInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paypal_info", schema = "dbo")
public class PaypalInfoEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer paypalInfoId;
	private AppUserPaymentInfoEntity appUserPaymentInfo;
	private Timestamp providedDate;
	private String isActive;

	// Constructors

	/** default constructor */
	public PaypalInfoEntity() {
	}

	/** minimal constructor */
	public PaypalInfoEntity(Integer paypalInfoId) {
		this.paypalInfoId = paypalInfoId;
	}

	/** full constructor */
	public PaypalInfoEntity(Integer paypalInfoId,
			AppUserPaymentInfoEntity appUserPaymentInfo, Timestamp providedDate,
			String isActive) {
		this.paypalInfoId = paypalInfoId;
		this.appUserPaymentInfo = appUserPaymentInfo;
		this.providedDate = providedDate;
		this.isActive = isActive;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "paypal_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPaypalInfoId() {
		return this.paypalInfoId;
	}

	public void setPaypalInfoId(Integer paypalInfoId) {
		this.paypalInfoId = paypalInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_payment_info_id")
	public AppUserPaymentInfoEntity getAppUserPaymentInfo() {
		return this.appUserPaymentInfo;
	}

	public void setAppUserPaymentInfo(AppUserPaymentInfoEntity appUserPaymentInfo) {
		this.appUserPaymentInfo = appUserPaymentInfo;
	}

	@Column(name = "provided_date", length = 23)
	public Timestamp getProvidedDate() {
		return this.providedDate;
	}

	public void setProvidedDate(Timestamp providedDate) {
		this.providedDate = providedDate;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}