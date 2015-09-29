package com.mongodash.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.bson.BasicBSONObject;
import org.springframework.stereotype.Component;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Database;
import com.mongodash.model.DatabaseInfo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class DatabaseInfoDBObjectMapper implements DBObjectMapper<DatabaseInfo> {

	@Override
	public DatabaseInfo mapDBObject(BasicDBObject object) {
		DatabaseInfo databases = new DatabaseInfo();
		if (object.containsField(COMMON_FIELDS._id.name())) {
			databases.setId(object.getInt(COMMON_FIELDS._id.name()));
		}
		databases.setTotalSize(object.getLong("totalSize"));
		if (object.containsField("databases")) {
			BasicDBList list = (BasicDBList) object.get("databases");
			if (list.size() > 0) {
				List<Database> dbs = new ArrayList<Database>();
				for (int i = 0; i < list.size(); i++) {
					BasicBSONObject obj = (BasicBSONObject) list.get(i);
					Database db = new Database();
					db.setName(obj.getString("name"));
					db.setSizeOnDisk(obj.getLong("sizeOnDisk"));
					db.setEmpty(obj.getBoolean("empty"));
					dbs.add(db);
				}
				databases.setDatabase(dbs);
			}
		}
		return databases;
	}

	@Override
	public DBObject mapObject(DatabaseInfo databases) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(COMMON_FIELDS._id.name(), databases.getId());
		dbObject.put("totalSize", databases.getTotalSize());
		if (databases.getDatabase() != null && databases.getDatabase().size() > 0) {
			BasicDBList list = new BasicDBList();
			for (Database database : databases.getDatabase()) {
				DBObject db = new BasicDBObject();
				db.put("name", database.getName());
				db.put("sizeOnDisk", database.getSizeOnDisk());
				db.put("empty", database.isEmpty());
				list.add(db);
			}
			dbObject.put("databases", list);
		}
		return dbObject;
	}

}
