angular.module('app', ['ngStorage']).controller('registrationController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

    $scope.registerUser = function () {
        if ($scope.newUser.password !== $scope.pwConfirm) {
            $scope.pwMatch = true;
        } else {
            $http.post(contextPath + 'registration/', $scope.newUser)
                .then(function successCallback() {
                    $scope.registered = true;
                }, function errorCallback(response) {
                    if (response.data.message) {
                        alert(response.data.message);
                    } else if (response.data.messages) {
                        for (let e in response.data.messages) {
                            alert(response.data.messages[e]);
                        }
                    }
                });
        }
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
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
        $scope.registered = false;
        $scope.pwMatch = false;
        $scope.newUser = null;
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

    $scope.goToShop = function () {
        window.location = contextPath + 'index.html';
    }
});