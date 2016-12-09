package com.tgi.safeher.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;

public interface DriverLocationTrackRepository extends MongoRepository<DriverLocationTrackMongoEntity, Integer> {

//	List<ActiveDriverLocationMongoEntity> findByLocNearAndIsPhysicalAndIsOnlineAndIsRequestedAndIsBooked(Point p, Distance d,String isPhysical, String isOnline, String isRequested, String isBooked);
	DriverLocationTrackMongoEntity findByAppUserId(Integer id);
	Long deleteByAppUserId(Integer id);
//	Long deleteByLastActiveTimeBetween(Date to, Date from);
}
