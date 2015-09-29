package com.mongodash.dump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mongodb.DBObject;

public class JsonDumpWriter extends DumpWriter {

	private static final String JSON_EXTENSION = "json";

	public JsonDumpWriter(String outputDirectory, String database) {
		super(outputDirectory, database);
	}

	public JsonDumpWriter(String outputDirectory) {
		super(outputDirectory);
	}

	@Override
	public String getExtension() {
		return JSON_EXTENSION;
	}

	@Override
	public void writeObject(String collection, DBObject dbObject) throws IOException {
		
		FileOutputStream outputStream = null;
		File outputFile = new File(getFilePath(collection));
		outputFile.getParentFile().mkdirs();
		
		try {
			
			outputStream = new FileOutputStream(outputFile, true);
			outputStream.write(dbObject.toString().getBytes());
			
		} finally {
			
			if (outputStream != null) {
				outputStream.close();
			}
			
		}
		
	}
}