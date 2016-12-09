package com.tgi.safeher.common.enumeration;

public enum PushNotificationStatus {

	PreRideBillingRequest("Pre_Ride_Billing_Request"),searchRideCriteria("Search_Request_Ride_From_Passenger"),SuccessfullVerification("SuccessfullyVerifiedColor"),InvoiceRequest("Invoice_Request"), PreRideLateOrCancelRequestDriver("Pre_Ride_Late_Cancel_Request_Driver")
	,PreRideStart("Pre_Ride_Start"),PreRideCancelRequest("Pre_Ride_Cancel_Request"),AcceptRideFromPassenger("Accept_Ride_From_Passenger"), StartDestinationReached("Start_Destination_Reached"),ColorVerificationFailed("ColorVerification_Failed"),NoActiveDriverInSuburb("No_Driver_FOUND_In_Suburb"),
	RideTransactionSuccess("Ride_Transaction_Success"), driverJustArrived("Driver_just_Arrived"),PreRideLateOrCancelRequest("Pre_Ride_Late_Cancel_Request"), 
	EndRideConfirmPassenger("End_Ride_ConfirmByPassenger"), ColorVerification("ColorVerification"),PreRideRequest("Pre_Ride_Request"), 
	PassengerMidEndRide("Passenger_Mid_End_Ride"), PassengerPaymentSuccessful("Passenger_Payment_Successful"), 
	AcceptRideFromDriver("Accept_Ride_From_Driver"),CancelRideFromDriver("Cancel_Ride_From_Driver"),
	CancelRideFromPassenger("Cancel_Ride_From_Passenger"),RideCancelByEmergency("Ride_Cancelled"),startRide("Start_Ride_From_Driver"),
	midRideRequest("Mid_Ride_Request"),CancelRideOnColorMatchByPassenger("Cancel_Ride_On_Color_Match_By_Passenger"),
	CancelRideOnColorMatchByDriver("Cancel_Ride_On_Color_Match_By_Driver"),ColorMatchedByDriver("Color_Matched_By_Driver"),
	ColorMatchedByPassenger("Color_Matched_By_Passenger"),PaymentCancel("Payment_Cancel"),SignOutUser("Sign_Out_User"),Payment("Payment");


	private String value;

	PushNotificationStatus(String code) {

		this.value = code;
	}

	public String getValue() {

		return value;
	}
}
