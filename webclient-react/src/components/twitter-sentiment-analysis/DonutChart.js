import * as d3 from 'd3';

class DonutChart {

    constructor(mydata, target) {
        this.data = mydata;
        this.target = target
    }

    d3Html() {

        var container = d3.select(this.target);
        container.html('');

        var posPct = (this.data.positive / this.data.total) * 100;
        var negPct = (this.data.negative / this.data.total) * 100;
        var neuPct = (this.data.neutral / this.data.total) * 100;

        var dataset = [
            { label: 'Positive ' + Math.round(posPct) + '%', count: this.data.positive },
            { label: 'Negative ' + Math.round(negPct) + '%', count: this.data.negative },
            { label: 'Neutral ' + Math.round(neuPct) + '%', count: this.data.neutral }
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
            .value(function (d) { return d.count; })
            .sort(null);

        var path = svg.selectAll('path')
            .data(pie(dataset))
            .enter()
            .append('path')
            .attr('d', arc)
            .attr('fill', function (d, i) {
                return color(d.data.label);
            });

        var tooltip = container
            .append('div')
            .attr('class', 'resultsChartTooltip');
        tooltip.append('div')
            .attr('class', 'label');

        path.on('mouseover', function (d) {
            tooltip.select('.label').html(d.data.label);
            tooltip.style('display', 'block');
        });

        path.on('mouseout', function () {
            tooltip.style('display', 'none');
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
            .attr('x', legendRectSize + legendSpacing)
            .attr('y', legendRectSize - legendSpacing)
            .text(function (d) { return d; });
    }
}
export default DonutChart;