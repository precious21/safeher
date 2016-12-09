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

/**
 * UserSelectedCharities entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_selected_charities", schema = "dbo")
public class UserSelectedCharitiesEntity implements java.io.Serializable {

	// Fields

	private Integer userSelectedCharitiesId;
	private CharitiesEntity charities;
	private UserCharitiesEntity userCharities;
	private String isActive;
	private Timestamp deactiveDate;

	// Constructors

	/** default constructor */
	public UserSelectedCharitiesEntity() {
	}

	/** full constructor */
	public UserSelectedCharitiesEntity(CharitiesEntity charities,
			UserCharitiesEntity userCharities, String isActive, Timestamp deactiveDate) {
		this.charities = charities;
		this.userCharities = userCharities;
		this.isActive = isActive;
		this.deactiveDate = deactiveDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_selected_charities_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserSelectedCharitiesId() {
		return this.userSelectedCharitiesId;
	}

	public void setUserSelectedCharitiesId(Integer userSelectedCharitiesId) {
		this.userSelectedCharitiesId = userSelectedCharitiesId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charities_id")
	public CharitiesEntity getCharities() {
		return this.charities;
	}

	public void setCharities(CharitiesEntity charities) {
		this.charities = charities;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_charities")
	public UserCharitiesEntity getUserCharities() {
		return this.userCharities;
	}

	public void setUserCharities(UserCharitiesEntity userCharities) {
		this.userCharities = userCharities;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "deactive_date", length = 23)
	public Timestamp getDeactiveDate() {
		return this.deactiveDate;
	}

	public void setDeactiveDate(Timestamp deactiveDate) {
		this.deactiveDate = deactiveDate;
	}

}