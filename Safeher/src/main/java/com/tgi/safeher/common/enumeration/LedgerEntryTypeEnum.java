package com.tgi.safeher.common.enumeration;

public enum LedgerEntryTypeEnum {

	FineCharged(1), BillPayment(2), Charged(3);
	private int value;

	LedgerEntryTypeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
