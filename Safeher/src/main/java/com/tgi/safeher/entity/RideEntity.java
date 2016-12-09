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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * Ride entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride", schema = "dbo")
public class RideEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideId;
	private AppUserVehicleEntity appUserVehicle;
	private PreRideEntity preRide;
	private RideCriteriaEntity rideCriteriaEntity;
	private GiftTypeEntity giftType;
	private Integer rideTypeId;
	private String isCancel;
	private String isCompleted;
	private Timestamp startTime;
	private Timestamp endTime;
	private String isChange;
	private Timestamp rideStartTime;
	private String estimatedArrival;
	private Double estimatedDistance;
	private String estimatedDuration;
	private String rideNo;
	private Timestamp rideEndTime;
	private String isGifted;
	private String giftNo;
	private Set<RideDetailEntity> rideDetailEntities = new HashSet<RideDetailEntity>(0);
	private Set<PostRideEntity> postRideEntities = new HashSet<PostRideEntity>(0);
	private Set<RideBillEntity> rideBillEntities = new HashSet<RideBillEntity>(0);
	private Set<RideChangeRequestEntity> rideChangeRequestEntities = new HashSet<RideChangeRequestEntity>(
			0);
	private Set<RideModifyEntity> rideModifyEntities = new HashSet<RideModifyEntity>(0);

	// Constructors

	/** default constructor */
	public RideEntity() {
	}

	/** full constructor */
	public RideEntity(PreRideEntity preRideEntity, RideCriteriaEntity rideCriteriaEntity, Integer rideTypeId,
			String isCancel, String isCompleted, Timestamp startTime,
			Timestamp endTime, String isChange, Timestamp rideStartTime,
			String estimatedArrival, Double estimatedDistance,
			String estimatedDuration, String rideNo,
			Set<RideDetailEntity> rideDetailEntities, Set<PostRideEntity> postRideEntities,
			Set<RideBillEntity> rideBillEntities, Set<RideChangeRequestEntity> rideChangeRequestEntities,
			Set<RideModifyEntity> rideModifyEntities) {
		this.preRide = preRideEntity;
		this.rideCriteriaEntity = rideCriteriaEntity;
		this.rideTypeId = rideTypeId;
		this.isCancel = isCancel;
		this.isCompleted = isCompleted;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isChange = isChange;
		this.rideStartTime = rideStartTime;
		this.estimatedArrival = estimatedArrival;
		this.estimatedDistance = estimatedDistance;
		this.estimatedDuration = estimatedDuration;
		this.rideNo = rideNo;
		this.rideDetailEntities = rideDetailEntities;
		this.postRideEntities = postRideEntities;
		this.rideBillEntities = rideBillEntities;
		this.rideChangeRequestEntities = rideChangeRequestEntities;
		this.rideModifyEntities = rideModifyEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideId() {
		return this.rideId;
	}

	public void setRideId(Integer rideId) {
		this.rideId = rideId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pre_ride_id")
	public PreRideEntity getPreRide() {
		return this.preRide;
	}

	public void setPreRide(PreRideEntity preRideEntity) {
		this.preRide = preRideEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteriaEntity;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteriaEntity) {
		this.rideCriteriaEntity = rideCriteriaEntity;
	}

	@Column(name = "ride_type_id", precision = 9, scale = 0)
	public Integer getRideTypeId() {
		return this.rideTypeId;
	}

	public void setRideTypeId(Integer rideTypeId) {
		this.rideTypeId = rideTypeId;
	}

	@Column(name = "is_cancel", length = 1)
	public String getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	@Column(name = "is_completed", length = 1)
	public String getIsCompleted() {
		return this.isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Column(name = "start_time", length = 23)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 23)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "is_change", length = 1)
	public String getIsChange() {
		return this.isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	@Column(name = "ride_start_time", length = 23)
	public Timestamp getRideStartTime() {
		return this.rideStartTime;
	}

	public void setRideStartTime(Timestamp rideStartTime) {
		this.rideStartTime = rideStartTime;
	}

	@Column(name = "estimated_arrival", length = 18)
	public String getEstimatedArrival() {
		return this.estimatedArrival;
	}

	public void setEstimatedArrival(String estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}

	@Column(name = "estimated_distance", precision = 11, scale = 3)
	public Double getEstimatedDistance() {
		return this.estimatedDistance;
	}

	public void setEstimatedDistance(Double estimatedDistance) {
		this.estimatedDistance = estimatedDistance;
	}

	@Column(name = "estimated_duration", length = 20)
	public String getEstimatedDuration() {
		return this.estimatedDuration;
	}

	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	@Column(name = "ride_no", length = 32)
	public String getRideNo() {
		return this.rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

	@Column(name = "ride_end_time", length = 23)
	public Timestamp getRideEndTime() {
		return this.rideEndTime;
	}

	public void setRideEndTime(Timestamp rideEndTime) {
		this.rideEndTime = rideEndTime;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideEntityId")
	public Set<RideDetailEntity> getRideDetails() {
		return this.rideDetailEntities;
	}

	public void setRideDetails(Set<RideDetailEntity> rideDetailEntities) {
		this.rideDetailEntities = rideDetailEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideEntityId")
	public Set<PostRideEntity> getPostRides() {
		return this.postRideEntities;
	}

	public void setPostRides(Set<PostRideEntity> postRideEntities) {
		this.postRideEntities = postRideEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideInfoId")
	public Set<RideBillEntity> getRideBills() {
		return this.rideBillEntities;
	}

	public void setRideBills(Set<RideBillEntity> rideBillEntities) {
		this.rideBillEntities = rideBillEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ride")
	public Set<RideChangeRequestEntity> getRideChangeRequests() {
		return this.rideChangeRequestEntities;
	}

	public void setRideChangeRequests(Set<RideChangeRequestEntity> rideChangeRequestEntities) {
		this.rideChangeRequestEntities = rideChangeRequestEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ride")
	public Set<RideModifyEntity> getRideModifies() {
		return this.rideModifyEntities;
	}

	public void setRideModifies(Set<RideModifyEntity> rideModifyEntities) {
		this.rideModifyEntities = rideModifyEntities;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_vehicle_id")
	public AppUserVehicleEntity getAppUserVehicle() {
		return appUserVehicle;
	}

	public void setAppUserVehicle(AppUserVehicleEntity appUserVehicle) {
		this.appUserVehicle = appUserVehicle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gift_type_id")
	public GiftTypeEntity getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftTypeEntity giftType) {
		this.giftType = giftType;
	}

	@Column(name = "is_gifted", length = 1)
	public String getIsGifted() {
		return isGifted;
	}

	public void setIsGifted(String isGifted) {
		this.isGifted = isGifted;
	}
	
	@Column(name = "giftNo", length = 40)
	public String getGiftNo() {
		return giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

}