package com.mongodash.dao.mapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.ServerStatus;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class ServerStatusDBObjectMapper implements DBObjectMapper<ServerStatus> {

	public static NumberFormat formatter = new DecimalFormat("#0.0");
	
	@Override
	public ServerStatus mapDBObject(BasicDBObject object) {
		ServerStatus serverStatus = new ServerStatus();
		serverStatus.setQuery(object.getInt("query"));
		serverStatus.setInsert(object.getInt("insert"));
		serverStatus.setUpdate(object.getInt("update"));
		serverStatus.setDelete(object.getInt("delete"));
		serverStatus.setCommand(object.getInt("command"));
		serverStatus.setGetmore(object.getInt("getmore"));
		serverStatus.setFlushes(object.getInt("flushes"));
		serverStatus.setVsize(object.getInt("vsize"));
		serverStatus.setRes(object.getInt("res"));
		serverStatus.setMapped(object.getInt("mapped"));
		serverStatus.setNonmapped(object.getInt("nonmapped"));
		serverStatus.setFaults(object.getInt("faults"));
		serverStatus.setLocked(object.getDouble("locked"));
		serverStatus.setLockedDB(object.getString("lockedDb"));
		serverStatus.setMisses(object.getInt("misses"));
		serverStatus.setQr(object.getInt("qr"));
		serverStatus.setQw(object.getInt("qw"));
		serverStatus.setAr(object.getInt("ar"));
		serverStatus.setAw(object.getInt("aw"));
		serverStatus.setConn(object.getInt("conn"));
		serverStatus.setAvailable(object.getInt("aval"));
		serverStatus.setBytesIn(object.getLong("bytesIn"));
		serverStatus.setBytesOut(object.getLong("bytesOut"));
		serverStatus.setHits(object.getInt("hits"));
		serverStatus.setAccess(object.getInt("access"));
		serverStatus.setOpen(object.getInt("open"));
		serverStatus.setTimedOut(object.getInt("timedout"));
		serverStatus.setRegular(object.getInt("regular"));
		serverStatus.setWarning(object.getInt("warning"));
		serverStatus.setMsg(object.getInt("msg"));
		serverStatus.setUser(object.getInt("user"));
		serverStatus.setTime(object.getString("time"));
		serverStatus.setServerId(object.getInt("server"));
		serverStatus.setCreated(object.getDate("created"));
		return serverStatus;
	}

	@Override
	public DBObject mapObject(ServerStatus serverStatus) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("server", serverStatus.getServerId());
		dbObject.put("query", serverStatus.getQuery());
		dbObject.put("insert", serverStatus.getInsert());
		dbObject.put("update", serverStatus.getUpdate());
		dbObject.put("delete", serverStatus.getDelete());
		dbObject.put("command", serverStatus.getCommand());
		dbObject.put("getmore", serverStatus.getGetmore());
		dbObject.put("flushes", serverStatus.getFlushes());
		dbObject.put("vsize", serverStatus.getVsize());
		dbObject.put("res", serverStatus.getRes());
		dbObject.put("mapped", serverStatus.getMapped());
		dbObject.put("nonmapped", serverStatus.getNonmapped());
		dbObject.put("faults", serverStatus.getFaults());
		dbObject.put("locked", getDouble(serverStatus.getLocked()));
		dbObject.put("lockedDb", serverStatus.getLockedDB());
		dbObject.put("misses", serverStatus.getMisses());
		dbObject.put("qr", serverStatus.getQr());
		dbObject.put("qw", serverStatus.getQw());
		dbObject.put("ar", serverStatus.getAr());
		dbObject.put("aw", serverStatus.getAw());
		dbObject.put("conn", serverStatus.getConn());
		dbObject.put("aval", serverStatus.getAvailable());
		dbObject.put("bytesIn", serverStatus.getBytesIn());
		dbObject.put("bytesOut", serverStatus.getBytesOut());
		dbObject.put("hits", serverStatus.getHits());
		dbObject.put("access", serverStatus.getAccess());
		dbObject.put("open", serverStatus.getOpen());
		dbObject.put("timedout", serverStatus.getTimedOut());
		dbObject.put("regular", serverStatus.getRegular());
		dbObject.put("warning", serverStatus.getWarning());
		dbObject.put("msg", serverStatus.getMsg());
		dbObject.put("user", serverStatus.getUser());		
		dbObject.put("time", serverStatus.getTime());
		dbObject.put("epoch", System.currentTimeMillis()/1000);
		dbObject.put("created", new Date());
		return dbObject;
	}
	
	private Double getDouble(double d) {
		return new Double(formatter.format(d));
	}
	
}
