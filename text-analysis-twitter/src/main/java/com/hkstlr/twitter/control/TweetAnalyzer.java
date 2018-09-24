package com.hkstlr.twitter.control;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;
import com.hkstlr.text.opennlp.control.LanguageDetectorManager;

import opennlp.tools.langdetect.Language;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetAnalyzer {

	private static final Logger LOG = Logger.getLogger(TweetAnalyzer.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private DocumentCategorizerManager cat;
	LanguageDetectorManager ldm;
	private TwitterClient tc;
	private String queryTerms;
	int tweetCount = 100;
	private List<Status> tweets = new ArrayList<>();
	
	public TweetAnalyzer() {
		super();
		init();
	}
	
	public TweetAnalyzer(String trainingDataFile, String modelOutputFile) {
		super();
		this.cat = new DocumentCategorizerManager(trainingDataFile, modelOutputFile);
		init();
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

	void init() {
		setCat();
		tc = new TwitterClient(new Config().getProps());
		ldm = new LanguageDetectorManager();
		
	}

	public Object getSAAnalysis(String queryTerms) throws TwitterException {
		this.queryTerms = queryTerms;
		return getSAAnalysis();
	}
	
	public Object getSAAnalysis(String queryTerms, int tweetCount) throws TwitterException {
		this.queryTerms = queryTerms;
		this.tweetCount = tweetCount;
		return getSAAnalysis();
	}

	public String getTweetTextForCategorization(String tweetText) {
		
		String rtnStr = tweetText;
		//thanks to https://stackoverflow.com/questions/8376691/how-to-remove-hashtag-user-link-of-a-tweet-using-regular-expression
		String TWITTER_SCREENNAME_REGEX = "(@[A-Za-z0-9]+)"; //([^0-9A-Za-z \\t]) removes hashtags
		String URL_REGEX = "(\\w+:\\/\\/\\S+)";
		String NEWLINE_REGEX = "(\\r\\n|\\r|\\n)";
		
		rtnStr =  rtnStr.replaceAll(NEWLINE_REGEX, " ");
		
		rtnStr = rtnStr.replaceAll(TWITTER_SCREENNAME_REGEX, " ");
		
		rtnStr = rtnStr.replaceAll(URL_REGEX, " ");
		
		return rtnStr;
	}
	
	public Object getSAAnalysis() throws TwitterException {

		int positive = 0;
		int negative = 0;
		int neutral = 0;
		Object[] returnAry = new Object[2];
		
		tweets = tc.getTweets(this.queryTerms,this.tweetCount);
		
		String msgTemplate = "{0};{1};{2};{3};{4}\n";

		String sentiment;
		StringBuilder tweetSAResults = new StringBuilder("sentiment;tweet;p1;p2;language\n");
		for (Status tweet : tweets) {
			String tweetText = getTweetTextForCategorization(tweet.getText());
			
			//the category, in this use case, sentiment
			sentiment = cat.getBestCategory(tweetText);
			
			//tokens
			String[] textTokens = cat.getTokenize(tweetText);
			// print the probabilities of the categories
			Map<String,Double> probMap = cat.getDoccat().scoreMap(textTokens);
			//and sorted
			Map<Double, Set<String>> sortedMap = cat.getDoccat().sortedScoreMap(textTokens);
			sortedMap = sortedMap.entrySet().stream()
					.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) 			
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
					(oldValue, newValue) -> oldValue, LinkedHashMap::new));
			//language detection
			Language language = ldm.getLanguageDetector().predictLanguage(tweet.getText());
			//for the csv
			tweetText = tweetText.replaceAll(";", "");
			String rtn = MessageFormat.format(msgTemplate, new Object[] 
					{ sentiment, tweetText, probMap.toString(), sortedMap.toString(), language.getLang() });
			tweetSAResults.append(rtn);
			

			if ("positive".equals(sentiment)) {
				positive++;
			} else if ("negative".equals(sentiment)) {
				negative++;
			} else if ("neutral".equals(sentiment)){
				neutral++;
			}
		}
		Map<String, Integer> results = new LinkedHashMap<>();
		results.put("total", tweets.size());
		results.put("positive", positive);		
		results.put("negative", negative);
		results.put("neutral", neutral);
		
		returnAry[0] = results;
		returnAry[1] = tweetSAResults.toString();
		return returnAry;
	}

	
	
	public DocumentCategorizerManager getCat() {
		return cat;
	}

	public void setCat() {
		if(null == this.cat) {
			this.cat = new DocumentCategorizerManager("/etc/config/twitter_sentiment_training_data.train",
					"/etc/config/twitter_sa_model.bin");
		}
		
	}

	public void setCat(DocumentCategorizerManager cat) {
		this.cat = cat;
	}

	

}
