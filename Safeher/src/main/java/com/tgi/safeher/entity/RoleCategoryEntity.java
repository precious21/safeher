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
 * RoleCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_category", schema = "dbo")
public class RoleCategoryEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer roleCategoryId;
	private String name;
	private Set<RoleFilterEntity> roleFilterEntities = new HashSet<RoleFilterEntity>(0);

	// Constructors

	/** default constructor */
	public RoleCategoryEntity() {
	}

	/** full constructor */
	public RoleCategoryEntity(String name, Set<RoleFilterEntity> roleFilterEntities) {
		this.name = name;
		this.roleFilterEntities = roleFilterEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "role_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRoleCategoryId() {
		return this.roleCategoryId;
	}

	public void setRoleCategoryId(Integer roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleCategory")
	public Set<RoleFilterEntity> getRoleFilters() {
		return this.roleFilterEntities;
	}

	public void setRoleFilters(Set<RoleFilterEntity> roleFilterEntities) {
		this.roleFilterEntities = roleFilterEntities;
	}

}