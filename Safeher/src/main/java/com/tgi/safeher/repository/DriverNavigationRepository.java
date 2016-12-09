package com.tgi.safeher.repository;

import java.util.List;
import java.util.Map;

import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.map.beans.MapBean;

public interface DriverNavigationRepository {

	public void saveDriverNavigation(String driverId,
			MapBean bean);

	public MapBean findDriverNavigation(String driverId);

	public void deleteDriverNavigation(String driverId);
	
	public Map<Object, Object> findAllDriverNavigation();
	
}
