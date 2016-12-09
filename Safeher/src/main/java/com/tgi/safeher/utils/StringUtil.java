/*
 * @Project : BCRS-Common
 * 
 * @Package : com.tabs.bcrs.utils
 * 
 * @FileName : StringUtil.java
 * 
 * Copyright © 2011-2012 Trans-Atlantic Business Solutions, All rights reserved.
 */
package com.tgi.safeher.utils;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

/**
 * @author : Waqas Nisar
 * @Date : Dec 1, 2011
 * @version : Ver. 1.0.0
 * 
 *          <center><b>StringUtil.java</b></center> <center><b>Modification
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
public class StringUtil implements Serializable{

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 1, 2011
	 * 
	 * @Description :
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (string == null) {
			return true;
		}
		if (string != null && string.trim().length() > 0) {
			return false;
		}

		return true;
	}

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 1, 2011
	 * 
	 * @Description :
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty( String string ) {

		return !( isEmpty( string ) );
	}

	/**
	 * 
	 * @author : Ahmar Hashmi
	 * @Date : Jan 5, 2012
	 * 
	 * @Description : Function that returns the man readable string after
	 *              converting from camel case string (inserts spaces)
	 * 
	 * @param camelCaseString
	 * @return String
	 */
	public static String splitCamelCase( String camelCaseString ) {

		String manReadableString;
		try {
			manReadableString = camelCaseString.replaceAll(
					String.format( "%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
							"(?<=[A-Za-z])(?=[^A-Za-z])" ), " " );
		} catch ( Exception e ) {
			manReadableString = camelCaseString;
		}
		return manReadableString;
	}

	/**
	 * @author : Ahmar Hashmi
	 * @Date : Aug 10, 2012
	 * 
	 * @Description : Function that returns the concatenated string. It is a
	 *              reverse function of
	 *              {@link StringUtil#splitCamelCase(String)}
	 * 
	 * @param inputString
	 * @return
	 */
	public static String getConcatenatedString( String inputString ) {

		String [ ] array = inputString.split( " " );
		StringBuilder sb = new StringBuilder( 0 );
		for ( String string : array ) {
			sb.append( string );
		}
		return sb.toString( );
	}

	/**
	 * @author : Ahmar Hashmi
	 * @Date : Aug 11, 2012
	 * 
	 * @Description :
	 * 
	 * @param inputString
	 * @return
	 */
	public static boolean isOnlyASCIICharacter( String inputString ) {

		// return CharMatcher.ASCII.matchesAllOf( inputString );

		CharsetEncoder asciiEncoder = Charset.forName( "US-ASCII" ).newEncoder( );

		return asciiEncoder.canEncode( inputString );
	}

	/**
	 * @author : Rafia Taqdees
	 * @Date : Feb 7, 2012
	 * 
	 * @Description : A method used to trim string
	 * 
	 * @param string
	 * @return
	 */
	public static String trim( String string ) {

		String result = null;
		if ( isNotEmpty( string ) ) {
			result = string.trim( );
		}
		return result;
	}
	
	/**
	 * 
	 * @author : Zeeshan Ahmad
	 * @Date : May 6, 2013
	 * 
	 * @Description :
	 * 
	 * @param entityClass
	 * @return
	 */
	public static String entityToFieldName( Class entityClass ) {

		if ( entityClass != null ) {
			String entityName = entityClass.getSimpleName( ).replace( "Entity", "" );
			return entityName.toLowerCase( ).substring( 0, 1 ) + entityName.substring( 1 );
		}
		return null;
	}
	
	/**
	 * 
	 * @author      : Zeeshan Ahmad
	 * @Date        : Dec 19, 2013
	 *
	 * @Description :
	 *
	 * @param inputString
	 * @param items
	 * @return
	 */
	public static boolean stringContainsItemFromList(String inputString, String[] items)
	{
	    for(int i =0; i < items.length; i++)
	    {
	        if(inputString.contains(items[i]))
	        {
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * @author      : Abu Turab
	 * @Date        : Feb 11, 2014
	 *
	 * @Description :
	 *
	 * @param string
	 * @param toReplace
	 * @param replacement
	 * @return      
	 */
	public static String replaceLast(String string, String toReplace, String replacement) {
	    int pos = string.lastIndexOf(toReplace);
	    if (pos > -1) {
	        return string.substring(0, pos)
	             + replacement
	             + string.substring(pos + toReplace.length(), string.length());
	    } else {
	        return string;
	    }
	}
	
	/**
	 * 
	 * @param ssnAlien
	 * @Description: Replacing ssn char with * from policy
	 * @return
	 */
	public static String replaceSSNAlien(String ssnAlien){
		
		String result = null;
		
		if(isNotEmpty(ssnAlien)){
			String[] ssn = ssnAlien.split("-");
			result = new String();
			result += "***";
			result += "-";
			result += "**";
			result += "-";
			result += ssn[2];
		}
		
		return result;
	}
}
