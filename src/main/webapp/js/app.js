/**
 * Created by artemvlasov on 03/09/15.
 */
var app = angular.module('household', [
    'ngRoute', 'underscore', 'ui.bootstrap', 'ngStorage', 'ngSanitize', 'ui.select',
    'apartment-controllers', 'apartment-services', 'apartment-directives', 'apartment-filters',
    'main-controllers', 'main-directives', 'main-filters', 'main-services',
    'payment-controllers', 'payment-services', 'payment-directives', 'payment-filters',
    'user-services', 'user-directives', 'user-controllers',
    'custom-service-services', 'custom-service-controllers'
]).config(
    function($routeProvider, $locationProvider, $httpProvider, $provide) {
        var addLoading = function() {
            $('.view').addClass('white');
            $('.loading-img').show();
        };
        var removeLoading = function() {
            $('.view').removeClass('white');
            $(".loading-img").hide();
        };
        $provide.factory('myHttpInterceptor', function($q) {
            return {
                'response': function(response) {
                    if(response.config.url == '/rest/street/search') {
                        angular.element('.loading-street').addClass('hide');
                    } else {
                        removeLoading();
                    }
                    return response;
                },
                'responseError': function(rejection) {
                    removeLoading();
                    return $q.reject(rejection);
                },
                'request': function(config) {
                    if(config.url == '/rest/street/search') {
                        angular.element('.loading-street').removeClass('hide');
                    } else if(!angular.element('body').hasClass('not-load')) {
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
        when('/service/:apartmentId', {
            templateUrl: 'app/payment/choose-service.html',
            controller: 'ChooseServiceCtrl',
            resolve: {
                types: function(ServiceFactory) {
                    return ServiceFactory.query({get: 'get', types: 'types'}).$promise;
                },
                services: function(ServiceFactory, $route) {
                    return ServiceFactory.query({get: 'get', apartmentId : $route.current.params.apartmentId}).$promise
                },
                apartment: function(ApartmentFactory, $route) {
                    return ApartmentFactory.get({get: 'get', id : $route.current.params.apartmentId}).$promise
                },
                apartmentServices: function(ServiceFactory, $route) {
                    return ServiceFactory.query({get : 'get', apartmentId : $route.current.params.apartmentId, services : 'services'})
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
                service: function($sessionStorage, $location) {
                    if($sessionStorage.service) {
                        return $sessionStorage.service
                    } else {
                        $location.path('/service/' + $sessionStorage.apartment.id);
                    }
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
                    return PaymentFactory.query({unpaid: 'unpaid', apartmentId : $sessionStorage.apartment.id}).$promise;
                },
                address: function($sessionStorage) {
                    return $sessionStorage.apartment.address;
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
        when('/custom/service/add', {
            templateUrl: 'app/custom-service/add.html',
            controller: 'AddCustomService',
            resolve: {
                cities : function(Cities) {
                    return Cities.query().$promise;
                },
                subtypes: function(ServiceFactory) {
                    return ServiceFactory.query({get: 'get', subtypes: 'subtypes'}).$promise;
                }
            }
        }).
        otherwise({
            redirectTo: '/'
        })
    });
app.run(['$rootScope', 'auth', function($rootScope, auth) {
    $rootScope.$on('$routeChangeStart', function(event, next, current) {
        if(!_.isUndefined(next)) {
            if(next.$$route) {
                if(next.$$route.originalPath == '/' || next.$$route.originalPath == '/success') {
                    $('nav').addClass('hide');
                    $('body').addClass('not-load')
                } else {
                    $('nav').removeClass('hide');
                    $('body').removeClass('not-load')
                }
            }
        }
    });
    auth.init('/', '/', '/logout');
}]);