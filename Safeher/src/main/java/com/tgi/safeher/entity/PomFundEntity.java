package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * PomFund entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pom_fund", schema = "dbo")
public class PomFundEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer pomFundId;
	private AppUserPaymentInfoEntity appUserPaymentInfo;
	private Double currentAmount;
	private Double lastUsedAmount;
	private Timestamp lastUsedDate;
	private Timestamp lastRenewDate;

	// Constructors

	/** default constructor */
	public PomFundEntity() {
	}

	/** minimal constructor */
	public PomFundEntity(Integer pomFundId) {
		this.pomFundId = pomFundId;
	}

	/** full constructor */
	public PomFundEntity(Integer pomFundId, AppUserPaymentInfoEntity appUserPaymentInfo,
			Double currentAmount, Double lastUsedAmount,
			Timestamp lastUsedDate, Timestamp lastRenewDate) {
		this.pomFundId = pomFundId;
		this.appUserPaymentInfo = appUserPaymentInfo;
		this.currentAmount = currentAmount;
		this.lastUsedAmount = lastUsedAmount;
		this.lastUsedDate = lastUsedDate;
		this.lastRenewDate = lastRenewDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "pom_fund_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPomFundId() {
		return this.pomFundId;
	}

	public void setPomFundId(Integer pomFundId) {
		this.pomFundId = pomFundId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_payment_info_id")
	public AppUserPaymentInfoEntity getAppUserPaymentInfo() {
		return this.appUserPaymentInfo;
	}

	public void setAppUserPaymentInfo(AppUserPaymentInfoEntity appUserPaymentInfo) {
		this.appUserPaymentInfo = appUserPaymentInfo;
	}

	@Column(name = "current_amount", precision = 6, scale = 3)
	public Double getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}

	@Column(name = "last_used_amount", precision = 6, scale = 3)
	public Double getLastUsedAmount() {
		return this.lastUsedAmount;
	}

	public void setLastUsedAmount(Double lastUsedAmount) {
		this.lastUsedAmount = lastUsedAmount;
	}

	@Column(name = "last_used_date", length = 23)
	public Timestamp getLastUsedDate() {
		return this.lastUsedDate;
	}

	public void setLastUsedDate(Timestamp lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}

	@Column(name = "last_renew_date", length = 23)
	public Timestamp getLastRenewDate() {
		return this.lastRenewDate;
	}

	public void setLastRenewDate(Timestamp lastRenewDate) {
		this.lastRenewDate = lastRenewDate;
	}

}