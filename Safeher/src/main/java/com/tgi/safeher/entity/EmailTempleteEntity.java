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
 * EmailTemplete entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "email_templete", schema = "dbo")
public class EmailTempleteEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer emailTempleteId;
	private String subject;
	private String emailBody;
	private String isMultimedia;
	private String isAttachment;
	private String attachmentLocation;
	private String emailCode;
	private Set<RegFlowMappingEntity> regFlowMappings = new HashSet<RegFlowMappingEntity>(0);

	// Constructors

	/** default constructor */
	public EmailTempleteEntity() {
	}

	/** full constructor */
	public EmailTempleteEntity(String subject, String emailBody, String isMultimedia,
			String isAttachment, String attachmentLocation, String emailCode,
			Set<RegFlowMappingEntity> regFlowMappings) {
		this.subject = subject;
		this.emailBody = emailBody;
		this.isMultimedia = isMultimedia;
		this.isAttachment = isAttachment;
		this.attachmentLocation = attachmentLocation;
		this.emailCode = emailCode;
		this.regFlowMappings = regFlowMappings;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "email_templete_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getEmailTempleteId() {
		return this.emailTempleteId;
	}

	public void setEmailTempleteId(Integer emailTempleteId) {
		this.emailTempleteId = emailTempleteId;
	}

	@Column(name = "subject", length = 200)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "email_body", length = 2000)
	public String getEmailBody() {
		return this.emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	@Column(name = "is_multimedia", length = 1)
	public String getIsMultimedia() {
		return this.isMultimedia;
	}

	public void setIsMultimedia(String isMultimedia) {
		this.isMultimedia = isMultimedia;
	}

	@Column(name = "is_attachment", length = 1)
	public String getIsAttachment() {
		return this.isAttachment;
	}

	public void setIsAttachment(String isAttachment) {
		this.isAttachment = isAttachment;
	}

	@Column(name = "attachment_location", length = 200)
	public String getAttachmentLocation() {
		return this.attachmentLocation;
	}

	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}

	@Column(name = "email_code", length = 10)
	public String getEmailCode() {
		return this.emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emailTemplete")
	public Set<RegFlowMappingEntity> getRegFlowMappings() {
		return this.regFlowMappings;
	}

	public void setRegFlowMappings(Set<RegFlowMappingEntity> regFlowMappings) {
		this.regFlowMappings = regFlowMappings;
	}

}