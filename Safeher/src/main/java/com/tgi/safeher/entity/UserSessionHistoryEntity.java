package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserSessionHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_session_history", schema = "dbo")
public class UserSessionHistoryEntity implements java.io.Serializable {

	// Fields

	private Integer userSessionHistoryId;
	private UserCurrentSessionEntity userCurrentSession;
	private String sessionNo;
	private Timestamp startDatetime;
	private Timestamp endDatetime;
	private String isUserLogout;

	// Constructors

	/** default constructor */
	public UserSessionHistoryEntity() {
	}

	/** minimal constructor */
	public UserSessionHistoryEntity(Integer userSessionHistoryId) {
		this.userSessionHistoryId = userSessionHistoryId;
	}

	/** full constructor */
	public UserSessionHistoryEntity(Integer userSessionHistoryId,
			UserCurrentSessionEntity userCurrentSession, String sessionNo,
			Timestamp startDatetime, Timestamp endDatetime, String isUserLogout) {
		this.userSessionHistoryId = userSessionHistoryId;
		this.userCurrentSession = userCurrentSession;
		this.sessionNo = sessionNo;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.isUserLogout = isUserLogout;
	}

	// Property accessors
	@Id
	@Column(name = "user_session_history_id", unique = true, nullable = false, precision = 9, scale = 0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getUserSessionHistoryId() {
		return this.userSessionHistoryId;
	}

	public void setUserSessionHistoryId(Integer userSessionHistoryId) {
		this.userSessionHistoryId = userSessionHistoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_current_session_id")
	public UserCurrentSessionEntity getUserCurrentSession() {
		return this.userCurrentSession;
	}

	public void setUserCurrentSession(
			UserCurrentSessionEntity userCurrentSession) {
		this.userCurrentSession = userCurrentSession;
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

}