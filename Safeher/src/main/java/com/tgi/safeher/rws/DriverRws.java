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

import com.google.maps.model.LatLng;
import com.tgi.safeher.API.thirdParty.GoogleWrapper;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.DriverInfoBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.beans.VehicleMakeInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.repositories.TestJPARepository;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.IVehicleService;
import com.tgi.safeher.service.manager.IProfileManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
// @RequestMapping("/driver")
public class DriverRws extends BaseRestfulWebService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO: Log Errors
	private static final Logger logger = Logger.getLogger(DriverRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private GoogleWrapper googleWrapper;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@Autowired
	private IDriverService iDriverService;

	@Autowired
	private IProfileManager profileManager;

	@Autowired
	private IVehicleService iVehicleService;
	

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverSignUp(@RequestBody String json) {
		logger.info("******Entering in driverSignUp  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			AppUserBean signUpBean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveDriverInfoSignUp(decorator);
			}
		} catch (GenericException e) {
			logger.info("******Exiting from signUp  with json "+ json + "and exception "+e.getMessage()+"******");
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	

	@RequestMapping(value = "/signUpUser", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject signUpUser(@RequestBody String json) {
		logger.info("******Entering in driverSignUp  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			AppUserBean signUpBean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.signUpUser(decorator);
			}
		} catch (GenericException e) {
			logger.info("******Exiting from signUpUser  with json "+ json + "and exception "+e.getMessage()+"******");
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/saveUserInformationValue", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject saveSSNNUmber(@RequestBody String json) {
		logger.info("******Entering in driverSignUp  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			AppUserBean signUpBean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveSSNNUmber(decorator);
			}
		} catch (GenericException e) {
			logger.info("******Exiting from saveSSNNUmber  with json "+ json + "and exception "+e.getMessage()+"******");
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/saveLocationAddress", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject saveLocationAddress(@RequestBody String json) {
		logger.info("******Entering in driverSignUp  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			AppUserBean signUpBean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveLocationAddress(decorator);
			}
		} catch (GenericException e) {
			logger.info("******Exiting from saveLocationAddress  with json "+ json + "and exception "+e.getMessage()+"******");
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/qualifiedVehicalOrDisclaimer", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject QualifiedVehicalOrDisclaimer(@RequestBody String json) {
		logger.info("******Entering in driverSignUp  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			AppUserBean signUpBean = (AppUserBean) dataBeanFactory
					.populateDataBeanFromJSON(AppUserBean.class, decorator,
							json);
			decorator.setDataBean(signUpBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iDriverService.saveQualifiedVehicalOrDisclaimer(decorator);
			}
		} catch (GenericException e) {
			logger.info("******Exiting from saveQualifiedVehicalOrDisclaimer  with json "+ json + "and exception "+e.getMessage()+"******");
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/LicenceDetail", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverLicenceMethod(@RequestBody String json) {
		logger.info("******Entering in driverLicenceMethod  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			DriverInfoBean driverInfoBean = (DriverInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(DriverInfoBean.class, decorator,
							json);
			decorator.setDataBean(driverInfoBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.licenceDetail(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverLicenceMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}

	@RequestMapping(value = "/vehicleMake", method = RequestMethod.GET)
	public @ResponseBody
	ResponseObject driverVehicleMethod(@RequestBody String json) {
		logger.info("******Entering in driverVehicleMethod  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			profileManager.getVehicleMake(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverVehicleMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/vehicleModel", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverVehicleModelMethod(@RequestBody String json) {
		logger.info("******Entering in driverVehicleModelMethod  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleMakeInfoBean vehicleInfoBean = (VehicleMakeInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleMakeInfoBean.class,
							decorator, json);
			decorator.setDataBean(vehicleInfoBean);
			profileManager.getVehicleModel(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverVehicleModelMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverVehicleRegistration(@RequestBody String json) {
		logger.info("******Entering in driverVehicleRegistration  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean vehicleInfoBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleInfoBean);
			profileManager.addVehicleInfo(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverVehicleRegistration  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/colorList", method = RequestMethod.GET)
	public @ResponseBody
	ResponseObject driverVehicleColorList(@RequestBody String json) {
		logger.info("******Entering in driverVehicleColorList  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {

			profileManager.getVehicleColor(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverVehicleColorList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	// Test Google API with test data
	@RequestMapping(value = "/estimatedFare", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject googleAPITestMethod(@RequestBody String json) {
		logger.info("******Entering in googleAPITestMethod  with json "+ json + "******");
		// test String
		System.out.println(json);
		SafeHerDecorator decorator = new SafeHerDecorator();
		DistanceAPIBean distanceAPIBean = (DistanceAPIBean) dataBeanFactory
				.populateDataBeanFromJSON(DistanceAPIBean.class, decorator,
						json);
		distanceAPIBean.setLatLngOrigins(new LatLng(distanceAPIBean.getLatOrigins(), distanceAPIBean.getLngOrigins()));
		distanceAPIBean.setLatLngDestinations(new LatLng(distanceAPIBean.getLatDestinations(), distanceAPIBean.getLngDestinations()));
		decorator.setDataBean(distanceAPIBean);
		googleWrapper.googleDistanceAPI(decorator);
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/getLicenceInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getDriverLicenceInfo(@RequestBody String json) {
		logger.info("******Entering in getDriverLicenceInfo  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			DriverInfoBean driverInfoBean = (DriverInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(DriverInfoBean.class, decorator,
							json);
			decorator.setDataBean(driverInfoBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getLicenceInfo(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getDriverLicenceInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/getVehicleInfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getVehicleInfo(@RequestBody String json) {
		logger.info("******Entering in getVehicleInfo  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean vehicleInfoBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleInfoBean);
			profileManager.getVehicleInfo(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getVehicleInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

	@RequestMapping(value = "/updateVehicle", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject updateVehicleInfo(@RequestBody String json) {
		logger.info("******Entering in updateVehicleInfo  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean vehicleInfoBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleInfoBean);
			profileManager.updateVehicleInfo(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from updateVehicleInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/updateLicenceDetail", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject updateDriverLicenceMethod(@RequestBody String json) {
		logger.info("******Entering in updateDriverLicenceMethod  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			DriverInfoBean driverInfoBean = (DriverInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(DriverInfoBean.class, decorator,
							json);
			decorator.setDataBean(driverInfoBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.updateLicenceInfo(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from updateDriverLicenceMethod  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	

	// Test Estimated Time Google API with test data
	@RequestMapping(value = "/estimatedArrivalAPI", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject estimatedRideTime(@RequestBody String json) {
		// test String
		logger.info("******Entering in estimatedRideTime  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			DistanceAPIBean distanceAPIBean = (DistanceAPIBean) dataBeanFactory
					.populateDataBeanFromJSON(DistanceAPIBean.class, decorator,
							json);
			decorator.setDataBean(distanceAPIBean);

			googleWrapper.getEstimatedArrivalTime(decorator);

		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Existing estimatedRideTime  with exception "+ e.getMessage() + "******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
		
		
	// Test Active Drivers within Distance
	@RequestMapping(value = "/activeDrivers", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject activeDriverList(@RequestBody String json) {
		logger.info("******Entering in activeDriverList  with json "+ json + "******");
		System.out.println(json);
		
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			DistanceAPIBean distanceAPIBean = (DistanceAPIBean) dataBeanFactory
					.populateDataBeanFromJSON(DistanceAPIBean.class, decorator,
							json);
			decorator.setDataBean(distanceAPIBean);

			googleWrapper.getActiveDriversList(decorator);
			/*googleWrapper.getActiveDriversList(decorator);*/
			logger.info("******Returning response in activeDriverList "+ decorator.getResponseMap()  + "******");

		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("****** Exiting from activeDriverList with Exeption "+ e.getMessage() + "******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	
	@RequestMapping(value = "/notifyDrivers", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject notifyDriverList(@RequestBody String json) {
		logger.info("******Entering in notifyDriverList  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			RideCriteriaBean rideCriteriaBean= (RideCriteriaBean) dataBeanFactory
					.populateDataBeanFromJSON(RideCriteriaBean.class, decorator,
							json);
			decorator.setDataBean(rideCriteriaBean);
			googleWrapper.getNotifyActiveDriversList(decorator);
			logger.info("******Returning response in activeDriverList "+ decorator + "******");

		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("****** Exiting from notifyDriverList with Exeption "+ e.getMessage() + "******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	
	}

	@RequestMapping(value = "/fetchDriverVehResEnd", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject fetchDriverVehResEnd(@RequestBody String json) {
		logger.info("******Entering in fetchDriverVehResEnd  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean bean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			iVehicleService.fetchDriverVehResEnd(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from fetchDriverVehResEnd  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
		
	@RequestMapping(value = "/saveDriverVehResEnd", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject saveDriverVehResEnd(@RequestBody String json) {
		logger.info("******Entering in saveDriverVehResEnd  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean bean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			iVehicleService.saveDriverVehResEnd(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveDriverVehResEnd  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/driverPassengerRating", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject driverPassengerRating(@RequestBody String json) {
		logger.info("******Entering in driverPassengerRating  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			UserRatingBean bean = (UserRatingBean) dataBeanFactory
					.populateDataBeanFromJSON(UserRatingBean.class,
							decorator, json);
			decorator.setDataBean(bean);
			iDriverService.driverPassengerRating(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from driverPassengerRating  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/deleteVehicle", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject deleteVehicleInfo(@RequestBody String json) {
		logger.info("******Entering in deleteVehicleInfo  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			VehicleInfoBean vehicleInfoBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleInfoBean);
			profileManager.deleteVehicleInfo(decorator);
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from deleteVehicleInfo  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	
	//Testing Method
	@RequestMapping(value = "/jpa", method = RequestMethod.GET)
	public @ResponseBody
	ResponseObject testJPA(@RequestBody String json) throws GenericException {
		logger.info("******Entering in deleteVehicleInfo  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		profileManager.deleteVehicleInfo(decorator);
		
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
}
