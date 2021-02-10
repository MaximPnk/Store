angular.module('app', []).controller('shopController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/';

    $http.defaults.headers.common.Authorization = null;
    $scope.authorized = false;
    $scope.jwtData = null;
    $scope.decodedJwtJsonData = null;
    $scope.decodedJwtData = null;
    $scope.isAdmin = false;
    $scope.isCustomer = false;
    $scope.username = null;

    $scope.tryToAuth = function () {
        $http.post(contextPath + 'auth/', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.authorized = true;

                    $scope.jwtData = response.data.token.split('.')[1];
                    $scope.decodedJwtJsonData = window.atob($scope.jwtData);
                    $scope.decodedJwtData = JSON.parse($scope.decodedJwtJsonData);
                    $scope.username = $scope.decodedJwtData.sub;
                    $scope.isAdmin = $scope.decodedJwtData.roles.includes("ROLE_ADMIN");
                    $scope.isCustomer = $scope.decodedJwtData.roles.includes("ROLE_CUSTOMER");

                    $scope.fillProducts();
                    $scope.fillCart();
                }
            }, function errorCallback(response) {
                window.alert("Error");
            });
    };

    $scope.logout = function () {
        $http.defaults.headers.common.Authorization = null;
        $scope.authorized = false;
        $scope.jwtData = null;
        $scope.decodedJwtJsonData = null;
        $scope.decodedJwtData = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
        $scope.username = null;
    }

    $scope.fillProducts = function (page) {
        $http({
            url: contextPath + 'api/v1/products/',
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
        $http.delete(contextPath + 'api/v1/products/' + id)
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

    $scope.addToCart = function (id) {
        $http.get(contextPath + "api/v1/cart/add/" + id)
            .then(function () {
                $scope.fillCart();
            })
    };

    $scope.fillCart = function() {
        $http.get(contextPath + 'api/v1/cart')
            .then(function (response) {
                $scope.items = response.data;
            });
    };

    $scope.inc = function (id) {
        $http.get(contextPath + "api/v1/cart/inc/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.dec = function (id) {
        $http.get(contextPath + "api/v1/cart/dec/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.rm = function (id) {
        $http.get(contextPath + "api/v1/cart/rm/" + id)
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.clearCart = function() {
        $http.get(contextPath + "api/v1/cart/clear")
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.makeOrder = function () {
        $http.get(contextPath + 'order/')
            .then(function () {
                $scope.fillProducts();
                $scope.fillCart();
            });
    }

    $scope.showProductForm = false;

    $scope.openProductForm = function(productId) {
        if (productId != null) {
            $http.get(contextPath + 'api/v1/products/' + productId)
                .then(function (response) {
                    $scope.productInForm = response.data;
                });
        } else {
            $scope.productInForm = null;
        }
        $scope.showProductForm = true;
    };

    $scope.hideProductForm = function() {
        $scope.showProductForm = false;
    }

    $scope.createOrUpdate = function (id) {
        if (id == null) {
            $http.post(contextPath + 'api/v1/products/', $scope.productInForm)
                .then(function() {
                    $scope.fillProducts();
                    $scope.hideProductForm();
                    $scope.productInForm = null;
                });
        } else {
            $http.put(contextPath + 'api/v1/products/', $scope.productInForm)
                .then(function() {
                    $scope.fillProducts();
                    $scope.hideProductForm();
                    $scope.productInForm = null;
                });
        }
    };

});