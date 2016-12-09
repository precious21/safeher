package com.tgi.safeher.utils;

import com.tgi.safeher.common.enumeration.CredtitCardEnum;

/**
 * @author :
 * @Date :
 * @version :

 * 
 *          <center><b></b></center> <center><b>
 *          </b></center>
 * 
 *          <pre>
 * 
 * ________________________________________________________________________________________________
 * 
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 * 
 * 
 * ________________________________________________________________________________________________
 * </pre>
 * 
 */
public class CreditCardUtil {
	public static String getCreditCardTypeByNumber(String creditCardNumber) {

		String regVisa = "^4[0-9]{12}(?:[0-9]{3})?$";
		String regMaster = "^5[1-5][0-9]{14}$";
		String regExpress = "^3[47][0-9]{13}$";
		String regDiners = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
		String regDiscover = "^6(?:011|5[0-9]{2})[0-9]{12}$";
		String regJCB= "^(?:2131|1800|35\\d{3})\\d{11}$";


		if(creditCardNumber.matches(regVisa))
			return CredtitCardEnum.Visa.getValue();
		if (creditCardNumber.matches(regMaster))
			return CredtitCardEnum.MasterCard.getValue();
		if (creditCardNumber.matches(regExpress))
			return CredtitCardEnum.AmericanExpress.getValue();
		if (creditCardNumber.matches(regDiscover))
			return CredtitCardEnum.DiscoverCard.getValue();
		if (creditCardNumber.matches(regJCB))
			return CredtitCardEnum.JCB.getValue();
		return "invalid";
	}
}
