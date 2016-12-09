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
 * RideQuickInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_quick_info", schema = "dbo")
public class RideQuickInfoEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer notificationStatusId;
	private RideProcessTrackingEntity rideProcessTracking;
	private String passengerAppId;
	private String driverAppId;
	private String charityName;
	private String requestNo;
	private String charityId;
	private Timestamp sendingTime;
	private String notificationType;
	private String notificationMessage;

	// Constructors

	/** default constructor */
	public RideQuickInfoEntity() {
	}

	/** full constructor */
	public RideQuickInfoEntity(RideProcessTrackingEntity rideProcessTracking,
			String passengerAppId, String driverAppId, String charityName,
			String requestNo, String charityId, Timestamp sendingTime,
			String notificationType) {
		this.rideProcessTracking = rideProcessTracking;
		this.passengerAppId = passengerAppId;
		this.driverAppId = driverAppId;
		this.charityName = charityName;
		this.requestNo = requestNo;
		this.charityId = charityId;
		this.sendingTime = sendingTime;
		this.notificationType = notificationType;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "notification_status_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getNotificationStatusId() {
		return this.notificationStatusId;
	}

	public void setNotificationStatusId(Integer notificationStatusId) {
		this.notificationStatusId = notificationStatusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ride_process_tracking_id")
	public RideProcessTrackingEntity getRideProcessTracking() {
		return this.rideProcessTracking;
	}

	public void setRideProcessTracking(RideProcessTrackingEntity rideProcessTracking) {
		this.rideProcessTracking = rideProcessTracking;
	}

	@Column(name = "passenger_app_id", length = 15)
	public String getPassengerAppId() {
		return this.passengerAppId;
	}

	public void setPassengerAppId(String passengerAppId) {
		this.passengerAppId = passengerAppId;
	}

	@Column(name = "driver_app_id", length = 20)
	public String getDriverAppId() {
		return this.driverAppId;
	}

	public void setDriverAppId(String driverAppId) {
		this.driverAppId = driverAppId;
	}

	@Column(name = "charity_name", length = 70)
	public String getCharityName() {
		return this.charityName;
	}

	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}

	@Column(name = "request_no", length = 15)
	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@Column(name = "charity_id", length = 20)
	public String getCharityId() {
		return this.charityId;
	}

	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}

	@Column(name = "notification_type", length = 100)
	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@Column(name = "notification_message", length = 250)
	public String getNotificationMessage() {
		return this.notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	@Column(name = "sending_time", length = 23)
	public Timestamp getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(Timestamp sendingTime) {
		this.sendingTime = sendingTime;
	}
}