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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * Suburb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "suburb", schema = "dbo")
public class SuburbEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer suburbId;
	private StateProvinceEntity stateProvinceEntity;
	private CityEntity cityEntity;
	private CountryEntity countryEntity;
	private String name;
	private String neighborhood;
	private Set<RideCriteriaEntity> rideCriteriasForSuburbSource = new HashSet<RideCriteriaEntity>(
			0);
	private Set<RideCriteriaEntity> rideCriteriasForSuburbDestination = new HashSet<RideCriteriaEntity>(
			0);
	private Set<RideSearchEntity> rideSearchEntities = new HashSet<RideSearchEntity>(0);
	private Set<DriverSuburbEntity> driverSuburbEntities = new HashSet<DriverSuburbEntity>(0);

	// Constructors

	/** default constructor */
	public SuburbEntity() {
	}

	/** full constructor */
	public SuburbEntity(StateProvinceEntity stateProvinceEntity, CityEntity cityEntity, CountryEntity countryEntity,
			String name, Set<RideCriteriaEntity> rideCriteriasForSuburbSource,
			Set<RideCriteriaEntity> rideCriteriasForSuburbDestination,
			Set<RideSearchEntity> rideSearchEntities, Set<DriverSuburbEntity> driverSuburbEntities) {
		this.stateProvinceEntity = stateProvinceEntity;
		this.cityEntity = cityEntity;
		this.countryEntity = countryEntity;
		this.name = name;
		this.rideCriteriasForSuburbSource = rideCriteriasForSuburbSource;
		this.rideCriteriasForSuburbDestination = rideCriteriasForSuburbDestination;
		this.rideSearchEntities = rideSearchEntities;
		this.driverSuburbEntities = driverSuburbEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "suburb_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSuburbId() {
		return this.suburbId;
	}

	public void setSuburbId(Integer suburbId) {
		this.suburbId = suburbId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	public StateProvinceEntity getStateProvince() {
		return this.stateProvinceEntity;
	}

	public void setStateProvince(StateProvinceEntity stateProvinceEntity) {
		this.stateProvinceEntity = stateProvinceEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return this.cityEntity;
	}

	public void setCity(CityEntity cityEntity) {
		this.cityEntity = cityEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.countryEntity;
	}

	public void setCountry(CountryEntity countryEntity) {
		this.countryEntity = countryEntity;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "neighborhood", length = 32)
	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "suburbBySuburbSource")
	public Set<RideCriteriaEntity> getRideCriteriasForSuburbSource() {
		return this.rideCriteriasForSuburbSource;
	}

	public void setRideCriteriasForSuburbSource(
			Set<RideCriteriaEntity> rideCriteriasForSuburbSource) {
		this.rideCriteriasForSuburbSource = rideCriteriasForSuburbSource;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "suburbBySuburbDestination")
	public Set<RideCriteriaEntity> getRideCriteriasForSuburbDestination() {
		return this.rideCriteriasForSuburbDestination;
	}

	public void setRideCriteriasForSuburbDestination(
			Set<RideCriteriaEntity> rideCriteriasForSuburbDestination) {
		this.rideCriteriasForSuburbDestination = rideCriteriasForSuburbDestination;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "suburb")
	public Set<RideSearchEntity> getRideSearchs() {
		return this.rideSearchEntities;
	}

	public void setRideSearchs(Set<RideSearchEntity> rideSearchEntities) {
		this.rideSearchEntities = rideSearchEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "suburb")
	public Set<DriverSuburbEntity> getDriverSuburbs() {
		return this.driverSuburbEntities;
	}

	public void setDriverSuburbs(Set<DriverSuburbEntity> driverSuburbEntities) {
		this.driverSuburbEntities = driverSuburbEntities;
	}

}