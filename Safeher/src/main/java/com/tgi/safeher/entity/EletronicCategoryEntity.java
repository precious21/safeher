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
 * EletronicCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eletronic_category", schema = "dbo")
public class EletronicCategoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer eletronicCategoryId;
	private String name;
	private Set<UserElectronicResourceEntity> userElectronicResources = new HashSet<UserElectronicResourceEntity>(
			0);
	private Set<EletronicFilterEntity> eletronicFilters = new HashSet<EletronicFilterEntity>(
			0);

	// Constructors

	/** default constructor */
	public EletronicCategoryEntity() {
	}

	/** full constructor */
	public EletronicCategoryEntity(String name,
			Set<UserElectronicResourceEntity> userElectronicResources,
			Set<EletronicFilterEntity> eletronicFilters) {
		this.name = name;
		this.userElectronicResources = userElectronicResources;
		this.eletronicFilters = eletronicFilters;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "eletronic_category_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEletronicCategoryId() {
		return this.eletronicCategoryId;
	}

	public void setEletronicCategoryId(Integer eletronicCategoryId) {
		this.eletronicCategoryId = eletronicCategoryId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eletronicCategory")
	public Set<UserElectronicResourceEntity> getUserElectronicResources() {
		return this.userElectronicResources;
	}

	public void setUserElectronicResources(
			Set<UserElectronicResourceEntity> userElectronicResources) {
		this.userElectronicResources = userElectronicResources;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eletronicCategory")
	public Set<EletronicFilterEntity> getEletronicFilters() {
		return this.eletronicFilters;
	}

	public void setEletronicFilters(Set<EletronicFilterEntity> eletronicFilters) {
		this.eletronicFilters = eletronicFilters;
	}

}