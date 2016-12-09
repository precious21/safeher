package com.tgi.safeher.map.service.converter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.tgi.safeher.beans.DriverDrivingDetailBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.DriverLocationTrackEntity;
import com.tgi.safeher.entity.PassengerSourceLocationEntity;
import com.tgi.safeher.entity.PersonEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverDrivingDetailMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrakListMongoEntity;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Component
public class MapConverter implements Serializable {

	public void convertObjectToBean(SafeHerDecorator decorator,
			List<Object[]> list) {
		MapBean bean = new MapBean();
		for (Object[] object : list) {
			MapBean mapBean = new MapBean();
			mapBean.setAppUserId(((AppUserEntity) object[0]).getAppUserId()
					+ "");
			mapBean.setPersonName(((PersonEntity) object[1]).getFirstName()
					+ " " + ((PersonEntity) object[1]).getLastName());
			bean.getPersonList().add(mapBean);
		}
		decorator.getResponseMap().put("personList", bean.getPersonList());
		decorator.setResponseMessage("Successfull Retreived");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
	}

	public void populateFullAddres(MapBean bean) {
		if (StringUtil.isNotEmpty(bean.getAddress())) {
			String[] address = bean.getAddress().split(",");
			bean.setStreet(address[0]);
			bean.setCity(address[1]);
			bean.setState(address[2]);
			bean.setCountry(address[3]);
			bean.setStateCode(address[4]);
			bean.setCountryCode(address[5]);
			bean.setAddress(address[0] + ", " + bean.getCity() + ", "
					+ bean.getState() + ", " + bean.getCountry());
			if(address.length >= 7){
				bean.setSuburb(address[6]);
			}
			if(address.length >= 8){
				bean.setNeighborHood(address[7]);
			}
		}
	}

	public void convertBeanToPassengerLoc(PassengerSourceLocationEntity entity, MapBean bean){
		if(StringUtil.isNotEmpty(bean.getLat())){
			entity.setLatValue(bean.getLat());
		}
		if(StringUtil.isNotEmpty(bean.getLng())){
			entity.setLongValue(bean.getLng());
		}
		if(StringUtil.isNotEmpty(bean.getIsPhysical())){
			entity.setIsPhysical(bean.getIsPhysical());
		}
	}

	//Mongo
	public void convertBeanToDriverLoc(ActiveDriverLocationEntity entity, MapBean bean, 
			DriverLocationTrackEntity trackEntity){
		if(StringUtil.isNotEmpty(bean.getLat())){
			entity.setLatValue(bean.getLat());
			trackEntity.setLatVal(bean.getLat());
		}
		if(StringUtil.isNotEmpty(bean.getLng())){
			entity.setLongValue(bean.getLng());
			trackEntity.setLongVal(bean.getLng());
		}
		if(StringUtil.isNotEmpty(bean.getIsPhysical())){
			entity.setIsPhysical(bean.getIsPhysical());
		}
		if(StringUtil.isNotEmpty(entity.getLatValue())){
			entity.setPreLatValue(entity.getLatValue());
		}
		if(StringUtil.isNotEmpty(entity.getLongValue())){
			entity.setPreLongValue(entity.getLongValue());
		}
		if(StringUtil.isNotEmpty(bean.getDirection())){
			entity.setDirection(bean.getDirection());
		}
		
		entity.setIsBooked("0");
		entity.setIsRequested("0");
		entity.setLastActiveTime(
				DateUtil.getCurrentTimestamp());
	}

	public boolean validatLngLat(SafeHerDecorator decorator) {
		boolean result = true;
		MapBean bean = (MapBean) decorator.getDataBean();
		if (StringUtil.isEmpty(bean.getLat())) {
			decorator.getErrors().add("Please provide fist name");
			result = false;
		}
		if (StringUtil.isEmpty(bean.getLng())) {
			decorator.getErrors().add("Please provide last name");
			result = false;
		}
		
		if (StringUtil.isNotEmpty(bean.getLat())
				&& Double.parseDouble(bean.getLat()) == 0.0d) {
			decorator.getErrors().add("Please turn on location services from device settings");
			result = false;
		}
		if (StringUtil.isEmpty(bean.getLng())
				&& Double.parseDouble(bean.getLng()) == 0.0d) {
			decorator.getErrors().add("Please turn on location services from device settings");
			result = false;
		}

		return result;
	}
	
	public void convertBeanToDriverLocMongo(ActiveDriverLocationMongoEntity entity, MapBean bean, 
			DriverLocationTrackEntity trackEntity){
		if(StringUtil.isNotEmpty(bean.getLat())){
			entity.setLatValue(bean.getLat());
			trackEntity.setLatVal(bean.getLat());
		}
		if(StringUtil.isNotEmpty(bean.getLng())){
			entity.setLongValue(bean.getLng());
			trackEntity.setLongVal(bean.getLng());
		}
		if(StringUtil.isNotEmpty(bean.getLat()) && StringUtil.isNotEmpty(bean.getLng())){
			entity.setLoc(new Point(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())));
		}
		
		if(StringUtil.isNotEmpty(bean.getIsPhysical())){
			entity.setIsPhysical(bean.getIsPhysical());
		}
		if(StringUtil.isNotEmpty(entity.getLatValue())){
			entity.setPreLatValue(entity.getLatValue());
		}
		if(StringUtil.isNotEmpty(entity.getLongValue())){
			entity.setPreLongValue(entity.getLongValue());
		}
		if(StringUtil.isNotEmpty(bean.getDirection())){
			entity.setDirection(bean.getDirection());
		}
		
		
		
		entity.setIsBooked("0");
		entity.setIsRequested("0");
		entity.setLastActiveTime(
				new Date(DateUtil.getCurrentTimestamp().getTime()));
	}
	
	public void convertBeanToDriverLocMongoV2(ActiveDriverLocationMongoEntity entity, MapBean bean, 
			DriverLocationTrakListMongoEntity trackEntity){
		if(StringUtil.isNotEmpty(bean.getLat())){
			entity.setLatValue(bean.getLat());
			trackEntity.setLatVal(bean.getLat());
		}
		if(StringUtil.isNotEmpty(bean.getLng())){
			entity.setLongValue(bean.getLng());
			trackEntity.setLongVal(bean.getLng());
		}
		if(StringUtil.isNotEmpty(bean.getLat()) && StringUtil.isNotEmpty(bean.getLng())){
			entity.setLoc(new Point(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())));
		}
		
		if(StringUtil.isNotEmpty(bean.getIsPhysical())){
			entity.setIsPhysical(bean.getIsPhysical());
		}
		if(StringUtil.isNotEmpty(entity.getLatValue())){
			entity.setPreLatValue(entity.getLatValue());
		}
		if(StringUtil.isNotEmpty(entity.getLongValue())){
			entity.setPreLongValue(entity.getLongValue());
		}
		if(StringUtil.isNotEmpty(bean.getDirection())){
			entity.setDirection(bean.getDirection());
		}
		
		
		
//		entity.setIsBooked("0");
//		entity.setIsRequested("0");
//		entity.setLastActiveTime(
//				new Date(DateUtil.getCurrentTimestamp().getTime()));
	}
	
	public void convertBeanToActivDriverMongo(ActiveDriverLocationMongoEntity entity, MapBean bean){
		if(StringUtil.isNotEmpty(bean.getLat())){
			entity.setLatValue(bean.getLat());
		}
		if(StringUtil.isNotEmpty(bean.getLng())){
			entity.setLongValue(bean.getLng());
		}
		if(StringUtil.isNotEmpty(bean.getLat()) && 
				StringUtil.isNotEmpty(bean.getLng())){
			entity.setLoc(new Point(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng())));
		}
		if(StringUtil.isNotEmpty(entity.getLatValue())){
			entity.setPreLatValue(entity.getLatValue());
		}
		if(StringUtil.isNotEmpty(entity.getLongValue())){
			entity.setPreLongValue(entity.getLongValue());
		}
		if(StringUtil.isNotEmpty(bean.getDirection())){
			entity.setDirection(bean.getDirection());
		}
		entity.setLastActiveTime(
				new Date(DateUtil.getCurrentTimestamp().getTime()));
		entity.setIsOnline("1");
		entity.setIsRequested("0");
		entity.setIsBooked("0");
	}
	
	public void convertBeanToDriverDrivDetailMongo(
			DriverDrivingDetailMongoEntity entity, DriverDrivingDetailBean bean){
		
		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			entity.setAppUserId(new Integer(bean.getAppUserId()));
		}
		if(bean.getTotalOnlineTime() != null){
			if(entity.getTotalOnlineTime() != null){
				entity.setTotalOnlineTime(entity.getTotalOnlineTime()+ bean.getTotalOnlineTime());	
			}else{
				entity.setTotalOnlineTime(bean.getTotalOnlineTime());
			}
		}
		if(bean.getTotalOnlineDistance() != null){
			if(entity.getTotalOnlineDistance() != null){
				entity.setTotalOnlineDistance(entity.getTotalOnlineDistance()+ bean.getTotalOnlineDistance());	
			}else{
				entity.setTotalOnlineDistance(bean.getTotalOnlineDistance());
			}
		}
		if(bean.getTotalPreRideTime() != null){
			if(entity.getTotalPreRideTime() != null){
				entity.setTotalPreRideTime(entity.getTotalPreRideTime()+ bean.getTotalPreRideTime());	
			}else{
				entity.setTotalPreRideTime(bean.getTotalPreRideTime());
			}
		}
		if(bean.getTotalPreRideDistance() != null){
			if(entity.getTotalPreRideDistance() != null){
				entity.setTotalPreRideDistance(entity.getTotalPreRideDistance()+ bean.getTotalPreRideDistance());
			}else{
				entity.setTotalPreRideDistance(bean.getTotalPreRideDistance());
			}
		}
		if(bean.getTotalRideTime() != null){
			if(entity.getTotalRideTime() != null){
				entity.setTotalRideTime(entity.getTotalRideTime()+ bean.getTotalRideTime());
			}else{
				entity.setTotalRideTime(bean.getTotalRideTime());
			}
		}
		if(bean.getTotalRideDistance() != null){
			if(entity.getTotalRideDistance() != null){
				entity.setTotalRideDistance(entity.getTotalRideDistance()+ bean.getTotalRideDistance());
			}else{
				entity.setTotalRideDistance(bean.getTotalRideDistance());
			}
		}
		if(bean.getTotalRides() != null){
			if(entity.getTotalRides() != null){
				entity.setTotalRides(entity.getTotalRides()+ bean.getTotalRides());
			}else{
				entity.setTotalRides(bean.getTotalRides());
			}
		}
		if(bean.getTotalEarning() != null){
			if(entity.getTotalEarning() != null){
				entity.setTotalEarning(entity.getTotalEarning()+ entity.getTotalEarning());
			}else{
				entity.setTotalEarning(bean.getTotalEarning());
			}
		}
		if(bean.getDriverEarning() != null){
			if(entity.getDriverEarning() != null){
				entity.setDriverEarning(entity.getDriverEarning()+ entity.getDriverEarning());
			}else{
				entity.setDriverEarning(bean.getDriverEarning());
			}
		}
		if(bean.getDisputeAmount() != null){
			if(entity.getDisputeAmount() != null){
				entity.setDisputeAmount(entity.getDisputeAmount()+ bean.getDisputeAmount());
			}else{
				entity.setDisputeAmount(bean.getDisputeAmount());
			}
		}
		if(bean.getTotalRequests() != null){
			if(entity.getTotalRequests() != null){
				entity.setTotalRequests(entity.getTotalRequests()+ bean.getTotalRequests());
			}else{
				entity.setTotalRequests(bean.getTotalRequests());
			}
		}
		if(bean.getTotalAcceptedRequest() != null){
			if(entity.getTotalAcceptedRequest() != null){
				entity.setTotalAcceptedRequest(entity.getTotalAcceptedRequest()+ bean.getTotalAcceptedRequest());
			}else{
				entity.setTotalAcceptedRequest(bean.getTotalAcceptedRequest());
			}
		}
		if(bean.getTotalCancelPreRides() != null){
			if(entity.getTotalCancelPreRides() != null){
				entity.setTotalCancelPreRides(entity.getTotalCancelPreRides()+ bean.getTotalCancelPreRides());
			}else{
				entity.setTotalCancelPreRides(bean.getTotalCancelPreRides());
			}
		}
		if(entity.getDate() == null){
			entity.setDate(DateUtil.getMongoDbDate(new Date()));
			
		}
	}

}
