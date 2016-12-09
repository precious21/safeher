package com.tgi.safeher.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideFinalize entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_finalize", schema = "dbo")
public class RideFinalizeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideFinalizeId;
	private RideRequestResponseEntity rideRequestResponseEntity;
	private RideColorEntity rideColorEntity;
	private RideCriteriaEntity rideCriteriaEntity;
	private ReasonEntity reason;
	private AppUserEntity appUser;
	private Timestamp finalizeTime;
	private String isCancleByDriver;
	private String commentsCancle;
	private Timestamp timeCancle;
	private String isCancled;
	private String isCancleFined;
	private Double fineAmount;
	private String requestNo;
	private Set<PreRideEntity> preRideEntities = new HashSet<PreRideEntity>(0);
	private Set<RideCharityEntity> rideCharities = new HashSet<RideCharityEntity>(0);

	// Constructors

	/** default constructor */
	public RideFinalizeEntity() {
	}

	/** full constructor */
	public RideFinalizeEntity(RideRequestResponseEntity rideRequestResponseEntity,
			RideColorEntity rideColorEntity, RideCriteriaEntity rideCriteriaEntity,
			Timestamp finalizeTime, Set<PreRideEntity> preRideEntities) {
		this.rideRequestResponseEntity = rideRequestResponseEntity;
		this.rideColorEntity = rideColorEntity;
		this.rideCriteriaEntity = rideCriteriaEntity;
		this.finalizeTime = finalizeTime;
		this.preRideEntities = preRideEntities;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(generator = "rideFinalizeEntity_Id")
	@GenericGenerator( name = "rideFinalizeEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_finalize" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_finalize_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideFinalizeId() {
		return this.rideFinalizeId;
	}

	public void setRideFinalizeId(Integer rideFinalizeId) {
		this.rideFinalizeId = rideFinalizeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_req_res_id")
	public RideRequestResponseEntity getRideRequestResponse() {
		return this.rideRequestResponseEntity;
	}

	public void setRideRequestResponse(RideRequestResponseEntity rideRequestResponseEntity) {
		this.rideRequestResponseEntity = rideRequestResponseEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_color_id")
	public RideColorEntity getRideColor() {
		return this.rideColorEntity;
	}

	public void setRideColor(RideColorEntity rideColorEntity) {
		this.rideColorEntity = rideColorEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteriaEntity;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteriaEntity) {
		this.rideCriteriaEntity = rideCriteriaEntity;
	}

	@Column(name = "finalize_time", length = 23)
	public Timestamp getFinalizeTime() {
		return this.finalizeTime;
	}

	public void setFinalizeTime(Timestamp finalizeTime) {
		this.finalizeTime = finalizeTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideFinalize")
	public Set<PreRideEntity> getPreRides() {
		return this.preRideEntities;
	}

	public void setPreRides(Set<PreRideEntity> preRideEntities) {
		this.preRideEntities = preRideEntities;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_cancle")
	public ReasonEntity getReason() {
		return reason;
	}

	public void setReason(ReasonEntity reason) {
		this.reason = reason;
	}

	@Column(name = "is_cancle_by_driver", length = 1)
	public String getIsCancleByDriver() {
		return isCancleByDriver;
	}

	public void setIsCancleByDriver(String isCancleByDriver) {
		this.isCancleByDriver = isCancleByDriver;
	}

	@Column(name = "comments_cancle", length = 500)
	public String getCommentsCancle() {
		return commentsCancle;
	}

	public void setCommentsCancle(String commentsCancle) {
		this.commentsCancle = commentsCancle;
	}

	/**
	 * @return the timeCancle
	 */
	@Column(name = "time_cancle", length = 23)
	public Timestamp getTimeCancle() {
		return timeCancle;
	}

	/**
	 * @param timeCancle the timeCancle to set
	 */
	public void setTimeCancle(Timestamp timeCancle) {
		this.timeCancle = timeCancle;
	}

	/**
	 * @return the rideCharities
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideFinalize")
	public Set<RideCharityEntity> getRideCharities() {
		return rideCharities;
	}

	/**
	 * @param rideCharities the rideCharities to set
	 */
	public void setRideCharities(Set<RideCharityEntity> rideCharities) {
		this.rideCharities = rideCharities;
	}

	/**
	 * @return the fineAmount
	 */
	@Column(name = "fine_amount", precision = 6, scale = 3)
	public Double getFineAmount() {
		return fineAmount;
	}

	/**
	 * @param fineAmount the fineAmount to set
	 */
	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	/**
	 * @return the isCancleFined
	 */
	@Column(name = "is_cancle_fined", length = 1)
	public String getIsCancleFined() {
		return isCancleFined;
	}

	/**
	 * @param isCancleFined the isCancleFined to set
	 */
	public void setIsCancleFined(String isCancleFined) {
		this.isCancleFined = isCancleFined;
	}

	/**
	 * @return the isCancled
	 */
	@Column(name = "is_cancled", length = 1)
	public String getIsCancled() {
		return isCancled;
	}

	/**
	 * @param isCancled the isCancled to set
	 */
	public void setIsCancled(String isCancled) {
		this.isCancled = isCancled;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_driver")
	public AppUserEntity getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "request_no", length = 200)
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

}