/**
 * Created by artemvlasov on 21/10/15.
 */
var service = angular.module('user-services', ['ngResource']);

service.factory('UserFactory', ['$resource', function($resource) {
    return $resource('/rest/user/:login/:authentication/:info', {
        registration: '@registration',
        login: '@login'
    }, {
        login: {
            params: {
                login: 'login'
            },
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
    })
}]);

service.factory('UserRegistrationFactory', ['$resource', function($resource) {
    return $resource('/rest/user/registration')
}]);
