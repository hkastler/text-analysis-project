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

public class FileWR {
	
	private static final Logger LOG = Logger.getLogger(FileWR.class.getName());
	private String fileName;
	
	public FileWR(String fileName){
		this.fileName = fileName;
	}
 
	public void writeFile(String text) throws IOException {
		
		try(
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		) {
			writer.write(text);		
			
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
		String filenameRegex = "[\\/:*?<>|]+";
		return r.replaceAll(filenameRegex, " ");
	}
}