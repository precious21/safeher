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
 * RideBill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_bill", schema = "dbo")
public class RideBillEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideBillId;
	private RideEntity rideInfoId;
	private PaymentModeEntity paymentMode;
	private StatusEntity status;
	private AppUserEntity appUserByAppUserPassenger;
	private AppUserEntity appUserByAppUserDriver;
	private AppUserEntity appUserByAppUserDonar;
	private CreditCardInfoEntity creditCardInfo;
	private Double fineAmount;
	private Double rideAmount;
	private Double tipAmount;
	private String invoiceNo;
	private Timestamp paymentTime;
	private Double totalAmount;
	private String btTransactionNo;
	private String btSettelmentStatus;
	private String isPromotionUsed;
	private Set<RidePaymnetDistributionEntity> ridePaymnetDistributionEntities = new HashSet<RidePaymnetDistributionEntity>(
			0);

	// Constructors

	/** default constructor */
	public RideBillEntity() {
	}

	/** full constructor */
	public RideBillEntity(RideEntity rideEntity,
			Set<RidePaymnetDistributionEntity> ridePaymnetDistributionEntities) {
		this.setRideInfoId(rideEntity);
		this.ridePaymnetDistributionEntities = ridePaymnetDistributionEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_bill_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideBillId() {
		return this.rideBillId;
	}

	public void setRideBillId(Integer rideBillId) {
		this.rideBillId = rideBillId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideBill")
	public Set<RidePaymnetDistributionEntity> getRidePaymnetDistributions() {
		return this.ridePaymnetDistributionEntities;
	}

	public void setRidePaymnetDistributions(
			Set<RidePaymnetDistributionEntity> ridePaymnetDistributionEntities) {
		this.ridePaymnetDistributionEntities = ridePaymnetDistributionEntities;
	}

	/**
	 * @return the appUserByAppUserPassenger
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_passenger")
	public AppUserEntity getAppUserByAppUserPassenger() {
		return appUserByAppUserPassenger;
	}

	/**
	 * @param appUserByAppUserPassenger the appUserByAppUserPassenger to set
	 */
	public void setAppUserByAppUserPassenger(AppUserEntity appUserByAppUserPassenger) {
		this.appUserByAppUserPassenger = appUserByAppUserPassenger;
	}

	/**
	 * @return the appUserByAppUserDriver
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_driver")
	public AppUserEntity getAppUserByAppUserDriver() {
		return appUserByAppUserDriver;
	}

	/**
	 * @param appUserByAppUserDriver the appUserByAppUserDriver to set
	 */
	public void setAppUserByAppUserDriver(AppUserEntity appUserByAppUserDriver) {
		this.appUserByAppUserDriver = appUserByAppUserDriver;
	}

	/**
	 * @return the creditCardInfo
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_card_info_id")
	public CreditCardInfoEntity getCreditCardInfo() {
		return creditCardInfo;
	}

	/**
	 * @param creditCardInfo the creditCardInfo to set
	 */
	public void setCreditCardInfo(CreditCardInfoEntity creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	/**
	 * @return the fineAmount
	 */
	@Column(name = "fine_amount", precision = 6, scale = 3)
	public Double getFineAmount() {
		return fineAmount;
	}

	/**
	 * @param fineAmount the fineAmount to set
	 */
	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	/**
	 * @return the rideAmount
	 */
	@Column(name = "ride_amount", precision = 9, scale = 3)
	public Double getRideAmount() {
		return rideAmount;
	}

	/**
	 * @param rideAmount the rideAmount to set
	 */
	public void setRideAmount(Double rideAmount) {
		this.rideAmount = rideAmount;
	}

	/**
	 * @return the invoiceNo
	 */
	@Column(name = "invoice_no", length = 20)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the tipAmount
	 */
	@Column(name = "tip_amount", precision = 6, scale = 3)
	public Double getTipAmount() {
		return tipAmount;
	}

	/**
	 * @param tipAmount the tipAmount to set
	 */
	public void setTipAmount(Double tipAmount) {
		this.tipAmount = tipAmount;
	}

	/**
	 * @return the paymentTime
	 */
	@Column(name = "payment_time", length = 23)
	public Timestamp getPaymentTime() {
		return paymentTime;
	}

	/**
	 * @param paymentTime the paymentTime to set
	 */
	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	/**
	 * @return the totalAmount
	 */
	@Column(name = "total_amount", precision = 9, scale = 3)
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the rideInfoId
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRideInfoId() {
		return rideInfoId;
	}

	/**
	 * @param rideInfoId the rideInfoId to set
	 */
	public void setRideInfoId(RideEntity rideInfoId) {
		this.rideInfoId = rideInfoId;
	}

	/**
	 * @return the paymentMode
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_mode_id")
	public PaymentModeEntity getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentModeEntity paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_payment")
	public StatusEntity getStatus() {
		return status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}
	
	@Column(name = "bt_transaction_no", length = 1000)
	public String getBtTransactionNo() {
		return btTransactionNo;
	}

	public void setBtTransactionNo(String btTransactionNo) {
		this.btTransactionNo = btTransactionNo;
	}

	@Column(name = "bt_settelment_status", length = 30)
	public String getBtSettelmentStatus() {
		return btSettelmentStatus;
	}

	public void setBtSettelmentStatus(String btSettelmentStatus) {
		this.btSettelmentStatus = btSettelmentStatus;
	}

	@Column(name="is_promotion_used", length=1)
	public String getIsPromotionUsed() {
		return isPromotionUsed;
	}

	public void setIsPromotionUsed(String isPromotionUsed) {
		this.isPromotionUsed = isPromotionUsed;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_donar")
	public AppUserEntity getAppUserByAppUserDonar() {
		return appUserByAppUserDonar;
	}

	public void setAppUserByAppUserDonar(AppUserEntity appUserByAppUserDonar) {
		this.appUserByAppUserDonar = appUserByAppUserDonar;
	}

}