/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('apartment-controllers', []);

app.controller('AddApartmentCtrl', ['$scope', 'cities', 'AddApartment', '$location', '$compile',
    function($scope, cities, AddApartment, $location, $compile) {
        $scope.cities = cities;
        $scope.validationMessage = {
            city : {
                required : "City is required"
            }
        };
        $scope.setFocus = function(id) {
            angular.element('#' + id).parents('.happ-form-group').addClass('is-focused');
        };
        $scope.setBlur = function(id) {
            angular.element('#' + id).parents('.happ-form-group').removeClass('is-focused');
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