package com.tgi.safeher.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EndVehResBean  implements Serializable {
	
	private String appUserId;
	private String isActive;
	private String id;
	private String name;
	private List<EndVehResBean> list = new ArrayList<EndVehResBean>();
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<EndVehResBean> getList() {
		return list;
	}
	public void setList(List<EndVehResBean> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}

