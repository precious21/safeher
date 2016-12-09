package com.tgi.safeher.entity;

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
 * RidePaymnetDistribution entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_paymnet_distribution", schema = "dbo")
public class RidePaymnetDistributionEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer ridePaymnetDistributionId;
	private RideBillEntity rideBillInfo;
	private BankAccountInfoEntity bankAccountInfoByBankAccountCompany;
	private BankAccountInfoEntity bankAccountInfoByBankAccountDriver;
	private BankAccountInfoEntity bankAccountInfoByBankAccountCharity;
	private StatusEntity status;
	private Double driverAmount;
	private Double companyAmount;
	private Double charityAmount;
	private String isDue;


	// Constructors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_account_company")
	public BankAccountInfoEntity getBankAccountInfoByBankAccountCompany() {
		return bankAccountInfoByBankAccountCompany;
	}

	public void setBankAccountInfoByBankAccountCompany(
			BankAccountInfoEntity bankAccountInfoByBankAccountCompany) {
		this.bankAccountInfoByBankAccountCompany = bankAccountInfoByBankAccountCompany;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_account_driver")
	public BankAccountInfoEntity getBankAccountInfoByBankAccountDriver() {
		return bankAccountInfoByBankAccountDriver;
	}

	public void setBankAccountInfoByBankAccountDriver(
			BankAccountInfoEntity bankAccountInfoByBankAccountDriver) {
		this.bankAccountInfoByBankAccountDriver = bankAccountInfoByBankAccountDriver;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_account_charity")
	public BankAccountInfoEntity getBankAccountInfoByBankAccountCharity() {
		return bankAccountInfoByBankAccountCharity;
	}

	public void setBankAccountInfoByBankAccountCharity(
			BankAccountInfoEntity bankAccountInfoByBankAccountCharity) {
		this.bankAccountInfoByBankAccountCharity = bankAccountInfoByBankAccountCharity;
	}
	
	@Column(name = "driver_amount", precision = 9, scale = 3)
	public Double getDriverAmount() {
		return driverAmount;
	}

	public void setDriverAmount(Double driverAmount) {
		this.driverAmount = driverAmount;
	}
	@Column(name = "company_amount", precision = 9, scale = 3)
	public Double getCompanyAmount() {
		return companyAmount;
	}

	public void setCompanyAmount(Double companyAmount) {
		this.companyAmount = companyAmount;
	}
	
	@Column(name = "charity_amount", precision = 9, scale = 3)
	public Double getCharityAmount() {
		return charityAmount;
	}

	public void setCharityAmount(Double charityAmount) {
		this.charityAmount = charityAmount;
	}
	
	@Column(name = "is_due", length = 1)
	public String getIsDue() {
		return isDue;
	}

	public void setIsDue(String isDue) {
		this.isDue = isDue;
	}

	/** default constructor */
	public RidePaymnetDistributionEntity() {
	}

	/** full constructor */
	public RidePaymnetDistributionEntity(RideBillEntity rideBillEntity) {
		this.rideBillInfo = rideBillEntity;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_paymnet_distribution_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRidePaymnetDistributionId() {
		return this.ridePaymnetDistributionId;
	}

	public void setRidePaymnetDistributionId(Integer ridePaymnetDistributionId) {
		this.ridePaymnetDistributionId = ridePaymnetDistributionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_bill_id")
	public RideBillEntity getRideBill() {
		return this.rideBillInfo;
	}

	public void setRideBill(RideBillEntity rideBillEntity) {
		this.rideBillInfo = rideBillEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_distribution")
	public StatusEntity getStatus() {
		return status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}

}