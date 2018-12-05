import * as d3 from 'd3';
import React from 'react';

class DsvTable extends React.Component{

  constructor(dsvdata, delimiter, target) {
    super();
    this.dsvdata = dsvdata;
    this.delimiter = delimiter;
    this.target = target
  }

  render() {
    return (
      <div id="resultsTable"></div>
    );
  }

  getData(delimiter, dsvdata) {
    var data = d3.dsvFormat(delimiter).parse(dsvdata);
    return data;
  }

  getContainer(target) {
    var container = d3.select(target);
    if (container.empty()) {
      container = d3.select("body").append("div")
      .attr("id",target);
    }
    return container;
  }

  getTable(container, className, caption) {
    var table = container.append("table");
    table.attr("class", className);
    table.append("caption").text(caption);
    return table;
  }

  getHeaders(table, headerData) {
    //table header 
    var thead = table.append("thead").append("tr");
    var headers = thead.selectAll("th")
      .data(headerData).enter()
      .append("th")
      .text(function (headerName) { return headerName; });
    return headers;
  }

  getRows(table, data, headerData) {
    //table body
    var tbody = table.append("tbody");
    var rows = tbody.selectAll("tr")
      //output remaining rows
      .data(data).enter()
      .append("tr");

    // http://bl.ocks.org/AMDS/4a61497182b8fcb05906
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

    var data = this.getData(this.delimiter, this.dsvdata);
    var container = this.getContainer(this.target);
    container.html('');

    var table = this.getTable(container, "table table-striped", "Tweets");
    var headers = d3.keys(data[0]);
    var headerRow = this.getHeaders(table, headers);
    var rows = this.getRows(table, data, headers);

    var sortAscending = true;
    headerRow.on('click', function (d) {
      //reset the headers
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
export default DsvTable;