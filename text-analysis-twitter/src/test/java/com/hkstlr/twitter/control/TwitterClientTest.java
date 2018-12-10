package com.hkstlr.twitter.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
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
        assertEquals(tw, cut.getTwitter());

        Properties props = new Properties();
        props.put("oAuthConsumerKey", "");
        props.put("oAuthConsumerKey", "");
        props.put("oAuthConsumerSecret", "");
        props.put("oAuthAccessToken", "");
        props.put("oAuthAccessTokenSecret", "");
        cut = new TwitterClient(props);
        assertNotNull(cut);
        System.out.println("cut.getTweets");

        List<Status> tweets = new ArrayList<>();
        boolean hasError = false;
        try {
            tweets = cut.getTweets("hey", 1, "en");
        } catch (TwitterException e) {
            hasError = true;

        }
        assertTrue(hasError);
        assertTrue(tweets.isEmpty());

        hasError = false;
        try {
            tweets = cut.getTweets("hey", 10, "en");
        } catch (TwitterException e) {
            hasError = true;

        }
        assertTrue(hasError);
        assertTrue(tweets.isEmpty());
    }

}
