package com.hkstlr.twitter.control;

import java.text.MessageFormat;
import java.util.ArrayList;
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
        
    private static final String POSITIVE = "positive";
	private static final String NEGATIVE = "negative";
	private static final String NEUTRAL = "neutral";
                
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
		setCat(new DocumentCategorizerManager(trainingDataFile, modelOutputFile));
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

		return rtnStr.replaceAll(newlineRegex, " ").replaceAll(twitterScreennameRegex, " ").replaceAll(urlRegex,
				" ");
	}

	public Object[] getSentimentAnalysis() throws TwitterException {


		String[] headers = { "sentiment", "tweet", POSITIVE, NEGATIVE, NEUTRAL };
		String delimiter = "~";
		String newLine = System.getProperty("line.separator");
		
		String dsvTemplate = getDsvTemplate(headers.length, delimiter, newLine);
		String sentiment;
		String dsvRow;
		String tweetText;

		StringBuilder tweetSAResults = new StringBuilder(MessageFormat.format(dsvTemplate,(Object[]) headers ));
		
		Map<String, Double> probMap;
		Double posScore;
		Double negScore;
		Double neuScore;

		int posCount = 0;
		int negCount = 0;
		int neuCount = 0;
		
		tweets = tc.getTweets(this.queryTerms, this.tweetCount, cat.getLanguageCode());
		
		for (Status tweet : tweets) {
			tweetText = getTweetTextForCategorization(tweet.getText());

			// the probabilities of the categories
			probMap = cat.getDoccat().scoreMap(cat.getTokenize(tweetText));
			// the category, in this use case, sentiment
			sentiment = probMap.entrySet().stream()
							.max(Map.Entry.comparingByValue())
							.map(Map.Entry::getKey)
							.orElse(NEUTRAL);
			
			posScore = probMap.get(POSITIVE);
			negScore = probMap.get(NEGATIVE);
			neuScore = probMap.get(NEUTRAL);

			tweetText = tweetText.replaceAll(delimiter, "&tilde;").replace("\"", "&quot;");
			dsvRow = MessageFormat.format(dsvTemplate, sentiment, tweetText, posScore, negScore, neuScore );
			tweetSAResults.append(dsvRow);

			if (POSITIVE.equals(sentiment)) {
				posCount++;
			} else if (NEGATIVE.equals(sentiment)) {
				negCount++;
			} else if (NEUTRAL.equals(sentiment)) {
				neuCount++;
			}
		}
		
		Map<String, Integer> results = new LinkedHashMap<>();
		results.put("total", tweets.size());
		results.put(POSITIVE, posCount);
		results.put(NEGATIVE, negCount);
		results.put(NEUTRAL, neuCount);

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
