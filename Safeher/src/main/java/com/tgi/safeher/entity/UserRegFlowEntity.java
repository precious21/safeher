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
 * UserRegFlow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_reg_flow", schema = "dbo")
public class UserRegFlowEntity implements java.io.Serializable {

	// Fields

	private Integer userRegFlowId;
	private AppUserEntity appUserType;
	private String name;
	private String isOptional;
	private String isOnWeb;
	private String isOnApp;
	private String isVerificationRequired;
	private String isEmailSuccess;
	private String isEmailFail;
	private String isEmailCompletion;
	private Integer stepCode;
	private Set<AppUserRegTrackEntity> appUserRegTracks = new HashSet<AppUserRegTrackEntity>(
			0);

	// Constructors

	/** default constructor */
	public UserRegFlowEntity() {
	}

	/** full constructor */
	public UserRegFlowEntity(AppUserEntity appUserType, String name, String isOptional,
			String isOnWeb, String isOnApp, String isVerificationRequired,
			String isEmailSuccess, String isEmailFail,
			String isEmailCompletion, Integer stepCode,
			Set<AppUserRegTrackEntity> appUserRegTracks) {
		this.appUserType = appUserType;
		this.name = name;
		this.isOptional = isOptional;
		this.isOnWeb = isOnWeb;
		this.isOnApp = isOnApp;
		this.isVerificationRequired = isVerificationRequired;
		this.isEmailSuccess = isEmailSuccess;
		this.isEmailFail = isEmailFail;
		this.isEmailCompletion = isEmailCompletion;
		this.stepCode = stepCode;
		this.appUserRegTracks = appUserRegTracks;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_reg_flow_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserRegFlowId() {
		return this.userRegFlowId;
	}

	public void setUserRegFlowId(Integer userRegFlowId) {
		this.userRegFlowId = userRegFlowId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_type_id")
	public AppUserEntity getAppUserType() {
		return this.appUserType;
	}

	public void setAppUserType(AppUserEntity appUserType) {
		this.appUserType = appUserType;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_optional", length = 1)
	public String getIsOptional() {
		return this.isOptional;
	}

	public void setIsOptional(String isOptional) {
		this.isOptional = isOptional;
	}

	@Column(name = "is_on_web", length = 1)
	public String getIsOnWeb() {
		return this.isOnWeb;
	}

	public void setIsOnWeb(String isOnWeb) {
		this.isOnWeb = isOnWeb;
	}

	@Column(name = "is_on_app", length = 1)
	public String getIsOnApp() {
		return this.isOnApp;
	}

	public void setIsOnApp(String isOnApp) {
		this.isOnApp = isOnApp;
	}

	@Column(name = "is_verification_required", length = 1)
	public String getIsVerificationRequired() {
		return this.isVerificationRequired;
	}

	public void setIsVerificationRequired(String isVerificationRequired) {
		this.isVerificationRequired = isVerificationRequired;
	}

	@Column(name = "is_email_success", length = 1)
	public String getIsEmailSuccess() {
		return this.isEmailSuccess;
	}

	public void setIsEmailSuccess(String isEmailSuccess) {
		this.isEmailSuccess = isEmailSuccess;
	}

	@Column(name = "is_email_fail", length = 1)
	public String getIsEmailFail() {
		return this.isEmailFail;
	}

	public void setIsEmailFail(String isEmailFail) {
		this.isEmailFail = isEmailFail;
	}

	@Column(name = "is_email_completion", length = 1)
	public String getIsEmailCompletion() {
		return this.isEmailCompletion;
	}

	public void setIsEmailCompletion(String isEmailCompletion) {
		this.isEmailCompletion = isEmailCompletion;
	}

	@Column(name = "step_code", precision = 9, scale = 0)
	public Integer getStepCode() {
		return this.stepCode;
	}

	public void setStepCode(Integer stepCode) {
		this.stepCode = stepCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRegFlow")
	public Set<AppUserRegTrackEntity> getAppUserRegTracks() {
		return this.appUserRegTracks;
	}

	public void setAppUserRegTracks(Set<AppUserRegTrackEntity> appUserRegTracks) {
		this.appUserRegTracks = appUserRegTracks;
	}

}