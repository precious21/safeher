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
 * EletronicFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eletronic_filter", schema = "dbo")
public class EletronicFilterEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer eletronicFilterId;
	private EletronicTypeEntity eletronicType;
	private EletronicCategoryEntity eletronicCategory;

	// Constructors

	/** default constructor */
	public EletronicFilterEntity() {
	}

	/** full constructor */
	public EletronicFilterEntity(EletronicTypeEntity eletronicType,
			EletronicCategoryEntity eletronicCategory) {
		this.eletronicType = eletronicType;
		this.eletronicCategory = eletronicCategory;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "eletronic_filter_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEletronicFilterId() {
		return this.eletronicFilterId;
	}

	public void setEletronicFilterId(Integer eletronicFilterId) {
		this.eletronicFilterId = eletronicFilterId;
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
	@JoinColumn(name = "eletronic_category_id")
	public EletronicCategoryEntity getEletronicCategory() {
		return this.eletronicCategory;
	}

	public void setEletronicCategory(EletronicCategoryEntity eletronicCategory) {
		this.eletronicCategory = eletronicCategory;
	}

}