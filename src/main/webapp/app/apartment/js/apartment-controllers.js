/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('apartment-controllers', []);

app.controller('AddApartmentCtrl', ['$scope', 'AddApartment', function($scope, AddApartment) {
    $scope.add = function() {
        AddApartment.save($scope.apartment);
    }
}]);