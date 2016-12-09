package com.tgi.safeher.dao;

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

import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.UserLoginEntity;

@Repository
public class GoogleMapDao {

	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	private SessionFactory sessionFactory;
	
	/*@PersistenceContext
	private EntityManager entityManager;*/

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		/*return entityManager.unwrap(Session.class);*/
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Returns Active Driver on the base of suburb name
	 * 
	 * @param suburb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveDriverLocationEntity> getDriverList(String suburb) {

		String hql = "select distinct a from com.tgi.safeher.entity.ActiveDriverLocationEntity a "
				+ "join a.suburb s " + "where s.name =:suburb and a.isPhysical='1' and a.isOnline='1' and a.isRequested='0' " +
						"and a.isBooked='0' ";

		Query query = getCurrentSession().createQuery(hql);
		query.setString("suburb", suburb);
		List<ActiveDriverLocationEntity> activeDriversLocationEntities = query
				.list();

		return activeDriversLocationEntities;

	}
	
	/**
	 * This method return list of provided class where its entity value fulfill the condition
	 * @param type Class
	 * @param entityAttribute Attribute name
	 * @param value Attribute value
	 * @return
	 */
	
	public <T> List<T> getAllByAttribute(Class<T> type, String entityAttribute, String value) {
		final Criteria criteria = getCurrentSession()
				.createCriteria(type);
		criteria.add(Restrictions.eq(entityAttribute, value));
		return criteria.list();
	}
	
	/**
	 * This method return all the active entities
	 * @param type Class 
	 * @return
	 */
	public <T> List<T> getAll(Class<T> type) {
		final Criteria criteria = getCurrentSession().createCriteria(type);
		return criteria.list();
	}

	public List<ActiveDriverLocationEntity> getAllActiveDrivers() {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.ActiveDriverLocationEntity ad where ad.isPhysical='1' and ad.isOnline='1'";
		Query queryObj = session.createQuery(query);
		return queryObj.list();
	}

	public UserLoginEntity findByIdParam2(Integer id){
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity aub where aub.appUser.appUserId=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (UserLoginEntity)queryObj.uniqueResult();
	}

	public List<ActiveDriverLocationEntity> getDriverListEtn(String suburb) {

		String hql = "select adl from ActiveDriverLocationEntity adl where adl.suburb.city.name = :suburb and adl.isPhysical='1' and adl.isOnline='1' and adl.isRequested=0" +
						"and adl.isBooked=0";
		Query query = getCurrentSession().createQuery(hql);
		query.setString("suburb", suburb);
		List<ActiveDriverLocationEntity> activeDriversLocationEntities = query.list();
		return activeDriversLocationEntities;

	}
	
	/**
	 * Get Active drivers from SQL Query
	 * @param lat
	 * @param lng
	 * @param distance
	 * @return List<ActiveDriverLocationEntity>
	 */
	public List<ActiveDriverLocationEntity> getDriverListFromQuery(String lat, String lng, String distance) {
		
		String hql= "select adl from ActiveDriverLocationEntity adl where 6378.137 * ACos( Cos( RADIANS(adl.latValue) ) * Cos( RADIANS( :Lat ) ) * Cos( RADIANS( :Lon ) - RADIANS(adl.longValue) ) + Sin( RADIANS(adl.latValue) ) * Sin( RADIANS( :Lat ) ) ) <= :distance  and adl.isPhysical='1' and adl.isOnline='1' and adl.isRequested=0 and adl.isBooked=0";
		Query query = getCurrentSession().createQuery(hql);
		query.setString("Lat", lat);
		query.setString("Lon", lng);
		query.setString("distance", distance);
		List<ActiveDriverLocationEntity> activeDriversLocationEntities = query.list();
		return activeDriversLocationEntities;

	}
	

}
