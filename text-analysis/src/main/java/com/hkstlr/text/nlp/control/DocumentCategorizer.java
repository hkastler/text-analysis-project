package com.hkstlr.text.nlp.control;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.util.InputStreamFactory;

public interface DocumentCategorizer {

	String getBestCategory(String str);

	double[] getCategorize(String str);

	Object[] getCategorizeAndBestCategory(String str);

	DocumentCategorizerME getDoccat();

	DoccatModel getModel();

	String getModelFile();

	InputStreamFactory getTrainingData();

	/**
	 * @return the trainingDataFile
	 */
	String getTrainingDataFile();

	void setDoccat(DocumentCategorizerME doccat);

	void setModel(DoccatModel model);

	void setModelFile(String modelFile);

	/**
	 * @param trainingDataFile the trainingDataFile to set
	 */
	void setTrainingDataFile(String trainingDataFile);

	void trainModel();

}