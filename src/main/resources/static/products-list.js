angular.module('app', []).controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/products/';

    $scope.fillProducts = function (page) {
        $http({
            url: contextPath,
            method: "GET",
            params: {
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                page: page
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePageIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.fillProducts();

    $scope.generatePageIndexes = function (start, end) {
        let arr = [];
        for (let i = start; i < end + 1; i ++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.fillWithFilter = function () {
        $scope.fillProducts();
        document.getElementById("filterMinPrice").value = null;
        document.getElementById("filterMaxPrice").value = null;
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + id)
            .then(function () {
                $scope.fillProducts();
            });
    };

    $scope.clearFilters = function () {
        $scope.filter.min = null;
        $scope.filter.max = null;
        $scope.fillProducts();
    }

});