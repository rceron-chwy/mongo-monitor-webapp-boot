package com.mongodash.jobs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.mongodash.model.Report;
import com.mongodash.service.ReportService;

public class ReportJob {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportJob.class);

	@Autowired
	private ReportService reportService;
	
	@Scheduled(cron="0 0 5 * * *")
	public void run() {
		logger.debug("Executing report job");
		
		List<Report> reports = reportService.list();
		if(reports != null) {
			for(Report report : reports) {
				if(report.isEnabled()) {
					logger.debug("Executing backup id {}", report.getId());
					reportService.runReport(report);
				}
			}
		}
		
		logger.debug("Finished executing report jobs");		
	}
	
}
