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

/**
 * Frequency entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "frequency", schema = "dbo")
public class FrequencyEntity implements java.io.Serializable {

	// Fields

	private Integer frequencyId;
	private String frequencyValue;
	

	/** default constructor */
	public FrequencyEntity() {
	}

	/** full constructor */
	public FrequencyEntity(String frequencyValue
			) {
		this.frequencyValue = frequencyValue;
	
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "frequency_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFrequencyId() {
		return this.frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	@Column(name = "frequency_value", length = 25)
	public String getFrequencyValue() {
		return this.frequencyValue;
	}

	public void setFrequencyValue(String frequencyValue) {
		this.frequencyValue = frequencyValue;
	}

	

}