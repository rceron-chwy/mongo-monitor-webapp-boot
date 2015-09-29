package com.mongodash.util;

import java.util.Date;

import com.mongodb.BasicDBObject;

public class JsonUtil {

	public static String getString(BasicDBObject obj, String field) {
		if(obj.containsField(field)) {
			return obj.getString(field);
		}
		
		return null;
	}
	
	public static Integer getInt(BasicDBObject obj, String field) {
		if(obj.containsField(field)) {
			return obj.getInt(field);
		}
		
		return null;
	}
	
	public static Long getLong(BasicDBObject obj, String field) {
		if(obj.containsField(field)) {
			return obj.getLong(field);
		}
		
		return null;
	}	
	
	public static Boolean getBoolean(BasicDBObject obj, String field) {
		if(obj.containsField(field)) {
			return obj.getBoolean(field);
		}
		
		return null;
	}
	
	public static Date getDate(BasicDBObject obj, String field) {
		if(obj.containsField(field)) {
			return obj.getDate(field);
		}
		
		return null;
	}		
	
}
