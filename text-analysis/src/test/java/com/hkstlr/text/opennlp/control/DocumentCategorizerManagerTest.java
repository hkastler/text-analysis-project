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
 */
package com.hkstlr.text.opennlp.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;

/**
 *
 * @author henry.kastler
 */
public class DocumentCategorizerManagerTest {

	
	DocumentCategorizerManager cut;

	public DocumentCategorizerManagerTest() {
	}

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() {
		Path testTrainFilepath = Paths.get("src", "test", "resources", 
				"test_twitter_sentiment_training_data.train");
		String tempModelFilePath = folder.getRoot().getAbsolutePath()
				+ File.separator + "testGetTrainingDataFile.bin";
		
		cut = new DocumentCategorizerManager(testTrainFilepath.toString(), 
				tempModelFilePath);
		
	}

	/**
	 * Test of trainModel method, of class SentimentAnalyzer.
	 */
	@Test
	public void testTrainModel() {
		assertNotNull(cut.getModel());
	}

	/**
	 * Test of getModel method, of class SentimentAnalyzer.
	 */
	@Test
	public void testGetModel() {
		assertNotNull(cut.getModel());
	}

	/**
	 * Test of getDoccat method, of class SentimentAnalyzer.
	 */
	@Test
	public void testGetDoccat() {
		assertNotNull(cut.getDoccat());
	}

	/**
	 * Test of getTrainingData method, of class SentimentAnalyzer.
	 */
	@Test
	public void testGetTrainingData() {
		System.out.println("getTrainingData");
		InputStreamFactory result = cut.getTrainingData();
		assertNotNull(result);

		Path testPath = Paths.get("src", "test", "resources", 
				"test_twitter_sentiment_training_data.train");
		Path tempFilePath = Paths.get(folder.getRoot().toString(), "testGetTrainingData.bin");
		cut = new DocumentCategorizerManager(testPath.toString(), tempFilePath.toString());
		result = cut.getTrainingData();

		assertNotNull(result);
		
		System.out.println("getTrainingData2");
		cut = new DocumentCategorizerManager();
		cut.setTrainingDataFile("");

		
		result = cut.getTrainingData();

		assertNotNull(result);
		
		System.out.println("getTrainingData3");
		cut = new DocumentCategorizerManager();
		cut.setTrainingDataFile("/dev/null/no.props");

		
		result = cut.getTrainingData();

		assertNotNull(result);
		
		System.out.println("getTrainingData4");
		Path testPath4 = Paths.get("src", "test", "resources", 
				"test_twitter_sentiment_training_data.train");
		cut = new DocumentCategorizerManager(testPath4.toString(),"");
		

		
		result = cut.getTrainingData();

		assertNotNull(result);
		
	}

	/**
	 * Test of categorize method, of class SentimentAnalyzer.
	 */
	@Test
	public void testCategorize() throws Exception {
		System.out.println("categorize");

		String str = "good";
		String expResult = "positive";
		double[] outcome = cut.getDoccat().categorize(str.split(" "));
		String result = cut.getDoccat().getBestCategory(outcome);
		assertEquals(expResult, result);

		result = cut.getBestCategory(str);
		assertEquals(expResult, result);

		str = "bad";
		expResult = "negative";
		outcome = cut.getDoccat().categorize(str.split(" "));
		result = cut.getDoccat().getBestCategory(outcome);
		assertEquals(expResult, result);

		result = cut.getBestCategory(str);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getTrainingDataFile method, of class SentimentAnalyzer.
	 */
	@Test
	public void testGetTrainingDataFile() {
		System.out.println("getTrainingDataFile");
		
		Path testPath = Paths.get("src", "test", "resources", 
				"test_twitter_sentiment_training_data.train");
		
		String result = cut.getTrainingDataFile();
		assertEquals(testPath.toString(), result);
		assertNotNull(cut.getModel());
		
	}
	
	/**
	 * Test of getTrainingDataFile method, of class SentimentAnalyzer.
	 */
	@Test
	public void testGetTrainingDataFileEmptyConstructor() {
		System.out.println("getTrainingDataFile");
		cut = new DocumentCategorizerManager();
		String result = cut.getTrainingDataFile();
		assertNull(result);

		Path testPath = Paths.get("src", "test", "resources", 
				"test_twitter_sentiment_training_data.train");
		String tempFilePath = folder.getRoot().getAbsolutePath()
				+ File.separator + "testGetTrainingDataFile.bin";
		
		cut = new DocumentCategorizerManager(testPath.toString(), tempFilePath);
		result = cut.getTrainingDataFile();
		assertEquals(testPath.toString(), result);
		
		
	}
	
	@Test
	public void testGetTrainingDataFileModelFileConstructor() {
		System.out.println("testGetTrainingDataFileModelFileConstructor");
		

		String modelFile = cut.getModelFile();
		
		cut = new DocumentCategorizerManager(modelFile);
		String result = cut.getModelFile();
		assertEquals(modelFile, result);
		assertNotNull(cut.getModel());
		assertEquals("DoccatModel",cut.getModel().getClass().getSimpleName());
	}
	
	
	@Test
	public void testGetTokenize() {
		Tokenizer tk = SimpleTokenizer.INSTANCE;
		
		String str = "Hello World this is a test of getTokenize to ensure is is calling the expected tokenizer!";
		String[] mtk = cut.getTokenize(str);
		String[] ctk = tk.tokenize(str);
		
		assertTrue(Arrays.deepEquals(ctk, mtk));
		
		tk = WhitespaceTokenizer.INSTANCE;
		ctk = tk.tokenize(str);
		assertFalse(Arrays.deepEquals(ctk, mtk));
		
	}

}
