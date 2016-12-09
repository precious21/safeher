package com.tgi.safeher.rws;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.beans.LogOutBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.ICharityService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.UserUtil;

@Controller
@Scope("prototype")
public class CharitiesRws extends BaseRestfulWebService {

	private static final Logger logger = Logger.getLogger(CharitiesRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private IDriverService iDriverService;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private ICharityService iCharityService;
	
//	@RequestMapping(value = "/fetchCharities", method = RequestMethod.GET)
	@RequestMapping(value = "/fetchCharities", method = RequestMethod.POST)
	public @ResponseBody ResponseObject fetchCharities(@RequestBody String json) {
		logger.info("******Entering in fetchCharities  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
//			iCharityService.getCharities(decorator);
			CharitiesBean bean = (CharitiesBean) dataBeanFactory
					.populateDataBeanFromJSON(CharitiesBean.class, decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iCharityService.getCharitiesV2(decorator);
			}
		}
		catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from fetchCharities  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/getDrivers", method = RequestMethod.GET)
	public @ResponseBody ResponseObject fetchCharities2(@RequestBody String json, HttpServletRequest request) {
		logger.info("******Entering in fetchCharities2  with json "+ json + "******");
		
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			iCharityService.getDrivers(decorator);
		}
		catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from fetchCharities2  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/saveUserSelectedCharities", method = RequestMethod.POST)
	public @ResponseBody ResponseObject saveSelectedCharities(@RequestBody String json) {
		logger.info("******Entering in saveSelectedCharities  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		CharitiesBean bean = (CharitiesBean) dataBeanFactory.
				populateDataBeanFromJSON(CharitiesBean.class, decorator, json);
//		if(!UserUtil.checkIfSessionExist(
//				bean.getAppUserId(), bean.getSessionNo())){
//			decorator.setResponseMessage("User is already logged in to another device");
//			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
//			return decoratorUtil.responseToClient(decorator);
//		}
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iCharityService.saveUserSelectedCharities(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveSelectedCharities  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}

}
