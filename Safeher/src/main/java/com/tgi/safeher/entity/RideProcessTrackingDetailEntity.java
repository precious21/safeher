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

/**
 * RideProcessTrackingDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_process_tracking_detail", schema = "dbo")
public class RideProcessTrackingDetailEntity implements java.io.Serializable {

	// Fields

	private Integer rideProcessTrackingDetialId;
	private RideProcessTrackingEntity rideProcessTracking;
	private ProcessStateEntity processState;
	private ProcessActionEntity processAction;
	private AppUserTypeEntity appUserType;
	private String statusFlag;
	private Timestamp actionTime;
	private String isRecontinued;
	private Timestamp recontinuedTime;
	private String recontinuedStatus;

	// Constructors

	/** default constructor */
	public RideProcessTrackingDetailEntity() {
	}

	/** full constructor */
	public RideProcessTrackingDetailEntity(RideProcessTrackingEntity rideProcessTracking,
			ProcessStateEntity processState, ProcessActionEntity processAction,
			String statusFlag, Timestamp actionTime, String isRecontinued,
			Timestamp recontinuedTime, String recontinuedStatus) {
		this.rideProcessTracking = rideProcessTracking;
		this.processState = processState;
		this.processAction = processAction;
		this.statusFlag = statusFlag;
		this.actionTime = actionTime;
		this.isRecontinued = isRecontinued;
		this.recontinuedTime = recontinuedTime;
		this.recontinuedStatus = recontinuedStatus;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_process_tracking_detial_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideProcessTrackingDetialId() {
		return this.rideProcessTrackingDetialId;
	}

	public void setRideProcessTrackingDetialId(
			Integer rideProcessTrackingDetialId) {
		this.rideProcessTrackingDetialId = rideProcessTrackingDetialId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_process_tracking_id")
	public RideProcessTrackingEntity getRideProcessTracking() {
		return this.rideProcessTracking;
	}

	public void setRideProcessTracking(RideProcessTrackingEntity rideProcessTracking) {
		this.rideProcessTracking = rideProcessTracking;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_state_id")
	public ProcessStateEntity getProcessState() {
		return this.processState;
	}

	public void setProcessState(ProcessStateEntity processState) {
		this.processState = processState;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_action_id")
	public ProcessActionEntity getProcessAction() {
		return this.processAction;
	}

	public void setProcessAction(ProcessActionEntity processAction) {
		this.processAction = processAction;
	}

	@Column(name = "status_flag", length = 1)
	public String getStatusFlag() {
		return this.statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	@Column(name = "action_time", length = 23)
	public Timestamp getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}

	@Column(name = "is_recontinued", length = 18)
	public String getIsRecontinued() {
		return this.isRecontinued;
	}

	public void setIsRecontinued(String isRecontinued) {
		this.isRecontinued = isRecontinued;
	}

	@Column(name = "recontinued_time", length = 23)
	public Timestamp getRecontinuedTime() {
		return this.recontinuedTime;
	}

	public void setRecontinuedTime(Timestamp recontinuedTime) {
		this.recontinuedTime = recontinuedTime;
	}

	@Column(name = "recontinued_status", length = 30)
	public String getRecontinuedStatus() {
		return this.recontinuedStatus;
	}

	public void setRecontinuedStatus(String recontinuedStatus) {
		this.recontinuedStatus = recontinuedStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_type_id")
	public AppUserTypeEntity getAppUserType() {
		return appUserType;
	}

	public void setAppUserType(AppUserTypeEntity appUserType) {
		this.appUserType = appUserType;
	}

}