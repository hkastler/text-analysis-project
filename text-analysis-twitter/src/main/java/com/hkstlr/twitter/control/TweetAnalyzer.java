package com.hkstlr.twitter.control;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TweetAnalyzer {

	private DocumentCategorizerManager cat;
	private static final String TRAINING_DATA_FILEPATH = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_training_data.train";
	private static final String MODEL_OUT_FILEPATH = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sa_model.bin";

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
	}

	public Object getSentimentAnalysis(String queryTerms) throws TwitterException {
		this.queryTerms = queryTerms;
		return getSentimentAnalysis();
	}

	public Object getSentimentAnalysis(String queryTerms, int tweetCount) throws TwitterException {
		this.queryTerms = queryTerms;
		this.tweetCount = tweetCount;
		return getSentimentAnalysis();
	}

	public String getTweetTextForCategorization(String tweetText) {

		String rtnStr = tweetText;
		// thanks to
		// https://stackoverflow.com/questions/8376691/how-to-remove-hashtag-user-link-of-a-tweet-using-regular-expression
		String TWITTER_SCREENNAME_REGEX = "(@[A-Za-z0-9]+)"; // ([^0-9A-Za-z \\t]) removes hashtags
		String URL_REGEX = "(\\w+:\\/\\/\\S+)";
		String NEWLINE_REGEX = "(\\r\\n|\\r|\\n)";

		return rtnStr.replaceAll(NEWLINE_REGEX, " ").replaceAll(TWITTER_SCREENNAME_REGEX, " ").replaceAll(URL_REGEX,
				" ");
	}

	public Object getSentimentAnalysis() throws TwitterException {

		tweets = tc.getTweets(this.queryTerms, this.tweetCount, cat.getLanguageCode());

		String[] headers = { "sentiment", "tweet", "scores" };
		String delimiter = "~";
		String newLine = "\n";
		
		String dsvTemplate = getDsvTemplate(headers.length, delimiter, newLine);
		String sentiment;
		String dsvRow;
		String tweetText;

		StringBuilder tweetSAResults = new StringBuilder(MessageFormat.format(dsvTemplate, headers ));
		
		Map<String, Double> probMap;
		int positive = 0;
		int negative = 0;
		int neutral = 0;
		
		for (Status tweet : tweets) {
			tweetText = getTweetTextForCategorization(tweet.getText());

			// the probabilities of the categories
			probMap = cat.getDoccat().scoreMap(cat.getTokenize(tweetText));
			// the category, in this use case, sentiment
			// sentiment = cat.getBestCategory(tweetText);
			sentiment = probMap.entrySet().stream()
							.max(Map.Entry.comparingByValue())
							.map(Map.Entry::getKey)
							.orElse("neutral");

			tweetText = tweetText.replaceAll(delimiter, "&tilde;").replace("\"", "&quot;");
			dsvRow = MessageFormat.format(dsvTemplate, new Object[] { sentiment, tweetText, probMap.toString() });
			tweetSAResults.append(dsvRow);

			if ("positive".equals(sentiment)) {
				positive++;
			} else if ("negative".equals(sentiment)) {
				negative++;
			} else if ("neutral".equals(sentiment)) {
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

	public String getDsvTemplate(int cols, String delimiter, String newLine) {
		StringBuilder msgSb = new StringBuilder();
		for (int x = 0; x <= cols - 1; x++) {
			msgSb.append("{").append(Integer.toString(x)).append("}");
			if(x < cols-1){
				msgSb.append(delimiter);
			}
				
		}
		msgSb.append(newLine);

		return msgSb.toString();
	}

	public DocumentCategorizerManager getCat() {
		return cat;
	}

	public void setCat() {
		if (null == this.cat) {
			this.cat = new DocumentCategorizerManager(TRAINING_DATA_FILEPATH, MODEL_OUT_FILEPATH);
		}

	}

	public void setCat(DocumentCategorizerManager cat) {
		this.cat = cat;
	}

	public static String getTrainingDataFilepath() {
		return TRAINING_DATA_FILEPATH;
	}

	public static String getModelOutFilepath() {
		return MODEL_OUT_FILEPATH;
	}

}
