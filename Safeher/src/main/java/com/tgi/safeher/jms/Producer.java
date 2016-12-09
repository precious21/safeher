package com.tgi.safeher.jms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



import com.tgi.safeher.jms.model.Notification;

/*
 * This producer is used to save messages in queue (providing names of desired queue)
 */
@Component("producer")
@Service
@Transactional
public class Producer {

	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;
	
	@Autowired
	private NotificationRegistry registry;

	public void convertAndSendMessage(Notification notification) {
		registry.registerNotification(notification);
		jmsTemplate.convertAndSend(notification);
	}
	/**
	 * Send Message to desired queue
	 * @param destination queue name
	 * @param notification Message to be send in notification model
	 */
	public void convertAndSendMessage(String destination, Notification notification)
	{
		registry.registerNotification(notification);
		jmsTemplate.convertAndSend(destination, notification);
							
	}	
	public void convertAndSendTopic(Notification notification) {

		jmsTopicTemplate.convertAndSend("ride.request.queue", notification);
	}
}
