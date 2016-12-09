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
 * FamilyUnitDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "family_unit_detail", schema = "dbo")
public class FamilyUnitDetailEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer familyUnitDetailId;
	private FamilyUnitEntity familyUnitEntity;
	private RoleEntity roleEntity;

	// Constructors

	/** default constructor */
	public FamilyUnitDetailEntity() {
	}

	/** full constructor */
	public FamilyUnitDetailEntity(FamilyUnitEntity familyUnitEntity, RoleEntity roleEntity) {
		this.familyUnitEntity = familyUnitEntity;
		this.roleEntity = roleEntity;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "family_unit_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFamilyUnitDetailId() {
		return this.familyUnitDetailId;
	}

	public void setFamilyUnitDetailId(Integer familyUnitDetailId) {
		this.familyUnitDetailId = familyUnitDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_unit_id")
	public FamilyUnitEntity getFamilyUnit() {
		return this.familyUnitEntity;
	}

	public void setFamilyUnit(FamilyUnitEntity familyUnitEntity) {
		this.familyUnitEntity = familyUnitEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rolde_person")
	public RoleEntity getRole() {
		return this.roleEntity;
	}

	public void setRole(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

}