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

/**
 * SurgeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "surge_info", schema = "dbo")
public class SurgeInfoEntity implements java.io.Serializable {

	// Fields

	private Integer surgeInfoId;
	private String name;
	private Set<RateInfoEntity> rateInfos = new HashSet<RateInfoEntity>(0);

	// Constructors

	/** default constructor */
	public SurgeInfoEntity() {
	}

	/** full constructor */
	public SurgeInfoEntity(String name, Set<RateInfoEntity> rateInfos) {
		this.name = name;
		this.rateInfos = rateInfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "surge_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSurgeInfoId() {
		return this.surgeInfoId;
	}

	public void setSurgeInfoId(Integer surgeInfoId) {
		this.surgeInfoId = surgeInfoId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "surgeInfo")
	public Set<RateInfoEntity> getRateInfos() {
		return this.rateInfos;
	}

	public void setRateInfos(Set<RateInfoEntity> rateInfos) {
		this.rateInfos = rateInfos;
	}

}