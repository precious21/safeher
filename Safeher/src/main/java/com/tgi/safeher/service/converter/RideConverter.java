package com.tgi.safeher.service.converter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.GeneralLedgerBean;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.beans.RideSearchResultBean;
import com.tgi.safeher.beans.UserRatingBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.LedgerEntryTypeEnum;
import com.tgi.safeher.common.enumeration.LedgerOwnerTypeEnum;
import com.tgi.safeher.common.enumeration.StatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.AppUserTypeEntity;
import com.tgi.safeher.entity.PostRideEntity;
import com.tgi.safeher.entity.PromotionInfoEntity;
import com.tgi.safeher.entity.PromotionType;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideEntity;
import com.tgi.safeher.entity.RideGiftEntity;
import com.tgi.safeher.entity.RidePaymnetDistributionEntity;
import com.tgi.safeher.entity.StatusEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserPromotionEntity;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
public class RideConverter implements Serializable {

	public void validateRideCriteria(SafeHerDecorator decorator) {

		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getSourceLat())) {
			decorator.getErrors().add("Please provide Source Lat");
		}
		if (StringUtil.isEmpty(bean.getSourceLong())) {
			decorator.getErrors().add("Please provide source Long ");
		}
		if (StringUtil.isEmpty(bean.getSourceAddress())) {
			decorator.getErrors().add("Please provide source Address String ");
		}
		if (StringUtil.isEmpty(bean.getSearchFromLat())) {
			decorator.getErrors().add("Please provide Search Lat");
		}
		if (StringUtil.isEmpty(bean.getSearchFromLong())) {
			decorator.getErrors().add("Please provide Serach Lang");
		}
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getNoPassenger())) {
			decorator.getErrors().add("Please provide No passenger");
		}
		if (StringUtil.isEmpty(bean.getRideTypeId())) {
			decorator.getErrors().add("Please provide Ride Type");
		}
		// if (StringUtil.isEmpty(bean.getRideCategoryId())) {
		// decorator.getErrors().add("Please provide Service Catagory");
		// }

		if (StringUtil.isEmpty(bean.getDistinatonLat())) {
			decorator.getErrors().add("Please provide Destination Lat");
		}
		if (StringUtil.isEmpty(bean.getDistinatonLong())) {
			decorator.getErrors().add("Please provide Destination Lang");
		}
		if (StringUtil.isEmpty(bean.getDistinatonAddress())) {
			decorator.getErrors().add(
					"Please provide Distination Address String ");
		}
	}

	public void validateRideSearch(SafeHerDecorator decorator) {
		RideSearchResultBean bean = (RideSearchResultBean) decorator
				.getDataBean();

		if (StringUtil.isEmpty(bean.getDriverLat())) {
			decorator.getErrors().add("Please provide Source Lat");
		}
		if (StringUtil.isEmpty(bean.getDriverLong())) {
			decorator.getErrors().add("Please provide source Long ");
		}

	}

	public RideSearchResultBean convertRideCriteriaToRideSearchResult(
			RideCriteriaBean rideCriteria) {

		RideSearchResultBean bean = new RideSearchResultBean();
		bean.setRideCriteriaEntityId(rideCriteria.getRideCriteriaId());
		bean.setRideSearchEntityId(rideCriteria.getRideSearchId());
		bean.setAppUserId(rideCriteria.getAppUserId());
		bean.setRequestNo(rideCriteria.getRequestNo());
		return bean;
	}

	public List<RideRequestResponseBean> convertRideSearchToRequestResponceBean(
			SafeHerDecorator decorator) {

		RideSearchResultBean rideSearchBean = (RideSearchResultBean) decorator
				.getDataBean();
		RideRequestResponseBean reqtBean = null;
		RideRequestResponseBean rideRequestResponceBean = null;
		List<RideRequestResponseBean> rideRequestList = new ArrayList<RideRequestResponseBean>();
		for (RideSearchResultBean rSRBean : rideSearchBean
				.getDriverPushNotification()) {

			rideRequestResponceBean = new RideRequestResponseBean();
			rideRequestResponceBean.setPassengerReqId(rSRBean.getAppUserId());
			rideRequestResponceBean.setRideSearchResultDetailId(rSRBean
					.getRideSearchResultDetailId());
			rideRequestResponceBean
					.setRequestTime(DateUtil.now().toGMTString());
			rideRequestList.add(rideRequestResponceBean);
		}

		return rideRequestList;
	}

	public DistanceAPIBean validateRideCrit(RideCriteriaEntity rideEntity) {
		DistanceAPIBean bean = new DistanceAPIBean();
		if (StringUtil.isNotEmpty(rideEntity.getSourceLat())) {
			bean.setLatOrigins(Double.valueOf(rideEntity.getSourceLat()));
		}
		if (StringUtil.isNotEmpty(rideEntity.getSourceLong())) {
			bean.setLngOrigins(Double.valueOf(rideEntity.getSourceLong()));
		}
		if (rideEntity.getAppUser() != null) {
			if (StringUtil.isNotEmpty(rideEntity.getAppUser().getAppUserId()
					+ "")) {
				bean.setAppUserId(rideEntity.getAppUser().getAppUserId());
			}
		}

		if (StringUtil.isNotEmpty(rideEntity.getDestinationLat())) {
			bean.setLatDestinations(Double.valueOf(rideEntity
					.getDestinationLat()));
		}
		if (StringUtil.isNotEmpty(rideEntity.getDestinationLong())) {
			bean.setLngDestinations(Double.valueOf(rideEntity
					.getDestinationLong()));
		}
		if (StringUtil.isNotEmpty(rideEntity.getNoChild().toString())) {
			bean.setNoChild(rideEntity.getNoChild().toString());
		}
		if (StringUtil.isNotEmpty(rideEntity.getNoPassenger().toString())) {
			bean.setNoPassengers(rideEntity.getNoPassenger().toString());
		}
		if (StringUtil.isNotEmpty(rideEntity.getNoSeats().toString())) {
			bean.setNoSeat(rideEntity.getNoSeats().toString());
		}

		if (StringUtil.isNotEmpty(rideEntity.getSourceAddress())) {
			bean.setOrigins(rideEntity.getSourceAddress());
		}
		if (StringUtil.isNotEmpty(rideEntity.getDistinationAddress())) {
			bean.setDestinations(rideEntity.getDistinationAddress());
		}

		return bean;
	}

	public void validatePassengerInfo(SafeHerDecorator decorator) {
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(rideCriteria.getAppUserId())) {
			decorator.getErrors().add("Please provide App User");
			return;
		}
		if (StringUtil.isEmpty(rideCriteria.getRequestNo())) {
			decorator.getErrors().add("Please provide Ride Request no");
			return;
		}

	}

	public void validatePreRideStart(SafeHerDecorator decorator) {
		PreRideRequestBean bean = (PreRideRequestBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getPreRideId())) {
			decorator.getErrors().add("Please provide Pre Ride ID");
		}
		if (StringUtil.isEmpty(bean.getPassengerappUserId())) {
			decorator.getErrors().add("Please provide Passenger App User ID");
		}

	}

	public void validateRideStart(SafeHerDecorator decorator) {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(ridebean.getPreRideId())) {
			decorator.getErrors().add("Please provide Pre Ride ID");
		}
		if (StringUtil.isEmpty(ridebean.getStartTime())) {
			decorator.getErrors().add("Please provide Ride Start Time");
		}
		if (StringUtil.isEmpty(ridebean.getEstimatedDistance())) {
			decorator.getErrors().add("Please provide Ride Estimated Distance");
		}
		if (StringUtil.isEmpty(ridebean.getEstimatedDuration())) {
			decorator.getErrors().add("Please provide Ride Estimated Duration");
		}
		if (StringUtil.isEmpty(ridebean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User Id");
		}
	}

	public void validateEndRide(SafeHerDecorator decorator) {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(ridebean.getIsCompleted())) {
			decorator.getErrors().add("Please provide Is Ride Complete??");
		} else {
			if (ridebean.getIsCompleted().equals("1")) {
				if (StringUtil.isEmpty(ridebean.getRideEndTime())) {
					decorator.getErrors().add("Please provide Ride End Time?");
				}
				if (StringUtil.isEmpty(ridebean.getRideNo())) {
					decorator.getErrors().add("Please provide Ride No");
				}
				if (StringUtil.isEmpty(ridebean.getActualDistance())) {
					decorator.getErrors().add(
							"Please provide Actual Ride Estimated Distance");
				}
				if (StringUtil.isEmpty(ridebean.getActualDuration())) {
					decorator.getErrors().add(
							"Please provide Actual Ride Estimated Duration");
				}
				if (StringUtil.isEmpty(ridebean.getRideEndTime())) {
					decorator.getErrors().add(
							"Please provide Actual Arrival Time");
				}
				// if (StringUtil.isEmpty(ridebean.getIdelTime())) {
				// decorator.getErrors().add(
				// "Please provide Idel Time If Exist");
				// }
				if (StringUtil.isEmpty(ridebean.getAppUserByAppUserDriver())) {
					decorator.getErrors().add(
							"Please provide Driver App Uesr No");
				}
				if (StringUtil.isEmpty(ridebean.getAppUserByAppUserPassenger())) {
					decorator.getErrors().add(
							"Please provide Passenger App Uesr No");
				}
				if (StringUtil.isEmpty(ridebean.getFineAmount())) {
					decorator.getErrors().add("Please provide Fine Ammount");
				}
				if (StringUtil.isEmpty(ridebean.getTotalAmount())) {
					decorator.getErrors().add("Please provide Fine Ammount");
				}
				if (StringUtil.isEmpty(ridebean.getRideAmount())) {
					decorator.getErrors().add("Please provide Ride  Ammount");
				}
			} else {
				decorator.getErrors().add("Please First Complete the Ride");
			}
		}

	}

	public void validateInvoiceInfo(SafeHerDecorator decorator) {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(ridebean.getRideNo())) {
			decorator.getErrors().add("Please provide Ride No");
		}
		if (StringUtil.isEmpty(ridebean.getInvoiceNo())) {
			decorator.getErrors().add("Please provide Invoice No");
		}

	}

	public void validateConfirmRide(SafeHerDecorator decorator) {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(ridebean.getRideNo())) {
			decorator.getErrors().add("Please provide Ride No");
		}
		if (StringUtil.isEmpty(ridebean.getInvoiceNo())) {
			decorator.getErrors().add("Please provide Invoice No");
		}
		if (StringUtil.isEmpty(ridebean.getAppUserByAppUserPassenger())) {
			decorator.getErrors().add("Please provide Passenger User Id ");
		}
		if (StringUtil.isEmpty(ridebean.getTotalAmount())) {
			decorator.getErrors().add("Please provide Total Amount  ");
		}
		if (StringUtil.isEmpty(ridebean.getTipAmount())) {
			decorator.getErrors().add("Please provide Tip Amount");
		}
		if (StringUtil.isEmpty(ridebean.getNonce())) {
			decorator.getErrors().add("Please provide Nounce");
		}
	}

	public void vaildateIdentity(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
			decorator.getErrors().add("Please Provide Your App User Id ");

		}
	}

	public void vaildateTransaction(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
			decorator.getErrors().add(
					"Please provide your app user passenger id ");
		}
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			decorator.getErrors()
					.add("Please provide your app user driver id ");
		}
		if (StringUtil.isEmpty(rideBean.getTotalAmount())) {
			decorator.getErrors().add("Please Provide total payment ");
		}
		if (StringUtil.isEmpty(rideBean.getNonce())) {
			decorator.getErrors().add("Please Provide payment nonce");
		}
	}

	public void vaildateLedgerEnteries(SafeHerDecorator decorator) {
		GeneralLedgerBean generalLedgerBean = (GeneralLedgerBean) decorator
				.getDataBean();
		if (StringUtil.isEmpty(generalLedgerBean.getRideBillId())) {
			decorator.getErrors().add("Please provide Ride Bill Information");
		}
		if (StringUtil.isEmpty(generalLedgerBean.getAmount())) {
			decorator.getErrors()
					.add("Please provide Total Amount Information");
		}
		if (StringUtil.isEmpty(generalLedgerBean.getRidePaymnetDistribution())) {
			decorator.getErrors().add(
					"Please provide Ride Payment distribution Information");
		}
		if (StringUtil.isEmpty(generalLedgerBean.getLedgerEntryTypeId())) {
			decorator.getErrors().add(
					"Please provide Ride Payment Ledger Entry Information");
		}
		if (StringUtil.isEmpty(generalLedgerBean.getLedgerOwnerType())) {
			decorator.getErrors().add(
					"Please provide Ride Payment Ledger Owner Information");
		}

	}

	public GeneralLedgerBean convertEntityToBeanForDriver(RidePaymnetDistributionEntity ridePaymentDistributionEntity) {
		GeneralLedgerBean generalLedgerBean = new GeneralLedgerBean();
		generalLedgerBean.setAmount(ridePaymentDistributionEntity.getDriverAmount().toString());
		generalLedgerBean.setLedgerOwnerType(LedgerOwnerTypeEnum.Driver.toString());
		generalLedgerBean.setLedgerEntryTypeId(LedgerEntryTypeEnum.BillPayment.toString());
		generalLedgerBean.setRidePaymnetDistribution(ridePaymentDistributionEntity.getRidePaymnetDistributionId()!=null ? ridePaymentDistributionEntity.getRidePaymnetDistributionId()+"" : "");
		generalLedgerBean.setRideBillId(ridePaymentDistributionEntity.getRideBill().getRideBillId() != null ? ridePaymentDistributionEntity.getRideBill().getRideBillId()+"" : "" );
		generalLedgerBean.setAppUserId(ridePaymentDistributionEntity.getRideBill().getAppUserByAppUserDriver().getAppUserId()+"");
		return generalLedgerBean;
	}

	public GeneralLedgerBean convertEntityToBeanForPassenger(RidePaymnetDistributionEntity ridePaymentDistributionEntity) {
		GeneralLedgerBean generalLedgerBean = new GeneralLedgerBean();
		Double totalAmout = (ridePaymentDistributionEntity.getDriverAmount()+ ridePaymentDistributionEntity.getCharityAmount() + ridePaymentDistributionEntity.getCompanyAmount());
		generalLedgerBean.setAmount(totalAmout.toString());
		generalLedgerBean.setLedgerOwnerType(LedgerOwnerTypeEnum.Passenger.toString());
		generalLedgerBean.setLedgerEntryTypeId(LedgerEntryTypeEnum.BillPayment.toString());
		generalLedgerBean.setRidePaymnetDistribution(ridePaymentDistributionEntity.getRidePaymnetDistributionId()!=null ? ridePaymentDistributionEntity.getRidePaymnetDistributionId()+"" : "");
		generalLedgerBean.setRideBillId(ridePaymentDistributionEntity.getRideBill().getRideBillId() != null ? ridePaymentDistributionEntity.getRideBill().getRideBillId()+"" : "");
		generalLedgerBean.setAppUserId(ridePaymentDistributionEntity.getRideBill().getAppUserByAppUserPassenger().getAppUserId()+"");
		return generalLedgerBean;
	}

	public GeneralLedgerBean convertEntityToBeanForSafeHer(RidePaymnetDistributionEntity ridePaymentDistributionEntity) {
		GeneralLedgerBean generalLedgerBean = new GeneralLedgerBean();
		generalLedgerBean.setAmount(ridePaymentDistributionEntity.getCompanyAmount().toString());
		generalLedgerBean.setLedgerOwnerType(LedgerOwnerTypeEnum.SafeHer.toString());
		generalLedgerBean.setLedgerEntryTypeId(LedgerEntryTypeEnum.BillPayment.toString());
		generalLedgerBean.setRidePaymnetDistribution(ridePaymentDistributionEntity.getRidePaymnetDistributionId()!=null ? ridePaymentDistributionEntity.getRidePaymnetDistributionId()+"" : "");
		generalLedgerBean.setRideBillId(ridePaymentDistributionEntity.getRideBill().getRideBillId() != null ? ridePaymentDistributionEntity.getRideBill().getRideBillId()+"" : "");
		return generalLedgerBean;
	}

	public GeneralLedgerBean convertEntityToBeanForCharity(RidePaymnetDistributionEntity ridePaymentDistributionEntity) {
		GeneralLedgerBean generalLedgerBean = new GeneralLedgerBean();
		generalLedgerBean.setAmount(ridePaymentDistributionEntity.getCharityAmount().toString());
		generalLedgerBean.setLedgerOwnerType(LedgerOwnerTypeEnum.Charity.toString());
		generalLedgerBean.setLedgerEntryTypeId(LedgerEntryTypeEnum.BillPayment.toString());
		generalLedgerBean.setRidePaymnetDistribution(ridePaymentDistributionEntity.getRidePaymnetDistributionId()!=null ? ridePaymentDistributionEntity.getRidePaymnetDistributionId()+"" : "");
		generalLedgerBean.setRideBillId(ridePaymentDistributionEntity.getRideBill().getRideBillId() != null ? ridePaymentDistributionEntity.getRideBill().getRideBillId()+"" : "");
		return generalLedgerBean;
	}

	public void vaildateLatestShare(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();

		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			decorator.getErrors()
					.add("Please provide your app user driver id ");
		}

	}

	public void validateUserRating(SafeHerDecorator decorator) {
		UserRatingBean bean = (UserRatingBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App USER iD!! :-) ");
		}
		if (StringUtil.isEmpty(bean.getRatingLimitId())) {
			decorator.getErrors().add(
					"Please provide Rating Limit to be Send :-) ");
		}
		if (StringUtil.isEmpty(bean.getRatingLimit())) {
			decorator.getErrors().add("Please provide Rating Limt iD!! :-) ");
		}
	}

	public List<UserRatingBean> convertEntityToUserRatingList(
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
			userRatingBean.setUserCommentId(userCommets.getUserCommentId()
					.toString());
			userRatingBean.setUserRating(userCommets.getRateValue().toString());
			userComment.add(userRatingBean);
		}
		return userComment;
	}

	public void vaildateShortEarning(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();

		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			decorator.getErrors()
					.add("Please provide your app user driver id ");
		}
		if (StringUtil.isEmpty(rideBean.getStartTime())) {
			decorator.getErrors().add("Please provide Start Time");
		}
		if (StringUtil.isEmpty(rideBean.getEndTime())) {
			decorator.getErrors().add("Please provide End Time");
		}
	}

	public void vaildateRides(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();

		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
				decorator.getErrors().add("Please provide your app user  id ");
			}
		}
		if (StringUtil.isEmpty(rideBean.getStartTime())) {
			decorator.getErrors().add("Please provide Start Time");
		}
		if (StringUtil.isEmpty(rideBean.getEndTime())) {
			decorator.getErrors().add("Please provide End Time");
		}
		if (StringUtil.isEmpty(rideBean.getFirstIndex())) {
			decorator.getErrors().add("Please provide First index");
		}
		if (StringUtil.isEmpty(rideBean.getMaxResult())) {
			decorator.getErrors().add("Please provide Max Result");
		}
	}

	public List<RideBean> prepareBeanFromEntity(
			List<RideBillEntity> rideBillList) throws GenericException {
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		if (rideBillList == null) {
			throw new GenericException("Driver has no recent Rides");
		}
		for (RideBillEntity rideBillEntityListObj : rideBillList) {
			RideBean rideBeanListObj = new RideBean();
			rideBeanListObj.setRideNo(rideBillEntityListObj.getRideInfoId()
					.getRideNo());
			// rideBeanListObj.setPaymentModeName(rideBillEntityListObj.getPaymentMode().getName());
			// /
			// rideBeanListObj.setPaymentModeId(rideBillEntityListObj.getPaymentMode().getPaymentModeId().toString());
			rideBeanListObj.setAppUserByAppUserPassenger(rideBillEntityListObj
					.getAppUserByAppUserPassenger().getAppUserId().toString());
			rideBeanListObj.setAppUserByAppUserDriver(rideBillEntityListObj
					.getAppUserByAppUserDriver().getAppUserId().toString());
			rideBeanListObj.setDriverFirstName(rideBillEntityListObj
					.getAppUserByAppUserDriver().getPerson().getFirstName());
			rideBeanListObj.setLastName(rideBillEntityListObj
					.getAppUserByAppUserDriver().getPerson().getLastName());
			// rideBeanListObj.setDestinationLat(rideBillEntityListObj
			// .getRideInfoId().getRideCriteria().getDestinationLat());
			rideBeanListObj
					.setDistinationAddress("This is Distination Address...rt ....");
			// rideBeanListObj.setDestinationLong(rideBillEntityListObj
			// .getRideInfoId().getRideCriteria().getDestinationLong());
			// rideBeanListObj.setSourceLat(rideBillEntityListObj.getRideInfoId()
			// .getRideCriteria().getSourceLat());
			// rideBeanListObj.setSourceLong(rideBillEntityListObj.getRideInfoId()
			// .getRideCriteria().getSourceLong());
			rideBeanListObj
					.setSourceAddress("This is Source Address.... fxc .. ");
			rideBeanListObj.setIsCompleted(rideBillEntityListObj
					.getRideInfoId().getIsCompleted());
			rideBeanListObj.setIsCancel(rideBillEntityListObj.getRideInfoId()
					.getIsCancel());
			rideBeanListObj.setTotalAmount(rideBillEntityListObj
					.getTotalAmount().toString());
			// rideBeanListObj.setRideTypeId(rideBillEntityListObj.getRideInfoId().getRideTypeId().toString());
			rideBeanListObj.setRideEndTime(rideBillEntityListObj
					.getRideInfoId().getEndTime().toString());
			rideBeanListObj.setRideStartTime(rideBillEntityListObj
					.getRideInfoId().getStartTime().toString());
			rideBeanList.add(rideBeanListObj);

		}
		return rideBeanList;
	}

	public void vaildateRideInfo(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
				decorator.getErrors().add("Please provide your app user  id ");
			}
		}
		if (StringUtil.isEmpty(rideBean.getRideNo())) {
			decorator.getErrors().add("Please provide Ride No");
		}
	}

	public RideBean populateRideBeanFromObjForPassenger(RideEntity rideEntity,
			RideBillEntity rideBillEntity) {
		RideBean rideBeanObj = new RideBean();
		rideBeanObj.setEstimatedArrival(rideEntity.getEstimatedArrival());
		rideBeanObj.setEstimatedDuration(rideEntity.getEstimatedDuration());
		rideBeanObj.setEstimatedDistance(rideEntity.getEstimatedDistance()
				.toString());
		rideBeanObj.setStartTime(rideEntity.getStartTime().toString());
		rideBeanObj.setEndTime(rideEntity.getEndTime().toString());
		rideBeanObj.setIsCompleted(rideEntity.getIsCompleted());
		rideBeanObj.setRideTypeId(rideEntity.getRideTypeId().toString());
		rideBeanObj.setFirstName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getFirstName());
		rideBeanObj.setLastName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getLastName());
		rideBeanObj.setAppUserByAppUserDriver(rideBillEntity
				.getAppUserByAppUserDriver().getAppUserId().toString());
		rideBeanObj.setActualChild(rideEntity.getRideCriteria().getNoChild()
				.toString());
		rideBeanObj.setActualPerson(rideEntity.getRideCriteria()
				.getNoPassenger().toString());
		rideBeanObj.setInvoiceNo(rideBillEntity.getInvoiceNo());
		rideBeanObj
				.setDistinationAddress("This is Distination Address...rt ....");
		rideBeanObj.setSourceAddress("This is Source Address.... fxc .. ");
		if (rideBillEntity.getPaymentMode() != null) {
			rideBeanObj.setPaymentModeName(rideBillEntity.getPaymentMode()
					.getName());
		}
		rideBeanObj.setRideAmount(rideBillEntity.getRideAmount().toString());
		rideBeanObj.setFineAmount(rideBillEntity.getFineAmount().toString());
		if (rideBillEntity.getTipAmount() != null) {
			rideBeanObj.setTipAmount(rideBillEntity.getTipAmount().toString());
		}

		rideBeanObj.setTotalAmount(rideBillEntity.getTotalAmount().toString());
		rideBeanObj.setDestinationLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLat());
		rideBeanObj.setDestinationLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getDestinationLong());
		rideBeanObj.setSourceLat(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLat());
		rideBeanObj.setSourceLong(rideBillEntity.getRideInfoId()
				.getRideCriteria().getSourceLong());
		return rideBeanObj;
	}

	public void vaildatePaymentFilter(SafeHerDecorator decorator) {

		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
				decorator.getErrors().add("Please provide your app user id ");
			}
		}
		if (StringUtil.isEmpty(rideBean.getStartTime())) {
			decorator.getErrors().add("Please provide Start Time");
		}
		if (StringUtil.isEmpty(rideBean.getEndTime())) {
			decorator.getErrors().add("Please provide End Time");
		}
		if (StringUtil.isEmpty(rideBean.getFirstIndex())) {
			decorator.getErrors().add("Please provide First index");
		}
		if (StringUtil.isEmpty(rideBean.getMaxResult())) {
			decorator.getErrors().add("Please provide Max Result");
		}
	}

	public List<RideBean> preparePaymentBeanFromEntity(
			List<RideBillEntity> rideBillList) throws GenericException {
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		if (rideBillList == null) {
			throw new GenericException("User has no Payment History");
		}
		for (RideBillEntity rideBillEntityListObj : rideBillList) {
			RideBean rideBeanListObj = new RideBean();
			rideBeanListObj.setRideNo(rideBillEntityListObj.getRideInfoId()
					.getRideNo());
			if (rideBillEntityListObj.getPaymentMode() != null) {
				rideBeanListObj.setPaymentModeId(rideBillEntityListObj
						.getPaymentMode().getPaymentModeId().toString());
				rideBeanListObj.setPaymentModeName(rideBillEntityListObj
						.getPaymentMode().getName());
			}
			rideBeanListObj.setInvoiceNo(rideBillEntityListObj.getInvoiceNo());
			if (rideBillEntityListObj.getStatus() != null) {
				rideBeanListObj.setStatus(rideBillEntityListObj.getStatus()
						.getName());
			}
			if (rideBillEntityListObj.getRideInfoId().getIsCancel() != null) {
				rideBeanListObj.setIsCancel(rideBillEntityListObj
						.getRideInfoId().getIsCancel());
			}

			// rideBeanListObj.setAppUserByAppUserPassenger(rideBillEntityListObj.getAppUserByAppUserPassenger().getAppUserId().toString());
			// rideBeanListObj.setAppUserByAppUserDriver(rideBillEntityListObj.getAppUserByAppUserDriver().getAppUserId().toString());
			// rideBeanListObj.setDestinationLat(rideBillEntityListObj.getRideInfoId().getRideCriteria().getDestinationLat());
			// rideBeanListObj.setDestinationLong(rideBillEntityListObj.getRideInfoId().getRideCriteria().getDestinationLong());
			// rideBeanListObj.setSourceLat(rideBillEntityListObj.getRideInfoId().getRideCriteria().getSourceLat());
			// rideBeanListObj.setSourceLong(rideBillEntityListObj.getRideInfoId().getRideCriteria().getSourceLong());
			// rideBeanListObj.setIsCompleted(rideBillEntityListObj.getRideInfoId().getIsCompleted());
			// rideBeanListObj.setRideAmount(rideBillEntityListObj.getRideAmount().toString());
			// rideBeanListObj.setFineAmount(rideBillEntityListObj.getFineAmount().toString());
			// if(rideBillEntityListObj.getTipAmount()!=null){
			// rideBeanListObj.setTipAmount(rideBillEntityListObj.getTipAmount().toString());
			// }

			// rideBeanListObj.setTotalAmount(rideBillEntityListObj.getTotalAmount().toString());
			rideBeanListObj.setRideEndTime(rideBillEntityListObj
					.getRideInfoId().getEndTime().toString());
			rideBeanListObj.setRideStartTime(rideBillEntityListObj
					.getRideInfoId().getStartTime().toString());
			rideBeanList.add(rideBeanListObj);

		}
		return rideBeanList;
	}

	public List<RideBean> preparePaymentBeanFromEntityforPayment(
			List<RideBillEntity> rideBillList) throws GenericException {
		List<RideBean> rideBeanList = new ArrayList<RideBean>();
		if (rideBillList == null) {
			throw new GenericException("User has no Payment History");
		}
		for (RideBillEntity rideBillEntityListObj : rideBillList) {
			RideBean rideBeanListObj = new RideBean();
			rideBeanListObj.setRideNo(rideBillEntityListObj.getRideInfoId()
					.getRideNo());
			if (rideBillEntityListObj.getPaymentMode() != null) {
				rideBeanListObj.setPaymentModeId(rideBillEntityListObj
						.getPaymentMode().getPaymentModeId().toString());
				rideBeanListObj.setPaymentModeName(rideBillEntityListObj
						.getPaymentMode().getName());
			}
			rideBeanListObj.setInvoiceNo(rideBillEntityListObj.getInvoiceNo());
			if (rideBillEntityListObj.getStatus() != null) {
				rideBeanListObj.setStatus(rideBillEntityListObj.getStatus()
						.getName());
			}
			rideBeanListObj.setRideEndTime(rideBillEntityListObj
					.getRideInfoId().getEndTime().toString());
			rideBeanListObj.setRideStartTime(rideBillEntityListObj
					.getRideInfoId().getStartTime().toString());
			rideBeanList.add(rideBeanListObj);
		}
		return rideBeanList;

	}

	public RideBean populateInvoiceFromObj(RideEntity rideEntity,
			RideBillEntity rideBillEntity) {
		RideBean rideBeanObj = new RideBean();
		// rideBeanObj.setEstimatedArrival(rideEntity.getEstimatedArrival());
		// rideBeanObj.setEstimatedDuration(rideEntity.getEstimatedDuration());
		// rideBeanObj.setEstimatedDistance(rideEntity.getEstimatedDistance().toString());
		rideBeanObj.setStartTime(rideEntity.getStartTime().toString());
		rideBeanObj.setEndTime(rideEntity.getEndTime().toString());
		rideBeanObj.setIsCompleted(rideEntity.getIsCompleted());
		rideBeanObj.setRideTypeId(rideEntity.getRideTypeId().toString());
		rideBeanObj.setFirstName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getFirstName());
		rideBeanObj.setLastName(rideBillEntity.getAppUserByAppUserDriver()
				.getPerson().getLastName());
		// rideBeanObj.setAppUserByAppUserDriver(rideBillEntity.getAppUserByAppUserDriver().getAppUserId().toString());
		// rideBeanObj.setActualChild(rideEntity.getRideCriteria().getNoChild().toString());
		// rideBeanObj.setActualPerson(rideEntity.getRideCriteria().getNoPassenger().toString());
		rideBeanObj.setInvoiceNo(rideBillEntity.getInvoiceNo());
		if (rideBillEntity.getPaymentMode() != null) {
			rideBeanObj.setPaymentModeName(rideBillEntity.getPaymentMode()
					.getName());
		}
		rideBeanObj.setRideAmount(rideBillEntity.getRideAmount().toString());
		rideBeanObj.setFineAmount(rideBillEntity.getFineAmount().toString());
		if (rideBillEntity.getTipAmount() != null) {
			rideBeanObj.setTipAmount(rideBillEntity.getTipAmount().toString());
		}

		rideBeanObj.setTotalAmount(rideBillEntity.getTotalAmount().toString());
		// rideBeanObj.setDestinationLat(rideBillEntity.getRideInfoId().getRideCriteria().getDestinationLat());
		// rideBeanObj.setDestinationLong(rideBillEntity.getRideInfoId().getRideCriteria().getDestinationLong());
		// rideBeanObj.setSourceLat(rideBillEntity.getRideInfoId().getRideCriteria().getSourceLat());
		// rideBeanObj.setSourceLong(rideBillEntity.getRideInfoId().getRideCriteria().getSourceLong());
		return rideBeanObj;
	}

	public void vaildateInvoiceInfo(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
				decorator.getErrors().add("Please provide your app user  id ");
			}
		}
		if (StringUtil.isEmpty(rideBean.getInvoiceNo())) {
			decorator.getErrors().add("Please provide Invoice No");
		}
	}

	public void validateGiftedRideCriteria(SafeHerDecorator decorator) {
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getGiftType())) {
			decorator.getErrors().add("Please provide Gift Type");
		}else{
			if(bean.getGiftType().equals("1")){
				if (StringUtil.isEmpty(bean.getConsumeAmount())) {
					decorator.getErrors().add("Please provide Gift Amount");
				}
			}else if(bean.getGiftType().equals("2")){
//				if (StringUtil.isEmpty(bean.getSourceLat())) {
//					decorator.getErrors().add("Please provide Source Lat");
//				}
//				if (StringUtil.isEmpty(bean.getSourceLong())) {
//					decorator.getErrors().add("Please provide Source Lang");
//				}
			}
		}
		if (StringUtil.isEmpty(bean.getReciverNum())) {
			if (StringUtil.isEmpty(bean.getEmail())) {
				decorator.getErrors().add(
						"Please provide gifted Person Email Address Or Phone Number");
			}
		}
			
	}

	public RideGiftEntity prepareGiftRide(RideCriteriaEntity criteriaEntity,
			String num, StatusEntity giftRideStatus, String email) {
		RideGiftEntity rideGiftedEntity = new RideGiftEntity();
		rideGiftedEntity.setAppUserBySenderUserId(criteriaEntity.getAppUser());
		if (num != null) {
			rideGiftedEntity.setReciverNum(num);
		} else {
			rideGiftedEntity.setEmail(email);
		}
		if (num != null && email != null) {
			rideGiftedEntity.setEmail(email);
			rideGiftedEntity.setReciverNum(num);
		}
		rideGiftedEntity.setGiftNo("GF" + System.currentTimeMillis());
		rideGiftedEntity.setRideCriteria(criteriaEntity);
		rideGiftedEntity.setStatus(giftRideStatus);
		rideGiftedEntity.setIsActive("1");
		rideGiftedEntity.setGiftTime(DateUtil.now());
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.setTimeInMillis(DateUtil.getCurrentTimestamp().getTime());
		expiryDate.add(Calendar.DAY_OF_MONTH, expiryDate.DAY_OF_MONTH+29);
        Timestamp later = new Timestamp(expiryDate.getTime().getTime());
        rideGiftedEntity.setExpiryDate(later);
		return rideGiftedEntity;
	}

	public RideCriteriaEntity prepareGiftedRideCriteriaPerameters(
			RideCriteriaBean bean) {
		RideCriteriaEntity citeriaEntity = new RideCriteriaEntity();

		if (bean.getGiftRideWrapper().getSourceLat() != null) {
			citeriaEntity.setSourceLat(bean.getGiftRideWrapper().getSourceLat());
		}
		if (bean.getGiftRideWrapper().getSourceLong() != null) {
			citeriaEntity.setSourceLong(bean.getGiftRideWrapper().getSourceLong());
		}
		if (bean.getGiftRideWrapper().getDistinatonLat() != null) {
			citeriaEntity.setDestinationLat(bean.getGiftRideWrapper().getDistinatonLat());
		}
		if (bean.getGiftRideWrapper().getDistinatonLong() != null) {
			citeriaEntity.setDestinationLong(bean.getGiftRideWrapper().getDistinatonLong());
		}
		if (bean.getGiftRideWrapper().getDistinatonAddress() != null) {
			citeriaEntity.setDistinationAddress(bean.getGiftRideWrapper().getDistinatonAddress());
		}
		if (bean.getGiftRideWrapper().getSourceAddress() != null) {
			citeriaEntity.setSourceAddress(bean.getGiftRideWrapper().getSourceAddress());
		}
		if (bean.getGiftRideWrapper().getNoChild() != null) {
			citeriaEntity.setNoChild(Short.valueOf(bean.getGiftRideWrapper().getNoChild()));
		}
		if (bean.getGiftRideWrapper().getNoPassenger() != null) {
			citeriaEntity.setNoPassenger(Short.valueOf(bean.getGiftRideWrapper().getNoPassenger()));
		}
		if (bean.getGiftRideWrapper().getNoSeats() != null) {
			citeriaEntity.setNoSeats(Short.valueOf(bean.getGiftRideWrapper().getNoSeats()));
		}
		if (bean.getGiftRideWrapper().getIsShared() != null) {
			citeriaEntity.setIsShared(bean.getGiftRideWrapper().getIsShared());
		}
		if (bean.getGiftRideWrapper().getIsFav() != null) {
			citeriaEntity.setIsFav(bean.getGiftRideWrapper().getIsFav());
		}
		if (bean.getGiftRideWrapper().getRideTypeId() != null) {
			if (bean.getGiftRideWrapper().getRideTypeId().equals("1")) {
				citeriaEntity.setIsShared("1");
			} else {
				citeriaEntity.setIsShared("0");
			}
			citeriaEntity.setRideTypeId(Integer.valueOf(bean.getGiftRideWrapper().getRideTypeId()));
		}
		citeriaEntity.setIsGift("1");
		return citeriaEntity;
	}

	public void validateGiftedRides(SafeHerDecorator decorator) {
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App User ID");
		}
		if (StringUtil.isEmpty(bean.getFirstIndex())) {
			decorator.getErrors().add("Please provide First index");
		}
		if (StringUtil.isEmpty(bean.getMaxResult())) {
			decorator.getErrors().add("Please provide Max Result");
		}
		if(StringUtil.isEmpty(bean.getGiftType())){
			decorator.getErrors().add("Please provide Gift Type");
		}
	}

	public List<RideCriteriaBean> convertEntitytoRideGiftedBean(
			List<RideGiftEntity> rideGiftedEntity) {
		List<RideCriteriaBean> rideGiftedBeanList = new ArrayList<RideCriteriaBean>();
		RideCriteriaBean rideCriteriaBean = null;
		for (RideGiftEntity rideGiftRide : rideGiftedEntity) {
			rideCriteriaBean = new RideCriteriaBean();
			rideCriteriaBean.setReciverNum(rideGiftRide.getReciverNum());
			rideCriteriaBean.setEmail(rideGiftRide.getEmail());
			rideCriteriaBean.setGiftId(rideGiftRide.getRideGiftId().toString());
			rideCriteriaBean.setGiftTime(rideGiftRide.getGiftTime().toString());
			if (rideGiftRide.getStatus().getName().trim().equals(StatusEnum.NoReceive.toString())) {
				rideCriteriaBean.setGiftStatus(StatusEnum.Receive.toString());
			}else{
				rideCriteriaBean.setGiftStatus(rideGiftRide.getStatus()
						.getName().trim());
			}
			rideCriteriaBean.setGiftType(rideGiftRide.getGiftType().getGiftTypeId().toString());
			rideCriteriaBean.setGiftTypeName(rideGiftRide.getGiftType().getName());
			if(rideGiftRide.getGiftType().getGiftTypeId()==1){
				rideCriteriaBean.setConsumeAmount(rideGiftRide.getConsumeAmount().toString());
			}else{
				rideCriteriaBean.setSourceAddress(rideGiftRide.getRideCriteria().getSourceAddress());
				rideCriteriaBean.setDistinatonAddress(rideGiftRide.getRideCriteria().getDistinationAddress());
				
			}
			rideCriteriaBean.setSenderAppUserId(rideGiftRide.getAppUserBySenderUserId().getAppUserId()+"");
			rideCriteriaBean.setSenderFirstName(rideGiftRide.getAppUserBySenderUserId().getPerson().getFirstName());
			rideCriteriaBean.setSenderLastName(rideGiftRide.getAppUserBySenderUserId().getPerson().getLastName());
			rideCriteriaBean.setGiftNo(rideGiftRide.getGiftNo());
			rideCriteriaBean.setRideSearchWrapper(null);
			rideCriteriaBean.setRideCriteriaWrapper(null);
			rideCriteriaBean.setRideCriteriaId(rideGiftRide.getRideCriteria()
					.getRideCriteriaId().toString());
			rideCriteriaBean.setSenderAppUserId(rideGiftRide
					.getAppUserBySenderUserId().getAppUserId().toString());
//			rideCriteriaBean.setReciverAppUserId(rideGiftRide
//					.getAppUserByReciverUserId().getAppUserId().toString());
			rideGiftedBeanList.add(rideCriteriaBean);

		}
		return rideGiftedBeanList;
	}

	public void validateGiftedRideInfo(SafeHerDecorator decorator) {
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getGiftNo())) {
			decorator.getErrors().add("Please provide Gift No");
		}
		if (StringUtil.isEmpty(bean.getReciverAppUserId())) {
			decorator.getErrors().add("Please provide Reciver App User Id");
		}
	}

	public RideCriteriaBean convertEntityToBean(RideGiftEntity gifteRideEntity) {
		RideCriteriaBean bean = new RideCriteriaBean();
		bean.setGiftId(gifteRideEntity.getRideGiftId().toString());
		bean.setGiftStatus(gifteRideEntity.getStatus().getName());
		bean.setGiftNo(gifteRideEntity.getGiftNo());
		//bean.setGiftTime(gifteRideEntity.getGiftTime().toString());
		bean.setGiftTime(DateUtil.convertDateToString(gifteRideEntity.getGiftTime(), "yyyy-MM-dd HH:mm:ss"));
		if (gifteRideEntity.getStatus().getName().trim().equals(StatusEnum.NoReceive.toString())) {
			bean.setGiftStatus(StatusEnum.Receive.toString());
		}else{
			bean.setGiftStatus(gifteRideEntity.getStatus()
					.getName().trim());
		}
		bean.setGiftType(gifteRideEntity.getGiftType().getGiftTypeId().toString());
		bean.setGiftTypeName(gifteRideEntity.getGiftType().getName());
		if(gifteRideEntity.getGiftType().getGiftTypeId()==1){
			bean.setConsumeAmount(gifteRideEntity.getConsumeAmount().toString());
		}else{
			bean.setSourceAddress(gifteRideEntity.getRideCriteria().getSourceAddress());
			bean.setDistinatonAddress(gifteRideEntity.getRideCriteria().getDistinationAddress());
			bean.setDistinatonLong(gifteRideEntity.getRideCriteria().getDestinationLong());
			bean.setDistinatonLat(gifteRideEntity.getRideCriteria().getDestinationLat());
			bean.setSourceLat(gifteRideEntity.getRideCriteria().getSourceLat());
			bean.setSourceLong(gifteRideEntity.getRideCriteria().getSourceLong());
			bean.setRideTypeId(gifteRideEntity.getRideCriteria().getRideTypeId()+"");
			bean.setNoSeats(gifteRideEntity.getRideCriteria().getNoSeats()+"");
			bean.setNoPassenger(gifteRideEntity.getRideCriteria().getNoPassenger()+"");
			bean.setNoChild(gifteRideEntity.getRideCriteria().getNoChild()+"");
		}
		bean.setSenderAppUserId(gifteRideEntity.getAppUserBySenderUserId().getAppUserId()+"");
		bean.setSenderFirstName(gifteRideEntity.getAppUserBySenderUserId().getPerson().getFirstName());
		bean.setSenderLastName(gifteRideEntity.getAppUserBySenderUserId().getPerson().getLastName());
		bean.setGiftNo(gifteRideEntity.getGiftNo());
		bean.setRideSearchWrapper(null);
		bean.setRideCriteriaWrapper(null);
		bean.setRideCriteriaId(gifteRideEntity.getRideCriteria()
				.getRideCriteriaId().toString());
		bean.setSenderAppUserId(gifteRideEntity
				.getAppUserBySenderUserId().getAppUserId().toString());

		bean.setSenderAppUserId(gifteRideEntity.getAppUserBySenderUserId()
				.getAppUserId().toString());
		if (gifteRideEntity.getAppUserByReciverUserId() != null) {
			bean.setReciverAppUserId(gifteRideEntity
					.getAppUserByReciverUserId().getAppUserId().toString());
		}
	//	bean.setReciverNum(gifteRideEntity.getReciverNum());
		return bean;
	}

	public void validateGiftRideCriteria(SafeHerDecorator decorator) {
		RideCriteriaBean bean = (RideCriteriaBean) decorator.getDataBean();

		if (StringUtil.isEmpty(bean.getReciverAppUserId())) {
			decorator.getErrors().add("Please provide Reciver App User ID");
		}
		if(StringUtil.isEmpty(bean.getGiftType())){
			decorator.getErrors().add("Please provide Reciver App User Ride Gift Type");
		}else{
			if(bean.getGiftType().equals("1")){
				if (StringUtil.isEmpty(bean.getDistinatonLat())) {
					decorator.getErrors().add("Please provide Destination Lat");
				}
				if (StringUtil.isEmpty(bean.getSourceLat())) {
					decorator.getErrors().add("Please provide Source Lat");
				}
				if (StringUtil.isEmpty(bean.getSourceLong())) {
					decorator.getErrors().add("Please provide Source Long");
				}
				if (StringUtil.isEmpty(bean.getDistinatonLong())) {
					decorator.getErrors().add("Please provide Destination Lang");
				}
				
			}
		}
		
		if (StringUtil.isEmpty(bean.getGiftNo())) {
			decorator.getErrors().add("Please provide Ride Gift No");
		}
		

	}

	public void validatereportingCommnet(SafeHerDecorator decorator) {
		UserRatingBean bean = (UserRatingBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getAppUserId())) {
			decorator.getErrors().add("Please provide App USER iD!! :-) ");
		}
		if (StringUtil.isEmpty(bean.getUserCommentId())) {
			decorator.getErrors().add("Please provide User Comment Id :-) ");
		}

	}

	public void validateIsRefresh(SafeHerDecorator decorator) {
		RideBean bean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getRequestNo())) {
			if (StringUtil.isEmpty(bean.getRideNo())) {
				decorator.getErrors().add("Please provide Request No ");
			}

		}
		if (StringUtil.isEmpty(bean.getRideNo())) {
			if (StringUtil.isEmpty(bean.getRequestNo())) {
				decorator.getErrors().add("Please provide Ride No ");
			}

		}
		if (StringUtil.isEmpty(bean.getAppUserByAppUserDriver())) {
			decorator.getErrors().add("Please provide Driver App User Id ");
		}
	}

	public void convertEntityTORideBean(PostRideEntity entity, RideBean bean) {

		if (StringUtil.isNotEmpty(entity.getActualDuration())) {
			bean.setActualDistance(new DecimalFormat("##.##").format(entity
					.getActualDistance() / 1609.344) + "");
		} else {
			bean.setActualDistance("0");
		}
		if (entity.getRideEntityId() != null) {
			if (StringUtil.isNotEmpty(entity.getRideEntityId().getStartTime()
					+ "")) {
				bean.setStartTime(entity.getRideEntityId().getStartTime() + "");
			}
			if (StringUtil.isNotEmpty(entity.getRideEntityId().getEndTime()
					+ "")) {
				bean.setEndTime(entity.getRideEntityId().getEndTime() + "");
			}
			if (entity.getRideEntityId().getRideCriteria() != null) {
				if (StringUtil.isNotEmpty(entity.getRideEntityId()
						.getRideCriteria().getSourceAddress())) {
					bean.setSourceAddress(entity.getRideEntityId()
							.getRideCriteria().getSourceAddress());
				}
				if (StringUtil.isNotEmpty(entity.getRideEntityId()
						.getRideCriteria().getDistinationAddress())) {
					bean.setDistinationAddress(entity.getRideEntityId()
							.getRideCriteria().getDistinationAddress());
				}
			}
		}
		if (StringUtil.isEmpty(bean.getCompanyAmount())) {
			bean.setCompanyAmount("0");
		}
		if (StringUtil.isEmpty(bean.getCharityAmount())) {
			bean.setCharityAmount("0");
		}
		if (StringUtil.isEmpty(bean.getTipAmount())) {
			bean.setTipAmount("0");
		}
		if (StringUtil.isEmpty(bean.getDriverAmount())) {
			bean.setDriverAmount("0");
		}
	}

	public void convertEntityTORideBeanForDriverPassInfo(
			RideBillEntity billEntity, RideBean bean) {

		if (billEntity.getAppUserByAppUserDriver() != null) {
			if (billEntity.getAppUserByAppUserDriver().getPerson() != null
					&& billEntity.getAppUserByAppUserDriver().getPersonDetail() != null) {
				bean.setDriverFirstName(billEntity.getAppUserByAppUserDriver()
						.getPerson().getFirstName());
				bean.setDriverLastName(billEntity.getAppUserByAppUserDriver()
						.getPerson().getLastName());
				bean.setDriverEmail(billEntity.getAppUserByAppUserDriver()
						.getPersonDetail().getPrimaryEmail());
			}

		}
		if (billEntity.getAppUserByAppUserPassenger() != null) {
			if (billEntity.getAppUserByAppUserPassenger().getPerson() != null
					&& billEntity.getAppUserByAppUserPassenger()
							.getPersonDetail() != null) {
				bean.setPassengerFirstName(billEntity
						.getAppUserByAppUserPassenger().getPerson()
						.getFirstName());
				bean.setPassengerLastName(billEntity
						.getAppUserByAppUserPassenger().getPerson()
						.getLastName());
				bean.setPassengerEmail(billEntity
						.getAppUserByAppUserPassenger().getPersonDetail()
						.getPrimaryEmail());
			}

		}
	}

	public void validateMidRideRequest(SafeHerDecorator decorator) {
		RideBean rideBean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserDriver())) {
			decorator.getErrors().add("Please provide " +
					"Driver app user  id ");
		}
		if (StringUtil.isEmpty(rideBean.getAppUserByAppUserPassenger())) {
			decorator.getErrors().add("Please provide Passenger app user  id ");
		}
		if (StringUtil.isEmpty(rideBean.getIsDriver())) {
			decorator.getErrors().add("Please provide isDriver Flag");
		}
		if(StringUtil.isEmpty(rideBean.getRequestCode())){
			decorator.getErrors().add("Please Provide Request Code");
		}
	}

	public List<PromAndReffBean> convertObjectToPromoBean(
			List<Object[]> listOfPromotions) {
		List<PromAndReffBean> promotionBeanList = new ArrayList<PromAndReffBean>();
		PromAndReffBean ListBean = null;
		for (Object[] oj : listOfPromotions) {
			ListBean = new PromAndReffBean();
			Integer promotionInfoId = (Integer) oj[0];
			String promotionDescription = (String) oj[1];
			String isPercentage = (String) oj[2];
			String isCount = (String) oj[3];
			AppUserTypeEntity appUserType = (AppUserTypeEntity) oj[4];
			String isActive = (String) oj[5];
			String durationInDays = (String) oj[6];
			Timestamp startDate = (Timestamp) oj[7];
			String isSingle = (String) oj[8];
			Timestamp expiryDate = (Timestamp) oj[9];
			PromotionType promotionType = (PromotionType) oj[11];
		//	String codeValue = (String) oj[11];
		//	Integer promotionCodesId = (Integer) oj[12];
			//String isUsed = (String) oj[13];
			Integer promotionAmount = (Integer) oj[10];
			// ListBean.setAppUser();
			ListBean.setAppUserTypeId(appUserType.getName());
		//	ListBean.setCodeValue(codeValue);
			ListBean.setIsSingle(isSingle);
//			ListBean.setStartDate(startDate.toString());
//			ListBean.setExpiryDate(expiryDate.toString());
			ListBean.setStartDate(DateUtil.convertDateToString(new Date(startDate.getTime()), "yyyy-MM-dd HH:mm:ss"));
			ListBean.setExpiryDate(DateUtil.convertDateToString(new Date(expiryDate.getTime()), "yyyy-MM-dd HH:mm:ss"));
			ListBean.setDurationInDays(durationInDays);
			if(isCount.equals("1")){
				ListBean.setCountValue((String) oj[12].toString().trim());
			}
			
		//	ListBean.setIsUsed(isUsed);
			// ListBean.setIsDriver(isDriver);
			ListBean.setPromotionDescription(promotionDescription);
//			if(promotionCodesId==null){
//				ListBean.setPromotionCodeId("");
//			}else{
//				ListBean.setPromotionCodeId(promotionCodesId.toString());
//			}
			ListBean.setPromotionInfoId(promotionInfoId.toString());
			ListBean.setPromotionTypeId(promotionType.getName());
			ListBean.setAmount(promotionAmount.toString());
			ListBean.setIsPercentage(isPercentage);
			ListBean.setIsActive(isActive);
			ListBean.setIsCount(isCount);
			promotionBeanList.add(ListBean);
		}
		return promotionBeanList;
	}

	public PromAndReffBean convertPromotionToBean(PromotionInfoEntity promotion) {
		PromAndReffBean bean = new PromAndReffBean();
		bean.setTotalValue(promotion.getAmountValue().toString());
		bean.setPromotionInfoId(promotion.getPromotionInfoId().toString());
		bean.setUseStartDate(DateUtil.convertDateToString(new Date(promotion.getStartDate().getTime()), "yyyy-MM-dd HH:mm:ss"));
		bean.setExpiryDate(DateUtil.convertDateToString(new Date(promotion.getExpiryDate().getTime()), "yyyy-MM-dd HH:mm:ss"));
//		bean.setUseStartDate(promotion.getStartDate().toString());
//		bean.setExpiryDate(promotion.getExpiryDate().toString());
		bean.setPromotionDescription(promotion.getPromotionDescription());
		if(promotion.getAppUserType().getAppUserTypeId()==2){
			bean.setNoOfRides(promotion.getCountValue());
		}
		return bean;
	}

	public List<PromAndReffBean> convertEntityListToBeanList(
			List<UserPromotionEntity> promotionUserList) {
		List<PromAndReffBean> promotionBeanList = new ArrayList<PromAndReffBean>();
		PromAndReffBean ListBean = null;
		for (UserPromotionEntity oj : promotionUserList) {
			ListBean = new PromAndReffBean();
			ListBean.setAmount(oj.getTotalValue().toString());
			ListBean.setPromotionInfoId(oj.getPromotionInfo().getPromotionInfoId().toString());
		//	ListBean.setPromotionTypeId(oj.getPromotionType().getName());
			ListBean.setIsActive(oj.getIsActive());
			ListBean.setPromotionDescription(oj.getPromotionInfo().getPromotionDescription());
			ListBean.setStartDate(DateUtil.convertDateToString(oj.getUseStartDate(), "yyyy-MM-dd HH:mm:ss"));
			ListBean.setExpiryDate(DateUtil.convertDateToString(oj.getUseExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
			ListBean.setLastUsedDate(oj.getTotalValue().toString());
			ListBean.setCountValue(oj.getCountValue().toString().trim());
			ListBean.setUseCountValue(oj.getUseCountValue().toString());
			ListBean.setIsCompleted(oj.getIsCompleted());
			promotionBeanList.add(ListBean);
		}
		return promotionBeanList;
	}

	public PromAndReffBean convertUserPromotionToBean(
			UserPromotionEntity userPromotionEntity) {
		
		PromAndReffBean bean = new PromAndReffBean();
		bean.setTotalValue(userPromotionEntity.getTotalValue().toString());
		bean.setPromotionInfoId(userPromotionEntity.getPromotionCodesEntity().getPromotionInfoEntity().getPromotionInfoId()+"");
	//	bean.setPromotionTypeId(userPromotionEntity.getPromotionType().getName());
		bean.setIsActive(userPromotionEntity.getIsActive());
		bean.setPromotionDescription(userPromotionEntity.getPromotionCodesEntity().getPromotionInfoEntity().getPromotionDescription()+"");
//		bean.setExpiryDate(userPromotionEntity.getUseExpiryDate().toString());
//		bean.setUseStartDate(userPromotionEntity.getUseStartDate().toString());
		bean.setExpiryDate(DateUtil.convertDateToString(userPromotionEntity.getUseExpiryDate(), "yyyy-MM-dd HH:mm:ss"));
		bean.setUseStartDate(DateUtil.convertDateToString(userPromotionEntity.getUseStartDate(), "yyyy-MM-dd HH:mm:ss"));
		
		bean.setCodeValue(userPromotionEntity.getPromotionCodesEntity().getCodeValue());
		bean.setPromotionCodeId(userPromotionEntity.getPromotionCodesEntity().getPromotionCodesId()+"");		
		//bean.setLastUsedDate(userPromotionEntity.getTotalValue().toString());
		bean.setIsCompleted(userPromotionEntity.getIsCompleted());
		return bean;	
	}

	public void validateGiftEndRide(SafeHerDecorator decorator) {
		RideBean ridebean = (RideBean) decorator.getDataBean();
		if (StringUtil.isEmpty(ridebean.getIsCompleted())) {
			decorator.getErrors().add("Please provide Is Ride Complete??");
		} else {
			if (ridebean.getIsCompleted().equals("1")) {
				if (StringUtil.isEmpty(ridebean.getRideEndTime())) {
					decorator.getErrors().add("Please provide Ride End Time?");
				}
				if (StringUtil.isEmpty(ridebean.getRideNo())) {
					decorator.getErrors().add("Please provide Ride No");
				}
				if (StringUtil.isEmpty(ridebean.getActualDistance())) {
					decorator.getErrors().add(
							"Please provide Actual Ride Estimated Distance");
				}
				if (StringUtil.isEmpty(ridebean.getActualDuration())) {
					decorator.getErrors().add(
							"Please provide Actual Ride Estimated Duration");
				}
				if (StringUtil.isEmpty(ridebean.getRideEndTime())) {
					decorator.getErrors().add(
							"Please provide Actual Arrival Time");
				}
				// if (StringUtil.isEmpty(ridebean.getIdelTime())) {
				// decorator.getErrors().add(
				// "Please provide Idel Time If Exist");
				// }
				if (StringUtil.isEmpty(ridebean.getAppUserByAppUserDriver())) {
					decorator.getErrors().add(
							"Please provide Driver App Uesr No");
				}
				if (StringUtil.isEmpty(ridebean.getAppUserByAppUserPassenger())) {
					decorator.getErrors().add(
							"Please provide Passenger App Uesr No");
				}
				if (StringUtil.isEmpty(ridebean.getFineAmount())) {
					decorator.getErrors().add("Please provide Fine Ammount");
				}
				if (StringUtil.isEmpty(ridebean.getTotalAmount())) {
					decorator.getErrors().add("Please provide Fine Ammount");
				}
				if (StringUtil.isEmpty(ridebean.getRideAmount())) {
					decorator.getErrors().add("Please provide Ride  Ammount");
				}
//				
			} else {
				decorator.getErrors().add("Please First Complete the Ride");
			}
		}
	}

	public void validateMidRideDistinationRequest(SafeHerDecorator decorator) {
		RideCriteriaBean rideCriteria = (RideCriteriaBean) decorator.getDataBean();	
		if (StringUtil.isEmpty(rideCriteria.getDistinatonLat())) {
			decorator.getErrors().add("Please provide Destination Lat");
		}
		if (StringUtil.isEmpty(rideCriteria.getDistinatonLong())) {
			decorator.getErrors().add("Please provide Destination Lang");
		}
		if (StringUtil.isEmpty(rideCriteria.getDistinatonAddress())) {
			decorator.getErrors().add(
					"Please provide Distination Address String ");
		}
		if (StringUtil.isEmpty(rideCriteria.getAppUserId())) {
			decorator.getErrors().add(
					"Please provide App User Id");
		}
		if (StringUtil.isEmpty(rideCriteria.getIsDriver())) {
			decorator.getErrors().add(
					"Please provide isDriver Check");
		}
		if (StringUtil.isEmpty(rideCriteria.getRequestNo())) {
			decorator.getErrors().add(
					"Please provide Request No");
		}
	}

}
