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
    return $resource('/rest/payment/:add/:last', {
        add: '@add',
        last: '@last'
    }, {
        save: {
            method: 'POST',
            params: {
                add: 'add'
            }
        },
        last: {
            method: 'POST',
            params: {
                last: 'last'
            }
        }
    })
});