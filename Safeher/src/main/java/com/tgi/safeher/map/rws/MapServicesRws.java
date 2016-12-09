package com.tgi.safeher.map.rws;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.service.IMapService;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DecoratorUtil;

@Controller
@Scope("prototype")
public class MapServicesRws {
	@Autowired
	private DecoratorUtil decoratorUtil;

	@Autowired
	private IMapService iMapService;

	@Autowired
	private DataBeanFactory dataBeanFactory;

	@RequestMapping(value = "/getAllPassangerDriver", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getAllPassangerDriver(@RequestBody String json) {
		
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		MapBean bean = (MapBean) dataBeanFactory.
				populateDataBeanFromJSON(MapBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iMapService.getAllPassangerDriver(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}
	
	@RequestMapping(value = "/savePassangerDriver", method = RequestMethod.POST)
	public @ResponseBody ResponseObject savePassangerDriver(@RequestBody String json) {
		
		System.out.println(json);
		long lStartTime = new Date().getTime();
		SafeHerDecorator decorator = new SafeHerDecorator();
		MapBean bean = (MapBean) dataBeanFactory.
				populateDataBeanFromJSON(MapBean.class, decorator, json);
		decorator.setDataBean(bean);
		try {	
			if (decorator.getResponseMessage() == null
					|| decorator.getResponseMessage().length() <= 0) {
				iMapService.savePassangerDriver(decorator);
			}
		} catch (GenericException e) {
			e.printStackTrace();
			decorator.setResponseMessage(e.getMessage());
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
		return decoratorUtil.responseToClient(decorator);
		
	}

}

