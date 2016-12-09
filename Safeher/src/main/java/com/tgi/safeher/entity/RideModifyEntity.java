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
 * RideModify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_modify", schema = "dbo")
public class RideModifyEntity  extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideModifyId;
	private RideChangeRequestEntity rideChangeRequestEntity;
	private RideEntity rideEntity;
	private Integer requestNo;
	private Timestamp newEstimatedArrival;
	private Double newEstimatedDistance;
	private String newEstimatedDuration;
	private Set<PostRideEntity> postRideEntities = new HashSet<PostRideEntity>(0);

	// Constructors

	/** default constructor */
	public RideModifyEntity() {
	}

	/** full constructor */
	public RideModifyEntity(RideChangeRequestEntity rideChangeRequestEntity, RideEntity rideEntity,
			Integer requestNo, Timestamp newEstimatedArrival,
			Double newEstimatedDistance, String newEstimatedDuration,
			Set<PostRideEntity> postRideEntities) {
		this.rideChangeRequestEntity = rideChangeRequestEntity;
		this.rideEntity = rideEntity;
		this.requestNo = requestNo;
		this.newEstimatedArrival = newEstimatedArrival;
		this.newEstimatedDistance = newEstimatedDistance;
		this.newEstimatedDuration = newEstimatedDuration;
		this.postRideEntities = postRideEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_modify_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideModifyId() {
		return this.rideModifyId;
	}

	public void setRideModifyId(Integer rideModifyId) {
		this.rideModifyId = rideModifyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_change_request_id")
	public RideChangeRequestEntity getRideChangeRequest() {
		return this.rideChangeRequestEntity;
	}

	public void setRideChangeRequest(RideChangeRequestEntity rideChangeRequestEntity) {
		this.rideChangeRequestEntity = rideChangeRequestEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRide() {
		return this.rideEntity;
	}

	public void setRide(RideEntity rideEntity) {
		this.rideEntity = rideEntity;
	}

	@Column(name = "request_no")
	public Integer getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(Integer requestNo) {
		this.requestNo = requestNo;
	}

	@Column(name = "new_estimated_arrival", length = 23)
	public Timestamp getNewEstimatedArrival() {
		return this.newEstimatedArrival;
	}

	public void setNewEstimatedArrival(Timestamp newEstimatedArrival) {
		this.newEstimatedArrival = newEstimatedArrival;
	}

	@Column(name = "new_estimated_distance", precision = 6, scale = 3)
	public Double getNewEstimatedDistance() {
		return this.newEstimatedDistance;
	}

	public void setNewEstimatedDistance(Double newEstimatedDistance) {
		this.newEstimatedDistance = newEstimatedDistance;
	}

	@Column(name = "new_estimated_duration", length = 20)
	public String getNewEstimatedDuration() {
		return this.newEstimatedDuration;
	}

	public void setNewEstimatedDuration(String newEstimatedDuration) {
		this.newEstimatedDuration = newEstimatedDuration;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideModify")
	public Set<PostRideEntity> getPostRides() {
		return this.postRideEntities;
	}

	public void setPostRides(Set<PostRideEntity> postRideEntities) {
		this.postRideEntities = postRideEntities;
	}

}