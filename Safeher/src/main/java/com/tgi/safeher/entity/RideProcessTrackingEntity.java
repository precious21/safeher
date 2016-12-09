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
 * RideProcessTracking entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ride_process_tracking", schema = "dbo")
public class RideProcessTrackingEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer rideProcessTrackingId;
	private String requestNo;
	private String rideNo;
	private String invoiceNo;
	private String isComplete;
	private AppUserEntity passenger;
	private AppUserEntity driver;
	private Set<RideQuickInfoEntity> rideQuickInfos = new HashSet<RideQuickInfoEntity>(0);

	// Constructors

	/** default constructor */
	public RideProcessTrackingEntity() {
	}

	/** full constructor */
	public RideProcessTrackingEntity(String requestNo, String rideNo,
			String invoiceNo, String isComplete,
			Set<RideQuickInfoEntity> rideQuickInfos) {
		this.requestNo = requestNo;
		this.rideNo = rideNo;
		this.invoiceNo = invoiceNo;
		this.isComplete = isComplete;
		this.rideQuickInfos = rideQuickInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ride_process_tracking_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRideProcessTrackingId() {
		return this.rideProcessTrackingId;
	}

	public void setRideProcessTrackingId(Integer rideProcessTrackingId) {
		this.rideProcessTrackingId = rideProcessTrackingId;
	}

	@Column(name = "request_no", length = 50)
	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@Column(name = "ride_no", length = 50)
	public String getRideNo() {
		return this.rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

	@Column(name = "invoice_no", length = 50)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "is_complete", length = 1)
	public String getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rideProcessTracking")
	public Set<RideQuickInfoEntity> getRideQuickInfos() {
		return this.rideQuickInfos;
	}

	public void setRideQuickInfos(Set<RideQuickInfoEntity> rideQuickInfos) {
		this.rideQuickInfos = rideQuickInfos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passenger")
	public AppUserEntity getPassenger() {
		return passenger;
	}

	public void setPassenger(AppUserEntity passenger) {
		this.passenger = passenger;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver")
	public AppUserEntity getDriver() {
		return driver;
	}

	public void setDriver(AppUserEntity driver) {
		this.driver = driver;
	}

}