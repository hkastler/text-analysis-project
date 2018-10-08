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

   