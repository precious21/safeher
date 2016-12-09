package com.tgi.safeher.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

public class CommonUtil {

	public static InetAddress getServerIpAddress() {

		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	
	public static String generateSecureRando(String pattern){
		SecureRandom rnd = new SecureRandom();
		StringBuilder code = new StringBuilder(10);
		for (int i = 0; i < 10; i++)
			code.append(pattern.charAt(rnd.nextInt(pattern.length())));
		return code.toString().toUpperCase();
	}
	
	
	
	
	
}
