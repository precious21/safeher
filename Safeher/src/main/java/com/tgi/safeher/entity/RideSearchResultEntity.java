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
 * RideSearchResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_search_result", schema = "dbo")
public class RideSearchResultEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer rideSearchResult;
	private RideSearchEntity rideSearchEntity;
	private RideCriteriaEntity rideCriteriaEntity;
	private Integer totalOption;
	private Timestamp resultTime;
	private Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities = new HashSet<RideSearchResultDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public RideSearchResultEntity() {
	}

	/** full constructor */
	public RideSearchResultEntity(RideSearchEntity rideSearchEntity, RideCriteriaEntity rideCriteriaEntity,
			Integer totalOption, Timestamp resultTime,
			Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities) {
		this.rideSearchEntity = rideSearchEntity;
		this.rideCriteriaEntity = rideCriteriaEntity;
		this.totalOption = totalOption;
		this.resultTime = resultTime;
		this.rideSearchResultDetailEntities = rideSearchResultDetailEntities;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "rideSearchResultEntity_Id")
	@GenericGenerator( name = "rideSearchResultEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_search_result" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_search_result", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideSearchResult() {
		return this.rideSearchResult;
	}

	public void setRideSearchResult(Integer rideSearchResult) {
		this.rideSearchResult = rideSearchResult;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "ride_search_id")
	public RideSearchEntity getRideSearch() {
		return this.rideSearchEntity;
	}

	public void setRideSearch(RideSearchEntity rideSearchEntity) {
		this.rideSearchEntity = rideSearchEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteriaEntity;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteriaEntity) {
		this.rideCriteriaEntity = rideCriteriaEntity;
	}

	@Column(name = "total_option", precision = 5, scale = 0)
	public Integer getTotalOption() {
		return this.totalOption;
	}

	public void setTotalOption(Integer totalOption) {
		this.totalOption = totalOption;
	}

	@Column(name = "result_time", length = 23)
	public Timestamp getResultTime() {
		return this.resultTime;
	}

	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideSearchResult")
	public Set<RideSearchResultDetailEntity> getRideSearchResultDetails() {
		return this.rideSearchResultDetailEntities;
	}

	public void setRideSearchResultDetails(
			Set<RideSearchResultDetailEntity> rideSearchResultDetailEntities) {
		this.rideSearchResultDetailEntities = rideSearchResultDetailEntities;
	}

}