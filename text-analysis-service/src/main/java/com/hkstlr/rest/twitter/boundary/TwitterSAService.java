package com.hkstlr.rest.twitter.boundary;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import twitter4j.TwitterException;

@RequestScoped
@Path("twittersa")
@Traced
@Timed
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
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
    @Counted(name = "getResultsCount", absolute = true, monotonic = true, description = "Number of times the getResults method is requested")
    public Object[] getResults(@DefaultValue(value = " ") @PathParam("queryTerms") String queryTerms) {
        Optional<Object[]> response = Optional
                .ofNullable(getSentimentAnalysis(queryTerms, tab.getTa().getTweetCount()));
        return response.orElse(new Object[2]);
    }

    @GET
    @Path("/sa/{queryTerms}/{tweetCount}")
    @Counted(name = "getSACount", absolute = true, monotonic = true, description = "Number of times the getSA method is requested")
    public Object[] getSA(@DefaultValue(value = " ") @PathParam("queryTerms") String queryTerms,
            @DefaultValue(value = "1") @PathParam("tweetCount") int tweetCount) {
        Optional<Object[]> response = Optional.ofNullable(getSentimentAnalysis(queryTerms, tweetCount));
        return response.orElse(new Object[2]);

    }

    private Object[] getSentimentAnalysis(String queryTerms, int tweetCount) {

        try {
            return tab.getTa().getSentimentAnalysis(queryTerms, tweetCount);
        } catch (NullPointerException | TwitterException e) {
            LOG.log(Level.INFO, "", e);
        }

        return new Object[2];
    }
}
