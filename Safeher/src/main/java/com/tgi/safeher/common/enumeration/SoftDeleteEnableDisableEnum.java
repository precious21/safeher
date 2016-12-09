/*
 * @Project : BCRS-Common
 * 
 * @Package : com.tabs.bcrs.enumeration
 * 
 * @FileName : SoftDeleteEnableDisableEnum.java
 * 
 * Copyright © 2011-2012 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.common.enumeration;

/**
 * @author : Ahmar Hashmi
 * @Date : Oct 23, 2012
 * @version : Ver. 1.0.0
 * 
 *          <center><b>SoftDeleteEnableDisableEnum.java</b></center>
 *          <center><b>Modification History</b></center>
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
public enum SoftDeleteEnableDisableEnum {

	SOFT_DELETE_ENABLED( false );

	private Boolean	value;

	SoftDeleteEnableDisableEnum( Boolean code ) {

		this.value = code;
	}

	public Boolean getValue( ) {

		return value;
	}
}
