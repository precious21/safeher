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

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * UserCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_category", schema = "dbo")
public class UserCategoryEntity  extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userCategoryId;
	private String name;
	private Set<UserTypeCatFilterEntity> userTypeCatFilterEntities = new HashSet<UserTypeCatFilterEntity>(
			0);
	private Set<AppUserTypeCatsEntity> appUserTypeCatses = new HashSet<AppUserTypeCatsEntity>(
			0);

	// Constructors

	/** default constructor */
	public UserCategoryEntity() {
	}

	/** full constructor */
	public UserCategoryEntity(String name, Set<UserTypeCatFilterEntity> userTypeCatFilterEntities,
			Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.name = name;
		this.userTypeCatFilterEntities = userTypeCatFilterEntities;
		this.appUserTypeCatses = appUserTypeCatses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserCategoryId() {
		return this.userCategoryId;
	}

	public void setUserCategoryId(Integer userCategoryId) {
		this.userCategoryId = userCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCategory")
	public Set<UserTypeCatFilterEntity> getUserTypeCatFilters() {
		return this.userTypeCatFilterEntities;
	}

	public void setUserTypeCatFilters(Set<UserTypeCatFilterEntity> userTypeCatFilterEntities) {
		this.userTypeCatFilterEntities = userTypeCatFilterEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCategory")
	public Set<AppUserTypeCatsEntity> getAppUserTypeCatses() {
		return this.appUserTypeCatses;
	}

	public void setAppUserTypeCatses(Set<AppUserTypeCatsEntity> appUserTypeCatses) {
		this.appUserTypeCatses = appUserTypeCatses;
	}

}