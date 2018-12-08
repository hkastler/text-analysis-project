import * as d3 from 'd3';
import React from 'react';
import './DonutChart.css';

class DonutChart extends React.Component {

    donutData
    target
    jDonutData
    width
    height
    donutWidth
    legendRectSize
    legendSpacing
    radius
    DOMElement

    constructor(mydata, target) {
        super();
        this.width = 360;
        this.height = 360;
        this.donutWidth = 75;
        this.legendRectSize = 18;
        this.legendSpacing = 4;
        this.radius = Math.min(this.width, this.height) / 2;
        this.jDonutData = "";

        this.donutData = mydata;
        this.target = target
    }

       
    makeDonut() {
        this.d3Html();
        this.jDonutData = JSON.stringify(this.donutData);
    }

    render() {
        return (
            <div id="resultsChart"></div>
        );
      }

    d3Html() {
        
        var mydata = this.donutData;
        var container = this.getContainer(this.target);
        container.html('');

        if (isNaN(mydata["total"])) {
            return;
        }

        var posPct = (mydata["positive"] / mydata["total"]) * 100;
        var negPct = (mydata["negative"] / mydata["total"]) * 100;
        var neuPct = (mydata["neutral"] / mydata["total"]) * 100;

        var dataset = [
            { label: 'Positive ' + Math.round(posPct) + '%', count: mydata["positive"] },
            { label: 'Negative ' + Math.round(negPct) + '%', count: mydata["negative"] },
            { label: 'Neutral ' + Math.round(neuPct) + '%', count: mydata["neutral"] }
        ];

        var color = d3.scaleOrdinal(d3.schemeCategory10);
        
        var svg = this.getSvg(container);
        var path = this.getPie(dataset, svg, color);
        this.getLegend(svg, color, this.legendRectSize, this.legendSpacing);

        var pathtip = this.getPathtip(container);

        path.on('mouseover', function (d) {
            pathtip.select('.label').html(d.data.label);
            pathtip.style('display', 'block');
            var labelId = d.data.label.substr(0, 3).toLowerCase();
            document.getElementById(labelId).classList.add('x-large');
        });

        path.on('mouseout', function (d) {
            pathtip.style('display', 'none');
            var labelId = d.data.label.substr(0, 3).toLowerCase();
            document.getElementById(labelId).classList.remove('x-large');
        });
        
    }
    getContainer(target) {        
        var container = d3.select(target);
        if (container.empty()) {
            container = d3.select("body").append("div")
            .attr("id",target);
        }
        container.html('');
        return container;
    }
    getSvg(container) {
        var figure = container.append("figure");
        var svg = figure.append('svg')
            .attr('width', this.width)
            .attr('height', this.height)
            .append('g')
            .attr('transform', 'translate(' + (this.width / 2) +
                ',' + (this.height / 2) + ')');
        return svg;
    }

    getPie(dataset, svg, color) {
        var pie = d3.pie()
            .value(function (d) { return d.count; })
            .sort(null);

        var arc = d3.arc()
            .innerRadius(this.radius - this.donutWidth)
            .outerRadius(this.radius);

        var path = svg.selectAll('path')
            .data(pie(dataset))
            .enter()
            .append('path')
            .attr('d', arc)
            .attr('fill', function (d) {
                return color(d.data.label);
            });

        return path;
    }

    getLegend(svg, color, size, spacing) {
        var legend = svg.selectAll('.legend')
            .data(color.domain())
            .enter()
            .append('g')
            .attr('class', 'legend')
            .attr('transform', function (d, i) {
                var h = size + spacing;
                var offset = h * color.domain().length / 2;
                var horz = -2 * size;
                var vert = i * h - offset;
                return 'translate(' + horz + ',' + vert + ')';
            });
        legend.append('rect')
            .attr('width', size)
            .attr('height', size)
            .style('fill', color)
            .style('stroke', color);
        legend.append('text')
            .attr('id', function (d) { return d.substr(0, 3).toLowerCase(); })
            .attr('x', size + spacing)
            .attr('y', size - spacing)
            .text(function (d) { return d; });
    }

    getPathtip(container) {
        var pathtip = container
            .append('div')
            .attr('id', 'pathtip')
            .attr('class', 'pathtip')
            .attr('style', 'display:none');

        pathtip.append('div')
            .attr('class', 'label');

        return pathtip;
    }



}
export default DonutChart;