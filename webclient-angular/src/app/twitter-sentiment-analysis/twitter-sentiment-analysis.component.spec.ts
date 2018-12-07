import { TestBed, getTestBed, async, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

import { DsvTableComponent } from '../charts/dsv-table/dsv-table.component';
import { DonutChartComponent } from '../charts/donut-chart/donut-chart.component';
import { LoaderComponent } from '../loader/loader.component';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis.component';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service'
import { of, Observable, throwError } from 'rxjs';

import './mock-twitter-sa-response';
import { RESPONSE } from './mock-twitter-sa-response';

class MockTwitterSentimentAnalysisService {
  getSAResults() {
    return of(RESPONSE);
  }
}

describe('TwitterSentimentAnalysisComponent', () => {
  let component: TwitterSentimentAnalysisComponent;
  let fixture: ComponentFixture<TwitterSentimentAnalysisComponent>;
  let twitterSentimentAnalysisService: TwitterSentimentAnalysisService;

  beforeEach(async(() => {
    let injector;
    
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        HttpClientTestingModule
      ],
      declarations: [
        TwitterSentimentAnalysisComponent,
        DsvTableComponent,
        DonutChartComponent,
        LoaderComponent
      ],
      providers: [
        { provide: TwitterSentimentAnalysisService, useClass: MockTwitterSentimentAnalysisService }
      ]
    })
      .compileComponents();

    injector = getTestBed();
    twitterSentimentAnalysisService = injector.get(TwitterSentimentAnalysisService);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TwitterSentimentAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should get dsvdata', () => {
    component.getSAResults();
    expect(component.dsvData).toBe(RESPONSE[1].toString());
  });

  it('should handle a bad service call', () => {
    twitterSentimentAnalysisService = TestBed.get(TwitterSentimentAnalysisService);
    spyOn(twitterSentimentAnalysisService, 'getSAResults').and.returnValue(throwError('Error'));
    component.getSAResults();
    expect(twitterSentimentAnalysisService.getSAResults).toHaveBeenCalled();
    expect(component.isLoading).toBe(false);

    twitterSentimentAnalysisService = TestBed.get(TwitterSentimentAnalysisService);
    twitterSentimentAnalysisService.getSAResults("chicago pizza",10);
    expect(twitterSentimentAnalysisService.getSAResults).toHaveBeenCalled();
    expect(component.isLoading).toBe(false);
  });

  it('should handle a call', () => {
    twitterSentimentAnalysisService = TestBed.get(TwitterSentimentAnalysisService);
    var obj = twitterSentimentAnalysisService.getSAResults("chicago pizza",10);
    expect(obj).not.toBe(null);
    
  });

});

