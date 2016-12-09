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
 * BankAccountInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bank_account_info", schema = "dbo")
public class BankAccountInfoEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer bankAccountInfoId;
	private BankEntity bank;
	private AppUserPaymentInfoEntity appUserPaymentInfo;
	private String ibanNo;
	private String accountNo;
	private String accountTitle;
	private String location;
	private String routingNo;
	private String swiftCode;
	private String isActive;
	private String zipcode;
	private String isDefault;
	private Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountCharity = new HashSet<RidePaymnetDistributionEntity>(
			0);
	private Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountDriver = new HashSet<RidePaymnetDistributionEntity>(
			0);
	private Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountCompany = new HashSet<RidePaymnetDistributionEntity>(
			0);

	// Constructors

	/** default constructor */
	public BankAccountInfoEntity() {
	}

	/** minimal constructor */
	public BankAccountInfoEntity(Integer bankAccountInfoId) {
		this.bankAccountInfoId = bankAccountInfoId;
	}

	/** full constructor */
	public BankAccountInfoEntity(Integer bankAccountInfoId, BankEntity bank,
			AppUserPaymentInfoEntity appUserPaymentInfo, String ibanNo,
			String accountNo, String accountTitle, String location,
			String routingNo, String swiftCode, String isActive) {
		this.bankAccountInfoId = bankAccountInfoId;
		this.bank = bank;
		this.appUserPaymentInfo = appUserPaymentInfo;
		this.ibanNo = ibanNo;
		this.accountNo = accountNo;
		this.accountTitle = accountTitle;
		this.location = location;
		this.routingNo = routingNo;
		this.swiftCode = swiftCode;
		this.isActive = isActive;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "bank_account_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getBankAccountInfoId() {
		return this.bankAccountInfoId;
	}

	public void setBankAccountInfoId(Integer bankAccountInfoId) {
		this.bankAccountInfoId = bankAccountInfoId;
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
	@JoinColumn(name = "app_user_payment_info_id")
	public AppUserPaymentInfoEntity getAppUserPaymentInfo() {
		return this.appUserPaymentInfo;
	}

	public void setAppUserPaymentInfo(AppUserPaymentInfoEntity appUserPaymentInfo) {
		this.appUserPaymentInfo = appUserPaymentInfo;
	}

	@Column(name = "iban_no", length = 30)
	public String getIbanNo() {
		return this.ibanNo;
	}

	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	@Column(name = "account_no", length = 30)
	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "account_title", length = 50)
	public String getAccountTitle() {
		return this.accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	@Column(name = "location", length = 200)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "routing_no", length = 20)
	public String getRoutingNo() {
		return this.routingNo;
	}

	public void setRoutingNo(String routingNo) {
		this.routingNo = routingNo;
	}

	@Column(name = "swift_code", length = 6)
	public String getSwiftCode() {
		return this.swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "zip_code", length = 20)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Column(name = "is_default", length = 1)
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bankAccountInfoByBankAccountCharity")
	public Set<RidePaymnetDistributionEntity> getRidePaymnetDistributionsForBankAccountCharity() {
		return this.ridePaymnetDistributionsForBankAccountCharity;
	}

	public void setRidePaymnetDistributionsForBankAccountCharity(
			Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountCharity) {
		this.ridePaymnetDistributionsForBankAccountCharity = ridePaymnetDistributionsForBankAccountCharity;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bankAccountInfoByBankAccountDriver")
	public Set<RidePaymnetDistributionEntity> getRidePaymnetDistributionsForBankAccountDriver() {
		return this.ridePaymnetDistributionsForBankAccountDriver;
	}

	public void setRidePaymnetDistributionsForBankAccountDriver(
			Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountDriver) {
		this.ridePaymnetDistributionsForBankAccountDriver = ridePaymnetDistributionsForBankAccountDriver;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bankAccountInfoByBankAccountCompany")
	public Set<RidePaymnetDistributionEntity> getRidePaymnetDistributionsForBankAccountCompany() {
		return this.ridePaymnetDistributionsForBankAccountCompany;
	}

	public void setRidePaymnetDistributionsForBankAccountCompany(
			Set<RidePaymnetDistributionEntity> ridePaymnetDistributionsForBankAccountCompany) {
		this.ridePaymnetDistributionsForBankAccountCompany = ridePaymnetDistributionsForBankAccountCompany;
	}
}