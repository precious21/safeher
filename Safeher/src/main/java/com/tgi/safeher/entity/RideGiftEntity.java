package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * RideGift entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_gift", schema = "dbo")
public class RideGiftEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideGiftId;
	private AppUserEntity appUserByReciverUserId;
	private RideCriteriaEntity rideCriteria;
	private StatusEntity status;
	private GiftTypeEntity giftType;
	private AppUserEntity appUserBySenderUserId;
	private Timestamp giftTime;
	private String reciverNum;
	private String giftNo;
	private String email;
	private Timestamp expiryDate;
	private Timestamp consumeDate;
	private Double consumeAmount;
	private String isActive;

	// Constructors

	/** default constructor */
	public RideGiftEntity() {
	}

	/** full constructor */
	public RideGiftEntity(AppUserEntity appUserByReciverUserId, RideCriteriaEntity rideCriteria,
			StatusEntity status, AppUserEntity appUserBySenderUserId, Timestamp giftTime,
			String reciverNum, String giftNo) {
		this.appUserByReciverUserId = appUserByReciverUserId;
		this.rideCriteria = rideCriteria;
		this.status = status;
		this.appUserBySenderUserId = appUserBySenderUserId;
		this.giftTime = giftTime;
		this.reciverNum = reciverNum;
		this.giftNo = giftNo;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_gift_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideGiftId() {
		return this.rideGiftId;
	}

	public void setRideGiftId(Integer rideGiftId) {
		this.rideGiftId = rideGiftId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reciver_user_id")
	public AppUserEntity getAppUserByReciverUserId() {
		return this.appUserByReciverUserId;
	}

	public void setAppUserByReciverUserId(AppUserEntity appUserByReciverUserId) {
		this.appUserByReciverUserId = appUserByReciverUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteria;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteria) {
		this.rideCriteria = rideCriteria;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_gift_ride")
	public StatusEntity getStatus() {
		return this.status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_user_id")
	public AppUserEntity getAppUserBySenderUserId() {
		return this.appUserBySenderUserId;
	}

	public void setAppUserBySenderUserId(AppUserEntity appUserBySenderUserId) {
		this.appUserBySenderUserId = appUserBySenderUserId;
	}

	@Column(name = "gift_time", length = 23)
	public Timestamp getGiftTime() {
		return this.giftTime;
	}

	public void setGiftTime(Timestamp giftTime) {
		this.giftTime = giftTime;
	}

	@Column(name = "reciver_num", length = 20)
	public String getReciverNum() {
		return this.reciverNum;
	}

	public void setReciverNum(String reciverNum) {
		this.reciverNum = reciverNum;
	}

	@Column(name = "gift_no", length = 20)
	public String getGiftNo() {
		return this.giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gift_type_id")
	public GiftTypeEntity getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftTypeEntity giftType) {
		this.giftType = giftType;
	}
	
	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "expiry_date", length = 23)
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	@Column(name = "consume_date", length = 23)
	public Timestamp getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(Timestamp consumeDate) {
		this.consumeDate = consumeDate;
	}

	
	@Column(name = "consume_amount", precision = 9, scale = 3)
	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}