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

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class ForgetPasswordRws extends BaseRestfulWebService{
	private static final Logger logger = Logger.getLogger(ForgetPasswordRws.class);
	@Autowired
	private DataBeanFactory dataBeanFactory;
	@Autowired
	private DecoratorUtil decoratorUtil;
	@Autowired
	private IDriverService iDriverService;

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public @ResponseBody ResponseObject forgetPassword(@RequestBody String json) {
		logger.info("******Entering in forgetPassword  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {

			AppUserBean signUpBean = (AppUserBean) dataBeanFactory.
					populateDataBeanFromJSON(AppUserBean.class, decorator, json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.sendEmailToClient(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from forgetPassword  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}


	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody ResponseObject resetPassword(@RequestBody String json) {
		logger.info("******Entering in resetPassword  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {

			AppUserBean signUpBean = (AppUserBean) dataBeanFactory.
					populateDataBeanFromJSON(AppUserBean.class, decorator, json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveNewPassword(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from resetPassword  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
}
