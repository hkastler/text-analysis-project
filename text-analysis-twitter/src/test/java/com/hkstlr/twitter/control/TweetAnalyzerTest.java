package com.hkstlr.twitter.control;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import twitter4j.TwitterException;

/**
 * Unit test for simple App.
 */
public class TweetAnalyzerTest {

    public TweetAnalyzerTest() {
    }

    TweetAnalyzer cut;
    TwitterClient mockTc;
    TweetAnalyzer mockCut;

    @Before
    public void setUp() {
        cut = new TweetAnalyzer();

    }

    @Test
    public void testTweetAnalyzer() throws TwitterException {
        String path = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_app_properties";
        cut = new TweetAnalyzer(path, "");
        assertNotNull(cut.getCat());

        String queryTerms = "love";
        cut.setQueryTerms(queryTerms);
        assertEquals(queryTerms, cut.getQueryTerms());

        int tweetCount = 1;
        cut.setTweetCount(tweetCount);
        assertEquals(tweetCount, cut.getTweetCount());

        String tweetText = "The @username, http://replace.me , and newline should be replaced by spaces\n";
        String catText = cut.getTweetTextForCategorization(tweetText);
        assertEquals("The  ,   , and newline should be replaced by spaces ", catText);

        assertEquals(0, cut.getTweets().size());

        Object[] obj = cut.getSentimentAnalysis();
        Map<String, Integer> results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotNull(results.get("positive"));
        assertNotEquals(0, cut.getTweets().size());

        obj = cut.getSentimentAnalysis(queryTerms, 10);
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotNull(results.get("positive"));
        assertNotNull(results.get("negative"));
        assertNotNull(results.get("neutral"));
        assertNotEquals(0, cut.getTweets().size());

        obj = cut.getSentimentAnalysis(queryTerms);
        results = (LinkedHashMap<String, Integer>) obj[0];
        assertNotNull(results.get("positive"));
        assertNotNull(results.get("negative"));
        assertNotNull(results.get("neutral"));
        assertNotEquals(0, cut.getTweets().size());

    }

    @Test
    public void testGetDsvTemplate() {
        String expected = "{0}~{1}~{2}\n";
        String dsvTemplate = cut.getDsvTemplate(3, "~", "\n");
        System.out.print(dsvTemplate);
        assertEquals(expected, dsvTemplate);
    }

}
