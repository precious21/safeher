package com.tgi.safeher.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.PromotionCodesEntity;
import com.tgi.safeher.entity.PromotionOfferToEntity;
import com.tgi.safeher.entity.PromotionType;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class PromAndReffBean implements Serializable{

	private static final long serialVersionUID = 129520604756921131L;
	private String promotionCodeId;
	private String codeValue;
	private String promotionInfoId;
	private String usedCount;
	private String isUsed;
	private String isPromotion;
	private String appUserId;
	private String isDriver;
	private String userPromotionId;
	private String promotionOfferToId;
	private String appUser;
	private String promotionTypeId;
	private String useStartDate;
	private String totalValue;
	private String useExpiryDate;
	private String lastUsedDate;
	private String totalUsedValue;
	private String isRefCreaterUser;
	private String durationInDays;
	private String startDate;
	private String expiryDate;
	private String amount;
	private String partialUseValue;
	private String isPartial;
	private String isCompleted;
	private String invoiceNo;
	private String appUserTypeId;
	private String promotionDescription;
    private String isPartialUse;
    private String partialValue;
    private String isSingle;
    private String isActive;
    private Integer amountValue;
    private String isPercentage;
    private String noOfRides;
	private List<PromAndReffBean> promotionList = new ArrayList<PromAndReffBean>();
	private List<PromAndReffBean> consumePromotionList = new ArrayList<PromAndReffBean>();
	private String maxUseCount;
	private String countValue;//for driver
	private String isCount;//for driver
	private String promDescription;
	private String refCreaterName;
	private String email;
	private String shareType;
	private String useCountValue;
	private List<AppUserBean> reffUserList = new ArrayList<AppUserBean>();
	
	
	
	public String getPartialUseValue() {
		return partialUseValue;
	}
	public void setPartialUseValue(String partialUseValue) {
		this.partialUseValue = partialUseValue;
	}
	public String getIsPartial() {
		return isPartial;
	}
	public void setIsPartial(String isPartial) {
		this.isPartial = isPartial;
	}
	public String getUserPromotionId() {
		return userPromotionId;
	}
	public void setUserPromotionId(String userPromotionId) {
		this.userPromotionId = userPromotionId;
	}
	public String getPromotionOfferToId() {
		return promotionOfferToId;
	}
	public void setPromotionOfferToId(String promotionOfferToId) {
		this.promotionOfferToId = promotionOfferToId;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	public String getPromotionTypeId() {
		return promotionTypeId;
	}
	public void setPromotionTypeId(String promotionTypeId) {
		this.promotionTypeId = promotionTypeId;
	}
	public String getUseStartDate() {
		return useStartDate;
	}
	public void setUseStartDate(String useStartDate) {
		this.useStartDate = useStartDate;
	}
	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	public String getUseExpiryDate() {
		return useExpiryDate;
	}
	public void setUseExpiryDate(String useExpiryDate) {
		this.useExpiryDate = useExpiryDate;
	}
	public String getLastUsedDate() {
		return lastUsedDate;
	}
	public void setLastUsedDate(String lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}
	public String getTotalUsedValue() {
		return totalUsedValue;
	}
	public void setTotalUsedValue(String totalUsedValue) {
		this.totalUsedValue = totalUsedValue;
	}
	public String getIsRefCreaterUser() {
		return isRefCreaterUser;
	}
	public void setIsRefCreaterUser(String isRefCreaterUser) {
		this.isRefCreaterUser = isRefCreaterUser;
	}
	public String getPromotionCodeId() {
		return promotionCodeId;
	}
	public void setPromotionCodeId(String promotionCodeId) {
		this.promotionCodeId = promotionCodeId;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getPromotionInfoId() {
		return promotionInfoId;
	}
	public void setPromotionInfoId(String promotionInfoId) {
		this.promotionInfoId = promotionInfoId;
	}
	public String getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(String usedCount) {
		this.usedCount = usedCount;
	}
	public String getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(String isPromotion) {
		this.isPromotion = isPromotion;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getDurationInDays() {
		return durationInDays;
	}
	public void setDurationInDays(String durationInDays) {
		this.durationInDays = durationInDays;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<AppUserBean> getReffUserList() {
		return reffUserList;
	}
	public void setReffUserList(List<AppUserBean> reffUserList) {
		this.reffUserList = reffUserList;
	}
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public List<PromAndReffBean> getPromotionList() {
		return promotionList;
	}
	public void setPromotionList(List<PromAndReffBean> promotionList) {
		this.promotionList = promotionList;
	}
	public String getAppUserTypeId() {
		return appUserTypeId;
	}
	public void setAppUserTypeId(String appUserTypeId) {
		this.appUserTypeId = appUserTypeId;
	}
	public String getPromotionDescription() {
		return promotionDescription;
	}
	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
	public String getIsPartialUse() {
		return isPartialUse;
	}
	public void setIsPartialUse(String isPartialUse) {
		this.isPartialUse = isPartialUse;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getAmountValue() {
		return amountValue;
	}
	public void setAmountValue(Integer amountValue) {
		this.amountValue = amountValue;
	}
	public String getIsPercentage() {
		return isPercentage;
	}
	public void setIsPercentage(String isPercentage) {
		this.isPercentage = isPercentage;
	}
	public List<PromAndReffBean> getConsumePromotionList() {
		return consumePromotionList;
	}
	public void setConsumePromotionList(List<PromAndReffBean> consumePromotionList) {
		this.consumePromotionList = consumePromotionList;
	}
	public String getNoOfRides() {
		return noOfRides;
	}
	public void setNoOfRides(String noOfRides) {
		this.noOfRides = noOfRides;
	}
	public String getIsSingle() {
		return isSingle;
	}
	public void setIsSingle(String isSingle) {
		this.isSingle = isSingle;
	}
	public String getMaxUseCount() {
		return maxUseCount;
	}
	public void setMaxUseCount(String maxUseCount) {
		this.maxUseCount = maxUseCount;
	}
	public String getCountValue() {
		return countValue;
	}
	public void setCountValue(String countValue) {
		this.countValue = countValue;
	}
	public String getIsCount() {
		return isCount;
	}
	public void setIsCount(String isCount) {
		this.isCount = isCount;
	}
	public String getPromDescription() {
		return promDescription;
	}
	public void setPromDescription(String promDescription) {
		this.promDescription = promDescription;
	}
	public String getPartialValue() {
		return partialValue;
	}
	public void setPartialValue(String partialValue) {
		this.partialValue = partialValue;
	}
	public String getRefCreaterName() {
		return refCreaterName;
	}
	public void setRefCreaterName(String refCreaterName) {
		this.refCreaterName = refCreaterName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getUseCountValue() {
		return useCountValue;
	}
	public void setUseCountValue(String useCountValue) {
		this.useCountValue = useCountValue;
	}
}
