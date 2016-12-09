package com.tgi.safeher.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
 * PersonDetailHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person_detail_history", schema = "dbo")
public class PersonDetailHistoryEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	
	private PersonDetailEntity personDetail;
	private String primaryEmail;
	private String secondaryEmail;
	private String primaryCell;
	private String secondaryCell;
	private String resPhone;
	private Integer personDetailHistoryId;
	private Timestamp changeDate;

	// Constructors

	/** default constructor */
	public PersonDetailHistoryEntity() {
	}

	/** minimal constructor */
	
	// Property accessors

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
	@Column(name = "change_date", length = 23)
	public Timestamp getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(Timestamp changeDate) {
		this.changeDate = changeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_detail_id")
	public PersonDetailEntity getPersonDetail() {
		return this.personDetail;
	}

	public void setPersonDetail(PersonDetailEntity personDetailEntity) {
		this.personDetail = personDetailEntity;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "person_detail_history", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getPersonDetailHistoryId() {
		return personDetailHistoryId;
	}

	public void setPersonDetailHistoryId(Integer personDetailHistoryId) {
		this.personDetailHistoryId = personDetailHistoryId;
	}

}