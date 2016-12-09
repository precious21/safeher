package com.tgi.safeher.service;

import java.util.Map;

import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IAsyncEmailService {
	
	public String sendForgetPasswordEmail(String toEmail)
			throws GenericException;
	
	public void sendInvoiceEmail(RideBean bean)
			throws GenericException;
	
	public void sendReferralEmail(PromAndReffBean bean)
			throws GenericException;
	
	public void sendEmail(Integer emailCode,String appUserId, String userEmail, Map<String,String> map);
}
