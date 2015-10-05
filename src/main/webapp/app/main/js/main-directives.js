/**
 * Created by artemvlasov on 23/09/15.
 */
var app = angular.module('main-directives', []);

app.directive('apartment', function() {
    return {
        restrict: 'A',
        scope: {
            apartment: '='
        },
        replace: true,
        template: '<div class="apartment-info">{{apartment.address.city.name + ", " + apartment.address.street + " " + apartment.address.house + ", ap. " + apartment.address.apartment}}</div>'
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
})