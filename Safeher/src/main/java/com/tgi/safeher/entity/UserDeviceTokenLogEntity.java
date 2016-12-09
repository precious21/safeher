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
 * UserDeviceTokenLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_device_token_log", schema = "dbo")
public class UserDeviceTokenLogEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer userDeviceTokenLogId;
	private UserLoginEntity userLogin;
	private String deviceToken;
	private Timestamp tokenDate;
	private String osType;
	private String tokenType;

	// Constructors

	/** default constructor */
	public UserDeviceTokenLogEntity() {
	}

	/** full constructor */
	public UserDeviceTokenLogEntity(UserLoginEntity userLogin, String deviceToken,
			Timestamp tokenDate, String osType, String tokenType) {
		this.userLogin = userLogin;
		this.deviceToken = deviceToken;
		this.tokenDate = tokenDate;
		this.osType = osType;
		this.tokenType = tokenType;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_device_token_log_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserDeviceTokenLogId() {
		return this.userDeviceTokenLogId;
	}

	public void setUserDeviceTokenLogId(Integer userDeviceTokenLogId) {
		this.userDeviceTokenLogId = userDeviceTokenLogId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_login_id")
	public UserLoginEntity getUserLogin() {
		return this.userLogin;
	}

	public void setUserLogin(UserLoginEntity userLogin) {
		this.userLogin = userLogin;
	}

	@Column(name = "device_token", length = 500)
	public String getDeviceToken() {
		return this.deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Column(name = "token_date", length = 23)
	public Timestamp getTokenDate() {
		return this.tokenDate;
	}

	public void setTokenDate(Timestamp tokenDate) {
		this.tokenDate = tokenDate;
	}

	@Column(name = "os_type", length = 1)
	public String getOsType() {
		return this.osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	@Column(name = "token_type", length = 20)
	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}