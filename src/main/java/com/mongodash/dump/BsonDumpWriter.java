package com.mongodash.dump;

import com.mongodb.DBObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bson.BSON;

public class BsonDumpWriter extends DumpWriter {

	private static final String BSON_EXTENSION = "bson";

	public BsonDumpWriter(String outputDirectory, String database) {
		super(outputDirectory, database);
	}

	public BsonDumpWriter(String outputDirectory) {
		super(outputDirectory);
	}

	@Override
	public String getExtension() {
		return BSON_EXTENSION;
	}

	@Override
	public void writeObject(String collection, DBObject dbObject) throws IOException {
		
		FileOutputStream outputStream = null;
		File outputFile = new File(getFilePath(collection));
		outputFile.getParentFile().mkdirs();
		
		try {
			
			outputStream = new FileOutputStream(outputFile, true);
			outputStream.write(BSON.encode(dbObject));
			
		} finally {
			
			if (outputStream != null) {
				outputStream.close();
			}
			
		}
	}
}