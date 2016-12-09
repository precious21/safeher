package com.tgi.safeher.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.model.LatLng;
import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.BrainTree.BrainTree;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.beans.GeneralLedgerBean;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.beans.RideSearchResultBean;
import com.tgi.safeher.beans.RideTrackingBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.LedgerEntryTypeEnum;
import com.tgi.safeher.common.enumeration.LedgerOwnerTypeEnum;
import com.tgi.safeher.common.enumeration.MidRideRequest;
import com.tgi.safeher.common.enumeration.PaymentModeEnum;
import com.tgi.safeher.common.enumeration.ProcessStateAndActionEnum;
import com.tgi.safeher.common.enumeration.PromotionTypeEnum;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserTypeEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.GeneralLedgerEntity;
import com.tgi.safeher.entity.GiftTypeEntity;
import com.tgi.safeher.entity.LedgerEntryTypeEntity;
import com.tgi.safeher.entity.LedgerOwnerTypeEntity;
import com.tgi.safeher.entity.LedgerSummaryEntity;
import com.tgi.safeher.entity.PaymentModeEntity;
import com.tgi.safeher.entity.PostRideEntity;
import com.tgi.safeher.entity.PreRideEntity;
import com.tgi.safeher.entity.RideBillDistribution;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideCategoryEntity;
import com.tgi.safeher.entity.RideCharityEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideDetailEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RideGiftEntity;
import com.tgi.safeher.entity.RidePaymnetDistributionEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideSearchEntity;
import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.entity.RideSearchResultEntity;
import com.tgi.safeher.entity.SafeHerAccountInfoEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.SuburbEntity;
import com.tgi.safeher.entity.UserCharitiesEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserPromotionUseEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.repository.ActiveDriverStatusRepository;
import com.tgi.safeher.repository.CriteriaRepository;
import com.tgi.safeher.service.IAsync;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.ICalculatorService;
import com.tgi.safeher.service.IRideService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class RideService implements IRideService {

	private static final Logger logger = Logger.getLogger(RideService.class);

	@Autowired
	private CriteriaRepository criteriaRepository;

	@Autowired
	private RideConverter rideConverter;

	@Autowired
	private ICalculatorService calculatorService;

	@Autowired
	private RideDao rideDao;

	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private IAsyncEmailService iAsyncEmailService;

	@Autowired
	private SignUpConverter signUpConverter;

	@Autowired
	private IGoogleMapsAPIService googleMapsAPIService;

	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	@Autowired
	private ActiveDriverStatusRepository activeDriverStatusRepository;
	
	@Autowired
	private IAsync iAsync;
	
	@Autowired
	private PushIOS iosPush;
	
	@Autowired
	private PushAndriod andriodPush;


	@Override
	public RideCriteriaBean saveRideCriteria(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		logger.info("******Entering in saveRideCriteria with RideCriteriaBean "
				+ bean + " ******");
		rideConverter.validateRideCriteria(decorator);
		boolean rideCriteriaFlg = false;
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("User Information Error");
			}
			RideCriteriaEntity citeriaEntity = prepareRideCriteriaPerameters(bean);
			citeriaEntity.setAppUser(appUser);
			try {

				rideCriteriaFlg = rideDao.save(citeriaEntity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new GenericRuntimeException(e.getMessage());
			}
			if (rideCriteriaFlg) {
				bean.setRideCriteriaId(citeriaEntity.getRideCriteriaId()
						.toString());
				RideSearchEntity rideSearch = prepareRideSearchParams(bean);
				rideSearch.setRideCriteria(citeriaEntity);
				if (!rideDao.save(rideSearch)) {
					throw new GenericException("Ride Search Information Error");
				}
				bean.setRideSearchId(rideSearch.getRideSearchId().toString());

			} else {
				throw new GenericException("Ride Criteria Information Error");
			}
			logger.info("Save Ride Criteria  End ");
		}
		return bean;

	}

	@Override
	public RideCriteriaBean saveRideCriteriaV2(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		logger.info("Save Ride Criteria  Method called  ");
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		logger.info("******Entering in saveRideCriteriaV2 with RideCriteriaBean "
				+ bean + " ******");
		RideGiftEntity rideGiftEntity = null;
		RideCriteriaEntity citeriaEntity = null;
		boolean rideCriteriaFlg = false;

		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("User Information Error");
			}
			// Prepare Parameter og Ride Criteria
			// //////////////////////////////////////////////
			if (bean.getIsGifted() != null && bean.getIsGifted().equals("1")) {

//				rideGiftEntity = rideDao.findBy(RideGiftEntity.class, "giftNo",
//						bean.getGiftNo());
				rideGiftEntity = rideDao.findByGiftNo(bean.getGiftNo().trim());
				// prepareRideCriteriaPerametersV2(bean);
				if (rideGiftEntity == null) {
					throw new GenericException(
							"No gifted Ride against Gift No!");
				}

				if (rideGiftEntity.getGiftType().getGiftTypeId() == 2) {
					citeriaEntity = rideGiftEntity.getRideCriteria();
				} else {
					citeriaEntity = prepareGiftRideCriteriaPerametersV2(bean,rideGiftEntity.getRideCriteria());
				}
//				rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,
//						StatusEnum.InProcess.getValue()));
//				rideGiftEntity.setConsumeDate(DateUtil.now());
				citeriaEntity.setAppUser(appUser);
			} else {
				citeriaEntity = prepareRideCriteriaPerametersV2(bean);
				citeriaEntity.setAppUser(appUser);
			}
			// ////////////////////////////////////////////////////////
			try {
				// Redix Save Criteria
				criteriaRepository.saveCriteria(citeriaEntity);
				// db Save
				if (bean.getIsGifted().equals("1")) {
//					if (rideGiftEntity.getGiftType().getGiftTypeId() == 1) {
//						rideCriteriaFlg = rideDao.save(citeriaEntity);
//					}else{
						rideCriteriaFlg = rideDao.update(citeriaEntity);
//					}
					rideGiftEntity.setAppUserByReciverUserId(appUser);
					rideDao.update(rideGiftEntity);
				} else {
					rideCriteriaFlg = rideDao.save(citeriaEntity);
				}

			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericRuntimeException(
						"Server is not responding right now");
			}
			if (rideCriteriaFlg) {
				bean.setRideCriteriaId(citeriaEntity.getRideCriteriaId()
						.toString());
				// Ride Search Parameter
				RideSearchEntity rideSearch = prepareRideSearchParamsV2(bean);
				rideSearch.setRideCriteria(citeriaEntity);
				// db save
				if (!rideDao.save(rideSearch)) {
					throw new GenericException("Ride Search Information Error");
				}
				bean.setRideSearchId(rideSearch.getRideSearchId().toString());
				bean.setRideCriteriaWrapper(citeriaEntity);
				bean.setRideSearchWrapper(rideSearch);

			} else {
				throw new GenericException("Ride Criteria Information Error");
			}
			logger.info("Save Ride Criteria  End ");
		}
		return bean;

	}

	private RideCriteriaEntity prepareGiftRideCriteriaPerametersV2(
			RideCriteriaBean bean,RideCriteriaEntity citeriaEntity) {
		
		logger.info("******Entering in prepareGiftRideCriteriaPerametersV2 with RideCriteriaBean "
				+ bean + " ******");

		if (bean.getSourceLat() != null) {
			citeriaEntity.setSourceLat(bean.getSourceLat());
		}
		if (bean.getSourceLong() != null) {
			citeriaEntity.setSourceLong(bean.getSourceLong());
		}
		if (bean.getDistinatonLat() != null) {
			citeriaEntity.setDestinationLat(bean.getDistinatonLat());
		}
		if (bean.getDistinatonLong() != null) {
			citeriaEntity.setDestinationLong(bean.getDistinatonLong());
		}
		if (bean.getNoChild() != null) {
			citeriaEntity.setNoChild(Short.valueOf(bean.getNoChild()));
		}
		if (bean.getNoPassenger() != null) {
			citeriaEntity.setNoPassenger(Short.valueOf(bean.getNoPassenger()));
		}
		if (bean.getNoSeats() != null) {
			citeriaEntity.setNoSeats(Short.valueOf(bean.getNoSeats()));
		}
		if (bean.getIsShared() != null) {
			citeriaEntity.setIsShared(bean.getIsShared());
		}
		if (bean.getIsFav() != null) {
			citeriaEntity.setIsFav(bean.getIsFav());
		}
		if (StringUtil.isNotEmpty(bean.getSourceAddress())) {
			citeriaEntity.setDistinationAddress(bean.getDistinatonAddress());
		}
		if (StringUtil.isNotEmpty(bean.getDistinatonAddress())) {
			citeriaEntity.setSourceAddress(bean.getSourceAddress());
		}
		if (bean.getRideTypeId() != null) {
			if (bean.getRideTypeId().equals("1")) {
				citeriaEntity.setIsShared("1");
			} else {
				citeriaEntity.setIsShared("0");
			}
			citeriaEntity.setRideTypeId(Integer.valueOf(bean.getRideTypeId()));
		}
		return citeriaEntity;
	}

	private RideSearchEntity prepareRideSearchParams(RideCriteriaBean bean) {
		RideSearchEntity rideSearch = new RideSearchEntity();
		logger.info("******Entering in prepareRideSearchParams with RideCriteriaBean "
				+ bean + " ******");
		rideSearch.setSearchFromLat(bean.getSearchFromLat());
		rideSearch.setSearchFromLong(bean.getSearchFromLong());
		String searchSuburb = googleMapsAPIService
				.getSuburbFromLngLat(new LatLng(Double.parseDouble(bean
						.getSearchFromLat()), Double.parseDouble(bean
						.getSearchFromLong())));
		rideSearch.setSuburb(rideDao.findBy(SuburbEntity.class, "name",
				searchSuburb));
		rideSearch.setSearchTime(DateUtil.now());
		rideSearch.setRideCategory(rideDao.get(RideCategoryEntity.class,
				Integer.valueOf(bean.getRideCategoryId())));
		rideSearch.setRequestNo(bean.getRequestNo());

		return rideSearch;
	}

	private RideSearchEntity prepareRideSearchParamsV2(RideCriteriaBean bean) {
		RideSearchEntity rideSearch = new RideSearchEntity();
		logger.info("******Entering in prepareRideSearchParamsV2 with RideCriteriaBean "
				+ bean + " ******");
		rideSearch.setSearchFromLat(bean.getSearchFromLat());
		rideSearch.setSearchFromLong(bean.getSearchFromLong());
		rideSearch.setSearchTime(DateUtil.now());
		rideSearch.setRideCategory(rideDao.get(RideCategoryEntity.class,
				Integer.valueOf(bean.getRideCategoryId())));
		rideSearch.setRequestNo(bean.getRequestNo());

		return rideSearch;
	}

	private RideCriteriaEntity prepareRideCriteriaPerametersV2(
			RideCriteriaBean bean) {
		RideCriteriaEntity citeriaEntity = new RideCriteriaEntity();
		logger.info("******Entering in prepareRideCriteriaPerametersV2 with RideCriteriaBean "
				+ bean + " ******");

		if (bean.getSourceLat() != null) {
			citeriaEntity.setSourceLat(bean.getSourceLat());
		}
		if (bean.getSourceLong() != null) {
			citeriaEntity.setSourceLong(bean.getSourceLong());
		}
		if (bean.getDistinatonLat() != null) {
			citeriaEntity.setDestinationLat(bean.getDistinatonLat());
		}
		if (bean.getDistinatonLong() != null) {
			citeriaEntity.setDestinationLong(bean.getDistinatonLong());
		}
		if (bean.getNoChild() != null) {
			citeriaEntity.setNoChild(Short.valueOf(bean.getNoChild()));
		}
		if (bean.getNoPassenger() != null) {
			citeriaEntity.setNoPassenger(Short.valueOf(bean.getNoPassenger()));
		}
		if (bean.getNoSeats() != null) {
			citeriaEntity.setNoSeats(Short.valueOf(bean.getNoSeats()));
		}
		if (bean.getIsShared() != null) {
			citeriaEntity.setIsShared(bean.getIsShared());
		}
		if (bean.getIsFav() != null) {
			citeriaEntity.setIsFav(bean.getIsFav());
		}
		if (StringUtil.isNotEmpty(bean.getSourceAddress())) {
			citeriaEntity.setDistinationAddress(bean.getDistinatonAddress());
		}
		if (StringUtil.isNotEmpty(bean.getDistinatonAddress())) {
			citeriaEntity.setSourceAddress(bean.getSourceAddress());
		}
		if (bean.getRideTypeId() != null) {
			if (bean.getRideTypeId().equals("1")) {
				citeriaEntity.setIsShared("1");
			} else {
				citeriaEntity.setIsShared("0");
			}
			citeriaEntity.setRideTypeId(Integer.valueOf(bean.getRideTypeId()));
		}
		citeriaEntity.setIsGift("0");
		return citeriaEntity;
	}

	private RideCriteriaEntity prepareRideCriteriaPerameters(
			RideCriteriaBean bean) {
		logger.info("******Entering in prepareRideCriteriaPerameters with RideCriteriaBean "
				+ bean + " ******");

		RideCriteriaEntity citeriaEntity = new RideCriteriaEntity();
		String sourceSuburb = googleMapsAPIService
				.getSuburbFromLngLat(new LatLng(Double.parseDouble(bean
						.getSourceLat()), Double.parseDouble(bean
						.getSourceLong())));
		String destinationSuburb = googleMapsAPIService
				.getSuburbFromLngLat(new LatLng(Double.parseDouble(bean
						.getDistinatonLat()), Double.parseDouble(bean
						.getDistinatonLong())));

		citeriaEntity.setSuburbBySuburbSource(rideDao.findBy(
				SuburbEntity.class, "name", sourceSuburb));
		citeriaEntity.setSuburbBySuburbDestination(rideDao.findBy(
				SuburbEntity.class, "name", destinationSuburb));
		if (bean.getSourceLat() != null) {
			citeriaEntity.setSourceLat(bean.getSourceLat());
		}
		if (bean.getSourceLong() != null) {
			citeriaEntity.setSourceLong(bean.getSourceLong());
		}
		if (bean.getDistinatonLat() != null) {
			citeriaEntity.setDestinationLat(bean.getDistinatonLat());
		}
		if (bean.getDistinatonLong() != null) {
			citeriaEntity.setDestinationLong(bean.getDistinatonLong());
		}
		if (bean.getNoChild() != null) {
			citeriaEntity.setNoChild(Short.valueOf(bean.getNoChild()));
		}
		if (bean.getNoPassenger() != null) {
			citeriaEntity.setNoPassenger(Short.valueOf(bean.getNoPassenger()));
		}
		if (bean.getNoSeats() != null) {
			citeriaEntity.setNoSeats(Short.valueOf(bean.getNoSeats()));
		}
		if (bean.getIsShared() != null) {
			citeriaEntity.setIsShared(bean.getIsShared());
		}
		if (bean.getIsFav() != null) {
			citeriaEntity.setIsFav(bean.getIsFav());
		}
		if (bean.getRideTypeId() != null) {
			if (bean.getRideTypeId().equals("1")) {
				citeriaEntity.setIsShared("1");
			} else {
				citeriaEntity.setIsShared("0");
			}
			citeriaEntity.setRideTypeId(Integer.valueOf(bean.getRideTypeId()));
		}

		return citeriaEntity;
	}

	@Override
	public void addRideSearchResult(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		int count = 0;
		RideSearchResultBean bean = (RideSearchResultBean) decorator
				.getDataBean();
		logger.info("******Entering in addRideSearchResult with RideSearchResultBean "
				+ bean + " ******");

		RideRequestResponseBean rideReqRes = null;
		List<RideRequestResponseBean> rideRequestList = new ArrayList<RideRequestResponseBean>();
		if (bean.getAppUserId() != null) {
			RideSearchResultEntity rideSearchRes = new RideSearchResultEntity();
			RideCriteriaEntity rideCriteria = rideDao.get(
					RideCriteriaEntity.class,
					Integer.valueOf(bean.getRideCriteriaEntityId()));
			if (rideCriteria == null) {
				throw new GenericException("Ride Criteria Info not Exist");
			}
			RideSearchEntity rideSearch = rideDao.get(RideSearchEntity.class,
					Integer.valueOf(bean.getRideSearchEntityId()));
			if (rideSearch == null) {
				throw new GenericException("Ride Criteria Info not Exist");
			}
			rideSearchRes.setRideCriteria(rideCriteria);
			rideSearchRes.setRideSearch(rideSearch);
			boolean isSearchResult = rideDao.save(rideSearchRes);
			if (isSearchResult) {
				RideSearchResultDetailEntity r2 = null;
				for (RideSearchResultBean r1 : bean.getDriverPushNotification()) {
					r2 = new RideSearchResultDetailEntity();
					r2.setDriverLat(r1.getDriverLat());
					r2.setDriverLong(r1.getDriverLong());
					r2.setRideSearchResult(rideSearchRes);
					r2.setRequestNo(bean.getRequestNo());
					r2.setAppUser(rideDao.get(AppUserEntity.class,
							Integer.valueOf(r1.getAppUserId())));
					if (rideDao.save(r2)) {
						count += 1;
						rideReqRes = new RideRequestResponseBean();
						rideReqRes.setAppUserId(bean.getAppUserId());
						rideReqRes.setRideSearchResultDetailId(r2
								.getRideSearchResultDetailId().toString());
						rideReqRes.setRequestTime(DateUtil.now().toString());
						rideRequestList.add(rideReqRes);
					}

				}
				rideReqRes = new RideRequestResponseBean();
				rideReqRes.setList(rideRequestList);
				rideReqRes.setRideCriteriaId(rideCriteria.getRideCriteriaId()
						.toString());
				rideReqRes.setRequestNo(bean.getRequestNo());
				decorator.setDataBean(rideReqRes);
				rideSearchRes.setTotalOption(count);
				rideSearchRes.setResultTime(DateUtil.now());
				boolean isSearchDResult = rideDao.update(rideSearchRes);
				if (isSearchDResult) {
					decorator
							.setResponseMessage("Passenger Search Successfully Done!");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				}

			} else {
				throw new GenericRuntimeException("Ride Search Info Error");
			}
		}

	}

	@Override
	public void addRideSearchResultV2(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		int count = 0;
		RideSearchResultBean bean = (RideSearchResultBean) decorator
				.getDataBean();
		logger.info("******Entering in addRideSearchResultV2 with RideSearchResultBean "
				+ bean + " ******");

		RideRequestResponseBean rideReqRes = null;
		List<RideRequestResponseBean> rideRequestList = new ArrayList<RideRequestResponseBean>();
		if (bean.getAppUserId() != null) {
			RideSearchResultEntity rideSearchRes = new RideSearchResultEntity();
			RideCriteriaEntity rideCriteria = rideDao.get(
					RideCriteriaEntity.class,
					Integer.valueOf(bean.getRideCriteriaEntityId()));
			if (rideCriteria == null) {
				throw new GenericException("Ride Criteria Info not Exist");
			}
			RideSearchEntity rideSearch = rideDao.get(RideSearchEntity.class,
					Integer.valueOf(bean.getRideSearchEntityId()));
			if (rideSearch == null) {
				throw new GenericException("Ride Criteria Info not Exist");
			}
			rideSearchRes.setRideCriteria(rideCriteria);
			rideSearchRes.setRideSearch(rideSearch);
			boolean isSearchResult = rideDao.save(rideSearchRes);
			if (isSearchResult) {
				RideSearchResultDetailEntity r2 = null;
				for (RideSearchResultBean r1 : bean.getDriverPushNotification()) {
					r2 = new RideSearchResultDetailEntity();
					r2.setDriverLat(r1.getDriverLat());
					r2.setDriverLong(r1.getDriverLong());
					r2.setRideSearchResult(rideSearchRes);
					r2.setRequestNo(bean.getRequestNo());
					r2.setAppUser(rideDao.get(AppUserEntity.class,
							Integer.valueOf(r1.getAppUserId())));
					if (rideDao.save(r2)) {
						count += 1;
						rideReqRes = new RideRequestResponseBean();
						rideReqRes.setAppUserId(bean.getAppUserId());
						rideReqRes.setRideSearchResultDetailId(r2
								.getRideSearchResultDetailId().toString());
						rideReqRes.setRequestTime(DateUtil.now().toString());
						rideRequestList.add(rideReqRes);
					}

				}
				rideReqRes = new RideRequestResponseBean();
				rideReqRes.setList(rideRequestList);
				rideReqRes.setRideCriteriaId(rideCriteria.getRideCriteriaId()
						.toString());
				rideReqRes.setRequestNo(bean.getRequestNo());
				decorator.setDataBean(rideReqRes);
				rideSearchRes.setTotalOption(count);
				rideSearchRes.setResultTime(DateUtil.now());
				boolean isSearchDResult = rideDao.update(rideSearchRes);
				if (isSearchDResult) {
					decorator
							.setResponseMessage("Passenger Search Successfully Done!");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				}

			} else {
				throw new GenericRuntimeException("Ride Search Info Error");
			}
		}

	}

	@Override
	@Transactional
	public DistanceAPIBean getPassengerRideInfo(SafeHerDecorator decorator) {
		try {

			// get latest rideCriteria against PassengerApp
			RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
			logger.info("******Entering in getPassengerRideInfo with RideCriteriaBean "
					+ bean + " ******");

			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			RideCriteriaEntity rideEntity = null;
			RideSearchEntity rideSearchEntity = null;
			// List<RideCriteriaEntity> rideCriterialst =
			// (List<RideCriteriaEntity>) rideDao
			// .findCriteriaByQuery(appUser);
			// On the Base of Ride Criteria
			logger.info("Request No: " + bean.getRequestNo());

			try {
				// old
				// rideSearchEntity = appUserDao.findRideSearch(bean
				// .getRequestNo());
				// new
				String[] requestNO = bean.getRequestNo().split("_");
				if (requestNO != null && requestNO.length > 0) {
					rideSearchEntity = appUserDao.findRideSearch(requestNO[0]);
				} else {
					throw new GenericException("Please provide ride requestNo");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new GenericRuntimeException(e.getMessage());
			}

			rideEntity = rideSearchEntity.getRideCriteria();
			DistanceAPIBean distanceBean = null;
			if (rideEntity != null) {
				distanceBean = rideConverter.validateRideCrit(rideEntity);
				distanceBean.setFirstName(appUser.getPerson().getFirstName());
				distanceBean.setPhoneNum(appUser.getPersonDetail()
						.getPrimaryCell());

			} else {
				logger.info("Ride entity is null");
				throw new GenericRuntimeException("Ride entity is null");
			}
			return distanceBean;
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			throw new GenericRuntimeException(e.getMessage());
		}
		// get ETD ETP

	}

	@Override
	public String getPicUrlByAppUserId(String appUserId) {
		logger.info("******Entering in getPicUrlByAppUserId with appUserId "
				+ appUserId + " ******");
		AppUserEntity appUser = rideDao.get(AppUserEntity.class,
				Integer.valueOf(appUserId));
		AppUserBiometricEntity appUserEntity = rideDao.findByOject(
				AppUserBiometricEntity.class, "appUser", appUser);
		if (appUserEntity == null) {
			return "";
		}
		return appUserEntity.getPath();
	}
	
	private String getPicUrlByAppUser(AppUserEntity appUser) {
		logger.info("******Entering in getPicUrlByAppUserId with appUserId "
				+ appUser + " ******");
	
		AppUserBiometricEntity appUserEntity = rideDao.findByOject(
				AppUserBiometricEntity.class, "appUser", appUser);
		if (appUserEntity == null) {
			return "";
		}
		return appUserEntity.getPath();
	}

	@Override
	public void jusReached(SafeHerDecorator decorator) throws GenericException,
			IOException {
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		PreRideRequestBean preBean = (PreRideRequestBean) decorator
				.getDataBean();
		logger.info("******Entering in jusReached with PreRideRequestBean "
				+ preBean + " ******");
		rideConverter.validatePreRideStart(decorator);
		if (decorator.getErrors().size() == 0) {
			PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
					Integer.valueOf(preBean.getPreRideId()));
			if (preRideEntity != null) {
				preRideEntity.setStatus(appUserDao.get(StatusEntity.class, 13));
				if (appUserDao.update(preRideEntity)) {
					AppUserEntity appUserEntity = appUserDao.findById(
							AppUserEntity.class,
							new Integer(preBean.getPassengerappUserId()));
					UserLoginEntity loginEntity = appUserDao.findByOject(
							UserLoginEntity.class, "appUser", appUserEntity);
					if (loginEntity.getOsType() != null
							&& loginEntity.getOsType().equals("1")) {
						pushAndriod.pushAndriodPassengerNotification(
								loginEntity.getKeyToken(), "", "", "", "",
								PushNotificationStatus.driverJustArrived
										.toString(),
								"Driver Just Arrived in 2 Mint! Thanks");
					} else {
						pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
								loginEntity.getIsDev(), "", "", "", "",
								PushNotificationStatus.driverJustArrived
										.toString(),
								"Driver Just Arrived in 2 Mint! Thanks",
								loginEntity.getFcmToken());
					}

				} else {
					throw new GenericException("");
				}

			}
		} else {
			throw new GenericException(
					"Please Send Complete Information Against This Request");
		}
	}

	@Override
	public void startRide(SafeHerDecorator decorator) throws GenericException {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in startRide with RideBean " + ridebean
				+ " ******");

		rideConverter.validateRideStart(decorator);
		Boolean flag = false;
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(ridebean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("App User Id is not valid");
				}
				AppUserVehicleEntity appUserVehicle = appUserDao
						.findByOjectForVehicle(AppUserVehicleEntity.class,
								"appUser", appUser);
				if (appUserVehicle == null) {
					throw new GenericException(
							"App User has No Vehicle Registed");
				}
				RideEntity rideEntity = populateRideEntity(ridebean);
				rideEntity.setAppUserVehicle(appUserVehicle);
				try {
					flag = appUserDao.save(rideEntity);
				} catch (RuntimeException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (flag) {
					AppUserEntity passenger = null;
					RideDetailEntity rideDetailEntity = saveRideDetail(rideEntity,ridebean);
					RideBean ridbean = convertEntityToRideBean(rideEntity,
							rideDetailEntity);
					// getPassengerCardType
					// for Gifted Ride get Passenger Information
					RideCriteriaEntity rideCriteriaEntity = rideEntity
							.getRideCriteria();
					if (rideCriteriaEntity != null) {
						// if (rideCriteriaEntity.getIsGift().equals("1")) {
						// passenger = rideCriteriaEntity.getAppUser();
						// ridbean.setIsGifted("1");
						// ridebean.setDonarPassengerId(
						// rideCriteriaEntity.getAppUser().getAppUserId().toString());
						// } else {
						// passenger =
						// appUserDao.findById(AppUserEntity.class,new
						// Integer(ridebean.getAppUserByAppUserPassenger()));
						// ridbean.setIsGifted("0");
						// }
						passenger = appUserDao.findById(
								AppUserEntity.class,
								new Integer(ridebean
										.getAppUserByAppUserPassenger()));
						if (rideCriteriaEntity.getIsGift() != null
								&& rideCriteriaEntity.getIsGift().equals("1")) {
							RideGiftEntity rideGiftEntity = appUserDao
									.findByOject(RideGiftEntity.class,
											"rideCriteria", rideCriteriaEntity);
							if (rideGiftEntity != null) {
								rideEntity.setGiftType(rideGiftEntity
										.getGiftType());
								rideEntity
										.setGiftNo(rideGiftEntity.getGiftNo());
								appUserDao.update(rideEntity);
								rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,StatusEnum.InProcess.getValue()));
								rideGiftEntity.setConsumeDate(DateUtil.now());
								appUserDao.update(rideGiftEntity);
							}
							ridbean.setGiftNo(rideGiftEntity.getGiftNo());
							ridbean.setGiftType(rideGiftEntity.getGiftType()
									.getName());
							ridbean.setIsGifted("1");
						} else {
							ridbean.setIsGifted("0");
						}

					}

					if (passenger != null) {
						AppUserPaymentInfoEntity paymentInfoEntity = appUserDao
								.findByOject(AppUserPaymentInfoEntity.class,
										"appUser", passenger);
						if (paymentInfoEntity != null) {
							if (paymentInfoEntity.getDefaultType() != null
									&& paymentInfoEntity.getDefaultType()
											.trim().equalsIgnoreCase("C")) {
								CreditCardInfoEntity entity = appUserDao
										.findCreditCard(paymentInfoEntity
												.getAppUserPaymentInfoId());
								if (entity != null) {
									ridbean.setPpCN(entity.getCardNumber());
									ridbean.setPpC(entity.getCvv());
									ridbean.setPpCE(entity.getExpiryDate() + "");
									ridbean.setPpType(paymentInfoEntity
											.getDefaultType().trim());
								}
							}

						}
						sendNotificationToPssaenger(passenger,rideEntity.getRideNo(),rideDetailEntity);
						
						//this is for socket message to receiver start
						ridebean.setRideNo(rideEntity.getRideNo());
						ridebean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
								.getValue());
						ridebean.setNotificationMessage("Passenger Has Accept the Ride Request");
						decorator.setDataBean(ridebean);
						//this is for socket message to receiver end

						//saving rideTracking
						try {
							String[] requestNO = ridebean.getRequestNo().split("_");	
							if(requestNO != null && requestNO.length > 0){
								RideTrackingBean rideTrackingBean = new RideTrackingBean();
								rideTrackingBean.setRequestNo(requestNO[0]);
								rideTrackingBean.setRideNo(ridebean.getRideNo());
								rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
								rideTrackingBean.setState(ProcessStateAndActionEnum.START_RIDE.getValue()+"");
								rideTrackingBean.setAction(ProcessStateAndActionEnum.Start_Ride_By_Driver.getValue()+"");
								rideTrackingBean.setIsComplete("0");
								asyncServiceImpl.saveRideTracking(rideTrackingBean);	
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else{
						throw new GenericException("Ride Passenger Information Error!");
					}

					decorator.getResponseMap().put("data", ridbean);
					decorator.setResponseMessage("Lets Start Ride");
				} else {
					throw new GenericException("Ride Information Error!");
				}
			} catch (Exception ex) {
				logger.info("******Exiting from startRide  with Exception "
						+ ex.getMessage() + "******");
				ex.printStackTrace();
			}
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

		} else {
			throw new GenericException("Please Complete Ride Information!");
		}
	}

	@Override
	public void startRideV2(SafeHerDecorator decorator) throws GenericException {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in startRide with RideBean " + ridebean
				+ " ******");
		RideBean ridbean = new RideBean();
		rideConverter.validateRideStart(decorator);
		Boolean flag = false;
		if (decorator.getErrors().size() == 0) {
			try {
				
				PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
						Integer.valueOf(ridebean.getPreRideId()));
				if (preRideEntity != null) {
					RideRequestResponseEntity requestResponseEntity = appUserDao.findRideRequestResponseV2(
							new Integer(ridebean.getAppUserId()), preRideEntity.getRequestNo());
					if (requestResponseEntity != null) {
						if (requestResponseEntity.getStatusByStatusResponse().getStatusId() == 4) {
							throw new GenericException("Ride Already Cancel");
						}
					}
					
				}
				
				/*AppUserVehicleEntity appUserVehicle = (AppUserVehicleEntity) appUserDao
						.findByIdParamCommon("AppUserVehicleEntity",
								"appUser.appUserId", Integer.valueOf(ridebean.getAppUserId()));*/
				 AppUserVehicleEntity appUserVehicle = appUserDao
					      .findByOjectForVehicleV2(AppUserVehicleEntity.class,
					        "appUser.appUserId", Integer.valueOf(ridebean.getAppUserId()));
				 
				if (appUserVehicle == null) {
					throw new GenericException(
							"App User has No Vehicle Registed");
				}
				RideEntity rideEntity = populateRideEntity(ridebean);
				rideEntity.setAppUserVehicle(appUserVehicle);
				try {
					// getPassengerCardType
					// for Gifted Ride get Passenger Information
					RideCriteriaEntity rideCriteriaEntity = rideEntity
							.getRideCriteria();
					if (rideCriteriaEntity != null) {
						if (rideCriteriaEntity.getIsGift() != null
								&& rideCriteriaEntity.getIsGift().equals("1")) {

							RideGiftEntity rideGiftEntity = (RideGiftEntity) appUserDao
									.findByIdParamCommon("RideGiftEntity", "rideCriteria.rideCriteriaId", 
											rideCriteriaEntity.getRideCriteriaId());
							if (rideGiftEntity != null) {
								rideEntity.setGiftType(rideGiftEntity
										.getGiftType());
								rideEntity
										.setGiftNo(rideGiftEntity.getGiftNo());
								rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,StatusEnum.InProcess.getValue()));
								rideGiftEntity.setConsumeDate(DateUtil.now());
								appUserDao.update(rideGiftEntity);
							}
							ridbean.setGiftNo(rideGiftEntity.getGiftNo());
							ridbean.setGiftType(rideGiftEntity.getGiftType()
									.getName());
							ridbean.setIsGifted("1");
						} else {
							ridbean.setIsGifted("0");
						}

					}
					flag = appUserDao.save(rideEntity);
					if (flag) {
						RideDetailEntity rideDetailEntity = saveRideDetail(rideEntity,ridebean);
						ridbean = convertEntityToRideBean(rideEntity,
								rideDetailEntity);
						UserLoginEntity passenger = appUserDao.findByIdParam2(
								Integer.valueOf(ridebean.getAppUserByAppUserPassenger()));
						if (passenger != null) {
							
							sendNotificationToPssaengerV2(passenger,rideEntity.getRideNo(),rideDetailEntity);
							
							//this is for socket message to receiver start
							ridebean.setRideNo(rideEntity.getRideNo());
							ridebean.setNotificationType(PushNotificationStatus.AcceptRideFromPassenger
									.getValue());
							ridebean.setNotificationMessage("Passenger Has Accept the Ride Request");
							decorator.setDataBean(ridebean);
							//this is for socket message to receiver end

							//saving rideTracking
							try {
								String[] requestNO = ridebean.getRequestNo().split("_");	
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setRideNo(ridebean.getRideNo());
									rideTrackingBean.setStatus(StatusEnum.Ready.getValue()+"");
									rideTrackingBean.setState(ProcessStateAndActionEnum.START_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.Start_Ride_By_Driver.getValue()+"");
									rideTrackingBean.setIsComplete("0");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							//async saving driverTrackIntoDrivingDetailIntoMongo
							asyncServiceImpl.saveLocationTrackIntoDrivingDetail(
									Integer.valueOf(ridebean.getAppUserId()), "2");
							
						}else{
							throw new GenericException("Ride Passenger Information Error!");
						}

						decorator.getResponseMap().put("data", ridbean);
						decorator.setResponseMessage("Lets Start Ride");
						
					} else {
						throw new GenericException("Ride Information Error!");
					}
					
				} catch (RuntimeException e) {
					logger.info("******Exiting from startRide  with Exception "
							+ e.getMessage() + "******");
					e.printStackTrace();
				} catch (Exception e) {
					logger.info("******Exiting from startRide  with Exception "
							+ e.getMessage() + "******");
					e.printStackTrace();
				}
			} catch (Exception ex) {
				logger.info("******Exiting from startRide  with Exception "
						+ ex.getMessage() + "******");
				ex.printStackTrace();
			}
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

		} else {
			throw new GenericException("Please Complete Ride Information!");
		}
	}

	private void sendNotificationToPssaengerV2(UserLoginEntity loginEntity,
			String rideNo,RideDetailEntity rideDetailEntity) throws GenericException {
		logger.info("******Entering in sendNotificationToPassenger For Start Ride with rideNo "
				+ rideNo + "," + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		String sourceAddress=rideDetailEntity.getSourceAddress()!=null ? rideDetailEntity.getSourceAddress() :"";
		if(sourceAddress.length() > 100){
			sourceAddress=sourceAddress.substring(0, 100);
		}
		
		
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), sourceAddress, rideNo,rideDetailEntity.getSourceLat() ,rideDetailEntity.getSourceLong(),
						PushNotificationStatus.startRide.toString(),
						"Driver has Started ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToPassenger For Andriod with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), sourceAddress, rideNo, rideDetailEntity.getSourceLat(), rideDetailEntity.getSourceLong(),
						PushNotificationStatus.startRide.toString(),
						"Driver has Started ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationToPassenger For IOS  with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}

	}

	private void sendNotificationToPssaenger(AppUserEntity passenger,
			String rideNo,RideDetailEntity rideDetailEntity) throws GenericException {
		logger.info("******Entering in sendNotificationToPassenger For Start Ride with rideNo "
				+ rideNo + "," + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", passenger);
		String sourceAddress=rideDetailEntity.getSourceAddress()!=null ? rideDetailEntity.getSourceAddress() :"";
		if(sourceAddress.length() > 100){
			sourceAddress=sourceAddress.substring(0, 100);
		}
		
		
		
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), sourceAddress, rideNo,rideDetailEntity.getSourceLat() ,rideDetailEntity.getSourceLong(),
						PushNotificationStatus.startRide.toString(),
						"Driver has Started ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToPassenger For Andriod with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), sourceAddress, rideNo, rideDetailEntity.getSourceLat(), rideDetailEntity.getSourceLong(),
						PushNotificationStatus.startRide.toString(),
						"Driver has Started ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationToPassenger For IOS  with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}

	}

	private RideBean convertEntityToRideBean(RideEntity rideEntity,
			RideDetailEntity rideDetailEntity) {
		logger.info("******Entering in convertEntityToRideBean  ******");
		RideBean ridebean = new RideBean();
		ridebean.setEstimatedArrival(rideEntity.getEstimatedArrival());
		ridebean.setEstimatedDistance(rideEntity.getEstimatedDistance()
				.toString());
		ridebean.setEstimatedDuration(rideEntity.getEstimatedDuration());
		ridebean.setSourceLat(rideEntity.getRideCriteria().getSourceLat());
		ridebean.setSourceLong(rideEntity.getRideCriteria().getSourceLong());
		ridebean.setStartTime(rideEntity.getRideStartTime().toString());
		ridebean.setDestinationLat(rideEntity.getRideCriteria().getDestinationLat());
		ridebean.setDestinationLong(rideEntity.getRideCriteria().getDestinationLong());
		ridebean.setActualChild(rideDetailEntity.getActualChild().toString());
		ridebean.setActualPerson(rideDetailEntity.getActualPerson().toString());
		ridebean.setActualSeats(rideDetailEntity.getActualSeats().toString());
		ridebean.setRideTypeId(rideEntity.getRideId().toString());

		ridebean.setStartSourceAddress(rideDetailEntity.getSourceAddress());
		ridebean.setStartSourceLat(rideDetailEntity.getSourceLat());
		ridebean.setSourceLong(rideDetailEntity.getSourceLong());
		
		ridebean.setRideNo(rideEntity.getRideNo());
		ridebean.setRideId(rideEntity.getRideId().toString());
		ridebean.setRideDetailId(rideDetailEntity.getRideDetailId().toString());
		ridebean.setRideCriteriaId(rideEntity.getRideCriteria()
				.getRideCriteriaId().toString());
		ridebean.setPreRideId(rideEntity.getPreRide().getPreRideId().toString());
		ridebean.setWeatherType("1");
		ridebean.setCurrentDemand("1");
		return ridebean;
	}

	private RideDetailEntity saveRideDetail(RideEntity rideEntity ,RideBean  bean)
			throws GenericException {
		RideDetailEntity rideDetailEntity = new RideDetailEntity();
		RideCriteriaEntity rideCriteriaEntity = rideEntity.getRideCriteria();
		rideDetailEntity.setRideEntityId(rideEntity);
//		rideDetailEntity.setDestinationLat(rideCriteriaEntity
//				.getDestinationLat());
//		rideDetailEntity.setDestinationLong(rideCriteriaEntity
//				.getDestinationLong());
		rideDetailEntity.setSourceLat(bean.getStartSourceLat());
		rideDetailEntity.setSourceLong(bean.getStartSourceLong());
		rideDetailEntity.setSourceAddress(bean.getStartSourceAddress());
		rideDetailEntity.setActualChild(rideCriteriaEntity.getNoChild());
		rideDetailEntity.setActualPerson(rideCriteriaEntity.getNoPassenger());
		rideDetailEntity.setActualSeats(rideCriteriaEntity.getNoSeats());
		if (appUserDao.save(rideDetailEntity)) {

		} else {
			throw new GenericException("Ride Information Error!");
		}
		return rideDetailEntity;
	}

	private RideEntity populateRideEntity(RideBean ridebean) {
		logger.info("******Entering in populateRideEntity with RideBean "
				+ ridebean + " ******");
		RideEntity entity = new RideEntity();
		entity.setEstimatedDistance(Double.valueOf(ridebean
				.getEstimatedDistance()));
		System.out.println(Double.valueOf(ridebean.getEstimatedDistance()));
		entity.setEstimatedDuration(ridebean.getEstimatedDuration());
		System.out.println(ridebean.getEstimatedDuration());
		entity.setRideStartTime(DateUtil.parseTimestampFromFormats(ridebean
				.getStartTime()));
		System.out.println(entity.getRideStartTime().toString().length());
		entity.setStartTime(DateUtil.now());
		System.out.println(entity.getStartTime());
		// System.out.println(entity.getStartTime().toString().length());
		// Convert Second into Mint
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(entity.getRideStartTime().getTime());
		cal.add(Calendar.SECOND,
				Integer.valueOf(ridebean.getEstimatedDuration()));
		System.out.println(new Timestamp(cal.getTime().getTime()).toString()
				.length());
		entity.setEstimatedArrival(new Timestamp(cal.getTime().getTime())
				.toString());

		PreRideEntity preRideEntity = appUserDao.get(PreRideEntity.class,
				Integer.valueOf(ridebean.getPreRideId()));
		entity.setPreRide(preRideEntity);
		entity.setRideCriteria(preRideEntity.getRideFinalize()
				.getRideCriteria());
		if (preRideEntity.getRideFinalize().getRideCriteria() != null) {
			if (preRideEntity.getRideFinalize().getRideCriteria().getIsGift() != null
					&& preRideEntity.getRideFinalize().getRideCriteria()
							.getIsGift().equals("1")) {
				RideGiftEntity rideGiftEntity = appUserDao.findByOject(RideGiftEntity.class, "rideCriteria", preRideEntity.getRideFinalize().getRideCriteria());
				entity.setGiftNo(rideGiftEntity.getGiftNo());
				entity.setGiftType(rideGiftEntity.getGiftType());
				entity.setIsGifted("1");
			} else {
				entity.setIsGifted("0");
			}
		}

		String ride = "RD" + System.currentTimeMillis()
				+ preRideEntity.getPreRideId();
		entity.setRideNo(ride);
		System.out.println(ride.length());
		entity.setRideTypeId(preRideEntity.getRideFinalize().getRideCriteria()
				.getRideTypeId());

		return entity;
	}

	@Override
	public void endRide(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRide with RideBean " + rideBean
				+ " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		rideConverter.validateEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				rideEntity = appUserDao.findByParameter(RideEntity.class,
						"rideNo", rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil
							.parseTimestampFromFormats(rideBean
									.getRideEndTime()));
					if (appUserDao.update(rideEntity)) {
						// PrePare Payment Detail About Company Driver And
						// Charity

						rideBillEntity = preparePaymentDetail(rideBean,
								rideEntity);

						rideBean = convertEntityToBean(rideBillEntity, rideBean);
						sendNotificationToOPPonent(
								rideBillEntity.getAppUserByAppUserPassenger(),
								rideBillEntity.getInvoiceNo(),
								rideEntity.getRideNo());

						// set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(
								AppUserEntity.class,
								new Integer(rideBean
										.getAppUserByAppUserDriver()));
						if (driver != null) {
							ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity
											.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);
								}
							}
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
						decorator.setResponseMessage("Invoice Information");
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException(
								"End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (Exception ex) {
				logger.info("******Exiting from endRide with Exception "
						+ ex.getMessage() + " ******");
				ex.printStackTrace();
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}

	@Override
	public void endRideV2(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRideV2 with RideBean " + rideBean
				+ " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		rideConverter.validateEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				// rideEntity = appUserDao.findByParameter(RideEntity.class,
				// "rideNo", rideBean.getRideNo());
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil
							.parseTimestampFromFormats(rideBean
									.getRideEndTime()));
					if (appUserDao.update(rideEntity)) {
						// PrePare Payment Detail About Company Driver And
						// Charity

						rideBillEntity = preparePaymentDetail(rideBean,
								rideEntity);

						rideBean = convertEntityToBean(rideBillEntity, rideBean);

						AppUserEntity passenger = appUserDao.findById(
								AppUserEntity.class,
								new Integer(rideBean
										.getAppUserByAppUserPassenger()));
						if (passenger != null) {
							CreditCardInfoBean creditCardInfoBean = getCreditCardInfoAndClientTokenForPayment(passenger);
							if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType()
											.equalsIgnoreCase("C")) {
								// doPaymentHere
								// creditCardInfoBean.setPaymentClientToken(BrainTree.getToken());
								// generateNonce

								// transaction

								String message = BrainTree.transaction(
										rideBean.getTotalAmount(),
										rideBean.getNonce());
								if (message != null
										&& !message.contains("Error")) {
									// sendNotificationToDriver(rideBean.getAppUserByAppUserDriver());
									// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									// .getValue());
									// decorator.setResponseMessage("Transaction proceeded");

									// transcation Id generate here
									System.out.println("Transcation ID    :"
											+ message);
									rideBillEntity.setBtTransactionNo(message);
									rideBillEntity.setPaymentTime(DateUtil
											.now());
									rideBillEntity
											.setTotalAmount(Double
													.valueOf(rideBean
															.getTotalAmount()));
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									paymentMode
											.setPaymentModeId(PaymentModeEnum.Creditcard
													.getValue());
									rideBillEntity.setPaymentMode(paymentMode);
									StatusEntity status_payment = new StatusEntity();
									status_payment
											.setStatusId(StatusEnum.Deduct
													.getValue());
									rideBillEntity.setStatus(status_payment);
									// rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);

									if (appUserDao.update(rideBillEntity)) {
										RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
										ridePaymentDistributionEntity
												.setIsDue("1");
										ridePaymentDistributionEntity
												.setRideBill(rideBillEntity);
										ridePaymentDistributionEntity
												.setStatus(appUserDao
														.get(StatusEntity.class,
																StatusEnum.DistributionDue
																		.getValue()));
										// Will Change On Customer Demand
										// Distribution here
										Double charityPayment = Double
												.valueOf(rideBean
														.getTotalAmount()) * 2 / 100;
										Double driverPayment = (Double
												.valueOf(rideBean
														.getTotalAmount())) * 20 / 100;
										System.out.println((Double
												.valueOf(rideBean
														.getTotalAmount())));
										Double companyPayment = (Double
												.valueOf(rideBean
														.getTotalAmount())) * 78 / 100;

										rideBean.setCharityAmount(charityPayment
												+ "");
										rideBean.setDriverAmount(driverPayment
												+ "");
										rideBean.setCompanyAmount(companyPayment
												+ "");

										ridePaymentDistributionEntity
												.setCharityAmount(charityPayment);
										ridePaymentDistributionEntity
												.setCompanyAmount(companyPayment);
										ridePaymentDistributionEntity
												.setDriverAmount(driverPayment);
										appUserDao
												.saveOrUpdate(ridePaymentDistributionEntity);

										// //For Driver
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForDriver(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Driver Ledger Requriment");
										// }
										// //Passenger
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForPassenger(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Passenger Ledger Requriment");
										// }
										// //SafeHer
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForSafeHer(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill SafeHer Ledger Requriment");
										// }
										// //Charity
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForCharity(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Charity Ledger Requriment");
										// }
										//
										sendNotificationToOPPonentV2(
												rideBillEntity
														.getAppUserByAppUserPassenger(),
												rideBillEntity.getInvoiceNo(),
												rideEntity.getRideNo(), "1");
										decorator
												.setResponseMessage("Passenger has successfully paid your dues");

									} else {
										decorator
												.setReturnCode(ReturnStatusEnum.FAILURE
														.getValue());
										throw new GenericException(
												"Confirm Ride Make's an Error");
									}

								} else {
									throw new GenericException(message);
								}
								// send async invoice email
								iAsyncEmailService.sendInvoiceEmail(rideBean);

							} else if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType()
											.equalsIgnoreCase("P")) {

								sendNotificationToOPPonentV2(
										rideBillEntity
												.getAppUserByAppUserPassenger(),
										rideBillEntity.getInvoiceNo(),
										rideEntity.getRideNo(), "0");
								decorator
										.setResponseMessage("Invoice Information");
							}
						}
						// set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(
								AppUserEntity.class,
								new Integer(rideBean
										.getAppUserByAppUserDriver()));
						if (driver != null) {
							ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationEntity = appUserDao
										.findByOject(
												ActiveDriverLocationEntity.class,
												"appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity
											.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao
											.saveOrUpdate(activeDriverLocationEntity);
								}
							}
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException(
								"End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception "
						+ ex.getMessage() + " ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}

	@Override
	public void endRideV3(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRideV2 with RideBean " +rideBean+" ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity= null;
		RideEntity rideEntity =null;
		RideDetailEntity rideDetail =null;
		rideConverter.validateEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
//				rideEntity = appUserDao.findByParameter(RideEntity.class, "rideNo", rideBean.getRideNo());
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil.parseTimestampFromFormats(rideBean.getRideEndTime()));
					rideDetail = appUserDao.findByOject(RideDetailEntity.class, "rideEntityId",rideEntity);
					if(rideDetail==null){throw new GenericException("Ride Number is Not Correct");}
					rideDetail.setDestinationAddress(rideBean.getEndDistinationAddress());
					rideDetail.setDestinationLat(rideBean.getEndDestinationLat());
					rideDetail.setDestinationLong(rideBean.getEndDestinationLong());
					appUserDao.saveOrUpdate(rideDetail);
					if (appUserDao.update(rideEntity)) {
						// PrePare Payment Detail About Company Driver And
						// Charity
						rideBean.setTimeStampStartTime(rideEntity.getStartTime());
						rideBean.setTimeStampEndTime(rideEntity.getEndTime());
						rideBillEntity = preparePaymentDetail(rideBean,rideEntity);
						
						rideBean = convertEntityToBean(rideBillEntity, rideBean);
						
						AppUserEntity passenger = appUserDao.findById(AppUserEntity.class, 
								new Integer(rideBean.getAppUserByAppUserPassenger()));
						if(passenger != null){
							UserPromotionUseEntity userPromotionUseEntity = new UserPromotionUseEntity();
							Double rideAmount = new Double(
									rideBean.getTotalAmount());
							Double totalPromotionAmount = 0.0;
							Double totalDiscount = 0.0;
							String message = "";
							boolean isTransactionRequired = true;

							//checking if promotion exist for this user
							UserPromotionEntity userPromotionEntity = appUserDao.
									findUserPromotionOnPayment(passenger.getAppUserId(), 
											new Integer(PromotionTypeEnum.Promotion.getValue()+""));
							if(userPromotionEntity == null){
								userPromotionEntity = appUserDao.
										findUserPromotionOnPayment(passenger.getAppUserId(), 
												new Integer(PromotionTypeEnum.Referral.getValue()+""));
							}
							if(userPromotionEntity != null && 
									userPromotionEntity.getPromotionInfo() != null){
								if(StringUtil.isNotEmpty(userPromotionEntity.getPromotionInfo().
										getIsPartialUse()) && userPromotionEntity.getPromotionInfo().
											getIsPartialUse().equals("1")){
									totalPromotionAmount = new Double(userPromotionEntity.
											getPromotionInfo().getPartialValue());
								}else{
									totalPromotionAmount = new Double(userPromotionEntity.getTotalValue());
								}
								rideAmount = totalPromotionAmount - rideAmount;
								if(rideAmount < 0){
									isTransactionRequired = true;
									rideAmount = Math.abs(rideAmount);
									int totalUsed = (int)(userPromotionEntity.getTotalUsedValue() + totalPromotionAmount);
									userPromotionEntity.setTotalUsedValue(totalUsed);
									totalDiscount = totalPromotionAmount;
								}else if(rideAmount >= 0){
									isTransactionRequired = false;
									int totalUsed = (int)(userPromotionEntity.getTotalUsedValue() + 
											totalPromotionAmount - rideAmount);
									userPromotionEntity.setTotalUsedValue(totalUsed);
									totalDiscount = (totalPromotionAmount - rideAmount);	
								}
								if(userPromotionEntity.getTotalValue() == 
										userPromotionEntity.getTotalUsedValue()){
									userPromotionEntity.setIsActive("0");
								}
								userPromotionEntity.setLastUsedDate(
										DateUtil.getCurrentTimestamp());
								appUserDao.saveOrUpdate(userPromotionEntity);
							}else{
								isTransactionRequired = true;
							}
							CreditCardInfoBean creditCardInfoBean = 
									getCreditCardInfoAndClientTokenForPayment(passenger);
							if(creditCardInfoBean != null && 
									creditCardInfoBean.getDefaultType().equalsIgnoreCase("C")){
								if(isTransactionRequired){
									//makeTransaction
									message = BrainTree.transactionUsingCustomer(
											rideAmount+"", EncryptDecryptUtil.decrypt(creditCardInfoBean.getBtCustomer()));	
								}
								
//								rideBean.setDiscount(totalDiscount+"");
								 String text = Double.toString(Math.abs(totalDiscount));
						         int integerPlaces = text.indexOf('.');
						         int decimalPlaces = text.length() - integerPlaces - 1;
						         if(decimalPlaces == 0){
						          rideBean.setDiscount(totalDiscount+".00"); 
						         }else if(decimalPlaces == 1){
						          rideBean.setDiscount(totalDiscount+"0"); 
						         }else if(decimalPlaces >= 2){
						          rideBean.setDiscount(new DecimalFormat(
						                     "##.##").format(totalDiscount)+"");
						         }
								//transcation Id generate here
								System.out.println("Transcation ID    :"+message);
								rideBillEntity.setBtTransactionNo(EncryptDecryptUtil.encrypt(
										message));
								rideBillEntity.setPaymentTime(DateUtil.now());
								rideBillEntity.setTotalAmount(Double.valueOf(rideBean
										.getTotalAmount()));
								PaymentModeEntity paymentMode =new PaymentModeEntity();
								paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
								rideBillEntity.setPaymentMode(paymentMode);
								StatusEntity status_payment =new StatusEntity();
								status_payment.setStatusId(StatusEnum.Deduct.getValue());
								rideBillEntity.setStatus(status_payment);
								//rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);
								
								if (appUserDao.update(rideBillEntity)) {
									RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
									ridePaymentDistributionEntity.setIsDue("1");
									ridePaymentDistributionEntity.setRideBill(rideBillEntity);
									ridePaymentDistributionEntity.setStatus(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
									//Will Change On Customer Demand Distribution here
//									Double charityPayment =  Double.valueOf(rideBean.getTotalAmount())*2/100;
//									Double driverPayment = (Double.valueOf(rideBean.getTotalAmount()))*20/100;
//									System.out.println((Double.valueOf(rideBean.getTotalAmount())));
//									Double companyPayment = (Double.valueOf(rideBean.getTotalAmount()))* 78/100;

									Double charityPayment =  Double.valueOf(rideBean.getTotalAmount())*2/100;
									Double remainingAmount = Double.valueOf(rideBean.getTotalAmount())-charityPayment;
									Double driverPayment = remainingAmount*80/100;
									Double companyPayment = remainingAmount* 20/100;
									
									rideBean.setCharityAmount(charityPayment+"");
									rideBean.setDriverAmount(driverPayment+"");
									rideBean.setCompanyAmount(companyPayment+"");
									
									ridePaymentDistributionEntity.setCharityAmount(charityPayment);
									ridePaymentDistributionEntity.setCompanyAmount(companyPayment);
									ridePaymentDistributionEntity.setDriverAmount(driverPayment);
									appUserDao.saveOrUpdate(ridePaymentDistributionEntity);
									
									//savingRideCharity
									if(StringUtil.isNotEmpty(rideBean.getCharityId())){
										CharitiesEntity charitiesEntity = new CharitiesEntity();
										charitiesEntity.setCharitiesId(new Integer(rideBean.getCharityId()));
										RideCharityEntity rideCharityEntity = new RideCharityEntity();
//										rideCharityEntity.setRideCharityId(userCharitiesEntity);
										rideCharityEntity.setRide(rideEntity);
										rideCharityEntity.setCharities(charitiesEntity);
										appUserDao.saveOrUpdate(rideCharityEntity);
									}

									rideBean.setIsRatingDone("0");
									
									//saving setRideBill
									if(userPromotionEntity != null && 
											userPromotionEntity.getPromotionInfo() != null && 
												totalDiscount > 0){
										userPromotionUseEntity.setRideBill(rideBillEntity);
									}

									///////////////////////////////////////////Ledger Managment //////////////////////////////////////////
									
								//	asyncServiceImpl.ledgerManagmentForSafHer(decorator, ridePaymentDistributionEntity);
									
									//////////////////////////////////////////////////Ledger Managment Ended Her /////////////////////////////
									String paymentDone = "1";
									if (message.contains("Error")) {
										paymentDone = "-1";
										
									} 
									sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(), 
											rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(), totalDiscount+"", paymentDone);
									//this is for socket message to receiver start
									rideBean.setInvoiceNo(rideBillEntity.getInvoiceNo());
									rideBean.setRideNo(rideEntity.getRideNo());
									rideBean.setTotalDiscount(totalDiscount+"");
									rideBean.setPaymentDone(paymentDone);
									rideBean.setNotificationType(PushNotificationStatus.InvoiceRequest
											.getValue());
									rideBean.setNotificationMessage("Driver has ended ride");
									//this is for socket message to receiver end
									decorator.setResponseMessage("Invoice Information");
									decorator.setResponseMessage("Passenger has successfully paid your dues");
									
								} else {
									decorator
											.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
									throw new GenericException("Confirm Ride Make's an Error");
								}
								
								//send async invoice email
								iAsyncEmailService.sendInvoiceEmail(rideBean);
								
							}else if(creditCardInfoBean != null && 
									creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")){
								
								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(), 
										rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(), totalDiscount+"", "0");
//								//this is for socket message to receiver start
//								rideBean.setInvoiceNo(rideBillEntity.getInvoiceNo());
//								rideBean.setRideNo(rideEntity.getRideNo());
//								rideBean.setTotalDiscount(totalDiscount+"");
//								rideBean.setPaymentDone("0");
//								rideBean.setNotificationType(PushNotificationStatus.InvoiceRequest
//										.getValue());
//								rideBean.setNotificationMessage("Driver has ended ride");
//								//this is for socket message to receiver end
								decorator.setResponseMessage("Invoice Information");
							}else{
//								sendNotificationThatPaymentNotDone(
//										rideBillEntity.getAppUserByAppUserPassenger());
//								//this is for socket message to receiver start
//								rideBean.setRideNo(rideEntity.getRideNo());
//								rideBean.setNotificationType(PushNotificationStatus.PaymentCancel
//										.getValue());
//								rideBean.setNotificationMessage(
//										"Driver has ended ride but your payment is still payable due to credit card processing error");
//								//this is for socket message to receiver end
							}

							//this is for socket message to receiver start
							decorator.setDataBean(rideBean);
							//this is for socket message to receiver end
							
							//saving rideTracking
							try {	
								String[] requestNO = rideBean.getRequestNo().split("_");
								if(requestNO != null && requestNO.length > 0){
									RideTrackingBean rideTrackingBean = new RideTrackingBean();
									rideTrackingBean.setRequestNo(requestNO[0]);
									rideTrackingBean.setInvoiceNo(rideBean.getInvoiceNo());
									rideTrackingBean.setStatus(StatusEnum.Complete.getValue()+"");
									rideTrackingBean.setState(ProcessStateAndActionEnum.END_RIDE.getValue()+"");
									rideTrackingBean.setAction(ProcessStateAndActionEnum.End_Ride_By_Driver.getValue()+"");
									rideTrackingBean.setIsComplete("1");
									asyncServiceImpl.saveRideTracking(rideTrackingBean);	
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//saveRideStatusCancelForReleaseDriverFree
							activeDriverStatusRepository.saveRequestStatus(
									"C", rideBean.getRequestNo());
							//saving userPromotionUse
							if(userPromotionEntity != null && 
									userPromotionEntity.getPromotionInfo() != null && 
										totalDiscount > 0){
								userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
								userPromotionUseEntity.setRideBill(rideBillEntity);
								userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
								userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
								appUserDao.saveOrUpdate(userPromotionUseEntity);
							}
						}	
						/////////////////////Driver Promotion Settelment /////////////////
				//		asyncServiceImpl.driverPromotionSettelmentMethod(decorator,rideBillEntity);
						///////////////////Driver Promotion Settelment ////////////////////////
						//set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(AppUserEntity.class,
								new Integer(rideBean.getAppUserByAppUserDriver()));
						/*if(driver != null){
							ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationEntity = appUserDao.findByOject(
										ActiveDriverLocationEntity.class, "appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao.saveOrUpdate(activeDriverLocationEntity);
								}
							}
						}*/
						if(driver != null){
							//TODO:Check
							/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
							ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
									
								}
								/*activeDriverLocationEntity = appUserDao.findByOject(
										ActiveDriverLocationEntity.class, "appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao.saveOrUpdate(activeDriverLocationEntity);
									
								}*/
							}
						}
						
						//async delate from redis arrival and crieteria
						try {
							asyncServiceImpl.deletRideCriteriaAndArrivalTimeFromRedis(
									rideBean.getAppUserByAppUserPassenger());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//async saving driverDrivingDetailIntoMongo
						DriverDrivingDetailBean detailBean = new DriverDrivingDetailBean();
						detailBean.setAppUserId(driver.getAppUserId()+"");
						detailBean.setTotalRides(1.0);
						detailBean.setTotalEarning(new Double(
								rideBean.getTotalAmount()));
						detailBean.setDriverEarning((
								Double.valueOf(rideBean.getTotalAmount()))*20/100);
						detailBean.setDisputeAmount(0.0);
						asyncServiceImpl.saveDriverDrivingDetailIntoMongo(detailBean);

						//async saving driverTrackIntoDrivingDetailIntoMongo
						asyncServiceImpl.saveLocationTrackIntoDrivingDetail(driver.getAppUserId(), "3");
						
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException(
								"End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception " +ex.getMessage()+" ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}

	@Override
	public void endRideV4(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRideV4 with RideBean " + rideBean + " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		RideDetailEntity rideDetail = null;
		rideConverter.validateEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil.parseTimestampFromFormats(rideBean.getRideEndTime()));
					rideDetail = (RideDetailEntity) appUserDao.findByIdParamCommon("RideDetailEntity",
							"rideEntityId.rideId", rideEntity.getRideId());
					if (rideDetail == null) {
						throw new GenericException("Ride Number is Not Correct");
					}
					rideDetail.setDestinationAddress(rideBean.getEndDistinationAddress());
					rideDetail.setDestinationLat(rideBean.getEndDestinationLat());
					rideDetail.setDestinationLong(rideBean.getEndDestinationLong());
//					appUserDao.saveOrUpdate(rideDetail);
//					if (appUserDao.update(rideEntity)) {
						rideBillEntity = preparePaymentDetail(rideBean, rideEntity);
						rideBean = convertEntityToBean(rideBillEntity, rideBean);
						AppUserEntity passenger = appUserDao.findById(AppUserEntity.class,
								new Integer(rideBean.getAppUserByAppUserPassenger()));
						if (passenger != null) {
							UserPromotionUseEntity userPromotionUseEntity = new UserPromotionUseEntity();
							Double rideAmount = new Double(rideBean.getTotalAmount());
							Double totalPromotionAmount = 0.0;
							Double totalDiscount = 0.0;
							String message = "";
							boolean isTransactionRequired = true;

							// checking if promotion exist for this user
							UserPromotionEntity userPromotionEntity = appUserDao.findUserPromotionOnPayment(
									passenger.getAppUserId(), new Integer(PromotionTypeEnum.Promotion.getValue() + ""));
							if (userPromotionEntity == null) {
								userPromotionEntity = appUserDao.findUserPromotionOnPayment(passenger.getAppUserId(),
										new Integer(PromotionTypeEnum.Referral.getValue() + ""));
							}
							if (userPromotionEntity != null && userPromotionEntity.getPromotionInfo() != null) {
								if (StringUtil.isNotEmpty(userPromotionEntity.getPromotionInfo().getIsPartialUse())
										&& userPromotionEntity.getPromotionInfo().getIsPartialUse().equals("1")) {
									totalPromotionAmount = new Double(
											userPromotionEntity.getPromotionInfo().getPartialValue());
								} else {
									totalPromotionAmount = new Double(userPromotionEntity.getTotalValue());
								}
								rideAmount = totalPromotionAmount - rideAmount;
								if (rideAmount < 0) {
									isTransactionRequired = true;
									rideAmount = Math.abs(rideAmount);
									int totalUsed = (int) (userPromotionEntity.getTotalUsedValue()
											+ totalPromotionAmount);
									userPromotionEntity.setTotalUsedValue(totalUsed);
									totalDiscount = totalPromotionAmount;
								} else if (rideAmount >= 0) {
									isTransactionRequired = false;
									int totalUsed = (int) (userPromotionEntity.getTotalUsedValue()
											+ totalPromotionAmount - rideAmount);
									userPromotionEntity.setTotalUsedValue(totalUsed);
									totalDiscount = (totalPromotionAmount - rideAmount);
								}
								if (userPromotionEntity.getTotalValue() == userPromotionEntity.getTotalUsedValue()) {
									userPromotionEntity.setIsActive("0");
								}
								userPromotionEntity.setLastUsedDate(DateUtil.getCurrentTimestamp());
								//appUserDao.saveOrUpdate(userPromotionEntity);
							} else {
								isTransactionRequired = true;
							}
							CreditCardInfoBean creditCardInfoBean = getCreditCardInfoAndClientTokenForPayment(
									passenger);
							if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType().equalsIgnoreCase("C")) {
								String paymentDone = "";
								if (isTransactionRequired) {
									// makeTransactionAndOtherThingsAsync
									iAsync.endRideAsyncCall(rideBillEntity, rideBean, 
											rideAmount, totalDiscount, creditCardInfoBean, 
											userPromotionEntity, userPromotionUseEntity, rideDetail, rideEntity);
									paymentDone = "-1";
								} else {
									rideBean.setDiscount(totalDiscount+"");
									String text = Double.toString(Math.abs(totalDiscount));
							        int integerPlaces = text.indexOf('.');
							        int decimalPlaces = text.length() - integerPlaces - 1;
							        if(decimalPlaces == 0){
							         rideBean.setDiscount(totalDiscount+".00"); 
							        }else if(decimalPlaces == 1){
							         rideBean.setDiscount(totalDiscount+"0"); 
							        }else if(decimalPlaces >= 2){
							         rideBean.setDiscount(new DecimalFormat(
							                    "##.##").format(totalDiscount)+"");
							        }
									paymentDone = "1";

//									Double charityPayment =  Double.valueOf(rideBean.getTotalAmount())*2/100;
//									Double driverPayment = (Double.valueOf(rideBean.getTotalAmount()))*20/100;
//									System.out.println((Double.valueOf(rideBean.getTotalAmount())));
//									Double companyPayment = (Double.valueOf(rideBean.getTotalAmount()))* 78/100;
									
									Double charityPayment =  Double.valueOf(rideBean.getTotalAmount())*2/100;
									Double remainingAmount = Double.valueOf(rideBean.getTotalAmount())-charityPayment;
									Double driverPayment = remainingAmount*80/100;
									Double companyPayment = remainingAmount* 20/100;
									
									rideBean.setCharityAmount(charityPayment+"");
									rideBean.setDriverAmount(driverPayment+"");
									rideBean.setCompanyAmount(companyPayment+"");
								}
								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(),
										rideBillEntity.getInvoiceNo(), rideEntity.getRideNo(), totalDiscount + "",
										paymentDone);
								decorator.setResponseMessage("Invoice Information");
								decorator.setResponseMessage("Passenger has successfully paid your dues");

							} else if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {

								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(),
										rideBillEntity.getInvoiceNo(), rideEntity.getRideNo(), totalDiscount + "", "0");
								decorator.setResponseMessage("Invoice Information");
							}
							
							//async delate from redis arrival and crieteria
							try {
								asyncServiceImpl.deletRideCriteriaAndArrivalTimeFromRedis(
										rideBean.getAppUserByAppUserPassenger());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							//saveRideStatusCancelForReleaseDriverFree
							activeDriverStatusRepository.saveRequestStatus(
									"C", rideBean.getRequestNo());
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
//					} else {
//						throw new GenericException("End Ride information Error!");
//					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception " + ex.getMessage() + " ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}
	
	private CreditCardInfoBean getCreditCardInfoAndClientTokenForPayment(
			AppUserEntity pessenger) {
		logger.info("******Entering in getCreditCardInfoAndClientTokenForPayment ******");
		CreditCardInfoBean creditCardInfoBean = new CreditCardInfoBean();
		AppUserPaymentInfoEntity paymentInfoEntity = appUserDao.findByOject(
				AppUserPaymentInfoEntity.class, "appUser", pessenger);
		if (paymentInfoEntity != null) {
			if (paymentInfoEntity.getDefaultType() != null
					&& paymentInfoEntity.getDefaultType().trim()
							.equalsIgnoreCase("C")) {
				CreditCardInfoEntity cardInfoEntity = appUserDao
						.findCreditCard(paymentInfoEntity
								.getAppUserPaymentInfoId());
				if (cardInfoEntity != null) {
					creditCardInfoBean = signUpConverter
							.convertEntityToCreditCardInofBean(cardInfoEntity);
				}
			}
			creditCardInfoBean.setDefaultType(paymentInfoEntity
					.getDefaultType().trim() + "");
		}
		return creditCardInfoBean;
	}

	private void sendNotificationToOPPonentV3(AppUserEntity appUserEntity,
			String invoiceNo, String rideNo, String discount, String paymentDone)
			throws GenericException {
		logger.info("******Entering in sendNotificationToOPPonentV2 with invoiceNo, rideNo, paymentDone "
				+ invoiceNo
				+ ","
				+ rideNo
				+ ","
				+ ","
				+ paymentDone
				+ " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				andriodPush.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), invoiceNo, rideNo,
						paymentDone, discount,
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				iosPush.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), invoiceNo, rideNo, paymentDone,
						discount,
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}
	}

	
	private void sendNotificationThatPaymentNotDone(AppUserEntity appUserEntity) throws GenericException {
		logger.info("******Entering in sendNotificationThatPaymentNotDone with appUserEntity ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class,	"appUser", appUserEntity);
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), "", "", "", "",
						PushNotificationStatus.PaymentCancel.toString(),
						"Driver has ended ride but your payment is still payable due to credit card processing error");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationThatPaymentNotDone with Exception "+e.getMessage()+" ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), "", "", "", "",
						PushNotificationStatus.PaymentCancel.toString(),
						"Driver has ended ride but your payment is still payable due to credit card processing error", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationThatPaymentNotDone with Exception "+e.getMessage()+" ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}		
	}
	
	private void sendNotificationToOPPonentV3ForGift(AppUserEntity appUserEntity,
			String invoiceNo, String rideNo, String discount, String paymentDone)
			throws GenericException {
		logger.info("******Entering in sendNotificationToOPPonentV2 with invoiceNo, rideNo, paymentDone "
				+ invoiceNo
				+ ","
				+ rideNo
				+ ","
				+ ","
				+ paymentDone
				+ " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), invoiceNo, rideNo,
						paymentDone, discount,
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), invoiceNo, rideNo, paymentDone,
						discount,
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}
	}	
		
	private void sendNotificationToOPPonentV2(AppUserEntity appUserEntity,
			String invoiceNo, String rideNo, String paymentDone)
			throws GenericException {
		logger.info("******Entering in sendNotificationToOPPonentV2 with invoiceNo, rideNo, paymentDone "
				+ invoiceNo
				+ ","
				+ rideNo
				+ ","
				+ ","
				+ paymentDone
				+ " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), invoiceNo, rideNo,
						paymentDone, "",
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), invoiceNo, rideNo, paymentDone,
						"", PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				logger.info("******Exiting from sendNotificationToOPPonentV2 with Exception "
						+ e.getMessage() + " ********");
				e.printStackTrace();
				throw new GenericException(e);
			}
		}
	}

	private void sendNotificationToOPPonent(AppUserEntity appUserEntity,
			String invoiceNo, String rideNo) throws GenericException {
		logger.info("******Entering in sendNotificationToOPPonent with invoiceNo, rideNo "
				+ invoiceNo + "," + rideNo + "," + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity.getOsType() != null
				&& loginEntity.getOsType().equals("1")) {
			try {
				// PushAndriod.pushAndriodDriverNotification(
				// loginEntity.getKeyToken(), invoiceNo, rideNo, "", "",
				// PushNotificationStatus.EndRideConfirmPassenger.toString(),
				// "Passenger Has Confirmed Your Payment");
				pushAndriod.pushAndriodPassengerNotification(
						loginEntity.getKeyToken(), invoiceNo, rideNo, "", "",
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride");
			} catch (IOException e) {
				logger.info("******Exiting from sendNotificationToOPPonent with Exception "
						+ e.getMessage() + " ********");

				e.printStackTrace();
				throw new GenericException(e);
			}
		} else {
			try {
				// PushIOS.pushIOSDriver(loginEntity.getKeyToken(),
				// loginEntity.getIsDev(), invoiceNo, rideNo, "", "",
				// PushNotificationStatus.EndRideConfirmPassenger.toString(),
				// "Passenger Has Confirmed Your Payment");
				pushIOS.pushIOSPassenger(loginEntity.getKeyToken(),
						loginEntity.getIsDev(), invoiceNo, rideNo, "", "",
						PushNotificationStatus.InvoiceRequest.toString(),
						"Driver has ended ride", loginEntity.getFcmToken());
			} catch (GenericException e) {
				e.printStackTrace();
				logger.info("******Exiting from sendNotificationToOPPonent with Exception "
						+ e.getMessage() + " ********");
				throw new GenericException(e);
			}
		}
	}

	private RideBean convertEntityToBean(RideBillEntity rideBillEntity,
			RideBean rideBean) {

		rideBean.setInvoiceNo(rideBillEntity.getInvoiceNo());
		rideBean.setRideId(rideBillEntity.getRideInfoId().getRideId()
				.toString());
		if (rideBillEntity.getAppUserByAppUserDonar() != null) {
			rideBean.setIsGifted("1");
			// rideBean.setgift
		} else {
			rideBean.setIsGifted("0");
		}

		logger.info("******Entering in convertEntityToBean with RideBean "
				+ rideBean + " ******");
		return rideBean;
	}

	private RideBillEntity preparePaymentDetail(RideBean rideBean,
			RideEntity rideEntity) {
		logger.info("******Entering in preparePaymentDetail with RideBean "
				+ rideBean + " ******");
		RideBillEntity rideBillEntity = new RideBillEntity();
		rideBillEntity.setAppUserByAppUserDriver(appUserDao.get(
				AppUserEntity.class,
				Integer.valueOf(rideBean.getAppUserByAppUserDriver())));
		rideBillEntity.setAppUserByAppUserPassenger(appUserDao.get(
				AppUserEntity.class,
				Integer.valueOf(rideBean.getAppUserByAppUserPassenger())));
		rideBillEntity.setRideInfoId(rideEntity);
		if (rideEntity.getIsGifted() != null
				&& rideEntity.getIsGifted().equals("1")) {
			RideGiftEntity rideGiftEntity = appUserDao.findByOject(
					RideGiftEntity.class, "rideCriteria",
					rideEntity.getRideCriteria());
			if (rideGiftEntity != null) {
				rideBillEntity.setAppUserByAppUserDonar(rideGiftEntity
						.getAppUserBySenderUserId());
			}

		}
		rideBillEntity.setFineAmount(Double.valueOf(rideBean.getFineAmount()));
		rideBillEntity.setInvoiceNo("IN" + System.currentTimeMillis());
		rideBillEntity
				.setTotalAmount(Double.valueOf(rideBean.getTotalAmount()));
		rideBillEntity.setRideAmount(Double.valueOf(rideBean.getRideAmount()));
		System.out.println(rideBillEntity.getRideInfoId().getRideNo());
		if (appUserDao.save(rideBillEntity)) {

		}
		return rideBillEntity;
	}

	private PostRideEntity populatePostRide(RideBean ridebean) {
		logger.info("******Entering in populatePostRide with RideBean "
				+ ridebean + " ******");
		PostRideEntity postRideEntity = new PostRideEntity();
		postRideEntity.setActualArrival(DateUtil
				.parseTimestampFromFormats(ridebean.getActualArrival()));
		postRideEntity.setActualDistance(Double.valueOf(ridebean
				.getActualDistance()));
		postRideEntity.setActualDuration(ridebean.getActualDuration());
		postRideEntity.setIdelTime(ridebean.getIdelTime());
		postRideEntity.setIsModified("0");
		postRideEntity.setActualArrival(DateUtil.now());
		postRideEntity.setRideEntityId(appUserDao.findByParameter(
				RideEntity.class, "rideNo", ridebean.getRideNo()));
		return postRideEntity;
	}

	@Override
	public void getInvoiceInfo(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in getInvoiceInfo with RideBean "
				+ rideBean + " ******");
		rideConverter.validateInvoiceInfo(decorator);
		RideEntity rideEntity = null;
		RideBillEntity rideBillEntity = null;
		RideDetailEntity rideDetail=null;
		if (decorator.getErrors().size() == 0) {
			rideEntity = appUserDao.findByParameter(RideEntity.class, "rideNo",
					rideBean.getRideNo());
			if (rideEntity != null) {
				rideBillEntity = appUserDao.findByOjectForInvoice(RideBillEntity.class,
						"rideInfoId", rideEntity);
				rideDetail = appUserDao.findByOject(RideDetailEntity.class, "rideEntityId",rideEntity);
				if(rideDetail==null){throw new GenericException("Ride Number is Not Correct");}
			} else {
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
				throw new GenericException("Ride Number is Not Correct");
			}
			if (rideBillEntity != null) {
				rideBean = convertBillingEntityToBean(rideBillEntity,
						rideEntity);
				rideBean.setStartSourceAddress(rideDetail.getSourceAddress());
				rideBean.setEndDistinationAddress(rideDetail.getDestinationAddress());
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				decorator.setResponseMessage("Invoice Information");
				decorator.getResponseMap().put("data", rideBean);
			} else {
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
				throw new GenericException("No Data Against Ride No");
			}

		} else {
			throw new GenericException("Please Complete Invoice Information!");
		}
	}

	private RideBean convertBillingEntityToBean(RideBillEntity rideBillEntity,
			RideEntity rideEntity) {
		logger.info("******Entering in convertBillingEntityToBean  ******");
		RideBean rideBean = new RideBean();
		rideBean.setInvoiceNo(rideBillEntity.getInvoiceNo());
		rideBean.setRideNo(rideEntity.getRideNo());
		RideDetailEntity rideDetail = appUserDao.findByOject(
				RideDetailEntity.class, "rideEntityId", rideEntity);
		rideBean.setFineAmount(rideBillEntity.getFineAmount().toString());
		rideBean.setRideAmount(rideBillEntity.getRideAmount().toString());
		rideBean.setTotalAmount(rideBillEntity.getTotalAmount().toString());
		PostRideEntity postrideEntity = appUserDao.findByOjectForInvoice(
				PostRideEntity.class, "rideEntityId", rideEntity);
		rideBean.setActualDistance(postrideEntity.getActualDistance()
				.toString());
		rideBean.setActualDuration(postrideEntity.getActualDuration()
				.toString());
		rideBean.setActualArrival(postrideEntity.getActualArrival().toString());
		// rideBean.setActualDistance(rideEntity.getEstimatedDistance().toString());
		rideBean.setActualPerson(rideDetail.getActualPerson().toString());
		rideBean.setDestinationLat(rideDetail.getDestinationLat());
		rideBean.setDestinationLong(rideDetail.getDestinationLong());
		rideBean.setSourceLat(rideDetail.getSourceLat());
		rideBean.setSourceLong(rideDetail.getSourceLong());
		rideBean.setTipAmount(rideBillEntity.getTipAmount() + "");
		// rideBean.setAppUserByAppUserDriver(rideBillEntity
		// .getAppUserByAppUserDriver().getAppUserNo().toString());
		// rideBean.setAppUserByAppUserPassenger(rideBillEntity
		// .getAppUserByAppUserPassenger().getAppUserNo().toString());
		if(rideEntity.getIsGifted()!=null && rideEntity.getIsGifted().equals("1")){
			RideGiftEntity rideGiftEntity = appUserDao.findByOject(
					RideGiftEntity.class, "rideCriteria",
					rideEntity.getRideCriteria());
			rideBean.setGiftAmount(rideGiftEntity.getConsumeAmount()+"");
			rideBean.setGiftNo(rideGiftEntity.getGiftNo());
			rideBean.setGiftType(rideGiftEntity.getGiftType().getGiftTypeId()+"");
		}
		return rideBean;
	}

	@Override
	public void confirmEndRide(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in getInvoiceInfo with RideBean "
				+ rideBean + " ******");
		rideConverter.validateConfirmRide(decorator);
		RidePaymnetDistributionEntity ridePaymentDistributionEntity = null;
		RideBillEntity rideBillEntity = null;
		AppUserPaymentInfoEntity appUserPyment = null;
		GeneralLedgerBean generalLedgerBean = null;
		PaymentModeEntity paymentMode = null;
		StatusEntity status_payment = null;
		if (decorator.getErrors().size() == 0) {

			rideBillEntity = appUserDao.findByParameter(RideBillEntity.class,
					"invoiceNo", rideBean.getInvoiceNo());
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserPassenger()));
			if (appUser != null) {
				appUserPyment = appUserDao.checkAppUserPaymentInfo(appUser);
				if (appUserPyment == null) {
					decorator
							.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
					throw new GenericException(
							"No Payment Information Exist Please Confrim Your Payment Method");
				}
			} else {
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
				throw new GenericException("User is Not Exist");
			}
			if (rideBillEntity != null) {
				rideBillEntity.setTipAmount(Double.valueOf(rideBean
						.getTipAmount()));
				rideBillEntity.setTotalAmount(Double.valueOf(rideBean
						.getTotalAmount()));
				// /////////////////////
				// appUserPyment
				// appUserDao.findByOject(CreditCardInfoEntity.class,
				// entityAttribute, value)
				// rideBillEntity.setCreditCardInfo(appUserDao.get(
				// CreditCardInfoEntity.class,
				// Integer.valueOf(rideBean.getCreditCardInfo())));

				// //////////////////////////////////////////////////
				if (appUserPyment.getDefaultType() != null
						&& appUserPyment.getDefaultType().trim()
								.equalsIgnoreCase("C")) {
					String message = BrainTree.transaction(
							rideBean.getTotalAmount(), rideBean.getNonce());
					if (message != null && !message.contains("Error")) {
						// sendNotificationToDriver(rideBean.getAppUserByAppUserDriver());
						// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
						// .getValue());
						// decorator.setResponseMessage("Transaction proceeded");

						// transcation Id generate here
						System.out.println("Transcation ID    :" + message);
						rideBillEntity.setBtTransactionNo(message);
						rideBillEntity.setPaymentTime(DateUtil.now());
						paymentMode = new PaymentModeEntity();
						paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard
								.getValue());
						rideBillEntity.setPaymentMode(paymentMode);
						status_payment = new StatusEntity();
						status_payment
								.setStatusId(StatusEnum.Deduct.getValue());
						rideBillEntity.setStatus(status_payment);
						// rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);

					} else {
						throw new GenericException(message);
					}
				} else if (appUserPyment.getDefaultType() != null
						&& appUserPyment.getDefaultType().trim()
								.equalsIgnoreCase("P")) {
					String message = null;
//					BrainTree.payPalTransaction(
//							rideBean.getTotalAmount(), rideBean.getNonce());
					if (message != null && !message.contains("Error")) {
						// sendNotificationToDriver(rideBean.getAppUserByAppUserDriver());
						// decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
						// .getValue());
						// decorator.setResponseMessage("Transaction proceeded");
						System.out.println("Transcation ID    :" + message);
						rideBillEntity.setBtTransactionNo(message);
						rideBillEntity.setPaymentTime(DateUtil.now());
						paymentMode = new PaymentModeEntity();
						paymentMode.setPaymentModeId(PaymentModeEnum.PayPal
								.getValue());
						rideBillEntity.setPaymentMode(paymentMode);
						status_payment = new StatusEntity();
						status_payment
								.setStatusId(StatusEnum.Deduct.getValue());
						rideBillEntity.setStatus(status_payment);
					} else {
						throw new GenericException(message);
					}
				}

				if (appUserDao.update(rideBillEntity)) {
					ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
					ridePaymentDistributionEntity.setIsDue("1");
					ridePaymentDistributionEntity.setRideBill(rideBillEntity);
					ridePaymentDistributionEntity.setStatus(appUserDao.get(
							StatusEntity.class,
							StatusEnum.DistributionDue.getValue()));
					// Will Change On Customer Demand Distribution here
					Double charityPayment = Double.valueOf(rideBean
							.getTotalAmount()) * 2 / 100;
					Double driverPayment = Double.valueOf(rideBean
							.getTipAmount())
							+ (Double.valueOf(rideBean.getTotalAmount()))
							* 20
							/ 100;
					System.out.println((Double.valueOf(rideBean
							.getTotalAmount())));
					Double companyPayment = (Double.valueOf(rideBean
							.getTotalAmount())) * 78 / 100;

					rideBean.setCharityAmount(charityPayment + "");
					rideBean.setDriverAmount(driverPayment + "");
					rideBean.setCompanyAmount(companyPayment + "");

					ridePaymentDistributionEntity
							.setCharityAmount(charityPayment);
					ridePaymentDistributionEntity
							.setCompanyAmount(companyPayment);
					ridePaymentDistributionEntity
							.setDriverAmount(driverPayment);
					appUserDao.saveOrUpdate(ridePaymentDistributionEntity);

					// //For Driver
					// generalLedgerBean=
					// rideConverter.convertEntityToBeanForDriver(ridePaymentDistributionEntity);
					// decorator.setDataBean(generalLedgerBean);
					// ledgerManagment(decorator);
					// if (decorator.getErrors().size() != 0) {
					// throw new GenericException(
					// "Please FullFill Driver Ledger Requriment");
					// }
					// //Passenger
					// generalLedgerBean=
					// rideConverter.convertEntityToBeanForPassenger(ridePaymentDistributionEntity);
					// decorator.setDataBean(generalLedgerBean);
					// ledgerManagment(decorator);
					// if (decorator.getErrors().size() != 0) {
					// throw new GenericException(
					// "Please FullFill Passenger Ledger Requriment");
					// }
					// //SafeHer
					// generalLedgerBean=
					// rideConverter.convertEntityToBeanForSafeHer(ridePaymentDistributionEntity);
					// decorator.setDataBean(generalLedgerBean);
					// ledgerManagment(decorator);
					// if (decorator.getErrors().size() != 0) {
					// throw new GenericException(
					// "Please FullFill SafeHer Ledger Requriment");
					// }
					// //Charity
					// generalLedgerBean=
					// rideConverter.convertEntityToBeanForCharity(ridePaymentDistributionEntity);
					// decorator.setDataBean(generalLedgerBean);
					// ledgerManagment(decorator);
					// if (decorator.getErrors().size() != 0) {
					// throw new GenericException(
					// "Please FullFill Charity Ledger Requriment");
					// }
					//
					// send async invoice email
					iAsyncEmailService.sendInvoiceEmail(rideBean);
					sendNotificationToDriverByEndRide(
							rideBillEntity.getAppUserByAppUserDriver(),
							rideBillEntity.getInvoiceNo(), rideBillEntity
									.getRideInfoId().getRideNo());
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
					decorator.setResponseMessage("Thanks for payment");

				} else {
					decorator
							.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
					throw new GenericException("Confirm Ride Make's an Error");
				}
			} else {
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
				throw new GenericException("Ride Number is Not Correct");
			}
		}
	}

	private void sendNotificationToDriverByEndRide(
			AppUserEntity appUserByAppUserDriver, String invoiceNo,
			String rideNo) throws GenericException {
		logger.info("******Entering in sendNotificationToDriverByEndRide with invoiceNo, rideNo "
				+ invoiceNo + "," + rideNo + "," + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserByAppUserDriver);
		if (loginEntity.getKeyToken() != null) {
			if (loginEntity.getOsType() != null
					&& loginEntity.getOsType().equals("1")) {
				try {
					pushAndriod.pushAndriodDriverNotification(loginEntity
							.getKeyToken(), invoiceNo, rideNo, "", "",
							PushNotificationStatus.PassengerPaymentSuccessful
									.toString(),
							"Passenger has successfully paid your dues");
				} catch (IOException e) {
					e.printStackTrace();
					logger.info("******Exiting from sendNotificationToDriverByEndRide with Exception "
							+ e.getMessage() + " ********");
					throw new GenericException(e);
				}
			} else {
				try {
					pushIOS.pushIOSDriver(loginEntity.getKeyToken(),
							loginEntity.getIsDev(), invoiceNo, rideNo, "", "",
							PushNotificationStatus.PassengerPaymentSuccessful
									.toString(),
							"Passenger has successfully paid your dues",
							loginEntity.getFcmToken());
				} catch (GenericException e) {
					e.printStackTrace();
					logger.info("******Exiting from sendNotificationToDriverByEndRide with Exception "
							+ e.getMessage() + " ********");
					throw new GenericException(e);
				}
			}
		}

	}

	@Override
	public void getkeyToken(SafeHerDecorator decorator) throws GenericException {
		PushIOS pushIOS = new PushIOS();
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in getkeyToken with RideBean " + rideBean
				+ " ******");
		rideConverter.vaildateIdentity(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserPassenger()));
			if (appUser != null) {
				rideBean.setClientToken(BrainTree.getToken());

				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				decorator.setResponseMessage("Passenger Client Token");
				decorator.getResponseMap().put("data", rideBean);
			} else {
				throw new GenericException("App User Is Not Exixt");
			}
		} else {
			throw new GenericException("Please First Confirm Your Identity");
		}
	}

	@Override
	public void rideBillingTransaction(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in rideBillingTransaction with RideBean "
				+ rideBean + " ******");
		rideConverter.vaildateTransaction(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(rideBean.getAppUserByAppUserPassenger()));
			if (appUser != null) {

				String message = BrainTree.transaction(
						rideBean.getTotalAmount(), rideBean.getNonce());
				if (message.equalsIgnoreCase("success")) {
					// sendNotificationToDriver(rideBean.getAppUserByAppUserDriver());
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
					decorator.setResponseMessage("Transaction proceeded");
				} else {
					throw new GenericException(message);
				}
			} else {
				throw new GenericException("App User Is Not Exixt");
			}
		} else {
			throw new GenericException("Please Provide following information");
		}
	}

	private void sendNotificationToDriver(String driverId, String message,
			String notificationType) throws GenericException {
		logger.info("******Entering in sendNotificationToDriver with driverId, message, notificationType "
				+ driverId + "," + message + "," + notificationType + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		AppUserEntity appUserEntity = new AppUserEntity();
		appUserEntity.setAppUserId(new Integer(driverId));
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity != null) {
			appUserEntity = loginEntity.getAppUser();
			if (loginEntity != null) {
				if (loginEntity.getKeyToken() == null) {
					throw new GenericException(
							"Some error occured please try again latter");
				} else {
					try {
						if (loginEntity.getIsDev() != null) {
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod.pushAndriodDriverNotification(
										loginEntity.getKeyToken(), message, "0",
										"", "", notificationType, message);
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSDriver(
										loginEntity.getKeyToken(),
										loginEntity.getIsDev(), message, "0",
										"", "", notificationType, message,
										loginEntity.getFcmToken());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new GenericException(
								"Some error occured please try again latter");
					}
				}
			}
		} else {
			throw new GenericException("Driver don't exists");
		}
	}

	@Override
	public void midEndRide(SafeHerDecorator decorator) throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		RideEntity rideEntity= null;
		PreRideEntity preRideEntity =null;
		RideBillEntity rideBillEntity = null;
		logger.info("******Entering in midEndRide with RideBean " + rideBean
				+ " ******");
		if (rideBean.getAppUserByAppUserDriver() != null) {
			if(rideBean.getPreRideId()!=null){
//				 preRideEntity = appUserDao.get(PreRideEntity.class,
//						Integer.valueOf(rideBean.getPreRideId()));
//				if (preRideEntity == null) {
//					throw new GenericException("Pre Ride Id is not Valid");
//				}else{
//					rideEntity = appUserDao.findByOject(RideEntity.class, "preRide",preRideEntity);
//				}

				rideEntity = (RideEntity) appUserDao
						.findByIdParamCommon("RideEntity",
								"preRide.preRideId", Integer.valueOf(rideBean.getPreRideId()));
				
				////////////////////////////////////////
				if(rideEntity==null){
					throw new GenericException("Ride Not Valid");
				} else {
					if (rideEntity.getIsCompleted() != null	&& rideEntity.getIsCompleted().equals("1")) {
						rideBillEntity = appUserDao.findByOject(RideBillEntity.class, "rideInfoId",rideEntity);
						if(rideBillEntity ==null){
							throw new GenericException("Pre Ride Id is not Valid");
						}
						
						//Notification data 
						//isRideComplete = 1
						rideBean.setRideNo(	rideEntity.getRideNo());
						rideBean.setIsGifted(	rideEntity.getIsGifted());
						rideBean.setInvoiceNo(	rideBillEntity.getInvoiceNo());
					//	rideBean.setDiscount(rideBillEntity.ge)
						rideBean.setPassengerType("0");
						Double totalPromotionAmount = 0.0;
						UserPromotionEntity userPromotionEntity = appUserDao.
								findUserPromotionOnPayment(rideBillEntity.getAppUserByAppUserPassenger().getAppUserId(), 
										new Integer(PromotionTypeEnum.Promotion.getValue()+""));
						if(userPromotionEntity == null){
							userPromotionEntity = appUserDao.
									findUserPromotionOnPayment(rideBillEntity.getAppUserByAppUserPassenger().getAppUserId(), 
											new Integer(PromotionTypeEnum.Referral.getValue()+""));
						}
						
						if(userPromotionEntity != null && 
								userPromotionEntity.getPromotionInfo() != null){
							if(StringUtil.isNotEmpty(userPromotionEntity.getPromotionInfo().
									getIsPartialUse()) && userPromotionEntity.getPromotionInfo().
										getIsPartialUse().equals("1")){
								totalPromotionAmount = new Double(userPromotionEntity.
										getPromotionInfo().getPartialValue());
							}else{
								totalPromotionAmount = new Double(userPromotionEntity.getTotalValue());
							}
						}
						rideBean.setDiscount(totalPromotionAmount.toString());
						//passengerType
						CreditCardInfoBean creditCardInfoBean = 
								getCreditCardInfoAndClientTokenForPayment(rideBillEntity.getAppUserByAppUserPassenger());
						if(creditCardInfoBean != null && 
								creditCardInfoBean.getDefaultType().equalsIgnoreCase("C")){
							if(rideBillEntity.getBtTransactionNo()!=null){
								rideBean.setPaymentDone("1");
							}else{
								rideBean.setPaymentDone("-1");
							}
						} else if (creditCardInfoBean != null
								&& creditCardInfoBean.getDefaultType()
										.equalsIgnoreCase("P")) {
							rideBean.setPaymentDone("0");
						}
						rideBean.setLastShare(null);
						rideBean.setLastDuration(null);
						rideBean.setIsCompleted("1");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
						decorator.setResponseMessage("Mid Ride Request ");
					} else {
					sendNotificationToDriver(rideBean.getAppUserByAppUserDriver(),
							"Passenger wants to end ride", "PassengerMidEndRide");
					//this is for socket message to receiver start
					rideBean.setNotificationType(PushNotificationStatus.PassengerMidEndRide
							.getValue());
						rideBean.setNotificationMessage("End ride request has been sent to the driver");
						decorator.setDataBean(rideBean);
						rideBean.setIsCompleted("0");
						rideBean.setLastShare(null);
						rideBean.setLastDuration(null);
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
						decorator.setResponseMessage("Mid Ride Request ");
					}
				}
			}
			
			
			//this is for socket message to receiver end
			
			//saving rideTracking
			try {	
				String[] requestNO = rideBean.getRequestNo().split("_");
				if(requestNO != null && requestNO.length > 0){
					RideTrackingBean rideTrackingBean = new RideTrackingBean();
					rideTrackingBean.setRequestNo(requestNO[0]);
					rideTrackingBean.setStatus(StatusEnum.DriverArrival.getValue()+"");
					rideTrackingBean.setState(ProcessStateAndActionEnum.END_RIDE.getValue()+"");
					rideTrackingBean.setAction(ProcessStateAndActionEnum.End_Ride_By_Passenger.getValue()+"");
					rideTrackingBean.setIsComplete("0");
					asyncServiceImpl.saveRideTracking(rideTrackingBean);	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			throw new GenericException("Please Provide driver id");
		}
	}

	@Override
	public void ledgerManagment(SafeHerDecorator decorator)
			throws GenericException {
		GeneralLedgerBean generalLedgerBean = (GeneralLedgerBean) decorator
				.getDataBean();
		logger.info("******Entering in ledgerManagment with GeneralLedgerBean "
				+ generalLedgerBean + " ******");
		rideConverter.vaildateLedgerEnteries(decorator);
		if (decorator.getErrors().size() == 0) {
			LedgerSummaryEntity ledgerSummaryEntity = prepareLedgerSummary(generalLedgerBean);
			if (ledgerSummaryEntity == null) {
				throw new GenericException("Ledger Summary Error");
			}

			GeneralLedgerEntity generalLedgerEntity = prepareGeneralLedger(
					generalLedgerBean, ledgerSummaryEntity);
			if (generalLedgerEntity == null) {
				throw new GenericException("General Ledger Error");
			}
		}
	}

	private GeneralLedgerEntity prepareGeneralLedger(GeneralLedgerBean generalLedgerBean,LedgerSummaryEntity ledgerSummaryEntity) throws GenericException {
		logger.info("******Entering in prepareGeneralLedger with GeneralLedgerBean "+ generalLedgerBean + " ******");
		GeneralLedgerEntity generalLedgerEntity = new GeneralLedgerEntity();
		RidePaymnetDistributionEntity ridePaymentDistribution = appUserDao.get(RidePaymnetDistributionEntity.class,Integer.valueOf(generalLedgerBean.getRidePaymnetDistribution()));
		RideBillEntity rideBill = appUserDao.get(RideBillEntity.class,Integer.valueOf(generalLedgerBean.getRideBillId()));

		if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Driver.toString())) {
			generalLedgerEntity.setAmount(Double.valueOf(ridePaymentDistribution.getDriverAmount()));
			generalLedgerEntity.setInvoiceDate(DateUtil.now());
			generalLedgerEntity.setStatusByStatusTrans(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
			generalLedgerEntity.setStatusByStatusPayment(appUserDao.get(StatusEntity.class, StatusEnum.Due.getValue()));
			generalLedgerEntity.setRidePaymnetDistribution(ridePaymentDistribution);
			generalLedgerEntity.setRideBill(rideBill);
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Account.getValue()));
			generalLedgerEntity.setPaymentDate(rideBill.getPaymentTime());
			generalLedgerEntity.setLedgerSummaryEntity(ledgerSummaryEntity);
			generalLedgerEntity.setLedgerEntryType(appUserDao.get(LedgerEntryTypeEntity.class,LedgerEntryTypeEnum.BillPayment.getValue()));
			generalLedgerEntity.setIsCr("1");
		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.SafeHer.toString())) {
			generalLedgerEntity.setAmount(Double.valueOf(ridePaymentDistribution.getCompanyAmount()));
			generalLedgerEntity.setInvoiceDate(DateUtil.now());
			generalLedgerEntity.setStatusByStatusTrans(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
			generalLedgerEntity.setStatusByStatusPayment(appUserDao.get(StatusEntity.class, StatusEnum.Due.getValue()));
			generalLedgerEntity.setRidePaymnetDistribution(ridePaymentDistribution);
			generalLedgerEntity.setRideBill(rideBill);
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Account.getValue()));
			generalLedgerEntity.setPaymentDate(rideBill.getPaymentTime());
			generalLedgerEntity.setLedgerSummaryEntity(ledgerSummaryEntity);
			generalLedgerEntity.setLedgerEntryType(appUserDao.get(LedgerEntryTypeEntity.class,LedgerEntryTypeEnum.BillPayment.getValue()));
			generalLedgerEntity.setIsCr("1");
		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Passenger.toString())) {
			generalLedgerEntity.setAmount(Double.valueOf(ridePaymentDistribution.getCharityAmount()	+ ridePaymentDistribution.getCharityAmount())+ Double.valueOf(ridePaymentDistribution.getDriverAmount()));
			generalLedgerEntity.setInvoiceDate(DateUtil.now());
			generalLedgerEntity.setStatusByStatusTrans(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
			generalLedgerEntity.setStatusByStatusPayment(appUserDao.get(StatusEntity.class, StatusEnum.Deduct.getValue()));
			generalLedgerEntity.setRidePaymnetDistribution(ridePaymentDistribution);
			generalLedgerEntity.setRideBill(rideBill);
			generalLedgerEntity.setPaymentDate(rideBill.getPaymentTime());
			generalLedgerEntity.setLedgerSummaryEntity(ledgerSummaryEntity);
			generalLedgerEntity.setLedgerEntryType(appUserDao.get(LedgerEntryTypeEntity.class,LedgerEntryTypeEnum.BillPayment.getValue()));
			generalLedgerEntity.setIsCr("0");

		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Charity.toString())) {
			generalLedgerEntity.setAmount(Double.valueOf(ridePaymentDistribution.getCharityAmount()));
			generalLedgerEntity.setInvoiceDate(DateUtil.now());
			generalLedgerEntity.setStatusByStatusTrans(appUserDao.get(StatusEntity.class, StatusEnum.DistributionDue.getValue()));
			generalLedgerEntity.setStatusByStatusPayment(appUserDao.get(StatusEntity.class, StatusEnum.Due.getValue()));
			generalLedgerEntity.setRidePaymnetDistribution(ridePaymentDistribution);
			generalLedgerEntity.setRideBill(rideBill);
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Account.getValue()));
			generalLedgerEntity.setPaymentDate(rideBill.getPaymentTime());
			generalLedgerEntity.setLedgerSummaryEntity(ledgerSummaryEntity);
			generalLedgerEntity.setLedgerEntryType(appUserDao.get(LedgerEntryTypeEntity.class,LedgerEntryTypeEnum.BillPayment.getValue()));
			generalLedgerEntity.setIsCr("1");
		}

		switch (generalLedgerBean.getPaymentModeId()) {
		case "Account":
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Account.getValue()));
		case "Creditcard":
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Creditcard.getValue()));
		case "PayPal":
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.PayPal.getValue()));
		default:
			generalLedgerEntity.setPaymentMode(appUserDao.get(PaymentModeEntity.class,PaymentModeEnum.Account.getValue()));
		}

		if (appUserDao.save(generalLedgerEntity)) {
			return generalLedgerEntity;
		} else {
			throw new GenericException("General Ledger Error");
		}

	}

	private LedgerSummaryEntity prepareLedgerSummary(GeneralLedgerBean generalLedgerBean) throws GenericException {
		LedgerSummaryEntity ledgerSummaryEntity = new LedgerSummaryEntity();
		if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Driver.toString())) {
			ledgerSummaryEntity.setAppUser(appUserDao.get(AppUserEntity.class,Integer.valueOf(generalLedgerBean.getAppUserId())));
			ledgerSummaryEntity.setLastTrasactionDate(DateUtil.now());
			ledgerSummaryEntity.setIsCr("1");
			ledgerSummaryEntity.setLedgerOwnerTypeEntity(appUserDao.get(LedgerOwnerTypeEntity.class,LedgerOwnerTypeEnum.Driver.getValue()));
			// ledgerSummaryEntity.setIsLastTrsactionCr(isLastTrsactionCr)
			ledgerSummaryEntity.setTotalAmount(Double.valueOf(generalLedgerBean.getAmount()));
		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.SafeHer.toString())) {
			ledgerSummaryEntity.setIsCr("1");
			ledgerSummaryEntity.setLastTrasactionDate(DateUtil.now());
			ledgerSummaryEntity.setLedgerOwnerTypeEntity(appUserDao.get(LedgerOwnerTypeEntity.class,LedgerOwnerTypeEnum.Driver.getValue()));
			ledgerSummaryEntity.setSafeHerAccountInfoEntity(appUserDao.get(SafeHerAccountInfoEntity.class, 1));
			// ledgerSummaryEntity.setIsLastTrsactionCr(isLastTrsactionCr)
			ledgerSummaryEntity.setTotalAmount(Double.valueOf(generalLedgerBean.getAmount()));
		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Passenger.toString())) {
			ledgerSummaryEntity.setAppUser(appUserDao.get(AppUserEntity.class,Integer.valueOf(generalLedgerBean.getAppUserId())));
			ledgerSummaryEntity.setIsCr("0");
			ledgerSummaryEntity.setLastTrasactionDate(DateUtil.now());
			ledgerSummaryEntity.setLedgerOwnerTypeEntity(appUserDao.get(LedgerOwnerTypeEntity.class,LedgerOwnerTypeEnum.Driver.getValue()));
			// ledgerSummaryEntity.setIsLastTrsactionCr(isLastTrsactionCr)
			ledgerSummaryEntity.setTotalAmount(Double.valueOf(generalLedgerBean.getAmount()));
		} else if (generalLedgerBean.getLedgerOwnerType().equals(LedgerOwnerTypeEnum.Charity.toString())) {
			ledgerSummaryEntity.setIsCr("1");
			ledgerSummaryEntity.setLastTrasactionDate(DateUtil.now());
			ledgerSummaryEntity.setLedgerOwnerTypeEntity(appUserDao.get(LedgerOwnerTypeEntity.class,LedgerOwnerTypeEnum.Driver.getValue()));
			// ledgerSummaryEntity.setIsLastTrsactionCr("1");
			ledgerSummaryEntity.setCharities(appUserDao.get(CharitiesEntity.class,Integer.valueOf(generalLedgerBean.getCharityId())));
			ledgerSummaryEntity.setTotalAmount(Double.valueOf(generalLedgerBean.getAmount()));
		}

		if (appUserDao.save(ledgerSummaryEntity)) {
			return ledgerSummaryEntity;
		} else {
			throw new GenericException("Summary Ledger Error");
		}

	}

	@Override
	public String getUserRatingByAppUser(String appUserId) {
		logger.info("Entering in getUserRatingByAppUser with appUserId "
				+ appUserId + " ********");
		AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
				Integer.valueOf(appUserId));
		UserRatingEntity userRating = appUserDao.findByOject(
				UserRatingEntity.class, "appUser", appUser);

		if (userRating == null || userRating.getCurrentValue() == null) {
			return "0";
		}
		return userRating.getCurrentValue().toString();
	}

	@Override
	public void addGitedRide(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		logger.info("Gifted Ride Criteria..........  ");
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		logger.info("Entering in addGitedRide with RideCriteriaBean " + bean
				+ " ********");
		RideCriteriaEntity criteriaEntity = null;
		rideConverter.validateGiftedRideCriteria(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("User Information Error");
			}
			if (bean.getGiftType().equals("1")) {
				criteriaEntity = new RideCriteriaEntity();
				criteriaEntity.setIsGift("1");
			} else {
				criteriaEntity = rideConverter
						.prepareGiftedRideCriteriaPerameters(bean);
			}

			if (rideDao.save(criteriaEntity)) {
				if (criteriaEntity.getIsGift().equals("1")) {
//					StatusEntity giftRideStatus = rideDao.get(
//							StatusEntity.class, StatusEnum.NoReceive.getValue());
					StatusEntity giftRideStatus = new StatusEntity();
					giftRideStatus.setStatusId(StatusEnum.NoReceive.getValue());
					
					GiftTypeEntity giftTypeEntity = rideDao.get(
							GiftTypeEntity.class,
							Integer.valueOf(bean.getGiftType().trim()));
					if (giftTypeEntity == null) {
						throw new GenericException("Gift Type is invalid");
					}
					RideGiftEntity rideGiftEntity = rideConverter
							.prepareGiftRide(criteriaEntity,
									bean.getReciverNum(), giftRideStatus,
									bean.getEmail());
					rideGiftEntity.setGiftType(giftTypeEntity);
					rideGiftEntity.setAppUserBySenderUserId(appUser);
					if (bean.getGiftType().trim().equals("1")) {
						rideGiftEntity.setConsumeAmount(Double.valueOf(bean
								.getConsumeAmount()));
					}
					if (rideDao.save(rideGiftEntity)) {
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
						decorator
								.setResponseMessage("Gifted Ride Successfully Donated");
					}
				}

			} else {
				throw new GenericException("Gifted Ride Error");
			}
		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of Gifted Ride");
		}
	}

	@Override
	public void getGiftedRides(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("Get Gifted Rides.........  ");
		List<RideGiftEntity> rideGiftedEntity = null;
		List<RideCriteriaBean> rideGiftedbean = null;
		List<RideGiftEntity> sendGiftedEntity = null;
		List<RideGiftEntity> consumeGiftedRide = null;
		GiftTypeEntity GiftType = null;
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		logger.info("Entering in getGiftedRides with RideCriteriaBean " + bean
				+ " ********");
		rideConverter.validateGiftedRides(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("User Information Error");
			}
//			StatusEntity giftRideStatus = rideDao.get(StatusEntity.class,
//					StatusEnum.Receive.getValue());
//			StatusEntity giftNotReciveRideStatus = rideDao.get(
//					StatusEntity.class, StatusEnum.NoReceive.getValue());
//			StatusEntity giftExpireStatus = rideDao.get(StatusEntity.class,
//					StatusEnum.Expire.getValue());
//			StatusEntity giftProcessStatus = rideDao.get(StatusEntity.class,
//					StatusEnum.InProcess.getValue());
//			StatusEntity giftConsumeStatus = rideDao.get(StatusEntity.class,
//					StatusEnum.Consumed.getValue());
			
				StatusEntity giftRideStatus = new StatusEntity();
				giftRideStatus.setStatusId(StatusEnum.Receive.getValue());
				   
			   StatusEntity giftNotReciveRideStatus = new StatusEntity();
			   giftNotReciveRideStatus.setStatusId(StatusEnum.NoReceive.getValue());

			   StatusEntity giftExpireStatus = new StatusEntity();
			   giftExpireStatus.setStatusId(StatusEnum.Expire.getValue());

			   StatusEntity giftProcessStatus = new StatusEntity();
			   giftProcessStatus.setStatusId(StatusEnum.InProcess.getValue());

			   StatusEntity giftConsumeStatus = new StatusEntity();
			   giftConsumeStatus.setStatusId(	StatusEnum.Consumed.getValue());
			
			
			
			GiftType = rideDao.get(GiftTypeEntity.class,
					Integer.valueOf(bean.getGiftType()));
			rideGiftedEntity = rideDao.findListByOjectforGift(
					RideGiftEntity.class, "email", "reciverNum", appUser
							.getPersonDetail().getPrimaryEmail(), appUser
							.getPersonDetail().getPrimaryCell(), Integer
							.valueOf(bean.getFirstIndex()), Integer
							.valueOf(bean.getMaxResult()), GiftType,
					giftRideStatus, giftNotReciveRideStatus);
			if (CollectionUtil.isNotEmpty(rideGiftedEntity)) {

				rideDao.updateGiftRideByEmail(appUser, appUser
						.getPersonDetail().getPrimaryCell(), GiftType);
				rideDao.updateGiftRideByNumber(appUser, appUser
						.getPersonDetail().getPrimaryCell(), GiftType);
			}

			consumeGiftedRide = rideDao.findListByOjectforGiftConsume(
					RideGiftEntity.class, "email", "reciverNum", appUser
							.getPersonDetail().getPrimaryEmail(), appUser
							.getPersonDetail().getPrimaryCell(), Integer
							.valueOf(bean.getFirstIndex()), Integer
							.valueOf(bean.getMaxResult()), GiftType,
					giftProcessStatus, giftConsumeStatus);

			rideGiftedbean = convertEntitytoRideGiftedBean(rideGiftedEntity);

			sendGiftedEntity = rideDao.findListByOject("appUserBySenderUserId",
					appUser, Integer.valueOf(bean.getFirstIndex()),
					Integer.valueOf(bean.getMaxResult()), GiftType);

			bean.setConsumeGifteRides(convertEntitytoRideGiftedBean(consumeGiftedRide));
			bean.setAvalibleGiftedRides(rideGiftedbean);
			bean.setRideCriteriaWrapper(null);
			bean.setRideSearchWrapper(null);

			bean.setGivenGiftedRides(convertEntitytoRideGiftedGivenBean(sendGiftedEntity));

			for (RideGiftEntity rideGiftRide : rideGiftedEntity) {
				if (giftNotReciveRideStatus.equals(rideGiftRide.getStatus())) {
					rideGiftRide.setStatus(giftRideStatus);
				}
				rideDao.update(rideGiftRide);
			}
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("Gift Ride Information");
			logger.info("Get Gifted Rides......... ....... .... End ");
		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of Get Gifted Ride");
		}
	}
	private List<RideCriteriaBean> convertEntitytoRideGiftedGivenBean(
			List<RideGiftEntity> rideGiftedEntity) {
			List<RideCriteriaBean> rideGiftedBeanList = new ArrayList<RideCriteriaBean>();
			RideCriteriaBean rideCriteriaBean = null;
			for (RideGiftEntity rideGiftRide : rideGiftedEntity) {
				rideCriteriaBean = new RideCriteriaBean();
				rideCriteriaBean.setReciverNum(rideGiftRide.getReciverNum());
				rideCriteriaBean.setEmail(rideGiftRide.getEmail());
				rideCriteriaBean.setGiftId(rideGiftRide.getRideGiftId().toString());
				//rideCriteriaBean.setGiftTime(rideGiftRide.getGiftTime().toString());
				rideCriteriaBean.setGiftTime(DateUtil.convertDateToString(rideGiftRide.getGiftTime(), "yyyy-MM-dd HH:mm:ss"));
				if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.NoReceive.toString())) {
					rideCriteriaBean.setGiftStatus("Not Received");
				}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Receive.toString())) {
					rideCriteriaBean.setGiftStatus("Received");
				}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.InProcess.toString())) {
					rideCriteriaBean.setGiftStatus("In Process");
				}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Consumed.toString())) {
					rideCriteriaBean.setGiftStatus("Consumed");
				}else {
					rideCriteriaBean.setGiftStatus(rideGiftRide.getStatus().getName());
				}
				
				if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Consumed.toString())) {
					rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount()+"");
				}
				rideCriteriaBean.setGiftType(rideGiftRide.getGiftType().getGiftTypeId().toString());
				rideCriteriaBean.setGiftTypeName(rideGiftRide.getGiftType().getName());
				if(rideGiftRide.getGiftType().getGiftTypeId()==1){
					rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount().toString());
				}else{
					rideCriteriaBean.setSourceAddress(rideGiftRide.getRideCriteria().getSourceAddress());
					rideCriteriaBean.setDistinatonAddress(rideGiftRide.getRideCriteria().getDistinationAddress());
					if(rideGiftRide.getConsumeAmount()!=null){
						rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount().toString());
					}
				
				}
//				rideCriteriaBean.setSenderAppUserId(rideGiftRide.getAppUserBySenderUserId().getAppUserId()+"");
//				rideCriteriaBean.setSenderFirstName(rideGiftRide.getAppUserBySenderUserId().getPerson().getFirstName());
//				rideCriteriaBean.setSenderLastName(rideGiftRide.getAppUserBySenderUserId().getPerson().getLastName());
				rideCriteriaBean.setGiftNo(rideGiftRide.getGiftNo());
			
				rideCriteriaBean.setRideSearchWrapper(null);
				rideCriteriaBean.setRideCriteriaWrapper(null);
				rideCriteriaBean.setRideCriteriaId(rideGiftRide.getRideCriteria()
						.getRideCriteriaId().toString());
//				rideCriteriaBean.setSenderAppUserId(rideGiftRide
//						.getAppUserBySenderUserId().getAppUserId().toString());
//				rideCriteriaBean.setReciverAppUserId(rideGiftRide
//						.getAppUserByReciverUserId().getAppUserId().toString());
				if(rideGiftRide.getAppUserByReciverUserId()!=null){
					rideCriteriaBean.setReciverAppUserId(rideGiftRide.getAppUserByReciverUserId().getAppUserId().toString());
					rideCriteriaBean.setReciverFirstName(rideGiftRide.getAppUserByReciverUserId().getPerson().getFirstName());
					rideCriteriaBean.setReciverLastName(rideGiftRide.getAppUserByReciverUserId().getPerson().getLastName());
					rideCriteriaBean.setUserImagePath(getPicUrlByAppUser(rideGiftRide.getAppUserByReciverUserId()));
				}
				
				
				
				
				rideGiftedBeanList.add(rideCriteriaBean);

			}
			return rideGiftedBeanList;
		}
	
	private  List<RideCriteriaBean> convertEntitytoRideGiftedBean(
			List<RideGiftEntity> rideGiftedEntity) {
		List<RideCriteriaBean> rideGiftedBeanList = new ArrayList<RideCriteriaBean>();
		RideCriteriaBean rideCriteriaBean = null;
		for (RideGiftEntity rideGiftRide : rideGiftedEntity) {
			rideCriteriaBean = new RideCriteriaBean();
			rideCriteriaBean.setReciverNum(rideGiftRide.getReciverNum());
			rideCriteriaBean.setEmail(rideGiftRide.getEmail());
			rideCriteriaBean.setGiftId(rideGiftRide.getRideGiftId().toString());
			//rideCriteriaBean.setGiftTime(rideGiftRide.getGiftTime().toString());
			rideCriteriaBean.setGiftTime(DateUtil.convertDateToString(rideGiftRide.getGiftTime(), "yyyy-MM-dd HH:mm:ss"));
			
			if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.NoReceive.toString())) {
				rideCriteriaBean.setGiftStatus("Not Received");
			}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Receive.toString())) {
				rideCriteriaBean.setGiftStatus("Received");
			}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.InProcess.toString())) {
				rideCriteriaBean.setGiftStatus("In Process");
			}else if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Consumed.toString())) {
				rideCriteriaBean.setGiftStatus("Consumed");
			}else {
				rideCriteriaBean.setGiftStatus(rideGiftRide.getStatus().getName().trim());
			}
			
			if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.Consumed.toString())) {
				rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount()+"");
			}
			rideCriteriaBean.setGiftType(rideGiftRide.getGiftType().getGiftTypeId().toString());
			rideCriteriaBean.setGiftTypeName(rideGiftRide.getGiftType().getName());
			if(rideGiftRide.getGiftType().getGiftTypeId()==1){
				rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount().toString());
			}else{
				rideCriteriaBean.setSourceAddress(rideGiftRide.getRideCriteria().getSourceAddress());
				rideCriteriaBean.setDistinatonAddress(rideGiftRide.getRideCriteria().getDistinationAddress());
				
				if(rideGiftRide.getConsumeAmount()!=null){
					rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount().toString());
				}
			
			}
			rideCriteriaBean.setSenderAppUserId(rideGiftRide.getAppUserBySenderUserId().getAppUserId()+"");
			rideCriteriaBean.setSenderFirstName(rideGiftRide.getAppUserBySenderUserId().getPerson().getFirstName());
			rideCriteriaBean.setSenderLastName(rideGiftRide.getAppUserBySenderUserId().getPerson().getLastName());
			rideCriteriaBean.setGiftNo(rideGiftRide.getGiftNo());
			rideCriteriaBean.setUserImagePath(getPicUrlByAppUser(rideGiftRide.getAppUserBySenderUserId()));
			rideCriteriaBean.setRideSearchWrapper(null);
			rideCriteriaBean.setRideCriteriaWrapper(null);
			rideCriteriaBean.setRideCriteriaId(rideGiftRide.getRideCriteria()
					.getRideCriteriaId().toString());
			rideCriteriaBean.setSenderAppUserId(rideGiftRide
					.getAppUserBySenderUserId().getAppUserId().toString());
//			rideCriteriaBean.setReciverAppUserId(rideGiftRide
//					.getAppUserByReciverUserId().getAppUserId().toString());
			
			rideGiftedBeanList.add(rideCriteriaBean);

		}
		return rideGiftedBeanList;
	}
	@Override
	public void getGiftedInfo(SafeHerDecorator decorator)
			throws GenericException {
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("Entering in getGiftedInfo with RideCriteriaBean "
				+ rideCriteriaBean + " ********");
		RideGiftEntity gifteRideEntity = null;
		RideCriteriaEntity rideCriteriaEntity = null;
		rideConverter.validateGiftedRideInfo(decorator);
		if (decorator.getErrors().size() == 0) {
//			gifteRideEntity = rideDao.findBy(RideGiftEntity.class, "giftNo",
//					rideCriteriaBean.getGiftNo().trim());
			
			gifteRideEntity = rideDao.findByGiftNo(rideCriteriaBean.getGiftNo().trim());
			if (gifteRideEntity == null) {
				throw new GenericException("No gifted Ride against Gift No!");
			}
			rideCriteriaBean = rideConverter
					.convertEntityToBean(gifteRideEntity);
			rideCriteriaBean.setUserImagePath(getPicUrlByAppUser(gifteRideEntity.getAppUserBySenderUserId()));
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			decorator.getResponseMap().put("data", rideCriteriaBean);
			decorator.setResponseMessage("Gift Ride Information");
			logger.info("Get Gifted Ride......... ....... .... End ");
		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of Get Gifte Ride");
		}

	}

	@Override
	public RideCriteriaBean saveGiftRideSearchV2(SafeHerDecorator decorator)
			throws DataAccessException, Exception {
		logger.info("Update Gift Ride Criteria  Method called  ");
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		logger.info("Entering in saveGiftRideSearchV2 with RideCriteriaBean "
				+ bean + " ********");
		RideGiftEntity rideGiftEntity = null;
		boolean rideCriteriaFlg = false;
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getReciverAppUserId()));
			if (appUser == null) {
				throw new GenericException("Reciver User Information Error");
			}
			// Prepare Parameter for Gift Ride Criteria
			rideGiftEntity = rideDao.findBy(RideGiftEntity.class, "giftNo",
					bean.getGiftNo());
			// prepareRideCriteriaPerametersV2(bean);
			if (rideGiftEntity != null) {
				throw new GenericException("No gifted Ride against Gift No!");
			}

			if (rideGiftEntity.getGiftType().getGiftTypeId() == 1) {
				RideCriteriaEntity criteriaEntity = rideConverter
						.prepareGiftedRideCriteriaPerameters(bean);
				rideDao.save(criteriaEntity);
			}
			rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,
					StatusEnum.InProcess.getValue()));
			rideGiftEntity.setConsumeDate(DateUtil.now());
			RideCriteriaEntity citeriaEntity = rideGiftEntity.getRideCriteria();
			citeriaEntity = prepareGiftedRideCriteria(bean, citeriaEntity);
			citeriaEntity.setAppUser(appUser);
			try {
				// Redix Save Criteria
				criteriaRepository.saveCriteria(citeriaEntity);
				// db Update
				rideCriteriaFlg = rideDao.update(citeriaEntity);
				rideDao.update(rideGiftEntity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new GenericRuntimeException(e.getMessage());
			}
			if (rideCriteriaFlg) {
				bean.setRideCriteriaId(citeriaEntity.getRideCriteriaId()
						.toString());
				// Ride Search Parameter
				RideSearchEntity rideSearch = prepareGiftRideSearchParamsV2(
						bean, citeriaEntity);
				// db save
				rideSearch.setSearchTime(rideGiftEntity.getGiftTime());
				if (!rideDao.save(rideSearch)) {
					throw new GenericException(
							"Gift Ride Search Information Error");
				}
				bean.setRideSearchId(rideSearch.getRideSearchId().toString());
				bean.setRideCriteriaWrapper(citeriaEntity);
				bean.setRideSearchWrapper(rideSearch);
				bean.setSourceLat(citeriaEntity.getSourceLat());
				bean.setSourceLong(citeriaEntity.getSourceLong());

			} else {
				throw new GenericException(
						"Gift Ride Criteria Information Error");
			}
			logger.info("Save Gift Ride Criteria/Search  End ");
		}
		return bean;
	}

	private RideSearchEntity prepareGiftRideSearchParamsV2(
			RideCriteriaBean bean, RideCriteriaEntity citeriaEntity) {
		logger.info("Entering in prepareGiftRideSearchParamsV2 with RideCriteriaBean "
				+ bean + " ********");

		RideSearchEntity rideSearch = new RideSearchEntity();

		rideSearch.setSearchFromLat(citeriaEntity.getSourceLat());
		rideSearch.setSearchFromLong(citeriaEntity.getSourceLong());
		rideSearch.setRideCategory(rideDao.get(RideCategoryEntity.class,
				Integer.valueOf(bean.getRideCategoryId())));
		rideSearch.setRequestNo(bean.getRequestNo());
		rideSearch.setRideCriteria(citeriaEntity);

		return rideSearch;
	}

	private RideCriteriaEntity prepareGiftedRideCriteria(RideCriteriaBean bean,
			RideCriteriaEntity citeriaEntity) {
		logger.info("Entering in prepareGiftedRideCriteria with RideCriteriaBean "
				+ bean + " ********");

		if (bean.getDistinatonLat() != null) {
			citeriaEntity.setDestinationLat(bean.getDistinatonLat());
		}
		if (bean.getDistinatonLong() != null) {
			citeriaEntity.setDestinationLong(bean.getDistinatonLong());
		}
		if (bean.getNoChild() != null) {
			citeriaEntity.setNoChild(Short.valueOf(bean.getNoChild()));
		}
		if (bean.getNoPassenger() != null) {
			citeriaEntity.setNoPassenger(Short.valueOf(bean.getNoPassenger()));
		}
		if (bean.getNoSeats() != null) {
			citeriaEntity.setNoSeats(Short.valueOf(bean.getNoSeats()));
		}
		if (bean.getIsShared() != null) {
			citeriaEntity.setIsShared(bean.getIsShared());
		}
		if (bean.getIsFav() != null) {
			citeriaEntity.setIsFav(bean.getIsFav());
		}
		if (bean.getRideTypeId() != null) {
			if (bean.getRideTypeId().equals("1")) {
				citeriaEntity.setIsShared("1");
			} else {
				citeriaEntity.setIsShared("0");
			}
			citeriaEntity.setRideTypeId(Integer.valueOf(bean.getRideTypeId()));
		}

		return citeriaEntity;
	}

	@Override
	public RideBean getInoviceInfo(RideBean bean) throws GenericException {

		return bean;
	}

	@Override
	public void refreshApplication(SafeHerDecorator decorator)
			throws GenericException {
		RideBean bean = (RideBean) decorator.getDataBean();
		rideConverter.validateIsRefresh(decorator);
		RideEntity rideEntity = null;
		RideBillEntity rideBillEntity = null;
		PreRideEntity preRideEntity = null;
		logger.info("Refresh called with Request no " + bean.getRequestNo());
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = rideDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserByAppUserDriver()));
			if (appUser == null) {
				throw new GenericException("Please Enter Valid Information");
			}

			if (bean.getRequestNo() != null) {
				if (bean.getRideNo() != null) {
					rideEntity = appUserDao.findByParameter(RideEntity.class,
							"rideNo", bean.getRideNo());
					if (rideEntity != null) {
						if (rideEntity.getIsCompleted() == null) {
							bean.setIsCompleted("N");
						} else if (rideEntity.getIsCompleted() != null) {
							if (rideEntity.getIsCompleted().equals("1")) {
								bean.setIsCompleted("C");
								rideBillEntity = appUserDao.findByOject(
										RideBillEntity.class, "rideInfoId",
										rideEntity);
								if (rideBillEntity == null) {
									bean.setIsBillComplete("RBN");
								} else {
									if (rideBillEntity.getBtTransactionNo() != null) {
										bean.setIsBillComplete("RBC");
									} else {
										bean.setIsBillComplete("RBT");
									}
								}
							} else {
								bean.setIsCompleted("NC");
							}
						}

					} else {
						decorator.setReturnCode(ReturnStatusEnum.FAILURE
								.getValue());
						throw new GenericException("Ride Number is Not Correct");
					}
				} else {
					// request No Checking
					preRideEntity = appUserDao.findByParameter(
							PreRideEntity.class, "requestNo",
							bean.getRequestNo());
					if (preRideEntity != null) {
						if (preRideEntity.getStatus().getStatusId() == 8) {
							bean.setIsPreRideComplete("PC");
						} else {
							bean.setIsPreRideComplete("PN");
						}
					} else {
						bean.setIsPreRideComplete("N");
					}

				}
			} else if (bean.getRideNo() != null) {

				if (rideEntity.getIsCompleted() == null) {
					bean.setIsCompleted("N");
				} else if (rideEntity.getIsCompleted() != null) {
					if (rideEntity.getIsCompleted().equals("1")) {
						bean.setIsCompleted("C");
						rideBillEntity = appUserDao.findByOject(
								RideBillEntity.class, "rideInfoId", rideEntity);
						if (rideBillEntity == null) {
							bean.setIsBillComplete("RBN");
						} else {
							if (rideBillEntity.getBtTransactionNo() != null) {
								bean.setIsBillComplete("RBC");
							} else {
								bean.setIsBillComplete("RBT");
							}
						}
					} else {
						bean.setIsCompleted("NC");
					}
				}
			}
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("Refresh Information");
			logger.info("Refresh Call by Driver......... ....... .... End ");

		} else {
			throw new GenericException(
					"Please Fullfill the Requriment of isRefresh");
		}
	}

	@Override
	public void giftEndRideV3(SafeHerDecorator decorator)
			throws GenericException {
		//call giftEndRideAsyncCall method in AsyncServiceImpl.java to perform async things
		
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in giftEndRideV3 with RideBean " + rideBean
				+ " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		RideDetailEntity rideDetail =null;
		RideBillDistribution rideBillDistribution=null;
		rideConverter.validateGiftEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}

				RideGiftEntity rideGiftEntity = appUserDao.findByOject(
						RideGiftEntity.class, "rideCriteria",
						rideEntity.getRideCriteria());
				if (rideGiftEntity == null) {
					throw new GenericException("No Gift Ride Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil
							.parseTimestampFromFormats(rideBean
									.getRideEndTime()));
					rideDetail = appUserDao.findByOject(RideDetailEntity.class, "rideEntityId",rideEntity);
					if(rideDetail==null){throw new GenericException("Ride Number is Not Correct");}
					rideDetail.setDestinationAddress(rideBean.getEndDistinationAddress());
					rideDetail.setDestinationLat(rideBean.getEndDestinationLat());
					rideDetail.setDestinationLong(rideBean.getEndDestinationLong());
					appUserDao.saveOrUpdate(rideDetail);
					if (appUserDao.update(rideEntity)) {
						rideBillEntity = preparePaymentDetail(rideBean,
								rideEntity);
						rideBean = convertEntityToBean(rideBillEntity, rideBean);
						AppUserEntity donarPassenger = rideBillEntity
								.getAppUserByAppUserDonar();
						if (donarPassenger != null) {
							Double rideAmount = new Double(
									rideBean.getTotalAmount());
							Double totalDiscount = 0.0;
							String message = "";
							boolean isTransactionRequired = true;
							boolean isRideAmountPartial=false;
							Double totalConsumeAmount =Double.valueOf(rideBean.getTotalAmount());
							Double passengerAmount=0.0;
							
									
							rideGiftEntity.setConsumeAmount(Double.valueOf(rideBean.getTotalAmount()));
							rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,StatusEnum.Consumed.getValue()));
							rideGiftEntity.setConsumeDate(DateUtil.now());
							appUserDao.saveOrUpdate(rideGiftEntity);
							// Payment to cut down from Donar Passenger
							CreditCardInfoBean creditCardInfoBean = getCreditCardInfoAndClientTokenForPayment(donarPassenger);
							CreditCardInfoBean creditCardInfoBeanPartial = null;
							if (totalConsumeAmount > rideGiftEntity.getConsumeAmount()) {
								creditCardInfoBeanPartial = getCreditCardInfoAndClientTokenForPayment(rideBillEntity.getAppUserByAppUserPassenger());
								if (creditCardInfoBeanPartial != null
										&& creditCardInfoBeanPartial.getDefaultType().equalsIgnoreCase("C")&& StringUtil.isNotEmpty(creditCardInfoBeanPartial.getBtCustomer())) {
									passengerAmount=	totalConsumeAmount- rideGiftEntity.getConsumeAmount();
									if (isTransactionRequired) {
										// makeTransaction
									message = BrainTree.transactionUsingCustomer(passengerAmount + "",creditCardInfoBeanPartial.getBtCustomer());
										if (!message.contains("Error")) {
											
											StatusEntity status_payment = new StatusEntity();
											status_payment.setStatusId(StatusEnum.Deduct.getValue());
											PaymentModeEntity paymentMode = new PaymentModeEntity();
											AppUserTypeEntity appUserType =new AppUserTypeEntity();
											appUserType.setAppUserTypeId(2);
											paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
											rideBillDistribution =new RideBillDistribution();
											rideBillDistribution.setAmount(passengerAmount);
											rideBillDistribution.setAppUser(rideBillEntity.getAppUserByAppUserPassenger());
											rideBillDistribution.setBtTransactionNo(message);
											rideBillDistribution.setAppUserType(appUserType);
											rideBillDistribution.setPaymentMode(paymentMode);
											rideBillDistribution.setPaymentTime(DateUtil.now());
											rideBillDistribution.setStatus(status_payment);
											rideBillDistribution.setRideBill(rideBillEntity);
											
											appUserDao.saveOrUpdate(rideBillDistribution);
											
										} else {
											throw new GenericException(message);
										}
									}
								}else  if (creditCardInfoBean != null && creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {
								
									StatusEntity status_payment = new StatusEntity();
									status_payment.setStatusId(StatusEnum.Deduct.getValue());
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									AppUserTypeEntity appUserType =new AppUserTypeEntity();
									appUserType.setAppUserTypeId(2);
									paymentMode.setPaymentModeId(PaymentModeEnum.PayPal.getValue());
									rideBillDistribution =new RideBillDistribution();
									rideBillDistribution.setAmount(passengerAmount);
									rideBillDistribution.setAppUser(rideBillEntity.getAppUserByAppUserPassenger());
									rideBillDistribution.setBtTransactionNo(message);
									rideBillDistribution.setAppUserType(appUserType);
									rideBillDistribution.setPaymentMode(paymentMode);
									rideBillDistribution.setPaymentTime(DateUtil.now());
									rideBillDistribution.setStatus(status_payment);
									rideBillDistribution.setRideBill(rideBillEntity);
									appUserDao.saveOrUpdate(rideBillDistribution);
								}
							}
							
							
							if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType().equalsIgnoreCase("C") && 
									StringUtil.isNotEmpty(creditCardInfoBean.getBtCustomer())) {
								if (isTransactionRequired) {
									// makeTransaction
								message = BrainTree.transactionUsingCustomer(rideAmount + "",creditCardInfoBean.getBtCustomer());
																
								}
								if (!message.contains("Error")) {
									// transcation Id generate here
									System.out.println("Transcation ID    :"+ message);
									rideBillEntity.setBtTransactionNo(message);
									rideBillEntity.setPaymentTime(DateUtil.now());
									rideBillEntity.setTotalAmount(Double.valueOf(rideBean.getTotalAmount()));
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
									rideBillEntity.setPaymentMode(paymentMode);
									StatusEntity status_payment = new StatusEntity();
									status_payment.setStatusId(StatusEnum.Deduct.getValue());
									rideBillEntity.setStatus(status_payment);
									// rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);

									if (appUserDao.update(rideBillEntity)) {
										RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
										ridePaymentDistributionEntity.setIsDue("1");
										ridePaymentDistributionEntity.setRideBill(rideBillEntity);
										ridePaymentDistributionEntity.setStatus(appUserDao.get(StatusEntity.class,StatusEnum.DistributionDue.getValue()));
										// Will Change On Customer Demand
										// Distribution here
										Double charityPayment = Double.valueOf(rideBean.getTotalAmount()) * 2 / 100;
										Double driverPayment = (Double.valueOf(rideBean.getTotalAmount())) * 20 / 100;
										System.out.println((Double.valueOf(rideBean.getTotalAmount())));
										Double companyPayment = (Double.valueOf(rideBean.getTotalAmount())) * 78 / 100;

										rideBean.setCharityAmount(charityPayment+ "");
										rideBean.setDriverAmount(driverPayment+ "");
										rideBean.setCompanyAmount(companyPayment+ "");

										ridePaymentDistributionEntity.setCharityAmount(charityPayment);
										ridePaymentDistributionEntity.setCompanyAmount(companyPayment);
										ridePaymentDistributionEntity.setDriverAmount(driverPayment);
										appUserDao.saveOrUpdate(ridePaymentDistributionEntity);

										// saving setRideBill
//										if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
//											userPromotionUseEntity.setRideBill(rideBillEntity);
//										}

										///////////////////////////////////////////Ledger Managment //////////////////////////////////////////
										
										//			asyncServiceImpl.ledgerManagmentForSafHer(decorator, ridePaymentDistributionEntity);
									
										//////////////////////////////////////////////////Ledger Managment Ended Her /////////////////////////////
										
								
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserPassenger(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount + "", "1_1_P");
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserDonar(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount+"", "1_1_D");
									
										decorator.setResponseMessage("Passenger has successfully paid your dues");

									} else {
										decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
										throw new GenericException("Confirm Ride Make's an Error");
									}

								} else {
									throw new GenericException(message);
								}
								// send async invoice email
								iAsyncEmailService.sendInvoiceEmail(rideBean);

							} else if (creditCardInfoBean != null && 
									creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {

								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(), 
										rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(), totalDiscount+ "", "0");
								decorator.setResponseMessage("Invoice Information");
							}
							// saving userPromotionUse
//							if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
//								userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
//								userPromotionUseEntity.setRideBill(rideBillEntity);
//								userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
//								userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
//								appUserDao.saveOrUpdate(userPromotionUseEntity);
//							}
						}
						
						
						/////////////////////Driver Promotion Settelment /////////////////
					//	asyncServiceImpl.driverPromotionSettelmentMethod(decorator,rideBillEntity);
						///////////////////Driver Promotion Settelment ////////////////////////
						
						
						// set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(AppUserEntity.class,	new Integer(rideBean.getAppUserByAppUserDriver()));
						if(driver != null){
							ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
							//TODO: Check
							/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								}
								/*activeDriverLocationEntity = appUserDao.findByOject(
										ActiveDriverLocationEntity.class, "appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao.saveOrUpdate(activeDriverLocationEntity);
								}*/
							}
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException("End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception "+ ex.getMessage() + " ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
		
	}

	@Override
	public void giftEndRideV2(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRideV2 with RideBean " + rideBean
				+ " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		RideDetailEntity rideDetail =null;
		RideBillDistribution rideBillDistribution=null;
		rideConverter.validateGiftEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}

				RideGiftEntity rideGiftEntity = appUserDao.findByOject(
						RideGiftEntity.class, "rideCriteria",
						rideEntity.getRideCriteria());
				if (rideGiftEntity == null) {
					throw new GenericException("No Gift Ride Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil
							.parseTimestampFromFormats(rideBean
									.getRideEndTime()));
					rideDetail = appUserDao.findByOject(RideDetailEntity.class, "rideEntityId",rideEntity);
					if(rideDetail==null){throw new GenericException("Ride Number is Not Correct");}
					rideDetail.setDestinationAddress(rideBean.getEndDistinationAddress());
					rideDetail.setDestinationLat(rideBean.getEndDestinationLat());
					rideDetail.setDestinationLong(rideBean.getEndDestinationLong());
					appUserDao.saveOrUpdate(rideDetail);
					if (appUserDao.update(rideEntity)) {
						// PrePare Payment Detail About Company Driver And
						// Charity

						rideBillEntity = preparePaymentDetail(rideBean,
								rideEntity);

						rideBean = convertEntityToBean(rideBillEntity, rideBean);

						// AppUserEntity passenger =
						// appUserDao.findById(AppUserEntity.class,
						// new
						// Integer(rideBean.getAppUserByAppUserPassenger()));
						// //
						AppUserEntity donarPassenger = rideBillEntity
								.getAppUserByAppUserDonar();
						if (donarPassenger != null) {
						//	UserPromotionUseEntity userPromotionUseEntity = new UserPromotionUseEntity();
							Double rideAmount = new Double(
									rideBean.getTotalAmount());
					//		Double totalPromotionAmount = 0.0;
							Double totalDiscount = 0.0;
						//	Integer countValue=0;
							String message = "";
							boolean isTransactionRequired = true;
							boolean isRideAmountPartial=false;
							Double totalConsumeAmount =Double.valueOf(rideBean.getTotalAmount());
							Double passengerAmount=0.0;
							
									
							rideGiftEntity.setConsumeAmount(Double.valueOf(rideBean.getTotalAmount()));
							rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,StatusEnum.Consumed.getValue()));
							rideGiftEntity.setConsumeDate(DateUtil.now());
							appUserDao.saveOrUpdate(rideGiftEntity);
							// Payment to cut down from Donar Passenger
							CreditCardInfoBean creditCardInfoBean = getCreditCardInfoAndClientTokenForPayment(donarPassenger);
							CreditCardInfoBean creditCardInfoBeanPartial = null;
							if (totalConsumeAmount > rideGiftEntity.getConsumeAmount()) {
								creditCardInfoBeanPartial = getCreditCardInfoAndClientTokenForPayment(rideBillEntity.getAppUserByAppUserPassenger());
								if (creditCardInfoBeanPartial != null
										&& creditCardInfoBeanPartial.getDefaultType().equalsIgnoreCase("C")&& StringUtil.isNotEmpty(creditCardInfoBeanPartial.getBtCustomer())) {
									passengerAmount=	totalConsumeAmount- rideGiftEntity.getConsumeAmount();
									if (isTransactionRequired) {
										// makeTransaction
									message = BrainTree.transactionUsingCustomer(passengerAmount + "",creditCardInfoBeanPartial.getBtCustomer());
										if (!message.contains("Error")) {
											
											StatusEntity status_payment = new StatusEntity();
											status_payment.setStatusId(StatusEnum.Deduct.getValue());
											PaymentModeEntity paymentMode = new PaymentModeEntity();
											AppUserTypeEntity appUserType =new AppUserTypeEntity();
											appUserType.setAppUserTypeId(2);
											paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
											rideBillDistribution =new RideBillDistribution();
											rideBillDistribution.setAmount(passengerAmount);
											rideBillDistribution.setAppUser(rideBillEntity.getAppUserByAppUserPassenger());
											rideBillDistribution.setBtTransactionNo(message);
											rideBillDistribution.setAppUserType(appUserType);
											rideBillDistribution.setPaymentMode(paymentMode);
											rideBillDistribution.setPaymentTime(DateUtil.now());
											rideBillDistribution.setStatus(status_payment);
											rideBillDistribution.setRideBill(rideBillEntity);
											
											appUserDao.saveOrUpdate(rideBillDistribution);
											
										} else {
											throw new GenericException(message);
										}
									}
								}else  if (creditCardInfoBean != null && creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {
								
									StatusEntity status_payment = new StatusEntity();
									status_payment.setStatusId(StatusEnum.Deduct.getValue());
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									AppUserTypeEntity appUserType =new AppUserTypeEntity();
									appUserType.setAppUserTypeId(2);
									paymentMode.setPaymentModeId(PaymentModeEnum.PayPal.getValue());
									rideBillDistribution =new RideBillDistribution();
									rideBillDistribution.setAmount(passengerAmount);
									rideBillDistribution.setAppUser(rideBillEntity.getAppUserByAppUserPassenger());
									rideBillDistribution.setBtTransactionNo(message);
									rideBillDistribution.setAppUserType(appUserType);
									rideBillDistribution.setPaymentMode(paymentMode);
									rideBillDistribution.setPaymentTime(DateUtil.now());
									rideBillDistribution.setStatus(status_payment);
									rideBillDistribution.setRideBill(rideBillEntity);
									appUserDao.saveOrUpdate(rideBillDistribution);
								}
							}
							
							
							if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType().equalsIgnoreCase("C")&& StringUtil.isNotEmpty(creditCardInfoBean.getBtCustomer())) {
								if (isTransactionRequired) {
									// makeTransaction
								message = BrainTree.transactionUsingCustomer(rideAmount + "",creditCardInfoBean.getBtCustomer());
																
								}
								if (!message.contains("Error")) {
									// transcation Id generate here
									System.out.println("Transcation ID    :"+ message);
									rideBillEntity.setBtTransactionNo(message);
									rideBillEntity.setPaymentTime(DateUtil.now());
									rideBillEntity.setTotalAmount(Double.valueOf(rideBean.getTotalAmount()));
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
									rideBillEntity.setPaymentMode(paymentMode);
									StatusEntity status_payment = new StatusEntity();
									status_payment.setStatusId(StatusEnum.Deduct.getValue());
									rideBillEntity.setStatus(status_payment);
									// rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);

									if (appUserDao.update(rideBillEntity)) {
										RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
										ridePaymentDistributionEntity.setIsDue("1");
										ridePaymentDistributionEntity.setRideBill(rideBillEntity);
										ridePaymentDistributionEntity.setStatus(appUserDao.get(StatusEntity.class,StatusEnum.DistributionDue.getValue()));
										// Will Change On Customer Demand
										// Distribution here
										Double charityPayment = Double.valueOf(rideBean.getTotalAmount()) * 2 / 100;
										Double driverPayment = (Double.valueOf(rideBean.getTotalAmount())) * 20 / 100;
										System.out.println((Double.valueOf(rideBean.getTotalAmount())));
										Double companyPayment = (Double.valueOf(rideBean.getTotalAmount())) * 78 / 100;

										rideBean.setCharityAmount(charityPayment+ "");
										rideBean.setDriverAmount(driverPayment+ "");
										rideBean.setCompanyAmount(companyPayment+ "");

										ridePaymentDistributionEntity.setCharityAmount(charityPayment);
										ridePaymentDistributionEntity.setCompanyAmount(companyPayment);
										ridePaymentDistributionEntity.setDriverAmount(driverPayment);
										appUserDao.saveOrUpdate(ridePaymentDistributionEntity);

										// saving setRideBill
//										if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
//											userPromotionUseEntity.setRideBill(rideBillEntity);
//										}

										///////////////////////////////////////////Ledger Managment //////////////////////////////////////////
										
										//			asyncServiceImpl.ledgerManagmentForSafHer(decorator, ridePaymentDistributionEntity);
									
										//////////////////////////////////////////////////Ledger Managment Ended Her /////////////////////////////
										
								
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserPassenger(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount + "", "1_1_P");
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserDonar(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount+"", "1_1_D");
									
										decorator.setResponseMessage("Passenger has successfully paid your dues");

									} else {
										decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
										throw new GenericException("Confirm Ride Make's an Error");
									}

								} else {
									throw new GenericException(message);
								}
								// send async invoice email
								iAsyncEmailService.sendInvoiceEmail(rideBean);

							} else if (creditCardInfoBean != null && creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {

								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(),rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(), totalDiscount+ "", "0");
								decorator.setResponseMessage("Invoice Information");
							}
							// saving userPromotionUse
//							if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
//								userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
//								userPromotionUseEntity.setRideBill(rideBillEntity);
//								userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
//								userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
//								appUserDao.saveOrUpdate(userPromotionUseEntity);
//							}
						}
						
						
						/////////////////////Driver Promotion Settelment /////////////////
					//	asyncServiceImpl.driverPromotionSettelmentMethod(decorator,rideBillEntity);
						///////////////////Driver Promotion Settelment ////////////////////////
						
						
						// set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(AppUserEntity.class,	new Integer(rideBean.getAppUserByAppUserDriver()));
						if(driver != null){
							ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
							//TODO: Check
							/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								}
								/*activeDriverLocationEntity = appUserDao.findByOject(
										ActiveDriverLocationEntity.class, "appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao.saveOrUpdate(activeDriverLocationEntity);
								}*/
							}
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException("End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception "+ ex.getMessage() + " ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}

	@Override
	public void giftEndRideV3Mongo(SafeHerDecorator decorator)
			throws GenericException {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		logger.info("******Entering in endRideV2 with RideBean " + rideBean
				+ " ******");
		PostRideEntity postRdeEntity = null;
		RideBillEntity rideBillEntity = null;
		RideEntity rideEntity = null;
		rideConverter.validateGiftEndRide(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				rideEntity = appUserDao.getRide(rideBean.getRideNo());
				if (rideEntity == null) {
					throw new GenericException("Ride No Not Exist in Error!");
				}

				RideGiftEntity rideGiftEntity = appUserDao.findByOject(
						RideGiftEntity.class, "rideCriteria",
						rideEntity.getRideCriteria());
				if (rideGiftEntity == null) {
					throw new GenericException("No Gift Ride Exist in Error!");
				}
				postRdeEntity = populatePostRide(rideBean);
				if (appUserDao.save(postRdeEntity)) {
					rideEntity.setIsCompleted(rideBean.getIsCompleted());
					rideEntity.setEndTime(DateUtil.now());
					rideEntity.setRideEndTime(DateUtil
							.parseTimestampFromFormats(rideBean
									.getRideEndTime()));
					if (appUserDao.update(rideEntity)) {
						// PrePare Payment Detail About Company Driver And
						// Charity

						rideBillEntity = preparePaymentDetail(rideBean,
								rideEntity);

						rideBean = convertEntityToBean(rideBillEntity, rideBean);

						// AppUserEntity passenger =
						// appUserDao.findById(AppUserEntity.class,
						// new
						// Integer(rideBean.getAppUserByAppUserPassenger()));
						// //
						AppUserEntity donarPassenger = rideBillEntity
								.getAppUserByAppUserDonar();
						if (donarPassenger != null) {
							UserPromotionUseEntity userPromotionUseEntity = new UserPromotionUseEntity();
							Double rideAmount = new Double(
									rideBean.getTotalAmount());
							Double totalPromotionAmount = 0.0;
							Double totalDiscount = 0.0;
							Integer countValue=0;
							String message = "";
							boolean isTransactionRequired = true;

							// checking if promotion exist for this user
							UserPromotionEntity userPromotionEntity = appUserDao.findUserPromotionOnPaymentForDriver(rideBillEntity.getAppUserByAppUserDriver().getAppUserId(),new Integer(PromotionTypeEnum.Promotion.getValue() + ""));
							if (userPromotionEntity == null) {
								userPromotionEntity = appUserDao.findUserPromotionOnPaymentForDriver(rideBillEntity.getAppUserByAppUserDriver().getAppUserId(),new Integer(PromotionTypeEnum.Referral.getValue()	+ ""));
							}
							if (userPromotionEntity != null
									&& userPromotionEntity.getPromotionInfo() != null) {
								if (StringUtil.isNotEmpty(userPromotionEntity.getPromotionType().getPromotionTypeId().toString())	&& userPromotionEntity.getPromotionType().getPromotionTypeId()==1) {
									countValue = userPromotionEntity.getCountValue()+1;
									//		totalPromotionAmount = new Double(userPromotionEntity.getPromotionInfo().getPartialValue());
									userPromotionEntity.setCountValue(countValue);

								} else {
									totalPromotionAmount = new Double(userPromotionEntity.getTotalValue());
								}
								if (totalPromotionAmount > 0) {
									isTransactionRequired = true;
								}
								userPromotionEntity.setLastUsedDate(DateUtil.getCurrentTimestamp());
								appUserDao.saveOrUpdate(userPromotionEntity);
							} else {
								isTransactionRequired = true;
							}
							rideGiftEntity.setConsumeAmount(Double.valueOf(rideBean.getTotalAmount()));
							rideGiftEntity.setStatus(rideDao.get(StatusEntity.class,StatusEnum.Consumed.getValue()));
							rideGiftEntity.setConsumeDate(DateUtil.now());
							appUserDao.saveOrUpdate(rideGiftEntity);
							// Payment to cut down from Donar Passenger
							CreditCardInfoBean creditCardInfoBean = getCreditCardInfoAndClientTokenForPayment(donarPassenger);
							if (creditCardInfoBean != null
									&& creditCardInfoBean.getDefaultType().equalsIgnoreCase("C")&& StringUtil.isNotEmpty(creditCardInfoBean.getBtCustomer())) {
								if (isTransactionRequired) {
									// makeTransaction
									message = BrainTree.transactionUsingCustomer(rideAmount + "",creditCardInfoBean.getBtCustomer());
								}
								if (!message.contains("Error")) {
									// transcation Id generate here
									System.out.println("Transcation ID    :"+ message);
									rideBillEntity.setBtTransactionNo(message);
									rideBillEntity.setPaymentTime(DateUtil.now());
									rideBillEntity.setTotalAmount(Double.valueOf(rideBean.getTotalAmount()));
									PaymentModeEntity paymentMode = new PaymentModeEntity();
									paymentMode.setPaymentModeId(PaymentModeEnum.Creditcard.getValue());
									rideBillEntity.setPaymentMode(paymentMode);
									StatusEntity status_payment = new StatusEntity();
									status_payment.setStatusId(StatusEnum.Deduct.getValue());
									rideBillEntity.setStatus(status_payment);
									// rideBillEntity.setBtSettelmentStatus(btSettelmentStatus);

									if (appUserDao.update(rideBillEntity)) {
										RidePaymnetDistributionEntity ridePaymentDistributionEntity = new RidePaymnetDistributionEntity();
										ridePaymentDistributionEntity.setIsDue("1");
										ridePaymentDistributionEntity.setRideBill(rideBillEntity);
										ridePaymentDistributionEntity.setStatus(appUserDao.get(StatusEntity.class,StatusEnum.DistributionDue.getValue()));
										// Will Change On Customer Demand
										// Distribution here
										Double charityPayment = Double.valueOf(rideBean.getTotalAmount()) * 2 / 100;
										Double driverPayment = (Double.valueOf(rideBean.getTotalAmount())) * 20 / 100;
										System.out.println((Double.valueOf(rideBean.getTotalAmount())));
										Double companyPayment = (Double.valueOf(rideBean.getTotalAmount())) * 78 / 100;

										rideBean.setCharityAmount(charityPayment+ "");
										rideBean.setDriverAmount(driverPayment+ "");
										rideBean.setCompanyAmount(companyPayment+ "");

										ridePaymentDistributionEntity.setCharityAmount(charityPayment);
										ridePaymentDistributionEntity.setCompanyAmount(companyPayment);
										ridePaymentDistributionEntity.setDriverAmount(driverPayment);
										appUserDao.saveOrUpdate(ridePaymentDistributionEntity);

										// saving setRideBill
										if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
											userPromotionUseEntity.setRideBill(rideBillEntity);
										}

										// //For Driver
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForDriver(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Driver Ledger Requriment");
										// }
										// //Passenger
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForPassenger(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Passenger Ledger Requriment");
										// }
										// //SafeHer
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForSafeHer(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill SafeHer Ledger Requriment");
										// }
										// //Charity
										// generalLedgerBean=
										// rideConverter.convertEntityToBeanForCharity(ridePaymentDistributionEntity);
										// decorator.setDataBean(generalLedgerBean);
										// ledgerManagment(decorator);
										// if (decorator.getErrors().size() !=
										// 0) {
										// throw new GenericException(
										// "Please FullFill Charity Ledger Requriment");
										// }
										//
										
								
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserPassenger(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount + "", "1_1_P");
										sendNotificationToOPPonentV3ForGift(rideBillEntity.getAppUserByAppUserDonar(),	rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(),totalDiscount+"", "1_1_D");
									
										decorator.setResponseMessage("Passenger has successfully paid your dues");

									} else {
										decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
										throw new GenericException("Confirm Ride Make's an Error");
									}

								} else {
									throw new GenericException(message);
								}
								// send async invoice email
								iAsyncEmailService.sendInvoiceEmail(rideBean);

							} else if (creditCardInfoBean != null && creditCardInfoBean.getDefaultType().equalsIgnoreCase("P")) {

								sendNotificationToOPPonentV3(rideBillEntity.getAppUserByAppUserPassenger(),rideBillEntity.getInvoiceNo(),rideEntity.getRideNo(), totalDiscount+ "", "0");
								decorator.setResponseMessage("Invoice Information");
							}
							// saving userPromotionUse
							if (userPromotionEntity != null	&& userPromotionEntity.getPromotionInfo() != null) {
								userPromotionUseEntity.setUserPromotionEntity(userPromotionEntity);
								userPromotionUseEntity.setRideBill(rideBillEntity);
								userPromotionUseEntity.setUseDate(DateUtil.getCurrentTimestamp());
								userPromotionUseEntity.setUseAmount(totalDiscount.intValue());
								appUserDao.saveOrUpdate(userPromotionUseEntity);
							}
						}
						// set isBooked and isRequest 0
						AppUserEntity driver = appUserDao.findById(AppUserEntity.class,	new Integer(rideBean.getAppUserByAppUserDriver()));
						if(driver != null){
							ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
							//TODO: Check
							/*ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();*/
							if (driver.getIsDriver().equalsIgnoreCase("1")) {
								activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(driver.getAppUserId());
								if (activeDriverLocationMongoEntity != null) {
									activeDriverLocationMongoEntity.setIsRequested("0");
									activeDriverLocationMongoEntity.setIsBooked("0");
									/*appUserDao.saveOrUpdate(activeDriverLocationEntity);*/
									activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
								}
								/*activeDriverLocationEntity = appUserDao.findByOject(
										ActiveDriverLocationEntity.class, "appUser", driver);
								if (activeDriverLocationEntity != null) {
									activeDriverLocationEntity.setIsRequested("0");
									activeDriverLocationEntity.setIsBooked("0");
									appUserDao.saveOrUpdate(activeDriverLocationEntity);
								}*/
							}
						}
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						decorator.getResponseMap().put("data", rideBean);
					} else {
						throw new GenericException("End Ride information Error!");
					}
				} else {
					throw new GenericException("Post Ride information Error!");
				}
			} catch (DataAccessException ex) {
				logger.info("******Exiting from endRideV2 with Exception "+ ex.getMessage() + " ******");
				ex.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Complete End Ride Information!");
		}
	}
	@Override
	public void midRideRequest(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("Get Mid Ride Request.........  ");
		RideBean bean = (RideBean) decorator.getDataBean();
		AppUserEntity appUserForDriver = null;
		AppUserEntity appUserForPassenger = null;
		String message = "";
		String code = "";
		logger.info("Entering in MidRidRequest *********  " + bean
				+ "  *************** ********");
		rideConverter.validateMidRideRequest(decorator);
		if (decorator.getErrors().size() == 0) {

			appUserForDriver = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserByAppUserDriver()));
			if (appUserForDriver == null) {
				throw new GenericException("Driver App User Id is not Exist");
			}

			appUserForPassenger = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserByAppUserPassenger()));

			if (appUserForPassenger == null) {
				throw new GenericException("Pasenger App User Id is not Exist");
			}

			if (bean.getIsDriver().equals("0")) {
				switch (bean.getRequestCode()) {
				case "R1":
					message = MidRideRequest.R1.getValue();
					code = MidRideRequest.R1.toString();
					break;
				case "R2":
					message = MidRideRequest.R2.getValue();
					code = MidRideRequest.R3.toString();
					break;
				case "R3":
					message = MidRideRequest.R3.getValue();
					code = MidRideRequest.R3.toString();
					break;
				case "R4":
					code = MidRideRequest.R4.toString();
					message = MidRideRequest.R4.getValue();
					break;
				case "R5":
					message = MidRideRequest.R5.getValue();
					code = MidRideRequest.R5.toString();
					break;
				case "R6":
					message = MidRideRequest.R6.getValue();
					code = MidRideRequest.R6.toString();
					break;
				case "R7":
					message = MidRideRequest.R7.getValue();
					code = MidRideRequest.R7.toString();
					break;
				case "R8":
					message = MidRideRequest.R8.getValue();
					code = MidRideRequest.R8.toString();
					break;
				case "R9":
					message = MidRideRequest.R9.getValue();
					code = MidRideRequest.R9.toString();
					break;
				default:
					message = MidRideRequest.R0.getValue();
					break;
				}
				sendNotificationToDriver(appUserForDriver, code.trim(),
						message, "midRideRequest");
				decorator
						.setResponseMessage("Notification is Successfully Send to Driver");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			}

			if (bean.getIsDriver().equals("1")) {
				sendNotificationToPassenger(appUserForPassenger, message,
						"midRideRequest");
			}

		} else {
			throw new GenericException(
					"Please Complete Mid Ride Request  Information!");
		}
	}

	private void sendNotificationToDriver(AppUserEntity appUser, String Code,
			String message, String notificationType) throws GenericException {
		logger.info("******Entering in sendNotificationToDriver with driverId, message, notificationType "
				+ message + "," + notificationType + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		AppUserEntity appUserEntity = appUser;
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity != null) {
			// appUserEntity = loginEntity.getAppUser();
			if (loginEntity != null) {
				if (loginEntity.getKeyToken() == null) {
					throw new GenericException(
							"Some error occured please try again latter");
				} else {
					try {
						if (loginEntity.getIsDev() != null) {
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod.pushAndriodDriverNotification(
										loginEntity.getKeyToken(), Code, "",
										"", "", notificationType, message);
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSDriver(
										loginEntity.getKeyToken(),
										loginEntity.getIsDev(), Code, "", "",
										"", notificationType, message,
										loginEntity.getFcmToken());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new GenericException(
								"Some error occured please try again latter");
					}
				}
			}
		} else {
			throw new GenericException("Driver don't exists");
		}
	}

	private void sendNotificationToPassenger(AppUserEntity appUser,
			String message, String notificationType) throws GenericException {
		logger.info("******Entering in sendNotificationToPassenger with driverId, message, notificationType "
				+ message + "," + notificationType + " ******");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		AppUserEntity appUserEntity = appUser;
		UserLoginEntity loginEntity = appUserDao.findByOject(
				UserLoginEntity.class, "appUser", appUserEntity);
		if (loginEntity != null) {
			// appUserEntity = loginEntity.getAppUser();
			if (loginEntity != null) {
				if (loginEntity.getKeyToken() == null) {
					throw new GenericException(
							"Some error occured please try again latter");
				} else {
					try {
						if (loginEntity.getIsDev() != null) {
							if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("1")) {
								pushAndriod.pushAndriodPassengerNotification(
										loginEntity.getKeyToken(), message, "",
										"", "", notificationType, message);
							} else if (loginEntity.getOsType() != null
									&& loginEntity.getOsType().equals("0")) {
								pushIOS.pushIOSPassenger(
										loginEntity.getKeyToken(),
										loginEntity.getIsDev(), message, "",
										"", "", notificationType, message,
										loginEntity.getFcmToken());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new GenericException(
								"Some error occured please try again latter");
					}
				}
			}
		} else {
			throw new GenericException("Driver don't exists");
		}
	}

	@Override
	public void rejectGiftRide(SafeHerDecorator decorator)
			throws GenericException {

		RideBean bean = (RideBean) decorator.getDataBean();
		AppUserEntity appUser =null;
		logger.info("******Entering in rejectGiftRide  with AppUserBean "
				+ bean + "******");
		try {
			if (StringUtil.isEmpty(bean.getAppUserByAppUserPassenger())) {
				throw new GenericException("Please provide App User Id");
			}
			appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserByAppUserPassenger()));
			if (appUser == null) {
				throw new GenericException("Driver App User Id is not Exist");
			}
			if (StringUtil.isNotEmpty(bean.getRideCriteriaId())) {
				RideCriteriaEntity criteriaEntity = appUserDao.findById(
						RideCriteriaEntity.class,
						new Integer(bean.getRideCriteriaId()));
				if (criteriaEntity == null) {
					throw new GenericException("No ride found");
				}
				appUserDao.rejectGiftRide(
						new Integer(bean.getRideCriteriaId()),
						StatusEnum.Rejected.getValue(),appUser);
				decorator.setResponseMessage("Ride rejected successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException("Please provide rideCriteriaId");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}

	@Override
	public RideEntity getRideInfo(String rideNo) throws GenericException {
		return appUserDao.getRide(rideNo);
	}

	@Override
	public void inActiveGiftRides() throws GenericException {
		appUserDao.inactiveGiftRides();	
	}

	@Override
	public void midRideDistinationRequest(SafeHerDecorator decorator)
			throws GenericException {
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("**************Entering in midRideDistinationRequest with RideCriteriaBean " + rideCriteria +" *********");
		rideConverter.validateMidRideDistinationRequest(decorator);		
		if (decorator.getErrors().size() == 0) {
			if (rideCriteria.getRideNo() == null) {

			} else {

			}
		} else {
			throw new GenericRuntimeException("Please Fullfill the Requriment of Mid Ride Distination Request");
		}
		
	}
}
