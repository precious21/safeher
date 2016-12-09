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
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ReasonBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.manager.IRideManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
// For the time being we are setting this rws scope to singelton
// @Scope("singleton")
public class RideBillingRws extends BaseRestfulWebService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RideBillingRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IRideRequestResponseService iRideRequestResponseService;

	@Autowired
	private IRideManager rideManager;

	@RequestMapping(value = "/rideAction", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject rideAction(@RequestBody String json) {
		logger.info("******Entering in rideAction  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideRequestResponseBean bean = (RideRequestResponseBean) dataBeanFactory
					.populateDataBeanFromJSON(RideRequestResponseBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iRideRequestResponseService
						.rideAction(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from rideAction  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getClientToken", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getClientTokenKey(@RequestBody String json) {
		logger.info("******Entering in getClientTokenKey  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean bean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager	.getClientToken(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getClientTokenKey  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/rideBillingTransaction", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject rideBillingTransaction(@RequestBody String json) {
		logger.info("******Entering in rideBillingTransaction  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean bean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.rideBillingTransaction(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from rideBillingTransaction  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/shortRideHistory", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject RideEarningHistory(@RequestBody String json) {
		logger.info("******Entering in RideEarningHistory  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean bean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.rideShortEarningHistory(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from RideEarningHistory  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/generalDriverSummary", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject GeneralDriverSummary(@RequestBody String json) {
		logger.info("******Entering in GeneralDriverSummary  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean bean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.rideGeneralHistory(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from GeneralDriverSummary  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
}
