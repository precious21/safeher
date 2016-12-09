/*
 * @Project   : BCRS-Common
 * @Package   : com.tabs.bcrs.utils
 * @FileName  : ConverterUtil.java
 *
 * Copyright © 2011-2012
 * Trans-Atlantic Business Solutions, 
 * All rights reserved.
 * 
 */


package com.tgi.safeher.utils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.tgi.safeher.beans.KeyValueDTO;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.base.BaseModel;


/**
 * @author : zeeshan.ahmad
 * @Date : Jul 10, 2012
 * @version : Ver. 1.0.0
 * 
 *          <center><b>ConverterUtil.java</b></center> <center><b>Modification
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
public class ConverterUtil implements Serializable{
	
	/**
	 * 
	 * @author      : zeeshan.ahmad
	 * @Date        : Jul 10, 2012
	 *
	 * @Description : This method will convert the passed entity list to KeyValueDTO list.
	 * Helpful to populate Drop Downs data.
	 *
	 * @param inputList
	 * @param keyFieldName
	 * @param valueFieldName
	 * @return
	 */
	public static List < KeyValueDTO > convertEntityToKeyValueDTOList( List < ? extends BaseModel> inputList, String keyFieldName, String valueFieldName ){

		List < KeyValueDTO > resultList = new ArrayList < KeyValueDTO >( );
		if ( CollectionUtil.isNotEmpty( inputList ) ) {
			KeyValueDTO keyValueDTO = null;
			for ( Object object : inputList ) {
				keyValueDTO = new KeyValueDTO( );
				try {
					keyValueDTO.setKey( ( Long ) PropertyUtils.getSimpleProperty( object, keyFieldName ) );
					keyValueDTO.setValue( ( String ) PropertyUtils.getSimpleProperty( object, valueFieldName ) );
				}catch (Exception e) {
					e.printStackTrace( );
				}

				resultList.add( keyValueDTO );
			}
		}
		return resultList;
	}
	
	/**
	 * 
	 * @author      : zeeshan.ahmad
	 * @Date        : Jul 10, 2012
	 *
	 * @Description : This method will convert the passed entity list to KeyValueDTO list.
	 *
	 * @param inputList
	 * @return
	 */
	public static List < KeyValueDTO > convertEntityToKeyValueDTOList( List < ? extends BaseModel > inputList ) {

		List < KeyValueDTO > resultList = new ArrayList < KeyValueDTO >( );
		if ( CollectionUtil.isNotEmpty( inputList ) ) {
			Object inputEntity = inputList.get( 0 );
			String keyFieldName = getIdFieldName( inputEntity );
			String valueFieldName = "name";
			if(StringUtil.isNotEmpty( keyFieldName )){
				resultList = convertEntityToKeyValueDTOList(inputList, keyFieldName, valueFieldName);
			}
		}
		return resultList;
	}
	
	
	/**
	 * 
	 * @author      : zeeshan.ahmad
	 * @Date        : Jul 10, 2012
	 *
	 * @Description : This method will convert the passed object array list to KeyValueDTO list.
	 *
	 * @param inputList
	 * @return
	 * @throws GenericException 
	 */
	public static List < KeyValueDTO > convertObjectArrayToKeyValueDTOList( List < Object[] > inputList ) throws GenericException {
		List < KeyValueDTO > resultList = new ArrayList < KeyValueDTO >( );
		if ( CollectionUtil.isNotEmpty( inputList ) ) {
			KeyValueDTO keyValueDTO = null;
			for ( Object[] objects: inputList ) {
				if(objects.length >= 2){
					keyValueDTO = new KeyValueDTO();
					if(objects[0] instanceof Long && objects[1] instanceof String){
						keyValueDTO.setKey( (Long) objects[0] );
						keyValueDTO.setValue( (String) objects[1] );
						resultList.add( keyValueDTO );
					}else{
						throw new GenericException( "Object array must contain Long key at 0 index and String name at 1." );
					}
				}else{
					throw new GenericException( "Object array passed must have two index values." );
				}
			}
		}
		return resultList;
	}

	
	/**
	 * 
	 * @author      : zeeshan.ahmad
	 * @Date        : Jul 10, 2012
	 *
	 * @Description :
	 *
	 * @param inputEntity
	 * @return
	 */
	private static String getIdFieldName( Object inputEntity ) {

		PropertyDescriptor [ ] properties = PropertyUtils.getPropertyDescriptors( inputEntity );
		for ( PropertyDescriptor propertyDescriptor : properties ) {
			if ( propertyDescriptor.getName( ).contains( "Id" ) && !propertyDescriptor.getName( ).equalsIgnoreCase("loginSessionId")) {
				if ( propertyDescriptor.getPropertyType( ).getName( ).contains( "Long" ) ) {
					return propertyDescriptor.getName( );
				}
			}
		}
		return null;
	}

	/**
	 * @param inputList
	 * @return
	 * @throws GenericException
	 */
	@Deprecated
	public static List < KeyValueDTO > convertObjectArrayToKeyValueDTO( List < Object[] > inputList ) throws GenericException {
		List < KeyValueDTO > resultList = new ArrayList < KeyValueDTO >( );
		if ( CollectionUtil.isNotEmpty( inputList ) ) {
			KeyValueDTO keyValueDTO = null;
			for ( Object[] objects: inputList ) {
				if(objects.length >= 2){
					keyValueDTO = new KeyValueDTO();
					
						keyValueDTO.setKey( (Long) objects[0] );
						keyValueDTO.setValue(  objects[1].toString() );
						resultList.add( keyValueDTO );
					}
				else{
					throw new GenericException( "Object array passed must have two index values." );
				}
			}
		}
		return resultList;
	}

}
