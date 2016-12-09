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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * AppUserPhoneEmailStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_phone_email_status", schema = "dbo")
public class AppUserPhoneEmailStatusEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserPhoneEmailStatusId;
	private String verificationType;
	private String verified;
	private String notVerified;
	private String pending;
	private Set<AppUserPhoneEmailStatusLogEntity> appUserPhoneEmailStatusLogs = new HashSet<AppUserPhoneEmailStatusLogEntity>(
			0);

	// Constructors

	/** default constructor */
	public AppUserPhoneEmailStatusEntity() {
	}

	/** full constructor */
	public AppUserPhoneEmailStatusEntity( String verificationType,
			Set<AppUserPhoneEmailStatusLogEntity> appUserPhoneEmailStatusLogs) {
	
		this.verificationType = verificationType;
		this.appUserPhoneEmailStatusLogs = appUserPhoneEmailStatusLogs;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_phone_email_status_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserPhoneEmailStatusId() {
		return this.appUserPhoneEmailStatusId;
	}

	public void setAppUserPhoneEmailStatusId(Integer appUserPhoneEmailStatusId) {
		this.appUserPhoneEmailStatusId = appUserPhoneEmailStatusId;
	}

	@Column(name = "Verification_type", length = 20)
	public String getVerificationType() {
		return this.verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserPhoneEmailStatus")
	public Set<AppUserPhoneEmailStatusLogEntity> getAppUserPhoneEmailStatusLogs() {
		return this.appUserPhoneEmailStatusLogs;
	}

	public void setAppUserPhoneEmailStatusLogs(
			Set<AppUserPhoneEmailStatusLogEntity> appUserPhoneEmailStatusLogs) {
		this.appUserPhoneEmailStatusLogs = appUserPhoneEmailStatusLogs;
	}
	@Column(name = "verified", length = 1)
	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	@Column(name = "not_verified", length = 1)
	public String getNotVerified() {
		return notVerified;
	}

	public void setNotVerified(String notVerified) {
		this.notVerified = notVerified;
	}

	@Column(name = "pending", length = 1)
	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

}