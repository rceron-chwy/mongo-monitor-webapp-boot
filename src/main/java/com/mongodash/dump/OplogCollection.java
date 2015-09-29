package com.mongodash.dump;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.io.IOException;

import org.apache.commons.lang3.Validate;


public class OplogCollection {
	
	private static final String MASTER_OPLOG = "$main";
	private static final String REPLICA_OPLOG = "rs";
	private static final String IS_MASTER_FIELD = "ismaster";

	private DB admin;
	private DB local;

	public OplogCollection(DB admin, DB local) {
		Validate.notNull(admin);
		Validate.notNull(local);

		this.admin = admin;
		this.local = local;
	}

	public DBCollection getOplogCollection() throws IOException {
		
		String oplogCollectionName = DumpConstants.OPLOG + ".";
		oplogCollectionName += isMaster() ? MASTER_OPLOG : REPLICA_OPLOG;

		return local.getCollection(oplogCollectionName);
	}

	private boolean isMaster() throws IOException {
		// Validate we are on master or replica
		CommandResult commandResult = admin.command(new BasicDBObject(IS_MASTER_FIELD, 1));
		boolean isMaster = commandResult.getBoolean(IS_MASTER_FIELD, false);

		// Replica set member
		if (commandResult.containsField("hosts")) {
			return false;
		} else {
			if (!isMaster) {
				throw new IOException("oplog mode is only supported on master or replica set member");
			}
			return true;
		}

	}

}