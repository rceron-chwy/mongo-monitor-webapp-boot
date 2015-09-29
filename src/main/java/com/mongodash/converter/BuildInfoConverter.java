package com.mongodash.converter;

import org.bson.BasicBSONObject;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.model.BuildInfo;

public class BuildInfoConverter {

	public static BuildInfo fromDBObject(BasicBSONObject object) {
		BuildInfo buildInfo = new BuildInfo();
		if(object.containsField(COMMON_FIELDS._id.name())) {		
			buildInfo.setId(object.getInt(COMMON_FIELDS._id.name()));
		}
		buildInfo.setVersion(object.getString("version"));
		buildInfo.setSysInfo(object.getString("sysInfo"));
		buildInfo.setAllocator(object.getString("allocator"));
		buildInfo.setLoaderFlags(object.getString("loaderFlags"));
		buildInfo.setJavascriptEngine(object.getString("javascriptEngine"));
		buildInfo.setBits(object.getInt("bits"));
		buildInfo.setDebug(object.getBoolean("debug"));
		buildInfo.setMaxBsonObjectSize(object.getInt("maxBsonObjectSize"));
		return buildInfo;
	}
}
