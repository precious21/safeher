package com.tgi.safeher.common.enumeration;

public enum ProcessStateAndActionEnum {

	
	SEARCH_RIDE(1), PRE_RIDE(2) ,START_RIDE(3), END_RIDE(4), Search_Ride_By_Passenger(1), Accept_And_Navigate_By_Driver(2),
	Accept_Request_By_Passenger(3),Cancel_Request_By_Passenger(4),Cancel_Request_By_Driver(5),Start_Pre_Ride_By_Driver(6),
	Color_Match(7),Cancel_Ride_On_Color_Match_By_Driver(8),Cancel_Ride_On_Color_Match_By_Passenger(9),Start_Ride_By_Driver(10),
	End_Ride_By_Driver(11),End_Ride_By_Passenger(12),Payment_By_PayPal(13),Color_Matched_By_Driver(14),Color_Matched_By_Passenger(15),
	Color_Matched(16),Canel_Late_Reason_By_Driver(17),Canel_Late_Reason_By_Passenger(18);
	
	private int	value;
	ProcessStateAndActionEnum(int code)
	{
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
