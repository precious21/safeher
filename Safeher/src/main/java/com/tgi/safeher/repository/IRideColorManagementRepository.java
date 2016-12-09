package com.tgi.safeher.repository;

import com.tgi.safeher.beans.PreRideRequestBean;


public interface IRideColorManagementRepository {

	void saveColorStatus(PreRideRequestBean bean, String requestNo);
	PreRideRequestBean findColorStatus(String requestNo);
    void updateColorStatus(PreRideRequestBean bean, String requestNo);
    void deleteColorStatus(String requestNo);
	
}
