import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { RESPONSE } from './mock-twitter-sa-response';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TwitterSentimentAnalysisService {

  serviceURL:string;

  constructor(private http: HttpClient) 
    { this.serviceURL = environment.twitterSentimentAnalysisURL}

  public getMockSAResults(): Observable<Object> {    
    return of(RESPONSE);
  }

  public getSAResults(queryTerms: string, tweetCount: number): Observable<Object> {
    
    return this.http.get( this.serviceURL + queryTerms + "/" + tweetCount);  

  }
}
