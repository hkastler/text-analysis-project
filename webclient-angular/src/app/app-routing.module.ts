import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis/twitter-sentiment-analysis.component';
import { AboutComponent } from './about/about.component';

const routes: Routes = [

  { path: 'home', component: TwitterSentimentAnalysisComponent },
  { path: 'about', component: AboutComponent },
  { path: '', component: TwitterSentimentAnalysisComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
