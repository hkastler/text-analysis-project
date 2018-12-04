import * as dev from './environment';
import * as prod from './environment.prod';


describe('Environment', () => {

    it('should create dev', () => {
        expect(dev).toBeTruthy();
        expect(dev.environment.production).toBe(false);
        expect(dev.environment.twitterSentimentAnalysisURL).toBe("//localhost:8080/text-analysis-service/rest/twittersa/sa/");
    });

    it('should create prod', () => {
        expect(prod).toBeTruthy();
        expect(prod.environment.production).toBe(true);
        expect(prod.environment.twitterSentimentAnalysisURL).toBe("//localhost:8080/text-analysis-service/rest/twittersa/sa/");
    });
});