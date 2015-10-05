/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-controllers', []);

app.controller('AddPaymentCtrl', ['$scope', 'types', 'apartment', 'ServiceFactory', '$filter',
    function($scope, types, apartment, ServiceFactory, $filter) {
        $scope.types = types;
        $scope.today = new Date();
        $scope.today.setHours(0, 0, 0, 0);
        $scope.apartment = apartment;
        $scope.payment = {
            prev_meter: 0,
            payment_date: new Date()
        };
        $scope.status = {
            opened: false
        };
        $scope.chooseServiceType = function(type) {
            if(angular.isDefined(type)) {
                ServiceFactory.query({get: 'get', all: 'all', city: apartment.address.city.name, type: type})
                    .$promise
                    .then(function (data) {
                        console.log(data);
                        if(data.length == 1) {
                            $scope.service = data[0];
                        } else {
                            $scope.services = data;
                        }
                    })
            }
        };
        var countPaymentSum = function(prev, cur) {
            var countSettlementMeterReading = cur - prev;
            var sum = 0;
            _.each($scope.service.volumes, function(value, index) {
                if(countSettlementMeterReading > value) {
                    sum += (value * $scope.service.rates[index]);
                } else {
                    sum += (countSettlementMeterReading * $scope.service.rates[index])
                }
                countSettlementMeterReading -= value;
            });
            if(countSettlementMeterReading > 0) {
                sum += (countSettlementMeterReading * $scope.service.rates[$scope.service.rates.length - 1]);
            }
            $scope.payment.payment_sum = sum;
            $scope.paymentForm.sum.$setViewValue(sum);
            $scope.paymentForm.sum.$render();
        };
        var toId;
        $scope.$watch('payment.cur_meter', function(newValue) {
            if(newValue >= $scope.payment.prev_meter) {
                if (toId) clearTimeout(toId);
                toId = setTimeout(function () {
                    countPaymentSum($scope.payment.prev_meter, newValue);
                }, 1000)
            }
        });
        $scope.countMaintenanceSum = function(footageOfApartments) {
            if(footageOfApartments > 0) {
                $scope.payment.payment_sum = $scope.service.rates[0] * footageOfApartments;
            }
        };
        $scope.addPayment = function () {
            $scope.payment.service = $scope.service;
            $scope.payment.payment_date = $filter('ToLocalDateFilter')($scope.payment.payment_date);
            console.log($scope.payment)
        };
        $scope.open = function($event) {
            $scope.status.opened = true;
        }
    }
]);