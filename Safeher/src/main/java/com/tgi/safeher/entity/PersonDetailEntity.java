package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * PersonDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person_detail", schema = "dbo")
public class PersonDetailEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer personDetailId;
	private PersonEntity personEntity;
	private String primaryEmail;
	private String secondaryEmail;
	private String primaryCell;
	private String secondaryCell;
	private String resPhone;
	private String ssnNumber;
	

	// Constructors

	/** default constructor */
	public PersonDetailEntity() {
	}

	/** full constructor */
	public PersonDetailEntity(PersonEntity personEntity, String primaryEmail,
			String secondaryEmail, String primaryCell, String secondaryCell,
			String resPhone) {
		this.personEntity = personEntity;
		this.primaryEmail = primaryEmail;
		this.secondaryEmail = secondaryEmail;
		this.primaryCell = primaryCell;
		this.secondaryCell = secondaryCell;
		this.resPhone = resPhone;
		
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "person_detail_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPersonDetailId() {
		return this.personDetailId;
	}

	public void setPersonDetailId(Integer personDetailId) {
		this.personDetailId = personDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.personEntity;
	}

	public void setPerson(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	@Column(name = "primary_email", length = 200)
	public String getPrimaryEmail() {
		return this.primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	@Column(name = "secondary_email", length = 200)
	public String getSecondaryEmail() {
		return this.secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	@Column(name = "primary_cell", length = 20)
	public String getPrimaryCell() {
		return this.primaryCell;
	}

	public void setPrimaryCell(String primaryCell) {
		this.primaryCell = primaryCell;
	}

	@Column(name = "secondary_cell", length = 20)
	public String getSecondaryCell() {
		return this.secondaryCell;
	}

	public void setSecondaryCell(String secondaryCell) {
		this.secondaryCell = secondaryCell;
	}

	@Column(name = "res_phone", length = 20)
	public String getResPhone() {
		return this.resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}
	
	@Column(name = "ssn_number", length = 500)
	public String getSsnNumber() {
		return this.ssnNumber;
	}
	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

}