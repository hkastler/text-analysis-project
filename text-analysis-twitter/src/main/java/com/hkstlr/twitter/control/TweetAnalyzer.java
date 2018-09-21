package com.hkstlr.twitter.control;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
		getCat();
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

		String msgTemplate = "{0};{1};{2}\n";

		String tweetCategory;
		StringBuilder probResults = new StringBuilder("sentiment;tweet;probabilities\n");
		for (Status tweet : tweets) {
			String tweetText = getTweetTextForCategorization(tweet.getText());
			Object[] outcomeAndtresult = this.cat.getCategorizeAndBestCategory(tweetText);
			double[] outcome = (double[]) outcomeAndtresult[0];

			// print the probabilities of the categories
			Map<String,Double> probMap = new HashMap<>();
			
			for (int i = 0; i < cat.getDoccat().getNumberOfCategories(); i++) {
				probMap.put(cat.getDoccat().getCategory(i), outcome[i]);
				
			}
			probMap = probMap.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(
								Map.Entry::getKey, 
								Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
			tweetCategory = (String) outcomeAndtresult[1];

			if ("positive".equals(tweetCategory)) {
				positive++;
			} else if ("negative".equals(tweetCategory)) {
				negative++;
			} else if ("neutral".equals(tweetCategory)){
				neutral++;
			}
			
			String rtn = MessageFormat.format(msgTemplate, new Object[] 
					{ tweetCategory, tweetText, probMap.toString() });
			probResults.append(rtn);
			
		}
		Map<String, Integer> results = new LinkedHashMap<>();
		results.put("total", tweets.size());
		results.put("positive", positive);		
		results.put("negative", negative);
		results.put("neutral", neutral);
		
		
		Object[] returnAry = new Object[2];
		returnAry[0] = results;
		returnAry[1] = probResults.toString();
		return returnAry;
	}

	public void getCat() {
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
