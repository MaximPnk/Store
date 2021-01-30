angular.module('app', []).controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/cart';

    $scope.fillCart = function() {
        $http.get(contextPath)
            .then(function (response) {
                $scope.items = response.data;
        });
    };

    $scope.fillCart();

    $scope.clearCart = function() {
        $http.get(contextPath + "/clear")
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.inc = function (id) {
        $http.get(contextPath + "/inc/" + id)
            .then(function () {
                $scope.fillCart();
        });
    };

    $scope.dec = function (id) {
        $http.get(contextPath + "/dec/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.rm = function (id) {
        $http.get(contextPath + "/rm/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

});