package com.tgi.safeher.service.manager.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.SignInBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.service.IMapService;
import com.tgi.safeher.service.IDriverService;
import com.tgi.safeher.service.manager.IDriverManager;

@Service
@Transactional
@Scope("prototype")
public class DriverManager implements IDriverManager {
	private static final Logger logger = Logger.getLogger(DriverManager.class);
	
	@Autowired
	private IDriverService driverService;

	@Autowired
	private IMapService mapService;

	@Override
	public void appUserVisibility(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("**************Entering in appUserVisibility *************");
		SignInBean bean = (SignInBean) decorator.getDataBean();
		String result = driverService.appUserActiveDeActive(decorator);
		logger.info("**************Entering in appUserVisibility with signin bean "+bean +"with active result "+result+" *************");
		if(result.equalsIgnoreCase("success")){
			if (bean.getIsAvalible().equals("1")) {
				MapBean mapBean = new MapBean();
				convertSignInBeanToMapBean(bean, mapBean);
				decorator.setDataBean(mapBean);
//				if (bean.getIsDriver().equals("1")) {
					try{
						mapService.savePassangerDriver(decorator);
						if(decorator.getFailure() == null){
							decorator.setResponseMessage("Visisbility is On");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
						}
					}catch (GenericException e) {
//						decorator.setResponseMessage("Some erorr occured, please try again letter");
						decorator.setResponseMessage(e.getMessage());
						decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
					}
//				}
			} else {
				driverService.appUserVisibility(decorator);
			}
		}else{
			decorator.setResponseMessage(result);
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
		}

	}

	private void convertSignInBeanToMapBean(SignInBean bean, MapBean mapBean) {
		logger.info("**************Entering in convertSignInBeanToMapBean with SignInBean "+bean+ "*************");
		mapBean.setAppUserId(bean.getAppUserId());
		mapBean.setIsDriver(bean.getIsDriver());
		mapBean.setIsPhysical(bean.getIsPhysical());
		mapBean.setLat(bean.getLat());
		mapBean.setLng(bean.getLng());
		mapBean.setLoc(new Point(Double.parseDouble(bean.getLat()),Double.parseDouble(bean.getLng())));
		logger.info("**************Exiting from convertSignInBeanToMapBean with MapBean "+mapBean+ "*************");
	}
	
	
	@Override
	public void appUserVisibilityV2(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("**************Entering in appUserVisibility *************");
		SignInBean bean = (SignInBean) decorator.getDataBean();
		String result = driverService.appUserActiveDeActive(decorator);
		logger.info("**************Entering in appUserVisibility with signin bean "+bean +"with active result "+result+" *************");
		if(result.equalsIgnoreCase("success")){
			if (bean.getIsAvalible().equals("1")) {
				MapBean mapBean = new MapBean();
				convertSignInBeanToMapBean(bean, mapBean);
				decorator.setDataBean(mapBean);
//				if (bean.getIsDriver().equals("1")) {
					try{
						mapService.savePassangerDriver(decorator);
						if(decorator.getFailure() == null){
							decorator.setResponseMessage("Visisbility is On");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
						}
					}catch (GenericException e) {
//						decorator.setResponseMessage("Some erorr occured, please try again letter");
						decorator.setResponseMessage(e.getMessage());
						decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
					}
//				}
			} else {
				driverService.appUserVisibilityV2(decorator);
			}
		}else{
			decorator.setResponseMessage(result);
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
		}

	}
	
}
