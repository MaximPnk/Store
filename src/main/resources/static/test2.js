angular.module('app', ['ngStorage']).controller('test2Controller', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

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
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
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

    $scope.tryToAuth = function() {
        $http.post(contextPath + 'auth/', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.decodedShopToken = ($scope.decodeToken(response.data.token));
                    $scope.addUserData($scope.decodedShopToken);
                    $localStorage.shopToken = response.data.token;

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.authorized = true;
                }
            }, function errorCallback(response) {
                window.alert("Error");
            });
    }

    $scope.logout = function() {
        $scope.clearUserData();
    }

    $scope.goToFirstPage = function () {
        window.location = contextPath + 'test1.html';
    }

});