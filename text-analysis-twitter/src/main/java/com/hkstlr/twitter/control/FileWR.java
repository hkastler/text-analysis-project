package com.hkstlr.twitter.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWR {

	private static final Logger LOG = Logger.getLogger(FileWR.class.getName());
	private final String fileName;

	public FileWR(String fileName) {
		this.fileName = fileName;
	}

	public void writeFile(String text) throws IOException {

		try (FileOutputStream fos = new FileOutputStream(fileName);
				Writer writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));) {
			writer.write(text);

		} catch (FileNotFoundException e) {
			LOG.log(Level.SEVERE, "FileNotFoundException", e);
			throw new FileNotFoundException(e.getMessage());
		} catch (NullPointerException ex) {
			LOG.log(Level.SEVERE, "NullPointerException", ex);
			throw new NullPointerException(ex.getMessage());
		}

	}

	public void appendTextToFile(String text) throws IOException {

		try (Writer writer = Files.newBufferedWriter(Paths.get(this.fileName), StandardCharsets.UTF_8,
				StandardOpenOption.APPEND);) {	
			writer.append(text);

		} catch (FileNotFoundException e) {
			LOG.log(Level.SEVERE, "FileNotFoundException", e);
			throw new FileNotFoundException(e.getMessage());
		} catch (NullPointerException ex) {
			LOG.log(Level.SEVERE, "NullPointerException", ex);
			throw new NullPointerException(ex.getMessage());
		}

	}

	public static String getDesktopFilePath(String fileNameBase, String fileExtension) {

		String fileName = fileNameSafeString(fileNameBase);
		return System.getProperty("user.home").concat(File.separator).concat("Desktop").concat(File.separator)
				.concat(fileName).concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
				.concat(fileExtension);

	}

	private static String fileNameSafeString(String fileName) {
		String r = fileName;
		String filenameRegex = "[\\/:*?<>|]+";
		return r.replaceAll(filenameRegex, "");
	}
}