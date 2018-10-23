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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
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
	private Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
	private String languageCode;
	private int minNgramSize = 2;
	private int maxNgramSize = 10;
	private int iterations = 10000;
	private int cutoff = 2;
	private boolean printMessages = false;
	
	private String trainingDataFile;
	private String modelFile;

	public DocumentCategorizerManager() {
		super();
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
		
		Optional<String> oLanguageCode = Optional.ofNullable(languageCode);
		languageCode = oLanguageCode.orElse(Locale.getDefault().getLanguage());
		
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
		return tokenizer.tokenize(str);
	}
	
	
	public double[] getCategorize(String str) {
		return doccat.categorize(getTokenize(str));
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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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
		if (tdataCustomFile.isPresent() && Paths.get(tdataCustomFile.get()).toFile().exists()) {
			try {
				tdata = new MarkableFileInputStreamFactory(
						Paths.get(tdataCustomFile.get()).toFile());

			} catch (IOException e) {
				LOG.log(Level.WARNING, null, e);
			}
			if(null != tdata)
				return tdata;
		}
		Optional<File> defaultFile = Optional.ofNullable(defaultTrainingDataFile());
		try {
			
			tdata = new MarkableFileInputStreamFactory(
					defaultFile.orElseThrow(() -> new FileNotFoundException()));
		
		} catch (Exception e) {

			LOG.log(Level.INFO, null, e);
		}
		return tdata;
	}

	public File defaultTrainingDataFile() {
		
		File defaultTrainingFile = null;
		InputStream codebaseIs = this.getClass().getClassLoader().getResourceAsStream("sentiment_training_data.train");
		
		try {
			File codebaseTrainerFile = streamToFile(codebaseIs);

			if(codebaseTrainerFile.exists()) {
				LOG.warning("using jar file .train file...is this expected?");
				LOG.warning("see TweetAnalyzer for default external file path");
				return codebaseTrainerFile;
			}
		} catch (Exception e) {
			LOG.log(Level.INFO,"",e);
		} 
		
		return defaultTrainingFile;
	}

	public File streamToFile(InputStream in){
		String prefix = "trainer";
		String suffix = ".train";

		File tempFile = null;
	
			try {
				tempFile = File.createTempFile(prefix, suffix);				
				Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);	
				in.close();
				tempFile.deleteOnExit();		
				
			}catch(Exception e){
				LOG.log(Level.WARNING,"",e);
			}
			
		
		return tempFile;
	}
	
	public String getTrainingDataFile() {
		return trainingDataFile;
	}


	void loadModelFromFile() {
		Optional<String> oModelFile = Optional.ofNullable(modelFile);
		this.model = loadModelFromFile(oModelFile.orElse(""));
	}
	
	DoccatModel loadModelFromFile(String modelFile) {
		DoccatModel dcm = null;
		if(new File(modelFile).exists()) {
			try {
				dcm = new DoccatModel(Paths.get(modelFile));
				
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "", e);
			}
		}
		return dcm;
	}

	void saveModelToFile() {

		try(
			FileOutputStream fos = new FileOutputStream(modelFile);
			BufferedOutputStream modelOut= new BufferedOutputStream(fos);
			) {
			
			model.serialize(modelOut);
			
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
			LOG.log(Level.WARNING, "default DocccatFactory created", e);
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
		
		try( 
			ObjectStream<String> lineStream = new PlainTextByLineStream(getTrainingData(), StandardCharsets.UTF_8); 
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			) {
			
			TrainingParameters params = new TrainingParameters();
			params.put("PrintMessages", printMessages);
			params.put(TrainingParameters.ITERATIONS_PARAM,  Integer.toString(iterations));
			params.put(TrainingParameters.CUTOFF_PARAM,  Integer.toString(cutoff));
			params.put(TrainingParameters.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
			
			this.model = DocumentCategorizerME.train(languageCode, 
					sampleStream, params, getDoccatFactory());

		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
		
	}

}
