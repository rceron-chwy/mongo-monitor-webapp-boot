package com.mongodash.dump;

import java.io.File;
import java.util.regex.Pattern;

public class DumpUtils {
	
	private static final String SYSTEM_COLLECTION_PREFIX = "system.";
	private static final String BSON_EXTENSION = "bson";

	public static boolean isBsonFile(File file) {
		return hasExtension(file, BSON_EXTENSION);
	}

	public static boolean hasExtension(File file, String extension) {
		return file.getName().endsWith("." + extension);
	}

	public static String getCollectionName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public static boolean isSystemCollection(String collection) {
		return collection.startsWith(SYSTEM_COLLECTION_PREFIX);
	}

	public static boolean isUserCollection(String collection) {
		return collection.endsWith(SYSTEM_COLLECTION_PREFIX + "user");
	}

	public static String removeExtension(String path) {
		return path.substring(0, path.lastIndexOf("."));
	}

	public static Pattern getNamespacePattern(String database) {
		return Pattern.compile("^" + database + ".*");
	}

}