package com.tgi.safeher.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.jms.model.Notification;
import com.tgi.safeher.repository.NotificationRepository;


/**
 * Can be used to add notification synchronous messages in List
 */
@Scope("singleton")
@Component("notificationRegistry")
public class NotificationRegistry {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private NotificationRepository notificationRepository;
	private List<Notification> receivedNotifications = new ArrayList<Notification>();
	private Map<String,Map<String,Notification>> map = new HashMap<String,Map<String,Notification>>();
	public NotificationRegistry(){
		map = new HashMap<String,Map<String,Notification>>();
	}
	public List<Notification> getReceivedNotifications() {
		return receivedNotifications;
	}
	
	public void registerNotification(Notification notification) {
		logger.info("inserting notification");
		/*
		Map<String,Notification> tMap=new HashMap<String,Notification>();
		tMap.put("1",new Notification());
		tMap.put("2",new Notification());
		tMap.put("3",new Notification());
		
		notificationRepository.saveNotification(tMap);*/


		
		Map<String,Notification> m=null;
		m=new HashMap<String,Notification>();
		
		
		
		if(map.get(notification.getPassengerId())!=null){
			m=map.get(notification.getPassengerId());
			if(notification.getAppUserId()==null){
				m.put(notification.getAppUserId(),notification);
				map.remove(notification.getPassengerId());
				map.put(notification.getPassengerId(), m);
			}
			
			
		}else{
			m=new HashMap<String,Notification>();
			m.put(notification.getAppUserId(),notification);
			map.put(notification.getPassengerId(), m);
			
			
		}
		
		receivedNotifications.add(notification);
	}
	
	public void clear() {
		receivedNotifications.clear();
		map.clear();
	}

	public Map<String, Map<String, Notification>> getMap() {
		return map;
	}

	public void setMap(Map<String, Map<String, Notification>> map) {
		this.map = map;
	}
}
