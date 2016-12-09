package com.tgi.safeher.rws;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgi.safeher.beans.BankAccountInfoBean;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.beans.PayPalBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.manager.IProfileManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class PassengerRws extends BaseRestfulWebService {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PassengerRws.class);
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IProfileManager profileManager;

	@RequestMapping(value = "/addCreditCardInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerPaymentMethod(@RequestBody String json) {
		logger.info("******Entering in passengerPaymentMethod  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			CreditCardInfoBean bean = (CreditCardInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(CreditCardInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			profileManager.addCreditCard(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from passengerPaymentMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/defaultCreditCard", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject defaultCreatitCard(@RequestBody String json) {
		logger.info("******Entering in defaultCreatitCard  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			CreditCardInfoBean bean = (CreditCardInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(CreditCardInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			profileManager.defaultCreatitCard(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from defaultCreatitCard  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/addBankInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverPaymentMethod(@RequestBody String json) {
		logger.info("******Entering in driverPaymentMethod  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			profileManager.addBankAccount(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverPaymentMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/bankList", method = RequestMethod.GET)
	public @ResponseBody
	ResponseObject driverBankMethod(@RequestBody String json) {
		logger.info("******Entering in driverBankMethod  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			profileManager.getBankList(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverBankMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/getBankInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverBankObj(@RequestBody String json) {
		logger.info("******Entering in driverBankObj  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
			profileManager.getBankInfo(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverBankObj  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	@RequestMapping(value = "/updateBankInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject updateDriverBank(@RequestBody String json) {
		logger.info("******Entering in updateDriverBank  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
			profileManager.updateBankInfo((decorator));
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from updateDriverBank  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/getPaymentInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getPassengerPaymentMethod(@RequestBody String json) {
		logger.info("******Entering in getPassengerPaymentMethod  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			CreditCardInfoBean bean = (CreditCardInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(CreditCardInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			profileManager.getPaymentMethod(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getPassengerPaymentMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/addPayPalInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject payPalPaymentMethod(@RequestBody String json) {
		logger.info("******Entering in payPalPaymentMethod  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			PayPalBean bean = (PayPalBean) dataBeanFactory
					.populateDataBeanFromJSON(PayPalBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			profileManager.addPayPalInfo(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from payPalPaymentMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getBankInfoByDriver", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getMultipleDriverById(@RequestBody String json) {
		logger.info("******Entering in getMultipleDriverById  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
			profileManager.getMultipleBankInfoByDriver(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getMultipleDriverById  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/setDefaultBankInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject setDefaultBankInfo(@RequestBody String json) {
		logger.info("******Entering in setDefaultBankInfo  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
			profileManager.setDefaultDriver(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from setDefaultBankInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/removeBankInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject setDltBankInfo(@RequestBody String json) {
		logger.info("******Entering in setDefaultBankInfo  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			BankAccountInfoBean bean = (BankAccountInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(BankAccountInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
			profileManager.setInActiveOrDeleteBankInfo(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from setDefaultBankInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
}
