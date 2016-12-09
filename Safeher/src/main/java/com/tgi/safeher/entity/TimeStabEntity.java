package com.tgi.safeher.entity;

import java.sql.Timestamp;
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

/**
 * TimeStab entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "time_stab", schema = "dbo")
public class TimeStabEntity implements java.io.Serializable {

	// Fields

	private Integer timeStabId;
	private String label;
	private Timestamp startTime;
	private Integer endTime;
	private Set<RateInfoEntity> rateInfos = new HashSet<RateInfoEntity>(0);

	// Constructors

	/** default constructor */
	public TimeStabEntity() {
	}

	/** full constructor */
	public TimeStabEntity(String label, Timestamp startTime, Integer endTime,
			Set<RateInfoEntity> rateInfos) {
		this.label = label;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rateInfos = rateInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "time_stab_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getTimeStabId() {
		return this.timeStabId;
	}

	public void setTimeStabId(Integer timeStabId) {
		this.timeStabId = timeStabId;
	}

	@Column(name = "label", length = 20)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "start_time", length = 23)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", precision = 9, scale = 0)
	public Integer getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "timeStab")
	public Set<RateInfoEntity> getRateInfos() {
		return this.rateInfos;
	}

	public void setRateInfos(Set<RateInfoEntity> rateInfos) {
		this.rateInfos = rateInfos;
	}

}