package com.tgi.safeher.map.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MapDao {

	private static final long serialVersionUID = -4053922634525257160L;

	/*Deprecated as we are using entity manager now*/
	@Autowired
	private SessionFactory sessionFactory;
	
	/*@PersistenceContext
	private EntityManager em;*/

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
		/*return em.unwrap(Session.class);*/
	}

	public <T> T findById(Class<T> entityClass, Integer id)
			throws DataAccessException {
		// T t = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		return (T) getCurrentSession().get(entityClass, id);
	}

	public <T> T findBy(final Class<T> entity, final String entityAttribute,
			final String value) throws DataAccessException {
		/*final Criteria cr = em.unwrap(Session.class).createCriteria(
				entity);*/
		final Criteria cr = getCurrentSession().createCriteria(
		entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	public <T> T findByParamId(final Class<T> entity, final String entityAttribute,
			final Integer value) throws DataAccessException {
		final Criteria cr =  getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	public List<Object[]> getAllPassangerDriver(String isDriver) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity as a inner join a.person as p where a.isDriver=:isDriver";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("isDriver", isDriver);
		return (List<Object[]>) queryObj.list();
	}
	
	public < T > boolean saveOrUpdate( T entity ) throws DataAccessException {
		
		Session session = getCurrentSession();
		session.saveOrUpdate( entity );
		return true;
	}

}
