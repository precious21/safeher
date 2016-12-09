package com.tgi.safeher.API.thirdParty.Andriod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.utils.StringUtil;

@Component
@Scope("prototype")
@EnableAsync
public class PushAndriod {
	final static String Passeger_API_KEY = "AIzaSyCI087lvxgOy_f6k7cDPHDW01JaQU8rtFw"; 
	final static String Driver_API_KEY = "AIzaSyCI087lvxgOy_f6k7cDPHDW01JaQU8rtFw";
	private static final Logger logger = Logger.getLogger(PushAndriod.class);
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;

	@Async
	public void pushAndriodPassengerNotification(String keyToken,
			String parm1, String param2, String param3,String parm4,
			String Notify,String Message) throws IOException, GenericException {
		logger.info("**************Entering in pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify);
		//String collpaseKey = "1";
		Sender sender = new Sender(Passeger_API_KEY);
		Message.Builder builder = new Message.Builder();
		//builder.collapseKey(collpaseKey);
		builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		Map<String, String> dataMap = new HashMap<String, String>();
		switch (Notify) {
		case "PreRideRequest":
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideRequest.getValue());
			break;
		case "AcceptRideFromDriver":
			dataMap.put("PassengerId", parm1);
			dataMap.put("DriverId", param2);
			dataMap.put("RideRequestId", param3);
			dataMap.put("RequestNo", parm4);
			dataMap.put("notificationType",
					PushNotificationStatus.AcceptRideFromDriver
							.getValue());
			break;
		case "PreRideStart":
			dataMap.put("PassengerAppId", parm1);
			dataMap.put("DriverAppId", param2);
			dataMap.put("PreRideId", param3);
			dataMap.put("EstimatedTime", parm4);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideStart.getValue());
			break;
		case "PreRideLateOrCancelRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideLateOrCancelRequest.getValue());
			break;
		case "PreRideCancelRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideCancelRequest
							.getValue());
			break;
		case "InvoiceRequest":
		dataMap.put("InvoiceNo", parm1);
		dataMap.put("RideNo", param2);
		dataMap.put("paymentDone", param3);
		dataMap.put("discount", parm4);
		dataMap.put("notificationType",
				PushNotificationStatus.InvoiceRequest
						.getValue());
			break;
		case "startRide":
			dataMap.put("RideNo", param2);
//			dataMap.put("currentAddress", param3);
			dataMap.put("startAddress", parm1);
			dataMap.put("startLat", param3);
			dataMap.put("startLong", parm4);
			dataMap.put("notificationType",
					PushNotificationStatus.startRide.getValue());
			break;
		case "PreRideLateOrCancelRequestDriver":
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideLateOrCancelRequestDriver.getValue());
			break;	
		case "ColorVerification":
			dataMap.put("notificationType",
					PushNotificationStatus.ColorVerification.getValue());
			break;
		case "SuccessfullVerification":
			dataMap.put("notificationType",
					PushNotificationStatus.SuccessfullVerification.getValue());
			break;
		case "NoActiveDriverInSuburb":
		dataMap.put("notificationType",
				PushNotificationStatus.NoActiveDriverInSuburb.getValue());
		break;
		case "ColorVerificationFailed":
			dataMap.put("notificationType",
					PushNotificationStatus.ColorVerificationFailed.getValue());
			break;
		case "StartDestinationReached":
			dataMap.put("notificationType",
					PushNotificationStatus.StartDestinationReached.getValue());
			break;
		case "driverJustArrived":
			dataMap.put("notificationType",
					PushNotificationStatus.driverJustArrived.getValue());
			break;
		case "PreRideBillingRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideBillingRequest.getValue());
			break;
		case "CancelRideFromDriver":
			dataMap.put("RequestNo", parm4);
			dataMap.put("notificationType",
					PushNotificationStatus.CancelRideFromDriver.getValue());
			break; 
		case "SignOutUser":
		    dataMap.put("notificationType",
		    	      PushNotificationStatus.SignOutUser.getValue());
		   break;
		case "PaymentCancel":
			dataMap.put("notificationType",
					PushNotificationStatus.PaymentCancel.getValue());
			break;
		case "Payment":
			dataMap.put("paymentDone", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.Payment.getValue());
			break;
			
		default:
			dataMap.put("notificationType", "DefaultNotification");
		}
		dataMap.put("message", Message);
		builder.setData(dataMap);
		Message message = builder.build();
		
		
		
//		Result result = sender.send(message, keyToken, 10);
//		System.out.println("Passenger Notification Result " + result);
//		if (result.getCanonicalRegistrationId() != null ) {
//			String canonicalRegId = result.getCanonicalRegistrationId();
//			System.out.println("canonicalRegId = " + canonicalRegId);
//		} else {
//			String error = result.getErrorCodeName();
//			System.out.println("Broadcast failure: " + error);
//		}
		
		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(keyToken);

		//MulticastResult result = sender.send(message, androidTargets, 1);
		Result result = sender.sendNoRetry(message, keyToken);
		System.out.println("result = " + result);
		
		String errorCode = result.getErrorCodeName();
		logger.info("**************Notification result in pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify + "with notification "+result +" with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap)+ "with Error Code " + errorCode +"*************");
		/*if (result.getResults() != null) {
			int canonicalRegId = result.getCanonicalIds();
			System.out.println("canonicalRegId = " + canonicalRegId);
			if (result.getResults().get(0).equals("error")) {
				if(result.getResults().get(0).getErrorCodeName()
						.equalsIgnoreCase("InvalidRegistration")){
					throw new GenericException("Invalid Token");
				}
			}
		} else {
			int error = result.getFailure();
			System.out.println("Broadcast failure: " + error);
		}*/
		
		if (result.getCanonicalRegistrationId() != null) {
			logger.info("**************Notification result in canonicalRegId"+result.getCanonicalRegistrationId()+"*************");
			String canonicalRegId = result.getCanonicalRegistrationId();
			asyncServiceImpl.replaceTokensAndroid(keyToken, canonicalRegId);
			System.out.println("canonicalRegId = " + canonicalRegId);
		} /*else {
			String error = result.getErrorCodeName();
			System.out.println("Broadcast failure: " + error);
		}*/
		if(errorCode !=null || StringUtil.isNotEmpty(errorCode)){
			logger.info("**************Exiting from pushAndriodPassengerNotification with error code excep"+errorCode+" *****");
			System.out.println("Broadcast failure: " + result.getErrorCodeName());
			throw new GenericException(errorCode);
			
		}
		logger.info("**************Exiting from pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify);

	}

	@Async
	public void pushAndriodPassengerNotificationForSignOutAnotherDevice(String keyToken,
			String parm1, String param2, String param3,String parm4,
			String Notify,String Message) throws IOException, GenericException {
		logger.info("**************Entering in pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify);
		//String collpaseKey = "1";
		Sender sender = new Sender(Passeger_API_KEY);
		Message.Builder builder = new Message.Builder();
		//builder.collapseKey(collpaseKey);
		builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		Map<String, String> dataMap = new HashMap<String, String>();
		switch (Notify) {
		case "SignOutUser":
		    dataMap.put("notificationType",
		    	      PushNotificationStatus.SignOutUser.getValue());
		   break;
		   default:
			dataMap.put("notificationType", "DefaultNotification");
		}
		dataMap.put("message", Message);
		builder.setData(dataMap);
		Message message = builder.build();
		
		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(keyToken);

		//MulticastResult result = sender.send(message, androidTargets, 1);
		Result result = sender.sendNoRetry(message, keyToken);
		System.out.println("result = " + result);
		
		String errorCode = result.getErrorCodeName();
		logger.info("**************Notification result in pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify + "with notification "+result +" with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap)+ "with Error Code " + errorCode +"*************");
		/*if (result.getResults() != null) {
			int canonicalRegId = result.getCanonicalIds();
			System.out.println("canonicalRegId = " + canonicalRegId);
			if (result.getResults().get(0).equals("error")) {
				if(result.getResults().get(0).getErrorCodeName()
						.equalsIgnoreCase("InvalidRegistration")){
					throw new GenericException("Invalid Token");
				}
			}
		} else {
			int error = result.getFailure();
			System.out.println("Broadcast failure: " + error);
		}*/
		
		if (result.getCanonicalRegistrationId() != null) {
//			logger.info("**************Notification result in canonicalRegId"+result.getCanonicalRegistrationId()+"*************");
//			String canonicalRegId = result.getCanonicalRegistrationId();
//			asyncServiceImpl.replaceTokensAndroid(keyToken, canonicalRegId);
//			System.out.println("canonicalRegId = " + canonicalRegId);
		} /*else {
			String error = result.getErrorCodeName();
			System.out.println("Broadcast failure: " + error);
		}*/
		if(errorCode !=null || StringUtil.isNotEmpty(errorCode)){
			logger.info("**************Exiting from pushAndriodPassengerNotification with error code excep"+errorCode+" *****");
			System.out.println("Broadcast failure: " + result.getErrorCodeName());
			throw new GenericException(errorCode);
			
		}
		logger.info("**************Exiting from pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify);

	}

	@Async
	public void pushAndriodDriverNotification(String keyToken,
			String parm1, String param2, String param3, String param4,
			String Notify,String Message) throws IOException, GenericException {
		logger.info("**************Entering in pushAndriodDriverNotification with Key Token "+keyToken + " with notification "+ Notify +"*************");
		String collpaseKey = "1";
		Sender sender = new Sender(Driver_API_KEY);
		Message.Builder builder = new Message.Builder();
		//builder.collapseKey(collpaseKey);
		builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		Map<String, String> dataMap = new HashMap<String, String>();
		switch (Notify) {
		case "PreRideRequest":
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideRequest.getValue());
			break;
		case "PreRideLateOrCancelRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideLateOrCancelRequest.getValue());
			break;
		case "PreRideCancelRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideCancelRequest
							.getValue());
			break;
		case "ColorVerification":
			dataMap.put("notificationType",
					PushNotificationStatus.ColorVerification.getValue());
			break;
		case "ColorVerificationFailed":
			dataMap.put("notificationType",
					PushNotificationStatus.ColorVerificationFailed.getValue());
			break;
		case "SuccessfullVerification":
			dataMap.put("notificationType",
					PushNotificationStatus.SuccessfullVerification.getValue());
			break;
		case "RideCancelByEmergency":
			dataMap.put("notificationType",
				PushNotificationStatus.RideCancelByEmergency.getValue());
		break;	
		case "StartDestinationReached":
			dataMap.put("notificationType",
					PushNotificationStatus.StartDestinationReached.getValue());
			break;
			
		case "AcceptRideFromPassenger":
			dataMap.put("PassengerAppId", parm1);
			dataMap.put("DriverAppId", param2);
			dataMap.put("PreRideId", param3);
			dataMap.put("rideReqResId", param4);
			dataMap.put("notificationType",
					PushNotificationStatus.AcceptRideFromPassenger.getValue());
			break;
		case "NoActiveDriverInSuburb":
			dataMap.put("PassengerId", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.NoActiveDriverInSuburb.getValue());
			break;	
		case "PreRideBillingRequest":
			dataMap.put("Reason", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.PreRideBillingRequest.getValue());
			break;
		case "searchRideCriteria":
			dataMap.put("PassengerAppId", parm1);
			dataMap.put("DriverAppId", param2);
			dataMap.put("requestNo", param3);
			dataMap.put("RideRequestId", param4);
			dataMap.put("notificationType",
					PushNotificationStatus.searchRideCriteria.getValue());
			break;
		case "EndRideConfirmPassenger":
			dataMap.put("InvoiceNo", parm1);
			dataMap.put("RideNo", param2);
			dataMap.put("notificationType",
					PushNotificationStatus.EndRideConfirmPassenger
							.getValue());
			break;		
		case "RideTransactionSuccess":
			dataMap.put("notificationType",
					PushNotificationStatus.RideTransactionSuccess.getValue());
			break;	
		case "PassengerMidEndRide":
			dataMap.put("notificationType",
					PushNotificationStatus.PassengerMidEndRide.getValue());
			break;
		case "PassengerPaymentSuccessful":
			dataMap.put("InvoiceNo", parm1);
			dataMap.put("RideNo", param2);
			dataMap.put("notificationType",
					PushNotificationStatus.PassengerPaymentSuccessful
							.getValue());
			break;
		case "midRideRequest":
			dataMap.put("RequestCode", parm1);
			dataMap.put("notificationType",
					PushNotificationStatus.midRideRequest
							.getValue());
			break;
		default:
			dataMap.put("notificationType", "DefaultNotification");
		}
		builder.setData(dataMap);
		Message message = builder.build();
		Result result = sender.sendNoRetry(message, keyToken);
		String errorCode = result.getErrorCodeName();
		logger.info("**************Notification Result  in pushAndriodDriverNotification with Key Token "+keyToken + " with notification "+ Notify + " with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap) +"with Error Code "+errorCode+"*************");
		System.out.println("Driver Notification Result  : " + result);
		if (result.getCanonicalRegistrationId() != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			asyncServiceImpl.replaceTokensAndroid(keyToken, canonicalRegId);
			System.out.println("canonicalRegId = " + canonicalRegId);
		} /*else {
			String error = result.getErrorCodeName();
			System.out.println("Broadcast failure: " + error);
		}*/
		if(errorCode !=null || StringUtil.isNotEmpty(errorCode)){
			logger.info("**************Exiting from pushAndriodDriverNotification Broadcast failure excep: " + result.getErrorCodeName()+" *******");
			System.out.println("Broadcast failure: " + result.getErrorCodeName());
			throw new GenericException(errorCode);
		}
		logger.info("**************Exiting from pushAndriodDriverNotification with Key Token "+keyToken + " with notification "+ Notify +"*************");

	}
	
	public static void main(String args[]){
		PushAndriod andriod = new PushAndriod();
		try {
			andriod.pushAndriodPassengerNotification("APA91bE093kRkw4AWmgH33vkMXHs7gHyOz0_RPEhOXZT7Sa1A_Kk0EqjIjaqG18UDWeKA57XHYUaGbzuMUi7q3usEdmx5BWSQaS7uStadKrbtjFjw0HgxeRxbv9ehL5BmuoAaabrYeBU", "1", "1", "1", "1", "abv", "HELLLLOOOO");
			System.out.println("abc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
