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
 * UserTypeCatFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_type_cat_filter", schema = "dbo")
public class UserTypeCatFilterEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userTypeCatFilter;
	private AppUserTypeEntity appUserTypeEntity;
	private UserCategoryEntity userCategoryEntity;

	// Constructors

	/** default constructor */
	public UserTypeCatFilterEntity() {
	}

	/** full constructor */
	public UserTypeCatFilterEntity(AppUserTypeEntity appUserTypeEntity, UserCategoryEntity userCategoryEntity) {
		this.appUserTypeEntity = appUserTypeEntity;
		this.userCategoryEntity = userCategoryEntity;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_type_cat_filter", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserTypeCatFilter() {
		return this.userTypeCatFilter;
	}

	public void setUserTypeCatFilter(Integer userTypeCatFilter) {
		this.userTypeCatFilter = userTypeCatFilter;
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

}