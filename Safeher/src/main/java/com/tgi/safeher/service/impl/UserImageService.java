package com.tgi.safeher.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.AppUserRegFlowBean;
import com.tgi.safeher.beans.UserImageBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.BioMetricTypeEnum;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.UserRegFlowEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.BiometricTypeEntity;
import com.tgi.safeher.entity.EletronicCategoryEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.UserElectronicResourceEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.rws.ElectronicResourceRws;
import com.tgi.safeher.service.IUserImageService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.service.validator.SafeHerCommonValidator;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.FileUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class UserImageService implements IUserImageService{
	private static final Logger logger = Logger.getLogger(UserImageService.class);
	@Autowired
	private DecoratorUtil commonUtill;
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private SignUpConverter signUpConverter;
	
	@Autowired
	private SafeHerCommonValidator commonValidator;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;

	@Override
	public void saveUserImage(SafeHerDecorator decorator) throws GenericException{
//		UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
		logger.info("******Entering in saveUserImage  ******");
		String message = FileUtil.copyFile(decorator);
		if(!message.equals("success")) {
			throw new GenericException( message );
		} else {
			decorator.setResponseMessage( "Image uploaded successfully" );
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}	
	}

	@Override
	public void saveDriverUserImage(SafeHerDecorator decorator) throws GenericException{
		UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
		logger.info("******Entering in saveUserImage  ******");
		if(StringUtil.isEmpty(imageBean.getAppUserId())){
			throw new GenericException("Please provide appUserId");
		}
		String message = FileUtil.copyFileForDriver(decorator);
		if(!message.equals("success")) {
			throw new GenericException( message );
		} else {
			//saveImageForPassenger
			if(StringUtil.isNotEmpty(imageBean.getMediaUrl())){
				AppUserEntity appUserEntity = appUserDao.findById(AppUserEntity.class,
						new Integer(imageBean.getAppUserId()));
				if(appUserEntity != null){
					AppUserBiometricEntity appUserBiometricEntity = appUserDao.findByIdParam(
							appUserEntity.getAppUserId());
					if(appUserBiometricEntity == null){
						appUserBiometricEntity = new AppUserBiometricEntity();
						BiometricTypeEntity biometricTypeEntity = new BiometricTypeEntity();
						biometricTypeEntity.setBiometricTypeId(BioMetricTypeEnum.Face.getValue());
						appUserBiometricEntity.setBiometricType(biometricTypeEntity);
					}
					signUpConverter.populateAppUserBiometricEntity(appUserBiometricEntity);
					appUserBiometricEntity.setPath(imageBean.getMediaUrl());
					appUserBiometricEntity.setAppUser(appUserEntity);
					if(appUserEntity.getPerson() != null){
						appUserBiometricEntity.setPerson(appUserEntity.getPerson());
					}
					appUserDao.saveOrUpdate(appUserBiometricEntity);

					//start asynchronous saving appUserRegFlow
					AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
					appUserRegFlowBean.setAppUserId(appUserEntity.getAppUserId()+"");
					appUserRegFlowBean.setIsFromApp(imageBean.getIsFromWindow());
					appUserRegFlowBean.setStepCode(
							UserRegFlowEnum.ProfileImage.getValue()+"");
					appUserRegFlowBean.setIsCompleted("1");
					asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
					//end asynchronous saving appUserRegFlow
				}
			}
			decorator.setResponseMessage( "Image uploaded successfully" );
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}	
	}

	@Override
	public void appUserImage(SafeHerDecorator decorator) throws GenericException{
		UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
		logger.info("******Entering in appUserImage  ****** with imageBean: "+imageBean);
		try{
			commonValidator.validateAppUserImage(decorator);
			if (decorator.getErrors().size() == 0) {
				AppUserEntity appUserEntity = appUserDao.findById(
						AppUserEntity.class, new Integer(imageBean.getAppUserId()));
				if(appUserEntity == null){
					throw new GenericException("App User dont exists");
				}
				String message = FileUtil.copyRegistrationFile(decorator);
				if(!message.equals("success")) {
					throw new GenericException( message );
				} else {
					//saveImageForPassenger
					if(StringUtil.isNotEmpty(imageBean.getMediaUrl())){
						if(appUserEntity != null){
							int dirc = 0;
							if(imageBean.getImageTypeFlag().equals("IPRO")){
								dirc = BioMetricTypeEnum.Face.getValue();
							}else if(imageBean.getImageTypeFlag().equals("ILIC")){
								dirc = BioMetricTypeEnum.Licence.getValue();
							}else if(imageBean.getImageTypeFlag().equals("IINS")){
								dirc = BioMetricTypeEnum.Insuranse.getValue();
							}else if(imageBean.getImageTypeFlag().equals("IREG")){
								dirc = BioMetricTypeEnum.Registration.getValue();
							}
							AppUserBiometricEntity appUserBiometricEntity = appUserDao.findByIdParam(
									appUserEntity.getAppUserId());
							if(appUserBiometricEntity == null){
								appUserBiometricEntity = new AppUserBiometricEntity();
								BiometricTypeEntity biometricTypeEntity = new BiometricTypeEntity();
								if(dirc != 0){
									biometricTypeEntity.setBiometricTypeId(dirc);
								}
								appUserBiometricEntity.setBiometricType(biometricTypeEntity);
							}
							signUpConverter.populateAppUserBiometricEntity(appUserBiometricEntity);
							appUserBiometricEntity.setPath(imageBean.getMediaUrl());
							appUserBiometricEntity.setAppUser(appUserEntity);
							if(appUserEntity.getPerson() != null){
								appUserBiometricEntity.setPerson(appUserEntity.getPerson());
							}
							appUserDao.saveOrUpdate(appUserBiometricEntity);
						}
					}
//					decorator.getResponseMap().put("data", imageBean);
					decorator.getResponseMap().put("userImageUrl", imageBean.getMediaUrl());
					decorator.setResponseMessage( "Image uploaded successfully" );
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
				}	
			}else{
				throw new GenericException("Image uploading failed");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from userSingIn with Exception "+e.getMessage()+"  ******");
			throw new GenericException("Server is not responding right now");
		}
	}

	@Override
	public void saveElectronicsResource(SafeHerDecorator decorator) throws GenericException{
		
		String message = FileUtil.copyFileForElectronicResource(decorator);
		if(!message.equals("success")) {
			throw new GenericException( message );
		} else {
			//codeSaveImagesToDataBase
			String messageRes = saveImagesToDataBase(decorator);
			if(!messageRes.equals("success")) {
				throw new GenericException( message );
			} else {
				decorator.setResponseMessage( "Image uploaded successfully" );
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}
		}	
	}

	@Override
	public void saveElectronicsResourceV2(SafeHerDecorator decorator) throws GenericException{

		UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
		logger.info("******Entering in appUserImage  ****** with imageBean: "+imageBean);
		if(StringUtil.isEmpty(imageBean.getAppUserId())){
			throw new GenericException("Please provide appUserId");
		}
		if(StringUtil.isEmpty(imageBean.getFileType())){
			throw new GenericException("Please provide fileType");
		}
		if(imageBean.getFileArray() == null || 
				imageBean.getFileArray().length < 0){
			throw new GenericException("Please provide image");
		}
		String message = FileUtil.copyFileForElectronicResource(decorator);
		if(!message.equals("success")) {
			throw new GenericException( message );
		} else {
			//codeSaveImagesToDataBase
			String messageRes = saveImagesToDataBaseV2(decorator);
			if(!messageRes.equals("success")) {
				throw new GenericException( message );
			} else {
				
				//start asynchronous saving appUserRegFlow
				AppUserRegFlowBean appUserRegFlowBean = new AppUserRegFlowBean();
				appUserRegFlowBean.setAppUserId(imageBean.getAppUserId());
				appUserRegFlowBean.setIsFromApp(imageBean.getIsFromWindow());
				int userRegFlow = 0;
				if(imageBean.getFileType().equals("1")){
//					userRegFlow = UserRegFlowEnum.Quali.getValue();
				}else if(imageBean.getFileType().equals("2")){
					userRegFlow = UserRegFlowEnum.License.getValue();
				}else if(imageBean.getFileType().equals("3")){
					userRegFlow = UserRegFlowEnum.Insuranse.getValue();
				}else if(imageBean.getFileType().equals("4")){
					userRegFlow = UserRegFlowEnum.Registraion.getValue();
				}
				if(userRegFlow > 0){
					appUserRegFlowBean.setStepCode(userRegFlow+"");
				}
//				appUserRegFlowBean.setIsCompleted("1");
				asyncServiceImpl.saveSignUpFlow(appUserRegFlowBean);
				//end asynchronous saving appUserRegFlow
				
				decorator.setResponseMessage( "Image uploaded successfully" );
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}
		}	
	}
	
	public String saveImagesToDataBase(SafeHerDecorator decorator) throws GenericException{
		
		String returnMessage = "success";
		
		List list = ((UserImageBean)decorator.getDataBean()).getUrlList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String path = (String) list.get(i);
				UserElectronicResourceEntity userElectronicResourceEntity = new UserElectronicResourceEntity();
		        String message = convertbeanToElecResEnt(userElectronicResourceEntity, path);
        		//pair.getValue().toString().substring(pair.getValue().toString().lastIndexOf("/"))
		        if(!message.equals("success")) {
					throw new GenericException( message );
				}
			}
//			decorator.getResponseMap().put("userImageUrl", imageBean.getUrlList());
		}
		return returnMessage;
	}
	
	public String saveImagesToDataBaseV2(SafeHerDecorator decorator) throws GenericException{
		
		String returnMessage = "success";
		
		List list = ((UserImageBean)decorator.getDataBean()).getUrlList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String path = (String) list.get(i);
				((UserImageBean)decorator.getDataBean()).setMediaUrl(path);
				UserElectronicResourceEntity userElectronicResourceEntity = new UserElectronicResourceEntity();
		        String message = convertbeanToElecResEntV2(userElectronicResourceEntity, decorator);
		        if(!message.equals("success")) {
					throw new GenericException( message );
				}
			}
//			decorator.getResponseMap().put("userImageUrl", imageBean.getUrlList());
		}
		return returnMessage;
	}
	
	public String convertbeanToElecResEnt(UserElectronicResourceEntity entity, String imgName){
		
		String message = "success";
        AppUserEntity appUserEntity = new AppUserEntity();
        EletronicCategoryEntity eletronicCategoryEntity = new EletronicCategoryEntity();
//      String[] imageNameArr = imgName.substring(imgName.lastIndexOf("\\")).split("-");
        String[] imageNameArr = imgName.substring(imgName.lastIndexOf("/")).split("-");
        if(imageNameArr != null && imageNameArr.length > 0){
        	appUserEntity.setAppUserId(new Integer(imageNameArr[1]));
        	eletronicCategoryEntity.setEletronicCategoryId(new Integer(imageNameArr[2]));
//        	entity = appUserDao.findByOject(UserElectronicResourceEntity.class,
//        			"appUser", appUserEntity);
//        	if(entity == null){
//        		entity = new UserElectronicResourceEntity();
//        	}
        	entity.setAppUser(appUserEntity);
        	entity.setEletronicCategory(eletronicCategoryEntity);
        	entity.setCreatedDate(DateUtil.getCurrentTimestamp());
        	entity.setProvidedDate(DateUtil.getCurrentTimestamp());
        	entity.setEndDate(DateUtil.getCurrentTimestamp());
        	entity.setIsActive("1");
        	entity.setPath(imgName);
        	entity.setHashcode(FileUtil.getBytesFromFilePath(
        			imgName.substring(imgName.lastIndexOf("/")+1)));
//        	entity.setHashcode(FileUtil.
//        			getBytesFromFilePath(imgName));
	        appUserDao.saveOrUpdate(entity);	
        }else{
        	message = "Image Uploading failed";
        }
        return message;
	}
	
	public String convertbeanToElecResEntV2(UserElectronicResourceEntity entity, SafeHerDecorator decorator){
		UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
		String message = "success";
        AppUserEntity appUserEntity = new AppUserEntity();
        EletronicCategoryEntity eletronicCategoryEntity = new EletronicCategoryEntity();
    	appUserEntity.setAppUserId(new Integer(imageBean.getAppUserId()));

		int dirc = 0;
		if(imageBean.getFileType().equals("1")){
			dirc = BioMetricTypeEnum.Vehical.getValue();
		}else if(imageBean.getFileType().equals("2")){
			dirc = BioMetricTypeEnum.Licence.getValue();
		}else if(imageBean.getFileType().equals("3")){
			dirc = BioMetricTypeEnum.Insuranse.getValue();
		}else if(imageBean.getFileType().equals("4")){
			dirc = BioMetricTypeEnum.Registration.getValue();
		}
		if(dirc > 0){
	    	eletronicCategoryEntity.setEletronicCategoryId(dirc);	
		}
		entity = appUserDao.findElectronicRes(
				new Integer(imageBean.getAppUserId()), dirc);
		if(entity == null){
			entity = new UserElectronicResourceEntity();
		}
    	entity.setAppUser(appUserEntity);
    	entity.setEletronicCategory(eletronicCategoryEntity);
    	entity.setCreatedDate(DateUtil.getCurrentTimestamp());
    	entity.setProvidedDate(DateUtil.getCurrentTimestamp());
    	entity.setEndDate(DateUtil.getCurrentTimestamp());
    	entity.setIsActive("1");
    	entity.setPath(imageBean.getMediaUrl());
    	entity.setHashcode(FileUtil.getBytesFromFilePath(
    			imageBean.getMediaUrl().substring(imageBean.getMediaUrl().lastIndexOf("/")+1)));
//        	entity.setHashcode(FileUtil.
//        			getBytesFromFilePath(imgName));
        appUserDao.saveOrUpdate(entity);
        return message;
	}
	
	public String getAppUserId(String userImageName){
		if(userImageName.contains("-")){
			if(StringUtil.isNotEmpty(userImageName)){
				String[] userNameArr = userImageName.split("-");
				return userNameArr[1];
			}
		}
		return "";
	}

	@Override
	public void fetchElectronicsResource(SafeHerDecorator decorator)
			throws GenericException {
		UserImageBean bean = (UserImageBean) decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			
			List<UserElectronicResourceEntity> list = appUserDao.getAllUserElec(
					new Integer(bean.getAppUserId()),  new Integer(bean.getFileType()));
			if(list != null && list.size() > 0){
				signUpConverter.populateUserElecRes(decorator, list);
				decorator.getResponseMap().put("data", bean.getImageList());
//				decorator.setResponseMessage("Location saved successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}
		}else{
			decorator.setResponseMessage("User don't exists");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
	}

	@Override
	public void removeElectronicsResource(SafeHerDecorator decorator)
			throws GenericException {

		UserImageBean bean = (UserImageBean) decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getUserElecResourceId())){
			
			UserElectronicResourceEntity entity = appUserDao.findById(
					UserElectronicResourceEntity.class, new Integer(bean.getUserElecResourceId()));
			if(entity != null){
				//deleteFile from temp server
				String message = FileUtil.removeElcRes(entity.getPath());
				if(!message.equals("success")) {
					throw new GenericException( message );
					//decorator.setResponseMap(Map<String, Object>);
				}else{
					//deleteFile from server
					String message2 = Common.removeFileFromMainServer(
							entity.getPath(), "ELECTRONICS_RESOURCE_SERVER", "electronicResources");
					if(!message2.equals("success")) {
						throw new GenericException( message );
						//decorator.setResponseMap(Map<String, Object>);
					}else{
						appUserDao.delete(entity);
						decorator.setResponseMessage("Deleted successfully");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
					}
				}
				
			}else{
				decorator.setResponseMessage("Delete operation is failed");
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			}
		}else{
			decorator.setResponseMessage("Delete operation is failed");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
	}
}
