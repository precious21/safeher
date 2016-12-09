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
 * AppUserTypeCats entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_type_cats", schema = "dbo")
public class AppUserTypeCatsEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserTypeCatsId;
	private AppUserTypeEntity appUserTypeEntity;
	private UserCategoryEntity userCategoryEntity;
	private AppUserEntity appUserEntity;
	private String isActive;
	private Timestamp activeDate;
	private Timestamp deactiveDate;

	// Constructors

	/** default constructor */
	public AppUserTypeCatsEntity() {
	}

	/** full constructor */
	public AppUserTypeCatsEntity(AppUserTypeEntity appUserTypeEntity, UserCategoryEntity userCategoryEntity,
			AppUserEntity appUserEntity, String isActive, Timestamp activeDate,
			Timestamp deactiveDate) {
		this.appUserTypeEntity = appUserTypeEntity;
		this.userCategoryEntity = userCategoryEntity;
		this.appUserEntity = appUserEntity;
		this.isActive = isActive;
		this.activeDate = activeDate;
		this.deactiveDate = deactiveDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_type_cats_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserTypeCatsId() {
		return this.appUserTypeCatsId;
	}

	public void setAppUserTypeCatsId(Integer appUserTypeCatsId) {
		this.appUserTypeCatsId = appUserTypeCatsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_type_id")
	public AppUserTypeEntity getAppUserType() {
		return this.appUserTypeEntity;
	}

	public void setAppUserType(AppUserTypeEntity appUserTypeEntity) {
		this.appUserTypeEntity = appUserTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_category_id")
	public UserCategoryEntity getUserCategory() {
		return this.userCategoryEntity;
	}

	public void setUserCategory(UserCategoryEntity userCategoryEntity) {
		this.userCategoryEntity = userCategoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@Column(name = "is_active", length = 20)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "active_date", length = 23)
	public Timestamp getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}

	@Column(name = "deactive_date", length = 23)
	public Timestamp getDeactiveDate() {
		return this.deactiveDate;
	}

	public void setDeactiveDate(Timestamp deactiveDate) {
		this.deactiveDate = deactiveDate;
	}

}