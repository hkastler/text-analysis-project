import { Component, OnInit } from '@angular/core';
import { TwitterSentimentAnalysisService } from './twitter-sentiment-analysis.service'
import { TwitterSentimentAnalyzer } from './twitter-sentiment-analyzer';
import * as d3 from 'd3';
import { DonutChartComponent } from '../charts/donut-chart/donut-chart.component';

@Component({
  selector: 'app-twitter-sentiment-analysis',
  templateUrl: './twitter-sentiment-analysis.component.html',
  styleUrls: ['./twitter-sentiment-analysis.component.css']
})
export class TwitterSentimentAnalysisComponent implements OnInit {

  model: TwitterSentimentAnalyzer;
  resultsTotal: string;

  constructor(private twitterSentimentAnalyisService: TwitterSentimentAnalysisService) { }

  ngOnInit() {
    this.model = new TwitterSentimentAnalyzer("chicago pizza", 100);
  }

  getSAResults(): void {
    this.twitterSentimentAnalyisService
      .getSAResults(this.model.queryTerms, this.model.tweetCount)
      .subscribe(response => {
        this.resultsTotal = JSON.stringify(response[0]);
        
        DonutChartComponent.d3Html(response[0], "#resultsChart");
        
        this.tabulateDsv(response[1], "~", "#resultsTable");
      });
    
   
  }

  tabulateDsv(dsvdata, delimiter, target) {

    var data = d3.dsvFormat(delimiter).parse(dsvdata);
    var cols = d3.keys(data[0]);

    var container = d3.select(target);
    container.html('');

    var table = container.append("table")
      .attr("class", "table table-striped");
    var caption = table.append("caption").text("Tweets");
   
    var sortAscending = true;
    //table header 
    var thead = table.append("thead").append("tr");
    var headers = thead.selectAll("th")
      .data(cols).enter()
      .append("th")
      .text(function (col) { return col; });

      headers.on('click', function (d) {
        //reset the headers
        headers.attr('class', 'header');

        if (sortAscending) {
            rows.sort(function(a,b){
              return d3.ascending(a[d] , b[d]);
            });
            sortAscending = false;
            this["className"] = 'aes';
        } else {
            rows.sort(function(a,b){
              return d3.descending(a[d] , b[d]);
            });
            sortAscending = true;
            this["className"] = 'des';
        }

    });
    //delete the header row
    data.shift();

    //table body
    var tbody = table.append("tbody");

    var rows = tbody.selectAll("tr")
      //output remaining rows
      .data(data).enter()
      .append("tr");

    // http://bl.ocks.org/AMDS/4a61497182b8fcb05906
    rows.selectAll('td')
      .data(function (d) {
        return cols.map(function (k) {
          return { 'value': d[k], 'name': k };
        });
      }).enter()
      .append('td')
      .attr('data-th', function (d) {
        return d.name;
      })
      .text(function (d) {
        return d.value.replace(/&quot;/g, "\"").replace(/&tilde;/g, "~");
      });

      
  }

  

}
