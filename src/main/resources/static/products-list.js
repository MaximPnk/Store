angular.module('app', []).controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189';

    $scope.fillProducts = function () {
        $http({
            url: contextPath + '/products',
            method: "GET",
            params: {
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null
            }
        }).then(function (response) {
            $scope.productsList = response.data;
        });
    };

    $scope.fillProducts();

    $scope.deleteProduct = function (id) {
        $http({
            url: contextPath + '/products/' + id,
            method: "DELETE"
        }).then (function () {
            window.location.reload();
        });
    };

    // $scope.deleteProduct(id);

});