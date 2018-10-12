(function () {
    'use strict';

    /**
     *
     * @ngdoc overview
     * @name twitterSAApp
     * @description
     * # Simple Angular Application
     */
    angular
        .module('twitterSAApp', [
            'ngTouch',
            'ngRoute',
            'ngResource',
            'ui.bootstrap'
        ])
        .config(function ($routeProvider, $compileProvider, $logProvider, $httpProvider) {
            //TODO: always uncomment source code below in production
            $compileProvider.debugInfoEnabled(true);
            $logProvider.debugEnabled(true);

            $httpProvider.interceptors.push('anInterceptor');

            $routeProvider
                .when('/', {
                    templateUrl: 'views/main.html',
                    controller: 'mainCtrl'
                })
                .when('/about', {
                    templateUrl: 'views/about.html'
                });

        })
        .controller('navCtrl', function ($scope, $location) {
            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };
        })
        .controller('mainCtrl', function ($log, $scope, TwitterSAService) {

            $scope.getResults = function () {
                TwitterSAService.getSAResults($scope.queryTerms, $scope.tweetCount)
                    .then(function (response) {

                        $scope.resultsTotal = response.data[0];
                        tabulateDsv(response.data[1], "~", "#resultsTable");
                        pieChart($scope.resultsTotal, "#resultsPie");
                    })
                    .catch(function (resp) {
                        $scope.resultsTotal = "An error has occurred";
                        $log.debug(resp);
                    });
            };

            function tabulateDsv(dsvdata, delimiter, target) {

                var rows = d3.dsvFormat(delimiter).parseRows(dsvdata);

                var container = d3.select(target);
                container.html('');

                var table = container.append("table")
                    .attr("class", "table table-striped");

                //proper table header 
                var thead = table.append("thead").append("tr");
                thead.selectAll("th")
                    .data(rows[0]).enter()
                    .append("th")
                    .text(function (column) { return column; });
                //this deletes the header row
                rows.shift();

                //proper table body
                var tbody = table.append("tbody");

                tbody.selectAll("tr")
                    //output the remaining data
                    .data(rows).enter()
                    .append("tr")
                    .selectAll("td")
                    .data(function (d) { return d; }).enter()
                    .append("td")
                    .text(function (d) { return d.replace(/&quot;/g, "\""); });

            }
            
            //https://github.com/zeroviscosity/d3-js-step-by-step/blob/master/step-3-adding-a-legend.html
            function pieChart(mydata, target) {

                var container = d3.select(target);
                container.html('');

                var dataset = [
                    { label: 'Positive', count: mydata.positive / mydata.total },
                    { label: 'Negative', count: mydata.negative / mydata.total },
                    { label: 'Neutral', count: mydata.neutral / mydata.total }
                ];
               
                  var width = 360;
                  var height = 360;
                  var radius = Math.min(width, height) / 2;
                  var donutWidth = 75;
                  var legendRectSize = 18;                                  // NEW
                  var legendSpacing = 4;                                    // NEW
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
                    .value(function(d) { return d.count; })
                    .sort(null);
                  var path = svg.selectAll('path')
                    .data(pie(dataset))
                    .enter()
                    .append('path')
                    .attr('d', arc)
                    .attr('fill', function(d, i) {
                      return color(d.data.label);
                    });
                  var legend = svg.selectAll('.legend')                     // NEW
                    .data(color.domain())                                   // NEW
                    .enter()                                                // NEW
                    .append('g')                                            // NEW
                    .attr('class', 'legend')                                // NEW
                    .attr('transform', function(d, i) {                     // NEW
                      var height = legendRectSize + legendSpacing;          // NEW
                      var offset =  height * color.domain().length / 2;     // NEW
                      var horz = -2 * legendRectSize;                       // NEW
                      var vert = i * height - offset;                       // NEW
                      return 'translate(' + horz + ',' + vert + ')';        // NEW
                    });                                                     // NEW
                  legend.append('rect')                                     // NEW
                    .attr('width', legendRectSize)                          // NEW
                    .attr('height', legendRectSize)                         // NEW
                    .style('fill', color)                                   // NEW
                    .style('stroke', color);                                // NEW
                  legend.append('text')                                     // NEW
                    .attr('x', legendRectSize + legendSpacing)              // NEW
                    .attr('y', legendRectSize - legendSpacing)              // NEW
                    .text(function(d) { return d; });                       // NEW
            }

        });
})();