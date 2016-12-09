package com.tgi.safeher.API.thirdParty.Andriod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.utils.StringUtil;

public class PushNotificationsAndriod {

	private static final Logger logger = Logger.getLogger(PushNotificationsAndriod.class);
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	public  void pushAndriod() throws IOException, GenericException {
		/*final String API_KEY = "AIzaSyCI087lvxgOy_f6k7cDPHDW01JaQU8rtFw"; // sender
																			// id
																			// got
																			// from
																			// google
																			// api
																			// console
																			// project
		String collpaseKey = "1"; // if messages are sent and not delivered yet
									// to android device (as device might not be
									// online), then only deliver latest message
									// when device is online
		String messageStr = "fsf content here"; // actual message content
		String messageId = "APA91bHnMLDnDNsS4CPMDre1Kqf6fJ6BiOF-ShQWhbLD68O4fs2uBewJSegnw-Hin2uIlj2jO__es-PotgyUwptqsKk3esgRBciOgsKSiPnGG4hB3pdeSZTU1Sy1c08biqX9-z78WXaz"; // gcm
																																											// registration
																																											// id
																																											// of
																																											// android
																																											// device

		Sender sender = new Sender(API_KEY);
		Message.Builder builder = new Message.Builder();

		builder.collapseKey(collpaseKey);
		//builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		builder.addData("message", messageStr);

		Message message = builder.build();

		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(messageId);

		MulticastResult result = sender.send(message, androidTargets, 1);

		System.out.println("result = " + result);

		if (result.getResults() != null) {
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
	}

	public static void main(String[] args) {
		try {
			//pushAndriod();
			System.out.println("message sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pushAndriod(String keyToken, String passengerId,
			String driverId, String rideReqResId, String Notify)
			throws IOException, GenericException {
		final String API_KEY = "AIzaSyCI087lvxgOy_f6k7cDPHDW01JaQU8rtFw"; // sender
																			// id
																			// got
																			// from
																			// google
																			// api
																			// console
																			// project
		String collpaseKey = "1"; // if messages are sent and not delivered yet
									// to android device (as device might not be
									// online), then only deliver latest message
									// when device is online
		String messageStr = "fsf content here"; // actual message content
		// String messageId =
		// "APA91bEbz4Uawj9thvKymeLTtHqUi0lncWYVcAXiJJfg9Ikd210z1FNqoHyFl2vE-fBbdxGnJltNmjP3DzG1aikHGm_LucwH79fbuwGrBiPsYPGdi7C6Jk689TF8WEdrPstssNaKO3Bq";
		// //gcm registration id of android device

		Sender sender = new Sender(API_KEY);
		Message.Builder builder = new Message.Builder();
		Map<String, String> dataMap = new HashMap<String, String>();
		//builder.collapseKey(collpaseKey);
		builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		dataMap.put("PassengerId", passengerId);
		dataMap.put("DriverId", driverId);
		dataMap.put("notificationType", Notify);
		dataMap.put("RideRequestId", rideReqResId);
		builder.setData(dataMap);
		// builder.addData("message", "PassengerId :" + passengerId
		// + ", DriverId: " + driverId + ",NotificationType :" +
		// Notify+" , RideRequestId: "+rideReqResId);

		Message message = builder.build();

		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(keyToken);

		//MulticastResult result = sender.send(message, androidTargets, 1);
		Result result = sender.sendNoRetry(message, keyToken);
		String errorCode = result.getErrorCodeName();
		System.out.println("result = " + result);

/*		if (result.getResults() != null) {
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
			System.out.println("Broadcast failure: " + result.getErrorCodeName());
			throw new GenericException(errorCode);
		}

	}

	public  void pushAndriodForPassengerRide(String keyToken,
			String passengerId, String driverId, String rideReqResId,
			String Notify, String RequestNo) throws IOException, GenericException {
		logger.info("**************Entering in pushAndriodForPassengerRide with Key Token "+keyToken + " with notification "+ Notify);
		final String API_KEY = "AIzaSyCI087lvxgOy_f6k7cDPHDW01JaQU8rtFw"; // sender
																			// id
																			// got
																			// from
																			// google
																			// api
																			// console
																			// project
		String collpaseKey = "1"; // if messages are sent and not delivered yet
									// to android device (as device might not be
									// online), then only deliver latest message
									// when device is online
		String messageStr = "fsf content here"; // actual message content
		// String messageId =
		// "APA91bEbz4Uawj9thvKymeLTtHqUi0lncWYVcAXiJJfg9Ikd210z1FNqoHyFl2vE-fBbdxGnJltNmjP3DzG1aikHGm_LucwH79fbuwGrBiPsYPGdi7C6Jk689TF8WEdrPstssNaKO3Bq";
		// //gcm registration id of android device

		Sender sender = new Sender(API_KEY);
		Message.Builder builder = new Message.Builder();

		//builder.collapseKey(collpaseKey);
		builder.timeToLive(30);
		//builder.delayWhileIdle(true);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("PassengerId", passengerId);
		dataMap.put("DriverId", driverId);
		dataMap.put("RequestNo", RequestNo);
		dataMap.put("notificationType", Notify);
		dataMap.put("RideRequestId", rideReqResId);
		builder.setData(dataMap);
		// builder.addData("message", "PassengerId :" + passengerId
		// + ", DriverId: " + driverId + ",NotificationType :" + Notify
		// +", RideRequestId: "+rideReqResId);

		Message message = builder.build();

		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(keyToken);

		//MulticastResult result = sender.send(message, androidTargets, 10);
		Result result = sender.sendNoRetry(message, keyToken);
		System.out.println("result = " + result);
		String errorCode = result.getErrorCodeName();
		logger.info("**************Notification result in pushAndriodPassengerNotification with Key Token "+keyToken + " with notification "+ Notify + "with notification "+result +" with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap)+ "with Error Code " + errorCode +"*************");
		
		/*if (result.getResults() != null) {
			int canonicalRegId = result.getCanonicalIds();
			System.out.println("canonicalRegId = " + canonicalRegId);

			if (canonicalRegId != 0) {
			}
		} else {
			int error = result.getFailure();
			System.out.println("Broadcast failure: " + error);
		}*/
		
		System.out.println("Driver Notification Result  : " + result);
		if (result.getCanonicalRegistrationId() != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			asyncServiceImpl.replaceTokensAndroid(keyToken, canonicalRegId);
			logger.info("**************Notification result in pushAndriodPassengerNotification canonicalRegId excep "+canonicalRegId+" ******");
			System.out.println("  = " + canonicalRegId);
		} 
		if(errorCode !=null || StringUtil.isNotEmpty(errorCode)){
			System.out.println("Broadcast failure: " + result.getErrorCodeName());
			logger.info("**************Notification result in pushAndriodPassengerNotification errorCode excep "+errorCode+" ******");
			throw new GenericException(errorCode);
		}

	}

}
