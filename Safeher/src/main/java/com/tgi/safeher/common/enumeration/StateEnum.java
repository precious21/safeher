package com.tgi.safeher.common.enumeration;

public enum StateEnum {

	AK( 1 ),NY(2);
	private int	value;

	StateEnum(int code)
	{
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
