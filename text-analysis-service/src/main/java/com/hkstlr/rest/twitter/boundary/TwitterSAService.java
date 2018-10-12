package com.hkstlr.rest.twitter.boundary;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hkstlr.twitter.control.TweetAnalyzer;

import twitter4j.TwitterException;

@Stateless
@Path("twittersa")
public class TwitterSAService {

	private static final Logger LOG = Logger.getLogger(TwitterSAService.class.getName());
	
	@Inject
	TweetAnalyzerBean tab;
	
	private TweetAnalyzer ta;
	
	public TwitterSAService() {
		super();
		
	}
	
	@PostConstruct
	void init() {
		this.ta = tab.getTa();
	}

	@GET
	@Path("/results/{queryTerms}")
	@Produces(MediaType.APPLICATION_JSON)
    public Object[] getResults(@DefaultValue(value = "pizza") @PathParam("queryTerms") String queryTerms) {
		return getSentimentAnalysis(queryTerms, ta.getTweetCount());
    }
	@GET
	@Path("/sa/{queryTerms}/{tweetCount}")
	@Produces(MediaType.APPLICATION_JSON)
    public Object[] getSA(@PathParam("queryTerms") String queryTerms,
    		@PathParam("tweetCount") int tweetCount) {
		return getSentimentAnalysis(queryTerms, tweetCount);
    }
	
	private Object[] getSentimentAnalysis(String queryTerms, int tweetCount) {
		Object[] results = null;
		try {
			results = (Object[]) ta.getSentimentAnalysis(queryTerms,tweetCount);
		} catch (TwitterException e) {
			LOG.log(Level.INFO,"", e);
		}
		Map<String,String> resultMap = (Map<String, String>) results[0];
		String csv = (String)results[1];
		Object[] obj = new Object[2];
		obj[0] = resultMap;
		obj[1] = csv;

		return obj;
	}
}
