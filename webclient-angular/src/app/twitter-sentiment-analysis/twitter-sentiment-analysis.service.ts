import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MessageService } from '../messages/message.service';
import { HttpClient } from '@angular/common/http';
import { RESPONSE } from './mock-twitter-sa-response';

@Injectable({
  providedIn: 'root'
})
export class TwitterSentimentAnalysisService {

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  public getMockSAResults(): Observable<Object> {
    this.messageService.add('TwitterSentimentAnalysisService: got SA');
    return of(RESPONSE);
  }

  public getSAResults(queryTerms: string, tweetCount: number): Observable<Object> {
    this.messageService.add('TwitterSentimentAnalysisService.getSAResults');
    var port = window.location.href.indexOf("https://") == 0 ? "8443" : "8080";

    var serviceLoc = "//" + window.location.hostname + ":" + port;
    var serviceUrl = '/text-analysis-service/rest/twittersa/sa/';
    
    return this.http.get(serviceLoc + serviceUrl + queryTerms + "/" + tweetCount);  

  }
}
