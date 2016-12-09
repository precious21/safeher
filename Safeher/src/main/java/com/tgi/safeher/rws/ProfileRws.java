package com.tgi.safeher.rws;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgi.safeher.API.thirdParty.GoogleWrapper;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DriverConfigBean;
import com.tgi.safeher.beans.ProfileInformationBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.IProfilService;
import com.tgi.safeher.service.IVehicleService;
import com.tgi.safeher.service.manager.IProfileManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Controller
@Scope("prototype")
public class ProfileRws {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ProfileRws.class);

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
	private IProfilService iProfileService;

	@Autowired
	private IVehicleService iVehicleService;
	
	
	@RequestMapping(value = "/getVehicleList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject getVehicleList(@RequestBody String json) {
		logger.info("******Entering in getVehicleList  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			VehicleInfoBean vehicleBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getVehicelList(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from getVehicleList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/setDefaultVehicle", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject setDefaultVehicle(@RequestBody String json) {
		logger.info("******Entering in setDefaultVehicle  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {

			VehicleInfoBean vehicleBean = (VehicleInfoBean) dataBeanFactory
					.populateDataBeanFromJSON(VehicleInfoBean.class, decorator,
							json);
			decorator.setDataBean(vehicleBean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.defaultVehicle(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from setDefaultVehicle  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	

	@RequestMapping(value = "/passengerPersonalEdit", method = RequestMethod.POST)
	public @ResponseBody ResponseObject passengerPersonalEdit(@RequestBody String json) {
		logger.info("******Entering in passengerPersonalEdit  with json "+ json + "******");	
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
				profileManager.passengerPersonalEdit(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from passengerPersonalEdit  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/driverConfig", method = RequestMethod.POST)
	public @ResponseBody ResponseObject generalConfurationForDirver(@RequestBody String json) {
		logger.info("******Entering in generalConfurationForDirver  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			
			DriverConfigBean bean = (DriverConfigBean) dataBeanFactory
					.populateDataBeanFromJSON(DriverConfigBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.driverGeneralConfig(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generalConfurationForDirver  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	

	@RequestMapping(value = "/getProfileInformation", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getProfileInformation (@RequestBody String json) {
		logger.info("******Entering in GetProfileInformation  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			
			ProfileInformationBean bean = (ProfileInformationBean) dataBeanFactory
					.populateDataBeanFromJSON(ProfileInformationBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getProfileInformation(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generalConfurationForDirver  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/CheckUserInformation", method = RequestMethod.POST)
	public @ResponseBody ResponseObject checkUSerInformation (@RequestBody String json) {
		logger.info("******Entering in GetProfileInformation  with json "+ json + "******");	
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();

		try {
			
			ProfileInformationBean bean = (ProfileInformationBean) dataBeanFactory
					.populateDataBeanFromJSON(ProfileInformationBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.checkUserInformation(decorator);
			}
		} catch (Exception e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from generalConfurationForDirver  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	//Verify User Code
	@ResponseBody
	@RequestMapping(value="/verifyUser", method = RequestMethod.GET)
	public String verifyUser(@RequestParam("vCode")String code, @RequestParam("appUserId")String appUserId, Map<String, Object> model){
		
		String status= "unverified";
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(code)){
			return "Please Come through Valid Link! Try Again";
		}
		try{
			status = iProfileService.verifyUser(code, EncryptDecryptUtil.decryptVerification(appUserId),"1");
		}catch (Exception e) {
			// TODO: handle exception
			status = "unverified";
		}
		
		System.out.println(code);
		System.out.println(appUserId);
		
		return "You are "+ status +" Safr User For Further Please Visit <a href='http://www.gosafr.com'>here</a>";
	
	}
	
	//Verify User Code
		@ResponseBody
		@RequestMapping(value="/verifyUserSms", method = RequestMethod.GET)
		public String verifyUserSms(@RequestParam("vCode")String code, @RequestParam("appUserId")String appUserId, Map<String, Object> model){
			
			String status= "unverified";
			if(StringUtil.isEmpty(code) || StringUtil.isEmpty(code)){
				return "Please Come through Valid Link! Try Again";
			}
			try{
				status = iProfileService.verifyUser(code, EncryptDecryptUtil.decryptVerification(appUserId),"0");
			}catch (Exception e) {
				// TODO: handle exception
				status = "unverified";
			}
			
			System.out.println(code);
			System.out.println(appUserId);
			
			return "You are "+ status +" Safr User For Further Please Visit <a href='http://www.gosafr.com'>here</a>";
		
		}
		
	
}
