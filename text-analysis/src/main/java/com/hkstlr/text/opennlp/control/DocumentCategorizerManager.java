/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * see https://github.com/technobium/opennlp-categorizer
 * 
 */
package com.hkstlr.text.opennlp.control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.doccat.NGramFeatureGenerator;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class DocumentCategorizerManager {

	static final Logger LOG = Logger.getLogger(DocumentCategorizerManager.class.getName());
	
	private DoccatModel model;
	private DoccatFactory doccatFactory;
	private DocumentCategorizer doccat;
	private int minNgramSize = 1;
	private int maxNgramSize = 4;
	private int iterations = 100;
	private int cutoff = 0;
	
	private String trainingDataFile;
	private String modelFile;

	public DocumentCategorizerManager() {
		init();
	}

	public DocumentCategorizerManager(String modelFile) {
		this.modelFile = modelFile;
		init();
	}
	
	public DocumentCategorizerManager(String trainingDataFile,
			String modelFile) {
		this.trainingDataFile = trainingDataFile;
		this.modelFile = modelFile;
		init();
	}

	void init() {
		Optional<String> oModelFile = Optional.ofNullable(modelFile);
		if (new File(oModelFile.orElse("")).exists()) {
			loadModelFromFile();
		} else {
			
			trainModel();
			
			if(oModelFile.isPresent() && !oModelFile.get().isEmpty()) {
				saveModelToFile();
			}
			
		}
		
		doccat = new DocumentCategorizerME(model);
		
	}

	
	public String getBestCategory(String str) {
		return doccat.getBestCategory(getCategorize(str));
	}

	public String[] getTokenize(String str) {
		return opennlp.tools.tokenize.SimpleTokenizer.INSTANCE.tokenize(str);
	}
	
	
	public double[] getCategorize(String str) {
		return doccat.categorize(opennlp.tools.tokenize.SimpleTokenizer.INSTANCE.tokenize(str));
	}

	
	public Object[] getCategorizeAndBestCategory(String str) {
		Object[] returnObj = new Object[2];

		returnObj[0] = getCategorize(str);
		returnObj[1] = doccat.getBestCategory((double[]) returnObj[0]);

		return returnObj;
	}

	
	public DocumentCategorizer getDoccat() {
		return doccat;
	}

	private DoccatFactory getDoccatFactory() {

		if (null == doccatFactory) {
			setDoccatFactory();
		}

		return doccatFactory;

	}

	
	public DoccatModel getModel() {
		return model;
	}

	
	public String getModelFile() {
		return modelFile;
	}

	
	public InputStreamFactory getTrainingData() {

		InputStreamFactory tdata = null;
		Optional<String> tdataCustomFile = Optional.ofNullable(trainingDataFile);
		if (tdataCustomFile.isPresent() && !tdataCustomFile.get().isEmpty()) {
			try {
				tdata = new MarkableFileInputStreamFactory(Paths.get(tdataCustomFile.get()).toFile());

			} catch (FileNotFoundException e) {
				LOG.log(Level.SEVERE, null, e);
			}
			return tdata;
		}

		try {

			tdata = new MarkableFileInputStreamFactory(
					Paths.get("/etc/config/twitter_sentiment_training_data.train").toFile());

		} catch (FileNotFoundException ne) {

			try {

				tdata = new MarkableFileInputStreamFactory(
						Paths.get("src", "main", "resources", "twitter_sentiment_training_data.train").toFile());

			} catch (FileNotFoundException e) {
				LOG.log(Level.SEVERE, null, e);
			}
		} catch (Exception e) {

			LOG.log(Level.SEVERE, null, e);
		}
		return tdata;
	}

	
	public String getTrainingDataFile() {
		return trainingDataFile;
	}


	private void loadModelFromFile() {

		try {

			this.model = new DoccatModel(Paths.get(modelFile));
			

		} catch (IOException e) {
			LOG.log(Level.SEVERE, "", e);

		}
		
	}

	private void saveModelToFile() {

		BufferedOutputStream modelOut;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(modelFile));
			model.serialize(modelOut);
			modelOut.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "", e);
		}

	}

	
	public void setDoccat(DocumentCategorizerME doccat) {
		this.doccat = doccat;
	}

	private void setDoccatFactory() {
		try {
			doccatFactory = new DoccatFactory(new FeatureGenerator[] {
					new BagOfWordsFeatureGenerator(),
					new NGramFeatureGenerator(minNgramSize, maxNgramSize) });
		} catch (InvalidFormatException e) {
			doccatFactory = new DoccatFactory();
			LOG.log(Level.SEVERE, "", e);
		}
	}

	
	public void setModel(DoccatModel model) {
		this.model = model;
	}

	
	public void setModelFile(String modelFile) {
		this.modelFile = modelFile;
	}

	
	public void setTrainingDataFile(String trainingDataFile) {
		this.trainingDataFile = trainingDataFile;
	}

	
	private void trainModel() {

		try {

			ObjectStream<String> lineStream = new PlainTextByLineStream(getTrainingData(), StandardCharsets.UTF_8);
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, iterations + "");
			params.put(TrainingParameters.CUTOFF_PARAM, cutoff + "");
			params.put(TrainingParameters.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
			
			this.model = DocumentCategorizerME.train(Locale.ENGLISH.getLanguage(), 
					sampleStream, params, getDoccatFactory());
			
			

		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
		
	}

}
