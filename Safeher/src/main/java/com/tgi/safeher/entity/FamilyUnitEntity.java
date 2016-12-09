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
 * FamilyUnit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "family_unit", schema = "dbo")
public class FamilyUnitEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer familyUnitId;
	private String familyNo;
	private String regDate;
	private Short totalPerson;
	private Short totalStakeHolder;
	private Set<FamilyUnitDetailEntity> familyUnitDetailEntities = new HashSet<FamilyUnitDetailEntity>(
			0);

	// Constructors

	/** default constructor */
	public FamilyUnitEntity() {
	}

	/** full constructor */
	public FamilyUnitEntity(String familyNo, String regDate, Short totalPerson,
			Short totalStakeHolder, Set<FamilyUnitDetailEntity> familyUnitDetailEntities) {
		this.familyNo = familyNo;
		this.regDate = regDate;
		this.totalPerson = totalPerson;
		this.totalStakeHolder = totalStakeHolder;
		this.familyUnitDetailEntities = familyUnitDetailEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "family_unit_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFamilyUnitId() {
		return this.familyUnitId;
	}

	public void setFamilyUnitId(Integer familyUnitId) {
		this.familyUnitId = familyUnitId;
	}

	@Column(name = "family_no", length = 32)
	public String getFamilyNo() {
		return this.familyNo;
	}

	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}

	@Column(name = "reg_date", length = 20)
	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Column(name = "total_person")
	public Short getTotalPerson() {
		return this.totalPerson;
	}

	public void setTotalPerson(Short totalPerson) {
		this.totalPerson = totalPerson;
	}

	@Column(name = "total_stake_holder")
	public Short getTotalStakeHolder() {
		return this.totalStakeHolder;
	}

	public void setTotalStakeHolder(Short totalStakeHolder) {
		this.totalStakeHolder = totalStakeHolder;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familyUnit")
	public Set<FamilyUnitDetailEntity> getFamilyUnitDetails() {
		return this.familyUnitDetailEntities;
	}

	public void setFamilyUnitDetails(Set<FamilyUnitDetailEntity> familyUnitDetailEntities) {
		this.familyUnitDetailEntities = familyUnitDetailEntities;
	}

}