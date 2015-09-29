package com.mongodash.model;

import java.util.Date;
import java.util.Map;

public class ServerStatus {

	private int insert;
	private int query;
	private int update;
	private int delete;
	private int getmore;
	private int command;

	private int flushes;
	private int mapped;
	private int vsize;
	private int res;
	private int nonmapped;

	private int faults;
	private double locked;
	private String lockedDB;
	private int misses;
	private int access;
	private int hits;
	private int conn;
	private int available;
	
	private int open;
	private int timedOut;

	private int qr;
	private int qw;
	private int ar;
	private int aw;
	
	private long bytesIn;
	private long bytesOut;	
	
	private int regular;
	private int warning;
	private int msg;
	private int user;

	private Long uptime;
	private Long uptimeMillis;
	private String time;

	private Map<String, LockStats> locks;
	private boolean mongos;
	
	private Integer serverId;
	
	private Date created;

	public int getInsert() {
		return insert;
	}

	public void setInsert(int insert) {
		this.insert = insert;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

	public int getDelete() {
		return delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}

	public int getGetmore() {
		return getmore;
	}

	public void setGetmore(int getmore) {
		this.getmore = getmore;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int getMapped() {
		return mapped;
	}

	public void setMapped(int mapped) {
		this.mapped = mapped;
	}

	public int getVsize() {
		return vsize;
	}

	public void setVsize(int vsize) {
		this.vsize = vsize;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public int getFaults() {
		return faults;
	}

	public void setFaults(int faults) {
		this.faults = faults;
	}

	public double getLocked() {
		return locked;
	}

	public void setLocked(double locked) {
		this.locked = locked;
	}

	public String getLockedDB() {
		return lockedDB;
	}

	public void setLockedDB(String lockedDB) {
		this.lockedDB = lockedDB;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}	

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getConn() {
		return conn;
	}

	public void setConn(int conn) {
		this.conn = conn;
	}

	public int getQr() {
		return qr;
	}

	public void setQr(int qr) {
		this.qr = qr;
	}

	public int getQw() {
		return qw;
	}

	public void setQw(int qw) {
		this.qw = qw;
	}

	public int getAr() {
		return ar;
	}

	public void setAr(int ar) {
		this.ar = ar;
	}

	public int getAw() {
		return aw;
	}

	public void setAw(int aw) {
		this.aw = aw;
	}

	public int getFlushes() {
		return flushes;
	}

	public void setFlushes(int flushes) {
		this.flushes = flushes;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getAvailable() {
		return available;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getTimedOut() {
		return timedOut;
	}

	public void setTimedOut(int timedOut) {
		this.timedOut = timedOut;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getNonmapped() {
		return nonmapped;
	}

	public void setNonmapped(int nonmapped) {
		this.nonmapped = nonmapped;
	}

	public Map<String, LockStats> getLocks() {
		return locks;
	}

	public void setLocks(Map<String, LockStats> locks) {
		this.locks = locks;
	}

	public Long getUptime() {
		return uptime;
	}

	public void setUptime(Long uptime) {
		this.uptime = uptime;
	}

	public Long getUptimeMillis() {
		return uptimeMillis;
	}

	public void setUptimeMillis(Long uptimeMillis) {
		this.uptimeMillis = uptimeMillis;
	}

	public boolean isMongos() {
		return mongos;
	}

	public void setMongos(boolean mongos) {
		this.mongos = mongos;
	}	
	
	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public long getBytesIn() {
		return bytesIn;
	}

	public void setBytesIn(long bytesIn) {
		this.bytesIn = bytesIn;
	}

	public long getBytesOut() {
		return bytesOut;
	}

	public void setBytesOut(long bytesOut) {
		this.bytesOut = bytesOut;
	}	

	public int getRegular() {
		return regular;
	}

	public void setRegular(int regular) {
		this.regular = regular;
	}

	public int getWarning() {
		return warning;
	}

	public void setWarning(int warning) {
		this.warning = warning;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ServerStatus [insert=" + insert + ", query=" + query
				+ ", update=" + update + ", delete=" + delete + ", getmore="
				+ getmore + ", command=" + command + ", flushes=" + flushes
				+ ", mapped=" + mapped + ", vsize=" + vsize + ", res=" + res
				+ ", nonmapped=" + nonmapped + ", faults=" + faults
				+ ", locked=" + lockedDB + ":" + locked + ", misses=" + misses
				+ ", conn=" + conn + ", available=" + available + ", qr=" + qr
				+ ", qw=" + qw + ", ar=" + ar + ", aw=" + aw + ", uptime="
				+ uptime + ", uptimeMillis=" + uptimeMillis + ", time=" + time
				+ ", mongos=" + mongos + "]";
	}

}
