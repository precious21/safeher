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
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.repository.RideRequestResponseRepository;
import com.tgi.safeher.repository.DriverNavigationRepository;
import com.tgi.safeher.repository.SocketClientUUIDRepository;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class SocketClientUUIDRepositoryImpl implements SocketClientUUIDRepository{

	private static final String KEY = "UUID";

	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private HashOperations hashOps;
	
	@Autowired
	private SocketClientUUIDRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveClientSocketUUID(String appUserID, String clientUUID) {
		hashOps.put(KEY, appUserID, clientUUID);		
	}

	@Override
	public String findClientSocketUUID(String appUserId) {
		return (String) hashOps.get(KEY, appUserId);
	}
	
}
