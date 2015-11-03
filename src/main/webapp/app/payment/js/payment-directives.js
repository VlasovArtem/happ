/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-directives', []);

app.directive('originalMeter', function() {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
            var countSum = function() {
                var sum = scope.countPaymentSum(scope.payment.prevMeter[0], scope.payment.curMeter[0]);
                scope.updateSum(sum)
            };
            scope.changed = function() {
                scope.updateSum(null);
                if(scope.payment.curMeter.length > 0) {
                    if (checkBeforeSumCount()) {
                        countSum();
                    }
                    if (scope.payment.prevMeter[0] > scope.payment.curMeter[0]) {
                        scope.error = "Prev meter cannot be greater than cur meter"
                    } else {
                        scope.error = null
                    }
                }
            };
            var checkBeforeSumCount = function() {
                return scope.payment.prevMeter[0] < scope.payment.curMeter[0];
            }
        },
        templateUrl: 'app/payment/service-type/meters/original-meter.html'
    }
});

app.directive('twoTariffMeter', function() {
    return {
        restrict: 'E',
        replace: true,
        link: function(scope, element, attr) {
            var countSum = function() {
                var daySum = Number(scope.countPaymentSum(scope.payment.prevMeter[0], scope.payment.curMeter[0]));
                var nightSum = Number(scope.countPaymentSum(scope.payment.prevMeter[1], scope.payment.curMeter[1]));
                var sum = daySum + nightSum * 0.5;
                scope.updateSum(sum.toFixed(2))
            };
            scope.changed = function() {
                scope.updateSum(null);
                if(scope.payment.curMeter.length > 0) {
                    if (checkBeforeSumCount()) {
                        countSum();
                    }
                    if (scope.payment.prevMeter[0] > scope.payment.curMeter[0] || scope.payment.prevMeter[1] > scope.payment.curMeter[1]) {
                        scope.error = "Prev meter cannot be greater than cur meter"
                    } else {
                        scope.error = null
                    }
                }
            };
            var checkBeforeSumCount = function() {
                return _.every(scope.payment.prevMeter, function(value, index) {
                    return value < scope.payment.curMeter[index];
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
            scope.meterTypes = [
                {
                    name : "ORIGINAL",
                    alias : "Обычный"
                }, {
                    name: "TWO_TARIFF",
                    alias: "Двухтарифный"
                }];
            scope.meterType = scope.meterTypes[0];
            angular.element('.meter-data').append('<original-meter></original-meter>');
            $compile(angular.element('.meter-data'))(scope);
            scope.changeMeterType = function(type) {
                angular.element('.meter').remove();
                if(_.isEqual(type, scope.meterTypes[1].name)) {
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
                scope.payment.paymentSum = sum;
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
                        scope.payment.paymentSum = Number(sum.toFixed(2));
                    }
                }
            };
            scope.userPreviousData = function() {
                scope.payment.service = scope.previousPayment.service;
                scope.payment.paymentSum = scope.previousPayment.paymentSum;
                scope.payment.personalAccount = scope.previousPayment.personalAccount;
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
            scope.userPreviousData = function() {
                scope.payment.service = scope.previousPayment.service;
                scope.payment.paymentSum = scope.previousPayment.paymentSum;
                scope.payment.personalAccount = scope.previousPayment.personalAccount
            }

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
                    if(scope.service.type.group != "OTHER") {
                        PaymentFactory.last({
                                apartmentId: scope.apartment.id,
                                type: scope.service.type.alias
                            },
                            function (prevPayment) {
                                if (prevPayment) {
                                    console.log(prevPayment);
                                    console.log(scope.service.id);
                                    console.log(prevPayment.service.id);
                                    if(_.isEqual(scope.service.id, prevPayment.service.id)) {
                                        if (scope.service.type.group == "WATER" || scope.service.type.group == "GAS") {
                                            PreviousPayment.updatePayment(prevPayment, scope.payment);
                                        } else if (scope.service.type.group == "ELECTRICITY" && prevPayment.meterType != scope.meterTypes[0].name) {
                                            scope.changeMeterType(prevPayment.meterType);
                                            PreviousPayment.updatePayment(prevPayment, scope.payment);
                                        } else if (scope.service.type.group == "MAINTENANCE") {
                                            scope.previousPayment = prevPayment;
                                            PreviousPayment.updatePayment(prevPayment, scope.payment);
                                        }
                                    }
                                }
                            }, function () {
                                scope.previousPayment = null;
                                scope.payment = Payment.clearPayment();
                                if (scope.service.type.group == "OTHER") {
                                    scope.payment.service.type = {
                                        subtypes: []
                                    };
                                }
                            }
                        );
                    } else {
                        PaymentFactory.lastOther({
                            apartmentId: scope.apartment.id
                        }, function(data) {
                            console.log(data);
                            if(data.length > 0) {
                                if (data.length > 1) {
                                    scope.previousPayments = data;
                                } else {
                                    scope.previousPayment = data[0];
                                }
                            }
                        })
                    }
                };
                scope.addPayment = function () {
                    if(scope.service) {
                        scope.payment.service = scope.service;
                    } else if(scope.type.group = "OTHER") {
                        _.each(scope.type, function(value, key) {
                            if(key != "subtypes") {
                                scope.payment.service.type[key] = value
                            }
                        });
                    }
                    scope.payment.paymentDate = $filter('ToLocalDateFilter')(scope.payment.paymentDate);
                    if(!scope.payment.service.city) {
                        scope.payment.service.city = scope.apartment.address.city;
                    }
                    var convertedPrevMeter = [];
                    _.each(scope.payment.prevMeter, function(meter) {
                        if(meter != 0) {
                            convertedPrevMeter.push(meter);
                        }
                    });
                    scope.payment.prevMeter = convertedPrevMeter.length > 0 ? convertedPrevMeter : null;
                    Service.perPersistService(scope.payment.service);
                    Payment.prePersistPayment(scope.payment);
                    PaymentSaveFactory.save(scope.payment, function() {
                        $location.path('/apartments')
                    })
                };
                scope.payment = Payment.clearPayment();
            }
        }
    }
);

app.directive('serviceInfo', function() {
    return {
        link: function(scope, element, attr) {

        },
        template: ''
    }
});

app.directive('chooseService', function(ServiceFactory, Payment, Service, $location) {
    return {
        link: function(scope, element, attr) {
            ServiceFactory.query({get: 'get', types: 'types'}, function(data) {
                scope.types = data;
            });
            scope.chooseServiceType = function(type) {
                if (_.contains(['GAS', 'WATER', 'ELECTRICITY', 'HEATING'], type.group)) {
                    $location.path('/payment/add/regular');
                } else if(_.isEqual(type.group, 'MAINTENANCE')) {
                    $location.path('/payment/add/maintenance');
                } else {
                    $location.path('/payment/add/other');
                }
                //angular.element('.service-data').remove();
                //scope.payment = Payment.clearPayment();
                //if(angular.isDefined(type.alias)) {
                //    ServiceFactory.query({
                //        get: 'get',
                //        all: 'all',
                //        city: scope.apartment.address.city.alias,
                //        type: type.alias
                //    }, function (services) {
                //        _.each(services, function(service) {
                //            Service.updateService(service);
                //        });
                //        if (services.length == 1) {
                //            scope.service = services[0];
                //        } else {
                //            scope.services = services;
                //        }
                //    });
                //    var appendedDirective;
                //    var appendedElement;
                //    if(_.isEqual(type.group, 'MAINTENANCE')) {
                //        appendedDirective = 'maintenance-service';
                //        appendedElement = '<maintenance-service></maintenance-service>'
                //    } else if(_.contains(['GAS', 'WATER', 'ELECTRICITY', 'HEATING'], type.group)) {
                //        appendedDirective = 'regular-service';
                //        appendedElement = '<regular-service></regular-service>'
                //    } else {
                //        appendedDirective = 'other-service';
                //        appendedElement = '<other-service></other-service>'
                //    }
                //
                //    angular.element('.service-payment-info').append(appendedElement);
                //    $compile(angular.element(appendedDirective))(scope)
                //}
            };
        },
        template:
        '<div class="col-md-2 info"> ' +
            '<label for="type">Сервис:</label> ' +
            '<select name="type" id="type" ng-model="type" class="form-control happ-form-control" ng-change="chooseServiceType(type)" ng-options="(type.name | camelCase) for type in types"> ' +
                '<option value="" disabled selected>Выберите сервис...</option> ' +
            '</select> ' +
        '</div>'
    }
});

app.directive('statistic', function($sessionStorage, $compile) {
    var link = function(scope, element, attr) {
        scope.apartment = $sessionStorage.apartment;
        var statistics = {
            byMonth: {
                tag: '<statistic-by-month class="statistic"></statistic-by-month>',
                class: '.by-month'
            },
            byService: {
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
        scope.switch('byMonth');
    };
    return {
        restrict: 'E',
        link: link,
        templateUrl: 'app/payment/statistic-block.html'
    }
});

app.directive('statisticByMonth', function(PaymentFactory, StatisticService) {
    var controller = ['$scope', '$filter', function($scope, $filter) {
        $scope.viewInfo = null;
        $scope.months = StatisticService.months;
        $scope.years = StatisticService.getYears();
        $scope.currentMonth = $scope.months[new Date().getMonth()];
        $scope.viewInfo = angular.copy($scope.currentMonth);
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
                $scope.viewInfo = angular.copy($scope.currentMonth);
            })
        };
        $scope.showNewStatistic();
    }];
    return {
        controller: controller,
        scope: true,
        templateUrl: 'app/payment/statistic-by-month.html'
    }
});

app.directive('statisticByService', function(PaymentFactory, ServiceFactory, StatisticService) {
    var controller = ['$scope', '$filter', function($scope, $filter) {
        $scope.viewInfo = null;
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
                $scope.viewInfo = $filter('firstCapital')(angular.copy($scope.currentType.name));
                $scope.summary = StatisticService.getSummary(data);
            });
        }
    }];
    return {
        controller: controller,
        scope: true,
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
        scope: true,
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