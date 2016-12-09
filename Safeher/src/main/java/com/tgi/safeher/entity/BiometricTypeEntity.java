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
 * BiometricType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biometric_type", schema = "dbo")
public class BiometricTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer biometricTypeId;
	private String name;
	private Set<AppUserBiometricEntity> appUserBiometricEntities = new HashSet<AppUserBiometricEntity>(
			0);

	// Constructors

	/** default constructor */
	public BiometricTypeEntity() {
	}

	/** full constructor */
	public BiometricTypeEntity(String name, Set<AppUserBiometricEntity> appUserBiometricEntities) {
		this.name = name;
		this.appUserBiometricEntities = appUserBiometricEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "biometric_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getBiometricTypeId() {
		return this.biometricTypeId;
	}

	public void setBiometricTypeId(Integer biometricTypeId) {
		this.biometricTypeId = biometricTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "biometricType")
	public Set<AppUserBiometricEntity> getAppUserBiometrics() {
		return this.appUserBiometricEntities;
	}

	public void setAppUserBiometrics(Set<AppUserBiometricEntity> appUserBiometricEntities) {
		this.appUserBiometricEntities = appUserBiometricEntities;
	}

}