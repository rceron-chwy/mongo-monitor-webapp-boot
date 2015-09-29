package com.mongodash.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {

	public static String encrypt(String message) {

		try {
			// String message =
			// TextDateUtil.convertDateToString(Calendar.getInstance().getTime());
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest("Callerid4Zsdfty".getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			final byte[] plainTextBytes = message.getBytes("utf-8");
			final byte[] cipherText = cipher.doFinal(plainTextBytes);
			String str = new String(BASE64EncoderStream.encode(cipherText));
			return URLDecoder.decode(URLEncoder.encode(str));
			// return cipherText;
		} catch (Exception e) {
			return message;
		}
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String input) {

		try {

			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest("Callerid4Zsdfty".getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			byte[] message = BASE64DecoderStream.decode(input.getBytes());

			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, key, iv);

			// final byte[] encData = new
			// sun.misc.BASE64Decoder().decodeBuffer(message);
			final byte[] plainText = decipher.doFinal(message);
			String str = new String(plainText);
			return str;
		} catch (Exception e) {
			return input;
		}
	}

	public static void main(String[] args) throws Exception {
		String password = encrypt("!mongo");
		System.out.println(password);
		System.out.println(decrypt(password));
	}

}
