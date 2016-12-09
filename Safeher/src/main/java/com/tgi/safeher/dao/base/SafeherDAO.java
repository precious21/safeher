package com.tgi.safeher.dao.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.base.BaseEntity;

public class SafeherDAO implements ISafeferDAO {

	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	protected SessionFactory sessionFactory;  
	
	/*@PersistenceContext
	EntityManager entityManager;*/

	
	@Override
	public <T> T findBy(Class<T> entity, String entityAttribute, String value)
			throws DataAccessException {
		final	Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	@Override
	public <T> List<T> getAll(Class<T> type) {
		final Criteria crit = getCurrentSession()
				.createCriteria(type);
		return crit.list();
	}

	@Override
	public <T> T get(Class<T> type, Integer id) {
		return (T) getCurrentSession().get(type, id);
	}

	@Override
	public void delete(Object object) {
		getCurrentSession().delete(object);
	}

	@Override
	public <T> T findByOject(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	@Override
	public <T> boolean save(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().save(entity);
		return true;
	}

	@Override
	public <T> boolean update(T entity) throws DataAccessException {
		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );
		getCurrentSession( ).update( entity );
		return true;
	}

	@Override
	public <T> boolean saveOrUpdate(T entity) throws DataAccessException {
		Session session = getCurrentSession();
		session.saveOrUpdate( entity );
		return true;
	}

	@Override
	public Session getCurrentSession() {
		/*return entityManager.unwrap(Session.class);*/
		return sessionFactory.getCurrentSession();
	}

}
