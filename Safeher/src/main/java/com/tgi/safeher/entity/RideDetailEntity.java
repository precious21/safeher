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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_detail", schema = "dbo")
public class RideDetailEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideDetailId;
	private ReasonEntity reasonByReasonCancel;
	private ReasonEntity reasonByReasonPartial;
	private RideEntity rideEntityId;
	private String sourceLong;
	private String sourceLat;
	private String destinationLat;
	private String destinationLong;
	private Short actualSeats;
	private Short actualChild;
	private Short actualPerson;
	private String sourceAddress;
	private String destinationAddress;
	// Constructors

	/** default constructor */
	public RideDetailEntity() {
	}

	/** full constructor */
	public RideDetailEntity(ReasonEntity reasonByReasonCancel,
			ReasonEntity reasonByReasonPartial, RideEntity rideEntity, String sourceLong,
			String sourceLat, String destinationLat, String destinationLong,
			Short actualSeats, Short actualChild, Short actualPerson) {
		this.reasonByReasonCancel = reasonByReasonCancel;
		this.reasonByReasonPartial = reasonByReasonPartial;
		this.rideEntityId = rideEntity;
		this.sourceLong = sourceLong;
		this.sourceLat = sourceLat;
		this.destinationLat = destinationLat;
		this.destinationLong = destinationLong;
		this.actualSeats = actualSeats;
		this.actualChild = actualChild;
		this.actualPerson = actualPerson;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideDetailId() {
		return this.rideDetailId;
	}

	public void setRideDetailId(Integer rideDetailId) {
		this.rideDetailId = rideDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_cancel")
	public ReasonEntity getReasonByReasonCancel() {
		return this.reasonByReasonCancel;
	}

	public void setReasonByReasonCancel(ReasonEntity reasonByReasonCancel) {
		this.reasonByReasonCancel = reasonByReasonCancel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_partial")
	public ReasonEntity getReasonByReasonPartial() {
		return this.reasonByReasonPartial;
	}

	public void setReasonByReasonPartial(ReasonEntity reasonByReasonPartial) {
		this.reasonByReasonPartial = reasonByReasonPartial;
	}

	@Column(name = "source_long", length = 32)
	public String getSourceLong() {
		return this.sourceLong;
	}

	public void setSourceLong(String sourceLong) {
		this.sourceLong = sourceLong;
	}

	@Column(name = "source_lat", length = 32)
	public String getSourceLat() {
		return this.sourceLat;
	}

	public void setSourceLat(String sourceLat) {
		this.sourceLat = sourceLat;
	}

	@Column(name = "destination_lat", length = 32)
	public String getDestinationLat() {
		return this.destinationLat;
	}

	public void setDestinationLat(String destinationLat) {
		this.destinationLat = destinationLat;
	}

	@Column(name = "destination_long", length = 32)
	public String getDestinationLong() {
		return this.destinationLong;
	}

	public void setDestinationLong(String destinationLong) {
		this.destinationLong = destinationLong;
	}

	@Column(name = "actual_seats")
	public Short getActualSeats() {
		return this.actualSeats;
	}

	public void setActualSeats(Short actualSeats) {
		this.actualSeats = actualSeats;
	}

	@Column(name = "actual_child")
	public Short getActualChild() {
		return this.actualChild;
	}

	public void setActualChild(Short actualChild) {
		this.actualChild = actualChild;
	}

	@Column(name = "actual_person")
	public Short getActualPerson() {
		return this.actualPerson;
	}

	public void setActualPerson(Short actualPerson) {
		this.actualPerson = actualPerson;
	}

	/**
	 * @return the rideEntityId
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_id")
	public RideEntity getRideEntityId() {
		return rideEntityId;
	}

	/**
	 * @param rideEntityId the rideEntityId to set
	 */
	public void setRideEntityId(RideEntity rideEntityId) {
		this.rideEntityId = rideEntityId;
	}

	@Column(name = "destination_address", length = 200)
	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	@Column(name = "source_address", length = 200)
	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

}