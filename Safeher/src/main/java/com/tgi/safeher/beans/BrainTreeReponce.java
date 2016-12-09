package com.tgi.safeher.beans;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Transaction;

public class BrainTreeReponce {
	private Transaction trancation;
	private Customer customer;
	private CreditCard creditcard;
	private boolean success;
	private String message;
	public Transaction getTrancation() {
		return trancation;
	}
	public void setTrancation(Transaction trancation) {
		this.trancation = trancation;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CreditCard getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(CreditCard creditcard) {
		this.creditcard = creditcard;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
