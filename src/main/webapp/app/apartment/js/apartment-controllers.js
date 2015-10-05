/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('apartment-controllers', []);

app.controller('AddApartmentCtrl', ['$scope', 'cities', 'AddApartment', '$location', '$route', function($scope, cities, AddApartment, $location, $route) {
    $scope.cities = cities;
    var defaultApartment = {
        address : {
            city: "",
            house: "",
            apartment: "",
            street: ""
        }
    };
    $scope.add = function() {
        AddApartment.save($scope.apartment,
            function() {
                alert('Apartment successfully added');
                $location('/')
            }, function(data) {
                $scope.error = data.data.error;
                $scope.addApartment.$setPristine();
                $scope.apartment = defaultApartment;
            });
    }
}]);

app.controller('ApartmentsCtrl', ['$scope', 'apartments', '$sessionStorage', '$location',
    function($scope, apartments, $sessionStorage, $location) {
        $scope.apartments = apartments;
        $scope.unpaidPayments = 0;
        $scope.addPayment = function(apartment) {
            $sessionStorage.apartment = apartment;
            $location.path('/payment/add');
        }
    }
]);