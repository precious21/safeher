package com.tgi.safeher.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Email {

//	final static String username = "safeherteam123@gmail.com";
//	final static String password = "safeher123";
//	final static String username = "tgi.shdev1@tabsusa.com";
//	final static String password = "Tg!D3v19";
	/*final static String username = "noreply-sh@tabsusa.com";
	final static String password = "NoRply#21";*/
//	final static String username = "info@safeher.com";
//	final static String password = "Saf3!9f76";
	final static String username = "info@gosafr.com";
	final static String password = "huHX0mw@yDM";


	public String sendEmail(String toEmail) {
		String returnMessage = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//		props.put("mail.smtp.host", "smtp.gmail.com");
		/*props.put("mail.smtp.ssl.trust", "webmail.tabsusa.com");
		props.put("mail.smtp.host", "webmail.tabsusa.com");
		props.put("mail.smtp.port", "587");*/
//		 props.put("mail.smtp.TLS.trust", "smtp.office365.com");
//		 props.put("mail.smtp.host", "smtp.office365.com");
//		 props.put("mail.smtp.port", "587");
		 props.put("mail.smtp.ssl.trust", "mail.gosafr.com");
		 props.put("mail.smtp.host", "mail.gosafr.com");
		 props.put("mail.smtp.port", "587");
		

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			
			String ipAddress = Common.getValueFromSpecificPropertieFile(
					"/properties/ipAddress.properties", "SERVER_IP_ADDRESS");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));
			message.setSubject("Reset Password");
//			message.setText("Dear Safeher User,"
//					+ "\n\n Click the link below for reset your password"
//					+ "\n http://"+Common.getServerIpAddress().getHostAddress()+":8080/Safeher/resetPassword.jsp?app="
//					+EncryptDecryptUtil.encrypt(toEmail) + "\n\n Best,"
//					+ "\n The Safeher team");
			message.setText("Dear Safeher User,"
					+ "\n\n Click the link below for reset your password"
					+ "\n http://"+ipAddress+":8080/Safeher/resetPassword.jsp?app="
					+EncryptDecryptUtil.encrypt(toEmail) + "\n\n Best,"
					+ "\n The Safeher team");

			Transport.send(message);

		} catch (MessagingException e) {
			returnMessage = "Email sending failed due to some error";
			e.printStackTrace();
		} catch (Exception e) {
			returnMessage = "Email sending failed due to some error";
			e.printStackTrace();
		}

		return returnMessage;

	}
}
