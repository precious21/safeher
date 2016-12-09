/*
 * @Project : BCRS-BASE-Model
 * 
 * @Package : com.tabs.bcrs.model.common
 * 
 * @FileName : SessionBean.java
 * 
 * Copyright © 2011-2012 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.common.sessionbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Waqas Nisar
 * @Date : Dec 7, 2011
 * @version : Ver. 1.0.0
 * 
 *          <center><b>SessionBean.java</b></center> <center><b>Modification
 *          History</b></center>
 * 
 *          <pre>
 * 
 * ________________________________________________________________________________________________
 * 
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 * 
 * 
 * ________________________________________________________________________________________________
 * </pre>
 * 
 */
public class SessionBean implements Serializable {

	private static final long				serialVersionUID		= 3546778955881460941L;

	private Long							userId					= null;
	private Long							groupId					= null;
	private String							userLogin				= null;
	private String							userFullName			= null;
	
	private String							macAddress				= null;
	private String							ipAddess				= null;
	
	private String							appId					= null;

	
	/**
	 * @return the userId
	 */
	public Long getUserId( ) {
	
		return userId;
	}

	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId( Long userId ) {
	
		this.userId = userId;
	}

	
	/**
	 * @return the groupId
	 */
	public Long getGroupId( ) {
	
		return groupId;
	}

	
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId( Long groupId ) {
	
		this.groupId = groupId;
	}

	
	/**
	 * @return the userLogin
	 */
	public String getUserLogin( ) {
	
		return userLogin;
	}

	
	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin( String userLogin ) {
	
		this.userLogin = userLogin;
	}

	
	/**
	 * @return the userFullName
	 */
	public String getUserFullName( ) {
	
		return userFullName;
	}

	
	/**
	 * @param userFullName the userFullName to set
	 */
	public void setUserFullName( String userFullName ) {
	
		this.userFullName = userFullName;
	}

	
	/**
	 * @return the macAddress
	 */
	public String getMacAddress( ) {
	
		return macAddress;
	}

	
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress( String macAddress ) {
	
		this.macAddress = macAddress;
	}

	
	/**
	 * @return the ipAddess
	 */
	public String getIpAddess( ) {
	
		return ipAddess;
	}

	
	/**
	 * @param ipAddess the ipAddess to set
	 */
	public void setIpAddess( String ipAddess ) {
	
		this.ipAddess = ipAddess;
	}

	
	/**
	 * @return the appId
	 */
	public String getAppId( ) {
	
		return appId;
	}
	

	
}
