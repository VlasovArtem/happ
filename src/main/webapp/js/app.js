/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', [
    'ngRoute', 'underscore', 'ui.bootstrap', 'ngStorage', 'ngSanitize', 'ui.select',
    'apartment-controllers', 'apartment-services', 'apartment-directives', 'apartment-filters',
    'main-controllers', 'main-directives', 'main-filters', 'main-services',
    'payment-controllers', 'payment-services', 'payment-directives', 'payment-filters',
    'user-services', 'user-directives', 'user-controllers'
]).config(
    function($routeProvider, $locationProvider, $httpProvider, $provide) {
        var isSafari = /Safari/.test(navigator.userAgent) && /Apple Computer/.test(navigator.vendor);
        var addLoading = function() {
            if(isSafari) {
                $('.view').addClass('white');
            } else {
                $('nav').addClass('blurred');
                $('.view').addClass('blurred');
                $('footer').addClass('blurred');
            }
            $('.loading-img').show();
        };
        var removeLoading = function() {
            if(isSafari) {
                $('.view').removeClass('white');
            } else {
                $('nav').removeClass('blurred');
                $('.view').removeClass('blurred');
                $('footer').removeClass('blurred');
            }
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
                    addLoading();
                    return config;
                }
            }
        });
        $httpProvider.interceptors.push('myHttpInterceptor');
        $locationProvider.html5Mode(true);
        $routeProvider
            .when('/', {
                templateUrl: 'app/main.html',
                controller: 'MainCtrl'
            }).
            when('/signup', {
                templateUrl: 'app/user/registration.html',
                controller: 'UserRegistrationCtrl'
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
            when('/success', {
                templateUrl: 'app/main/success.html',
                controller: 'SuccessCtrl'
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
                    apartment: function($sessionStorage, Payment, Service) {
                        Service.initService($sessionStorage.apartment);
                        Payment.initPayment($sessionStorage.apartment);
                        return $sessionStorage.apartment;
                    }
                }
            }).
            when('/payment/unpaid', {
                templateUrl: 'app/payment/unpaid.html',
                controller: 'UnpaidPaymentCtrl',
                resolve: {
                    payments: function(PaymentFactory, $sessionStorage) {
                        return PaymentFactory.query({unpaid: 'unpaid', addressId : $sessionStorage.apartment.address.id}).$promise;
                    }
                }
            }).
            when('/payment/statistic', {
                templateUrl: 'app/payment/statistic.html',
                controller: 'PaymentStatisticCtrl',
                resolve: {
                    apartment: function($sessionStorage) {
                        return $sessionStorage.apartment;
                    }
                }
            }).
            otherwise({
                redirectTo: '/'
            })
    });
app.run(['$rootScope', 'auth', function($rootScope, auth) {
    auth.init('/', '/', '/logout');
    $rootScope.$on('$routeChangeStart', function(event, next, current) {
        if(!_.isUndefined(next)) {
            if(next.$$route) {
                if(next.$$route.originalPath == '/' || next.$$route.originalPath == '/success') {
                    $('nav').addClass('hide');
                } else {
                    $('nav').removeClass('hide');
                }
            }
        }
    });
}]);