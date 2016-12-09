/*
 * @Project   : 
 * @Package   : 
 * @FileName  : 
 *
 * Copyright © 
 * Trans-Atlantic Business Solutions,
 * All rights reserved.
 *
 */
package com.tgi.safeher.beans.base;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.tgi.safeher.entity.base.BaseModel;




/**
 * @author    : 
 * @Date      : 
 * @version   :
 *
 * 						   <center><b>BaseDTO.java</b></center>
 * 						<center><b></b></center>
 * <pre>
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
public class  BaseDTO extends BaseModel  implements Serializable{

	private static final long	serialVersionUID	= 4153471587344951507L;
	
	// Only for development in Flex, please do not change it
	private boolean devMode = false;
	

	/* (non-Javadoc)
	 * @see com.tabs.bcrs.entity.base.BaseModel#printEntity()
	 */
	@Override
	public String printEntity( ) {

		return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
	}


	public boolean isDevMode() {
		return devMode;
	}


	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	





}
