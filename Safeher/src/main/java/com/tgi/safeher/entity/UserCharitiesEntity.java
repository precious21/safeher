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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserCharities entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_charities", schema = "dbo")
public class UserCharitiesEntity implements java.io.Serializable {

	// Fields

	private Integer userCharities;
	private AppUserEntity appUser;
	private Timestamp selectionDate;
	private String isSelectionPending;
	private String isSelectionBySafeher;
	private Set<UserSelectedCharitiesEntity> userSelectedCharitieses = new HashSet<UserSelectedCharitiesEntity>(
			0);

	// Constructors

	/** default constructor */
	public UserCharitiesEntity() {
	}

	/** full constructor */
	public UserCharitiesEntity(AppUserEntity appUser, Timestamp selectionDate,
			String isSelectionPending, String isSelectionBySafeher,
			Set<UserSelectedCharitiesEntity> userSelectedCharitieses) {
		this.appUser = appUser;
		this.selectionDate = selectionDate;
		this.isSelectionPending = isSelectionPending;
		this.isSelectionBySafeher = isSelectionBySafeher;
		this.userSelectedCharitieses = userSelectedCharitieses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_charities", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserCharities() {
		return this.userCharities;
	}

	public void setUserCharities(Integer userCharities) {
		this.userCharities = userCharities;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@Column(name = "selection_date", length = 23)
	public Timestamp getSelectionDate() {
		return this.selectionDate;
	}

	public void setSelectionDate(Timestamp selectionDate) {
		this.selectionDate = selectionDate;
	}

	@Column(name = "is_selection_pending", length = 1)
	public String getIsSelectionPending() {
		return this.isSelectionPending;
	}

	public void setIsSelectionPending(String isSelectionPending) {
		this.isSelectionPending = isSelectionPending;
	}

	@Column(name = "is_selection_by_safeher", length = 1)
	public String getIsSelectionBySafeher() {
		return this.isSelectionBySafeher;
	}

	public void setIsSelectionBySafeher(String isSelectionBySafeher) {
		this.isSelectionBySafeher = isSelectionBySafeher;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCharities")
	public Set<UserSelectedCharitiesEntity> getUserSelectedCharitieses() {
		return this.userSelectedCharitieses;
	}

	public void setUserSelectedCharitieses(
			Set<UserSelectedCharitiesEntity> userSelectedCharitieses) {
		this.userSelectedCharitieses = userSelectedCharitieses;
	}

}