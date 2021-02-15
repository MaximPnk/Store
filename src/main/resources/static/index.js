app.controller('indexController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:8189/';

    $rootScope.decodeToken = function(token) {
        let jwtData = token.split('.')[1];
        let decodedJwtJsonData = window.atob(jwtData);
        return JSON.parse(decodedJwtJsonData);
    }

    $rootScope.addUserData = function(token) {
        $rootScope.authorized = true;
        $rootScope.username = token.sub;
        $rootScope.isAdmin = token.roles.includes("ROLE_ADMIN");
        $rootScope.isCustomer = token.roles.includes("ROLE_CUSTOMER");
    }

    $rootScope.clearUserData = function() {
        delete $localStorage.shopToken;
        $rootScope.authorized = false;
        $rootScope.username = null;
        $rootScope.isAdmin = false;
        $rootScope.isCustomer = false;
    }

    $rootScope.checkAuth = function () {
        if ($localStorage.shopToken) {
            $rootScope.decodedShopToken = $rootScope.decodeToken($localStorage.shopToken);
            if ($rootScope.decodedShopToken.exp <= (new Date().getTime() / 1000)) {
                $rootScope.clearUserData();
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.shopToken;
                $rootScope.addUserData($rootScope.decodedShopToken);
            }
        } else {
            $rootScope.clearUserData();
        }
    }

    $rootScope.checkAuth();

});