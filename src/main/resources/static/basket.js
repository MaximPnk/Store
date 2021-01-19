angular.module('app', []).controller('basketController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/basket/';

    $scope.userId = 1;

    $scope.fillBasket = function (user_id) {
        $http({
            url: contextPath,
            method: "GET",
            params: {
                userId: user_id ? user_id : 1
            }
        }).then(function (response) {
            $scope.products = response.data;
        });
    };

    $scope.fillBasket();

    $scope.clearBasket = function() {
        $http.delete(contextPath + "/" + $scope.userId)
            .then(function () {
            $scope.fillBasket();
        });
    };

    $scope.removeProduct = function (product) {
        $http({
            url: contextPath,
            method: "DELETE",
            headers: {
                'Content-Type': "application/json"
            },
            data: product
        }).then(function () {
            $scope.fillBasket();
        });
    };

    $scope.updateCount = function(p, count) {
        p.count = p.count + count;
        $http.put(contextPath, p).then( function () {
            $scope.fillBasket();
        });
    };

});