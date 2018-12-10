package com.hkstlr.twitter.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

import twitter4j.TwitterException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for TweetAnalyzerTest.
 */
public class TweetAnalyzerTest {

    public TweetAnalyzerTest() {
    }

    TweetAnalyzer cut;

    @Before
    public void setUp() {
        cut = new TweetAnalyzer();
    }

    @Test
    public void testSetCat(){
        cut.setCat();
        DocumentCategorizerManager cat = cut.getCat();
        cut.setCat(cat);
        assertEquals(cat, cut.getCat());
    }

    @Test
    public void testQueryTerms(){
        String queryTerms = "love";
        cut.setQueryTerms(queryTerms);
        assertEquals(queryTerms, cut.getQueryTerms());
    }

    @Test
    public void testTweetCount(){
        int tweetCount = 1;
        cut.setTweetCount(tweetCount);
        assertEquals(tweetCount, cut.getTweetCount());
    }

    @Test
    public void testTweetTextForCategorization(){
        String tweetText = "The @username, http://replace.me , and newline should be replaced by spaces\n";
        String catText = cut.getTweetTextForCategorization(tweetText);
        assertEquals("The  ,   , and newline should be replaced by spaces ", catText);
    }

    @Test
    public void testGetSentimentalAnalysis() {
    	String queryTerms = "chicago pizza";
    	try {
			cut.getSentimentAnalysis(queryTerms);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
    	
    }

    @Test
    public void testGetSentimentalAnalysis2() {
    	String queryTerms = "chicago pizza";
    	Integer tweetCount = 1;
    	try {
			cut.getSentimentAnalysis(queryTerms,tweetCount);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testGetTc() {
    	assertNotNull(cut.getTc());
    }

}
