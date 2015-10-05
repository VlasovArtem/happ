/**
 * Created by artemvlasov on 05/10/15.
 */
var app = angular.module('payment-filters', []);

app.filter('ToLocalDateFilter', function() {
    return function(date) {
        if(angular.isDefined(date) && _.isDate(date)) {
            var year = date.getFullYear(),
                month = (date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : '0' + (date.getMonth() + 1),
                parsedDate = date.getDate() >= 10 ? date.getDate() : '0' + date.getDate();
            return year + "-" + month + "-" + parsedDate;
        }
    }
});