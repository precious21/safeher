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
 * RideSearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_search", schema = "dbo")
public class RideSearchEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideSearchId;
	private SuburbEntity suburbEntity;
	private RideCategoryEntity rideCategoryEntity;
	private RideCriteriaEntity rideCriteriaEntity;
	private Timestamp searchTime;
	private String searchFromLong;
	private String searchFromLat;
	private String isGotResult;
	private String isResearch;
	private String requestNo;
	private Set<RideSearchResultEntity> rideSearchResultEntities = new HashSet<RideSearchResultEntity>(
			0);

	// Constructors

	/** default constructor */
	public RideSearchEntity() {
	}

	/** full constructor */
	public RideSearchEntity(SuburbEntity suburbEntity, RideCategoryEntity rideCategoryEntity,
			RideCriteriaEntity rideCriteriaEntity, Timestamp searchTime,
			String searchFromLong, String searchFromLat, String isGotResult,
			String isResearch, Set<RideSearchResultEntity> rideSearchResultEntities) {
		this.suburbEntity = suburbEntity;
		this.rideCategoryEntity = rideCategoryEntity;
		this.rideCriteriaEntity = rideCriteriaEntity;
		this.searchTime = searchTime;
		this.searchFromLong = searchFromLong;
		this.searchFromLat = searchFromLat;
		this.isGotResult = isGotResult;
		this.isResearch = isResearch;
		this.rideSearchResultEntities = rideSearchResultEntities;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "rideSearchEntity_Id")
	@GenericGenerator( name = "rideSearchEntity_Id", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
						@Parameter( name = "sequence_name", value = "seq_ride_search" ),
						@Parameter( name = "optimizer", value = "pooled" ),
						@Parameter( name = "initial_value", value = "1" ),
						@Parameter( name = "increment_size", value = "1" )
						}
					)
	@Column(name = "ride_search_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideSearchId() {
		return this.rideSearchId;
	}

	public void setRideSearchId(Integer rideSearchId) {
		this.rideSearchId = rideSearchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_current")
	public SuburbEntity getSuburb() {
		return this.suburbEntity;
	}

	public void setSuburb(SuburbEntity suburbEntity) {
		this.suburbEntity = suburbEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_category_id")
	public RideCategoryEntity getRideCategory() {
		return this.rideCategoryEntity;
	}

	public void setRideCategory(RideCategoryEntity rideCategoryEntity) {
		this.rideCategoryEntity = rideCategoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "ride_criteria_id")
	public RideCriteriaEntity getRideCriteria() {
		return this.rideCriteriaEntity;
	}

	public void setRideCriteria(RideCriteriaEntity rideCriteriaEntity) {
		this.rideCriteriaEntity = rideCriteriaEntity;
	}

	@Column(name = "search_time", length = 23)
	public Timestamp getSearchTime() {
		return this.searchTime;
	}

	public void setSearchTime(Timestamp searchTime) {
		this.searchTime = searchTime;
	}

	@Column(name = "search_from_long", length = 32)
	public String getSearchFromLong() {
		return this.searchFromLong;
	}

	public void setSearchFromLong(String searchFromLong) {
		this.searchFromLong = searchFromLong;
	}

	@Column(name = "search_from_lat", length = 32)
	public String getSearchFromLat() {
		return this.searchFromLat;
	}

	public void setSearchFromLat(String searchFromLat) {
		this.searchFromLat = searchFromLat;
	}

	@Column(name = "is_got_result", length = 1)
	public String getIsGotResult() {
		return this.isGotResult;
	}

	public void setIsGotResult(String isGotResult) {
		this.isGotResult = isGotResult;
	}

	@Column(name = "is_research", length = 1)
	public String getIsResearch() {
		return this.isResearch;
	}

	public void setIsResearch(String isResearch) {
		this.isResearch = isResearch;
	}


	@Column(name = "request_no", length = 200)
	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideSearch")
	public Set<RideSearchResultEntity> getRideSearchResults() {
		return this.rideSearchResultEntities;
	}

	public void setRideSearchResults(Set<RideSearchResultEntity> rideSearchResultEntities) {
		this.rideSearchResultEntities = rideSearchResultEntities;
	}

}