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
        .controller('mainCtrl', function ($log, $scope, TwitterSAService, $route) {
          
            $scope.getResults = function () {
                TwitterSAService.getSAResults($scope.queryTerms,$scope.tweetCount)
                    .then(function (response) {

                        $scope.resultsTotal = response.data[0];
                        tabulateDsv(response.data[1], ";", "#resultsTable"); 
                        
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
                    .text(function (d) { return d; });

            }
            
        });
})();