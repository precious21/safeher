package com.tgi.safeher.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverDrivingDetailMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;

public interface DriverDrivingDetailRepository extends MongoRepository<DriverDrivingDetailMongoEntity, Integer> {

	DriverDrivingDetailMongoEntity findByAppUserIdAndDate(Integer id, Date date);

	Long deleteByAppUserIdAndDate(Integer id, Date date);
	
	List<DriverDrivingDetailMongoEntity> findByDateBetween(Date toDate,Date fromDate);
	
	Long deleteByDateBetween(Date to, Date from);
}
