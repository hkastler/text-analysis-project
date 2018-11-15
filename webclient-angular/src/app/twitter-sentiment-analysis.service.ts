import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { RESPONSE } from './twitter-sentiment-analysis/mock-twitter-sa-response';

@Injectable({
  providedIn: 'root'
})
export class TwitterSentimentAnalysisService {

  constructor(private messageService: MessageService) { }

  public getSAResults(): Observable<Object[]> {
    this.messageService.add('TwitterSentimentAnalysisService: got SA');
    return of(RESPONSE);
  }
}
