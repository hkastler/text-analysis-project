package com.hkstlr.twitter.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterException;

/**
 *
 * @author henry.kastler
 */
public class TweetAnalyzerIT {

    TweetAnalyzer cut;

    @Before
    public void setUp() {
        cut = new TweetAnalyzer();
    }

    public TweetAnalyzerIT() {
    }

    @SuppressWarnings("unchecked") 
    @Test
    public void testTweetAnalyzer() throws TwitterException {
        
        assertNotNull(cut.getTc());

        Object[] obj = cut.getSentimentAnalysis();
        Map<String, Integer> results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotNull(results.get("positive"));
        
        String queryTerms = "pizza";
        obj = cut.getSentimentAnalysis(queryTerms);
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertEquals(queryTerms,cut.getQueryTerms());
        
        assertNotNull(results.get("positive"));
        assertNotNull(results.get("negative"));
        assertNotNull(results.get("neutral"));
       

        int itemsToGet = 500;
        obj = cut.getSentimentAnalysis(queryTerms, itemsToGet);
        assertEquals(itemsToGet,cut.getTweetCount());
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotEquals(0, (int) results.get("positive"));
        assertNotEquals(0, (int) results.get("negative"));
        assertNotEquals(0, (int) results.get("neutral"));
       

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetSentimentAnalysis() throws TwitterException {
        int itemsToGet = 500;
        String queryTerms = "pizza";
        Object[] obj = cut.getSentimentAnalysis(queryTerms, itemsToGet);
        assertEquals(itemsToGet,cut.getTweetCount());
        Map<String,Integer>results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotEquals(0, (int) results.get("positive"));
        assertNotEquals(0, (int) results.get("negative"));
        assertNotEquals(0, (int) results.get("neutral"));
        
    }

}
