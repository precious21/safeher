/*
 * @Project : Safeher
 * 
 * @Package : com.tgi.safeher.dao.base
 * 
 * @FileName : BaseDAO.java
 * 
 * Copyright © 2016 Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;


import com.tgi.safeher.common.constant.IConfigConst;
import com.tgi.safeher.common.enumeration.SoftDeleteEnableDisableEnum;
import com.tgi.safeher.common.exception.MessageException;
import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.base.BaseEntity;
import com.tgi.safeher.utils.BooleanUtil;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;


/**
 * @author : TGI Java Backend Team
 * @Date : June 15 2016
 * @version : Ver. 1.0.0
 * 
 *          A Generic data access object (DAO) providing persistence and search
 *          support for all mapped entities. Transaction control of the save(),
 *          update() and delete() operations can directly support Spring
 *          container-managed transactions or they can be augmented to handle
 *          user-managed Spring transactions. Each of these methods provides
 *          additional information for how to configure it for the desired type
 *          of transaction control.
 * 
 *          <center><b>BaseDAO.java</b></center> <center><b>Modification
 *          History</b></center>
 * 
 *          <pre>
 * 
 * ________________________________________________________________________________________________
 * 
 *  Developer				Date		Version		Operation			Description
 * ________________________________________________________________________________________________
 * 										1.1			Added				Added Audit Columns Handling			
 * 										1.1			Added + Modified 	Modified the implementation to handle soft delete
 *               														functionality. Modified after discussing with PM.
 * 
 * ________________________________________________________________________________________________
 * </pre>
 * 
 */

@SuppressWarnings( "all" )

public class BaseDAO implements IBaseDAO, Serializable{

	protected transient Logger			logger				= null;

//	@Autowired
//	private HibernateTemplate	hibernateTemplate	= null;
	
	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	protected SessionFactory sessionFactory;
	
	/*@PersistenceContext
	EntityManager entityManager;*/

	public BaseDAO( Class < ? > daoClass ) {

		logger = Logger.getLogger( daoClass );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#findQuery(java.lang.String)
	 */
//	public < T > List < T > findQuery( String queryString ) throws DataAccessException {
//
//		return (List<T>) getHibernateTemplate( ).find( queryString );
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#findById(java.lang.Class,
	 * java.lang.Long)
	 */
//	public < T > T findById( Class < T > entityClass, Long id ) throws DataAccessException {
//
//		T obj = ( T ) getHibernateTemplate( ).get( entityClass, id );
//		if ( obj == null ) {
//			logger.warn( "Entity with ID '" + id + "' not found..." );
//			throw new ObjectRetrievalFailureException( entityClass, id );
//		} else {
//			return obj;
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.dao.base.IBaseDAO#findByNamedQueryAndNamedParam(java
	 * .lang.Class, java.lang.String, java.lang.String[], java.lang.Object[])
	 */
//	public < T > List < T > findByNamedQueryAndNamedParam( Class < T > entityClass,
//			String queryName, String [ ] paramNames, Object [ ] values ) throws DataAccessException {
//
//		List < T > results = ( List < T > ) getHibernateTemplate( ).findByNamedQueryAndNamedParam( queryName,
//				paramNames, values );
//		return results;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.dao.base.IBaseDAO#findByNamedQueryAndNamedParam(java
	 * .lang.Class, java.lang.String, java.util.Map)
	 */
	public < T > List < T > findByNamedQueryAndNamedParam(
			Class < T > entityClass, String queryName, Map < String, ? > params ) throws DataAccessException {

		String [ ] paramNames = new String[params.size( )];
		Object [ ] values = new Object[params.size( )];

		List < String > keys = new ArrayList < String >( params.keySet( ) );
		for ( int i = 0 ; i < keys.size( ) ; i++ ) {
			String k = keys.get( i );
			paramNames[ i ] = k;
			values[ i ] = params.get( k );
		}

		return this.findByNamedQueryAndNamedParam( entityClass, queryName, paramNames, values );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#findByNamedParam(java.lang.Class,
	 * java.lang.String, java.lang.String[], java.lang.Object[])
	 */
//	public < T > List < T > findByNamedParam( Class < T > entityClass, String query,
//			String [ ] paramNames, Object [ ] values ) throws DataAccessException {
//
//		List < T > results = ( List < T > ) getHibernateTemplate( ).findByNamedParam( query, paramNames, values );
//		return results;
//
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#findByNamedParam(java.lang.Class,
	 * java.lang.String, java.util.Map)
	 */
//	public < T > List < T > findByNamedParam( Class < T > entityClass, String query, Map < String, ? > params )
//			throws DataAccessException {
//
//		String [ ] paramNames = new String[params.size( )];
//		Object [ ] values = new Object[params.size( )];
//
//		List < String > keys = new ArrayList < String >( params.keySet( ) );
//		for ( int i = 0 ; i < keys.size( ) ; i++ ) {
//			String k = keys.get( i );
//			paramNames[ i ] = k;
//			values[ i ] = params.get( k );
//		}
//
//		List < T > results = ( List < T > ) getHibernateTemplate( ).findByNamedParam( query, paramNames, values );
//		return results;
//
//	}

	public < T > List < T > getSelectedLimitResult( Query hibernateQuery, int firstResultSet, int maxResultSet )
			throws DataAccessException {

		// Session hibernateSession = null ;//this.getHibernateTemplate(
		// ).getSessionFactory( ).getCurrentSession( );
		// boolean isTmpSession = false ;
		// if( hibernateSession == null ){
		// hibernateSession = this.getHibernateTemplate( ).getSessionFactory(
		// ).openSession( );
		// isTmpSession = true ;
		// }
		// Query hibernateQuery = hibernateSession.createQuery( query );
		// hibernateQuery.setParameter( "isCardDesign",(short) 1 );

		hibernateQuery.setFirstResult( 0 );
		hibernateQuery.setMaxResults( maxResultSet );
		hibernateQuery.setFetchSize( maxResultSet ); // added by Turab to limit
														// the fetch size
		List < T > results = hibernateQuery.list( );

		// if( isTmpSession ) {
		// hibernateSession.close( );
		// }

		return results;
	}

//	public Session getHibernateSession( ) {
//
//		return this.getHibernateTemplate( ).getSessionFactory( ).openSession( );
//	}

	public boolean closeHibernateSession( Session hibernateSession ) {

		boolean isConnectionClosed = true;
		if ( hibernateSession != null ) {
			try {
				hibernateSession.close( );
			} catch ( HibernateException hex ) {
				isConnectionClosed = false;
			}
		} else {
			isConnectionClosed = false;
		}

		return isConnectionClosed;
	}

	/**************************************** Modified/Added Methods by Waqas Nisar ***********************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.IBaseDAO#findByNamedParam(java.lang.StringBuilder
	 * , java.lang.String[], java.lang.Object[])
	 */

//	public < T > List < T > findByNamedParam( StringBuilder query, String [ ] params, Object [ ] values )
//			throws DataAccessException {
//
//		List < T > results = ( List < T > ) getHibernateTemplate( )
//				.findByNamedParam( query.toString( ), params, values );
//		return results;
//
//	}

	public < T > List < T > findByNamedParam( StringBuilder query, List < String > params, List < Object > values )
			throws DataAccessException {

		return findByNamedParam( query, params.toArray( new String[0] ), values.toArray( ) );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#save(java.lang.Object)
	 */
	public < T > boolean save( T entity ) throws DataAccessException {

		// Modified method to manage audit logs : (Waqas Nisar)
		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );

		/*
		 * if (sessionBean == null){ throw new SessionbeanMissingException( ); }
		 * 
		 * if (! NumaricUtil.isEmpty( sessionBean.getUserId( ) )){
		 * baseEntity.setCreatedBy( sessionBean.getUserId( ) );
		 * baseEntity.setCreatedDate( DateUtil.getCurrentTimestamp() );
		 * baseEntity.setLoginSessionId( sessionBean.getDbLoginSessionId( ) ); }
		 */

		getCurrentSession( ).save(entity);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#update(java.lang.Object)
	 */
	public < T > boolean update( T entity ) throws DataAccessException {

		// Modified method to manage audit logs : (Waqas Nisar)
		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );

		/*
		 * if (sessionBean == null){ throw new SessionbeanMissingException( ); }
		 * 
		 * if (! NumaricUtil.isEmpty( sessionBean.getUserId( ) )){
		 * baseEntity.setUpdatedBy( sessionBean.getUserId( ) );
		 * baseEntity.setUpdatedDate( DateUtil.getCurrentTimestamp() );
		 * baseEntity.setLoginSessionId( sessionBean.getDbLoginSessionId( ) ); }
		 */

		getCurrentSession( ).update( entity );

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#saveOrUpdate(java.lang.Object)
	 */
	public < T > boolean saveOrUpdate( T entity ) throws DataAccessException {

		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );

		// Modified method to manage audit logs : (Waqas Nisar)

		/*
		 * if (sessionBean == null){ throw new SessionbeanMissingException( ); }
		 * 
		 * if (! NumaricUtil.isEmpty( sessionBean.getUserId( ) )){ if
		 * (baseEntity.getIsSave( ) == null){ throw new
		 * MessageException("BaseEntity (IsSave) Flag is Null"); } if (
		 * baseEntity.getIsSave( ) ) { // save case this.save( baseEntity );
		 * }else{ this.update( baseEntity ); } }
		 */

		getCurrentSession( ).saveOrUpdate( entity );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#remove(java.lang.Object)
	 */
	public < T > boolean remove( T entity ) throws DataAccessException, MessageException {

		// Modified method to manage audit logs : (Waqas Nisar)
		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );

		/*
		 * if (sessionBean == null){ throw new SessionbeanMissingException( ); }
		 */

		getCurrentSession( ).delete( entity );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#merge(java.lang.Object)
	 */
//	public < T > T merge( T entity ) throws DataAccessException {
//
//		// Modified method to manage audit logs : (Waqas Nisar)
//		BaseEntity baseEntity = ( BaseEntity ) entity;
//		SessionBean sessionBean = baseEntity.getSessionBean( );
//
//		/*
//		 * if (sessionBean == null){ throw new SessionbeanMissingException( ); }
//		 * 
//		 * if (! NumaricUtil.isEmpty( sessionBean.getUserId( ) )){
//		 * baseEntity.setUpdatedBy( sessionBean.getUserId( ) );
//		 * baseEntity.setUpdatedDate( DateUtil.getCurrentTimestamp() );
//		 * baseEntity.setLoginSessionId( sessionBean.getDbLoginSessionId( ) ); }
//		 */
//
//		return ( T ) getHibernateTemplate( ).merge( entity );
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#saveUpdateRemove(java.lang.Object)
	 */
	public < T > boolean saveUpdateRemove( T entity ) throws DataAccessException {

		BaseEntity baseEntity = ( BaseEntity ) entity;
		if ( baseEntity.getIsSave( ) ) { // save case
			this.save( entity );
		} else {
			if ( ! BooleanUtil.getBooleanValue( baseEntity.getIsDelete( )) ) { // update case
				this.update( entity );
			} else { // delete case
				this.remove( entity );
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#saveUpdateRemoveAll(java.util.Collection
	 * )
	 */
	public < T > boolean saveUpdateRemoveAll( Collection < T > entities ) throws DataAccessException {

		for ( T entity : entities ) {
			this.saveUpdateRemove( entity );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#removeAll(java.lang.Class)
	 */
//	public < T > boolean removeAll( Class < T > entityClass ) throws DataAccessException {
//
//		getHibernateTemplate( ).deleteAll( this.findAll( entityClass ) );
//		return true;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.dao.base.IBaseDAO#findAll(java.lang.Class)
	 */
	public < T > List < T > findAll( Class < T > entityClass ) throws DataAccessException {

		/*
		 * List < T > results = getHibernateTemplate( ).loadAll( entityClass );
		 * Set < T > set = new HashSet < T >( results ); results = new ArrayList
		 * < T >( set ); return results;
		 */

		StringBuilder query = new StringBuilder( );
		query.append( " SELECT ent  " )
				.append( " FROM " ).append( entityClass.getSimpleName( ) ).append( "  ent  " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " WHERE ent.isDeleted = null OR ent.isDeleted != 1 " );
		}

		return this.findQuery( query.toString( ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#findAllSortByName(java.lang.Class)
	 */
	public < T > List < T > findAllSortByName( Class < T > entityClass ) throws DataAccessException {

		StringBuilder query = new StringBuilder( );
		query.append( "SELECT ent " )
				.append( "FROM " ).append( entityClass.getSimpleName( ) ).append( " ent " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " WHERE ent.isDeleted = null OR ent.isDeleted != 1 " );
		}
		query.append( "ORDER BY  ent.name  asc" );

		return this.findQuery( query.toString( ) );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#findAllSortByProperty(java.lang.Class,
	 * java.lang.String)
	 */
	public < T > List < T > findAllSortByProperty( Class < T > entityClass, String propertyName )
			throws DataAccessException {

		StringBuilder query = new StringBuilder( );
		query.append( "SELECT ent  " )
				.append( "FROM " ).append( entityClass.getSimpleName( ) ).append( "  ent  " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " WHERE ent.isDeleted = null OR ent.isDeleted != 1 " );
		}
		query.append( "ORDER BY " )
				.append( propertyName )
				.append( " asc" );

		return this.findQuery( query.toString( ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#findByCriteria(java.lang.Object,
	 * java.lang.String, java.lang.Object)
	 */
	public < T > List < T > findByCriteria( Class < T > entity, String propertyName, Object propertyValue ) {

		StringBuilder query = new StringBuilder( );
		query.append( "FROM " ).append( entity.getName( ) ).append( " vp " )
				.append( "WHERE vp." ).append( propertyName ).append( " = :propertyValue " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " AND ( vp.isDeleted = null OR vp.isDeleted != 1 ) " );
		}

		Object [ ] values = { propertyValue };
		String [ ] params = { "propertyValue" };

		return this.findByNamedParam( query, params, values );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#findByNotInCriteria(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	public < T > List < T > findByNotInCriteria( Class < T > entity, String propertyName, Object notIn ) {

		StringBuilder query = new StringBuilder( );
		query.append( "FROM " ).append( entity.getName( ) ).append( " vp " )
				.append( "WHERE vp." ).append( propertyName ).append( " NOT IN :notIn " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " AND ( vp.isDeleted = null OR vp.isDeleted != 1 ) " );
		}

		Object [ ] values = { notIn };
		String [ ] params = { "notIn" };

		return this.findByNamedParam( query, params, values );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.IBaseDAO#removeByProperty(java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	public < T > Boolean removeByProperty( Class < T > entityClass, String propertyName, Long propertyValue )
			throws DataAccessException {

		StringBuilder query = new StringBuilder( );
		query.append( "DELETE FROM " ).append( entityClass.getName( ) ).append( " vp " )
				.append( "WHERE vp." ).append( propertyName ).append( " = ?" );

		return this.bulkUpdate( query.toString( ), propertyValue );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#softDeleteByProperty(java.lang.Class,
	 * java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public < T > Boolean softDeleteByProperty(
			Class < T > entityClass, String propertyName, Long propertyValue, Long userId )
			throws DataAccessException {

		StringBuilder query = new StringBuilder( );
		query.append( "UPDATE " ).append( entityClass.getName( ) ).append( " vp " )
				.append( "SET vp.isDeleted = ?, vp.updatedBy = ?, vp.updatedDate = ? " )
				.append( "WHERE vp." ).append( propertyName ).append( " = ?" );

		Object [ ] values = { ( short ) 1, userId, DateUtil.now( ), propertyValue };
		// if(true)throw new DataIntegrityViolationException(
		// "Record not deleted due to further reference in the system." );
		return this.bulkUpdate( query.toString( ), values );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.ICurdDAO#saveAll(java.util.Collection)
	 */
	public < T > boolean saveAll( Collection < T > entities ) throws DataAccessException {

		for ( T entity : entities ) {
			this.save( entity );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.ICurdDAO#updateAll(java.util.Collection)
	 */
	public < T > boolean updateAll( Collection < T > entities ) throws DataAccessException {

		for ( T entity : entities ) {
			this.update( entity );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tabs.bcrs.fo.base.dao.ICurdDAO#saveUpdateAll(java.util.Collection)
	 */
	public < T > boolean saveUpdateAll( Collection < T > entities ) throws DataAccessException {

		for ( T entity : entities ) {
			this.saveOrUpdate( entity );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#bulkUpdate(java.lang.String,
	 * java.lang.Object)
	 */
//	public boolean bulkUpdate( String queryString, Object value ) throws DataAccessException {
//
//		int count = getHibernateTemplate( ).bulkUpdate( queryString, value );
//		return ( count > 0 ? true : false );
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#bulkUpdate(java.lang.String,
	 * java.lang.Object[])
	 */
//	public boolean bulkUpdate( String queryString, Object [ ] values ) throws DataAccessException {
//
//		int count = getHibernateTemplate( ).bulkUpdate( queryString, values );
//		return ( count > 0 ? true : false );
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#bulkUpdate(java.lang.String)
	 */
//	public boolean bulkUpdate( String queryString ) throws DataAccessException {
//
//		int count = getHibernateTemplate( ).bulkUpdate( queryString );
//		return ( count > 0 ? true : false );
//	}
//
//	/**
//	 * @return the hibernateTemplate
//	 */
//	protected HibernateTemplate getHibernateTemplate( ) {
//
//		hibernateTemplate.setCacheQueries( IConfigConst.ICacheConst.isCachable );
//		return hibernateTemplate;
//	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#findByFilter(java.lang.Class, java.lang.Class, java.lang.Class, java.lang.String)
	 */
	public < T, S, U > List < T > findByFilter( Class < T > entity, Class < S > filter, Class< U > category, String code ) throws DataAccessException {

		StringBuilder query = new StringBuilder( );
		query.append("SELECT vp.").append( StringUtil.entityToFieldName( entity ) )
		.append( " FROM " ).append( filter.getName( ) ).append( " vp " ).append( "WHERE vp." )
		.append( StringUtil.entityToFieldName( category ) ).append( ".code = :propertyValue " );

		if ( SoftDeleteEnableDisableEnum.SOFT_DELETE_ENABLED.getValue( ) ) {
			query.append( " AND ( vp.isDeleted = null OR vp.isDeleted != 1 ) " );
		}

		Object [ ] values = { code };
		String [ ] params = { "propertyValue" };

		return this.findByNamedParam( query, params, values );
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tabs.bcrs.fo.base.dao.IBaseDAO#findByCriteriaSingleItem(java.lang.Class, java.lang.String, java.lang.Object)
	 */
	public <T > T findByCriteriaSingleItem( Class < T > entity, String propertyName, Object propertyValue ) throws DataAccessException{
		List < T > resultList = this.findByCriteria( entity, propertyName, propertyValue );
		return CollectionUtil.isNotEmpty( resultList ) ? resultList.get( 0 ) : null;
	}
	
	protected Session getCurrentSession( ) {
		/*return entityManager.unwrap(Session.class);*/
		return  sessionFactory.getCurrentSession();
	}

	@Override
	public <T> T merge(T entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> boolean removeAll(Class<T> entityClass)
			throws DataAccessException, MessageException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> List<T> findQuery(String queryString) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T findById(Class<T> entityClass, Long id)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByNamedQueryAndNamedParam(Class<T> entityClass,
			String queryName, String[] paramNames, Object[] values)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByNamedParam(Class<T> entityClass, String query,
			String[] paramNames, Object[] values) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByNamedParam(Class<T> entityClass, String query,
			Map<String, ?> params) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getHibernateSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByNamedParam(StringBuilder query, String[] params,
			Object[] values) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean bulkUpdate(String queryString, Object value)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bulkUpdate(String queryString) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bulkUpdate(String queryString, Object[] values)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
