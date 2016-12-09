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
 * RideCriteria entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_criteria", schema = "dbo")
public class RideCriteriaEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideCriteriaId;
	private SuburbEntity suburbBySuburbDestination;
	private SuburbEntity suburbBySuburbSource;
	private AppUserEntity appUser;
	private Integer rideTypeId;
	private String sourceLong;
	private String sourceLat;
	private String isShared;
	private String isFav;
	private Short noPassenger;
	private Short noChild;
	private Short noSeats;
	private String destinationLong;
	private String destinationLat;
	private String isGift;
	private String distinationAddress;
	private String sourceAddress;
	private Set<RideEntity> rideEntities = new HashSet<RideEntity>(0);
	private Set<RideFinalizeEntity> rideFinalizeEntities = new HashSet<RideFinalizeEntity>(0);
	private Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities = new HashSet<RideCriteriaDetailEntity>(
			0);
	private Set<RideSearchResultEntity> rideSearchResultEntities = new HashSet<RideSearchResultEntity>(
			0);
	private Set<RideSearchEntity> rideSearchEntities = new HashSet<RideSearchEntity>(0);

	// Constructors

	/** default constructor */
	public RideCriteriaEntity() {
	}

	/** full constructor */
	public RideCriteriaEntity(SuburbEntity suburbBySuburbDestination,
			SuburbEntity suburbBySuburbSource, AppUserEntity appUserEntity, Integer rideTypeId,
			String sourceLong, String sourceLat, String isShared, String isFav,
			Short noPassenger, Short noChild, Short noSeats, Set<RideEntity> rideEntities,
			Set<RideFinalizeEntity> rideFinalizeEntities,
			Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities,
			Set<RideSearchResultEntity> rideSearchResultEntities, Set<RideSearchEntity> rideSearchEntities) {
		this.suburbBySuburbDestination = suburbBySuburbDestination;
		this.suburbBySuburbSource = suburbBySuburbSource;
		this.appUser = appUserEntity;
		this.rideTypeId = rideTypeId;
		this.sourceLong = sourceLong;
		this.sourceLat = sourceLat;
		this.isShared = isShared;
		this.isFav = isFav;
		this.noPassenger = noPassenger;
		this.noChild = noChild;
		this.noSeats = noSeats;
		this.rideEntities = rideEntities;
		this.rideFinalizeEntities = rideFinalizeEntities;
		this.rideCriteriaDetailEntities = rideCriteriaDetailEntities;
		this.rideSearchResultEntities = rideSearchResultEntities;
		this.rideSearchEntities = rideSearchEntities;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "rideCriteriaEntity_Id")
	@GenericGenerator( name = "rideCriteriaEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_criteria" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_criteria_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideCriteriaId() {
		return this.rideCriteriaId;
	}

	public void setRideCriteriaId(Integer rideCriteriaId) {
		this.rideCriteriaId = rideCriteriaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_destination")
	public SuburbEntity getSuburbBySuburbDestination() {
		return this.suburbBySuburbDestination;
	}

	public void setSuburbBySuburbDestination(SuburbEntity suburbBySuburbDestination) {
		this.suburbBySuburbDestination = suburbBySuburbDestination;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_source")
	public SuburbEntity getSuburbBySuburbSource() {
		return this.suburbBySuburbSource;
	}

	public void setSuburbBySuburbSource(SuburbEntity suburbBySuburbSource) {
		this.suburbBySuburbSource = suburbBySuburbSource;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "search_user")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUser = appUserEntity;
	}

	@Column(name = "ride_type_id", precision = 9, scale = 0)
	public Integer getRideTypeId() {
		return this.rideTypeId;
	}

	public void setRideTypeId(Integer rideTypeId) {
		this.rideTypeId = rideTypeId;
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

	@Column(name = "is_shared", length = 1)
	public String getIsShared() {
		return this.isShared;
	}

	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}

	@Column(name = "is_fav", length = 1)
	public String getIsFav() {
		return this.isFav;
	}

	public void setIsFav(String isFav) {
		this.isFav = isFav;
	}

	@Column(name = "no_passenger")
	public Short getNoPassenger() {
		return this.noPassenger;
	}

	public void setNoPassenger(Short noPassenger) {
		this.noPassenger = noPassenger;
	}

	@Column(name = "no_child")
	public Short getNoChild() {
		return this.noChild;
	}

	public void setNoChild(Short noChild) {
		this.noChild = noChild;
	}

	@Column(name = "no_seats")
	public Short getNoSeats() {
		return this.noSeats;
	}

	public void setNoSeats(Short noSeats) {
		this.noSeats = noSeats;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCriteria")
	public Set<RideEntity> getRides() {
		return this.rideEntities;
	}

	public void setRides(Set<RideEntity> rideEntities) {
		this.rideEntities = rideEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCriteria")
	public Set<RideFinalizeEntity> getRideFinalizes() {
		return this.rideFinalizeEntities;
	}

	public void setRideFinalizes(Set<RideFinalizeEntity> rideFinalizeEntities) {
		this.rideFinalizeEntities = rideFinalizeEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCriteria")
	public Set<RideCriteriaDetailEntity> getRideCriteriaDetails() {
		return this.rideCriteriaDetailEntities;
	}

	public void setRideCriteriaDetails(
			Set<RideCriteriaDetailEntity> rideCriteriaDetailEntities) {
		this.rideCriteriaDetailEntities = rideCriteriaDetailEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCriteria")
	public Set<RideSearchResultEntity> getRideSearchResults() {
		return this.rideSearchResultEntities;
	}

	public void setRideSearchResults(Set<RideSearchResultEntity> rideSearchResultEntities) {
		this.rideSearchResultEntities = rideSearchResultEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideCriteria")
	public Set<RideSearchEntity> getRideSearchs() {
		return this.rideSearchEntities;
	}

	public void setRideSearchs(Set<RideSearchEntity> rideSearchEntities) {
		this.rideSearchEntities = rideSearchEntities;
	}
	@Column(name = "distination_lat")
	public String getDestinationLat() {
		return destinationLat;
	}

	public void setDestinationLat(String destinationLat) {
		this.destinationLat = destinationLat;
	}

	@Column(name = "distination_long")
	public String getDestinationLong() {
		return destinationLong;
	}

	
	public void setDestinationLong(String destinationLong) {
		this.destinationLong = destinationLong;
	}
	
	@Column(name = "is_gift", length = 1)
	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}

	@Column(name = "distination_address", length = 200)
	public String getDistinationAddress() {
		return this.distinationAddress;
	}

	public void setDistinationAddress(String distinationAddress) {
		this.distinationAddress = distinationAddress;
	}

	@Column(name = "source_address", length = 200)
	public String getSourceAddress() {
		return this.sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
}