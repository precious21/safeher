package com.tgi.safeher.beans;

import java.util.Map;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class ResponseObject {
	
	private String ReturnType="";
	private String ReturnCode="";
	private String ReturnMessage="";
	private Map<?,?> ReturnData;
	private String queryTimeInMilli = "";
	public String getReturnType() {
		return ReturnType;
	}
	public void setReturnType(String returnType) {
		ReturnType = returnType;
	}
	public String getReturnMessage() {
		return ReturnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		ReturnMessage = returnMessage;
	}
	public Map<?, ?> getReturnData() {
		return ReturnData;
	}
	public void setReturnData(Map<?, ?> returnData) {
		ReturnData = returnData;
	}
	public String getQueryTimeInMilli() {
		return queryTimeInMilli;
	}
	public void setQueryTimeInMilli(String queryTimeInMilli) {
		this.queryTimeInMilli = queryTimeInMilli;
	}
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	
	

}
