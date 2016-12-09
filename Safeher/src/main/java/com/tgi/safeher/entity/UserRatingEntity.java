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
 * UserRating entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_rating", schema = "dbo")
public class UserRatingEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userRatingId;
	private AppUserEntity appUser;
	private Double currentValue;
	private Timestamp lastRatingDate;
	private Set<UserRatingDetailEntity> userRatingDetailEntities = new HashSet<UserRatingDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public UserRatingEntity() {
	}

	/** full constructor */
	public UserRatingEntity(AppUserEntity appUserEntity, Double currentValue,
			Timestamp lastRatingDate, Set<UserRatingDetailEntity> userRatingDetailEntities) {
		this.appUser = appUserEntity;
		this.currentValue = currentValue;
		this.lastRatingDate = lastRatingDate;
		this.userRatingDetailEntities = userRatingDetailEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_rating_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserRatingId() {
		return this.userRatingId;
	}

	public void setUserRatingId(Integer userRatingId) {
		this.userRatingId = userRatingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUser = appUserEntity;
	}

	@Column(name = "current_value")
	public Double getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(Double currentValue) {
		this.currentValue = currentValue;
	}

	@Column(name = "last_rating_date", length = 23)
	public Timestamp getLastRatingDate() {
		return this.lastRatingDate;
	}

	public void setLastRatingDate(Timestamp lastRatingDate) {
		this.lastRatingDate = lastRatingDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRating")
	public Set<UserRatingDetailEntity> getUserRatingDetails() {
		return this.userRatingDetailEntities;
	}

	public void setUserRatingDetails(Set<UserRatingDetailEntity> userRatingDetailEntities) {
		this.userRatingDetailEntities = userRatingDetailEntities;
	}

}