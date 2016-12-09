package com.tgi.safeher.API.thirdParty.IOS;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.cfg.ExtendsQueueEntry;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.internal.ReconnectPolicies.Always;
import com.notnoop.exceptions.NetworkIOException;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.PushFCMIOS;
import com.tgi.safeher.utils.StringUtil;
@Component
@Scope("prototype")
@EnableAsync
public class PushIOS {
	
//	@Autowired
//	private PushFCMIOS pushFCMIOS;
	
	private static final Logger logger = Logger.getLogger(PushIOS.class);
	
	@Async
	public void pushIOSDriver(String token, String type, String parm1,
			String parma2, String parm3, String parm4, String notificationType, String message, String fcmToken)
			throws GenericException {
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		String value = Common.getValueFromSpecificPropertieFile(
				"/properties/pushNotificationPriority.properties", "IOS_PRIORITY");
		System.out.println(value);
		
		if(StringUtil.isNotEmpty(value) &&
				value.equalsIgnoreCase("FCM")){
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
		}else if(StringUtil.isNotEmpty(value) &&
				value.equalsIgnoreCase("APNS")){
			pushIOSDriverPriority(
					token, type, parm1, parma2, parm3, parm4, notificationType, message, fcmToken);
		}
	}

	public void pushIOSDriverPriority(String token, String type, String parm1,
			String parma2, String parm3, String parm4, String notificationType, String message, String fcmToken)
			throws GenericException {
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ApnsServiceBuilder serviceBuilder = null;
		ClassLoader classLoader = null;
		
		logger.info("PushIOSDriver Start with notification type "+ notificationType +" and token"+token);
		try{
			logger.info("Service Starting");
			serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		    classLoader = PushNotificationsIOS.class.getClassLoader();
		    logger.info("Service Ending D");
		   
		    
		}catch(NetworkIOException ex){
			logger.info("##### PushIOSDriver Exception of Network Error " + ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//			throw new GenericException(
//					"Server is Not Responding Please Try Later ");
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.info("##### PushIOSDriver Exception FCM"+e.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
		}
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
			// Only Driver Status Added here
			ApnsService service = serviceBuilder.build();
			HashMap<String, String> dataMap = new HashMap<String, String>();
			switch (notificationType) {
			case "PreRideRequest":
				dataMap.put("", "");
				dataMap.put("", "");
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideRequest.getValue());
				break;
			case "PreRideCancelRequest":
				dataMap.put("Reason", parm1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideCancelRequest
								.getValue());
				break;
			case "PreRideLateOrCancelRequest":
				dataMap.put("Reason", parm1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideLateOrCancelRequest.getValue());
				break;
			case "ColorVerification":
				dataMap.put("notificationType",
						PushNotificationStatus.ColorVerification.getValue());
				break;
			case "ColorVerificationFailed":
				dataMap.put("notificationType",
						PushNotificationStatus.ColorVerificationFailed
								.getValue());
				break;
			case "SuccessfullVerification":
				dataMap.put("VerificationFlag", "true");
				dataMap.put("notificationType",
						PushNotificationStatus.SuccessfullVerification
								.getValue());
				break;
				
			case "RideCancelByEmergency":
				dataMap.put("VerificationFlag", "false");
				dataMap.put("notificationType",
						PushNotificationStatus.RideCancelByEmergency
								.getValue());
				break;
				
			case "AcceptRideFromPassenger":
				dataMap.put("PassengerAppId", parm1);
				dataMap.put("DriverAppId", parma2);
				dataMap.put("PreRideId", parm3);
				dataMap.put("rideReqResId", parm4);
				dataMap.put("notificationType",
						PushNotificationStatus.AcceptRideFromPassenger.getValue());
				break;
			case "StartDestinationReached":
				dataMap.put("notificationType",
						PushNotificationStatus.StartDestinationReached.getValue());
				break;
			case "PreRideBillingRequest":
				dataMap.put("Reason", parm1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideBillingRequest.getValue());
				break;
			case "searchRideCriteria":
				dataMap.put("PassengerAppId", parm1);
//				dataMap.put("DriverAppId", parma2);
				dataMap.put("charityName", parma2);
				dataMap.put("requestNo", parm3);
//				dataMap.put("RideRequestId", parm4);
				dataMap.put("charityId", parm4);
				dataMap.put("sendingTime", DateUtil.getCurrentTimestamp()+"");
				dataMap.put("notificationType",
						PushNotificationStatus.searchRideCriteria.getValue());
				break;
			case "EndRideConfirmPassenger":
				dataMap.put("InvoiceNo", parm1);
				dataMap.put("RideNo", parma2);
				dataMap.put("notificationType",
						PushNotificationStatus.EndRideConfirmPassenger
								.getValue());
				break;				
			case "RideTransactionSuccess":
				dataMap.put("notificationType",
						PushNotificationStatus.RideTransactionSuccess.getValue());
			case "PassengerMidEndRide":
				dataMap.put("notificationType",
						PushNotificationStatus.PassengerMidEndRide.getValue());
				break;
			case "PassengerPaymentSuccessful":
				dataMap.put("InvoiceNo", parm1);
				dataMap.put("RideNo", parma2);
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
			case "SignOutUser":
			    dataMap.put("notificationType",
			    	      PushNotificationStatus.SignOutUser.getValue());
			   break;
			case "PreRideStart":
//				info.put("PassengerAppId", param1);
//				info.put("DriverAppId", param2);
				dataMap.put("rideReqResId", parm1);
				dataMap.put("PreRideId", parma2);
				//info.put("EstimatedTime", param4);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideStart.getValue());
				break;
			case "Payment":
				dataMap.put("paymentDone", parm1);
				dataMap.put("notificationType",
						PushNotificationStatus.Payment.getValue());
				break;
			default:			
				dataMap.put("notificationType", "DefaultNotification");
			}

			String payload = APNS.newPayload().alertBody(message).instantDeliveryOrSilentNotification()
					.sound("default").instantDeliveryOrSilentNotification().customFields(dataMap).build();
			System.out.println("payload: " + payload);
			try {
				service.push(token, payload);
				System.out.println("The message has been hopefully sent...");
				
				service.stop();
			} catch (NetworkIOException e) {
				logger.info("PushIOSDriver Ended with NetworkIOException "+e.getMessage());
				e.printStackTrace();
				System.out.println(e.getMessage());
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			} catch (Exception e) {
				logger.info("PushIOSDriver Ended with Exception "+e.getMessage());
				System.out.println(e.getMessage());
				e.printStackTrace();
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}finally{
				logger.info("PushIOSDriver End with notification type "+ notificationType +" and token"+token + " with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap));
			}
		}
	}

	@Async
	public void pushIOSPassenger(String token, String type,
			String param1, String param2, String parm3,String parm4,
			String notificationType, String message, String fcmToken) throws GenericException {
		logger.info("pushIOSPassenger Start with notification type "+ notificationType +" and token"+token +"and message "+message);
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		
		String value = Common.getValueFromSpecificPropertieFile(
				"/properties/pushNotificationPriority.properties", "IOS_PRIORITY");
		System.out.println(value);
		
		if(StringUtil.isNotEmpty(value) &&
				value.equalsIgnoreCase("FCM")){
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
		}else if(StringUtil.isNotEmpty(value) &&
				value.equalsIgnoreCase("APNS")){
			pushIOSPassengerPriority(
					token, type, param1, param2, parm3, parm4, notificationType, message, fcmToken);
		}
	}

	public void pushIOSPassengerPriority(String token, String type,
			String param1, String param2, String parm3,String parm4,
			String notificationType, String message, String fcmToken) throws GenericException {
		logger.info("pushIOSPassenger Start with notification type "+ notificationType +" and token"+token +"and message "+message);
		ApnsServiceBuilder serviceBuilder = null;
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ClassLoader classLoader = null;
		
		try{
			logger.info("Service Starting");
			serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		    classLoader = PushNotificationsIOS.class.getClassLoader();
		    logger.info("Service Ending");
		   //Thread.sleep(1000);
		    
		}catch(NetworkIOException ex){
			logger.info("PushIOSPassenger Exception of Network Error P"+ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
//			throw new GenericException(
//					"Server is Not Responding Please Try Later ");
			
			
		}catch (Exception ex) {
			// TODO: handle exception
			logger.info("PushIOSPassenger Exception "+ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
		}
		
		
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
			HashMap<String, String> dataMap = new HashMap<String, String>();
			switch (notificationType) {
			case "PreRideRequest":
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideRequest.getValue());
				break;
			case "AcceptRideFromDriver":
				dataMap.put("PassengerAppId", param1);
				dataMap.put("DriverAppId", param2);
				dataMap.put("RideRequestId", parm3);
				dataMap.put("RequestNo", parm4);
				dataMap.put("notificationType",
						PushNotificationStatus.AcceptRideFromDriver
								.getValue());
				break;
			case "PreRideStart":
				dataMap.put("PassengerAppId", param1);
				dataMap.put("DriverAppId", param2);
				dataMap.put("PreRideId", parm3);
				dataMap.put("EstimatedTime", parm4);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideStart.getValue());
				break;
			case "PreRideLateOrCancelRequest":
				dataMap.put("Reason", param1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideLateOrCancelRequest.getValue());
				break;
			case "PreRideCancelRequest":
				dataMap.put("Reason", param1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideCancelRequest
								.getValue());
				break;
			case "InvoiceRequest":  
				dataMap.put("InvoiceNo", param1);
				dataMap.put("RideNo", param2);
				String[] split =parm3.split("_");
				if(split!=null && split.length==3){
					dataMap.put("paymentDone", split[0]);
					dataMap.put("isGifted", split[1]);
					dataMap.put("PassengerType", split[2]);
				}else{
					dataMap.put("paymentDone", parm3);
					dataMap.put("isGifted", "0");
					dataMap.put("PassengerType", "0");
				}
				dataMap.put("discount", parm4);
				dataMap.put("notificationType",
						PushNotificationStatus.InvoiceRequest
								.getValue());
				break;
			case "startRide":
				dataMap.put("RideNo", param2);
//				dataMap.put("currentAddress", parm3);
				dataMap.put("startAddress", param1);
				dataMap.put("startLat", parm3);
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
			case "ColorVerificationFailed":
				dataMap.put("notificationType",
						PushNotificationStatus.ColorVerificationFailed
								.getValue());
				break;
			case "SuccessfullVerification":
				dataMap.put("VerificationFlag", "true");
				dataMap.put("notificationType",
						PushNotificationStatus.SuccessfullVerification.getValue());
				break;
			
			case "RideCancelByEmergency":
				dataMap.put("VerificationFlag", "false");
				dataMap.put("notificationType",
					PushNotificationStatus.RideCancelByEmergency.getValue());
			break;
			
			case "StartDestinationReached":
				dataMap.put("notificationType",
						PushNotificationStatus.StartDestinationReached.getValue());
				break;
			case "driverJustArrived":
				dataMap.put("notificationType",
						PushNotificationStatus.driverJustArrived.getValue());
				break;
			case "NoActiveDriverInSuburb":
				dataMap.put("PassengerId", param1);
				dataMap.put("notificationType",
						PushNotificationStatus.NoActiveDriverInSuburb.getValue());
				break;
			case "PreRideBillingRequest":
				dataMap.put("Reason", param1);
				dataMap.put("notificationType",
						PushNotificationStatus.PreRideBillingRequest.getValue());
				break;
			case "searchRideCriteria":
				dataMap.put("PassengerId", param1);
				dataMap.put("DriverId", param2);
				dataMap.put("RequestNo", parm3);
				dataMap.put("RideRequestId", parm4);
				dataMap.put("notificationType",
						PushNotificationStatus.searchRideCriteria.getValue());
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
				dataMap.put("paymentDone", param1);
				dataMap.put("notificationType",
						PushNotificationStatus.Payment.getValue());
				break;
			default:
				dataMap.put("notificationType", "DefaultNotification");
			}
			String payload = APNS.newPayload().alertBody(message).instantDeliveryOrSilentNotification()
					.sound("default").instantDeliveryOrSilentNotification().customFields(dataMap).build();
			System.out.println("payload: " + payload);
			try {
				/*service.testConnection();*/
				service.push(token, payload);
				System.out.println("The message has been hopefully sent...");
				logger.info("The message has been hopefully sent...PushIOSPassenger");
				service.stop();
			} catch (NetworkIOException e) {
				logger.info("PushIOSPassenger NetworkIOException "+e.getMessage());
				e.printStackTrace();
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}finally{
				logger.info("PushIOSPassenger End with notification type "+ notificationType +" and token"+token + " with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap));
			}

		}
	}

	@Async
	public void pushIOSDriverPriorityForSignOutAnotherDevice(String token, String type, String parm1,
			String parma2, String parm3, String parm4, String notificationType, String message, String fcmToken)
			throws GenericException {
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ApnsServiceBuilder serviceBuilder = null;
		ClassLoader classLoader = null;
		
		logger.info("PushIOSDriver Start with notification type "+ notificationType +" and token"+token);
		try{
			logger.info("Service Starting");
			serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		    classLoader = PushNotificationsIOS.class.getClassLoader();
		    logger.info("Service Ending D");
		   
		    
		}catch(NetworkIOException ex){
			logger.info("##### PushIOSDriver Exception of Network Error " + ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//			throw new GenericException(
//					"Server is Not Responding Please Try Later ");
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.info("##### PushIOSDriver Exception FCM"+e.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
		}
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
			// Only Driver Status Added here
			ApnsService service = serviceBuilder.build();
			HashMap<String, String> dataMap = new HashMap<String, String>();
			switch (notificationType) {
			case "SignOutUser":
			    dataMap.put("notificationType",
			    	      PushNotificationStatus.SignOutUser.getValue());
			   break;
			default:			
				dataMap.put("notificationType", "DefaultNotification");
			}

			String payload = APNS.newPayload().alertBody(message).instantDeliveryOrSilentNotification()
					.sound("default").instantDeliveryOrSilentNotification().customFields(dataMap).build();
			System.out.println("payload: " + payload);
			try {
				service.push(token, payload);
				System.out.println("The message has been hopefully sent...");
				
				service.stop();
			} catch (NetworkIOException e) {
				logger.info("PushIOSDriver Ended with NetworkIOException "+e.getMessage());
				e.printStackTrace();
				System.out.println(e.getMessage());
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			} catch (Exception e) {
				logger.info("PushIOSDriver Ended with Exception "+e.getMessage());
				System.out.println(e.getMessage());
				e.printStackTrace();
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, parm1, parma2, parm3, parm4, "1", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}finally{
				logger.info("PushIOSDriver End with notification type "+ notificationType +" and token"+token + " with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap));
			}
		}
	}

	@Async
	public void pushIOSPassengerPriorityForSingOutAnotherDevice(String token, String type,
			String param1, String param2, String parm3,String parm4,
			String notificationType, String message, String fcmToken) throws GenericException {
		logger.info("pushIOSPassenger Start with notification type "+ notificationType +" and token"+token +"and message "+message);
		ApnsServiceBuilder serviceBuilder = null;
		PushFCMIOS pushFCMIOS = new PushFCMIOS();
		ClassLoader classLoader = null;
		
		try{
			logger.info("Service Starting");
			serviceBuilder = APNS.newService().withReconnectPolicy(new Always()).withConnectTimeout(3000);
		    classLoader = PushNotificationsIOS.class.getClassLoader();
		    logger.info("Service Ending");
		   //Thread.sleep(1000);
		    
		}catch(NetworkIOException ex){
			logger.info("PushIOSPassenger Exception of Network Error P"+ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
//			throw new GenericException(
//					"Server is Not Responding Please Try Later ");
			
			
		}catch (Exception ex) {
			// TODO: handle exception
			logger.info("PushIOSPassenger Exception "+ex.getMessage());
			pushFCMIOS.pushFCMIOSNotification(
					fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
		}
		
		
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
			HashMap<String, String> dataMap = new HashMap<String, String>();
			switch (notificationType) {
			case "SignOutUser":
			    dataMap.put("notificationType",
			    	      PushNotificationStatus.SignOutUser.getValue());
			   break;
			default:
				dataMap.put("notificationType", "DefaultNotification");
			}
			String payload = APNS.newPayload().alertBody(message).instantDeliveryOrSilentNotification()
					.sound("default").instantDeliveryOrSilentNotification().customFields(dataMap).build();
			System.out.println("payload: " + payload);
			try {
				/*service.testConnection();*/
				service.push(token, payload);
				System.out.println("The message has been hopefully sent...");
				logger.info("The message has been hopefully sent...PushIOSPassenger");
				service.stop();
			} catch (NetworkIOException e) {
				logger.info("PushIOSPassenger NetworkIOException "+e.getMessage());
				e.printStackTrace();
				pushFCMIOS.pushFCMIOSNotification(
						fcmToken, type, param1, param2, parm3, parm4, "0", notificationType, message, token);
//				throw new GenericException(
//						"Server is Not Responding Please Try Later ");
			}finally{
				logger.info("PushIOSPassenger End with notification type "+ notificationType +" and token"+token + " with DataMap "+dataMap.entrySet().toString()+" "  + Arrays.asList(dataMap));
			}

		}
	}
	
	public static void main(String args[]){
		//2082c451d1fe41494581871412ff6243beef9a408cae33743965c04e9c6c58d5
		PushIOS ios = new PushIOS();
		try {
			for(int i = 1; i <=1; i++){
			
					ios.pushIOSDriver("f57e0e0488df1c1a9f59df4b3ddbaa41792a5f0893295c404ad72d6ea1e9ce1c", "1", 
							"sdafjsdklfj sdjfkjassdf asdf asdfdklf", "sdafjsdklfj sdjfas dfasd fasdfkjasdklf", "sdafjsdklsdf asdfasdfasdfasdfsd ffj sdjfkjasdklf", 
							"sdafjsdklfj sdasdf asdfa sdfasdfasdfasdfasdfjfkjasdklf",
							"AcceptRideFromDriver", i+"helsd fasdf sdfasdflo asdas asfsdf asdf sdfasd fsda fasdf asdfasd fasdf sdfsdf sdmr dj ios", "2sadf asdfasdf34");
				
			
			}
			} catch (GenericException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
