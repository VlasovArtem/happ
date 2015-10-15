/**
 * Created by artemvlasov on 07/10/15.
 */
var app = angular.module('apartment-filters', []);

app.filter('streetConverter', function($filter) {
    return function(street) {
        if(street) {
            return $filter('camelCase')(street.type) + ' ' + street.name;
        }
    }
});

app.filter('addressConverter', function($filter) {
    return function(address) {
        if(address) {
            return $filter('camelCase')(address.street.type) + ' ' + address.street.name + ' ' + address.house + ', кв. ' + address.apartment;
        }
    }
})
