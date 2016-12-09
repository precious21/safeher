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

import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.BankAccountInfoEntity;
import com.tgi.safeher.entity.BankEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.CreditCardTypeEntity;
import com.tgi.safeher.entity.base.BaseEntity;

@Repository
public class PaymentDao {

	/*
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 */
	/*@Deprecated*/
	@Autowired
	SessionFactory sessionFactory;
	
	/*@Autowired
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
		return sessionFactory.getCurrentSession();
	/*	return entityManager.unwrap(Session.class);*/
	}

	
	public AppUserPaymentInfoEntity checkAppUserPaymentInfo(AppUserEntity appUser)throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(AppUserPaymentInfoEntity.class);
		 cr.add(Restrictions.eq("appUser", appUser));
		 return (AppUserPaymentInfoEntity) cr.uniqueResult();
		
	}

	public Object findByIdParamCommon(String className, String param, Integer id)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity."+className+" obj where obj."+param+"=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (Object) queryObj.uniqueResult();
	}
	
	public CreditCardTypeEntity creditCardTypeInfo(String creditCrdTypeId){
		 Criteria cr = getCurrentSession().createCriteria(CreditCardTypeEntity.class);
		 cr.add(Restrictions.eq("creditCardTypeId", Integer.valueOf(creditCrdTypeId)));
		 return (CreditCardTypeEntity) cr.uniqueResult();
		
	}
	
	public CreditCardInfoEntity checkCreditCardInfo(AppUserPaymentInfoEntity appUser){
		 Criteria cr = getCurrentSession().createCriteria(CreditCardInfoEntity.class);
		 cr.add(Restrictions.eq("appUserPaymentInfo", appUser));
		 return (CreditCardInfoEntity) cr.uniqueResult();		
	}
	
	public CreditCardInfoEntity checkUniqueCreditCard(String CardNo,  AppUserPaymentInfoEntity appPaymentInfo){
		 Criteria cr = getCurrentSession().createCriteria(CreditCardInfoEntity.class);
		 cr.add(Restrictions.eq("cardNumber", CardNo));
		 cr.add(Restrictions.eq("appUserPaymentInfo", appPaymentInfo));
		 return (CreditCardInfoEntity) cr.uniqueResult();
	}
	
	public BankAccountInfoEntity checkBankAccountInfo(AppUserPaymentInfoEntity appUser){
		 Criteria cr = getCurrentSession().createCriteria(BankAccountInfoEntity.class);
		 cr.add(Restrictions.eq("appUserPaymentInfo", appUser));
		 return (BankAccountInfoEntity) cr.uniqueResult();		
	}
	public BankAccountInfoEntity checkUniqueAccount(String IBAN , AppUserPaymentInfoEntity appPaymentInfo) throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(BankAccountInfoEntity.class);
		 cr.add(Restrictions.eq("ibanNo", IBAN));
		 cr.add(Restrictions.eq("appUserPaymentInfo", appPaymentInfo));
		 return (BankAccountInfoEntity) cr.uniqueResult();
	}

	public BankEntity getBankInfo(String Id)throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(BankEntity.class);
		 cr.add(Restrictions.eq("bankId", Integer.valueOf(Id)));
		 return (BankEntity) cr.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<BankEntity> getBankList(){
		/* Criteria cr = entityManager.unwrap(Session.class).createCriteria(BankEntity.class);*/
		Criteria cr = getCurrentSession().createCriteria(BankEntity.class);
		return  cr.list();
		
	}
	public <T> T findBy(Class<T> entity, String entityAttribute, String value)
			throws DataAccessException {
		final	Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}
	
	public <T> T findById(Class<T> entityClass, Integer id) throws DataAccessException{
//		T t = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		return (T) getCurrentSession().get(entityClass, id);
	}
	

	public <T> List<T> getAll(Class<T> type) {
		final Criteria crit = getCurrentSession()
				.createCriteria(type);
		return crit.list();
	}

	
	public <T> T get(Class<T> type, Integer id) throws DataAccessException{
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
		List<T> list = cr.list();//TODO: Check Unique Result Condition
		return  list.get(0);
	}
	
	public List<CreditCardInfoEntity> getCreditCardInfo(AppUserPaymentInfoEntity Id) throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(CreditCardInfoEntity.class);
		 cr.add(Restrictions.eq("appUserPaymentInfo", Id));
		 return cr.list();
	}

	public void setCreditCardUniqueIsDefult(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.CreditCardInfoEntity u set u.isDefault='0' where u.appUserPaymentInfo.appUserPaymentInfoId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.executeUpdate();
		
	}

	public void setUniqueDefultForBankAccount(Integer appUserPaymentInfoId) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.BankAccountInfoEntity u set u.isDefault='0' where u.appUserPaymentInfo.appUserPaymentInfoId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserPaymentInfoId);
		queryObj.executeUpdate();
		
	}

	public void setUniqueDefultForBankAccountExceptNewCreated(Integer appUserPaymentInfoId, Integer bankAccountInfoId) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.BankAccountInfoEntity u set u.isDefault='0' where "+
					"u.appUserPaymentInfo.appUserPaymentInfoId=:appUserId and u.bankAccountInfoId<>:bankAccountInfoId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserPaymentInfoId);
		queryObj.setParameter("bankAccountInfoId", bankAccountInfoId);
		queryObj.executeUpdate();
		
	}

	public boolean checkForUniqueAccountNo(Integer appUserPaymentInfoId, String accountNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.BankAccountInfoEntity u  where "+
					"u.appUserPaymentInfo.appUserPaymentInfoId=:appUserId and u.accountNo=:accountNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserPaymentInfoId);
		queryObj.setParameter("accountNo", accountNo);
		
		List<BankAccountInfoEntity> list = queryObj.list();
		if(list == null || list.size() <= 0){
			return false;
		}
		return  true;
		
	}

	public void setAllCreditCardNonDefault(Integer appUserPaymentInfoId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.CreditCardInfoEntity cc set cc.isDefault='0' where cc.appUserPaymentInfo.appUserPaymentInfoId=:appUserPaymentInfoId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserPaymentInfoId", appUserPaymentInfoId);
		queryObj.executeUpdate();
		
	}

	public <T> List<T> findListByOject(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException{
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (List<T>) cr.list();
	}
	
	public BankAccountInfoEntity checkUniqueBankAccount(String isDefault , AppUserPaymentInfoEntity appPaymentInfo) throws DataAccessException{
		 Criteria cr = getCurrentSession().createCriteria(BankAccountInfoEntity.class);
		 cr.add(Restrictions.eq("isDefault", isDefault));
		 cr.add(Restrictions.eq("appUserPaymentInfo", appPaymentInfo));
		 return (BankAccountInfoEntity) cr.uniqueResult();
	}

}
