package com.kusoduck.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtils {
	private static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

	private static String arg = System.getProperty("encrypt.arg");

	private EncryptUtils() {

	}

	public static String encrypt(String password) {
		logger.debug("start encrypt");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(arg);
		String encryptedPassword = encryptor.encrypt(password);
		logger.debug("end encrypt");
		return encryptedPassword;
	}

	public static String decrypt(String encryptedMessage) {
		logger.debug("start decrypt");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(arg);
		String password = encryptor.decrypt(encryptedMessage);
		logger.debug("end decrypt");
		return password;
	}
}
