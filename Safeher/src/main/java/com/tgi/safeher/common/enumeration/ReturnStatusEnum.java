package com.tgi.safeher.common.enumeration;

import org.springframework.stereotype.Component;

public enum ReturnStatusEnum {

	SUCCESFUL( "RSC001" ),
	INVALID_DATA( "RSC002" ),
	FAILURE( "RSC003" ),
	NO_RESULT( "RSC004" ),
	EMPTY_REQUEST( "RSC005" );
	
	private String	value;

	ReturnStatusEnum( String code ) {

		this.value = code;
	}

	public String getValue( ) {

		return value;
	}
}
