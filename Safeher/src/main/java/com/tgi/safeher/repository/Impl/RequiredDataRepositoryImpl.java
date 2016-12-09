package com.tgi.safeher.repository.Impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RequiredDataBean;
import com.tgi.safeher.repository.RequiredDataRepository;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
@Repository
public class RequiredDataRepositoryImpl implements RequiredDataRepository {

	private static final String KEY = "RequiredData";

	private RedisTemplate<String, DistanceAPIBean> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private HashOperations hashOps;
	
	@Autowired
	private RequiredDataRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveRequiredData(String passengerId,
			RequiredDataBean requiredDataBean) {
		hashOps.put(KEY, passengerId, requiredDataBean);
	}

	@Override
	public RequiredDataBean findRequiredData(String requiredDataBean) {
		return (RequiredDataBean) hashOps.get(KEY, requiredDataBean);
	}

	@Override
	public void deleteRequiredData(String passengerId) {
		hashOps.delete(KEY, passengerId);
	}
	@Override
	public void updateRequiredData(String passengerId, RequiredDataBean requiredDataBean){
		hashOps.put(KEY, passengerId, requiredDataBean);
	}
}
