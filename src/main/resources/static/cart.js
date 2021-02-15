angular.module('app', ['ngStorage']).controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

    $scope.fillCart = function() {
        $http.get(contextPath + 'api/v1/cart/')
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

    $scope.goToShop = function () {
        window.location = contextPath + 'index.html';
    }

    $scope.goToHistory = function () {
        window.location = contextPath + 'history.html';
    }

    $scope.makeOrder = function () {
        $http.post(contextPath + 'api/v1/order/')
            .then(function successCallback() {
                $scope.goToShop();
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    }

    $scope.decodeToken = function(token) {
        let jwtData = token.split('.')[1];
        let decodedJwtJsonData = window.atob(jwtData);
        return JSON.parse(decodedJwtJsonData);
    }

    $scope.addUserData = function(token) {
        $scope.authorized = true;
        $scope.username = token.sub;
        $scope.isAdmin = token.roles.includes("ROLE_ADMIN");
        $scope.isCustomer = token.roles.includes("ROLE_CUSTOMER");
        $scope.fillCart();
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
        $scope.items = null;
    }

    if ($localStorage.shopToken) {
        $scope.decodedShopToken = $scope.decodeToken($localStorage.shopToken);
        if ($scope.decodedShopToken.exp <= (new Date().getTime() / 1000)) {
            $scope.clearUserData();
        } else {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.shopToken;
            $scope.addUserData($scope.decodedShopToken);
        }
    } else {
        $scope.clearUserData();
    }

    $scope.goToAuth = function () {
        window.location = contextPath + 'auth.html';
    }

    $scope.goToReg = function () {
        window.location = contextPath + 'registration.html';
    }

    $scope.logout = function() {
        $scope.clearUserData();
    }

});