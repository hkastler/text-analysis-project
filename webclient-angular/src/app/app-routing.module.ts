import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis/twitter-sentiment-analysis.component';
import { AboutComponent } from './about/about.component';
import {APP_BASE_HREF} from '@angular/common';

export const routes: Routes = [
  { path: '', component: TwitterSentimentAnalysisComponent },
  { path: 'home', component: TwitterSentimentAnalysisComponent },
  { path: 'about', component: AboutComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{provide: APP_BASE_HREF, useValue: '/text-analysis-webapp/'}]
})
export class AppRoutingModule { }
