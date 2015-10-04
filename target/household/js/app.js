/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', ['ngRoute', 'underscore', 'ui.bootstrap']).config(
    function($routeProvider, $locationProvider) {
        $locationProvider.html5Mode(true);
        $routeProvider
            .when('/', {
                templateUrl: 'app/main.html'
            }).
            otherwise({
                redirectTo: '/'
            })
    });