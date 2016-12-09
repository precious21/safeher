package com.tgi.safeher.common.enumeration;

public enum UserRegFlowEnum {

	BasicInfo(1), ProfileImage(2), Address(3), License(4), Insuranse(5), Registraion(6), QualifiedVehicle(7), SecurityCheck(8),
	Disclosure(9), BankInfo(10);
	private int value;
	
	private UserRegFlowEnum(int code) {
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
