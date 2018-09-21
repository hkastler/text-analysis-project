package com.hkstlr.twitter.control;

import java.io.IOException;
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

import com.hkstlr.text.nlp.control.OpenNLPDocumentCategorizer;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetAnalyzer {

	private static final Logger LOG = Logger.getLogger(TweetAnalyzer.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private OpenNLPDocumentCategorizer cat;
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
		this.cat = new OpenNLPDocumentCategorizer(trainingDataFile, modelOutputFile);
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
		//thanks to https://stackoverflow.com/questions/8376691/how-to-remove-hashtag-user-link-of-a-tweet-using-regular-expression
		String TWITTER_TEXT_REGEX = "(@[A-Za-z0-9]+)|(\\w+:\\/\\/\\S+)|(\\r\\n|\\r|\\n)"; //([^0-9A-Za-z \\t]) removes hashtags
		return tweetText.replaceAll(TWITTER_TEXT_REGEX, " ");
	}
	
	public Object getSAAnalysis() throws TwitterException {

		int positive = 0;
		int negative = 0;
		int neutral = 0;
		
		tweets = tc.getTweets(this.queryTerms,this.tweetCount);

		String msgTemplate = "{0};{1};{2};{3}\n";

		String tweetCategory;
		StringBuilder tweetSAResults = new StringBuilder("sentiment;tweet;p1;p2\n");
		for (Status tweet : tweets) {
			String tweetText = getTweetTextForCategorization(tweet.getText());
			
			String[] tokenText = cat.getTokenize(tweetText);
			// print the probabilities of the categories
			Map<String,Double> probMap = cat.getDoccat().scoreMap(tokenText);
			
			Map<Double, Set<String>> sortedMap = cat.getDoccat().sortedScoreMap(tokenText);
			sortedMap = sortedMap.entrySet().stream()
					.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) 			
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
					(oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			tweetCategory = cat.getBestCategory(tweetText);
			//for the csv
			tweetText = tweetText.replaceAll(";", "");
			String rtn = MessageFormat.format(msgTemplate, new Object[] 
					{ tweetCategory, tweetText, probMap.toString(), sortedMap.toString() });
			tweetSAResults.append(rtn);
			

			if ("positive".equals(tweetCategory)) {
				positive++;
			} else if ("negative".equals(tweetCategory)) {
				negative++;
			} else if ("neutral".equals(tweetCategory)){
				neutral++;
			}
		}
		Map<String, Integer> results = new LinkedHashMap<>();
		results.put("total", tweets.size());
		results.put("positive", positive);		
		results.put("negative", negative);
		results.put("neutral", neutral);
		
		
		Object[] returnAry = new Object[2];
		returnAry[0] = results;
		returnAry[1] = tweetSAResults.toString();
		return returnAry;
	}

	
	
	public OpenNLPDocumentCategorizer getCat() {
		return cat;
	}

	public void setCat() {
		if(null == this.cat) {
			this.cat = new OpenNLPDocumentCategorizer("/etc/config/twitter_sentiment_training_data.train",
					"/etc/config/twitter_sa_model.bin");
		}
		
	}

	public void setCat(OpenNLPDocumentCategorizer cat) {
		this.cat = cat;
	}

	public void writeTweets(String filePath, String tweets) {
		FileWR writer = new FileWR(filePath);
		
		try {
			writer.writeFile(tweets);
		} catch (IOException e) {
			LOG.log(LOG_LEVEL,"",e);
		}
 
		writer.close();
	}

}
