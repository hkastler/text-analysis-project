import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DonutChartComponent } from './donut-chart.component';
import { RESPONSE } from '../../twitter-sentiment-analysis/mock-twitter-sa-response';
import * as d3 from 'd3';

describe('DonutChartComponent', () => {
  let component: DonutChartComponent;
  let fixture: ComponentFixture<DonutChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DonutChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DonutChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should create a donut chart', () => {
    component.ngOnInit();
    component.init(RESPONSE[0])
    component.ngOnChanges();
    expect(component).toBeTruthy();
    expect(getDonut().size()).toBe(1);
    expect(getPaths().size()).toBe(3);
    expect(getLegends().size()).toBe(3);
    expect(getPathtip().empty()).toBe(false);
  });

  function getDonut() {
    return d3.select("svg");
  }

  function getPaths(){
    return d3.selectAll("#resultsChart > figure > svg > g > path");
  }

  function getLegends() {
    return d3.selectAll("g.legend");
  }

  function getPathtip() {
    return d3.select(".pathtip");
  }
  
});
