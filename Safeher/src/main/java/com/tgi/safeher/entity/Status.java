package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "status", schema = "dbo")
public class Status implements java.io.Serializable {

	// Fields

	private Integer statusId;
	private String name;
	private Set<RideChangeRequestEntity> rideChangeRequestEntities = new HashSet<RideChangeRequestEntity>(
			0);
	private Set<UserLoginEntity> userLoginsForStatusUser = new HashSet<UserLoginEntity>(0);
	private Set<UserLoginEntity> userLoginsForStatusAccount = new HashSet<UserLoginEntity>(
			0);
	private Set<RideRequestResponseEntity> rideRequestResponsesForStatusFinal = new HashSet<RideRequestResponseEntity>(
			0);
	private Set<PreRideEntity> preRideEntities = new HashSet<PreRideEntity>(0);
	private Set<RideRequestResponseEntity> rideRequestResponsesForStatusResponse = new HashSet<RideRequestResponseEntity>(
			0);
	private Set<AppUserVehicleEntity> appUserVehicles = new HashSet<AppUserVehicleEntity>(0);
	private Set<StatusFilterEntity> statusFilters = new HashSet<StatusFilterEntity>(0);
	private Set<VehicleInfoEntity> vehicleInfos = new HashSet<VehicleInfoEntity>(0);

	// Constructors

	/** default constructor */
	public Status() {
	}

	/** minimal constructor */
	public Status(Integer statusId) {
		this.statusId = statusId;
	}

	/** full constructor */
	public Status(Integer statusId, String name,
			Set<RideChangeRequestEntity> rideChangeRequests,
			Set<UserLoginEntity> userLoginsForStatusUser,
			Set<UserLoginEntity> userLoginsForStatusAccount,
			Set<RideRequestResponseEntity> rideRequestResponsesForStatusFinal,
			Set<PreRideEntity> preRides,
			Set<RideRequestResponseEntity> rideRequestResponsesForStatusResponse,
			Set<AppUserVehicleEntity> appUserVehicles,
			Set<StatusFilterEntity> statusFilters, Set<VehicleInfoEntity> vehicleInfos) {
		this.statusId = statusId;
		this.name = name;
		this.rideChangeRequestEntities = rideChangeRequests;
		this.userLoginsForStatusUser = userLoginsForStatusUser;
		this.userLoginsForStatusAccount = userLoginsForStatusAccount;
		this.rideRequestResponsesForStatusFinal = rideRequestResponsesForStatusFinal;
		this.preRideEntities = preRides;
		this.rideRequestResponsesForStatusResponse = rideRequestResponsesForStatusResponse;
		this.appUserVehicles = appUserVehicles;
		this.statusFilters = statusFilters;
		this.vehicleInfos = vehicleInfos;
	}

	// Property accessors
	@Id
	@Column(name = "status_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "status")
	public Set<RideChangeRequestEntity> getRideChangeRequests() {
		return this.rideChangeRequestEntities;
	}

	public void setRideChangeRequests(Set<RideChangeRequestEntity> rideChangeRequests) {
		this.rideChangeRequestEntities = rideChangeRequests;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "statusByStatusUser")
	public Set<UserLoginEntity> getUserLoginsForStatusUser() {
		return this.userLoginsForStatusUser;
	}

	public void setUserLoginsForStatusUser(
			Set<UserLoginEntity> userLoginsForStatusUser) {
		this.userLoginsForStatusUser = userLoginsForStatusUser;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "statusByStatusAccount")
	public Set<UserLoginEntity> getUserLoginsForStatusAccount() {
		return this.userLoginsForStatusAccount;
	}

	public void setUserLoginsForStatusAccount(
			Set<UserLoginEntity> userLoginsForStatusAccount) {
		this.userLoginsForStatusAccount = userLoginsForStatusAccount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "statusByStatusFinal")
	public Set<RideRequestResponseEntity> getRideRequestResponsesForStatusFinal() {
		return this.rideRequestResponsesForStatusFinal;
	}

	public void setRideRequestResponsesForStatusFinal(
			Set<RideRequestResponseEntity> rideRequestResponsesForStatusFinal) {
		this.rideRequestResponsesForStatusFinal = rideRequestResponsesForStatusFinal;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "status")
	public Set<PreRideEntity> getPreRides() {
		return this.preRideEntities;
	}

	public void setPreRides(Set<PreRideEntity> preRides) {
		this.preRideEntities = preRides;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "statusByStatusResponse")
	public Set<RideRequestResponseEntity> getRideRequestResponsesForStatusResponse() {
		return this.rideRequestResponsesForStatusResponse;
	}

	public void setRideRequestResponsesForStatusResponse(
			Set<RideRequestResponseEntity> rideRequestResponsesForStatusResponse) {
		this.rideRequestResponsesForStatusResponse = rideRequestResponsesForStatusResponse;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "status")
	public Set<AppUserVehicleEntity> getAppUserVehicles() {
		return this.appUserVehicles;
	}

	public void setAppUserVehicles(Set<AppUserVehicleEntity> appUserVehicles) {
		this.appUserVehicles = appUserVehicles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "status")
	public Set<StatusFilterEntity> getStatusFilters() {
		return this.statusFilters;
	}

	public void setStatusFilters(Set<StatusFilterEntity> statusFilters) {
		this.statusFilters = statusFilters;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "status")
	public Set<VehicleInfoEntity> getVehicleInfos() {
		return this.vehicleInfos;
	}

	public void setVehicleInfos(Set<VehicleInfoEntity> vehicleInfos) {
		this.vehicleInfos = vehicleInfos;
	}

}