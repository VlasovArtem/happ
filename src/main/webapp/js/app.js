/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', [
    'ngRoute', 'underscore', 'ui.bootstrap', 'ngStorage',
    'apartment-controllers', 'apartment-services', 'apartment-directives',
    'main-controllers', 'main-directives', 'main-filters',
    'payment-controllers', 'payment-services', 'payment-directives', 'payment-filters'
]).config(
    function($routeProvider, $locationProvider, $httpProvider, $provide) {
        var addLoading = function() {
            $('nav').addClass('blurred');
            $('.view').addClass('blurred');
            $('footer').addClass('blurred');
            $('.loading-img').show();
        };
        var removeLoading = function() {
            $('nav').removeClass('blurred');
            $('.view').removeClass('blurred');
            $('footer').removeClass('blurred');
            $(".loading-img").hide();
        };
        $provide.factory('myHttpInterceptor', function($q) {
            return {
                'response': function(response) {
                    removeLoading();
                    return response;
                },
                'responseError': function(rejection) {
                    removeLoading();
                    return $q.reject(rejection);
                },
                'request': function(config) {
                    if(config.url.indexOf('page') > -1) {
                        addLoading();
                    }
                    return config;
                }
            }
        });
        $httpProvider.interceptors.push('myHttpInterceptor');
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
                    types: function(ServiceFactory) {
                        return ServiceFactory.query({get: 'get', types: 'types'}).$promise;
                    },
                    meters: function(ServiceFactory) {
                        return ServiceFactory.query({get: 'get', meters: 'meters'}).$promise;
                    },
                    apartment: function($sessionStorage) {
                        return $sessionStorage.apartment;
                    }
                }
            }).
            otherwise({
                redirectTo: '/'
            })
    });