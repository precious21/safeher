package com.tgi.safeher.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class UserFavoritiesBean implements java.io.Serializable{
	
	private String appUserId;
	private String userFavoritiesId;
	private String favorityType;
	private String favLong;
	private String favLat;
	private String lable;
	private String location;
	private String placeId;
	private List<UserFavoritiesBean> favList = new ArrayList<UserFavoritiesBean>();
	
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getUserFavoritiesId() {
		return userFavoritiesId;
	}
	public void setUserFavoritiesId(String userFavoritiesId) {
		this.userFavoritiesId = userFavoritiesId;
	}
	public String getFavorityType() {
		return favorityType;
	}
	public void setFavorityType(String favorityType) {
		this.favorityType = favorityType;
	}
	public String getFavLong() {
		return favLong;
	}
	public void setFavLong(String favLong) {
		this.favLong = favLong;
	}
	public String getFavLat() {
		return favLat;
	}
	public void setFavLat(String favLat) {
		this.favLat = favLat;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<UserFavoritiesBean> getFavList() {
		return favList;
	}
	public void setFavList(List<UserFavoritiesBean> favList) {
		this.favList = favList;
	}
	@Override
	public String toString() {
		return "UserFavoritiesBean [appUserId=" + appUserId
				+ ", userFavoritiesId=" + userFavoritiesId + ", favorityType="
				+ favorityType + ", favLong=" + favLong + ", favLat=" + favLat
				+ ", lable=" + lable + ", location=" + location + ", favList="
				+ favList + "]";
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

}
