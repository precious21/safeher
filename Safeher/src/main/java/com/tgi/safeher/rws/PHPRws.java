package com.tgi.safeher.rws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgi.safeher.rws.base.BaseRestfulWebService;
import com.tgi.safeher.service.IAsyncEmailService;
import com.tgi.safeher.utils.StringUtil;

@Controller
@Scope("prototype")
public class PHPRws extends BaseRestfulWebService{
	
	@Autowired
	private IAsyncEmailService iAsyncEmailService;
	
	//Send Email through PHP
		@ResponseBody
		@RequestMapping(value="/mail", method = RequestMethod.POST)
		public String sendEmail(@RequestParam("emailCode")String emailCode, @RequestParam("appUserId")String appUserId, @RequestParam("name")String name, @RequestParam("email")String email,  Map<String, Object> model){
			
			Map<String, String> map = new HashMap<String, String>();
			
			if(StringUtil.isEmpty(emailCode) || StringUtil.isEmpty(emailCode) || StringUtil.isEmpty(emailCode) || StringUtil.isEmpty(emailCode)  ){
				return "Please fill all the require fields then try Again";
			}
			
			try{
				iAsyncEmailService.sendEmail(Integer.parseInt(emailCode), appUserId, email, map);
			}catch(Exception e){
				return e.getMessage();
			}
			
			return "Success";
		
		}
		

}
