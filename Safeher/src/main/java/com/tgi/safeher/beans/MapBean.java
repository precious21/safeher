package com.tgi.safeher.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class MapBean {

	private String isDriver;
	private List<AppUserBean> personList;

	public List<AppUserBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<AppUserBean> personList) {
		this.personList = personList;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
}
