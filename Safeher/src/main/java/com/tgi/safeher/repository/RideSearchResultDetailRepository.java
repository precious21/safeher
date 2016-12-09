package com.tgi.safeher.repository;

import com.tgi.safeher.entity.RideSearchResultDetailEntity;

public interface RideSearchResultDetailRepository {
	void saveRideSearchResultDetailEntity(RideSearchResultDetailEntity rideCriteriaEntity);
	RideSearchResultDetailEntity findRideSearchResultDetailEntity(String id);
}
