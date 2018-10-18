(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name twitterSAApp:controller:mainCtrl
     * @description
     * # controls twitter sa main page
     */
    angular.module('twitterSAApp')



        .controller('mainCtrl', function ($log, $scope, TwitterSAService) {

            $scope.getResults = function () {
                TwitterSAService.getSAResults($scope.queryTerms, $scope.tweetCount)
                    .then(function (response) {

                        $scope.resultsTotal = response.data[0];
                        tabulateDsv(response.data[1], "~", "#resultsTable");
                        donutChart($scope.resultsTotal, "#resultsChart");
                    })
                    .catch(function (resp) {
                        $scope.resultsTotal = resp;
                        $log.debug(resp);
                    });
            };

            function tabulateDsv(dsvdata, delimiter, target) {

                var data = d3.dsvFormat(delimiter).parse(dsvdata);
                var cols = d3.keys(data[0]);

                var container = d3.select(target);
                container.html('');

                var table = container.append("table")
                    .attr("class", "table table-striped");

                var caption = table.append("caption").text("Tweets");
                
                var sortAscending = true;
                //table header 
                var thead = table.append("thead").append("tr");
                var headers = thead.selectAll("th")
                    .data(cols).enter()
                    .append("th")
                    .text(function (col) { return col; })
                    .on('click', function (d) {
                        headers.attr('class', 'header');

                        if (sortAscending) {
                            rows.sort(function (a, b) { return b[d] < a[d]; });
                            sortAscending = false;
                            this.className = 'aes';
                        } else {
                            rows.sort(function (a, b) { return b[d] > a[d]; });
                            sortAscending = true;
                            this.className = 'des';
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
                        return d.value.replace(/&quot;/g, "\"");
                    });
            }

            //https://github.com/zeroviscosity/d3-js-step-by-step/blob/master/step-3-adding-a-legend.html
            function donutChart(mydata, target) {

                var container = d3.select(target);
                container.html('');

                var posPct = (mydata.positive / mydata.total) * 100;
                var negPct = (mydata.negative / mydata.total) * 100;
                var neuPct = (mydata.neutral / mydata.total) * 100;

                var dataset = [
                    { label: 'Positive ' + Math.round(posPct) + '%', count: mydata.positive },
                    { label: 'Negative ' + Math.round(negPct) + '%', count: mydata.negative },
                    { label: 'Neutral ' + Math.round(neuPct) + '%', count: mydata.neutral }
                ];

                var width = 360;
                var height = 360;
                var radius = Math.min(width, height) / 2;
                var donutWidth = 75;
                var legendRectSize = 18;
                var legendSpacing = 4;
                var color = d3.scaleOrdinal(d3.schemeCategory10);
                var svg = container.append('svg')
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

        });
})();