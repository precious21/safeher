package com.tgi.safeher.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class UserRatingBean {

	private String appUserId;
	private String userToAppUserId;
	private String userByAppUserId;
	private String userRatingId;
	private String userRatingDetailId;
	private String userCommentId;
	private String userRating;
	private String comment;
	private String userRatingAvg;
	private String ratingLimit;
	private String ratingLimitId;
	private String userByName;
	private String userUrlId;
	private String isReported;
	private String rideNo;
	private List<UserRatingBean> userRatingList;
	
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getUserRatingId() {
		return userRatingId;
	}
	public void setUserRatingId(String userRatingId) {
		this.userRatingId = userRatingId;
	}
	public String getUserRatingDetailId() {
		return userRatingDetailId;
	}
	public void setUserRatingDetailId(String userRatingDetailId) {
		this.userRatingDetailId = userRatingDetailId;
	}
	public String getUserCommentId() {
		return userCommentId;
	}
	public void setUserCommentId(String userCommentId) {
		this.userCommentId = userCommentId;
	}
	public String getUserRating() {
		return userRating;
	}
	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserRatingAvg() {
		return userRatingAvg;
	}
	public void setUserRatingAvg(String userRatingAvg) {
		this.userRatingAvg = userRatingAvg;
	}
	public String getUserByAppUserId() {
		return userByAppUserId;
	}
	public void setUserByAppUserId(String userByAppUserId) {
		this.userByAppUserId = userByAppUserId;
	}
	public String getUserToAppUserId() {
		return userToAppUserId;
	}
	public void setUserToAppUserId(String userToAppUserId) {
		this.userToAppUserId = userToAppUserId;
	}
	public String getRatingLimit() {
		return ratingLimit;
	}
	public void setRatingLimit(String ratingLimit) {
		this.ratingLimit = ratingLimit;
	}
	public String getRatingLimitId() {
		return ratingLimitId;
	}
	public void setRatingLimitId(String ratingLimitId) {
		this.ratingLimitId = ratingLimitId;
	}
	public String getUserUrlId() {
		return userUrlId;
	}
	public void setUserUrlId(String userUrlId) {
		this.userUrlId = userUrlId;
	}
	public String getUserByName() {
		return userByName;
	}
	public void setUserByName(String userByName) {
		this.userByName = userByName;
	}
	public List<UserRatingBean> getUserRatingList() {
		return userRatingList;
	}
	public void setUserRatingList(List<UserRatingBean> userRatingList) {
		this.userRatingList = userRatingList;
	}
	public String getIsReported() {
		return isReported;
	}
	public void setIsReported(String isReported) {
		this.isReported = isReported;
	}
	@Override
	public String toString() {
		return "UserRatingBean [appUserId=" + appUserId + ", userToAppUserId="
				+ userToAppUserId + ", userByAppUserId=" + userByAppUserId
				+ ", userRatingId=" + userRatingId + ", userRatingDetailId="
				+ userRatingDetailId + ", userCommentId=" + userCommentId
				+ ", userRating=" + userRating + ", comment=" + comment
				+ ", userRatingAvg=" + userRatingAvg + ", ratingLimit="
				+ ratingLimit + ", ratingLimitId=" + ratingLimitId
				+ ", userByName=" + userByName + ", userUrlId=" + userUrlId
				+ ", isReported=" + isReported + ", userRatingList="
				+ userRatingList + "]";
	}
	public String getRideNo() {
		return rideNo;
	}
	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

}
