package com.mongodash.model;

import java.util.Date;
import java.util.List;

public class Report {
	
	public static enum fields {
		_id, enabled, name, reportPeriod, aggregationFields, aggregationOperators, serverIds,
		groupBy, emailEnabled, recipientList, lastExecutionDate;
	}


	private Integer id;
	private boolean enabled;
	private String name;
	private String reportPeriod;
	private List<String> aggregationFields;
	private List<String> aggregationOperators;
	private List<String> serverIds;
	private String groupBy;
	private boolean emailEnabled;
	private String recipientList;
	private Date lastExecutionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAggregationFields() {
		return aggregationFields;
	}

	public void setAggregationFields(List<String> aggregationFields) {
		this.aggregationFields = aggregationFields;
	}

	public List<String> getAggregationOperators() {
		return aggregationOperators;
	}

	public void setAggregationOperators(List<String> aggregationOperators) {
		this.aggregationOperators = aggregationOperators;
	}

	public String getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(String recipientList) {
		this.recipientList = recipientList;
	}

	public boolean isEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public String getReportPeriod() {
		return reportPeriod;
	}

	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public Date getLastExecutionDate() {
		return lastExecutionDate;
	}

	public void setLastExecutionDate(Date lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}

	public List<String> getServerIds() {
		return serverIds;
	}

	public void setServerIds(List<String> serverIds) {
		this.serverIds = serverIds;
	}

}
