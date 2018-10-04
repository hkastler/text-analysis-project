package com.hkstlr.rest.twitter.boundary;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hkstlr.twitter.control.TweetAnalyzer;

import twitter4j.TwitterException;

@Path("twittersa")
public class TwitterSAService {

	private static final Logger LOG = Logger.getLogger(TwitterSAService.class.getName());
	
	private TweetAnalyzer ta;
	
	
	public TwitterSAService() {
		super();
		ta = new TweetAnalyzer(
				TweetAnalyzer.getTrainingDataFilepath(),
				"");
	}

	@GET
	@Path("/results/{queryTerms}")
	@Produces(MediaType.APPLICATION_JSON)
    public String[] getResults(@PathParam("queryTerms") String queryTerms) {
		return getSentimentAnalysis(queryTerms, ta.getTweetCount());
    }
	@GET
	@Path("/sa/{queryTerms}/{tweetCount}")
	@Produces(MediaType.APPLICATION_JSON)
    public String[] getSA(@PathParam("queryTerms") String queryTerms,
    		@PathParam("tweetCount") int tweetCount) {
		return getSentimentAnalysis(queryTerms, tweetCount);
    }
	
	private String[] getSentimentAnalysis(String queryTerms, int tweetCount) {
		Object[] results = null;
		try {
			results = (Object[]) ta.getSentimentAnalysis(queryTerms,tweetCount);
		} catch (TwitterException e) {
			LOG.log(Level.INFO,"", e);
		}
		String csv = (String)results[1];
		String[] obj = new String[2];
		obj[0] = results[0].toString();
		obj[1] = csv;

		return obj;
	}
}
