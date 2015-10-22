/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('apartment-controllers', []);

app.controller('AddApartmentCtrl', ['$scope', 'cities', 'AddApartment', '$location', '$route',
    function($scope, cities, AddApartment, $location, $route) {
        $scope.cities = cities;
        $scope.validationMessage = {
            city : {
                required : "City is required"
            }
        };
        $scope.apartment = {};
        $scope.changeCity = function() {
            $scope.streets = [];
        };
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
                    $location.path('/apartments')
                }, function(data) {
                    $scope.error = data.data.error;
                    $scope.addApartment.$setPristine();
                    $scope.apartment = defaultApartment;
                });
        }
    }
]);

app.controller('ApartmentsCtrl', ['$scope', 'apartments',
    function($scope, apartments) {
        $scope.apartments = apartments;
        $scope.apartmentTracking = function(apartment) {
            return apartment.address.street.name + '-' + apartment.address.street.type + '-' + apartment.apartment + '-' + apartment.house;
        }

    }
]);