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
 * RideChangeRequest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_change_request", schema = "dbo")
public class RideChangeRequestEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideChangeRequestId;
	private StatusEntity statusEntity;
	private ReasonEntity reasonEntity;
	private RideEntity rideEntity;
	private String newDestLong;
	private String newDestLat;
	private Integer requestTime;
	private Timestamp responseTime;
	private String isApproved;
	private String currentLong;
	private String currentLat;
	private String isRerequest;
	private Set<RideModifyEntity> rideModifyEntities = new HashSet<RideModifyEntity>(0);

	// Constructors

	/** default constructor */
	public RideChangeRequestEntity() {
	}

	/** full constructor */
	public RideChangeRequestEntity(StatusEntity statusEntity, ReasonEntity reasonEntity, RideEntity rideEntity,
			String newDestLong, String newDestLat, Integer requestTime,
			Timestamp responseTime, String isApproved, String currentLong,
			String currentLat, String isRerequest, Set<RideModifyEntity> rideModifyEntities) {
		this.statusEntity = statusEntity;
		this.reasonEntity = reasonEntity;
		this.rideEntity = rideEntity;
		this.newDestLong = newDestLong;
		this.newDestLat = newDestLat;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.isApproved = isApproved;
		this.currentLong = currentLong;
		this.currentLat = currentLat;
		this.isRerequest = isRerequest;
		this.rideModifyEntities = rideModifyEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_change_request_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideChangeRequestId() {
		return this.rideChangeRequestId;
	}

	public void setRideChangeRequestId(Integer rideChangeRequestId) {
		this.rideChangeRequestId = rideChangeRequestId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_decision")
	public StatusEntity getStatus() {
		return this.statusEntity;
	}

	public void setStatus(StatusEntity statusEntity) {
		this.statusEntity = statusEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_deny")
	public ReasonEntity getReason() {
		return this.reasonEntity;
	}

	public void setReason(ReasonEntity reasonEntity) {
		this.reasonEntity = reasonEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRide() {
		return this.rideEntity;
	}

	public void setRide(RideEntity rideEntity) {
		this.rideEntity = rideEntity;
	}

	@Column(name = "new_dest_long", length = 32)
	public String getNewDestLong() {
		return this.newDestLong;
	}

	public void setNewDestLong(String newDestLong) {
		this.newDestLong = newDestLong;
	}

	@Column(name = "new_dest_lat", length = 32)
	public String getNewDestLat() {
		return this.newDestLat;
	}

	public void setNewDestLat(String newDestLat) {
		this.newDestLat = newDestLat;
	}

	@Column(name = "request_time")
	public Integer getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Integer requestTime) {
		this.requestTime = requestTime;
	}

	@Column(name = "response_time", length = 23)
	public Timestamp getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	@Column(name = "is_approved", length = 18)
	public String getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	@Column(name = "current_long", length = 32)
	public String getCurrentLong() {
		return this.currentLong;
	}

	public void setCurrentLong(String currentLong) {
		this.currentLong = currentLong;
	}

	@Column(name = "current_lat", length = 32)
	public String getCurrentLat() {
		return this.currentLat;
	}

	public void setCurrentLat(String currentLat) {
		this.currentLat = currentLat;
	}

	@Column(name = "is_rerequest", length = 1)
	public String getIsRerequest() {
		return this.isRerequest;
	}

	public void setIsRerequest(String isRerequest) {
		this.isRerequest = isRerequest;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideChangeRequest")
	public Set<RideModifyEntity> getRideModifies() {
		return this.rideModifyEntities;
	}

	public void setRideModifies(Set<RideModifyEntity> rideModifyEntities) {
		this.rideModifyEntities = rideModifyEntities;
	}

}