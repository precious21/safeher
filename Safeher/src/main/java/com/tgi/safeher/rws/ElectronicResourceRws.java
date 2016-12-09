package com.tgi.safeher.rws;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.UserImageBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.service.IUserImageService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class ElectronicResourceRws {
	private static final Logger logger = Logger.getLogger(ElectronicResourceRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private IUserImageService iUserImageService;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	
	@RequestMapping(value = "/electronicsResource", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject electronicsResource(@RequestParam MultipartFile[] images) {
		SafeHerDecorator decorator = new SafeHerDecorator();
		long startTime = System.currentTimeMillis();
		UserImageBean userImageBean = new UserImageBean();
		
		try {
			userImageBean.setFileArray(images);
			decorator.setDataBean(userImageBean);

			iUserImageService.saveElectronicsResource(decorator);
		} catch (GenericException e) {
			decorator.getErrors().add(e.getMessage());
			decorator.setResponseMessage( "Unable to upload image" );
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from electronicsResource exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - startTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	@RequestMapping(value = "/electronicsResourceV2", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject electronicsResourceV2(@RequestParam MultipartFile[] images, 
			 @RequestParam(value = "json", required = false) String json) {
		SafeHerDecorator decorator = new SafeHerDecorator();
		long startTime = System.currentTimeMillis();
		UserImageBean userImageBean = new UserImageBean();
		
		try {
			if(json != null){
				userImageBean = (UserImageBean) dataBeanFactory.populateDataBeanFromJSON(
						UserImageBean.class, decorator, json);	
			}
			userImageBean.setFileArray(images);
			decorator.setDataBean(userImageBean);

			iUserImageService.saveElectronicsResourceV2(decorator);
		} catch (GenericException e) {
			decorator.getErrors().add(e.getMessage());
			decorator.setResponseMessage( "Unable to upload image" );
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from electronicsResource exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - startTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	

	@RequestMapping(value = "/fetchElectronicsResource", method = RequestMethod.POST)
	public @ResponseBody ResponseObject fetchElectronicsResource(@RequestBody String json) {
		logger.info("******Entering in fetchElectronicsResource  with json "+ json + "******");
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			
			UserImageBean bean = (UserImageBean) dataBeanFactory
					.populateDataBeanFromJSON(UserImageBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iUserImageService.fetchElectronicsResource(decorator);
			}
		}
		catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from fetchElectronicsResource  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);
		
	}

	@RequestMapping(value = "/removeElectronicsResource", method = RequestMethod.POST)
	public @ResponseBody ResponseObject removeElectronicsResource(@RequestBody String json) {
		logger.info("******Entering in removeElectronicsResource  with json "+ json + "******");
		//userElecResourceId json
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		try {
			UserImageBean bean = (UserImageBean) dataBeanFactory
					.populateDataBeanFromJSON(UserImageBean.class, decorator,
							json);
			decorator.setDataBean(bean);
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iUserImageService.removeElectronicsResource(decorator);
			}
		}
		catch (GenericException e) {
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from removeElectronicsResource  with json "+ json + "and exception "+e.getMessage()+"******");
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");

		return decoratorUtil.responseToClient(decorator);
		
	}
	
}
