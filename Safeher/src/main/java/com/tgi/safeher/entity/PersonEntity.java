package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * Person entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person", schema = "dbo")
public class PersonEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer personId;
	private String firstName;
	private String lastName;
	private Timestamp dob;
	private String sex;
	private String isBlocked;

	// Constructors

	/** default constructor */
	public PersonEntity() {
	}

	/** minimal constructor */
	public PersonEntity(String firstName) {
		this.firstName = firstName;
	}

	/** full constructor */
	public PersonEntity(String firstName, String lastName, Timestamp dob, String sex,
			String isBlocked) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.sex = sex;
		this.isBlocked = isBlocked;
	
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "person_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name = "first_name", nullable = true, length = 20)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 20)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "dob", length = 23)
	public Timestamp getDob() {
		return this.dob;
	}

	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "is_blocked", length = 1)
	public String getIsBlocked() {
		return this.isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	
}