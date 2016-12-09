package com.tgi.safeher.API.thirdParty.Twilio;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusEntity;
import com.tgi.safeher.entity.AppUserPhoneEmailStatusLogEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.service.impl.AsyncEmailService;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.EncryptDecryptUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
@EnableAsync
@Transactional
@Scope("prototype")
public class SmsTwilioRest {
	
	@Autowired 
	private AppUserDao appUserDao;
	
	public SmsTwilioRest(){
		
		String accountId = Common.getValueFromSpecificPropertieFile(
				"/properties/twilioCredentials.properties", "AccountId");
		String authToken = Common.getValueFromSpecificPropertieFile(
				"/properties/twilioCredentials.properties", "AuthToken");
		Twilio.init(accountId, authToken);		
	}
	
	
	public String sendSMS(String to, String from, String body, UserLoginEntity userLoginEntity, Map<String,String> map, String url){
	
		Message message = Message
				.creator(new PhoneNumber(to), new PhoneNumber(from),
						body).create();
		if(message.getErrorMessage()==null){
			AppUserPhoneEmailStatusEntity appUserPhoneEmailStatusEntity = new AppUserPhoneEmailStatusEntity();
			appUserPhoneEmailStatusEntity.setPending("1");
			appUserPhoneEmailStatusEntity.setNotVerified("1");

			AppUserPhoneEmailStatusLogEntity appUserPhoneEmailStatusLogEntity = new AppUserPhoneEmailStatusLogEntity();
			appUserPhoneEmailStatusLogEntity.setPrimaryEmail("0");
			appUserPhoneEmailStatusLogEntity.setSecondaryEmail("0");
			appUserPhoneEmailStatusLogEntity.setPrimaryCell("1");
			appUserPhoneEmailStatusLogEntity.setCode(EncryptDecryptUtil.
					encryptVerification(map.get("vcode")));
			appUserPhoneEmailStatusLogEntity.setAppUser(userLoginEntity.getAppUser());
			saveSMSLog(appUserPhoneEmailStatusEntity, appUserPhoneEmailStatusLogEntity); 
			
			
			
		}
		return message.getErrorMessage();
		
	}
	
	@Async
	public void saveSMSLog(AppUserPhoneEmailStatusEntity appUserPhoneEmailStatusEntity, AppUserPhoneEmailStatusLogEntity  appUserPhoneEmailStatusLogEntity){
		boolean found = false;
		try{
			found = appUserDao.save(appUserPhoneEmailStatusEntity);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(found){
			try{
					appUserPhoneEmailStatusLogEntity.setAppUserPhoneEmailStatus(appUserPhoneEmailStatusEntity);
					appUserDao.saveOrUpdate(appUserPhoneEmailStatusLogEntity);
				}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
	
	

}
