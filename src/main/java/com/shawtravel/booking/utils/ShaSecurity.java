package com.shawtravel.booking.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ShaSecurity {
	
	public static String hash256(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}
	
	public static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
	public static String hashMD5(String data)  {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data.getBytes());
			return bytesToHex(md.digest());
		}catch(NoSuchAlgorithmException e){
			System.out.println(e);
		}
		return null;
	} 

	public static String getRandomUUID(){
		return String.valueOf(UUID.randomUUID());
	}

}
