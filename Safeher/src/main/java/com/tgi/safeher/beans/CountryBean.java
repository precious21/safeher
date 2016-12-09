package com.tgi.safeher.beans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CountryBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1809992523615907772L;
	String countryId;
	String name;
	String code;
	String abbriviation;
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAbbriviation() {
		return abbriviation;
	}
	public void setAbbriviation(String abbriviation) {
		this.abbriviation = abbriviation;
	}
	
	
	
}
