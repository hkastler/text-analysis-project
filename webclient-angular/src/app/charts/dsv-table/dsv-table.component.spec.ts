import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DsvTableComponent } from './dsv-table.component';
import { RESPONSE } from '../../twitter-sentiment-analysis/mock-twitter-sa-response';
import * as d3 from 'd3';

describe('DsvTableComponent', () => {
  let component: DsvTableComponent;
  let fixture: ComponentFixture<DsvTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DsvTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DsvTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create a table', () => {
    let dsvdata = RESPONSE[1].toString();
    let delimiter = "~";
    component.target = "resultsTable";
    component.init(dsvdata, delimiter);
    component.ngOnChanges();

    expect(getTable().empty()).toBe(false);
    let table = getTable();
    expect(table.attr('class')).toBe("table table-striped");
    expect(getTable().size()).toBe(1);

    expect(getRows().size()).toBe(100);

    expect(getHeaders().size()).toBe(1);
    var elt = getHeaders().html();
    let headerString =
      "<tr><th>sentiment</th><th>tweet</th><th>positive</th><th>negative</th><th>neutral</th></tr>";
    expect(elt).toBe(headerString);

  });

  function getTable() {
    return d3.select("#d3HtmlTable");
  }

  function getHeaders() {
    return d3.select("#d3HtmlTable > thead");
  }

  function getRows() {
    return d3.selectAll("#d3Tbody > tr");
  }

});
