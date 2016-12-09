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
 * PostRide entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "post_ride", schema = "dbo")
public class PostRideEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer postRideId;
	private RideModifyEntity rideModifyEntity;
	private RideEntity rideEntityId;
	private Timestamp actualArrival;
	private String isModified;
	private Double actualDistance;
	private String actualDuration;
	private String idelTime;

	// Constructors

	/** default constructor */
	public PostRideEntity() {
	}

	/** full constructor */
	public PostRideEntity(RideModifyEntity rideModifyEntity, RideEntity rideEntityId, Timestamp actualArrival,
			String isModified, Double actualDistance, String actualDuration,
			String idelTime) {
		this.rideModifyEntity = rideModifyEntity;
		this.rideEntityId = rideEntityId;
		this.actualArrival = actualArrival;
		this.isModified = isModified;
		this.actualDistance = actualDistance;
		this.actualDuration = actualDuration;
		this.idelTime = idelTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "post_ride_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPostRideId() {
		return this.postRideId;
	}

	public void setPostRideId(Integer postRideId) {
		this.postRideId = postRideId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_modify_id")
	public RideModifyEntity getRideModify() {
		return this.rideModifyEntity;
	}

	public void setRideModify(RideModifyEntity rideModifyEntity) {
		this.rideModifyEntity = rideModifyEntity;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRideEntityId() {
		return this.rideEntityId;
	}

	public void setRideEntityId(RideEntity rideEntityId) {
		this.rideEntityId = rideEntityId;
	}

	@Column(name = "actual_arrival", length = 23)
	public Timestamp getActualArrival() {
		return this.actualArrival;
	}

	public void setActualArrival(Timestamp actualArrival) {
		this.actualArrival = actualArrival;
	}

	@Column(name = "is_modified", length = 1)
	public String getIsModified() {
		return this.isModified;
	}

	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}

	@Column(name = "actual_distance", precision = 9, scale = 3)
	public Double getActualDistance() {
		return this.actualDistance;
	}

	public void setActualDistance(Double actualDistance) {
		this.actualDistance = actualDistance;
	}

	@Column(name = "actual_duration", length = 20)
	public String getActualDuration() {
		return this.actualDuration;
	}

	public void setActualDuration(String actualDuration) {
		this.actualDuration = actualDuration;
	}

	@Column(name = "idel_time", length = 20)
	public String getIdelTime() {
		return this.idelTime;
	}

	public void setIdelTime(String idelTime) {
		this.idelTime = idelTime;
	}

}