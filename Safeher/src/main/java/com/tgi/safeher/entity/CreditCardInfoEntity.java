package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * CreditCardInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "credit_card_info", schema = "dbo")
public class CreditCardInfoEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer creditCardInfoId;
	private CreditCardTypeEntity creditCardType;
	private AppUserPaymentInfoEntity appUserPaymentInfo;
	private String firstName;
	private String cvv;
	private String cardNumber;
	private Timestamp expiryDate;
	private String lastName;
	private String isActive;
	private String isDefault;
	private String btCustomerNo;
	private Set<RideBillEntity> rideBills = new HashSet<RideBillEntity>(0);
	// Constructors

	/** default constructor */
	public CreditCardInfoEntity() {
	}

	/** minimal constructor */
	public CreditCardInfoEntity(Integer creditCardInfoId) {
		this.creditCardInfoId = creditCardInfoId;
	}

	/** full constructor */
	public CreditCardInfoEntity(Integer creditCardInfoId,
			CreditCardTypeEntity creditCardType,
			AppUserPaymentInfoEntity appUserPaymentInfo, String firstName,
			String cvv, String cardNumber, Timestamp expiryDate,
			String lastName, String isActive) {
		this.creditCardInfoId = creditCardInfoId;
		this.creditCardType = creditCardType;
		this.appUserPaymentInfo = appUserPaymentInfo;
		this.firstName = firstName;
		this.cvv = cvv;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.lastName = lastName;
		this.isActive = isActive;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "credit_card_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getCreditCardInfoId() {
		return this.creditCardInfoId;
	}

	public void setCreditCardInfoId(Integer creditCardInfoId) {
		this.creditCardInfoId = creditCardInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_card_type_id")
	public CreditCardTypeEntity getCreditCardType() {
		return this.creditCardType;
	}

	public void setCreditCardType(CreditCardTypeEntity creditCardType) {
		this.creditCardType = creditCardType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_payment_info_id")
	public AppUserPaymentInfoEntity getAppUserPaymentInfo() {
		return this.appUserPaymentInfo;
	}

	public void setAppUserPaymentInfo(AppUserPaymentInfoEntity appUserPaymentInfo) {
		this.appUserPaymentInfo = appUserPaymentInfo;
	}

	@Column(name = "first_name", length = 32)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "cvv", length = 4)
	public String getCvv() {
		return this.cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Column(name = "card_number", length = 20)
	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(name = "expiry_date", length = 23)
	public Timestamp getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "last_name", length = 32)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "is_default", length = 1)
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creditCardInfo")
	public Set<RideBillEntity> getRideBills() {
		return this.rideBills;
	}

	public void setRideBills(Set<RideBillEntity> rideBills) {
		this.rideBills = rideBills;
	}

	/**
	 * @return the btCustomerNo
	 */
	@Column(name = "bt_customer_no", length = 1000)
	public String getBtCustomerNo() {
		return btCustomerNo;
	}

	/**
	 * @param btCustomerNo the btCustomerNo to set
	 */
	public void setBtCustomerNo(String btCustomerNo) {
		this.btCustomerNo = btCustomerNo;
	}
}