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
 * UserElectronicResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_electronic_resource", schema = "dbo")
public class UserElectronicResourceEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer userElectronicResourceId;
	private EletronicTypeEntity eletronicType;
	private AppUserEntity appUser;
	private EletronicCategoryEntity eletronicCategory;
	private String path;
	private String isActive;
	private Timestamp providedDate;
	private Timestamp endDate;
	private String hashcode;

	// Constructors

	/** default constructor */
	public UserElectronicResourceEntity() {
	}

	/** full constructor */
	public UserElectronicResourceEntity(EletronicTypeEntity eletronicType, AppUserEntity appUser,
			EletronicCategoryEntity eletronicCategory, String path, String isActive,
			Timestamp providedDate, Timestamp endDate, String hashcode) {
		this.eletronicType = eletronicType;
		this.appUser = appUser;
		this.eletronicCategory = eletronicCategory;
		this.path = path;
		this.isActive = isActive;
		this.providedDate = providedDate;
		this.endDate = endDate;
		this.hashcode = hashcode;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_electronic_resource_id", unique = true, nullable = false)
	public Integer getUserElectronicResourceId() {
		return this.userElectronicResourceId;
	}

	public void setUserElectronicResourceId(Integer userElectronicResourceId) {
		this.userElectronicResourceId = userElectronicResourceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eletronic_type_id")
	public EletronicTypeEntity getEletronicType() {
		return this.eletronicType;
	}

	public void setEletronicType(EletronicTypeEntity eletronicType) {
		this.eletronicType = eletronicType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUser) {
		this.appUser = appUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eletronic_category_id")
	public EletronicCategoryEntity getEletronicCategory() {
		return this.eletronicCategory;
	}

	public void setEletronicCategory(EletronicCategoryEntity eletronicCategory) {
		this.eletronicCategory = eletronicCategory;
	}

	@Column(name = "path", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "provided_date", length = 23)
	public Timestamp getProvidedDate() {
		return this.providedDate;
	}

	public void setProvidedDate(Timestamp providedDate) {
		this.providedDate = providedDate;
	}

	@Column(name = "end_date", length = 23)
	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name = "hashcode", length = 200)
	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

}