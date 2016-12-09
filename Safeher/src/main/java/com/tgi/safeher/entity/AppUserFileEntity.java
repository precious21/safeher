package com.tgi.safeher.entity;

import java.sql.Timestamp;
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
 * AppUserFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_user_file", schema = "dbo")
public class AppUserFileEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer appUserFileId;
	private AppUserEntity appUserEntity;
	private PersonEntity personEntity;
	private String path;
	private String isActive;
	private Timestamp provideDate;
	private String endDate;

	// Constructors

	/** default constructor */
	public AppUserFileEntity() {
	}

	/** full constructor */
	public AppUserFileEntity(AppUserEntity appUserEntity, PersonEntity personEntity, String path,
			String isActive, Timestamp provideDate, String endDate) {
		this.appUserEntity = appUserEntity;
		this.personEntity = personEntity;
		this.path = path;
		this.isActive = isActive;
		this.provideDate = provideDate;
		this.endDate = endDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "app_user_file_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getAppUserFileId() {
		return this.appUserFileId;
	}

	public void setAppUserFileId(Integer appUserFileId) {
		this.appUserFileId = appUserFileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	public AppUserEntity getAppUser() {
		return this.appUserEntity;
	}

	public void setAppUser(AppUserEntity appUserEntity) {
		this.appUserEntity = appUserEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public PersonEntity getPerson() {
		return this.personEntity;
	}

	public void setPerson(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	@Column(name = "path", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "provide_date", length = 23)
	public Timestamp getProvideDate() {
		return this.provideDate;
	}

	public void setProvideDate(Timestamp provideDate) {
		this.provideDate = provideDate;
	}

	@Column(name = "end_date", length = 20)
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}