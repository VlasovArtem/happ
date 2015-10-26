/**
 * Created by artemvlasov on 21/10/15.
 */
var app = angular.module('user-controllers', []);

app.controller('UserRegistrationCtrl', ['$scope', '$route', '$location', 'UserFactory', 'UserRegistrationFactory', '$templateCache', 'auth',
    function($scope, $route, $location, UserFactory, UserRegistrationFactory, $templateCache, auth) {
    $scope.cancel = function() {
        $route.reload();
    };

    $scope.registration = function() {
        UserRegistrationFactory.save($scope.user, function() {
            $templateCache.put('success-info.html', '<article class="success-info"><h2>Congratulation, you' +
                ' successfully registered on Happ Project.</h2><h3>You will be login and redirect to main page in' +
                ' {{seconds}}... or click the <a href="/">redirect</a></h3></article>');
            var loginInfo = {
                loginData : $scope.user.login,
                password : $scope.user.password
            };
            auth.authenticate(loginInfo);
            $location.path('/success');
        }, function(data) {
            $scope.error = data.data.error;
        })
    };

    $scope.setFocus = function(id) {
        angular.element('#' + id).parents('.happ-form-group').addClass('is-focused');
    };
    $scope.setBlur = function(id) {
        angular.element('#' + id).parents('.happ-form-group').removeClass('is-focused');
    };

    $scope.validationMessages = {
        firstname: {
            pattern : "Поле поддерживает следующие символы a-z, а-я ",
            minlength : "Минимальная длинна имени 3 символов",
            maxlength : "Максимальная длинна имени 25 символов"
        },
        lastname: {
            pattern: "Поле поддерживает следующие символы a-z, а-я,', ,. ",
            minlength: "Минимальная длинна имени 2 символов",
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