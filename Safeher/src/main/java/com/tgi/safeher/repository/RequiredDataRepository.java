package com.tgi.safeher.repository;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RequiredDataBean;

public interface RequiredDataRepository {
	

	void saveRequiredData(String passengerId, RequiredDataBean requestNo);
	RequiredDataBean findRequiredData(String passengerId);
    void updateRequiredData(String passengerId, RequiredDataBean requestNo);
    void deleteRequiredData(String passengerId);
    
	

}
