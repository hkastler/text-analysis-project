package com.hkstlr.twitter.control;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.hkstlr.text.nlp.control.DocumentCategorizer;
import com.hkstlr.text.nlp.control.OpenNLPDocumentCategorizer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetAnalyzer {

	private static final Logger LOG = Logger.getLogger(TweetAnalyzer.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private DocumentCategorizer cat;
	private Twitter twitter;
	private String queryTerms;

	public TweetAnalyzer() {
		super();
		init();
	}
	
	public TweetAnalyzer(String trainingDataFile, String modelOutputFile) {
		super();
		this.cat =  new OpenNLPDocumentCategorizer(trainingDataFile, modelOutputFile);
		init();
	}

	public String getQueryTerms() {
		return queryTerms;
	}

	public void setQueryTerms(String queryTerms) {
		this.queryTerms = queryTerms;
	}

	void init() {
		getCat();
		twitter = new TwitterClient().getTwitter(new Config().getProps());
	}

	public Object getSAAnalysis(String queryTerms) throws TwitterException {
		this.queryTerms = queryTerms;
		return getSAAnalysis();
	}

	public Object getSAAnalysis() throws TwitterException {

		int positive = 0;
		int negative = 0;
		int neutral = 0;

		this.queryTerms += " +exclude:retweets";

		Query query = new Query(this.queryTerms);
		query.setCount(100);

		QueryResult tweets = this.twitter.search(query);

		String msgTemplate = "{0}:{1}\n {2} ";

		String tresult;
		StringBuilder probResults = new StringBuilder();
		for (Status tweet : tweets.getTweets()) {

			Object[] outcomeAndtresult = this.cat.getCategorizeAndBestCategory(tweet.getText());
			double[] outcome = (double[]) outcomeAndtresult[0];

			// print the probabilities of the categories
			Map<String,Double> probMap = new HashMap<>();
			//probs.append("category:probability\n");
			for (int i = 0; i < cat.getDoccat().getNumberOfCategories(); i++) {
				probMap.put(cat.getDoccat().getCategory(i), outcome[i]);
				
			}

			probMap = probMap.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(
								Map.Entry::getKey, 
								Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
			tresult = (String) outcomeAndtresult[1];

			if ("positive".equals(tresult)) {
				positive++;
			} else if ("negative".equals(tresult)) {
				negative++;
			} else {
				neutral++;
			}
			
			String rtn = MessageFormat.format(msgTemplate, new Object[] 
					{ tresult, tweet.getText() , probMap.toString() });
			probResults.append(rtn);
			probResults.append("\n-------------------\n");
		}
		StringBuilder sb = new StringBuilder("Positive Tweets,").append(Integer.toString(positive)).append("\n")
				.append("Negative Tweets,").append(Integer.toString(negative)).append("\n")
				.append("Neutral Tweets, ").append(Integer.toString(neutral));
		Object[] returnAry = new Object[2];
		returnAry[0] = sb.toString();
		returnAry[1] = probResults.toString();
		return returnAry;
	}

	public DocumentCategorizer getCat() {
		if(null == cat) {
			cat = new OpenNLPDocumentCategorizer("/etc/config/twitter_sentiment_training_data.train",
					"/etc/config/twitter_sa_model.bin");
		}
		
		return cat;
	}

	public void setCat(DocumentCategorizer cat) {
		this.cat = cat;
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

}
