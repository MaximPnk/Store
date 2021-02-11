angular.module('app', ['ngStorage']).controller('templateController', function ($scope, $http, $localStorage) {
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
        // $scope.fill();
    }

    $scope.clearUserData = function() {
        delete $localStorage.shopToken;
        $scope.authorized = false;
        $scope.username = null;
        $scope.isAdmin = false;
        $scope.isCustomer = false;
        // $scope.clear = null
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

    $scope.logout = function() {
        $scope.clearUserData();
    }

});