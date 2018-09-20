package com.hkstlr.twitter.control;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @authorcoding-guru.com
 *
 */
public class FileWR {
	
	private static final Logger LOG = Logger.getLogger(FileWR.class.getName());
	static FileWriter writer;
	
	public FileWR(String fileName){
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"",e);
		}
	}
 
	public static void writeFile(String text) throws IOException {
		writer.write(text);
	}
 
	public static void close(){
		try {
			writer.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"",e);
		}
	}
}