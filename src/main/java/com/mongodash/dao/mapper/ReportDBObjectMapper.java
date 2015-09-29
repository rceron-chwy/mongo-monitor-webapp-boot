package com.mongodash.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mongodash.Config;
import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Report;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class ReportDBObjectMapper implements DBObjectMapper<Report> {

	@Override
	public Report mapDBObject(BasicDBObject object) {
		Report report = new Report();
		report.setId(object.getInt(Config.COMMON_FIELDS._id.name()));
		report.setName(object.getString(Config.COMMON_FIELDS.name.name()));
		report.setEnabled(object.getBoolean(Config.COMMON_FIELDS.enabled.name()));
		report.setReportPeriod(object.getString(Report.fields.reportPeriod.name()));
		report.setEmailEnabled(object.getBoolean(Report.fields.emailEnabled.name()));
		report.setRecipientList(object.getString(Report.fields.recipientList.name()));
		report.setLastExecutionDate(object.getDate(Report.fields.lastExecutionDate.name()));
		report.setGroupBy(object.getString(Report.fields.groupBy.name()));

		if (object.containsField(Report.fields.aggregationFields.name())) {
			BasicDBList list = (BasicDBList) object.get(Report.fields.aggregationFields.name());
			List<String> aggregationFields = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				aggregationFields.add((String) list.get(i));
			}
			report.setAggregationFields(aggregationFields);
		}

		if (object.containsField(Report.fields.aggregationOperators.name())) {
			BasicDBList list = (BasicDBList) object.get(Report.fields.aggregationOperators.name());
			List<String> aggregationOperators = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				aggregationOperators.add((String) list.get(i));
			}
			report.setAggregationOperators(aggregationOperators);
		}

		if (object.containsField(Report.fields.serverIds.name())) {
			BasicDBList list = (BasicDBList) object.get(Report.fields.serverIds.name());
			List<String> serverIds = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				serverIds.add((String) list.get(i));
			}
			report.setServerIds(serverIds);
		}

		return report;
	}

	@Override
	public DBObject mapObject(Report object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(COMMON_FIELDS._id.name(), object.getId());
		dbObject.put(COMMON_FIELDS.name.name(), object.getName());
		dbObject.put(COMMON_FIELDS.enabled.name(), object.isEnabled());
		dbObject.put(Report.fields.reportPeriod.name(), object.getReportPeriod());
		dbObject.put(Report.fields.emailEnabled.name(), object.isEmailEnabled());
		dbObject.put(Report.fields.recipientList.name(), object.getRecipientList());
		dbObject.put(Report.fields.lastExecutionDate.name(), object.getLastExecutionDate());
		dbObject.put(Report.fields.groupBy.name(), object.getGroupBy());
		if (object.getAggregationFields() != null && object.getAggregationFields().size() > 0) {
			BasicDBList list = new BasicDBList();
			list.addAll(object.getAggregationFields());
			dbObject.put(Report.fields.aggregationFields.name(), list);
		}
		if (object.getAggregationOperators() != null && object.getAggregationOperators().size() > 0) {
			BasicDBList list = new BasicDBList();
			list.addAll(object.getAggregationOperators());
			dbObject.put(Report.fields.aggregationOperators.name(), list);
		}
		if (object.getServerIds() != null && object.getServerIds().size() > 0) {
			BasicDBList list = new BasicDBList();
			list.addAll(object.getServerIds());
			dbObject.put(Report.fields.serverIds.name(), list);
		}
		return dbObject;
	}

}
