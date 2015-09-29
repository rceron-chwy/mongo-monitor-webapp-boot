package com.mongodash.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utility class that simply creates an error code to be used along with exceptions, helping the track of errors in log files.  
 * 
 * @author Rafael Ceron
 */
public class ExceptionUtil {

	
	public static String getExceptionId() {

		StringBuilder exId = new StringBuilder();
		try {
			exId.append(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			exId.append("unknownHostname");
		}
		exId.append('.');
		exId.append(System.currentTimeMillis());

		return exId.toString();
	}
	
	/**
	 * Checks whether the throwable (or its cause) is an instance of the class recursively
	 * 
	 * @param t
	 * @param clazz 
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isCausedBy(Throwable t, Class... clazz){
		boolean isInstanceOf = false;
		
		for(Class c : clazz){
			isInstanceOf = t.getClass().equals(c);
			if(isInstanceOf) break;
		}
		
		if(!isInstanceOf && t.getCause() != null){
			isInstanceOf = isCausedBy(t.getCause(), clazz);
		}
		
		return isInstanceOf;
	}
}