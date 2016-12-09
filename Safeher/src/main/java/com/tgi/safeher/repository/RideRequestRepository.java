package com.tgi.safeher.repository;

import java.util.List;
import java.util.Map;

import com.tgi.safeher.entity.RideRequestResponseEntity;

public interface RideRequestRepository {

	public void saveRideRequestResponseResultDetail(String requestNo,
			RideRequestResponseEntity rideRequestResponseEntity);

	public RideRequestResponseEntity findNotification(String requestNo);

	public void delateRideRequestResponseResultDetail(String requestNo);
	
	public Map<Object, Object> findAllRideRequestResponseResultDetail();
}
