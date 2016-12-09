package com.tgi.safeher.common.enumeration;

public enum MidRideRequest {
	
	R0("Passenger Have Problem"),R1("Turn On AC"),R2("Turn Off AC"),R3("Please Decrease Temprature"),R4("Please Increase Temprature"),R5("Turn On Music"),R6("Turn Off Music"),R7("Please Volume Down"),R8("Please Volume Up"),R9("Please Provide Charger");
	private String value;   
	MidRideRequest(String code) {

		this.value = code;
	}

	public String getValue() {

		return value;
	}
}
