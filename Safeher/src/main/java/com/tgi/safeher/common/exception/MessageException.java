/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.exception.common
 * @FileName  : InvalidCriteraException.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */
package com.tgi.safeher.common.exception;


/**
 * @author    : Waqas
 * @Date      : May 23, 2012
 * @version   : Ver. 1.0.0
 *
 * 						   <center><b>InvalidCriteraException.java</b></center>
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
public class MessageException extends GenericRuntimeException {
	

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8738082028008880464L;

	public MessageException( String message ) {
		super( message );
	
	}
	
	public MessageException(Throwable e) {
		super( e );
	}

}
