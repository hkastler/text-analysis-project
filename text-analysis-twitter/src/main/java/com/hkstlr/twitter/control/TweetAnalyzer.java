package com.hkstlr.twitter.control;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetAnalyzer {
    
    SentimentAnalyzer sa;
    private TwitterClient tc;
    private String queryTerms = "";
    int tweetCount = 100;
    private List<Status> tweets = new ArrayList<>();

    public TweetAnalyzer() {
        super();
        init();
    }

     void init() {
    	sa = new SentimentAnalyzer();
    	sa.init();
        Config analyzerConfig = new Config("tweetAnalyzer.properties");
        Config twitterClientConfig = new Config(analyzerConfig.getProps().getProperty("twitterClientConfigPath"));
        setTc(new TwitterClient(twitterClientConfig.getProps()));
    }

    public String getQueryTerms() {
        return queryTerms;
    }

    public void setQueryTerms(String queryTerms) {
        this.queryTerms = queryTerms;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public Object[] getSentimentAnalysis(String queryTerms) throws TwitterException {
        this.queryTerms = queryTerms;
        return getSentimentAnalysis();
    }

    public Object[] getSentimentAnalysis(String queryTerms, int tweetCount) throws TwitterException {
        this.queryTerms = queryTerms;
        this.tweetCount = tweetCount;
        return getSentimentAnalysis();
    }

    public String getTweetTextForCategorization(String tweetText) {

        String rtnStr = tweetText;
        // thanks to
        // https://stackoverflow.com/questions/8376691/how-to-remove-hashtag-user-link-of-a-tweet-using-regular-expression
        String twitterScreennameRegex = "(@[A-Za-z0-9]+)"; // ([^0-9A-Za-z \\t]) removes hashtags
        String urlRegex = "(\\w+:\\/\\/\\S+)";
        String newlineRegex = "(\\r\\n|\\r|\\n)";

        return rtnStr.replaceAll(newlineRegex, " ").replaceAll(twitterScreennameRegex, " ").replaceAll(urlRegex, " ");
    }

    public Object[] getSentimentAnalysis() throws TwitterException {
    	tweets = tc.getTweets(this.queryTerms, this.tweetCount, sa.getCat().getLanguageCode());
        for (Status tweet : tweets) {
        	sa.analyzeText(getTweetTextForCategorization(tweet.getText()));
        }
        return sa.getSentimentAnalysis();
    }
    
    
    public List<Status> getTweets() {
        return tweets;
    }

    public TwitterClient getTc() {
        return tc;
    }

    public void setTc(TwitterClient tc) {
        this.tc = tc;
    }

}
