package com.tgi.safeher.map.service;

import com.tgi.safeher.common.exception.GenericException;

public interface IGoogleMapServices {
	public String getAddressFromLngLat(String lat, String lng)
			throws GenericException;
	public String getFormatedAddress(String lat, String lng)
			throws GenericException;
}
