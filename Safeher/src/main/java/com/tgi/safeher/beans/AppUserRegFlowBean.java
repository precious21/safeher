package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class AppUserRegFlowBean implements Serializable {
	
	private String appUserId;
	private String isFromApp;
	private String stepCode;
	private String isCompleted;
	private String isVerified;
	private String isCompletionEmailSent;
	private String isVerificationEmailSent;
	
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getIsFromApp() {
		return isFromApp;
	}
	public void setIsFromApp(String isFromApp) {
		this.isFromApp = isFromApp;
	}
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	public String getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}
	public String getIsCompletionEmailSent() {
		return isCompletionEmailSent;
	}
	public void setIsCompletionEmailSent(String isCompletionEmailSent) {
		this.isCompletionEmailSent = isCompletionEmailSent;
	}
	public String getIsVerificationEmailSent() {
		return isVerificationEmailSent;
	}
	public void setIsVerificationEmailSent(String isVerificationEmailSent) {
		this.isVerificationEmailSent = isVerificationEmailSent;
	}

}
