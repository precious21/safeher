/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.utils
 * @FileName  : BooleanUtil.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */
package com.tgi.safeher.utils;

import java.io.Serializable;


/**
 * @author    : Waqas Nisar
 * @Date      : Dec 1, 2011
 * @version   : Ver. 1.0.0
 *
 * 						   <center><b>BooleanUtil.java</b></center>
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
public class BooleanUtil implements Serializable{
	
	public static final short TRUE_SHORT= 1; 
	public static final short FALSE_SHORT= 0; 
	

	
	/**
	 * @author      : Waqas Nisar
	 * @Date        : Dec 1, 2011
	 *
	 * @Description :
	 *
	 * @param shortValue
	 * @return      
	 */
	public static boolean  getBooleanValue(Short shortValue){
		
		if (shortValue != null){
			if (shortValue.shortValue( ) == FALSE_SHORT){
				return false;
			}else{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @author      : zeeshan.ahmad
	 * @Date        : Jul 10, 2012
	 *
	 * @Description :
	 *
	 * @param booleanValue
	 * @return
	 */
	public static boolean getBooleanValue(Boolean booleanValue){
		if (booleanValue != null){
			return booleanValue.booleanValue( );
		}
		return false;
	}
	
	
	/**
	 * @author      : Waqas Nisar
	 * @Date        : Dec 1, 2011
	 *
	 * @Description :
	 *
	 * @param booleanValue
	 * @return      
	 */
	public static short  getShortValue(Boolean booleanValue){
		
		if (booleanValue != null){
			if (booleanValue.booleanValue( ) ){
				return TRUE_SHORT;
			}
		}
		return FALSE_SHORT;
	}
	
	/**
	 * @author      : Waqas
	 * @Date        : Feb 20, 2012
	 *
	 * @Description :
	 *
	 * @return      
	 */
	public short getTrueShort(){
		return TRUE_SHORT;
	}
	
	/**
	 * @author      : Waqas
	 * @Date        : Feb 20, 2012
	 *
	 * @Description :
	 *
	 * @return      
	 */
	public short getFalseShort(){
		return FALSE_SHORT;
	}
	
	/**
	 * @author      : Waqas
	 * @Date        : Jul 10, 2012
	 *
	 * @Description : to give boolean value
	 *
	 * @return      
	 */
	public static boolean getValue(Boolean value){
		
		if (value!=null && value==true){
			return true;
		}
		return false;
	}


}
