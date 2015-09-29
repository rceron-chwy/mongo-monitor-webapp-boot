package com.mongodash.dao;

import com.mongodash.model.Report;
import com.mongodb.AggregationOutput;
import com.mongodb.DBObject;

public interface ReportDao extends BaseDao<Report> {

	AggregationOutput runReport(DBObject projection, DBObject match, DBObject group, DBObject sort);
	
}
