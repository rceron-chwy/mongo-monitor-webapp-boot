package com.mongodash.dump;

import com.mongodb.DBObject;

import java.io.File;
import java.io.IOException;

public abstract class DumpWriter {
	
	private String outputDirectory;
	private String database;

	public DumpWriter(String outputDirectory, String database) {
		this.outputDirectory = outputDirectory;
		this.database = database;
	}

	public DumpWriter(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getFilePath(String collection) {
		StringBuilder path = new StringBuilder(outputDirectory);
		path.append(File.separator);

		if (database != null) {
			path.append(database).append(File.separator);
		}
		
		path.append(collection).append(".").append(getExtension());
		return path.toString();
	}

	public abstract String getExtension();

	public abstract void writeObject(String collection, DBObject dbObject) throws IOException;

}