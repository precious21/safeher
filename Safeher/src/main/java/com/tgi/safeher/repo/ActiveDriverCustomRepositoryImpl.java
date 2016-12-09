package com.tgi.safeher.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.entity.mongo.DriverDrivingDetailMongoEntity;
import com.tgi.safeher.entity.mongo.DriverLocationTrackMongoEntity;

@Repository
public class ActiveDriverCustomRepositoryImpl implements ActiveDriverCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<ActiveDriverLocationMongoEntity> findAll() {
		return mongoTemplate.findAll(ActiveDriverLocationMongoEntity.class);
	}
	
	@Override
	public void updateLocation(Integer appUserId) {
		Query query=new Query(Criteria.where("appUserId").is(appUserId));
		Update update =new Update().set("isOnline","0").set("isRequested", "0").set("isBooked", "0");
		WriteResult rr = mongoTemplate.updateFirst(query, update, ActiveDriverLocationMongoEntity.class);
	}

	@Override
	public List<ActiveDriverLocationMongoEntity> findActiveDriversForRide() {
		LinkedList circle = new LinkedList();
        // Set the center - 10gen Office
        circle.addLast(new double[] { 31.447731, 74.321376 });

        // Set the radius
        circle.addLast(1000);
		Query query = new Query(Criteria.where("isRequested").is(
				"0").and("isOnline").is("1").and("isBooked").is("0").and("isPhysical").is("1").and("loc").
				is(new BasicDBObject("$geoWithin", new BasicDBObject("$center", circle))));
		
				//.//and("minDistance").is(1.0).
//				and("maxDistance").is(5.0)).limit(5);
//				.near(new Point(31.447675, 74.321296)).minDistance(1.0*8046.72).maxDistance(5.0*8046.72)).limit(5);
		
		System.out.println(query);
		List<ActiveDriverLocationMongoEntity> list = mongoTemplate.
				find(query, ActiveDriverLocationMongoEntity.class);
		
		return list;
	}

	@Override
	public DriverDrivingDetailMongoEntity findDriverDrivingDetail(Integer appUserId, Date date) {
//		Query query = new Query().addCriteria(Criteria.where("appUserId").is(appUserId).and("date").gt(date).lte(date));
		Query query=new Query(Criteria.where("appUserId").is(appUserId).and("date").is(date));
		System.out.println(query);
		return (DriverDrivingDetailMongoEntity) mongoTemplate.findOne(
				query, DriverDrivingDetailMongoEntity.class);
	}

	@Override
	public DriverLocationTrackMongoEntity findDriverTrack(Integer appUserId) {
		Criteria abc = Criteria.where("isSavedIntoSql").is("0");
		Query query=new Query(Criteria.where("appUserId").is(appUserId).and("list").elemMatch(abc));
		System.out.println(query);
		return mongoTemplate.findOne(query, DriverLocationTrackMongoEntity.class);
	}

	@Override
	public void updateIsSavedIntoSqlToOne(Integer appUserId, Integer idNo) {
		
		Query query=new Query(Criteria.where("appUserId").is(appUserId).and("list.idNo").is(idNo));
		Update update =new Update().set("list.$.isSavedIntoSql","1");
		WriteResult rr = mongoTemplate.updateFirst(query, update, DriverLocationTrackMongoEntity.class);
	}

	@Override
	public DriverLocationTrackMongoEntity findDriverTrackForOnline(Integer appUserId) {
		Criteria abc = Criteria.where("isSavedIntoSql").is("0").and("rideNo").is("");
		Query query=new Query(Criteria.where("appUserId").is(appUserId).and("list").elemMatch(abc));
		System.out.println(query);
		return mongoTemplate.findOne(query, DriverLocationTrackMongoEntity.class);
	}

}
