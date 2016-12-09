/**
 * 
 */
package com.tgi.safeher.service.safeHerMapService;


import java.util.List;
import java.util.Map;

import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RideSearchResultBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

/**
 * @author Awais Haider
 *
 */
public interface IGoogleMapsAPIService {

	public DistanceAPIBean googleDistanceAPI(DistanceAPIBean distanceAPIBean);
	public String getArriveTime(LatLng driver, LatLng passenger);
	public DistanceAPIBean googleDistanceAPIV2(DistanceAPIBean distanceAPIBean);
	
	public String googlePlacesAPITest(String latitude, String longitude, String format, String radius, String type);
	
	public DirectionsRoute[] googleDirectionAPITest(String origins, String destinations);
	
	public String getSuburbFromLngLat(LatLng latLng);
	
	public List<DistanceAPIBean> getActiveDriverDistanceAPI(String suburb);
	
	public List<DistanceAPIBean> getActiveDriverMinDistanceAPI(String lat, String lng);
	
	public List<DistanceAPIBean> getActiveDriverMaxDistanceAPI(String lat, String lng);
	
	public Map<String,Object> getLeastPickupTime(List<DistanceAPIBean> list,SafeHerDecorator decorator, LatLng latLng,Map<String,Object> map);
	
	public List<DistanceAPIBean> getActiveDriversPhysicaltoDistanceAPI();
	
	public List<DistanceAPIBean> getActiveDriverstoDistanceAPI();
	
	public List<DistanceAPIBean> getActiveDriverstoDistanceAPIMongo();
	
	public List<Map<String,Object>> getDriversWithinDistance(List<DistanceAPIBean> activeDriversList,LatLng latLng);
	
	public List<RideSearchResultBean>  pushNotificationsToActiveDrivers(SafeHerDecorator decorator) throws GenericException;
	
	public List<RideSearchResultBean>  pushNotificationsToActiveDriversV2(SafeHerDecorator decorator) throws GenericException;
	
	public String  getCityFromLngLat(LatLng latLng);
	
	public List<DistanceAPIBean> getActiveDriverMinDistanceAPIMongo(String lat, String lng);
	
	public List<DistanceAPIBean> getActiveDriverMaxDistanceAPIMongo(String lat, String lng);
	
	public List<Map<String,Object>> getDriversWithinDistanceMongo(List<DistanceAPIBean> activeDriversList,LatLng latLng);
	
	public List<RideSearchResultBean> pushNotificationsToActiveDriversMongo(SafeHerDecorator decorator) throws GenericException;
	
	

}