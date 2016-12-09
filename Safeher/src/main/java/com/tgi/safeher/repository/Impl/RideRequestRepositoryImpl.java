package com.tgi.safeher.repository.Impl;


import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.jms.model.Notification;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.repository.RideRequestResponseRepository;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class RideRequestRepositoryImpl implements RideRequestRepository{

	private static final String KEY = "RideRequestResponse";

	private RedisTemplate<String, RideRequestResponseEntity> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private HashOperations hashOps;
	
	@Autowired
	private RideRequestRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveRideRequestResponseResultDetail(String requestNo,
			RideRequestResponseEntity rideRequestResponseEntity) {
		hashOps.put(KEY, requestNo, rideRequestResponseEntity);
	}

	@Override
	public RideRequestResponseEntity findNotification(String requestNo) {
		return (RideRequestResponseEntity) hashOps.get(KEY, requestNo);
	}

	@Override
	public void delateRideRequestResponseResultDetail(String requestNo) {
		hashOps.delete(KEY, requestNo);
	}

	@Override
	public Map<Object, Object> findAllRideRequestResponseResultDetail() {
		return hashOps.entries(KEY);
	}
	
}
