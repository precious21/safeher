package com.tgi.safeher.map.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.data.geo.Point;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class MapBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3387097184433961357L;
	private String isDriver;
	private String personName;
	private String appUserId;
	private String lng;
	private String lat;
	private String preLng;
	private String preLat;
	private String address;
	private String street;
	private String city;
	private String state;
	private String stateCode;
	private String countryCode;
	private String country;
	private String suburb;
	private String rideNo;
	private String neighborHood;
	private String isPhysical;
	private String direction = "0.0";
	private String receiverId;
	private String arrivelTime;
	private String requestNo;
	private List<MapBean> personList = new ArrayList<MapBean>();
	private List<Point> latlngList = new ArrayList<Point>();
	private Point loc;
	private String activeDriverStatus;
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public List<MapBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<MapBean> personList) {
		this.personList = personList;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getIsPhysical() {
		return isPhysical;
	}

	public void setIsPhysical(String isPhysical) {
		this.isPhysical = isPhysical;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getNeighborHood() {
		return neighborHood;
	}

	public void setNeighborHood(String neighborHood) {
		this.neighborHood = neighborHood;
	}

	public String getPreLng() {
		return preLng;
	}

	public void setPreLng(String preLng) {
		this.preLng = preLng;
	}

	public String getPreLat() {
		return preLat;
	}

	public void setPreLat(String preLat) {
		this.preLat = preLat;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "MapBean [isDriver=" + isDriver + ", personName=" + personName
				+ ", appUserId=" + appUserId + ", lng=" + lng + ", lat=" + lat
				+ ", preLng=" + preLng + ", preLat=" + preLat + ", address="
				+ address + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", stateCode=" + stateCode
				+ ", countryCode=" + countryCode + ", country=" + country
				+ ", suburb=" + suburb + ", neighborHood=" + neighborHood
				+ ", isPhysical=" + isPhysical + ", direction=" + direction
				+ ", personList=" + personList + "]";
	}

	public String getRideNo() {
		return rideNo;
	}

	public void setRideNo(String rideNo) {
		this.rideNo = rideNo;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getActiveDriverStatus() {
		return activeDriverStatus;
	}

	public void setActiveDriverStatus(String activeDriverStatus) {
		this.activeDriverStatus = activeDriverStatus;
	}

	public List<Point> getLatlngList() {
		return latlngList;
	}

	public void setLatlngList(List<Point> latlngList) {
		this.latlngList = latlngList;
	}

	public String getArrivelTime() {
		return arrivelTime;
	}

	public void setArrivelTime(String arrivelTime) {
		this.arrivelTime = arrivelTime;
	}
	
}
