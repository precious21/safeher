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
import com.tgi.safeher.beans.RideQuickInfoBean;
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
public class RideRequestRws extends BaseRestfulWebService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RideRequestRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IRideRequestResponseService iRideRequestResponseService;

	@Autowired
	private IRideManager rideManager;

	@RequestMapping(value = "/driverRequestToPassenger", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverRequestToPassenger(@RequestBody String json) {
		logger.info("******Entering in driverRequestToPassenger  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideRequestResponseBean signUpBean = (RideRequestResponseBean) dataBeanFactory
					.populateDataBeanFromJSON(RideRequestResponseBean.class,
							decorator, json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iRideRequestResponseService.driverRequestToPassengerV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverRequestToPassenger  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/driverRequestToPassengerV2", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverRequestToPassengerV2(@RequestBody String json) {
		logger.info("******Entering in driverRequestToPassengerV2  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideRequestResponseBean signUpBean = (RideRequestResponseBean) dataBeanFactory
					.populateDataBeanFromJSON(RideRequestResponseBean.class,
							decorator, json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iRideRequestResponseService.driverRequestToPassengerV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverRequestToPassengerV2  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject cancelRequest(@RequestBody String json) {
		logger.info("******Entering in cancelRequest  with json "+ json + "******");	
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
				iRideRequestResponseService.cancelRequestV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from cancelRequest  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/acceptRequest", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject acceptRequest(@RequestBody String json) {
		logger.info("******Entering in acceptRequest  with json "+ json + "******");	
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
				iRideRequestResponseService.acceptRequestV3(decorator);
			}
		} catch (Exception e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from acceptRequest  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/getDriverInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getDriverInfo(@RequestBody String json) {
		logger.info("******Entering in getDriverInfo  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			AppUserBean bean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				//Mongo
				/*iRideRequestResponseService.getDriverInfo(decorator);*/
				iRideRequestResponseService.getDriverInfoV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveAddress  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/getColourRequest", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject colorRequest(@RequestBody String json) {
		logger.info("******Entering in colorRequest  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			PreRideRequestBean bean = (PreRideRequestBean) dataBeanFactory
					.populateDataBeanFromJSON(PreRideRequestBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getColorManagment(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from colorRequest  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/getReasons", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getReasons(@RequestBody String json) {
		logger.info("******Entering in getReasons  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			ReasonBean bean = (ReasonBean) dataBeanFactory
					.populateDataBeanFromJSON(ReasonBean.class, decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getReasons(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getReasons  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/cancelReasonRequest", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject cancelReasonRequest(@RequestBody String json) {
		logger.info("******Entering in cancelReasonRequest  with json "+ json + "******");	
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
				//TODO:check
				/*iRideRequestResponseService
						.cancelOrLateReasonRequest(decorator);*/
				iRideRequestResponseService
				.cancelOrLateReasonRequestV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from cancelReasonRequest  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/colorMatch", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject colorMatchRequest(@RequestBody String json) {
		logger.info("******Entering in colorMatchRequest  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			PreRideRequestBean bean = (PreRideRequestBean) dataBeanFactory
					.populateDataBeanFromJSON(PreRideRequestBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getColorMatch(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from colorMatchRequest  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/passengerOrDriverNotReached", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerOrDriverNotReached(@RequestBody String json) {
		logger.info("******Entering in passengerOrDriverNotReached  with json "+ json + "******");	
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
						.passengerOrDriverNotReached(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from passengerOrDriverNotReached  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/cancelRequestByDriver", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject cancelRequestByDriver(@RequestBody String json) {
		logger.info("******Entering in cancelRequestByDriver  with json "+ json + "******");	
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
				rideManager.cancelRequestByDriver(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from cancelRequestByDriver  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/checkForRideNotification", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject checkForRideNotification(@RequestBody String json) {
		logger.info("******Entering in checkForRideNotification  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideQuickInfoBean bean = (RideQuickInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(RideQuickInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iRideRequestResponseService.checkForRideNotification(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from checkForRideNotification  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/getRideTracking", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getRideTracking(@RequestBody String json) {
		logger.info("******Entering in getReasons  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			RideBean bean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class, decorator, json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iRideRequestResponseService.getRideTracking(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getReasons  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
}
