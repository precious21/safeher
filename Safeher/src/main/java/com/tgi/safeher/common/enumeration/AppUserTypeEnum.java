package com.tgi.safeher.common.enumeration;

public enum AppUserTypeEnum {
	Driver(1), Passenger(2);
		           
	private int value;

	AppUserTypeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
