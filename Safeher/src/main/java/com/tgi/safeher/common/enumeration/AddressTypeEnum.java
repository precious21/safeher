package com.tgi.safeher.common.enumeration;

public enum AddressTypeEnum {

	Mailing(1),POBox(2),Residential(3);
	private int value;
	
	private AddressTypeEnum(int code) {
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
	
}
