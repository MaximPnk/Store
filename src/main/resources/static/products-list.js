angular.module('app', []).controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/api/v1/products/';

    $scope.fillProducts = function (page) {
        $http({
            url: contextPath,
            method: "GET",
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                page: page ? page : 1,
                limit: $scope.limitPage ? $scope.limitPage : 5
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePageIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.fillProducts();

    $scope.limits = [3, 5, 7, 10];

    $scope.updateSelected = function() {
        switch($scope.selectedOption){
            case 3:
                $scope.limitPage = 3;
                break;
            case 5:
                $scope.limitPage = 5;
                break;
            case 7:
                $scope.limitPage = 7;
                break;
            case 10:
                $scope.limitPage = 10;
                break;
        }
        $scope.fillProducts();
    }

    $scope.generatePageIndexes = function (start, end) {
        let arr = [];
        for (let i = start; i < end + 1; i ++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + id)
            .then(function () {
                $scope.fillProducts();
            });
    };

    $scope.clearFilters = function () {
        document.getElementById("filterMinPrice").value = null;
        document.getElementById("filterMaxPrice").value = null;
        document.getElementById("filterTitle").value = null;
        $scope.filter.min = null;
        $scope.filter.max = null;
        $scope.filter.title = null;
        $scope.fillProducts();
    }

    $scope.openForm = function (id) {
        window.location.assign('product-form.html?id=' + id);
    }

});