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