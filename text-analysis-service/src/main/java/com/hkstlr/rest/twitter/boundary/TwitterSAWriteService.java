package com.hkstlr.rest.twitter.boundary;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.hkstlr.twitter.control.FileWR;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

@RequestScoped
@Path("twttr-sa-write")
@Traced
@Timed
public class TwitterSAWriteService {

    private static final Logger LOG = Logger.getLogger(TwitterSAWriteService.class.getName());

    TweetAnalyzerBean tab;

    public TwitterSAWriteService() {
        super();
    }

    @Inject
    public TwitterSAWriteService(TweetAnalyzerBean tab) {
        this.tab = tab;
    }

    @GET
    @Path("/train/{sentiment}/{tweetText}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Counted(name = "train-text", absolute = true, monotonic = true, description = "Number of times the getSA method is requested")
    public String addTrainText(@DefaultValue(value = " ") @PathParam("sentiment") String sentiment,
            @DefaultValue(value = " ") @PathParam("tweetText") String tweetText) throws IOException {
       
        FileWR wr = new FileWR(tab.getTa().getTrainingDataFile());
        String addString = sentiment.concat("\t").concat(tweetText);
        LOG.info("addString:" + addString);
        wr.appendTextToFile(addString);
        return addString.concat(" added");
    }

}
