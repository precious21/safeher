package com.tgi.safeher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * AreaCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area_code", schema = "dbo")
public class AreaCodeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer areaCodeId;
	private String name;
	private String code;

	// Constructors

	/** default constructor */
	public AreaCodeEntity() {
	}

	/** full constructor */
	public AreaCodeEntity(String name, String code) {
		this.name = name;
		this.code = code;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "area_code_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAreaCodeId() {
		return this.areaCodeId;
	}

	public void setAreaCodeId(Integer areaCodeId) {
		this.areaCodeId = areaCodeId;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}