/**
 * Created by artemvlasov on 04/11/15.
 */
var app = angular.module('custom-service-controllers', []);

app.controller('AddCustomService', ['$scope', 'cities', 'subtypes', 'AddCustomService',
    function($scope, cities, subtypes, AddCustomService) {
        $scope.customService = {
            service : {
                "type": {
                    "name": "Другое",
                    "alias": "other",
                    "group": "OTHER",
                    "subtypes": []
                }
            }
        };
        angular.element('form[name=addServiceForm] select');
        $scope.changeSelect = function(id) {
            angular.element('#' + id).css("color", "#555")
        };
        $scope.selectSubtype = function(subtype) {
            if(!_.isEqual(subtype.alias, 'other')) {
                $scope.customService.service.type.subtypes.push(subtype);
            } else {
                $scope.customService.service.type.subtypes = [];
            }
            $scope.changeSelect('subtype');
        };
        $scope.cities = cities;
        $scope.subtypes = subtypes;
        $scope.setFocus = function(id) {
            angular.element('#' + id).parents('.happ-form-group').addClass('is-focused');
        };
        $scope.setBlur = function(id) {
            angular.element('#' + id).parents('.happ-form-group').removeClass('is-focused');
        };
        $scope.addCustomService = function() {
            AddCustomService.save($scope.customService);
        };
        $scope.validationMessages = {
            name: {
                pattern : "Поле поддерживает следующие символы a-z, а-я ",
                minlength : "Минимальная длинна имени 3 символов",
                maxlength : "Максимальная длинна имени 50 символов"
            },
            customSubtype: {
                minlength : "Минимальная длинна имени 3 символов",
                maxlength : "Максимальная длинна имени 100 символов"
            },
            bankCode: {
                min: "Минимальное значение кода банка 300000",
                max: "Максимальное значение кода банка 399999",
                number: "Поле должно содержать, только цифры"
            },
            checkingAccount: {
                min: "Минимальное значение расчётного счёта 26000000000000",
                max: "Максимальное значение расчётного счёта 26999999999999",
                number: "Поле должно содержать, только цифры"
            }
        }
    }
]);