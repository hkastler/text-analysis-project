
package com.hkstlr.rest.twitter.boundary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.enterprise.event.Event;


import com.hkstlr.twitter.control.TweetAnalyzer;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;

/**
 *
 * @author henry.kastler
 */
public class TwitterSAWriteServiceTest {
    
    TwitterSAWriteService cut;
    
    public TwitterSAWriteServiceTest() {
    }
    
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testConstructor() throws Exception {
        cut = new TwitterSAWriteService();
        assertNotNull(cut);
        TweetAnalyzerBean tab = new TweetAnalyzerBean();
        cut = new TwitterSAWriteService(tab);
        assertNotNull(cut);
    }

    /**
     * Test of addTrainText method, of class TwitterSAWriteService.
     * @throws java.lang.Exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testAddTrainText() throws Exception {
        
        cut = mock(TwitterSAWriteService.class);
        cut.tab = mock(TweetAnalyzerBean.class);
        when(cut.tab.getTa()).thenReturn(mock(TweetAnalyzer.class));
        cut.wse = mock(Event.class);
        
        String sentiment = "positive";
        String text = "hello world";
        
        doCallRealMethod().when(cut).addTrainText(sentiment, text);
        String expected = text + " trained to " + sentiment;
      
        assertEquals(expected,cut.addTrainText(sentiment, text));
        
    }
    
}
