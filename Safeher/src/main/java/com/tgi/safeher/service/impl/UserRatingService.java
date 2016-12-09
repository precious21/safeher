package com.tgi.safeher.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.dao.RideDao;
import com.tgi.safeher.entity.AppUserBiometricEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserCommentReportEntity;
import com.tgi.safeher.entity.UserRatingDetailEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.service.IUserRatingService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.DateUtil;

@Service
@Transactional
@Scope("prototype")
public class UserRatingService implements IUserRatingService {
	
	private static final Logger logger = Logger.getLogger(UserRatingService.class);
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private RideConverter rideConverter;

	@Autowired
	private RideDao rideDao;
	
	@Override
	public void detailUserRating(SafeHerDecorator decorator)
			throws GenericException {
	
		UserRatingDetailEntity userRatingDetail=null;
		UserCommentEntity userComment=null;
		List<UserCommentEntity> userCommentList=null;
		List<UserCommentEntity> userComments=null;
		List<UserRatingBean> userCommentbean =null;
		UserRatingEntity userRating=null;
		UserRatingBean userRatingBean = (UserRatingBean)  decorator.getDataBean();
		logger.info("Entering in detailUserRating with UserRatingBean "+userRatingBean );
		rideConverter.validateUserRating(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(userRatingBean.getAppUserId()));
			if (appUser.getIsDriver()==null) {
				throw new GenericException("Please Enter the valid App User Information");
			}else if(appUser.getIsDriver()!=null && appUser.getIsDriver().equals("0")) {
				decorator.setResponseMessage("Passenger Query Against Rating");
			}else{
				decorator.setResponseMessage("Driver Query Against Rating");
			}
			userRating=	appUserDao.findByOject(UserRatingEntity.class,"appUser",appUser);
			if(userRating==null){
				throw new GenericException("No Rating Against Driver Information");
			}
			if (userRatingBean.getRatingLimit() != null
					&& userRatingBean.getRatingLimitId() != null) {
				userCommentList = appUserDao.findListByOjectForRating(
						UserCommentEntity.class, "appUserByUserFor", appUser,
						Integer.valueOf(userRatingBean.getRatingLimitId()),
						Integer.valueOf(userRatingBean.getRatingLimit()));
			}else{
				userCommentList = appUserDao.findListByOjectForRating(
						UserCommentEntity.class, "appUserByUserFor", appUser,0,10);
			}
//			userComments=new ArrayList<UserCommentEntity>();
//				for(:userCommentList){
//					
//				}
			if (!CollectionUtil.isEmpty(userCommentList)) {
				userCommentbean=convertEntityToUserRatingList(userCommentList);
				userRatingBean.setUserRatingList(userCommentbean);
				userRatingBean.setUserRatingAvg(userRating.getCurrentValue().toString());
				userRatingBean.setUserRatingId(userRating.getUserRatingId().toString());
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
				decorator.getResponseMap().put("data", userRatingBean);
			}else{
				throw new GenericException("Driver Has no Passenger Rating!");
			}
			
		}else{
			throw new GenericException("Please Complete Basic Information Against User Rating!");
		}
	}

	private List<UserRatingBean> convertEntityToUserRatingList(
			List<UserCommentEntity> userCommentList) {
		List<UserRatingBean> userComment = new ArrayList<UserRatingBean>();

			for (UserCommentEntity userCommets : userCommentList) {
				UserRatingBean userRatingBean = new UserRatingBean();
				userRatingBean.setComment(userCommets.getComments());
				userRatingBean.setUserRatingDetailId(userCommets
						.getUserRatingDetail().getUserRatingDetailId().toString());
				userRatingBean.setUserByAppUserId(userCommets.getAppUserByUserBy()
						.getAppUserId().toString());
				userRatingBean.setUserToAppUserId(userCommets.getAppUserByUserFor()
						.getAppUserId().toString());
				userRatingBean.setUserByName(userCommets.getAppUserByUserBy()
						.getPerson().getFirstName()
						+ " "
						+ userCommets.getAppUserByUserBy().getPerson()
								.getLastName());
				String picUrl=getPicUrlByAppUser(userCommets.getAppUserByUserBy());
				if(picUrl!=null){
					userRatingBean.setUserUrlId(picUrl);
				}else{
					userRatingBean.setUserUrlId("''");
				}
				if(userCommets.getIsReported()!=null){
					userRatingBean.setIsReported(userCommets.getIsReported());
				}else{
					userRatingBean.setIsReported("0");
				}
				
				userRatingBean.setUserCommentId(userCommets.getUserCommentId()
						.toString());
				userRatingBean.setUserRating(userCommets.getRateValue().toString());
				userComment.add(userRatingBean);
			}
			return userComment;
		
	}
	
	public String getPicUrlByAppUser(AppUserEntity appUser) {
		AppUserBiometricEntity appUserEntity = rideDao.findByOject(
				AppUserBiometricEntity.class, "appUser", appUser);
		if (appUserEntity == null) {
			return "";
		}
		return appUserEntity.getPath();
	}

	@Override
	public void reportUserRating(SafeHerDecorator decorator)
			throws DataAccessException, Exception {

		logger.info("Report User Comment....");
		UserRatingDetailEntity userRatingDetail = null;
		UserCommentEntity userComment = null;
		UserRatingEntity userRating = null;
		UserCommentReportEntity userCommentReport=null;
		UserRatingBean userRatingBean = (UserRatingBean) decorator
				.getDataBean();
		rideConverter.validatereportingCommnet(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(userRatingBean.getAppUserId()));
			if (appUser.getIsDriver() == null) {
				throw new GenericException(
						"Please Enter the valid App User Information");
			}
			userComment = rideDao.get(UserCommentEntity.class,
					Integer.valueOf(userRatingBean.getUserCommentId()));
			if (userComment == null) {
				throw new GenericException(
						"Please Chos the Correct User Comment!");
			}
			userCommentReport = new UserCommentReportEntity();
			userCommentReport.setAppUser(appUser);
			userCommentReport.setUserComment(userComment);
			userCommentReport.setReportDate(DateUtil.now());
			if (userRatingBean.getComment() != null) {
				userCommentReport.setComment(userRatingBean.getComment());
			}
			if (rideDao.save(userCommentReport)) {
				userComment.setIsReported("1");
				if(rideDao.update(userComment)){
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
					decorator.setResponseMessage("User Report are Done");
				}
			}

		} else {
			throw new GenericException(
					"Please fullFill the Requriment of Report User Comment!");
		}

	}
}
