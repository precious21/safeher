package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * ProcessState entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "process_state", schema = "dbo")
public class ProcessStateEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer processStateId;
	private String name;
	private Set<ProcessActionEntity> processActions = new HashSet<ProcessActionEntity>(0);
	private Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails = new HashSet<RideProcessTrackingDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public ProcessStateEntity() {
	}

	/** full constructor */
	public ProcessStateEntity(String name, Set<ProcessActionEntity> processActions,
			Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails) {
		this.name = name;
		this.processActions = processActions;
		this.rideProcessTrackingDetails = rideProcessTrackingDetails;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "process_state_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getProcessStateId() {
		return this.processStateId;
	}

	public void setProcessStateId(Integer processStateId) {
		this.processStateId = processStateId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processState")
	public Set<ProcessActionEntity> getProcessActions() {
		return this.processActions;
	}

	public void setProcessActions(Set<ProcessActionEntity> processActions) {
		this.processActions = processActions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processState")
	public Set<RideProcessTrackingDetailEntity> getRideProcessTrackingDetails() {
		return this.rideProcessTrackingDetails;
	}

	public void setRideProcessTrackingDetails(
			Set<RideProcessTrackingDetailEntity> rideProcessTrackingDetails) {
		this.rideProcessTrackingDetails = rideProcessTrackingDetails;
	}

}