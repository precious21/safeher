package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Ahtesham Asif
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class PayPalBean  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paypalInfoId;
	private String appUserPaymentInfo;
	private String providedDate;
	private String isActive;
	private String appUserId;
	public String getPaypalInfoId() {
		return paypalInfoId;
	}
	public void setPaypalInfoId(String paypalInfoId) {
		this.paypalInfoId = paypalInfoId;
	}
	public String getAppUserPaymentInfo() {
		return appUserPaymentInfo;
	}
	public void setAppUserPaymentInfo(String appUserPaymentInfo) {
		this.appUserPaymentInfo = appUserPaymentInfo;
	}
	public String getProvidedDate() {
		return providedDate;
	}
	public void setProvidedDate(String providedDate) {
		this.providedDate = providedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the appUserId
	 */
	public String getAppUserId() {
		return appUserId;
	}
	/**
	 * @param appUserId the appUserId to set
	 */
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	@Override
	public String toString() {
		return "PayPalBean [paypalInfoId=" + paypalInfoId
				+ ", appUserPaymentInfo=" + appUserPaymentInfo
				+ ", providedDate=" + providedDate + ", isActive=" + isActive
				+ ", appUserId=" + appUserId + "]";
	}
	

}
