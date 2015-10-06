/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('payment-controllers', []);

app.controller('AddPaymentCtrl', ['$scope', 'types', 'meters', 'apartment', 'ServiceFactory', '$filter', '$compile',
    function($scope, types, meters, apartment, ServiceFactory, $filter, $compile) {
        $scope.types = types;
        $scope.meters = meters;
        $scope.apartment = apartment;
    }
]);