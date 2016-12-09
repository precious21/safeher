package com.tgi.safeher.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;

@Component
public class DataBeanFactory {
	
	public Object populateDataBeanFromJSON(Class<?> clazz, SafeHerDecorator decorator, String json){
		Object object = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			object = clazz.newInstance();
			if(json != null && json.trim().length() > 0) {
				object = mapper.readValue(json, clazz);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			decorator.setResponseMessage("Invalid JSON");
			decorator.setReturnCode(ReturnStatusEnum.INVALID_DATA.getValue());
			decorator.getErrors().add("Provided input is not in valid JSON form");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			decorator.setResponseMessage("Invalid JSON");
			decorator.setReturnCode(ReturnStatusEnum.INVALID_DATA.getValue());
			decorator.getErrors().add("Provided input is not mapping with JSON properties");
		} catch (IOException e) {
			e.printStackTrace();
			decorator.setResponseMessage("Invalid JSON");
			decorator.setReturnCode(ReturnStatusEnum.INVALID_DATA.getValue());
			decorator.getErrors().add("JSON input/output error");
		} catch (Exception e){
			e.printStackTrace();
			decorator.setResponseMessage("Invalid JSON");
			decorator.setReturnCode(ReturnStatusEnum.INVALID_DATA.getValue());
			decorator.getErrors().add("Invalid JSON");
		}		
		return object;
	}	

}
