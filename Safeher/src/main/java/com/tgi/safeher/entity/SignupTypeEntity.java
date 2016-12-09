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
 * SignupType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "signup_type", schema = "dbo")
public class SignupTypeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer signupTypeId;
	private String name;
	private Set<UserLoginEntity> userLoginEntities = new HashSet<UserLoginEntity>(0);

	// Constructors

	/** default constructor */
	public SignupTypeEntity() {
	}

	/** full constructor */
	public SignupTypeEntity(String name, Set<UserLoginEntity> userLoginEntities) {
		this.name = name;
		this.userLoginEntities = userLoginEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "signup_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getSignupTypeId() {
		return this.signupTypeId;
	}

	public void setSignupTypeId(Integer signupTypeId) {
		this.signupTypeId = signupTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "signupType")
	public Set<UserLoginEntity> getUserLogins() {
		return this.userLoginEntities;
	}

	public void setUserLogins(Set<UserLoginEntity> userLoginEntities) {
		this.userLoginEntities = userLoginEntities;
	}

}