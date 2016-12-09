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

import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.repository.RequiredDataRepository;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.manager.IPassengerManager;
import com.tgi.safeher.service.manager.IRideManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;


@Controller
@Scope("prototype")
public class RideRws extends BaseRestfulWebService {

	@Autowired
	private RequiredDataRepository requiredDataRepository;
	
	@Autowired
	private DataBeanFactory dataBeanFactory;
	
	@Autowired
	private DecoratorUtil decoratorUtil;
	
	@Autowired
	private IPassengerManager passengerManager;
	
	@Autowired
	private IRideManager rideManager;
	
	private static final Logger logger = Logger.getLogger(RideRws.class);
	
	@RequestMapping(value = "/rideCriteria", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerRideCriteria(@RequestBody String json) {
		logger.info("******Entering in passengerRideCriteria  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				if(requiredDataRepository.findRequiredData(rideCriteriabean.getAppUserId()) !=null){
					requiredDataRepository.deleteRequiredData(rideCriteriabean.getAppUserId());
				}
				passengerManager.rideCriteriaSearchV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("Ride Criteria Ended with Exception " +e.getMessage());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/rideCriteriaV2", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerRideCriteriaV2(@RequestBody String json) {
		logger.info("******Entering in passengerRideCriteriaV2  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.rideCriteriaSearchV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("Ride Criteria Ended with Exception " +e.getMessage());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/ridePassengerInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerRideInformation(@RequestBody String json) {
		logger.info("******Entering in passengerRideInformation  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.ridePassengerInfo(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveAddress  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/startPreRideByDriver", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject rideStart(@RequestBody String json) {
		logger.info("******Entering in rideStart  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			PreRideRequestBean ridebean = (PreRideRequestBean) dataBeanFactory
					.populateDataBeanFromJSON(PreRideRequestBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.startPerRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from rideStart  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	
	@RequestMapping(value = "/reachedStartDestination", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject reachedStartDestination(@RequestBody String json) {
		logger.info("******Entering in reachedStartDestination  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			PreRideRequestBean ridebean = (PreRideRequestBean) dataBeanFactory
					.populateDataBeanFromJSON(PreRideRequestBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.reachedStartDestination(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from reachedStartDestination  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/justReached", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject justReached(@RequestBody String json) {
		logger.info("******Entering in justReached  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			PreRideRequestBean ridebean = (PreRideRequestBean) dataBeanFactory
					.populateDataBeanFromJSON(PreRideRequestBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.justReached(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from justReached  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/startFinalRide", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject startRide(@RequestBody String json) {
		logger.info("******Entering in startRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.startRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from startRide  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/endRide", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject endRide(@RequestBody String json) {
		logger.info("******Entering in endRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.endRideV2(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from endRide  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/midEndRide", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject midEndRide(@RequestBody String json) {
		logger.info("******Entering in midEndRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.midEndRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from midEndRide  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/getInvoiceInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getInvoiceInfo(@RequestBody String json) {
		logger.info("******Entering in confirmEndRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getInvoiceInfo(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getInvoiceInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/confirmEndRide", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject confirmEndRide(@RequestBody String json) {
		logger.info("******Entering in confirmEndRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.confirmEndRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from confirmEndRide  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getLatestShare", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject frontScreenSummary(@RequestBody String json) {
		logger.info("******Entering in frontScreenSummary  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getLatestShareSummary(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from frontScreenSummary  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/recentRides", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject recentRides(@RequestBody String json) {
		logger.info("******Entering in recentRides  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getRecentRides(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from recentRides  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getRideInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getRideInfo(@RequestBody String json) {
		logger.info("******Entering in getRideInfo  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getRideInfo(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getRideInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/paymentFilter", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getPaymentByFilter(@RequestBody String json) {
		logger.info("******Entering in getPaymentByFilter  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getPaymentByFilter(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getPaymentByFilter  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/getInvoiceByInvoiceNo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getInvoiceByInvoiceNo(@RequestBody String json) {
		logger.info("******Entering in getInvoiceByInvoiceNo  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideBean ridebean = (RideBean) dataBeanFactory
							.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(ridebean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getInvoiceByNo(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getInvoiceByInvoiceNo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/addGiftedRide", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject giftedRide(@RequestBody String json) {
		logger.info("******Entering in giftedRide  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.addGiftedRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from giftedRide  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getGiftedRides", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getGiftedRides(@RequestBody String json) {
		logger.info("******Entering in getGiftedRides  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				rideManager.getGiftedRide(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getGiftedRides  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getGifeRideInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getGiftedPassengerRideInfo(@RequestBody String json) {
		logger.info("get Passenger Ride Info called with JSON " +json);
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		logger.info("******Entering in getGiftedPassengerRideInfo  with json "+ json + "******");	
		try {

			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.getPassengerGiftedRideInfo(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getGiftedPassengerRideInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/giftedRideConsumeStart", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject passengerGiftedRideSearchV2(@RequestBody String json) {
		logger.info("******Entering in passengerGiftedRideSearchV2  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideCriteriaBean rideCriteriabean = (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.giftRideCriteriaSearchV2(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from passengerGiftedRideSearchV2  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from passengerGiftedRideSearchV2  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/isRefresh", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject checkForRefresh(@RequestBody String json) {
		logger.info("Is Refresh  Method called with JSON " +json);
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean rideabean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(rideabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.isRefresh(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("Ride Criteria Ended with Exception " +e.getMessage());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/midRideRequest", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject midRideRequest(@RequestBody String json) {
		logger.info("Mid Ride RequestMethod called with JSON " +json);
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			RideBean rideabean = (RideBean) dataBeanFactory
					.populateDataBeanFromJSON(RideBean.class, decorator,
							json);
			decorator.setDataBean(rideabean);
			
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				passengerManager.midRideRequest(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("Mid Ride Request Method Ended with Exception " +e.getMessage());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	@RequestMapping(value = "/rejectGiftRide", method = RequestMethod.POST)
	 public @ResponseBody
	 ResponseObject rejectGiftRide(@RequestBody String json) {
	  logger.info("Mid Ride RequestMethod called with JSON " +json);
	  System.out.println(json);
	  long lStartTime = new Date().getTime();
	  SafeHerDecorator decorator = new SafeHerDecorator();
	  try {
	   RideBean rideabean = (RideBean) dataBeanFactory
	     .populateDataBeanFromJSON(RideBean.class, decorator,
	       json);
	   decorator.setDataBean(rideabean);
	   
	   if (decorator.getResponseMessage() == null
	     || decorator.getResponseMessage().length() <= 0) {
	    passengerManager.rejectGiftRide(decorator);
	   }
	  } catch (Exception e) {
	   decorator.setResponseMessage(e.getMessage());
	   decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
	   logger.info("Mid Ride Request Method Ended with Exception " +e.getMessage());
	  }
	  decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
	  return decoratorUtil.responseToClient(decorator);

	 }
}
