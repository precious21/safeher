package com.tgi.safeher.repository.Impl;

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
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

	private static final String KEY = "Notifications";

	private RedisTemplate<String, Map<String,RideRequestResponseEntity>> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private NotificationRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}
		     
	public void updateNotification(Map<String,RideRequestResponseEntity>  rideRequestResponseEntity, String requestId) {
		hashOps.put(KEY, requestId, rideRequestResponseEntity);
	}

	public Map<String,RideRequestResponseEntity> findNotification(String requestId) {
		return (Map<String,RideRequestResponseEntity>) hashOps.get(KEY, requestId);
	}

	public Map<Object, Object> findAllNotifications() {
		return hashOps.entries(KEY);
	}

	public void deleteNotification(String requestId) {
		hashOps.delete(KEY, requestId);
	}

	public void saveNotification(Map<String,RideRequestResponseEntity> rideRequestResponseEntity,String requestId){
		
		hashOps.put(KEY, requestId, rideRequestResponseEntity);

	}
}
