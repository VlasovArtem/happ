/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-directives', []);

app.directive('originalMeter', function() {
    return {
        restrict: 'E',
        link: function(scope, element, attr) {
            scope.payment.prev_meter = [0];
            var countSum = function() {
                var sum = scope.countPaymentSum(scope.payment.prev_meter[0], scope.payment.cur_meter[0]);
                scope.updateSum(sum)
            };
            scope.changed = function() {
                scope.updateSum(null);
                if(scope.payment.cur_meter.length > 0) {
                    if (checkBeforeSumCount()) {
                        countSum();
                    }
                    if (scope.payment.prev_meter[0] > scope.payment.cur_meter[0]) {
                        scope.error = "Prev meter cannot be greater than cur meter"
                    } else {
                        scope.error = null
                    }
                }
            };
            var checkBeforeSumCount = function() {
                return scope.payment.prev_meter[0] < scope.payment.cur_meter[0];
            }
        },
        templateUrl: 'app/payment/service-type/meters/original-meter.html'
    }
});

app.directive('twoTariffMeter', function() {
    return {
        restrict: 'E',
        link: function(scope, element, attr) {
            scope.payment.prev_meter = [0, 0];
            var countSum = function() {
                var daySum = Number(scope.countPaymentSum(scope.payment.prev_meter[0], scope.payment.cur_meter[0]));
                var nightSum = Number(scope.countPaymentSum(scope.payment.prev_meter[1], scope.payment.cur_meter[1]));
                var sum = daySum + nightSum * 0.5;
                scope.updateSum(sum.toFixed(2))
            };
            scope.changed = function() {
                scope.updateSum(null);
                if(scope.payment.cur_meter.length > 0) {
                    if (checkBeforeSumCount()) {
                        countSum();
                    }
                    if (scope.payment.prev_meter[0] > scope.payment.cur_meter[0] || scope.payment.prev_meter[1] > scope.payment.cur_meter[1]) {
                        scope.error = "Prev meter cannot be greater than cur meter"
                    } else {
                        scope.error = null
                    }
                }
            };
            var checkBeforeSumCount = function() {
                return _.every(scope.payment.prev_meter, function(value, index) {
                    return value < scope.payment.cur_meter[index];
                });
            };
        },
        templateUrl: 'app/payment/service-type/meters/two-tariff-meter.html'
    }
});

app.directive('regularService', function($compile) {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
            scope.status = {
                opened: false
            };
            angular.element('.meter-data').append('<original-meter></original-meter>');
            $compile(angular.element('.meter-data'))(scope);
            scope.changeMeterType = function(type) {
                angular.element('original-meter').remove();
                angular.element('two-tariff-meter').remove();
                if(_.isEqual(type, 'TWO_TARIFF')) {
                    angular.element('.meter-data').append('<two-tariff-meter></two-tariff-meter>');
                } else {
                    angular.element('.meter-data').append('<original-meter></original-meter>');
                }
                $compile(angular.element('.meter-data'))(scope);
                scope.payment.prev_meter = [0, 0];
                scope.payment.cur_meter = [];
                scope.payment.payment_sum = null;
            };
            scope.open = function($event) {
                scope.status.opened = true;
            };
            scope.countPaymentSum = function(prev, cur) {
                var countSettlementMeterReading = cur - prev;
                var sum = 0;
                var volumeCount = 0;
                if(scope.service.volumes) {
                    while (volumeCount < scope.service.volumes.length && countSettlementMeterReading > 0) {
                        if (countSettlementMeterReading > scope.service.volumes[volumeCount]) {
                            sum += (scope.service.volumes[volumeCount] * scope.service.rates[volumeCount])
                        } else {
                            sum += (countSettlementMeterReading * scope.service.rates[volumeCount])
                        }
                        countSettlementMeterReading -= scope.service.volumes[volumeCount];
                        volumeCount++;
                    }
                    if (countSettlementMeterReading > 0) {
                        sum += (countSettlementMeterReading * scope.service.rates[scope.service.rates.length - 1]);
                    }
                } else {
                    sum += countSettlementMeterReading * scope.service.rates[0];
                }
                return sum.toFixed(2);
            };
            scope.updateSum = function(sum) {
                scope.payment.payment_sum = sum;
                scope.paymentForm.sum.$setViewValue(sum);
                scope.paymentForm.sum.$render();
            }
        },
        templateUrl: 'app/payment/service-type/regular-service.html'
    }
});

app.directive('maintenanceService', function() {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
            scope.countMaintenanceSum = function(footageOfApartments) {
                if(footageOfApartments > 0) {
                    scope.payment.payment_sum = (scope.service.rates[0] * footageOfApartments).toFixed(2);
                }
            };
        },
        templateUrl: 'app/payment/service-type/maintenance.html'
    }
});

app.directive('servicePayment', function(ServiceFactory, PaymentFactory, $compile, $filter) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            scope.today = new Date();
            scope.today.setHours(0, 0, 0, 0);
            scope.initPayment = {
                prev_meter: [0, 0],
                cur_meter: [],
                payment_date: new Date(),
                meter_type: scope.meters[scope.meters.indexOf('ORIGINAL')]
            };
            scope.chooseServiceType = function(type) {
                angular.element('.service-data').remove();
                scope.payment = angular.copy(scope.initPayment);
                if(angular.isDefined(type)) {
                    ServiceFactory.query({get: 'get', all: 'all', city: scope.apartment.address.city.name, type: type})
                        .$promise
                        .then(function (data) {
                            if(data.length == 1) {
                                scope.service = data[0];
                                PaymentFactory.last({address: scope.apartment.address, service_name: scope.service.name});
                            } else {
                                scope.services = data;
                            }
                        });
                    var appendedDirective;
                    var appendedElement;
                    if(_.isEqual(type, 'MAINTENANCE')) {
                        appendedDirective = 'maintenance-service';
                        appendedElement = '<maintenance-service></maintenance-service>'
                    } else {
                        appendedDirective = 'regular-service';
                        appendedElement = '<regular-service></regular-service>'
                    }
                    angular.element('.service-payment-info').append(appendedElement);
                    $compile(angular.element(appendedDirective))(scope)
                }
            };

            scope.addPayment = function () {
                scope.payment.service = scope.service;
                scope.payment.payment_date = $filter('ToLocalDateFilter')(scope.payment.payment_date);
                scope.payment.address = scope.apartment.address;
                PaymentFactory.save(scope.payment)
            };
            scope.payment = angular.copy(scope.initPayment);
        }
    }
})