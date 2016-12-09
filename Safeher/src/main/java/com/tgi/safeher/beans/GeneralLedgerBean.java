package com.tgi.safeher.beans;

import java.io.Serializable;

public class GeneralLedgerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7531495161983076711L;
	 private String generalLedgerId;
     private String ledgerEntryTypeId;
     private String rideBillId;
     private String statusPaymentId;
     private String statusTransId;
     private String paymentModeId;
     private String ridePaymnetDistribution;
     private String ledgerSummaryEntityId;
     private String amount;
     private String isCr;
     private String invoiceDate;
     private String paymentDate;
     private String isPartial;
     private String partialAmount;
     private String isReversed;
     private String isFineCharge;
     private String ledgerOwnerType;
     private String appUserId;
     private String charityId;
	public String getGeneralLedgerId() {
		return generalLedgerId;
	}
	public void setGeneralLedgerId(String generalLedgerId) {
		this.generalLedgerId = generalLedgerId;
	}
	public String getLedgerEntryTypeId() {
		return ledgerEntryTypeId;
	}
	public void setLedgerEntryTypeId(String ledgerEntryTypeId) {
		this.ledgerEntryTypeId = ledgerEntryTypeId;
	}
	public String getRideBillId() {
		return rideBillId;
	}
	public void setRideBillId(String rideBillId) {
		this.rideBillId = rideBillId;
	}
	public String getStatusPaymentId() {
		return statusPaymentId;
	}
	public void setStatusPaymentId(String statusPaymentId) {
		this.statusPaymentId = statusPaymentId;
	}
	public String getStatusTransId() {
		return statusTransId;
	}
	public void setStatusTransId(String statusTransId) {
		this.statusTransId = statusTransId;
	}
	public String getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(String paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public String getRidePaymnetDistribution() {
		return ridePaymnetDistribution;
	}
	public void setRidePaymnetDistribution(String ridePaymnetDistribution) {
		this.ridePaymnetDistribution = ridePaymnetDistribution;
	}
	public String getLedgerSummaryEntityId() {
		return ledgerSummaryEntityId;
	}
	public void setLedgerSummaryEntityId(String ledgerSummaryEntityId) {
		this.ledgerSummaryEntityId = ledgerSummaryEntityId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIsCr() {
		return isCr;
	}
	public void setIsCr(String isCr) {
		this.isCr = isCr;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getIsPartial() {
		return isPartial;
	}
	public void setIsPartial(String isPartial) {
		this.isPartial = isPartial;
	}
	public String getPartialAmount() {
		return partialAmount;
	}
	public void setPartialAmount(String partialAmount) {
		this.partialAmount = partialAmount;
	}
	public String getIsReversed() {
		return isReversed;
	}
	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}
	public String getIsFineCharge() {
		return isFineCharge;
	}
	public void setIsFineCharge(String isFineCharge) {
		this.isFineCharge = isFineCharge;
	}
	public String getLedgerOwnerType() {
		return ledgerOwnerType;
	}
	public void setLedgerOwnerType(String ledgerOwnerType) {
		this.ledgerOwnerType = ledgerOwnerType;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getCharityId() {
		return charityId;
	}
	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}
	@Override
	public String toString() {
		return "GeneralLedgerBean [generalLedgerId=" + generalLedgerId
				+ ", ledgerEntryTypeId=" + ledgerEntryTypeId + ", rideBillId="
				+ rideBillId + ", statusPaymentId=" + statusPaymentId
				+ ", statusTransId=" + statusTransId + ", paymentModeId="
				+ paymentModeId + ", ridePaymnetDistribution="
				+ ridePaymnetDistribution + ", ledgerSummaryEntityId="
				+ ledgerSummaryEntityId + ", amount=" + amount + ", isCr="
				+ isCr + ", invoiceDate=" + invoiceDate + ", paymentDate="
				+ paymentDate + ", isPartial=" + isPartial + ", partialAmount="
				+ partialAmount + ", isReversed=" + isReversed
				+ ", isFineCharge=" + isFineCharge + ", ledgerOwnerType="
				+ ledgerOwnerType + ", appUserId=" + appUserId + ", charityId="
				+ charityId + "]";
	}
     
}
