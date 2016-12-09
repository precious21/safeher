package com.tgi.safeher.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.tgi.safeher.API.thirdParty.Twilio.SmsTwilioRest;
import com.tgi.safeher.beans.PromAndReffBean;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.common.constant.EmailConstant;
import com.tgi.safeher.common.enumeration.UserRegFlowEnum;
import com.tgi.safeher.common.enumeration.SmsTypeEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.AppUserVehicleEntity;
import com.tgi.safeher.entity.CreditCardInfoEntity;
import com.tgi.safeher.entity.EmailLogEntity;
import com.tgi.safeher.entity.EmailTempleteEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.PostRideEntity;
import com.tgi.safeher.entity.PromotionCodesEntity;
import com.tgi.safeher.entity.RideBillEntity;
import com.tgi.safeher.entity.UserRatingEntity;
import com.tgi.safeher.entity.SmsLogEntity;
import com.tgi.safeher.entity.SmsTempelteEntity;
import com.tgi.safeher.entity.UserCommentEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.service.converter.RideConverter;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@EnableAsync
public class AsyncEmailService implements IAsyncEmailService {
	private static final Logger logger = Logger.getLogger(AsyncEmailService.class);
	/*final static String username = "noreply-sh@tabsusa.com";
	  final static String password = "NoRply#21";*/
//	final static String username = "info@safeher.com";
//	final static String password = "Saf3!9f76";
	final static String username = "info@gosafr.com";
	final static String password = "huHX0mw@yDM";

	@Autowired
	private SmsTwilioRest smsTwilioRest;
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Autowired
	private RideConverter rideConverter;
	
	
	@SuppressWarnings("unused")
	private Properties getPropertiesForEmail(){
		logger.info("**************Entering in getPropertiesForEmail  ******** "); 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		/*props.put("mail.smtp.ssl.trust", "webmail.tabsusa.com");
		props.put("mail.smtp.host", "webmail.tabsusa.com");
		props.put("mail.smtp.port", "587");*/
//		props.put("mail.smtp.TLS.trust", "smtp.office365.com");
//		props.put("mail.smtp.host", "smtp.office365.com");
//		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.ssl.trust", "mail.gosafr.com");
		props.put("mail.smtp.host", "mail.gosafr.com");
		props.put("mail.smtp.port", "587");
		
		return props;
	}
	
	@SuppressWarnings("unused")
	private Session getSessionForEmail(Properties props){
		logger.info("**************Entering in getSessionForEmail with  Properties"+ props+" ******** "); 
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		return session;
	}

	@Async
	@Override
	public String sendForgetPasswordEmail(String toEmail) throws GenericException {
		logger.info("**************Entering in sendForgetPasswordEmail with  Email"+ toEmail+" ******** "); 
		//we will use this method latter
		String returnMessage = "";

		Properties props = getPropertiesForEmail();
		Session session = getSessionForEmail(props);

		try {

			String ipAddress = Common.getValueFromSpecificPropertieFile(
					"/properties/ipAddress.properties", "SERVER_IP_ADDRESS");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));
			message.setSubject("Reset Password");
			message.setText("Dear Safeher User,"
					+ "\n\n Click the link below for reset your password"
					+ "\n http://" + ipAddress
					+ ":8080/Safeher/resetPassword.jsp?app="
					+ EncryptDecryptUtil.encrypt(toEmail) + "\n\n Best,"
					+ "\n The Safeher team");

			Transport.send(message);

		} catch (MessagingException e) {
			returnMessage = "Email sending failed due to some error";
			e.printStackTrace();
			logger.info("**************Exiting from sendForgetPasswordEmail with  MessagingException "+ e.getMessage()+" ******** "); 
			
		} catch (Exception e) {
			returnMessage = "Email sending failed due to some error";
			e.printStackTrace();
			logger.info("**************Exiting from sendForgetPasswordEmail with  Exception "+ e.getMessage()+" ******** "); 
			
		}

		return returnMessage;
	}

	@Async
	@Override
	public void sendInvoiceEmail(RideBean bean)
			throws GenericException {
		logger.info("**************Entering in sendInvoiceEmail with  RideBean"+ bean+" ******** ");
		getRideInfo(bean);
		getDriverPassengerInfo(bean);
		if(StringUtil.isNotEmpty(bean.getPassengerEmail()) && 
				StringUtil.isNotEmpty(bean.getDriverEmail())){
			Properties props = getPropertiesForEmail();
			Session session = getSessionForEmail(props);
			try {
//				InternetAddress[] recipientAddress = new InternetAddress[2];
//				recipientAddress[0] = new InternetAddress(bean.getDriverEmail().trim());
//				recipientAddress[1] = new InternetAddress(bean.getPassengerEmail().trim());
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(bean.getDriverEmail()));
//				message.setRecipients(Message.RecipientType.TO, recipientAddress);
//				message.setRecipients(Message.RecipientType.BCC,
//						InternetAddress.parse(bean.getPassengerEmail()));
				message.setSubject("Safr Driver Reciept");
				emailMessage(message, bean);
				Transport.send(message);
				logger.info("**************Invoice email has been sent to specific user Driver ******** "); 

			} catch (MessagingException e) {
				e.printStackTrace();
				logger.info("**************Exiting from sendInvoiceEmail with  MessagingException "+ e.getMessage()+" ******** "); 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("**************Exiting from sendInvoiceEmail with  Exception "+ e.getMessage()+" ******** "); 
				
			}
			Properties props2 = getPropertiesForEmail();
			Session session2 = getSessionForEmail(props2);
			try {
				Message message = new MimeMessage(session2);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(bean.getPassengerEmail()));
//				message.setRecipients(Message.RecipientType.TO, recipientAddress);
//				message.setRecipients(Message.RecipientType.BCC,
//						InternetAddress.parse(bean.getPassengerEmail()));
				message.setSubject("Safr Passenger Reciept");
				emailMessageForPassenger(message, bean);
				Transport.send(message);
				logger.info("**************Invoice email has been sent to specific user passenger ******** "); 

			} catch (MessagingException e) {
				e.printStackTrace();
				logger.info("**************Exiting from sendInvoiceEmail with  MessagingException "+ e.getMessage()+" ******** "); 
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("**************Exiting from sendInvoiceEmail with  Exception "+ e.getMessage()+" ******** "); 
				
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void emailMessage(Message message, RideBean bean) throws MessagingException{
		message.setText("Dear Safeher User,\n\n"
				+ "Here are your invoice information agaisnt your ride \n\n"
				+ "From: "+bean.getSourceAddress()+"\n"
				+ "To: "+bean.getDistinationAddress()+"\n\n"
				+ "Driver: "+bean.getDriverFirstName()+" "+bean.getDriverLastName()+" \n"
				+ "Passenger: "+bean.getPassengerFirstName()+" "+bean.getPassengerLastName()+" \n"
				+ "Invoice No: "+bean.getInvoiceNo()+"\n"
				+ "Total Amount: "+bean.getTotalAmount()+" $\n"
				+ "Company Amount: "+bean.getCompanyAmount()+" $\n"
				+ "Charity Amount: "+bean.getCharityAmount()+" $\n"
				+ "Tip Amount: "+bean.getTipAmount()+" $\n"
				+ "Driver Amount: "+bean.getDriverAmount()+" $\n"
				+ "Total Distance(Miles): "+bean.getActualDistance()+" \n"
				+ "Start Time: "+bean.getStartTime()+"\n"
				+ "End Time: "+bean.getEndTime()+"\n\n"
				+ "Best Regards,\n"
				+ "The Safeher team");
	}

	@SuppressWarnings("unused")
	private void emailMessageForPassenger(Message message, RideBean bean) throws MessagingException{
		
		String html = EmailConstant.InvoiceEmail;

		//Vehicle Information Setting
		 AppUserVehicleEntity appUserVehicle = appUserDao
			      .findByOjectForVehicleV2(AppUserVehicleEntity.class,
			        "appUser.appUserId", new Integer(bean.getAppUserByAppUserDriver()));
		if (appUserVehicle != null) {
			if(appUserVehicle.getVehicleInfo() != null){
				if(appUserVehicle.getVehicleInfo().getPlateNumber() != null){
					html = html.replaceAll("@driverVehicleNumber", 
							appUserVehicle.getVehicleInfo().getPlateNumber());
				}else{
					html = html.replaceAll("@driverVehicleNumber", "N/A");
				}
				if(appUserVehicle.getVehicleInfo().getVehicleModel() != null){
					if(appUserVehicle.getVehicleInfo().getVehicleModel().getName() != null){
						html = html.replaceAll("@driverVehicleName", appUserVehicle.
								getVehicleInfo().getVehicleModel().getName());
					}else{
						html = html.replaceAll("@driverVehicleName", "N/A");
					}
				}else{
					html = html.replaceAll("@driverVehicleName", "N/A");
				}
			}else{
				html = html.replaceAll("@driverVehicleNumber", "N/A");
				html = html.replaceAll("@driverVehicleName", "N/A");
			}
		}else{
			html = html.replaceAll("@driverVehicleNumber", "N/A");
			html = html.replaceAll("@driverVehicleName", "N/A");
		}

		// fetchingUserRating
		UserRatingEntity userRatingEntity = (UserRatingEntity) appUserDao
				.findByIdParamCommon("UserRatingEntity", "appUser.appUserId",
						new Integer(bean.getAppUserByAppUserDriver()));
		if (userRatingEntity != null) {
			if(userRatingEntity.getCurrentValue() != null){
				html = html.replaceAll("@driverOverallRating", userRatingEntity.getCurrentValue()+"");
			}else{
				html = html.replaceAll("@driverOverallRating", "N/A");
			}
		}else{
			html = html.replaceAll("@driverOverallRating", "N/A");
		}
		
		//fetching card
		AppUserPaymentInfoEntity appUserPaymentInfoEntity = (AppUserPaymentInfoEntity) appUserDao.findByIdParamCommon(
				"AppUserPaymentInfoEntity", "appUser.appUserId", new Integer(bean.getAppUserByAppUserPassenger()));
		if (appUserPaymentInfoEntity != null) {
			CreditCardInfoEntity creditCardInfo = appUserDao.findDefaultCreditCard(
					CreditCardInfoEntity.class, "appUserPaymentInfo",
					appUserPaymentInfoEntity);
			if(appUserPaymentInfoEntity.getDefaultType() != null && 
					appUserPaymentInfoEntity.getDefaultType().trim().equals("C")){
				if(creditCardInfo!=null){
					if(creditCardInfo.getCardNumber() != null){
						html = html.replaceAll("@creditLastFourNumber", 
								creditCardInfo.getCardNumber().substring(creditCardInfo.getCardNumber().length()-4)+"");
					}else{
						html = html.replaceAll("@creditLastFourNumber", "N/A");
					}
				}else{
					html = html.replaceAll("@creditLastFourNumber", "N/A");
				}
			}else{
				html = html.replaceAll("@creditLastFourNumber", "N/A");
			}
		}else{
			html = html.replaceAll("@creditLastFourNumber", "N/A");
		}
		
		//getRideTime
		Date startDate = new Date(bean.getTimeStampStartTime().getTime());
		Date endDate = new Date(bean.getTimeStampEndTime().getTime());
		Double sec = Common.getSecFromTwoDates(startDate, endDate);
		html = html.replaceAll("@tripTime", DateUtil.getMinutesAndSecs(sec));
		
		String time = bean.getTimeStampStartTime()+"";
		String startTime = time.split("\\s")[1].split("\\.")[0];
		html = html.replaceAll("@startTime", startTime);

		String time2 = bean.getTimeStampEndTime()+"";
		String endTime = time2.split("\\s")[1].split("\\.")[0];
		html = html.replaceAll("@endTime", endTime);
		
		//fetching passenger rating to driver
		if(bean.getIsRatingDone().equals("1")){
			UserCommentEntity userCommentEntity = appUserDao.getPassengerRatingToDriver(
					new Integer(bean.getAppUserByAppUserPassenger()), bean.getRideNo());
			if(userCommentEntity != null && 
					userCommentEntity.getRateValue() != null){
				html = html.replaceAll("@youRateDriver", userCommentEntity.getRateValue()+"");
			}else{
				html = html.replaceAll("@youRateDriver", "N/A");
			}
		}else{
			String ipAddress = Common.getValueFromSpecificPropertieFile(
					"/properties/ipAddress.properties", "SERVER_IP_ADDRESS");
			html = html.replaceAll("@youRateDriver", "<a href='http://"+ipAddress+":8080/Safeher/ratingComingSoon.jsp' traget='_blank' "+
					"Style='color: #ffffff;'>Rate Driver</a>");
		}
		
		//fetchCode
		PromotionCodesEntity promotionCodesEntity = appUserDao.findReferralCode(
				new Integer(bean.getAppUserByAppUserPassenger()));
		if(promotionCodesEntity != null){
			html = html.replaceAll("@referralCode", promotionCodesEntity.getCodeValue());
		}else{
			html = html.replaceAll("@referralCode", "N/A");
		}
		
		html = html.replaceAll("@invoice", bean.getInvoiceNo());
		html = html.replaceAll("@Date", DateUtil.getInvoiceDate(bean.getTimeStampStartTime()));
		html = html.replaceAll("@totalAmount", bean.getTotalAmount());
		html = html.replaceAll("@driverName", bean.getDriverFirstName()+" "+bean.getDriverLastName());
		html = html.replaceAll("@startAddress", bean.getSourceAddress());
		html = html.replaceAll("@endAddress", bean.getDistinationAddress());
		html = html.replaceAll("@miles", bean.getActualDistance());
		html = html.replaceAll("@charityAmount", bean.getCharityAmount());
		html = html.replaceAll("@charityName", bean.getCharityName());
		html = html.replaceAll("@driverImageUrl", 
				"http://uat5-redhat4994.cloudapp.net:8080/userImagesTemp/thumb-1481031656455Screenshot_20161018-234053.png");
		
		message.setContent(html, "text/html; charset=utf-8");
	}
	
	private void getRideInfo(RideBean bean) throws GenericException {
		try {
			PostRideEntity postRideEntity = appUserDao.
					getRideInfo(bean.getInvoiceNo());
			if(postRideEntity != null){
				rideConverter.convertEntityTORideBean(
						postRideEntity, bean);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from DataAccessException with Exception "+e.getMessage() +"  ******");
			throw new GenericException(
					"Server is not responding right now");
		}
	}
	
	private void getDriverPassengerInfo(RideBean bean) throws GenericException {
		try {
			RideBillEntity billEntity = appUserDao.
					getDriverPassengerInfo(bean.getInvoiceNo());
			if(billEntity != null){
				rideConverter.convertEntityTORideBeanForDriverPassInfo(
						billEntity, bean);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("******Exiting from DataAccessException with Exception "+e.getMessage() +"  ******");
			throw new GenericException(
					"Server is not responding right now");
		}
	}

	@Async
	@Override
	public void sendReferralEmail(PromAndReffBean bean) throws GenericException {
		Properties props = getPropertiesForEmail();
		Session session = getSessionForEmail(props);
		try {
			if(StringUtil.isNotEmpty(bean.getEmail())){
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(bean.getEmail()));
				message.setSubject("SafeHer Invitation");
				message.setText("Hi,\n\n"
						+ "You have been invited "+bean.getAmount()+"$ amount for ride from "+bean.getRefCreaterName()
						+ " through SafeHer App. Please download the SafeHer App and enjoy your free ride against "
						+ "this amount. \n"
						+ "Please click the link below to download SafeHer App \n"
						+ "http://www.safeher.com/ \n\n"
						+ "Best Regards,\n"
						+ "The Safeher team");
				Transport.send(message);
				logger.info("**************referral email has been sent to specific user  ******** "); 	
			}

		} catch (MessagingException e) {
			e.printStackTrace();
			logger.info("**************Exiting from referralEmail with  MessagingException "+ e.getMessage()+" ******** "); 
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("**************Exiting from referralEmail with  Exception "+ e.getMessage()+" ******** "); 
			
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tgi.safeher.service.IAsyncEmailService#sendEmail(java.lang.Integer,
	 * java.lang.String, java.lang.String, java.util.Map) Generic Email Sender
	 * with Map for Properties
	 */
	@Async
	@Override
	public void sendEmail(Integer emailCode, String appUserId, String userEmail, Map<String, String> map) {
		Properties props = getPropertiesForEmail();
		Session session = getSessionForEmail(props);
		String body = null;
		EmailTempleteEntity emailTempleteEntity = null;
		try {
			emailTempleteEntity = appUserDao.findEmailData(emailCode);
			if (emailTempleteEntity != null) {
				// to be get by DB subject, body
				System.out.println(body);
				body = emailTempleteEntity.getEmailBody();
				for (Map.Entry<String, String> entry : map.entrySet())
				{
					body = body.replace(entry.getKey(), entry.getValue());
				}
				

				if (StringUtil.isNotEmpty(userEmail)) {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
					message.setSubject(emailTempleteEntity.getSubject());
					
					if(map.get("Url")!=null){
						String url = map.get("Url");
						String address = null;
						String address2 = null;
						String ipAddress = Common.getValueFromSpecificPropertieFile(
								"/properties/ipAddress.properties", "SERVER_IP_ADDRESS");
						address = "http://"+ipAddress+":8080/Safeher/"+url+".jsp?appUserId="+EncryptDecryptUtil.encryptVerification(appUserId)+"&vCode="
								+map.get("vcode");
						body = body.replace("link", address);
						System.out.println(body);
						address2 = "http://"+ipAddress+":8080/Safeher/"+url+"Sms"+".jsp?appUserId="+EncryptDecryptUtil.encryptVerification(appUserId)+"&vCode="
								+map.get("vcode");
						map.put("Address", address2);
						sendSMS(appUserId, SmsTypeEnum.AccountVerification.getValue(), map);
			
					}

					String html = EmailConstant.HEADER+EmailConstant.MESSAGE_START+
							body+
							EmailConstant.MESSAGE_END+EmailConstant.FOOTER;

					message.setContent(html, "text/html; charset=utf-8");
					
//					message.setText(body);
//					System.out.println(body);
					Transport.send(message);

					EmailLogEntity emailLogEntity = new EmailLogEntity();
					emailLogEntity.setAppUserId(appUserId);
					emailLogEntity.setEmailResultStatus("Success");
					emailLogEntity.setEmailTempleteId(emailCode);
					emailLogEntity.setEmailTo(userEmail);
					emailLogEntity.setEmailTime(DateUtil.getCurrentTimestamp());
					appUserDao.logEmailStatus(emailLogEntity);

					logger.info("**************Email from AppUserID " + appUserId + " with subject "
							+ emailTempleteEntity.getSubject() + " has been Sent Check Log  ******** ");

				}
			}

		} catch (MessagingException e) {
			e.printStackTrace();
			EmailLogEntity emailLogEntity = new EmailLogEntity();
			emailLogEntity.setAppUserId(appUserId);
			emailLogEntity.setEmailResultStatus("Failed");
			//emailLogEntity.setEmailStatus(e.getMessage());
			emailLogEntity.setEmailTempleteId(emailCode);
			emailLogEntity.setEmailTo(userEmail);
			emailLogEntity.setEmailTime(DateUtil.getCurrentTimestamp());
			appUserDao.logEmailStatus(emailLogEntity);
			logger.info("**************Email from AppUserID " + appUserId + " with subject "
					+ emailTempleteEntity.getSubject() + " has been failed Check Log  ******** ");

		} catch (DataAccessException ex) {
			ex.printStackTrace();
			EmailLogEntity emailLogEntity = new EmailLogEntity();
			emailLogEntity.setAppUserId(appUserId);
			emailLogEntity.setEmailResultStatus("Failed");
			//emailLogEntity.setEmailStatus(e.getMessage());
			emailLogEntity.setEmailTempleteId(emailCode);
			emailLogEntity.setEmailTo(userEmail);
			emailLogEntity.setEmailTime(DateUtil.getCurrentTimestamp());
			appUserDao.logEmailStatus(emailLogEntity);
			logger.info("**************Email from AppUserID " + appUserId + " with subject "
					+ emailTempleteEntity.getSubject() + " has been failed Check Log  ******** ");
		} catch (Exception e) {
			EmailLogEntity emailLogEntity = new EmailLogEntity();
			emailLogEntity.setAppUserId(appUserId);
			emailLogEntity.setEmailResultStatus("Failed");
			//emailLogEntity.setEmailStatus(e.getMessage());
			emailLogEntity.setEmailTempleteId(emailCode);
			emailLogEntity.setEmailTo(userEmail);
			emailLogEntity.setEmailTime(DateUtil.getCurrentTimestamp());
			appUserDao.logEmailStatus(emailLogEntity);
			logger.info("**************Email from AppUserID " + appUserId + " with subject "
					+ emailTempleteEntity.getSubject() + " has been failed Check Log  ******** ");

		}

	}
		
			

	/*
	 * Save User Secret Code
	 */
	@Async
	private void sendSMS(String appUserId, Integer smsCode, Map<String, String> map){
		String smsData = null;
		
		String status = " ";
		PersonDetailEntity personDetailEntity = null;
			
		try{		
			UserLoginEntity userLoginEntity = appUserDao.findByIdParam2(Integer.parseInt(appUserId));	
			if(userLoginEntity != null ){
				personDetailEntity = userLoginEntity.getAppUser().getPersonDetail();
			}
		
		SmsTempelteEntity smsTempelteEntity = appUserDao.findSmsData(smsCode);
			if(smsTempelteEntity!=null){
				smsData = smsTempelteEntity.getSmsBody();
				if(smsData != null){
				
					for (Map.Entry<String, String> entry : map.entrySet())
					{
						smsData = smsData.replace(entry.getKey(), entry.getValue());
					}
					
				}
								
						
				if(personDetailEntity != null){
					status = smsTwilioRest.sendSMS("+"+personDetailEntity.getPrimaryCell(), "+18574018199", smsData,userLoginEntity, map, "");
					if(status == null){
						SmsLogEntity smsLogEntity = new SmsLogEntity();
						smsLogEntity.setAppUserId(appUserId);
						smsLogEntity.setSmsResultStatus("Success");
						smsLogEntity.setSmsTempelte(smsCode);
						smsLogEntity.setSentTime(DateUtil.getCurrentTimestamp());
						appUserDao.logSmsStatus(smsLogEntity);
					}else{
						SmsLogEntity smsLogEntity = new SmsLogEntity();
						smsLogEntity.setAppUserId(appUserId);
						smsLogEntity.setSmsResultStatus("Failed");
						smsLogEntity.setSmsTempelte(smsCode);
						smsLogEntity.setSentTime(DateUtil.getCurrentTimestamp());
						appUserDao.logSmsStatus(smsLogEntity);
					}
					
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
			SmsLogEntity smsLogEntity = new SmsLogEntity();
			smsLogEntity.setAppUserId(appUserId);
			smsLogEntity.setSmsResultStatus("Failed");
			smsLogEntity.setSmsTempelte(smsCode);
			smsLogEntity.setSentTime(DateUtil.getCurrentTimestamp());
			appUserDao.logSmsStatus(smsLogEntity);
					
		}
	
		
		
	
	}
	
	
	

}
