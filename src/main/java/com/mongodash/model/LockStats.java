package com.mongodash.model;

public class LockStats implements Comparable<LockStats>{

	private String ns;
	private long w;
	private long r;
	
	public LockStats(String ns) {
		this.ns = ns;
	}

	public LockStats(String ns, LockStats previous, LockStats current) {
		this.ns = ns;
		this.r = current.getR() - previous.getR();
		this.w = current.getW() - previous.getW();
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public long getW() {
		return w;
	}

	public void setW(long w) {
		this.w = w;
	}

	public long getR() {
		return r;
	}

	public void setR(long r) {
		this.r = r;
	}
	
	public long total() {
		return w + r;
	}

	public int compareTo(LockStats o) {
		int total = total() < o.total() ? -1 
				: (total() > o.total()) ? 1 
				: 0;
		
		if(total == 0) {
			total = ns.compareTo(o.getNs());
		}
		
		return total;
	}

}