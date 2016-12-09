package com.tgi.safeher.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * UserComment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_comment", schema = "dbo")
public class UserCommentEntity  extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userCommentId;
	private UserRatingDetailEntity userRatingDetailEntity;
	private AppUserEntity appUserByUserFor;
	private AppUserEntity appUserByUserBy;
	private String comments;
	private Timestamp commentDate;
	private Short rateValue;
	private String isReported;
	private String isBlocked;
	private String rideNo;

	// Constructors

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	/** default constructor */
	public UserCommentEntity() {
	}

	/** full constructor */
	public UserCommentEntity(UserRatingDetailEntity userRatingDetailEntity,
			AppUserEntity appUserByUserFor, AppUserEntity appUserByUserBy, String comments,
			Timestamp commentDate, Short rateValue) {
		this.userRatingDetailEntity = userRatingDetailEntity;
		this.appUserByUserFor = appUserByUserFor;
		this.appUserByUserBy = appUserByUserBy;
		this.comments = comments;
		this.commentDate = commentDate;
		this.rateValue = rateValue;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_comment_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserCommentId() {
		return this.userCommentId;
	}

	public void setUserCommentId(Integer userCommentId) {
		this.userCommentId = userCommentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_rating_detail_id")
	public UserRatingDetailEntity getUserRatingDetail() {
		return this.userRatingDetailEntity;
	}

	public void setUserRatingDetail(UserRatingDetailEntity userRatingDetailEntity) {
		this.userRatingDetailEntity = userRatingDetailEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_for")
	public AppUserEntity getAppUserByUserFor() {
		return this.appUserByUserFor;
	}

	public void setAppUserByUserFor(AppUserEntity appUserByUserFor) {
		this.appUserByUserFor = appUserByUserFor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_by")
	public AppUserEntity getAppUserByUserBy() {
		return this.appUserByUserBy;
	}

	public void setAppUserByUserBy(AppUserEntity appUserByUserBy) {
		this.appUserByUserBy = appUserByUserBy;
	}

	@Column(name = "comments", length = 512)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "comment_date", length = 23)
	public Timestamp getCommentDate() {
		return this.commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

	@Column(name = "rate_value")
	public Short getRateValue() {
		return this.rateValue;
	}

	public void setRateValue(Short rateValue) {
		this.rateValue = rateValue;
	}
	@Column(name = "is_reported", length = 1)
	public String getIsReported() {
		return this.isReported;
	}

	public void setIsReported(String isReported) {
		this.isReported = isReported;
	}

	@Column(name = "is_blocked", length = 1)
	public String getIsBlocked() {
		return this.isBlocked;
	}

	@Column(name = "ride_no", length = 30)
	public String getRideNo() {
		return rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

}