package com.tgi.safeher.repository;

public interface ArrivalTimeRepository {

	void saveArrivalTimeFlag(long count, String passengerId);
	Long findArrivalTimeFlag(String passengerId);
    void deleteArrivalTimeFlag(String passengerId);
    
}
