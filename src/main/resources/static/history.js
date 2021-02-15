angular.module('app', ['ngStorage']).controller('historyController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

    $scope.date1 = '2021-02-11T14:53:06.656';

    $scope.formatDate = function(date){
        return new Date(date);
    };

    $scope.fillHistoryForCustomer = function () {
        $http.get(contextPath + 'api/v1/order/curr')
            .then(function (response) {
                $scope.customerHistory = response.data;
        });
    }

    $scope.fillHistoryForAdmin = function () {
        $http.get(contextPath + 'api/v1/order/')
            .then(function (response) {
                $scope.history = response.data;
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
        if ($scope.isCustomer) {
            $scope.fillHistoryForCustomer();
        } else if ($scope.isAdmin) {
            $scope.fillHistoryForAdmin();
        }
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
    }

    if ($localStorage.shopToken) {
        $scope.customerHistory = null;
        $scope.history = null;
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
        window.location.href = contextPath + 'auth.html';
    }

    $scope.goToShop = function () {
        window.location.href = contextPath + 'index.html';
    }

    $scope.goToCart = function () {
        window.location = contextPath + 'cart.html';
    }

    $scope.goToReg = function () {
        window.location = contextPath + 'registration.html';
    }

    $scope.logout = function() {
        $scope.clearUserData();
    }

});