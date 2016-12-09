package com.tgi.safeher.common.enumeration;

public enum CredtitCardEnum {

	Visa("1"),MasterCard("2"),AmericanExpress("3"),JCB("4"),DiscoverCard("5");
	private String	value;

	CredtitCardEnum(String code)
	{
		this.value = code;
	}
	public String getValue( ) {

		return value;
	}
}
