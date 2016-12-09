package com.tgi.safeher.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.RideCategoryEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideSearchEntity;
import com.tgi.safeher.entity.SuburbEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideCriteriaBean implements Serializable {
	
	private String rideCriteriaId;
	private String suburbBySuburbDestination;
	private String suburbBySuburbSource;
	private String appUserId;
	private String rideTypeId;
	private String sourceLong;
	private String sourceLat;
	private String longitude;
	private String latitude;
	private String distinatonLong ;
	private String distinatonLat;
	private String isShared;
	private String isFav;
	private String noPassenger;
	private String noChild;
	private String noSeats;
	private String rideSearchId;
	private String suburbEntity;
	private String rideCategoryId;
	private String searchTime;
	private String searchFromLong;
	private String searchFromLat;
	private String isGotResult;
	private String isResearch;
	private String userImagePath;
	private String requestNo;
	private String charityId;
	private String isGifted;
	///////////////////////////////////
	private String reciverNum;
	private String firstIndex;
	private String maxResult;
	private String reciverAppUserId;
	private String reciverFirstName;
	private String reciverLastName;
	private String senderAppUserId;
	private String senderFirstName;
	private String senderLastName;
	private String giftNo;
	private String giftStatus;
	private String giftTime;
	private String giftId;
	private String giftType;
	private String giftTypeName;
	private String email;
	private String expiryDate;
	private String consumeDate;
	private String consumeAmount;
	private RideCriteriaEntity rideCriteriaWrapper = new RideCriteriaEntity();
	private RideSearchEntity rideSearchWrapper = new RideSearchEntity();
	private RideCriteriaBean giftRideWrapper;
	private List<RideCriteriaBean> consumeGifteRides;
	private List<RideCriteriaBean> avalibleGiftedRides;
	private List<RideCriteriaBean> givenGiftedRides;

	//////
	private String sourceAddress;
	private String distinatonAddress;
	
	////
	private String isDriver;
	private String rideNo;
	public String getDistinatonLong() {
		return distinatonLong;
	}
	public void setDistinatonLong(String distinatonLong) {
		this.distinatonLong = distinatonLong;
	}
	public String getDistinatonLat() {
		return distinatonLat;
	}
	public void setDistinatonLat(String distinatonLat) {
		this.distinatonLat = distinatonLat;
	}
	public String getRideCriteriaId() {
		return rideCriteriaId;
	}
	public void setRideCriteriaId(String rideCriteriaId) {
		this.rideCriteriaId = rideCriteriaId;
	}
	public String getRideSearchId() {
		return rideSearchId;
	}
	public void setRideSearchId(String rideSearchId) {
		this.rideSearchId = rideSearchId;
	}
	public String getSuburbEntity() {
		return suburbEntity;
	}
	public void setSuburbEntity(String suburbEntity) {
		this.suburbEntity = suburbEntity;
	}
	public String getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
	public String getSearchFromLong() {
		return searchFromLong;
	}
	public void setSearchFromLong(String searchFromLong) {
		this.searchFromLong = searchFromLong;
	}
	public String getSearchFromLat() {
		return searchFromLat;
	}
	public void setSearchFromLat(String searchFromLat) {
		this.searchFromLat = searchFromLat;
	}
	public String getIsGotResult() {
		return isGotResult;
	}
	public void setIsGotResult(String isGotResult) {
		this.isGotResult = isGotResult;
	}
	public String getIsResearch() {
		return isResearch;
	}
	public void setIsResearch(String isResearch) {
		this.isResearch = isResearch;
	}
	public String getSuburbBySuburbDestination() {
		return suburbBySuburbDestination;
	}
	public void setSuburbBySuburbDestination(String suburbBySuburbDestination) {
		this.suburbBySuburbDestination = suburbBySuburbDestination;
	}
	public String getSuburbBySuburbSource() {
		return suburbBySuburbSource;
	}
	public void setSuburbBySuburbSource(String suburbBySuburbSource) {
		this.suburbBySuburbSource = suburbBySuburbSource;
	}
	public String getRideTypeId() {
		return rideTypeId;
	}
	public void setRideTypeId(String rideTypeId) {
		this.rideTypeId = rideTypeId;
	}
	public String getSourceLong() {
		return sourceLong;
	}
	public void setSourceLong(String sourceLong) {
		this.sourceLong = sourceLong;
	}
	public String getSourceLat() {
		return sourceLat;
	}
	public void setSourceLat(String sourceLat) {
		this.sourceLat = sourceLat;
	}
	public String getIsShared() {
		return isShared;
	}
	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}
	public String getIsFav() {
		return isFav;
	}
	public void setIsFav(String isFav) {
		this.isFav = isFav;
	}
	public String getNoPassenger() {
		return noPassenger;
	}
	public void setNoPassenger(String noPassenger) {
		this.noPassenger = noPassenger;
	}
	public String getNoChild() {
		return noChild;
	}
	public void setNoChild(String noChild) {
		this.noChild = noChild;
	}
	public String getNoSeats() {
		return noSeats;
	}
	public void setNoSeats(String noSeats) {
		this.noSeats = noSeats;
	}
	public String getRideCategoryId() {
		return rideCategoryId;
	}
	public void setRideCategoryId(String rideCategoryId) {
		this.rideCategoryId = rideCategoryId;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getUserImagePath() {
		return userImagePath;
	}
	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}
	/**
	 * @return the requestNo
	 */
	public String getRequestNo() {
		return requestNo;
	}
	/**
	 * @param requestNo the requestNo to set
	 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public RideCriteriaEntity getRideCriteriaWrapper() {
		return rideCriteriaWrapper;
	}
	public void setRideCriteriaWrapper(RideCriteriaEntity rideCriteriaWrapper) {
		this.rideCriteriaWrapper = rideCriteriaWrapper;
	}
	public RideSearchEntity getRideSearchWrapper() {
		return rideSearchWrapper;
	}
	public void setRideSearchWrapper(RideSearchEntity rideSearchWrapper) {
		this.rideSearchWrapper = rideSearchWrapper;
	}
	public String getIsGifted() {
		return isGifted;
	}
	public void setIsGifted(String isGifted) {
		this.isGifted = isGifted;
	}
	public String getReciverNum() {
		return reciverNum;
	}
	public void setReciverNum(String reciverNum) {
		this.reciverNum = reciverNum;
	}
	public String getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(String firstIndex) {
		this.firstIndex = firstIndex;
	}
	public String getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(String maxResult) {
		this.maxResult = maxResult;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	public String getGiftStatus() {
		return giftStatus;
	}
	public void setGiftStatus(String giftStatus) {
		this.giftStatus = giftStatus;
	}
	public String getGiftTime() {
		return giftTime;
	}
	public void setGiftTime(String giftTime) {
		this.giftTime = giftTime;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getSenderAppUserId() {
		return senderAppUserId;
	}
	public void setSenderAppUserId(String senderAppUserId) {
		this.senderAppUserId = senderAppUserId;
	}
	public String getReciverAppUserId() {
		return reciverAppUserId;
	}
	public void setReciverAppUserId(String reciverAppUserId) {
		this.reciverAppUserId = reciverAppUserId;
	}
	public String getCharityId() {
		return charityId;
	}
	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}
	public String getDistinatonAddress() {
		return distinatonAddress;
	}
	public void setDistinatonAddress(String distinatonAddress) {
		this.distinatonAddress = distinatonAddress;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	@Override
	public String toString() {
		return "RideCriteriaBean [rideCriteriaId=" + rideCriteriaId
				+ ", appUserId=" + appUserId + ", rideTypeId=" + rideTypeId
				+ ", sourceLong=" + sourceLong + ", sourceLat=" + sourceLat
				+ ", distinatonLong=" + distinatonLong + ", distinatonLat="
				+ distinatonLat + ", isShared=" + isShared + ", isFav=" + isFav
				+ ", noPassenger=" + noPassenger + ", noChild=" + noChild
				+ ", noSeats=" + noSeats + ", rideSearchId=" + rideSearchId
				+ ", rideCategoryId=" + rideCategoryId + ", searchTime="
				+ searchTime + ", searchFromLong=" + searchFromLong
				+ ", searchFromLat=" + searchFromLat + ", isGotResult="
				+ isGotResult + ", isResearch=" + isResearch
				+ ", userImagePath=" + userImagePath + ", requestNo="
				+ requestNo + ", charityId=" + charityId + ", isGifted="
				+ isGifted + ", reciverNum=" + reciverNum + ", firstIndex="
				+ firstIndex + ", maxResult=" + maxResult
				+ ", reciverAppUserId=" + reciverAppUserId
				+ ", senderAppUserId=" + senderAppUserId + ", giftNo=" + giftNo
				+ ", giftStatus=" + giftStatus + ", giftTime=" + giftTime
				+ ", giftId=" + giftId + ", rideCriteriaWrapper="
				+ rideCriteriaWrapper + ", rideSearchWrapper="
				+ rideSearchWrapper + ", sourceAddress=" + sourceAddress
				+ ", distinatonAddress=" + distinatonAddress + "]";
	}
	public String getGiftTypeName() {
		return giftTypeName;
	}
	public void setGiftTypeName(String giftTypeName) {
		this.giftTypeName = giftTypeName;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	public String getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(String consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public String getConsumeDate() {
		return consumeDate;
	}
	public void setConsumeDate(String consumeDate) {
		this.consumeDate = consumeDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public RideCriteriaBean getGiftRideWrapper() {
		return giftRideWrapper;
	}
	public void setGiftRideWrapper(RideCriteriaBean giftRideWrapper) {
		this.giftRideWrapper = giftRideWrapper;
	}
	public List<RideCriteriaBean> getGivenGiftedRides() {
		return givenGiftedRides;
	}
	public void setGivenGiftedRides(List<RideCriteriaBean> givenGiftedRides) {
		this.givenGiftedRides = givenGiftedRides;
	}
	public List<RideCriteriaBean> getAvalibleGiftedRides() {
		return avalibleGiftedRides;
	}
	public void setAvalibleGiftedRides(List<RideCriteriaBean> avalibleGiftedRides) {
		this.avalibleGiftedRides = avalibleGiftedRides;
	}
	public List<RideCriteriaBean> getConsumeGifteRides() {
		return consumeGifteRides;
	}
	public void setConsumeGifteRides(List<RideCriteriaBean> consumeGifteRides) {
		this.consumeGifteRides = consumeGifteRides;
	}
	public String getSenderLastName() {
		return senderLastName;
	}
	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}
	public String getSenderFirstName() {
		return senderFirstName;
	}
	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getRideNo() {
		return rideNo;
	}
	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getReciverLastName() {
		return reciverLastName;
	}
	public void setReciverLastName(String reciverLastName) {
		this.reciverLastName = reciverLastName;
	}
	public String getReciverFirstName() {
		return reciverFirstName;
	}
	public void setReciverFirstName(String reciverFirstName) {
		this.reciverFirstName = reciverFirstName;
	}

	
}
