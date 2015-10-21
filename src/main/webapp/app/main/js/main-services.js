/**
 * Created by artemvlasov on 21/10/15.
 */
var app = angular.module('main-services', ['ngResource']);

app.service('auth', ['$resource', '$location', '$route', 'UserFactory',
    function($resource, $location, $route, UserFactory) {
        var auth = {
            resolved: false,
            loginPath: '/signin',
            logoutPath: '/logout',
            homePath: '/',
            error: null,
            authentication: function() {
                UserFactory.get(
                    {authentication : 'authentication'},
                    function(data) {
                        auth.admin = data.role == "ADMIN";
                        auth.authenticated = true;
                        auth.resolved = true;
                    }, function() {
                        auth.authenticated = false;
                        auth.resolved = true;
                    }
                );
            },
            authenticate: function(credentials, callback) {
                UserFactory.login($.param(credentials), function() {
                    auth.authenticated = true;
                    auth.authentication();
                    $location.path(auth.homePath);
                    $route.reload();
                }, function() {
                    auth.authenticated = false;
                    callback && callback("You have an error in email, login, password or person deleted.");
                })
            },
            clear: function() {
                auth.authenticated = false;
                $location.path(auth.loginPath);
                var res = $resource('/rest/user/logout');
                res.get();
            },
            init: function(homePath, loginPath, logoutPath) {
                auth.homePath = homePath;
                auth.loginPath = loginPath;
                auth.logoutPath = logoutPath;
                auth.authentication();
            }
        };
        return auth;
    }
]);