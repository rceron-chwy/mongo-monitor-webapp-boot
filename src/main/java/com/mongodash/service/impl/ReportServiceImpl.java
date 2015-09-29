package com.mongodash.service.impl;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.Config;
import com.mongodash.dao.ReportDao;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.AggregationGroup;
import com.mongodash.model.AggregationRange;
import com.mongodash.model.AggregationType;
import com.mongodash.model.AlertField;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Report;
import com.mongodash.service.ReportService;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportDao reportDao;

	@Override
	public List<Report> list() {
		return reportDao.list();
	}

	@Override
	public Report getById(Integer id) {
		return reportDao.getById(id);
	}

	@Override
	public void save(Report t) {
		reportDao.save(t);
		MongoEventBus.post(new Notification(NotificationType.REPORT_CREATED, t.getName()));
	}

	@Override
	public void update(Report t) {
		reportDao.update(t);
		MongoEventBus.post(new Notification(NotificationType.REPORT_UPDATED, t.getName()));
	}

	@Override
	public void remove(Report t) {
		reportDao.remove(t);
		MongoEventBus.post(new Notification(NotificationType.REPORT_REMOVED, t.getName()));
	}

	@Override
	public void removeById(Integer id) {
		Report report = getById(id);
		if(report != null) {
			remove(report);
		}
	}

	@Override
	public void runReport(Integer reportId) {

		Report report = getById(reportId);
		if (report == null) return;

		DBObject projection = createProjection(report);
		DBObject match = createMatch(report);
		DBObject group = createGroup(report);
		DBObject sort = createSort(report);

		System.out.println("db.serverStats.aggregate(");
		DBObject projectionObj = new BasicDBObject("$project", projection);
		System.out.println(projectionObj + ",");
		DBObject matchObj = new BasicDBObject("$match", match);
		System.out.println(matchObj + ",");
		DBObject groupObj = new BasicDBObject("$group", group);
		System.out.println(groupObj + ",");
		DBObject sortObj = new BasicDBObject("$sort", sort);
		System.out.println(sortObj);
		System.out.println(")");

		AggregationOutput output = reportDao.runReport(projectionObj, matchObj, groupObj, sortObj);
		System.out.println(output);
		
		MongoEventBus.post(new Notification(NotificationType.REPORT_EXECUTED, report.getName()));
	}

	private DBObject createProjection(Report report) {

		DBObject projection = new BasicDBObject();
		projection.put(Config.COMMON_FIELDS._id.name(), 1);
		projection.put("server", 1);
		projection.put("epoch", 1);
		projection.put(AggregationGroup.DAY.name().toLowerCase(), new BasicDBObject("$dayOfMonth", "$created"));
		projection.put("month", new BasicDBObject("$month", "$created"));
		projection.put("year", new BasicDBObject("$year", "$created"));
		projection.put(AggregationGroup.HOUR.name().toLowerCase(), new BasicDBObject("$hour", "$created"));
		projection.put(AggregationGroup.MINUTE.name().toLowerCase(), new BasicDBObject("$minute", "$created"));

		for (String field : report.getAggregationFields()) {
			String f = AlertField.valueOf(field).getField();
			projection.put(f, 1);
		}

		return projection;
	}

	private DBObject createMatch(Report report) {

		DBObject match = new BasicDBObject();

		if (report.getServerIds() != null && report.getServerIds().size() > 0) {
			BasicDBList ids = new BasicDBList();
			for (String id : report.getServerIds()) {
				ids.add(Integer.parseInt(id));
			}
			match.put("server", new BasicDBObject("$in", ids));
		}

		AggregationRange ar = AggregationRange.valueOf(report.getReportPeriod());
		GregorianCalendar gc = new GregorianCalendar();

		switch (ar) {
		case LASTDAY:
			gc.add(GregorianCalendar.DAY_OF_MONTH, -1);
			break;
		case MONTH:
			gc.add(GregorianCalendar.MONTH, -1);
			break;
		case WEEK:
			gc.add(GregorianCalendar.DAY_OF_MONTH, -7);
			break;
		case TODAY:

			break;
		}

		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		gc.set(GregorianCalendar.MILLISECOND, 0);
		match.put("epoch", new BasicDBObject("$gte", gc.getTimeInMillis() / 1000));

		return match;

	}

	private DBObject createGroup(Report report) {

		DBObject group = new BasicDBObject();

		// Setting up the field _id for the group
		BasicDBObject _id = new BasicDBObject();
		_id.put("server", "$server");
		AggregationGroup ag = AggregationGroup.valueOf(report.getGroupBy());
		switch (ag) {
		case MONTH:
			_id.put(AggregationGroup.MONTH.name().toLowerCase(), "$" + AggregationGroup.MONTH.name().toLowerCase());
			break;
		case DAY:
			_id.put(AggregationGroup.MONTH.name().toLowerCase(), "$" + AggregationGroup.MONTH.name().toLowerCase());
			_id.put(AggregationGroup.DAY.name().toLowerCase(), "$" + AggregationGroup.DAY.name().toLowerCase());
			break;
		case HOUR:
			_id.put(AggregationGroup.MONTH.name().toLowerCase(), "$" + AggregationGroup.MONTH.name().toLowerCase());
			_id.put(AggregationGroup.DAY.name().toLowerCase(), "$" + AggregationGroup.DAY.name().toLowerCase());
			_id.put(AggregationGroup.HOUR.name().toLowerCase(), "$" + AggregationGroup.HOUR.name().toLowerCase());
			break;
		case MINUTE:
			_id.put(AggregationGroup.MONTH.name().toLowerCase(), "$" + AggregationGroup.MONTH.name().toLowerCase());
			_id.put(AggregationGroup.DAY.name().toLowerCase(), "$" + AggregationGroup.DAY.name().toLowerCase());
			_id.put(AggregationGroup.HOUR.name().toLowerCase(), "$" + AggregationGroup.HOUR.name().toLowerCase());
			_id.put(AggregationGroup.MINUTE.name().toLowerCase(), "$" + AggregationGroup.MINUTE.name().toLowerCase());
			break;
		}
		group.put(Config.COMMON_FIELDS._id.name(), _id);
		group.put("total", new BasicDBObject("$sum", 1));

		// Setting up remaining fields and aggregation operators
		for (String field : report.getAggregationFields()) {
			for (String operation : report.getAggregationOperators()) {
				String f = AlertField.valueOf(field).getField();
				String o = AggregationType.valueOf(operation).name().toLowerCase();
				group.put(o + "_" + f, new BasicDBObject("$" + o.toLowerCase(), "$" + f));
			}
		}

		return group;
	}

	private DBObject createSort(Report report) {

		DBObject sort = new BasicDBObject();
		sort.put("_id.server", 1);
		AggregationGroup ag = AggregationGroup.valueOf(report.getGroupBy());
		switch (ag) {
		case MONTH:
			sort.put("_id." + AggregationGroup.MONTH.name().toLowerCase(), 1);
			break;
		case DAY:
			sort.put("_id." + AggregationGroup.MONTH.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.DAY.name().toLowerCase(), 1);
			break;
		case HOUR:
			sort.put("_id." + AggregationGroup.MONTH.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.DAY.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.HOUR.name().toLowerCase(), 1);
			break;
		case MINUTE:
			sort.put("_id." + AggregationGroup.MONTH.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.DAY.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.HOUR.name().toLowerCase(), 1);
			sort.put("_id." + AggregationGroup.MINUTE.name().toLowerCase(), 1);
			break;
		}

		return sort;

	}

	@Override
	public void runReport(Report report) {
		runReport(report.getId());
	}

}
