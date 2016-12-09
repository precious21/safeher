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
 * RoleFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_filter", schema = "dbo")
public class RoleFilterEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer roleFilterId;
	private RoleCategoryEntity roleCategoryEntity;
	private RoleEntity roleEntity;

	// Constructors

	/** default constructor */
	public RoleFilterEntity() {
	}

	/** full constructor */
	public RoleFilterEntity(RoleCategoryEntity roleCategoryEntity, RoleEntity roleEntity) {
		this.roleCategoryEntity = roleCategoryEntity;
		this.roleEntity = roleEntity;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "role_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRoleFilterId() {
		return this.roleFilterId;
	}

	public void setRoleFilterId(Integer roleFilterId) {
		this.roleFilterId = roleFilterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_category_id")
	public RoleCategoryEntity getRoleCategory() {
		return this.roleCategoryEntity;
	}

	public void setRoleCategory(RoleCategoryEntity roleCategoryEntity) {
		this.roleCategoryEntity = roleCategoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rolde_id")
	public RoleEntity getRole() {
		return this.roleEntity;
	}

	public void setRole(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

}