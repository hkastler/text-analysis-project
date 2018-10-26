package com.hkstlr.rest.twitter.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import com.hkstlr.twitter.control.TweetAnalyzer;

@Startup
@ApplicationScoped
public class TweetAnalyzerBean {

    TweetAnalyzer ta;

    public TweetAnalyzerBean() {
        super();
    }

    @PostConstruct
    void init() {
        setTa(new TweetAnalyzer());
    }

    /**
     * @return the ta
     */
    TweetAnalyzer getTa() {
        return ta;
    }

    /**
     * @param ta the ta to set
     */
    void setTa(TweetAnalyzer ta) {
        this.ta = ta;
    }

}
