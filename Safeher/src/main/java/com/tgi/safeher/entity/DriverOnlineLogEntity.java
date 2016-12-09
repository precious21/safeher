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
 * DriverOnlineLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "driver_online_log", schema = "dbo")
public class DriverOnlineLogEntity implements java.io.Serializable {

	// Fields

	private Integer driverOnlineLogId;
	private AppUserEntity appUser;
	private String onlineTime;
	private String offlineTime;
	private String selfOffline;
	private Set<DriverOnlineDetailEntity> driverOnlineDetails = new HashSet<DriverOnlineDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public DriverOnlineLogEntity() {
	}

	/** full constructor */
	public DriverOnlineLogEntity(AppUserEntity appUser, String onlineTime,
			String offlineTime, String selfOffline,
			Set<DriverOnlineDetailEntity> driverOnlineDetails) {
		this.appUser = appUser;
		this.onlineTime = onlineTime;
		this.offlineTime = offlineTime;
		this.selfOffline = selfOffline;
		this.driverOnlineDetails = driverOnlineDetails;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "driver_online_log_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getDriverOnlineLogId() {
		return this.driverOnlineLogId;
	}

	public void setDriverOnlineLogId(Integer driverOnlineLogId) {
		this.driverOnlineLogId = driverOnlineLogId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "online_time", length = 30)
	public String getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	@Column(name = "offline_time", length = 30)
	public String getOfflineTime() {
		return this.offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	@Column(name = "self_offline", length = 20)
	public String getSelfOffline() {
		return this.selfOffline;
	}

	public void setSelfOffline(String selfOffline) {
		this.selfOffline = selfOffline;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "driverOnlineLog")
	public Set<DriverOnlineDetailEntity> getDriverOnlineDetails() {
		return this.driverOnlineDetails;
	}

	public void setDriverOnlineDetails(
			Set<DriverOnlineDetailEntity> driverOnlineDetails) {
		this.driverOnlineDetails = driverOnlineDetails;
	}

}