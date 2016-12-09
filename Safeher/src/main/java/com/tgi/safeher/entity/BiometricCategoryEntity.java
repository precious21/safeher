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
 * BiometricCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biometric_category", schema = "dbo")
public class BiometricCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer biometricCategoryId;
	private String name;
	private Set<AppUserBiometricEntity> appUserBiometricEntities = new HashSet<AppUserBiometricEntity>(
			0);

	// Constructors

	/** default constructor */
	public BiometricCategoryEntity() {
	}

	/** full constructor */
	public BiometricCategoryEntity(String name,
			Set<AppUserBiometricEntity> appUserBiometricEntities) {
		this.name = name;
		this.appUserBiometricEntities = appUserBiometricEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "biometric_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getBiometricCategoryId() {
		return this.biometricCategoryId;
	}

	public void setBiometricCategoryId(Integer biometricCategoryId) {
		this.biometricCategoryId = biometricCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "biometricCategory")
	public Set<AppUserBiometricEntity> getAppUserBiometrics() {
		return this.appUserBiometricEntities;
	}

	public void setAppUserBiometrics(Set<AppUserBiometricEntity> appUserBiometricEntities) {
		this.appUserBiometricEntities = appUserBiometricEntities;
	}

}