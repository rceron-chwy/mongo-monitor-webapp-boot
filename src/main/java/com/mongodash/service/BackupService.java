package com.mongodash.service;

import com.mongodash.model.Backup;

public interface BackupService extends BaseService<Backup> {

	void run(Integer backupId);
	
	void run(Backup backup);
	
}
