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

import com.tgi.safeher.beans.AddressBean;
import com.tgi.safeher.beans.CityBean;
import com.tgi.safeher.beans.DriverInfoBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.StateProvinceBean;
import com.tgi.safeher.beans.ZipCodeBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.manager.IProfileManager;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class AddressRws extends BaseRestfulWebService {

	@Autowired
	private DecoratorUtil decoratorUtil;
	
	@Autowired
	private IProfileManager profileManager;
	
	@Autowired
	private DataBeanFactory dataBeanFactory;
	
	private static final Logger logger = Logger.getLogger(AddressRws.class);
	
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject saveAddress(@RequestBody String json) {
		logger.info("******Entering in saveAdress  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			AddressBean bean = (AddressBean) dataBeanFactory
					.populateDataBeanFromJSON(AddressBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {

				profileManager.addAddress(decorator);

			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveAddress  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/countryList", method = RequestMethod.GET)
	public @ResponseBody
	ResponseObject countryList(@RequestBody String json) {
		logger.info("******Entering in countryList  with json "+ json + "******");
		
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			profileManager.getCountryList(decorator);

		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from countryList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/stateList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject stateList(@RequestBody String json) {
		logger.info("******Entering in stateList  with json "+ json + "******");
		
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			StateProvinceBean bean = (StateProvinceBean) dataBeanFactory
					.populateDataBeanFromJSON(StateProvinceBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getStateList(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from stateList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/cityList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject cityList(@RequestBody String json) {
		logger.info("******Entering in cityList  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			CityBean bean = (CityBean) dataBeanFactory
					.populateDataBeanFromJSON(CityBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getCityList(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from cityList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
	
	@RequestMapping(value = "/zipCodeList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject zipCodeList(@RequestBody String json) {
		logger.info("******Entering in zipCodeList with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			ZipCodeBean bean = (ZipCodeBean) dataBeanFactory
					.populateDataBeanFromJSON(ZipCodeBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				profileManager.getZipCodeList(decorator);
			}
		} catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from zipCodeList  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);

	}
}
