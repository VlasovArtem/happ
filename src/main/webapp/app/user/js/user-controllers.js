/**
 * Created by artemvlasov on 21/10/15.
 */
var app = angular.module('user-controllers', []);

app.controller('UserRegistrationCtrl', ['$scope', '$route', '$location', 'UserFactory', function($scope, $route, $location, UserFactory) {
    $scope.cancel = function() {
        $route.reload();
    };

    $scope.registration = function() {
        UserFactory.registration($scope.user, function() {
            $location.path('/');
        }, function(data) {
            $scope.error = data.data.error;
        })
    };

    $scope.validationMessages = {
        firstname: {
            pattern : "Поле поддерживает следующие символы a-z, а-я ",
            minlength : "Минимальная длинна имени 6 символов",
            maxlength : "Максимальная длинна имени 25 символов"
        },
        lastname: {
            pattern: "Поле поддерживает следующие символы a-z, а-я,', ,. ",
            minlength: "Минимальная длинна имени 6 символов",
            maxlength: "Максимальная длинна имени 25 символов"
        },
        email: {
            required: "Обязательное поле",
            minlength: "Минимальная длинна email 9 символов",
            maxlength: "Максимальная длинна email 254 символа",
            email: "Email невалидный"
        },
        login: {
            pattern: "Поле поддерживает следующие символы a-z, а-я,_,-, ,.",
            required: "Обязательное поле",
            minlength: "Минимальная длинна логин 6 символов",
            maxlength: "Максимальная длинна логин 100 символа"
        },
        password: {
            required: "Обязательное поле",
            minlength: "Минимальная длинна пароля 8 символов",
            maxlength: "Максимальная длинна пароля 128 символа"
        }
    }
}]);