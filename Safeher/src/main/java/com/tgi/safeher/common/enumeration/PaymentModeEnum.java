package com.tgi.safeher.common.enumeration;

public enum PaymentModeEnum {
	PayPal(1), Creditcard(2), Account(3);
		           
	private int value;

	PaymentModeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
