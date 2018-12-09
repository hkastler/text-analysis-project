import * as dev from './environment';
import * as prod from './environment.prod';
import { PROPERTIES } from './service-properties.js';


describe('Environment', () => {

    it('should create dev', () => {
        expect(dev).toBeTruthy();
        expect(dev.environment.production).toBe(false);
        expect(dev.environment.twitterSentimentAnalysisURL).toContain(PROPERTIES.serviceUrl);
    });

    it('should create prod', () => {
        expect(prod).toBeTruthy();
        expect(prod.environment.production).toBe(true);
        expect(prod.environment.twitterSentimentAnalysisURL).toContain(PROPERTIES.serviceUrl);
    });
});