package com.hkstlr.rest.twitter.boundary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
        Assertions.assertNotNull(cut);
        Assertions.assertNotNull(cut.getTa());
    }

    
}
