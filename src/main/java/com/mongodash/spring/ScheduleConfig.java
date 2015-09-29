package com.mongodash.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodash.jobs.BackupJob;
import com.mongodash.jobs.ReportJob;

@Configuration
@EnableScheduling
public class ScheduleConfig {

	@Bean
	public ReportJob reportJob() {
		return new ReportJob();
	}
	
	@Bean
	public BackupJob backupJob() {
		return new BackupJob();
	}
	
}
