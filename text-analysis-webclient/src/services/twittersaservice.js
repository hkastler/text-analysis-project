(function() {
    'use strict';
    
    /**
     * @ngdoc function
     * @name twitterSAApp:service:TwitterSAService
     * @description
     * # Service to call rest endpoint
     */
    angular.module('twitterSAApp')
        .service('TwitterSAService', ['$http', function ($http) {

            var serviceLoc = 'http://localhost:8080/text-analysis-service/';
            var serviceUrl = 'rest/twittersa/';            

            this.getSAResults = function (queryTerms, tweetCount) {
                return  $http.get(serviceLoc + serviceUrl + 'sa/' + queryTerms + "/" + tweetCount);
            };

        }]);
    })  ();

   