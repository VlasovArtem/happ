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

app.filter('monthFilter', function() {
    return function(months, year) {
        if(year < new Date().getFullYear() && new Date().getMonth() == 11) {
            return months
        } else {
            var currentMonth = new Date().getMonth();
            var filteredMonths = [];
            for(var i = 0; i <= new Date().getMonth(); i++) {
                filteredMonths.push(months[i]);
            }
            return filteredMonths;
        }
    }
});
