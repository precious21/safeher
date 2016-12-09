package com.tgi.safeher.repository;


public interface RequestStatusRepository {

	void saveRequestStatus(String status, String requestNo);
	String findRequestStatus(String requestNo);
    void updateRequestStatus(String status, String requestNo);
    void deleteRequestStatus(String requestNo);
	
	
}
