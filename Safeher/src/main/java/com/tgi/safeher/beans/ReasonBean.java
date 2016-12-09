package com.tgi.safeher.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class ReasonBean {

	private String appUserId;
	private String isDriver;
	private String reasonid;
	private String reasonFilterId;
	private String reasonCategoryId;
	private String reason;
	private List<ReasonBean> reasonsList = new ArrayList<ReasonBean>();
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public String getReasonid() {
		return reasonid;
	}
	public void setReasonid(String reasonid) {
		this.reasonid = reasonid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<ReasonBean> getReasonsList() {
		return reasonsList;
	}
	public void setReasonsList(List<ReasonBean> reasonsList) {
		this.reasonsList = reasonsList;
	}
	public String getReasonFilterId() {
		return reasonFilterId;
	}
	public void setReasonFilterId(String reasonFilterId) {
		this.reasonFilterId = reasonFilterId;
	}
	public String getReasonCategoryId() {
		return reasonCategoryId;
	}
	public void setReasonCategoryId(String reasonCategoryId) {
		this.reasonCategoryId = reasonCategoryId;
	}
	
	
}
