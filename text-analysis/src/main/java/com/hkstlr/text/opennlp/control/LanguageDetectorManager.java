package com.hkstlr.text.opennlp.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

public class LanguageDetectorManager {

	private File modelFile;
	private LanguageDetectorModel model = null;
	private LanguageDetector languageDetector;
	
	private static Logger LOG = Logger.getLogger(LanguageDetectorManager.class.getName());
	
	
	public LanguageDetectorManager() {
		super();
		init();
	}
	
	void init() {
		modelFile = Paths.get("/etc/config/models/langdetect-183.bin").toFile();
		setModel(modelFile);
		languageDetector = new LanguageDetectorME(model);
		
	}
	
	
	private LanguageDetectorModel setModel(File modelFile) {
		try {
			model =  new LanguageDetectorModel(modelFile);
		} catch (IOException e) {
			LOG.log(Level.WARNING, "setModel error", e);
		}
		return model;
	}

	public LanguageDetector getLanguageDetector() {
		return languageDetector;
	}

	public void setLanguageDetector(LanguageDetector languageDetector) {
		this.languageDetector = languageDetector;
	}
	
	
}
