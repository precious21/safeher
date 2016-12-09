package com.tgi.safeher.service.converter;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.common.constant.Paths;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.UserCharitiesEntity;
import com.tgi.safeher.entity.UserSelectedCharitiesEntity;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.FileUtil;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
public class CharityConverter implements Serializable {

	public void validateSelectedCharities(SafeHerDecorator decorator) {

		CharitiesBean bean = (CharitiesBean) decorator.getDataBean();
//			if (bean.getCharitiesList() == null && bean.getCharitiesList().size() < 10) {
//				decorator.getErrors().add("Please select at lease 10 charities");
//			}
			if (StringUtil.isEmpty(bean.getCharities())) {
				decorator.getErrors().add("Please select at lease 10 charities");
			}else if(StringUtil.isNotEmpty(bean.getCharities())) {
				String[] charitiesIds = bean.getCharities().split(",");
				if(charitiesIds.length < 10){
					decorator.getErrors().add("Please select at lease 10 charities");
				}
			}

	}
	
	public void convertEntityToCharityBean(CharitiesEntity entity, CharitiesBean bean){
		if(StringUtil.isNotEmpty(entity.getCharitiesId()+"")){
			bean.setCharityId(entity.getCharitiesId()+"");
		}
		if(StringUtil.isNotEmpty(entity.getName())){
			bean.setCharityName(entity.getName());
		}
		if(StringUtil.isNotEmpty(entity.getDescription())){
			bean.setDescription(entity.getDescription());
		}
		if(StringUtil.isNotEmpty(entity.getRating()+"")){
			bean.setRating(entity.getRating()+"");
		}
		if(StringUtil.isNotEmpty(entity.getPopularity()+"")){
			bean.setPopularity(entity.getPopularity()+"");
		}
//		bean.setIcon(Paths.CHARITIES_IMAGE+"task-force-for-global-health_100x100.jpg");
//		bean.setIcon(FileUtil.getPathFromProperties(
//				"CHARITIES_IMAGE_SERVER")+"task-force-for-global-health_100x100.jpg");
		
		String path = FileUtil.getPathFromProperties(
				"CHARITIES_IMAGE_SERVER")+"task-force-for-global-health_100x100.jpg";
		String result = Common.saveTempImg(path, "charitiesTemp");
		if(result.equalsIgnoreCase("Fail")){
//			message = "Failed to upload image";
		}else{
			bean.setIcon(result);
		}
		
	}
	
	public void convertCharitiesEntity(UserCharitiesEntity entity, CharitiesBean bean){
		if(StringUtil.isNotEmpty(bean.getIsSelectionBySafeher())){
			entity.setIsSelectionBySafeher(bean.getIsSelectionBySafeher());
		}
		entity.setIsSelectionPending("1");
		entity.setSelectionDate(DateUtil.getCurrentTimestamp());
	}
	
	public void populateUserSelectedEntity(
			UserSelectedCharitiesEntity entity, CharitiesBean bean){
		
		entity.setIsActive("1");
		entity.setDeactiveDate(DateUtil.getCurrentTimestamp());
	}
	
	public String getRandomeCharitiesId(List charitiesId){
		String charities = "";
		if(charitiesId != null && charitiesId.size() > 0){
			for(int i = 0; i < charitiesId.size(); i++){
			    charities += charitiesId.get(i)+",";
			}
		}
		return charities;
	}
}
