package com.tgi.safeher.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

	public static boolean isEmailAddress(String validateableEmail) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(validateableEmail);
		boolean matchFound = m.matches();
		return matchFound;
	}

}
