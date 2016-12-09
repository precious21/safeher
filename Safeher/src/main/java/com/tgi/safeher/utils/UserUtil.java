package com.tgi.safeher.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.rws.SingletonSession;

@Component
@Scope("prototype")
public class UserUtil {

	public static boolean checkIfSessionExist(String appUserId, String sessionNo){
		
		String value = SingletonSession.getSessionMap().get(appUserId);
		if(value != null && 
				value.equalsIgnoreCase(sessionNo)){
			return true;
		}
		return false;	
	}
}
