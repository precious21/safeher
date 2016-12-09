package com.tgi.safeher.repo;

import java.util.Date;
import java.util.List;

import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverDrivingDetailMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;


public interface ActiveDriverCustomRepository  {
	List<ActiveDriverLocationMongoEntity> findAll();
	public void updateLocation(Integer appUserId);
	public List<ActiveDriverLocationMongoEntity> findActiveDriversForRide();
	public DriverDrivingDetailMongoEntity findDriverDrivingDetail(Integer appUserId, Date date);
	public DriverLocationTrackMongoEntity findDriverTrack(Integer appUserId);
	public DriverLocationTrackMongoEntity findDriverTrackForOnline(Integer appUserId);
	public void updateIsSavedIntoSqlToOne(Integer appUserId, Integer idNo);
}
