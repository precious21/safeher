package com.tgi.safeher.utils;

import java.security.Key;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.tgi.safeher.common.exception.MessageException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptDecryptUtil {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8302109394271007637L;

	private static final String ALGO = "AES";
	
	//Default Key
	private static final byte[] key1 = new byte[] { 'B', 'O', 'O', 'f', 'f',
			'i', 'c', 'e', 'F', 'O', 'O', 'f', 'f', 'i', 'c', 'e' };// BO Office FO Office
	
	private static final byte[] key4 = new byte[] { 'B', 'O', 'O', 'f', 'f',
			'i', 'c', 'e', 'F', 'O', 'O', 'f', 'f', 'i', 'c', 'e', 'S', 'a', 'F', 'e', 'H', 'e', 'R', 'S', 
			'S', 'a', 'F', 'e', 'H', 'e', 'R', 'S'};// BO Office FO Office SaFeHeRs
	
	private static final byte[] key2 = new byte[] { 'U', 'S', 'A', 'M', 'o',
			't', 'o', 'r', 'V', 'e', 'h', 'i', 'c', 'l', 'e', 's' };// USA Motor Vehicles
	
	private static final byte[] key3 = new byte[] { 'B', 'O', 'F', 'O', 'A',
		'P', 'I', 'W', 'e', 'b', 'K', 'e', 'y', 'P', 'K', 'G' };// BO FO API Web Key PKG
	
	private static final byte[] keyForVerification = new byte[] { 'Y', 'O', 'U', 'a', 'r',
			'R', 'A', 'w', 's', 'o', 'm', 'e', 'f', 'i', 'c', 'e' };

	public static String encrypt(String Data) {

		String encryptedValue = null;
		if(StringUtil.isEmpty(Data)){
			throw new MessageException("Data is null");
		}
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) {

		String decryptedValue = null;
		if(StringUtil.isEmpty(encryptedData)){
			throw new MessageException("encryptedData is null");
		}
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedValue;
	}

	public static String encryptVerification(String Data) {

		String encryptedValue = null;
		if(StringUtil.isEmpty(Data)){
			throw new MessageException("Data is null");
		}
		try {
			Key key = new SecretKeySpec(keyForVerification, ALGO);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	public static String decryptVerification(String encryptedData) {

		String decryptedValue = null;
		if(StringUtil.isEmpty(encryptedData)){
			throw new MessageException("encryptedData is null");
		}
		try {
			Key key = new SecretKeySpec(keyForVerification, ALGO);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedValue;
	}

	public static String encrypt256(String Data) {

		String encryptedValue = null;
		if(StringUtil.isEmpty(Data)){
			throw new MessageException("Data is null");
		}
		try {
			Key key = new SecretKeySpec(key4, ALGO);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	public static String decrypt256(String encryptedData) {

		String decryptedValue = null;
		if(StringUtil.isEmpty(encryptedData)){
			throw new MessageException("encryptedData is null");
		}
		try {
			Key key = new SecretKeySpec(key4, ALGO);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(key1, ALGO);
		return key;
//		Key key = null;
//		if (type == EDKTypeEnum.TYPE_B.getValue()) {
//			key = new SecretKeySpec(key2, ALGO);
//		} else if(type == EDKTypeEnum.TYPE_C.getValue()) {
//			key = new SecretKeySpec(key3, ALGO);
//		}else {
//			key = new SecretKeySpec(key1, ALGO);
//		}
//		return key;
	}

	public void encrptionAlgos() {

		// get an array containing all the installed providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// get a view of the property keys contained in this provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = it.next().toString();
				key = key.split(" ")[0];
				if (key.startsWith("Alg.Alias.")) {
					// strip the alias
					key = key.substring(10);
				}
				int index = key.indexOf('.');
				String serviceType = key.substring(0, index);
				Set<String> algorithms = getAlgorithms(serviceType);
				System.out.println(serviceType);
				for (Iterator<String> iter = algorithms.iterator(); iter
						.hasNext();) {
					System.out.println("\t" + iter.next());
				}
			}
		}
	}

	private static Set<String> getAlgorithms(String serviceType) {

		Set<String> algorithms = new TreeSet<String>();
		// get an array containing all the installed providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// get a view of the property keys contained in this provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = it.next().toString();
				key = key.split(" ")[0];
				if (key.startsWith(serviceType + ".")) {
					algorithms.add(key.substring(serviceType.length() + 1));
				} else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
					algorithms.add(key.substring(serviceType.length() + 11));
				}
			}
		}
		return algorithms;
	}
	
	public static void main(String[] args) {
		String pass2 = decrypt256("/S7IZv/w/A/4HxJiqFn74R6LGnta7qrlTKmhwsKMb+o=");
		String pass3 = decrypt256("ewIXkprjDs6M6X9flAYmQCvrvqmiwL3lisRtrdxjWEo=");
		System.out.println(pass2);
		System.out.println(pass3);
		System.out.println(pass2);
		String pass = decrypt("udei1KpHlpoTSiEnmQ2a5A==");
		System.out.println(pass);
	}
}
