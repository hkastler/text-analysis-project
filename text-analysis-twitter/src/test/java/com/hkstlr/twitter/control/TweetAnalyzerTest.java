package com.hkstlr.twitter.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
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
    public void testTweets(){
        assertEquals(0, cut.getTweets().size());
    }

    @Test
    public void testSetCat(){
        cut.setCat(null);
        cut.setCat();
        DocumentCategorizerManager hold = cut.getCat();
        assertNotNull(hold);
        cut.setCat();
        assertEquals(hold,cut.getCat());
    }
    
    @Test
    public void testGetDsvTemplate() {
        String expected = "{0}~{1}~{2}\n";
        String dsvTemplate = cut.getDsvTemplate(3, "~", "\n");
        System.out.print(dsvTemplate);
        assertEquals(expected, dsvTemplate);
    }

}
