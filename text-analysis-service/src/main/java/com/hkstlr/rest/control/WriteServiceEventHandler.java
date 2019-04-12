package com.hkstlr.rest.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import com.hkstlr.twitter.control.FileWR;

@Stateless
public class WriteServiceEventHandler {

    static final Logger LOG = Logger.getLogger(WriteServiceEventHandler.class.getCanonicalName());

    public WriteServiceEventHandler() {
        super();
    }

    @Asynchronous
    public void handle(@Observes WriteServiceEvent event) {
        String eventName = event.getName();
        Object payload = event.getPayload();
        
        if (null != eventName) switch (eventName) {
            case "addTrainText":
                addTrainText((String[]) payload);
                break;
            default:
                break;
        }
    }
    
    
    void addTrainText(String[] payload){
        
        String sentiment = payload[0];
        String trainText = payload[1];
        String file = payload[2];
        
        FileWR wr = new FileWR(file);
        String addString = sentiment.concat("\t").concat(trainText);
        String wrText = System.getProperty("line.separator") + addString;

        try {
            wr.appendTextToFile(wrText);
        } catch (IOException e) {
           LOG.log(Level.SEVERE,"addTrainText:",e);
        }
    }


}
