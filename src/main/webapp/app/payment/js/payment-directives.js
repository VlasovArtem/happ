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

app.directive('maintenanceService', function($sessionStorage) {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
            scope.validationMessage = {
                serviceName: {
                    required: 'Service name is required'},
                footage: {
                    required: 'Footage of the apartment is required'
                },
                rate: {
                    required: 'Rate for maintenance is required'
                },
                sum: {
                    required: 'Payment sum is required'
                }
            };
            scope.payment = angular.copy(scope.initPayment);
            scope.payment.service = {
                rates: [],
                volumes: [],
                type: scope.type
            };
            scope.countMaintenanceSum = function() {
                if(scope.payment.service.rates && scope.payment.service.volumes) {
                    if(scope.payment.service.rates[0] > 0 && scope.payment.service.volumes[0] > 0) {
                        var sum = scope.payment.service.rates[0] * scope.payment.service.volumes[0];
                        scope.payment.payment_sum = Number(sum.toFixed(2));
                    }
                }
            };
            scope.userPreviousData = function() {
                scope.payment.service = scope.previousPayment.service;
                scope.payment.payment_sum = scope.previousPayment.payment_sum;
            }
        },
        templateUrl: 'app/payment/service-type/maintenance.html'
    }
});

app.directive('servicePayment', function(ServiceFactory, PaymentFactory, $compile, $filter, $location) {
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
                    var regularServiceTypes = ["GAS", "WATER", "ELECTRICITY"];
                    if(_.contains(regularServiceTypes, type)) {
                        ServiceFactory.query({
                            get: 'get',
                            all: 'all',
                            city: scope.apartment.address.city.name,
                            type: type
                        })
                            .$promise
                            .then(function (data) {
                                if (data.length == 1) {
                                    scope.service = data[0];
                                    PaymentFactory.last({
                                            addressId: scope.apartment.address.id,
                                            type: type
                                        },
                                        function (data) {
                                            if(data) {
                                                scope.initPayment.prev_meter = data.cur_meter;
                                                scope.payment.prev_meter = data.cur_meter;
                                            }
                                        });
                                } else {
                                    scope.services = data;
                                }
                            });
                    } else {
                        PaymentFactory.last({
                            addressId: scope.apartment.address.id,
                            type: type
                        }, function(data) {
                            if(data) {
                                scope.service = data.service;
                                scope.previousPayment = data;
                                scope.initPayment.payment_sum = data.payment_sum;
                                scope.initPayment.service = data.service;
                                scope.payment.payment_sum = data.payment_sum;
                                scope.payment.service = data.service;
                            }
                        })
                    }
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
                if(angular.isDefined(scope.service)) {
                    scope.payment.service = scope.service;
                }
                scope.payment.payment_date = $filter('ToLocalDateFilter')(scope.payment.payment_date);
                scope.payment.address = scope.apartment.address;
                if(!scope.payment.service.city) {
                    scope.payemnt.service.city = scope.apartment.address.city;
                }
                var convertedPrevMeter = [];
                _.each(scope.payment.prev_meter, function(meter) {
                    if(meter != 0) {
                        convertedPrevMeter.push(meter);
                    }
                });
                scope.payment.prev_meter = convertedPrevMeter.length > 0 ? convertedPrevMeter : null;
                console.log(scope.payment);
                PaymentFactory.save(scope.payment, function() {
                    $location.path('/apartments')
                })
            };
            scope.payment = angular.copy(scope.initPayment);
        }
    }
});

app.directive('statistic', function($sessionStorage, $compile) {
    var link = function(scope, element, attr) {
        scope.apartment = $sessionStorage.apartment;
        var statistics = {
            by_month: {
                tag: '<statistic-by-month class="statistic"></statistic-by-month>',
                class: '.by-month'
            },
            by_service: {
                tag: '<statistic-by-service class="statistic"></statistic-by-service>',
                class: '.by-service'
            },
            all: {
                tag: '<statistic-all class="statistic"></statistic-all>',
                class: '.all'
            }
        };
        scope.switch = function(link) {
            angular.element('.statistic').remove();
            var switchedStatistic = statistics[link];
            var el = switchedStatistic.tag;
            element.append(el);
            angular.element('.statistic-tab > li').removeClass('active');
            angular.element(switchedStatistic.class).addClass('active');
            $compile(angular.element('.statistic'))(scope);

        };
        scope.switch('by_month');
    };
    return {
        restrict: 'E',
        link: link,
        templateUrl: 'app/payment/statistic-block.html'
    }
});

app.directive('statisticByMonth', function(PaymentFactory, StatisticService) {
    var controller = ['$scope', function($scope) {
        $scope.months = StatisticService.months
        $scope.years = StatisticService.getYears();
        $scope.currentMonth = $scope.months[new Date().getMonth()];
        $scope.viewMonth = angular.copy($scope.currentMonth);
        $scope.currentYear = new Date().getFullYear();
        $scope.showNewStatistic = function() {
            PaymentFactory.query({
                get: 'get',
                stat: 'stat',
                month: 'month',
                addressId: $scope.apartment.address.id,
                monthNum: $scope.months.indexOf($scope.currentMonth) + 1,
                year: $scope.currentYear
            }, function(data) {
                $scope.payments = data;
                $scope.summary = StatisticService.getSummary(data);
                $scope.viewMonth = angular.copy($scope.currentMonth);
            })
        };
        $scope.showNewStatistic();
    }];
    return {
        controller: controller,
        templateUrl: 'app/payment/statistic-by-month.html'
    }
});

app.directive('statisticByService', function(PaymentFactory, ServiceFactory, StatisticService) {
    var controller = ['$scope', function($scope) {
        $scope.years = StatisticService.getYears();
        ServiceFactory.query({get: 'get', types: 'types'}, function(data) {
            $scope.types = data;
            $scope.currentType = data[data.indexOf('ELECTRICITY')];
            $scope.viewType = angular.copy($scope.currentType);
            $scope.currentYear = new Date().getFullYear();
            $scope.showNewStatistic();
        });
        $scope.showNewStatistic = function() {
            PaymentFactory.query({
                get: 'get',
                stat: 'stat',
                service: 'service',
                addressId: $scope.apartment.address.id,
                type: $scope.currentType,
                year: $scope.currentYear
            }, function(data) {
                $scope.payments = data;
                $scope.viewType = angular.copy($scope.currentType);
                $scope.summary = StatisticService.getSummary(data);
            });
        }
    }];
    return {
        controller: controller,
        templateUrl: 'app/payment/statistic-by-service.html'
    }
});

app.directive('statisticAll', function(PaymentFactory, StatisticService) {
    var controller = ['$scope', function($scope) {
        $scope.years = StatisticService.getYears();
        $scope.currentYear = new Date().getFullYear();
        $scope.showNewStatistic = function() {
            PaymentFactory.query({
                get: 'get',
                stat: 'stat',
                all: 'all',
                addressId: $scope.apartment.address.id,
                year: $scope.currentYear
            }, function(data) {
                $scope.payments = data;
                $scope.summary = StatisticService.getSummary(data);
            })
        };
        $scope.showNewStatistic();
    }];
    return {
        controller: controller,
        templateUrl: 'app/payment/statistic-all.html'
    }
});

app.directive('paid', function() {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            paid: '@'
        },
        link: function(scope, element, attr) {
            if(scope.paid == 'true') {
                element.append('<span class="paid glyphicon glyphicon-ok"></span>')
            } else {
                element.append('<span class="unpaid glyphicon glyphicon-remove"></span>')
            }
        }

    }
});