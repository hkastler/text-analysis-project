package com.hkstlr.rest.twitter.boundary;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.hkstlr.rest.control.WriteServiceEvent;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

@RequestScoped
@Path("twttr-sa-write")
@Traced
@Timed
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TwitterSAWriteService {

    TweetAnalyzerBean tab;

    public TwitterSAWriteService() {
        super();
    }

    @Inject
    public TwitterSAWriteService(TweetAnalyzerBean tab) {
        this.tab = tab;
    }

    @Inject
    Event<WriteServiceEvent> wse;

    @GET
    @Path("/train/{sentiment}/{tweetText}")
    @Counted(name = "addTrainText", absolute = true, monotonic = true, description = "Number of times the addTrainText method is requested")
    public String addTrainText(@DefaultValue(value = " ") @PathParam("sentiment") String sentiment,
            @DefaultValue(value = " ") @PathParam("tweetText") String tweetText) throws IOException {

        WriteServiceEvent writeServiceEvent = new WriteServiceEvent();
        writeServiceEvent.setName("addTrainText");
        String[] payload = new String[3];
        payload[0] = sentiment;
        payload[1] = tweetText;
        payload[2] = tab.getTa().getTrainingDataFile();
        writeServiceEvent.setPayload(payload);
        wse.fire(writeServiceEvent);

        return tweetText.concat(" trained to ").concat(sentiment);
    }

}
