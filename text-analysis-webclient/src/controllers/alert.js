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

