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
                .when('/twittersa/:queryTerm/:tweetCount', {
                    templateUrl: 'views/main.html',
                    controller: 'mainCtrl'
                })
                .when('/about', {
                    templateUrl: 'views/about.html',
                    caseInsensitiveMatch: true
                });

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
                        return d.value.replace(/&quot;/g, "\"").replace(/&tilde;/g, "~");
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

        });
})();
(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name twitterSAApp:controller:navCtrl
     * @description
     * # controls navbar
     */
    angular.module('twitterSAApp')
        .controller('navCtrl', function ($scope, $location) {
            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };
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
(function () {
    'use strict';

    /**
     * @ngdoc directive
     * @name twitterSAApp.directive:navbar
     * @description
     * show navbar
     */
    angular.module('twitterSAApp')
        .directive('navBar', function () {
            return {
                templateUrl: 'views/templates/navbar.html'
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
(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name twitterSAApp:service:TwitterSAService
     * @description
     * # Service to call rest endpoint
     */
    angular.module('twitterSAApp')
        .service('TwitterSAService', ['$http', function ($http) {

            var port = window.location.href.indexOf("https://") == 0 ? "8443" : "8080";

            var serviceLoc = "//" + window.location.hostname + ":" + port;
            var serviceUrl = '/text-analysis-service/rest/twittersa/sa/';

            this.getSAResults = function (queryTerms, tweetCount) {
                return $http.get(serviceLoc + serviceUrl + queryTerms + "/" + tweetCount);
            };

        }]);
})();

