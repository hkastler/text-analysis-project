import { TestBed } from '@angular/core/testing';

import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service';

describe('TwitterSentimentAnalysisService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TwitterSentimentAnalysisService = TestBed.get(TwitterSentimentAnalysisService);
    expect(service).toBeTruthy();
  });
});
