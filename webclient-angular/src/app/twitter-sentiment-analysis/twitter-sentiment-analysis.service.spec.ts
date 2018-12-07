import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service';

describe('TwitterSentimentAnalysisService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule
    ],
  }));

  it('should be created', () => {
    const service: TwitterSentimentAnalysisService = TestBed.get(TwitterSentimentAnalysisService);
    expect(service).toBeTruthy();

   expect(service.serviceURL).toContain("/text-analysis-service/rest/twittersa/sa/");  
  });
});
