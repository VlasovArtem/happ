/**
 * Created by artemvlasov on 04/10/15.
 */
var app = angular.module('apartment-directives', []);

app.directive('apartment', function ($sessionStorage, PaymentFactory, $location, StatisticFactory, ApartmentFactory, $route) {
    return {
        restrict: 'E',
        scope: {
            apartment: '=apart'
        },
        link: function(scope, element, attr) {
            StatisticFactory.get({unpaid : 'unpaid', sum : 'sum', apartmentId : scope.apartment.id},
                function(data) {
                    if(data.unpaid && data.unpaidSum) {
                        scope.unpaidPayments = data.unpaid;
                        scope.unpaidSum = data.unpaidSum.toFixed(2);
                    }
                }
            );
            //scope.addPayment = function() {
            //    $sessionStorage.apartment = scope.apartment;
            //    $location.path('/payment/add');
            //};
            scope.addPayment = function() {
                $sessionStorage.apartment = scope.apartment;
                var preparedLink = '/service/' + scope.apartment.id;
                $location.path(preparedLink);
            };
            scope.showUnpaid = function() {
                $sessionStorage.apartment = scope.apartment;
                $location.path('/payment/unpaid')
            };
            scope.showStatistic = function() {
                $sessionStorage.apartment = scope.apartment;
                $location.path('/payment/statistic')
            };
            scope.removeApartment = function() {
                var deleteConfirmed = confirm('Вы уверены, что хотите удалить эту квартиру?');
                if(deleteConfirmed) {
                    ApartmentFactory.delete({delete: 'delete', id: scope.apartment.id}, function () {
                        $route.reload();
                    });
                }
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

app.directive('streetSelect', function (StreetsFactory) {
    return {
        link: function (scope, elem, attr) {
            scope.refreshCityStreets = function(text) {
                if(scope.apartment.address) {
                    if(scope.apartment.address.city != "") {
                        StreetsFactory.query({text: text, city: scope.apartment.address.city.alias}, function (data) {
                            scope.streets = data;
                        })
                    }
                }
            };
        },
        template:
            '<ui-select ng-model="apartment.address.street" reset-search-input="false" ng-disabled="!apartment.address.city">' +
                '<ui-select-match placeholder="Введите улицу...">{{$select.selected.type + " " + $select.selected.name}}</ui-select-match>' +
                '<ui-select-choices repeat="street in streets" refresh="refreshCityStreets($select.search)" refresh-delay="1000">' +
                    '<div ng-bind-html="street | streetConverter | highlight: $select.search"></div>' +
                '</ui-select-choices>' +
            '</ui-select>'
    }
});