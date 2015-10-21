/**
 * Created by artemvlasov on 23/09/15.
 */
var app = angular.module('main-controllers', []);

app.controller('MainCtrl', ['$scope', 'auth', 'ApartmentFactory',
    function($scope, auth, ApartmentFactory) {
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
    }
]);