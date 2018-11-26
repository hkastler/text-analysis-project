import { Component, Input, OnInit, OnChanges, ViewEncapsulation } from '@angular/core';
import * as d3 from 'd3';

@Component({
  selector: 'donut-chart',
  templateUrl: './donut-chart.component.html',
  styleUrls: ['./donut-chart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DonutChartComponent implements OnInit {

  @Input() public donutData:object;
  jDonutData: string;

  constructor(){ }

  ngOnInit() {
  }

  init(donutData: object){
    this.donutData = donutData;
  }

  ngOnChanges(){
    this.d3Html();
    this.jDonutData = JSON.stringify(this.donutData);
  }
  //https://github.com/zeroviscosity/d3-js-step-by-step/blob/master/step-3-adding-a-legend.html
  d3Html() {
   
    var mydata = this.donutData;

    var container = d3.select("#resultsChart");
    container.html('');

    if(isNaN(mydata["total"] )){
      return;
    }

    var posPct = (mydata["positive"] / mydata["total"]) * 100;
    var negPct = (mydata["negative"] / mydata["total"]) * 100;
    var neuPct = (mydata["neutral"] / mydata["total"]) * 100;

    var dataset = [
      { label: 'Positive ' + Math.round(posPct) + '%', count: mydata["positive"] },
      { label: 'Negative ' + Math.round(negPct) + '%', count: mydata["negative"] },
      { label: 'Neutral ' + Math.round(neuPct) + '%', count:mydata["neutral"] }
    ];

    var width = 360;
    var height = 360;
    var radius = Math.min(width, height) / 2;
    var donutWidth = 75;
    var legendRectSize = 18;
    var legendSpacing = 4;
    var color = d3.scaleOrdinal(d3.schemeCategory10);
    var figure = container.append("figure");
    var svg = figure.append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', 'translate(' + (width / 2) +
        ',' + (height / 2) + ')');
    var arc = d3.arc()
      .innerRadius(radius - donutWidth)
      .outerRadius(radius);

    var pie = d3.pie()
      .value(function (d:any) { return d.count; })
      .sort(null);

    var path = svg.selectAll('path')
      .data(pie(<any>dataset))
      .enter()
      .append('path')
      .attr('d', <any>arc)
      .attr('fill', function (d:any) {
        return color(d.data.label);
      });

    var pathtip = container
      .append('div')
      .attr('class', 'pathtip');

      pathtip.append('div')
      .attr('class', 'label');

    path.on('mouseover', function (d:any) {
      
      pathtip.select('.label').html(d["data"]["label"]);
      pathtip.style('display', 'block');
      var labelId = d.data.label.substr(0,3).toLowerCase();
      document.getElementById(labelId).classList.add('x-large');
           
    });

    path.on('mouseout', function (d:any) {
      pathtip.style('display', 'none');
      var labelId = d.data.label.substr(0,3).toLowerCase();
      document.getElementById(labelId).classList.remove('x-large');
    });

    var legend = svg.selectAll('.legend')
      .data(color.domain())
      .enter()
      .append('g')
      .attr('class', 'legend')
      .attr('transform', function (d, i) {
        var height = legendRectSize + legendSpacing;
        var offset = height * color.domain().length / 2;
        var horz = -2 * legendRectSize;
        var vert = i * height - offset;
        return 'translate(' + horz + ',' + vert + ')';
      });
    legend.append('rect')
      .attr('width', legendRectSize)
      .attr('height', legendRectSize)
      .style('fill', color)
      .style('stroke', color);
    legend.append('text')
      .attr('id',function (d) { return d.substr(0,3).toLowerCase(); })
      .attr('x', legendRectSize + legendSpacing)
      .attr('y', legendRectSize - legendSpacing)
      .text(function (d) { return d; });
  }

}
