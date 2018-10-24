package com.hkstlr.rest.twitter.boundary;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hkstlr.twitter.control.TweetAnalyzer;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import twitter4j.TwitterException;


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

    @Test
    public void testGetSAThrow() throws Exception {
        TwitterSAService mtsas = mock(TwitterSAService.class);
        TweetAnalyzerBean mtab = mock(TweetAnalyzerBean.class);
        TweetAnalyzer mta = mock(TweetAnalyzer.class);
        mtab.setTa(mta);
        mtsas.tab = mtab;
        mtsas.tab.ta = mta;
        when(mtsas.tab.ta.getSentimentAnalysis()).thenThrow(TwitterException.class);
        
        assertThatThrownBy(() -> mtsas.tab.ta.getSentimentAnalysis()).isInstanceOf(TwitterException.class);
    }

}
