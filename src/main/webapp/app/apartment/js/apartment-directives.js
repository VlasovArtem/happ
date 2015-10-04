/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('apartment-directives', []);

app.directive('apartment', function () {
    return {
        restrict: 'E',
        templateUrl: 'app/apartment/apartment-block.html'
    }
});