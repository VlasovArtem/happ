/**
 * Created by artemvlasov on 23/09/15.
 */
var app = angular.module('main-controllers', []);

app.controller('MainCtrl', ['$scope', 'auth', 'ApartmentFactory', '$location',
    function($scope, auth, ApartmentFactory, $location) {
        $scope.auth = auth;
        ApartmentFactory.get({count : 'count'}, function(data) {
            $scope.count = data.count;
        });
        $scope.login = function(loginInfo) {
            auth.authenticate(loginInfo, function(callback) {
                if(callback) {
                    $scope.error = callback
                }
            });
        };
        $scope.adminPermission = function() {
            return auth.admin;
        };
        $scope.authenticated = function () {
            return auth.authenticated;
        };
        $scope.logout = function() {
            auth.clear();
        };
        $scope.addApartment = function() {
            $location.path('/apartment/add');
        }

    }
]);

app.controller('SuccessCtrl', ['$scope', '$timeout', '$location', function($scope, $timeout, $location) {
    $scope.seconds = 10;
    var redirect = function() {
        if($scope.seconds == 0) {
            $location.path('/')
        }
        $scope.seconds--;
        timeout = $timeout(redirect, 1000);
    };
    var timeout = $timeout(redirect, 1000);
}]);