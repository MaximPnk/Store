angular.module('app', ['ngStorage']).controller('a1productFormController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/';

    if (document.URL.match(".*id=[0-9]+$")) {
        $scope.id = document.URL.split("id=")[1];
    }

    $scope.fillForm = function() {
        if ($scope.id != null) {
            $http.get(contextPath + 'api/v1/products/' + $scope.id)
                .then(function (response) {
                    $scope.productInForm = response.data;
                });
        }
    }

    $scope.createOrUpdate = function () {
        if ($scope.id == null) {
            $http.post(contextPath + 'api/v1/products/', $scope.productInForm)
        } else {
            $http.put(contextPath + 'api/v1/products/', $scope.productInForm)
        }
        $scope.goToShop();
    }

    $scope.goToShop = function () {
        window.location = contextPath + 'a1index.html';
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
        $scope.fillForm();
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
        $scope.selectedOption = 5;
        $scope.updatePageLimit();
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
        window.location = contextPath + 'a1auth.html';
    }

    $scope.logout = function() {
        $scope.clearUserData();
    }

});