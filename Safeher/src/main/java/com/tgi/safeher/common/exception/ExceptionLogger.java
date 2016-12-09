/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.exception.logger
 * @FileName  : ExceptionLogger.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */
package com.tgi.safeher.common.exception;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.tgi.safeher.entity.base.BaseModel;


/**
 * @author    : Zeeshan Mirza
 * @Date      : Nov 28, 2011
 * @version   : Ver. 1.0.0
 *
 * 						   <center><b>ExceptionLogger.java</b></center>
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
public class ExceptionLogger implements Serializable{

	private static Logger	logger	= Logger.getLogger( ExceptionLogger.class );

	/**
	 * It logs the exception
	 * 
	 * @param exceptionDescription
	 * @param className
	 * @param methodName
	 * @param exception
	 */
	public static void logException( String exceptionDescription, String className, String methodName, Exception ex ) {

		StringBuilder exceptionGen = new StringBuilder( );
		StringBuilder exceptionEnd = new StringBuilder( );
		StringBuilder exceptionMsg = new StringBuilder( );

		buildMessage( exceptionDescription, className, methodName, exceptionGen, exceptionEnd, exceptionMsg );

		if ( ex != null ) {
			logger.error( exceptionGen.toString( ) + exceptionMsg.toString( ) + getStackTraceAsString( ex )
					+ exceptionEnd.toString( ) );
		} else {
			logger.error( exceptionGen.toString( ) + exceptionMsg.toString( ) + exceptionEnd.toString( ) );
		}
		exceptionGen = null;
		exceptionMsg = null;
	}

	/**
	 * Generic exception logger
	 * 
	 * @author jawad.hameed
	 * @param e
	 */
	public static void logException( Exception e ) {

		logException( e.getMessage( ), e.getStackTrace( )[ 0 ].getClassName( ),
				e.getStackTrace( )[ 0 ].getMethodName( ), e );
	}

	/**
	 * @author      : Zeeshan Mirza
	 * @Date        : Nov 25, 2011
	 *
	 * @Description : This method will log the custom exception along with .
	 *
	 * @param e
	 * @param baseModel      
	 */
	public static void logException( Exception e, BaseModel baseModel ) {

		logException( e.getMessage( ), e.getStackTrace( )[ 0 ].getClassName( ),
				e.getStackTrace( )[ 0 ].getMethodName( ), baseModel, e );
	}

	/**
	 * @author : Zeeshan Mirza
	 * @Date : Nov 25, 2011
	 * 
	 * @Description : This method will log custom exception
	 * 
	 * @param exceptionDescription
	 * @param className
	 * @param methodName
	 * @param baseModel
	 * @param ex
	 */
	public static void logException(
			String exceptionDescription, String className, String methodName, BaseModel baseModel, Exception ex ) {

		StringBuilder exceptionGen = new StringBuilder( );
		StringBuilder exceptionEnd = new StringBuilder( );
		StringBuilder exceptionMsg = new StringBuilder( );

		buildMessage( exceptionDescription, className, methodName, exceptionGen, exceptionEnd, exceptionMsg );

		if ( ex != null ) {
			logger.error( exceptionGen.toString( ) + exceptionMsg.toString( ) + getStackTraceAsString( ex )
					+ ( ( baseModel != null ) ? baseModel.printEntity( )  : "" ) + exceptionEnd.toString( ) );
		} else {
			logger.error( exceptionGen.toString( ) + exceptionMsg.toString( )
					+ ( ( baseModel != null ) ? baseModel.printEntity( ) : "" ) + exceptionEnd.toString( ) );
		}
		exceptionGen = null;
		exceptionMsg = null;
	}

	/**
	 * @author : Zeeshan Mirza
	 * @Date : Nov 25, 2011
	 * 
	 * @Description : This method will generate the formate of the custome exception that is going
	 *              to be log.
	 * 
	 * @param exceptionDescription
	 * @param className
	 * @param methodName
	 * @param exceptionGen
	 * @param exceptionEnd
	 * @param exceptionMsg
	 */
	private static void buildMessage(
			String exceptionDescription, String className, String methodName, StringBuilder exceptionGen,
			StringBuilder exceptionEnd, StringBuilder exceptionMsg ) {

		exceptionGen.append( "\n " ).append( "\n\t\t\t\t ####################################################### " )
				.append( "\n\t\t\t\t     ************ Exception Generated **************" )
				.append( "\n\t\t\t\t #######################################################" );

		exceptionEnd.append( "\n " ).append( "\n\t\t\t\t ####################################################### " )
				.append( "\n\t\t\t\t     ************** Exception End ****************" )
				.append( "\n\t\t\t\t #######################################################" );

		exceptionMsg.append( " \n\n\t\t\t\t\t >>>> " ).append( exceptionDescription )
				.append( "\n\n\t\t\t\t\t Class \t\t::\t" ).append( className ).append( "\n\t\t\t\t\t Method \t::\t " )
				.append( methodName ).append( "\n\t\t\t\t\t Exception \t::\t" );
	}

	/**
	 * @author : Zeeshan Mirza
	 * @Date : Nov 25, 2011
	 * 
	 * @Description : This mehtod will print exception stack trace.
	 * 
	 * @param exception
	 * @return returns the exception stack trace as a string.
	 */
	private static String getStackTraceAsString( Exception exception ) {

		StringWriter sw = new StringWriter( );
		PrintWriter pw = new PrintWriter( sw );
		pw.print( " [ " );
		pw.print( exception.getClass( ).getName( ) );
		pw.print( " ] " );
		pw.print( exception.getMessage( ) + "\n" );
		exception.printStackTrace( pw );
		String trace[] = sw.toString( ).split( "\n" );

		StringBuilder exc = new StringBuilder( );
		for ( int i = 0 ; i < trace.length ; i++ ) {
			if ( i == 0 ) {
				exc.append( trace[ i ] ).append( " \n " ).append( "\n\t\t\t\tStackTrace :: \n\n" );
			} else if ( i != 1 && i != 0 )
				exc.append( "\t\t\t" ).append( trace[ i ] );
		}
		return exc.toString( );
	}

}
