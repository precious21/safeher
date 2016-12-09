package com.tgi.safeher.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.common.sessionbean.SessionBean;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusLogEntity;
import com.tgi.safeher.entity.AppUserRegTrackEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.CityEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.DriverDrivingSummaryEntity;
import com.tgi.safeher.entity.DriverLicenceHistoryEntity;
import com.tgi.safeher.entity.EmailLogEntity;
import com.tgi.safeher.entity.EmailTempleteEntity;
import com.tgi.safeher.entity.PersonAddressEntity;
import com.tgi.safeher.entity.PersonAddressHistoryEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.PostRideEntity;
import com.tgi.safeher.entity.PreRideEntity;
import com.tgi.safeher.entity.PromotionCodesEntity;
import com.tgi.safeher.entity.PromotionInfoEntity;
import com.tgi.safeher.entity.PromotionType;
import com.tgi.safeher.entity.ReasonEntity;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RideFinalizeEntity;
import com.tgi.safeher.entity.RideProcessTrackingDetailEntity;
import com.tgi.safeher.entity.RideQuickInfoEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchEntity;
import com.tgi.safeher.entity.SmsLogEntity;
import com.tgi.safeher.entity.SmsTempelteEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserElectronicResourceEntity;
import com.tgi.safeher.entity.UserFavoritiesEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserRatingDetailEntity;
import com.tgi.safeher.entity.UserSelectedCharitiesEntity;
import com.tgi.safeher.entity.ZipCodeEntity;
import com.tgi.safeher.entity.base.BaseEntity;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.impl.RideService;
import com.tgi.safeher.utils.Email;
import com.tgi.safeher.utils.StringUtil;

@Repository
public class AppUserDao {

	// public UserDao() {
	// super( UserDao.class );
	// }

	private static final long serialVersionUID = -4053922634525257160L;
	private Logger logger =Logger.getLogger(AppUserDao.class);

	/*
	 * @deprecated
	 * Session Factory is deprecated as now we are using EntityManager Instead
	 *  
	 */
	/*@Deprecated*/ 
	@Autowired
	private SessionFactory sessionFactory;

	/*@PersistenceContext
	EntityManager entityManager;*/
	
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	public Session getCurrentSession() {
		/*return entityManager.unwrap(Session.class);*/
		return sessionFactory.getCurrentSession();
	}

	public PersonEntity insert(PersonEntity entity) {

		Session session = getCurrentSession();
		session.save(entity);

		return entity;

	}

	public RideSearchEntity findRideSearch(String requestNo)
			throws DataAccessException {
		final Logger logger = Logger.getLogger(RideService.class);
		logger.info("Request No in AppUserDao :" + requestNo);
		Session session = getCurrentSession();
		String str = requestNo.trim();
		String query = "select an from RideSearchEntity as an where an.requestNo= :str";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("str", str);
		RideSearchEntity entity = (RideSearchEntity) queryObj.uniqueResult();
		if (entity != null) {
			logger.info("Entity is :" + entity.toString());
		} else {
			logger.info("No Data found in DB");
		}
		return entity;

	}

	public <T> boolean saveOrUpdate(T entity) throws DataAccessException {

		Session session = getCurrentSession();

		session.saveOrUpdate(entity);
		return true;
	}

	public boolean checkIfEmailExists(String email) throws DataAccessException {

		Session session = getCurrentSession();
		Criteria cr = session.createCriteria(UserLoginEntity.class);

		cr.add(Restrictions.eq("loginEmail", email));

		return cr.list().size() <= 0;

	}

	public boolean checkIfEmailExistsV2(String loginEmail, String isDriver) throws DataAccessException {

		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity u where u.loginEmail=:loginEmail and u.appUser.isDriver=:isDriver";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("loginEmail", loginEmail);
		queryObj.setParameter("isDriver", isDriver);

		return queryObj.list().size() <= 0;

	}

	public boolean checkIfPhoneNumberExistsV2(String primaryCell, String isDriver)
			throws DataAccessException {

		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity a where a.isDriver=:isDriver and a.personDetail.primaryCell=:primaryCell";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("primaryCell", primaryCell);
		queryObj.setParameter("isDriver", isDriver);

		return queryObj.list().size() <= 0;

	}

	public boolean checkIfPhoneNumberExists(String phoneNumber)
			throws DataAccessException {

		Session session = getCurrentSession();
		Criteria cr = session.createCriteria(PersonDetailEntity.class);

		cr.add(Restrictions.eq("primaryCell", phoneNumber));

		return cr.list().size() <= 0;

	}

	public <T> T findById(Class<T> entityClass, Integer id)
			throws DataAccessException {
		// T t = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		return (T) getCurrentSession().get(entityClass, id);
	}

	public <T> T findObject(String entity, String entityAttribute,
			Integer value, String entityAttribute2, Integer value2)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity." + entity
				+ " en where en." + entityAttribute + "=:value and " + "en."
				+ entityAttribute2 + "=:value2";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("value", value);
		queryObj.setParameter("value2", value2);
		return (T) queryObj.uniqueResult();
	}

	public AppUserBiometricEntity findByIdParam(Integer appUserId)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserBiometricEntity aub where aub.appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (AppUserBiometricEntity) queryObj.uniqueResult();
	}

	public UserLoginEntity findByIdParam2(Integer id)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity aub where aub.appUser.appUserId=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (UserLoginEntity) queryObj.uniqueResult();
	}

	public Object findByIdParamCommon(String className, String param, Integer id)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity."+className+" obj where obj."+param+"=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (Object) queryObj.uniqueResult();
	}

	public Object findByIdParamCommonStr(String className, String param, String id)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity."+className+" obj where obj."+param+"=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (Object) queryObj.uniqueResult();
	}

	public Object[] getAppUserEntityPersonObj(Integer id)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity as a inner join a.person as p inner join a.personDetail as pd where a.appUserId=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (Object[]) queryObj.uniqueResult();
	}

	public AppUserBiometricEntity findBioMetric(Integer id) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserBiometricEntity ab where ab.appUser.appUserId=:id";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("id", id);
		return (AppUserBiometricEntity) queryObj.uniqueResult();
	}

	public List<CharitiesEntity> getCharities() throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.CharitiesEntity";
		Query queryObj = session.createQuery(query);
		queryObj.setFirstResult(0);
		queryObj.setMaxResults(15);
		return (List<CharitiesEntity>) queryObj.list();
	}

	public List<Integer> getSelectedCharities(Integer appUserId)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "select usc.charities.charitiesId from com.tgi.safeher.entity.UserSelectedCharitiesEntity usc "
				+ "where usc.userCharities.appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (List<Integer>) queryObj.list();
	}

	public List<UserSelectedCharitiesEntity> getUserCharities(Integer appUserId)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserSelectedCharitiesEntity usc "
				+ "inner join fetch usc.charities where usc.userCharities.appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (List<UserSelectedCharitiesEntity>) queryObj.list();
	}

	public List<Object[]> getDrivers() {
		Session session = getCurrentSession();
		String query = "select lat_value, long_value from demo";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setFirstResult(1);
		queryObj.setMaxResults(10000);
		return (List<Object[]>) queryObj.list();
	}

	public List<ReasonEntity> getReasons(Integer reasonCategoryId) {
		Session session = getCurrentSession();
		String query = "select rf.reason from com.tgi.safeher.entity.ReasonFilterEntity rf "
				+ "inner join rf.reason r where rf.reasonCategory.reasonCategoryId=:reasonCategoryId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("reasonCategoryId", reasonCategoryId);
		return (List<ReasonEntity>) queryObj.list();
	}

	public RideFinalizeEntity findRideFinalize(Integer rideReqResId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideFinalizeEntity rf "
				+ "inner join fetch rf.rideRequestResponse r where rf.rideRequestResponse.rideReqResId=:rideReqResId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideReqResId", rideReqResId);
		return (RideFinalizeEntity) queryObj.uniqueResult();
	}

	public List<RideFinalizeEntity> findRideFinalizeList(Integer rideReqResId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideFinalizeEntity rf "
				+ "inner join fetch rf.rideRequestResponse r where rf.rideRequestResponse.rideReqResId=:rideReqResId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideReqResId", rideReqResId);
		return (List<RideFinalizeEntity>) queryObj.list();
	}

	public List<RideFinalizeEntity> findRideFinalizeListV2(String requestNo,
			Integer driverId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideFinalizeEntity rf "
				+ "where rf.requestNo=:requestNo and "
				+ "rf.appUser.appUserId=:driverId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("requestNo", requestNo);
		queryObj.setParameter("driverId", driverId);
		return (List<RideFinalizeEntity>) queryObj.list();
	}

	// check if you user exist
	public UserLoginEntity verifyUser(SignInBean bean)
			throws DataAccessException {

		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity u where u.loginEmail=:loginEmail and u.pswd=:password "
				+ "and u.appUser.isDriver=:isDriver";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("loginEmail", bean.getEmail());
		queryObj.setParameter("password", bean.getPassword());
		queryObj.setParameter("isDriver", bean.getIsDriver());
		return (UserLoginEntity) queryObj.uniqueResult();

	}

	public List getTopTenRandome() throws DataAccessException {
		Session session = getCurrentSession();
		String query = "SELECT TOP 10 charities_id FROM charities ORDER BY NEWID()";
		Query queryObj = session.createSQLQuery(query);
		return (List) queryObj.list();
	}

	@SuppressWarnings("unchecked")
	public <T> T findBy(final Class<T> entity, final String entityAttribute,
			final String value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
	}

	public <T> boolean update(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().update(entity);
		getCurrentSession().flush();
		return true;
	}

	public <T> boolean merge(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().merge(entity);

		return true;
	}

	public int hibernateQuery(String query) throws DataAccessException {
		Query qery = getCurrentSession().createQuery(query);
		int result = qery.executeUpdate();
		return result;
	}

	public <T> List<T> getAll(Class<T> type) {
		final Criteria crit = getCurrentSession().createCriteria(type);
		return crit.list();
	}

	public <T> T get(Class<T> type, Integer id) {
		return (T) getCurrentSession().get(type, id);
	}

	public void delete(Object object) throws DataAccessException {
		getCurrentSession().delete(object);
	}

	public void deleteDriverActiveLocation(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "delete from com.tgi.safeher.entity.ActiveDriverLocationEntity where appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.executeUpdate();
	}

	public void updateLocation(Integer appUserId, String classEntity) {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity." + classEntity
				+ " u set u.isOnline = 0, u.isRequested = 0, u.isBooked = 0 "
				+ "where u.appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.executeUpdate();
	}

	public void updateRideReqResStatusUsingRequestNoAndDriverId(
			String requestNo, String driverid, StatusEntity status) {

		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.RideRequestResponseEntity r "
				+ "set r.statusByStatusResponse.statusId = "
				+ status.getStatusId()
				+ " where r.rideSearchResultDetail.rideSearchResultDetailId IN( "
				+ "select rd.rideSearchResultDetailId from r.rideSearchResultDetail rd where rd.requestNo='"
				+ requestNo + "' and rd.appUser.appUserId=" + driverid + ")";
		try {
			Query queryObj = session.createQuery(query);
			queryObj.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public <T> T findByOject(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.uniqueResult();
//		return (T) cr.list().get(0);
	}
	
	public <T> T findByOjectForInvoice(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		return (T) cr.list().get(0);
	}

	public <T> T findByOjectByRideDistribution(Class<T> entity,
			String entityAttribute, Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.addOrder(Order.desc("ridePaymnetDistributionId"));
		return (T) cr.list().get(0);
	}

	public <T> T findByOjectForVehicle(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.add(Restrictions.eq("isActive", "1"));
		return (T) cr.uniqueResult();
	}

	public <T> T findByOjectForPost(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		if (cr.list() != null && cr.list().size() > 0) {
			return (T) cr.list().get(0);
		}
		return null;

	}

	public <T> boolean save(T entity) throws DataAccessException {
		BaseEntity baseEntity = (BaseEntity) entity;
		SessionBean sessionBean = baseEntity.getSessionBean();
		getCurrentSession().save(entity);
		return true;
	}

	public PersonAddressHistoryEntity getCurrentPersonAddressHistory(
			PersonAddressEntity entity) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(
				PersonAddressHistoryEntity.class);
		cr.add(Restrictions.eq("personAddress", entity));
		cr.add(Restrictions.eq("isCurrent", "1"));
		return (PersonAddressHistoryEntity) cr.uniqueResult();
	}

	public List<StateProvinceEntity> getStateByCountry(CountryEntity country) {
		String query = "select st from StateProvinceEntity st  where st.country.countryId=:id";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setInteger("id", country.getCountryId());
		return (List<StateProvinceEntity>) queryObj.list();
	}

	public List<CityEntity> getCityByState(StateProvinceEntity state) {
		Criteria cr = getCurrentSession().createCriteria(CityEntity.class);
		cr.add(Restrictions.eq("stateProvinceEntity", state));
		return cr.list();

	}

	public List<ZipCodeEntity> getZipByCity(CityEntity city) {
		Criteria cr = getCurrentSession().createCriteria(ZipCodeEntity.class);
		cr.add(Restrictions.eq("cityEntity", city));
		return cr.list();

	}

	public List<UserElectronicResourceEntity> getAllUserElec(Integer appUserId,
			Integer eletronicCategoryId) throws DataAccessException {

		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserElectronicResourceEntity uer where uer.appUser.appUserId=:appUserId "
				+ "and eletronicCategory.eletronicCategoryId=:eletronicCategoryId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("eletronicCategoryId", eletronicCategoryId);
		return (List<UserElectronicResourceEntity>) queryObj.list();
	}

	public List<UserFavoritiesEntity> getUserFavorities(Integer appUserId,
			Integer favorityTypeId) throws DataAccessException {

		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserFavoritiesEntity uf where uf.appUser.appUserId=:appUserId "
				+ "and favorityType.favorityTypeId=:favorityTypeId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("favorityTypeId", favorityTypeId);
		return (List<UserFavoritiesEntity>) queryObj.list();
	}

	public List<ActiveDriverLocationEntity> fetchAllDriver()
			throws DataAccessException {

		Session session = getCurrentSession();
		String query = "select ad.latValue, ad.longValue, ad.appUser.appUserId from com.tgi.safeher.entity.ActiveDriverLocationEntity ad "
				+ "where ad.isBooked <> '1'";
		Query queryObj = session.createQuery(query);
		return (List<ActiveDriverLocationEntity>) queryObj.list();
	}

	public List<Object> fetchAllVehResEnd(String className, String innerJoin,
			Integer appUserId) throws DataAccessException {

		Session session = getCurrentSession();
		String query = "select en." + innerJoin
				+ " from com.tgi.safeher.entity." + className
				+ " as en inner join en." + innerJoin
				+ " as inner where en.appUser.appUserId=:appUserId "
				+ "order by en." + innerJoin + ".name asc";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (List<Object>) queryObj.list();
	}

	public <T> List<T> getAllVehResEnd(Class<T> type) {
		final Criteria crit = getCurrentSession().createCriteria(type);
		crit.addOrder(Order.asc("name"));
		return crit.list();
	}

	public void removeItem(String className, Integer removeItem,
			String foriengObj, Integer appUserId) {
		Session session = getCurrentSession();
		String query = "delete from com.tgi.safeher.entity." + className
				+ " entity where entity.appUser.appUserId=:appUserId and "
				+ foriengObj + " =:removeItem";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("removeItem", removeItem);
		queryObj.executeUpdate();

	}

	public List<Object[]> findRideRequest(Integer driverId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideRequestResponseEntity as r inner join r.rideSearchResultDetail.appUser as rd "
				+ " where rd.appUserId=:driverId order by r.rideSearchResultDetail.rideSearchResultDetailId desc";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("driverId", driverId);
		return (List<Object[]>) queryObj.list();

	}

	public List<Object[]> findRideRequestResponse(Integer driverId,
			String requestNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideRequestResponseEntity as r inner join r.rideSearchResultDetail as rd "
				+ "inner join rd.appUser as au where au.appUserId=:driverId and r.rideSearchResultDetail.requestNo=:requestNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("driverId", driverId);
		queryObj.setParameter("requestNo", requestNo);
		return (List<Object[]>) queryObj.list();

	}

	public RideRequestResponseEntity findRideRequestResponseV2(
			Integer driverId, String requestNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideRequestResponseEntity r inner join fetch r.rideSearchResultDetail rd "
				+ "where rd.appUser.appUserId=:driverId and r.rideSearchResultDetail.requestNo=:requestNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("driverId", driverId);
		queryObj.setParameter("requestNo", requestNo);
		return (RideRequestResponseEntity) queryObj.uniqueResult();

	}

	public AppUserEntity findPesonDetail(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity as an inner join fetch an.person as p "
				+ "where an.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (AppUserEntity) queryObj.uniqueResult();

	}

	public PersonDetailEntity getPersonDetail(Integer pesonId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PersonDetailEntity as pd "
				+ "where pd.person.personId=:pesonId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("pesonId", pesonId);
		return (PersonDetailEntity) queryObj.uniqueResult();

	}

	public AppUserVehicleEntity findDriverVehicalDetail(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserVehicleEntity as ap inner join fetch ap.vehicleInfo as v "+
				"inner join fetch v.vehicleModel as vm where ap.appUser.appUserId=:appUserId and ap.isActive=1";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (AppUserVehicleEntity) queryObj.uniqueResult();

	}

	public Double getRatingAvg(Integer userRatingId) {
		Session session = getCurrentSession();
		String query = "select avg(value) from com.tgi.safeher.entity.UserRatingDetailEntity as rd "
				+ "where rd.userRating.userRatingId=:userRatingId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("userRatingId", userRatingId);
		return (Double) queryObj.uniqueResult();

	}

	
	
	public UserRatingDetailEntity getRatingDetail(Integer userRatingId,
			Integer appUserId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserRatingDetailEntity as rd "
				+ "where rd.appUser.appUserId=:appUserId and rd.userRating.userRatingId=:userRatingId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("userRatingId", userRatingId);
		return (UserRatingDetailEntity) queryObj.uniqueResult();

	}

	public AppUserEntity findDrivers(Integer rideReqResId) {
		Session session = getCurrentSession();
		String query = "select rs.appUser from com.tgi.safeher.entity.RideRequestResponseEntity as r inner join "
				+ "r.rideSearchResultDetail as rs inner join rs.appUser as au where r.rideReqResId=:rideReqResId";
		// inner join fetch rs.appUser as au
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideReqResId", rideReqResId);
		return (AppUserEntity) queryObj.uniqueResult();

	}

	public <T> T findByParameter(Class<T> entity, String ParameterName,
			String ParameterValue) throws DataAccessException {
		System.out
				.println("*****************************************************Entering in DAO*****************************************************");
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(ParameterName, ParameterValue));
		return (T) cr.uniqueResult();
	}

	public AppUserPaymentInfoEntity checkAppUserPaymentInfo(
			AppUserEntity appUser) {
		Criteria cr = getCurrentSession().createCriteria(
				AppUserPaymentInfoEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		return (AppUserPaymentInfoEntity) cr.uniqueResult();

	}

	public CreditCardInfoEntity findCreditCard(Integer appUserPaymentInfoId)
			throws DataAccessException {
		Session session = getCurrentSession();
		// String query =
		// "from com.tgi.safeher.entity.CreditCardInfoEntity as cc "+
		// "where cc.appUserPaymentInfo.appUserPaymentInfoId=:appUserPaymentInfoId order by cc.creditCardInfoId desc";
		String query = "from com.tgi.safeher.entity.CreditCardInfoEntity as cc "
				+ "where cc.appUserPaymentInfo.appUserPaymentInfoId=:appUserPaymentInfoId and cc.isDefault='1'";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserPaymentInfoId", appUserPaymentInfoId);
		List<CreditCardInfoEntity> list = queryObj.list();
		if (list != null && list.size() > 0) {
			return (CreditCardInfoEntity) queryObj.list().get(0);
		}
		return null;

	}

	public List<RideBillEntity> calculateLastTripAmount(AppUserEntity appUser,
			Timestamp fromDate, Timestamp toDate) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.rideInfoId.isCompleted='1' and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("start_date", fromDate);
		queryObj.setParameter("end_date", toDate);
		// Criteria cr = getCurrentSession().createCriteria(
		// RideBillEntity.class);
		// //cr.add(Restrictions.between("rideInfoId.startTime", fromDate,
		// toDate));
		// cr.add(Restrictions.eq("appUserByAppUserDriver", appUser));
		// cr.addOrder(Order.desc("rideBillId"));
		// cr.addOrder(Order.desc("rideInfoId.rideId"));
		return queryObj.list();
	}

	public <T> List<T> findListByOject(Class<T> entity, String entityAttribute,
			Object value, Integer firstResult, Integer maxResult)
			throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.setFirstResult(firstResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}
	
	public <T> List<T> findListByOjectForRating(Class<T> entity, String entityAttribute,
			Object value, Integer firstResult, Integer maxResult)
			throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.addOrder(Order.desc("userCommentId"));
		cr.setFirstResult(firstResult);
		cr.setMaxResults(maxResult);
		return (List<T>) cr.list();
	}

	public List<RideBillEntity> GetRecentRidesForDriver(AppUserEntity appUser,
			Timestamp fromDate, Timestamp toDate, Integer firstIndex,
			Integer MaxResult) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.rideInfoId.isCompleted='1'  and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("start_date", fromDate);
		queryObj.setParameter("end_date", toDate);
		queryObj.setFirstResult(firstIndex);
		queryObj.setMaxResults(MaxResult);
		return queryObj.list();
	}

	public List<RideBillEntity> GetRecentRidesForPassenger(
			AppUserEntity appUser, Timestamp fromDate, Timestamp toDate,
			Integer firstIndex, Integer MaxResult) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserPassenger=:appUser and cc.rideInfoId.isCompleted='1' and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("start_date", fromDate);
		queryObj.setParameter("end_date", toDate);
		queryObj.setFirstResult(firstIndex);
		queryObj.setMaxResults(MaxResult);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByIsCancelforPassenger(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, String isCancel) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserPassenger=:appUser and cc.rideInfoId.isCancel=:cancel and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("cancel", isCancel);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByIsPaymentforPassenger(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, Integer valueOf3) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserPassenger=:appUser and cc.paymentMode.paymentModeId=:paymentId and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("paymentId", valueOf3);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByStatusforPassenger(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, Integer valueOf3) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserPassenger=:appUser and cc.status.statusId=:statusId and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("statusId", valueOf3);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByIsCancelforDriver(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, String isCancel) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.rideInfoId.isCancel=:cancel and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("cancel", isCancel);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByIsPaymentforDriver(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, Integer valueOf3) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.paymentMode.paymentModeId=:paymentId and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("paymentId", valueOf3);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public List<RideBillEntity> getPaymentFilterByStatusforDriver(
			AppUserEntity appUser, Timestamp timestamp, Timestamp timestamp2,
			Integer valueOf, Integer valueOf2, Integer valueOf3) {
		String query = "from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.status.statusId=:statusId and cc.rideInfoId.endTime between :start_date and :end_date  order by rideBillId desc";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("statusId", valueOf3);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		queryObj.setFirstResult(valueOf);
		queryObj.setMaxResults(valueOf2);
		return queryObj.list();
	}

	public RideEntity getRide(String rideNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideEntity r "
				+ "where r.rideNo=:rideNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideNo", rideNo);
		List<RideEntity> list = queryObj.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean matchPassword(Integer appUserId, String pswd)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity user "
				+ "where user.appUser.appUserId=:appUserId and user.pswd=:pswd";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("pswd", pswd);
		UserLoginEntity entity = (UserLoginEntity) queryObj.uniqueResult();
		if (entity == null) {
			return false;
		}
		return true;
	}

	public RideQuickInfoEntity getRideQuickInfo(String driverAppId,
			Timestamp to, Timestamp from) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideQuickInfoEntity rq "
				+ "where rq.driverAppId=:driverAppId and rq.sendingTime between:from and :to"
				+ " order by rq.sendingTime desc";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("from", from);
		queryObj.setParameter("to", to);
		queryObj.setParameter("driverAppId", driverAppId);
		List<RideQuickInfoEntity> list = queryObj.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public UserLoginEntity verifySocialUser(SignInBean bean) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity u where u.socialId=:socialId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("socialId", bean.getSocialId().trim());
		// Criteria cr =
		// getCurrentSession().createCriteria(UserLoginEntity.class);
		// cr.add(Restrictions.eq("socialId", bean.getSocialId()));
		// return (UserLoginEntity) cr.uniqueResult();

		// return (UserLoginEntity) queryObj.uniqueResult();
		List<UserLoginEntity> list = queryObj.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public Long countShortEarningRides(AppUserEntity appUser,
			Timestamp fromDate, Timestamp toDate) {
		String query = "select count(*) from com.tgi.safeher.entity.RideBillEntity as cc "
				+ "where cc.appUserByAppUserDriver=:appUser and cc.rideInfoId.isCompleted='1' and cc.rideInfoId.endTime between :start_date and :end_date";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("start_date", fromDate);
		queryObj.setParameter("end_date", toDate);
		// Criteria cr = getCurrentSession().createCriteria(
		// RideBillEntity.class);
		// //cr.add(Restrictions.between("rideInfoId.startTime", fromDate,
		// toDate));
		// cr.add(Restrictions.eq("appUserByAppUserDriver", appUser));
		// cr.addOrder(Order.desc("rideBillId"));
		// cr.addOrder(Order.desc("rideInfoId.rideId"));
		return (Long) queryObj.uniqueResult();
	}

	public PostRideEntity getRideInfo(String invoiceNo)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PostRideEntity pr "
				+ " where pr.rideEntityId.rideId IN( "
				+ "select rb.rideInfoId.rideId from RideBillEntity rb where rb.invoiceNo=:invoiceNo)";

		Query queryObj = session.createQuery(query);
		queryObj.setParameter("invoiceNo", invoiceNo);
		List<PostRideEntity> list = queryObj.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public RideBillEntity getDriverPassengerInfo(String invoiceNo)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideBillEntity rb where rb.invoiceNo=:invoiceNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("invoiceNo", invoiceNo);
		return (RideBillEntity) queryObj.uniqueResult();
	}

	public void deleteUnneccesaryActiveDriver(Timestamp to, Timestamp from)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "delete from com.tgi.safeher.entity.ActiveDriverLocationEntity ad "
				+ "where ad.lastActiveTime not between:from and :to";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("from", from);
		queryObj.setParameter("to", to);
		queryObj.executeUpdate();
	}

	// To be used for setting user online status
	public void setUserStatus(Integer userLoginId, String onlineStatus)
			throws DataAccessException {

		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.UserLoginEntity u set u.isOnline=:onlineStatus where u.userLoginId=:userLoginId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("onlineStatus", onlineStatus);
		queryObj.setParameter("userLoginId", userLoginId);
		queryObj.executeUpdate();
	}

	public void deleteCharities(Integer charitiesId, Integer appUserId)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "delete from com.tgi.safeher.entity.UserSelectedCharitiesEntity usc "
				+ " where usc.charities.charitiesId=:charitiesId and usc.userCharities.userCharities IN( "
				+ "select uc.userCharities from UserCharitiesEntity uc where uc.appUser.appUserId=:appUserId)";

		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("charitiesId", charitiesId);
		queryObj.executeUpdate();
	}

	public UserSelectedCharitiesEntity findUserSelectedCharity(
			Integer charitiesId, Integer appUserId) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserSelectedCharitiesEntity usc "
				+ " where usc.charities.charitiesId=:charitiesId and usc.userCharities.userCharities IN( "
				+ "select uc.userCharities from UserCharitiesEntity uc where uc.appUser.appUserId=:appUserId)";

		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("charitiesId", charitiesId);
		return (UserSelectedCharitiesEntity) queryObj.uniqueResult();
	}

	public UserPromotionEntity findPromotionByCodes(
			PromotionCodesEntity promotionCode, AppUserEntity appUser) {
		final Criteria cr = getCurrentSession().createCriteria(
				UserPromotionEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		cr.add(Restrictions.eq("promotionCodesEntity", promotionCode));

		return (UserPromotionEntity) cr.uniqueResult();
	}

	public AppUserEntity findAppUserByEmailOrNumber(String primaryEmail,
			String primaryCell) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity app "
				+ " where app.personDetail.personDetailId IN( select pd.personDetailId from PersonDetailEntity "
				+ "pd where pd.primaryEmail=:primaryEmail or pd.primaryCell=:primaryCell)";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("primaryCell", primaryCell);
		queryObj.setParameter("primaryEmail", primaryEmail);
		return (AppUserEntity) queryObj.uniqueResult();
	}

	public boolean checkIfPhoneNumberExistsForUpdate(Integer appUserId,
			String phoneNumber) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity au where au.appUserId<>:appUserId and au.personDetail.primaryCell=:phoneNumber";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("phoneNumber", phoneNumber);
		return queryObj.list().size() <= 0;

	}

	public boolean checkIfPhoneNumberExistsForUpdateV2(Integer appUserId,
			String phoneNumber, String isDriver) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserEntity au where au.appUserId<>:appUserId and au.isDriver=:isDriver and au.personDetail.primaryCell=:phoneNumber";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("phoneNumber", phoneNumber);
		queryObj.setParameter("isDriver", isDriver);
		return queryObj.list().size() <= 0;

	} 

	public void inActiveAllVehicle(Integer appUserId, Integer vehicleInfoId)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "update from com.tgi.safeher.entity.AppUserVehicleEntity av "
				+ "set av.isActive = '0' where av.appUser.appUserId=:appUserId and av.vehicleInfo.vehicleInfoId<>:vehicleInfoId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("vehicleInfoId", vehicleInfoId);
		queryObj.executeUpdate();
	}
	
	public void inActivePrevReferral(Integer appUserTypeId, Integer promotionTypeId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "update from com.tgi.safeher.entity.PromotionInfoEntity pInfo "
						+"set pInfo.isActive = '0' where pInfo.appUserType.appUserTypeId=:appUserTypeId and pInfo.promotionType.promotionTypeId=:promotionTypeId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserTypeId", appUserTypeId);
		queryObj.setParameter("promotionTypeId", promotionTypeId);
		queryObj.executeUpdate();
	}
	
	public PromotionInfoEntity findReferral( Integer promotionTypeId, 
			Integer appUserTypeId) throws DataAccessException{
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PromotionInfoEntity pInfo where "+
						"pInfo.appUserType.appUserTypeId=:appUserTypeId and pInfo.promotionType.promotionTypeId=:promotionTypeId "+
						"and pInfo.isActive = '1'";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserTypeId", appUserTypeId);
		queryObj.setParameter("promotionTypeId", promotionTypeId);
		List<PromotionInfoEntity> list = queryObj.list();
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public List<Object[]> findPromotions() throws DataAccessException {
		Session session = getCurrentSession();
		// String query =
		// "select r1.promotionInfoId,r1.promotionDescription,r1.isPercentage,r1.isCount,"
		// +
		// "r1.appUserType,r1.isActive,r1.durationInDays,r1.startDate,r1.isSingle,r1.expiryDate,"
		// +
		// " r1.promotionType,r2.codeValue , r2.promotionCodesId ,r2.isUsed from "
		// +
		// "PromotionInfoEntity r1  inner join PromotionCodesEntity r2 where " +
		// " r1.isActive='1'";
//		String query = "select r1.promotionInfoId,r1.promotionDescription,r1.isPercentage,r1.isCount,"
//				+ "r1.appUserType,r1.isActive,r1.durationInDays,r1.startDate,r1.isSingle,r1.expiryDate,"
//				+ " r1.promotionType,r2.codeValue , r2.promotionCodesId ,r2.isUsed,r1.amountValue  from "
//				+ "PromotionCodesEntity r2  right join r2.promotionInfoEntity r1 where "
//				+ " r1.isActive='1' and r1.appUserType.appUserTypeId=:appUserType and  r1.promotionType.promotionTypeId=:promotionType";
		String query = "select r1.promotionInfoId,r1.promotionDescription,r1.isPercentage,r1.isCount,"
				+ "r1.appUserType,r1.isActive,r1.durationInDays,r1.startDate,r1.isSingle,r1.expiryDate,"
				+ " r1.amountValue  from  r2.promotionInfoEntity r1 where "
				+ " r1.isActive='1' and r1.appUserType.appUserTypeId=:appUserType and  r1.promotionType.promotionTypeId=:promotionType";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("promotionType", 1);
		queryObj.setParameter("appUserType", 2);
		// queryObj.executeUpdate();
		return (List<Object[]>) queryObj.list();
	}

	public List<Object[]> findPromotionsByDriver() {
		Session session = getCurrentSession();
//		String query = "select r1.promotionInfoId,r1.promotionDescription,r1.isPercentage,r1.isCount,"
//				+ "r1.appUserType,r1.isActive,r1.durationInDays,r1.startDate,r1.isSingle,r1.expiryDate,"
//				+ " r1.promotionType,r2.codeValue , r2.promotionCodesId ,r2.isUsed,r1.amountValue  from "
//				+ "PromotionCodesEntity r2  right join r2.promotionInfoEntity r1 where "
//				+ " r1.isActive='1' and r1.appUserType.appUserTypeId=:appUserType and  r1.promotionType.promotionTypeId=:promotionType";
		String query = "select r1.promotionInfoId,r1.promotionDescription,r1.isPercentage,r1.isCount,"
				+ "r1.appUserType,r1.isActive,r1.durationInDays,r1.startDate,r1.isSingle,r1.expiryDate,"
				+ " r1.amountValue,r1.promotionType,r1.countValue  from  PromotionInfoEntity r1 where "
				+ " r1.isActive='1' and r1.appUserType.appUserTypeId=:appUserType and  r1.promotionType.promotionTypeId=:promotionType";
		
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("promotionType", 1);
		queryObj.setParameter("appUserType", 1);
		// queryObj.executeUpdate();
		return (List<Object[]>) queryObj.list();
	}

	public Double findUserCommentAvgRating(AppUserEntity appUser,
			Timestamp timestamp, Timestamp timestamp2) {
		String query = "select avg(cc.rateValue) from com.tgi.safeher.entity.UserCommentEntity as cc "
				+ "where cc.appUserByUserFor=:appUser  and cc.commentDate between :start_date and :end_date ";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("appUser", appUser);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		return (Double) queryObj.uniqueResult();
	}

	public Double findUserIntervalRating(Integer userRatingId,
			Timestamp timestamp, Timestamp timestamp2) {
 		String query = "select avg(cc.value) from com.tgi.safeher.entity.UserRatingDetailEntity as cc "
				+ "where cc.userRating.userRatingId=:userRatingId  and cc.ratingDate between :start_date and :end_date ";
		Query queryObj = getCurrentSession().createQuery(query);
		queryObj.setParameter("userRatingId", userRatingId);
		queryObj.setParameter("start_date", timestamp);
		queryObj.setParameter("end_date", timestamp2);
		return (Double) queryObj.uniqueResult();
	}

	public UserPromotionEntity findByUserPromotionisActive(AppUserEntity appUser) {
		final Criteria cr = getCurrentSession().createCriteria(
				UserPromotionEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		cr.add(Restrictions.eq("isActive", "1"));
		return (UserPromotionEntity) cr.uniqueResult();
	}

	public UserPromotionEntity findByReferringUserPromotionisActive(
			AppUserEntity appUser, PromotionType promotionType) {
		final Criteria cr = getCurrentSession().createCriteria(
				UserPromotionEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		cr.add(Restrictions.eq("promotionType", promotionType));
		cr.add(Restrictions.eq("isActive", "1"));
		return (UserPromotionEntity) cr.uniqueResult();
	}

	public UserPromotionEntity findUserPromotionOnPayment(
			Integer appUserId, Integer promotionTypeId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserPromotionEntity up where "+
						"up.appUser.appUserId=:appUserId and up.promotionType.promotionTypeId=:promotionTypeId "+
						"and up.isActive = '1' and up.totalValue > totalUsedValue";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("promotionTypeId", promotionTypeId);
		List<UserPromotionEntity> list = queryObj.list();
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public List<UserPromotionEntity> findConsumePromotion(AppUserEntity appUser) {
		final Criteria cr = getCurrentSession().createCriteria(
				UserPromotionEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		return cr.list();
	}
	
	public UserPromotionEntity findByUserPromotionByMultipleConsume(AppUserEntity appUser, PromotionCodesEntity promotionCode) {
		final Criteria cr = getCurrentSession().createCriteria(
				UserPromotionEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		cr.add(Restrictions.eq("promotionCodesEntity", promotionCode));
		return (UserPromotionEntity) cr.uniqueResult();
	}

	public RideProcessTrackingDetailEntity getrideTracking(
			Integer appUserId, String isDriver, String requestNo) throws DataAccessException{
		Session session = getCurrentSession();
//		String query = "from com.tgi.safeher.entity.RideProcessTrackingDetailEntity rptd where "+
//						"rptd.rideProcessTracking."+isDriver+".appUserId=:appUserId "+//and rptd.statusFlag<>'4'
//						"or rptd.rideProcessTracking.requestNo =:requestNo order by rptd.rideProcessTrackingDetialId desc";
		String query = "from com.tgi.safeher.entity.RideProcessTrackingDetailEntity rptd where "+
				"rptd.rideProcessTracking."+isDriver+".appUserId=:appUserId ";//and rptd.statusFlag<>'4'
		if(StringUtil.isNotEmpty(requestNo)){
			query += "and rptd.rideProcessTracking.requestNo =:requestNo";
		}
		query += " order by rptd.rideProcessTrackingDetialId desc";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		if(StringUtil.isNotEmpty(requestNo)){
			queryObj.setParameter("requestNo", requestNo);
		}
		
		List<RideProcessTrackingDetailEntity> list = queryObj.list();
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public RideQuickInfoEntity getRideQuickInfoFromRequestNo(
			Integer rideProcessTrackingId) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideQuickInfoEntity rq "
				+ "where rq.rideProcessTracking.rideProcessTrackingId=:rideProcessTrackingId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideProcessTrackingId", rideProcessTrackingId);
		List<RideQuickInfoEntity> list = queryObj.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public String getPreRide(String requestNo) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PreRideEntity pr "
				+ "where pr.requestNo like=:requestNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("requestNo", requestNo+"%");
		
		return ((PreRideEntity)queryObj.uniqueResult()).getPreRideId()+"";
	}
	public UserPromotionEntity findUserPromotionOnPaymentForDriver(
			Integer appUserId, Integer promotionTypeId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserPromotionEntity up where "+
						"up.appUser.appUserId=:appUserId and up.promotionType.promotionTypeId=:promotionTypeId "+
						"and up.isActive = '1' and countValue > useCountValue";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("promotionTypeId", promotionTypeId);
		List<UserPromotionEntity> list = queryObj.list();
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public void rejectGiftRide(Integer rideCriteriaId, Integer statusId,AppUserEntity appUser)
			throws DataAccessException {
		Session session = getCurrentSession();
		String query = "update com.tgi.safeher.entity.RideGiftEntity rg "
				+ "set status.statusId=:statusId ,rg.appUserByReciverUserId=:reciverAppuser  where rg.rideCriteria.rideCriteriaId=:rideCriteriaId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideCriteriaId", rideCriteriaId);
		queryObj.setParameter("reciverAppuser", appUser);
		queryObj.setParameter("statusId", statusId);
		queryObj.executeUpdate();
	}

	public void makeInActiveUserPromotionByExpiryDate() {
		Session session = getCurrentSession();
		String query = "update user_promotion set is_active='0' where user_promotion_id in (SELECT  user_promotion_id  FROM   user_promotion  where use_expiry_date < GETDATE())";
		Query queryObj = session.createSQLQuery(query);
		queryObj.executeUpdate();
	}

	public void makeInActivePromotionByExpiryDate() {
		Session session = getCurrentSession();
		String query = "update promotion_info set is_active='0' where promotion_info_id in (SELECT promotion_info_id FROM   promotion_info  where expiry_date < GETDATE())";
		Query queryObj = session.createSQLQuery(query);
		queryObj.executeUpdate();
		
	}

	public void inactiveGiftRides() {
		Session session = getCurrentSession();
		String query = "update ride_gift set is_active='0' where ride_gift_id in (SELECT ride_gift_id FROM   ride_gift  where expiry_date < GETDATE())";
		Query queryObj = session.createSQLQuery(query);
		queryObj.executeUpdate();
		
	}
	
	public <T> T findDefaultCreditCard(Class<T> entity, String entityAttribute,
			Object value) throws DataAccessException {
		final Criteria cr = getCurrentSession().createCriteria(entity);
		cr.add(Restrictions.eq(entityAttribute, value));
		cr.add(Restrictions.eq("isDefault", "1"));
		return (T) cr.uniqueResult();
	}
	
	
	public void deleteUnneccesaryActiveDriverMongo(Date to, Date from)
			throws DataAccessException {
		activeDriverLocationRepository.deleteByLastActiveTimeBetween(to, from);
		
	}
	
	public void deleteDriverActiveLocationMongo(Integer appUserId) {
		activeDriverLocationRepository.deleteByAppUserId(appUserId);
	}
	
	public void InActiveActiveUserDriverLocation(Integer appUserID) {
		Session session = getCurrentSession();
		String query = "update active_driver_locatoin set is_requested='0' , is_booked='0' ,is_online='1' where app_user_driver="+appUserID+"";
		Query queryObj = session.createSQLQuery(query);
		queryObj.executeUpdate();
		
	}
	
	public void InActiveDriverLocationByHql(Integer appUserId,
			String requested, String booked) {
		String Qur2 = "update ActiveDriverLocationEntity set isRequested='"
				+ requested.trim() + "' , isBooked='" + booked.trim()
				+ "' where appUser.appUserId=" + appUserId + "";
		hibernateQuery(Qur2);
	}
	
	public void inActiveDriverLocationByHqlTx(Integer appUserId){
		logger.info("$Updated AppUserId "+appUserId);	
	Session session = getCurrentSession();
	Transaction tx = session.beginTransaction();

	String hqlUpdate = "update ActiveDriverLocationEntity act set act.isBooked = '0', act.isRequested ='0' where act.appUser.appUserId = :appUserId";
	// or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
	System.out.println(session.createQuery( hqlUpdate ).setInteger("appUserId", appUserId).toString());
	int updatedEntities = session.createQuery( hqlUpdate ).setInteger("appUserId", appUserId).executeUpdate();
	tx.commit();
	session.close();
	
	logger.info("$Updated hibernate Query "+updatedEntities );
	}

	public PersonAddressEntity findAddressByAddressTypeAndPerson(Integer personId, Integer addressTypeId) throws DataAccessException {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PersonAddressEntity pa where "+
				"pa.person.personId=:personId and pa.addressType.addressTypeId=:addressTypeId ";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("addressTypeId", addressTypeId);
		queryObj.setParameter("personId", personId);
		List<PersonAddressEntity> list = queryObj.list();
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	/*
	 * Email Sender
	 */
	public EmailTempleteEntity findEmailData(Integer emailCode) throws DataAccessException {
		Session session = getCurrentSession();
		System.out.println(session.getFlushMode().toString());
		return (EmailTempleteEntity)session.createCriteria(EmailTempleteEntity.class).add(Restrictions.eq("emailTempleteId", emailCode)).uniqueResult();
		
	}
	
	public void logEmailStatus(EmailLogEntity emailLogEntity) throws DataAccessException {
		Session session = getCurrentSession();
		System.out.println(session.getFlushMode().toString());
		session.save(emailLogEntity);
	}
	
	public List<AppUserRegTrackEntity> findTrackEntity(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.AppUserRegTrackEntity up where "+
						"up.appUser.appUserId=:appUserId ORDER BY up.userRegFlow.userRegFlowId DESC";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		
		List<AppUserRegTrackEntity> list = queryObj.list();
		
		return list;
	}
	
	 public <T> T findByOjectForVehicleV2(Class<T> entity, String entityAttribute,
			   Integer value) throws DataAccessException {
			  final Criteria cr = getCurrentSession().createCriteria(entity);
			  cr.add(Restrictions.eq(entityAttribute, value));
			  cr.add(Restrictions.eq("isActive", "1"));
			  return (T) cr.uniqueResult();
	 }

	public boolean checkRideRequestIsNotCancel(String rideRquestId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.RideRequestResponseEntity up where "
				+ "up.rideReqResId=:rideREqId and up.statusByStatusResponse.statusId=4";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("rideREqId", rideRquestId);

		if(queryObj.uniqueResult()!=null){
			return true;
		}else{
			return false;
		}

		
	}
	 
	 public boolean verifyUser(String code, String appUserId, String type){

		Criteria cr = getCurrentSession().createCriteria(AppUserPhoneEmailStatusLogEntity.class);
		cr.add(Restrictions.eq("code", code));
		cr.createAlias("appUser", "appUser");
		cr.add(Restrictions.eq("appUser.appUserId", Integer.parseInt(appUserId)));
		cr.add(Restrictions.eq("primaryEmail", type));

		AppUserPhoneEmailStatusLogEntity appUserPhoneEmailStatusLogEntity = (AppUserPhoneEmailStatusLogEntity) cr
				.uniqueResult();
		if (appUserPhoneEmailStatusLogEntity != null) {
			if(type == "1"){
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setVerificationType("Email");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setVerified("1");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setNotVerified("0");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setPending("0");
				saveOrUpdate(appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus());
			}else{
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setVerificationType("Phone");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setVerified("1");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setNotVerified("0");
				appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus().setPending("0");
				saveOrUpdate(appUserPhoneEmailStatusLogEntity.getAppUserPhoneEmailStatus());
			}
			return true;
		} else
			return false;
			 
	 }
	 
		
	public List<UserLoginEntity> findIncompleteUsers() {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserLoginEntity u where "+
						"u.isComplete !='1' and u.isComplete is not null";
		Query queryObj = session.createQuery(query);
		List<UserLoginEntity> list = queryObj.list();
		return list;
	}

	public DriverDrivingSummaryEntity findDriverDrivingSummary(AppUserEntity appUser) {
		Criteria cr = getCurrentSession().createCriteria(DriverDrivingSummaryEntity.class);
		cr.add(Restrictions.eq("appUser", appUser));
		DriverDrivingSummaryEntity driverDriving = (DriverDrivingSummaryEntity) cr.uniqueResult();

		return driverDriving;

	}
	
	public PersonDetailEntity findByEmail(String email){
		
	
		  final Criteria cr = getCurrentSession().createCriteria(PersonDetailEntity.class);
		  cr.add(Restrictions.eq("primaryEmail", email));
		  return (PersonDetailEntity) cr.uniqueResult();
		
	}
	
	
	/*
	 * Sms Sender
	 */
	public SmsTempelteEntity findSmsData(Integer smsCode) throws DataAccessException {
		Session session = getCurrentSession();
		System.out.println(session.getFlushMode().toString());
		return (SmsTempelteEntity)session.createCriteria(SmsTempelteEntity.class).add(Restrictions.eq("smsTempelteId", smsCode)).uniqueResult();
		
	}
	
	public void logSmsStatus(SmsLogEntity smsLogEntity) throws DataAccessException {
		Session session = getCurrentSession();
		System.out.println(session.getFlushMode().toString());
		session.save(smsLogEntity);
	}
	
	
	
	
	public UserElectronicResourceEntity findElectronicRes(Integer appUserId, Integer eletronicCategoryId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserElectronicResourceEntity ue where "+
						"ue.appUser.appUserId=:appUserId and ue.eletronicCategory.eletronicCategoryId=:eletronicCategoryId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("eletronicCategoryId", eletronicCategoryId);
		List<UserElectronicResourceEntity> list = queryObj.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	public UserCommentEntity getPassengerRatingToDriver(Integer appUserId, String rideNo) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.UserCommentEntity uc where "+
						"uc.appUserByUserBy.appUserId=:appUserId and uc.rideNo=:rideNo";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		queryObj.setParameter("rideNo", rideNo);
		List<UserCommentEntity> list = queryObj.list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public PromotionCodesEntity findReferralCode(Integer appUserId) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PromotionCodesEntity pc where "+
						"pc.appUser.appUserId=:appUserId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("appUserId", appUserId);
		return (PromotionCodesEntity) queryObj.uniqueResult();
	}

	public List<PersonAddressEntity> findPersonAddrress(PersonEntity person) {
		Session session = getCurrentSession();
		String query = "from com.tgi.safeher.entity.PersonAddressEntity uc where "
				+ "uc.person=:personId";
		Query queryObj = session.createQuery(query);
		queryObj.setParameter("personId", person);
		List<PersonAddressEntity> list = queryObj.list();
		return list;
	}

}

