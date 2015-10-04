var app = angular.module('apartment-services', ['ngResource']);

app.factory('AddApartment', ['$resource', function($resource) {
    return $resource('/rest/apartment/add');
}]);