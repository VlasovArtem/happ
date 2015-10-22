/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-services', ['ngResource']);

app.factory('ServiceFactory', function($resource) {
    return $resource('/rest/service/:get/:all/:types/:meters', {
        get: '@get',
        all: '@all',
        types: '@types',
        meters: '@meters'
    })
});

app.factory('PaymentFactory', function($resource) {
    return $resource('/rest/payment/:last/:unpaid/:count/:get/:stat/:month/:service/:all/:paid/:id', {
        last: '@last',
        unpaid: '@unpaid',
        count: '@count',
        paid: '@paid',
        id: '@id'
    }, {
        last: {
            method: 'GET',
            params: {
                last: 'last'
            }
        },
        countUnpaid: {
            method: 'GET',
            params: {
                unpaid: 'unpaid',
                count: 'count'
            }
        },
        setPaid: {
            method: 'PUT',
            params: {
                paid: 'paid'
            }
        }
    })
});

app.factory('PaymentSaveFactory', function($resource) {
    return $resource('/rest/payment/add');
});

app.service('StatisticService', function() {
    this.getSummary = function(payments) {
        var summary = {
            total: payments.length
        };
        var sum = 0;
        var paid = 0;
        var unpaid_month_sum = 0;
        _.each(payments, function(payment) {
            if(payment.paid) {
                paid++;
            } else {
                unpaid_month_sum += payment.payment_sum;
            }
            sum += payment.payment_sum;
        });
        summary.paid = paid;
        summary.unpaid = summary.total - paid;
        summary.unpaid_month_sum = unpaid_month_sum.toFixed(2);
        summary.sum = sum.toFixed(2);
        return summary;
    };
    this.getYears = function() {
        var years = [2015];
        for(var i = 0; i < new Date().getFullYear() - years[0]; i++) {
            years.push(years[0] + (i + 1));
        }
        return years;
    };
    this.months = ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'];
});

app.service('Service', function() {
    var initialService = null;
    this.initService = function(apartment) {
        initialService = {
            alias: null,
            city: apartment.address.city,
            id: null,
            name: null,
            rates: [],
            service_information: null,
            type: null,
            volumes: []
        }
    };
    this.clearService = function() {
        return angular.copy(initialService)
    };
    this.updateService = function(service) {
        if(initialService) {
            _.each(initialService, function (value, key) {
                if (initialService[key] != null && service[key] == null) {
                    service[key] = value
                }
            })
        }
    };
    this.perPersistService = function(service) {
        _.each(service, function (value, key) {
            if (_.isArray(value) && value.length == 0) {
                service[key] = null;
            }
        })
    }
});

app.service('Payment', function(Service) {
    var initialPayment = null;
    this.initPayment = function(apartment) {
        initialPayment = {
            id: null,
            service: Service.clearService(),
            personal_account: null,
            meter_type: 'ORIGINAL',
            description: null,
            apartment_id: apartment.id,
            payment_sum: null,
            payment_date: new Date(),
            paid: false,
            prev_meter: [0, 0],
            cur_meter: []
        }
    };
    this.clearPayment = function() {
        return angular.copy(initialPayment);
    };
    this.updatePayment = function(payment) {
        if(initialPayment) {
            _.each(initialPayment, function (value, key) {
                if (initialPayment[key] != null && payment[key] == null) {
                    payment[key] = value
                }
            })
        }
    };
    this.prePersistPayment = function(payment) {
        _.each(payment, function (value, key) {
            if (_.isArray(value) && (value.length == 0 || _.every(value, function(val) { return val == 0 }))) {
                payment[key] = null;
            }
        })
    }
});

app.service('PreviousPayment', function() {
    var ignoredKeys = ["id", "payment_date", "paid", "cur_meter", "prev_meter", "$promise", "$resolved", "created_date", "payment_sum"];
    this.updatePayment = function(prevPayment, newPayment) {
        _.each(prevPayment, function(value, key) {
            if(!_.contains(ignoredKeys, key)) {
                newPayment[key] = value;
            } else if (key == "cur_meter") {
                newPayment["prev_meter"] = value
            }
        });
    }
});
