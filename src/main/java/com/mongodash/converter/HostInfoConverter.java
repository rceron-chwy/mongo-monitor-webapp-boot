package com.mongodash.converter;

import org.bson.BasicBSONObject;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.model.HostInfo;
import com.mongodash.util.JsonUtil;
import com.mongodb.BasicDBObject;

public class HostInfoConverter {
	
	public static HostInfo fromDBObject(BasicDBObject object, boolean isFromCommand) {
		System.out.println(object);
		HostInfo hostInfo = new HostInfo();
		if(isFromCommand) {
			BasicBSONObject system = (BasicDBObject) object.get("system");		
			hostInfo.setHostname(system.getString("hostname"));
			hostInfo.setCpuAddrSize(system.getInt("cpuAddrSize"));
			hostInfo.setMemSizeMB(system.getInt("memSizeMB"));
			hostInfo.setNumCores(system.getInt("numCores"));
			hostInfo.setCpuArch(removeNonChar(system.getString("cpuArch")));
			
			BasicDBObject os = (BasicDBObject) object.get("os");
			hostInfo.setOsName(os.getString("name"));
			hostInfo.setOsType(os.getString("type"));
			hostInfo.setOsVersion(removeNonChar(os.getString("version")));
			
			BasicDBObject extra = (BasicDBObject) object.get("extra");
			hostInfo.setVersionString(removeNonChar(extra.getString("versionString")));
			hostInfo.setModel(removeNonChar(extra.getString("model")));
			hostInfo.setPhysicalCores(JsonUtil.getInt(extra, "physicalCores"));
			hostInfo.setCpuFrequencyMHz(extra.getString("cpuFrequencyMHz"));
			hostInfo.setCpuString(removeNonChar(extra.getString("cpuString")));
			hostInfo.setPageSize(extra.getInt("pageSize"));
		}
		else {
			if(object.containsField(COMMON_FIELDS._id.name())) {
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
		}
		return hostInfo;	
	}
	
	private static String removeNonChar(String x) {
		if(x != null) {
			return x.replaceAll("[\u0000-\u001f]", "");
		}
		
		return null;
	}
	
}
