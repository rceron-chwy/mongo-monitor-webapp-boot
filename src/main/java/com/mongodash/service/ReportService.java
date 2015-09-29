package com.mongodash.service;

import com.mongodash.model.Report;

public interface ReportService extends BaseService<Report> {

	void runReport(Integer reportId);
	
	void runReport(Report report);
	
}
