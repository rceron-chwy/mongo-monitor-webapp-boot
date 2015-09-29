package com.mongodash.dao.mapper;

import org.springframework.stereotype.Component;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.HostInfo;
import com.mongodash.util.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class HostInfoDBObjectMapper implements DBObjectMapper<HostInfo> {

	@Override
	public HostInfo mapDBObject(BasicDBObject object) {
		HostInfo hostInfo = new HostInfo();
		if (object.containsField(COMMON_FIELDS._id.name())) {
			hostInfo.setId(object.getInt(COMMON_FIELDS._id.name()));
		}
		hostInfo.setHostname(object.getString("hostname"));
		hostInfo.setCpuAddrSize(object.getInt("cpuAddrSize"));
		hostInfo.setMemSizeMB(object.getInt("memSizeMB"));
		hostInfo.setNumCores(object.getInt("numCores"));
		hostInfo.setCpuArch(object.getString("cpuArch"));
		hostInfo.setOsName(object.getString("osName"));
		hostInfo.setOsType(object.getString("osType"));
		hostInfo.setOsVersion(object.getString("osVersion"));
		hostInfo.setVersionString(object.getString("versionString"));
		hostInfo.setModel(object.getString("model"));
		hostInfo.setPhysicalCores(JsonUtil.getInt(object, "physicalCores"));
		hostInfo.setCpuFrequencyMHz(object.getString("cpuFrequencyMHz"));
		hostInfo.setCpuString(object.getString("cpuString"));
		hostInfo.setPageSize(object.getInt("pageSize"));
		return hostInfo;
	}

	@Override
	public DBObject mapObject(HostInfo hostInfo) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(COMMON_FIELDS._id.name(), hostInfo.getId());
		dbObject.put("hostname", hostInfo.getHostname());
		dbObject.put("cpuAddrSize", hostInfo.getCpuAddrSize());
		dbObject.put("memSizeMB", hostInfo.getMemSizeMB());
		dbObject.put("numCores", hostInfo.getNumCores());
		dbObject.put("cpuArch", hostInfo.getCpuArch());
		dbObject.put("osName", hostInfo.getOsName());
		dbObject.put("osType", hostInfo.getOsType());
		dbObject.put("osVersion", hostInfo.getOsVersion());
		dbObject.put("versionString", hostInfo.getVersionString());
		dbObject.put("model", hostInfo.getModel());
		dbObject.put("physicalCores", hostInfo.getPhysicalCores());
		dbObject.put("cpuFrequencyMHz", hostInfo.getCpuFrequencyMHz());
		dbObject.put("cpuString", hostInfo.getCpuString());
		dbObject.put("pageSize", hostInfo.getPageSize());
		return dbObject;
	}
}
