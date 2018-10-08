(function() {
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
        .config(function($routeProvider, $compileProvider, $logProvider, $httpProvider) {
            //TODO: always uncomment source code below in production
            $compileProvider.debugInfoEnabled(true);
            $logProvider.debugEnabled(true);
            

            $routeProvider
                .when('/', {
                    templateUrl: 'views/main.html',
                    controller: 'mainCtrl'
                })
                .when('/about', {
                    templateUrl: 'views/about.html'
                });

        })
        .controller('mainCtrl', function($log, $scope, $http) {
            $scope.queryTerms = 'pizza';

            $scope.resultsTotal = '';
            $scope.resultsData = '';
            
            var urlBase = 'rest/twittersa/';
           
        
            $scope.getResults = function() {
                $http.get(urlBase + 'results/' + $scope.queryTerms)
                .then(function(data) {
                    
                    $scope.resultsTotal = data.data[0];
                    var psv = d3.dsvFormat(";");
                    $scope.resultsData = psv.parseRows(data.data[1]);

                    var container = d3.select("#resultsTable");
                    container.html('');
                    container.append("table")
                    
                    
                    .selectAll("tr")
                        .data($scope.resultsData).enter()
                        .append("tr")
 
                    .selectAll("td")
                        .data(function(d) { return d; }).enter()
                        .append("td")
                        .text(function(d) { return d; });

                   
               });
                 
            };
            
        });
})();