package com.mongodash.dump;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.bson.types.BSONTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodash.model.Server;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDump extends AbstractMongoUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoDump.class);
	
	public static final String TIMESTAMP_FORMAT = "'.'yyyy-MM-dd-HH-mm";

	private Server server;
	private DumpFormat dumpFormat;
	private MongoClient mongoClient;

	private boolean zip;
	private boolean oplog;
	private final Map<String, DB> dbs = new HashMap<String, DB>();
	private DBCollection oplogCollection;
	private BSONTimestamp oplogStart;
	
	public MongoDump(Server server, DumpFormat dumpFormat) {
		this.server = server;
		this.dumpFormat = dumpFormat;
	}

	public void dump(final String outputDirectory, final String database, 
			String outputName) throws IOException {
		
		logger.debug("Starting backup for database " + database);
		
		Validate.notNull(outputDirectory);
		Validate.notNull(outputName);
		Validate.notNull(database);

		mongoClient = new MongoClient(server.getHostname(), server.getPort());
		DB admin = mongoClient.getDB(DumpConstants.ADMIN_DB);
		if(server.getUsername() != null && server.getPassword() != null) {
			boolean isOk = admin.authenticate(server.getUsername(), 
					server.getPassword().toCharArray());
			
			if(!isOk) {
				mongoClient.close();
				return;
			}
		}

		initOplog(database);

		DB db = mongoClient.getDB(database);
		final Collection<String> collections = db.getCollectionNames();
		
		long startTime = System.currentTimeMillis();
		long totalRecords = 0;
		
		if (collections != null && collections.size() > 0) {
			
			final ExecutorService executor = Executors.newFixedThreadPool(collections.size());
			final DumpWriter dumpWriter = getDumpWriter(outputDirectory, outputName, dumpFormat);
			
			for (final String collectionName : collections) {
				final DBCollection dbCollection = db.getCollection(collectionName);
				final MongoDumpCollection dumpCollection = new MongoDumpCollection(dbCollection, dumpWriter);

				final Future<Long> future = executor.submit(dumpCollection);
				totalRecords += propagateException(future);
			}

			executor.shutdown();
			
			try {
				
				if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
					executor.shutdownNow();
				}

				if (oplog) {
					final ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
					final MongoDumpCollection dumpCollection = new MongoDumpCollection(oplogCollection, dumpWriter);
					dumpCollection.setName(DumpConstants.OPLOG);
					dumpCollection.addOption(Bytes.QUERYOPTION_OPLOGREPLAY);
					dumpCollection.addOption(Bytes.QUERYOPTION_SLAVEOK);
					final DBObject query = new BasicDBObject();
					query.put(DumpConstants.TIMESTAMP_FIELD, new BasicDBObject("$gt", oplogStart));
					// Filter only oplogs for given database
					query.put(DumpConstants.NAMESPACE_FIELD, DumpUtils.getNamespacePattern(database));
					dumpCollection.setQuery(query);
					final Future<Long> future = singleExecutor.submit(dumpCollection);
					propagateException(future);
				}

				if (zip) {
					final String dbDumpPath = outputDirectory + File.separator + outputName;
					final String outputFile = dbDumpPath + appendTimestamp();
					ZipUtils.zipDirectory(dbDumpPath, outputFile);
					FileUtils.deleteDirectory(new File(dbDumpPath));
				}
				
			} catch (final InterruptedException ie) {
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
		
		mongoClient.close();
		
		logger.debug("Backup for database {} finished", database);
		logger.debug("Total backup time in seconds [{}]", (System.currentTimeMillis() - startTime) / 1000);
		logger.debug("Total records exported [{}]", totalRecords);
		
	}

	private void initOplog(final String database) throws IOException {
		if (oplog) {
			
			oplogCollection = new OplogCollection(dbs.get(DumpConstants.ADMIN_DB), 
					dbs.get(DumpConstants.LOCAL_DB)).getOplogCollection();
			
			// Filter for oplogs for the given database
			final DBObject query = new BasicDBObject(DumpConstants.NAMESPACE_FIELD, 
					DumpUtils.getNamespacePattern(database));
			
			final DBCursor oplogCursor = oplogCollection.find(query);
			oplogCursor.sort(new BasicDBObject("$natural", -1));
			if (oplogCursor.hasNext()) {
				oplogStart = ((BSONTimestamp) oplogCursor.next().get("ts"));
			}
			
		}
	}
	
	private DumpWriter getDumpWriter(String outputDir, String database, DumpFormat dumpFormat) {
		if(dumpFormat == DumpFormat.BSON) {
			return new BsonDumpWriter(outputDir, database);
		}
		else {
			return new JsonDumpWriter(outputDir, database);
		}
	}

	private String appendTimestamp() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(new Date());
	}

	public void setZip(final boolean zip) {
		this.zip = zip;
	}

	public void setOplog(final boolean oplog) {
		this.oplog = oplog;
	}

	public void addDB(final DB db) {
		dbs.put(db.getName(), db);
	}
	
}