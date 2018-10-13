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