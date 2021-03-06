package ggitlab.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtils {
	private static final Integer KEY_STRETCHING_START = 0;
	private static final Integer KEY_STRETCHING_END = 1234;
	private static final Integer SALT_SIZE = 20;
	private static final String HASHING_ALGORITHM = "SHA-256";
	private static final String HEX_FORMAT = "%02x";

	public static String getEncrypted(String salt, byte[] password) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		for (int i = KEY_STRETCHING_START; i < KEY_STRETCHING_END; i++) {
			String temp = salt + bytesToString(password);
			messageDigest.update(temp.getBytes());
			password = messageDigest.digest();
		}
		return bytesToString(password);
	}

	public static String getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] temp = new byte[SALT_SIZE];
		random.nextBytes(temp);
		return bytesToString(temp);
	}

	private static String bytesToString(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for (byte b : temp) {
			sb.append(String.format(HEX_FORMAT, b));
		}
		return sb.toString();
	}
}
