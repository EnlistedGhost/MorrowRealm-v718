package com.rs.utils;

import java.security.MessageDigest;

public class Encrypt {

	public static String encryptSHA1(String string) {
		String hash = null;
		try {
			hash = byteArrayToHexString(hash(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static byte[] hash(String x) throws Exception {
		MessageDigest string;
		string = java.security.MessageDigest.getInstance("SHA-1");
		string.reset();
		string.update(x.getBytes());
		return string.digest();
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer string = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				string.append('0');
			}
			string.append(Integer.toHexString(v));
		}
		return string.toString();
	}
}