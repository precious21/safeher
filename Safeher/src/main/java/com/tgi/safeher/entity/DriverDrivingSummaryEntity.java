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
 * DriverDrivingSummary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_driving_summary", schema = "dbo")
public class DriverDrivingSummaryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer driverDrivingSummaryId;
	private AppUserEntity appUser;
	private String totalOnlineTime;
	private String totalOnlineDistance;
	private String totalPreRideTime;
	private String totalPreRideDistance;
	private String totalRideTime;
	private String totalRideDistance;
	private Timestamp modifiedTime;
	private String distanceUnit;
	private String totalRides;
	private String driverEarning;
	private String totalEarning;
	private String disputeAmount;
	private String totalAcceptedRequest;
	private String totalCancelPreRides;
	private String totalRequests;
	// Constructors

	/** default constructor */
	public DriverDrivingSummaryEntity() {
	}

	/** full constructor */
	public DriverDrivingSummaryEntity(DriverDrivingDetailEntity driverDrivingDetail,
			AppUserEntity appUser, String totalOnlineTime,
			String totalOnlineDistance, String totalPreRideTime,
			String totalPreRideDistance, String totalRideTime,
			String totalRideDistance, Timestamp modifiedTime,
			String distanceUnit) {
		this.appUser = appUser;
		this.totalOnlineTime = totalOnlineTime;
		this.totalOnlineDistance = totalOnlineDistance;
		this.totalPreRideTime = totalPreRideTime;
		this.totalPreRideDistance = totalPreRideDistance;
		this.totalRideTime = totalRideTime;
		this.totalRideDistance = totalRideDistance;
		this.modifiedTime = modifiedTime;
		this.distanceUnit = distanceUnit;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_driving_summary_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverDrivingSummaryId() {
		return this.driverDrivingSummaryId;
	}

	public void setDriverDrivingSummaryId(Integer driverDrivingSummaryId) {
		this.driverDrivingSummaryId = driverDrivingSummaryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "total_online_time", length = 30)
	public String getTotalOnlineTime() {
		return this.totalOnlineTime;
	}

	public void setTotalOnlineTime(String totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	@Column(name = "total_online_distance", length = 20)
	public String getTotalOnlineDistance() {
		return this.totalOnlineDistance;
	}

	public void setTotalOnlineDistance(String totalOnlineDistance) {
		this.totalOnlineDistance = totalOnlineDistance;
	}

	@Column(name = "total_pre_ride_time", length = 30)
	public String getTotalPreRideTime() {
		return this.totalPreRideTime;
	}

	public void setTotalPreRideTime(String totalPreRideTime) {
		this.totalPreRideTime = totalPreRideTime;
	}

	@Column(name = "total_pre_ride_distance", length = 20)
	public String getTotalPreRideDistance() {
		return this.totalPreRideDistance;
	}

	public void setTotalPreRideDistance(String totalPreRideDistance) {
		this.totalPreRideDistance = totalPreRideDistance;
	}

	@Column(name = "total_ride_time", length = 30)
	public String getTotalRideTime() {
		return this.totalRideTime;
	}

	public void setTotalRideTime(String totalRideTime) {
		this.totalRideTime = totalRideTime;
	}

	@Column(name = "total_ride_distance", length = 20)
	public String getTotalRideDistance() {
		return this.totalRideDistance;
	}

	public void setTotalRideDistance(String totalRideDistance) {
		this.totalRideDistance = totalRideDistance;
	}

	@Column(name = "modified_time", length = 23)
	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Column(name = "distance_unit", length = 20)
	public String getDistanceUnit() {
		return this.distanceUnit;
	}

	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	@Column(name = "total_rides", length = 20)
	public String getTotalRides() {
		return this.totalRides;
	}

	public void setTotalRides(String totalRides) {
		this.totalRides = totalRides;
	}

	@Column(name = "driver_earning", length = 20)
	public String getDriverEarning() {
		return this.driverEarning;
	}

	public void setDriverEarning(String driverEarning) {
		this.driverEarning = driverEarning;
	}

	@Column(name = "total_earning", length = 20)
	public String getTotalEarning() {
		return this.totalEarning;
	}

	public void setTotalEarning(String totalEarning) {
		this.totalEarning = totalEarning;
	}

	@Column(name = "dispute_amount", length = 20)
	public String getDisputeAmount() {
		return this.disputeAmount;
	}

	public void setDisputeAmount(String disputeAmount) {
		this.disputeAmount = disputeAmount;
	}

	@Column(name = "total_accepted_request", length = 20)
	public String getTotalAcceptedRequest() {
		return this.totalAcceptedRequest;
	}

	public void setTotalAcceptedRequest(String totalAcceptedRequest) {
		this.totalAcceptedRequest = totalAcceptedRequest;
	}

	@Column(name = "total_cancel_pre_rides", length = 20)
	public String getTotalCancelPreRides() {
		return this.totalCancelPreRides;
	}

	public void setTotalCancelPreRides(String totalCancelPreRides) {
		this.totalCancelPreRides = totalCancelPreRides;
	}

	@Column(name = "total_requests", length = 20)
	public String getTotalRequests() {
		return this.totalRequests;
	}

	public void setTotalRequests(String totalRequests) {
		this.totalRequests = totalRequests;
	}

}