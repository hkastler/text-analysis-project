package com.hkstlr.twitter.control;

import java.util.List;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetAnalyzer {

    DocumentCategorizerManager cat;
    String trainingDataFile = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_training_data.train";
    String modelOutFile = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sa_model.bin";

    TwitterClient tc;
    String queryTerms = "";
    int tweetCount = 100;
    String languageCode = "";
    
    public TweetAnalyzer() {
        super();
        init();
    }

    void init() {

        Config analyzerConfig = new Config("tweetAnalyzer.properties");
        setTrainingDataFile(analyzerConfig.getProps().getProperty("trainingDataFilePath", getTrainingDataFile()));
        setModelOutFile(analyzerConfig.getProps().getProperty("modelOutFile", getModelOutFile()));
        setQueryTerms(analyzerConfig.getProps().getProperty("queryTerms", getQueryTerms()));
        setCat();
        this.languageCode = cat.getLanguageCode();
        Config twitterClientConfig = new Config(analyzerConfig.getProps().getProperty("twitterClientConfigPath"));
        setTc(new TwitterClient(twitterClientConfig.getProps()));
    }

    public String getQueryTerms() {
        return this.queryTerms;
    }

    public void setQueryTerms(String queryTerms) {
        this.queryTerms = queryTerms;
    }

    public int getTweetCount() {
        return this.tweetCount;
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

        SentimentAnalyzer sa = new SentimentAnalyzer(getCat());
        sa.init();
        fetchTweets().stream().forEach(tweet ->
            sa.analyzeText(getTweetTextForCategorization(tweet.getText()))
        );

        return  sa.getSentimentAnalysis();
    }

    public List<Status> fetchTweets() throws TwitterException{
       return tc.getTweets(this.queryTerms, this.tweetCount, this.languageCode);
    }

    public DocumentCategorizerManager getCat() {
        return this.cat;
    }

    public void setCat() {
        if (null == this.cat) {
            this.cat = new DocumentCategorizerManager(getTrainingDataFile(), getModelOutFile());
        }
    }

    public void setCat(DocumentCategorizerManager cat) {
        this.cat = cat;
    }

    public String getTrainingDataFile() {
        return trainingDataFile;
    }

    public void setTrainingDataFile(String trainingDataFile) {
        this.trainingDataFile = trainingDataFile;
    }

    public String getModelOutFile() {
        return modelOutFile;
    }

    public void setModelOutFile(String modelOutFile) {
        this.modelOutFile = modelOutFile;
    }

    public TwitterClient getTc() {
        return tc;
    }

    public void setTc(TwitterClient tc) {
        this.tc = tc;
    }

}
