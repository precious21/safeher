package com.tgi.safeher.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tgi.safeher.entity.AppUserEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class RideBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7195081309264591006L;
	private String rideId;
	private String preRideId;
	private String rideCriteriaId;
	private String rideTypeId;
	private String isCancel;
	private String isCompleted;
	private String startTime;
	private String endTime;
	private String isChange;
	private String rideStartTime;
	private String estimatedArrival;
	private String estimatedDistance;
	private String estimatedDuration;
	private String rideNo;
	private String totalRides;
	private String driverEarning;
	private String rideDetailId;
	private String reasonByReasonCancel;
	private String reasonByReasonPartial;
	private String sourceLong;
	private String sourceLat;
	private String destinationLat;
	private String destinationLong;
	private String actualSeats;
	private String actualChild;
	private String actualPerson;
	private String actualArrival;
	private String isModified;
	private String actualDistance;
	private String actualDuration;
	private String idelTime;
	private String rideEndTime;
	private String rideBillId;
	private String appUserByAppUserPassenger;
	private String appUserByAppUserDriver;
	private String appUserByAppUserDonar;
	private String creditCardInfo;
	private String fineAmount;
	private String rideAmount;
	private String tipAmount;
	private String invoiceNo;
	private String paymentTime;
	private String totalAmount;
	private String clientToken;
	private String nonce;
	private String charityId;
	private String charityName;
	private String charityAmount;
	private String driverAmount;
	private String companyAmount;
	private String todayDuration;
	private String todayShare;
	private String lastDuration="0";
	private String lastShare="0.0";
	private String weatherType;
	private String currentDemand;
	private String currentAddress;
	private String rideTotalAmount;
	private String totalDuration;
	private String totalDistance;
	private String totalTipAmount;
	private String paymentModeName;
	private String paymentModeId;
	private String status;
	private String firstIndex;
	private String maxResult;
	private List<RideBean> rideList;
	private String lastName;
	private String firstName;
	private String ppType;//cardType
	private String ppCN;//cardNo
	private String ppCE;//cardExpiry
	private String ppT;//clientToken
	private String ppN;//cardNonce
	private String ppC;//cardCVV
	private String toEmail;
	private String passengerEmail;
	private String driverEmail;
	private String isGifted;
	private String donarPassengerId;
	private String sourceAddress;
	private String distinationAddress;
	private String driverFirstName;
	private String driverLastName;
	private String passengerFirstName;
	private String passengerLastName;
	private String userPicUrl;
	private String vehicleModel;
	private String vehicleMake;
	private String vehicleNum;
	private String userRating;
	private String requestNo;
	private String isBillComplete;
	private String isPreRideComplete;
	private String appUserId;
	private String isDriver;
	private String requestCode;
	private String driverOnTime;
	private String discount;
	private String userIntervalRating;
	private String receiverId;
	private String notificationType;
	private String notificationMessage;
	private String totalDiscount;
	private String paymentDone;
	private String giftType;
	private String giftNo;
	private String passengerType;
	private Timestamp timeStampStartTime;
	private Timestamp timeStampEndTime;
	private String isRatingDone;
	
	
	/////////////////////
	private String endDistinationAddress;

	private String endDestinationLat;
	private String endDestinationLong;
	
	private String startSourceAddress;

	private String startSourceLong;
	private String startSourceLat;
	
	/////////////////
	private String giftAmount;

	
	
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getEndDistinationAddress() {
		return endDistinationAddress;
	}
	public void setEndDistinationAddress(String endDistinationAddress) {
		this.endDistinationAddress = endDistinationAddress;
	}
	
	
	public String getEndDestinationLat() {
		return endDestinationLat;
	}
	public void setEndDestinationLat(String endDestinationLat) {
		this.endDestinationLat = endDestinationLat;
	}
	public String getEndDestinationLong() {
		return endDestinationLong;
	}
	public void setEndDestinationLong(String endDestinationLong) {
		this.endDestinationLong = endDestinationLong;
	}
	public String getStartSourceAddress() {
		return startSourceAddress;
	}
	public void setStartSourceAddress(String startSourceAddress) {
		this.startSourceAddress = startSourceAddress;
	}
	
	public String getStartSourceLong() {
		return startSourceLong;
	}
	public void setStartSourceLong(String startSourceLong) {
		this.startSourceLong = startSourceLong;
	}
	public String getStartSourceLat() {
		return startSourceLat;
	}
	public void setStartSourceLat(String startSourceLat) {
		this.startSourceLat = startSourceLat;
	}
	
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getRideBillId() {
		return rideBillId;
	}
	public void setRideBillId(String rideBillId) {
		this.rideBillId = rideBillId;
	}
	public String getAppUserByAppUserPassenger() {
		return appUserByAppUserPassenger;
	}
	public void setAppUserByAppUserPassenger(String appUserByAppUserPassenger) {
		this.appUserByAppUserPassenger = appUserByAppUserPassenger;
	}
	public String getAppUserByAppUserDriver() {
		return appUserByAppUserDriver;
	}
	public void setAppUserByAppUserDriver(String appUserByAppUserDriver) {
		this.appUserByAppUserDriver = appUserByAppUserDriver;
	}
	public String getCreditCardInfo() {
		return creditCardInfo;
	}
	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}
	public String getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}
	public String getRideAmount() {
		return rideAmount;
	}
	public void setRideAmount(String rideAmount) {
		this.rideAmount = rideAmount;
	}
	public String getTipAmount() {
		return tipAmount;
	}
	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getActualArrival() {
		return actualArrival;
	}
	public void setActualArrival(String actualArrival) {
		this.actualArrival = actualArrival;
	}
	public String getIsModified() {
		return isModified;
	}
	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}
	public String getActualDistance() {
		return actualDistance;
	}
	public void setActualDistance(String actualDistance) {
		this.actualDistance = actualDistance;
	}
	public String getActualDuration() {
		return actualDuration;
	}
	public void setActualDuration(String actualDuration) {
		this.actualDuration = actualDuration;
	}
	public String getIdelTime() {
		return idelTime;
	}
	public void setIdelTime(String idelTime) {
		this.idelTime = idelTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRideDetailId() {
		return rideDetailId;
	}
	public void setRideDetailId(String rideDetailId) {
		this.rideDetailId = rideDetailId;
	}
	public String getReasonByReasonCancel() {
		return reasonByReasonCancel;
	}
	public void setReasonByReasonCancel(String reasonByReasonCancel) {
		this.reasonByReasonCancel = reasonByReasonCancel;
	}
	public String getReasonByReasonPartial() {
		return reasonByReasonPartial;
	}
	public void setReasonByReasonPartial(String reasonByReasonPartial) {
		this.reasonByReasonPartial = reasonByReasonPartial;
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
	public String getDestinationLat() {
		return destinationLat;
	}
	public void setDestinationLat(String destinationLat) {
		this.destinationLat = destinationLat;
	}
	public String getDestinationLong() {
		return destinationLong;
	}
	public void setDestinationLong(String destinationLong) {
		this.destinationLong = destinationLong;
	}
	public String getActualSeats() {
		return actualSeats;
	}
	public void setActualSeats(String actualSeats) {
		this.actualSeats = actualSeats;
	}
	public String getActualChild() {
		return actualChild;
	}
	public void setActualChild(String actualChild) {
		this.actualChild = actualChild;
	}
	public String getActualPerson() {
		return actualPerson;
	}
	public void setActualPerson(String actualPerson) {
		this.actualPerson = actualPerson;
	}
	public String getRideId() {
		return rideId;
	}
	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
	public String getRideTypeId() {
		return rideTypeId;
	}
	public void setRideTypeId(String rideTypeId) {
		this.rideTypeId = rideTypeId;
	}
	public String getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsChange() {
		return isChange;
	}
	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	public String getRideStartTime() {
		return rideStartTime;
	}
	public void setRideStartTime(String rideStartTime) {
		this.rideStartTime = rideStartTime;
	}
	public String getEstimatedArrival() {
		return estimatedArrival;
	}
	public void setEstimatedArrival(String estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}
	public String getEstimatedDistance() {
		return estimatedDistance;
	}
	public void setEstimatedDistance(String estimatedDistance) {
		this.estimatedDistance = estimatedDistance;
	}
	public String getEstimatedDuration() {
		return estimatedDuration;
	}
	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}
	public String getRideNo() {
		return rideNo;
	}
	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}
	/**
	 * @return the rideCriteriaId
	 */
	public String getRideCriteriaId() {
		return rideCriteriaId;
	}
	/**
	 * @param rideCriteriaId the rideCriteriaId to set
	 */
	public void setRideCriteriaId(String rideCriteriaId) {
		this.rideCriteriaId = rideCriteriaId;
	}
	/**
	 * @return the preRideId
	 */
	public String getPreRideId() {
		return preRideId;
	}
	/**
	 * @param preRideId the preRideId to set
	 */
	public void setPreRideId(String preRideId) {
		this.preRideId = preRideId;
	}
	/**
	 * @return the rideEndTime
	 */
	public String getRideEndTime() {
		return rideEndTime;
	}
	/**
	 * @param rideEndTime the rideEndTime to set
	 */
	public void setRideEndTime(String rideEndTime) {
		this.rideEndTime = rideEndTime;
	}
	/**
	 * @return the clientToken
	 */
	public String getClientToken() {
		return clientToken;
	}
	/**
	 * @param clientToken the clientToken to set
	 */
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getCharityId() {
		return charityId;
	}
	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}
	public String getTodayDuration() {
		return todayDuration;
	}
	public void setTodayDuration(String todayDuration) {
		this.todayDuration = todayDuration;
	}
	public String getTodayShare() {
		return todayShare;
	}
	public void setTodayShare(String todayShare) {
		this.todayShare = todayShare;
	}
	public String getLastDuration() {
		return lastDuration;
	}
	public void setLastDuration(String lastDuration) {
		this.lastDuration = lastDuration;
	}
	public String getLastShare() {
		return lastShare;
	}
	public void setLastShare(String lastShare) {
		this.lastShare = lastShare;
	}
	public String getWeatherType() {
		return weatherType;
	}
	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}
	public String getCurrentDemand() {
		return currentDemand;
	}
	public void setCurrentDemand(String currentDemand) {
		this.currentDemand = currentDemand;
	}
	public String getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getRideTotalAmount() {
		return rideTotalAmount;
	}
	public void setRideTotalAmount(String rideTotalAmount) {
		this.rideTotalAmount = rideTotalAmount;
	}
	public String getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
	public String getTotalTipAmount() {
		return totalTipAmount;
	}
	public void setTotalTipAmount(String totalTipAmount) {
		this.totalTipAmount = totalTipAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(String paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public String getPaymentModeName() {
		return paymentModeName;
	}
	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}
	public List<RideBean> getRideList() {
		return rideList;
	}
	public void setRideList(List<RideBean> rideList) {
		this.rideList = rideList;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPpType() {
		return ppType;
	}
	public void setPpType(String ppType) {
		this.ppType = ppType;
	}
	public String getPpCN() {
		return ppCN;
	}
	public void setPpCN(String ppCN) {
		this.ppCN = ppCN;
	}
	public String getPpCE() {
		return ppCE;
	}
	public void setPpCE(String ppCE) {
		this.ppCE = ppCE;
	}
	public String getPpT() {
		return ppT;
	}
	public void setPpT(String ppT) {
		this.ppT = ppT;
	}
	public String getPpN() {
		return ppN;
	}
	public void setPpN(String ppN) {
		this.ppN = ppN;
	}
	public String getPpC() {
		return ppC;
	}
	public void setPpC(String ppC) {
		this.ppC = ppC;
	}
	public String getDonarPassengerId() {
		return donarPassengerId;
	}
	public void setDonarPassengerId(String donarPassengerId) {
		this.donarPassengerId = donarPassengerId;
	}
	public String getIsGifted() {
		return isGifted;
	}
	public void setIsGifted(String isGifted) {
		this.isGifted = isGifted;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getDistinationAddress() {
		return distinationAddress;
	}
	public void setDistinationAddress(String distinationAddress) {
		this.distinationAddress = distinationAddress;
	}
	public String getDriverLastName() {
		return driverLastName;
	}
	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}
	public String getDriverFirstName() {
		return driverFirstName;
	}
	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}
	public String getUserPisUrl() {
		return userPicUrl;
	}
	public void setUserPisUrl(String userPisUrl) {
		this.userPicUrl = userPisUrl;
	}
public String getCharityAmount() {
		return charityAmount;
	}
	public void setCharityAmount(String charityAmount) {
		this.charityAmount = charityAmount;
	}
	public String getDriverAmount() {
		return driverAmount;
	}
	public void setDriverAmount(String driverAmount) {
		this.driverAmount = driverAmount;
	}
	public String getCompanyAmount() {
		return companyAmount;
	}
	public void setCompanyAmount(String companyAmount) {
		this.companyAmount = companyAmount;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleNum() {
		return vehicleNum;
	}
	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}
	public String getUserRating() {
		return userRating;
	}
	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getIsBillComplete() {
		return isBillComplete;
	}
	public void setIsBillComplete(String isBillComplete) {
		this.isBillComplete = isBillComplete;
	}
	public String getIsPreRideComplete() {
		return isPreRideComplete;
	}
	public void setIsPreRideComplete(String isPreRideComplete) {
		this.isPreRideComplete = isPreRideComplete;
	}
	@Override
	public String toString() {
		return "RideBean [rideId=" + rideId + ", preRideId=" + preRideId
				+ ", rideCriteriaId=" + rideCriteriaId + ", rideTypeId="
				+ rideTypeId + ", isCancel=" + isCancel + ", isCompleted="
				+ isCompleted + ", startTime=" + startTime + ", endTime="
				+ endTime + ", isChange=" + isChange + ", rideStartTime="
				+ rideStartTime + ", estimatedArrival=" + estimatedArrival
				+ ", estimatedDistance=" + estimatedDistance
				+ ", estimatedDuration=" + estimatedDuration + ", rideNo="
				+ rideNo + ", rideDetailId=" + rideDetailId
				+ ", reasonByReasonCancel=" + reasonByReasonCancel
				+ ", reasonByReasonPartial=" + reasonByReasonPartial
				+ ", sourceLong=" + sourceLong + ", sourceLat=" + sourceLat
				+ ", destinationLat=" + destinationLat + ", destinationLong="
				+ destinationLong + ", actualSeats=" + actualSeats
				+ ", actualChild=" + actualChild + ", actualPerson="
				+ actualPerson + ", actualArrival=" + actualArrival
				+ ", isModified=" + isModified + ", actualDistance="
				+ actualDistance + ", actualDuration=" + actualDuration
				+ ", idelTime=" + idelTime + ", rideEndTime=" + rideEndTime
				+ ", rideBillId=" + rideBillId + ", appUserByAppUserPassenger="
				+ appUserByAppUserPassenger + ", appUserByAppUserDriver="
				+ appUserByAppUserDriver + ", creditCardInfo=" + creditCardInfo
				+ ", fineAmount=" + fineAmount + ", rideAmount=" + rideAmount
				+ ", tipAmount=" + tipAmount + ", invoiceNo=" + invoiceNo
				+ ", paymentTime=" + paymentTime + ", totalAmount="
				+ totalAmount + ", clientToken=" + clientToken + ", nonce="
				+ nonce + ", charityId=" + charityId + ", charityAmount="
				+ charityAmount + ", driverAmount=" + driverAmount
				+ ", companyAmount=" + companyAmount + ", todayDuration="
				+ todayDuration + ", todayShare=" + todayShare
				+ ", lastDuration=" + lastDuration + ", lastShare=" + lastShare
				+ ", weatherType=" + weatherType + ", currentDemand="
				+ currentDemand + ", rideTotalAmount=" + rideTotalAmount
				+ ", totalDuration=" + totalDuration + ", totalDistance="
				+ totalDistance + ", totalTipAmount=" + totalTipAmount
				+ ", paymentModeName=" + paymentModeName + ", paymentModeId="
				+ paymentModeId + ", status=" + status + ", firstIndex="
				+ firstIndex + ", maxResult=" + maxResult + ", rideList="
				+ rideList + ", lastName=" + lastName + ", firstName="
				+ firstName + ", ppType=" + ppType + ", ppCN=" + ppCN
				+ ", ppCE=" + ppCE + ", ppT=" + ppT + ", ppN=" + ppN + ", ppC="
				+ ppC + ", toEmail=" + toEmail + ", isGifted=" + isGifted
				+ ", donarPassengerId=" + donarPassengerId + ", sourceAddress="
				+ sourceAddress + ", distinationAddress=" + distinationAddress
				+ ", driverFirstName=" + driverFirstName + ", driverLastName="
				+ driverLastName + ", userPicUrl=" + userPicUrl
				+ ", vehicleModel=" + vehicleModel + ", vehicleMake="
				+ vehicleMake + ", vehicleNum=" + vehicleNum + ", userRating="
				+ userRating + "]";
	}
	public String getTotalRides() {
		return totalRides;
	}
	public void setTotalRides(String totalRides) {
		this.totalRides = totalRides;
	}
	public String getPassengerEmail() {
		return passengerEmail;
	}
	public void setPassengerEmail(String passengerEmail) {
		this.passengerEmail = passengerEmail;
	}
	public String getDriverEmail() {
		return driverEmail;
	}
	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}
	public String getPassengerFirstName() {
		return passengerFirstName;
	}
	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}
	public String getPassengerLastName() {
		return passengerLastName;
	}
	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}
	public String getDriverEarning() {
		return driverEarning;
	}
	public void setDriverEarning(String driverEarning) {
		this.driverEarning = driverEarning;
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
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public String getDriverOnTime() {
		return driverOnTime;
	}
	public void setDriverOnTime(String driverOnTime) {
		this.driverOnTime = driverOnTime;
	}
	public String getUserIntervalRating() {
		return userIntervalRating;
	}
	public void setUserIntervalRating(String userIntervalRating) {
		this.userIntervalRating = userIntervalRating;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	public String getAppUserByAppUserDonar() {
		return appUserByAppUserDonar;
	}
	public void setAppUserByAppUserDonar(String appUserByAppUserDonar) {
		this.appUserByAppUserDonar = appUserByAppUserDonar;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getPaymentDone() {
		return paymentDone;
	}
	public void setPaymentDone(String paymentDone) {
		this.paymentDone = paymentDone;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(String giftAmount) {
		this.giftAmount = giftAmount;
	}
	public String getCharityName() {
		return charityName;
	}
	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}
	public Timestamp getTimeStampStartTime() {
		return timeStampStartTime;
	}
	public void setTimeStampStartTime(Timestamp timeStampStartTime) {
		this.timeStampStartTime = timeStampStartTime;
	}
	public Timestamp getTimeStampEndTime() {
		return timeStampEndTime;
	}
	public void setTimeStampEndTime(Timestamp timeStampEndTime) {
		this.timeStampEndTime = timeStampEndTime;
	}
	public String getIsRatingDone() {
		return isRatingDone;
	}
	public void setIsRatingDone(String isRatingDone) {
		this.isRatingDone = isRatingDone;
	}
	
}
