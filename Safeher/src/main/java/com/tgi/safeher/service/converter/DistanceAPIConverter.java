package com.tgi.safeher.service.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
public class DistanceAPIConverter implements Serializable {
	
	public List<DistanceAPIBean> convertActiveDriverEntityToDistanceBean(List<ActiveDriverLocationEntity> entities){
	
			List<DistanceAPIBean> distanceAPIBeanList = new ArrayList<DistanceAPIBean>();
			for (ActiveDriverLocationEntity entity : entities) {
				//todo check null
				DistanceAPIBean distanceAPIBean = new DistanceAPIBean();
				distanceAPIBean.setLatLngDestinations(new LatLng(Double.parseDouble(entity.getLatValue()),Double.parseDouble(entity.getLongValue())));
				distanceAPIBean.setLatDestinations(Double.parseDouble(entity.getLatValue()));
				distanceAPIBean.setLngDestinations(Double.parseDouble(entity.getLongValue()));
				if(StringUtil.isNotEmpty(entity.getPreLatValue())){
					distanceAPIBean.setPreLat(entity.getPreLatValue());
				}else{
					distanceAPIBean.setPreLat("0.0");
				}
				if(StringUtil.isNotEmpty(entity.getPreLongValue())){
					distanceAPIBean.setPreLng(entity.getPreLongValue());
				}else{
					distanceAPIBean.setPreLng("0.0");
				}
				distanceAPIBean.setAppUserId(entity.getAppUser().getAppUserId());
				if(entity.getIsBooked().equals("1")){
					distanceAPIBean.setDriverStatus("B");
				}else if(entity.getIsRequested().equals("1")){
					distanceAPIBean.setDriverStatus("R");
				}else{
					distanceAPIBean.setDriverStatus("A");
				}
				if(StringUtil.isNotEmpty(entity.getDirection())){
					distanceAPIBean.setDirection(entity.getDirection());
				}
				distanceAPIBeanList.add(distanceAPIBean);
				
			}
		
		
		return distanceAPIBeanList;
		
	}
	
	
public List<DistanceAPIBean> convertActiveDriverEntityToDistanceBeanMongo(List<ActiveDriverLocationMongoEntity> entities){
		
		List<DistanceAPIBean> distanceAPIBeanList = new ArrayList<DistanceAPIBean>();
		for (ActiveDriverLocationMongoEntity entity : entities) {
			//todo check null
			DistanceAPIBean distanceAPIBean = new DistanceAPIBean();
			distanceAPIBean.setLatLngDestinations(new LatLng(Double.parseDouble(entity.getLatValue()),Double.parseDouble(entity.getLongValue())));
			distanceAPIBean.setLoc(new Point(Double.parseDouble(entity.getLatValue()),Double.parseDouble(entity.getLongValue())));
			distanceAPIBean.setLatDestinations(Double.parseDouble(entity.getLatValue()));
			distanceAPIBean.setLngDestinations(Double.parseDouble(entity.getLongValue()));
			if(StringUtil.isNotEmpty(entity.getPreLatValue())){
				distanceAPIBean.setPreLat(entity.getPreLatValue());
			}else{
				distanceAPIBean.setPreLat("0.0");
			}
			if(StringUtil.isNotEmpty(entity.getPreLongValue())){
				distanceAPIBean.setPreLng(entity.getPreLongValue());
			}else{
				distanceAPIBean.setPreLng("0.0");
			}
			distanceAPIBean.setAppUserId(entity.getAppUserId());
			if(entity.getIsBooked().equals("1")){
				distanceAPIBean.setDriverStatus("B");
			}else if(entity.getIsRequested().equals("1")){
				distanceAPIBean.setDriverStatus("R");
			}else{
				distanceAPIBean.setDriverStatus("A");
			}
			if(StringUtil.isNotEmpty(entity.getDirection())){
				distanceAPIBean.setDirection(entity.getDirection());
			}
			distanceAPIBeanList.add(distanceAPIBean);
			
		}
	
	
	return distanceAPIBeanList;
	
}

	

}
