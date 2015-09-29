package com.mongodash.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.BasicBSONObject;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.model.Database;
import com.mongodash.model.DatabaseInfo;
import com.mongodb.BasicDBList;

public class DatabaseInfoConverter {
	
	public static DatabaseInfo fromDBObject(BasicBSONObject object) {	
		DatabaseInfo databases = new DatabaseInfo();
		if(object.containsField(COMMON_FIELDS._id.name())) {
			databases.setId(object.getInt(COMMON_FIELDS._id.name()));
		}
		databases.setTotalSize(object.getLong("totalSize"));
		if(object.containsField("databases")) {
			BasicDBList list = (BasicDBList) object.get("databases");
			if(list.size() > 0) {
				List<Database> dbs = new ArrayList<Database>();
				for(int i = 0; i < list.size(); i++) {
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
	
}
