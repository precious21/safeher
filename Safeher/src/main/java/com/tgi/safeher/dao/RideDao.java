package com.tgi.safeher.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.GiftTypeEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RideFinalizeEntity;
import com.tgi.safeher.entity.RideGiftEntity;
import com.tgi.safeher.entity.RideProcessTrackingDetailEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.base.BaseEntity;

@Repository
public class RideDao {

	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
/*	@Deprecated*/
	@Autowired
	protected SessionFactory sessionFactory;  
	
	/*@PersistenceContext
	EntityManager entityManager;*/
	
	public <T> T findBy(Class<T> entity, String entityAttribute, String value)
			throws DataAccessException {
		final	Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	
	public <T> List<T> getAll(Class<T> type) {
		final Criteria crit = getCurrentSession()
				.createCriteria(type);
		return crit.list();
	}

	
	public <T> T get(Class<T> type, Integer id) {
		return (T) getCurrentSession().get(type, id);
	}

	
	public void delete(Object object) {
		getCurrentSession().delete(object);
	}

	
	public <T> T findByOject(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	
	public <T> boolean save(T entity) throws DataAccessException , Exception {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().save(entity);
		return true;
	}

	
	public <T> boolean update(T entity) throws DataAccessException {
		BaseEntity baseEntity = ( BaseEntity ) entity;
		SessionBean sessionBean = baseEntity.getSessionBean( );
		getCurrentSession( ).update( entity );
		return true;
	}

	
	public <T> boolean saveOrUpdate(T entity) throws DataAccessException {
		Session session = getCurrentSession();
		session.saveOrUpdate( entity );
		return true;
	}

	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
		/*return entityManager.unwrap(Session.class);*/
	}

	public List<RideCriteriaEntity> findCriteriaByQuery(AppUserEntity appuser) {
		final Criteria crit = getCurrentSession()
				.createCriteria(RideCriteriaEntity.class);
		crit.add(Restrictions.eq("appUser", appuser));
		crit.addOrder(Order.desc("rideCriteriaId"));
		return crit.list();

	}
	
	public UserLoginEntity findByIdParam2(Integer id){
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity aub where aub.appUser.appUserId=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (UserLoginEntity)queryObj.uniqueResult();
	}
	
	public  <T> List<T> findListByOject(Class<T> entity, String entityAttribute,
			String value,Integer firstResult ,Integer maxResult) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.setFirstResult(firstResult);
		cr.setFetchSize(maxResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}
	
	public List<RideSearchResultDetailEntity> getSpecificRideDrivers(String requestNo){
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideSearchResultDetailEntity rs "
						+"where rs.requestNo like:requestNo";
		Query queryObj = session.createQuery(query);
//		queryObj.setParameter("requestNo", requestNo);
		queryObj.setParameter("requestNo", requestNo+"%");
		return (List<RideSearchResultDetailEntity>)queryObj.list();
	}
	
	public void replaceTokensAndroid(String prevToken, String currToken) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.UserLoginEntity u set u.keyToken=:currToken where u.keyToken=:prevToken";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("prevToken", prevToken);
		queryObj.setParameter("currToken", currToken);
		queryObj.executeUpdate();
		
	}
	
	public void replaceFCMToken(String prevToken, String currToken) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.UserLoginEntity u set u.fcmToken=:currToken where u.fcmToken=:prevToken";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("prevToken", prevToken);
		queryObj.setParameter("currToken", currToken);
		queryObj.executeUpdate();
		
	}
	
	public void updateRideQuickInfo(String requestNo, 
			Integer rideProcessTrackingId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.RideQuickInfoEntity rqi set "+
						"rqi.rideProcessTracking.rideProcessTrackingId=:rideProcessTrackingId where "+
						"rqi.requestNo like:requestNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideProcessTrackingId", rideProcessTrackingId);
		queryObj.setParameter("requestNo", requestNo + "%");
		queryObj.executeUpdate();
		
	}
	
	public boolean checkForRideTrackingReContinued(Integer rideProcessTrackingId, 
			Integer processActionId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideProcessTrackingDetailEntity rptd "+
						"where rptd.rideProcessTracking.rideProcessTrackingId=:rideProcessTrackingId and "+
						"rptd.processAction.processActionId=:processActionId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideProcessTrackingId", rideProcessTrackingId);
		queryObj.setParameter("processActionId", processActionId);
		List<RideProcessTrackingDetailEntity> list = queryObj.list();
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
		
	}

	public <T> T findById(Class<T> entityClass, Integer id)
			throws DataAccessException {
		// T t = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		return (T) getCurrentSession().get(entityClass, id);
	}


	public void updateGiftRideByNumber(AppUserEntity appUser, String reciverNum, GiftTypeEntity giftType) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.RideGiftEntity rqi set "+
						"rqi.appUserByReciverUserId=:reciveAppUser where "+
						"rqi.reciverNum like:reciverNum and rqi.giftType=:giftType";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("reciveAppUser", appUser);
		queryObj.setParameter("reciverNum", reciverNum );
		queryObj.setParameter("giftType", giftType);
		queryObj.executeUpdate();
	}


	public void updateGiftRideByEmail(AppUserEntity appUser, String email, GiftTypeEntity giftType) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.RideGiftEntity rqi set "+
						"rqi.appUserByReciverUserId=:reciveAppUser where "+
						"rqi.email like:reciverNum and rqi.giftType=:giftType";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("reciveAppUser", appUser);
		queryObj.setParameter("reciverNum", email);
		queryObj.setParameter("giftType", giftType);
	
		queryObj.executeUpdate();
	}
	
	public<T> List<T> findListByOject(Class<T> entity, String entityAttribute,
			Object value ,Integer firstResult ,Integer maxResult) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.setFirstResult(firstResult);
		cr.setFetchSize(maxResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}
	
	public <T> List<T> findListByOjectforGift(Class<T> entity,
			String entityAttribute1, String entityAttribute2, String value1,
			String value2, Integer firstResult, Integer maxResult,
			GiftTypeEntity GiftType, StatusEntity giftReciveStatus,
			StatusEntity giftNotReciveStatus) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		// cr.add(Restrictions.eq(entityAttribute, value));
		Criterion crt1= Restrictions.eq(entityAttribute1, value1);
		value2=		value2.length() > 10 ? value2.substring(value2.length() - 10) : value2;
		Criterion crt2= Restrictions.like(entityAttribute2, value2, MatchMode.EXACT);
	//	Criterion crt2= Restrictions.eq(entityAttribute2, value2);
	
		cr.add(Restrictions.or(crt1,crt2));
		cr.add(Restrictions.eq("giftType",GiftType)); 
		Criterion crt3= Restrictions.eq("status", giftReciveStatus);
		Criterion crt4= Restrictions.eq("status", giftNotReciveStatus);
		cr.add(Restrictions.or(crt3,crt4));
		cr.addOrder(Order.desc("rideGiftId"));
		cr.setFirstResult(firstResult);
		cr.setFetchSize(maxResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}

	public <T> List<T> findListByOjectforGiftConsume(Class<T> entity,
			String entityAttribute1, String entityAttribute2, String value1,
			String value2, Integer firstResult, Integer maxResult,
			GiftTypeEntity GiftType, StatusEntity giftReciveStatus,
			StatusEntity giftNotReciveStatus) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		//cr.add(Restrictions.eq(entityAttribute, value));
		Criterion crt1= Restrictions.eq(entityAttribute1, value1);
		value2=		value2.length() > 10 ? value2.substring(value2.length() - 10) : value2;
		Criterion crt2= Restrictions.like(entityAttribute2, value2, MatchMode.EXACT);
	//	Criterion crt2= Restrictions.eq(entityAttribute2, value2);
		cr.add(Restrictions.or(crt1,crt2));
		cr.add(Restrictions.eq("giftType",GiftType)); 
		Criterion crt3= Restrictions.eq("status", giftReciveStatus);
		Criterion crt4= Restrictions.eq("status", giftNotReciveStatus);
		cr.add(Restrictions.or(crt3,crt4));
		cr.addOrder(Order.desc("rideGiftId"));
		cr.setFirstResult(firstResult);
		cr.setFetchSize(maxResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}
	public List<RideGiftEntity> findListByOject(
			String string, AppUserEntity appUser, Integer valueOf,
			Integer valueOf2, GiftTypeEntity giftType) {
		final Criteria cr = getCurrentSession().createCriteria(
				RideGiftEntity.class);
		cr.add(Restrictions.eq(string, appUser));
		cr.add(Restrictions.eq("giftType",giftType));
		cr.addOrder(Order.desc("rideGiftId"));
		cr.setFirstResult(valueOf);
		cr.setFetchSize(valueOf2);
		cr.setMaxResults(valueOf2);
		return (List<RideGiftEntity>) cr.list();
	}


	public RideGiftEntity findByGiftNo(String giftNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideGiftEntity aub where aub.giftNo=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", giftNo);
		List<RideGiftEntity> list = queryObj.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
		
	//	return (RideGiftEntity)queryObj.uniqueResult();
	}
}
