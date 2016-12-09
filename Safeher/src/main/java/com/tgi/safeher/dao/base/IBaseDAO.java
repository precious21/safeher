/*
 * @Project : Safeher
 * 
 * @Package : com.tgi.safeher.dao.base
 * 
 * @FileName : IBaseDAO.java
 * 
 * Copyright © 2016 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.dao.base;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

/**
 * @author : TGI Java Backend Team	
 * @Date :   June 15 2016
 * @version : Ver. 1.0.0
 * 
 *          <center><b>IBaseDAO.java</b></center> <center><b>Modification
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

public interface IBaseDAO extends ICurdDAO {

	/**
	 * @author : TGI Java Backend Team	
	 * @Date :   June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findQuery( String queryString ) throws DataAccessException;

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

	/**
	 * @author :  TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedQueryAndNamedParam( Class < T > entityClass,
			String queryName, String [ ] paramNames, Object [ ] values ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryName
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedQueryAndNamedParam( Class < T > entityClass,
			String queryName, Map < String, ? > params ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param query
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedParam( Class < T > entityClass, String query,
			String [ ] paramNames, Object [ ] values ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param query
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedParam( Class < T > entityClass, String query,
			Map < String, ? > params ) throws DataAccessException;

	
	public <T> List <T> getSelectedLimitResult( Query hibernateQuery, int firstResultSet, int lastResultSet ) 
			throws DataAccessException;
	
	public Session getHibernateSession();
	
	public boolean closeHibernateSession( Session hibernateSession );
	
	/**************************************** Modified Methods ***********************************************************/

	/**
	 * @author : TGI Java Backend Team
	 * @Date : June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param <T>
	 * @param query
	 * @param params
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedParam( StringBuilder query, String [ ] params, Object [ ] values )
			throws DataAccessException;
	
	/**
	 * 
	 * @author      :  TGI Java Backend Team
	 * @Date        : June 15 2016
	 *
	 * @Description :
	 *
	 * @param query
	 * @param params
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public < T > List < T > findByNamedParam( StringBuilder query, List < String > params, List < Object > values )
			throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description : To run bulk update query with single value
	 * 
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 */
	public boolean bulkUpdate( String queryString, Object value ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description : To run bulk update query with out value
	 * 
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 */
	public boolean bulkUpdate( String queryString ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description : To run bulk update query with milti value
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	public boolean bulkUpdate( String queryString, Object [ ] values ) throws DataAccessException;

	/**
	 * @author :  TGI Java Backend Team
	 * @Date :June 15 2016
	 * 
	 * @Description :
	 * 
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws DataAccessException
	 */
	public < T > Boolean removeByProperty( Class < T > entityClass, String propertyName, Long propertyValue )
			throws DataAccessException;

	/**
	 * @author : TGI Java Backend Team
	 * @Date :June 15 2016
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
	public < T > List < T > findByCriteria( Class < T > entity, String propertyName, Object propertyValue )
			throws DataAccessException;
	
	/**
	 * 
	 * @author      : TGI Java Backend Team
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
	 * @author      :  TGI Java Backend Team
	 * @Date        :June 15 2016
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
