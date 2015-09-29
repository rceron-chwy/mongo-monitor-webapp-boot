package com.mongodash.model;

public class HostInfo {

	private Integer id;

	/* System Info */
	private String hostname;
	private Integer cpuAddrSize;
	private Integer memSizeMB;
	private Integer numCores;
	private String cpuArch;

	/* OS Info */
	private String osType;
	private String osName;
	private String osVersion;

	/* Extra Info */
	private String versionString;
	private String model;
	private Integer physicalCores;
	private String cpuFrequencyMHz;
	private String cpuString;
	private Integer pageSize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getCpuAddrSize() {
		return cpuAddrSize;
	}

	public void setCpuAddrSize(Integer cpuAddrSize) {
		this.cpuAddrSize = cpuAddrSize;
	}

	public Integer getMemSizeMB() {
		return memSizeMB;
	}

	public void setMemSizeMB(Integer memSizeMB) {
		this.memSizeMB = memSizeMB;
	}

	public Integer getNumCores() {
		return numCores;
	}

	public void setNumCores(Integer numCores) {
		this.numCores = numCores;
	}

	public String getCpuArch() {
		return cpuArch;
	}

	public void setCpuArch(String cpuArch) {
		this.cpuArch = cpuArch;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getVersionString() {
		return versionString;
	}

	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getPhysicalCores() {
		return physicalCores;
	}

	public void setPhysicalCores(Integer physicalCores) {
		this.physicalCores = physicalCores;
	}

	public String getCpuFrequencyMHz() {
		return cpuFrequencyMHz;
	}

	public void setCpuFrequencyMHz(String cpuFrequencyMHz) {
		this.cpuFrequencyMHz = cpuFrequencyMHz;
	}

	public String getCpuString() {
		return cpuString;
	}

	public void setCpuString(String cpuString) {
		this.cpuString = cpuString;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "HostInfo [id=" + id + ", hostname=" + hostname + ", cpuAddrSize=" + cpuAddrSize + ", memSizeMB=" + memSizeMB + ", numCores=" + numCores
				+ ", cpuArch=" + cpuArch + ", osType=" + osType + ", osName=" + osName + ", osVersion=" + osVersion + ", versionString=" + versionString
				+ ", model=" + model + ", physicalCores=" + physicalCores + ", cpuFrequencyMHz=" + cpuFrequencyMHz + ", cpuString=" + cpuString + ", pageSize="
				+ pageSize + "]";
	}

}
