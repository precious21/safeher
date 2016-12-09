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
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role", schema = "dbo")
public class RoleEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String name;
	private Set<FamilyUnitDetailEntity> familyUnitDetailEntities = new HashSet<FamilyUnitDetailEntity>(
			0);
	private Set<RoleFilterEntity> roleFilterEntities = new HashSet<RoleFilterEntity>(0);

	private Set<AppUserVehicleEntity> appUserVehicles = new HashSet<AppUserVehicleEntity>(0);
	// Constructors

	/** default constructor */
	public RoleEntity() {
	}

	/** full constructor */
	public RoleEntity(String name, Set<FamilyUnitDetailEntity> familyUnitDetailEntities,
			Set<RoleFilterEntity> roleFilterEntities ,Set<AppUserVehicleEntity> appUserVehicles) {
		this.name = name;
		this.familyUnitDetailEntities = familyUnitDetailEntities;
		this.roleFilterEntities = roleFilterEntities;
		this.setAppUserVehicles(appUserVehicles);
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "role_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRoldeId() {
		return this.roleId;
	}

	public void setRoldeId(Integer roldeId) {
		this.roleId = roldeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<FamilyUnitDetailEntity> getFamilyUnitDetails() {
		return this.familyUnitDetailEntities;
	}

	public void setFamilyUnitDetails(Set<FamilyUnitDetailEntity> familyUnitDetailEntities) {
		this.familyUnitDetailEntities = familyUnitDetailEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RoleFilterEntity> getRoleFilters() {
		return this.roleFilterEntities;
	}

	public void setRoleFilters(Set<RoleFilterEntity> roleFilterEntities) {
		this.roleFilterEntities = roleFilterEntities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<AppUserVehicleEntity> getAppUserVehicles() {
		return appUserVehicles;
	}

	public void setAppUserVehicles(Set<AppUserVehicleEntity> appUserVehicles) {
		this.appUserVehicles = appUserVehicles;
	}

}