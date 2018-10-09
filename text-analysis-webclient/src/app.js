(function () {
    'use strict';

    /**
     *
     * @ngdoc overview
     * @name twitterSAApp
     * @description
     * # Single Page Application with Java EE 7
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
        .controller('mainCtrl', function ($log, $scope, $http) {


            $scope.queryTerms = 'pizza';

            $scope.resultsTotal = '';
            $scope.resultsData = [];

            var serviceLoc = 'http://localhost:8080/text-analysis-service/';
            var urlBase = 'rest/twittersa/';


            $scope.getResults = function () {

                $http.get(serviceLoc + urlBase + 'results/' + $scope.queryTerms)
                    .then(function (data) {

                        $scope.resultsTotal = data.data[0];
                        var psv = d3.dsvFormat(";");
                        $scope.resultsData = psv.parseRows(data.data[1]);

                        var container = d3.select("#resultsTable");
                        container.html('');

                        var table = d3.select("#resultsTable").append("table")
                            .attr("class", "table table-striped");

                        //proper table header 
                        var thead = table.append("thead");
                        thead.selectAll("th")
                        .data($scope.resultsData[0]).enter()
                        .append("th")
                        .text(function (column) { return column; });                        
                        //this deletes the header row
                        $scope.resultsData.shift();

                        //proper table body
                        var tbody = table.append("tbody");

                        tbody.selectAll("tr")
                        //output the remaining data
                            .data($scope.resultsData).enter()
                            .append("tr")

                            .selectAll("td")
                            .data(function (d) { return d; }).enter()
                            .append("td")
                            .text(function (d) { return d; });


                    });

            };

        });
})();