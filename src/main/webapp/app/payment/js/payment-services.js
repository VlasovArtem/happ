/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-services', ['ngResource']);

app.factory('ServiceFactory', function($resource) {
    return $resource('/rest/service/:get/:all/:types', {
        get: '@get',
        all: '@all',
        types: '@types'
    })
});