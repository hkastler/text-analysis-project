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
(function() {
'use strict';

/**
 * @ngdoc function
 * @name twitterSAApp:controller:AlertCtrl
 * @description
 * # Retrieve all broadcast about alert
 */
angular.module('twitterSAApp')
    .controller('AlertCtrl', function($log, $scope) {
        
        //remove alert
        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };
        
        // Picks event about error message
        $scope.$on('error', function() {
            $scope.alerts = [{
                    type: 'danger', 
                    msg: 'There was a problem in the server!'
            }];
        });
        
        // Picks event about success message
        $scope.$on('success', function() {
            $scope.alerts = [{
                    type: 'success', 
                    msg: 'Operation is success!'
            }];
        });
        
        $scope.$on('created', function() {
            $scope.alerts = [{
                    type: 'success', 
                    msg: 'Data is has been saved!'
            }];
        });
        
    });
})();


(function() {
'use strict';

/**
 * @ngdoc directive
 * @name twitterSAApp.directive:Loader
 * @description
 * Receive broadcast to show or hide loader
 */
angular.module('twitterSAApp')
    .directive('anLoader', function($log) {

        return {
            restrict: 'EA',
            replace: true,
            templateUrl: 'views/templates/loader.html',
            link: function(scope, element) {
                var shownType = element.css('display');
                function hideElement() {
                    element.css('display', 'none');
                }
                scope.$on('loader_show', function() {
                    $log.debug('receive broadcast to show loader');

                    element.css('display', shownType);
                });

                scope.$on('loader_hide', function() {
                    $log.debug('receive broadcast to hide loader');

                    hideElement();
                });

                hideElement();
            }
        };

    });
})();
(function() {
'use strict';

/**
 * @ngdoc directive
 * @name twitterSAApp.directive:SearchDirective
 * @description
 * Directive search form for table 
 */
angular.module('twitterSAApp')
    .directive('anSearch', function(){
        
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'views/templates/search.html'
        };
        
    });
})();
(function() {
'use strict';

/**
 * @ngdoc interceptors
 * @name twitterSAApp.service:AnInterceptor
 *
 * @description
 * intercept any $http request and response
 */
angular.module('twitterSAApp')
    .factory('anInterceptor', function($q, $log, $rootScope) {

        var loadings = 0;

        var requestInterceptor = {
            request: function(config) {
                $log.debug('request interceptor');
                
                loadings++;
                $rootScope.$broadcast('loader_show');
                
                // do wathever you want with your request
                
                return config;
            },
            response: function(response) {
                $log.debug('response interceptor');

                if((--loadings) === 0) {
                    $rootScope.$broadcast('loader_hide');
                }

                if(response.status === 204) {
                    $rootScope.$broadcast('success');
                } else if(response.status === 201) {
                    $rootScope.$broadcast('created');
                }

                return response;
            },
            responseError: function(response) {
                $log.debug('responseError interceptor');

                if(response.status === 400) {
                    $rootScope.$broadcast('error');
                }
                
                if((--loadings) === 0) {
                    $rootScope.$broadcast('loader_hide');
                }
                
                return $q.reject(response);
            }
        };

        return requestInterceptor;
    });
})();
(function() {
    'use strict';
    
    /**
     * @ngdoc function
     * @name twitterSAApp:service:TwitterSAService
     * @description
     * # Service to call rest endpoint
     */
    angular.module('twitterSAApp')
        .factory('TwitterSAService', ['$http', function ($http) {

            var urlBase = '/rest/twittersa/';
            var dataFactory = {};



            dataFactory.getResults = function (queryTerms) {
                return $http.get(urlBase + 'results/' + queryTerms);
            };



            return dataFactory;
        }]);
    })  ();

   