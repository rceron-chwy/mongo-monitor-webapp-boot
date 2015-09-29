package com.mongodash.dao.mapper;

import org.springframework.stereotype.Component;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.BuildInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class BuildInfoDBObjectMapper implements DBObjectMapper<BuildInfo> {

	@Override
	public BuildInfo mapDBObject(BasicDBObject object) {
		BuildInfo buildInfo = new BuildInfo();
		if (object.containsField(COMMON_FIELDS._id.name())) {
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

	@Override
	public DBObject mapObject(BuildInfo buildInfo) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(COMMON_FIELDS._id.name(), buildInfo.getId());
		dbObject.put("version", buildInfo.getVersion());
		dbObject.put("sysInfo", buildInfo.getSysInfo());
		dbObject.put("allocator", buildInfo.getAllocator());
		dbObject.put("loaderFlags", buildInfo.getLoaderFlags());
		dbObject.put("javascriptEngine", buildInfo.getJavascriptEngine());
		dbObject.put("bits", buildInfo.getBits());
		dbObject.put("debug", buildInfo.isDebug());
		dbObject.put("maxBsonObjectSize", buildInfo.getMaxBsonObjectSize());
		return dbObject;
	}
}
