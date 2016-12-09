package com.tgi.safeher.API.thirdParty.IOS;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.internal.ReconnectPolicies.Always;
import com.notnoop.exceptions.NetworkIOException;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.utils.PushFCMIOS;

@Component
@Scope("prototype")
@EnableAsync
public class PushNotificationsIOS {
	
	private static final Logger logger = Logger.getLogger(PushNotificationsIOS.class);
//	@Autowired
//	private PushFCMIOS pushFCMIOS;

	@Async
	public void pushIOS(final String token, final String ApiName,
			String type) throws GenericException {
		logger.info("Entering in pushIOS  with notification type "+ type +" and token "+token);
		ApnsServiceBuilder serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		ClassLoader classLoader = PushNotificationsIOS.class.getClassLoader();
		if (!token.equals(null)
				&& !token.equalsIgnoreCase("simulator/No permission")
				&& !token.isEmpty()) {
			System.out.println("The target token is " + token);
			if (ApiName.equals("Passenger")) {
				if (type.equals("prod") || type.equals("0")) {
					System.out.println("using Passenger prod API");
					String certPath = classLoader.getResource(
							"certificates/passengerProd.p12").getPath();
					serviceBuilder.withCert(certPath, "123")
							.withProductionDestination();
				} else if (type.equals("dev") || type.equals("1")) {
					System.out.println("using Passenger dev API");
					String certPath = classLoader.getResource(
							"certificates/passegnerDevCertificate.p12")
							.getPath();
					serviceBuilder.withCert(certPath, "SafeHer(*&")
							.withSandboxDestination();
				} else {
					System.out.println("unknown API type " + type);
					return;
				}
			} else if (ApiName.equals("Driver")) {
				if (type.equals("prod") || type.equals("0")) {
					System.out.println("using Driver prod API");
					String certPath = classLoader.getResource(
							"certificates/driverProd.p12").getPath();
					serviceBuilder.withCert(certPath, "SafeHer!@#")
							.withProductionDestination();
				} else if (type.equals("dev") || type.equals("1")) {
					System.out.println("using Driver dev API");
					String certPath = classLoader.getResource(
							"certificates/driverDevCertifcate.p12").getPath();
					serviceBuilder.withCert(certPath, "SafeHer!@#")
							.withSandboxDestination();
				} else {
					System.out.println("unknown API type " + type);
					return;
				}

				ApnsService service = serviceBuilder.build();

				String payload = APNS.newPayload()
						.alertBody("Passenger has Search in this Area")
						.alertTitle("Attention").sound("default")
						.customField("RequestType3", "DummyValue").build();
				System.out.println("payload: " + payload);
				service.push(token, payload);
				System.out.println("The message has been hopefully sent...");
				service.stop();
				logger.info("Exiting from pushIOS  with notification type "+ type +" and token "+token);
				
			}

		}

	}

	private void deleteInactiveDevices(ApnsService service) {
		// get the list of the devices that no longer have your app installed
		// from apple
		// ignore the ="" after Date here, it's a bug...
		Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (String deviceToken : inactiveDevices.keySet()) {
		}

	}

	@Async
	public void pushIOSForRide(String token, String type,
			String passengerId, String driverApId, String notificationType, String fcmToken)
			throws GenericException {
		logger.info("******Entering in pushIOSForRide  with notification type "+ notificationType + " token "+ token+ " and fcmtoken "+fcmToken + "******");
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ServerSocket serverSocket = null;
		ApnsServiceBuilder serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		ClassLoader classLoader = PushNotificationsIOS.class.getClassLoader();
		System.out.println("The target token is " + token);
		if (!token.equals(null)
				&& !token.equalsIgnoreCase("simulator/No permission")
				&& !token.isEmpty()) {
			if (type.equals("prod") || type.equals("0")) {
				System.out.println("using Driver prod API");
				String certPath = classLoader.getResource(
						"certificates/DriverDitributionCerti.p12").getPath();
				serviceBuilder.withCert(certPath, "SafeHer!@#")
						.withProductionDestination();
			} else if (type.equals("dev") || type.equals("1")) {
				System.out.println("using Driver dev API");
				String certPath = classLoader.getResource(
						"certificates/DriverDevelopementCert.p12").getPath();
				serviceBuilder.withCert(certPath, "SafeHer!@#")
						.withSandboxDestination();
			} else {
				System.out.println("unknown API type " + type);
				return;
			}

			ApnsService service = serviceBuilder.build();

			String payload = APNS.newPayload()
					.alertBody("Passenger has Search in this Area").instantDeliveryOrSilentNotification()
					.sound("default")
					.customField("PassengerAppId", passengerId)
					.customField("DriverAppId", driverApId)
					.customField("notificationType", notificationType).build();
			System.out.println("payload: " + payload);
			try {
				service.push(token, payload);
				logger.info("******Notification result in pushIOSForRide  with notification type "+ notificationType + " token "+ token+" PassengerAppId "+ passengerId+ " DriverAppId "+driverApId+"******");
				System.out.println("The message has been hopefully sent...");
				service.stop();
			} catch (NetworkIOException e) {
				logger.info("******Entering in NetworkIOException  with notification type "+ notificationType + "and fcmtoken "+fcmToken + "******");
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, "", "", "", "", "1", notificationType, "Request has been canceled by passenger", token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			} catch (Exception e) {
				logger.info("******Entering in Exception  with notification type "+ notificationType + " token "+ token+ " and fcmtoken "+fcmToken + "******");
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, "", "", "", "", "1", notificationType, "Request has been canceled by passenger", token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}
		}
	}

	@Async
	public void pushIOSForPassengerRide(String token, String type,
			String passengerId, String driverApId, String rideReqResId,
			String notificationType, String RequestNo, String fcmToken) throws GenericException {
		//ServerSocket serverSocket = null;
		logger.info("******Entering in pushIOSForPassengerRide  with notification type "+ notificationType + " token "+ token+ " and fcmtoken "+fcmToken + "******");
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ApnsServiceBuilder serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		ClassLoader classLoader = PushNotificationsIOS.class.getClassLoader();
		System.out.println("The target token is " + token);
		if (!token.equals(null)
				&& !token.equalsIgnoreCase("simulator/No permission")
				&& !token.isEmpty()) {
			if (type.equals("prod") || type.equals("0")) {
				System.out.println("using Passenger prod API");
				String certPath = classLoader.getResource(
						"certificates/PassengerProductionCerts.p12").getPath();
				serviceBuilder.withCert(certPath, "123")
						.withProductionDestination();
			} else if (type.equals("dev") || type.equals("1")) {
				System.out.println("using Passenger dev API");
				String certPath = classLoader.getResource(
						"certificates/PassengerDevelopmentCerts.p12").getPath();
				serviceBuilder.withCert(certPath, "123")
						.withSandboxDestination();
			} else {
				System.out.println("unknown API type " + type);
				return;
			}
			ApnsService service = serviceBuilder.build();

			String payload = APNS.newPayload()
					.alertBody("Driver has accepted your request").instantDeliveryOrSilentNotification()
					.sound("default")
					.customField("PassengerAppId", passengerId)
					.customField("DriverAppId", driverApId)
					.customField("RequestNo", RequestNo)
					.customField("RideRequestId", rideReqResId)
					.customField("notificationType", notificationType).build();
			System.out.println("payload: " + payload);
			try {
				service.push(token, payload);
				service.stop();
				logger.info("******Notification result in pushIOSForRide  with notification type "+ notificationType + " token "+ token+" PassengerAppId "+ passengerId+ " DriverAppId "+driverApId+ " Request # "+RequestNo+ " RideRequestID # "+ rideReqResId+"******");
				
				
			} catch (NetworkIOException e) {
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, passengerId, driverApId, rideReqResId, RequestNo, "1", notificationType, 
						"Driver has accepted your request", token);
				logger.info("******Notification result in pushIOSForPassengerRide  with notification type "+ notificationType + " fcmtoken "+ fcmToken+ " and payload "+payload+ " ******");
				
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}
			System.out.println("The message has been hopefully sent...");
			logger.info("******Exiting from pushIOSForPassengerRide******");
			
		}
	}

	public void pushIOSByGCM(String keyToken, String passengerId,
			String driverId, String rideReqResId, String Notify)
			throws IOException {
		final String API_KEY = "AIzaSyBnwB44Q8vng1BQgRcCggivSXA-ZmI2UcE"; // sender
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
		String messageStr = "Testing Content "; // actual message content
		// String messageId =
		// "APA91bEbz4Uawj9thvKymeLTtHqUi0lncWYVcAXiJJfg9Ikd210z1FNqoHyFl2vE-fBbdxGnJltNmjP3DzG1aikHGm_LucwH79fbuwGrBiPsYPGdi7C6Jk689TF8WEdrPstssNaKO3Bq";
		// //gcm registration id of android device

		Sender sender = new Sender(API_KEY);
		Message.Builder builder = new Message.Builder();
		//
		// JSONObject obj = new JSONObject();
		// JSONObject obj2 = new JSONObject();
		//
		//
		// String
		// js="{\"aps\":{\"sound\":\"default\",\"alert\":\"Hello World\"}}";
		// JSONObject ohbj= new JSONObject(js);
		// obj2.put("alert", "Hello World");
		// obj2.put("sound", "default");
		// obj.put("aps", obj2);

		// String payload = APNS.newPayload()
		// .alertBody("Driver has Search in this Area").sound("default")
		// .customField("OSaKA", "JoGAR")
		// .build();

		// obj2.toString();

		// System.out.println(payload);
		Map<String, String> dataMap = new HashMap<String, String>();
		// dataMap.put("notification",
		// "{'alert':'Driver Alert','sound':'default'}");
		dataMap.put("notification", "{'body':'Hello GCM_IOS', 'NICK': 'ABC'}");
//		dataMap.put("PassengerAppId", "123");
//		dataMap.put("notificationType", "ABC_JASD");
//		dataMap.put("DriverAppId", "234");
		dataMap.put("content_available", "1");
		dataMap.put("priority", "high");
//		builder.collapseKey(collpaseKey);
		//builder.timeToLive(30);
		//builder.delayWhileIdle(true);

		builder.setData(dataMap);
		// builder.addData("{'alert':'Driver has Search in this Area','sound':'default'},'OSaKA':'JoGAR'}");

		Message message = builder.build();
		
		System.out.println(message);

		List<String> androidTargets = new ArrayList<String>();
		// if multiple messages needs to be deliver then add more message ids to
		// this list
		androidTargets.add(keyToken);

		MulticastResult result = sender.send(message, androidTargets, 1);

		System.out.println("result = " + result);
		System.out.println("Success: "+result.getSuccess());

		if (result.getResults() != null) {
			int canonicalRegId = result.getCanonicalIds();
			System.out.println("canonicalRegId = " + canonicalRegId);

			if (canonicalRegId != 0) {
			}
		} else {
			int error = result.getFailure();
			System.out.println("Broadcast failure: " + error);
		}

	}

	public static void main(String[] args) {
		try {
			//pushIOSByGCM(
				//	"e5xM81w61Tg:APA91bFAlbIrFqiJmkzNXV26t20_nWy935EsMRQGZSMCrSiCeVooJHXxhfweyg30zDPOku0HzYLWlPZSL42dTugPF45HVcgX2x1a4H885JC2WqyZO_OVcvvHDfzzOlnrOrgJ3yr0jNVi",
					//"123123", "12", "123", "1233234");
			System.out.println("hello");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
