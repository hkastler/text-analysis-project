import { TestBed, getTestBed, async, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

import { DsvTableComponent } from '../charts/dsv-table/dsv-table.component';
import { DonutChartComponent } from '../charts/donut-chart/donut-chart.component';
import { LoaderComponent } from '../loader/loader.component';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis.component';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service'
import { of } from 'rxjs';
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

  beforeEach(async(() => {
    let injector;
    let twitterSentimentAnalysisService: TwitterSentimentAnalysisService;

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
});

