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
 * AppUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user", schema = "dbo")
public class AppUserEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserId;
	private DriverInfoEntity driverInfoEntity;
	private AppUserTypeEntity appUserTypeEntity;
	private PersonDetailEntity personDetailEntity;
	private PersonEntity personEntity;
	private String isDriver;
	private Timestamp userDate;
	private String appUserNo;
	private String registrationDate;
	private String userNo;
	private String familyNo;
	private String isActivated;
	private StatusEntity userStatus;
	private Set<AppUserBiometricEntity> appUserBiometricEntities = new HashSet<AppUserBiometricEntity>(
			0);
	private Set<TravelHistoryEntity> travelHistoryEntities = new HashSet<TravelHistoryEntity>(0);
	private Set<UserRatingEntity> userRatingEntities = new HashSet<UserRatingEntity>(0);
	private Set<DriverSuburbEntity> driverSuburbEntities = new HashSet<DriverSuburbEntity>(0);
	private Set<UserRatingDetailEntity> userRatingDetailEntities = new HashSet<UserRatingDetailEntity>(
			0);
	private Set<RideCriteriaEntity> rideCriteriaEntities = new HashSet<RideCriteriaEntity>(0);
	private Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities = new HashSet<RideSearchResultDetailEntity>(
			0);
	private Set<UserCommentEntity> userCommentsForUserFor = new HashSet<UserCommentEntity>(
			0);
	private Set<UserCommentEntity> userCommentsForUserBy = new HashSet<UserCommentEntity>(0);
	private Set<RideRequestResponseEntity> rideRequestResponseEntities = new HashSet<RideRequestResponseEntity>(
			0);
	private Set<AppUserFileEntity> appUserFileEntities = new HashSet<AppUserFileEntity>(0);
	private Set<AppUserTypeCatsEntity> appUserTypeCatses = new HashSet<AppUserTypeCatsEntity>(
			0);

	// Constructors

	/** default constructor */
	public AppUserEntity() {
	}

	/** full constructor */
	public AppUserEntity(DriverInfoEntity driverInfoEntity, AppUserTypeEntity appUserTypeEntity,
			PersonDetailEntity personDetailEntity, PersonEntity personEntity, String isDriver,
			Timestamp userDate, String appUserNo, String registrationDate,
			String userNo, String familyNo,
			Set<AppUserBiometricEntity> appUserBiometricEntities,
			Set<TravelHistoryEntity> travelHistoryEntities, Set<UserRatingEntity> userRatingEntities,
			Set<DriverSuburbEntity> driverSuburbEntities,
			Set<UserRatingDetailEntity> userRatingDetailEntities,
			Set<RideCriteriaEntity> rideCriteriaEntities,
			Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities,
			Set<UserCommentEntity> userCommentsForUserFor,
			Set<UserCommentEntity> userCommentsForUserBy,
			Set<RideRequestResponseEntity> rideRequestResponseEntities,
			Set<AppUserFileEntity> appUserFileEntities,
			Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.driverInfoEntity = driverInfoEntity;
		this.appUserTypeEntity = appUserTypeEntity;
		this.personDetailEntity = personDetailEntity;
		this.personEntity = personEntity;
		this.isDriver = isDriver;
		this.userDate = userDate;
		this.appUserNo = appUserNo;
		this.registrationDate = registrationDate;
		this.userNo = userNo;
		this.familyNo = familyNo;
		this.appUserBiometricEntities = appUserBiometricEntities;
		this.travelHistoryEntities = travelHistoryEntities;
		this.userRatingEntities = userRatingEntities;
		this.driverSuburbEntities = driverSuburbEntities;
		this.userRatingDetailEntities = userRatingDetailEntities;
		this.rideCriteriaEntities = rideCriteriaEntities;
		this.rideSearchResultDetailEntities = rideSearchResultDetailEntities;
		this.userCommentsForUserFor = userCommentsForUserFor;
		this.userCommentsForUserBy = userCommentsForUserBy;
		this.rideRequestResponseEntities = rideRequestResponseEntities;
		this.appUserFileEntities = appUserFileEntities;
		this.appUserTypeCatses = appUserTypeCatses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_info_id")
	public DriverInfoEntity getDriverInfo() {
		return this.driverInfoEntity;
	}

	public void setDriverInfo(DriverInfoEntity driverInfoEntity) {
		this.driverInfoEntity = driverInfoEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_user_type")
	public AppUserTypeEntity getAppUserType() {
		return this.appUserTypeEntity;
	}

	public void setAppUserType(AppUserTypeEntity appUserTypeEntity) {
		this.appUserTypeEntity = appUserTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_detail_id")
	public PersonDetailEntity getPersonDetail() {
		return this.personDetailEntity;
	}

	public void setPersonDetail(PersonDetailEntity personDetailEntity) {
		this.personDetailEntity = personDetailEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.personEntity;
	}

	public void setPerson(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	@Column(name = "is_driver", length = 1)
	public String getIsDriver() {
		return this.isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	@Column(name = "user_date", length = 23)
	public Timestamp getUserDate() {
		return this.userDate;
	}

	public void setUserDate(Timestamp userDate) {
		this.userDate = userDate;
	}

	@Column(name = "app_user_no", length = 32)
	public String getAppUserNo() {
		return this.appUserNo;
	}

	public void setAppUserNo(String appUserNo) {
		this.appUserNo = appUserNo;
	}

	@Column(name = "registration_date", length = 18)
	public String getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Column(name = "user_no", length = 32)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "family_no", length = 32)
	public String getFamilyNo() {
		return this.familyNo;
	}

	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<AppUserBiometricEntity> getAppUserBiometrics() {
		return this.appUserBiometricEntities;
	}

	public void setAppUserBiometrics(Set<AppUserBiometricEntity> appUserBiometricEntities) {
		this.appUserBiometricEntities = appUserBiometricEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<TravelHistoryEntity> getTravelHistories() {
		return this.travelHistoryEntities;
	}

	public void setTravelHistories(Set<TravelHistoryEntity> travelHistoryEntities) {
		this.travelHistoryEntities = travelHistoryEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<UserRatingEntity> getUserRatings() {
		return this.userRatingEntities;
	}

	public void setUserRatings(Set<UserRatingEntity> userRatingEntities) {
		this.userRatingEntities = userRatingEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<DriverSuburbEntity> getDriverSuburbs() {
		return this.driverSuburbEntities;
	}

	public void setDriverSuburbs(Set<DriverSuburbEntity> driverSuburbEntities) {
		this.driverSuburbEntities = driverSuburbEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<UserRatingDetailEntity> getUserRatingDetails() {
		return this.userRatingDetailEntities;
	}

	public void setUserRatingDetails(Set<UserRatingDetailEntity> userRatingDetailEntities) {
		this.userRatingDetailEntities = userRatingDetailEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<RideCriteriaEntity> getRideCriterias() {
		return this.rideCriteriaEntities;
	}

	public void setRideCriterias(Set<RideCriteriaEntity> rideCriteriaEntities) {
		this.rideCriteriaEntities = rideCriteriaEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<RideSearchResultDetailEntity> getRideSearchResultDetails() {
		return this.rideSearchResultDetailEntities;
	}

	public void setRideSearchResultDetails(
			Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities) {
		this.rideSearchResultDetailEntities = rideSearchResultDetailEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserByUserFor")
	public Set<UserCommentEntity> getUserCommentsForUserFor() {
		return this.userCommentsForUserFor;
	}

	public void setUserCommentsForUserFor(
			Set<UserCommentEntity> userCommentsForUserFor) {
		this.userCommentsForUserFor = userCommentsForUserFor;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserByUserBy")
	public Set<UserCommentEntity> getUserCommentsForUserBy() {
		return this.userCommentsForUserBy;
	}

	public void setUserCommentsForUserBy(Set<UserCommentEntity> userCommentsForUserBy) {
		this.userCommentsForUserBy = userCommentsForUserBy;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<RideRequestResponseEntity> getRideRequestResponses() {
		return this.rideRequestResponseEntities;
	}

	public void setRideRequestResponses(
			Set<RideRequestResponseEntity> rideRequestResponseEntities) {
		this.rideRequestResponseEntities = rideRequestResponseEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<AppUserFileEntity> getAppUserFiles() {
		return this.appUserFileEntities;
	}

	public void setAppUserFiles(Set<AppUserFileEntity> appUserFileEntities) {
		this.appUserFileEntities = appUserFileEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<AppUserTypeCatsEntity> getAppUserTypeCatses() {
		return this.appUserTypeCatses;
	}

	public void setAppUserTypeCatses(Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.appUserTypeCatses = appUserTypeCatses;
	}

	@Column(name = "is_activated", length = 32)
	public String getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_status")
	public StatusEntity getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(StatusEntity userStatus) {
		this.userStatus = userStatus;
	}

}