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
 * TravelHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "travel_history", schema = "dbo")
public class TravelHistoryEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer travelHistoryId;
	private AppUserTypeEntity appUserTypeEntity;
	private AppUserEntity appUserEntity;
	private String totalRides;
	private String totalSearch;
	private String totalCancle;
	private String totalDuration;
	private String totalDistance;
	private String lastRideDate;

	// Constructors

	/** default constructor */
	public TravelHistoryEntity() {
	}

	/** full constructor */
	public TravelHistoryEntity(AppUserTypeEntity appUserTypeEntity, AppUserEntity appUserEntity,
			String totalRides, String totalSearch, String totalCancle,
			String totalDuration, String totalDistance, String lastRideDate) {
		this.appUserTypeEntity = appUserTypeEntity;
		this.appUserEntity = appUserEntity;
		this.totalRides = totalRides;
		this.totalSearch = totalSearch;
		this.totalCancle = totalCancle;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.lastRideDate = lastRideDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "travel_history_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getTravelHistoryId() {
		return this.travelHistoryId;
	}

	public void setTravelHistoryId(Integer travelHistoryId) {
		this.travelHistoryId = travelHistoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_type_id")
	public AppUserTypeEntity getAppUserType() {
		return this.appUserTypeEntity;
	}

	public void setAppUserType(AppUserTypeEntity appUserTypeEntity) {
		this.appUserTypeEntity = appUserTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@Column(name = "total_rides", length = 18)
	public String getTotalRides() {
		return this.totalRides;
	}

	public void setTotalRides(String totalRides) {
		this.totalRides = totalRides;
	}

	@Column(name = "total_search", length = 18)
	public String getTotalSearch() {
		return this.totalSearch;
	}

	public void setTotalSearch(String totalSearch) {
		this.totalSearch = totalSearch;
	}

	@Column(name = "total_cancle", length = 18)
	public String getTotalCancle() {
		return this.totalCancle;
	}

	public void setTotalCancle(String totalCancle) {
		this.totalCancle = totalCancle;
	}

	@Column(name = "total_duration", length = 18)
	public String getTotalDuration() {
		return this.totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	@Column(name = "total_distance", length = 18)
	public String getTotalDistance() {
		return this.totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	@Column(name = "last_ride_date", length = 18)
	public String getLastRideDate() {
		return this.lastRideDate;
	}

	public void setLastRideDate(String lastRideDate) {
		this.lastRideDate = lastRideDate;
	}

}