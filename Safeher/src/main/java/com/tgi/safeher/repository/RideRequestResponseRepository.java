package com.tgi.safeher.repository;

import com.tgi.safeher.entity.RideRequestResponseEntity;

public interface RideRequestResponseRepository {
	
	void saveRideRequestResponseEntity(RideRequestResponseEntity rideRequestResponseEntity);
	RideRequestResponseEntity findRideRequestResponseEntity(String id);

}
