/* tslint:disable:no-unused-variable */
import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";


import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component'
import { AboutComponent } from './about/about.component';
import { TwitterSentimentAnalysisComponent } from './twitter-sentiment-analysis/twitter-sentiment-analysis.component';
import { AppRoutingModule, routes } from './app-routing.module';
import { DonutChartComponent } from './charts/donut-chart/donut-chart.component';
import { DsvTableComponent } from './charts/dsv-table/dsv-table.component';
import { LoaderComponent } from './loader/loader.component';
import { NgZone } from '@angular/core';
import { FormsModule } from '@angular/forms';


describe('Router: App', () => {
    let location: Location;
    let router: Router;
    let ngZone: NgZone;

    // Configure router testing module
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                FormsModule,
                AppRoutingModule,
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                AppComponent,
                NavbarComponent,
                AboutComponent,
                TwitterSentimentAnalysisComponent,
                DonutChartComponent,
                DsvTableComponent,
                LoaderComponent
            ],
            providers: [Location]
        }).compileComponents();

        router = TestBed.get(Router);
        location = TestBed.get(Location);
        ngZone = TestBed.get(NgZone);
    });

    // Test for asyncFake
    it('fakeAsync works', fakeAsync(() => {
        const promise = new Promise(resolve => {
            setTimeout(resolve, 10);
        });
        let done = false;
        promise.then(() => done = true);
        tick(50);
        expect(done).toBeTruthy();
    }));


    it('routes correctly ', fakeAsync(() => {
        routes.forEach(
            (r) => {
                let calledPath = "/" + r.path;
                let expectedPath = (r.path != '') ? "/" + r.path : '';
                ngZone.run(() => {
                    checkPath(router, location, calledPath, expectedPath);
                });
            }
        );
    }));

    function checkPath(router, location, path, expected) {
        router.navigateByUrl(path);
        tick();
        expect(location.path()).toBe(expected);
    }
});

