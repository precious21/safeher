package com.tgi.safeher.jms.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.DistanceAPIBeanV2;
import com.tgi.safeher.entity.RideSearchResultEntity;
import com.tgi.safeher.entity.UserLoginEntity;


/**
 * Notification Model to receive message on queues
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String requestNo;
	private String message;
	private String keyToken;
	private String passengerId;
	private String appUserId;
	private String pushNotificationStatus;
	private String pushNotificationMessage;
	private String osType;
	private Map<String,List<DistanceAPIBean>> map=new LinkedHashMap<String,List<DistanceAPIBean>>();
	private DistanceAPIBeanV2 dataBean;
	private RideSearchResultEntity entity;
	private UserLoginEntity entityAppUser;
	
	/*public Notification(String id, String message) {
		this.id = id;
		this.message = message;
	}*/

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getKeyToken() {
		return keyToken;
	}

	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getPushNotificationStatus() {
		return pushNotificationStatus;
	}

	public void setPushNotificationStatus(String pushNotificationStatus) {
		this.pushNotificationStatus = pushNotificationStatus;
	}

	public String getPushNotificationMessage() {
		return pushNotificationMessage;
	}

	public void setPushNotificationMessage(String pushNotificationMessage) {
		this.pushNotificationMessage = pushNotificationMessage;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public Map<String, List<DistanceAPIBean>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<DistanceAPIBean>> map) {
		this.map = map;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public RideSearchResultEntity getEntity() {
		return entity;
	}

	public void setEntity(RideSearchResultEntity entity) {
		this.entity = entity;
	}

	public UserLoginEntity getEntityAppUser() {
		return entityAppUser;
	}

	public void setEntityAppUser(UserLoginEntity entityAppUser) {
		this.entityAppUser = entityAppUser;
	}

	public DistanceAPIBeanV2 getDataBean() {
		return dataBean;
	}

	public void setDataBean(DistanceAPIBeanV2 dataBean) {
		this.dataBean = dataBean;
	}

}
