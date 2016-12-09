/*
 * @Project : 
 * 
 * @Package : com.tgi.safeher.entity.base
 * 
 * @FileName : BaseModel.java
 * 
 * Copyright © 2016
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 */

package com.tgi.safeher.entity.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.tgi.safeher.common.sessionbean.SessionBean;



/**
 * @author  : 
 * @Date    : 
 * @version :
 * 
 *          <center><b>BaseModel.java</b></center> 
 *          <center><b></b></center>
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
public abstract class BaseModel implements Serializable{


	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4634813711846215219L;
	private Boolean isSave   = null;
	private Boolean isDelete = null;
	
	private SessionBean sessionBean; 
	
	
	
	public SessionBean getSessionBean( ) {
	
		return sessionBean;
	}


	
	public void setSessionBean( SessionBean sessionBean ) {
	
		this.sessionBean = sessionBean;
	}


	public Boolean getIsSave( ) {
		
		return isSave;
	}

	
	public void setIsSave( Boolean isSave ) {
	
		this.isSave = isSave;
	}


	
	public Boolean getIsDelete( ) {
	
		return isDelete;
	}


	
	public void setIsDelete( Boolean isDelete ) {
	
		this.isDelete = isDelete;
	}

	
	
	/**
	 * @author : Zeeshan Mirza
	 * @Date : Nov 25, 2011
	 * 
	 * @Description : This method uses reflection to dynamically print java
	 *              class field values one line at a time. requires the Apache
	 *              Commons ToStringBuilder class.
	 * 
	 * @return returns String of java class field values one line at a time.
	 *         e.g. Person[ name=John Doe, age=33, smoker=false ]
	 */
	public String printEntity( ){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
