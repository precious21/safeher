package com.tgi.safeher.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class CharitiesBean implements java.io.Serializable{

	private String charityId;
	private String charityName;
	private String description;
	private String icon;
	private String rating;
	private String popularity;
	private List<CharitiesBean> charitiesList = new ArrayList<CharitiesBean>();
	private String appUserId;
	private String selectionDate;
	private String isSelectionPending;
	private String isSelectionBySafeher;
	private String charities;
	private String preCharities;
	private String sessionNo;
	private String isSelected;
	
	public String getCharityId() {
		return charityId;
	}
	public void setCharityId(String charityId) {
		this.charityId = charityId;
	}
	public String getCharityName() {
		return charityName;
	}
	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getPopularity() {
		return popularity;
	}
	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}
	public List<CharitiesBean> getCharitiesList() {
		return charitiesList;
	}
	public void setCharitiesList(List<CharitiesBean> charitiesList) {
		this.charitiesList = charitiesList;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getSelectionDate() {
		return selectionDate;
	}
	public void setSelectionDate(String selectionDate) {
		this.selectionDate = selectionDate;
	}
	public String getIsSelectionPending() {
		return isSelectionPending;
	}
	public void setIsSelectionPending(String isSelectionPending) {
		this.isSelectionPending = isSelectionPending;
	}
	public String getIsSelectionBySafeher() {
		return isSelectionBySafeher;
	}
	public void setIsSelectionBySafeher(String isSelectionBySafeher) {
		this.isSelectionBySafeher = isSelectionBySafeher;
	}
	public String getCharities() {
		return charities;
	}
	public void setCharities(String charities) {
		this.charities = charities;
	}
	public String getSessionNo() {
		return sessionNo;
	}
	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	}
	@Override
	public String toString() {
		return "CharitiesBean [charityId=" + charityId + ", charityName="
				+ charityName + ", description=" + description + ", icon="
				+ icon + ", rating=" + rating + ", popularity=" + popularity
				+ ", charitiesList=" + charitiesList + ", appUserId="
				+ appUserId + ", selectionDate=" + selectionDate
				+ ", isSelectionPending=" + isSelectionPending
				+ ", isSelectionBySafeher=" + isSelectionBySafeher
				+ ", charities=" + charities + ", sessionNo=" + sessionNo + "]";
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getPreCharities() {
		return preCharities;
	}
	public void setPreCharities(String preCharities) {
		this.preCharities = preCharities;
	}
	
}
