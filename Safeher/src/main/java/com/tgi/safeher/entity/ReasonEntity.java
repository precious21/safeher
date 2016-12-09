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
 * Reason entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reason", schema = "dbo")
public class ReasonEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer reasonId;
	private String name;
	private Set<RideChangeRequestEntity> rideChangeRequestEntities = new HashSet<RideChangeRequestEntity>(
			0);
	private Set<ReasonFilterEntity> reasonFilterEntities = new HashSet<ReasonFilterEntity>(0);
	private Set<RideDetailEntity> rideDetailsForReasonCancel = new HashSet<RideDetailEntity>(
			0);
	private Set<RideDetailEntity> rideDetailsForReasonPartial = new HashSet<RideDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public ReasonEntity() {
	}

	/** full constructor */
	public ReasonEntity(String name, Set<RideChangeRequestEntity> rideChangeRequestEntities,
			Set<ReasonFilterEntity> reasonFilterEntities,
			Set<RideDetailEntity> rideDetailsForReasonCancel,
			Set<RideDetailEntity> rideDetailsForReasonPartial) {
		this.name = name;
		this.rideChangeRequestEntities = rideChangeRequestEntities;
		this.reasonFilterEntities = reasonFilterEntities;
		this.rideDetailsForReasonCancel = rideDetailsForReasonCancel;
		this.rideDetailsForReasonPartial = rideDetailsForReasonPartial;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "reason_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reason")
	public Set<RideChangeRequestEntity> getRideChangeRequests() {
		return this.rideChangeRequestEntities;
	}

	public void setRideChangeRequests(Set<RideChangeRequestEntity> rideChangeRequestEntities) {
		this.rideChangeRequestEntities = rideChangeRequestEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reason")
	public Set<ReasonFilterEntity> getReasonFilters() {
		return this.reasonFilterEntities;
	}

	public void setReasonFilters(Set<ReasonFilterEntity> reasonFilterEntities) {
		this.reasonFilterEntities = reasonFilterEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reasonByReasonCancel")
	public Set<RideDetailEntity> getRideDetailsForReasonCancel() {
		return this.rideDetailsForReasonCancel;
	}

	public void setRideDetailsForReasonCancel(
			Set<RideDetailEntity> rideDetailsForReasonCancel) {
		this.rideDetailsForReasonCancel = rideDetailsForReasonCancel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reasonByReasonPartial")
	public Set<RideDetailEntity> getRideDetailsForReasonPartial() {
		return this.rideDetailsForReasonPartial;
	}

	public void setRideDetailsForReasonPartial(
			Set<RideDetailEntity> rideDetailsForReasonPartial) {
		this.rideDetailsForReasonPartial = rideDetailsForReasonPartial;
	}

}