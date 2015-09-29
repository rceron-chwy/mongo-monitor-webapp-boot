package com.mongodash.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mongodash.Config;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Backup;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class BackupDBObjectMapper implements DBObjectMapper<Backup> {

	@Override
	public Backup mapDBObject(BasicDBObject object) {
		Backup backup = new Backup();
		backup.setId(object.getInt(Config.COMMON_FIELDS._id.name()));
		backup.setName(object.getString(Config.COMMON_FIELDS.name.name()));
		backup.setServerId(object.getInt(Backup.fields.serverId.name()));
		backup.setDumpFormat(object.getString(Backup.fields.dumpFormat.name()));
		backup.setCompressFolder(object.getBoolean(Backup.fields.compressFolder.name()));
		backup.setRunDaily(object.getBoolean(Backup.fields.runDaily.name()));
		backup.setIncludeOpLog(object.getBoolean(Backup.fields.includeOpLog.name()));
		backup.setNotifyOnFinish(object.getBoolean(Backup.fields.notifyOnFinish.name()));
		backup.setOutputFolder(object.getString(Backup.fields.outputFolder.name()));
		backup.setLastExecution(object.getDate(Backup.fields.lastExecution.name()));

		if (object.containsField(Backup.fields.databases.name())) {
			BasicDBList list = (BasicDBList) object.get(Backup.fields.databases.name());
			List<String> databases = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				databases.add((String) list.get(i));
			}
			backup.setDatabases(databases);
		}

		return backup;
	}

	@Override
	public DBObject mapObject(Backup backup) {
		DBObject object = new BasicDBObject();
		object.put(Config.COMMON_FIELDS._id.name(), backup.getId());
		object.put(Config.COMMON_FIELDS.name.name(), backup.getName());
		object.put(Backup.fields.serverId.name(), backup.getServerId());
		object.put(Backup.fields.dumpFormat.name(), backup.getDumpFormat());
		object.put(Backup.fields.compressFolder.name(), backup.isCompressFolder());
		object.put(Backup.fields.runDaily.name(), backup.isRunDaily());
		object.put(Backup.fields.includeOpLog.name(), backup.isIncludeOpLog());
		object.put(Backup.fields.notifyOnFinish.name(), backup.isNotifyOnFinish());
		object.put(Backup.fields.outputFolder.name(), backup.getOutputFolder());
		object.put(Backup.fields.lastExecution.name(), backup.getLastExecution());

		if (backup.getDatabases() != null && backup.getDatabases().size() > 0) {
			BasicDBList databases = new BasicDBList();
			for (String database : backup.getDatabases()) {
				databases.add(database);
			}
			object.put(Backup.fields.databases.name(), databases);
		}

		return object;
	}

}
