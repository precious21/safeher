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

/**
 * UserFavorities entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_favorities", schema = "dbo")
public class UserFavoritiesEntity implements java.io.Serializable {

	// Fields

	private Integer userFavoritiesId;
	private AppUserEntity appUser;
	private FavorityTypeEntity favorityType;
	private String fovLong;
	private String fovLat;
	private String lable;
	private String placeId;
	private String favoritiesLocation;

	// Constructors

	/** default constructor */
	public UserFavoritiesEntity() {
	}

	/** full constructor */
	public UserFavoritiesEntity(AppUserEntity appUser, FavorityTypeEntity favorityType,
			String fovLong, String fovLat, String lable , String favoritiesLocation) {
		this.appUser = appUser;
		this.favorityType = favorityType;
		this.fovLong = fovLong;
		this.fovLat = fovLat;
		this.lable = lable;
		this.favoritiesLocation = favoritiesLocation;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_favorities_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserFavoritiesId() {
		return this.userFavoritiesId;
	}

	public void setUserFavoritiesId(Integer userFavoritiesId) {
		this.userFavoritiesId = userFavoritiesId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "favority_type_id")
	public FavorityTypeEntity getFavorityType() {
		return this.favorityType;
	}

	public void setFavorityType(FavorityTypeEntity favorityType) {
		this.favorityType = favorityType;
	}

	@Column(name = "fov_long", length = 20)
	public String getFovLong() {
		return this.fovLong;
	}

	public void setFovLong(String fovLong) {
		this.fovLong = fovLong;
	}

	@Column(name = "fov_lat", length = 20)
	public String getFovLat() {
		return this.fovLat;
	}

	public void setFovLat(String fovLat) {
		this.fovLat = fovLat;
	}

	@Column(name = "lable", length = 20)
	public String getLable() {
		return this.lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	@Column(name = "favorities_location", length = 200)
	public String getFavoritiesLocation() {
		return this.favoritiesLocation;
	}

	public void setFavoritiesLocation(String favoritiesLocation) {
		this.favoritiesLocation = favoritiesLocation;
	}
	
	@Column(name = "place_id", length = 100)
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}