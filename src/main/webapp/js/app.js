/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', [
    'ngRoute', 'underscore', 'ui.bootstrap',
    'apartment-controllers', 'apartment-services',
    'main-controllers', 'main-directives'

]).config(
    function($routeProvider, $locationProvider) {
        $locationProvider.html5Mode(true);
        $routeProvider
            .when('/', {
                templateUrl: 'app/main.html'
            }).
            when('/apartment/add', {
                templateUrl: 'app/apartment/add.html',
                controller: 'AddApartmentCtrl'
            }).
            otherwise({
                redirectTo: '/'
            })
    });