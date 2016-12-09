package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Charities entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "charities", schema = "dbo")
public class CharitiesEntity implements java.io.Serializable {

	// Fields

	private Integer charitiesId;
	private String name;
	private String description;
	private Double rating;
	private Byte popularity;
	private Set<UserSelectedCharitiesEntity> userSelectedCharitieses = new HashSet<UserSelectedCharitiesEntity>(
			0);
	private Set<RideCharityEntity> rideCharities = new HashSet<RideCharityEntity>(0);

	// Constructors

	/** default constructor */
	public CharitiesEntity() {
	}

	/** full constructor */
	public CharitiesEntity(String name, String description, Double rating,
			Byte popularity,
			Set<UserSelectedCharitiesEntity> userSelectedCharitieses,
			Set<RideCharityEntity> rideCharities) {
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.popularity = popularity;
		this.userSelectedCharitieses = userSelectedCharitieses;
		this.rideCharities = rideCharities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "charities_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getCharitiesId() {
		return this.charitiesId;
	}

	public void setCharitiesId(Integer charitiesId) {
		this.charitiesId = charitiesId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "rating", precision = 4)
	public Double getRating() {
		return this.rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Column(name = "popularity", precision = 2, scale = 0)
	public Byte getPopularity() {
		return this.popularity;
	}

	public void setPopularity(Byte popularity) {
		this.popularity = popularity;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "charities")
	public Set<UserSelectedCharitiesEntity> getUserSelectedCharitieses() {
		return this.userSelectedCharitieses;
	}

	public void setUserSelectedCharitieses(
			Set<UserSelectedCharitiesEntity> userSelectedCharitieses) {
		this.userSelectedCharitieses = userSelectedCharitieses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "charities")
	public Set<RideCharityEntity> getRideCharities() {
		return this.rideCharities;
	}

	public void setRideCharities(Set<RideCharityEntity> rideCharities) {
		this.rideCharities = rideCharities;
	}

}