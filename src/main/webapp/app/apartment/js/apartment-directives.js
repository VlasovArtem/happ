/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('apartment-directives', []);

app.directive('apartment', function ($sessionStorage, PaymentFactory, $location) {
    return {
        restrict: 'E',
        scope: {
            apartment: '=apart'
        },
        link: function(scope, element, attr) {
            scope.unpaidPayments = 0;
            PaymentFactory.countUnpaid({addressId : scope.apartment.address.id}, function(data) {
                scope.unpaidPayments = data.count;
            });
            scope.addPayment = function() {
                $sessionStorage.apartment = scope.apartment;
                $location.path('/payment/add');
            };
            scope.showUnpaid = function() {
                $sessionStorage.apartment = scope.apartment;
                $location.path('/payment/unpaid')
            };
            scope.showStatistic = function() {
                $sessionStorage.apartment = scope.apartment;
                $location.path('/payment/statistic')
            }
        },
        templateUrl: 'app/apartment/apartment-block.html'
    }
});

app.directive('apartmentInfo', function() {
    return {
        restrict: 'E',
        scope: {
            address: '='
        },
        templateUrl: 'app/apartment/apartment-info.html'
    }
});