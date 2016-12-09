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
 * AddressType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "address_type", schema = "dbo")
public class AddressTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer addressTypeId;
	private String name;
	
	// Constructors

	/** default constructor */
	public AddressTypeEntity() {
	}

	/** full constructor */
	public AddressTypeEntity(String name, Set<PersonAddressEntity> personAddresses,
			Set<PersonAddressHistoryEntity> personAddressHistoryEntities) {
		this.name = name;
		
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "address_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAddressTypeId() {
		return this.addressTypeId;
	}

	public void setAddressTypeId(Integer addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}