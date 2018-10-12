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
           
            var host = "localhost";
            var port = window.location.href.indexOf("https://")==0?"8443":"8080";
            //var port = "8080";
            var serviceLoc = "//" + host + ":" + port;
            var serviceUrl = '/text-analysis-service/rest/twittersa/sa/';            

            this.getSAResults = function (queryTerms, tweetCount) {
                return  $http.get(serviceLoc + serviceUrl + queryTerms + "/" + tweetCount);
            };

        }]);
    })  ();

   