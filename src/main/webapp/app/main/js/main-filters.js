/**
 * Created by artemvlasov on 05/10/15.
 */
var app = angular.module('main-filters', []);

app.filter('camelCase', function() {
    return function(object) {
        var convertedString = _.isString(object) ? object : object.toString();
        convertedString = convertedString.replace("_", " ");
        var finalText = "";
        return finalText.concat(convertedString.charAt(0).toUpperCase()).concat(convertedString.substr(1).toLowerCase());
    }
});