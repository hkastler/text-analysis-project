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