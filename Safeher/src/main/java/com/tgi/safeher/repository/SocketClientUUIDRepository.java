package com.tgi.safeher.repository;

import java.util.List;
import java.util.Map;

import com.tgi.safeher.entity.RideRequestResponseEntity;
import com.tgi.safeher.map.beans.MapBean;

public interface SocketClientUUIDRepository {

	public void saveClientSocketUUID(String appUserID,
			String clientUUID);

	public String findClientSocketUUID(String appUserId);
	
}
