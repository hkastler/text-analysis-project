import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MessageService } from '../messages/message.service';
import { HttpClient } from '@angular/common/http';
import { RESPONSE } from './mock-twitter-sa-response';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TwitterSentimentAnalysisService {

  serviceURL:string;

  constructor(private http: HttpClient,
    private messageService: MessageService) 
    { this.serviceURL = environment.twitterSentimentAnalysisURL}

  public getMockSAResults(): Observable<Object> {
    this.messageService.add('TwitterSentimentAnalysisService.getMockSAResults');
    return of(RESPONSE);
  }

  public getSAResults0(queryTerms: string, tweetCount: number): Observable<Object> {
    
    return this.http.get( this.serviceURL + queryTerms + "/" + tweetCount);  

  }
  public getSAResults(queryTerms: string, tweetCount: number): Observable<Object> {
    
    return this.http.get( this.serviceURL + queryTerms + "/" + tweetCount);  

  }
  
}
