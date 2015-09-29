/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mongodash.appender;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.status.ErrorStatus;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Logback {@link Appender} to write into MongoDB.
 * 
 * @author kofemann
 */
public class MongoDBAppender extends AppenderBase<LoggingEvent> {

	private Mongo _mongo;
	private String _dbHost;
	private int _dbPort;
	private String _dbName;
	private String _dbCollection;
	private int _dbRetainDataInSeconds;
	private boolean _dbEnableStacktrace;
	private DBCollection _collection;

	@Override
	public void start() {
		try {
			_mongo = new MongoClient(_dbHost, _dbPort);
			DB db = _mongo.getDB(_dbName);
			_collection = db.getCollection(_dbCollection);
			DBObject index = new BasicDBObject();
			index.put("ts", 1);
			index.put("expireAfterSeconds", _dbRetainDataInSeconds);
			_collection.ensureIndex(index);
		} catch (Exception e) {
			addStatus(new ErrorStatus("Failed to initialize MondoDB", this, e));
			return;
		}
		super.start();
	}

	public void setDbHost(String dbHost) {
		_dbHost = dbHost;
	}

	public void setDbPort(int dbPort) {
		_dbPort = dbPort;
	}

	public void setDbName(String dbName) {
		_dbName = dbName;
	}

	public void setDbCollection(String dbCollection) {
		_dbCollection = dbCollection;
	}

	public void setDbRetainDataInSeconds(int dbRetainDataInSeconds) {
		_dbRetainDataInSeconds = dbRetainDataInSeconds;
	}

	public void setDbEnableStacktrace(boolean dbEnableStacktrace) {
		this._dbEnableStacktrace = dbEnableStacktrace;
	}

	@Override
	public void stop() {
		_mongo.close();
		super.stop();
	}

	@Override
	protected void append(LoggingEvent e) {

		BasicDBObjectBuilder objectBuilder = BasicDBObjectBuilder.start().add("ts", new Date(e.getTimeStamp())).add("msg", e.getFormattedMessage())
				.add("level", e.getLevel().toString()).add("logger", e.getLoggerName()).add("thread", e.getThreadName());
		if (e.hasCallerData()) {
			StackTraceElement st = e.getCallerData()[0];
			String callerData = String.format("%s.%s:%d", st.getClassName(), st.getMethodName(), st.getLineNumber());
			objectBuilder.add("caller", callerData);
		}
		Map<String, String> mdc = e.getMdc();
		if (mdc != null && !mdc.isEmpty()) {
			objectBuilder.add("mdc", new BasicDBObject(mdc));
		}
		if (e.getThrowableProxy() != null && _dbEnableStacktrace) {

			String tpAsString = ThrowableProxyUtil.asString(e.getThrowableProxy()); // the
																					// stack
																					// trace
																					// basically
			List<String> stackTrace = Arrays.asList(tpAsString.replace("\t", "").split(CoreConstants.LINE_SEPARATOR));
			if (stackTrace.size() > 0) {
				objectBuilder.add("exception", stackTrace.get(0));
			}
			if (stackTrace.size() > 1) {
				objectBuilder.add("stacktrace", stackTrace.subList(1, stackTrace.size()));
			}
		}
		_collection.insert(objectBuilder.get());
	}
}