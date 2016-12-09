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
 * UserRatingDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_rating_detail", schema = "dbo")
public class UserRatingDetailEntity  extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userRatingDetailId;
	private UserRatingEntity userRatingEntity;
	private AppUserEntity appUser;
	private Short value;
	private Timestamp ratingDate;
	private String rideNo;
	private Set<UserCommentEntity> userCommentEntities = new HashSet<UserCommentEntity>(0);

	// Constructors

	/** default constructor */
	public UserRatingDetailEntity() {
	}

	/** full constructor */
	public UserRatingDetailEntity(UserRatingEntity userRatingEntity, AppUserEntity appUser,
			Short value, Timestamp ratingDate, String rideNo,
			Set<UserCommentEntity> userCommentEntities) {
		this.userRatingEntity = userRatingEntity;
		this.appUser = appUser;
		this.value = value;
		this.ratingDate = ratingDate;
		this.rideNo = rideNo;
		this.userCommentEntities = userCommentEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_rating_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserRatingDetailId() {
		return this.userRatingDetailId;
	}

	public void setUserRatingDetailId(Integer userRatingDetailId) {
		this.userRatingDetailId = userRatingDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_rating_id")
	public UserRatingEntity getUserRating() {
		return this.userRatingEntity;
	}

	public void setUserRating(UserRatingEntity userRatingEntity) {
		this.userRatingEntity = userRatingEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rating_by")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "value")
	public Short getValue() {
		return this.value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

	@Column(name = "rating_date", length = 23)
	public Timestamp getRatingDate() {
		return this.ratingDate;
	}

	public void setRatingDate(Timestamp ratingDate) {
		this.ratingDate = ratingDate;
	}

	@Column(name = "ride_no", length = 32)
	public String getRideNo() {
		return this.rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRatingDetail")
	public Set<UserCommentEntity> getUserComments() {
		return this.userCommentEntities;
	}

	public void setUserComments(Set<UserCommentEntity> userCommentEntities) {
		this.userCommentEntities = userCommentEntities;
	}

}