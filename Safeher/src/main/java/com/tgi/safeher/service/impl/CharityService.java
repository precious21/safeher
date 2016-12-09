package com.tgi.safeher.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Sleeper;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.CharitiesEntity;
import com.tgi.safeher.entity.UserCharitiesEntity;
import com.tgi.safeher.entity.UserSelectedCharitiesEntity;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.service.ICharityService;
import com.tgi.safeher.service.converter.CharityConverter;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.StringUtil;
import com.tgi.safeher.utils.ValidationUtil;

@Service
@Transactional
@Scope("prototype")
public class CharityService implements ICharityService{
	private static final Logger logger = Logger.getLogger(CharityService.class);
	
	@Autowired
	private CharityConverter charityConverter;
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private DecoratorUtil decoratorUtil;
	
	@Override
	public void saveUserSelectedCharities(SafeHerDecorator decorator)
			throws GenericException {
		CharitiesBean bean = (CharitiesBean) decorator.getDataBean();
		logger.info("**********Entering in saveUserSelectedCharities Start with CharitiesBean "+bean+"************* ");	
		try {
			if(StringUtil.isNotEmpty(bean.getIsSelectionBySafeher()) && 
					bean.getIsSelectionBySafeher().equalsIgnoreCase("0")){
				charityConverter.validateSelectedCharities(decorator);
			}else if(StringUtil.isNotEmpty(bean.getIsSelectionBySafeher()) && 
						bean.getIsSelectionBySafeher().equalsIgnoreCase("1")){
				bean.setCharities(charityConverter.getRandomeCharitiesId(
						appUserDao.getTopTenRandome()));
				bean.setCharities(bean.getCharities().substring(0, bean.getCharities().length()-1));
			}
			if(decorator.getErrors().size() == 0){
				if(StringUtil.isNotEmpty(bean.getAppUserId()+"")){
					String message = saveSelectedCharities(decorator);
					if(message != null && message.length() > 0){
						throw new GenericException(message);
					}else{
						decorator.setResponseMessage("Charities saved");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					}
				}else{
					throw new GenericException("App User Id don't exist");
				}
				
			}else{
				throw new GenericException("Failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from saveUserSelectedCharities with Exception "+e.getMessage()+" ********");;
			throw new GenericException("Server is not responding right now");
		}
	}
	
	public String saveSelectedCharities(SafeHerDecorator decorator){
		String message = "";
		CharitiesBean bean = (CharitiesBean) decorator.getDataBean();
		logger.info("**********Entering in saveSelectedCharities Start with CharitiesBean "+bean+"************* ");	
		
		UserCharitiesEntity userCharitiesEntity = new UserCharitiesEntity();
		CharitiesEntity charitiesEntity = new CharitiesEntity();
		UserSelectedCharitiesEntity userSelectedCharitiesEntity = new UserSelectedCharitiesEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		CharitiesBean beanToAddInList = new CharitiesBean();

		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
		userCharitiesEntity = appUserDao.findByOject(
				UserCharitiesEntity.class, "appUser", appUserEntity);
		if(userCharitiesEntity == null){
			userCharitiesEntity = new UserCharitiesEntity();
		}
		charityConverter.convertCharitiesEntity(userCharitiesEntity, bean);
		userCharitiesEntity.setAppUser(appUserEntity);
		appUserDao.saveOrUpdate(userCharitiesEntity);
		
		if(StringUtil.isNotEmpty(bean.getCharities())){
			String[] charitiesIds = bean.getCharities().split(",");
			if(charitiesIds.length > 0){
				//deleteUnselectedCharities();
				if(StringUtil.isNotEmpty(bean.getPreCharities())){
					String[] preCharIds = bean.getPreCharities().split(",");
					if(preCharIds.length > 0){
//							System.out.println(bean.getCharities());
//							System.out.println(bean.getPreCharities());
						Set<String> charitiesIdsSet = new HashSet
								<String>(Arrays.asList(charitiesIds));
						for(String charityId : preCharIds){
							if(!charitiesIdsSet.contains(charityId.trim())){
								//delete here
								System.out.println(charityId+"deletedId");
								appUserDao.deleteCharities(new Integer(charityId.trim()),
										new Integer(bean.getAppUserId()));
							}	
						}
					}	
				}
				bean.setCharitiesList(new ArrayList<CharitiesBean>());
				for(int i=0;i<charitiesIds.length;i++){
					beanToAddInList = new CharitiesBean();
					userSelectedCharitiesEntity = new UserSelectedCharitiesEntity();
					CharitiesEntity entity = appUserDao.findById(
							CharitiesEntity.class, new Integer(charitiesIds[i].trim()));
					if(entity != null){
						userSelectedCharitiesEntity = appUserDao.findUserSelectedCharity(
								entity.getCharitiesId(), new Integer(bean.getAppUserId()));
						if(userSelectedCharitiesEntity == null){
							userSelectedCharitiesEntity = new UserSelectedCharitiesEntity();
						}
						charityConverter.populateUserSelectedEntity(userSelectedCharitiesEntity, bean);
						userSelectedCharitiesEntity.setCharities(entity);
						userSelectedCharitiesEntity.setUserCharities(userCharitiesEntity);
						appUserDao.saveOrUpdate(userSelectedCharitiesEntity);
						charityConverter.convertEntityToCharityBean(entity, beanToAddInList);
						beanToAddInList.setAppUserId(bean.getAppUserId());
						bean.getCharitiesList().add(beanToAddInList);
						System.out.println(beanToAddInList.getCharitiesList().size());
					}else{
						message = "Some charities dont't exist on system";
					}
				}
				if(message == ""){
					decorator.getResponseMap().put("charitiesList",
							bean.getCharitiesList());
				}
			}
		}else{
			message = "Please select charities";
		}
		
		
		return message;
		
	}

	@Override
	public void getCharities(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("**********Entering in getCharities ************* ");	
		try {
			List<CharitiesEntity> list = appUserDao.getCharities();
			if(list != null && list.size() > 0){
				populateCharities(decorator, list);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}

	@Override
	public void getCharitiesV2(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("**********Entering in getCharities ************* ");	
		try {
			CharitiesBean bean = (CharitiesBean) 
					decorator.getDataBean();
			if(StringUtil.isEmpty(bean.getAppUserId())){
				throw new GenericException("Please provide app user");
			}
			List<CharitiesEntity> list = appUserDao.getCharities();
			if(list != null && list.size() > 0){
				populateCharitiesV2(decorator, list);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}		
	}
	
	public void populateCharities(SafeHerDecorator decorator, List<CharitiesEntity> list){
		logger.info("**********Entering in populateCharities ************* ");	
		CharitiesBean charityBean = new CharitiesBean();
		for(int i=0;i<list.size();i++){
			CharitiesEntity entity = list.get(i);
			CharitiesBean bean = new CharitiesBean();
			charityConverter.convertEntityToCharityBean(entity, bean);
			charityBean.getCharitiesList().add(bean);
		}
		decorator.setDataBean(charityBean);
		decorator.getResponseMap().put("charitiesList", charityBean.getCharitiesList());
		decorator.setResponseMessage("Fetch All Charities");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}
	
	public void populateCharitiesV2(SafeHerDecorator decorator, List<CharitiesEntity> list){
		logger.info("**********Entering in populateCharities ************* ");	
		CharitiesBean charityBean = new CharitiesBean();
		List<Integer> selectedCharitiesIdList = appUserDao.getSelectedCharities(new Integer
				(((CharitiesBean)decorator.getDataBean()).getAppUserId()));
		Map<String, String> selectedCharitiesIdMap = new HashMap<String, String>();
		if(selectedCharitiesIdList != null && selectedCharitiesIdList.size() > 0){
			for(int i=0;i<selectedCharitiesIdList.size();i++){
				Integer id = selectedCharitiesIdList.get(i);
				selectedCharitiesIdMap.put(id+"", id+"");
			}
		}
		for(int i=0;i<list.size();i++){
			CharitiesEntity entity = list.get(i);
			CharitiesBean bean = new CharitiesBean();
			charityConverter.convertEntityToCharityBean(entity, bean);
			if(selectedCharitiesIdMap.containsKey(entity.getCharitiesId()+"")){
				bean.setIsSelected("1");
			}else{
				bean.setIsSelected("0");	
			}
			charityBean.getCharitiesList().add(bean);
		}
		decorator.setDataBean(charityBean);
		decorator.getResponseMap().put("charitiesList", charityBean.getCharitiesList());
		decorator.setResponseMessage("Fetch All Charities");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public void getDrivers(SafeHerDecorator decorator) throws GenericException {
		logger.info("**********Entering in getDrivers ************* ");	
		List<Object[]> list = appUserDao.getDrivers();
		List<MapBean> list2 = new ArrayList<MapBean>();
		if(list != null && list.size() > 0){
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					MapBean lat = new MapBean();
					lat.setLat(list.get(i)[0]+"");
					lat.setLng(list.get(i)[1]+"");
					list2.add(lat);
				}
			}
		}
		decorator.getResponseMap().put("list", list2);
		decorator.setResponseMessage("Fetch All Charities");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}
}
