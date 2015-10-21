/**
 * Created by artemvlasov on 21/10/15.
 */
var service = angular.module('user-services', ['ngResource']);

service.factory('UserFactory', ['$resource', function($resource) {
    return $resource('/rest/user/:registration/:login/:authentication/:info', {
        registration: '@registration',
        login: '@login'
    }, {
        registration: {
            params: {
                registration: 'registration'
            },
            method: 'POST',

        },
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
