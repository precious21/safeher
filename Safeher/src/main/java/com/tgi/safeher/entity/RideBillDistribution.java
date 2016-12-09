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
 * RideBillDistribution entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_bill_distribution", schema = "dbo")
public class RideBillDistribution extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideBillDistributionId;
	private AppUserTypeEntity appUserType;
	private RideBillEntity rideBill;
	private StatusEntity status;
	private AppUserEntity appUser;
	private PaymentModeEntity paymentMode;
	private String btTransactionNo;
	private String btSettelmentStatus;
	private Timestamp paymentTime;
	private Double amount;

	// Constructors

	/** default constructor */
	public RideBillDistribution() {
	}

	/** full constructor */
	public RideBillDistribution(AppUserTypeEntity appUserType, RideBillEntity rideBill,
			StatusEntity status, AppUserEntity appUser, PaymentModeEntity paymentMode,
			String btTransactionNo, String btSettelmentStatus,
			Timestamp paymentTime, Double amount) {
		this.appUserType = appUserType;
		this.rideBill = rideBill;
		this.status = status;
		this.appUser = appUser;
		this.paymentMode = paymentMode;
		this.btTransactionNo = btTransactionNo;
		this.btSettelmentStatus = btSettelmentStatus;
		this.paymentTime = paymentTime;
		this.amount = amount;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_bill_distribution_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideBillDistributionId() {
		return this.rideBillDistributionId;
	}

	public void setRideBillDistributionId(Integer rideBillDistributionId) {
		this.rideBillDistributionId = rideBillDistributionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_type_id")
	public AppUserTypeEntity getAppUserType() {
		return this.appUserType;
	}

	public void setAppUserType(AppUserTypeEntity appUserType) {
		this.appUserType = appUserType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_bill_id")
	public RideBillEntity getRideBill() {
		return this.rideBill;
	}

	public void setRideBill(RideBillEntity rideBill) {
		this.rideBill = rideBill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_payment")
	public StatusEntity getStatus() {
		return this.status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "distribution_user")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_mode_id")
	public PaymentModeEntity getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(PaymentModeEntity paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Column(name = "bt_transaction_no", length = 30)
	public String getBtTransactionNo() {
		return this.btTransactionNo;
	}

	public void setBtTransactionNo(String btTransactionNo) {
		this.btTransactionNo = btTransactionNo;
	}

	@Column(name = "bt_settelment_status", length = 50)
	public String getBtSettelmentStatus() {
		return this.btSettelmentStatus;
	}

	public void setBtSettelmentStatus(String btSettelmentStatus) {
		this.btSettelmentStatus = btSettelmentStatus;
	}

	@Column(name = "payment_time", length = 23)
	public Timestamp getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Column(name = "amount", precision = 9, scale = 3)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}