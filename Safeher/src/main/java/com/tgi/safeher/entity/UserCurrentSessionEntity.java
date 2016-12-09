package com.tgi.safeher.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UserCurrentSession entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_current_session", schema = "dbo")
public class UserCurrentSessionEntity implements java.io.Serializable {

	// Fields

	private Integer userCurrentSessionId;
	private UserLoginEntity userLogin;
	private String sessionNo;
	private Timestamp startDatetime;
	private Timestamp endDatetime;
	private String isUserLogout;
	private Set<UserSessionHistoryEntity> userSessionHistories = new HashSet<UserSessionHistoryEntity>(
			0);

	// Constructors

	/** default constructor */
	public UserCurrentSessionEntity() {
	}

	/** minimal constructor */
	public UserCurrentSessionEntity(Integer userCurrentSessionId) {
		this.userCurrentSessionId = userCurrentSessionId;
	}

	/** full constructor */
	public UserCurrentSessionEntity(Integer userCurrentSessionId,
			UserLoginEntity userLogin, String sessionNo, Timestamp startDatetime,
			Timestamp endDatetime, String isUserLogout,
			Set<UserSessionHistoryEntity> userSessionHistories) {
		this.userCurrentSessionId = userCurrentSessionId;
		this.userLogin = userLogin;
		this.sessionNo = sessionNo;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.isUserLogout = isUserLogout;
		this.userSessionHistories = userSessionHistories;
	}

	// Property accessors
	@Id
	@Column(name = "user_current_session_id", unique = true, nullable = false, precision = 9, scale = 0)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getUserCurrentSessionId() {
		return this.userCurrentSessionId;
	}

	public void setUserCurrentSessionId(Integer userCurrentSessionId) {
		this.userCurrentSessionId = userCurrentSessionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_login_id")
	public UserLoginEntity getUserLogin() {
		return this.userLogin;
	}

	public void setUserLogin(UserLoginEntity userLogin) {
		this.userLogin = userLogin;
	}

	@Column(name = "session_no", length = 30)
	public String getSessionNo() {
		return this.sessionNo;
	}

	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	}

	@Column(name = "start_datetime", length = 23)
	public Timestamp getStartDatetime() {
		return this.startDatetime;
	}

	public void setStartDatetime(Timestamp startDatetime) {
		this.startDatetime = startDatetime;
	}

	@Column(name = "end_datetime", length = 23)
	public Timestamp getEndDatetime() {
		return this.endDatetime;
	}

	public void setEndDatetime(Timestamp endDatetime) {
		this.endDatetime = endDatetime;
	}

	@Column(name = "is_user_logout", length = 1)
	public String getIsUserLogout() {
		return this.isUserLogout;
	}

	public void setIsUserLogout(String isUserLogout) {
		this.isUserLogout = isUserLogout;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCurrentSession")
	public Set<UserSessionHistoryEntity> getUserSessionHistories() {
		return this.userSessionHistories;
	}

	public void setUserSessionHistories(
			Set<UserSessionHistoryEntity> userSessionHistories) {
		this.userSessionHistories = userSessionHistories;
	}

}