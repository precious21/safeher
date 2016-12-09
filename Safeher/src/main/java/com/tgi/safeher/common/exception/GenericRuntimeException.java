/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.exception.common
 * @FileName  : GenericRuntimeException.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */
package com.tgi.safeher.common.exception;

import java.io.Serializable;


/**
 * @author    : Waqas
 * @Date      : Oct 18, 2012
 * @version   : Ver. 1.0.0
 *
 * 						   <center><b>GenericRuntimeException.java</b></center>
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
public class GenericRuntimeException extends RuntimeException implements Serializable{

	private static final long	serialVersionUID	= 1L;


	/**
	 * @Constructor
	 * @param e
	 */
	public GenericRuntimeException( Throwable e ) {
		super( e );
	}
	
	/**
	 * @Constructor
	 * @param message
	 */
	public GenericRuntimeException( String message ) {
		super( message );
	}
	
	/**
	 * @Constructor
	 * @param message
	 * @param e
	 */
	public GenericRuntimeException( String message , Throwable e ) {
		super( message, e );
		
	}
	
	/**
	 * @Constructor
	 * @param code
	 * @param message
	 * @param e
	 */
	public GenericRuntimeException( String code,  String message , Throwable e ) {
		super( message, e );
	}
	
}
