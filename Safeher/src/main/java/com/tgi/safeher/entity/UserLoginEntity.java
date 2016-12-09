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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * UserLogin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_login", schema = "dbo")
public class UserLoginEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer userLoginId;
	private StatusEntity statusByStatusUser;
	private UserLoginTypeEntity userLoginType;
	private SignupTypeEntity signupType;
	private AppUserEntity appUser;
	private StatusEntity statusByStatusAccount;
	private String loginEmail;
	private String pswd;
	private String isActive;
	private Long currentLoginCount;
	private Short invalidCount;
	private String isSocial;
	private String keyToken;
	private String fcmToken;
	private String userNumber;
	private Short pinCode;
	private String isAuto;
	private String socialId;
	private String osType;
	private String isDev;
	private String invalidToken;
	private String currentSessionNo;
	private String userRegFlowId;
	private String isComplete;
	private String sex;
//	private String isOnline;
	
	// Constructors

	/** default constructor */
	public UserLoginEntity() {
	}

	/** minimal constructor */
	public UserLoginEntity(Integer userLoginId) {
		this.userLoginId = userLoginId;
	}

	/** full constructor */
	public UserLoginEntity(Integer userLoginId,
			StatusEntity statusByStatusUser, UserLoginTypeEntity userLoginType,
			SignupTypeEntity signupType, AppUserEntity appUser,
			StatusEntity statusByStatusAccount, String loginEmail,
			String password, String isActive, Long currentLoginCount,
			Short invalidCount, String isSocial, String keyToken,
			String userNumber, Short pinCode, String isAuto, String socialId) {
		this.userLoginId = userLoginId;
		this.statusByStatusUser = statusByStatusUser;
		this.userLoginType = userLoginType;
		this.signupType = signupType;
		this.appUser = appUser;
		this.statusByStatusAccount = statusByStatusAccount;
		this.loginEmail = loginEmail;
		this.pswd = password;
		this.isActive = isActive;
		this.currentLoginCount = currentLoginCount;
		this.invalidCount = invalidCount;
		this.isSocial = isSocial;
		this.keyToken = keyToken;
		this.userNumber = userNumber;
		this.pinCode = pinCode;
		this.isAuto = isAuto;
		this.socialId=socialId;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_login_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(Integer userLoginId) {
		this.userLoginId = userLoginId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_user")
	public StatusEntity getStatusByStatusUser() {
		return this.statusByStatusUser;
	}

	public void setStatusByStatusUser(StatusEntity statusByStatusUser) {
		this.statusByStatusUser = statusByStatusUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_login_type")
	public UserLoginTypeEntity getUserLoginType() {
		return this.userLoginType;
	}

	public void setUserLoginType(UserLoginTypeEntity userLoginType) {
		this.userLoginType = userLoginType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "signup_type_id")
	public SignupTypeEntity getSignupType() {
		return this.signupType;
	}

	public void setSignupType(SignupTypeEntity signupType) {
		this.signupType = signupType;
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
	@JoinColumn(name = "status_account")
	public StatusEntity getStatusByStatusAccount() {
		return this.statusByStatusAccount;
	}

	public void setStatusByStatusAccount(StatusEntity statusByStatusAccount) {
		this.statusByStatusAccount = statusByStatusAccount;
	}

	@Column(name = "login_email", length = 100)
	public String getLoginEmail() {
		return this.loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "current_login_count", precision = 11, scale = 0)
	public Long getCurrentLoginCount() {
		return this.currentLoginCount;
	}

	public void setCurrentLoginCount(Long currentLoginCount) {
		this.currentLoginCount = currentLoginCount;
	}

	@Column(name = "invalid_count", precision = 3, scale = 0)
	public Short getInvalidCount() {
		return this.invalidCount;
	}

	public void setInvalidCount(Short invalidCount) {
		this.invalidCount = invalidCount;
	}

	@Column(name = "is_social", length = 20)
	public String getIsSocial() {
		return this.isSocial;
	}

	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}

	@Column(name = "key_token", length = 200)
	public String getKeyToken() {
		return this.keyToken;
	}

	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}

	@Column(name = "user_number", length = 20)
	public String getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	@Column(name = "pin_code", precision = 4, scale = 0)
	public Short getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(Short pinCode) {
		this.pinCode = pinCode;
	}

	@Column(name = "is_auto", length = 1)
	public String getIsAuto() {
		return this.isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	@Column(name = "social_id", length = 200)
	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	@Column(name = "password", length = 100)
	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	@Column(name = "os_type", length = 1)
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	@Column(name = "is_dev", length = 1)
	public String getIsDev() {
		return isDev;
	}

	public void setIsDev(String isDev) {
		this.isDev = isDev;
	}

	@Column(name = "current_session_no", length = 30)
	public String getCurrentSessionNo() {
		return this.currentSessionNo;
	}

	public void setCurrentSessionNo(String currentSessionNo) {
		this.currentSessionNo = currentSessionNo;
	}

	@Column(name = "invalid_token", length = 1)
	public String getInvalidToken() {
		return invalidToken;
	}

	public void setInvalidToken(String invalidToken) {
		this.invalidToken = invalidToken;
	}

	@Column(name = "fcm_token", length = 300)
	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	//TODO: to be implement later
	/*
	@Column(name = "is_online", length = 1)
	 public String getIsOnline() {
	  return this.isOnline;
	 }

	 public void setIsOnline(String isOnline) {
	  this.isOnline = isOnline;
	 }
*/
	@Column(name = "is_complete", length = 1)
	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	@Column(name = "user_reg_flow_id", length = 10)
	public String getUserRegFlowId() {
		return userRegFlowId;
	}

	public void setUserRegFlowId(String userRegFlowId) {
		this.userRegFlowId = userRegFlowId;
	}

	@Column(name = "sex", length = 20)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}