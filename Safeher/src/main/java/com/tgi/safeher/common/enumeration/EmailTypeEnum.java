package com.tgi.safeher.common.enumeration;

public enum EmailTypeEnum {
	AccountCreation(2),InCompleteDate(4), DraftAccountCreatedConfirmationEmail(5), Completion(6), InvalidState(7), 
	NoMale(10), AccountVerification(3), CompletionPassenger(18);
	private int value;

	private EmailTypeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
