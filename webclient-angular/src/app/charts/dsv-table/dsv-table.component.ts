import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import * as d3 from 'd3';


@Component({
  selector: 'dsv-table',
  templateUrl: './dsv-table.component.html',
  styleUrls: ['./dsv-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DsvTableComponent implements OnInit {

  @Input() public dsvdata: string;
  @Input() public delimiter: string;
  target: string;

  constructor() { }

  ngOnInit() {
    this.target = "resultsTable";
  }

  init(dsvdata: string, delimiter: string) {
    this.dsvdata = dsvdata;
    this.delimiter = delimiter;
  }

  ngOnChanges() {
    if(this.dsvdata.length > 0){
      this.d3Html();
    }
  }

  parseData(delimiter, dsvdata) {
    let data = d3.dsvFormat(delimiter).parse(dsvdata, function(line){
      return line;
    });
    return data;
  }

  getContainer(target) {
    let container;
    container= d3.select("#"+target);
    if (container.empty()) {
      container = d3.select("body").append("div")
      .attr("id",target);
    }
    return container;
  }

  getTable(container, id, className, caption) {
    let table = container.append("table");
    table.attr("id", id)
    table.attr("class", className);
    table.append("caption").text(caption);
    return table;
  }

  getHeaders(table, headerData) {
    let thead = table.append("thead").append("tr");
    let headers = thead.selectAll("th")
      .data(headerData).enter()
      .append("th")
      .text(function (headerName) { return headerName; });
    return headers;
  }

  getRows(table, data, headerData) {
    let tbody = table.append("tbody");
    tbody.attr("id", "d3Tbody");    
    let rows = tbody.selectAll("tr")
      .data(data).enter()
      .append("tr");
      
     rows.selectAll('td')
      .data(function (d) {
        return headerData.map(function (k) {
          return { 'value': d[k], 'name': k };
        });
      }).enter()
      .append('td')
      .attr('data-th', function (d) {
        return d.name;
      })
      .text(function (d) {
        return d.value.replace(/&quot;/g, "\"").replace(/&tilde;/g, "~");
      });

    return rows;
  }


  d3Html() {
    
    let parsedData = this.parseData(this.delimiter,this.dsvdata);
       
    let container = this.getContainer(this.target);
    container.html('');
    
    let table = this.getTable(container, "d3HtmlTable", "table table-striped", "Tweets");
    let headers = d3.keys(parsedData[0]);
    let headerRow = this.getHeaders(table, headers);
    let rows = this.getRows(table, parsedData, headers);

    let sortAscending = true;
    headerRow.on('click', function (d) {
      headerRow.attr('class', 'header');
      if (sortAscending) {
        rows.sort(function (a, b) {
          return d3.ascending(a[d], b[d]);
        });
        sortAscending = false;
        this["className"] = 'aes';
      } else {
        rows.sort(function (a, b) {
          return d3.descending(a[d], b[d]);
        });
        sortAscending = true;
        this["className"] = 'des';
      }
    });
  }

}
