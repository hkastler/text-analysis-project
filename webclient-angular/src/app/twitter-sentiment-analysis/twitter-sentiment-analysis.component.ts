import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service'
import { TwitterSentimentAnalyzer } from './twitter-sentiment-analyzer';

import { DonutChartComponent } from '../charts/donut-chart/donut-chart.component';
import { DsvTableComponent } from '../charts/dsv-table/dsv-table.component';

@Component({
  selector: 'app-twitter-sentiment-analysis',
  templateUrl: './twitter-sentiment-analysis.component.html',
  styleUrls: ['./twitter-sentiment-analysis.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TwitterSentimentAnalysisComponent implements OnInit {

  model: TwitterSentimentAnalyzer;
  resultsTotal: string;
  serviceResponse: object;
  dsvTable: DsvTableComponent;
  donutChart:DonutChartComponent;

  constructor(private twitterSentimentAnalyisService: TwitterSentimentAnalysisService) { }

  ngOnInit() {
    this.model = new TwitterSentimentAnalyzer("thanksgiving leftovers", 100);
    this.dsvTable = new DsvTableComponent();
    this.donutChart = new DonutChartComponent();
  }

  getSAResults(): void {
    this.twitterSentimentAnalyisService
      .getSAResults(this.model.queryTerms, this.model.tweetCount)
      .subscribe(response => {
        
        this.serviceResponse = response; 
      }, (err) => {
        console.log(err);
      }, () => {
        if(Array.isArray(this.serviceResponse)){

          this.handleResponse();
        }
      });
  }

  handleResponse() {
    
      this.resultsTotal = JSON.stringify(this.serviceResponse[0]);
      this.getD3DonutChart(this.serviceResponse[0]);
      this.getD3Table(this.serviceResponse[1]);
    
  }

  async getD3DonutChart(data){    
    this.donutChart.init(data);
    this.donutChart.d3Html();
  }
  async getD3Table(data){    
    this.dsvTable.init(data,"~");
    this.dsvTable.d3Html();
  }

}
