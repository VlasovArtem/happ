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
});

app.directive('contentTable',
    function(deviceCheck, $filter) {
        return {
            restrict: 'E',
            scope: {
                sectionId: '@',
                tableInfo: '='
            },
            controller: '@',
            name: "controllerName",
            link: function(scope, element, attr) {
                if(scope.tableInfo.data.content.length == 0) {
                    angular.element("#" + scope.sectionId).hide();
                }
                scope.currentPage = 1;
                scope.maxSize = 5;
                scope.pageSize = [
                    {label: '15', value: 15},
                    {label: '25', value: 25},
                    {label: '50', value: 50},
                    {label: '100', value: 100}
                ];
                scope.currentPageSize = scope.pageSize[0];
                scope.tableData = scope.tableInfo.data.content;
                scope.totalItems = scope.tableInfo.data.total_elements;

                var tabletLandscapeWidth = 960,
                    currentWidth = window.outerWidth,
                    sortInfo = scope.tableInfo.data.sort[0],
                    dataSorting = [],
                    pageable = {
                        direction: sortInfo.direction,
                        property: sortInfo.property,
                        size: scope.currentPageSize.value,
                        page: scope.currentPage - 1
                    };
                scope.tabletLandscape = currentWidth >= tabletLandscapeWidth;
                scope.mobileDevice = deviceCheck.mobileDevice;

                var getData = function(pageable) {
                    scope.tableInfo.factory.get(pageable).$promise.then(function(data) {
                        scope.tableData = data.content;
                        scope.totalItems = data.total_elements;
                    });
                };

                function getNestedData(dataIndex, property) {
                    var nestedProperties = property.split(".");
                    var data = scope.tableData[dataIndex][nestedProperties[0]];
                    _.each(nestedProperties, function(property, index) {
                        if(index != 0) {
                            data = data[property]
                        }
                    });
                    return data;
                }

                scope.setData = function (headDataIndex, dataIndex) {
                    var data = "";
                    var contains = _.some(scope.tableInfo.filteredProperties, function(value) {
                        if(scope.tableInfo.head[headDataIndex].property == value.property) {
                            if(value.filter) {
                                if(value.property.indexOf('phone_number') > -1) {
                                    if(value.property.indexOf('.') > -1) {
                                        data = value.filter(getNestedData(dataIndex, value.property), getNestedData(dataIndex, value.property.split(".")[0].concat(".location.country")))
                                    } else {
                                        data = value.filter(scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property], scope.tableData[dataIndex].location.country);
                                    }
                                } else {
                                    data = value.filter(scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property]);
                                }
                                return true;
                            } else if (value.appender) {
                                if(_.isNumber(scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property])) {
                                    if(scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property] == 0) {
                                        data = 'n/a';
                                    } else {
                                        data = scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property] + value.appender;
                                    }
                                    return true;
                                }
                                data = scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property] + value.appender;
                                return true;
                            }
                        }
                    });
                    if(!contains) {
                        if(scope.tableInfo.head[headDataIndex].property.indexOf(".") > -1) {
                            data = getNestedData(dataIndex, scope.tableInfo.head[headDataIndex].property);
                        } else {
                            data = scope.tableData[dataIndex][scope.tableInfo.head[headDataIndex].property];
                        }
                    }
                    return data;
                };

                _.each(scope.tableInfo.head, function (data) {
                    data.direction = 'DESC'
                });

                scope.$watch("head", function () {
                    Array.prototype.push.apply(dataSorting, scope.tableInfo.head);
                });

                scope.selectSort = function (index) {
                    if (pageable.property == $filter('camelCase')(scope.tableInfo.head[index].property)) {
                        if (pageable.direction != 'DESC') {
                            return 'glyphicon glyphicon-sort-by-alphabet'
                        } else {
                            return 'glyphicon glyphicon-sort-by-alphabet-alt'
                        }
                    } else {
                        return ''
                    }
                };

                /* Functions to get new data */
                scope.changePageSize = function(value) {
                    pageable.size = value;
                    getData(pageable);
                };
                scope.changePage = function() {
                    pageable.page = scope.currentPage - 1;
                    getData(pageable);
                };
                scope.sorting = function(type){
                    if(pageable.property == $filter('camelCase')(type)) {
                        pageable.direction = pageable.direction == 'ASC' ? 'DESC' : 'ASC';
                    } else {
                        pageable.property = $filter('camelCase')(type);
                        pageable.direction = 'DESC';
                    }
                    getData(pageable);
                };

            },
            templateUrl: 'app/main/data-table-template.html'
        }
    }
);

app.directive('validation', function() {
    return {
        scope: {
            model: '=',
            messages: '='
        },
        restrict: 'E',
        link: function(scope, element, attr) {
            console.log(element);
            console.log(scope);
            console.log(attr);
            scope.$watch('model.$error', function(data) {
                if(data) {
                    scope.errorMessages = [];
                    angular.forEach(data, function (value, key) {
                        scope.errorMessages.push(scope.messages[scope.model.$name][key])
                    });
                    console.log(scope.errorMessages);
                    //if(scope.errorMessages.length > 0) {
                    //    element.removeClass('has-success');
                    //    element.addClass('has-error');
                    //} else {
                    //    element.removeClass('has-error');
                    //    element.addClass('has-success');
                    //}
                }
            });
        },
        template:'<small ng-repeat="message in errorMessages" class="help-block" ng-bind="message"></small>'
    }
});