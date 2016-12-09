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
 * UserCommentReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_comment_report", schema = "dbo")
public class UserCommentReportEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer userCommentReportId;
	private UserCommentEntity userComment;
	private AppUserEntity appUser;
	private Timestamp reportDate;
	private String decisionStatus;
	private Timestamp blockedDate;
	private String reviewUser;
	private String comment;

	// Constructors

	/** default constructor */
	public UserCommentReportEntity() {
	}

	/** full constructor */
	public UserCommentReportEntity(UserCommentEntity userComment, AppUserEntity appUser,
			Timestamp reportDate, String decisionStatus, Timestamp blockedDate,
			String reviewUser) {
		this.userComment = userComment;
		this.appUser = appUser;
		this.reportDate = reportDate;
		this.decisionStatus = decisionStatus;
		this.blockedDate = blockedDate;
		this.reviewUser = reviewUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_comment_report_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserCommentReportId() {
		return this.userCommentReportId;
	}

	public void setUserCommentReportId(Integer userCommentReportId) {
		this.userCommentReportId = userCommentReportId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_comment_id")
	public UserCommentEntity getUserComment() {
		return this.userComment;
	}

	public void setUserComment(UserCommentEntity userComment) {
		this.userComment = userComment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reported_by")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "report_date", length = 23)
	public Timestamp getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "decision_status", length = 100)
	public String getDecisionStatus() {
		return this.decisionStatus;
	}

	public void setDecisionStatus(String decisionStatus) {
		this.decisionStatus = decisionStatus;
	}

	@Column(name = "blocked_date", length = 23)
	public Timestamp getBlockedDate() {
		return this.blockedDate;
	}

	public void setBlockedDate(Timestamp blockedDate) {
		this.blockedDate = blockedDate;
	}

	@Column(name = "review_user", length = 35)
	public String getReviewUser() {
		return this.reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}
	@Column(name = "comment", length = 150)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}