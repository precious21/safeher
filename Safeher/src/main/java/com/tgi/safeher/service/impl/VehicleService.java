package com.tgi.safeher.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.ColorBean;
import com.tgi.safeher.beans.EndVehResBean;
import com.tgi.safeher.beans.UserFavoritiesBean;
import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.beans.VehicleMakeInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.VehicleDao;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.ColorEntity;
import com.tgi.safeher.entity.DriverEndorcementEntity;
import com.tgi.safeher.entity.DriverRestrictionEntity;
import com.tgi.safeher.entity.DriverVehClassEntity;
import com.tgi.safeher.entity.EndorcementEntity;
import com.tgi.safeher.entity.RestrictionEntity;
import com.tgi.safeher.entity.RoleEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserFavoritiesEntity;
import com.tgi.safeher.entity.VehClassEntity;
import com.tgi.safeher.entity.VehicleInfoEntity;
import com.tgi.safeher.entity.VehicleMakeEntity;
import com.tgi.safeher.entity.VehicleModelEntity;
import com.tgi.safeher.repositories.TestJPARepository;
import com.tgi.safeher.service.IVehicleService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class VehicleService implements IVehicleService {

	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private SignUpConverter signUpConverter;

	@Autowired
	private AppUserDao appUserDao;
	
	/*@Autowired
	private TestJPARepository testJPA;*/

	private AppUserVehicleEntity appUserVehicle;
	@Override
	public void getVehicleMake(SafeHerDecorator decorator) {
		List<VehicleMakeEntity> lsVehicleMake = vehicleDao.getVehicleMake();

	//	signUpConverter.convertEntiyToVehicleBean(lsVehicleMake);
		decorator.setDataBean(signUpConverter
				.convertEntiyToVehicleBean(lsVehicleMake));
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage("Vehicle Make List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public List<VehicleModelEntity> getVehicleModelByMake(
			VehicleMakeEntity vehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleMakeEntity getVehicleMakeById(String id) {

		return vehicleDao.getByMakeId(Integer.valueOf(id));
	}

	@Override
	public void getVehicleModel(SafeHerDecorator decorator) {

		VehicleMakeInfoBean bean = (VehicleMakeInfoBean) decorator
				.getDataBean();
		VehicleMakeEntity vkMake = getVehicleMakeById(bean.getVehicleMakeId());
		List<VehicleModelEntity> lsVehicleMake = vehicleDao
				.getVehicleModelByMake(vkMake);
		decorator.setDataBean(signUpConverter
				.convertVehicleModelEntiyToVehicleBean(lsVehicleMake));
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage("Vehicle Model List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@SuppressWarnings("unused")
	@Override
	public void getVehicleColor(SafeHerDecorator decorator) throws GenericException {
		try {

			ColorBean bean = new ColorBean();
			List<ColorEntity> vehicleColorLst = vehicleDao.getVehicleColor();

			decorator.setDataBean(signUpConverter
					.convertEntiyToVehicleColorBean(vehicleColorLst));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage("Vehicle Color List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public void saveVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {

		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		// Validate Request Bean
		signUpConverter.validateVehicleInfo(decorator);
		try{

			if (decorator.getErrors().size() == 0) {

				VehicleInfoEntity entity = signUpConverter
						.convertBeanToVehicleInfoEntity(bean);

				AppUserEntity appUser =vehicleDao.get(AppUserEntity.class,Integer.valueOf(bean.getAppUserId()));
				if(checkAppUserVehicleInfoIsActive(appUser)){
					if(!checkVehicleRegistration(bean.getPlateNumber())){
						//	entity=addAppUserInstance(bean,entity);
						entity.setColor(vehicleDao.get(ColorEntity.class,Integer.valueOf(bean.getColor())));
						entity.setVehicleMake(vehicleDao.get(VehicleMakeEntity.class,Integer.valueOf(bean.getVehicleMake())));
						entity.setVehicleModel(vehicleDao.get(VehicleModelEntity.class,	Integer.valueOf(bean.getVehicleModel())));
						entity.setStatus(vehicleDao.get(StatusEntity.class,	Integer.valueOf(bean.getStatus())));
						entity.setSeatCapacity(new Short(bean.getSeatCapacity()));
						vehicleDao.save(entity);
						saveAppUserVehicleInfo(entity, bean);
						bean.setVehicleInfoId(entity.getVehicleInfoId().toString());

					}
					else{
						throw new GenericException("Vehicle Registration Number Already Exist! ");
					}

				}else{
					if(!checkVehicleRegistration(bean.getPlateNumber())){
						//entity=addAppUserInstance(bean,entity);
						entity.setColor(vehicleDao.get(ColorEntity.class,Integer.valueOf(bean.getColor())));
						entity.setVehicleMake(vehicleDao.get(VehicleMakeEntity.class,Integer.valueOf(bean.getVehicleMake())));
						entity.setVehicleModel(vehicleDao.get(VehicleModelEntity.class,	Integer.valueOf(bean.getVehicleModel())));
						entity.setStatus(vehicleDao.get(StatusEntity.class,	Integer.valueOf(bean.getStatus())));
						entity.setSeatCapacity(new Short(bean.getSeatCapacity()));
						vehicleDao.save(entity);
						/////
						if (bean.getIsActive().equals("1")) {
							appUserVehicle.setToDate(DateUtil.now());
							appUserVehicle.setIsActive("0");
						} 
//						appUserVehicle.setVehicleInfo(entity);
						vehicleDao.update(appUserVehicle);
						saveAppUserVehicleInfo(entity, bean);
						bean.setVehicleInfoId(entity.getVehicleInfoId().toString());

					} else {
						throw new GenericException(
								"Vehicle Registration Number Already Exist! ");
					}
				}
			} else {
				throw new GenericException("Vehicle Registration Error ");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
		decorator.getResponseMap().put("data", bean);
		decorator.setResponseMessage("Vehicle Successfully Added");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	}

	@Override
	public void saveAppUserVehicleInfo(VehicleInfoEntity entity,
			VehicleInfoBean bean) throws GenericException {

		AppUserVehicleEntity appEntity = new AppUserVehicleEntity();
		appEntity.setStatus(entity.getStatus());
		appEntity.setVehicleInfo(entity);
		appEntity.setRole(vehicleDao.get(RoleEntity.class, 2));  
		appEntity.setFromDate(DateUtil.now());
		appEntity.setIsActive(bean.getIsActive());
		appEntity.setAppUser(vehicleDao.get(AppUserEntity.class,
				Integer.valueOf(bean.getAppUserId())));

		if (vehicleDao.save(appEntity)) {

		} else {
			throw new GenericException("Vehicle Registration Failed");
		}
	}

	@Override
	public boolean checkVehicleRegistration(String vehicleNum)
			throws GenericException {
		VehicleInfoEntity v1=vehicleDao.findBy(VehicleInfoEntity.class, "plateNumber", vehicleNum);
		if(v1!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAppUserVehicleInfo(AppUserEntity appUser)
			throws GenericException {
		appUserVehicle = vehicleDao.findByOject(AppUserVehicleEntity.class,"appUser", appUser);
		if (appUserVehicle == null) {
			return true;
		}
		return false;
	}

	public boolean checkAppUserVehicleInfoIsActive(AppUserEntity appUser)
			throws GenericException {
		appUserVehicle = vehicleDao.checkAppUserVehicleInfo(appUser);
		if (appUserVehicle == null) {
			return true;
		}
		return false;
	}

	@Override
	public VehicleInfoEntity addAppUserInstance(VehicleInfoBean bean , VehicleInfoEntity entity)
			throws GenericException {
		entity.setColor(vehicleDao.get(ColorEntity.class,Integer.valueOf(bean.getColor())));
		entity.setVehicleMake(vehicleDao.get(VehicleMakeEntity.class,Integer.valueOf(bean.getVehicleMake())));
		entity.setVehicleModel(vehicleDao.get(VehicleModelEntity.class,	Integer.valueOf(bean.getVehicleModel())));
		entity.setStatus(vehicleDao.get(StatusEntity.class,	Integer.valueOf(bean.getStatus())));
		vehicleDao.save(entity);
		
		return entity;
		
	}

	@Override
	public void getVehicleInfo(SafeHerDecorator decorator) throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		// Validate Request Bean
		signUpConverter.validateGetVehicleIfo(decorator);
		try {
			AppUserEntity appUser = vehicleDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (decorator.getErrors().size() == 0) {
//				AppUserVehicleEntity appUserVehEnt = vehicleDao.findByOject(
//						AppUserVehicleEntity.class, "appUser", appUser);
				AppUserVehicleEntity appUserVehEnt = vehicleDao.findByOjectForVehical(
						AppUserVehicleEntity.class, "appUser", appUser);
				if (appUserVehEnt != null) {
					VehicleInfoEntity vehicleInfoEntity = appUserVehEnt
							.getVehicleInfo();
					bean = signUpConverter
							.convertEntityToVehicleInfoBean(vehicleInfoEntity);
					bean.setAppUserId(appUser.getAppUserId().toString());
					bean.setAppUserVehicleId(appUserVehEnt
							.getAppUserVehicleId().toString());

					decorator.getResponseMap().put("data", bean);
					decorator.setResponseMessage("Vehicle Information");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				}
			} else {
				throw new GenericException("Vehicle Information Error ");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}

	}

	@Override
	public void updateVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		signUpConverter.validateVehicleInfo(decorator);
		try {

			if (decorator.getErrors().size() == 0) {
				AppUserEntity appUser = vehicleDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
//				AppUserVehicleEntity appUserVehEnt = vehicleDao.findByOject(
//						AppUserVehicleEntity.class, "appUser", appUser);
				AppUserVehicleEntity appUserVehEnt = vehicleDao.
						findAppUserVehicle(new Integer(bean.getVehicleInfoId()));
				if (appUserVehEnt != null) {
					VehicleInfoEntity vehicleInfoEntity = appUserVehEnt
							.getVehicleInfo();
					vehicleInfoEntity = signUpConverter
							.convertBeanToUpdateVehicleInfo(bean,
									vehicleInfoEntity);
					if (vehicleInfoEntity.getVehicleMake().getVehicleMakeId() != Integer
							.valueOf(bean.getVehicleMake())) {
						vehicleInfoEntity.setVehicleMake(vehicleDao.get(
								VehicleMakeEntity.class,
								Integer.valueOf(bean.getVehicleMake())));
					}
					if (vehicleInfoEntity.getVehicleModel().getVehicleModelId() != Integer
							.valueOf(bean.getVehicleModel())) {
						vehicleInfoEntity.setVehicleModel(vehicleDao.get(
								VehicleModelEntity.class,
								Integer.valueOf(bean.getVehicleModel())));
					}

					if (!vehicleDao.update(vehicleInfoEntity)) {
						throw new GenericException("Updation Error");
					}
					if(StringUtil.isNotEmpty(bean.getIsActive()) && 
							bean.getIsActive().equalsIgnoreCase("0")){
						appUserVehEnt.setIsActive("0");
						vehicleDao.update(appUserVehEnt);
					}else if(StringUtil.isNotEmpty(bean.getIsActive()) && 
								bean.getIsActive().equalsIgnoreCase("1")){
						appUserDao.inActiveAllVehicle(new Integer(
								bean.getAppUserId()), new Integer(bean.getVehicleInfoId()));
						appUserVehEnt.setIsActive("1");
						vehicleDao.update(appUserVehEnt);
					}
					decorator.getResponseMap().put("data",
							decorator.getDataBean());
					decorator
							.setResponseMessage("Vehicle Update Successfully ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				}
			} else {
				throw new GenericException(
						"Please Fullfill the Requriment Of Api");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}
	}

	@Override
	public void fetchDriverVehResEnd(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		List<AppUserBean> mainList = new ArrayList<AppUserBean>();
		EndVehResBean endVehResBean = new EndVehResBean();
		List<Object> list = null;
		List subList = null;
		try {
			if (bean.getFlag().equals("1")) {
				// Endorecment
			list = appUserDao.fetchAllVehResEnd(
					"DriverEndorcementEntity", "endorcement", new Integer(bean.getAppUserId()));
//			subList = appUserDao.getAll(EndorcementEntity.class);
			subList = appUserDao.getAllVehResEnd(EndorcementEntity.class);
//			list = appUserDao.getDriverEndoc(new Integer(bean.getAppUserId()));
		}else if(bean.getFlag().equals("2")){
			//Restriction
			list = appUserDao.fetchAllVehResEnd(
					"DriverRestrictionEntity", "restriction", new Integer(bean.getAppUserId()));
//			subList = appUserDao.getAll(RestrictionEntity.class);
			subList = appUserDao.getAllVehResEnd(RestrictionEntity.class);
		}else if(bean.getFlag().equals("3")){
			//classification
			list = appUserDao.fetchAllVehResEnd(
					"DriverVehClassEntity", "vehClass", new Integer(bean.getAppUserId()));
//			subList = appUserDao.getAll(VehClassEntity.class);
			subList = appUserDao.getAllVehResEnd(VehClassEntity.class);
		}
			if (subList != null && subList.size() > 0) {
				convetList(bean, list, subList, endVehResBean);
				endVehResBean.setAppUserId(bean.getAppUserId());
				decorator.getResponseMap().put("data", endVehResBean);
				decorator.setResponseMessage("Successfully fetched data");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
			} else {
				decorator.setResponseMessage("No Recorde for this user");
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new GenericException("Server is not responding right now");
		}

	}
	
	public void convetList(VehicleInfoBean vehBean, 
			List<Object> list, List subList, EndVehResBean endVehResBean){
		
		if(subList != null && subList.size() > 0){
			for(int i=0;i<list.size();i++){
				EndVehResBean bean = new EndVehResBean();
				if(vehBean.getFlag().equals("1")){
					EndorcementEntity entity1 = (EndorcementEntity) list.get(i);
					bean.setName(entity1.getName());
					bean.setId(entity1.getEndorcementId()+"");
				}else if(vehBean.getFlag().equals("2")){
					RestrictionEntity entity2 = (RestrictionEntity) list.get(i);
					bean.setName(entity2.getName());
					bean.setId(entity2.getRestrictionId()+"");
				}else if(vehBean.getFlag().equals("3")){
					VehClassEntity entity3 = (VehClassEntity) list.get(i);
					bean.setName(entity3.getName());
					bean.setId(entity3.getVehClassId()+"");
				}
				bean.setIsActive("1");
				endVehResBean.getList().add(bean);
			}
			for(int i=0;i<subList.size();i++){
				EndVehResBean bean = new EndVehResBean();
				if(vehBean.getFlag().equals("1")){
					EndorcementEntity entity1 = (EndorcementEntity) subList.get(i);
					bean.setName(entity1.getName());
					bean.setId(entity1.getEndorcementId()+"");
				}else if(vehBean.getFlag().equals("2")){
					RestrictionEntity entity2 = (RestrictionEntity) subList.get(i);
					bean.setName(entity2.getName());
					bean.setId(entity2.getRestrictionId()+"");
				}else if(vehBean.getFlag().equals("3")){
					VehClassEntity entity3 = (VehClassEntity) subList.get(i);
					bean.setName(entity3.getName());
					bean.setId(entity3.getVehClassId()+"");
				}
				bean.setIsActive("0");
				endVehResBean.getList().add(bean);
			}
		}
		Set<String> set = new HashSet<String>();
		for(int i=0;i<endVehResBean.getList().size();i++){
			EndVehResBean obj1 = endVehResBean.getList().get(i);
			set.add(obj1.getId());
		}
		System.out.println(set.size());
		for(int i=0;i<endVehResBean.getList().size();i++){
			EndVehResBean obj = endVehResBean.getList().get(i);
			if(set.contains(obj.getId())){
				if(obj.getIsActive().equals("0") && 
						checkDuplicate(endVehResBean.getList(), obj.getId())){
					endVehResBean.getList().remove(i);
					i--;
				}
			}
		}
	}
	
	private boolean checkDuplicate(List<EndVehResBean> list, String id){
		int count = 0;
		for(int i=0;i<list.size();i++){
			EndVehResBean obj = list.get(i);
			if(obj.getId().equals(id)){
				count++;
			}
		}
		if(count >= 2){
			return true;
		}
		return false;
		
	}

	@Override
	public void saveDriverVehResEnd(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			if(bean.getFlag().equals("1")){
				//Endorecment
				saveDeleteDriverEndorcment(bean);
			}else if(bean.getFlag().equals("2")){
				//Restriction
				saveDeleteDriverRestriction(bean);
			}else if(bean.getFlag().equals("3")){
				//classification
				saveDeleteDriverClass(bean);
			}
			decorator.setResponseMessage("Successfully saved");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		}else{
			throw new GenericException("User don't exists");
		}
	}
	
	public String saveDeleteDriverEndorcment(VehicleInfoBean bean){
		String message = "success";
		String[] saveItem = new String[0];
		if(StringUtil.isNotEmpty(bean.getSaveItem())){
			saveItem = bean.getSaveItem().split(",");
		}
		if(bean.getRemoveItem() != null && bean.getRemoveItem().length() > 0){
			String[] saveItemId = bean.getRemoveItem().split(",");
			for (int i = 0; i < saveItemId.length; i++){
				appUserDao.removeItem("DriverEndorcementEntity", 
						new Integer(saveItemId[i]), "entity.endorcement.endorcementId", new Integer(bean.getAppUserId()));
			}
		}
		if(saveItem.length > 0){
			for(int i=0;i<saveItem.length;i++){
				EndorcementEntity endorcementEntity = new EndorcementEntity();
				DriverEndorcementEntity driverEndorcementEntity = new DriverEndorcementEntity();
				driverEndorcementEntity = appUserDao.findObject(
						"DriverEndorcementEntity", "appUser.appUserId", new Integer(bean.getAppUserId()),
						"endorcement.endorcementId", new Integer(saveItem[i]));
				if(driverEndorcementEntity == null){
					driverEndorcementEntity = new DriverEndorcementEntity();
					signUpConverter.convertBeanToEndorcement(driverEndorcementEntity, bean);
					endorcementEntity.setEndorcementId(new Integer(saveItem[i]));
					driverEndorcementEntity.setEndorcement(endorcementEntity);
					appUserDao.saveOrUpdate(driverEndorcementEntity);
				}
			}
		}
		return message;
	}
	
	public String saveDeleteDriverRestriction(VehicleInfoBean bean){
		String message = "success";

		String[] saveItem = new String[0];
		if(StringUtil.isNotEmpty(bean.getSaveItem())){
			saveItem = bean.getSaveItem().split(",");
		}
		if(bean.getRemoveItem() != null && bean.getRemoveItem().length() > 0){
			String[] saveItemId = bean.getRemoveItem().split(",");
			for (int i = 0; i < saveItemId.length; i++){
				appUserDao.removeItem("DriverRestrictionEntity", 
						new Integer(saveItemId[i]), "entity.restriction.restrictionId", new Integer(bean.getAppUserId()));
			}
		}
		if(saveItem.length > 0){
			for(int i=0;i<saveItem.length;i++){
				DriverRestrictionEntity entity = new DriverRestrictionEntity();
				entity = appUserDao.findObject(
						"DriverRestrictionEntity", "appUser.appUserId", new Integer(bean.getAppUserId()),
						"restriction.restrictionId", new Integer(saveItem[i]));
				if(entity == null){
					entity = new DriverRestrictionEntity();
					RestrictionEntity restrictionEntity = new RestrictionEntity();
					signUpConverter.convertBeanToRestriction(entity, bean);
					restrictionEntity.setRestrictionId(new Integer(saveItem[i]));
					entity.setRestriction(restrictionEntity);
					appUserDao.saveOrUpdate(entity);
				}
			}
		}
		return message;
	}
	
	public String saveDeleteDriverClass(VehicleInfoBean bean){
		String message = "success";

		String[] saveItem = new String[0];
		if(StringUtil.isNotEmpty(bean.getSaveItem())){
			saveItem = bean.getSaveItem().split(",");
		}
		if(bean.getRemoveItem() != null && bean.getRemoveItem().length() > 0){
			String[] saveItemId = bean.getRemoveItem().split(",");
			for (int i = 0; i < saveItemId.length; i++){
				appUserDao.removeItem("DriverVehClassEntity", 
						new Integer(saveItemId[i]), "entity.vehClass.vehClassId", new Integer(bean.getAppUserId()));
			}
		}
		if(saveItem.length > 0){
			for(int i=0;i<saveItem.length;i++){
				DriverVehClassEntity entity = new DriverVehClassEntity();
				entity = appUserDao.findObject(
						"DriverVehClassEntity", "appUser.appUserId", new Integer(bean.getAppUserId()),
						"vehClass.vehClassId", new Integer(saveItem[i]));
				if(entity == null){
					entity = new DriverVehClassEntity();
					VehClassEntity vehClassEntity = new VehClassEntity();
					signUpConverter.convertBeanToVehicalClass(entity, bean);
					vehClassEntity.setVehClassId(new Integer(saveItem[i]));
					entity.setVehClass(vehClassEntity);
					appUserDao.saveOrUpdate(entity);
				}	
			}
		}
		return message;
	}

	@Override
	public void getvehicleList(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		List<AppUserVehicleEntity> vehicleInfoList=null;
		List<VehicleInfoBean> vehicleInfoBeanlist=null;
		AppUserEntity appUser=null;
		AppUserVehicleEntity appUserVehicle=null;
		signUpConverter.validateGetVehicleInfo(decorator);

		if (decorator.getErrors().size() == 0) {
			appUser	 = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("Please Provide the Valid User");
			}
//			appUserVehicle = appUserDao.findByOject(AppUserVehicleEntity.class,
//					"appUser", appUser);
//			if (appUserVehicle == null) {
//				throw new GenericException("Please Add Vehicle First ");
//			}
			vehicleInfoList=appUserDao.findListByOject(AppUserVehicleEntity.class, "appUser", appUser, 0, 10);
			vehicleInfoBeanlist=signUpConverter.convertEntityListBeanList(vehicleInfoList);
			bean.setVehicleInfoList(vehicleInfoBeanlist);
			decorator.getResponseMap().put("data", bean);
			decorator.setResponseMessage("Driver Vehicle List");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		} else {
			throw new GenericException(
					"Please FullFill the Requriment of Vehicle Get List Request");
		}

	}

	@Override
	public void setDefaultVehicle(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		AppUserEntity appUser=null;
		AppUserVehicleEntity appUserVehicleInfo=null;
		signUpConverter.validatedefaultVehicle(decorator);
		if (decorator.getErrors().size() == 0) {
			try{
				appUser = vehicleDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
			if (appUser == null) {
				throw new GenericException("Please Provide the Valid User");
			}
			appUserVehicleInfo=vehicleDao.get(AppUserVehicleEntity.class,
					Integer.valueOf(bean.getAppUserVehicleId()));
			if (appUserVehicleInfo == null) {
				throw new GenericException("Please Provide the Valid App User Vehicle");
			}
			
			if(!checkAppUserVehicleInfoIsActive(appUser)){
				appUserVehicle.setIsActive("0");
				appUserVehicle.setToDate(DateUtil.now());
				vehicleDao.update(appUserVehicle);
				//
				appUserVehicleInfo.setIsActive(bean.getIsActive());
				vehicleDao.update(appUserVehicleInfo);
					
			}else{
				appUserVehicleInfo.setIsActive(bean.getIsActive());
				vehicleDao.update(appUserVehicleInfo);
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException(
					"Please FullFill the Requriment of Vehicle Default Request");
		}
	}

	@Override
	public void deleteVehicleInfo(SafeHerDecorator decorator)
			throws GenericException {
		VehicleInfoBean bean = (VehicleInfoBean) decorator.getDataBean();
		AppUserEntity appUser = null;
		AppUserVehicleEntity appUserVehicleInfo = null;
		VehicleInfoEntity vehicleInfoEntity =null;
		signUpConverter.validateDeleteVehicle(decorator);
		if (decorator.getErrors().size() == 0) {
			try {
				appUser = vehicleDao.get(AppUserEntity.class,
						Integer.valueOf(bean.getAppUserId()));
				if (appUser == null) {
					throw new GenericException("Please Provide the Valid User");
				}
				appUserVehicleInfo = vehicleDao.get(AppUserVehicleEntity.class,
						Integer.valueOf(bean.getAppUserVehicleId()));
				if (appUserVehicleInfo == null) {
					throw new GenericException(
							"Please Provide the Valid App User Vehicle");
				}
				vehicleInfoEntity=vehicleDao.get(VehicleInfoEntity.class,
						Integer.valueOf(bean.getVehicleInfoId()));
				if (vehicleInfoEntity == null) {
					throw new GenericException(
							"Please Provide the Valid Vehicle Info Id");
				}
				try {
					vehicleDao.delete(appUserVehicleInfo);
					vehicleDao.delete(vehicleInfoEntity);
				//	decorator.getResponseMap().put("data", bean);
					decorator.setResponseMessage("Unuse Vehicle Successfully Deleted");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				} catch (Exception ex) {
					vehicleInfoEntity.setIsActive("0");
					appUserVehicleInfo.setIsActive("0");
					if (vehicleDao.update(appUserVehicleInfo)) {
						if (vehicleDao.update(vehicleInfoEntity)) {
							decorator.setResponseMessage("Vehicle Successfully Deleted");
							decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
						} else {
							throw new GenericException("Vehicle Info Error");
						}
					} else {
						throw new GenericException(
								"App User Vehicle Info Error");
					}
				}

			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException("Server is not responding right now");
			}
		} else {
			throw new GenericException(
					"Please FullFill the Requriment of Delete Vehicle Request");
		}
	}

}
