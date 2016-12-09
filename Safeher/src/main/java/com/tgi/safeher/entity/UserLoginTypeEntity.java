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
 * UserLoginType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_login_type", schema = "dbo")
public class UserLoginTypeEntity extends BaseEntity  implements java.io.Serializable {

	// Fields

	private Integer userLoginType;
	private Short priorty;
	private String name;
	private Set<UserLoginEntity> userLoginEntities = new HashSet<UserLoginEntity>(0);

	// Constructors

	/** default constructor */
	public UserLoginTypeEntity() {
	}

	/** full constructor */
	public UserLoginTypeEntity(Short priorty, String name, Set<UserLoginEntity> userLoginEntities) {
		this.priorty = priorty;
		this.name = name;
		this.userLoginEntities = userLoginEntities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_login_type", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserLoginType() {
		return this.userLoginType;
	}

	public void setUserLoginType(Integer userLoginType) {
		this.userLoginType = userLoginType;
	}

	@Column(name = "priorty", precision = 4, scale = 0)
	public Short getPriorty() {
		return this.priorty;
	}

	public void setPriorty(Short priorty) {
		this.priorty = priorty;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userLoginType")
	public Set<UserLoginEntity> getUserLogins() {
		return this.userLoginEntities;
	}

	public void setUserLogins(Set<UserLoginEntity> userLoginEntities) {
		this.userLoginEntities = userLoginEntities;
	}

}