package com.mongodash.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mongodash.exception.ServerStatusException;
import com.mongodash.model.LockStats;
import com.mongodash.model.ServerStatus;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ServerStatusConverter {

	public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);

	public static NumberFormat formatter = new DecimalFormat("#0.0");

	public static DBObject toDBObject(ServerStatus serverStatus) {
		DBObject dbObject = new BasicDBObject();
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
		dbObject.put("regular", serverStatus.getRegular());
		dbObject.put("warning", serverStatus.getWarning());
		dbObject.put("msg", serverStatus.getMsg());
		dbObject.put("user", serverStatus.getUser());
		dbObject.put("timedout", serverStatus.getTimedOut());
		dbObject.put("time", serverStatus.getTime());
		dbObject.put("epoch", System.currentTimeMillis()/1000);
		dbObject.put("created", new Date());
		return dbObject;
	}

	public static ServerStatus fromHTTPDBObject(DBObject object) throws ServerStatusException {
		return getStatusFromDBObject(object);
	}

	public static ServerStatus fromTCPDBObject(DBObject object) throws ServerStatusException {
		return getStatusFromDBObject(object);
	}

	private static ServerStatus getStatusFromDBObject(DBObject object) throws ServerStatusException {
		ServerStatus serverStatus = new ServerStatus();

		try {

			boolean isMongos = object.containsField("shardCursorType") || "mongos".equals(object.get("process").toString());

			serverStatus.setMongos(isMongos);
			serverStatus.setUptime(getLong(object, "uptime"));
			serverStatus.setUptimeMillis(getLong(object, "uptimeMillis"));

			DBObject temp = (DBObject) object.get("opcounters");
			serverStatus.setInsert(getInt(temp, "insert"));
			serverStatus.setQuery(getInt(temp, "query"));
			serverStatus.setUpdate(getInt(temp, "update"));
			serverStatus.setDelete(getInt(temp, "delete"));
			serverStatus.setGetmore(getInt(temp, "getmore"));
			serverStatus.setCommand(getInt(temp, "command"));

			temp = (DBObject) object.get("backgroundFlushing");
			serverStatus.setFlushes(getInt(temp, "flushes"));

			temp = (DBObject) object.get("mem");
			serverStatus.setVsize(getInt(temp, "virtual"));
			serverStatus.setRes(getInt(temp, "resident"));
			serverStatus.setNonmapped(getInt(temp, "virtual") - getInt(temp, "mapped"));
			if (!isMongos) {
				serverStatus.setMapped(getInt(temp, "mapped"));
			}

			temp = (DBObject) object.get("extra_info");
			serverStatus.setFaults(getInt(temp, "page_faults"));

			temp = (DBObject) object.get("indexCounters");
			if(temp != null) {
				if (temp.containsField("btree")) {
					temp = (DBObject) temp.get("btree");
				}
				serverStatus.setMisses(percent((Integer) temp.get("accesses"), (Integer) temp.get("misses")));
				serverStatus.setAccess(getInt(temp, "accesses"));
				serverStatus.setHits(getInt(temp, "hits"));

			}
			else {
				serverStatus.setMisses(0);
				serverStatus.setAccess(0);
				serverStatus.setHits(0);
			}
			
			temp = (DBObject) object.get("cursors");
			if(temp != null) {
				serverStatus.setOpen(getInt(temp, "open"));
				serverStatus.setTimedOut(getInt(temp, "timedOut"));
			}
			

			temp = (DBObject) ((DBObject) object.get("globalLock")).get("currentQueue");
			serverStatus.setQr(getInt(temp, "readers"));
			serverStatus.setQw(getInt(temp, "writers"));

			temp = (DBObject) ((DBObject) object.get("globalLock")).get("activeClients");
			serverStatus.setAr(getInt(temp, "readers"));
			serverStatus.setAw(getInt(temp, "writers"));

			temp = (DBObject) object.get("connections");
			serverStatus.setConn(getInt(temp, "current"));
			serverStatus.setAvailable(getInt(temp, "available"));

			if (!isMongos) {
				if (object.containsField("locks")) {
					temp = (DBObject) object.get("locks");
					Map<String, LockStats> locks = parseServerStatusLocks(temp);
					serverStatus.setLocks(locks);
				}
				else {
					temp = (DBObject) object.get("globalLock");
					serverStatus.setLocked(percent(getLong(temp, "totalTime"), getLong(temp, "lockTime")));
				}
			}
			
			if(object.containsField("network")) {
				temp = (DBObject) object.get("network");
				Long bytesIn = getLong(temp, "bytesIn");
				Long bytesOut = getLong(temp, "bytesOut");
				serverStatus.setBytesIn(bytesIn);
				serverStatus.setBytesOut(bytesOut);
			}
			
			if(object.containsField("asserts")) {
				temp = (DBObject) object.get("asserts");
				serverStatus.setRegular(getInt(temp, "regular"));
				serverStatus.setWarning(getInt(temp, "warning"));
				serverStatus.setMsg(getInt(temp, "msg"));
				serverStatus.setUser(getInt(temp, "user"));
			}

			serverStatus.setTime(sdf.format(new Date()));
		} catch (Exception e) {
			throw new ServerStatusException("Error getting server status for server: ");
		}

		return serverStatus;
	}

	public static ServerStatus getDiff(ServerStatus previous, ServerStatus current, int seconds) {

		ServerStatus serverStatus = new ServerStatus();
		serverStatus.setInsert(diff(previous.getInsert(), current.getInsert(), seconds));
		serverStatus.setQuery(diff(previous.getQuery(), current.getQuery(), seconds));
		serverStatus.setUpdate(diff(previous.getUpdate(), current.getUpdate(), seconds));
		serverStatus.setDelete(diff(previous.getDelete(), current.getDelete(), seconds));
		serverStatus.setGetmore(diff(previous.getGetmore(), current.getGetmore(), seconds));
		serverStatus.setCommand(diff(previous.getCommand(), current.getCommand(), seconds));
		serverStatus.setFlushes(diff(previous.getFlushes(), current.getFlushes(), seconds));

		serverStatus.setFaults(diff(previous.getFaults(), current.getFaults(), seconds));
		
		serverStatus.setBytesIn((long) diff(previous.getBytesIn(), current.getBytesIn(), seconds));
		serverStatus.setBytesOut((long) diff(previous.getBytesOut(), current.getBytesOut(), seconds));
		
		serverStatus.setHits(diff(previous.getHits(), current.getHits(), seconds));
		serverStatus.setAccess(diff(previous.getAccess(), current.getAccess(), seconds));
		serverStatus.setOpen(diff(previous.getOpen(), current.getOpen(), seconds));
		serverStatus.setTimedOut(diff(previous.getTimedOut(), current.getTimedOut(), seconds));
		serverStatus.setRegular(diff(previous.getRegular(), current.getRegular(), seconds));
		serverStatus.setWarning(diff(previous.getWarning(), current.getWarning(), seconds));
		serverStatus.setMsg(diff(previous.getMsg(), current.getMsg(), seconds));
		serverStatus.setUser(diff(previous.getUser(), current.getUser(), seconds));
		
		serverStatus.setMisses(current.getMisses());
		serverStatus.setUptime(current.getUptime());
		serverStatus.setUptimeMillis(current.getUptimeMillis());
		serverStatus.setMapped(current.getMapped());
		serverStatus.setVsize(current.getVsize());
		serverStatus.setRes(current.getRes());
		serverStatus.setNonmapped(current.getNonmapped());
		serverStatus.setQr(current.getQr());
		serverStatus.setQw(current.getQw());
		serverStatus.setAr(current.getAr());
		serverStatus.setAw(current.getAw());
		serverStatus.setConn(current.getConn());
		serverStatus.setAvailable(current.getAvailable());
		serverStatus.setTime(current.getTime());

		//Locks
		if (previous.getLocks() != null && current.getLocks() != null) {
			calculateFinalLock(previous, current, serverStatus, seconds);
		} else {
			serverStatus.setLocked(diff(previous.getLocked(), current.getLocked(), seconds));
		}

		return serverStatus;

	}
	
	public static double percent(double a, double b) {
		return (b * 100) / a;
	}

	public static double percent(long a, long b) {
		return (b * 100) / a;
	}

	public static int percent(int a, int b) {
		if (a == 0)
			return 0;
		return (b * 100) / a;
	}

	public static double diff(double a, double b, int seconds) {
		return (b - a) / seconds;
	}

	public static int diff(int a, int b, int seconds) {
		return (b - a) / seconds;
	}

	public static double diff(long a, long b, int seconds) {
		return (b - a) / seconds;
	}

	public static Integer getInt(DBObject object, String name) {
		
		if (object == null || !object.containsField(name))
			return new Integer("0");
		
		return (Integer) object.get(name);
	}

	public static Long getLong(DBObject object, String name) {
		
		if (object == null || !object.containsField(name))
			return new Long("0");

		Number n = (Number) object.get(name);

		if (n instanceof Double)
			return new Long(((Double) n).longValue());

		if (n instanceof Integer)
			return new Long((Integer) n);

		return (Long) object.get(name);
	}

	/*
	public static Double getDouble(DBObject object, String name) {
		if (!object.containsField(name))
			return 0d;
		return (Double) object.get(name);
	}
	*/

	public static Map<String, LockStats> parseServerStatusLocks(DBObject dbobject) {

		Map<String, LockStats> locks = null;
		if (dbobject.keySet().size() == 0) {
			return locks;
		}

		locks = new HashMap<String, LockStats>();
		for (String key : dbobject.keySet()) {
			DBObject obj = (DBObject) dbobject.get(key);
			LockStats ls = new LockStats(key);
			DBObject timeLockedMicros = (DBObject) obj.get("timeLockedMicros");
			ls.setR((getLong(timeLockedMicros, "r") + getLong(timeLockedMicros, "R")) / 1000);
			ls.setW((getLong(timeLockedMicros, "w") + getLong(timeLockedMicros, "W")) / 1000);
			locks.put(key, ls);
		}

		return locks;

	}

	public static List<LockStats> computeDiff(Map<String, LockStats> previous, Map<String, LockStats> current) {

		List<LockStats> locks = new ArrayList<LockStats>();

		for (String key : current.keySet()) {
			LockStats currentLockStats = current.get(key);
			LockStats previousLockStats = previous.get(key);
			if (previousLockStats != null) {
				LockStats ls = new LockStats(key, previousLockStats, currentLockStats);
				locks.add(ls);
			}
		}

		Collections.sort(locks);
		return locks;

	}

	private static void calculateFinalLock(ServerStatus previous, ServerStatus current, ServerStatus serverStatus, int seconds) {

		List<LockStats> locksDiff = computeDiff(previous.getLocks(), current.getLocks());

		if (locksDiff == null || locksDiff.size() == 0) {
			serverStatus.setLocked(0d);
		} else {
			double uptimeMillis = diff(previous.getUptimeMillis(), current.getUptimeMillis(), seconds) * seconds;
			LockStats last = locksDiff.get(locksDiff.size() - 1);
			double lockToReport = last.getW();

			if (!".".equals(last.getNs())) {
				for (LockStats ls : locksDiff) {
					if (".".equals(ls.getNs())) {
						lockToReport += ls.getW();
					}
				}
			}

			serverStatus.setLocked(100.0 * lockToReport / uptimeMillis);
			serverStatus.setLockedDB(last.getNs());

		}

	}
	
	private static Double getDouble(double d) {
		return new Double(formatter.format(d));
	}

}
