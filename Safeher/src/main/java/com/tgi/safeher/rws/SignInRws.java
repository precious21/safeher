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

import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.manager.IDriverManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class SignInRws extends BaseRestfulWebService {

	private static final Logger logger = Logger.getLogger(SignInRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IDriverService iDriverService;

	@Autowired
	private IDriverManager driverManager;
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public @ResponseBody ResponseObject driverSignUp(@RequestBody String json) {
		logger.info("******Entering in signIn  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean signInBean = (SignInBean) dataBeanFactory.
					populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(signInBean);

			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				
				iDriverService.userSingIn(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from driverSignUp  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/signInWithSocial", method = RequestMethod.POST)
	public @ResponseBody ResponseObject socialSignIn(@RequestBody String json) {

		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean signInBean = (SignInBean) dataBeanFactory.
					populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(signInBean);

			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				
				iDriverService.userSocialSingIn(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/driverVisibility", method = RequestMethod.POST)
	public @ResponseBody ResponseObject driverVisibility(@RequestBody String json) {
		logger.info("******Entering in driverVisibility  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean signInBean = (SignInBean) dataBeanFactory.
					populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(signInBean);

			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {

				/*driverManager.appUserVisibility(decorator);*/
				driverManager.appUserVisibilityV2(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from driverVisibility  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/saveFcmToken", method = RequestMethod.POST)
	public @ResponseBody ResponseObject saveFcmToken(@RequestBody String json) {
		logger.info("******Entering in saveFcmToken  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean signInBean = (SignInBean) dataBeanFactory.
					populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(signInBean);

			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				
				iDriverService.saveFcmToken(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from saveFcmToken  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/matchPassword", method = RequestMethod.POST)
	public @ResponseBody ResponseObject matchPassword(@RequestBody String json) {
		logger.info("******Entering in matchPassword  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			SignInBean signInBean = (SignInBean) dataBeanFactory.
					populateDataBeanFromJSON(SignInBean.class, decorator, json);
			decorator.setDataBean(signInBean);

			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				
				iDriverService.matchPassword(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			logger.info("******Exiting from matchPassword  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);

	}
	
}
