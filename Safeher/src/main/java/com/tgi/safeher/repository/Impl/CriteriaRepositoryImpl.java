package com.tgi.safeher.repository.Impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.repository.CriteriaRepository;
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class CriteriaRepositoryImpl implements CriteriaRepository{

	private static final String KEY = "RideCriteria";

	private RedisTemplate<String, RideCriteriaEntity> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations hashOps;

	@Autowired
	private CriteriaRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}
		     
	public void updateCriteria(RideCriteriaEntity  criteria) {
		hashOps.put(KEY, criteria.getAppUser().getAppUserId().toString(), criteria);
	}

	public RideCriteriaEntity findCriteria(String id) {
		return (RideCriteriaEntity) hashOps.get(KEY, id);
	}

	public Map<Object, Object> findAllCriterias() {
		return hashOps.entries(KEY);
	}

	public void deleteCriteria(String id) {
		hashOps.delete(KEY, id);
	}

	public void saveCriteria(RideCriteriaEntity criteria) {

		hashOps.put(KEY, criteria.getAppUser().getAppUserId().toString(), criteria);

	}
	
	
}
