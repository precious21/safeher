package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * EmailLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "email_log", schema = "dbo")
public class EmailLogEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer emailLogId;
	private Timestamp emailTime;
	private String emailTo;
	private String emailCc;
	private String emailStatus;
	private String appUserId;
	private String emailResultStatus;
	private Integer emailTempleteId;
	private String toName;
	private String specialCode;

	// Constructors

	/** default constructor */
	public EmailLogEntity() {
	}

	/** full constructor */
	public EmailLogEntity(Timestamp emailTime, String emailTo, String emailCc,
			String emailStatus, String appUserId, String emailResultStatus,
			Integer emailTempleteId, String toName, String specialCode) {
		this.emailTime = emailTime;
		this.emailTo = emailTo;
		this.emailCc = emailCc;
		this.emailStatus = emailStatus;
		this.appUserId = appUserId;
		this.emailResultStatus = emailResultStatus;
		this.emailTempleteId = emailTempleteId;
		this.toName = toName;
		this.specialCode = specialCode;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "email_log_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEmailLogId() {
		return this.emailLogId;
	}

	public void setEmailLogId(Integer emailLogId) {
		this.emailLogId = emailLogId;
	}

	@Column(name = "email_time", length = 23)
	public Timestamp getEmailTime() {
		return this.emailTime;
	}

	public void setEmailTime(Timestamp emailTime) {
		this.emailTime = emailTime;
	}

	@Column(name = "email_to", length = 50)
	public String getEmailTo() {
		return this.emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	@Column(name = "email_cc", length = 50)
	public String getEmailCc() {
		return this.emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	@Column(name = "email_status", length = 20)
	public String getEmailStatus() {
		return this.emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	@Column(name = "app_user_id", length = 10)
	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	@Column(name = "email_result_status", length = 20)
	public String getEmailResultStatus() {
		return this.emailResultStatus;
	}

	public void setEmailResultStatus(String emailResultStatus) {
		this.emailResultStatus = emailResultStatus;
	}

	@Column(name = "email_templete_id", precision = 9, scale = 0)
	public Integer getEmailTempleteId() {
		return this.emailTempleteId;
	}

	public void setEmailTempleteId(Integer emailTempleteId) {
		this.emailTempleteId = emailTempleteId;
	}

	@Column(name = "to_name", length = 20)
	public String getToName() {
		return this.toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	@Column(name = "special_code", length = 20)
	public String getSpecialCode() {
		return this.specialCode;
	}

	public void setSpecialCode(String specialCode) {
		this.specialCode = specialCode;
	}

}