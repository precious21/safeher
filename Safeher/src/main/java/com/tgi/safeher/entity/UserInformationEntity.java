package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserInformation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_Information", schema = "dbo")
public class UserInformationEntity implements java.io.Serializable {

	// Fields

	private Integer userInformationId;
	private Timestamp infoDate;
	private String appUserId;
	private String value;

	// Constructors

	/** default constructor */
	public UserInformationEntity() {
	}

	/** full constructor */
	public UserInformationEntity(Timestamp infoDate, String appUserId, String value) {
		this.infoDate = infoDate;
		this.appUserId = appUserId;
		this.value = value;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_information_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getUserInformationId() {
		return this.userInformationId;
	}

	public void setUserInformationId(Integer userInformationId) {
		this.userInformationId = userInformationId;
	}

	@Column(name = "info_date", length = 23)
	public Timestamp getInfoDate() {
		return this.infoDate;
	}

	public void setInfoDate(Timestamp infoDate) {
		this.infoDate = infoDate;
	}

	@Column(name = "app_user_id", length = 500)
	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	@Column(name = "value", length = 500)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}