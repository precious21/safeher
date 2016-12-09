package com.tgi.safeher.rws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
//@RequestMapping("/image")
public class UserImageRws {

	private static final Logger logger = Logger.getLogger(UserImageRws.class);
	
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private IUserImageService iUserImageService;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	
	@RequestMapping(value = "/userImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject userUploadImage(@RequestParam MultipartFile userImage) {

		SafeHerDecorator decorator = new SafeHerDecorator();
		long startTime = System.currentTimeMillis();
		UserImageBean userImageBean = new UserImageBean();
		
		try {
			userImageBean.setFile(userImage);
			decorator.setDataBean(userImageBean);

			iUserImageService.saveUserImage(decorator);
		} catch (GenericException e) {
			decorator.getErrors().add(e.getMessage());
			decorator.setResponseMessage( "Unable to upload image" );
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveAddress and exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - startTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/userDriverImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject userDriverImage(@RequestParam MultipartFile userImage, 
			 @RequestParam(value = "json", required = false) String json) {

		SafeHerDecorator decorator = new SafeHerDecorator();
		long startTime = System.currentTimeMillis();
		UserImageBean userImageBean = new UserImageBean();
		
		try {
			if(json != null){
				userImageBean = (UserImageBean) dataBeanFactory.populateDataBeanFromJSON(
						UserImageBean.class, decorator, json);	
			}
			userImageBean.setFile(userImage);
			decorator.setDataBean(userImageBean);

			iUserImageService.saveDriverUserImage(decorator);
		} catch (GenericException e) {
			decorator.getErrors().add(e.getMessage());
			decorator.setResponseMessage( "Unable to upload image" );
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from saveAddress and exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - startTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}
	
	@RequestMapping(value = "/appUserImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public @ResponseBody
	ResponseObject appUserImages(@RequestParam MultipartFile image) {

		SafeHerDecorator decorator = new SafeHerDecorator();
		long startTime = System.currentTimeMillis();
		UserImageBean userImageBean = new UserImageBean();
		
		try {
			userImageBean.setFile(image);
			decorator.setDataBean(userImageBean);
			iUserImageService.appUserImage(decorator);
		} catch (GenericException e) {
			decorator.getErrors().add(e.getMessage());
			decorator.setResponseMessage( "Unable to upload image" );
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			logger.info("******Exiting from appUserImage and exception "+e.getMessage()+"******");
		}

		decorator.setQueryTime((System.currentTimeMillis() - startTime) + "");
		return decoratorUtil.responseToClient(decorator);
	}

}
