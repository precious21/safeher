/*
 * @Project : BCRS-Common
 * 
 * @Package : com.tabs.bcrs.utils
 * 
 * @FileName : CollectionUtil.java
 * 
 * Copyright © 2011-2012 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tgi.safeher.beans.KeyValueDTO;




/**
 * @author : Waqas Nisar
 * @Date : Dec 1, 2011
 * @version : Ver. 1.0.0
 * 
 *          <center><b>CollectionUtil.java</b></center> <center><b>Modification
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
public class CollectionUtil implements Serializable{

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 1, 2011
	 * 
	 * @Description :
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty( /* List list */Collection < ? > collection ) {

		if ( collection != null && !collection.isEmpty( ) ) {
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
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty( /* List list */Collection < ? > collection ) {

		return !( isEmpty( collection ) );
	}

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 1, 2011
	 * 
	 * @Description :
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty( Object array[] ) {

		if ( array != null && array.length > 0 ) {
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
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty( Object array[] ) {

		return !( isEmpty( array ) );
	}

	public static Object getObjectFormArray( int index, Object [ ] objectArray ) {

		Object object = null;
		if ( !CollectionUtil.isEmpty( objectArray ) ) {
			if ( objectArray.length > index ) {
				if ( objectArray[ index ] != null ) {
					object = objectArray[ index ];
				}
			}
		}

		return object;
	}

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 20, 2011
	 * 
	 * @Description :
	 * 
	 * @param list
	 * @return
	 */
	public static Object getListFirstItem( List list ) {

		if ( isNotEmpty( list ) ) {
			return list.get( 0 );
		}
		return null;
	}

	/**
	 * @author : Ahmar Hashmi
	 * @Date : Jun 20, 2012
	 * 
	 * @Description : This function checks any Object that whether it can be
	 *              cast to a collection or a simple object.
	 * 
	 * @param <N>
	 * @param obj
	 * @return
	 */
	public static < N > boolean isACollection( Object obj ) {

		try {
			@SuppressWarnings( { "unchecked", "unused" } )
			List < ? > list = ( ArrayList < N > ) obj;
			return true;
		} catch ( ClassCastException e ) {
			return false;
		}
	}

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 20, 2011
	 * 
	 * @Description :
	 * 
	 * @param set
	 * @return
	 */
	/*
	 * public static boolean isEmpty( Set set ) {
	 * 
	 * if ( set != null && !set.isEmpty( ) ) { return false; } return true; }
	 */

	/**
	 * @author : Waqas Nisar
	 * @Date : Dec 20, 2011
	 * 
	 * @Description :
	 * 
	 * @param set
	 * @return
	 */
	/*
	 * public static boolean isNotEmpty( Set set ) {
	 * 
	 * return !( isEmpty( set ) ); }
	 */
	
	

	/**
	 * @author Abu Turab
	 * @param col
	 * @param predicate
	 * @return colletion
	 */
//	public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {
//			  Collection<T> result = new ArrayList<T>();
//			  for (T element: col) {
//			    if (predicate.apply(element)) {
//			      result.add(element);
//			    }
//			  }
//			  return result;
//	}
	

	// clear collection
	public static boolean clear(Collection < ? > col){
		if(CollectionUtil.isNotEmpty( col )){
			col.clear( );
			return true;
		}
		return false;
	}
	
	/**
	 * @author Asad Ullah
	 * @param itemsList
	 * @param keyToRemove
	 * @return collection
	 */
	public static List<KeyValueDTO> removeItemByKey (  List < KeyValueDTO > itemsList , Long keyToRemove ) {
		
		List < KeyValueDTO > itemList = null;
		if (!CollectionUtil.isEmpty(itemsList) ) {
			itemList = new ArrayList<KeyValueDTO>();
			for (KeyValueDTO keyValue: itemsList ) {
				if (!keyValue.getKey().equals(keyToRemove) ) {
					itemList.add(keyValue);
				}
			}
		} else  {
			return itemsList;
		}
		return itemList;
	}
	
}
