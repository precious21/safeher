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
 * EletronicType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eletronic_type", schema = "dbo")
public class EletronicTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer eletronicTypeId;
	private String name;
	private Set<UserElectronicResourceEntity> userElectronicResources = new HashSet<UserElectronicResourceEntity>(
			0);
	private Set<EletronicFilterEntity> eletronicFilters = new HashSet<EletronicFilterEntity>(
			0);

	// Constructors

	/** default constructor */
	public EletronicTypeEntity() {
	}

	/** full constructor */
	public EletronicTypeEntity(String name,
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
	@Column(name = "eletronic_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEletronicTypeId() {
		return this.eletronicTypeId;
	}

	public void setEletronicTypeId(Integer eletronicTypeId) {
		this.eletronicTypeId = eletronicTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eletronicType")
	public Set<UserElectronicResourceEntity> getUserElectronicResources() {
		return this.userElectronicResources;
	}

	public void setUserElectronicResources(
			Set<UserElectronicResourceEntity> userElectronicResources) {
		this.userElectronicResources = userElectronicResources;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eletronicType")
	public Set<EletronicFilterEntity> getEletronicFilters() {
		return this.eletronicFilters;
	}

	public void setEletronicFilters(Set<EletronicFilterEntity> eletronicFilters) {
		this.eletronicFilters = eletronicFilters;
	}

}