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
import org.hibernate.annotations.Parameter;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * RideSearchResultDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_search_result_detail", schema = "dbo")
public class RideSearchResultDetailEntity  extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideSearchResultDetailId;
	private AppUserEntity appUserEntity;
	private RideSearchResultEntity rideSearchResultEntity;
	private String driverLong;
	private String driverLat;
	private String isPreSelect;
	private String requestNo;
	private Set<RideRequestResponseEntity> rideRequestResponseEntities = new HashSet<RideRequestResponseEntity>(
			0);

	// Constructors

	/** default constructor */
	public RideSearchResultDetailEntity() {
	}

	/** full constructor */
	public RideSearchResultDetailEntity(AppUserEntity appUserEntity,
			RideSearchResultEntity rideSearchResultEntity, String driverLong,
			String driverLat, String isPreSelect,
			Set<RideRequestResponseEntity> rideRequestResponseEntities) {
		this.appUserEntity = appUserEntity;
		this.rideSearchResultEntity = rideSearchResultEntity;
		this.driverLong = driverLong;
		this.driverLat = driverLat;
		this.isPreSelect = isPreSelect;
		this.rideRequestResponseEntities = rideRequestResponseEntities;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(generator = "rideSearchResultDetailEntity_Id")
	@GenericGenerator( name = "rideSearchResultDetailEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_search_result_detail" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_search_result_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideSearchResultDetailId() {
		return this.rideSearchResultDetailId;
	}

	public void setRideSearchResultDetailId(Integer rideSearchResultDetailId) {
		this.rideSearchResultDetailId = rideSearchResultDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_driver")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_search_result")
	public RideSearchResultEntity getRideSearchResult() {
		return this.rideSearchResultEntity;
	}

	public void setRideSearchResult(RideSearchResultEntity rideSearchResultEntity) {
		this.rideSearchResultEntity = rideSearchResultEntity;
	}

	@Column(name = "driver_long", length = 32)
	public String getDriverLong() {
		return this.driverLong;
	}

	public void setDriverLong(String driverLong) {
		this.driverLong = driverLong;
	}

	@Column(name = "driver_lat", length = 32)
	public String getDriverLat() {
		return this.driverLat;
	}

	public void setDriverLat(String driverLat) {
		this.driverLat = driverLat;
	}

	@Column(name = "is_pre_select", length = 1)
	public String getIsPreSelect() {
		return this.isPreSelect;
	}

	public void setIsPreSelect(String isPreSelect) {
		this.isPreSelect = isPreSelect;
	}

	@Column(name = "request_no", length = 200)
	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideSearchResultDetail")
	public Set<RideRequestResponseEntity> getRideRequestResponses() {
		return this.rideRequestResponseEntities;
	}

	public void setRideRequestResponses(
			Set<RideRequestResponseEntity> rideRequestResponseEntities) {
		this.rideRequestResponseEntities = rideRequestResponseEntities;
	}

}