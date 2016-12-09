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

import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.ICharityService;
import com.tgi.safeher.service.IPromReffService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class PromAndReffRws extends BaseRestfulWebService {

	private static final Logger logger = Logger.getLogger(PromAndReffRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IPromReffService iPromReffService;
	
	@RequestMapping(value = "/safeHerApigeneratePromReffCode", method = RequestMethod.POST)
	public @ResponseBody ResponseObject generatePromReffCode(@RequestBody String json) {
		logger.info("******Entering in generatePromReffCode  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.generatePromReffCode(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generatePromReffCode  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/getPromotions", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getPromotion(@RequestBody String json) {
		logger.info("******Entering in getPromotions  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.getPromotions(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getPromotions  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/userPromotion", method = RequestMethod.POST)
	public @ResponseBody ResponseObject userPromotion(@RequestBody String json) {
		logger.info("******Entering in userPromotion  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.userPromotion(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from userPromotion  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/safeHerApiPromReffCodeGeneration", method = RequestMethod.POST)
	public @ResponseBody ResponseObject safeHerApiPromReffCodeGeneration(@RequestBody String json) {
		logger.info("******Entering in generatePromReffCode  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.safeHerApiPromReffCodeGeneration(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generatePromReffCode  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/generateReferralCode", method = RequestMethod.POST)
	public @ResponseBody ResponseObject generateReferralCode(@RequestBody String json) {
		logger.info("******Entering in generatePromReffCode  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.generateReferralCode(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generatePromReffCode  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/sendReferralToFrnds", method = RequestMethod.POST)
	public @ResponseBody ResponseObject sendReferralToFrnds(@RequestBody String json) {
		logger.info("******Entering in generatePromReffCode  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.sendReferralToFrnds(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generatePromReffCode  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}

	@RequestMapping(value = "/activeUserPromotion", method = RequestMethod.POST)
	public @ResponseBody ResponseObject activeUserPromotion(@RequestBody String json) {
		logger.info("******Entering in userPromotion  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		PromAndReffBean bean = (PromAndReffBean) dataBeanFactory.
				populateDataBeanFromJSON(PromAndReffBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iPromReffService.activeUserPromotion(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from userPromotion  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
}
