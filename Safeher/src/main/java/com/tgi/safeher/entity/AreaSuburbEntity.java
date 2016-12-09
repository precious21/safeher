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

/**
 * AreaSuburb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area_suburb", schema = "dbo")
public class AreaSuburbEntity implements java.io.Serializable {

	// Fields

	private Integer areaSuburbId;
	private AreaEntity area;
	private SuburbEntity suburb;
	private Set<ActiveDriverLocationEntity> activeDriverLocatoins = new HashSet<ActiveDriverLocationEntity>(
			0);
	private Set<PassengerSourceLocationEntity> passengerSourceLocatoins = new HashSet<PassengerSourceLocationEntity>(
			0);

	// Constructors

	/** default constructor */
	public AreaSuburbEntity() {
	}

	/** full constructor */
	public AreaSuburbEntity(AreaEntity area, SuburbEntity suburb,
			Set<ActiveDriverLocationEntity> activeDriverLocatoins,
			Set<DriverLocationTrackEntity> driverLocationTracks,
			Set<PassengerSourceLocationEntity> passengerSourceLocatoins) {
		this.area = area;
		this.suburb = suburb;
		this.activeDriverLocatoins = activeDriverLocatoins;
	
		this.passengerSourceLocatoins = passengerSourceLocatoins;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "area_suburb_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAreaSuburbId() {
		return this.areaSuburbId;
	}

	public void setAreaSuburbId(Integer areaSuburbId) {
		this.areaSuburbId = areaSuburbId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	public AreaEntity getArea() {
		return this.area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "suburb_id")
	public SuburbEntity getSuburb() {
		return this.suburb;
	}

	public void setSuburb(SuburbEntity suburb) {
		this.suburb = suburb;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "areaSuburb")
	public Set<ActiveDriverLocationEntity> getActiveDriverLocatoins() {
		return this.activeDriverLocatoins;
	}

	public void setActiveDriverLocatoins(
			Set<ActiveDriverLocationEntity> activeDriverLocatoins) {
		this.activeDriverLocatoins = activeDriverLocatoins;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "areaSuburb")
	public Set<PassengerSourceLocationEntity> getPassengerSourceLocatoins() {
		return this.passengerSourceLocatoins;
	}

	public void setPassengerSourceLocatoins(
			Set<PassengerSourceLocationEntity> passengerSourceLocatoins) {
		this.passengerSourceLocatoins = passengerSourceLocatoins;
	}

}