/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-controllers', []);

app.controller('AddPaymentCtrl', ['$scope', '$sessionStorage', '$compile', '$location', 'service', 'apartment',
    function($scope, $sessionStorage, $compile, $location, service, apartment) {
        var element = angular.element('.service-form');
        if(_.isEqual(service.type.group, 'MAINTENANCE')) {
            element.append('<maintenance-service></maintenance-service>');
        } else if(_.contains(['GAS', 'WATER', 'ELECTRICITY', 'HEATING'], service.type.group)) {
            element.append('<regular-service></regular-service>');
        } else {
            element.append('<other-service></other-service>');
        }
        $compile(element)($scope);
        $scope.service = service;
        $scope.apartment = apartment;
        $scope.backwardToServices = function() {
            $location.path('/service/' + apartment.id);
        };
        delete $sessionStorage.service;
    }
]);

app.controller('UnpaidPaymentCtrl', ['$scope', 'payments', '$route', 'PaymentFactory', 'address',
    function($scope, payments, $route, PaymentFactory, address) {
        $scope.address = address;
        $scope.payments = payments;
        $scope.setPaid = function(paymentId) {
            PaymentFactory.setPaid({id : paymentId}, function() {
                $route.reload();
            }, function() {
                $scope.error = "Ooops"
            })
        }
    }
]);

app.controller('PaymentStatisticCtrl', ['$scope', 'apartment',
    function($scope, apartment) {
        $scope.address = apartment.address;
    }
]);

app.controller('ChooseServiceCtrl', ['$scope', '$route', '$filter', '$location', '$sessionStorage', 'apartment', 'services', 'types', 'apartmentServices', 'ServiceFactory', '$timeout',
    function($scope, $route, $filter, $location, $sessionStorage, apartment, services, types, apartmentServices, ServiceFactory, $timeout) {
        $scope.apartmentServices = apartmentServices;
        $scope.showPaid = false;
        $scope.servicesHead = 'Доступные сервисы';
        $scope.apartment = apartment;
        $scope.types = types;
        $scope.services = services;
        $scope.serviceImages = {
            electricity: {
                original : '/style/images/services/electricity.png',
                hover : '/style/images/services/electricity-hover.png'
            },
            heating: {
                original : '/style/images/services/heating.png',
                hover : '/style/images/services/heating-hover.png'
            },
            water: {
                original : '/style/images/services/water.png',
                hover : '/style/images/services/water-hover.png'
            },
            rent: {
                original : '/style/images/services/house.png',
                hover : '/style/images/services/house-hover.png'
            },
            gas: {
                original : '/style/images/services/gas.png',
                hover : '/style/images/services/gas-hover.png'
            },
            internet: {
                original : '/style/images/services/internet.png',
                hover : '/style/images/services/internet-hover.png'
            },
            tel: {
                original : '/style/images/services/tel.png',
                hover : '/style/images/services/tel-hover.png'
            },
            tv: {
                original : '/style/images/services/tv.png',
                hover : '/style/images/services/tv-hover.png'
            },
            intercom: {
                original : '/style/images/services/intercom.png',
                hover : '/style/images/services/intercom-hover.png'
            }
        };
        $scope.resetSearch = function() {
            $scope.search = null;
            $scope.servicesHead = 'Доступные сервисы';
            ServiceFactory.query({get: 'get', apartmentId : $scope.apartment.id}, function(data) {
                $scope.services = data;
            })

        };
        $scope.serviceSearch = function() {
            if($scope.search) {
                var convertedSearch = {
                    city : $scope.apartment.address.city.alias
                };
                _.each($scope.search, function(value, key) {
                    convertedSearch[key] = value.alias
                });
                ServiceFactory.search(convertedSearch, function (data) {
                    console.log(data);
                    $scope.servicesHead = $filter('camelCase')($scope.search.type.name);
                    $scope.services = data;
                });
            }
        };
        $scope.addPayment = function(service) {
            $sessionStorage.service = service;
            $location.path('/payment/add');
        };
        $scope.apartmentPayment = function(id) {
            return _.contains(apartmentServices, id);
        };
        //var canvas = document.getElementById('line');
        //var ctx = canvas.getContext("2d");
        //ctx.fillStyle = "76B21C";
        //ctx.fillRect(0,0,canvas.width,canvas.height);
    }
]);