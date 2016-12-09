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
 * VehClass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "veh_class", schema = "dbo")
public class VehClassEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer vehClassId;
	private String name;
	private Set<DriverVehClassEntity> deiverVehClasses = new HashSet<DriverVehClassEntity>(
			0);

	// Constructors

	/** default constructor */
	public VehClassEntity() {
	}

	/** full constructor */
	public VehClassEntity(String name, Set<DriverVehClassEntity> deiverVehClasses) {
		this.name = name;
		this.deiverVehClasses = deiverVehClasses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "veh_class_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getVehClassId() {
		return this.vehClassId;
	}

	public void setVehClassId(Integer vehClassId) {
		this.vehClassId = vehClassId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "vehClass")
	public Set<DriverVehClassEntity> getDeiverVehClasses() {
		return this.deiverVehClasses;
	}

	public void setDeiverVehClasses(Set<DriverVehClassEntity> deiverVehClasses) {
		this.deiverVehClasses = deiverVehClasses;
	}

}