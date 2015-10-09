/**
 * Created by artemvlasov on 05/10/15.
 */
var app = angular.module('main-filters', []);

app.filter('camelCase', function() {
    return function(object) {
        if(angular.isDefined(object)) {
            var convertedString = _.isString(object) ? object : object.toString();
            convertedString = convertedString.replace("_", " ");
            var finalText = "";
            return finalText.concat(convertedString.charAt(0).toUpperCase()).concat(convertedString.substr(1).toLowerCase());
        }
    }
});

app.filter('dateFilter', function() {
    return function(date) {
        var filteredDate = "";
        var convertNumbers = function(date) {
            return date > 9 ? date : "0" + date;
        };
        for(var i = 0; i < 3; i++) {
            if(i == 2) {
                filteredDate += convertNumbers(date[i]);
            } else {
                filteredDate += convertNumbers(date[i]) + '-';
            }
        }
        return filteredDate;
    }
});

app.filter('showArray', function() {
    return function(array) {
        if(_.isArray(array)) {
            var info = "";
            _.each(array, function(value, index) {
                if (index == array.length - 1) {
                    info = info.concat(value)
                } else {
                    info = info.concat(value + ", ")
                }
            });
            return info;
        }
    }
});