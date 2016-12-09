package com.tgi.safeher.entity;

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
 * ProcessAction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "process_action", schema = "dbo")
public class ProcessActionEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer processActionId;
	private ProcessStateEntity processState;
	private String name;
	private Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails = new HashSet<RideProcessTrackingDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public ProcessActionEntity() {
	}

	/** full constructor */
	public ProcessActionEntity(ProcessStateEntity processState, String name,
			Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails) {
		this.processState = processState;
		this.name = name;
		this.rideProcessTrackingDetails = rideProcessTrackingDetails;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "process_action_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getProcessActionId() {
		return this.processActionId;
	}

	public void setProcessActionId(Integer processActionId) {
		this.processActionId = processActionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_state_id")
	public ProcessStateEntity getProcessState() {
		return this.processState;
	}

	public void setProcessState(ProcessStateEntity processState) {
		this.processState = processState;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processAction")
	public Set<RideProcessTrackingDetailEntity> getRideProcessTrackingDetails() {
		return this.rideProcessTrackingDetails;
	}

	public void setRideProcessTrackingDetails(
			Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails) {
		this.rideProcessTrackingDetails = rideProcessTrackingDetails;
	}

}