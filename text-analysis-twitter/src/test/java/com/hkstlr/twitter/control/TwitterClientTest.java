/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.twitter.control;

import java.util.List;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 *
 * @author henry.kastler
 */
public class TwitterClientTest {
    
    TwitterClient cut;
    
    public TwitterClientTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of setTwitter method, of class TwitterClient.
     */
    @Test
    public void testTwitterClient() {
        System.out.println("testTwitterClient");
        cut = new TwitterClient();
        assertNotNull(cut);
        
        Twitter tw = new TwitterFactory().getInstance();
        cut.setTwitter(tw);
        assertEquals(tw,cut.getTwitter());
        
        Properties props = new Properties();
        props.put("oAuthConsumerKey","");
        props.put("oAuthConsumerKey","");
	props.put("oAuthConsumerSecret","");
	props.put("oAuthAccessToken","");
	props.put("oAuthAccessTokenSecret","");
        cut = new TwitterClient(props);
        assertNotNull(cut);
        
        
       
    }

    

    
}
