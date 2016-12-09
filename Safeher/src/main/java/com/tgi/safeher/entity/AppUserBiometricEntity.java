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
 * AppUserBiometric entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_biometric", schema = "dbo")
public class AppUserBiometricEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserBiometricId;
	private AppUserEntity appUser;
	private BiometricCategoryEntity biometricCategoryEntity;
	private BiometricTypeEntity biometricTypeEntity;
	private PersonEntity personEntity;
	private String path;
	private String isActive;
	private Timestamp providedDate;
	private Timestamp endDate;

	// Constructors

	/** default constructor */
	public AppUserBiometricEntity() {
	}

	/** full constructor */
	public AppUserBiometricEntity(AppUserEntity appUserEntity,
			BiometricCategoryEntity biometricCategoryEntity, BiometricTypeEntity biometricTypeEntity,
			PersonEntity personEntity, String path, String isActive,
			Timestamp providedDate, Timestamp endDate) {
		this.appUser = appUserEntity;
		this.biometricCategoryEntity = biometricCategoryEntity;
		this.biometricTypeEntity = biometricTypeEntity;
		this.personEntity = personEntity;
		this.path = path;
		this.isActive = isActive;
		this.providedDate = providedDate;
		this.endDate = endDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_biometric_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserBiometricId() {
		return this.appUserBiometricId;
	}

	public void setAppUserBiometricId(Integer appUserBiometricId) {
		this.appUserBiometricId = appUserBiometricId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUser = appUserEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biometric_category_id")
	public BiometricCategoryEntity getBiometricCategory() {
		return this.biometricCategoryEntity;
	}

	public void setBiometricCategory(BiometricCategoryEntity biometricCategoryEntity) {
		this.biometricCategoryEntity = biometricCategoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biometric_type_id")
	public BiometricTypeEntity getBiometricType() {
		return this.biometricTypeEntity;
	}

	public void setBiometricType(BiometricTypeEntity biometricTypeEntity) {
		this.biometricTypeEntity = biometricTypeEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.personEntity;
	}

	public void setPerson(PersonEntity personEntity) {
		this.personEntity = personEntity;
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

}