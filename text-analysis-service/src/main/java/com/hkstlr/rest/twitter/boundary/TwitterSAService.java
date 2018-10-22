package com.hkstlr.rest.twitter.boundary;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import twitter4j.TwitterException;

@Stateless
@Path("twittersa")
public class TwitterSAService {

	private static final Logger LOG = Logger.getLogger(TwitterSAService.class.getName());
		
	TweetAnalyzerBean tab;
		
	public TwitterSAService() {
		super();		
	}

	@Inject
	public TwitterSAService(TweetAnalyzerBean tab) {
		this.tab = tab;	
	}

	@GET
	@Path("/results/{queryTerms}")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Object[] getResults(@DefaultValue(value = "pizza") @PathParam("queryTerms") String queryTerms) {
		return getSentimentAnalysis(queryTerms, tab.getTa().getTweetCount());
	}
	
	@GET
	@Path("/sa/{queryTerms}/{tweetCount}")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Object[] getSA(@PathParam("queryTerms") String queryTerms,
    		@PathParam("tweetCount") int tweetCount) {
		return getSentimentAnalysis(queryTerms, tweetCount);
    }
	
	private Object[] getSentimentAnalysis(String queryTerms, int tweetCount) {
		Object[] results = null;
		try {
			results = (Object[]) tab.getTa().getSentimentAnalysis(queryTerms,tweetCount);
		} catch (TwitterException e) {
			LOG.log(Level.INFO,"", e);
		}		

		return results;
	}
}
