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
        .controller('navCtrl', function ($scope, $location) {
            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };
        })
        .controller('mainCtrl', function ($log, $scope, $http) {


            $scope.queryTerms = 'chicago pizza';
            $scope.tweetCount = 100;

            $scope.resultsTotal = '';
            $scope.resultsData = [];

            var serviceLoc = 'http://localhost:8080/text-analysis-service/';
            var urlBase = 'rest/twittersa/';


            $scope.getResults = function () {

                $http.get(serviceLoc + urlBase + 'sa/' + $scope.queryTerms + "/" + $scope.tweetCount)
                    .then(function (data) {

                        $scope.resultsTotal = data.data[0];
                        tabulateDsv(data.data[1],";", "#resultsTable");

                    });

            };
            function tabulateDsv(dsvdata, delimiter, target) {
                
                var rows = d3.dsvFormat(delimiter).parseRows(dsvdata);

                var container = d3.select(target);
                container.html('');

                var table = d3.select(target).append("table")
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