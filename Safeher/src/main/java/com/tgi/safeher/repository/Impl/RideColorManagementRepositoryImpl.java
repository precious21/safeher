package com.tgi.safeher.repository.Impl;


import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.jms.model.Notification;
import com.tgi.safeher.repository.IRideColorManagementRepository;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RideRequestRepository;
import com.tgi.safeher.repository.RideRequestResponseRepository;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class RideColorManagementRepositoryImpl implements IRideColorManagementRepository{

	private static final String KEY = "RideColorManagement";

	private RedisTemplate<String, RideRequestResponseEntity> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private HashOperations hashOps;
	
	@Autowired
	private RideColorManagementRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveColorStatus(PreRideRequestBean bean, String requestNo) {
		hashOps.put(KEY, requestNo, bean);
	}

	@Override
	public PreRideRequestBean findColorStatus(String requestNo) {
		return (PreRideRequestBean) hashOps.get(KEY, requestNo);
	}

	@Override
	public void updateColorStatus(PreRideRequestBean bean, String requestNo) {
		hashOps.put(KEY, requestNo, bean);
	}

	@Override
	public void deleteColorStatus(String requestNo) {
		hashOps.delete(KEY, requestNo);
	}

	
	
}
