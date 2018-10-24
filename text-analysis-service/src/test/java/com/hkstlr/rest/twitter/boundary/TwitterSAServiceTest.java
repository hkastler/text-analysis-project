package com.hkstlr.rest.twitter.boundary;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import com.hkstlr.twitter.control.TweetAnalyzer;

import org.junit.Before;
import org.junit.jupiter.api.Test;


/**
 *
 * @author henry.kastler
 */
public class TwitterSAServiceTest {
    
    TwitterSAService cut;
    
    public TwitterSAServiceTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of getResults method, of class TwitterSAService.
     */
    @Test
    public void testGetResults() throws Exception {
        TweetAnalyzerBean tab = new TweetAnalyzerBean();
        tab.setTa(new TweetAnalyzer());
        cut = new TwitterSAService(tab);
        assertNotNull(cut.getResults("twitter"));
    }

    /**
     * Test of getSA method, of class TwitterSAService.
     */
    @Test
    public void testGetSA() throws Exception {
        TweetAnalyzerBean tab = new TweetAnalyzerBean();
        tab.setTa(new TweetAnalyzer());
        cut = new TwitterSAService(tab);
        assertNotNull(cut.getSA("twitter",10));
    }
    
    /**
     * Test of getSA method, of class TwitterSAService.
     */
    @Test
    public void testGetSANull() throws Exception {
        TweetAnalyzerBean tab = new TweetAnalyzerBean();
        tab.setTa(new TweetAnalyzer());
        cut = new TwitterSAService();
        assertNotNull(cut.getSA("twitter",10));
    }
    
}
