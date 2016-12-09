package com.tgi.safeher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.CharitiesBean;
import com.tgi.safeher.beans.PushFcmResult;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.base.SafeherDAO;
import com.tgi.safeher.service.impl.AsyncServiceImpl;

@Component
@Scope("prototype")
public class PushFCMIOS {
	private static final Logger logger = Logger.getLogger(PushFCMIOS.class);
	public final static String AUTH_KEY_FCM_PASSENGER = "AIzaSyCVQCZHILn7E5oSvxTIUlPzqsWY1UfGxjE";
	public final static String AUTH_KEY_FCM_DRIVER = "AIzaSyDjABcy7fyQY251W3GM-TeUn2djA9rTY7o";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@SuppressWarnings("unchecked")
	public void pushFCMIOSNotification(String token, String type,
			String param1, String param2, String param3, String param4, String isDriver,
			String notificationType, String message, String apnsToken) throws GenericException {
		logger.info("**************Entering in pushFCMIOSNotification with Key Token "+token + " with notification "+ notificationType);
		
		PushIOS pushIOS = new PushIOS();
		String authKey = AUTH_KEY_FCM_PASSENGER; // You FCM AUTH key
		if(isDriver != null && isDriver.equalsIgnoreCase("1")){
			authKey = AUTH_KEY_FCM_DRIVER;
		}

		try {
			if (StringUtil.isNotEmpty(token)) {

				URL url = new URL(API_URL_FCM);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();

				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);

				conn.setRequestProperty("Authorization", "key=" + authKey);
				conn.setRequestProperty("Content-Type", "application/json");

				JSONObject json = new JSONObject();
//				json.put("collapse_key", "score_update");
//				json.put("time_to_live", 108);
				json.put("delay_while_idle", true);
				json.put("content_available", true);
				JSONObject info = new JSONObject();
				info.put("body", message); 
				info.put("sound", "default"); 

				switch (notificationType) {
				case "PreRideRequest":
					info.put("notificationType",
							PushNotificationStatus.PreRideRequest.getValue());
					break;
				case "PreRideCancelRequest":
					info.put("Reason", param1);
					info.put("notificationType",
							PushNotificationStatus.PreRideCancelRequest
									.getValue());
					break;
				case "PreRideLateOrCancelRequest":
					info.put("Reason", param1);
					info.put("notificationType",
							PushNotificationStatus.PreRideLateOrCancelRequest
									.getValue());
					break;
				case "ColorVerification":
					info.put("notificationType",
							PushNotificationStatus.ColorVerification.getValue());
					break;
				case "ColorVerificationFailed":
					info.put("notificationType",
							PushNotificationStatus.ColorVerificationFailed
									.getValue());
					break;
				case "SuccessfullVerification":
					info.put("VerificationFlag", "true");
					info.put("notificationType",
							PushNotificationStatus.SuccessfullVerification
									.getValue());
					break;
				case "AcceptRideFromPassenger":
					info.put("PassengerAppId", param1);
					info.put("DriverAppId", param2);
					info.put("PreRideId", param3);
					info.put("rideReqResId", param4);
					info.put("notificationType",
							PushNotificationStatus.AcceptRideFromPassenger
									.getValue());
					break;
				case "StartDestinationReached":
					info.put("notificationType",
							PushNotificationStatus.StartDestinationReached
									.getValue());
					break;
				case "startRide":
					info.put("RideNo", param2);
//					info.put("currentAddress", param3);
					info.put("startAddress", param1);
					info.put("startLat", param3);
					info.put("startLong", param4);
					info.put("notificationType",
							PushNotificationStatus.startRide.getValue());
					break;
				case "PreRideBillingRequest":
					info.put("Reason", param1);
					info.put("notificationType",
							PushNotificationStatus.PreRideBillingRequest
									.getValue());
					break;
				case "searchRideCriteria":
					info.put("PassengerAppId", param1);
//					info.put("DriverAppId", parma2);
					info.put("charityName", param2);
					info.put("requestNo", param3);
//					info.put("RideRequestId", parm4);
					info.put("charityId", param4);
					info.put("sendingTime", DateUtil.getCurrentTimestamp()+"");
					info.put("notificationType",
							PushNotificationStatus.searchRideCriteria
									.getValue());
					break;
				case "EndRideConfirmPassenger":
					info.put("InvoiceNo", param1);
					info.put("RideNo", param2);
					info.put("notificationType",
							PushNotificationStatus.EndRideConfirmPassenger
									.getValue());
					break;
				case "RideTransactionSuccess":
					info.put("notificationType",
							PushNotificationStatus.RideTransactionSuccess
									.getValue());
				case "PassengerMidEndRide":
					info.put("notificationType",
							PushNotificationStatus.PassengerMidEndRide
									.getValue());
					break;
				case "PassengerPaymentSuccessful":
					info.put("InvoiceNo", param1);
					info.put("RideNo", param2);
					info.put("notificationType",
							PushNotificationStatus.PassengerPaymentSuccessful
									.getValue());
					break;
					case "midRideRequest":
						info.put("RequestCode", param1);
						info.put("notificationType",
								PushNotificationStatus.midRideRequest
										.getValue());
						break; 
					case "SignOutUser":
						info.put("notificationType",
					    	      PushNotificationStatus.SignOutUser.getValue());
				   break;
				case "PreRideStart":
//					info.put("PassengerAppId", param1);
//					info.put("DriverAppId", param2);
					info.put("rideReqResId", param1);
					info.put("PreRideId", param2);
					//info.put("EstimatedTime", param4);
					info.put("notificationType",
							PushNotificationStatus.PreRideStart.getValue());
					break;
				case "InvoiceRequest":
					info.put("InvoiceNo", param1);
					info.put("RideNo", param2);
					String[] split =param3.split("_");
					if(split!=null && split.length==3){
						info.put("paymentDone", split[0]);
						info.put("isGifted", split[1]);
						info.put("PassengerType", split[2]);
					}else{
						info.put("paymentDone", param3);
						info.put("isGifted", "0");
						info.put("PassengerType", "0");
					}
					info.put("discount", param4);
					info.put("notificationType",
							PushNotificationStatus.InvoiceRequest
									.getValue());
					break;
				case "PreRideLateOrCancelRequestDriver":
					info.put("notificationType",
							PushNotificationStatus.PreRideLateOrCancelRequestDriver.getValue());
					break;	
				case "driverJustArrived":
					info.put("notificationType",
							PushNotificationStatus.driverJustArrived.getValue());
					break;
				case "NoActiveDriverInSuburb":
					info.put("PassengerId", param1);
					info.put("notificationType",
							PushNotificationStatus.NoActiveDriverInSuburb.getValue());
					break;
				case "Cancel_Ride_From_Passenger":
					info.put("notificationType",
							PushNotificationStatus.CancelRideFromPassenger.getValue());
					break;
				case "AcceptRideFromDriver":
					info.put("PassengerAppId", param1);
					info.put("DriverAppId", param2);
					info.put("RideRequestId", param3);
					info.put("RequestNo", param4);
					info.put("notificationType",
							PushNotificationStatus.AcceptRideFromDriver.getValue());
					break;
				case "CancelRideFromDriver":
					info.put("RequestNo", param4);
					info.put("notificationType",
							PushNotificationStatus.CancelRideFromDriver.getValue());
					break;	
				case "PaymentCancel":
					info.put("notificationType",
							PushNotificationStatus.PaymentCancel.getValue());
					break;
				case "RideCancelByEmergency":
					info.put("VerificationFlag", "false");
					info.put("notificationType",
						PushNotificationStatus.RideCancelByEmergency.getValue());
				break;
				case "Payment":
					info.put("paymentDone", param1);
					info.put("notificationType",
							PushNotificationStatus.Payment.getValue());
					break;
				default:
					info.put("notificationType", "DefaultNotification");
				}

				info.put("isFCM", "1");
				json.put("notification", info);
				json.put("to", token.trim());
				
				System.out.println(json);

				String result = "";
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(json.toString());
				wr.flush();
				InputStream input = conn.getInputStream();
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(input))) {
					for (String line; (line = reader.readLine()) != null;) {
						//System.out.println(line);
						result += line;
					}
				}
				System.out.println("result: "+result);
		        JSONObject jObject = new JSONObject(result);
		        JSONArray array = jObject.getJSONArray("results");
		        
		        DataBeanFactory dataBeanFactory = new DataBeanFactory();
		        PushFcmResult pushFcmResult = (PushFcmResult) dataBeanFactory
						.populateDataBeanFromJSON(PushFcmResult.class, new SafeHerDecorator(), result);
		        if(StringUtil.isNotEmpty(pushFcmResult.getSuccess()) && 
		        		pushFcmResult.getSuccess().equals("1")){
    				System.out.println("The message has been sent...");
    				logger.info("**************The message has been sent throught FCM... "+
    				"Notification result in pushFCMIOSNotification with Key Token "+token + 
    				" with DataMap "+info.keySet().toString()+" "  + Arrays.asList(info)+ 
    				"with result " + result +"*************");
            	}else{
            		System.out.println("The message not sent");
            	}
        		if(array.length() > 0){
        			 for (int i = 0 ; i < array.length(); i++) {
    			        JSONObject obj = array.getJSONObject(i);
    			        if(StringUtil.isNotEmpty(pushFcmResult.getSuccess()) && 
    			        		pushFcmResult.getSuccess().equals("1")){
        			        //boolean isContailn = obj.has("registration_id");
    			            if(obj.has("registration_id")){
    			            	String registration_id = obj.getString("registration_id");
    			            	System.out.println(registration_id);
    		    				logger.info("************** Updating fcm token "+" *************");
    		    				asyncServiceImpl.replaceFCMToken(token, registration_id);
    		    				break;	
    			            }	
    			        }else{
        			        //boolean isContailn = obj.has("error");
    			            if(obj.has("error")){
    			            	String error = obj.getString("error");
    			            	//System.out.println(error);
    		    				logger.info("************** Error sending FCM notification is: "+ error+ " *************");
    		    				if(isDriver != null && 
    		    						isDriver.equalsIgnoreCase("1")){
    		    					pushIOS.pushIOSDriverPriority(
    		    							apnsToken, type, param1, param2, param3, param4, notificationType, message, token);
    		    				}else if(isDriver != null && 
    		    						isDriver.equalsIgnoreCase("0")){
    		    					pushIOS.pushIOSPassengerPriority(
    		    							apnsToken, type, param1, param2, param3, param4, notificationType, message, token);
    		    				}
    		    				
    		    				//throw new GenericException("Error sending FCM notification is: "+ error);
    			            }
    			        }
    			    }
        		}
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
			logger.info("******Exiting from pushFCMIOSNotification  with  exception "+e.getMessage()+"******");
			throw new GenericException("Can not connect to the server");
		} catch (IOException e1) {
			logger.info("******Exiting from pushFCMIOSNotification  with  exception "+e1.getMessage()+"******");
			e1.printStackTrace();
			throw new GenericException("Connection timed out");
		}
	}
	public static void main(String[] args) throws GenericException {
		PushFCMIOS fcmSender = new PushFCMIOS();
		try {
//			c3tPAt-k7Gg:APA91bEnQV0LZxJhLpqgDo7nBTd3BPiMP7IRhLwHh4mMwe_J15j3oaqPAm74vbuUyCiDxN09yGTWEd3Fj-wSzF8cDnn7Zqx8Bz4S5Io4zNs20kvP2VM9gIxGLTjv0SuTDCA9bopfVttl
//			c3tPAt-k7Gg:APA91bEnQV0LZxJhLpqgDo7nBTd3BPiMP7IRhLwHh4mMwe_J15j3oaqPAm74vbuUyCiDxN09yGTWEd3Fj-wSzF8cDnn7Zqx8Bz4S5Io4zNs20kvP2VM9gIxGLTjv0SuTDCA9bopfVttl
			fcmSender.pushFCMIOSNotification(
					"c3tPAt-k7Gg:APA91bEnQV0LZxJhLpqgDo7nBTd3BPiMP7IRhLwHh4mMwe_J15j3oaqPAm74vbuUyCiDxN09yGTWEd3Fj-wSzF8cDnn7Zqx8Bz4S5Io4zNs20kvP2VM9gIxGLTjv0SuTDCA9bopfVttl", 
					"12","3","4","5","6","1","searchRideCriteria","Testing FCM", "sdfs");
			System.out.println("abc");
		} catch (Exception e) {
			e.printStackTrace();
//			throw new GenericException("Your server key is invalid");
		}
	}
}
