package com.mongodash.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.dao.BackupDao;
import com.mongodash.dump.DumpFormat;
import com.mongodash.dump.MongoDump;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.Backup;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Server;
import com.mongodash.service.BackupService;
import com.mongodash.service.ServerService;

@Service
public class BackupServiceImpl implements BackupService {

	@Autowired
	BackupDao backupDao;
	
	@Autowired
	ServerService serverService;
	
	@Override
	public List<Backup> list() {
		return backupDao.list();
	}

	@Override
	public Backup getById(Integer id) {
		return backupDao.getById(id);
	}

	@Override
	public void save(Backup t) {
		backupDao.save(t);
		MongoEventBus.post(new Notification(NotificationType.BACKUP_CREATED, t.getName()));
	}

	@Override
	public void update(Backup t) {
		backupDao.update(t);
		MongoEventBus.post(new Notification(NotificationType.BACKUP_UPDATED, t.getName()));
	}

	@Override
	public void remove(Backup t) {
		backupDao.remove(t);
		MongoEventBus.post(new Notification(NotificationType.BACKUP_REMOVED, t.getName()));
	}

	@Override
	public void removeById(Integer id) {
		Backup backup = getById(id);
		if(backup != null) {
			remove(backup);
		}
	}

	@Override
	public void run(Integer backupId) {
		
		final Backup backup = getById(backupId);
		if(backup == null) return;
		
		new Thread( new Runnable() {

			@Override
			public void run() {
				Server server = serverService.getById(backup.getServerId());
				MongoDump mongoDump = new MongoDump(server, DumpFormat.valueOf(backup.getDumpFormat()));
				mongoDump.setZip(backup.isCompressFolder());
				mongoDump.setOplog(backup.isIncludeOpLog());
				
				for(String database : backup.getDatabases()) {
					try {
						mongoDump.dump(backup.getOutputFolder()
								+ File.separator + backup.getName(), database, database + "_dump");
					}
					catch(IOException e) {
						
					}
				}		
			}
			
		}).start();
		
		MongoEventBus.post(new Notification(NotificationType.BACKUP_EXECUTED, backup.getName()));
		
	}

	@Override
	public void run(Backup backup) {
		run(backup.getId());		
	}

}
