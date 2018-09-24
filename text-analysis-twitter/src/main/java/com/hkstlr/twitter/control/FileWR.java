package com.hkstlr.twitter.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @authorcoding-guru.com
 *
 */
public class FileWR {
	
	private static final Logger LOG = Logger.getLogger(FileWR.class.getName());
	Writer writer;
	
	public FileWR(String fileName){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8"));
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"",e);
		}
	}
 
	public void writeFile(String text) throws IOException {
		writer.write(text);
	}
 
	public void close(){
		try {
			writer.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"",e);
		}
	}
	
	public static String getDesktopFilePath(String fileNameBase, String fileExtension) {

		String fileName = fileNameSafeString(fileNameBase);
		StringBuilder filePath = new StringBuilder(System.getProperty("user.home"))
				.append(File.separator)
				.append("Desktop")
				.append(File.separator)
				.append(fileName)
				.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
				.append(fileExtension);
		return filePath.toString();

	}

	private static String fileNameSafeString(String fileName) {
		String r = fileName;
		String FILENAME_REGEX = "[\\/:*?<>|]+";
		return r.replaceAll(FILENAME_REGEX, " ");
	}
}