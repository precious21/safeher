package com.tgi.safeher.common.enumeration;

public enum ActiveDriverStatusEnum {

	Free(1), Pre_Ride(2), On_Ride(3);
	private int value;
	
	private ActiveDriverStatusEnum(int code) {
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
