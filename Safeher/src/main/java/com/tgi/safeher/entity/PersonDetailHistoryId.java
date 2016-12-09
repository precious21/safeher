package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PersonDetailHistoryId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class PersonDetailHistoryId implements java.io.Serializable {

	// Fields

	private String primaryEmail;
	private String secondaryEmail;
	private String primaryCell;
	private String secondaryCell;
	private String resPhone;
	private Integer personDetailHistory;
	private Integer personDetailId;
	private Timestamp changeDate;

	// Constructors

	/** default constructor */
	public PersonDetailHistoryId() {
	}

	/** minimal constructor */
	public PersonDetailHistoryId(Integer personDetailHistory) {
		this.personDetailHistory = personDetailHistory;
	}

	/** full constructor */
	public PersonDetailHistoryId(String primaryEmail, String secondaryEmail,
			String primaryCell, String secondaryCell, String resPhone,
			Integer personDetailHistory, Integer personDetailId,
			Timestamp changeDate) {
		this.primaryEmail = primaryEmail;
		this.secondaryEmail = secondaryEmail;
		this.primaryCell = primaryCell;
		this.secondaryCell = secondaryCell;
		this.resPhone = resPhone;
		this.personDetailHistory = personDetailHistory;
		this.personDetailId = personDetailId;
		this.changeDate = changeDate;
	}

	// Property accessors

	@Column(name = "primary_email", length = 20)
	public String getPrimaryEmail() {
		return this.primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	@Column(name = "secondary_email", length = 20)
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

	@Column(name = "person_detail_history", nullable = false, precision = 9, scale = 0)
	public Integer getPersonDetailHistory() {
		return this.personDetailHistory;
	}

	public void setPersonDetailHistory(Integer personDetailHistory) {
		this.personDetailHistory = personDetailHistory;
	}

	@Column(name = "person_detail_id", precision = 9, scale = 0)
	public Integer getPersonDetailId() {
		return this.personDetailId;
	}

	public void setPersonDetailId(Integer personDetailId) {
		this.personDetailId = personDetailId;
	}

	@Column(name = "change_date", length = 23)
	public Timestamp getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(Timestamp changeDate) {
		this.changeDate = changeDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PersonDetailHistoryId))
			return false;
		PersonDetailHistoryId castOther = (PersonDetailHistoryId) other;

		return ((this.getPrimaryEmail() == castOther.getPrimaryEmail()) || (this
				.getPrimaryEmail() != null
				&& castOther.getPrimaryEmail() != null && this
				.getPrimaryEmail().equals(castOther.getPrimaryEmail())))
				&& ((this.getSecondaryEmail() == castOther.getSecondaryEmail()) || (this
						.getSecondaryEmail() != null
						&& castOther.getSecondaryEmail() != null && this
						.getSecondaryEmail().equals(
								castOther.getSecondaryEmail())))
				&& ((this.getPrimaryCell() == castOther.getPrimaryCell()) || (this
						.getPrimaryCell() != null
						&& castOther.getPrimaryCell() != null && this
						.getPrimaryCell().equals(castOther.getPrimaryCell())))
				&& ((this.getSecondaryCell() == castOther.getSecondaryCell()) || (this
						.getSecondaryCell() != null
						&& castOther.getSecondaryCell() != null && this
						.getSecondaryCell()
						.equals(castOther.getSecondaryCell())))
				&& ((this.getResPhone() == castOther.getResPhone()) || (this
						.getResPhone() != null
						&& castOther.getResPhone() != null && this
						.getResPhone().equals(castOther.getResPhone())))
				&& ((this.getPersonDetailHistory() == castOther
						.getPersonDetailHistory()) || (this
						.getPersonDetailHistory() != null
						&& castOther.getPersonDetailHistory() != null && this
						.getPersonDetailHistory().equals(
								castOther.getPersonDetailHistory())))
				&& ((this.getPersonDetailId() == castOther.getPersonDetailId()) || (this
						.getPersonDetailId() != null
						&& castOther.getPersonDetailId() != null && this
						.getPersonDetailId().equals(
								castOther.getPersonDetailId())))
				&& ((this.getChangeDate() == castOther.getChangeDate()) || (this
						.getChangeDate() != null
						&& castOther.getChangeDate() != null && this
						.getChangeDate().equals(castOther.getChangeDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPrimaryEmail() == null ? 0 : this.getPrimaryEmail()
						.hashCode());
		result = 37
				* result
				+ (getSecondaryEmail() == null ? 0 : this.getSecondaryEmail()
						.hashCode());
		result = 37
				* result
				+ (getPrimaryCell() == null ? 0 : this.getPrimaryCell()
						.hashCode());
		result = 37
				* result
				+ (getSecondaryCell() == null ? 0 : this.getSecondaryCell()
						.hashCode());
		result = 37 * result
				+ (getResPhone() == null ? 0 : this.getResPhone().hashCode());
		result = 37
				* result
				+ (getPersonDetailHistory() == null ? 0 : this
						.getPersonDetailHistory().hashCode());
		result = 37
				* result
				+ (getPersonDetailId() == null ? 0 : this.getPersonDetailId()
						.hashCode());
		result = 37
				* result
				+ (getChangeDate() == null ? 0 : this.getChangeDate()
						.hashCode());
		return result;
	}

}