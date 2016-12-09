package com.tgi.safeher.jms;

import javax.jms.JMSException;

import org.springframework.stereotype.Component;

import com.tgi.safeher.jms.model.Notification;

@Component("asyncErrorReceiver")
public class RideRequestErrorMessageReceiver {

	public void receiveMessage(Notification notification) throws JMSException, InterruptedException {
		System.out.println("I'm here");
	}
	
	
}
