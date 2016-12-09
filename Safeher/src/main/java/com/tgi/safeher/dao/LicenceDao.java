package com.tgi.safeher.dao;

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
import com.tgi.safeher.dao.base.SafeherDAO;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.DriverInfoEntity;
import com.tgi.safeher.entity.DriverLicenceHistoryEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.base.BaseEntity;

@Repository
public class LicenceDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4053922634525257160L;

	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	SessionFactory sessionFactory;
	/*
	@PersistenceContext
	EntityManager entityManager;*/

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

	public AppUserEntity getAppUserById(Integer accountId) throws DataAccessException{

		Criteria cr = getCurrentSession().createCriteria(
				AppUserEntity.class);
		cr.add(Restrictions.eq("appUserId", accountId));
		return (AppUserEntity) cr.uniqueResult();
	}

	public StateProvinceEntity getStateProvinceById(int stateId) {
		StateProvinceEntity resultObj = null;
		resultObj = (StateProvinceEntity) getCurrentSession()
				.get(StateProvinceEntity.class, stateId);
		return resultObj;
	}

	public DriverInfoEntity getByLicenceNo(String LicenceNo) {
		Criteria cr = getCurrentSession().createCriteria(
				DriverInfoEntity.class);
		cr.add(Restrictions.eq("currentLicenceNo", LicenceNo));
		return (DriverInfoEntity) cr.uniqueResult();

	}

	public boolean checkIfLicenceUnique(String LicenceNo) {
		Criteria cr = getCurrentSession().createCriteria(
				DriverInfoEntity.class);
		cr.add(Restrictions.eq("currentLicenceNo", LicenceNo));
		return cr.list().size() <= 0;

	}
	
	public <T> T get(Class<T> type, Integer id) {
		return (T) getCurrentSession().get(type, id);
	}

	public DriverLicenceHistoryEntity checkLicenceHistory(
			DriverInfoEntity entity, String active) {
		Criteria cr = getCurrentSession().createCriteria(
				DriverLicenceHistoryEntity.class);
		cr.add(Restrictions.eq("driverInfo", entity));
		cr.add(Restrictions.eq("isActive", active));
		return (DriverLicenceHistoryEntity) cr.uniqueResult();
	}

	public DriverLicenceHistoryEntity findByDriverInfo(DriverInfoEntity driverInfo) {
		  final Criteria cr = getCurrentSession().createCriteria(DriverLicenceHistoryEntity.class);
		  cr.add(Restrictions.eq("driverInfo", driverInfo));
		  return (DriverLicenceHistoryEntity) cr.uniqueResult();
	}

}
