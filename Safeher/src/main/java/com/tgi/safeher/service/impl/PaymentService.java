package com.tgi.safeher.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.API.thirdParty.BrainTree.BrainTree;
import com.tgi.safeher.beans.AppUserRegFlowBean;
import com.tgi.safeher.beans.BankAccountInfoBean;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.PayPalBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.EmailTypeEnum;
import com.tgi.safeher.common.enumeration.PaymentModeEnum;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.UserRegFlowEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.LicenceDao;
import com.tgi.safeher.dao.PaymentDao;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.BankAccountInfoEntity;
import com.tgi.safeher.entity.BankEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.CreditCardTypeEntity;
import com.tgi.safeher.entity.PaymentModeEntity;
import com.tgi.safeher.entity.PaypalInfoEntity;
import com.tgi.safeher.rws.DriverRws;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.IPaymentService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.CreditCardUtil;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class PaymentService implements IPaymentService {
	private static final Logger logger = Logger.getLogger(DriverRws.class);
	@Autowired
	private SignUpConverter signUpConverter;

	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private LicenceDao licenceDao;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private IAsyncEmailService asyncEmailService;

	AppUserPaymentInfoEntity appaymentInfo;

	CreditCardInfoEntity creditCardInfo;
	
	BankAccountInfoEntity bankAccountInfo;

	@Override
	public void saveCreditCardInfo(SafeHerDecorator decorator)
			throws GenericException {
		// TODO Auto-generated method stub
		CreditCardInfoBean bean = (CreditCardInfoBean) decorator.getDataBean();
		logger.info("******Entering in saveCreditCardInfo  with CreditCardInfoBean "+ bean + "******");
		// Validate Request Bean
		signUpConverter.validateCreditCardInfo(decorator);
		// chk error
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = licenceDao.getAppUserById(Integer
						.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if (appUser.getIsDriver() == null
						|| !appUser.getIsDriver().equals("1")) {

					if (checkAppPaymentInfo(appUser)) {
						if (checkCreditCardInfo(appaymentInfo,
								bean.getCreditCardNo())) {
							throw new GenericException(
									"Payment Method not be inserted with same Card Number ");
						} else {
							if (!insertCreditCardInfo(bean,appUser)) {
								BrainTree.deleteCustomer(bean.getBtCustomer());
								throw new GenericException("Data is not Vaild ");
							}
						}
					} else {
						appaymentInfo = new AppUserPaymentInfoEntity();
						appaymentInfo = signUpConverter
								.convertBeanAppUserPaymentInfoEntity();
						appaymentInfo.setAppUser(appUser);
						paymentDao.save(appaymentInfo);
						if (checkCreditCardInfo(appaymentInfo,
								bean.getCreditCardNo())) {
							throw new GenericException(
									"Payment Method not be inserted with same Card Number");
						} else {
							if (!insertCreditCardInfo(bean,appUser)) {
								BrainTree.deleteCustomer(bean.getBtCustomer());
								throw new GenericException("Data is not Vaild ");
							}
						}
					}

					AppUserPaymentInfoEntity paymentInfoEntity = paymentDao.findByOject(
							AppUserPaymentInfoEntity.class, "appUser", appUser);
					if(paymentInfoEntity != null){
						if(paymentInfoEntity.getDefaultType() != null){
							bean.setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
							if(bean.getDefaultType() != null 
									&& bean.getDefaultType().trim().equalsIgnoreCase("C")){
								CreditCardInfoEntity cardInfoEntity = appUserDao.findCreditCard(
										paymentInfoEntity.getAppUserPaymentInfoId());
								if(cardInfoEntity != null){
									CreditCardInfoBean cardInfoBean = new CreditCardInfoBean();
									cardInfoBean = signUpConverter.convertEntityToCreditCardInofBean(cardInfoEntity);
									cardInfoBean.setDefaultType(paymentInfoEntity.getDefaultType().trim()+"");
									decorator.getResponseMap().put("data", cardInfoBean);
								}
							}
						}
					}
					decorator
							.setResponseMessage("Passenger Credit Card Information added Successfully  ");
					 decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

				} else {
					throw new GenericException(
							"This Payment Method only For Passenger");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from saveCreditCardInfo  with Exception "+ e.getMessage() + " ******");
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Credit Card Information Error");
		}

	}

	@Override
	public boolean checkAppPaymentInfo(AppUserEntity appUser)
			throws GenericException {
		logger.info("******Entering in checkAppPaymentInfo ******");
		appaymentInfo = paymentDao.checkAppUserPaymentInfo(appUser);
		if (appaymentInfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCreditCardInfo(AppUserPaymentInfoEntity appPaymentInfo,
			String creditCardNo) throws GenericException {
		logger.info("******Entering in checkCreditCardInfo with creditCardNo "+creditCardNo+ " ******");
		if (appPaymentInfo.getIsCard() != null) {

			if (appPaymentInfo.getIsCard().equals("1")) {
				appPaymentInfo.setDefaultType("C");
				paymentDao.update(appPaymentInfo);
			} else {
				appPaymentInfo.setIsCard("1");
				appPaymentInfo.setDefaultType("C");
				paymentDao.update(appPaymentInfo);
				return false;
			}

		} else {
			appPaymentInfo.setIsCard("1");
			appPaymentInfo.setDefaultType("C");
			paymentDao.update(appPaymentInfo);
			return false;
		}
			// creditCardInfo = paymentDao
			// .checkCreditCardInfo(appPaymentInfo);

			// if (creditCardInfo != null) {
			creditCardInfo = paymentDao.checkUniqueCreditCard(creditCardNo,
					appPaymentInfo);
			if (creditCardInfo != null) {
				return true;
			}
		

		return false;
		// }
		// return false;
	}

	@Override
	public boolean insertCreditCardInfo(CreditCardInfoBean bean, AppUserEntity appUser) throws GenericException {
		logger.info("******Entering in insertCreditCardInfo with CreditCardInfoBean "+bean+ " ******");
		String brainTreeResponce[];
		brainTreeResponce = BrainTree.createCustomer(bean.getFirstName(),bean.getLastName(), bean.getNounce());
		if (brainTreeResponce[0]==null ) {  //|| brainTreeResponce[0].equalsIgnoreCase("VERIFIED")
			bean.setBtCustomer(EncryptDecryptUtil.encrypt(brainTreeResponce[1]));
		} else {
			throw new GenericException(
					"Your Credit Card Request  "
							+ brainTreeResponce[0]
							+ " Beacause "
							+ brainTreeResponce[1]);
		}
		setIsDefaultCreditCardInfo(appaymentInfo.getAppUserPaymentInfoId());
		creditCardInfo = signUpConverter
				.convertBeanToCreditCardInfoEntity(bean);
		creditCardInfo.setAppUserPaymentInfo(appaymentInfo);
		creditCardInfo.setCreditCardType(getCreditCardType(bean
				.getCreditCardNo()));
		creditCardInfo.setBtCustomerNo(bean.getBtCustomer());
		creditCardInfo.setIsDefault(bean.getIsDefault());
		if (paymentDao.save(creditCardInfo))
			return true;
		else
			return false;
	}

	private void setIsDefaultCreditCardInfo(Integer appPayment) {
		paymentDao.setCreditCardUniqueIsDefult(appPayment);
	}

	@Override
	public CreditCardTypeEntity getCreditCardType(String cardNum) {
		logger.info("******Entering in getCreditCardType with cardNum "+cardNum+ " ******");
		CreditCardTypeEntity ccTpyInfo = paymentDao
				.creditCardTypeInfo(CreditCardUtil
						.getCreditCardTypeByNumber(cardNum));
		return ccTpyInfo;
	}

	@Override
	public void saveBankAccountInfo(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator.getDataBean();
		BankAccountInfoEntity defaultBankAccountInfo=null;
		logger.info("******Entering in saveBankAccountInfo with BankAccountInfoBean "+bean+ " ******");
		// Validate Request Bean
		signUpConverter.validateBankAccountInfo(decorator);
		// chk error
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = licenceDao.getAppUserById(Integer
						.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if(appUser.getIsDriver()!=null &&  appUser.getIsDriver().equals("1")){

					if (checkAppPaymentInfo(appUser)) {
						if (checkBankAccountInfo(appaymentInfo,bean.getIbanNo())) {
							throw new GenericException("Payment Method not be inserted with same IBAN Number");
						} else {
							if (bean.getIsDefault().trim().equals("1")) {
								defaultBankAccountInfo = paymentDao
										.checkUniqueBankAccount("1",
												appaymentInfo);
								if (defaultBankAccountInfo != null) {
									defaultBankAccountInfo.setIsDefault("0");
									paymentDao.update(defaultBankAccountInfo);
								}
							}
							if (!insertBankInfo(bean)) {
								throw new GenericException("Data is not Vaild ");
							}
						}
					} else {
						appaymentInfo = new AppUserPaymentInfoEntity();
						appaymentInfo.setIsBankAccount("1");
						appaymentInfo.setAppUser(appUser);
						appaymentInfo.setDefaultType("A");
						paymentDao.save(appaymentInfo);
						if (checkBankAccountInfo(appaymentInfo,bean.getIbanNo())) {
							throw new GenericException(
									"Payment Method not be inserted with same IBAN Number");
						} else {
							if (!insertBankInfo(bean)) {

								throw new GenericException("Data is not Vaild ");
							}
						}
					}
					//decorator.getResponseMap().put("data", );
					decorator.setResponseMessage("Driver bank Information added Successfully  ");
					//decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

				}else{
					throw new GenericException("This Payment Method only For Driver");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from saveBankAccountInfo with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Bank Account No Information Error");
		}
		
	}

	@Override
	public void saveBankAccountInfoV2(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator.getDataBean();
		BankAccountInfoEntity defaultBankAccountInfo=null;
		logger.info("******Entering in saveBankAccountInfoV2 with BankAccountInfoBean "+bean+ " ******");
		// Validate Request Bean
		signUpConverter.validateBankAccountInfoV2(decorator);
		// chk error
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = licenceDao.getAppUserById(Integer
						.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if(appUser.getIsDriver()!=null &&  
						appUser.getIsDriver().equals("1")){
					
					AppUserPaymentInfoEntity appUserPaymentInfoEntity = (AppUserPaymentInfoEntity) 
							paymentDao.findByIdParamCommon("AppUserPaymentInfoEntity", "appUser.appUserId",
									new Integer(bean.getAppUserId()));
					if(appUserPaymentInfoEntity == null){
						appUserPaymentInfoEntity = new AppUserPaymentInfoEntity();
						PaymentModeEntity paymentModeEntity = new PaymentModeEntity();
						
						paymentModeEntity.setPaymentModeId(PaymentModeEnum.Account.getValue());
						appUserPaymentInfoEntity.setIsBankAccount("1");
						appUserPaymentInfoEntity.setAppUser(appUser);
						appUserPaymentInfoEntity.setDefaultType("A");
						appUserPaymentInfoEntity.setPaymentMode(paymentModeEntity);
						paymentDao.saveOrUpdate(appUserPaymentInfoEntity);
					}

					BankAccountInfoEntity bankAccountInfoEntity = new BankAccountInfoEntity();
					
					if(paymentDao.checkForUniqueAccountNo(
							appUserPaymentInfoEntity.getAppUserPaymentInfoId(), bean.getAccountNo())){
						throw new GenericException("Account number already exists");
					}
					
					bankAccountInfoEntity.setAccountNo(bean.getAccountNo());
					bankAccountInfoEntity.setRoutingNo(bean.getRoutingNo());
					bankAccountInfoEntity.setAccountTitle(bean.getAccountTitle());
					bankAccountInfoEntity.setIsDefault(bean.getIsDefault());
					bankAccountInfoEntity.setAppUserPaymentInfo(appUserPaymentInfoEntity);
					paymentDao.saveOrUpdate(bankAccountInfoEntity);
					
					if(bean.getIsDefault().equals("1")){
						//set isDefault 0 to other banks
						paymentDao.setUniqueDefultForBankAccountExceptNewCreated(
								appUserPaymentInfoEntity.getAppUserPaymentInfoId(), bankAccountInfoEntity.getBankAccountInfoId());
					}

					//start asynchronous saving appUserRegFlow
					AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
					appUserRegFlowBean.setAppUserId(appUser.getAppUserId()+"");
					appUserRegFlowBean.setIsFromApp(bean.getIsFromWindow());
					appUserRegFlowBean.setStepCode(
							UserRegFlowEnum.BankInfo.getValue()+"");
					appUserRegFlowBean.setIsCompleted("1");
					asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
					//end asynchronous saving appUserRegFlow
					//Start Sending Mail
					Map<String, String> map = new HashMap<String,String>();
					map.put("Name", appUser.getPerson().getFirstName());
					map.put("fbLink", "https://www.facebook.com/GoSafr/?fref=ts");
					map.put("tweetLink", "https://twitter.com/gosafr");
					asyncEmailService.sendEmail(EmailTypeEnum.Completion.getValue(), 
							appUser.getAppUserId()+"",appUser.getPersonDetail().getPrimaryEmail(), map);
					//End Mailing
					decorator.setResponseMessage("Driver bank Information added Successfully  ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

				}else{
					throw new GenericException("This Payment Method only For Driver");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from saveBankAccountInfoV2 with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Bank Account No Information Error");
		}
		
	}

	@Override
	public boolean checkBankAccountInfo(AppUserPaymentInfoEntity appPaymentInfo,String IBanNo)
			throws GenericException {
		logger.info("******Entering in checkBankAccountInfo with IBANNo "+IBanNo+ " ******");
		
		if (appPaymentInfo.getIsBankAccount().equals("1")) {

		} else {
			appPaymentInfo.setIsBankAccount("1");
			appPaymentInfo.setDefaultType("A");
			paymentDao.update(appPaymentInfo);
			return false;
		}
	//	bankAccountInfo = paymentDao.checkBankAccountInfo(appPaymentInfo);

	//	if (bankAccountInfo != null) {
			bankAccountInfo = paymentDao.checkUniqueAccount(IBanNo , appPaymentInfo);
			if (bankAccountInfo != null) {
				return true;
			}
			return false;
	//	}
	//	return false;
	}

	@Override
	public boolean insertBankInfo(BankAccountInfoBean bean) {
		logger.info("******Entering in insertBankInfo with BankAccountInfoBean "+bean+ " ******");
		setIsDefaultForUniqueBankAccount(appaymentInfo.getAppUserPaymentInfoId());
		bankAccountInfo = signUpConverter
				.convertBeanToBankAccountInfoEntity(bean);
		bankAccountInfo.setAppUserPaymentInfo(appaymentInfo);
		bankAccountInfo.setBank(paymentDao.getBankInfo(bean.getBankId()));
		if (paymentDao.save(bankAccountInfo))
			return true;
		else
			return false;

	}

	private void setIsDefaultForUniqueBankAccount(Integer appUserPaymentInfoId) {
		logger.info("******Entering in setIsDefaultForUniqueBankAccount with appUserPaymentInfoId "+appUserPaymentInfoId+ " ******");
		paymentDao.setUniqueDefultForBankAccount(appUserPaymentInfoId);		
	}

	@Override
	public void getBankList(SafeHerDecorator decorator) {
		logger.info("******Entering in getBankList ******");
		List<BankEntity> BankLst =paymentDao.getBankList();
		decorator.setDataBean(signUpConverter.convertEntiyToBankBean(BankLst));
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage("Bank List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		
	}

	@Override
	public void getBankInfo(SafeHerDecorator decorator) throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		logger.info("******Entering in getBankInfo with BankAccountInfoBean "+bean+ " ******");
		
		// Validate Request Bean
		signUpConverter.validGetBankInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if(appUser.getIsDriver()!=null &&  appUser.getIsDriver().equals("1")){
					AppUserPaymentInfoEntity appPayEntity = paymentDao
							.checkAppUserPaymentInfo(appUser);

					if (appPayEntity != null) {
						BankAccountInfoEntity bankInfoEntity = paymentDao
								.findByOject(BankAccountInfoEntity.class,
										"appUserPaymentInfo", appPayEntity);
						if (bankInfoEntity != null) {
							bean = signUpConverter
									.convertEntityToBankBean(bankInfoEntity);
							bean.setAppUserId(appUser.getAppUserId().toString());
							decorator.getResponseMap().put("data", bean);
							decorator.setResponseMessage("Bank Info List");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
									.getValue());

						} else {
							throw new GenericException(
									"App User Not Exist Please Check Your Value");
						}

					} else {
						throw new GenericException(
								"User Has No Bank Information Please Go To Add Bank Service");
					}
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from getBankInfo with DataAccessException "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}

		} else {
			throw new GenericException("Bank Related Information Error");
		}

	}

	@Override
	public void updateBankInfo(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		BankAccountInfoEntity defaultBankAccountInfo=null;
		logger.info("******Entering in updateBankInfo with BankAccountInfoBean "+bean+ " ******");
		signUpConverter.validateBankAccountInfo(decorator);
		if (StringUtil.isEmpty(bean.getBankAccountInfoId())) {
			decorator.getErrors().add("Please provide Bank Account No");
		}
		if (StringUtil.isEmpty(bean.getAppUserPaymentInfoId())) {
			decorator.getErrors().add("Please provide App User Payment Id");
		}
		if (decorator.getErrors().size() == 0) {
			try {
				BankAccountInfoEntity bankInfoEntity = paymentDao.get(
						BankAccountInfoEntity.class,
						Integer.valueOf(bean.getBankAccountInfoId()));
				signUpConverter.convertBeanToBankAccountInfoEntityForUpdate(bean,
						bankInfoEntity);
				if (bankInfoEntity.getBank().getBankId() != Integer.valueOf(bean
						.getBankId())) {
					BankEntity bnk = paymentDao.getBankInfo(bean.getBankId());
					bankInfoEntity.setBank(bnk);
				}
				if (!bankInfoEntity.getIbanNo().equals(bean.getIbanNo())) {
					bankAccountInfo = paymentDao.checkUniqueAccount(
							bean.getIbanNo(),
							bankInfoEntity.getAppUserPaymentInfo());
					if (bankAccountInfo == null) {
						bankInfoEntity.setIbanNo(bean.getIbanNo());
					} else {
						throw new GenericException("IBAN is Already For This User");
					}
				} else {
					bankInfoEntity.setIbanNo(bean.getIbanNo());
				}

//				if (!paymentDao.update(bankInfoEntity)) {
//					throw new GenericException("Bank Information Can't be Updated ");
//				}
				
				////////////////////////////////////////////////
				
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				
				AppUserPaymentInfoEntity appPayEntity = paymentDao
						.checkAppUserPaymentInfo(appUser);
				if (appPayEntity == null) {
					throw new GenericException(
							"User Has No Bank Information Please Go To Add Bank Service");
				}
				
				if (bean.getIsDefault().equals("1")) {
					defaultBankAccountInfo = paymentDao
							.checkUniqueBankAccount("1", appPayEntity);
					if (defaultBankAccountInfo == null) {
						bankInfoEntity.setIsDefault(bean.getIsDefault());
					}else{
						defaultBankAccountInfo.setIsDefault("0");
						bankInfoEntity.setIsDefault(bean.getIsDefault());
						if(paymentDao.update(defaultBankAccountInfo)){
							if(paymentDao.update(bankInfoEntity)){
								//decorator.getResponseMap().put("data", bankAccountInfoBeanList);
								decorator.setResponseMessage("You Requested Bank is Now Default");
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
										.getValue());
							}
						}else{
							decorator.setReturnCode(ReturnStatusEnum.FAILURE
									.getValue());
							throw new GenericException(
									"Bank Information Error in Updation");
							
						}
					}
				} else if (bean.getIsDefault().equals("0")) {
//					defaultBankAccountInfo = paymentDao
//							.checkUniqueBankAccount("1", appPayEntity);
//					if (defaultBankAccountInfo == null) {
//						throw new GenericException(
//								"AtLeast One Account will be default");
//					}else{
//						decorator.setResponseMessage("AtLeast One Account will be in default");
//						decorator.setReturnCode(ReturnStatusEnum.FAILURE
//								.getValue());
//						throw new GenericException(
//								"AtLeast One Account will be default");
//					}
				}
				
				
				decorator.getResponseMap().put("data", decorator.getDataBean());
				decorator.setResponseMessage("Bank Update Successfully ");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());

			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from updateBankInfo with DataAccessException "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		}
	}

	@Override
	public void getPassengerPaymentDetail(SafeHerDecorator decorator)
			throws GenericException {

		CreditCardInfoBean bean = (CreditCardInfoBean) decorator.getDataBean();
		logger.info("******Entering in getPassengerPaymentDetail with CreditCardInfoBean "+bean+ " ******");
		List<CreditCardInfoEntity> creditCardInfoEntity = new ArrayList<CreditCardInfoEntity>();
		List<CreditCardInfoBean> creditCardInfoBean = new ArrayList<CreditCardInfoBean>();
		signUpConverter.validGetCreaditCard(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				AppUserPaymentInfoEntity appUserPyment = paymentDao
						.checkAppUserPaymentInfo(appUser);
				if (appUserPyment != null) {
					creditCardInfoEntity=paymentDao.getCreditCardInfo(appUserPyment);
					creditCardInfoBean = signUpConverter
							.convertEntityToCreditCardBean(creditCardInfoEntity);
					bean.setCreaditCardList(creditCardInfoBean);
					decorator.getResponseMap().put("data", bean);
					decorator
							.setResponseMessage("Credit Card Information Successfully ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				} else {
					throw new GenericException("Please Add Creadit Card Info First");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from getPassengerPaymentDetail with DataAccessException "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}

		} else {
			throw new GenericException("Credit Card Information Error");
		}

	}

	@Override
	public void addPayPalInfo(SafeHerDecorator decorator) throws GenericException {
		PayPalBean payPalBean = (PayPalBean) decorator.getDataBean();
		logger.info("******Entering in addPayPalInfo with CreditCardInfoBean "+payPalBean+ " ******");
		PaypalInfoEntity payPalEntity=new PaypalInfoEntity();
		AppUserPaymentInfoEntity appUserPyment =null;
		signUpConverter.validPayPalInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(payPalBean.getAppUserId()));
				if(appUser!=null){
				
					appUserPyment = paymentDao.checkAppUserPaymentInfo(appUser);
					if (appUserPyment != null) {
						appUserPyment.setDefaultType("P");
						appUserPyment.setIsPaypal("1");
						if (paymentDao.update(appUserPyment)) {
							payPalEntity.setIsActive(payPalBean.getIsActive());
							payPalEntity.setProvidedDate(DateUtil.now());
							payPalEntity.setAppUserPaymentInfo(appUserPyment);

							if (paymentDao.save(payPalEntity)) {
								decorator
										.setResponseMessage("PayPal Information Successfully Added");
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
										.getValue());
							} else {
								throw new GenericException("Pay Pal Info Issue");
							}
						} else {
							throw new GenericException(
									"Update App User Payment issue");
						}
					} else {

						appUserPyment = new AppUserPaymentInfoEntity();
						appUserPyment.setIsPaypal("1");
						appUserPyment.setAppUser(appUser);
						appUserPyment.setDefaultType("P");
						if (paymentDao.save(appUserPyment)) {
							payPalEntity.setIsActive(payPalBean.getIsActive());
							payPalEntity.setProvidedDate(DateUtil.now());
							payPalEntity.setAppUserPaymentInfo(appUserPyment);
							if (paymentDao.save(payPalEntity)) {
								decorator
										.setResponseMessage("PayPal Information Successfully Added");
								decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
										.getValue());
							} else {
								throw new GenericException("Pay Pal Info Issue");
							}
						} else {
							throw new GenericException(
									"App User Payment Info Issue");
						}

					}
				}else {
					throw new GenericException(
							"User Not Exist in Record");
				}

			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from addPayPalInfo with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException("Please Add Creadit Card Info First");
		}
	}

	@Override
	public void defaultCreatitCard(SafeHerDecorator decorator)
			throws GenericException {
		
		CreditCardInfoBean bean = (CreditCardInfoBean) decorator.getDataBean();
		logger.info("******Entering in defaultCreatitCard with CreditCardInfoBean "+bean+ " ******");
		if(StringUtil.isNotEmpty(bean.getCreditCardInfoId()) || 
				StringUtil.isNotEmpty(bean.getAppUserId()) || 
				StringUtil.isNotEmpty(bean.getAppUserPaymentInfoId())){
			try {
				CreditCardInfoEntity creditCardInfoEntity = paymentDao.findById(
						CreditCardInfoEntity.class, new Integer(bean.getCreditCardInfoId()));
				if(creditCardInfoEntity != null){
					AppUserPaymentInfoEntity appUserPaymentInfoEntity = paymentDao.findById(
							AppUserPaymentInfoEntity.class, new Integer(bean.getAppUserPaymentInfoId()));
					if(appUserPaymentInfoEntity != null){
						//System.out.println(appUserPaymentInfoEntity.getDefaultType());
						appUserPaymentInfoEntity.setDefaultType("C");
						paymentDao.saveOrUpdate(appUserPaymentInfoEntity);
					}
					if(creditCardInfoEntity.getIsDefault()!=null && creditCardInfoEntity.getIsDefault().equals("1")){
						decorator.setResponseMessage("This CreditCard is Already Default ");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					}else{
						paymentDao.setAllCreditCardNonDefault(new Integer(bean.getAppUserPaymentInfoId()));
						creditCardInfoEntity.setIsDefault("1");
						paymentDao.saveOrUpdate(creditCardInfoEntity);
						decorator.setResponseMessage("Successfully updated default");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					}
					
					
				}else{
					throw new GenericException("Credit card not found");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from defaultCreatitCard with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		}else{
			throw new GenericException("Please provide credit card");
		}
	}

	@Override
	public void getMultipleDrivers(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		logger.info("******Entering in getMultipleDrivers with BankAccountInfoBean "+bean+ " ******");
		List<BankAccountInfoEntity> bankAccountInfoList=null;
		List<BankAccountInfoBean> bankAccountInfoBeanList=null;
		signUpConverter.validGetBankInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if (appUser.getIsDriver() != null
						&& appUser.getIsDriver().equals("1")) {
					AppUserPaymentInfoEntity appPayEntity = paymentDao
							.checkAppUserPaymentInfo(appUser);
					if (appPayEntity == null) {
						throw new GenericException(
								"User Has No Bank Information Please Go To Add Bank Service");
					} else {
						bankAccountInfoList = paymentDao.findListByOject(
								BankAccountInfoEntity.class, "appUserPaymentInfo",
								appPayEntity);
						if(!CollectionUtil.isEmpty(bankAccountInfoList)){
							bankAccountInfoBeanList=signUpConverter.convertEntityToBeanList(bankAccountInfoList);
						}
						
						decorator.getResponseMap().put("data", bankAccountInfoBeanList);
						decorator.setResponseMessage("Driver Bank Info List ");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
					
					}
				} else {
					throw new GenericException(
							"Bank Information Not Exist against Driver Id");
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from getMultipleDrivers with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		}

	}

	@Override
	public void setDefaultBankInfo(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		logger.info("******Entering in setDefaultBankInfo with BankAccountInfoBean "+bean+ " ******");
		BankAccountInfoEntity bankAccountInfo=null;
		BankAccountInfoEntity defaultBankAccountInfo=null;
	//	List<BankAccountInfoEntity> bankAccountInfoList=null;
		signUpConverter.validDefaultBankInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				AppUserEntity appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please provide Valid AppUser Id");
				}
				if (appUser.getIsDriver() != null
						&& appUser.getIsDriver().equals("1")) {
					AppUserPaymentInfoEntity appPayEntity = paymentDao
							.checkAppUserPaymentInfo(appUser);
					if (appPayEntity == null) {
						throw new GenericException(
								"User Has No Bank Information Please Go To Add Bank Service");
					} else {
						bankAccountInfo =paymentDao.get(BankAccountInfoEntity.class,
								Integer.valueOf(bean.getBankAccountInfoId()));
						if(bankAccountInfo==null){
							throw new GenericException(
									"Please Enter Valid Bank Id");
						}
											
						//////////////////
						if (bean.getIsDefault().equals("1")) {
							defaultBankAccountInfo = paymentDao
									.checkUniqueBankAccount("1", appPayEntity);
							if (defaultBankAccountInfo == null) {
								bankAccountInfo.setIsDefault(bean.getIsDefault());
							}else{
								defaultBankAccountInfo.setIsDefault("0");
								bankAccountInfo.setIsDefault("1");
								if(paymentDao.update(defaultBankAccountInfo)){
									if(paymentDao.update(bankAccountInfo)){
										//decorator.getResponseMap().put("data", bankAccountInfoBeanList);
										decorator.setResponseMessage("You Requested Bank is Now Default");
										decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
												.getValue());
									}
								}else{
									decorator.setReturnCode(ReturnStatusEnum.FAILURE
											.getValue());
									throw new GenericException(
											"Bank Information Error in Updation");
									
								}
							}
						} else if (bean.getIsDefault().equals("0")) {
							defaultBankAccountInfo = paymentDao
									.checkUniqueBankAccount("1", appPayEntity);
							if (defaultBankAccountInfo == null) {
								throw new GenericException(
										"AtLeast One Account will be default");
							}else{
								decorator.setResponseMessage("AtLeast One Account will be in default");
								decorator.setReturnCode(ReturnStatusEnum.FAILURE
										.getValue());
								throw new GenericException(
										"AtLeast One Account will be default");
							}
						}
					}
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.info("******Exiting from setDefaultBankInfo with Exception "+e.getMessage()+ " ******");
				throw new GenericException("Server is not responding right now");
			}
		}

	}

	@Override
	public void setInActiveOrDeleteBankInfo(SafeHerDecorator decorator)
			throws GenericException {
		BankAccountInfoBean bean = (BankAccountInfoBean) decorator
				.getDataBean();
		logger.info("******Entering in setDiable Bank Info with BankAccountInfoBean "+bean+ " ******");
		AppUserEntity appUser = null;
		BankAccountInfoEntity bankAccountInfo=null;
		//BankAccountInfoEntity defaultBankAccountInfo=null;
		AppUserPaymentInfoEntity appUserPayment = null;
	//	List<BankAccountInfoEntity> bankAccountInfoList=null;
		signUpConverter.validateDeleteBankInfo(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				appUser = paymentDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please Provide the Valid User");
				}
				appUserPayment = paymentDao.get(AppUserPaymentInfoEntity.class,
						Integer.valueOf(bean.getAppUserPaymentInfoId()));
				if (appUserPayment == null) {
					throw new GenericException(
							"Please Provide the Valid App User Payment ID");
				}
				bankAccountInfo=paymentDao.get(BankAccountInfoEntity.class,
						Integer.valueOf(bean.getBankAccountInfoId()));
				if (bankAccountInfo == null) {
					throw new GenericException(
							"Please Provide the Valid Bank Info Id");
				}
				try {
					bankAccountInfo.setIsActive("0");
					paymentDao.update(bankAccountInfo);
				//	paymentDao.delete(bankAccountInfo);
					//paymentDao.delete(vehicleInfoEntity);
				//	decorator.getResponseMap().put("data", bean);
					decorator.setResponseMessage("Account Successfully Deleted");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				} catch (Exception ex) {
					//bankAccountInfo.setIsActive("0");
				//	bankAccountInfo.setIsDefault("0");
				//	if (paymentDao.update(bankAccountInfo)) {
						//if (vehicleDao.update(vehicleInfoEntity)) {
						//	decorator.setResponseMessage("Account Successfully Deleted");
						//	decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					//	} 
				//	else {
							throw new GenericException("Bank Account Info Error");
				//		}
				//	} else {
					//	throw new GenericException(
					//			"App User Vehicle Info Error");
					//}
				}

			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException(
					"Please FullFill the Requriment of Delete Vehicle Request");
		}		
	}

}
