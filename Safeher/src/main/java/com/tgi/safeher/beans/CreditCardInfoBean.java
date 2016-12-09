package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.CreditCardTypeEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class CreditCardInfoBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8254280203207413457L;
	private	String firstName;
	private String lastName;
	private String creditCardNo;
	private String creditCardTypeId;
	private String expiryDate;
	private String isActive;
	private String appUserId;
	private String appPaymentUserId;
	private String isBankAccount;
	private String isCard;
	private String isPaypal;
	private String cvv;
	private String creditCardInfoId;
	private String isDefault;
	private String nounce; 
	private String appUserPaymentInfoId;
	private String defaultType;
	private String paymentClientToken;
	private String btCustomer;
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	List<CreditCardInfoBean> creaditCardList;
	
	public List<CreditCardInfoBean> getCreaditCardList() {
		return creaditCardList;
	}
	public void setCreaditCardList(List<CreditCardInfoBean> creaditCardList) {
		this.creaditCardList = creaditCardList;
	}
	public String getCreditCardInfoId() {
		return creditCardInfoId;
	}
	public void setCreditCardInfoId(String creditCardInfoId) {
		this.creditCardInfoId = creditCardInfoId;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getCreditCardTypeId() {
		return creditCardTypeId;
	}
	public void setCreditCardTypeId(String creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
	}
	public String getAppPaymentUserId() {
		return appPaymentUserId;
	}
	public void setAppPaymentUserId(String appPaymentUserId) {
		this.appPaymentUserId = appPaymentUserId;
	}
	public String getIsBankAccount() {
		return isBankAccount;
	}
	public void setIsBankAccount(String isBankAccount) {
		this.isBankAccount = isBankAccount;
	}
	public String getIsCard() {
		return isCard;
	}
	public void setIsCard(String isCard) {
		this.isCard = isCard;
	}
	public String getIsPaypal() {
		return isPaypal;
	}
	public void setIsPaypal(String isPaypal) {
		this.isPaypal = isPaypal;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	public String getAppUserPaymentInfoId() {
		return appUserPaymentInfoId;
	}
	public void setAppUserPaymentInfoId(String appUserPaymentInfoId) {
		this.appUserPaymentInfoId = appUserPaymentInfoId;
	}
	public String getPaymentClientToken() {
		return paymentClientToken;
	}
	public void setPaymentClientToken(String paymentClientToken) {
		this.paymentClientToken = paymentClientToken;
	}
	public String getNounce() {
		return nounce;
	}
	public void setNounce(String nounce) {
		this.nounce = nounce;
	}
	public String getBtCustomer() {
		return btCustomer;
	}
	public void setBtCustomer(String btCustomer) {
		this.btCustomer = btCustomer;
	}
	@Override
	public String toString() {
		return "CreditCardInfoBean [firstName=" + firstName + ", lastName="
				+ lastName + ", creditCardNo=" + creditCardNo
				+ ", creditCardTypeId=" + creditCardTypeId + ", expiryDate="
				+ expiryDate + ", isActive=" + isActive + ", appUserId="
				+ appUserId + ", appPaymentUserId=" + appPaymentUserId
				+ ", isBankAccount=" + isBankAccount + ", isCard=" + isCard
				+ ", isPaypal=" + isPaypal + ", cvv=" + cvv
				+ ", creditCardInfoId=" + creditCardInfoId + ", isDefault="
				+ isDefault + ", nounce=" + nounce + ", appUserPaymentInfoId="
				+ appUserPaymentInfoId + ", defaultType=" + defaultType
				+ ", paymentClientToken=" + paymentClientToken
				+ ", btCustomer=" + btCustomer + ", creaditCardList="
				+ creaditCardList + "]";
	}
		
}
