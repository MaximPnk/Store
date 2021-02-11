angular.module('app', []).controller('productFormController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/products/';

    if (document.URL.match(".*id=[0-9]+$")) {
        $scope.id = document.URL.split("id=")[1];
    }

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