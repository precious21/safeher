package com.tgi.safeher.repository.Impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.repository.RideRequestResponseRepository;
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class RideRequestResponseRepositoryImp implements RideRequestResponseRepository {

	private static final String KEY = "RideSearchResult";

	private RedisTemplate<String, RideRequestResponseEntity> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private RideRequestResponseRepositoryImp(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}
		     
	public void updateRideRequestResponseEntity(RideRequestResponseEntity  criteria) {
		hashOps.put(KEY, criteria.getDriverId(), criteria);
	}

	public RideRequestResponseEntity findRideRequestResponseEntity(String id) {
		return (RideRequestResponseEntity) hashOps.get(KEY, id);
	}

	public Map<Object, Object> findAllRideRequestResponseEntity() {
		return hashOps.entries(KEY);
	}

	public void deleteRideRequestResponseEntity(String id) {
		hashOps.delete(KEY, id);
	}

	public void saveRideRequestResponseEntity(RideRequestResponseEntity criteria) {

		hashOps.put(KEY, criteria.getDriverId(), criteria);

	}

	

}
