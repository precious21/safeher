package com.tgi.safeher.entity;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * AppUserRegTrack entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_reg_track", schema = "dbo")
public class AppUserRegTrackEntity implements java.io.Serializable {

	// Fields

	private Integer appUserRegTrackId;
	private Timestamp actionTme;
	private String isCompleted;
	private String isFromApp;
	private String isVerified;
	private String isCompletionEmailSent;
	private String isVerificationEmailSent;
	private Timestamp verificationEmailTime;
	private String completionEmailTime;
	private UserRegFlowEntity userRegFlow;
	private AppUserEntity appUser;

	// Constructors
		
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_reg_track_id", nullable = false, precision = 9, scale = 0)
	public Integer getAppUserRegTrackId() {
		return this.appUserRegTrackId;
	}

	public void setAppUserRegTrackId(Integer appUserRegTrackId) {
		this.appUserRegTrackId = appUserRegTrackId;
	}
	
	@Column(name = "action_tme", length = 23)
	public Timestamp getActionTme() {
		return this.actionTme;
	}

	public void setActionTme(Timestamp actionTme) {
		this.actionTme = actionTme;
	}

	@Column(name = "is_completed", length = 1)
	public String getIsCompleted() {
		return this.isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Column(name = "is_from_app", length = 1)
	public String getIsFromApp() {
		return this.isFromApp;
	}

	public void setIsFromApp(String isFromApp) {
		this.isFromApp = isFromApp;
	}

	@Column(name = "is_verified", length = 1)
	public String getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	@Column(name = "is_completion_email_sent", length = 1)
	public String getIsCompletionEmailSent() {
		return this.isCompletionEmailSent;
	}

	public void setIsCompletionEmailSent(String isCompletionEmailSent) {
		this.isCompletionEmailSent = isCompletionEmailSent;
	}

	@Column(name = "is_verification_email_sent", length = 1)
	public String getIsVerificationEmailSent() {
		return this.isVerificationEmailSent;
	}

	public void setIsVerificationEmailSent(String isVerificationEmailSent) {
		this.isVerificationEmailSent = isVerificationEmailSent;
	}

	@Column(name = "verification_email_time", length = 23)
	public Timestamp getVerificationEmailTime() {
		return this.verificationEmailTime;
	}

	public void setVerificationEmailTime(Timestamp verificationEmailTime) {
		this.verificationEmailTime = verificationEmailTime;
	}

	@Column(name = "completion_email_time", length = 18)
	public String getCompletionEmailTime() {
		return this.completionEmailTime;
	}

	public void setCompletionEmailTime(String completionEmailTime) {
		this.completionEmailTime = completionEmailTime;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_reg_flow_id", insertable = true, updatable = true)
	public UserRegFlowEntity getUserRegFlow() {
		return this.userRegFlow;
	}

	public void setUserRegFlow(UserRegFlowEntity userRegFlow) {
		this.userRegFlow = userRegFlow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id", insertable = true, updatable = true)
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

}