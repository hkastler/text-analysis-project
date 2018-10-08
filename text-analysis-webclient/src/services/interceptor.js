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