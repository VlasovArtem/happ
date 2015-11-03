/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-controllers', []);

app.controller('AddPaymentCtrl', ['$scope', '$sessionStorage', 'service', 'apartment',
    function($scope, $sessionStorage, service, apartment) {
        $scope.service = service;
        $scope.apartment = apartment;
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

app.controller('ChooseServiceCtrl', ['$scope', '$route', '$filter', '$location', '$sessionStorage', 'apartment', 'services', 'types', 'apartmentServices', 'ServiceFactory',
    function($scope, $route, $filter, $location, $sessionStorage, apartment, services, types, apartmentServices, ServiceFactory) {
        console.log(apartmentServices);
        console.log(services);
        $scope.servicesHead = 'Доступные сервисы';
        $scope.apartment = apartment;
        $scope.types = types;
        $scope.services = services;
        $scope.serviceImages = {
            electricity: '/style/images/services/electricity.png',
            heating: '/style/images/services/heating.png',
            water: '/style/images/services/water.png',
            rent: '/style/images/services/house.png',
            gas: '/style/images/services/gas.png',
            internet: '/style/images/services/internet.png',
            tel: '/style/images/services/tel.png',
            tv: '/style/images/services/tv.png',
            intercom: '/style/images/services/intercom.png'
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