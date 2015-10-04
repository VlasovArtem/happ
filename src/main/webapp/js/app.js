/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', [
    'ngRoute', 'underscore', 'ui.bootstrap',
    'apartment-controllers', 'apartment-services', 'apartment-directives',
    'main-controllers', 'main-directives',
    'payment-controllers', 'payment-services', 'payment-directives'

]).config(
    function($routeProvider, $locationProvider) {
        $locationProvider.html5Mode(true);
        $routeProvider
            .when('/', {
                templateUrl: 'app/main.html'
            }).
            when('/apartment/add', {
                templateUrl: 'app/apartment/add.html',
                controller: 'AddApartmentCtrl',
                resolve: {
                    cities : function(Cities) {
                        return Cities.query().$promise;
                    }
                }
            }).
            when('/apartments', {
                templateUrl: 'app/apartment/apartments.html',
                controller: 'ApartmentsCtrl',
                resolve: {
                    apartments: function(ApartmentFactory) {
                        return ApartmentFactory.query({get : 'get', all : 'all'}).$promise;
                    }
                }
            }).
            when('/payment/add', {
                templateUrl: 'app/payment/add.html',
                controller: 'AddPaymentCtrl',
                resolve: {
                    services: function(Services) {
                        return Services.query().$promise;
                    }
                }
            }).
            otherwise({
                redirectTo: '/'
            })
    });