package com.tgi.safeher.entity;

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
 * RegFlowMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reg_flow_mapping", schema = "dbo")
public class RegFlowMappingEntity  extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer regFlowMappingId;
	private EmailTempleteEntity emailTemplete;
	private UserRegFlowEntity userRegFlow;
	private String isPass;

	// Constructors

	/** default constructor */
	public RegFlowMappingEntity() {
	}

	/** full constructor */
	public RegFlowMappingEntity(EmailTempleteEntity emailTemplete, UserRegFlowEntity userRegFlow,
			String isPass) {
		this.emailTemplete = emailTemplete;
		this.userRegFlow = userRegFlow;
		this.isPass = isPass;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "reg_flow_mapping_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRegFlowMappingId() {
		return this.regFlowMappingId;
	}

	public void setRegFlowMappingId(Integer regFlowMappingId) {
		this.regFlowMappingId = regFlowMappingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_templete_id")
	public EmailTempleteEntity getEmailTemplete() {
		return this.emailTemplete;
	}

	public void setEmailTemplete(EmailTempleteEntity emailTemplete) {
		this.emailTemplete = emailTemplete;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_reg_flow_id")
	public UserRegFlowEntity getUserRegFlow() {
		return this.userRegFlow;
	}

	public void setUserRegFlow(UserRegFlowEntity userRegFlow) {
		this.userRegFlow = userRegFlow;
	}

	@Column(name = "is_pass", length = 1)
	public String getIsPass() {
		return this.isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

}