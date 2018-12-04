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

  it('should create a div for table', () => {
    let dsvdata = RESPONSE[1].toString();
    let delimiter = "~";    
    component.ngOnInit();
    component.target = "newDivTarget";
    component.init(dsvdata, delimiter);
    component.ngOnChanges();
    let el = d3.select('#newDivTarget');
    expect(el.empty()).toBe(false);
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

  it('should do a click event', () => {
    let dsvdata = RESPONSE[1].toString();
    let delimiter = "~";
    component.target = "resultsTable";
    component.init(dsvdata, delimiter);
    component.ngOnChanges();
    let el = document.querySelector('#d3HtmlTable > thead:nth-child(2) > tr:nth-child(1) > th:nth-child(3)');
    el.dispatchEvent(new MouseEvent('click'));
    
    let clazz = el.getAttribute("class");
    expect(clazz).toBe("aes");

    el.dispatchEvent(new MouseEvent('click'));
    clazz = el.getAttribute("class");
    expect(clazz).toBe("des");
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
