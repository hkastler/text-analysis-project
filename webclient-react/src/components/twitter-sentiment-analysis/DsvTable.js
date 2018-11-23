import * as d3 from 'd3';

class DsvTable {

  constructor(dsvdata, delimiter, target) {
    this.dsvdata = dsvdata;
    this.delimiter = delimiter;
    this.target = target
  }

  d3Html() {

    var data = d3.dsvFormat(this.delimiter).parse(this.dsvdata);
    var cols = d3.keys(data[0]);

    var container = d3.select(this.target);
    container.html('');

    var table = container.append("table")
      .attr("class", "table table-striped");
    table.append("caption").text("Tweets");

    var sortAscending = true;
    //table header 
    var thead = table.append("thead").append("tr");
    var headers = thead.selectAll("th")
      .data(cols).enter()
      .append("th")
      .text(function (col) { return col; });

    headers.on('click', function (d) {
      //reset the headers
      headers.attr('class', 'header');

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
    //delete the header row
    data.shift();

    //table body
    var tbody = table.append("tbody");

    var rows = tbody.selectAll("tr")
      //output remaining rows
      .data(data).enter()
      .append("tr");

    // http://bl.ocks.org/AMDS/4a61497182b8fcb05906
    rows.selectAll('td')
      .data(function (d) {
        return cols.map(function (k) {
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
  }
}
export default DsvTable;