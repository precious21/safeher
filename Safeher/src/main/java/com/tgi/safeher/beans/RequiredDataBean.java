package com.tgi.safeher.beans;

import java.io.Serializable;

public class RequiredDataBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double estimatedFare;
	private String imageUrl;
	
	public RequiredDataBean(){
		
	}

	public Double getEstimatedFare() {
		return estimatedFare;
	}

	public void setEstimatedFare(Double estimatedFare) {
		this.estimatedFare = estimatedFare;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	

}
