/**
 * 
 */
package com.tgi.safeher.common.enumeration;

/**
 * @author Awais Haider
 *
 */
public enum SmsTypeEnum {
	AccountVerification(1);
	private int value;
	
	private SmsTypeEnum(int code){
		this.value = code;
	}
	
	public int getValue() {

		return value;
	}

}
