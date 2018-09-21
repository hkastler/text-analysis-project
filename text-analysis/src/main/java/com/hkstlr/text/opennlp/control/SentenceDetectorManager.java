package com.hkstlr.text.opennlp.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectorManager {

    private static final Logger LOG = Logger.getLogger(SentenceDetectorManager.class.getName());

    private String modelFile;
    private SentenceModel model;
    private SentenceDetector sentenceDetector;

    public SentenceDetectorManager() {
        super();
        init();
    }

    public SentenceDetectorManager(String modelFile) {
        super();
        this.modelFile = modelFile;
        init();
    }

    void init() {

        Path modelPath = Paths.get("src", "main", "resources", "models", "en-sent.bin");

        if (Optional.ofNullable(modelFile).isPresent()) {
            modelPath = Paths.get(Optional.ofNullable(modelFile).get());
        }
        InputStream modelIn;

        try {
            modelIn = new FileInputStream(modelPath.toFile());
            this.model = new SentenceModel(modelIn);
            this.sentenceDetector = new SentenceDetectorME(this.model);
            modelIn.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE,"", e);
        }
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    public SentenceModel getModel() {
        return model;
    }

    public void setModel(SentenceModel model) {
        this.model = model;
    }

    public SentenceDetector getSentenceDetector() {
        return sentenceDetector;
    }

    public void setSentenceDetector(SentenceDetector sentenceDetector) {
        this.sentenceDetector = sentenceDetector;
    }

}
