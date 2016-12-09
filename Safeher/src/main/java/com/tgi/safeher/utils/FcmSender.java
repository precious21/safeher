package com.tgi.safeher.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.exception.GenericException;

public class FcmSender {
	
	// Method to send Notifications from server to client end.

//	public final static String AUTH_KEY_FCM = "AIzaSyDjABcy7fyQY251W3GM-TeUn2djA9rTY7o";
	public final static String AUTH_KEY_FCM = "AIzaSyCVQCZHILn7E5oSvxTIUlPzqsWY1UfGxjE";
	 public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	// userDeviceIdKey is the device id you will query from your database

	public static void pushFCMNotification(String userDeviceIdKey) throws Exception, GenericException{

		String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
		String FMCurl = API_URL_FCM;     
	
		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
	
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization","key="+authKey);
		conn.setRequestProperty("Content-Type","application/json");
	
		JSONObject json = new JSONObject();
//		json.put("priority","high");
//		json.put("content_available", true);
		json.put("collapse_key", "score_update");
		json.put("time_to_live", 108);
		json.put("delay_while_idle", true);
		json.put("content-available", true);
		JSONObject info = new JSONObject();
		info.put("title", "Notificatoin Title");   // Notification title
		info.put("body", "Request has been accepted by the driver"); // Notification body
		info.put("sound", "default"); // Notification sound 
		info.put("PassengerAppId", "123");
		info.put("DriverAppId", "657");
		info.put("PreRideId", "75");
		info.put("EstimatedTime", "7364");
		info.put("notificationType",
				PushNotificationStatus.AcceptRideFromDriver.getValue());
		info.put("isFCM", "1");
		json.put("notification", info);
		json.put("to",userDeviceIdKey.trim());
		
		System.out.println(json);
		
//		 try {
//			 Sender sender = new Sender(authKey);
//			 Message.Builder builder = new Message.Builder(); 
//
//             // Use the same token(or registration id) that was earlier
//             // used to send the message to the client directly from
//             // Firebase Console's Notification tab.
//			 
//			 	builder.timeToLive(30);
//				builder.delayWhileIdle(true);
//	
//				builder.setData(json);
//				
//				Message message = builder.build();
//				
//				System.out.println(message);
//				
//				com.google.android.gcm.server.Result result = sender.send(message, userDeviceIdKey, 10);
//
//				System.out.println("result = " + result);
//				System.out.println("Success: "+result.getMessageId());
//			 
//				System.out.println("Result: " + result.toString());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
	
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(json.toString());
		wr.flush();
//		conn.getInputStream();
		InputStream input = conn.getInputStream();
		String result = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            for (String line; (line = reader.readLine()) != null;) {
            	result += line;
                System.out.println(line);
            }
        }
        System.out.println("result: "+result);
//        {"multicast_id":6764893239365663261,"success":1,"failure":0,"canonical_ids":0,"results":[{"message_id":"0:1472452253764277%6fde5a256fde5a25"}]}
        System.out.println("Http POST request sent!");
	}
	
	public static void main(String[] args) throws GenericException {
		FcmSender fcmSender = new FcmSender();
		try {
			fcmSender.pushFCMNotification("cj-kmyPEPBA:APA91bEWsvA-2OoRLxirnT_efnqIJ2pm9I2NBVbo9AboU2Z_qQGsmsgYgswE0DRNNoyQ8mgURBuDaZizfOrJU5jgJdwWhFhUGNGwcQ-X4nkBtl2CsA40K7kKwDtOOZGqi1_v-MXGztni");
			System.out.println("abc");
		} catch (Exception e) {
			e.printStackTrace();
//			throw new GenericException("Your server key is invalid");
		}
	}
}
