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
  width: number;
  height: number;
  donutWidth: number;
  legendRectSize: number;
  legendSpacing: number;
  radius: number;

  constructor(){ }

  ngOnInit() {
   this.width = 360;
   this.height = 360;
   this.donutWidth = 75;
   this.legendRectSize = 18;
   this.legendSpacing = 4;
   this.radius = Math.min(this.width, this.height) / 2;
   this.jDonutData = "";
  }

  init(donutData: object){
    this.donutData = donutData;
  }

  ngOnChanges(){
    this.d3Html();
    this.jDonutData = JSON.stringify(this.donutData);
  }

  d3Html() {
   
    let mydata = this.donutData;
    let container = this.getContainer("resultsChart");

    if(isNaN(mydata["total"] )){
      return;
    }

    let posPct = (mydata["positive"] / mydata["total"]) * 100;
    let negPct = (mydata["negative"] / mydata["total"]) * 100;
    let neuPct = (mydata["neutral"] / mydata["total"]) * 100;

    let dataset = [
      { label: 'Positive ' + Math.round(posPct) + '%', count: mydata["positive"] },
      { label: 'Negative ' + Math.round(negPct) + '%', count: mydata["negative"] },
      { label: 'Neutral ' + Math.round(neuPct) + '%', count:mydata["neutral"] }
    ];

    let svg = this.getSvg(container);

    let color = d3.scaleOrdinal(d3.schemeCategory10);
   
    let path = this.getPie(dataset,svg,color);

    this.getLegend(svg, color, this.legendRectSize, this.legendSpacing);

    let pathtip = this.getPathtip(container);

    path.on('mouseover', function (d:any) {
      pathtip.select('.label').html(d.data.label);
      pathtip.style('display', 'block');
      let labelId = d.data.label.substr(0,3).toLowerCase();
      document.getElementById(labelId).classList.add('x-large');
    });

    path.on('mouseout', function (d:any) {
      pathtip.style('display', 'none');
      let labelId = d.data.label.substr(0,3).toLowerCase();
      document.getElementById(labelId).classList.remove('x-large');
    });

  }
  getContainer(target){
    let container = d3.select("#" + target);
    container.html('');
    return container;
  }
  getSvg(container){
    let figure = container.append("figure");
    let svg = figure.append('svg')
      .attr('width', this.width)
      .attr('height', this.height)
      .append('g')
      .attr('transform', 'translate(' + (this.width / 2) +
        ',' + (this.height / 2) + ')');
    return svg;
  }

  getPie(dataset, svg, color){
    let pie = d3.pie()
      .value(function (d:any) { return d.count; })
      .sort(null);
    
    let arc = d3.arc()
      .innerRadius(this.radius - this.donutWidth)
      .outerRadius(this.radius);

    let path = svg.selectAll('path')
      .data(pie(dataset))
      .enter()
      .append('path')
      .attr('d', <any>arc)
      .attr('fill', function (d:any) {
        return color(d.data.label);
      });

      return path;
  }

  getLegend(svg, color, size, spacing){
    let legend = svg.selectAll('.legend')
      .data(color.domain())
      .enter()
      .append('g')
      .attr('class', 'legend')
      .attr('transform', function (d, i) {
        let h = size + spacing;
        let offset = h * color.domain().length / 2;
        let horz = -2 * size;
        let vert = i * h - offset;
        return 'translate(' + horz + ',' + vert + ')';
      });

    legend.append('rect')
      .attr('width', size).attr('height', size)
      .style('fill', color).style('stroke', color);

    legend.append('text')
      .attr('id',function (d) { return d.substr(0,3).toLowerCase(); })
      .attr('x', size + spacing).attr('y', size - spacing)
      .text(function (d) { return d; });
  }
  
  getPathtip(container){
    let pathtip = container
      .append('div').attr('id','pathtip').attr('class', 'pathtip')
      .attr('style', 'display:none');

      pathtip.append('div')
      .attr('class', 'label');

    return pathtip;
  }
  
  

}
