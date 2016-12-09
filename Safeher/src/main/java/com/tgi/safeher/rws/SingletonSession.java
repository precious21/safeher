package com.tgi.safeher.rws;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.map.beans.MapBean;

@Component
@Scope("singleton")
public class SingletonSession {

	private static Map<String, String> sessionMap = new HashMap<String, String>();
	private static Map<String, MapBean> navigationMap = new HashMap<String, MapBean>();
	private static Map<String, String> clientUUIDMap = new HashMap<String, String>();

	public static Map<String, String> getSessionMap() {
		return sessionMap;
	}

	public static void setSessionMap(Map<String, String> sessionMap) {
		SingletonSession.sessionMap = sessionMap;
	}

	public static Map<String, MapBean> getNavigationMap() {
		return navigationMap;
	}

	public static void setNavigationMap(Map<String, MapBean> navigationMap) {
		SingletonSession.navigationMap = navigationMap;
	}

	public static Map<String, String> getClientUUIDMap() {
		return clientUUIDMap;
	}

	public static void setClientUUIDMap(Map<String, String> clientUUIDMap) {
		SingletonSession.clientUUIDMap = clientUUIDMap;
	}
	
}
