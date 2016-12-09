package com.tgi.safeher.dao.base;

import java.util.List;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

public interface ISafeferDAO {

	public < T > T findBy(final Class<T> entity,final String entityAttribute,final String value ) throws DataAccessException ;
	
	public <T> List<T> getAll(final Class<T> type);
	
	public <T> T get(final Class<T> type, final Integer id);
	
	public void delete(final Object object);
	
	public < T > T findByOject(final Class<T> entity,final String entityAttribute,final Object value ) throws DataAccessException;
	
	public < T > boolean save( T entity ) throws DataAccessException;
	
	public < T > boolean update( T entity ) throws DataAccessException ;
	
	public < T > boolean saveOrUpdate( T entity ) throws DataAccessException;
	
	public Session getCurrentSession();
}
