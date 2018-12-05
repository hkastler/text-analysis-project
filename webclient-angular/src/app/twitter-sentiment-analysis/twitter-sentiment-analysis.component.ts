import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service'
import { TwitterSentimentAnalyzer } from './twitter-sentiment-analyzer';


@Component({
  selector: 'app-twitter-sentiment-analysis',
  templateUrl: './twitter-sentiment-analysis.component.html',
  styleUrls: ['./twitter-sentiment-analysis.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TwitterSentimentAnalysisComponent implements OnInit {

  @Input() model: TwitterSentimentAnalyzer;
  resultsTotal: string;
  isLoading: boolean;
  dsvData: string;
  delimiter: string;
  donutChartObj: object;
  
  constructor(private twitterSentimentAnalyisService: TwitterSentimentAnalysisService) { }

  ngOnInit() {
    this.model = new TwitterSentimentAnalyzer("chicago pizza", 100);
    this.dsvData = "";
    this.delimiter = "~";
    this.donutChartObj = {};
  }

  getSAResults(): void {
    this.isLoading = true;
    this.twitterSentimentAnalyisService
      .getSAResults(this.model.queryTerms, this.model.tweetCount)
      .subscribe(
        response => {
          this.donutChartObj = response[0];
          this.dsvData = response[1]; 
        }, 
        err => {
          this.isLoading = false;
          console.error('err:', err.message);
        }, 
        () => {
          this.isLoading = false;
        }
      );
  }

}
