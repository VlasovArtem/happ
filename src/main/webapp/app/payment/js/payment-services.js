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
    return $resource('/rest/payment/:add/:last/:unpaid/:count/:get/:stat/:month/:service/:all/:paid/:id', {
        add: '@add',
        last: '@last',
        unpaid: '@unpaid',
        count: '@count',
        paid: '@paid',
        id: '@id'
    }, {
        save: {
            method: 'POST',
            params: {
                add: 'add'
            }
        },
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

app.service('StatisticService', function() {
    this.getSummary = function(payments) {
        var summary = {
            total: payments.length
        };
        var sum = 0;
        var paid = 0;
        _.each(payments, function(payment) {
            if(payment.paid) {
                paid++;
            }
            sum += payment.payment_sum;
        });
        summary.paid = paid;
        summary.unpaid = summary.total - paid;
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
    this.months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
});
