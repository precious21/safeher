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

import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.UserFavoritiesBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.ICharityService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class UserFavoritiesRws extends BaseRestfulWebService {

	private static final Logger logger = Logger.getLogger(UserFavoritiesRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private IDriverService iDriverService;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@RequestMapping(value = "/saveUserFavorities", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject fetchCharities(@RequestBody String json) {
		logger.info("******Entering in fetchCharities  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		UserFavoritiesBean bean = (UserFavoritiesBean) dataBeanFactory.
				populateDataBeanFromJSON(UserFavoritiesBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveUserFavorities(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from fetchCharities  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getUserFavorities", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getUserFavorities(@RequestBody String json) {
		logger.info("******Entering in getUserFavorities  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		UserFavoritiesBean bean = (UserFavoritiesBean) dataBeanFactory.
				populateDataBeanFromJSON(UserFavoritiesBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.getUserFavorities(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getUserFavorities  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/deleteUserFavorities", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject deleteUserFavorities(@RequestBody String json) {
		logger.info("******Entering in deleteUserFavorities  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		UserFavoritiesBean bean = (UserFavoritiesBean) dataBeanFactory.
				populateDataBeanFromJSON(UserFavoritiesBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.deleteUserFavorities(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from deleteUserFavorities  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

}
