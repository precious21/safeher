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
import com.tgi.safeher.repository.ArrivalTimeRepository;
import com.tgi.safeher.repository.NotificationRepository;
import com.tgi.safeher.repository.RequestStatusRepository;
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class ArrivalTimeRepositoryImp implements ArrivalTimeRepository{

	private static final String KEY = "ArrivalTime";

	private RedisTemplate<String, Map<String, String>> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private ArrivalTimeRepositoryImp(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveArrivalTimeFlag(long count, String passengerId) {
		hashOps.put(KEY, passengerId, count);
		
	}

	@Override
	public Long findArrivalTimeFlag(String passengerId) {
		return (Long) hashOps.get(KEY, passengerId);
	}

	@Override
	public void deleteArrivalTimeFlag(String passengerId) {
		hashOps.delete(KEY, passengerId);
		
	}
}

