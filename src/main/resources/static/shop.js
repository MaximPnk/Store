app.controller('shopController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/';


    $scope.fillProducts = function (page) {
        $http({
            url: contextPath + 'api/v1/products/',
            method: "GET",
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                present: $scope.present ? $scope.present : false,
                page: page ? page : 1,
                limit: $scope.limitPage ? $scope.limitPage : 5
            }
        }).then(function (response) {
            console.log(response.data);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePageIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.fillProducts();

    $scope.limits = [3, 5, 7, 10];

    $scope.updatePageLimit = function() {
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

    $scope.goToProductForm = function (id) {
        if (id == null) {
            window.location.href = contextPath + '#!/product-form';
        } else {
            window.location.href = contextPath + '#!/product-form?id=' + id;
        }
    }

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + 'api/v1/products/' + id)
            .then(function () {
                $scope.fillProducts();
            });
    };

    $scope.addToCart = function (id) {
        $http.get(contextPath + "api/v1/cart/add/" + id);
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

});