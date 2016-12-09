package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * SmsTempelte entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sms_tempelte", schema = "dbo")
public class SmsTempelteEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer smsTempelteId;
	private String smsBody;
	private String isMultimedia;
	private String smsCode;
	private String isAnyCode;
	private Set<SmsLogEntity> smsLogs = new HashSet<SmsLogEntity>(0);

	// Constructors

	/** default constructor */
	public SmsTempelteEntity() {
	}

	/** minimal constructor */
	public SmsTempelteEntity(String smsCode) {
		this.smsCode = smsCode;
	}

	/** full constructor */
	public SmsTempelteEntity(String smsBody, String isMultimedia, String smsCode,
			String isAnyCode, Set<SmsLogEntity> smsLogs) {
		this.smsBody = smsBody;
		this.isMultimedia = isMultimedia;
		this.smsCode = smsCode;
		this.isAnyCode = isAnyCode;
		this.smsLogs = smsLogs;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "sms_tempelte_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSmsTempelteId() {
		return this.smsTempelteId;
	}

	public void setSmsTempelteId(Integer smsTempelteId) {
		this.smsTempelteId = smsTempelteId;
	}

	@Column(name = "sms_body", length = 200)
	public String getSmsBody() {
		return this.smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	@Column(name = "is_multimedia", length = 1)
	public String getIsMultimedia() {
		return this.isMultimedia;
	}

	public void setIsMultimedia(String isMultimedia) {
		this.isMultimedia = isMultimedia;
	}

	@Column(name = "sms_code", nullable = false, length = 10)
	public String getSmsCode() {
		return this.smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Column(name = "is_any_code", length = 1)
	public String getIsAnyCode() {
		return this.isAnyCode;
	}

	public void setIsAnyCode(String isAnyCode) {
		this.isAnyCode = isAnyCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "smsTempelte")
	public Set<SmsLogEntity> getSmsLogs() {
		return this.smsLogs;
	}

	public void setSmsLogs(Set<SmsLogEntity> smsLogs) {
		this.smsLogs = smsLogs;
	}

}