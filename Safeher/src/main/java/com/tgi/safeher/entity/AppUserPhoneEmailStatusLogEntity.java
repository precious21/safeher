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
 * AppUserPhoneEmailStatusLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_phone_email_status_log", schema = "dbo")
public class AppUserPhoneEmailStatusLogEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserPhoneEmailStatusLogId;
	private AppUserPhoneEmailStatusEntity appUserPhoneEmailStatus;
	private AppUserEntity appUser;
	private String primaryCell;
	private String primaryEmail;
	private String secondaryEmail;
	private String secondaryCell;
	private String code;

	// Constructors

	/** default constructor */
	public AppUserPhoneEmailStatusLogEntity() {
	}

	/** full constructor */
	public AppUserPhoneEmailStatusLogEntity(
			AppUserPhoneEmailStatusEntity appUserPhoneEmailStatus, AppUserEntity appUser,
			String primaryCell, String primaryEmail, String secondaryEmail,
			String secondaryCell, String code) {
		this.appUserPhoneEmailStatus = appUserPhoneEmailStatus;
		this.appUser = appUser;
		this.primaryCell = primaryCell;
		this.primaryEmail = primaryEmail;
		this.secondaryEmail = secondaryEmail;
		this.secondaryCell = secondaryCell;
		this.code = code;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_phone_email_status_log_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserPhoneEmailStatusLogId() {
		return this.appUserPhoneEmailStatusLogId;
	}

	public void setAppUserPhoneEmailStatusLogId(
			Integer appUserPhoneEmailStatusLogId) {
		this.appUserPhoneEmailStatusLogId = appUserPhoneEmailStatusLogId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_phone_email_status_id")
	public AppUserPhoneEmailStatusEntity getAppUserPhoneEmailStatus() {
		return this.appUserPhoneEmailStatus;
	}

	public void setAppUserPhoneEmailStatus(
			AppUserPhoneEmailStatusEntity appUserPhoneEmailStatus) {
		this.appUserPhoneEmailStatus = appUserPhoneEmailStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "primary_cell", length = 30)
	public String getPrimaryCell() {
		return this.primaryCell;
	}

	public void setPrimaryCell(String primaryCell) {
		this.primaryCell = primaryCell;
	}

	@Column(name = "primary_email", length = 60)
	public String getPrimaryEmail() {
		return this.primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	@Column(name = "secondary_email", length = 60)
	public String getSecondaryEmail() {
		return this.secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	@Column(name = "secondary_cell", length = 30)
	public String getSecondaryCell() {
		return this.secondaryCell;
	}

	public void setSecondaryCell(String secondaryCell) {
		this.secondaryCell = secondaryCell;
	}

	@Column(name = "code", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}