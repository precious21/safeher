package com.tgi.safeher.common.enumeration;

public enum PromotionTypeEnum {

	Promotion(1),Referral(2);
	private int	value;
	PromotionTypeEnum(int code)
	{
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
