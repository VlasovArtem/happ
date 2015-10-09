/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-controllers', []);

app.controller('AddPaymentCtrl', ['$scope', 'types', 'meters', 'apartment',
    function($scope, types, meters, apartment) {
        $scope.types = types;
        $scope.meters = meters;
        $scope.apartment = apartment;
    }
]);

app.controller('UnpaidPaymentCtrl', ['$scope', 'payments', '$route', 'PaymentFactory',
    function($scope, payments, $route, PaymentFactory) {
        $scope.address = payments[0].address;
        $scope.payments = payments;
        $scope.setPaid = function(paymentId) {
            PaymentFactory.setPaid({id : paymentId}, function() {
                $route.reload();
            }, function() {
                $scope.error = "Ooops"
            })
        }
    }
]);

app.controller('PaymentStatisticCtrl', ['$scope', 'apartment',
    function($scope, apartment) {
        $scope.address = apartment.address;
    }
]);