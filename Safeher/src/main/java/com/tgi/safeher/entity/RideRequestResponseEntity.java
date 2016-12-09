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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideRequestResponse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_request_response", schema = "dbo")
public class RideRequestResponseEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideReqResId;
	private RideSearchResultDetailEntity rideSearchResultDetailEntity;
	private StatusEntity statusByStatusFinal;
	private StatusEntity statusByStatusResponse;
	private AppUserEntity appUserEntity;
	private Timestamp requestTime;
	private Timestamp responseTime;
	private String isCall;
	private String isText;
	private Set<RideFinalizeEntity> rideFinalizeEntities = new HashSet<RideFinalizeEntity>(0);
	private String requestNo;
	private String driverId;

	// Constructors

	/** default constructor */
	public RideRequestResponseEntity() {
	}

	/** full constructor */
	public RideRequestResponseEntity(RideSearchResultDetailEntity rideSearchResultDetailEntity,
			StatusEntity statusByStatusFinal, StatusEntity statusByStatusResponse,
			AppUserEntity appUserEntity, Timestamp requestTime, Timestamp responseTime,
			String isCall, String isText, Set<RideFinalizeEntity> rideFinalizeEntities) {
		this.rideSearchResultDetailEntity = rideSearchResultDetailEntity;
		this.statusByStatusFinal = statusByStatusFinal;
		this.statusByStatusResponse = statusByStatusResponse;
		this.appUserEntity = appUserEntity;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.isCall = isCall;
		this.isText = isText;
		this.rideFinalizeEntities = rideFinalizeEntities;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(generator = "rideReqResEntity_Id")
	@GenericGenerator( name = "rideReqResEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_request_response" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_req_res_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideReqResId() {
		return this.rideReqResId;
	}

	public void setRideReqResId(Integer rideReqResId) {
		this.rideReqResId = rideReqResId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_search_result_detail_id")
	public RideSearchResultDetailEntity getRideSearchResultDetail() {
		return this.rideSearchResultDetailEntity;
	}

	public void setRideSearchResultDetail(
			RideSearchResultDetailEntity rideSearchResultDetailEntity) {
		this.rideSearchResultDetailEntity = rideSearchResultDetailEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_final")
	public StatusEntity getStatusByStatusFinal() {
		return this.statusByStatusFinal;
	}

	public void setStatusByStatusFinal(StatusEntity statusByStatusFinal) {
		this.statusByStatusFinal = statusByStatusFinal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_response")
	public StatusEntity getStatusByStatusResponse() {
		return this.statusByStatusResponse;
	}

	public void setStatusByStatusResponse(StatusEntity statusByStatusResponse) {
		this.statusByStatusResponse = statusByStatusResponse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_passenger")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@Column(name = "request_time", length = 23)
	public Timestamp getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	@Column(name = "response_time", length = 23)
	public Timestamp getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	@Column(name = "is_call", length = 1)
	public String getIsCall() {
		return this.isCall;
	}

	public void setIsCall(String isCall) {
		this.isCall = isCall;
	}

	@Column(name = "is_text", length = 1)
	public String getIsText() {
		return this.isText;
	}

	public void setIsText(String isText) {
		this.isText = isText;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideRequestResponse")
	public Set<RideFinalizeEntity> getRideFinalizes() {
		return this.rideFinalizeEntities;
	}

	public void setRideFinalizes(Set<RideFinalizeEntity> rideFinalizeEntities) {
		this.rideFinalizeEntities = rideFinalizeEntities;
	}
	
	@Transient
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	@Transient
	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

}