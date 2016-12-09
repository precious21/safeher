package com.tgi.safeher.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.ColorEntity;
import com.tgi.safeher.entity.VehicleMakeEntity;
import com.tgi.safeher.entity.VehicleModelEntity;
import com.tgi.safeher.entity.base.BaseEntity;

@Repository
public class VehicleDao {
	
	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	SessionFactory sessionFactory;
	
	/*@PersistenceContext
	EntityManager entityManager;*/
	
	@SuppressWarnings("unchecked")
	public List<VehicleMakeEntity> getVehicleMake()  throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(VehicleMakeEntity.class);
		return  cr.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<VehicleModelEntity> getVehicleModelByMake(VehicleMakeEntity vehicle)  throws DataAccessException{
		Criteria cr = getCurrentSession().createCriteria(VehicleModelEntity.class);
		cr.add(Restrictions.eq("vehicleMake", vehicle));
		return  cr.list();
		
	}
	public VehicleMakeEntity getByMakeId(Integer vehicleMakeId)  throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(VehicleMakeEntity.class);
		 cr.add(Restrictions.eq("vehicleMakeId", vehicleMakeId));
		 return (VehicleMakeEntity) cr.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ColorEntity> getVehicleColor()  throws DataAccessException{
		 Criteria cr =getCurrentSession().createCriteria(ColorEntity.class);
		 cr.addOrder(Order.asc("name"));
		return  cr.list();
		
	}
	

	public <T> boolean save(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().save(entity);
		return true;
	}

	public <T> boolean update(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().update(entity);
		return true;
	}

	public <T> boolean saveOrUpdate(T entity) throws DataAccessException {
		Session session = getCurrentSession();
		session.saveOrUpdate(entity);
		return true;
	}

	public Session getCurrentSession() {
		/*return entityManager.unwrap(Session.class);*/
		return sessionFactory.getCurrentSession();
	}

	
	public <T> T findBy(Class<T> entity, String entityAttribute, String value)
			throws DataAccessException {
		final	Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	
	public <T> List<T> getAll(Class<T> type)  throws DataAccessException{
		final Criteria crit = getCurrentSession()
				.createCriteria(type);
		return crit.list();
	}

	
	public <T> T get(Class<T> type, Integer id)  throws DataAccessException{
		return (T) getCurrentSession().get(type, id);
	}

	
	public void delete(Object object)  throws DataAccessException{
		getCurrentSession().delete(object);
	}

	
	public <T> T findByOject(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}
	
	public <T> T findByOjectForVehical(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.add(Restrictions.eq("isActive", "1"));
		return (T) cr.uniqueResult();
	}
	
	
	public AppUserVehicleEntity checkAppUserVehicleInfo(
			AppUserEntity appUser)  throws DataAccessException{
		Criteria cr = getCurrentSession().createCriteria(
				AppUserVehicleEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		cr.add(Restrictions.eq("isActive", "1"));
		return (AppUserVehicleEntity) cr.uniqueResult();

	}
	
	public <T> T findById(Class<T> entityClass, Integer id) throws DataAccessException{
		return (T) getCurrentSession().get(entityClass, id);
	}
	
	public AppUserVehicleEntity findAppUserVehicle(Integer vehicleInfoId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserVehicleEntity auv where auv.vehicleInfo.vehicleInfoId=:vehicleInfoId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("vehicleInfoId", vehicleInfoId);
		return (AppUserVehicleEntity) queryObj.uniqueResult();
	}

}
