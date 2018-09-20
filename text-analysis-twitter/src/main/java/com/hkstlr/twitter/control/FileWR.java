package com.hkstlr.twitter.control;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @authorcoding-guru.com
 *
 */
public class FileWR {
	
	private static final Logger LOG = Logger.getLogger(FileWR.class.getName());
	static Writer writer;
	
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
}