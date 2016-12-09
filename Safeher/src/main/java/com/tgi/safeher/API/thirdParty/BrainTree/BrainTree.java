package com.tgi.safeher.API.thirdParty.BrainTree;

import java.math.BigDecimal;
import java.util.Arrays;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.CreditCardVerification;
import com.braintreegateway.CreditCardVerificationSearchRequest;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.PaymentMethod;
import com.braintreegateway.PaymentMethodRequest;
import com.braintreegateway.ResourceCollection;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.braintreegateway.TransactionOptionsRequest;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.exceptions.NotFoundException;
import com.tgi.safeher.beans.BrainTreeReponce;

public class BrainTree {
	private static BraintreeGateway gateway = new BraintreeGateway(
			Environment.SANDBOX, "km5hhg9kxkggx659", "vhg6gx95p2c3562s",
			"df130325132ef887e56ff8965431b741");

	private static Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] {
			Transaction.Status.AUTHORIZED, Transaction.Status.AUTHORIZING,
			Transaction.Status.SETTLED,
			Transaction.Status.SETTLEMENT_CONFIRMED,
			Transaction.Status.SETTLEMENT_PENDING, Transaction.Status.SETTLING,
			Transaction.Status.SUBMITTED_FOR_SETTLEMENT };

	public static String getToken() {
		String clientToken = gateway.clientToken().generate();
		System.out.println(clientToken);
		return clientToken;
	}

	public static String transaction(String amount, String nonce) {
		String message = "success";
		BigDecimal decimalAmount;
		try {
			decimalAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			return "Amount is an invalid format";
		}

		TransactionRequest request = new TransactionRequest()
				.amount(decimalAmount).paymentMethodNonce(nonce).options()
				.done();

		Result<Transaction> result = gateway.transaction().sale(request);

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			return transaction.getId();
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			return transaction.getId();
		} else {
//			String errorString = "";
			for (ValidationError error : result.getErrors()
					.getAllDeepValidationErrors()) {
				message += "Error: " + error.getCode() + ": "
						+ error.getMessage() + "\n";
			}
			return message;
		}
	}
	
	public static String transactionUsingCustomer(String amount, String customerId) {
		String message = "success";
		BigDecimal decimalAmount;
		try {
			decimalAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			return "Amount is an invalid format";
		}

		TransactionRequest request = new TransactionRequest()
	    		.customerId(customerId)
	    		.amount(new BigDecimal(amount));

		Result<Transaction> result = gateway.transaction().sale(request);

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			return transaction.getId();
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			return transaction.getId();
		} else {
//			String errorString = "";
			for (ValidationError error : result.getErrors()
					.getAllDeepValidationErrors()) {
				message += "Error: " + error.getCode() + ": "
						+ error.getMessage() + "\n";
			}
			return message;
		}
	}

	public static String payPalTransaction(String amount, String nonce) {
		String message = "success";
		
		BigDecimal decimalAmount;
		try {
			decimalAmount = new BigDecimal(amount);
		} catch (NumberFormatException e) {
			return "Amount is an invalid format";
		}
		
		TransactionOptionsRequest request = new TransactionRequest()
				.amount(decimalAmount)
				.paymentMethodNonce(nonce).options().paypal().done();

		Result<Transaction> result = gateway.transaction().sale(request.done());

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			return transaction.getId();
		} else {
			message = "ErrorMessage: " + result.getMessage();
		}

		return message;
	}

	public static BrainTreeReponce getTransaction(String transactionId) {
		BrainTreeReponce brainTreeReponce = new BrainTreeReponce();
		Transaction transaction = null;
		CreditCard creditCard = null;
		Customer customer = null;

		try {
			transaction = gateway.transaction().find(transactionId);
			creditCard = transaction.getCreditCard();
			customer = transaction.getCustomer();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			brainTreeReponce.setMessage("Exception: " + e);
		}
		
		brainTreeReponce.setCreditcard(creditCard);
		brainTreeReponce.setSuccess(
				Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(
						transaction.getStatus()));
		brainTreeReponce.setTrancation(transaction);
		brainTreeReponce.setCustomer(customer);

//		model.addAttribute(
//				"isSuccess",
//				Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(
//						transaction.getStatus()));
//		model.addAttribute("transaction", transaction);
//		model.addAttribute("creditCard", creditCard);
//		model.addAttribute("customer", customer);

		return brainTreeReponce;
	}

	public static String verifyCardByVerificationid(String customerId, String nonce) {
		String message = "";

		CreditCardVerificationSearchRequest request = new CreditCardVerificationSearchRequest()
				.creditCardNumber().is("the_verification_id");

		ResourceCollection<CreditCardVerification> collection = gateway
				.creditCardVerification().search(request);

		for (CreditCardVerification verification : collection) {
			System.out.println(verification.getStatus());
		}
		return null;
	}
	
	public static String[] createCustomer(String firstName, String lastName,
			String nonceFromClient) {
		Result<Customer> result = null;
		String[] customerInfo= new String[10];
		CustomerRequest request = new CustomerRequest()
		.firstName(firstName)
		.lastName(lastName).creditCard()
		.paymentMethodNonce(nonceFromClient)
		.options()
		.verifyCard(true)
		.done()
		.done();
		try {
			result = gateway.customer().create(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(result.isSuccess()){
			CreditCardVerification verification = result
					.getCreditCardVerification();
			Customer customer = result.getTarget();
		//	customerInfo[0] = verification.getStatus().toString();
			customerInfo[1]=customer.getId();
			customer.getPaymentMethods().get(0).getToken();
		} else {
			CreditCardVerification verification = result
					.getCreditCardVerification();
			if (verification.getStatus().name().equals("PROCESSOR_DECLINED")) {
				customerInfo[0] = verification.getStatus().toString();
				customerInfo[1] = verification.getProcessorResponseText();
			} else if (verification.getStatus().name().equals("GATEWAY_REJECTED")) {
				customerInfo[1] = verification.getGatewayRejectionReason()
						.toString();
				customerInfo[0] = verification.getStatus().toString();
			} else if (verification.getStatus().name().equals("FAILED")) {
				customerInfo[0] = verification.getStatus().toString();
			}

		}
		
		return customerInfo;
	}

	public static void submitForSettelment(String transcationId){
		try {
			Result<Transaction> result = gateway.transaction()
					.submitForSettlement(transcationId);
			if (result.isSuccess()) {
				Transaction transaction = result.getTarget();
				transaction.getStatus();
			}
		}
		catch(NotFoundException ex){
			System.out.println("Exception: " + ex);
		}
		
	}
	public static Customer getCustomerById(String customerId) {
		Customer customer = gateway.customer().find(customerId);
		return customer;
	}
	
	public static boolean deleteCustomer(String customerId) {
		Result<Customer> result = gateway.customer().delete(customerId);
		return result.isSuccess();
	}
//	public static boolean verifyCreditCardByPaymentToken(String token){
//		try{
//			CreditCardVerificationSearchRequest request = new CreditCardVerificationSearchRequest()
//		    .paymentMethodToken().is(token);
//		}catch(){
//			
//		}
//		CreditCardVerificationSearchRequest request = new CreditCardVerificationSearchRequest()
//	    .paymentMethodToken().is(token);
//	ResourceCollection<CreditCardVerification> collection = gateway.transaction().search(request);
//		return false;
//		
//	}
	public static void main (String args[]){
//		String token = BrainTree.getToken();
//		System.out.println(token);
//		String token = BrainTree.getToken();
//		System.out.println(token);
//		String message = BrainTree.payPalTransaction("0.9", "ec93c244-4137-4683-8257-7f8212f16a56");
//		String message = BrainTree.transaction("1.0", "68cb8c7d-9af1-4742-9bbb-d3ca4c3c4b2a");
//		System.out.println(message);
		String message = BrainTree.transactionUsingCustomer("5.0", "37685162");
		System.out.println("Transaction ID: "+message);
//		BrainTreeReponce brainTreeReponce = BrainTree.getTransaction(message);
//		System.out.println(brainTreeReponce.isSuccess());
	}
}
