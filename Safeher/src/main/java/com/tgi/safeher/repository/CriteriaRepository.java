package com.tgi.safeher.repository;

import com.tgi.safeher.entity.RideCriteriaEntity;

public interface CriteriaRepository {

	void saveCriteria(RideCriteriaEntity rideCriteriaEntity);
	RideCriteriaEntity findCriteria(String id);
	void deleteCriteria(String id);
}
