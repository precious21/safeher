package com.tgi.safeher.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserImageBean {

	private String appUserId;
	private String fileType;
	private String size;
	private String mediaUrl;
	private String externalTag;
	private String externalTagProvider;
	private String userElecResourceId;
	private String imageName;
	private String imageTypeFlag;
	private String isFromWindow;
	
	private List<String> urlList = new ArrayList<String>();
	private List<UserImageBean> imageList = new ArrayList<UserImageBean>();
	
	private MultipartFile file;
	
	private MultipartFile[] fileArray;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getExternalTag() {
		return externalTag;
	}

	public void setExternalTag(String externalTag) {
		this.externalTag = externalTag;
	}

	public String getExternalTagProvider() {
		return externalTagProvider;
	}

	public void setExternalTagProvider(String externalTagProvider) {
		this.externalTagProvider = externalTagProvider;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile[] getFileArray() {
		return fileArray;
	}

	public void setFileArray(MultipartFile[] fileArray) {
		this.fileArray = fileArray;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public List<UserImageBean> getImageList() {
		return imageList;
	}

	public void setImageList(List<UserImageBean> imageList) {
		this.imageList = imageList;
	}

	public String getUserElecResourceId() {
		return userElecResourceId;
	}

	public void setUserElecResourceId(String userElecResourceId) {
		this.userElecResourceId = userElecResourceId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageTypeFlag() {
		return imageTypeFlag;
	}

	public void setImageTypeFlag(String imageTypeFlag) {
		this.imageTypeFlag = imageTypeFlag;
	}

	public String getIsFromWindow() {
		return isFromWindow;
	}

	public void setIsFromWindow(String isFromWindow) {
		this.isFromWindow = isFromWindow;
	}
	
	
}
