package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * DriverOnlineDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_online_detail", schema = "dbo")
public class DriverOnlineDetailEntity implements java.io.Serializable {

	// Fields

	private String driverOnlineDetailId;
	private DriverOnlineLogEntity driverOnlineLog;
	private Integer noRides;
	private Integer noRequest;
	private Integer acceptRequests;
	private Integer completeRides;
	private String totalAcceptedRequest;
	private String totalCancelPreRides;
	private String totalRequests;
	// Constructors

	/** default constructor */
	public DriverOnlineDetailEntity() {
	}

	/** full constructor */
	public DriverOnlineDetailEntity(DriverOnlineLogEntity driverOnlineLog, Integer noRides,
			Integer noRequest, Integer acceptRequests, Integer completeRides) {
		this.driverOnlineLog = driverOnlineLog;
		this.noRides = noRides;
		this.noRequest = noRequest;
		this.acceptRequests = acceptRequests;
		this.completeRides = completeRides;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_online_detail_id", unique = true, nullable = false, length = 18)
	public String getDriverOnlineDetailId() {
		return this.driverOnlineDetailId;
	}

	public void setDriverOnlineDetailId(String driverOnlineDetailId) {
		this.driverOnlineDetailId = driverOnlineDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_online_log_id")
	public DriverOnlineLogEntity getDriverOnlineLog() {
		return this.driverOnlineLog;
	}

	public void setDriverOnlineLog(DriverOnlineLogEntity driverOnlineLog) {
		this.driverOnlineLog = driverOnlineLog;
	}

	@Column(name = "no_rides", precision = 9, scale = 0)
	public Integer getNoRides() {
		return this.noRides;
	}

	public void setNoRides(Integer noRides) {
		this.noRides = noRides;
	}

	@Column(name = "no_request", precision = 9, scale = 0)
	public Integer getNoRequest() {
		return this.noRequest;
	}

	public void setNoRequest(Integer noRequest) {
		this.noRequest = noRequest;
	}

	@Column(name = "accept_requests", precision = 9, scale = 0)
	public Integer getAcceptRequests() {
		return this.acceptRequests;
	}

	public void setAcceptRequests(Integer acceptRequests) {
		this.acceptRequests = acceptRequests;
	}

	@Column(name = "complete_rides", precision = 9, scale = 0)
	public Integer getCompleteRides() {
		return this.completeRides;
	}

	public void setCompleteRides(Integer completeRides) {
		this.completeRides = completeRides;
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