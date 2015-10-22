var app = angular.module('apartment-services', ['ngResource']);

app.factory('AddApartment', ['$resource', function($resource) {
    return $resource('/rest/apartment/add');
}]);

app.factory('ApartmentFactory', ['$resource', function($resource) {
    return $resource('/rest/apartment/:get/:all/:count', {
        get: '@get',
        all: '@all'
    })
}]);

app.factory('Cities', ['$resource', function($resource) {
    return $resource('/rest/city/get/all');
}]);

app.factory('StreetsFactory', ['$resource', function ($resource) {
    return $resource('/rest/street/search')
}]);

app.factory('StatisticFactory', ['$resource', function($resource) {
    return $resource('/rest/stat/:unpaid/:sum/:account/:stat');
}]);