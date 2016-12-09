package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class BankAccountInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9087168965852217779L;
	
	private String bankAccountInfoId;
	private String bankId;
	private String appUserPaymentInfoId;
	private String ibanNo;
	private String accountNo;
	private String accountTitle;
	private String location;
	private String routingNo;
	private String swiftCode;
	private String isActive;
	private String appUserId;
	private String zipCode;
	private String isDefault;
	private String isFromWindow;
	
	
	
	public String getBankAccountInfoId() {
		return bankAccountInfoId;
	}
	public void setBankAccountInfoId(String bankAccountInfoId) {
		this.bankAccountInfoId = bankAccountInfoId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAppUserPaymentInfoId() {
		return appUserPaymentInfoId;
	}
	public void setAppUserPaymentInfoId(String appUserPaymentInfoId) {
		this.appUserPaymentInfoId = appUserPaymentInfoId;
	}
	public String getIbanNo() {
		return ibanNo;
	}
	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountTitle() {
		return accountTitle;
	}
	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRoutingNo() {
		return routingNo;
	}
	public void setRoutingNo(String routingNo) {
		this.routingNo = routingNo;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "BankAccountInfoBean [bankAccountInfoId=" + bankAccountInfoId
				+ ", bankId=" + bankId + ", appUserPaymentInfoId="
				+ appUserPaymentInfoId + ", ibanNo=" + ibanNo + ", accountNo="
				+ accountNo + ", accountTitle=" + accountTitle + ", location="
				+ location + ", routingNo=" + routingNo + ", swiftCode="
				+ swiftCode + ", isActive=" + isActive + ", appUserId="
				+ appUserId + ", zipCode=" + zipCode + ", isDefault="
				+ isDefault + "]";
	}
	public String getIsFromWindow() {
		return isFromWindow;
	}
	public void setIsFromWindow(String isFromWindow) {
		this.isFromWindow = isFromWindow;
	}
}
