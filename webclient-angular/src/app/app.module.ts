import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis/twitter-sentiment-analysis.component';
import { AboutComponent } from './about/about.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MessagesComponent } from './messages/messages.component';
import { OverlayModule } from './overlay/overlay.module';
import { EventBusService } from './services/event-bus.service';
import { DonutChartComponent } from './charts/donut-chart/donut-chart.component';
import { DsvTableComponent } from './charts/dsv-table/dsv-table.component';



@NgModule({
  declarations: [
    AppComponent,
    TwitterSentimentAnalysisComponent,
    AboutComponent,
    NavbarComponent,
    MessagesComponent,
    DonutChartComponent,
    DsvTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    OverlayModule
  ],
  providers: [
    EventBusService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
