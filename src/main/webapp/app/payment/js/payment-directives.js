/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-directives', []);

app.directive('originalMeter', function() {
    return {
        restrict: 'E',
        link: function(scope, element, attr) {
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

app.directive('regularService', function($compile, Payment) {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
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
                scope.payment = Payment.clearPayment();
            };
            scope.findLast();
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
            scope.validationMessage = {
                serviceName: {
                    required: 'Service name is required',
                    maxlength: 'Max length of service name is 50 characters'
                },
                footage: {
                    required: 'Footage of the apartment is required',
                    min: 'Footage cannot be negative',
                    max: 'Footage cannot be greater than 500'
                },
                sum: {
                    required: 'Payment sum is required'
                }
            };
            scope.findLast();
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

app.directive('otherService', function() {
    return {
        replace: true,
        link: function(scope, element, attr) {
            scope.validationMessage = {
                serviceName: {
                    required: 'Service name is required',
                    maxlength: 'Max length of service name is 50 characters'
                },
                personalAccount: {
                    maxlenght: 'Personal account max length is 16'
                },
                sum: {
                    required: 'Payment sum is required'
                }
            };
            scope.findLast();

        },
        templateUrl: 'app/payment/service-type/other.html'
    }
});

app.directive('servicePayment',
    function(ServiceFactory, PaymentFactory, PaymentSaveFactory, $compile, $filter, $location, Service, Payment, PreviousPayment) {
        return {
            restrict: 'A',
            link: function(scope, element, attr) {
                scope.status = {
                    opened: false
                };
                scope.open = function($event) {
                    scope.status.opened = true;
                };
                scope.today = new Date();
                scope.today.setHours(0, 0, 0, 0);
                scope.chooseServiceType = function(type) {
                    angular.element('.service-data').remove();
                    scope.payment = Payment.clearPayment();
                    if(angular.isDefined(type.alias)) {
                        ServiceFactory.query({
                            get: 'get',
                            all: 'all',
                            city: scope.apartment.address.city.alias,
                            type: type.alias
                        }, function (services) {
                            _.each(services, function(service) {
                                Service.updateService(service);
                            });
                            if (services.length == 1) {
                                scope.service = services[0];
                            } else {
                                scope.services = services;
                            }
                        });
                        var appendedDirective;
                        var appendedElement;
                        if(_.isEqual(type.group, 'MAINTENANCE')) {
                            appendedDirective = 'maintenance-service';
                            appendedElement = '<maintenance-service></maintenance-service>'
                        } else if(_.contains(['GAS', 'WATER', 'ELECTRICITY', 'HEATING'], type.group)) {
                            appendedDirective = 'regular-service';
                            appendedElement = '<regular-service></regular-service>'
                        } else {
                            appendedDirective = 'other-service';
                            appendedElement = '<other-service></other-service>'
                        }

                        angular.element('.service-payment-info').append(appendedElement);
                        $compile(angular.element(appendedDirective))(scope)
                    }
                };
                scope.findLast = function() {
                    PaymentFactory.last({
                            apartmentId: scope.apartment.id,
                            type: scope.type.alias
                        },
                        function (prevPayment) {
                            if(prevPayment) {
                                if(scope.type.group == "ELECTRICITY") {
                                    scope.changeMeterType(prevPayment.meter_type);
                                }
                                PreviousPayment.updatePayment(prevPayment, scope.payment);
                                if(scope.type.group == "MAINTENANCE" && scope.type.group == "OTHER") {
                                    Service.updateService(scope.payment.service);
                                    scope.previousPayment = prevPayment;
                                }
                            }
                        }, function() {
                            scope.previousPayment = null;
                            scope.payment = Payment.clearPayment();
                            if(scope.type.group == "OTHER") {
                                scope.payment.service.type = {
                                    subtypes: []
                                };
                            }
                        }
                    );
                };
                scope.addPayment = function () {
                    if(scope.service) {
                        scope.payment.service = scope.service;
                    }
                    scope.payment.payment_date = $filter('ToLocalDateFilter')(scope.payment.payment_date);
                    if(!scope.payment.service.city) {
                        scope.payment.service.city = scope.apartment.address.city;
                    }
                    var convertedPrevMeter = [];
                    _.each(scope.payment.prev_meter, function(meter) {
                        if(meter != 0) {
                            convertedPrevMeter.push(meter);
                        }
                    });
                    scope.payment.prev_meter = convertedPrevMeter.length > 0 ? convertedPrevMeter : null;
                    Service.perPersistService(scope.payment.service);
                    Payment.prePersistPayment(scope.payment);
                    PaymentSaveFactory.save(scope.payment, function() {
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
        $scope.months = StatisticService.months;
        $scope.years = StatisticService.getYears();
        $scope.currentMonth = $scope.months[new Date().getMonth()];
        $scope.viewMonth = angular.copy($scope.currentMonth);
        $scope.currentYear = new Date().getFullYear();
        $scope.showNewStatistic = function() {
            PaymentFactory.query({
                get: 'get',
                stat: 'stat',
                month: 'month',
                apartmentId: $scope.apartment.id,
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
            $scope.currentType = data[0];
            $scope.viewType = angular.copy($scope.currentType.name);
            $scope.currentYear = new Date().getFullYear();
            $scope.showNewStatistic();
        });
        $scope.showNewStatistic = function() {
            PaymentFactory.query({
                get: 'get',
                stat: 'stat',
                service: 'service',
                apartmentId: $scope.apartment.id,
                type: $scope.currentType.alias,
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
                apartmentId: $scope.apartment.id,
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

app.directive('statisticInfo', function() {
    return {
        replace: true,
        link: function(scope, elem, attr) {
            if(attr.viewInfo) {
                scope.viewInfo = attr.viewInfo;
            }
        },
        templateUrl: 'app/payment/statistic-info.html'
    }
})