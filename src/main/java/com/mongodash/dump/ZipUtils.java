package com.mongodash.dump;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private static final String ZIP_EXTENSION = "zip";

	public static void zipDirectory(String dbDumpPath, String outputName) throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(outputName + ".zip");
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
		File dumpDirectory = new File(dbDumpPath);
		addDirectory(zipOutputStream, dumpDirectory);
		zipOutputStream.close();

	}

	private static void addDirectory(ZipOutputStream zipOutputStream, File dumpDirectory) throws IOException {

		File[] files = dumpDirectory.listFiles();

		for (File file : files) {
			
			if (file.isDirectory()) {
				addDirectory(zipOutputStream, file);
				continue;
			}

			byte[] buffer = new byte[1024];
			FileInputStream fileInputStream = new FileInputStream(file);
			zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

			int length;
			
			while ((length = fileInputStream.read(buffer)) > 0) {
				zipOutputStream.write(buffer, 0, length);
			}
			
			zipOutputStream.closeEntry();
			fileInputStream.close();

		}

	}

	public static boolean isZipFile(File file) {
		return DumpUtils.hasExtension(file, ZIP_EXTENSION);
	}

}