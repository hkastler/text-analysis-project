package com.hkstlr.twitter.boundary;

import static org.junit.Assert.assertEquals;

import com.hkstlr.twitter.control.TweetAnalyzer;

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
    public void testGetDsvTemplate() {
        String expected = "{0}~{1}~{2}\n";
        String dsvTemplate = cut.getDsvTemplate(3, "~", "\n");
        System.out.print(dsvTemplate);
        assertEquals(expected, dsvTemplate);
    }

}
