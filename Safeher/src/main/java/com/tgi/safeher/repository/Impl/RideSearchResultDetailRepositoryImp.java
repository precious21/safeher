package com.tgi.safeher.repository.Impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.entity.RideSearchResultDetailEntity;
import com.tgi.safeher.repository.RideSearchResultDetailRepository;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class RideSearchResultDetailRepositoryImp implements RideSearchResultDetailRepository {

	private static final String KEY = "RideSearchResult";

	private RedisTemplate<String, RideSearchResultDetailEntity> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private RideSearchResultDetailRepositoryImp(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}
		     
	public void updateRideSearchResultDetailEntity(RideSearchResultDetailEntity  criteria) {
		hashOps.put(KEY, criteria.getAppUser().getAppUserId().toString(), criteria);
	}

	public RideSearchResultDetailEntity findRideSearchResultDetailEntity(String id) {
		return (RideSearchResultDetailEntity) hashOps.get(KEY, id);
	}

	public Map<Object, Object> findAllRideSearchResultDetailEntity() {
		return hashOps.entries(KEY);
	}

	public void deleteRideSearchResultDetailEntity(String id) {
		hashOps.delete(KEY, id);
	}

	public void saveRideSearchResultDetailEntity(RideSearchResultDetailEntity criteria) {

		hashOps.put(KEY, criteria.getAppUser().getAppUserId().toString(), criteria);

	}

	
	
	
}
