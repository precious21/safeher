/*
 * @Project : BCRS-FO-API
 * 
 * @Package : com.tabs.bcrs.fo.base.dao
 * 
 * @FileName : ICurdDAO.java
 * 
 * Copyright © 2011-2012 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tgi.safeher.common.exception.MessageException;



/**
 * @author : TGI Java Backend Team
 * @Date : June 15 2016
 * @version : Ver. 1.0.0
 * 
 *          <center><b>ICurdDAO.java</b></center> <center><b>Modification
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
/**
 * @author : TGI Java Backend Team
 * @Date :June 15 2016
 * @version : Ver. 1.0.0
 * 
 *          <center><b>ICurdDAO.java</b></center> <center><b>Modification
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
public interface ICurdDAO extends Serializable{

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entity
	 * @throws DataAccessException
	 */
	public < T > boolean saveOrUpdate( T entity ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public < T > T merge( T entity ) throws DataAccessException;

	/**
	 * @author :TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entity
	 * @throws DataAccessException
	 */
	public < T > boolean remove( T entity ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entity
	 * @throws DataAccessException
	 */
	public < T > boolean save( T entity ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entity
	 * @throws DataAccessException
	 */
	public < T > boolean update( T entity ) throws DataAccessException;

	/**
	 * @author :TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @throws DataAccessException
	 */
	public < T > boolean removeAll( Class < T > entityClass ) throws DataAccessException, MessageException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findAll( Class < T > entityClass ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findAllSortByName( Class < T > entityClass ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findAllSortByProperty( Class < T > entityClass, String propertyName )
			throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entities
	 * @return
	 * @throws DataAccessException
	 */
	public < T > boolean saveUpdateAll( Collection < T > entities ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public < T > T findById( Class < T > entityClass, Long id ) throws DataAccessException;

	// @Deprecated
	// public boolean deleteWithLoad( Class < ? > entityClass, Long id ) throws
	// DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description : Save Collection Object's
	 * 
	 * @param <T>
	 * @param entities
	 * @return
	 * @throws DataAccessException
	 */
	public < T > boolean saveAll( Collection < T > entities ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description : Method to Update Multiple Entities
	 * 
	 * @param <T>
	 * @param entities
	 * @return
	 * @throws DataAccessException
	 */
	public < T > boolean updateAll( Collection < T > entities ) throws DataAccessException;

	/**
	 * @author :TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description : Save / Update / Remove Entity on the base of flag set.
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public < T > boolean saveUpdateRemove( T entity ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description : Save / Update / Remove Entity on the base of flag set on
	 *              list.
	 * 
	 * @param <T>
	 * @param entities
	 * @return
	 * @throws DataAccessException
	 */
	public < T > boolean saveUpdateRemoveAll( Collection < T > entities ) throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws DataAccessException
	 */
	public < B > Boolean removeByProperty( Class < B > entityClass, String propertyName, Long propertyValue )
			throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <B>
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public < B > Boolean softDeleteByProperty(
			Class < B > entityClass, String propertyName, Long propertyValue, Long userId )
			throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <B>
	 * @param entity
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws DataAccessException
	 */
	public < B > List < B > findByCriteria( Class < B > entity, String propertyName, Object propertyValue )
			throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param entity
	 * @param propertyName
	 * @param notIn
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNotInCriteria( Class < T > entity, String propertyName, Object notIn )
			throws DataAccessException;
	
	/**
	 * 
	 * @author      :TGI Java Backend Team
	 * @Date        :June 15 2016
	 *
	 * @Description :
	 *
	 * @param entity
	 * @param filter
	 * @param category
	 * @param code
	 * @return
	 * @throws DataAccessException
	 */
	public < T, S, U > List < T > findByFilter( Class < T > entity, Class < S > filter, Class< U > category, String code )throws DataAccessException;
	
	/**
	 * 
	 * @author      : TGI Java Backend Team
	 * @Date        : June 15 2016
	 *
	 * @Description :
	 *
	 * @param entity
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws DataAccessException
	 */
	public <T > T findByCriteriaSingleItem( Class < T > entity, String propertyName, Object propertyValue ) throws DataAccessException;
}
