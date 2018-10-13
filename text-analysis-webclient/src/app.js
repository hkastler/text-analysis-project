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