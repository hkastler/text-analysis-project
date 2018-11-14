import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis.component';

describe('TwitterSentimentAnalysisComponent', () => {
  let component: TwitterSentimentAnalysisComponent;
  let fixture: ComponentFixture<TwitterSentimentAnalysisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TwitterSentimentAnalysisComponent ]
    })
    .compileComponents();
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
