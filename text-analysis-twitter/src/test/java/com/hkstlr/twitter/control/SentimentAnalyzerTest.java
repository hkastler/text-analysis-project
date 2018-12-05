package com.hkstlr.twitter.control;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

public class SentimentAnalyzerTest {

	SentimentAnalyzer cut;

	@Before
	public void setUp() throws Exception {
		cut = new SentimentAnalyzer();
	}

	@Test
	public void testSetCat() {
		cut.setCat(null);
		cut.setCat();
		DocumentCategorizerManager hold = cut.getCat();
		assertNotNull(hold);
		cut.setCat();
		assertEquals(hold, cut.getCat());
	}

	@Test
	public void testAnalyzeText() {
		
		cut.init();
		String text = "good";
		cut.analyzeText(text);
		Integer expected = 1;
		assertEquals(expected, cut.categoryCount.get("positive"));
		
	}

	@Test
	public void testCategoryCountAdd() {
		cut.init();
		cut.categoryCountAdd("positive");
		//System.out.println(cut.categoryCount.toString());
		Integer expected = 1;
		assertEquals(expected, cut.categoryCount.get("positive"));
	}

	@Test
	public void testDsvAdd() {
		cut.init();
		String sentiment = "positive";
		String text = "sample text";
		Map<String, Double> map = new LinkedHashMap<>();
		
		map.put("positive", .80);
		map.put("neutral", .15);
		map.put("negative", .05);
		cut.dsvAdd(sentiment, text, map);
		
		String expected = "positive~sample text~0.8~0.15~0.05";
		String[] ary = cut.dsv.toString().split(cut.newLine);
		assertEquals(expected, ary[1]);
	}

	@Test
	public void testGetSentimentAnalysis() {
		cut.init();
		Object[] results = cut.getSentimentAnalysis();
		String expectedDsv = "sentiment~tweet~positive~neutral~negative" + cut.newLine;
		
		assertEquals(expectedDsv,results[1]);
		String expectedMapString = "{total=0, positive=0, neutral=0, negative=0}";
		assertEquals(expectedMapString, results[0].toString());
	}

	@Test
	public void testGetDsvTemplate() {
		String expected = "{0}~{1}~{2}\n";
        String dsvTemplate = cut.getDsvTemplate(3, "~", "\n");
        assertEquals(expected, dsvTemplate);
	}

	@Test
	public void testGetCat() {
		cut.init();
		assertNotNull(cut.getCat());
	}

	@Test
	public void testSetCatDocumentCategorizerManager() {
		cut.init();
		cut.cat = null;
		cut.setCat();
		assertTrue(null != cut.getCat());
	}

	@Test
	public void testGetTrainingDataFile() {
		String path = "/test/path/here";
		cut.setTrainingDataFile(path);
		assertEquals(path, cut.getTrainingDataFile());
	}

	

	@Test
	public void testGetModelOutFile() {
		String path = "/test/path/here";
		cut.setModelOutFile(path);
		assertEquals(path, cut.getModelOutFile());
	}


}
