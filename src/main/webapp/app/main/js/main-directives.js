/**
 * Created by artemvlasov on 23/09/15.
 */
var app = angular.module('main-directives', ['ngResource']);

app.directive('apartment', function() {
    return {
        restrict: 'A',
        scope: {
            apartment: '='
        },
        replace: true,
        template: '<div class="apartment-info">{{apartment.address.city.name + ", " + (apartment.address.street | streetConverter) + " " + apartment.address.house + ", кв. " + apartment.address.apartment}}</div>'
    }
});

app.directive('loading', function() {
    return {
        restrict: 'E',
        link: function (scope, element, attr) {
            element.show();
        },
        templateUrl: 'app/main/loading.html'
    }
});

app.directive('validationMessages', function () {
    return {
        scope: {
            modelController: '=',
            messages: '='
        },
        restrict: 'EA',
        link: function (scope, elm, attrs) {
            scope.$watch('modelController.$error', function (newValue) {
                if (newValue) {
                    scope.errorMessages = [];
                    angular.forEach(newValue, function (value, key) {
                        scope.errorMessages.push(scope.messages[scope.modelController.$name][key]);
                    });
                    //if(scope.errorMessages.length > 0) {
                    //    elm.parent().addClass('has-error');
                    //    elm.parent().removeClass('has-success');
                    //} else {
                    //    elm.parent().addClass('has-success');
                    //    elm.parent().removeClass('has-error');
                    //}
                }
            }, true);
        },
        template: '<small class="help-block error-info" ng-repeat="message in errorMessages" ng-show= "!modelController.$pristine && $first" class="warning">{{message}}</small>'
    }
});

app.directive('accountInfo', function(StatisticFactory, $rootScope, auth, $q) {
    return {
        scope: true,
        link: function(scope, element, attr) {
            function authSync() {
                return $q(function(resolve, reject) {
                    setTimeout(function() {
                        if(auth.authenticated) {
                            resolve(auth.authenticated)
                        } else {
                            reject(auth.authenticated)
                        }
                    }, 10)
                })
            }
            authSync().then(function(info) {
                StatisticFactory.get({account: 'account', stat: 'stat'}).$promise.then(function (data) {
                    $rootScope.count = data.apartmentsCount;
                    scope.info = data;
                    scope.emptyAccountInfo = false;
                }, function() {
                    scope.emptyAccountInfo = true;
                });
            }, function(reason) {
            });
        }
    }
});
