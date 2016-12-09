package com.tgi.safeher.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;

public interface ActiveDriverLocationRepository extends MongoRepository<ActiveDriverLocationMongoEntity, String> {

	List<ActiveDriverLocationMongoEntity> findByLocNearAndIsPhysicalAndIsOnlineAndIsRequestedAndIsBooked(Point p, Distance d,String isPhysical, String isOnline, String isRequested, String isBooked,Pageable pageable);
	List<ActiveDriverLocationMongoEntity> findByLocNearAndIsPhysicalAndIsOnlineAndIsRequestedAndIsBooked(Point p, Distance d,String isPhysical, String isOnline, String isRequested, String isBooked);
	ActiveDriverLocationMongoEntity findByAppUserId(Integer id);
	Long deleteByAppUserId(Integer id);
	Long deleteByLastActiveTimeBetween(Date to, Date from);
}
