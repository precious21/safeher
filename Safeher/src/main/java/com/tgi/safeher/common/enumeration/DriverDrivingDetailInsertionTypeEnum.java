package com.tgi.safeher.common.enumeration;

public enum DriverDrivingDetailInsertionTypeEnum {
	TotalRequests(1), TotalAcceptRequests(2), TotalRides(3), NoOfPreRideCancel(4), TimeDistance(5),
	TotalEarning(6), MyEarning(7), DisputedAmount(8);
		           
	private int value;

	DriverDrivingDetailInsertionTypeEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}
}
