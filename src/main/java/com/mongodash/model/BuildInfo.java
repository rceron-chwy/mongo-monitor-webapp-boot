package com.mongodash.model;

public class BuildInfo {

	private Integer id;

	private String version;
	private String sysInfo;
	private String allocator;
	private String loaderFlags;
	private String javascriptEngine;
	private Integer bits;
	private boolean debug;
	private Integer maxBsonObjectSize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSysInfo() {
		return sysInfo;
	}

	public void setSysInfo(String sysInfo) {
		this.sysInfo = sysInfo;
	}

	public String getAllocator() {
		return allocator;
	}

	public void setAllocator(String allocator) {
		this.allocator = allocator;
	}

	public String getLoaderFlags() {
		return loaderFlags;
	}

	public void setLoaderFlags(String loaderFlags) {
		this.loaderFlags = loaderFlags;
	}

	public String getJavascriptEngine() {
		return javascriptEngine;
	}

	public void setJavascriptEngine(String javascriptEngine) {
		this.javascriptEngine = javascriptEngine;
	}

	public Integer getBits() {
		return bits;
	}

	public void setBits(Integer bits) {
		this.bits = bits;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Integer getMaxBsonObjectSize() {
		return maxBsonObjectSize;
	}

	public void setMaxBsonObjectSize(Integer maxBsonObjectSize) {
		this.maxBsonObjectSize = maxBsonObjectSize;
	}

	@Override
	public String toString() {
		return "BuildInfo [id=" + id + ", version=" + version + ", sysInfo=" + sysInfo + ", allocator=" + allocator + ", loaderFlags=" + loaderFlags
				+ ", javascriptEngine=" + javascriptEngine + ", bits=" + bits + ", debug=" + debug + ", maxBsonObjectSize=" + maxBsonObjectSize + "]";
	}

}
