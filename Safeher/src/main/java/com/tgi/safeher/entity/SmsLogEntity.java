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
 * SmsLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sms_log", schema = "dbo")
public class SmsLogEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer smsLog;
	private Integer smsTempelte;
	private String toNumber;
	private Timestamp sentTime;
	private String smsStatus;
	private String smsResultStatus;
	private String specialCode;
	private String appUserId;

	// Constructors

	/** default constructor */
	public SmsLogEntity() {
	}

	/** full constructor */
	public SmsLogEntity(Integer smsTempelte, String toNumber, Timestamp sentTime,
			String smsStatus, String smsResultStatus, String specialCode,
			String appUserId) {
		this.smsTempelte = smsTempelte;
		this.toNumber = toNumber;
		this.sentTime = sentTime;
		this.smsStatus = smsStatus;
		this.smsResultStatus = smsResultStatus;
		this.specialCode = specialCode;
		this.appUserId = appUserId;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "sms_log", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSmsLog() {
		return this.smsLog;
	}

	public void setSmsLog(Integer smsLog) {
		this.smsLog = smsLog;
	}

	@Column(name = "sms_tempelte_id", precision = 9)
	public Integer getSmsTempelte() {
		return this.smsTempelte;
	}

	public void setSmsTempelte(Integer smsTempelte) {
		this.smsTempelte = smsTempelte;
	}

	@Column(name = "to_number", length = 20)
	public String getToNumber() {
		return this.toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	@Column(name = "sent_time", length = 23)
	public Timestamp getSentTime() {
		return this.sentTime;
	}

	public void setSentTime(Timestamp sentTime) {
		this.sentTime = sentTime;
	}

	@Column(name = "sms_status", length = 20)
	public String getSmsStatus() {
		return this.smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	@Column(name = "sms_result_status", length = 20)
	public String getSmsResultStatus() {
		return this.smsResultStatus;
	}

	public void setSmsResultStatus(String smsResultStatus) {
		this.smsResultStatus = smsResultStatus;
	}

	@Column(name = "special_code", length = 20)
	public String getSpecialCode() {
		return this.specialCode;
	}

	public void setSpecialCode(String specialCode) {
		this.specialCode = specialCode;
	}

	@Column(name = "app_user_id", length = 10)
	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

}