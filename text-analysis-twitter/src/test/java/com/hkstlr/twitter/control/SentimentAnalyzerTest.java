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
		cut = new SentimentAnalyzer(getDCM());		
	}

	@Test
	public void testSetCat() {
		cut = new SentimentAnalyzer();
		cut.setCat(getDCM());
		DocumentCategorizerManager dcm = getDCM();
		cut.setCat(dcm);
		DocumentCategorizerManager hold = cut.getCat();
		assertNotNull(hold);		
		assertEquals(dcm, cut.getCat());
	}

	public DocumentCategorizerManager getDCM(){
		TweetAnalyzer ta = new TweetAnalyzer();
		return ta.getCat();
	}

	@Test
	public void testAnalyzeText() {		
		cut.setCat(getDCM());
		cut.init();
		String text = "good";
		cut.analyzeText(text);
		Integer expected = 1;
		assertEquals(expected, cut.categoryCount.get("positive"));
		
	}

	@Test
	public void testCategoryCountAdd() {
		cut.setCat(getDCM());
		cut.init();
		cut.categoryCountAdd("positive");
		//System.out.println(cut.categoryCount.toString());
		Integer expected = 1;
		assertEquals(expected, cut.categoryCount.get("positive"));
	}

	@Test
	public void testDsvAdd() {
		cut.setCat(getDCM());
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
		cut.setCat(getDCM());
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

	

}
