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

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class DriverNavigationRepositoryImpl implements DriverNavigationRepository{

	private static final String KEY = "navigation";

	private RedisTemplate<String, MapBean> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private HashOperations hashOps;
	
	@Autowired
	private DriverNavigationRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveDriverNavigation(String driverId,
			MapBean bean) {
		hashOps.put(KEY, driverId, bean);
	}

	@Override
	public MapBean findDriverNavigation(String driverId) {
		return (MapBean)hashOps.get(KEY, driverId);
	}

	@Override
	public void deleteDriverNavigation(String driverId) {
		hashOps.delete(KEY, driverId);
	}

	@Override
	public Map<Object, Object> findAllDriverNavigation() {
		return hashOps.entries(KEY);
	}
	
}
