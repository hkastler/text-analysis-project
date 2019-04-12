package com.hkstlr.rest.twitter.boundary;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;


/**
 *
 * @author henry.kastler
 */
public class TweetAnalyzerBeanTest {
    
    TweetAnalyzerBean cut;
    
    public TweetAnalyzerBeanTest() {
        //
    }
  
    
    @Test
    public void testTa() {
        System.out.println("init");
        cut = new TweetAnalyzerBean();
        cut.init();
        assertNotNull(cut);
        assertNotNull(cut.getTa());
    }

    
}
