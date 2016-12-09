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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * AppUserPaymentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_payment_info", schema = "dbo")
public class AppUserPaymentInfoEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserPaymentInfoId;
	private AppUserEntity appUser;
	private String isBankAccount;
	private String isCard;
	private String isPaypal;
	private String defaultType;
	private PaymentModeEntity paymentMode;
	private Set<BankAccountInfoEntity> bankAccountInfos = new HashSet<BankAccountInfoEntity>(
			0);
	private Set<CreditCardInfoEntity> creditCardInfos = new HashSet<CreditCardInfoEntity>(0);
	private Set<PomFundEntity> pomFunds = new HashSet<PomFundEntity>(0);
	private Set<PaypalInfoEntity> paypalInfos = new HashSet<PaypalInfoEntity>(0);

	// Constructors

	/** default constructor */
	public AppUserPaymentInfoEntity() {
	}

	/** minimal constructor */
	public AppUserPaymentInfoEntity(Integer appUserPaymentInfoId) {
		this.appUserPaymentInfoId = appUserPaymentInfoId;
	}

	/** full constructor */
	public AppUserPaymentInfoEntity(Integer appUserPaymentInfoId, AppUserEntity appUser,
			String isBankAccount, String isCard, String isPaypal,
			Set<BankAccountInfoEntity> bankAccountInfos,
			Set<CreditCardInfoEntity> creditCardInfos, Set<PomFundEntity> pomFunds,
			Set<PaypalInfoEntity> paypalInfos) {
		this.appUserPaymentInfoId = appUserPaymentInfoId;
		this.appUser = appUser;
		this.isBankAccount = isBankAccount;
		this.isCard = isCard;
		this.isPaypal = isPaypal;
		this.bankAccountInfos = bankAccountInfos;
		this.creditCardInfos = creditCardInfos;
		this.pomFunds = pomFunds;
		this.paypalInfos = paypalInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_payment_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserPaymentInfoId() {
		return this.appUserPaymentInfoId;
	}

	public void setAppUserPaymentInfoId(Integer appUserPaymentInfoId) {
		this.appUserPaymentInfoId = appUserPaymentInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "is_bank_account", length = 20)
	public String getIsBankAccount() {
		return this.isBankAccount;
	}

	public void setIsBankAccount(String isBankAccount) {
		this.isBankAccount = isBankAccount;
	}

	@Column(name = "is_card", length = 20)
	public String getIsCard() {
		return this.isCard;
	}

	public void setIsCard(String isCard) {
		this.isCard = isCard;
	}

	@Column(name = "is_paypal", length = 18)
	public String getIsPaypal() {
		return this.isPaypal;
	}

	public void setIsPaypal(String isPaypal) {
		this.isPaypal = isPaypal;
	}

	@Column(name = "default_type", length = 20)
	public String getDefaultType() {
		return this.defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserPaymentInfo")
	public Set<BankAccountInfoEntity> getBankAccountInfos() {
		return this.bankAccountInfos;
	}

	public void setBankAccountInfos(Set<BankAccountInfoEntity> bankAccountInfos) {
		this.bankAccountInfos = bankAccountInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserPaymentInfo")
	public Set<CreditCardInfoEntity> getCreditCardInfos() {
		return this.creditCardInfos;
	}

	public void setCreditCardInfos(Set<CreditCardInfoEntity> creditCardInfos) {
		this.creditCardInfos = creditCardInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserPaymentInfo")
	public Set<PomFundEntity> getPomFunds() {
		return this.pomFunds;
	}

	public void setPomFunds(Set<PomFundEntity> pomFunds) {
		this.pomFunds = pomFunds;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserPaymentInfo")
	public Set<PaypalInfoEntity> getPaypalInfos() {
		return this.paypalInfos;
	}

	public void setPaypalInfos(Set<PaypalInfoEntity> paypalInfos) {
		this.paypalInfos = paypalInfos;
	}

	/**
	 * @return the paymentMode
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_mode_default")
	public PaymentModeEntity getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentModeEntity paymentMode) {
		this.paymentMode = paymentMode;
	}

}