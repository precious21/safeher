package com.tgi.safeher.service;

import com.tgi.safeher.beans.BankAccountInfoBean;
import com.tgi.safeher.beans.CreditCardInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.AppUserPaymentInfoEntity;
import com.tgi.safeher.entity.CreditCardTypeEntity;

public interface IPaymentService {

	public void saveCreditCardInfo(SafeHerDecorator decorator)
			throws GenericException;

	public void defaultCreatitCard(SafeHerDecorator decorator)
			throws GenericException;

	public boolean checkAppPaymentInfo(AppUserEntity appUser)
			throws GenericException;

	public boolean checkCreditCardInfo(AppUserPaymentInfoEntity appPaymentInfo ,String creditCardNo)
			throws GenericException;

	//public boolean insertCreditCardInfo(CreditCardInfoBean bean);

	public CreditCardTypeEntity getCreditCardType(String cardNum);
	
	public void saveBankAccountInfo(SafeHerDecorator decorator)
			throws GenericException;
	
	public void saveBankAccountInfoV2(SafeHerDecorator decorator)
			throws GenericException;
	
	public boolean checkBankAccountInfo(AppUserPaymentInfoEntity appPaymentInfo,String IBanNo)
			throws GenericException;

	public boolean insertBankInfo(BankAccountInfoBean bean);

	public void getBankList(SafeHerDecorator decorator);

	public void getBankInfo(SafeHerDecorator decorator) throws GenericException;

	public void updateBankInfo(SafeHerDecorator decorator) throws GenericException;

	public void getPassengerPaymentDetail(SafeHerDecorator decorator)throws GenericException;

	public void addPayPalInfo(SafeHerDecorator decorator) throws GenericException;

	public void getMultipleDrivers(SafeHerDecorator decorator) throws GenericException;

	public void setDefaultBankInfo(SafeHerDecorator decorator)throws GenericException;

	public boolean insertCreditCardInfo(CreditCardInfoBean bean, AppUserEntity appUser) throws GenericException;

	public void setInActiveOrDeleteBankInfo(SafeHerDecorator decorator) throws GenericException;
}
