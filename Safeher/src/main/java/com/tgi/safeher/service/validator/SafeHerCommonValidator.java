package com.tgi.safeher.service.validator;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.UserImageBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
public class SafeHerCommonValidator implements Serializable {

	private static final long serialVersionUID = -3596192317997518209L;

	public void validateBeforeGeneratingCode(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		
		if (StringUtil.isEmpty(bean.getIsPromotion())) {
			decorator.getErrors().add(
					"Please proveide isPromotion for promotion and referral code");
			return;
		}else {
			if (bean.getIsPromotion().equals("0")) {
				if (StringUtil.isEmpty(bean.getAppUserId())) {
					decorator.getErrors().add("Please proveide appUserId");
					return;
				}
				if (bean.getReffUserList() == null || 
						bean.getReffUserList().size() <= 0) {
					decorator.getErrors().add("Please provide referral list");
					return;
				}
				if (bean.getReffUserList().size() > 1) {
					if (StringUtil.isEmpty(bean.getMaxUseCount())) {
						decorator.getErrors().add("Please proveide getMaxUseCount");
						return;	
					}
					bean.setIsSingle("0");
				}else{
					bean.setIsSingle("1");
				}

			}else if (bean.getIsPromotion().equals("1")) {
				if (StringUtil.isEmpty(bean.getIsSingle())) {
					decorator.getErrors().add("Please proveide isSingle");
					return;	
				}
				if (bean.getIsSingle().equals("0")) {
					if (StringUtil.isEmpty(bean.getMaxUseCount())) {
						decorator.getErrors().add("Please proveide getMaxUseCount");
						return;	
					}
				}else if (bean.getIsSingle().equals("1")) {
					bean.setMaxUseCount("0");
				}
			}
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please proveide isDriver");
			return;	
		}
		if (StringUtil.isEmpty(bean.getDurationInDays())) {
			decorator.getErrors().add("Please proveide duration in days");
			return;	
		}
		if (StringUtil.isEmpty(bean.getStartDate())) {
			decorator.getErrors().add("Please proveide start date");
			return;	
		}
		if (StringUtil.isEmpty(bean.getExpiryDate())) {
			decorator.getErrors().add("Please proveide expiry date");
			return;	
		}
		if (StringUtil.isEmpty(bean.getAmount())) {
			decorator.getErrors().add("Please proveide amount");
			return;	
		}
		if (StringUtil.isEmpty(bean.getPromDescription())) {
			decorator.getErrors().add("Please proveide promotion/referral description");
			return;	
		}

		if (bean.getIsDriver().equals("1")) {
			if (StringUtil.isEmpty(bean.getIsCount())) {
				decorator.getErrors().add("Please proveide isCount 1 or 0");
				return;	
			}
			if (bean.getIsCount().equals("1")) {
				if (StringUtil.isEmpty(bean.getCountValue())) {
					decorator.getErrors().add("Please proveide countValue");
					return;	
				}
			}	
		}else if (bean.getIsDriver().equals("0")) {
			bean.setIsCount(null);
			bean.setCountValue(null);
		}
		if (StringUtil.isEmpty(bean.getIsPartialUse())) {
			decorator.getErrors().add("Please proveide isPartialUse 1 or 0");
			return;	
		}
		if (bean.getIsPartialUse().equals("1")) {
			if (StringUtil.isEmpty(bean.getPartialValue())) {
				decorator.getErrors().add("Please proveide partialValue");
				return;	
			}
		}else if (bean.getIsPartialUse().equals("0")) {
			bean.setPartialValue(null);
		}

	}

	public void validateBeforeGeneratingCode2(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		
		if (StringUtil.isEmpty(bean.getIsPromotion())) {
			decorator.getErrors().add(
					"Please proveide isPromotion for promotion and referral code");
			return;
		}else {
//			if (bean.getIsPromotion().equals("0")) {
//
//			}else if (bean.getIsPromotion().equals("1")) {
//				
//			}
		}
		if (StringUtil.isEmpty(bean.getIsSingle())) {
			decorator.getErrors().add("Please proveide isSingle");
			return;	
		}
		if (bean.getIsSingle().equals("0")) {
			if (StringUtil.isEmpty(bean.getMaxUseCount())) {
				decorator.getErrors().add("Please proveide getMaxUseCount");
				return;	
			}
		}else if (bean.getIsSingle().equals("1")) {
			bean.setMaxUseCount("0");
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please proveide isDriver");
			return;	
		}
		if (StringUtil.isEmpty(bean.getDurationInDays())) {
			decorator.getErrors().add("Please proveide duration in days");
			return;	
		}
		if (StringUtil.isEmpty(bean.getStartDate())) {
			decorator.getErrors().add("Please proveide start date");
			return;	
		}
		if (StringUtil.isEmpty(bean.getExpiryDate())) {
			decorator.getErrors().add("Please proveide expiry date");
			return;	
		}
		if (StringUtil.isEmpty(bean.getAmount())) {
			decorator.getErrors().add("Please proveide amount");
			return;	
		}
		if (StringUtil.isEmpty(bean.getPromDescription())) {
			decorator.getErrors().add("Please proveide promotion/referral description");
			return;	
		}

		if (bean.getIsDriver().equals("1")) {
			if (StringUtil.isEmpty(bean.getIsCount())) {
				decorator.getErrors().add("Please proveide isCount 1 or 0");
				return;	
			}
			if (bean.getIsCount().equals("1")) {
				if (StringUtil.isEmpty(bean.getCountValue())) {
					decorator.getErrors().add("Please proveide countValue");
					return;	
				}
			}	
		}else if (bean.getIsDriver().equals("0")) {
			bean.setIsCount(null);
			bean.setCountValue(null);
		}
		if (StringUtil.isEmpty(bean.getIsPartialUse())) {
			decorator.getErrors().add("Please proveide isPartialUse 1 or 0");
			return;	
		}
		if (bean.getIsPartialUse().equals("1")) {
			if (StringUtil.isEmpty(bean.getPartialValue())) {
				decorator.getErrors().add("Please proveide partialValue");
				return;	
			}
		}else if (bean.getIsPartialUse().equals("0")) {
			bean.setPartialValue(null);
		}

	}

	public void validatePromotionBeforeConsume(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please proveide appUserId");
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please proveide isDriver");
		}
		if (StringUtil.isEmpty(bean.getIsPromotion())) {
			decorator.getErrors().add(
					"Please Provide isPromotion for promotion and referral code");
		}
		if (StringUtil.isNotEmpty(bean.getIsDriver())) {
			if (bean.getIsDriver().equals("1")) {
				if (StringUtil.isEmpty(bean.getPromotionInfoId())) {
					decorator.getErrors().add(
							"Please Provide Promotion Info Id");
				}
			} else if (bean.getIsDriver().equals("0")) {
				if (StringUtil.isEmpty(bean.getCodeValue())) {
					decorator.getErrors().add("Please Provide Promotion Code");
				}
			}
		}

	}

	public void validateGetpromotionValidate(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add(
					"Please Provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add(
					"Please Provide Is Driver");
		}
	}

	public void validatePromotionActive(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please Provide App User ID");
		}
	}

	public void validateBeforeGenerationgReferralCode(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please proveide appUserId");
			return;
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please proveide isDriver");
			return;
		}
		if (StringUtil.isEmpty(bean.getIsPromotion())) {
			decorator.getErrors().add(
					"Please Provide isPromotion");
			return;
		}
	}

	public void validateBeforeSendingReferralToFrnds(SafeHerDecorator decorator) {
		PromAndReffBean bean = (PromAndReffBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please proveide appUserId");
			return;
		}
		if (StringUtil.isEmpty(bean.getCodeValue())) {
			decorator.getErrors().add("Please proveide codeValue");
			return;
		}
		if (StringUtil.isEmpty(bean.getShareType())) {
			decorator.getErrors().add("Please proveide shareType");
			return;
		}
		if(!bean.getShareType().equalsIgnoreCase("S")){
			if (bean.getReffUserList() == null || 
					bean.getReffUserList().size() <= 0) {
				decorator.getErrors().add("Please provide referral list");
				return;
			}	
		}
	}

	public void validateRideTrackingJson(SafeHerDecorator decorator) {
		RideBean bean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please proveide appUserId");
			return;
		}
		if (StringUtil.isEmpty(bean.getIsDriver())) {
			decorator.getErrors().add("Please proveide isDriver");
			return;
		}
	}

	public void validateAppUserImage(SafeHerDecorator decorator) {
		UserImageBean bean = (UserImageBean) decorator.getDataBean();
		MultipartFile userImage = bean.getFile();
		if(userImage == null){
			decorator.getErrors().add("Please proveide uploading image");
			return;
		}
		if(userImage.getOriginalFilename().contains("_")){
			String[] imageNameSplit = userImage.
					getOriginalFilename().split("_");
			if(imageNameSplit.length < 3){
				decorator.getErrors().add("Please proveide appUserId and imageTypeFlag");
				return;
			}
			bean.setImageName(imageNameSplit[0]);
			bean.setAppUserId(imageNameSplit[1]);
			bean.setImageTypeFlag(imageNameSplit[2]);
			if(bean.getImageTypeFlag().contains(".")){
				bean.setImageTypeFlag(bean.getImageTypeFlag().substring(
						0, bean.getImageTypeFlag().lastIndexOf(".")));
			}
		}else{
			decorator.getErrors().add("Please proveide appUserId and imageTypeFlag");
			return;
		}
	}

}
