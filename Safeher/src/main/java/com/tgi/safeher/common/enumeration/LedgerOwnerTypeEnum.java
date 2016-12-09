package com.tgi.safeher.common.enumeration;

public enum LedgerOwnerTypeEnum {
	Driver(1), Passenger(2), SafeHer(3), Charity(4), ThrdParty(5);
	private int value;

	LedgerOwnerTypeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
