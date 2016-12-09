package com.tgi.safeher.repository;

import java.util.Map;

import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.jms.model.Notification;

public interface NotificationRepository {
	void saveNotification(Map<String, RideRequestResponseEntity>  rideRequestResponseEntity, String requestId);
	Map<String, RideRequestResponseEntity> findNotification(String requestId);
	public void updateNotification(Map<String,RideRequestResponseEntity>  rideRequestResponseEntity, String requestId);
	public void deleteNotification(String requestId);
}
