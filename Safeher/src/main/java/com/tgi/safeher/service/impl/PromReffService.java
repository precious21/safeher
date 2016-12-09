package com.tgi.safeher.service.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.AppUserTypeEnum;
import com.tgi.safeher.common.enumeration.PromotionTypeEnum;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserTypeEntity;
import com.tgi.safeher.entity.PromotionCodesEntity;
import com.tgi.safeher.entity.PromotionInfoEntity;
import com.tgi.safeher.entity.PromotionOfferToEntity;
import com.tgi.safeher.entity.PromotionType;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.entity.UserPromotionUseEntity;
import com.tgi.safeher.entity.Status;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.IPromReffService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.service.validator.SafeHerCommonValidator;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class PromReffService implements IPromReffService {
	private static final Logger logger = Logger
			.getLogger(PromReffService.class);

	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private SafeHerCommonValidator safeHerCommonValidator;
	@Autowired
	private IAsyncEmailService iAsyncEmailService;	
	@Autowired
	private RideConverter rideConverter;


	@Override
	public void generatePromReffCode(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		logger.info("******Entering in generatePromReffCode  with AppUserBean "
				+ bean + "******");
		safeHerCommonValidator.validateBeforeGeneratingCode(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				bean.setCodeValue(generateCode(bean));
				//saveIntoDb
				saveCodeIntoDb(bean);
				decorator.getResponseMap().put("data", bean);
				decorator.setResponseMessage("Code generated successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException("Code generation failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}
	
	private void saveCodeIntoDb(PromAndReffBean bean){
		PromotionCodesEntity entity = new PromotionCodesEntity();
		PromotionInfoEntity infoEntity = new PromotionInfoEntity();
		PromotionType promotionType = new PromotionType();
		AppUserTypeEntity appUserTypeEntity = new AppUserTypeEntity();

		if(bean.getIsDriver().equals("1")){
			appUserTypeEntity.setAppUserTypeId(
					AppUserTypeEnum.Driver.getValue());	
		}else{
			appUserTypeEntity.setAppUserTypeId(
					AppUserTypeEnum.Passenger.getValue());
		}
		
		infoEntity.setIsSingle(bean.getIsSingle());
		infoEntity.setDurationInDays(bean.getDurationInDays());
		infoEntity.setStartDate(DateUtil.
				parseTimestampFromFormats(bean.getStartDate()));
		infoEntity.setExpiryDate(DateUtil.
				parseTimestampFromFormats(bean.getExpiryDate()));
		infoEntity.setIsActive("1");
		infoEntity.setAmountValue(new Integer(bean.getAmount()));
		infoEntity.setIsPercentage("0");
		infoEntity.setAppUserType(appUserTypeEntity);
		infoEntity.setMaxUseCount(new Integer(bean.getMaxUseCount()));
		infoEntity.setCountValue(bean.getCountValue());
		infoEntity.setIsCount(bean.getIsCount());
		infoEntity.setPromotionDescription(bean.getPromDescription());
		infoEntity.setIsPartialUse(bean.getIsPartialUse());
		if(StringUtil.isNotEmpty(bean.getPartialValue())){
			infoEntity.setPartialValue(new Integer(bean.getPartialValue()));
		}
		
		if(bean.getIsPromotion().equals("0")){
			promotionType.setPromotionTypeId(
					PromotionTypeEnum.Referral.getValue());
			//inActivePrevReferral
			appUserDao.inActivePrevReferral(appUserTypeEntity.getAppUserTypeId(), 
					new Integer(PromotionTypeEnum.Referral.getValue()+""));
		}else{
			promotionType.setPromotionTypeId(
					PromotionTypeEnum.Promotion.getValue());
		}
		infoEntity.setPromotionType(promotionType);
		appUserDao.saveOrUpdate(infoEntity);

		if(bean.getIsPromotion().equals("1")){
			entity.setCodeValue(bean.getCodeValue());
			entity.setIsUsed("0");
			entity.setUsedCount(0);
			entity.setPromotionInfoEntity(infoEntity);
			appUserDao.saveOrUpdate(entity);	
		}
//		bean.setPromotionCodeId(entity.getPromotionCodesId()+"");
	}

	private String generateCode(PromAndReffBean bean){
		
		String deciderFlag = "";
		
		if(bean.getIsPromotion().equals("1")){
			deciderFlag = "PRODP";
		}else{
			deciderFlag = "REFDP";
		}
		return generateSecureRando("SH"+deciderFlag+bean.getAppUserId()+
				bean.getIsDriver()+System.currentTimeMillis());
	}
	
	private String generateSecureRando(String pattern){
		SecureRandom rnd = new SecureRandom();
		StringBuilder code = new StringBuilder(10);
		for (int i = 0; i < 10; i++)
			code.append(pattern.charAt(rnd.nextInt(pattern.length())));
		return code.toString().toUpperCase();
	}

	@Override
	public void userPromotion(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		PromotionInfoEntity promotion=null;
		UserPromotionEntity userPromotionEntity= null;
		UserPromotionEntity multipleActiveCheck=null;
		Integer usedAmount=0;
		Integer consumeCount=0;
		PromAndReffBean promoBean =null;
		logger.info("******Entering in Promotion USe  with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validatePromotionBeforeConsume(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				AppUserEntity appUser=appUserDao.get(AppUserEntity.class,Integer.valueOf(bean.getAppUserId()));
				if(appUser==null){
					throw new GenericException(
							"Please check Your App User");
				}
				multipleActiveCheck = appUserDao.findByUserPromotionisActive(appUser);
				if (multipleActiveCheck != null) {
					throw new GenericException(
							"You have Already Consume Promotion! Thanks");
				}
				if (bean.getIsDriver().equals("1")) {
					// Promotion Info
					
					//check promotion not use again
					promotion = appUserDao.get(PromotionInfoEntity.class,
							Integer.valueOf(bean.getPromotionInfoId()));
					if (promotion == null) {
						throw new GenericException(
								"Please Enter Valid Promotion Info Id");
					}
					if (promotion.getPromotionType().getPromotionTypeId() == 2) {
						throw new GenericException(
								"Please Ensure Your Promotion Type");
					}
					if (promotion.getAppUserType().getAppUserTypeId() == 2) {
						throw new GenericException(
								"Please Ensure Your AppUserType");
					}
					
					if (promotion.getStartDate() != null) {

						if (DateUtil.now().after(promotion.getExpiryDate())) {
							throw new GenericException(
									"Sorry !This Promotion is Expire");
						}
						userPromotionEntity = prepareUserPromotionForDriver(
								promotion, appUser);
						userPromotionEntity.setLastUsedDate(DateUtil.now());

					} else {
						if (promotion.getDurationInDays() != null) {
							userPromotionEntity = prepareUserPromotionForDriverWithDuration(
									promotion, appUser);
							promotion.setStartDate(userPromotionEntity
									.getUseStartDate());
							appUserDao.update(promotion);
							userPromotionEntity.setLastUsedDate(DateUtil.now());
						} else {
							throw new GenericException(
									"This Promotion is InComplete Please Contact with Offical");
						}
					}
							
					if (!appUserDao.saveOrUpdate(userPromotionEntity)) {
						throw new GenericException(
								"User Promotion Saving Error");
					}

				} else if (bean.getIsDriver().equals("0")) {
					PromotionCodesEntity promotionCode = appUserDao
							.findByParameter(PromotionCodesEntity.class,
									"codeValue", bean.getCodeValue().trim());
					if (promotionCode == null) {
						throw new GenericException(
								"Please Enter the Valid Promotion Code");
					}else{
						if(appUser.equals(promotionCode.getAppUser())){
							throw new GenericException(
									"You are not allow to use this Code");
						}
					}

					promotion = promotionCode.getPromotionInfoEntity();
					if (promotion == null) {
						throw new GenericException("Please Ensure Your Code");
					}	
					if (promotion.getPromotionType().getPromotionTypeId() == 2) {
						throw new GenericException(
								"Please Ensure Your Promotion Type");
					}
					if (promotion.getAppUserType().getAppUserTypeId() == 1) {
						throw new GenericException(
								"Please Ensure Your AppUserType");
					}
					
					userPromotionEntity = appUserDao.findByUserPromotionByMultipleConsume(
							appUser,promotionCode);
					if(userPromotionEntity!=null){
						throw new GenericException("U have Already Consume This Code Please Try Another Code");
					}
					
					
					userPromotionEntity = appUserDao.findPromotionByCodes(
							promotionCode, appUser);
					if (userPromotionEntity == null) {

					} else {
						if (userPromotionEntity.getTotalUsedValue() != null) {
							usedAmount = userPromotionEntity
									.getTotalUsedValue();
						}
					}

					if (promotion.getIsSingle().equals("1")) {
						// For Single Use Promotion
						if (promotionCode.getIsUsed() != null
								&& promotionCode.getIsUsed().equals("1")) {
							throw new GenericException(
									"Sorry !This Promotion is Already Consume");
						}

						if (promotion.getStartDate() != null) {
							// If Promotion has a Start and End Date
							if (DateUtil.now().after(promotion.getExpiryDate())) {
								throw new GenericException(
										"Sorry !This Promotion is Expire");
							}
							if (userPromotionEntity == null) {
								userPromotionEntity = prepareUserPromotionEntity(
										promotion, appUser, promotionCode);
							}

							if (promotion.getIsPartialUse()!=null && promotion.getIsPartialUse().equals("1")) {
								usedAmount = usedAmount	+ Integer.valueOf(bean.getPartialUseValue());
								userPromotionEntity.setTotalUsedValue(usedAmount);
							} else if (promotion.getIsPartialUse()==null || bean.getIsPartial().equals("0")) {
								userPromotionEntity.setTotalUsedValue(promotion.getAmountValue());
								userPromotionEntity.setIsCompleted("1");
							}
							userPromotionEntity.setLastUsedDate(DateUtil.now());

						} else {
							// If Promotion has Days Duration
							if (promotion.getDurationInDays() != null) {
								if (userPromotionEntity == null) {
									userPromotionEntity = prepareUserPromotionEntityHasDuration(
											promotion, appUser, promotionCode);
									promotion.setStartDate(userPromotionEntity.getUseStartDate());
									appUserDao.update(promotion);
									if (bean.getIsPartial().equals("1")) {
										usedAmount = usedAmount	+ Integer.valueOf(bean.getPartialUseValue());
										userPromotionEntity.setTotalUsedValue(usedAmount);
									} else if (bean.getIsPartial().equals("0")) {
										userPromotionEntity.setTotalUsedValue(promotion.getAmountValue());
									}
								}else{
									if(!DateUtil.now().after(userPromotionEntity.getUseExpiryDate())){
										throw new GenericException("Sorry !This Promotion is Expire");
									}
									if(userPromotionEntity.getTotalUsedValue() == userPromotionEntity.getTotalValue()){
										throw new GenericException(
												"Sorry !This Promotion Amount is Already Consumed");
									} else {
										if (promotion.getIsPartialUse()!=null && promotion.getIsPartialUse().equals("1")) {
											usedAmount = usedAmount	+ Integer.valueOf(bean.getPartialUseValue());
											userPromotionEntity.setTotalUsedValue(usedAmount);
										} else if (promotion.getIsPartialUse()==null || bean.getIsPartial().equals("0")) {
											userPromotionEntity.setTotalUsedValue(promotion.getAmountValue());
											userPromotionEntity.setIsCompleted("1");

										}
									}
								}

							}
						}

						userPromotionEntity.setIsActive("1");
						userPromotionEntity.setLastUsedDate(DateUtil.now());
						if (appUserDao.saveOrUpdate(userPromotionEntity)) {
							// userPromotionUseEntity =
							// prepareUserPromotinUseEntity(userPromotionEntity,bean);
							// appUserDao.save(userPromotionUseEntity);
							promotionCode.setIsUsed("1");
							appUserDao.update(promotionCode);
						}

					} else if (promotion.getIsSingle().equals("0")) {
						// For MultiPle Use 
						if (DateUtil.now().after(promotion.getExpiryDate())) {
							throw new GenericException(
									"Sorry !This Promotion is Expire");
						}
						if (promotion.getMaxUseCount()!=null) {
							if (promotion.getMaxUseCount() == promotionCode.getUsedCount()) {
								throw new GenericException(
										"Sorry !This Promotion has a Maximum Count");
							}
						}

						if (userPromotionEntity == null) {
							userPromotionEntity = prepareUserPromotionEntity(
									promotion, appUser, promotionCode);
						}
						consumeCount = Integer.valueOf(promotionCode
								.getUsedCount());
						consumeCount = consumeCount + 1;
						promotionCode.setUsedCount(consumeCount);
						if (promotion.getIsPartialUse()!=null && promotion.getIsPartialUse().equals("1")) {
							usedAmount = usedAmount	+ Integer.valueOf(promotion.getPartialValue());
							userPromotionEntity.setTotalUsedValue(0);
						} else if (promotion.getIsPartialUse()==null || bean.getIsPartial().equals("0")) {
							userPromotionEntity.setTotalUsedValue(0);
							userPromotionEntity.setIsCompleted("1");
						}
						userPromotionEntity.setIsActive("1");
						userPromotionEntity.setLastUsedDate(DateUtil.now());
						if (appUserDao.saveOrUpdate(userPromotionEntity)) {
							//	userPromotionUseEntity = prepareUserPromotinUseEntity(userPromotionEntity,bean);
							//	appUserDao.save(userPromotionUseEntity);
							
							appUserDao.update(promotionCode);
						}

					}

				}
				promoBean=	rideConverter.convertPromotionToBean(promotion);
				decorator.getResponseMap().put("PromotionInfo",promoBean);
				decorator.setResponseMessage("Promotion Successfully Availed");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException(
						"Please Complete the Information About Promotion");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}

	private UserPromotionEntity prepareUserPromotionForDriverWithDuration(
			PromotionInfoEntity promotion, AppUserEntity appUser) {
		UserPromotionEntity	userPromotionEntity =new UserPromotionEntity();
		userPromotionEntity.setUseStartDate(DateUtil.now());
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.setTime(DateUtil.now());
		expiryDate.set(Calendar.HOUR_OF_DAY, Integer
				.valueOf(promotion.getDurationInDays()));
		Date ConvertDate = expiryDate.getTime();
		userPromotionEntity.setUseExpiryDate(new java.sql.Timestamp(
				ConvertDate.getTime()));
		userPromotionEntity.setTotalValue(promotion.getAmountValue());
		userPromotionEntity.setPromotionInfo(promotion);
		userPromotionEntity.setPromotionType(promotion.getPromotionType());
		userPromotionEntity.setTotalUsedValue(0);
		if(promotion.getIsCount()!=null && promotion.getIsCount().equals("1")){
			userPromotionEntity.setCountValue(Integer.valueOf(promotion.getCountValue()));
			userPromotionEntity.setUseCountValue(1);
		}
		userPromotionEntity.setAppUser(appUser);
		userPromotionEntity.setIsActive("1");
		return userPromotionEntity;
	}

	private UserPromotionEntity prepareUserPromotionForDriver(
			PromotionInfoEntity promotion, AppUserEntity appUser) {
		UserPromotionEntity	userPromotionEntity =new UserPromotionEntity();
		userPromotionEntity.setUseStartDate(DateUtil.now());
		userPromotionEntity.setUseExpiryDate(promotion.getExpiryDate());
		userPromotionEntity.setTotalValue(promotion.getAmountValue());
		userPromotionEntity.setPromotionInfo(promotion);
		userPromotionEntity.setPromotionType(promotion.getPromotionType());
		userPromotionEntity.setAppUser(appUser);
		userPromotionEntity.setTotalUsedValue(0);
		if(promotion.getIsCount()!=null && promotion.getIsCount().equals("1")){
		userPromotionEntity.setCountValue(Integer.valueOf(promotion.getCountValue().trim()));
		userPromotionEntity.setUseCountValue(1);
		}
		userPromotionEntity.setIsActive("1");
		return userPromotionEntity;
	}

	private UserPromotionEntity prepareUserPromotionEntityHasDuration(
			PromotionInfoEntity promotion, AppUserEntity appUser,
			PromotionCodesEntity promotionCode) {
		UserPromotionEntity	userPromotionEntity =new UserPromotionEntity();
		userPromotionEntity.setUseStartDate(DateUtil.now());
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.setTime(DateUtil.now());
		expiryDate.set(Calendar.HOUR_OF_DAY, Integer
				.valueOf(promotion.getDurationInDays()));
		Date ConvertDate = expiryDate.getTime();
		userPromotionEntity.setUseExpiryDate(new java.sql.Timestamp(
				ConvertDate.getTime()));
		userPromotionEntity.setTotalValue(promotion.getAmountValue());
		userPromotionEntity.setPromotionCodesEntity(promotionCode);
		userPromotionEntity.setTotalUsedValue(0);
		userPromotionEntity.setPromotionType(promotion.getPromotionType());
		userPromotionEntity.setPromotionInfo(promotion);
		userPromotionEntity.setAppUser(appUser);
		return userPromotionEntity;
	}

	private UserPromotionEntity prepareUserPromotionEntity(PromotionInfoEntity promotion,
			AppUserEntity appUser, PromotionCodesEntity promotionCode) {
		UserPromotionEntity	userPromotionEntity =new UserPromotionEntity();
		userPromotionEntity.setUseStartDate(DateUtil.now());
//		Calendar expiryDate = Calendar.getInstance();
//		expiryDate.setTime(DateUtil.now());
//		expiryDate.set(Calendar.HOUR_OF_DAY, Integer
//				.valueOf(promotion.getDurationInDays()));
//		Date ConvertDate = expiryDate.getTime();
		userPromotionEntity.setUseExpiryDate(promotion.getExpiryDate());
		userPromotionEntity.setTotalValue(promotion.getAmountValue());
		userPromotionEntity.setTotalUsedValue(0);
		userPromotionEntity.setPromotionCodesEntity(promotionCode);
		userPromotionEntity.setPromotionType(promotion.getPromotionType());
		userPromotionEntity.setPromotionInfo(promotion);
		userPromotionEntity.setAppUser(appUser);
		return userPromotionEntity;
	}

	@Override
	public void getPromotions(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
	//	List<PromotionInfoEntity> promotion=null;
		List<PromAndReffBean> promotionBeanList=null;
		List<PromAndReffBean> promotionBeanConsumeList=null;
		List<UserPromotionEntity> promotionUserList=null;
		PromAndReffBean ListBean=null;
		logger.info("******Entering in getPromotions with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validateGetpromotionValidate(decorator);
		try {
			List<Object[]> listOfPromotions=null;
		
			if (decorator.getErrors().size() == 0) {
				AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException(
							"Please Enter the valid App User Id");
				}
				if (bean.getIsDriver().equals("1")) {
					listOfPromotions = appUserDao.findPromotionsByDriver();
				}
				//System.out.println(listOfPromotions.size());
				promotionBeanList = rideConverter
						.convertObjectToPromoBean(listOfPromotions);
				//Add a list for Consume Promotion by Driver
				//promotionBeanConsumeList = 
				promotionUserList=	appUserDao.findConsumePromotion(appUser);
				promotionBeanConsumeList=	rideConverter.convertEntityListToBeanList(promotionUserList);
				bean.setPromotionList(promotionBeanList);
				bean.setConsumePromotionList(promotionBeanConsumeList);
				decorator.getResponseMap().put("DriverPromotionList", bean);
				decorator.setResponseMessage("Promotion List");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException(
						"Please Complete the Information About Get Promotion");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}

	}

	@Override
	public void activeUserPromotion(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		UserPromotionEntity userPromotionEntity= null;
		PromAndReffBean promoBean =null;
		logger.info("******Entering in activeUserPromotion USe  with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validatePromotionActive(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please check Your App User");
				}
				userPromotionEntity =appUserDao.findByUserPromotionisActive(appUser);
				if(userPromotionEntity!=null){
					promoBean=	rideConverter.convertUserPromotionToBean(userPromotionEntity);
					decorator.getResponseMap().put("PromotionInfo",promoBean);
					decorator.setResponseMessage("Promotion Successfully Availed");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				}else{
					throw new GenericException(
							"You have no Active Promotion's this Time");
				}
				
			} else {
				throw new GenericException(
						"Please Complete the Information About Get Promotion");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
		
	}
	
	
	

	@Override
	public void safeHerApiPromReffCodeGeneration(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		logger.info("******Entering in safeHerApiPromReffCodeGeneration  with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validateBeforeGeneratingCode2(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				if(bean.getIsPromotion().equals("1")){
					bean.setCodeValue(generateCode(bean));
				}
				//saveIntoDb
				saveCodeIntoDb(bean);
				decorator.getResponseMap().put("data", bean);
				decorator.setResponseMessage("Code generated successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException("Code generation failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}

	@Override
	public void generateReferralCode(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		logger.info("******Entering in generateReferralCode  with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validateBeforeGenerationgReferralCode(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				bean.setCodeValue(generateCode(bean));
				//saveIntoDb
				String result = saveReferralCodeIntoDb(bean);
				if(result != null && result.length() > 0){
					throw new GenericException(result);
				}
				decorator.getResponseMap().put("data", bean);
				decorator.setResponseMessage("Code generated successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException("Code generation failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}
	
	private String saveReferralCodeIntoDb(PromAndReffBean bean){
		PromotionCodesEntity entity = new PromotionCodesEntity();
		AppUserEntity reffAppUser = new AppUserEntity();
		String promotionType = "";
		String appUserType = "";
		if(bean.getIsPromotion().equals("0")){
			promotionType = PromotionTypeEnum.Referral.getValue()+"";
		}
		if(bean.getIsDriver().equals("1")){
			appUserType = AppUserTypeEnum.Driver.getValue()+"";	
		}else{
			appUserType = AppUserTypeEnum.Passenger.getValue()+"";
		}
		
		PromotionInfoEntity infoEntity = appUserDao.findReferral(
				new Integer(promotionType), new Integer(appUserType));
		if(infoEntity == null){
			return "No referral promotion found";
		}
		reffAppUser = appUserDao.findById(AppUserEntity.class,
				new Integer(bean.getAppUserId()));
		if(reffAppUser != null){
			entity.setAppUser(reffAppUser);
		}
		entity.setCodeValue(bean.getCodeValue());
		entity.setIsUsed("0");
		entity.setUsedCount(0);
		entity.setPromotionInfoEntity(infoEntity);
		appUserDao.saveOrUpdate(entity);	
		bean.setPromotionCodeId(entity.getPromotionCodesId()+"");
		bean.setPromotionInfoId(infoEntity.getPromotionInfoId()+"");
		bean.setMaxUseCount(infoEntity.getMaxUseCount()+"");
		bean.setPromDescription(infoEntity.getPromotionDescription());
		if(infoEntity.getIsPartialUse() != null && 
				infoEntity.getIsPartialUse().equals("1")){
			bean.setIsPartialUse(infoEntity.getIsPartialUse()+"");
			bean.setPartialValue(infoEntity.getPartialValue()+"");	
		}else{
			bean.setIsPartialUse("0");
		}
		bean.setTotalValue(infoEntity.getAmountValue()+"");
		return "";
	}

	@Override
	public void sendReferralToFrnds(SafeHerDecorator decorator)
			throws GenericException {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		logger.info("******Entering in sendReferralToFrnds  with PromAndReffBean "
				+ bean + "******");
		safeHerCommonValidator.validateBeforeSendingReferralToFrnds(decorator);
		try {
			if (decorator.getErrors().size() == 0) {
				String result = saveReferralFrndsIntoDb(bean);
				if(result != null && result.length() > 0){
					throw new GenericException(result);
				}
				decorator.getResponseMap().put("data", bean);
				decorator.setResponseMessage("Referral code sent successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			} else {
				throw new GenericException("Code generation failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}
	
	private String saveReferralFrndsIntoDb(PromAndReffBean bean) {
		UserPromotionEntity userPromotionEntity = new UserPromotionEntity();
		PromotionCodesEntity codeEntity = appUserDao.findBy(
				PromotionCodesEntity.class, "codeValue", bean.getCodeValue());
		if (codeEntity == null) {
			return "No referral code found";
		}
		codeEntity.setShareType(bean.getShareType());
		appUserDao.saveOrUpdate(codeEntity);
		for (int i = 0; i < bean.getReffUserList().size(); i++) {
			PromotionOfferToEntity offerToEntity = new PromotionOfferToEntity();
			AppUserBean appUserBean = bean.getReffUserList().get(i);
			AppUserEntity appUser = null;
			if (appUserBean.getEmail() != null
					|| appUserBean.getPhoneNumber() != null) {
				appUser = appUserDao.findAppUserByEmailOrNumber(
						appUserBean.getEmail(), appUserBean.getPhoneNumber());
				if (appUser != null) {
					offerToEntity.setAppUser(appUser);
				}
				offerToEntity.setPromotionCodesEntity(codeEntity);
				offerToEntity.setCellOn(appUserBean.getPhoneNumber());
				offerToEntity.setEmialId(appUserBean.getEmail());
				offerToEntity.setOfferDate(DateUtil.getCurrentTimestamp());
				appUserDao.saveOrUpdate(offerToEntity);
				//asyncSendEmail
				try {
					bean.setCodeValue(codeEntity.getCodeValue());
					bean.setRefCreaterName(codeEntity.getAppUser().getPerson().getFirstName()+
							" "+codeEntity.getAppUser().getPerson().getLastName());
					bean.setEmail(appUserBean.getEmail());
					bean.setAmount(codeEntity.getPromotionInfoEntity().getAmountValue()+"");
					iAsyncEmailService.sendReferralEmail(bean);
				} catch (GenericException e) {
					e.printStackTrace();
				}
			}
		}
		AppUserEntity userReferring = appUserDao.findById(
				AppUserEntity.class, new Integer(bean.getAppUserId()));
		if(userReferring != null){
			int totalAmount = bean.getReffUserList().size() * 
					codeEntity.getPromotionInfoEntity().getAmountValue();
			userPromotionEntity = appUserDao.findByReferringUserPromotionisActive(
					userReferring, codeEntity.getPromotionInfoEntity().getPromotionType());
			if(userPromotionEntity == null){
				userPromotionEntity = new UserPromotionEntity();
			    userPromotionEntity.setTotalValue(totalAmount);
			}else{
			    userPromotionEntity.setTotalValue(
			    		userPromotionEntity.getTotalValue()+totalAmount);
			}
			userPromotionEntity.setUseStartDate(DateUtil.now());
			userPromotionEntity.setUseExpiryDate(
					codeEntity.getPromotionInfoEntity().getExpiryDate());
		    userPromotionEntity.setPromotionCodesEntity(codeEntity);
		   userPromotionEntity.setPromotionType(
		    		codeEntity.getPromotionInfoEntity().getPromotionType());
		    userPromotionEntity.setAppUser(userReferring);
		    userPromotionEntity.setIsActive("1");
		    userPromotionEntity.setTotalUsedValue(0);
		    userPromotionEntity.setIsRefCreaterUser("1");
		    userPromotionEntity.setIsCompleted("0");
		    userPromotionEntity.setPromotionInfo(codeEntity.getPromotionInfoEntity());
		    appUserDao.saveOrUpdate(userPromotionEntity);
		}
		
		return "";
	}

	@Override
	public void inActivePromotions() throws GenericException {
		// In Active User Promotion After Expire Date
		appUserDao.makeInActiveUserPromotionByExpiryDate();
		// Promotion Info After Expiry Date
		appUserDao.makeInActivePromotionByExpiryDate();

	}
	
}
