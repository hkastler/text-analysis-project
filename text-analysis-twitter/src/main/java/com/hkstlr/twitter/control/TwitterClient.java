package com.hkstlr.twitter.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient {

	private Twitter twitter;

	private static final Logger LOG = Logger.getLogger(TwitterClient.class.getName());

	public TwitterClient() {
		super();
	}

	public TwitterClient(Properties props) {
		super();
		setTwitter(props);
	}

	public void setTwitter(Properties props) {

		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey(props.getProperty("oAuthConsumerKey"))
				.setOAuthConsumerSecret(props.getProperty("oAuthConsumerSecret"))
				.setOAuthAccessToken(props.getProperty("oAuthAccessToken"))
				.setOAuthAccessTokenSecret(props.getProperty("oAuthAccessTokenSecret"));

		twitter = new TwitterFactory(cb.build()).getInstance();
	}

	public List<Status> getTweets(String queryTerms) {
		return getTweets(queryTerms, 100);
	}

	// http://coding-guru.com/how-to-retrieve-tweets-with-the-twitter-api-and-twitter4j/
	public List<Status> getTweets(String qTerms, int tweetCount, String lang) {
		List<Status> tweets = new ArrayList<>();
		int numberOfTweets = tweetCount;
		int queryCount = 100;
		String queryTerms = qTerms;

		queryTerms.concat("+exclude:retweets");
		Query query = new Query(queryTerms);

		long lastID = Long.MAX_VALUE;

		// filter by lang in query
		if (!lang.isEmpty())
			query.setLang(lang);

		while (tweets.size() < numberOfTweets) {

			if (numberOfTweets - tweets.size() > queryCount) {
				query.setCount(queryCount);
			} else {
				query.setCount(numberOfTweets - tweets.size());
			}
			try {
				QueryResult result = this.twitter.search(query);
				tweets.addAll(result.getTweets());
				if (result.getTweets().size() < queryCount) {
					break;
				}
				for (Status t : tweets) {
					if (t.getId() < lastID)
						lastID = t.getId();
				}

			} catch (TwitterException te) {
				LOG.log(Level.SEVERE, "", te);
			}

			query.setMaxId(lastID - 1);
		}
		return tweets;

	}

	// http://coding-guru.com/how-to-retrieve-tweets-with-the-twitter-api-and-twitter4j/
	public List<Status> getTweets(String queryTerms, int tweetCount) {
		return getTweets(queryTerms, tweetCount, "");

	}

	/**
	 * @return the twitter
	 */
	public Twitter getTwitter() {
		return twitter;
	}

	/**
	 * @param twitter the twitter to set
	 */
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

}
