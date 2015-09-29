package com.mongodash.jobs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.mongodash.model.Backup;
import com.mongodash.service.BackupService;

public class BackupJob {
	
	private static final Logger logger = LoggerFactory.getLogger(BackupJob.class);
	
	@Autowired
	private BackupService backupService;
	
	@Scheduled(cron="0 0 2 * * *")
	public void run() {
		logger.debug("Executing backup job");
		
		List<Backup> backups = backupService.list();
		if(backups != null) {
			for(Backup backup : backups) {
				if(backup.isRunDaily()) {
					logger.debug("Executing backup id {}", backup.getId());
					backupService.run(backup);
				}
			}
		}
		logger.debug("Finished executing backup jobs");
	}
	
}
