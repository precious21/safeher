package com.tgi.safeher.utils;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.SocialBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;

@Component
@Scope("prototype")
public class SocialVerificationUtil implements Serializable {

	@Autowired
	private DataBeanFactory dataBeanFactory;

	private static FacebookClient facebookClient;

	public SocialBean verifyFaceBookAccountRestFbApi(String token)
			throws FacebookOAuthException {
		SocialBean bean = new SocialBean();
		facebookClient = new DefaultFacebookClient(token, Version.LATEST);
		User user = facebookClient.fetchObject("me", User.class);
		return bean;
	}

	public SocialBean verifyGoogleAccountUsingGoolgeApi(String token)
			throws ClientProtocolException, IOException {
		SocialBean bean = new SocialBean();
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier mVerifier = new 
        		GoogleIdTokenVerifier(transport, jsonFactory);
        try {
			GoogleIdToken googleIdToken = GoogleIdToken.parse(jsonFactory, token);
            if (mVerifier.verify(googleIdToken)) {
                GoogleIdToken.Payload tempPayload = googleIdToken.getPayload();
                System.out.println(tempPayload);
                bean = (SocialBean) dataBeanFactory.populateDataBeanFromJSON(
        				SocialBean.class, new SafeHerDecorator(), tempPayload.toString());
            }

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return bean;
	}

	//we won't use it
	public SocialBean verifyFaceBookAccountUsingUrl(String token)
			throws ClientProtocolException, IOException {
		SocialBean bean = new SocialBean();
		HttpClient httpclient = new DefaultHttpClient();
		String newUrl = "https://graph.facebook.com/me?access_token=" + token;
		HttpGet httpget = new HttpGet(newUrl);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpget, responseHandler);
		System.out.println(responseBody);
		bean = (SocialBean) dataBeanFactory.populateDataBeanFromJSON(
				SocialBean.class, new SafeHerDecorator(), responseBody);
		return bean;
	}
	
	//we won't use it
	public SocialBean verifyGoogleAccountUsingUrl(String token)
			throws ClientProtocolException, IOException {
		SocialBean bean = new SocialBean();
		HttpClient httpclient = new DefaultHttpClient();
		String newUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token;
		HttpGet httpget = new HttpGet(newUrl);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpget, responseHandler);
		System.out.println(responseBody);
		bean = (SocialBean) dataBeanFactory.populateDataBeanFromJSON(
				SocialBean.class, new SafeHerDecorator(), responseBody);
		return bean;
	}
}
