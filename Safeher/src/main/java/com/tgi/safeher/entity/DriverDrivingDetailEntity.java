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
 * DriverDrivingDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_driving_detail", schema = "dbo")
public class DriverDrivingDetailEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer driverDrivingDetailId;
	private FrequencyEntity frequency;
	private DriverDrivingSummaryEntity driverDrivingSummary;
	private Timestamp startDate;
	private String totalOnlineTime;
	private String totalOnlineDistance;
	private String totalPreRideTime;
	private String totalPreRideDistance;
	private String totalRideTime;
	private String totalRideDistance;
	private String totalRides;
	private String driverEarning;
	private String totalEarning;
	private String disputeAmount;
	private String totalAcceptedRequest;
	private String totalCancelPreRides;
	private String totalRequests;

	// Constructors

	/** default constructor */
	public DriverDrivingDetailEntity() {
	}

	/** full constructor */
	public DriverDrivingDetailEntity(FrequencyEntity frequency, Timestamp startDate,
			String totalOnlineTime, String totalOnlineDistance,
			String totalPreRideTime, String totalPreRideDistance,
			String totalRideTime, String totalRideDistance,
			DriverDrivingSummaryEntity driverDrivingSummary,
			Set<DriverDrivingSummaryEntity> driverDrivingSummaries) {
		this.frequency = frequency;
		this.startDate = startDate;
		this.totalOnlineTime = totalOnlineTime;
		this.totalOnlineDistance = totalOnlineDistance;
		this.totalPreRideTime = totalPreRideTime;
		this.totalPreRideDistance = totalPreRideDistance;
		this.totalRideTime = totalRideTime;
		this.totalRideDistance = totalRideDistance;
		this.driverDrivingSummary = driverDrivingSummary;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_driving_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverDrivingDetailId() {
		return this.driverDrivingDetailId;
	}

	public void setDriverDrivingDetailId(Integer driverDrivingDetailId) {
		this.driverDrivingDetailId = driverDrivingDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "frequency_id")
	public FrequencyEntity getFrequency() {
		return this.frequency;
	}

	public void setFrequency(FrequencyEntity frequency) {
		this.frequency = frequency;
	}

	@Column(name = "start_date", length = 23)
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name = "total_online_time", length = 20)
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

	@Column(name = "total_pre_ride_time", length = 20)
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

	@Column(name = "total_ride_time", length = 20)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_driving_summary_id")
	public DriverDrivingSummaryEntity getDriverDrivingSummaryEntity() {
		return driverDrivingSummary;
	}

	public void setDriverDrivingSummaryEntity(DriverDrivingSummaryEntity driverDrivingSummary) {
		this.driverDrivingSummary = driverDrivingSummary;
	}

}