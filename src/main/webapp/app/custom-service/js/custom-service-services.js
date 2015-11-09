/**
 * Created by artemvlasov on 04/11/15.
 */
var app = angular.module('custom-service-services', ['ngResource']);

app.factory('AddCustomService', function($resource) {
    return $resource('/rest/custom/service/add')
});