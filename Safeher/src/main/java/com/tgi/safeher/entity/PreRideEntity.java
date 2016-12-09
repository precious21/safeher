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
import org.hibernate.annotations.Parameter;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * PreRide entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pre_ride", schema = "dbo")
public class PreRideEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer preRideId;
	private StatusEntity statusEntity;
	private RideColorEntity rideColorByColorDriver;
	private RideColorEntity rideColorByColorPassenger;
	private RideFinalizeEntity rideFinalizeEntity;
	private Timestamp driverArrivalTime;
	private Timestamp passengerArrivalTime;
	private String sourceSuburb;
	private String isDriverColorVerified;
	private String isPassengerColorVerified;
	private Timestamp driverVerificationTime;
	private Timestamp passengerVerificationTime;
	private String driverVerificationAttemps;
	private String passengerVerificationAttemps;
	private Timestamp driverStartTime;
	private String estimatedTime;
	private String requestNo;
	private Set<RideEntity> rideEntities = new HashSet<RideEntity>(0);

	// Constructors

	/** default constructor */
	public PreRideEntity() {
	}

	/** full constructor */
	public PreRideEntity(StatusEntity statusEntity, RideColorEntity rideColorByColorDriver,
			RideColorEntity rideColorByColorPassenger, RideFinalizeEntity rideFinalizeEntity,
			Timestamp driverArrivalTime, Timestamp passengerArrivalTime,
			String sourceSuburb, Set<RideEntity> rideEntities) {
		this.statusEntity = statusEntity;
		this.rideColorByColorDriver = rideColorByColorDriver;
		this.rideColorByColorPassenger = rideColorByColorPassenger;
		this.rideFinalizeEntity = rideFinalizeEntity;
		this.driverArrivalTime = driverArrivalTime;
		this.passengerArrivalTime = passengerArrivalTime;
		this.sourceSuburb = sourceSuburb;
		this.rideEntities = rideEntities;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "preRideEntity_Id")
	@GenericGenerator( name = "preRideEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_pre_ride" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "pre_ride_id", unique = true, nullable = false, precision = 1, scale = 0)
	public Integer getPreRideId() {
		return this.preRideId;
	}

	public void setPreRideId(Integer preRideId) {
		this.preRideId = preRideId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_ride")
	public StatusEntity getStatus() {
		return this.statusEntity;
	}

	public void setStatus(StatusEntity statusEntity) {
		this.statusEntity = statusEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_driver")
	public RideColorEntity getRideColorByColorDriver() {
		return this.rideColorByColorDriver;
	}

	public void setRideColorByColorDriver(RideColorEntity rideColorByColorDriver) {
		this.rideColorByColorDriver = rideColorByColorDriver;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "color_passenger")
	public RideColorEntity getRideColorByColorPassenger() {
		return this.rideColorByColorPassenger;
	}

	public void setRideColorByColorPassenger(RideColorEntity rideColorByColorPassenger) {
		this.rideColorByColorPassenger = rideColorByColorPassenger;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_finalize_id")
	public RideFinalizeEntity getRideFinalize() {
		return this.rideFinalizeEntity;
	}

	public void setRideFinalize(RideFinalizeEntity rideFinalizeEntity) {
		this.rideFinalizeEntity = rideFinalizeEntity;
	}

	@Column(name = "driver_arrival_time", length = 23)
	public Timestamp getDriverArrivalTime() {
		return this.driverArrivalTime;
	}

	public void setDriverArrivalTime(Timestamp driverArrivalTime) {
		this.driverArrivalTime = driverArrivalTime;
	}

	@Column(name = "passenger_arrival_time", length = 23)
	public Timestamp getPassengerArrivalTime() {
		return this.passengerArrivalTime;
	}

	public void setPassengerArrivalTime(Timestamp passengerArrivalTime) {
		this.passengerArrivalTime = passengerArrivalTime;
	}

	@Column(name = "source_suburb", length = 32)
	public String getSourceSuburb() {
		return this.sourceSuburb;
	}

	public void setSourceSuburb(String sourceSuburb) {
		this.sourceSuburb = sourceSuburb;
	}
	@Column(name = "is_driver_color_verified", length = 1)
	public String getIsDriverColorVerified() {
		return this.isDriverColorVerified;
	}

	public void setIsDriverColorVerified(String isDriverColorVerified) {
		this.isDriverColorVerified = isDriverColorVerified;
	}

	@Column(name = "is_passenger_color_verified", length = 1)
	public String getIsPassengerColorVerified() {
		return this.isPassengerColorVerified;
	}

	public void setIsPassengerColorVerified(String isPassengerColorVerified) {
		this.isPassengerColorVerified = isPassengerColorVerified;
	}

	@Column(name = "driver_verification_time", length = 23)
	public Timestamp getDriverVerificationTime() {
		return this.driverVerificationTime;
	}

	public void setDriverVerificationTime(Timestamp driverVerificationTime) {
		this.driverVerificationTime = driverVerificationTime;
	}

	@Column(name = "passenger_verification_time", length = 23)
	public Timestamp getPassengerVerificationTime() {
		return this.passengerVerificationTime;
	}

	public void setPassengerVerificationTime(Timestamp passengerVerificationTime) {
		this.passengerVerificationTime = passengerVerificationTime;
	}

	@Column(name = "driver_verification_attemps", length = 1)
	public String getDriverVerificationAttemps() {
		return this.driverVerificationAttemps;
	}

	public void setDriverVerificationAttemps(String driverVerificationAttemps) {
		this.driverVerificationAttemps = driverVerificationAttemps;
	}

	@Column(name = "passenger_verification_attemps", length = 1)
	public String getPassengerVerificationAttemps() {
		return this.passengerVerificationAttemps;
	}

	public void setPassengerVerificationAttemps(
			String passengerVerificationAttemps) {
		this.passengerVerificationAttemps = passengerVerificationAttemps;
	}
	
	@Column(name = "driver_start_time", length = 23)
	public Timestamp getDriverStartTime() {
		return this.driverStartTime;
	}

	public void setDriverStartTime(Timestamp driverStartTime) {
		this.driverStartTime = driverStartTime;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "preRide")
	public Set<RideEntity> getRides() {
		return this.rideEntities;
	}

	public void setRides(Set<RideEntity> rideEntities) {
		this.rideEntities = rideEntities;
	}
	
	@Column(name = "estimated_time", length = 20)
	public String getEstimatedTime() {
		return this.estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	@Column(name = "request_no", length = 200)
	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
}