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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * AppUserType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_type", schema = "dbo")
public class AppUserTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserTypeId;
	private String name;
	private Set<UserTypeCatFilterEntity> userTypeCatFilterEntities = new HashSet<UserTypeCatFilterEntity>(
			0);
	private Set<AppUserEntity> appUserEntities = new HashSet<AppUserEntity>(0);
	private Set<TravelHistoryEntity> travelHistoryEntities = new HashSet<TravelHistoryEntity>(0);
	private Set<AppUserTypeCatsEntity> appUserTypeCatses = new HashSet<AppUserTypeCatsEntity>(
			0);

	// Constructors

	/** default constructor */
	public AppUserTypeEntity() {
	}

	/** full constructor */
	public AppUserTypeEntity(String name, Set<UserTypeCatFilterEntity> userTypeCatFilterEntities,
			Set<AppUserEntity> appUserEntities, Set<TravelHistoryEntity> travelHistoryEntities,
			Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.name = name;
		this.userTypeCatFilterEntities = userTypeCatFilterEntities;
		this.appUserEntities = appUserEntities;
		this.travelHistoryEntities = travelHistoryEntities;
		this.appUserTypeCatses = appUserTypeCatses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserTypeId() {
		return this.appUserTypeId;
	}

	public void setAppUserTypeId(Integer appUserTypeId) {
		this.appUserTypeId = appUserTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserType")
	public Set<UserTypeCatFilterEntity> getUserTypeCatFilters() {
		return this.userTypeCatFilterEntities;
	}

	public void setUserTypeCatFilters(Set<UserTypeCatFilterEntity> userTypeCatFilterEntities) {
		this.userTypeCatFilterEntities = userTypeCatFilterEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserType")
	public Set<AppUserEntity> getAppUsers() {
		return this.appUserEntities;
	}

	public void setAppUsers(Set<AppUserEntity> appUserEntities) {
		this.appUserEntities = appUserEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserType")
	public Set<TravelHistoryEntity> getTravelHistories() {
		return this.travelHistoryEntities;
	}

	public void setTravelHistories(Set<TravelHistoryEntity> travelHistoryEntities) {
		this.travelHistoryEntities = travelHistoryEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserType")
	public Set<AppUserTypeCatsEntity> getAppUserTypeCatses() {
		return this.appUserTypeCatses;
	}

	public void setAppUserTypeCatses(Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.appUserTypeCatses = appUserTypeCatses;
	}

}