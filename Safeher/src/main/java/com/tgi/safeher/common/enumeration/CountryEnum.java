package com.tgi.safeher.common.enumeration;

public enum CountryEnum {

	USA( 1 );
	private int	value;

	CountryEnum(int code)
	{
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
