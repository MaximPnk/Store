angular.module('app', []).controller('productFormController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/products/';

    $scope.id = 2;

    $scope.fillForm = function() {
        if ($scope.id != null) {
            $http.get(contextPath + $scope.id)
                .then(function (response) {
                   $scope.product = response.data;
                });
        }
    };

    $scope.fillForm();

    $scope.createOrUpdate = function () {
        if ($scope.id == null) {
            $http.post(contextPath, $scope.product)
        } else {
            $http.put(contextPath, $scope.product)
        }

    };

});