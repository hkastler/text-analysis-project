package com.hkstlr.twitter.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

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
        String path = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_app_properties";
        cut = new TweetAnalyzer(path, "");
    }

    public TweetAnalyzerIT() {
    }

    @SuppressWarnings("unchecked") 
    @Test
    public void testTweetAnalyzer() throws TwitterException {
        
        assertNotNull(cut.getCat());
        DocumentCategorizerManager temp = cut.getCat();
        cut.setCat(temp);
        assertEquals(temp, cut.getCat());
        assertNotNull(cut.getTc());

        Object[] obj = cut.getSentimentAnalysis();
        Map<String, Integer> results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotNull(results.get("positive"));
        assertEquals(cut.getTweetCount(), cut.getTweets().size());
        System.out.print(cut.getQueryTerms());

        String queryTerms = "pizza";
        obj = cut.getSentimentAnalysis(queryTerms);
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertEquals(queryTerms,cut.getQueryTerms());
        
        assertNotNull(results.get("positive"));
        assertNotNull(results.get("negative"));
        assertNotNull(results.get("neutral"));
        assertEquals(100, cut.getTweets().size());

        int itemsToGet = 500;
        obj = cut.getSentimentAnalysis(queryTerms, itemsToGet);
        assertEquals(itemsToGet,cut.getTweetCount());
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotEquals(0, (int) results.get("positive"));
        assertNotEquals(0, (int) results.get("negative"));
        assertNotEquals(0, (int) results.get("neutral"));
        assertNotEquals(0, (int) cut.getTweets().size());

    }

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
        assertNotEquals(0, (int) cut.getTweets().size());
    }

    @Test
    public void testGetTweets() throws TwitterException {
        
        String queryTerms = "pizza";
        int tweetCount = 100;

        cut.getTc().getTweets(queryTerms, tweetCount);
        assertNotNull(cut.getTweets());

        cut.getTc().getTweets(queryTerms);
        assertNotNull(cut.getTweets());

        queryTerms = "chicago scooters";
        tweetCount = 1000;
        cut.getTc().getTweets(queryTerms,tweetCount);
        assertNotNull(cut.getTweets());
    }

}
