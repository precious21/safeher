package com.tgi.safeher.repository.Impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.repository.ActiveDriverStatusRepository;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RequestStatusRepository;
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class ActiveDriverStatusRepositoryImp implements ActiveDriverStatusRepository{

	private static final String KEY = "ActiveDriverStaus";

	private RedisTemplate<String, Map<String, String>> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private ActiveDriverStatusRepositoryImp(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}
		     
	public void updateRequestStatus(String status, String requestNo) {
		hashOps.put(KEY, requestNo, status);
	}

	public String findRequestStatus(String requestNo) {
		return (String) hashOps.get(KEY, requestNo);
	}

	public Map<Object, Object> findAllStatus() {
		return hashOps.entries(KEY);
	}

	public void deleteRequestStatus(String requestId) {
		hashOps.delete(KEY, requestId);
	}

	public void saveRequestStatus(String status,String requestNo){
		
		hashOps.put(KEY, requestNo, status);

	}
}

