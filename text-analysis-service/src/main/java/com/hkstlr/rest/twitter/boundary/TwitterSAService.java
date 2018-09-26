package com.hkstlr.rest.twitter.boundary;

import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hkstlr.twitter.control.TweetAnalyzer;

import twitter4j.TwitterException;

@Path("twittersa")
public class TwitterSAService {

	
	private TweetAnalyzer ta;
	
	
	public TwitterSAService() {
		super();
		ta = new TweetAnalyzer(
				Paths.get("src","main","resources","twitter_sentiment_training_data.train")
				.toString(),
				"");
	}

	@GET
	@Path("/results/{queryTerms}")
	@Produces(MediaType.APPLICATION_JSON)
    public String[] getResults(@PathParam("queryTerms") String queryTerms) {
		Object[] results = null;
		try {
			results = (Object[]) ta.getSAAnalysis(queryTerms);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String csv = (String)results[1];
		String[] obj = new String[2];
		obj[0] = results[0].toString();
		obj[1] = csv;
				
		return obj;
    }
}
