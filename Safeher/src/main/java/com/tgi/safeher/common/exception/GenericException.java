/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.exception.common
 * @FileName  : GenericException.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */
package com.tgi.safeher.common.exception;

import java.io.Serializable;


/**
 * @author    : Waqas Nisar
 * @Date      : Dec 1, 2011
 * @version   : Ver. 1.0.0
 *
 * 						   <center><b>GenericException.java</b></center>
 * 						<center><b>Modification History</b></center>
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
public class GenericException extends Exception implements Serializable {


	private static final long	serialVersionUID	= 1L;


	/**
	 * @Constructor
	 * @param e
	 */
	public GenericException( Throwable e ) {
		super( e );
	}
	
	/**
	 * @Constructor
	 * @param message
	 */
	public GenericException( String message ) {
		super( message );
	}
	
	/**
	 * @Constructor
	 * @param message
	 * @param e
	 */
	public GenericException( String message , Throwable e ) {
		super( message, e );
		
	}
	
	/**
	 * @Constructor
	 * @param code
	 * @param message
	 * @param e
	 */
	public GenericException( String code,  String message , Throwable e ) {
		super( message, e );
	}
	

	
}
