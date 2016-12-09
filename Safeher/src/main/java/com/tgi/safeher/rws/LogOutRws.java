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

import com.tgi.safeher.beans.LogOutBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.manager.IProfileManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class LogOutRws extends BaseRestfulWebService {
	private static final Logger logger = Logger.getLogger(LogOutRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IProfileManager profileManager;

	@Autowired
	private IDriverService iDriverService;

	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject appUserLogout(@RequestBody String json) {
		logger.info("******Entering in appUserLogout  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			LogOutBean logOutBean = (LogOutBean) dataBeanFactory
					.populateDataBeanFromJSON(LogOutBean.class, decorator, json);
			decorator.setDataBean(logOutBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.logOut(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from appUserLogout  with json "+ json + "and exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/logOutFromAnotherDevice", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject logOutFromAnotherDevice(@RequestBody String json) {
		logger.info("******Entering in logOutFromAnotherDevice  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean bean = (SignInBean) dataBeanFactory
					.populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.logOutFromAnotherDevice(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from logOutFromAnotherDevice  with json "+ json + "and exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
}
